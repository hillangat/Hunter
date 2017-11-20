package com.techmaster.hunter.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.dao.types.EmailAttachmentMetadataDao;
import com.techmaster.hunter.obj.beans.MessageAttachmentMetadata;
import com.techmaster.hunter.util.HunterHibernateHelper;

public class EmailAttachmentMetadataDaoImpl implements EmailAttachmentMetadataDao{

	private Logger logger = Logger.getLogger(getClass());
	@Autowired private HunterHibernateHelper hunterHibernateHelper;
	
	@Override
	public void insertEmailAttachmentMetadata(MessageAttachmentMetadata attachmentMetadata) {
		logger.debug("Inserting attachmentMetadata...");
		hunterHibernateHelper.saveEntity(attachmentMetadata); 
		logger.debug("Finished inserting attachmentMetadata");		
	}
	
	@Override
	public void insertEmailAttachmentMetadatas(List<MessageAttachmentMetadata> attachmentMetadatas) {
		logger.debug("Inserting attachmentMetadatas...");
		hunterHibernateHelper.saveEntities(attachmentMetadatas);  
		logger.debug("Finished inserting attachmentMetadatas");		
	}

	@Override
	public void deleteEmailAttachmentMetadata(MessageAttachmentMetadata attachmentMetadata) {
		logger.debug("Deleting attachmentMetadata  :  " + attachmentMetadata);
		hunterHibernateHelper.deleteEntity(attachmentMetadata);  
		logger.debug("Finished deleting attachmentMetadata");	
	}

	@Override
	public void deleteEmailAttachmentMetadataById(Long attachmentMetadataId) {
		logger.debug("Deleting attachmentMetadata id : " + attachmentMetadataId);
		String readyQuery = "DELETE EmailAttachmentMetadata WHERE id = " + attachmentMetadataId;
		hunterHibernateHelper.executeVoidTransctnlReadyQuery(readyQuery); 
		logger.debug("Successfully deleted attachmentMetadata");		
	}

	@Override
	public void updateEmailAttachmentMetadata(MessageAttachmentMetadata attachmentMetadata) {
		logger.debug("Updating email attachment metadata...");
		hunterHibernateHelper.updateEntity(attachmentMetadata);
		logger.debug("Successfully updated attachmentMetadata");			
	}

	@Override
	public MessageAttachmentMetadata getEmailAttachmentMetadataById(Long attachmentMetadataId) {
		logger.debug("Fetching attachmentMetadata id ( " + attachmentMetadataId + " )");
		MessageAttachmentMetadata  attachmentMetadata = hunterHibernateHelper.getEntityById(attachmentMetadataId, MessageAttachmentMetadata.class);
		logger.debug("Finished fetching attachment metadata : " + attachmentMetadata); 
		return attachmentMetadata;
	}

	@Override
	public List<MessageAttachmentMetadata> getAllEmailAttachmentMetadata() {
		logger.debug("Retrieving all EmailAttachmentMetadatas...");
		List<MessageAttachmentMetadata> attachmentMetadatas = hunterHibernateHelper.getAllEntities(MessageAttachmentMetadata.class);
		logger.debug("Finished retrieving attachmentMetadatas. Size ( "+ ( attachmentMetadatas == null ? 0 : attachmentMetadatas.size() ) +" )"  ); 
		return attachmentMetadatas;
	}

	@Override
	public List<MessageAttachmentMetadata> getAllEmailAttachmentMetadataForMsg(Long msgId) {
		String readyQuery = "FROM EmailAttachmentMetadata e WHERE e.msgId=" + msgId;
		List<MessageAttachmentMetadata> attachmentMetadatas= hunterHibernateHelper.executeQueryForObjList(MessageAttachmentMetadata.class, readyQuery);
		return attachmentMetadatas;
	}

}
