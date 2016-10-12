package cn.hncu.sqlMapping.sqldomain;

import java.util.List;
import java.util.Map;

public class Collection<T> {
	
	private String property;
	private Class<T> type;
	private List<Result> list;
	private Map<String,Collection<?>> collections;
	private Map<String,Association<?>> associations;
	
	
	public Map<String, Collection<?>> getCollections() {
		return collections;
	}
	public void setCollections(Map<String, Collection<?>> collections) {
		this.collections = collections;
	}
	public Map<String, Association<?>> getAssociations() {
		return associations;
	}
	public void setAssociations(Map<String, Association<?>> associations) {
		this.associations = associations;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public Class<T> getJavaType() {
		return type;
	}
	public List<Result> getList() {
		return list;
	}
	public void setList(List<Result> list) {
		this.list = list;
	}
	
	public static<T> Collection<T> createCollection(Class<T> cls){
		Collection<T> collection=new Collection<T>();
		collection.type=cls;
		return collection;
	}

}
