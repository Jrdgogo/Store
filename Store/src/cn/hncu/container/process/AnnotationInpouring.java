package cn.hncu.container.process;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

import cn.hncu.container.annotation.Inpouring;
import cn.hncu.container.parse.HashContainer;

public class AnnotationInpouring {

	public static void Inpouring() throws IllegalArgumentException, IllegalAccessException{
		Map<String, Object> map=HashContainer.getContainer();
		Map<String, Object> proxyMap=new BeansContainer().getContainer();
		Iterator<Object> it=map.values().iterator();
		while(it.hasNext()){
			Object obj=it.next();
			Class<?> cls=obj.getClass();
			if(cls.equals(String.class))
				continue;
			Field[] fields=cls.getDeclaredFields();
			for(Field field:fields){
				if(!field.isAnnotationPresent(Inpouring.class))
					continue;
				Inpouring inpouring=field.getAnnotation(Inpouring.class);
				String name=inpouring.name();
				field.setAccessible(true);
				field.set(obj, proxyMap.get(name));
			}
		}
	}
	
}
