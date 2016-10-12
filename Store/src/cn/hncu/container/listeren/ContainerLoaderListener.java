package cn.hncu.container.listeren;

import java.io.InputStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import cn.hncu.container.mapping.RequestMapping;
import cn.hncu.container.parse.XMLInputStreamParse;
import cn.hncu.container.process.AnnotationInpouring;
import cn.hncu.container.process.InitBeans;
import cn.hncu.container.process.ProxyBeans;
import cn.hncu.sqlMapping.xmlparse.ConfigParse;



/**
 * Application Lifecycle Listener implementation class ContainerLoaderListener
 *
 */
@WebListener
public class ContainerLoaderListener implements ServletContextListener {

	private final Logger log=Logger.getLogger(getClass());
	/**
     * Default constructor. 
     */
    public ContainerLoaderListener() {
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
    	String xmlpath=sce.getServletContext().getInitParameter("containerConfig");
    	String[] xmlpaths=xmlpath.split(",");
    	for(String path:xmlpaths){
    		path=path.trim();
    		InputStream in=null;
    		if(path.startsWith("classpath:")){
    			path=path.substring("classpath:".length());
    		    in=Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    		}else{
    			in=sce.getServletContext().getResourceAsStream(path);
    		}
    		try {
				XMLInputStreamParse.parse(in);
				log.info("加载配置文件"+path);
			} catch (Exception e) {
				log.warn("加载配置文件"+path+"失败",e);
			} 
    	}
    	try {
    		new ProxyBeans().proxyBeans();
    	} catch (Exception e) {
    		log.warn("代理bean失败",e);
    	}
    	try {
    		AnnotationInpouring.Inpouring();
    	} catch (Exception e) {
    		log.warn("注入属性bean失败",e);
    	}
    	try {
    		InitBeans.init();
    	} catch (Exception e) {
    		log.warn("初始化bean失败",e);
    	}
    	try {
			RequestMapping requestMapping=new RequestMapping();
			requestMapping.loadermapping();
		} catch (Exception e) {
			log.warn("加载urlMapping失败",e);
		}
    	try {
			ConfigParse.parseXmlConfig();
		} catch (Exception e) {
			log.warn("加载sqlMapping失败",e);
		}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
    }
	
}
