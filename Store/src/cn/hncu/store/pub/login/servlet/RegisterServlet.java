package cn.hncu.store.pub.login.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.hncu.container.annotation.Inpouring;
import cn.hncu.container.annotation.Param;
import cn.hncu.container.annotation.Servlet;
import cn.hncu.container.annotation.URL;
import cn.hncu.store.domain.User;
import cn.hncu.store.pub.login.service.LoginService;
import cn.hncu.store.util.Encrypt;

@Servlet
public class RegisterServlet {

	@Inpouring(name="LoginService")
	private LoginService iService;
	
	@URL(value="/pub/login/register")
	public void register(ServletContext context,HttpServletResponse response,User user) throws IOException{
		user.setId(Encrypt.getID());
		boolean f=iService.save(user);
		Object o=null;
		if(f){
			o=JSON.parse("{\"success\":true,msg:\"邮件已发送，请激活用户\"}");
			@SuppressWarnings("unchecked")
			Map<String, User> map =(Map<String, User>) context.getAttribute("tmpUsers");
			map.put(user.getId(), user);
		}
		else
			o=JSON.parse("{\"success\":false,msg:\"注册失败，用户名或重复\"}");
		response.getWriter().print(JSON.toJSON(o));	
	}
	@URL(value="/pub/login/regValidate")
	public void regValidate(@Param(name="acode")String acode,ServletContext context,PrintWriter pw) throws IOException{
		@SuppressWarnings("unchecked")
		Map<String, User> map =(Map<String, User>) context.getAttribute("tmpUsers");
		User user=map.get(acode);
		if(user!=null){
			boolean f=iService.update(user);
			if(f){
				map.remove(acode);
				pw.print("激活正确,<a href='http://localhost:8080/Store/jsp/public/login/Login.jsp'>请登录</a>");
				return;
			}
		}
		pw.print("激活失败，<a href='http://localhost:8080/Store/jsp/public/login/Register.jsp'>重新注册</a>");
	}
	
}
