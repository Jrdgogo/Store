package cn.hncu.store.pub.login.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.hncu.container.annotation.Attribute;
import cn.hncu.container.annotation.Inpouring;
import cn.hncu.container.annotation.Servlet;
import cn.hncu.container.annotation.URL;
import cn.hncu.container.enums.ScopeType;
import cn.hncu.store.domain.User;
import cn.hncu.store.pub.login.service.LoginService;

@Servlet(value="loginServlet")
public class LoginServlet{
	@Inpouring(name="LoginService")
	private LoginService iService;
	@URL(value="/pub/login/loginVaildate")
	public String login(HttpSession session,User user) throws IOException{
		user=iService.login(user);
		session.setAttribute("user",user);
		return "/container/pub/login/return";
	}
	@URL(value="/pub/login/return")
	public String returnJsp(HttpServletRequest request,
			@Attribute(name="user", scopes={ScopeType.SESSION})User user){
		if(user!=null){//登录成功返回 进入登录界面的 原来界面
			String url=request.getHeader("Referer");
			String path=request.getContextPath();
			url=url.substring(url.indexOf(path)+path.length());
			return "redirect:"+url;//重定向是jsp的地址
		}else//登陆失败返回登录界面
			return "redirect:loginJsp";
		
	}
}
