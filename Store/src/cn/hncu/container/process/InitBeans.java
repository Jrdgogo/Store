package cn.hncu.container.process;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import cn.hncu.container.interfaces.Container;
import cn.hncu.container.interfaces.ContainerWare;
import cn.hncu.container.parse.HashContainer;

public class InitBeans {

	public static void init() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Map<String, Object> map=HashContainer.getContainer();
		Iterator<Object> it=map.values().iterator();
		while(it.hasNext()){
			Object obj=it.next();
			Class<?> cls=obj.getClass();
			if(cls.equals(String.class))
				continue;
			try {
				if(ContainerWare.class.isAssignableFrom(cls)){
					cls.getMethod("visitContainer", new Class[]{Container.class}).invoke(obj, new Object[]{new BeansContainer()});
				}
				Method method=cls.getMethod("initBean", new Class[]{});
				method.invoke(obj, new Object[]{});
			} catch (Exception e) {
			}
		}
		
	}

}
