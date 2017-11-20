package com.techmaster.hunter.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.dao.types.GateWayMessageDao;
import com.techmaster.hunter.obj.beans.GateWayMessage;
import com.techmaster.hunter.obj.beans.Task;
import com.techmaster.hunter.util.HunterHibernateHelper;

public class GateWayMessageDaoImpl implements GateWayMessageDao{
	
	Logger logger = Logger.getLogger(GateWayMessageDaoImpl.class);
	
	@Autowired HunterHibernateHelper hunterHibernateHelper;

	@Override
	public void insertMessage(GateWayMessage message) {
		logger.debug("Inserting GateWayMessage...");
		hunterHibernateHelper.saveEntity(message);
		logger.debug("Finished inserting GateWayMessage");
	}
	
	@Override
	public void update(GateWayMessage update) {
		logger.debug("Upadting gateway message...");
		hunterHibernateHelper.updateEntity(update);
		logger.debug("Finished updating Gateway message!"); 
	}

	@Override
	public void insertMessages(Set<GateWayMessage> messages) {
		logger.debug("Inserting gateway messages...");
		List<GateWayMessage> msgList = new ArrayList<>();
		msgList.addAll(messages);
		hunterHibernateHelper.saveEntities(msgList); 
		logger.debug("Finished inserting GateWayMessages!"); 
		
	}

	@Override
	public void deleteMessage(GateWayMessage message) {
		logger.debug("Deleting GateWayMessage .... ");
		hunterHibernateHelper.deleteEntity(message);
		logger.debug("Finished deleting GatewayMessage");
	}

	@Override
	public Set<GateWayMessage> getGateWayMessagesForTask(Task task) {
		Long taskId = task.getTaskId();
		return getGateWayMessagesForTaskId(taskId);
	}

	@Override
	public Set<GateWayMessage> getGateWayMessagesForTaskId(Long taskId) {
		logger.debug("Fetching GatewayMessage for id (" + taskId + ")");	
		String query = "From GateWayMessage g where g.taskId = '" + taskId + "'";
		List<GateWayMessage> messages = hunterHibernateHelper.executeQueryForObjList(GateWayMessage.class, query);
		Set<GateWayMessage> gMsgs = new HashSet<>();
		gMsgs.addAll(messages);
		logger.debug("Successfully returned messages for taskId");
		return gMsgs;
	}

	@Override
	public List<GateWayMessage> getAllGateWayMessages() {
		logger.debug("Getting all GateWayMessages...");
		List<GateWayMessage> gatewayMessages = hunterHibernateHelper.getAllEntities(GateWayMessage.class);
		logger.debug("Successfully returned gateWayMessages!!");
		return gatewayMessages;
	}

	@Override
	public void updateGatewayMessages(List<GateWayMessage> gateWayMessages) {
		logger.debug("Updating gate way messages...");
		hunterHibernateHelper.updateEntitities(gateWayMessages);
		logger.debug("Finished updating gate way messages!");
	}

	@Override
	public void updateGatewayMessages(Set<GateWayMessage> gateWayMessages) {
		List<GateWayMessage> newList = new ArrayList<>();
		newList.addAll(gateWayMessages);
		updateGatewayMessages(newList);
	}

	@Override
	public void insertMessages(List<GateWayMessage> messages) {		
		Set<GateWayMessage> set = new HashSet<>();
		set.addAll(messages);
		insertMessages(set); 
	}

	
	
}
