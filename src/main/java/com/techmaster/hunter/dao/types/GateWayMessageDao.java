package com.techmaster.hunter.dao.types;

import java.util.List;
import java.util.Set;

import com.techmaster.hunter.obj.beans.GateWayMessage;
import com.techmaster.hunter.obj.beans.Task;

public interface GateWayMessageDao {
	
	public void insertMessage(GateWayMessage message);
	public void update(GateWayMessage message);
	public void  updateGatewayMessages(List<GateWayMessage> gateWayMessages);
	public void  updateGatewayMessages(Set<GateWayMessage> gateWayMessages);
	public void insertMessages(Set<GateWayMessage> messages);
	public void insertMessages(List<GateWayMessage> messages);
	public void deleteMessage(GateWayMessage message);
	public Set<GateWayMessage> getGateWayMessagesForTask(Task task);
	public Set<GateWayMessage>  getGateWayMessagesForTaskId(Long taskId);
	public List<GateWayMessage>  getAllGateWayMessages();

}
