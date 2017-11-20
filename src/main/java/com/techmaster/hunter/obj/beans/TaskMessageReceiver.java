package com.techmaster.hunter.obj.beans;

public class TaskMessageReceiver {
	
	private long receiverId;
	private String receiverContact;
	private String receiverType;
	private String receiverRegionLevel;
	private boolean blocked;
	private boolean active;
	private boolean random;
	private Long taskId;
	private String countryName;
	private String stateName;
	private String countyName;
	private String consName;
	private String consWardName;
	private String receiverRegionLevelName;
	private AuditInfo auditInfo;
	private int successDeliveryTimes;
	private int failDeliveryTimes;
	
	public long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(long receiverId) {
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
	public String getReceiverRegionLevel() {
		return receiverRegionLevel;
	}
	public void setReceiverRegionLevel(String receiverRegionLevel) {
		this.receiverRegionLevel = receiverRegionLevel;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isRandom() {
		return random;
	}
	public void setRandom(boolean random) {
		this.random = random;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getConsName() {
		return consName;
	}
	public void setConsName(String consName) {
		this.consName = consName;
	}
	public String getConsWardName() {
		return consWardName;
	}
	public void setConsWardName(String consWardName) {
		this.consWardName = consWardName;
	}
	public String getReceiverRegionLevelName() {
		return receiverRegionLevelName;
	}
	public void setReceiverRegionLevelName(String receiverRegionLevelName) {
		this.receiverRegionLevelName = receiverRegionLevelName;
	}
	public AuditInfo getAuditInfo() {
		return auditInfo;
	}
	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}
	public int getSuccessDeliveryTimes() {
		return successDeliveryTimes;
	}
	public void setSuccessDeliveryTimes(int successDeliveryTimes) {
		this.successDeliveryTimes = successDeliveryTimes;
	}
	public int getFailDeliveryTimes() {
		return failDeliveryTimes;
	}
	public void setFailDeliveryTimes(int failDeliveryTimes) {
		this.failDeliveryTimes = failDeliveryTimes;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result
				+ ((auditInfo == null) ? 0 : auditInfo.hashCode());
		result = prime * result + (blocked ? 1231 : 1237);
		result = prime * result
				+ ((consName == null) ? 0 : consName.hashCode());
		result = prime * result
				+ ((consWardName == null) ? 0 : consWardName.hashCode());
		result = prime * result
				+ ((countryName == null) ? 0 : countryName.hashCode());
		result = prime * result
				+ ((countyName == null) ? 0 : countyName.hashCode());
		result = prime * result + failDeliveryTimes;
		result = prime * result + (random ? 1231 : 1237);
		result = prime * result
				+ ((receiverContact == null) ? 0 : receiverContact.hashCode());
		result = prime * result + (int) (receiverId ^ (receiverId >>> 32));
		result = prime
				* result
				+ ((receiverRegionLevel == null) ? 0 : receiverRegionLevel
						.hashCode());
		result = prime
				* result
				+ ((receiverRegionLevelName == null) ? 0
						: receiverRegionLevelName.hashCode());
		result = prime * result
				+ ((receiverType == null) ? 0 : receiverType.hashCode());
		result = prime * result
				+ ((stateName == null) ? 0 : stateName.hashCode());
		result = prime * result + successDeliveryTimes;
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
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
		TaskMessageReceiver other = (TaskMessageReceiver) obj;
		if (active != other.active)
			return false;
		if (auditInfo == null) {
			if (other.auditInfo != null)
				return false;
		} else if (!auditInfo.equals(other.auditInfo))
			return false;
		if (blocked != other.blocked)
			return false;
		if (consName == null) {
			if (other.consName != null)
				return false;
		} else if (!consName.equals(other.consName))
			return false;
		if (consWardName == null) {
			if (other.consWardName != null)
				return false;
		} else if (!consWardName.equals(other.consWardName))
			return false;
		if (countryName == null) {
			if (other.countryName != null)
				return false;
		} else if (!countryName.equals(other.countryName))
			return false;
		if (countyName == null) {
			if (other.countyName != null)
				return false;
		} else if (!countyName.equals(other.countyName))
			return false;
		if (failDeliveryTimes != other.failDeliveryTimes)
			return false;
		if (random != other.random)
			return false;
		if (receiverContact == null) {
			if (other.receiverContact != null)
				return false;
		} else if (!receiverContact.equals(other.receiverContact))
			return false;
		if (receiverId != other.receiverId)
			return false;
		if (receiverRegionLevel == null) {
			if (other.receiverRegionLevel != null)
				return false;
		} else if (!receiverRegionLevel.equals(other.receiverRegionLevel))
			return false;
		if (receiverRegionLevelName == null) {
			if (other.receiverRegionLevelName != null)
				return false;
		} else if (!receiverRegionLevelName
				.equals(other.receiverRegionLevelName))
			return false;
		if (receiverType == null) {
			if (other.receiverType != null)
				return false;
		} else if (!receiverType.equals(other.receiverType))
			return false;
		if (stateName == null) {
			if (other.stateName != null)
				return false;
		} else if (!stateName.equals(other.stateName))
			return false;
		if (successDeliveryTimes != other.successDeliveryTimes)
			return false;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TaskMessageReceiver [receiverId=" + receiverId
				+ ", receiverContact=" + receiverContact + ", receiverType="
				+ receiverType + ", receiverRegionLevel=" + receiverRegionLevel
				+ ", blocked=" + blocked + ", active=" + active + ", random="
				+ random + ", taskId=" + taskId + ", countryName="
				+ countryName + ", stateName=" + stateName + ", countyName="
				+ countyName + ", consName=" + consName + ", consWardName="
				+ consWardName + ", receiverRegionLevelName="
				+ receiverRegionLevelName + ", auditInfo=" + auditInfo
				+ ", successDeliveryTimes=" + successDeliveryTimes
				+ ", failDeliveryTimes=" + failDeliveryTimes + "]";
	}
	
	
	
	
	
	

	

	
	
	
	

}
