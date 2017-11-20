package com.techmaster.hunter.task.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.constants.TaskProcessConstants;
import com.techmaster.hunter.gateway.beans.GateWayClientHelper;
import com.techmaster.hunter.gateway.beans.GateWayClientService;
import com.techmaster.hunter.gateway.beans.HunterEmailClientSenderBean;
import com.techmaster.hunter.obj.beans.GateWayMessage;
import com.techmaster.hunter.obj.beans.Task;
import com.techmaster.hunter.obj.beans.TaskClientConfigBean;
import com.techmaster.hunter.util.HunterUtility;

public class HunterEmailProcessWorker extends AbstractTaskProcessWorker {
	
	private Set<GateWayMessage> messages;
	private Long workerId;
	private String processJobKey;
	private Task task;
	private String[] receivers = null;
	
	public HunterEmailProcessWorker(TaskClientConfigBean configBean,Set<GateWayMessage> messages, Long workerId, String processJobKey) {
		super();
		this.messages = messages;
		this.workerId = workerId;
		this.processJobKey = processJobKey;
	}
	
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}


	@Override
	public void run() {
		
		Map<String, String> values = new HashMap<>();
		values.put(TaskProcessConstants.WORKER_NAME, toString());
		values.put(TaskProcessConstants.MESSAGE_IDS, getMessageIds(messages));
		values.put(TaskProcessConstants.ERROR_TYPE, "Application Error");
		values.put(TaskProcessConstants.ERROR_TEXT, "There was an exception while making a connection to the client server");
		
		Long duration = System.currentTimeMillis();
		Map<String,Object> sendEmailResults = new HashMap<>();
				
		
		for(GateWayMessage message : messages){
			receivers = HunterUtility.initArrayAndInsert(receivers, message.getContact());
		}
		
		try {
			logWorker("Starting to send email..."); 
			sendEmailResults = new HunterEmailClientSenderBean(HunterConstants.CONFIG_HUNTER_DEFAULT_EMAIL_CONFIG_NAME, task, receivers).sendEmail();
			logWorker("Done sending email : " + HunterUtility.stringifyMap( sendEmailResults )); 
			
			if(!sendEmailResults.isEmpty()){
				@SuppressWarnings("unchecked")
				List<String> errors = (List<String>)sendEmailResults.get(GateWayClientService.TASK_PROCESS_ERRORS);
				logWorker( HunterUtility.stringifyList(errors) );
				if( !HunterUtility.isCollectionNotEmpty(errors) && HunterConstants.STATUS_SUCCESS.equals(sendEmailResults.get(TaskProcessConstants.WORKER_STATUS))  ){
					values.put(TaskProcessConstants.ERROR_TEXT, null);
					values.put(TaskProcessConstants.ERROR_TYPE, "N/A" ); 
					values.put(TaskProcessConstants.WORKER_STATUS, HunterConstants.STATUS_SUCCESS);
				}else{
					values.put(TaskProcessConstants.ERROR_TYPE, TaskProcessConstants.ERROR_TYPE_EMAIL_EXCEPTION );
					values.put(TaskProcessConstants.ERROR_TEXT, HunterUtility.stringifyList(errors)); 
					values.put(TaskProcessConstants.WORKER_STATUS, HunterConstants.STATUS_FAILED);
				}
				values.put(TaskProcessConstants.END_POINT, Long.toString(System.currentTimeMillis()));
				values.put(TaskProcessConstants.CONN_STATUS, HunterConstants.STATUS_FAILED);
				
			}else{
				values.put(TaskProcessConstants.ERROR_TYPE, "N/A");
				values.put(TaskProcessConstants.ERROR_TEXT, "No results returned from email sender service");
				values.put(TaskProcessConstants.WORKER_STATUS, HunterConstants.STATUS_SUCCESS);
				values.put(TaskProcessConstants.END_POINT, Long.toString(System.currentTimeMillis()));
				values.put(TaskProcessConstants.CONN_STATUS, HunterConstants.STATUS_SUCCESS);
			}
			
		} catch (Exception e) {
			values.put(TaskProcessConstants.ERROR_TYPE, TaskProcessConstants.ERROR_TYPE_EXCEPTION);
			values.put(TaskProcessConstants.ERROR_TEXT, e.getMessage());
			values.put(TaskProcessConstants.WORKER_STATUS, HunterConstants.STATUS_FAILED);
			values.put(TaskProcessConstants.END_POINT, Long.toString(System.currentTimeMillis()));
			values.put(TaskProcessConstants.CONN_STATUS, HunterConstants.STATUS_FAILED);
			e.printStackTrace();
		}finally{
			//This needs to be done irrespective of whether there is or no exception.
			values.put(TaskProcessConstants.DURATION, Long.toString(duration - System.currentTimeMillis()));  
			values.put(TaskProcessConstants.START_POINT, Long.toString(duration));
			values.put(TaskProcessConstants.WORKER_MSG_COUNT, Integer.toString(messages.size()));
			TaskProcessJobHandler.getInstance().addTaskProcessWorker(toString(), processJobKey, values); 
			logWorker("Updating messages for statuses..."); 
			GateWayClientHelper.getInstance().doSaveOrUpdateInHibernate(messages);
		}
		
		
	}

	public void logWorker(String message){
		Logger.getLogger(HunterProcessWorker.class).debug(this.toString() + " : " + message);
	}
	
	@Override
	public String toString() {
		return Thread.currentThread().getName() + "-" + this.workerId; 
	}
}
