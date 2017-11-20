package com.techmaster.hunter.obj.beans;

public class ReceiverGroupReceiver {
	
	private Long receiverId;
	private String receiverContact;
	private String receiverType;
	private boolean active;
	private boolean approved;
	private String approver;
	private AuditInfo auditInfo;
	
	public ReceiverGroupReceiver() {
		super();
	}
	
	public Long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}
	public String getReceiverContact() {
		return receiverContact;
	}
	public void setReceiverContact(String receiverContact) {
		this.receiverContact = receiverContact;
	}
	public String getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public AuditInfo getAuditInfo() {
		return auditInfo;
	}
	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + (approved ? 1231 : 1237);
		result = prime * result
				+ ((approver == null) ? 0 : approver.hashCode());
		result = prime * result
				+ ((auditInfo == null) ? 0 : auditInfo.hashCode());
		result = prime * result
				+ ((receiverContact == null) ? 0 : receiverContact.hashCode());
		result = prime * result
				+ ((receiverId == null) ? 0 : receiverId.hashCode());
		result = prime * result
				+ ((receiverType == null) ? 0 : receiverType.hashCode());
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
		ReceiverGroupReceiver other = (ReceiverGroupReceiver) obj;
		if (active != other.active)
			return false;
		if (approved != other.approved)
			return false;
		if (approver == null) {
			if (other.approver != null)
				return false;
		} else if (!approver.equals(other.approver))
			return false;
		if (auditInfo == null) {
			if (other.auditInfo != null)
				return false;
		} else if (!auditInfo.equals(other.auditInfo))
			return false;
		if (receiverContact == null) {
			if (other.receiverContact != null)
				return false;
		} else if (!receiverContact.equals(other.receiverContact))
			return false;
		if (receiverId == null) {
			if (other.receiverId != null)
				return false;
		} else if (!receiverId.equals(other.receiverId))
			return false;
		if (receiverType == null) {
			if (other.receiverType != null)
				return false;
		} else if (!receiverType.equals(other.receiverType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReceiverGroupReceiver [receiverId=" + receiverId
				+ ", receiverContact=" + receiverContact + ", receiverType="
				+ receiverType + ", active=" + active + ", approved="
				+ approved + ", approver=" + approver + ", auditInfo="
				+ auditInfo + "]";
	}
	
	
	
	
	
	
	

}
