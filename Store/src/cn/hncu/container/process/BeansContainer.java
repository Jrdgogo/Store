package cn.hncu.container.process;

import java.util.HashMap;
import java.util.Map;

import cn.hncu.container.interfaces.Container;

public class BeansContainer implements Container{

	private static Map<String, Object> container=new HashMap<String, Object>(0);

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(String id) {
		
		return (T) container.get(id);
	}

	public Map<String, Object> getContainer() {
		return container;
	}

	public void setContainer(Map<String, Object> container) {
		BeansContainer.container = container;
	}
    
}
