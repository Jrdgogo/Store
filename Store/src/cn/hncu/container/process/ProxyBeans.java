package cn.hncu.container.process;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

import cn.hncu.container.parse.HashContainer;
import cn.hncu.container.process.proxy.Proxy;

public class ProxyBeans{
	

	public void proxyBeans() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		
		Map<String, Object> map=HashContainer.getContainer();
		BeansContainer beans=new BeansContainer();
		Map<String, Object> container=beans.getContainer();
		
		Iterator<String> it=map.keySet().iterator();
		while(it.hasNext()){
			String key=it.next();
			Object obj=map.get(key);
			if(obj.getClass().equals(String.class))
				continue;
			//Class<?> cls=obj.getClass();
			
			Proxy proxy=new Proxy();
		    container.put(key,proxy.getProxyInstancebycglib(obj));
			
		}

	}

}
