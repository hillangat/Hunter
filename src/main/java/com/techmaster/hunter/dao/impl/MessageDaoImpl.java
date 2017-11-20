package com.techmaster.hunter.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.dao.types.MessageDao;
import com.techmaster.hunter.obj.beans.Message;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterUtility;

public class MessageDaoImpl implements MessageDao{
	
	Logger logger = Logger.getLogger(getClass());
	@Autowired private HunterJDBCExecutor hunterJDBCExecutor;
	@Autowired private HunterHibernateHelper hunterHibernateHelper;

	@Override
	public void insertMessage(Message message) {
		logger.debug("Inserting message...");
		hunterHibernateHelper.saveEntity(message);
		logger.debug("Done inserting message!!"); 
		
	}

	@Override
	public void insertMessages(List<Message> messages) {
		logger.debug("Inserting messages...");
		hunterHibernateHelper.saveEntities(messages);
		logger.debug("Done inserting messages!!"); 
	}

	@Override
	public void deleteMessage(Message message) {
		logger.debug("Deleting messages...");
		hunterHibernateHelper.deleteEntity(message); 
		logger.debug("Done deleting messages!!"); 
	}

	@Override
	public void deleteMessageById(Long msgId) {
		logger.debug("Deleting message with msgId = " + msgId);
		Message message = hunterHibernateHelper.getEntityById(msgId, Message.class);
		hunterHibernateHelper.deleteEntity(message); 
		logger.debug("Done deleting message!!"); 
	}

	@Override
	public Message getMessageById(Long msgId) {
		logger.debug("Fetching message for msgId : " + msgId);
		Message message = hunterHibernateHelper.getEntityById(msgId, Message.class);
		logger.debug("Successfully obtained message : " + message);
		return message;
	}

	@Override
	public List<Message> getAllMessages() {
		logger.debug("Getting all messages...");
		List<Message> messages = hunterHibernateHelper.getAllEntities(Message.class);
		logger.debug("Obtained messages. Size ( " + messages.size() + " )");
		return messages;
	}

	@Override
	public void updateMessage(Message update) {
		logger.debug("Updating message");
		hunterHibernateHelper.updateEntity(update);
		logger.debug("Successfully updated message : " + update);
	}

	@Override
	public Long getNextMessageId(Class<?> clss) { 
		logger.debug("Fetching next message id..."); 
		Long max = hunterHibernateHelper.getMaxEntityIdAsNumber(clss, Long.class,"msgId");
		max = max == null ? 1 : max + 1;
		logger.debug("Successfully obtained max id for entity : " + clss.getSimpleName() + " : Max Id = " + max);
		return max;
		
	}

	@Override
	public void updateTaskMsgLifeStatus(Long taskId, String msgLifeStatus) {
		logger.debug("Updating msg status :  "+ msgLifeStatus);
		Message message = hunterHibernateHelper.getEntityById(taskId, Message.class);
		message.setMsgLifeStatus(msgLifeStatus); 
		logger.debug("Successfully updated task message life status!" );
	}
	
	@Override
	public void updateTaskMsgDelStatus(Long taskId, String msgDelStatus) {
		logger.debug("Updating msg status :  "+ msgDelStatus);
		Message message = hunterHibernateHelper.getEntityById(taskId, Message.class);
		message.setMsgDeliveryStatus(msgDelStatus); 
		logger.debug("Successfully updated task message delivery status!" );
	}

	@Override
	public Map<String, Object> getEmailMsgRefreshData(Long msgId) {
		logger.debug("Fetching email msg refresh data..."); 
		String query = hunterJDBCExecutor.getQueryForSqlId("getEmailMsgRefreshData");
		List<Map<String, Object>> mapList = hunterJDBCExecutor.executeQueryRowMap(query, hunterJDBCExecutor.getValuesList(new Object[]{msgId}));
		Map<String,Object> map = mapList != null && !mapList.isEmpty() ? mapList.get(0) : new HashMap<String, Object>();
		logger.debug("Finished getting email msg data!");
		logger.debug(HunterUtility.stringifyMap(map)); 
		return map;
	}
	
	@Override
	public Map<String, Object> getPrcssTxtMssgeDtls(Long msgId) {
		logger.debug("Fetching text msg details data..."); 
		String query = hunterJDBCExecutor.getQueryForSqlId("getPrcssTxtMssgeDtls");
		List<Map<String, Object>> mapList = hunterJDBCExecutor.executeQueryRowMap(query, hunterJDBCExecutor.getValuesList(new Object[]{msgId}));
		if(mapList != null && mapList.size() > 1){
			logger.warn("Message count is greater than one!!! Returning the first row...");
		}
		Map<String,Object> map = mapList != null && !mapList.isEmpty() ? mapList.get(0) : new HashMap<String, Object>();
		logger.debug("Size of the data ( "+ map.size() +" ) : " + HunterUtility.stringifyMap(map));  
		return map;
	}

}
