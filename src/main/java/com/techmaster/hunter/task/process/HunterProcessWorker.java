package com.techmaster.hunter.task.process;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.constants.TaskProcessConstants;
import com.techmaster.hunter.gateway.beans.GateWayClientHelper;
import com.techmaster.hunter.gateway.beans.GateWayResponseHanlder;
import com.techmaster.hunter.obj.beans.GateWayMessage;
import com.techmaster.hunter.obj.beans.TaskClientConfigBean;
import com.techmaster.hunter.util.HunterUtility;

public class HunterProcessWorker extends AbstractTaskProcessWorker{
	
	private TaskClientConfigBean configBean;
	private Set<GateWayMessage> messages;
	private Long workerId;
	private String processJobKey;
	private Logger logger = Logger.getLogger(getClass()); 
	private String workerStatus = HunterConstants.STATUS_FAILED;
	
	public HunterProcessWorker(TaskClientConfigBean configBean,Set<GateWayMessage> messages, Long workerId, String processJobKey) {
		super();
		this.configBean = configBean;
		this.messages = messages;
		this.workerId = workerId;
		this.processJobKey = processJobKey;
	}
	
	@Override
	public void run() {
		
		// first set requestBodies.
		setRequestBody(messages, configBean); 
		
		Map<String, String> values = new HashMap<>();
		values.put(TaskProcessConstants.WORKER_NAME, toString());
		values.put(TaskProcessConstants.MESSAGE_IDS, getMessageIds(messages));
		values.put(TaskProcessConstants.ERROR_TYPE, HunterUtility.getApplicationErrorMessage());
		values.put(TaskProcessConstants.ERROR_TEXT, "There was an exception while making a connection to the client server");
		Long duration = System.currentTimeMillis();
		
		try {
			
			HunterProcessorConnection conn = getConnection();
			values.put(TaskProcessConstants.START_POINT, Long.toString(duration)); 
			Map<String, Object> response = conn.getResponse();
			Long endPoint = System.currentTimeMillis();
			values.put(TaskProcessConstants.END_POINT, Long.toString(endPoint)); 
			duration = endPoint - duration;
			setDuration(duration, messages);
			values.put(TaskProcessConstants.WORKER_MSG_COUNT, Long.toString(messages.size())); 
			String responseCode = HunterUtility.getStringOrNullOfObj(response.get(TaskProcessConstants.RESPONSE_CODE)); 
			values.put(TaskProcessConstants.RESPONSE_CODE, responseCode); 
			values.put(TaskProcessConstants.DURATION, HunterUtility.getStringOrNullOfObj(response.get(TaskProcessConstants.RESPONSE_DURATION)));  
			String responseText = HunterUtility.getStringOrNullOfObj(response.get(TaskProcessConstants.RESPONSE_TEXT));
			values.put(TaskProcessConstants.RESPONSE_TEXT, responseText);
			values.put(TaskProcessConstants.CONN_STATUS, HunterUtility.getStringOrNullOfObj(response.get(TaskProcessConstants.CONN_STATUS)));
			processResponse(responseCode, responseText); 
			logWorker(HunterUtility.stringifyMap(response));
			values.put(TaskProcessConstants.ERROR_TYPE, HunterUtility.getStringOrNullOfObj(response.get(TaskProcessConstants.ERROR_TYPE))); 
			values.put(TaskProcessConstants.ERROR_TYPE_RESPONSE, HunterUtility.getStringOrNullOfObj(response.get(TaskProcessConstants.ERROR_TYPE_RESPONSE))); 
			values.put(TaskProcessConstants.WORKER_STATUS, workerStatus);
			
		} catch (Exception e) {
			values.put(TaskProcessConstants.ERROR_TYPE, TaskProcessConstants.ERROR_TYPE_EXCEPTION);
			values.put(TaskProcessConstants.ERROR_TEXT, e.getMessage());
			values.put(TaskProcessConstants.WORKER_STATUS, HunterConstants.STATUS_FAILED);
			values.put(TaskProcessConstants.START_POINT, Long.toString(duration));
			values.put(TaskProcessConstants.END_POINT, Long.toString(System.currentTimeMillis()));
			values.put(TaskProcessConstants.DURATION, Long.toString(duration - System.currentTimeMillis()));  
			values.put(TaskProcessConstants.WORKER_MSG_COUNT, Integer.toString(messages.size()));  
			values.put(TaskProcessConstants.CONN_STATUS, HunterConstants.STATUS_FAILED);
			e.printStackTrace();
		}finally{
			//This needs to be done irrespective of whether there is or no exception.
			TaskProcessJobHandler.getInstance().addTaskProcessWorker(toString(), processJobKey, values); 
			logWorker("Updating messages for statuses..."); 
			GateWayClientHelper.getInstance().doSaveOrUpdateInHibernate(messages);
		}
		logger.debug("Successfully completed process job : " + toString()); 
	}

	@Override
	public HunterProcessorConnection getConnection() {
		logWorker("Getting hunter connection object...");
		String requestBody = getRequestBody();
		HunterProcessorConnection conn = new HunterProcessorConnection(configBean.getActiveMethod(),configBean,requestBody);
		return conn;
	}

	@Override
	public void processResponse(String responseCode, String responseText) {
		logWorker("Processing response..."); 
		if(messages != null && !messages.isEmpty()){
			for(GateWayMessage message : messages){
				GateWayResponseHanlder.getInstance().updateGateWayMessageForStatus(configBean.getClientName(), responseText, message, HunterConstants.STATUS_TYPE_CLIENT);
			}
		}
		String status = GateWayResponseHanlder.getInstance().setStatusFromResponseText(responseText, configBean.getClientName(), messages);
		this.workerStatus = status;
	}
	
	@Override
	public String getRequestBody() {
		logWorker("getting request body..."); 
		String clientName = configBean.getClientName();
		String contentStr = null;
		if(HunterConstants.CLIENT_CM.equals(clientName) && HunterConstants.METHOD_POST.equals(configBean.getActiveMethod())){ 
			contentStr = GateWayClientHelper.getInstance().getCMPostRequestBody(configBean, messages);
		}else if(HunterConstants.CLIENT_CM.equals(clientName) && HunterConstants.METHOD_GET.equals(configBean.getActiveMethod())){
			contentStr = GateWayClientHelper.getInstance().getCMGetRequestBody(configBean, messages);
		}else if(HunterConstants.CLIENT_OZEKI.equals(clientName) && HunterConstants.METHOD_GET.equals(configBean.getActiveMethod())){
			contentStr = GateWayClientHelper.getInstance().getOzekiGetRequestBody(configBean, messages);
		}else if(HunterConstants.CLIENT_OZEKI.equals(clientName) && HunterConstants.METHOD_GET.equals(configBean.getActiveMethod())){
			contentStr = GateWayClientHelper.getInstance().getOzekiPostRequestBody(configBean, messages);
		}
		logWorker(contentStr); 
		return contentStr;
	}
	
	
	public void logWorker(String message){
		Logger.getLogger(HunterProcessWorker.class).debug(this.toString() + " : " + message);
	}
	
	@Override
	public void setRequestBody(Set<GateWayMessage> messages, TaskClientConfigBean configBean) {
			logger.debug(toString() + " : setting request bodies..."); 
			ClientReqBodyHelper.getInstance().setRequestBody(messages, configBean); 	
	}

	@Override
	public String toString() {
		return Thread.currentThread().getName() + "-" + this.workerId; 
	}
	

}
