package com.techmaster.hunter.gateway.beans;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.GateWayMessage;
import com.techmaster.hunter.obj.beans.HunterMessageReceiver;
import com.techmaster.hunter.obj.beans.ReceiverGroupReceiver;
import com.techmaster.hunter.obj.beans.Task;
import com.techmaster.hunter.obj.beans.TaskClientConfigBean;
import com.techmaster.hunter.obj.beans.TaskMessageReceiver;

public interface GateWayClientService {
	
	
	public static final String GATE_WAY_MESSAGES = "GATE_WAY_MESSAGES";
	public static final String TASK_PROCESS_ERRORS = "TASK_PROCESS_ERRORS";
	public static final String TASK_PROCESS_STATUS = "TASK_PROCESS_STATUS";
	public static final String TASK_BEAN = "TASK_BEAN";
	public static final String TASK_HISTORY_BEAN = "TASK_HISTORY_BEAN";
	
	public static final String TASK_VALIDATION_ERRORS = "TASK_VALIDATION_ERRORS";
	public static final String TASK_VALIDATION_STATUS = "TASK_VALIDATION_STATUS";
	
	public static final String TASK_REGION_RECEIVERS = "REGION_RECEIVERS";
	public static final String TASK_GROUP_RECEIVERS = "TASK_REGION_RECEIVERS";
	
	public static final String EXECUTION_TYPE_KEY = "PREEMPTIVE_EXECUTION_TYPE";
	public static final String RESPONSE_BODY_KEY = "RESPONSE_BODY";

	public static final String PARAM_AUDIT_INFO = "PARAM_AUDIT_INFO";
	
	
	
	public Map<String, Object> execute(Map<String, Object> executeParams);
	
	
	public List<HunterMessageReceiver> getRegionReceivers(Task task);
	public List<ReceiverGroupReceiver> getGroupReceivers(Task task);
	public TaskClientConfigBean readConfigurations(String clientName);
	public Set<GateWayMessage> createGatewayMsgs(List<? extends Object> receivers, Task task, AuditInfo auditInfo);
	public Set<TaskMessageReceiver> getTskMsgRcvrsFrTskId(Long taskId);
	

}
