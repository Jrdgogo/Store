package cn.hncu.container.jdbc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

import cn.hncu.container.process.BeansContainer;
import cn.hncu.sqlMapping.runSql.SQLExecute;

public class IConnection {
	
	private static DataSourceTransactionManager manager;
	
	public final Connection getConnection(){
		try {
			if(manager==null)
				manager=new BeansContainer().getBean("transactionManager");
			return manager.getConnection();
		} catch (SQLException e) {}
		return null;
	}
	@SuppressWarnings("unchecked")
	public <T> T getMapping(final Class<T> cls){
			return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{cls},
					new InvocationHandler() {
											
					@Override
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
				        return SQLExecute.runSql(getConnection(),cls,method,args);
					}
			} );
		
	}
}
