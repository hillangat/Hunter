package com.techmaster.hunter.dao.types;

import java.util.List;

import com.techmaster.hunter.obj.beans.EmailMessage;

public interface EmailMessageDao {
	
	public void insertEmailMessage(EmailMessage email);
	public void insertEmailMessages(List<EmailMessage> email);
	public void deleteEmailMessageById(Long clientId);
	public void deleteEmailMessage(EmailMessage email);
	public EmailMessage getEmailMessageById(Long id);
	public List<EmailMessage> getAllEmailMessages();
	public void updateEmailMessage(EmailMessage update);
	public Long getNextEmailMessageId();

}
