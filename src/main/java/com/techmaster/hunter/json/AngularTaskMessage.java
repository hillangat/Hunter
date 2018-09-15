package com.techmaster.hunter.json;

import java.io.Serializable;
import java.util.Arrays;

import com.techmaster.hunter.obj.beans.MessageAttachmentMetadata;
import com.techmaster.hunter.obj.beans.ServiceProvider;

public class AngularTaskMessage implements Serializable{
	
	private static final long serialVersionUID = -1884354933063593622L;

	private Long msgId;
	private String msgDeliveryStatus;
	private String msgLifeStatus;
	private String msgSendDate;
	private String msgTaskType;
	private String msgText;
	private String desiredReceivers;
	private String actualReceivers;
	private String confirmedReceivers;
	private String msgOwner;
	private String cretDate;
	private String lastUpdate;
	private String createdBy;
	private String lastUpdatedBy;
	private ServiceProvider provider;
	private String eSubject;
	private String eBody;
	private String eFooter;
	private String toList;
	private String eFrom;
	private String ccList;
	private boolean multiPart;
	private boolean hasAttachment;
	private String attchmntBnId;
	private String attchmtntFileType;
	private String contentType;
	private String cssObject;
	private String replyTo;
	private String priority;
	private String fromPhone;
	private String disclaimer;
	private String pageable;
	private String toPhone;
	private String emailTemplateName;
	private String messageAttachments;
	private MessageAttachmentMetadata[] messageAttachmentMetadata;
	

	public Long getMsgId() {
		return msgId;
	}
	public void setMsgId(Long msgId) {
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
	public String getMsgSendDate() {
		return msgSendDate;
	}
	public void setMsgSendDate(String msgSendDate) {
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
	public String getDesiredReceivers() {
		return desiredReceivers;
	}
	public void setDesiredReceivers(String desiredReceivers) {
		this.desiredReceivers = desiredReceivers;
	}
	public String getActualReceivers() {
		return actualReceivers;
	}
	public void setActualReceivers(String actualReceivers) {
		this.actualReceivers = actualReceivers;
	}
	public String getConfirmedReceivers() {
		return confirmedReceivers;
	}
	public void setConfirmedReceivers(String confirmedReceivers) {
		this.confirmedReceivers = confirmedReceivers;
	}
	public String getMsgOwner() {
		return msgOwner;
	}
	public void setMsgOwner(String msgOwner) {
		this.msgOwner = msgOwner;
	}
	public String getCretDate() {
		return cretDate;
	}
	public void setCretDate(String cretDate) {
		this.cretDate = cretDate;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
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
	public String geteSubject() {
		return eSubject;
	}
	public void seteSubject(String eSubject) {
		this.eSubject = eSubject;
	}
	public String geteBody() {
		return eBody;
	}
	public void seteBody(String eBody) {
		this.eBody = eBody;
	}
	public String geteFooter() {
		return eFooter;
	}
	public void seteFooter(String eFooter) {
		this.eFooter = eFooter;
	}
	public String getToList() {
		return toList;
	}
	public void setToList(String toList) {
		this.toList = toList;
	}
	public String geteFrom() {
		return eFrom;
	}
	public void seteFrom(String eFrom) {
		this.eFrom = eFrom;
	}
	public String getCcList() {
		return ccList;
	}
	public void setCcList(String ccList) {
		this.ccList = ccList;
	}
	public boolean isMultiPart() {
		return multiPart;
	}
	public void setMultiPart(boolean multiPart) {
		this.multiPart = multiPart;
	}
	public boolean isHasAttachment() {
		return hasAttachment;
	}
	public void setHasAttachment(boolean hasAttachment) {
		this.hasAttachment = hasAttachment;
	}
	public String getAttchmntBnId() {
		return attchmntBnId;
	}
	public void setAttchmntBnId(String attchmntBnId) {
		this.attchmntBnId = attchmntBnId;
	}
	public String getAttchmtntFileType() {
		return attchmtntFileType;
	}
	public void setAttchmtntFileType(String attchmtntFileType) {
		this.attchmtntFileType = attchmtntFileType;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getCssObject() {
		return cssObject;
	}
	public void setCssObject(String cssObject) {
		this.cssObject = cssObject;
	}
	public String getReplyTo() {
		return replyTo;
	}
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getFromPhone() {
		return fromPhone;
	}
	public void setFromPhone(String fromPhone) {
		this.fromPhone = fromPhone;
	}
	public String getDisclaimer() {
		return disclaimer;
	}
	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}
	public String getPageable() {
		return pageable;
	}
	public void setPageable(String pageable) {
		this.pageable = pageable;
	}
	public String getToPhone() {
		return toPhone;
	}
	public void setToPhone(String toPhone) {
		this.toPhone = toPhone;
	}
	public String getEmailTemplateName() {
		return emailTemplateName;
	}
	public void setEmailTemplateName(String emailTemplateName) {
		this.emailTemplateName = emailTemplateName;
	}
	public String getMessageAttachments() {
		return messageAttachments;
	}
	public void setMessageAttachments(String messageAttachments) {
		this.messageAttachments = messageAttachments;
	}
	public MessageAttachmentMetadata[] getMessageAttachmentMetadata() {
		return messageAttachmentMetadata;
	}
	public void setMessageAttachmentMetadata(MessageAttachmentMetadata[] messageAttachmentMetadata) {
		this.messageAttachmentMetadata = messageAttachmentMetadata;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualReceivers == null) ? 0 : actualReceivers.hashCode());
		result = prime * result + ((attchmntBnId == null) ? 0 : attchmntBnId.hashCode());
		result = prime * result + ((attchmtntFileType == null) ? 0 : attchmtntFileType.hashCode());
		result = prime * result + ((ccList == null) ? 0 : ccList.hashCode());
		result = prime * result + ((confirmedReceivers == null) ? 0 : confirmedReceivers.hashCode());
		result = prime * result + ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((cretDate == null) ? 0 : cretDate.hashCode());
		result = prime * result + ((cssObject == null) ? 0 : cssObject.hashCode());
		result = prime * result + ((desiredReceivers == null) ? 0 : desiredReceivers.hashCode());
		result = prime * result + ((disclaimer == null) ? 0 : disclaimer.hashCode());
		result = prime * result + ((eBody == null) ? 0 : eBody.hashCode());
		result = prime * result + ((eFooter == null) ? 0 : eFooter.hashCode());
		result = prime * result + ((eFrom == null) ? 0 : eFrom.hashCode());
		result = prime * result + ((eSubject == null) ? 0 : eSubject.hashCode());
		result = prime * result + ((emailTemplateName == null) ? 0 : emailTemplateName.hashCode());
		result = prime * result + ((fromPhone == null) ? 0 : fromPhone.hashCode());
		result = prime * result + (hasAttachment ? 1231 : 1237);
		result = prime * result + ((lastUpdate == null) ? 0 : lastUpdate.hashCode());
		result = prime * result + ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
		result = prime * result + Arrays.hashCode(messageAttachmentMetadata);
		result = prime * result + ((messageAttachments == null) ? 0 : messageAttachments.hashCode());
		result = prime * result + ((msgDeliveryStatus == null) ? 0 : msgDeliveryStatus.hashCode());
		result = prime * result + ((msgId == null) ? 0 : msgId.hashCode());
		result = prime * result + ((msgLifeStatus == null) ? 0 : msgLifeStatus.hashCode());
		result = prime * result + ((msgOwner == null) ? 0 : msgOwner.hashCode());
		result = prime * result + ((msgSendDate == null) ? 0 : msgSendDate.hashCode());
		result = prime * result + ((msgTaskType == null) ? 0 : msgTaskType.hashCode());
		result = prime * result + ((msgText == null) ? 0 : msgText.hashCode());
		result = prime * result + (multiPart ? 1231 : 1237);
		result = prime * result + ((pageable == null) ? 0 : pageable.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((provider == null) ? 0 : provider.hashCode());
		result = prime * result + ((replyTo == null) ? 0 : replyTo.hashCode());
		result = prime * result + ((toList == null) ? 0 : toList.hashCode());
		result = prime * result + ((toPhone == null) ? 0 : toPhone.hashCode());
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
		AngularTaskMessage other = (AngularTaskMessage) obj;
		if (actualReceivers == null) {
			if (other.actualReceivers != null)
				return false;
		} else if (!actualReceivers.equals(other.actualReceivers))
			return false;
		if (attchmntBnId == null) {
			if (other.attchmntBnId != null)
				return false;
		} else if (!attchmntBnId.equals(other.attchmntBnId))
			return false;
		if (attchmtntFileType == null) {
			if (other.attchmtntFileType != null)
				return false;
		} else if (!attchmtntFileType.equals(other.attchmtntFileType))
			return false;
		if (ccList == null) {
			if (other.ccList != null)
				return false;
		} else if (!ccList.equals(other.ccList))
			return false;
		if (confirmedReceivers == null) {
			if (other.confirmedReceivers != null)
				return false;
		} else if (!confirmedReceivers.equals(other.confirmedReceivers))
			return false;
		if (contentType == null) {
			if (other.contentType != null)
				return false;
		} else if (!contentType.equals(other.contentType))
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
		if (cssObject == null) {
			if (other.cssObject != null)
				return false;
		} else if (!cssObject.equals(other.cssObject))
			return false;
		if (desiredReceivers == null) {
			if (other.desiredReceivers != null)
				return false;
		} else if (!desiredReceivers.equals(other.desiredReceivers))
			return false;
		if (disclaimer == null) {
			if (other.disclaimer != null)
				return false;
		} else if (!disclaimer.equals(other.disclaimer))
			return false;
		if (eBody == null) {
			if (other.eBody != null)
				return false;
		} else if (!eBody.equals(other.eBody))
			return false;
		if (eFooter == null) {
			if (other.eFooter != null)
				return false;
		} else if (!eFooter.equals(other.eFooter))
			return false;
		if (eFrom == null) {
			if (other.eFrom != null)
				return false;
		} else if (!eFrom.equals(other.eFrom))
			return false;
		if (eSubject == null) {
			if (other.eSubject != null)
				return false;
		} else if (!eSubject.equals(other.eSubject))
			return false;
		if (emailTemplateName == null) {
			if (other.emailTemplateName != null)
				return false;
		} else if (!emailTemplateName.equals(other.emailTemplateName))
			return false;
		if (fromPhone == null) {
			if (other.fromPhone != null)
				return false;
		} else if (!fromPhone.equals(other.fromPhone))
			return false;
		if (hasAttachment != other.hasAttachment)
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
		if (!Arrays.equals(messageAttachmentMetadata, other.messageAttachmentMetadata))
			return false;
		if (messageAttachments == null) {
			if (other.messageAttachments != null)
				return false;
		} else if (!messageAttachments.equals(other.messageAttachments))
			return false;
		if (msgDeliveryStatus == null) {
			if (other.msgDeliveryStatus != null)
				return false;
		} else if (!msgDeliveryStatus.equals(other.msgDeliveryStatus))
			return false;
		if (msgId == null) {
			if (other.msgId != null)
				return false;
		} else if (!msgId.equals(other.msgId))
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
		if (multiPart != other.multiPart)
			return false;
		if (pageable == null) {
			if (other.pageable != null)
				return false;
		} else if (!pageable.equals(other.pageable))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		if (replyTo == null) {
			if (other.replyTo != null)
				return false;
		} else if (!replyTo.equals(other.replyTo))
			return false;
		if (toList == null) {
			if (other.toList != null)
				return false;
		} else if (!toList.equals(other.toList))
			return false;
		if (toPhone == null) {
			if (other.toPhone != null)
				return false;
		} else if (!toPhone.equals(other.toPhone))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AngularTaskMessage [msgId=" + msgId + ", msgDeliveryStatus=" + msgDeliveryStatus + ", msgLifeStatus="
				+ msgLifeStatus + ", msgSendDate=" + msgSendDate + ", msgTaskType=" + msgTaskType + ", msgText="
				+ msgText + ", desiredReceivers=" + desiredReceivers + ", actualReceivers=" + actualReceivers
				+ ", confirmedReceivers=" + confirmedReceivers + ", msgOwner=" + msgOwner + ", cretDate=" + cretDate
				+ ", lastUpdate=" + lastUpdate + ", createdBy=" + createdBy + ", lastUpdatedBy=" + lastUpdatedBy
				+ ", provider=" + provider + ", eSubject=" + eSubject + ", eBody=" + eBody + ", eFooter=" + eFooter
				+ ", toList=" + toList + ", eFrom=" + eFrom + ", ccList=" + ccList + ", multiPart=" + multiPart
				+ ", hasAttachment=" + hasAttachment + ", attchmntBnId=" + attchmntBnId + ", attchmtntFileType="
				+ attchmtntFileType + ", contentType=" + contentType + ", cssObject=" + cssObject + ", replyTo="
				+ replyTo + ", priority=" + priority + ", fromPhone=" + fromPhone + ", disclaimer=" + disclaimer
				+ ", pageable=" + pageable + ", toPhone=" + toPhone + ", emailTemplateName=" + emailTemplateName
				+ ", messageAttachments=" + messageAttachments + ", messageAttachmentMetadata="
				+ Arrays.toString(messageAttachmentMetadata) + "]";
	}

	
	
	
}
