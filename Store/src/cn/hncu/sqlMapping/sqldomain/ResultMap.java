package cn.hncu.sqlMapping.sqldomain;

import java.util.List;
import java.util.Map;

public class ResultMap<T>{
	private Class<T> resultType;
	private List<Result> list;
	private Map<String,Collection<?>> collections;
	private Map<String,Association<?>> associations;
	
	public Class<T> getResultType() {
		return resultType;
	}
	
	public List<Result> getList() {
		return list;
	}
	public void setList(List<Result> list) {
		this.list = list;
	}
	
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
	public static<T> ResultMap<T> createResultMap(Class<T> cls){
		ResultMap<T> resultMap=new ResultMap<T>();
		resultMap.resultType=cls;
		return resultMap;
	}

}
