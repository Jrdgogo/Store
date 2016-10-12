package cn.hncu.container.mapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cn.hncu.container.annotation.Servlet;
import cn.hncu.container.annotation.URL;
import cn.hncu.container.parse.HashContainer;
import cn.hncu.container.process.BeansContainer;

public class RequestMapping {

	private static Map<String, Map<Object, Method>> mapping=new HashMap<String, Map<Object,Method>>(0);
	
	
	public void loadermapping(){
		BeansContainer beansContainer=new BeansContainer();
		Map<String, Object> map=beansContainer.getContainer();
	     Iterator<Entry<String, Object>> it=map.entrySet().iterator();
	     while(it.hasNext()){
	    	 Entry<String, Object> entry= it.next();
	    	 Class<?> cls=HashContainer.getContainer().get(entry.getKey()).getClass();
	    	 if(!cls.isAnnotationPresent(Servlet.class))
	    		 continue;
	    	 Method[] methods=cls.getMethods();
	    	 for(Method method:methods){
	    		 if(!method.isAnnotationPresent(URL.class))
	    			 continue;
	    		 URL url=method.getAnnotation(URL.class);
	    		 String urlMapping=url.value();
	    		 Map<Object, Method> m=new HashMap<Object, Method>();
	    		 m.put(entry.getValue(), method);
	    		 mapping.put(urlMapping, m);
	    	 }
	     }
	     
	}


	public static Map<String, Map<Object, Method>> getMapping() {
		return mapping;
	}

	
}
