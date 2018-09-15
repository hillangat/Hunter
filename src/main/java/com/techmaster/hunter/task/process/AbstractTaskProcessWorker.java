package com.techmaster.hunter.task.process;

import java.util.Set;

import org.apache.log4j.Logger;

import com.techmaster.hunter.obj.beans.GateWayMessage;
import com.techmaster.hunter.obj.beans.TaskClientConfigBean;
import com.techmaster.hunter.util.HunterUtility;

public abstract class AbstractTaskProcessWorker implements TaskProcessWorker {
	
	private Logger logger = Logger.getLogger(getClass());

	public String getMessageIds( Set<GateWayMessage> messages ){
		StringBuilder builder = new StringBuilder();
		if(messages != null && !messages.isEmpty()){
			for(GateWayMessage message : messages){
				builder.append(message.getContact());
				builder.append(",");
			}
		}
		String msgIdStr = builder.toString();
		if(msgIdStr.endsWith(",")){
			msgIdStr = msgIdStr.substring(0, msgIdStr.length() - 1);
		}
		logger.debug("Message Ids : " + msgIdStr); 
		return msgIdStr;
	}
	
	
	@Override
	public void setDuration(Long duration, Set<GateWayMessage> messages) {
		if( !HunterUtility.isCollNotEmpty(messages) ){ 
			duration = duration/messages.size();
			for(GateWayMessage msg : messages){
				msg.setDuration(duration);
			}
		}
	}
	
	
	@Override
	public HunterProcessorConnection getConnection() {
		return null;
	}

	@Override
	public void processResponse(String responseCode, String responseText) {
		
	}

	@Override
	public String getRequestBody() {
		return null;
	}

	@Override
	public void setRequestBody(Set<GateWayMessage> messages, TaskClientConfigBean configBean) {
	}

	
	
}
