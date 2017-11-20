package com.techmaster.hunter.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.obj.beans.AudioMessage;
import com.techmaster.hunter.obj.beans.EmailMessage;
import com.techmaster.hunter.obj.beans.MediaMessage;
import com.techmaster.hunter.obj.beans.Message;
import com.techmaster.hunter.obj.beans.TextMessage;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterLogFactory;

public class HunterMessageDaoHelper {
	
	private static Map<String, Long> msgIdMap = new HashMap<>();
	
	private static final String MAX_AUDIO_ID = "maxAudioMsgId";
	private static final String MAX_MEDIA_ID = "maxMediaMsgId";
	private static final String MAX_EMAIL_ID = "maxEmailMsgId";
	private static final String MAX_TEXT_ID = "maxTxtMsgId";
	
	private static volatile Long currntMaxAudioMsgId;
	private static volatile Long currntMaxMediaMsgId;
	private static volatile Long currntMaxEmailMsgId;
	private static volatile Long currntMaxTxtMsgId;
	
	private static Logger LOGGER = HunterLogFactory.getLog(HunterMessageDaoHelper.class);
	
	@Autowired private HunterHibernateHelper hunterHibernateHelper;
	
	
	public HunterMessageDaoHelper(){
		if( hunterHibernateHelper != null ){
			refreshMaxMessageIdMap();
			refreshCurrentMaxMessageIds();
		}
	}
	
	public void refreshMaxMessageIdMap(){
		
		LOGGER.debug("populating max message id map"); 
		
		Long maxTxtMsgId = getNextMessageId(TextMessage.class);  	LOGGER.debug(MAX_TEXT_ID + " >> " + maxTxtMsgId);
		Long maxAudioMsgId = getNextMessageId(AudioMessage.class);	LOGGER.debug(MAX_AUDIO_ID + " >> " + maxAudioMsgId);
		Long maxMediaMsgId = getNextMessageId(MediaMessage.class);	LOGGER.debug(MAX_MEDIA_ID + " >> " + maxMediaMsgId);
		Long maxEmailMsgId = getNextMessageId(EmailMessage.class);	LOGGER.debug(MAX_EMAIL_ID + " >> " + maxEmailMsgId);
		
		msgIdMap.put(MAX_TEXT_ID, maxTxtMsgId);
		msgIdMap.put(MAX_AUDIO_ID, maxAudioMsgId);
		msgIdMap.put(MAX_MEDIA_ID, maxMediaMsgId);
		msgIdMap.put(MAX_EMAIL_ID, maxEmailMsgId);
		
	}
	
	public static void refreshCurrentMaxMessageIds(){
		currntMaxAudioMsgId = msgIdMap.get(MAX_AUDIO_ID);
		currntMaxMediaMsgId = msgIdMap.get(MAX_MEDIA_ID);
		currntMaxEmailMsgId = msgIdMap.get(MAX_EMAIL_ID);
		currntMaxTxtMsgId = msgIdMap.get(MAX_TEXT_ID);
	}
	
	public static Long getNextMessageId(Message message){
		
		if(message instanceof TextMessage){
			return incrementForMessage(MAX_TEXT_ID);
		}else if(message instanceof EmailMessage){
			return incrementForMessage(MAX_EMAIL_ID);
		}else if(message instanceof AudioMessage){
			return incrementForMessage(MAX_AUDIO_ID);
		}else if(message instanceof MediaMessage){
			return incrementForMessage(MAX_MEDIA_ID);
		}
			
		throw new IllegalArgumentException("Message type provided does not exist >> ");
	}
	
	
	public Long getNextMessageId(Class<?> clss) { 
		if(clss == null) throw new IllegalArgumentException("Class for which the id is sought is required. clss >> " + clss);  
		Long nextId = hunterHibernateHelper.getMaxEntityIdAsNumber(clss, Long.class, "msgId");
		nextId = nextId == null ? 1 : nextId + 1;
		HunterLogFactory.getLog(HunterMessageDaoHelper.class).debug("Obtained next hunter address id >> " + nextId); 
		return nextId;
	}
	
	
	public void refreshMapAndCurrentIds(){ 
		refreshMaxMessageIdMap();
		refreshCurrentMaxMessageIds();
	}
	
	public static Long incrementForMessage(String type){
		Long msgId = null;
		if(type.equals(MAX_TEXT_ID)){
			msgId = currntMaxTxtMsgId++;
			msgIdMap.put(MAX_TEXT_ID, currntMaxTxtMsgId);
		}else if(type.equals(MAX_AUDIO_ID)){
			msgId = currntMaxAudioMsgId++;
			msgIdMap.put(MAX_MEDIA_ID, currntMaxAudioMsgId);
		}else if(type.equals(MAX_MEDIA_ID)){
			msgId = currntMaxMediaMsgId++;
			msgIdMap.put(MAX_MEDIA_ID, currntMaxMediaMsgId);
		}else if(type.equals(MAX_EMAIL_ID)){
			msgId = currntMaxEmailMsgId++;
			msgIdMap.put(MAX_EMAIL_ID, currntMaxEmailMsgId);
		}
		return msgId;
	}
	
	
	
	
	
	
	
	
	
	
}
