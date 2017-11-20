package com.techmaster.hunter.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.dao.types.HunterCreditCardDao;
import com.techmaster.hunter.obj.beans.HunterCreditCard;
import com.techmaster.hunter.util.HunterHibernateHelper;

public class HunterCreditCardDaoImpl implements HunterCreditCardDao{
	
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired private HunterHibernateHelper hunterHibernateHelper;

	@Override
	public void insertCreditCard(HunterCreditCard creditCard) {
		logger.debug("Creating credit card ... " );
		ArrayList<HunterCreditCard> creditCards = new ArrayList<>();
		creditCards.add(creditCard);
		checkAndSetId(creditCards); 
		hunterHibernateHelper.saveEntity(creditCard);
		logger.debug("Done creating credit card!"); 
	}

	@Override
	public HunterCreditCard getCreditCardById(Long id) {
		logger.debug("Retrieving credit card for it : "+ id);
		HunterCreditCard creditCard = hunterHibernateHelper.getEntityById(id, HunterCreditCard.class);
		logger.debug("Successfully obtained credit card!!");
		return creditCard;
	}

	@Override
	public void deleteCreditCard(HunterCreditCard creditCard) {
		logger.debug("Deleting credit card of id : " + creditCard);
		hunterHibernateHelper.deleteEntity(creditCard);
		logger.debug("Successfully deleted credit card!!"); 
	}

	@Override
	public List<HunterCreditCard> getAllCreditCards() {
		logger.debug("Getting all credit cards..."); 
		List<HunterCreditCard> allCreditCards = hunterHibernateHelper.getAllEntities(HunterCreditCard.class);
		logger.debug("Done retrieving all credit cards. Size ( " + allCreditCards.size() + " )");  
		return allCreditCards;
	}

	@Override
	public void deleteCreditCardById(Long id) {
		logger.debug("Loading creadit card for deletion : " + id);
		HunterCreditCard creditCard = hunterHibernateHelper.getEntityById(id, HunterCreditCard.class);
		logger.debug("Successfully loaded credit card for deletion!");
		deleteCreditCard(creditCard); 
	}

	@Override
	public void updateCreditCard(HunterCreditCard update) {
		logger.debug("Updating credit card of id " + update.getCardId());
		hunterHibernateHelper.updateEntity(update); 
		logger.debug("Done updating credit card!!"); 		
	}

	@Override
	public void insertCreditCards(List<HunterCreditCard> creditCards) {
		logger.debug("Inserting credit cards. Size ( " + creditCards + " )"); 
		checkAndSetId(creditCards); 
		hunterHibernateHelper.saveEntities(creditCards);
		logger.debug("Successfully inserted credit cards.");
	}

	@Override
	public Long getNextCreditCardId() {
		Long maxId = hunterHibernateHelper.getMaxEntityIdAsNumber(HunterCreditCard.class, Long.class, "cardId");
		maxId = maxId == null ? 0 : maxId;
		maxId++;
		logger.debug("Obtained next hunter credit card id >> " + maxId); 
		return maxId;
	}
	
	private void checkAndSetId(List<HunterCreditCard> creditCards){
		long nexId = getNextCreditCardId();
		if(creditCards != null && !creditCards.isEmpty()){
			for(HunterCreditCard creditCard : creditCards){
				if(creditCard.getCardId() == 0){
					creditCard.setCardId(nexId);
					nexId++;
				}
			}
		}
	}


}
