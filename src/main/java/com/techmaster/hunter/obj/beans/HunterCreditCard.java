package com.techmaster.hunter.obj.beans;

import java.util.Date;

public class HunterCreditCard {

	private long cardId;
	private String nameOnCard;
	private String securityNumber;
	private String cardNumber;
	private Date expirationDate;
	private boolean selForBilling;
	private boolean valid;
	private String cardType;
	private Long userId;
	
	public HunterCreditCard() {
		super();
	}

	public long getCardId() {
		return cardId;
	}

	public void setCardId(long cardId) {
		this.cardId = cardId;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getSecurityNumber() {
		return securityNumber;
	}

	public void setSecurityNumber(String securityNumber) {
		this.securityNumber = securityNumber;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public boolean isSelForBilling() {
		return selForBilling;
	}

	public void setSelForBilling(boolean selForBilling) {
		this.selForBilling = selForBilling;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (cardId ^ (cardId >>> 32));
		result = prime * result
				+ ((cardNumber == null) ? 0 : cardNumber.hashCode());
		result = prime * result
				+ ((cardType == null) ? 0 : cardType.hashCode());
		result = prime * result
				+ ((expirationDate == null) ? 0 : expirationDate.hashCode());
		result = prime * result
				+ ((nameOnCard == null) ? 0 : nameOnCard.hashCode());
		result = prime * result
				+ ((securityNumber == null) ? 0 : securityNumber.hashCode());
		result = prime * result + (selForBilling ? 1231 : 1237);
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + (valid ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HunterCreditCard other = (HunterCreditCard) obj;
		if (cardId != other.cardId)
			return false;
		if (cardNumber == null) {
			if (other.cardNumber != null)
				return false;
		} else if (!cardNumber.equals(other.cardNumber))
			return false;
		if (cardType == null) {
			if (other.cardType != null)
				return false;
		} else if (!cardType.equals(other.cardType))
			return false;
		if (expirationDate == null) {
			if (other.expirationDate != null)
				return false;
		} else if (!expirationDate.equals(other.expirationDate))
			return false;
		if (nameOnCard == null) {
			if (other.nameOnCard != null)
				return false;
		} else if (!nameOnCard.equals(other.nameOnCard))
			return false;
		if (securityNumber == null) {
			if (other.securityNumber != null)
				return false;
		} else if (!securityNumber.equals(other.securityNumber))
			return false;
		if (selForBilling != other.selForBilling)
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (valid != other.valid)
			return false;
		return true;
	}

	
	
	@Override
	public String toString() {
		return "HunterCreditCard [cardId=" + cardId + ", nameOnCard="
				+ nameOnCard + ", securityNumber=" + securityNumber
				+ ", cardNumber=" + cardNumber + ", expirationDate="
				+ expirationDate + ", selForBilling=" + selForBilling
				+ ", valid=" + valid + ", cardType=" + cardType + ", userId="
				+ userId + "]";
	}

	
		
		
	
	
	
	
	
}
