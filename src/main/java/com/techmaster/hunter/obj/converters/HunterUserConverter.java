package com.techmaster.hunter.obj.converters;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.json.HunterUserJson;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.HunterUser;
import com.techmaster.hunter.util.HunterUtility;

public class HunterUserConverter {
	
	private Logger logger = Logger.getLogger(HunterUserConverter.class);
	
	private static HunterUserConverter instance;
	
	static{
		if(instance == null){
			synchronized (HunterUserConverter.class) {
				instance = new HunterUserConverter();
			}
		}
	}
	
	public static HunterUserConverter getInstance(){
		return instance;
	}
	
	public void updateWithHunterUserJson(HunterUserJson hunterUserJson, HunterUser hunterUser){
		hunterUser.setActive(hunterUserJson.isActive());
		hunterUser.setEmail(hunterUserJson.getEmail());
		hunterUser.setFirstName(hunterUserJson.getFirstName());
		hunterUser.setLastName(hunterUserJson.getLastName());
		hunterUser.setMiddleName(hunterUserJson.getMiddleName());
		hunterUser.setPhoneNumber(hunterUserJson.getPhoneNumber());
		hunterUser.setUserId(hunterUserJson.getUserId());
		hunterUser.setUserName(hunterUserJson.getUserName());
	}
	
	public HunterUser getNewHunterUserFromJson(HunterUserJson hunterUserJson, AuditInfo auditInfo){
		HunterUser hunterUser = new HunterUser();
		hunterUser.setCreatedBy(auditInfo.getCreatedBy());
		hunterUser.setCretDate(auditInfo.getCretDate()); 
		hunterUser.setLastUpdatedBy(auditInfo.getLastUpdatedBy());
		hunterUser.setLastUpdate(auditInfo.getLastUpdate()); 
		hunterUser.setUserType(HunterConstants.HUNTER_CLIENT_USER); 
		updateWithHunterUserJson(hunterUserJson, hunterUser);
		return hunterUser;
	}
	
	public HunterUserJson createHunterUserJsonForUser(HunterUser hunterUser){
		List<HunterUser> hunterUsers = new ArrayList<>();
		hunterUsers.add(hunterUser);
		List<HunterUserJson> hunterUserJsons = createJsonFromUsers(hunterUsers);
		return hunterUserJsons.get(0);
	}
	
	public List<HunterUserJson> createJsonFromUsers(List<HunterUser> hunterUsers){
		logger.debug("converting hunter users to json..."); 
		List<HunterUserJson> jsons = new ArrayList<HunterUserJson>();
		if(hunterUsers != null && !hunterUsers.isEmpty()){
			for(HunterUser hunterUser : hunterUsers){
				HunterUserJson json = new HunterUserJson();
				json.setActive(hunterUser.isActive());
				json.setCreatedBy(hunterUser.getCreatedBy());
				json.setCretDate(HunterUtility.formatDate(hunterUser.getCretDate(), HunterConstants.DATE_FORMAT_STRING)); 
				json.setEmail(hunterUser.getEmail());
				json.setFirstName(hunterUser.getFirstName());
				json.setLastName(hunterUser.getLastName());
				json.setLastUpdate(HunterUtility.formatDate(hunterUser.getLastUpdate(), HunterConstants.DATE_FORMAT_STRING));
				json.setLastUpdatedBy(hunterUser.getLastUpdatedBy());
				json.setMiddleName(hunterUser.getMiddleName());
				json.setPhoneNumber(hunterUser.getPhoneNumber());
				json.setUserId(hunterUser.getUserId());
				json.setUserName(hunterUser.getUserName());
				json.setUserType(hunterUser.getUserType()); 
				jsons.add(json);
			}
		}
		logger.debug("Finished creating jsons. size ( " + jsons.size() + " )");  
		return jsons;
	}

}
