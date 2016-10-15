package cn.hncu.store.pub.servlet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;

import cn.hncu.container.annotation.Servlet;
import cn.hncu.container.annotation.URL;

@Servlet
public class CreateImgServlet {
    
	@URL(value="/pub/getImg")
	public void createImg(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setContentType("image/jpeg");
		int width=40;
		int height=40;
		BufferedImage bufImg=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g=bufImg.getGraphics();
		
		HttpSession session= request.getSession();
		String str="";
		Random r=new Random(new Date().getTime());
		for(int i=0;i<4;i++){
			int num=r.nextInt(10);
			str+=num;
			g.setColor(new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)));
			g.drawString(""+num, width/4*i, height/2);
		}
		for(int i=0;i<6;i++){
			g.setColor(new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)));
			g.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));
		}
		session.setAttribute("imgVaildate", str);
		ImageIO.write(bufImg, "JPEG", response.getOutputStream());
	}
	@URL(value="/pub/imgVaildate")
	public void imgVaildate(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String imfStr=request.getParameter("img");
		String s=(String) request.getSession().getAttribute("imgVaildate");
		request.getSession().removeAttribute("imgVaildate");
		Object o=null;
		if(s!=null&&s.equals(imfStr)){
			o=JSON.parse("{success:true}");
		}else{
			o=JSON.parse("{\"success\":false,\"msg\":\"验证码错误\"}");
		}
		response.getWriter().print(JSON.toJSON(o));
	}
}
