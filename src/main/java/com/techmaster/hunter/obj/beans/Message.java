package com.techmaster.hunter.obj.beans;

import java.util.Date;


public abstract class Message {
	
	private long msgId;
	private String msgDeliveryStatus;
	private String msgLifeStatus;
	private Date msgSendDate;
	private String msgTaskType;
	private String msgText;
	private int desiredReceivers;
	private int actualReceivers;
	private int confirmedReceivers;
	private String msgOwner;
	
	private java.util.Date cretDate;
	private java.util.Date lastUpdate;
	private String createdBy;
	private String lastUpdatedBy;
	
	private ServiceProvider provider;

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public String getMsgDeliveryStatus() {
		return msgDeliveryStatus;
	}

	public void setMsgDeliveryStatus(String msgDeliveryStatus) {
		this.msgDeliveryStatus = msgDeliveryStatus;
	}

	public String getMsgLifeStatus() {
		return msgLifeStatus;
	}

	public void setMsgLifeStatus(String msgLifeStatus) {
		this.msgLifeStatus = msgLifeStatus;
	}

	public Date getMsgSendDate() {
		return msgSendDate;
	}

	public void setMsgSendDate(Date msgSendDate) {
		this.msgSendDate = msgSendDate;
	}

	public String getMsgTaskType() {
		return msgTaskType;
	}

	public void setMsgTaskType(String msgTaskType) {
		this.msgTaskType = msgTaskType;
	}

	public String getMsgText() {
		return msgText;
	}

	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}

	public int getDesiredReceivers() {
		return desiredReceivers;
	}

	public void setDesiredReceivers(int desiredReceivers) {
		this.desiredReceivers = desiredReceivers;
	}

	public int getActualReceivers() {
		return actualReceivers;
	}

	public void setActualReceivers(int actualReceivers) {
		this.actualReceivers = actualReceivers;
	}

	public int getConfirmedReceivers() {
		return confirmedReceivers;
	}

	public void setConfirmedReceivers(int confirmedReceivers) {
		this.confirmedReceivers = confirmedReceivers;
	}

	public String getMsgOwner() {
		return msgOwner;
	}

	public void setMsgOwner(String msgOwner) {
		this.msgOwner = msgOwner;
	}

	public java.util.Date getCretDate() {
		return cretDate;
	}

	public void setCretDate(java.util.Date cretDate) {
		this.cretDate = cretDate;
	}

	public java.util.Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(java.util.Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public ServiceProvider getProvider() {
		return provider;
	}

	public void setProvider(ServiceProvider provider) {
		this.provider = provider;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + actualReceivers;
		result = prime * result + confirmedReceivers;
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((cretDate == null) ? 0 : cretDate.hashCode());
		result = prime * result + desiredReceivers;
		result = prime * result
				+ ((lastUpdate == null) ? 0 : lastUpdate.hashCode());
		result = prime * result
				+ ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
		result = prime
				* result
				+ ((msgDeliveryStatus == null) ? 0 : msgDeliveryStatus
						.hashCode());
		result = prime * result + (int) (msgId ^ (msgId >>> 32));
		result = prime * result
				+ ((msgLifeStatus == null) ? 0 : msgLifeStatus.hashCode());
		result = prime * result
				+ ((msgOwner == null) ? 0 : msgOwner.hashCode());
		result = prime * result
				+ ((msgSendDate == null) ? 0 : msgSendDate.hashCode());
		result = prime * result
				+ ((msgTaskType == null) ? 0 : msgTaskType.hashCode());
		result = prime * result + ((msgText == null) ? 0 : msgText.hashCode());
		result = prime * result
				+ ((provider == null) ? 0 : provider.hashCode());
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
		Message other = (Message) obj;
		if (actualReceivers != other.actualReceivers)
			return false;
		if (confirmedReceivers != other.confirmedReceivers)
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (cretDate == null) {
			if (other.cretDate != null)
				return false;
		} else if (!cretDate.equals(other.cretDate))
			return false;
		if (desiredReceivers != other.desiredReceivers)
			return false;
		if (lastUpdate == null) {
			if (other.lastUpdate != null)
				return false;
		} else if (!lastUpdate.equals(other.lastUpdate))
			return false;
		if (lastUpdatedBy == null) {
			if (other.lastUpdatedBy != null)
				return false;
		} else if (!lastUpdatedBy.equals(other.lastUpdatedBy))
			return false;
		if (msgDeliveryStatus == null) {
			if (other.msgDeliveryStatus != null)
				return false;
		} else if (!msgDeliveryStatus.equals(other.msgDeliveryStatus))
			return false;
		if (msgId != other.msgId)
			return false;
		if (msgLifeStatus == null) {
			if (other.msgLifeStatus != null)
				return false;
		} else if (!msgLifeStatus.equals(other.msgLifeStatus))
			return false;
		if (msgOwner == null) {
			if (other.msgOwner != null)
				return false;
		} else if (!msgOwner.equals(other.msgOwner))
			return false;
		if (msgSendDate == null) {
			if (other.msgSendDate != null)
				return false;
		} else if (!msgSendDate.equals(other.msgSendDate))
			return false;
		if (msgTaskType == null) {
			if (other.msgTaskType != null)
				return false;
		} else if (!msgTaskType.equals(other.msgTaskType))
			return false;
		if (msgText == null) {
			if (other.msgText != null)
				return false;
		} else if (!msgText.equals(other.msgText))
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Message [msgId=" + msgId + ", msgDeliveryStatus="
				+ msgDeliveryStatus + ", msgLifeStatus=" + msgLifeStatus
				+ ", msgSendDate=" + msgSendDate + ", msgTaskType="
				+ msgTaskType + ", msgText=" + msgText + ", desiredReceivers="
				+ desiredReceivers + ", actualReceivers=" + actualReceivers
				+ ", confirmedReceivers=" + confirmedReceivers + ", msgOwner="
				+ msgOwner + ", cretDate=" + cretDate + ", lastUpdate="
				+ lastUpdate + ", createdBy=" + createdBy + ", lastUpdatedBy="
				+ lastUpdatedBy + ", provider=" + provider + "]";
	}

	
	
}
