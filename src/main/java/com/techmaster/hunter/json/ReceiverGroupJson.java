package com.techmaster.hunter.json;


public class ReceiverGroupJson {
	
	private long groupId;
	private String ownerUserName;
	private String firstName;
	private String lastName;
	private String groupName;
	private String groupDesc;
	private int receiverCount;
	private String receiverType;
	private java.util.Date cretDate;
	private String createdBy;
	private java.util.Date lastUpdate;
	private String lastUpdatedBy;
	
	public ReceiverGroupJson() {
		super();
	}
	
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public String getOwnerUserName() {
		return ownerUserName;
	}
	public void setOwnerUserName(String ownerUserName) {
		this.ownerUserName = ownerUserName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupDesc() {
		return groupDesc;
	}
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
	public int getReceiverCount() {
		return receiverCount;
	}
	public void setReceiverCount(int receiverCount) {
		this.receiverCount = receiverCount;
	}
	public java.util.Date getCretDate() {
		return cretDate;
	}
	public void setCretDate(java.util.Date cretDate) {
		this.cretDate = cretDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public java.util.Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(java.util.Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((cretDate == null) ? 0 : cretDate.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((groupDesc == null) ? 0 : groupDesc.hashCode());
		result = prime * result + (int) (groupId ^ (groupId >>> 32));
		result = prime * result
				+ ((groupName == null) ? 0 : groupName.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((lastUpdate == null) ? 0 : lastUpdate.hashCode());
		result = prime * result
				+ ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
		result = prime * result
				+ ((ownerUserName == null) ? 0 : ownerUserName.hashCode());
		result = prime * result + receiverCount;
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
		ReceiverGroupJson other = (ReceiverGroupJson) obj;
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
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (groupDesc == null) {
			if (other.groupDesc != null)
				return false;
		} else if (!groupDesc.equals(other.groupDesc))
			return false;
		if (groupId != other.groupId)
			return false;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
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
		if (ownerUserName == null) {
			if (other.ownerUserName != null)
				return false;
		} else if (!ownerUserName.equals(other.ownerUserName))
			return false;
		if (receiverCount != other.receiverCount)
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
		return "ReceiverGroupJson [groupId=" + groupId + ", ownerUserName="
				+ ownerUserName + ", firstName=" + firstName + ", lastName="
				+ lastName + ", groupName=" + groupName + ", groupDesc="
				+ groupDesc + ", receiverCount=" + receiverCount
				+ ", receiverType=" + receiverType + ", cretDate=" + cretDate
				+ ", createdBy=" + createdBy + ", lastUpdate=" + lastUpdate
				+ ", lastUpdatedBy=" + lastUpdatedBy + "]";
	}
	
	
	
}
