package cn.hncu.sqlMapping.xmlparse;

import java.util.HashMap;
import java.util.Map;

import cn.hncu.sqlMapping.sqldomain.ResultMap;
import cn.hncu.sqlMapping.sqldomain.SQlXML;

public class MappingContainer<T> {
	
	private Class<T> cls;
	public MappingContainer(Class<T> cls) {
		this.cls=cls;
	}
	public Class<T> returnMapping(){
		return cls;
	}

	private Map<String, String> sqlMap=new HashMap<String, String>(0);
	private Map<String, SQlXML> statementMap=new HashMap<String, SQlXML>(0);
	private Map<String, ResultMap<?>> resultMap=new HashMap<String, ResultMap<?>>(0);

	
	
	public Map<String, ResultMap<?>> getResultMap() {
		return resultMap;
	}
	public void addResult(String id,ResultMap<?> resultMap) {
		this.resultMap.put(id, resultMap);
	}
	
	public Map<String, SQlXML> getStatementMap() {
		return statementMap;
	}

	public void addStatement(String id,SQlXML sql) {
		statementMap.put(id, sql);
	}

	public Map<String, String> getSqlMap() {
		return sqlMap;
	}
	
	public void addSql(String name,String sql){
		sqlMap.put(name, sql);
	}
}
