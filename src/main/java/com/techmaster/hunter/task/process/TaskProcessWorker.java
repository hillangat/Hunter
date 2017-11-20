package com.techmaster.hunter.task.process;

import java.util.Set;

import com.techmaster.hunter.obj.beans.GateWayMessage;
import com.techmaster.hunter.obj.beans.TaskClientConfigBean;


public interface TaskProcessWorker extends Runnable{
	
	public HunterProcessorConnection getConnection();
	public void processResponse(String responseCode, String responseText);
	public String getRequestBody();
	public void setRequestBody(Set<GateWayMessage> messages, TaskClientConfigBean configBean);
	public void setDuration(Long duration,Set<GateWayMessage> messages);
	
}
