package com.techmaster.hunter.obj.converters;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.json.ClientUserJson;
import com.techmaster.hunter.obj.beans.HunterClient;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterLogFactory;
import com.techmaster.hunter.util.HunterUtility;

public class ClientUserJsonConverter {

	private static Logger logger = HunterLogFactory.getLog(TaskConverter.class);
	
	public ClientUserJson convert(String requestBody){
		
		logger.debug("Getting client user json for request body : " + requestBody ); 
		
		ClientUserJson userJson = new ClientUserJson();
		JSONObject json = new JSONObject(requestBody);
		
		String userIdStr = HunterUtility.getStringOrNullFromJSONObj(json, "userId");
		Long userId = userIdStr == null ? null : HunterUtility.getLongFromObject(userIdStr);
		String userName = HunterUtility.getStringOrNullFromJSONObj(json, "userName");
		String firstName = HunterUtility.getStringOrNullFromJSONObj(json, "firstName");
		String lastName = HunterUtility.getStringOrNullFromJSONObj(json, "lastName");
		String middleName = HunterUtility.getStringOrNullFromJSONObj(json, "middleName");
		String email = HunterUtility.getStringOrNullFromJSONObj(json, "email");
		String phoneNumber = HunterUtility.getStringOrNullFromJSONObj(json, "phoneNumber");
		String userType = HunterUtility.getStringOrNullFromJSONObj(json, "userType");
		String cretDate = HunterUtility.getStringOrNullFromJSONObj(json, "cretDate");
		String lastUpdate = HunterUtility.getStringOrNullFromJSONObj(json, "lastUpdate");
		String createdBy = HunterUtility.getStringOrNullFromJSONObj(json, "createdBy");
		String lastUpdatedBy = HunterUtility.getStringOrNullFromJSONObj(json, "lastUpdatedBy");
		String clientTotalBudgetStr = HunterUtility.getStringOrNullFromJSONObj(json, "clientTotalBudget");
		float clientTotalBudget = Float.parseFloat(clientTotalBudgetStr);
		boolean receiver = json.getBoolean("receiver");
		String clientIdStr = HunterUtility.getStringOrNullFromJSONObj(json, "clientId");
		Long clientId = userIdStr == null ? null : HunterUtility.getLongFromObject(clientIdStr);
		
		userJson.setClientId(clientId);
		userJson.setClientTotalBudget(clientTotalBudget);
		userJson.setCreatedBy(createdBy);
		userJson.setCretDate(cretDate);
		userJson.setEmail(email);
		userJson.setFirstName(firstName);
		userJson.setLastUpdate(lastUpdate);
		userJson.setMiddleName(middleName); 
		userJson.setPhoneNumber(phoneNumber);
		userJson.setReceiver(receiver);
		userJson.setUserId(userId);
		userJson.setUserName(userName);
		userJson.setUserType(userType); 
		userJson.setLastUpdatedBy(lastUpdatedBy); 
		userJson.setLastName(lastName); 
		
		HunterClient client = HunterDaoFactory.getObject(HunterHibernateHelper.class).getEntityById(userId, HunterClient.class);
		
		if(client != null){
			userJson.setHasClient(true); 
		}else{
			userJson.setHasClient(false);
		}
		
		return userJson;
	}
	
	public List<ClientUserJson> getJsons(List<HunterClient> clients){
		logger.debug("creating clientUserJson beans..."); 
		List<ClientUserJson> userJsons = new ArrayList<ClientUserJson>();
		for(HunterClient client : clients){
			ClientUserJson clientUserJson = new ClientUserJson();
			clientUserJson.setClientId(client.getClientId());
			clientUserJson.setClientTotalBudget(client.getClientTotalBudget());
			clientUserJson.setCreatedBy(client.getCreatedBy());
			clientUserJson.setCretDate(HunterUtility.formatDate(client.getCretDate(), HunterConstants.DATE_FORMAT_STRING));  
			clientUserJson.setEmail(client.getUser().getEmail());
			clientUserJson.setFirstName(client.getUser().getFirstName());
			clientUserJson.setLastName(client.getUser().getLastName());
			clientUserJson.setLastUpdate(HunterUtility.formatDate(client.getLastUpdate(), HunterConstants.DATE_FORMAT_STRING));
			clientUserJson.setLastUpdatedBy(client.getLastUpdatedBy());
			clientUserJson.setMiddleName(client.getUser().getMiddleName());
			clientUserJson.setReceiver(client.isReceiver());
			clientUserJson.setUserType(client.getUser().getUserType());
			userJsons.add(clientUserJson); 
		}
		logger.debug("Done creating clientUserJson beans!!");  
		return userJsons;
	}

	
}
