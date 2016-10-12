package cn.hncu.sqlMapping.util;



public class ClassForName {
	
	public static Class<?> classForName(String name){
		if("byte".equals(name))
			return byte.class;
		else if("Byte".equals(name))
			return Byte.class;
		else if("char".equals(name))
			return char.class;
		else if("Character".equals(name))
			return Character.class;
		else if("String".equals(name))
			return String.class;
		else if("int".equals(name))
			return int.class;
		else if("Integer".equals(name))
			return Integer.class;
		else if("double".equals(name))
			return double.class;
		else if("Double".equals(name))
			return Double.class;
		else if("float".equals(name))
			return float.class;
		else if("Float".equals(name))
			return Float.class;
		else if("short".equals(name))
			return short.class;
		else if("Short".equals(name))
			return Short.class;
		else if("long".equals(name))
			return long.class;
		else if("Long".equals(name))
			return Long.class;
		else if("boolean".equals(name))
			return boolean.class;
		else if("Boolean".equals(name))
			return Boolean.class;
		return Object.class;
	}
	public static boolean isPrimitive(Class<?> cls){
		if(cls.equals(Byte.class))
			return true;
		else if(cls.equals(Short.class))
			return true;
		else if(cls.equals(Integer.class))
			return true;
		else if(cls.equals(Long.class))
			return true;
		else if(cls.equals(Character.class))
			return true;
		else if(cls.equals(String.class))
			return true;
		else if(cls.equals(Float.class))
			return true;
		else if(cls.equals(Double.class))
			return true;
		else if(cls.isPrimitive())
			return true;
		
		return false;
	}

}
