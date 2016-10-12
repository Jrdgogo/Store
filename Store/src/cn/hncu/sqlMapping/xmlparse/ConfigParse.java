package cn.hncu.sqlMapping.xmlparse;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.hncu.sqlMapping.sqldomain.Association;
import cn.hncu.sqlMapping.sqldomain.Collection;
import cn.hncu.sqlMapping.sqldomain.Result;
import cn.hncu.sqlMapping.sqldomain.ResultMap;
import cn.hncu.sqlMapping.sqldomain.SQlXML;
import cn.hncu.sqlMapping.util.ClassForName;

public class ConfigParse {
	
	public static void parseXmlConfig() throws Exception{
		
		Map<String,String> uris = new HashMap<String, String>();
		uris.put("config", "http://www.example.org/XMLSchemaMapping");
		SAXReader sax=new SAXReader();
		sax.getDocumentFactory().setXPathNamespaceURIs(uris);
		
		InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream("sqlMappingConfig.xml");
		Document dom=sax.read(in);
		
		@SuppressWarnings("unchecked")
		List<Element> list=dom.selectNodes("//config:typeAlias");
		for(Element element:list){
			String className=element.attributeValue("class");
			String alias=element.attributeValue("alias");
			SQLContainer.setTypeMap(alias, className);
		}
		@SuppressWarnings("unchecked")
		List<Element> mapperList=dom.selectNodes("//config:mapper");
		for(Element element:mapperList){
			String url=element.attributeValue("url");
			Map<String,String> mapperUris = new HashMap<String, String>();
			mapperUris.put("mapping", "http://www.example.org/XMLSchemaSql");
			SAXReader mapperSax=new SAXReader();
			mapperSax.getDocumentFactory().setXPathNamespaceURIs(mapperUris);
			
			InputStream mapperIn=Thread.currentThread().getContextClassLoader().getResourceAsStream(url);
			Document document=mapperSax.read(mapperIn);
			parseMapping(document);
		}
		
	}

	private static MappingContainer<?> mapping;
	@SuppressWarnings("unchecked")
	private static void parseMapping(Document document) throws Exception{
		Element root=(Element) document.selectSingleNode("//mapping:mapper");
		Class<?> cls=Class.forName(root.attributeValue("class"));
		
		mapping=new MappingContainer<>(cls);
		List<Element> list=document.selectNodes("//mapping:sql");
		for(Element element:list){
			String name=element.attributeValue("id");
			String sql=element.getTextTrim();
			mapping.addSql(name, sql);
		}
		List<Element> excutelist=new ArrayList<Element>(0);
		excutelist.addAll(document.selectNodes("//mapping:select"));
		excutelist.addAll(document.selectNodes("//mapping:insert"));
		excutelist.addAll(document.selectNodes("//mapping:update"));
		excutelist.addAll(document.selectNodes("//mapping:delete"));
		for(Element element:excutelist){
			String id=element.attributeValue("id");
			String sqlparam=element.attributeValue("sql");
			String parameterType=element.attributeValue("parameterType");
			String resultType =element.attributeValue("resultType");
			if(resultType!=null||!"".equals(resultType)){
				
			}
			String resultMap =element.attributeValue("resultMap");
			List<Element> sqlList=element.selectNodes("//mapping:include");
			String xml=element.asXML();
			for(Element ele:sqlList){
				String sqlText=mapping.getSqlMap().get(ele.attributeValue("ref"));
				xml=xml.replace(ele.asXML().replace("xmlns=\"http://www.example.org/XMLSchemaSql\" ", ""),sqlText);
			}
			xml=xml.substring(xml.indexOf(">")+1, xml.indexOf("</")).trim().replaceAll("  +","");
		    SQlXML sql= new SQlXML();
		    sql.setExecuteType(element.getName());
			sql.setResultMap(resultMap);
			sql.setResultType(resultType);
			sql.setParameterType(parameterType);
			sql.setSqlparam(sqlparam);
			sql.setSql(xml);
			mapping.addStatement(id, sql);
		}
		parseResultMap(document);
		SQLContainer.add(mapping);
	}
	@SuppressWarnings("unchecked")
	private static void parseResultMap(Document document) throws Exception{
		List<Element> list=document.selectNodes("//mapping:resultMap");
		for(Element element:list){
			String id=element.attributeValue("id");
			String type=element.attributeValue("type");
			Class<?> cls=SQLContainer.getTypeMap().get(type);
			ResultMap<?> resultMap=ResultMap.createResultMap(cls);
			//one
			List<Result> results = getResult(element);
			
			//two
			Map<String,Collection<?>> collections=getCollection(element);
			
			//three
			Map<String, Association<?>> associations=getAssociation(element);
			
			
			
			resultMap.setList(results);
			resultMap.setCollections(collections);
			resultMap.setAssociations(associations);
			
			mapping.addResult(id, resultMap);
		}
		
	}
	@SuppressWarnings("unchecked")
	private static Map<String, Association<?>> getAssociation(Element element){
		Map<String,Association<?>> associations=new HashMap<String,Association<?>>();
		List<Element> resultElement=element.elements("association");
		for(Element ele:resultElement){
			String property=ele.attributeValue("property");
			String javaType=ele.attributeValue("javaType");
			Class<?> c=SQLContainer.getTypeMap().get(javaType);
			if(c==null)
				c=ClassForName.classForName(javaType);
			Association<?> association=Association.createAssociation(c);
			association.setProperty(property);
			association.setList(getResult(ele));
			association.setCollections(getCollection(ele));
			associations.put(property, association);
		}
		return associations;
	}
	@SuppressWarnings("unchecked")
	private static Map<String,Collection<?>> getCollection(Element element){
		Map<String,Collection<?>> collections=new HashMap<String,Collection<?>>();
		List<Element> resultElement=element.elements("collection");
		for(Element ele:resultElement){
			String property=ele.attributeValue("property");
			String javaType=ele.attributeValue("javaType");
			Class<?> c=SQLContainer.getTypeMap().get(javaType);
			if(c==null)
				c=ClassForName.classForName(javaType);
			Collection<?> collection=Collection.createCollection(c);
			collection.setProperty(property);
			collection.setList(getResult(ele));
			collection.setCollections(getCollection(ele));
			collection.setAssociations(getAssociation(ele));
			collections.put(property, collection);
		}
		return collections;
	}
	@SuppressWarnings("unchecked")
	private static List<Result> getResult(Element element) {
		List<Result> results=new ArrayList<Result>();
		Element idNode=element.element("id");
		if(idNode!=null){
			results.add(addReault(idNode));
		}
		List<Element> resultElement=element.elements("result");
		for(Element ele:resultElement){
			results.add(addReault(ele));
		}
		return results;
	}
	private static Result addReault(Element idNode) {
		String property=idNode.attributeValue("property");
		String column=idNode.attributeValue("column");
		String javaType=idNode.attributeValue("javaType");
		String jdbcType=idNode.attributeValue("jdbcType");
		
		Result result=new Result();
		result.setProperty(property);
		result.setColumn(column);
		result.setJdbcType(jdbcType);
		result.setJavaType(javaType);
		return result;
	}

}
