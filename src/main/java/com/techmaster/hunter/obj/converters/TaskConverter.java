package com.techmaster.hunter.obj.converters;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.json.ReceiverGroupJson;
import com.techmaster.hunter.obj.beans.EmailMessage;
import com.techmaster.hunter.obj.beans.HunterSocialApp;
import com.techmaster.hunter.obj.beans.HunterSocialGroup;
import com.techmaster.hunter.obj.beans.Message;
import com.techmaster.hunter.obj.beans.ReceiverRegion;
import com.techmaster.hunter.obj.beans.ServiceProvider;
import com.techmaster.hunter.obj.beans.SocialMessage;
import com.techmaster.hunter.obj.beans.Task;
import com.techmaster.hunter.obj.beans.TextMessage;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterLogFactory;
import com.techmaster.hunter.util.HunterUtility;

public class TaskConverter {
	
	private String requestBody;
	private JSONObject taskJson = null;
	private static Logger logger = HunterLogFactory.getLog(TaskConverter.class);
	
	public TaskConverter(String requestBody) { 
		super();
		this.requestBody = requestBody;
		this.taskJson = new JSONObject(this.requestBody); 
	}

	public Task convert(){
		
		logger.debug("Beginning task conversion process.."); 
		
		Task task = getBasicTask();
		Message message = getTaskMessage();
		Set<ReceiverRegion> taskRegions = getTaskReceiverRegions();
		Set<ReceiverGroupJson> taskGroups = getTaskGroups();
		task.setTaskGroups(taskGroups); 
		task.setTaskMessage(message); 
		task.setTaskRegions(taskRegions);
		
		logger.debug("Completed task conversion process successfully..");
		logger.debug("Resultant task >> " + task);
		
		return task;
	}
	
	private Set<ReceiverGroupJson> getTaskGroups() {
		logger.debug("Extracting task groups json..."); 
		Set<ReceiverGroupJson> taskGroups = new HashSet<>();
		JSONArray groupJsonArray = null;
		try {
			groupJsonArray = taskJson.getJSONArray("taskGroups");
			logger.debug("Successfully obtained group json \n " + groupJsonArray); 
		} catch (JSONException e) {
			logger.error("Failed to get task message. Exception >> " + e.getMessage()); 
			return taskGroups;
		}
		if(groupJsonArray != null){
			for(int i=0; i<groupJsonArray.length();i++){
				
				Object groupJson_ = groupJsonArray.get(i);
				JSONObject groupJson = new JSONObject(groupJson_.toString());
				
				String firsName = HunterUtility.getStringOrNullFromJSONObj(groupJson, "firstName");
				String lastName = HunterUtility.getStringOrNullFromJSONObj(groupJson, "lastName");
				String lastUpdatedBy = HunterUtility.getStringOrNullFromJSONObj(groupJson, "lastUpdatedBy");
				String groupName = HunterUtility.getStringOrNullFromJSONObj(groupJson, "groupName");
				String groupDesc = HunterUtility.getStringOrNullFromJSONObj(groupJson, "groupDesc");
				String createdBy = HunterUtility.getStringOrNullFromJSONObj(groupJson, "createdBy");
				String receiverType = HunterUtility.getStringOrNullFromJSONObj(groupJson, "receiverType");
				String groupIdStr = HunterUtility.getStringOrNullFromJSONObj(groupJson, "groupId");
				Long groupId = HunterUtility.getLongFromObject(groupIdStr == null ? "0" : groupIdStr);
				String lastUpdateStr = HunterUtility.getStringOrNullFromJSONObj(groupJson, "lastUpdate");
				Date lastUpdate = HunterUtility.parseDate(lastUpdateStr+":00", HunterConstants.DATE_FORMAT_STRING);
				int receiverCount = HunterUtility.getIntOrZeroFromJsonStr(groupJson, "receiverCount");
				String ownerUserName = HunterUtility.getStringOrNullFromJSONObj(groupJson, "ownerUserName");
				String cretDateStr = HunterUtility.getStringOrNullFromJSONObj(groupJson, "cretDate");
				Date cretDate = HunterUtility.parseDate(cretDateStr+":00", HunterConstants.DATE_FORMAT_STRING);
				
				ReceiverGroupJson receiverGroupJson = new ReceiverGroupJson();
				receiverGroupJson.setCreatedBy(createdBy);
				receiverGroupJson.setFirstName(firsName);
				receiverGroupJson.setGroupDesc(groupDesc);
				receiverGroupJson.setGroupId(groupId);
				receiverGroupJson.setGroupName(groupName);
				receiverGroupJson.setLastName(lastName);
				receiverGroupJson.setCretDate(HunterUtility.formatDate(cretDate, HunterConstants.DATE_FORMAT_STRING));
				receiverGroupJson.setLastUpdate(HunterUtility.formatDate(lastUpdate, HunterConstants.DATE_FORMAT_STRING));
				receiverGroupJson.setLastUpdatedBy(lastUpdatedBy);
				receiverGroupJson.setOwnerUserName(ownerUserName);
				receiverGroupJson.setReceiverCount(receiverCount);
				receiverGroupJson.setReceiverType(receiverType);
				
				taskGroups.add(receiverGroupJson);
			}
		}
		logger.debug("Successfully reconstructed receiver group jsons : \n " + HunterUtility.stringifySet(taskGroups)); 
		return taskGroups;
	}

	public Task convertBasic(){
		return getBasicTask();
	}
	
	private Task getBasicTask(){
		
		Task task = new Task();
		
		String taskType 			= HunterUtility.getStringOrNulFromJSONObj(taskJson, "taskType");
		String taskName 			= HunterUtility.getStringOrNulFromJSONObj(taskJson, "taskName");
		String taskObjective 		= HunterUtility.getStringOrNulFromJSONObj(taskJson, "taskObjective");
		String description 			= HunterUtility.getStringOrNulFromJSONObj(taskJson, "description");
		String cretDate_ 			= HunterUtility.getStringOrNulFromJSONObj(taskJson, "cretDate");
		String createdBy 			= HunterUtility.getStringOrNulFromJSONObj(taskJson, "createdBy");
		String lastUpdate_			= HunterUtility.getStringOrNulFromJSONObj(taskJson, "lastUpdatedBy");
		String lastUpdatedBy		= HunterUtility.getStringOrNulFromJSONObj(taskJson, "createdBy");
		String taskDeliveryStatus 	= HunterUtility.getStringOrNulFromJSONObj(taskJson, "taskDeliveryStatus");
		String taskLifeStatus 		= HunterUtility.getStringOrNulFromJSONObj(taskJson, "taskLifeStatus");
		String taskDateline 		= HunterUtility.getStringOrNulFromJSONObj(taskJson, "taskDateline");
		String taskApprover 		= HunterUtility.getStringOrNulFromJSONObj(taskJson, "taskApprover");
		String gateWayClient 		= HunterUtility.getStringOrNulFromJSONObj(taskJson, "gateWayClient");
		String tskMsgType 			= HunterUtility.getStringOrNulFromJSONObj(taskJson, "tskMsgType");
		String tskAgrmntLoc 		= HunterUtility.getStringOrNulFromJSONObj(taskJson, "tskAgrmntLoc");
		
		Date cretDate 				= HunterUtility.parseDate(cretDate_, HunterConstants.HUNTER_DATE_FORMAT_MIN);
		Date lastUpdate				= HunterUtility.parseDate(lastUpdate_, HunterConstants.HUNTER_DATE_FORMAT_MIN);
		
		Boolean taskApproved 		= Boolean.valueOf( HunterUtility.getStringOrNulFromJSONObj(taskJson, "taskApproved") );
		Boolean recurrentTask 		= Boolean.valueOf( HunterUtility.getStringOrNulFromJSONObj(taskJson, "recurrentTask") );
		
		int desiredReceiverCount 	= Integer.parseInt( HunterUtility.getStringOrNulFromJSONObj(taskJson, "desiredReceiverCount") );
		int availableReceiverCount 	= Integer.parseInt( HunterUtility.getStringOrNulFromJSONObj(taskJson, "availableReceiverCount") == null ? "0" : HunterUtility.getStringOrNulFromJSONObj(taskJson, "availableReceiverCount") );
		int confirmedReceiverCount 	= Integer.parseInt( HunterUtility.getStringOrNulFromJSONObj(taskJson, "confirmedReceiverCount") == null ? "0" : HunterUtility.getStringOrNulFromJSONObj(taskJson, "confirmedReceiverCount") );
		
		long taskBudget 			= HunterUtility.getLongOrNulFromJSONObj(taskJson, "taskBudget");
		long taskCost 				= HunterUtility.getLongOrNulFromJSONObj(taskJson, "taskCost");
		long taskId 				= HunterUtility.getLongOrNulFromJSONObj(taskJson, "taskId");
		long clientId 				= HunterUtility.getLongOrNulFromJSONObj(taskJson, "clientId");
		
		float taskCostF 			= (float)taskCost;
		float taskBudgetF 			= (float)taskBudget;
		
		
		String srlzdTskPrcssJbObjsFilLoc= HunterUtility.getStringOrNulFromJSONObj(taskJson, "srlzdTskPrcssJbObjsFilLoc");
		
		task.setSrlzdTskPrcssJbObjsFilLoc(srlzdTskPrcssJbObjsFilLoc); 
		task.setTaskId(taskId); 
		task.setTaskType(taskType);
		task.setTaskName(taskName);
		task.setTaskObjective(taskObjective);
		task.setDescription(description);
		task.setTaskBudget(taskBudgetF);
		task.setTaskCost(taskCostF);
		task.setRecurrentTask(recurrentTask);
		task.setTaskDeliveryStatus( taskDeliveryStatus == null ? HunterConstants.STATUS_CONCEPTUAL : taskDeliveryStatus ); 
		task.setTaskLifeStatus(taskLifeStatus == null ? HunterConstants.STATUS_DRAFT : taskLifeStatus); 
		task.setTaskDateline( taskDateline == null ? new Date() : HunterUtility.parseDate(taskDateline, HunterConstants.HUNTER_DATE_FORMAT_MIN));
		task.setTaskApprover(taskApprover);
		task.setTskMsgType(tskMsgType); 
		task.setTaskApproved(taskApproved);
		task.setGateWayClient(gateWayClient); 
		task.setTskAgrmntLoc(tskAgrmntLoc); 
		
		task.setDesiredReceiverCount(desiredReceiverCount);
		task.setAvailableReceiverCount(availableReceiverCount);
		task.setConfirmedReceiverCount(confirmedReceiverCount); 
		
		task.setCretDate(cretDate == null ? new Date() : cretDate);
		task.setLastUpdate( lastUpdate_ == null ? new Date() : lastUpdate );
		task.setCreatedBy(createdBy); 
		task.setUpdatedBy( lastUpdatedBy ); 
		task.setClientId( clientId == 0 ? null : clientId ); 
		
		logger.debug("Successfully created basic task from request body!\n  Request Body >> " + requestBody);
		logger.debug("Basic task created >> " + task);
		
		
		return task;
	}
	
	private ServiceProvider getServiceProviderForMsgJson(JSONObject providerJson){
		
		if(providerJson == null || providerJson.length() == 0){
			logger.debug("Service provider is not set for message. Returning null..."); 
			return null;
		}
		
		logger.debug("Constructing provider for messageJSON >> " + providerJson); 
		
		String providerStr = HunterUtility.getStringOrNullFromJSONObj(providerJson, "providerId"); 
		Long providerId = HunterUtility.getLongFromObject(providerStr); 
		
		String providerName = HunterUtility.getStringOrNullFromJSONObj(providerJson, "providerName"); 
		float cstPrAudMsg = HunterUtility.getFloatOrZeroFromJsonStr(providerJson, "cstPrAudMsg");
		float cstPrTxtMsg = HunterUtility.getFloatOrZeroFromJsonStr(providerJson, "cstPrTxtMsg");
		
		ServiceProvider provider = new ServiceProvider();
		
		provider.setProviderId(providerId);
		provider.setProviderName(providerName);
		provider.setCstPrAudMsg(cstPrAudMsg);
		provider.setCstPrTxtMsg(cstPrTxtMsg); 
		
		logger.debug("Successfully constructed provider >> " + provider); 
		
		return provider;
	}
	
	private Message getTaskMessage(){
		String tskMsgType = HunterUtility.getStringOrNullFromJSONObj(taskJson, "tskMsgType");
		logger.debug("Getting task message for task message type : " + tskMsgType); 
		if(tskMsgType != null){
			if(tskMsgType.equalsIgnoreCase(HunterConstants.MESSAGE_TYPE_TEXT)){
				return getTextMessage();
			}else if(tskMsgType.equalsIgnoreCase(HunterConstants.MESSAGE_TYPE_EMAIL)){
				return getEmailMessage();
			}else if(tskMsgType.equalsIgnoreCase(HunterConstants.MESSAGE_TYPE_SOCIAL)){
				return getSocialMessage();
			}else{
				throw new IllegalArgumentException("Please implement extraction for message type : " + tskMsgType);
			}
		}
		return null;	
	}
	
	private JSONObject getTskMsgJson(){
		JSONObject msgJson = null;
		try {
			msgJson = taskJson.getJSONObject("taskMessage");
			logger.debug("Successfully obtained task message json : " + msgJson); 
			return msgJson;
		} catch (JSONException e) {
			logger.error("Failed to get task message. Exception >> " + e.getMessage()); 
			return null;
		}
	}
	
	private EmailMessage getEmailMessage(){
		logger.debug("Extracting email message..."); 
		EmailMessage emailMsg = new EmailMessage();
		JSONObject json = getTskMsgJson();
		
		if(json == null){
			logger.debug("No email messag found! Returning null");
			return null;
		}
		
		boolean multiPart = json.getBoolean("multiPart");
		String msgDeliveryStatus = HunterUtility.getStringOrNullFromJSONObj(json, "msgDeliveryStatus");
		int actualReceivers = HunterUtility.getIntOrZeroFromJsonStr(json, "actualReceivers");
		String eBody = HunterUtility.getStringOrNullFromJSONObj(json, "eBody");
		Long msgId = HunterUtility.getLongOrZeroFromJsonStr(json, "msgId");
		String toList = HunterUtility.getStringOrNullFromJSONObj(json, "toList");
		String msgTaskType = HunterUtility.getStringOrNullFromJSONObj(json, "msgTaskType");
		String attchmtntFileType = HunterUtility.getStringOrNullFromJSONObj(json, "attchmtntFileType");
		boolean hasAttachment =  json.getBoolean("hasAttachment");
		String cssObject = HunterUtility.getStringOrNullFromJSONObj(json, "cssObject");
		String msgOwner = HunterUtility.getStringOrNullFromJSONObj(json, "msgOwner");
		JSONObject providerJson = HunterUtility.getJSONobjOrNullFromJsonObj(json, "provider");
		ServiceProvider provider = getServiceProviderForMsgJson(providerJson);
		String msgLifeStatus = HunterUtility.getStringOrNullFromJSONObj(json, "msgLifeStatus");
		String ccList = HunterUtility.getStringOrNullFromJSONObj(json, "ccList");
		String msgText = HunterUtility.getStringOrNullFromJSONObj(json, "msgText");
		String cretDateStr = HunterUtility.getStringOrNullFromJSONObj(json, "cretDate");
		Date cretDate = HunterUtility.parseDate(cretDateStr, HunterConstants.DATE_FORMAT_STRING);
		String contentType = HunterUtility.getStringOrNullFromJSONObj(json, "contentType");
		String eFrom = HunterUtility.getStringOrNullFromJSONObj(json, "eFrom");
		String lastUpdatedBy = HunterUtility.getStringOrNullFromJSONObj(json, "lastUpdatedBy");
		String msgSendDateStr = HunterUtility.getStringOrNullFromJSONObj(json, "msgSendDate");
		Date msgSendDate = HunterUtility.parseDate(msgSendDateStr, HunterConstants.DATE_FORMAT_STRING);
		int confirmedReceivers = HunterUtility.getIntOrZeroFromJsonStr(json, "confirmedReceivers");
		int priority = HunterUtility.getIntOrZeroFromJsonStr(json, "priority");
		long attchmntBnId = HunterUtility.getLongOrZeroFromJsonStr(json, "attchmntBnId");
		String createdBy = HunterUtility.getStringOrNullFromJSONObj(json, "createdBy");
		String lastUpdateStr  = HunterUtility.getStringOrNullFromJSONObj(json, "lastUpdate");
		Date lastUpdate  = HunterUtility.parseDate(lastUpdateStr, HunterConstants.DATE_FORMAT_STRING);
		String eFooter = HunterUtility.getStringOrNullFromJSONObj(json, "eFooter");
		String eSubject = HunterUtility.getStringOrNullFromJSONObj(json, "eSubject");
		int desiredReceivers = HunterUtility.getIntOrZeroFromJsonStr(json, "desiredReceivers");
		String emailTemplateName = HunterUtility.getStringOrNullFromJSONObj(json, "emailTemplateName");
		String msgAttachment = HunterUtility.getStringOrNullFromJSONObj(json, "messageAttachments");
		
		emailMsg.setMessageAttachments(msgAttachment); 
		emailMsg.setMultiPart(multiPart);
		emailMsg.setMsgDeliveryStatus(msgDeliveryStatus);
		emailMsg.setActualReceivers(actualReceivers);
		emailMsg.seteBody(eBody);
		emailMsg.setMsgId(msgId); 
		emailMsg.setToList(toList);
		emailMsg.setMsgTaskType(msgTaskType);
		emailMsg.setAttchmtntFileType(attchmtntFileType);
		emailMsg.setHasAttachment(hasAttachment);
		emailMsg.setCssObject(cssObject);
		emailMsg.setMsgOwner(msgOwner);
		emailMsg.setProvider(provider);
		emailMsg.setMsgLifeStatus(msgLifeStatus);
		emailMsg.setCcList(ccList);
		emailMsg.setMsgText(msgText);
		emailMsg.setCretDate(cretDate);
		emailMsg.setContentType(contentType);
		emailMsg.seteFrom(eFrom);
		emailMsg.setLastUpdatedBy(lastUpdatedBy); 
		emailMsg.setMsgSendDate(msgSendDate);
		emailMsg.setConfirmedReceivers(confirmedReceivers);
		emailMsg.setPriority(priority); 
		emailMsg.setAttchmntBnId(attchmntBnId);
		emailMsg.setCreatedBy(createdBy);
		emailMsg.setLastUpdate(lastUpdate);
		emailMsg.seteFooter(eFooter);
		emailMsg.seteSubject(eSubject);
		emailMsg.setDesiredReceivers(desiredReceivers);
		emailMsg.setEmailTemplateName(emailTemplateName); 
		
		return emailMsg;
	}
	
	private TextMessage getTextMessage(){
		logger.debug("Extracting text message...");
		TextMessage message = new TextMessage();
		JSONObject msgJson = getTskMsgJson();
		
		if(msgJson == null) {
			logger.warn("Null task message!! Returning null!");
			return null;
		}
		
		logger.debug("Creating textMessage for json >> " + msgJson); 
		
		long msgId = msgJson.getLong("msgId");
		String msgDeliveryStatus = HunterUtility.getStringOrNullFromJSONObj(msgJson, "msgDeliveryStatus"); 
		String msgLifeStatus = HunterUtility.getStringOrNullFromJSONObj(msgJson, "msgLifeStatus"); 
		String msgSendDate_ = HunterUtility.getStringOrNullFromJSONObj(msgJson, "msgSendDate");
		Date msgSendDate = HunterUtility.parseDate(msgSendDate_, HunterConstants.HUNTER_DATE_FORMAT_MIN);
		String msgTaskType = HunterUtility.getStringOrNullFromJSONObj(msgJson, "msgTaskType");
		String msgText = HunterUtility.getStringOrNullFromJSONObj(msgJson, "msgText"); 
		int desiredReceivers = msgJson.getInt("desiredReceivers");
		int actualReceivers = msgJson.getInt("actualReceivers");
		int confirmedReceivers = msgJson.getInt("confirmedReceivers");
		String msgOwner = HunterUtility.getStringOrNullFromJSONObj(msgJson, "msgOwner");
		String text = HunterUtility.getStringOrNullFromJSONObj(msgJson, "text");
		String disclaimer = HunterUtility.getStringOrNullFromJSONObj(msgJson, "disclaimer"); 
		String fromPhone = HunterUtility.getStringOrNullFromJSONObj(msgJson, "fromPhone");
		String toPhone = HunterUtility.getStringOrNullFromJSONObj(msgJson, "toPhone");
		int pageWordCount = msgJson.getInt("pageWordCount");
		
		String cretDate_ = msgJson.getString("cretDate");
		Date cretDate = HunterUtility.parseDate(cretDate_, HunterConstants.HUNTER_DATE_FORMAT_MIN);
		String lastUpdate_ = msgJson.getString("cretDate");
		Date lastUpdate = HunterUtility.parseDate(lastUpdate_, HunterConstants.HUNTER_DATE_FORMAT_MIN);
		String createdBy = msgJson.getString("createdBy"); 
		String lastUpdatedBy = msgJson.getString("lastUpdatedBy"); 
		
		message.setMsgId(msgId);
		message.setMsgDeliveryStatus(msgDeliveryStatus);
		message.setMsgLifeStatus(msgLifeStatus);
		message.setMsgSendDate(msgSendDate);
		message.setMsgTaskType(msgTaskType);
		message.setMsgText(msgText);
		message.setDesiredReceivers(desiredReceivers);
		message.setActualReceivers(actualReceivers);
		message.setConfirmedReceivers(confirmedReceivers);
		message.setMsgOwner(msgOwner);
		
		message.setCretDate(cretDate);
		message.setLastUpdate(lastUpdate);
		message.setCreatedBy(createdBy);
		message.setLastUpdatedBy(lastUpdatedBy);
		
		message.setText(text);
		message.setDisclaimer(disclaimer); 
		message.setFromPhone(fromPhone);
		message.setToPhone(toPhone); 
		message.setPageWordCount(pageWordCount); 
		
		ServiceProvider provider = getServiceProviderForMsgJson(msgJson.getJSONObject("provider"));
		message.setProvider(provider); 
		
		logger.debug("Successfully created textMessage >> " + message); 
		
		
		return message;
	}
	
	public SocialMessage getSocialMessage(){
		logger.debug("Extracting social message...");
		SocialMessage message = new SocialMessage();
		JSONObject msgJson = getTskMsgJson();
		
		if(msgJson == null) {
			logger.warn("Null task message!! Returning null!");
			return null;
		}
		
		Long 
		socialMsgId 		= HunterUtility.getLongOrNulFromJSONObj(msgJson, "socialMsgId"),
		defaultSocialAppId	= HunterUtility.getLongOrNulFromJSONObj(msgJson, "defaultSocialAppId");
		
		String 
		externalId 			  = HunterUtility.getStringOrNulFromJSONObj(msgJson, "externalId"),
		mediaType 			  = HunterUtility.getStringOrNulFromJSONObj(msgJson, "mediaType"),
		description 		  = HunterUtility.getStringOrNulFromJSONObj(msgJson, "description"),
		socialPost 			  = HunterUtility.getStringOrNulFromJSONObj(msgJson, "socialPost"),
		socialPostType 		  = HunterUtility.getStringOrNulFromJSONObj(msgJson, "socialPostType");
		//originalFileFormat 	  = HunterUtility.getStringOrNulFromJSONObj(msgJson, "originalFileFormat"),
		//socialMediaId 		  = HunterUtility.getStringOrNulFromJSONObj(msgJson, "socialMediaId"),
		
		if( socialMsgId != null ){
			message =  HunterDaoFactory.getObject(HunterHibernateHelper.class).getEntityById(socialMsgId, SocialMessage.class);
		}
		
		Set<HunterSocialGroup> hunterSocialGroups = new HashSet<HunterSocialGroup>();
		JSONArray groupIds = msgJson.getJSONArray("hunterSocialGroupsIds");
		
		if( groupIds != null && groupIds.length() > 0 ){
			for (int i = 0; i < groupIds.length(); i++){
				Long groupId = groupIds.getLong(i);
				HunterSocialGroup socialGroup =  HunterDaoFactory.getObject(HunterHibernateHelper.class).getEntityById(groupId, HunterSocialGroup.class);
				hunterSocialGroups.add(socialGroup);
			}
			message.setHunterSocialGroups(hunterSocialGroups);
		}
		
		HunterSocialApp socialApp =  HunterDaoFactory.getObject(HunterHibernateHelper.class).getEntityById(HunterUtility.getLongFromObject(defaultSocialAppId), HunterSocialApp.class);
		
		message.setExternalId(externalId);
		message.setMediaType(mediaType);
		message.setDescription(description);
		message.setSocialPost(socialPost);
		message.setSocialPostType(socialPostType);
		message.setDefaultSocialApp(socialApp);
		
		return message;
	}
	
	private Set<ReceiverRegion> getTaskReceiverRegions(){
		
		Set<ReceiverRegion> taskRegions = new HashSet<>();
		JSONArray jsonArray = null;
		
		try {
			jsonArray = taskJson.getJSONArray("taskRegions");
		} catch (JSONException e) {
			logger.error("Failed to get task region. Exception >> " + e.getMessage()); 
			return null;
		}
		
		if(jsonArray != null){
			for(int i=0; i<jsonArray.length(); i++){
				Object regionJson = jsonArray.get(i);
				if(regionJson != null){
					JSONObject jsonObject =  new JSONObject(regionJson.toString());
					ReceiverRegion receiverRegion = getTaskRegion(jsonObject);
					taskRegions.add(receiverRegion);
				}
			}
		}
		
		return taskRegions;
	}
	
	private ReceiverRegion getTaskRegion(JSONObject rgnJson) {
		
		if(rgnJson == null) {
			logger.warn("Null task region!! Returning null!");
			return null;
		}
		
		logger.debug("Constructing receiver region for regionJson >> " + rgnJson); 
		
		Long regionId = rgnJson.getLong("regionId"); 
		String country = rgnJson.getString("country");
		String state = rgnJson.has("state") ? rgnJson.get("state") != null ? rgnJson.get("state").toString() : null : null;
		boolean hasState = rgnJson.getBoolean("hasState"); 
		String county = rgnJson.has("county") ? rgnJson.get("county") != null ? rgnJson.get("county").toString() : null : null;
		String constituency = rgnJson.has("constituency") ? rgnJson.get("constituency") != null ? rgnJson.get("constituency").toString() : null : null;
		String city = rgnJson.has("city") ? rgnJson.get("city") != null ? rgnJson.get("city").toString() : null : null;
		String ward = rgnJson.has("ward") ? rgnJson.get("ward") != null ? rgnJson.get("ward").toString() : null : null;
		String village = rgnJson.has("village") ? rgnJson.get("village") != null ? rgnJson.get("village").toString() : null : null;
		double longitude = rgnJson.has("longitude") ? rgnJson.getLong("longitude") : 0;
		double latitude =  rgnJson.has("latitude") ?  rgnJson.getLong("latitude") : 0; 
		String currentLevel = rgnJson.has("currentLevel") ? rgnJson.get("currentLevel") != null ? rgnJson.get("currentLevel").toString() : null : null;
		String borderLongLats = rgnJson.has("borderLongLats") ? rgnJson.get("borderLongLats") != null ? rgnJson.get("borderLongLats").toString() : null : null;
		
		ReceiverRegion region = new ReceiverRegion();
		region.setRegionId(regionId);
		region.setCountry(country);
		region.setState(state);
		region.setHasState(hasState);
		region.setCounty(county);
		region.setConstituency(constituency);
		region.setCity(city);
		region.setWard(ward);
		region.setVillage(village);
		region.setLongitude(longitude);
		region.setLatitude(latitude);
		region.setCurrentLevel(currentLevel);
		region.setBorderLongLats(borderLongLats); 
		
		logger.debug("Successfully constructed receiver region >> " + region); 
		
		return region;
	}
	
	public TextMessage convertTextMessage(){
		if(taskJson == null){
			return null;
		}
		TextMessage textMessage = getTextMessage();
		return textMessage;
	}
	
	
	
	
	
	
	
	
	
}

