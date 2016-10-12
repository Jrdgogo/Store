package cn.hncu.store.util;
import java.util.Map;
public class BeanUtils {
	public static<T> T populate(Class<T> cls,Map<String,?> map){
		try{
			T t = cls.newInstance();
			return populate(t, map);
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	private static<T> T populate(T t,Map<String,?> map){
		try{
			org.apache.commons.beanutils.BeanUtils.populate(t,map);
			return t;
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(),e);
		}
	}
}
