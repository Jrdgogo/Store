package cn.hncu.container.parse;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLInputStreamParse {
	
	@SuppressWarnings("unchecked")
	public static void parse(InputStream in) throws Exception{
		Map<String,String> uris = new HashMap<String, String>();
		uris.put("store", "http://www.example.org/XMLSchema");
		SAXReader sax=new SAXReader();
		sax.getDocumentFactory().setXPathNamespaceURIs(uris);

		Document dom=sax.read(in);
		List<Element> elements=dom.selectNodes("//store:propertyFileLoader");
		for(Element e:elements){
			String url=e.attributeValue("url");
			Properties p=new Properties();
			p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("../config/"+url));
			HashContainer.put(p);
		}
		
		elements=dom.selectNodes("//store:bean");
		for(Element e:elements){
			String id=e.attributeValue("id");
			String className=e.attributeValue("class");
			List<Element> constructorArgs=e.elements("constructor-arg");
			if(constructorArgs!=null)
				for(Element arg:constructorArgs){
					String name=arg.attributeValue("name");
					String ref=arg.attributeValue("ref");
					String value=arg.attributeValue("value");
					if(value!=null)
						HashContainer.setConstructor(name, value);
					else
						HashContainer.setConstructorBean(name, ref);
				}
			List<Element> properties=e.elements("property");
			if(properties!=null)
				for(Element arg:properties){
					String name=arg.attributeValue("name");
					String ref=arg.attributeValue("ref");
					String value=arg.attributeValue("value");
					if(value!=null)
						HashContainer.setProperty(name, value);
					else
						HashContainer.setPropertyBean(name, ref);
				}
			HashContainer.put(id, className);
		}
		
		elements=dom.selectNodes("//store:ergodic-inpouring");
		for(Element e:elements){
			String basePath=e.attributeValue("base-package");
			List<Element> excludeFilters=e.elements("exclude-filter");
			for(Element filter:excludeFilters){
				String type=filter.attributeValue("type");
				String expression=filter.attributeValue("expression");
				HashContainer.setExcludeFilter(type,expression);
			}
			List<Element> includeFilters=e.elements("include-filter");
			for(Element filter:includeFilters){
				String type=filter.attributeValue("type");
				String expression=filter.attributeValue("expression");
				HashContainer.setIncludeFilter(type,expression);
			}
			HashContainer.put(basePath);
		}
	}
}
