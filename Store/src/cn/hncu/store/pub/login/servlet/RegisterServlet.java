package cn.hncu.store.pub.login.servlet;

import javax.servlet.http.HttpServletRequest;

import cn.hncu.container.annotation.Attribute;
import cn.hncu.container.annotation.Inpouring;
import cn.hncu.container.annotation.Servlet;
import cn.hncu.container.annotation.URL;
import cn.hncu.container.enums.ScopeType;
import cn.hncu.store.domain.User;
import cn.hncu.store.pub.login.service.LoginService;

@Servlet
public class RegisterServlet {

	@Inpouring(name="LoginService")
	private LoginService iService;
	
	@URL(value="/pub/login/register")
	public String register(HttpServletRequest request,User user){
		request.setAttribute("reg",iService.save(user));
		return "regResult";
	}
	@URL(value="/pub/login/regResult")
	public String regResult(@Attribute(name="reg",scopes={ScopeType.REQUEST})boolean result){
		if(result)
			return "regTrue";
		return "regFalse";
	}
	
}
