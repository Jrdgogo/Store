package cn.hncu.store.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Encrypt {
	
	private Encrypt(){
	}
	
	public static String encrypt(String input){
	    byte[] buf=null;
		try {
			buf = MessageDigest.getInstance("MD5").digest(input.getBytes("utf-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    StringBuffer sb=new StringBuffer(0);
	    for(byte b:buf){
	    	String s=Integer.toHexString(b & 0xff);
	    	if(s.length()<2){
	    		s+=s;
	    	}
	    	sb.append(s);
	    }
	    return sb.toString();
	}
	public static String encryptID(String input){
		String s=encrypt(input);
		char c=s.charAt(0);
		if(c<='9'){
			int i=(int)(Integer.parseInt(""+c)*1.0/2+0.5);
			c=(char)('a'+i);
			s=c+s.substring(1);
		}
		return s;
	}
	public static String getID(){
		return UUID.randomUUID().toString().replace("-", "");
	}

}
