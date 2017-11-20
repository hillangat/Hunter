package com.techmaster.hunter.task.process;

import java.util.HashMap;
import java.util.Map;

import com.techmaster.hunter.gateway.beans.GateWayClientService;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.Task;

public class TaskProcessSubmitter extends Thread {
	
	private Task task = null;
	private AuditInfo auditInfo = null;
	private GateWayClientService clientService = null;
	private Map<String,Object> executeParams = new HashMap<String, Object>();
	private boolean isValid = false;

	public TaskProcessSubmitter(Task task, AuditInfo auditInfo,GateWayClientService clientService) {
		super();
		this.task = task;
		this.auditInfo = auditInfo;
		this.clientService = clientService;
		//validate fields first
		this.isValid = task != null && auditInfo != null && clientService != null;
	}
	
	public Map<String,Object> submit(){
		if( isValid ){
			executeParams.put(GateWayClientService.PARAM_AUDIT_INFO, auditInfo);
			executeParams.put(GateWayClientService.TASK_BEAN, task);
			return clientService.execute(executeParams);
		}
		throw new IllegalArgumentException("Parameter to submit task for processing is not valid!"); 
	}

	@Override
	public void run() {
		this.submit();
	}
	
}
