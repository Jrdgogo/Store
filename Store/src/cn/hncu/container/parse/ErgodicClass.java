package cn.hncu.container.parse;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.hncu.container.annotation.Container;




public class ErgodicClass{
	
	private static final Logger log=Logger.getLogger(ErgodicClass.class);

	public static List<Class<?>> getAllBeanClass(String packageName){
		List<Class<?>> clss=new ArrayList<Class<?>>(0);
		for(Class<?> cls:getAllClass(packageName)){
			Annotation[] annotations= cls.getAnnotations();
			for(Annotation annotation:annotations){//待定bean的指定注解
				Class<?> annotationClass =annotation.annotationType();
				if(annotationClass.isAnnotationPresent(Container.class))
					clss.add(cls);
			}
		}
		return clss;
	}
	
	public static List<Class<?>> getAllClass(String packageName){
		List<Class<?>> clss=new ArrayList<Class<?>>(0);
		URL url=Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));
		try {
			String path=url.getPath();
			if(path.contains("/bin/"))
				path=path.substring(path.indexOf("/bin/")+"/bin/".length());
			else if(path.contains("/classes/"))
				path=path.substring(path.indexOf("/classes/")+"/classes/".length());
			if(path.endsWith("/"))
				path=path.substring(0, path.length()-1);
			clss.addAll(findClass(new File(url.getFile()),path.replace("/", ".")));
		} catch (Exception e) {
			log.error("遍历bean失败", e);
		}
		
		return clss;
	}

	private static List<Class<?>> findClass(File file, String dirName) throws ClassNotFoundException {
		List<Class<?>> clss=new ArrayList<Class<?>>(0);
		if(!file.exists())
			return clss;
		File[] files=file.listFiles();
		for(File f:files){
			if(f.isDirectory()){
				if(dirName.equals(""))
					clss.addAll(findClass(f,f.getName()));
				else
					clss.addAll(findClass(f,dirName+"."+f.getName()));
			}
			else{
				if(f.getName().endsWith(".class")){
					clss.add(Class.forName(dirName+"."+f.getName().substring(0, f.getName().lastIndexOf(".class"))));
				}
			}
		}
		
		return clss;
	}


}
