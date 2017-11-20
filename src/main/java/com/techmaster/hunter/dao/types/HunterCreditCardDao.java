package com.techmaster.hunter.dao.types;

import java.util.List;

import com.techmaster.hunter.obj.beans.HunterCreditCard;

public interface HunterCreditCardDao {
	
	public void insertCreditCard(HunterCreditCard creditCard);
	public HunterCreditCard getCreditCardById(Long id);
	public void deleteCreditCard(HunterCreditCard creditCard);
	public List<HunterCreditCard> getAllCreditCards();
	public void deleteCreditCardById(Long id);
	public void updateCreditCard(HunterCreditCard update);
	public void insertCreditCards(List<HunterCreditCard> creditCards);
	public Long getNextCreditCardId();

}
