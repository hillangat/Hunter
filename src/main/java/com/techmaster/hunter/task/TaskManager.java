package com.techmaster.hunter.task;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.techmaster.hunter.gateway.beans.GateWayClientService;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.EmailMessage;
import com.techmaster.hunter.obj.beans.GateWayMessage;
import com.techmaster.hunter.obj.beans.HunterMessageReceiver;
import com.techmaster.hunter.obj.beans.Message;
import com.techmaster.hunter.obj.beans.Task;
import com.techmaster.hunter.obj.beans.TaskHistory;
import com.techmaster.hunter.obj.beans.TaskMessageReceiver;
import com.techmaster.hunter.obj.beans.TextMessage;


public interface TaskManager {
	
	public abstract List<HunterMessageReceiver> getHntrMsgRcvrsFrmRgn(String countryName, String regionLevel, String regionLevelName, String contactType, boolean  activeOnly);
	public abstract List<TaskMessageReceiver> genTaskMsgRcvrsFrmRgn(String countryName,String regionLevel, String regionLevelName, String contactType, boolean activeOnly, Long taskId);
	public abstract List<String> validateStatusChange(Long taskId, String status, String userName);
	public abstract TaskMessageReceiver createTskMsgRcvrFrmHntrMsgRcvr(HunterMessageReceiver hntrMsgRcvr, Long taskId, boolean random);
	public abstract List<String> validateTask(Task task);
	public abstract Message getTaskMessage(Task task);
	public abstract Message getTaskIdMessage(Long taskId);
	public abstract GateWayClientService getClientForTask(Task task);
	public abstract Map<String, Object>  processTask(Task task, AuditInfo auditInfo);
	public abstract List<GateWayMessage> getUnSuccessfulMessagesForTask(Task task);
	public abstract Task cloneTask(Task task, String newOwner,String taskName, String taskDescription, AuditInfo auditInfo)  throws IllegalAccessException, InvocationTargetException;
	public abstract TextMessage cloneTextMessage(TextMessage textMessage);
	public abstract EmailMessage cloneEmailMessage(EmailMessage emailMessage);
	public abstract TextMessage convertTextMessage(String json);
	public abstract Message getTaskDefaultMessage(Long taskId, String type);
	public abstract String addGroupToTask(Long groupId, Long taskId);
	public abstract void removeGroupFromTask(Long groupId, Long taskId);
	public abstract int getTotalTaskGroupsReceivers(Long taskId); 
	public List<String> validateTaskFinance(Task task);
	public abstract List<Object> validateMessageDelete(Long emailId); 
	public Map<String, Object> getGateWayClientExecuteMap(Task task);
	public int getTaskGroupTotalNumber(Long taskId);
	public TaskHistory getNewTaskHistoryForEventName(Long taskId, String evenName, String eventUser);
	public void setTaskHistoryStatusAndMessage(TaskHistory taskHistory,String eventStatus, String message);
	public boolean userHasRole(String roleName, String userName);
	public String deleteTask(Long taskId);
	public TextMessage getDefaultTextMessage(Task task, AuditInfo auditInfo);
	
	
	
	
	
}

