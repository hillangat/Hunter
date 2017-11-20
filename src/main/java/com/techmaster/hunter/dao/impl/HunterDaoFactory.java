package com.techmaster.hunter.dao.impl;

import java.util.HashMap;
import java.util.Map;

public class HunterDaoFactory {
	
	
	private static HunterDaoFactory instance = null;
	private static Map<String, Object> daosMap = new HashMap<String, Object>();
	
	private HunterDaoFactory() {
		super();
	}
	
	public static <T>T getObject(Class<T> clzz){
		return HunterDaoFactory.getDaoObject(clzz); 
	}

	public static synchronized HunterDaoFactory getInstance(){
		if(instance == null){
			synchronized (HunterDaoFactory.class) {
				instance = new HunterDaoFactory();
			}
		}
		return instance;
	}

	public void setDaosMap(Map<String, Object> daosMap) {
		HunterDaoFactory.daosMap = daosMap; 
	}
	public static Object get(String key){
		Object obj = daosMap.get(key);
		return obj;
	}
	
	public static <T>T getDaoObject(Class<T> clzz){
		String key = clzz.getSimpleName();
		key = (key.substring(0,1)).toLowerCase()+ key.substring(1,key.length());
		@SuppressWarnings("unchecked") T t = (T)get(key); 
		return t;
	}
	
	public void put(String key, Object obj){
		daosMap.put(key, obj);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((daosMap == null) ? 0 : daosMap.hashCode());
		return result;
	}


}
