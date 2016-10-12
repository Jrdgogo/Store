package cn.hncu.sqlMapping.sqldomain;

public class SQlXML {
	
	private String resultType;
	private String resultMap;
	private String sqlparam;
	private String parameterType;
	private String sql;
	private String executeType;
	
	public String getExecuteType() {
		return executeType;
	}
	public void setExecuteType(String executeType) {
		this.executeType = executeType;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	public String getResultMap() {
		return resultMap;
	}
	public void setResultMap(String resultMap) {
		this.resultMap = resultMap;
	}
	public String getSqlparam() {
		return sqlparam;
	}
	public void setSqlparam(String sqlparam) {
		this.sqlparam = sqlparam;
	}
	public String getParameterType() {
		return parameterType;
	}
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	@Override
	public String toString() {
		return "SQlXML [resultType=" + resultType + ", resultMap=" + resultMap
				+ ", sqlparam=" + sqlparam + ", parameterType=" + parameterType
				+ ", sql=" + sql + "]";
	}

}
