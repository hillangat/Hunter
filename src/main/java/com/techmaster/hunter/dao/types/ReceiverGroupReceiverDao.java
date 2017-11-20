package com.techmaster.hunter.dao.types;

import java.util.List;

import com.techmaster.hunter.obj.beans.ReceiverGroupReceiver;

public interface ReceiverGroupReceiverDao {
	
	public void insertReceiverGroupReceiver(ReceiverGroupReceiver receiverGroupReceiver);
	public void insertReceiverGroupReceivers(List<ReceiverGroupReceiver> receiverGroupReceivers);
	public ReceiverGroupReceiver getReceiverById(Long receiverId);
	public List<ReceiverGroupReceiver> getReceiversForGroupId(Long groupId);
	public void deleteReceiverById(Long receiverId);
	public void deleteReceiver(ReceiverGroupReceiver receiverGroupReceiver);
	public void updateReceiverGroup(ReceiverGroupReceiver receiverGroupReceiver);

}
