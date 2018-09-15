package com.techmaster.hunter.gateway.beans;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.techmaster.hunter.cache.HunterCache;
import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.GateWayMessage;
import com.techmaster.hunter.obj.beans.HunterMessageReceiver;
import com.techmaster.hunter.obj.beans.ReceiverGroupReceiver;
import com.techmaster.hunter.obj.beans.Task;
import com.techmaster.hunter.obj.beans.TaskClientConfigBean;
import com.techmaster.hunter.obj.beans.TaskMessageReceiver;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterSessionFactory;
import com.techmaster.hunter.util.HunterUtility;
import com.techmaster.hunter.xml.XMLService;

public abstract class AbstractGateWayClientService implements GateWayClientService {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired private HunterSessionFactory hunterSessionFactory;
	
	public void addErrorsAndStatusToResultMap(Map<String, Object> results,List<String> taskProcessErrors, String taskProcessStatus){
		results.put(TASK_PROCESS_ERRORS, taskProcessErrors);
		results.put(TASK_PROCESS_STATUS, taskProcessStatus);
	}
	
	public <T> T getObjFromExecParams(Class<T> clzz, String key, Map<String, Object> execParams){
		if(execParams != null && !execParams.isEmpty() && execParams.containsKey(key)){
			@SuppressWarnings("unchecked")
			T t = (T)execParams.get(key);
			return t;
		}else{
			throw new NullPointerException("Cannot find the requested object : " + key);
		}
	}
	
	public void putStatusAndErrors(List<String> errors, String status, Map<String, Object> results){
		results.put(TASK_VALIDATION_ERRORS, errors);
		results.put(TASK_PROCESS_STATUS, status);
	}
	
	public Set<GateWayMessage> doBatchSaveOrUpdate(final Set<GateWayMessage> messages){
		logger.debug("Storing messages in hibernate..."); 
		Session session = null;
		Transaction trans = null;
		try {
			session = hunterSessionFactory.getSessionFactory().openSession();
			trans = session.beginTransaction();
			int i = 0;
			for ( GateWayMessage message : messages ) {
			    session.saveOrUpdate(message);
				if( i % 50 == 0 ) { // Same as the JDBC batch size
			        session.flush();
			        session.clear();
			    }
				i++;
			}
			logger.debug("Successfully saved or updated messages!"); 
			trans.commit();
			session.close();
		} catch (HibernateException e) {
			trans.rollback();
			e.printStackTrace();
		}finally{
			HunterHibernateHelper.closeSession(session); 
		}
		return messages;
	}
	
	public Set<GateWayMessage> createGtwayMsgsFrAllTskGtwyMssgs(Task task, AuditInfo auditInfo){
		logger.debug("Getting gateway message for task : " + task.getTaskId()); 
		List<HunterMessageReceiver> regionReceivers = getRegionReceivers(task);
		List<ReceiverGroupReceiver> groupReceivers = getGroupReceivers(task);
		Set<GateWayMessage> regionMsgs = createGatewayMsgs(regionReceivers, task, auditInfo);
		Set<GateWayMessage> groupMsgs = createGatewayMsgs(groupReceivers, task, auditInfo);
		Set<GateWayMessage> allMsgs = new HashSet<>();
		allMsgs.addAll(regionMsgs);
		allMsgs.addAll(groupMsgs);
		allMsgs = doBatchSaveOrUpdate(allMsgs);
		logger.debug("Totak size of task gateway messages : " + allMsgs.size());
		return allMsgs;
	}

	@Override
	public List<HunterMessageReceiver> getRegionReceivers(Task task) {
		List<HunterMessageReceiver> regionReceivers = GateWayClientHelper.getInstance().getTaskRegionReceivers(task.getTaskId());
		logger.debug("Region receivers for task ( " + task.getTaskId()+ " ). Sze ( " + ( regionReceivers == null ? 0 : regionReceivers.size()) + " )");
		return regionReceivers;
	}
	

	@Override
	public List<ReceiverGroupReceiver> getGroupReceivers(Task task) {
		List<ReceiverGroupReceiver> groupReceivers = GateWayClientHelper.getInstance().getAllTaskGroupReceivers(task);
		logger.debug("Group receivers for task ( " + task.getTaskId()+ " ). Sze ( " + ( groupReceivers == null ? 0 : groupReceivers.size()) + " )");
		return groupReceivers;
	}

	@Override
	public TaskClientConfigBean readConfigurations(String clientName) {
		
		GateWayClientHelper.getInstance().validateTaskConfigName(clientName);
		TaskClientConfigBean clientConfigsBean = HunterCacheUtil.getInstance().getTaskClientConfigBean(clientName);
		
		if(clientConfigsBean == null){
			logger.debug("Client configuration has not been created yet. Creating a new one..."); 
			clientConfigsBean = new TaskClientConfigBean();
		}else{
			logger.debug("Client configuration is already created : " + clientConfigsBean.getClientName());  
			return clientConfigsBean;
		}
		
		XMLService configService = HunterCacheUtil.getInstance().getXMLService(HunterConstants.CLIENT_CONFIG_XML_CACHED_SERVICE);
		
		if(configService != null){
			
			String configsPath = "//client[@clientName=\""+ clientName +"\"]/configurations";
			String clientIdPath = "//client[@clientName=\""+ clientName +"\"]/@clientId";
			
			Long clientId = Long.parseLong(configService.getTextValue(clientIdPath));
			String userName = configService.getTextValue(configsPath + "/context/security/userName");
			String password = configService.getTextValue(configsPath + "/context/security/password");
			String actvMethod = configService.getTextValue(configsPath + "/context/methods/method[@active=\"true\"]/@type");
			String methoUrl = configService.getTextValue(configsPath + "/context/methods/method[@active=\"true\"]/@url");
			String denomination = configService.getTextValue(configsPath + "/context/denomination");
			String msgType = configService.getTextValue(configsPath + "/context/messageType");
			String preemptReceivers = configService.getTextValue(configsPath + "/context/preempt/receivers");
			String preempPercentage = configService.getTextValue(configsPath + "/context/preempt/percentage");
			String batchNo = configService.getTextValue(configsPath + "/context/batch/@batchNo");
			batchNo = batchNo == null ? batchNo = "0" : batchNo;
			String batchMark = configService.getTextValue(configsPath + "/context/batch/@batchMark");
			batchMark =batchMark == null ? batchMark = "0" : batchMark;
			String batchType = configService.getTextValue(configsPath + "/context/worker/@multiple");
			String workerDefault = configService.getTextValue(configsPath + "/context/worker/@workerDefault");
			String workerName = configService.getTextValue(configsPath + "/context/worker/@workerName");
			
			clientConfigsBean.setActiveMethod(actvMethod);
			clientConfigsBean.setClientId(clientId);
			clientConfigsBean.setUserName(userName);
			clientConfigsBean.setPassword(password);
			clientConfigsBean.setMethodURL(methoUrl);
			clientConfigsBean.setDenomination(denomination);
			clientConfigsBean.setClientName(clientName);
			clientConfigsBean.setMsgType(msgType);
			clientConfigsBean.setPreempReceivers(preemptReceivers);
			clientConfigsBean.setBatchNo(Integer.parseInt(batchNo));
			clientConfigsBean.setBatchMark(Integer.parseInt(batchMark));  
			clientConfigsBean.setPreemptPercentage(preempPercentage == null ? 0 : Float.parseFloat(preempPercentage)); 
			clientConfigsBean.setBatchType(batchType);
			clientConfigsBean.setWorkerDefault(Boolean.valueOf(workerDefault)); 
			clientConfigsBean.setWorkerName(workerName);
			
			String configsPath_ = configsPath + "/configs/config";
			NodeList configsList = configService.getNodeListForPathUsingJavax(configsPath_);
			Map<String, String> configs = new HashMap<>();
			
			if(HunterUtility.isNodeListNotEmptpy(configsList)){
				for(int i=0; i<configsList.getLength(); i++){
					Node config = configsList.item(i);
					String label = config.getChildNodes().item(1).getTextContent();
					String value = config.getChildNodes().item(3).getTextContent();
					configs.put(label, value);
				}
				logger.debug("Configurations for client ("+ clientName +") : " +  HunterUtility.stringifyMap(configs)); 
				clientConfigsBean.setConfigs(configs); 
			}
			
			
			configsPath_ = configsPath + "/connConfigs/config";
			configsList = configService.getNodeListForPathUsingJavax(configsPath_);
			Map<String, String> connConfigs = new HashMap<>();
			
			if(HunterUtility.isNodeListNotEmptpy(configsList)){
				for(int i=0; i<configsList.getLength(); i++){
					Node config = configsList.item(i);
					String label = config.getChildNodes().item(1).getTextContent();
					String value = config.getChildNodes().item(3).getTextContent();
					connConfigs.put(label, value);
				}
				logger.debug("Connection configurations for client ("+ clientName +") : " +  HunterUtility.stringifyMap(connConfigs)); 
				clientConfigsBean.setConnConfigs(connConfigs); 
			}
			
			
			
			
		}
		logger.debug("Finished wiring up configuration bean. Now caching it..."); 
		HunterCache.getInstance().put(clientName, clientConfigsBean);
		return clientConfigsBean;
	}
	
	@Override
	public Set<GateWayMessage> createGatewayMsgs(List<? extends Object> receivers, Task task, AuditInfo auditInfo) {
		
		Set<GateWayMessage> gateWayMessages = new HashSet<>();
		if(receivers == null || receivers.isEmpty()){
			logger.debug("Receivers passed in is empty. Returning...");
			return gateWayMessages;
		}
		
		Object obj = receivers != null && !receivers.isEmpty() ? receivers.get(0) : null;
		
		if(obj != null && obj instanceof HunterMessageReceiver){
			for(Object receiver : receivers){
				HunterMessageReceiver msgReceiver = (HunterMessageReceiver)receiver;
				GateWayMessage gateWayMessage = new GateWayMessage();
				gateWayMessage.setClientTagKey("region_" + msgReceiver.getReceiverContact() + "_" + task.getTaskId()); 
				gateWayMessage.setContact(msgReceiver.getReceiverContact());
				setOtherGateWayMsgValues(gateWayMessage, task, auditInfo); 
				gateWayMessages.add(gateWayMessage);
			}
		}else if(obj != null && obj instanceof ReceiverGroupReceiver){
			for(Object receiver : receivers){
				ReceiverGroupReceiver msgReceiver = (ReceiverGroupReceiver)receiver;
				GateWayMessage gateWayMessage = new GateWayMessage();
				gateWayMessage.setClientTagKey("group_" + msgReceiver.getReceiverContact() + "_" + task.getTaskId()); 
				gateWayMessage.setContact(msgReceiver.getReceiverContact());
				setOtherGateWayMsgValues(gateWayMessage, task, auditInfo); 
				gateWayMessages.add(gateWayMessage);
			}
		}
		
		return gateWayMessages;
	}
	
	
	private void setOtherGateWayMsgValues(GateWayMessage gateWayMessage, Task task, AuditInfo auditInfo){
		
		gateWayMessage.setCreatedBy(auditInfo.getCreatedBy());
		gateWayMessage.setCreatedOn(new Date());
		gateWayMessage.setgClient(task.getGateWayClient());
		gateWayMessage.setMessageType(task.getTskMsgType());
		gateWayMessage.setSendDate(new Date()); 
		gateWayMessage.setStatus(HunterConstants.STATUS_DRAFT);
		gateWayMessage.setTaskId(task.getTaskId());
		gateWayMessage.setText(HunterUtility.getStringBlob(task.getTaskMessage().getMsgText())); 
		gateWayMessage.setMsgId(null);
		
		gateWayMessage.setDuration(null);
		gateWayMessage.setErrorText(null);
		gateWayMessage.setClntRspCode(null);
		gateWayMessage.setClntRspText(null);
		gateWayMessage.setRequestBody(null);
		gateWayMessage.setSubsRspnsCode(null);
		gateWayMessage.setSubsRspnsText(null);
	}

	
	@Override
	public Set<TaskMessageReceiver> getTskMsgRcvrsFrTskId(Long taskId) {
		List<HunterMessageReceiver> receivers = GateWayClientHelper.getInstance().getTaskRegionReceivers(taskId);
		Set<TaskMessageReceiver> taskMsgRcvers = new HashSet<TaskMessageReceiver>();
		logger.debug("Creating task message receivers from hunterMessage receivers...");
		int counter = 0;
		for(HunterMessageReceiver hntrRcvr : receivers){
			TaskMessageReceiver tskMsgRcvr = GateWayClientHelper.getInstance().convertHunterMsgRcvrToTaskMsgRcvr(hntrRcvr);
			logger.debug(tskMsgRcvr); 
			taskMsgRcvers.add(tskMsgRcvr);
			counter++;
		}
		logger.debug("Count = " + counter); 
		logger.debug("Finished creating task message receivers for task!!"); 
		return taskMsgRcvers;
	}
	
	
}
