package cn.hncu.container.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cn.hncu.container.annotation.Attribute;
import cn.hncu.container.annotation.Param;
import cn.hncu.container.enums.ScopeType;
import cn.hncu.container.mapping.RequestMapping;
import cn.hncu.container.mapping.ResponseMapping;
import cn.hncu.store.util.BeanUtils;

/**
 * Servlet Filter implementation class ContainerFilter
 */
@WebFilter(urlPatterns={"/container/*"},dispatcherTypes={DispatcherType.REQUEST,DispatcherType.FORWARD,DispatcherType.INCLUDE,DispatcherType.ERROR,DispatcherType.ASYNC})
public class ContainerFilter implements Filter {

	private final Logger log=Logger.getLogger(getClass());
    /**
     * Default constructor. 
     */
    public ContainerFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Map<String, Map<Object, Method>>  mapping=RequestMapping.getMapping();
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse)response;
		String url=req.getRequestURL().toString();
		String prtix="container";
		url=url.substring(url.indexOf(prtix)+prtix.length());
		if(url.indexOf(";")!=-1)
			url=url.substring(0,url.indexOf(";"));
		//System.out.println(url);
		Map<Object, Method> map=mapping.get(url);

        try {
        	Iterator<Entry<Object, Method>> it=map.entrySet().iterator();
        	while(it.hasNext()){
        		Entry<Object, Method> entry=it.next();
        		Object obj=entry.getKey();
        		Method method=entry.getValue();
        		List<Object> list=new ArrayList<Object>(0);
        		Annotation[][] parameterAnnotations=method.getParameterAnnotations();
        		Class<?>[] clss=method.getParameterTypes();
        		forclass:for(int i=0;i<clss.length;i++){
        			Class<?> cls=clss[i];
        			
        			if(cls.isAssignableFrom(AsyncContext.class)){
        				AsyncContext acsyncContext= req.getAsyncContext();
        				list.add(acsyncContext);
        			}
        			else if(cls.isAssignableFrom(DispatcherType.class)){
        				DispatcherType dispatcherType= req.getDispatcherType();
        				list.add(dispatcherType);
        			}
        			else if(cls.isAssignableFrom(InputStream.class)){
        				InputStream in= req.getInputStream();
        				list.add(in);
        			}
        			else if(cls.isAssignableFrom(Reader.class)){
        				Reader reader=req.getReader();
        				list.add(reader);
        			}
        			else if(cls.isAssignableFrom(ServletContext.class)){
        				ServletContext servletContext =req.getServletContext();
        				list.add(servletContext);
        			}
        			else if(cls.isAssignableFrom(HttpSession.class)){
        				HttpSession httpSession = req.getSession();
        				list.add(httpSession);
        			}
        			else if(cls.isAssignableFrom(OutputStream.class)){
        				OutputStream outputStream=resp.getOutputStream();
        				list.add(outputStream);
        			}
        			else if(cls.isAssignableFrom(Writer.class)){
        				Writer writer=resp.getWriter();
        				list.add(writer);
        			}
        			else if(cls.isAssignableFrom(request.getClass()))
        				list.add(request);
        			else if(cls.isAssignableFrom(response.getClass())){
        				list.add(response);
        			}
        			else{
        				//参数注入
        				for(Annotation annotation:parameterAnnotations[i]){
        					if(annotation.annotationType().equals(Param.class)){
        						Param param=(Param) annotation;
        						String name=param.name();
        						String value=req.getParameter(name);
        						if(cls.equals(int.class))
        							list.add(Integer.parseInt(value));
        						else if(cls.equals(double.class))
        							list.add(Double.parseDouble(value));
        						else if(cls.equals(long.class))
        							list.add(Long.parseLong(value));
        						else if(cls.equals(byte.class))
        							list.add(Byte.parseByte(value));
        						else
        							list.add(value);
        						continue forclass;
        					}else if(annotation.annotationType().equals(Attribute.class)){
        						Attribute attribute=(Attribute) annotation;
        						String name=attribute.name();
        						ScopeType[] scopes=attribute.scopes();
        						Object value=req.getAttribute(name);
        						if(scopes==null||scopes.length==0){
        							if(value==null)
        								value=req.getSession().getAttribute(name);
        							if(value==null)
        								value=req.getServletContext().getAttribute(name);
        						}
        						else{
        							ScopeType scope=scopes[0];
        							if(scope.equals(ScopeType.SESSION))
        								value=req.getSession().getAttribute(name);
        							else if(scope.equals(ScopeType.CONTEXT))
        								value=req.getServletContext().getAttribute(name);
        						}
        						if(cls.equals(int.class))
        							list.add(Integer.parseInt(""+value));
        						else if(cls.equals(double.class))
        							list.add(Double.parseDouble(""+value));
        						else if(cls.equals(long.class))
        							list.add(Long.parseLong(""+value));
        						else if(cls.equals(byte.class))
        							list.add(Byte.parseByte(""+value));
        						else if(cls.equals(boolean.class))
        							list.add(Boolean.parseBoolean(""+value));
        						else if(cls.equals(String.class))
        							list.add(""+value);
        						else{
        							list.add(value);
        						}
        						continue forclass;
        					}
        					
        				}
        				//封装值对象
        				list.add(BeanUtils.populate(cls,request.getParameterMap()));
        				
        				//populate(req, list, cls); 
        			}
        		}
        		Object resultObj=method.invoke(obj, list.toArray());
        		//转发视图
        		if(resultObj!=null&&!"".equals(resultObj))
        			forword(request, response, req, resp, resultObj);
        	}
        } catch (Exception e) {
        	throw new RuntimeException(url+"不存在",e);
        }
	}

	private void forword(ServletRequest request, ServletResponse response,
			HttpServletRequest req, HttpServletResponse resp, Object resultObj)
			throws ServletException, IOException {
		if(resultObj instanceof String){
			String key=(String)resultObj;
			boolean flag=true;
			if(key.startsWith("redirect:")){
				key=key.substring("redirect:".length());
				flag=false;
			}
			Map<String, Map<String, String>> respMapping=ResponseMapping.getMapping();
			Map<String, String> rMap=respMapping.get(key);
			if(rMap==null){
				if(flag)
					req.getRequestDispatcher(key).forward(request, response);
				else
					resp.sendRedirect(req.getContextPath()+key);
			}else{
				Iterator<Entry<String, String>> itMap=rMap.entrySet().iterator();
				while(itMap.hasNext()){
					Entry<String, String> entryMap=itMap.next();
					String view=entryMap.getKey();
					String uri=entryMap.getValue();
					if("jsp/servlet".equals(view)){
						if(flag)
							req.getRequestDispatcher(uri).forward(request, response);
						else
							resp.sendRedirect(req.getContextPath()+uri);
					}
				}
			}
			
		}else
			throw new RuntimeException(resultObj+"不存在");
	}

//	private void populate(HttpServletRequest req, List<Object> list,
//			Class<?> cls) {
//		try {
//			Object instance=cls.newInstance();
//			//Map<String, String[]> paramMap=req.getParameterMap();
//			Field[] fields=cls.getDeclaredFields();
//			for(Field field:fields){
//				field.setAccessible(true);
//				String name=field.getName();
//				String value=req.getParameter(name);
//				if(value==null)
//					continue;
//				name="set"+name.substring(0,1).toUpperCase()+name.substring(1);
//				Method m=cls.getMethod(name, field.getType());
//				Class<?> paramClass=field.getType();
//				if(paramClass.equals(int.class))
//					m.invoke(instance, new Object[]{Integer.parseInt(value)});
//				else if(paramClass.equals(double.class))
//					m.invoke(instance, new Object[]{Double.parseDouble(value)});
//				else if(paramClass.equals(long.class))
//					m.invoke(instance, new Object[]{Long.parseLong(value)});
//				else if(paramClass.equals(byte.class))
//					m.invoke(instance, new Object[]{Byte.parseByte(value)});
//				else
//					m.invoke(instance, new Object[]{value});
//			}
//			list.add(instance);	
//		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
//			log.error("封装值对象失败", e);
//		}
//	}

	
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
