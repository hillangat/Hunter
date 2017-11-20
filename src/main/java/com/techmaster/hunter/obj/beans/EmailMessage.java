package com.techmaster.hunter.obj.beans;

import java.util.HashSet;
import java.util.Set;

import com.techmaster.hunter.util.HunterUtility;



public class EmailMessage extends Message {
	
	private String eSubject;
	private String eBody;
	private String eFooter;
	private String toList;
	private String eFrom;
	private String ccList;
	private boolean multiPart;
	private boolean hasAttachment;
	private Long attchmntBnId;
	private String attchmtntFileType;
	private String contentType;
	private String cssObject;
	private String replyTo = null;
	private int priority = 0;
	private String emailTemplateName;
	private String messageAttachments;// format path||key,path||key
	private Set<MessageAttachmentMetadata> messageAttachmentMetadata = new HashSet<MessageAttachmentMetadata>();
	
		
	public EmailMessage() {
		super();
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
	public Long getAttchmntBnId() {
		return attchmntBnId;
	}
	public void setAttchmntBnId(Long attchmntBnId) {
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
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
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
	public Set<MessageAttachmentMetadata> getMessageAttachmentMetadata() {
		return messageAttachmentMetadata;
	}
	public void setMessageAttachmentMetadata(
			Set<MessageAttachmentMetadata> messageAttachmentMetadata) {
		this.messageAttachmentMetadata = messageAttachmentMetadata;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((attchmntBnId == null) ? 0 : attchmntBnId.hashCode());
		result = prime
				* result
				+ ((attchmtntFileType == null) ? 0 : attchmtntFileType
						.hashCode());
		result = prime * result + ((ccList == null) ? 0 : ccList.hashCode());
		result = prime * result
				+ ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result
				+ ((cssObject == null) ? 0 : cssObject.hashCode());
		result = prime * result + ((eBody == null) ? 0 : eBody.hashCode());
		result = prime * result + ((eFooter == null) ? 0 : eFooter.hashCode());
		result = prime * result + ((eFrom == null) ? 0 : eFrom.hashCode());
		result = prime * result
				+ ((eSubject == null) ? 0 : eSubject.hashCode());
		result = prime
				* result
				+ ((emailTemplateName == null) ? 0 : emailTemplateName
						.hashCode());
		result = prime * result + (hasAttachment ? 1231 : 1237);
		result = prime
				* result
				+ ((messageAttachments == null) ? 0 : messageAttachments
						.hashCode());
		result = prime * result + (multiPart ? 1231 : 1237);
		result = prime * result + priority;
		result = prime * result + ((replyTo == null) ? 0 : replyTo.hashCode());
		result = prime * result + ((toList == null) ? 0 : toList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmailMessage other = (EmailMessage) obj;
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
		if (contentType == null) {
			if (other.contentType != null)
				return false;
		} else if (!contentType.equals(other.contentType))
			return false;
		if (cssObject == null) {
			if (other.cssObject != null)
				return false;
		} else if (!cssObject.equals(other.cssObject))
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
		if (hasAttachment != other.hasAttachment)
			return false;
		if (messageAttachments == null) {
			if (other.messageAttachments != null)
				return false;
		} else if (!messageAttachments.equals(other.messageAttachments))
			return false;
		if (multiPart != other.multiPart)
			return false;
		if (priority != other.priority)
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
		return true;
	}

	@Override
	public String toString() {
		return "EmailMessage [eSubject=" + eSubject + ", eBody=" + eBody
				+ ", eFooter=" + eFooter + ", toList=" + toList + ", eFrom="
				+ eFrom + ", ccList=" + ccList + ", multiPart=" + multiPart
				+ ", hasAttachment=" + hasAttachment + ", attchmntBnId="
				+ attchmntBnId + ", attchmtntFileType=" + attchmtntFileType
				+ ", contentType=" + contentType + ", cssObject=" + cssObject
				+ ", replyTo=" + replyTo + ", priority=" + priority
				+ ", emailTemplateName=" + emailTemplateName
				+ ", messageAttachments=" + messageAttachments
				+ ", messageAttachmentMetadata=" + HunterUtility.stringifySet(messageAttachmentMetadata) + "]"; 
	}

	
	
	

	

	
		
	
}
