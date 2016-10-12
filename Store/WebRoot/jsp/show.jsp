<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

  </head>
  
  <body>
    <c:if test="${!empty user}" var="boo">
           编号:${user.id}<br/>
           姓名:${user.name}<br/>
           密码:${user.password}<br/>
           电话:${user.phone}<br/>
           邮箱:${user.email}<br/>
    </c:if>
    <c:if test="${!boo}">
             保存失败;
    </c:if>
  </body>
</html>
