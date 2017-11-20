package com.techmaster.hunter.obj.converters;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.UIMessageConstants;
import com.techmaster.hunter.obj.beans.ConstituencyWard;
import com.techmaster.hunter.obj.beans.HunterJacksonMapper;
import com.techmaster.hunter.util.HunterLogFactory;
import com.techmaster.hunter.util.HunterUtility;

public class ConstituencyWardConverter {
	
	private String requestBody;
	private JSONObject converterJson = null;
	private String errorMessage = ""; 
	private String entityKey;

	private static Logger logger = HunterLogFactory.getLog(TaskConverter.class);
	private HunterJacksonMapper hunterJacksonMapper = new HunterJacksonMapper();
	
	public ConstituencyWardConverter(String requestBody, String entityKey) { 
		super();
		this.requestBody = requestBody;
		this.entityKey = entityKey;
		this.converterJson = new JSONObject(this.requestBody); 
	}
	
	public JSONObject getConverterJson() {
		return converterJson;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public String getEntityKey() {
		return entityKey;
	}

	public ConstituencyWard convert(){
		logger.info("Starting ConstituencyWard json conversion process >> " + this.getConverterJson());
		ConstituencyWard constituencyWard = null;
		try {
			constituencyWard = hunterJacksonMapper.readValue(this.requestBody, ConstituencyWard.class);
			logger.info("Successfully converted constituencyWard >> " + constituencyWard); 
		} catch (JsonParseException e) {
			logAndSetErrorMessage(e, UIMessageConstants.MSG_REGION_001);
		} catch (JsonMappingException e) {
			logAndSetErrorMessage(e, UIMessageConstants.MSG_REGION_001);
		} catch (IOException e) {
			logAndSetErrorMessage(e, UIMessageConstants.MSG_REGION_001);
		}
		logger.info("Finished ConstituencyWard json conversion process"); 
		return constituencyWard;
	}
	
	public Set<ConstituencyWard> convertForList(){
		
		if(!HunterUtility.notNullNotEmpty(this.getEntityKey())){
			errorMessage = "Invalid arguments encountered.";
			throw new IllegalArgumentException("For converForList, entity key cannot be null! entity >> " + this.getEntityKey()); 
		}
		
		Set<ConstituencyWard> constituencyWards = new HashSet<>();
		JSONArray jsonWards = null;
		
		try {
			jsonWards = this.getConverterJson().getJSONArray(this.getEntityKey());
		} catch (JSONException e1) {
			logAndSetErrorMessage(e1, UIMessageConstants.MSG_REGION_001);
			return null;
		}
		
		logger.debug("constructing set of constituencyWards from json array >> " + jsonWards); 
		
		for(int i=0; i<jsonWards.length(); i++){
			
			JSONObject constituencyWardJson = jsonWards.getJSONObject(i);
			logger.debug("Starting to convert json >> " + constituencyWardJson); 
			ConstituencyWard constituencyWard = null;
			
			if(hunterJacksonMapper == null) continue;
			
			try {
				constituencyWard = hunterJacksonMapper.readValue(constituencyWardJson.toString(), ConstituencyWard.class);
			} catch (JsonParseException e) {
				logAndSetErrorMessage(e, UIMessageConstants.MSG_REGION_001);
				continue;
			} catch (JsonMappingException e) {
				logAndSetErrorMessage(e, UIMessageConstants.MSG_REGION_001); 
				continue;
			} catch (IOException e) {
				logAndSetErrorMessage(e, UIMessageConstants.MSG_REGION_001);
				continue;
			} 
			
			constituencyWards.add(constituencyWard);
			
		}
		 
		logger.debug("Successfully constructed constituencyWards set >> " + HunterUtility.stringifySet(constituencyWards));  
		
		return constituencyWards;
		
		
	}
	
	public void logAndSetErrorMessage(Throwable e, String msgId){
		logger.error("Error encountered while converting consituencyward. " + e.getMessage());
		errorMessage = HunterCacheUtil.getInstance().getUIMsgTxtForMsgId(msgId);
	}

	
	
	
	
}
