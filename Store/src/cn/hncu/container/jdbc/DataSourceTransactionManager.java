package cn.hncu.container.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DataSourceTransactionManager{
	
	private DataSource dataSource;
	private ThreadLocal<Connection> t=new ThreadLocal<Connection>();
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	public Connection getConnection() throws SQLException{
		Connection con=t.get();
		if(con==null||con.isClosed()){
			con=dataSource.getConnection();
			t.set(con);
		}
		return con;
	}
	

}
