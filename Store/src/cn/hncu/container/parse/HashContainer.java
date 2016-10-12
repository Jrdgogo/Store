package cn.hncu.container.parse;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import cn.hncu.container.annotation.Container;

public class HashContainer{

	private static Map<String, Object> map=new HashMap<String, Object>(0);
	private static Map<String, Object> propertyMap=new HashMap<String, Object>(0);
	private static Map<String, Object> constructorMap=new HashMap<String, Object>(0);
	
	public static void put(Properties p){
		@SuppressWarnings("unchecked")
		Enumeration<String> enums= (Enumeration<String>) p.propertyNames();
		while(enums.hasMoreElements()){
			String key=enums.nextElement();
			map.put(key, p.getProperty(key));
		}
	}

	public static void put(String key, String className) 
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		if(key==null||key.equals(""))
			key=className;
		Class<?> cls=Class.forName(className);
		Object obj=null;
		if(!constructorMap.isEmpty()){
			Class<?>[] clss=new Class[constructorMap.size()];
			Iterator<Object> it=constructorMap.values().iterator();
			int i=0;
			while(it.hasNext()){
				clss[i]=it.next().getClass();
			}
			Constructor<?>[] constructors=cls.getConstructors();
			forConstructor:for(Constructor<?> constructor:constructors){
				Class<?>[]  paramclass=constructor.getParameterTypes();
				if(paramclass.length!=clss.length)
					continue;
				for(int n=0;n<paramclass.length;n++){
					if(!paramclass[n].isAssignableFrom(clss[n]))
						continue forConstructor;
				}
				obj=constructor.newInstance(constructorMap.values().toArray());
				map.put(key,obj);
			}
		}else{
			obj=cls.newInstance();
			map.put(key, obj);
		}
		if(!propertyMap.isEmpty()){
			Iterator<String> it=propertyMap.keySet().iterator();
			while(it.hasNext()){
				String name=it.next();
				try {
					Field field=cls.getDeclaredField(name);
					field.setAccessible(true);
					field.set(obj, propertyMap.get(name));
				} catch (Exception e) {
					Method[] mothods=cls.getMethods();
					String methodName="set"+name.substring(0, 1).toUpperCase()+name.substring(1);
					for(Method mothod:mothods){
						if(mothod.getName().equals(methodName)){
							mothod.invoke(obj, propertyMap.get(name));break;
						}
					}
				}
			}
		}
		try {
			Method init=cls.getMethod("init", new Class[]{});
			init.invoke(obj, new Object[]{});
		} catch (Exception e) {
		}
		constructorMap.clear();propertyMap.clear();
	}

	public static void setProperty(String key, String value) {
		if(value.startsWith("${")&&value.endsWith("}"))
			propertyMap.put(key, map.get(value.substring(2, value.length()-1)));
		else
			propertyMap.put(key, value);
	}

	public static void setPropertyBean(String key, String ref) {
		propertyMap.put(key, map.get(ref));
	}

	public static void setConstructor(String key, String value) {
		if(value.startsWith("${")&&value.endsWith("}"))
			constructorMap.put(key, map.get(value.substring(2, value.length()-1)));
		else
			constructorMap.put(key, value);
		
	}

	public static void setConstructorBean(String key, String ref) {
		constructorMap.put(key, map.get(ref));
	}

	
	private static List<Class<?>> excludeAnnotationList=new ArrayList<Class<?>>(0);
	private static List<Class<?>> includeAnnotationList=new ArrayList<Class<?>>(0);
	public static void setExcludeFilter(String type, String expression) throws ClassNotFoundException {
		
		if(type.equals("annotation")){
			Class<?> cls=Class.forName(expression);
			if(cls.isAnnotationPresent(Container.class))
				excludeAnnotationList.add(cls);
		}else if(type.equals("regex")){
			
		}
		
	}

	public static void setIncludeFilter(String type, String expression) throws ClassNotFoundException {
		if(type.equals("annotation")){
			Class<?> cls=Class.forName(expression);
			if(cls.isAnnotationPresent(Container.class))
				includeAnnotationList.add(cls);
		}else if(type.equals("regex")){
			
		}
	}

	public static void put(String basePath) 
			throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
		List<Class<?>> clss=ErgodicClass.getAllBeanClass(basePath);
		forClass:for(Class<?> cls:clss){//每一个bean
			Annotation[] annotations= cls.getAnnotations();
			if(!includeAnnotationList.isEmpty()){
				for(Annotation annotation:annotations){//待定bean的指定注解
					Class<?> annotationClass =annotation.annotationType();
					if(!includeAnnotationList.contains(annotationClass))
						continue;
					Method method=annotationClass.getMethod("value", new Class[]{});
					String value=(String) method.invoke(annotation, new Object[]{});
					if(value==null||value.equals(""))
						value=cls.getCanonicalName();
					map.put(value, cls.newInstance());
					continue forClass;
				}
				continue;
			}
			if(!excludeAnnotationList.isEmpty()){
				for(Annotation annotation:annotations){
					Class<?> annotationClass =annotation.annotationType();
					if(excludeAnnotationList.contains(annotationClass))
						continue forClass;
				}
			}
			//无要求的Container注解
			for(Annotation annotation:annotations){
				Class<?> annotationClass =annotation.annotationType();
				if(!annotationClass.isAnnotationPresent(Container.class))
					continue;
				Method method=annotationClass.getMethod("value", new Class[]{});
				String value=(String) method.invoke(annotation, new Object[]{});
				if(value==null||value.equals(""))
					value=cls.getCanonicalName();
				map.put(value, cls.newInstance());
				break;
			}
		}
		excludeAnnotationList.clear();
		includeAnnotationList.clear();
	}

	public static Map<String, Object> getContainer() {
		return map;
	}
	
}
