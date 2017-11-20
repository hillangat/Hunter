package com.techmaster.hunter.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

public class HunterReflection {

private static final Logger logger = Logger.getLogger(HunterReflection.class);
	
	
	public static void invoke(String forName,String methodName, Class<?>[] paramsTypes, Object[] params){
		
		try {
		
			Class<?> cls = Class.forName(forName);
			Object obj = cls.newInstance(); 
			Method method = cls.getDeclaredMethod(methodName, paramsTypes);
			method.invoke(obj, params);
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static void invoke(Object obj, Class<?> cls,String methodName, Class<?>[] paramsTypes, Object[] params){
		
		logger.debug(params[1].getClass().getSimpleName()); 
			
			try {
			
				Method method = cls.getDeclaredMethod(methodName, paramsTypes);
				method.invoke(obj, params);
			
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
	}
	
}
