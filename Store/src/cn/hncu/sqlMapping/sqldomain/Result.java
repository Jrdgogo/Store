package cn.hncu.sqlMapping.sqldomain;

public class Result {

	private String property;
	private String column;
	private String javaType;
	private String jdbcType;
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	public String getJdbcType() {
		return jdbcType;
	}
	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}
	@Override
	public String toString() {
		return "Result [property=" + property + ", column=" + column
				+ ", javaType=" + javaType + ", jdbcType=" + jdbcType + "]";
	}
	
	
	
}
