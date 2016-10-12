package cn.hncu.sqlMapping.runSql;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.hncu.sqlMapping.sqldomain.Association;
import cn.hncu.sqlMapping.sqldomain.Collection;
import cn.hncu.sqlMapping.sqldomain.Result;
import cn.hncu.sqlMapping.sqldomain.ResultMap;
import cn.hncu.sqlMapping.sqldomain.SQlXML;
import cn.hncu.sqlMapping.util.ClassForName;
import cn.hncu.sqlMapping.xmlparse.MappingContainer;
import cn.hncu.sqlMapping.xmlparse.SQLContainer;

public class SQLExecute {

	public static Object runSql(Connection connection, Class<?> cls,Method method, Object[] paramagrs) throws Exception{
		List<MappingContainer<?>> list=SQLContainer.getList();
		for(MappingContainer<?> container:list){
			//确定mapping
			if(!container.returnMapping().equals(cls))
				continue;
			//获取语句
			SQlXML sqlxml=container.getStatementMap().get(method.getName());
			String sql=sqlxml.getSql();
			if(sqlxml.getSqlparam()!=null)//是否传sql语句
				sql=sql.replace("${"+sqlxml.getSqlparam()+"}", (String)paramagrs[0]);
			PreparedStatement statement=connection.prepareStatement(sql);
			//执行(设置?参数)
			if(paramagrs!=null)
				if(paramagrs[1] instanceof Object[]){
					   Object[] paramobjs=(Object[])paramagrs[1];
					for(int i=0;i<paramobjs.length;i++){
						statement.setObject(i+1, paramobjs[i]);
					}
				}
			//System.out.println(sql);
			//确定执行的s/u/d/i类别
			String type=sqlxml.getExecuteType();
			//获取返回类型
			ResultMap<?> resultMap=container.getResultMap().get(sqlxml.getResultMap());
			Class<?> returnClass=returnClass(method, resultMap);
			//获取dao中mapping接口的函数返回实例(基本数据类型及包装类和数组为null)
			Object returnObj=returnObj(method, resultMap);
			if("select".equals(type)){
				ResultSet resultSet=statement.executeQuery();
				if(resultMap!=null)
					resultMap(resultSet, resultMap,returnObj,returnClass);
				else{//只支持值对象(查询语句)
					while(resultSet.next()){
						returnObj=returnClass.newInstance();
						Field[] fields=returnClass.getDeclaredFields();
						for(Field field:fields){
							field.setAccessible(true);
							if(field.getType().equals(byte[].class)){
								setBlob(resultSet, returnClass, returnObj, field.getName(), field.getName());
								continue;
							}
							try {
								field.set(returnObj, resultSet.getObject(field.getName()));
							} catch (Exception e) {
							}
						}
						return returnObj;
					}
				}
					
			}
			return returnObj;
		}
		return null;
	}

	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void resultMap(ResultSet resultSet,
			ResultMap<?> resultMap, Object returnObj, Class<?> returnClass) throws Exception {
		while(resultSet.next()){
			Object instance=returnClass.newInstance();
			//设置属性
			setProperty(resultMap.getList(), resultSet, returnClass, instance);
			if(returnObj!=null){
				if(returnObj instanceof List){
					Iterator it=((List)returnObj).iterator();
					while(it.hasNext()){
						Object o=it.next();
						if(o.equals(instance)){
							instance=o;
						}
					}
					((List)returnObj).remove(instance);
				}
				else if(returnObj instanceof Set){
					Iterator it=((Set)returnObj).iterator();
					while(it.hasNext()){
						Object o=it.next();
						if(o.equals(instance)){
							instance=o;
						}
					}
					((Set)returnObj).remove(instance);
				}
			}
			
			setAssociation(resultMap.getAssociations(), resultSet, returnClass, instance);
			setCollection(resultMap.getCollections(), resultSet, returnClass, instance);
			if(returnObj!=null){
				if(returnObj instanceof List){
					((List)returnObj).add(instance);
				}
				else if(returnObj instanceof Set){
					((Set)returnObj).add(instance);
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void setCollection(Map<String, Collection<?>> map,
			ResultSet resultSet, Class<?> returnClass, Object instance)
					throws Exception {
		Iterator<Entry<String, Collection<?>>> it=map.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, Collection<?>> entry=it.next();
			String property=entry.getKey();
			Collection<?> collection=entry.getValue();
			
			String column=collection.getList().get(0).getColumn();
			if(collection.getJavaType().equals(byte.class)){
				setBlob(resultSet, returnClass, instance, property, column);
				return;
			}
			property="get"+property.substring(0,1).toUpperCase()+property.substring(1);
			Method method2=returnClass.getMethod(property);
			Object obj=method2.invoke(instance, new Object[]{});
			
			Class<?> acls=collection.getJavaType();
			Object aobj=acls.newInstance();
			
			setProperty(collection.getList(), resultSet, acls, aobj);
			setCollection(collection.getCollections(), resultSet, acls, aobj);
			setAssociation(collection.getAssociations(), resultSet, acls, aobj);
			
			if(obj instanceof Set){
				((Set)obj).add(aobj);
			}
		}
	}



	private static void setBlob(ResultSet resultSet, Class<?> returnClass,
			Object returnObj, String property, String column)
			throws SQLException, NoSuchFieldException, IllegalAccessException {
		Blob b=resultSet.getBlob(column);
		byte[] buf=null;
		if(b!=null)
			buf=b.getBytes(1, (int)b.length());
		Field field=returnClass.getDeclaredField(property);
		field.setAccessible(true);
		field.set(returnObj, buf);
	}
	private static void setAssociation(Map<String, Association<?>> map,
			ResultSet resultSet, Class<?> returnClass, Object instance)
			throws Exception {
		Iterator<Entry<String, Association<?>>> it=map.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, Association<?>> entry=it.next();
			String property=entry.getKey();
			Association<?> association=entry.getValue();
			property="set"+property.substring(0,1).toUpperCase()+property.substring(1);
			Method method2=returnClass.getMethod(property, new Class[]{association.getJavaType()});
			
			Class<?> acls=association.getJavaType();
			Object aobj=acls.newInstance();
			setProperty(association.getList(), resultSet, acls, aobj);
			setCollection(association.getCollections(), resultSet, acls, aobj);
			setAssociation(association.getAssociations(), resultSet, acls, aobj);
			method2.invoke(instance, new Object[]{aobj});
			
		}
	}

	private static void setProperty(List<Result> list,
			ResultSet resultSet, Class<?> returnClass, Object instance)
			throws Exception {
		for(Result result:list){
			String property=result.getProperty();
			property="set"+property.substring(0,1).toUpperCase()+property.substring(1);
			Method method2=returnClass.getMethod(property, new Class[]{ClassForName.classForName(result.getJavaType())});
		    Object obj=resultSet.getObject(result.getColumn());
			if(obj==null){
		    	obj=returnClass.getMethod(property.replaceFirst("s", "g"), new Class[]{}).invoke(instance, new Object[]{});
		    }
			method2.invoke(instance, new Object[]{obj});
		}
	}

	private static Class<?> returnClass(Method method, ResultMap<?> resultMap) {
		if(resultMap!=null)
			return resultMap.getResultType();
		return method.getReturnType();
	}
	private static Object returnObj(Method method, ResultMap<?> resultMap) throws Exception{
		Class<?> class1=method.getReturnType();
		if(resultMap!=null){
			if(Map.class.equals(class1))
				return new HashMap<>();
			else if(Set.class.equals(class1))
				return new HashSet<>();
			else if(List.class.equals(class1))
				return new ArrayList<>();
		}
		return null;	
	}

}
