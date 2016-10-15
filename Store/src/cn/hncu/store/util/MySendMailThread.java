package cn.hncu.store.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.log4j.Logger;

import cn.hncu.store.domain.User;

import com.sun.mail.util.MailSSLSocketFactory;



public class MySendMailThread implements Runnable{
    private User user = null;
	private Logger log = Logger.getLogger(MySendMailThread.class);//log--1
	
	public MySendMailThread(User user) {
		this.user = user;
	}
	
	@Override
	public void run() {
		//跟smtp服务器建立一个连接
		Properties p = new Properties();
		p.setProperty("mail.host", "smtp."+user.getEmail().split("@")[1]);//指定邮件服务器，默认端口 25
		p.setProperty("mail.smtp.auth", "true");//要采用指定用户名密码的方式去认证
		p.setProperty("mail.transport.protocol", "smtp");
		//开启ssl加密
		try {
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			p.put("mail.smtp.ssl.enable", "true");
			p.put("mail.smtp.ssl.socketFactory", sf);
			Session session = Session.getDefaultInstance(p, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					PasswordAuthentication pa = new PasswordAuthentication("1477450172", "ignkctidhrbxhjgc");
					return pa;
				}
			});
			session.setDebug(true);//设置打开调试状态
			
			
			//声明一个Message对象(代表一封邮件),从session中创建
			MimeMessage msg = new MimeMessage(session);
			//邮件信息封装
			//1发件人
			msg.setFrom( new InternetAddress("1477450172@qq.com") );
			//2收件人
			msg.setRecipient(RecipientType.TO, new InternetAddress( user.getEmail() )  ); //※※※
			//3邮件内容:主题、内容
			msg.setSubject(user.getName()+",欢迎注册Store账号,请激活账号");
			
			StringBuilder sb = new StringBuilder();
			sb.append("请点击:<a href='http://localhost:8080/Store/container/pub/login/regValidate");
			sb.append("?acode="+user.getId()+"'>激活</a>");
			sb.append("<br/>如果激活未成功,请复制下面地址到浏览器地址栏进行手动激活:");
			sb.append("http://localhost:8080/Store/container/pub/login/regValidate?acode="+user.getId());
			
			msg.setContent(sb.toString(), "text/html;charset=utf-8");//发html格式的文本
			//发送动作
			Transport.send(msg);
			log.info("一封激活邮件成功发送至:"+user.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("一份邮件发送失败!对方的邮箱是:"+user.getEmail());
		}
	}
}
