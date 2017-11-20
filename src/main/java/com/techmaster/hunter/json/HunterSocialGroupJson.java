package com.techmaster.hunter.json;



public class HunterSocialGroupJson {
	
	private Long groupId;
	private String externalId;
	private String groupName;
	private String groupDescription;
	private String socialType;
	private boolean hunterOwned;
	private boolean acquired;
	private String acquiredFromFullName;
	private int receiversCount;
	private String clientUserName;
	private String hunterGroupAdmin;
	private boolean existent;
	private boolean verified;
	private String verifiedBy;
	private String verifiedDate;
	private boolean active;
	private boolean suspended;
	private String status;
	private String suspensionDescription;
	
	private Long regionId;
	private String regionName;
	private int regionPopulation;
	private String regionDesc;
	private String regionCountryName;
	private String regionCountyName;
	private String regionConsName;
	private String regionWardName;
	private String regionCoordinates;
	private String regionAssignedTo;
	
	private Long socialAppId;
	private String socialAppName;
	
	private String cretDate;
	private String createdBy;
	private String lastUpdate;
	private String lastUpdatedBy;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupDescription() {
		return groupDescription;
	}
	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}
	public String getSocialType() {
		return socialType;
	}
	public void setSocialType(String socialType) {
		this.socialType = socialType;
	}
	public boolean isHunterOwned() {
		return hunterOwned;
	}
	public void setHunterOwned(boolean hunterOwned) {
		this.hunterOwned = hunterOwned;
	}
	public boolean isAcquired() {
		return acquired;
	}
	public void setAcquired(boolean acquired) {
		this.acquired = acquired;
	}
	public String getAcquiredFromFullName() {
		return acquiredFromFullName;
	}
	public void setAcquiredFromFullName(String acquiredFromFullName) {
		this.acquiredFromFullName = acquiredFromFullName;
	}
	public int getReceiversCount() {
		return receiversCount;
	}
	public void setReceiversCount(int receiversCount) {
		this.receiversCount = receiversCount;
	}
	public String getClientUserName() {
		return clientUserName;
	}
	public void setClientUserName(String clientUserName) {
		this.clientUserName = clientUserName;
	}
	public String getHunterGroupAdmin() {
		return hunterGroupAdmin;
	}
	public void setHunterGroupAdmin(String hunterGroupAdmin) {
		this.hunterGroupAdmin = hunterGroupAdmin;
	}
	public boolean isExistent() {
		return existent;
	}
	public void setExistent(boolean existent) {
		this.existent = existent;
	}
	public String getVerifiedBy() {
		return verifiedBy;
	}
	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}
	public String getVerifiedDate() {
		return verifiedDate;
	}
	public void setVerifiedDate(String verifiedDate) {
		this.verifiedDate = verifiedDate;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isSuspended() {
		return suspended;
	}
	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}
	public String getSuspensionDescription() {
		return suspensionDescription;
	}
	public void setSuspensionDescription(String suspensionDescription) {
		this.suspensionDescription = suspensionDescription;
	}
	public Long getRegionId() {
		return regionId;
	}
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public int getRegionPopulation() {
		return regionPopulation;
	}
	public void setRegionPopulation(int regionPopulation) {
		this.regionPopulation = regionPopulation;
	}
	public String getRegionDesc() {
		return regionDesc;
	}
	public void setRegionDesc(String regionDesc) {
		this.regionDesc = regionDesc;
	}
	public String getRegionCountryName() {
		return regionCountryName;
	}
	public void setRegionCountryName(String regionCountryName) {
		this.regionCountryName = regionCountryName;
	}
	public String getRegionCountyName() {
		return regionCountyName;
	}
	public void setRegionCountyName(String regionCountyName) {
		this.regionCountyName = regionCountyName;
	}
	public String getRegionConsName() {
		return regionConsName;
	}
	public void setRegionConsName(String regionConsName) {
		this.regionConsName = regionConsName;
	}
	public String getRegionWardName() {
		return regionWardName;
	}
	public void setRegionWardName(String regionWardName) {
		this.regionWardName = regionWardName;
	}
	public String getRegionCoordinates() {
		return regionCoordinates;
	}
	public void setRegionCoordinates(String regionCoordinates) {
		this.regionCoordinates = regionCoordinates;
	}
	public String getRegionAssignedTo() {
		return regionAssignedTo;
	}
	public void setRegionAssignedTo(String regionAssignedTo) {
		this.regionAssignedTo = regionAssignedTo;
	}
	public String getCretDate() {
		return cretDate;
	}
	public void setCretDate(String cretDate) {
		this.cretDate = cretDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public Long getSocialAppId() {
		return socialAppId;
	}
	public void setSocialAppId(Long socialAppId) {
		this.socialAppId = socialAppId;
	}
	public String getSocialAppName() {
		return socialAppName;
	}
	public void setSocialAppName(String socialAppName) {
		this.socialAppName = socialAppName;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime
				* result
				+ ((groupDescription == null) ? 0 : groupDescription.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result
				+ ((groupName == null) ? 0 : groupName.hashCode());
		result = prime * result
				+ ((socialType == null) ? 0 : socialType.hashCode());
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
		HunterSocialGroupJson other = (HunterSocialGroupJson) obj;
		if (active != other.active)
			return false;
		if (groupDescription == null) {
			if (other.groupDescription != null)
				return false;
		} else if (!groupDescription.equals(other.groupDescription))
			return false;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		if (socialType == null) {
			if (other.socialType != null)
				return false;
		} else if (!socialType.equals(other.socialType))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "HunterSocialGroupJson [groupId=" + groupId + ", externalId="
				+ externalId + ", groupName=" + groupName
				+ ", groupDescription=" + groupDescription + ", socialType="
				+ socialType + ", hunterOwned=" + hunterOwned + ", acquired="
				+ acquired + ", acquiredFromFullName=" + acquiredFromFullName
				+ ", receiversCount=" + receiversCount + ", clientUserName="
				+ clientUserName + ", hunterGroupAdmin=" + hunterGroupAdmin
				+ ", existent=" + existent + ", verifiedBy=" + verifiedBy
				+ ", verifiedDate=" + verifiedDate + ", active=" + active
				+ ", suspended=" + suspended + ", suspensionDescription="
				+ suspensionDescription + ", regionId=" + regionId
				+ ", regionName=" + regionName + ", regionPopulation="
				+ regionPopulation + ", regionDesc=" + regionDesc
				+ ", regionCountryName=" + regionCountryName
				+ ", regionCountyName=" + regionCountyName
				+ ", status=" + status
				+ ", socialAppName=" + socialAppName
				+ ", socialAppId=" + socialAppId
				+ ", regionConsName=" + regionConsName + ", regionWardName="
				+ regionWardName + ", regionCoordinates=" + regionCoordinates
				+ ", regionAssignedTo=" + regionAssignedTo + ", cretDate="
				+ cretDate + ", createdBy=" + createdBy + ", lastUpdate="
				+ lastUpdate + ", lastUpdatedBy=" + lastUpdatedBy + "]";
	}
	
	
	
	

}
