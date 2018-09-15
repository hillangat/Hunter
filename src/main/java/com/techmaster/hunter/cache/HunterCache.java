package com.techmaster.hunter.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class HunterCache implements Serializable{

	private static final long serialVersionUID = 1L;
	private static HunterCache instance;
	private final static Map<String, Object> hunterCache = new HashMap<>();
	private HunterCache (){};
	private static Logger logger = Logger.getLogger(HunterCache.class);
	
	static{
		if(instance == null){
			synchronized (HunterCache.class) {
				instance = new HunterCache();
			}
		}
		loadHunterCache();
	}
	
	public static HunterCache getInstance(){
		return instance;
	}
	
	private static void loadHunterCache() {
		logger.debug("Refreshing hunter cache...");
		HunterCacheUtil util = HunterCacheUtil.getInstance();
		util.refreshAllXMLServices();
		util.loadExistentEmailTemplatesNames();
		util.loadEmailTemplateBeans();
		util.populateUIMessages();
		util.loadCountries();
		util.loadReceivers();
		logger.debug("Finished refreshing hunter cache...");
	}

	public boolean containsKey(String key){
		return hunterCache.containsKey(key);
	}
	
	public Object get(String key){
		if(containsKey(key)){
			return hunterCache.get(key);
		}
		return null;
	}
	
	public boolean isEmpty(){
		return hunterCache.isEmpty();
	}
	
	public Object put(String key, Object value){
		return hunterCache.put(key, value);
	}
	
	public Set<String> keySet(){
		return hunterCache.keySet();
	}
	
	public Object remove(String key){
		return hunterCache.remove(key);
	}
	
	public int size(){
		return hunterCache.size();
	}
	
	
	
	

}
