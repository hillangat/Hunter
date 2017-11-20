package com.techmaster.hunter.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.dao.types.EmailMessageDao;
import com.techmaster.hunter.obj.beans.EmailMessage;
import com.techmaster.hunter.util.HunterHibernateHelper;

public class EmailMessageDaoImpl implements EmailMessageDao{
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired private HunterHibernateHelper hunterHibernateHelper;

	@Override
	public void insertEmailMessage(EmailMessage email) {
		logger.debug("Inserting email...");
		hunterHibernateHelper.saveEntity(email); 
		logger.debug("Finished inserting email"); 
	}

	@Override
	public void insertEmailMessages(List<EmailMessage> emails) {
		logger.debug("Inserting emails..."); 
		hunterHibernateHelper.saveEntities(emails);
		logger.debug("Finished inserting emails"); 
	}

	@Override
	public void deleteEmailMessageById(Long id) {
		EmailMessage email = hunterHibernateHelper.getEntityById(id, EmailMessage.class);
		if(email == null){
			logger.error("Email for id(" + id +") Was not found and could not be deleted!!!"); 
		}
		logger.debug("Obtained email message for id(" + id +") Email Message >> " + email);
		logger.debug("Deleting email...");
		hunterHibernateHelper.deleteEntity(email); 
		logger.debug("Finished deleting email");
		
	}

	@Override
	public void deleteEmailMessage(EmailMessage email) {
		logger.debug("Deleting email...");
		hunterHibernateHelper.deleteEntity(email); 
		logger.debug("Finished deleting email");
		
	}

	@Override
	public EmailMessage getEmailMessageById(Long id) {
		logger.debug("Fetching email by id (" + id + ")");
		EmailMessage email = hunterHibernateHelper.getEntityById(id, EmailMessage.class);
		logger.debug("Successfully obtained email message >> " + email);
		return email;
	}

	@Override
	public List<EmailMessage> getAllEmailMessages() {
		logger.debug("Fetching all email messages...");
		List<EmailMessage> emails = hunterHibernateHelper.getAllEntities(EmailMessage.class);
		logger.debug("Successfully obtained email messages. Size ( "  + emails.size() + " )");
		return emails;
	}

	@Override
	public void updateEmailMessage(EmailMessage update) {
		logger.debug("Updating email message ... ");
		hunterHibernateHelper.updateEntity(update);
		logger.debug("Finished updating email message"); 
	}

	@Override
	public Long getNextEmailMessageId() {
		logger.debug("Fetching next email message Id..."); 
		Long maxId = hunterHibernateHelper.getMaxEntityIdAsNumber(EmailMessage.class, Long.class, "msgId");
		logger.debug("Max email message Id = " + maxId);
		maxId = maxId == null ? 0  : maxId + 1;
		logger.debug("Next email message Id = " + maxId);
		return maxId;
	}

}
