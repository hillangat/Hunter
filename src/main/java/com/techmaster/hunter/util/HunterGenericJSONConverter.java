package com.techmaster.hunter.util;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;

public class HunterGenericJSONConverter {
	
	Logger logger = Logger.getLogger(HunterGenericJSONConverter.class);
	
	private HunterGenericJSONConverter() {}
	private static HunterGenericJSONConverter instance;  
	static {
		if ( instance == null ) {
			synchronized (HunterGenericJSONConverter.class) {
				instance = new HunterGenericJSONConverter();
			}
		}
	}
	
	public static HunterGenericJSONConverter getInstance() {
		return instance;
	}	
	
	public JSONArray convert( List<?> objects ) {
		JSONArray jsonArray = new JSONArray();
		for( Object object : objects ){
			JSONObject clientRow = convert(object);
			jsonArray.put(clientRow);
		}
		logger.debug(jsonArray.toString());
		return jsonArray;
	}
	
	public JSONObject convert( Object obj ) {
		PropertyAccessor propertyAccessor = PropertyAccessorFactory.forBeanPropertyAccess(obj);
		JSONObject jsonObject = new JSONObject();
		Field[]  fields = obj.getClass().getDeclaredFields();
		for ( Field field : fields ) {
			jsonObject.put(field.getName(), propertyAccessor.getPropertyValue(field.getName())); 
		}
		logger.debug(jsonObject.toString());
		return jsonObject;
	}

}
