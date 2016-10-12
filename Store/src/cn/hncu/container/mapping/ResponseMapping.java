package cn.hncu.container.mapping;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ResponseMapping {
	
	private String viewMapping;
	private String defaultView;
	private static Map<String, Map<String, String>> mapping=new HashMap<String, Map<String,String>>(0);
	public String getViewMapping() {
		return viewMapping;
	}
	public void setViewMapping(String viewMapping) {
		this.viewMapping = viewMapping;
	}
	public String getDefaultView() {
		return defaultView;
	}
	public void setDefaultView(String defaultView) {
		this.defaultView = defaultView;
	}
	
	public void initBean() throws IOException{
		InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream(viewMapping);
		Properties p=new Properties();
		p.load(in);
		String defaultView=p.getProperty(this.defaultView+".view");
		p.remove(defaultView);
		Enumeration<?> enums=p.propertyNames();
		List<String> list=new ArrayList<String>();
		while(enums.hasMoreElements()){
			Object obj=enums.nextElement();
			if(obj instanceof String){
				String key=(String)obj;
				if(list.contains(key))
					continue;
				String key2=null;
				String view=null;
				String url=null;
				
				if(key.endsWith(".view")){
					key2=key.substring(0,key.indexOf(".view"))+".url";
					view=p.getProperty(key);
					url=p.getProperty(key2);
					key=key.substring(0,key.indexOf(".view"));
				}
				else if(key.endsWith(".url")){
					key2=key.substring(0,key.indexOf(".url"))+".view";
					view=p.getProperty(key2);
					url=p.getProperty(key);
					key=key.substring(0,key.indexOf(".url"));
				}
				if(view==null)
					view=defaultView;
				Map<String, String> map=new HashMap<String, String>();
				map.put(view, url);
				mapping.put(key, map);
				list.add(key2);
			}
		}
		
	}
	public static Map<String, Map<String, String>> getMapping() {
		return mapping;
	}
	
	

}
