package cn.hncu.container.jdbc;

import java.beans.PropertyVetoException;

import com.mchange.v2.c3p0.ComboPooledDataSource;


public class DriverDataSource extends DriverBasedDataSource {
	
	public void init() throws PropertyVetoException {
		ComboPooledDataSource dataSource=new ComboPooledDataSource();
		dataSource.setDriverClass(getDriver());
		dataSource.setJdbcUrl(getUrl());
		dataSource.setUser(getUsername());
		dataSource.setPassword(getPassword());
		dataSource.setInitialPoolSize(2);
		dataSource.setMaxIdleTime(30);
		dataSource.setMaxPoolSize(10);
		dataSource.setMinPoolSize(2);
		dataSource.setMaxStatements(50);
		setDataSource(dataSource);
	}
}
