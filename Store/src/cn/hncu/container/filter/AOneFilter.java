package cn.hncu.container.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPOutputStream;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

@WebFilter(urlPatterns={"/*"},dispatcherTypes={DispatcherType.REQUEST,DispatcherType.FORWARD,DispatcherType.INCLUDE,DispatcherType.ERROR,DispatcherType.ASYNC},
           initParams={@WebInitParam(name="charset",value="utf-8"),
                       @WebInitParam(name="gzip",value="false"),
                       @WebInitParam(name="getParam",value="true")})
public class AOneFilter implements Filter {
	
	private boolean getParam;
	private boolean gzip;
	private String charset;

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println(((HttpServletRequest)request).getHeader("Referer"));
		if(charset!=null)
           request.setCharacterEncoding(charset);
        if(getParam)
        	request=new StrengthenRequest((HttpServletRequest)request);
        if(gzip){
        	StrengthenResponse resp=new StrengthenResponse((HttpServletResponse)response);
        	chain.doFilter(request,resp );
        	HttpServletResponse httpRsep=(HttpServletResponse)response;
        	if("gzip".equals(httpRsep.getHeader("Content-Encoding"))){
        		httpRsep.getOutputStream().write(resp.getByteOut().toByteArray());
        		httpRsep.getOutputStream().close();
        		return;
        	}
        	
        	ByteArrayOutputStream byteOut=new ByteArrayOutputStream();
        	GZIPOutputStream gzip=new GZIPOutputStream(byteOut);
        	gzip.write(resp.getByteOut().toByteArray());
        	gzip.close();
        	
        	httpRsep.setHeader("Content-Encoding", "gzip");
        	
        	httpRsep.getOutputStream().write(byteOut.toByteArray());
        	httpRsep.getOutputStream().close();
        	return;
        }
        chain.doFilter(request,response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		charset=config.getInitParameter("charset");
		if(config.getInitParameter("getParam").equals("true"))
			getParam=true;
		if(config.getInitParameter("gzip").equals("true"))
			gzip=true;
		
	}

	class StrengthenRequest extends HttpServletRequestWrapper{
		public StrengthenRequest(HttpServletRequest request) {
			super(request);
			
		}

		@Override
		public String getParameter(String name) {
			if(getMethod().equalsIgnoreCase("GET")){
				try {
					String param=super.getParameter(name);
					if(param!=null)
						return new String(param.getBytes("ISO-8859-1"),getCharacterEncoding());
					return param;
				} catch (UnsupportedEncodingException e) {
				}
			}
			return super.getParameter(name);
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			Map<String, String[]> map=super.getParameterMap();
			if(getMethod().equalsIgnoreCase("GET")){
				try {
					Iterator<Entry<String, String[]>> it=map.entrySet().iterator();
					while(it.hasNext()){
						Entry<String, String[]> en=it.next();
						en.setValue(paramsCode(en.getValue()));
					}
				} catch (UnsupportedEncodingException e) {
				}
			}
			return map;
		}

		@Override
		public Enumeration<String> getParameterNames() {
			final Enumeration<String> en=super.getParameterNames();
			if(getMethod().equalsIgnoreCase("GET")){
				return new Enumeration<String>() {

					@Override
					public boolean hasMoreElements() {
						return en.hasMoreElements();
					}

					@Override
					public java.lang.String nextElement() {
						try {
							return new String(en.nextElement().getBytes("ISO-8859-1"),getCharacterEncoding());
						} catch (UnsupportedEncodingException e) {
						}
						return en.nextElement();
					}
				};
			}
			return en;
		}

		@Override
		public String[] getParameterValues(String name) {
			if(getMethod().equalsIgnoreCase("GET")){
				try {
					return paramsCode(super.getParameterValues(name));
				} catch (UnsupportedEncodingException e) {
				}
			}
			return super.getParameterValues(name);
		}
		private String[] paramsCode(String[] params) throws UnsupportedEncodingException{
			for(int i=0;i<params.length;i++){
				params[i]=new String(params[i].getBytes("ISO-8859-1"),getCharacterEncoding());
			}
			return params;
		}

		
		
	}
	class StrengthenResponse extends HttpServletResponseWrapper{

		private ByteArrayOutputStream byteOut;
		private ServletOutputStream out;
		private PrintWriter pw;
		private HttpServletResponse response;
		public StrengthenResponse(HttpServletResponse response) {
			super(response);
			this.response=response;
			byteOut=new ByteArrayOutputStream();
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			final ServletOutputStream output=response.getOutputStream();
			if(out==null)
				out=new ServletOutputStream() {
				@Override
				public void write(int b) throws IOException {
					byteOut.write(b);
				}

				@Override
				public boolean isReady() {
					return output.isReady(); 
				}

				@Override
				public void setWriteListener(WriteListener arg0) {
					output.setWriteListener(arg0);
				}
			};
			return out;
		}
		@Override
		public PrintWriter getWriter() throws IOException {
			if(pw==null)
				pw=new PrintWriter(new OutputStreamWriter(byteOut,getCharacterEncoding()));
			return pw;
		}
		
		public ByteArrayOutputStream getByteOut() {
			return byteOut;
		}
		
		
	}
	
	public void setGetParam(boolean getParam) {
		this.getParam = getParam;
	}

	public void setGzip(boolean gzip) {
		this.gzip = gzip;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
}
