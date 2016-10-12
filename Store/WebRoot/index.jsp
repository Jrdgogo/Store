<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <title>主页</title>
  </head>
  
  <body>
    <form action="<c:url value='/container/pub/login/loginVaildate'/>" method="post">
                  姓名:<input type="text" name="name"/><br/>
                 密码:<input type="text" name="password"/><br/>
       <input type="submit" value="登录"/>
    </form>
    <div>2222</div>
    <div>1111</div>
  </body>
</html>
