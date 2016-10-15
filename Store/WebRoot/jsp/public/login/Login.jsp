<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>登录界面</title>
   

  </head>
  
  <body>
    <form action="<c:url value='/container/pub/login/loginVaildate'/>">
       <input type="text" name="name"/>
       <input type="password" name="password"/>
       <input type="submit" value="登录"/>
    </form>
  </body>
</html>
