package com.techmaster.hunter.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.dao.types.ReceiverGroupReceiverDao;
import com.techmaster.hunter.obj.beans.ReceiverGroupReceiver;
import com.techmaster.hunter.util.HunterHibernateHelper;

public class ReceiverGroupReceiverDaoImpl implements ReceiverGroupReceiverDao {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired private HunterHibernateHelper hunterHibernateHelper;

	@Override
	public void insertReceiverGroupReceiver(ReceiverGroupReceiver receiverGroupReceiver) {
		logger.debug("inserting receiver group receiver..."); 
		hunterHibernateHelper.saveEntity(receiverGroupReceiver);
		logger.debug("Done inserting receiver group receivers!"); 
	}

	@Override
	public void insertReceiverGroupReceivers(List<ReceiverGroupReceiver> receiverGroupReceivers) {
		logger.debug("inserting receiver group receivers..."); 
		hunterHibernateHelper.saveEntities(receiverGroupReceivers);
		logger.debug("Done inserting receiver group receivers!");
	}

	@Override
	public ReceiverGroupReceiver getReceiverById(Long receiverId) {
		logger.debug("Getting receiver group receiver by id ( " + receiverId + " )"); 
		ReceiverGroupReceiver receiverGroupReceiver =  hunterHibernateHelper.getEntityById(receiverId, ReceiverGroupReceiver.class);
		logger.debug("Done getting receiver group receiver");
		return receiverGroupReceiver;
	}

	@Override
	public List<ReceiverGroupReceiver> getReceiversForGroupId(Long groupId) {
		throw new NullPointerException("Not yet implemented!!"); 
	}

	@Override
	public void deleteReceiverById(Long receiverId) {
		logger.debug("Deleting receiver group receiver by id ( " + receiverId + " )"); 
		ReceiverGroupReceiver receiver = getReceiverById(receiverId);
		if(receiver == null){
			logger.debug("No receiver group receiver of the id ( " + receiverId + " ) . Returning...");  
			return;
		}else{
			logger.debug("Deleting receiver group : " + receiver); 
		}
		deleteReceiver(receiver); 
		logger.debug("Done getting receiver group receiver");
	}

	@Override
	public void deleteReceiver(ReceiverGroupReceiver receiverGroupReceiver) {
		logger.debug("Deleting receiver group receiver : " + receiverGroupReceiver);
		hunterHibernateHelper.deleteEntity(receiverGroupReceiver); 
		logger.debug("Successfully deleted receiver group receiver!" );
	}

	@Override
	public void updateReceiverGroup(ReceiverGroupReceiver receiverGroupReceiver) {
		logger.debug("Updating receiver group receiver : " + receiverGroupReceiver);		 
		hunterHibernateHelper.updateEntity(receiverGroupReceiver);
		logger.debug("Done updating receiver group : " + receiverGroupReceiver); 
	}

}
