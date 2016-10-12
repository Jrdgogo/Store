package cn.hncu.container.process.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.log4j.Logger;

import cn.hncu.container.annotation.TransAction;
import cn.hncu.container.jdbc.DataSourceTransactionManager;
import cn.hncu.container.parse.HashContainer;


public class Proxy {
	private List<ProxyListener> before;
	private List<ProxyListener> after;
	private List<ProxyListener> proxy;
	private ProxyEvent event;
	private Map<Method, List<ProxyListener>> map;
	private static ThreadLocal<Connection> threadLocal=new ThreadLocal<Connection>();
	private static Logger log=Logger.getLogger(Proxy.class);
	public Proxy(){
		before=new ArrayList<ProxyListener>(0);
		after=new ArrayList<ProxyListener>(0);
		proxy=new ArrayList<ProxyListener>(0);
		map=new HashMap<Method, List<ProxyListener>>(0); 
		event=new ProxyEvent(this);
	}
	public boolean addProxyListener(ProxyListener listener){
		return proxy.add(listener);
	}
	public boolean addProxyListener(Method method,ProxyListener listener){
		List<ProxyListener> list=map.get(method);
		if(list==null){
			list=new ArrayList<ProxyListener>();
			map.put(method, list);
		}
		return list.add(listener);
	}
	public boolean addBeforeProxyListener(ProxyListener listener){
		return before.add(listener);
	}
	public boolean addAfterProxyListener(ProxyListener listener){
		return after.add(listener);
	}
	public boolean removeProxyListener(ProxyListener listener){
		return before.remove(listener)||after.remove(listener)||proxy.remove(listener);
	}
	public boolean removeProxyListener(Method method,ProxyListener listener){
		return map.get(method).remove(listener);
	}
	
	private void runMethod(List<ProxyListener> method){
		if(method!=null&&!method.isEmpty())
			for(ProxyListener listener:method){
				action(listener);
			}
	}
	private void runProxy(){
		for(ProxyListener listener:proxy){
			action(listener);
		}
	}
	private void runBefore(){
		for(ProxyListener listener:before){
			action(listener);
		}
	}
	private void runAfter(){
		for(ProxyListener listener:after){
			action(listener);
		}
	}
	private void action(ProxyListener listener) {
		try {
			Method method=listener.getClass().getMethod("proxyperformed", new Class[]{ProxyEvent.class});
			if(method.isAnnotationPresent(TransAction.class))
				try {
					method.setAccessible(true);
					sqlTransAction(method, listener, event);
				} catch (SQLException e) {
					log.error("proxyperformed执行错误",e);
				}
			else
				listener.proxyperformed(event);
		} catch (NoSuchMethodException | SecurityException e) {
			log.error("监听器无proxyperformed方法",e);
		}
	}
	
	private Object sqlTransAction(Method method,Object obj,Object ... args) throws SQLException{
		
		Connection con=((DataSourceTransactionManager) HashContainer.getContainer().get("transactionManager")).getConnection();
		Object o=null;
		try {
			con.setAutoCommit(false);
			log.info("事务开启");
			o=method.invoke(obj, args);
			con.commit();
			log.info("事务提交");
		} catch (Exception e) {
			if(con!=null){
				con.rollback();
				log.error("事务回滚",e);
			}
		}finally{
			threadLocal.set(null);
			if(con!=null){
				con.setAutoCommit(true);
				con.close();
			}
		}
		return o;
	}
	
	
	public <T> T getProxy(T t,Class<?>[] classes){
		return getProxyInstancebyJdk(Thread.currentThread().getContextClassLoader(),classes,t);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getProxyInstancebyJdk(ClassLoader classloader,Class<?>[] classes,final T t){
		return (T)java.lang.reflect.Proxy.newProxyInstance(
				classloader,
				classes,
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						if(!Proxy.this.proxy.isEmpty()){
							runProxy();
							return null;
						}
						if(!Proxy.this.map.isEmpty()){
							if(map.get(method)!=null){
								runMethod(map.get(method));
								return null;
							}
						}
						
						if(!Proxy.this.before.isEmpty())
							runBefore();
						
						Object returnObj=null;
						if(method.isAnnotationPresent(TransAction.class))
							returnObj=sqlTransAction(method, t, args);
						else
							returnObj=method.invoke(t, args);
						
						if(!Proxy.this.after.isEmpty())
							runAfter();
						return returnObj;
					}
				});
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getProxyInstancebycglib(final T t){
		final Class<?>[] clss=t.getClass().getInterfaces();
		
		Callback callback = new MethodInterceptor(){
			@Override
			public Object intercept(Object proxiedObj, Method method,
					Object[] args, MethodProxy proxy) throws Throwable {
				if(!Proxy.this.proxy.isEmpty()){
					runProxy();
					return null;
				}
				if(!Proxy.this.map.isEmpty()){
					if(map.get(method)!=null){
						runMethod(map.get(method));
						return null;
					}
				}
				
				if(!Proxy.this.before.isEmpty())
					runBefore();
				
				Object returnObj=null;
				int count=0;
				for(Class<?> cls:clss){
					try {
						Method interfaceMethod=cls.getMethod(method.getName(), method.getParameterTypes());
						if(interfaceMethod.isAnnotationPresent(TransAction.class))
							count++;
					} catch (Exception e) {
					}
				}
				
				Method instanceMethod=t.getClass().getMethod(method.getName(), method.getParameterTypes());
				if(instanceMethod.isAnnotationPresent(TransAction.class))
					count++;
				if(count>0)
					returnObj=sqlTransAction(method, t, args);
				else
					returnObj=method.invoke(t, args);
				
				if(!Proxy.this.after.isEmpty())
					runAfter();
				return returnObj;
			}
		};
		return (T) Enhancer.create(t.getClass(), callback); 
	}
	private String command;
	public String getProxyCommand() {
		return command;
	}
	public void setProxyCommand(String command) {
		this.command=command;
	}
}
