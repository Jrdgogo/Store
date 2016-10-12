package cn.hncu.sqlMapping.xmlparse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLContainer {

	private static Map<String, Class<?>> typeMap=new HashMap<String, Class<?>>(0);
	private static List<MappingContainer<?>> list=new ArrayList<MappingContainer<?>>();
	
	public static List<MappingContainer<?>> getList() {
		return list;
	}

	public static void add(MappingContainer<?> mapping) {
		list.add(mapping);
	}

	public static void setTypeMap(Map<String, Class<?>> typeMap) {
		SQLContainer.typeMap = typeMap;
	}

	public static Map<String, Class<?>> getTypeMap() {
		return typeMap;
	}

	public static void setTypeMap(String name,String className) throws ClassNotFoundException {
		typeMap.put(name, Class.forName(className));
	}
	
	
}
