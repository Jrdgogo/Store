<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>注册界面</title>
    <script type="text/javascript" src="/Store/js/jquery-3.1.0.min.js"></script>
    <script type="text/javascript">
	  $(document).ready(function(){
	     $("img").click(function(){
	        this.src="/Store/container/pub/getImg?time="+new Date().getTime();
	     });
	  
	     $("#reg").click(function(){
	         $.ajax({
	          type:"POST",
	          url:"/Store/container/pub/imgVaildate",
	          dataType:"json",
	          data:{
	             img:$("#img").val()
	          
	          },
	          success:function(data){
	             if(data.success){
	                if($("#password").val()!=$("#password2").val())
	                   alert("俩次密码不一致");
			        $.ajax({
			          type:"POST",
			          url:"/Store/container/pub/login/register",
			          dataType:"json",
			          data:{
			             name:$("#name").val(),
			             password:$("#password").val(),
			             phone:$("#phone").val(),
			             email:$("#email").val(),
			          
			          },
			          success:function(data){
			             alert(data.msg);
			          },
			          error:function(xhr){
			            alert("注册错误："+xhr.status);
			          }
			        });
	             
	             }else
	                alert(data.msg);
	             
	          },
	          error:function(xhr){
	            alert("验证码错误："+xhr.status);
	          }
	        });
	     });
	  
	  });
    </script>
  </head>
  
  <body>
       <input type="text" id="name"/>
       <input type="text" id="phone"/>
       <input type="text" id="email"/>
       <input type="password" id="password"/>
       <input type="password" id="password2"/>
       <input type="text" id="img"><img src="/Store/container/pub/getImg" >
       <input type="button" value="注册" id="reg"/>
  </body>
</html>
