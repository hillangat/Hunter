package com.techmaster.hunter.dao.types;

import java.util.List;
import java.util.Map;

import com.techmaster.hunter.obj.beans.Message;

public interface MessageDao {
	
	public void insertMessage(Message message);
	public void insertMessages(List<Message> messages);
	public void deleteMessage(Message message);
	public void deleteMessageById(Long msgId);
	public Message getMessageById(Long msgId);
	public List<Message> getAllMessages();
	public void updateMessage(Message update);
	public Long getNextMessageId(Class<?> clss);
	public abstract void updateTaskMsgLifeStatus(Long taskId, String msgSts);
	public abstract void updateTaskMsgDelStatus(Long taskId, String msgLifeStatus); 
	public Map<String, Object> getEmailMsgRefreshData(Long msgId);
	public Map<String, Object> getPrcssTxtMssgeDtls(Long msgId); 

	
}
