package com.techmaster.hunter.obj.beans;

import java.sql.Blob;
import java.util.Date;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.util.HunterUtility;

public class HunterSocialGroup {
	
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
	private String verifiedBy;
	private Date verifiedDate;
	private AuditInfo auditInfo;
	private String status = HunterConstants.STATUS_DRAFT;
	private boolean active;
	private boolean suspended;
	private String suspensionDescription;
	private String delStatus = HunterConstants.STATUS_CONCEPTUAL;
	private Blob processData;
	
	private HunterSocialRegion socialRegion;
	private HunterSocialApp defaultSocialApp;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getReceiversCount() {
		return receiversCount;
	}
	public void setReceiversCount(int receiversCount) {
		this.receiversCount = receiversCount;
	}
	public boolean isSuspended() {
		return suspended;
	}
	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}
	public HunterSocialRegion getSocialRegion() {
		return socialRegion;
	}
	public void setSocialRegion(HunterSocialRegion socialRegion) {
		this.socialRegion = socialRegion;
	}
	public String getSuspensionDescription() {
		return suspensionDescription;
	}
	public void setSuspensionDescription(String suspensionDescription) {
		this.suspensionDescription = suspensionDescription;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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
	public AuditInfo getAuditInfo() {
		return auditInfo;
	}
	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}
	public boolean isHunterOwned() {
		return hunterOwned;
	}
	public void setHunterOwned(boolean hunterOwned) {
		this.hunterOwned = hunterOwned;
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
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
	public Date getVerifiedDate() {
		return verifiedDate;
	}
	public void setVerifiedDate(Date verifiedDate) {
		this.verifiedDate = verifiedDate;
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
	public HunterSocialApp getDefaultSocialApp() {
		return defaultSocialApp;
	}
	public void setDefaultSocialApp(HunterSocialApp defaultSocialApp) {
		this.defaultSocialApp = defaultSocialApp;
	}
	public String getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}
	public Blob getProcessData() {
		return processData;
	}
	public void setProcessData(Blob processData) {
		this.processData = processData;
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
		HunterSocialGroup other = (HunterSocialGroup) obj;
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
		return "HunterSocialGroup [groupId=" + groupId + ", groupName="
				+ groupName + ", groupDescription=" + groupDescription
				+ ", socialType=" + socialType + ", hunterOwned=" + hunterOwned
				+ ", receiversCount=" + receiversCount + ", clientUserName="
				+ clientUserName + ", hunterGroupAdmin=" + hunterGroupAdmin
				+ ", socialRegion=" + socialRegion 
				+ ", auditInfo=" + auditInfo + ", active=" + active
				+ ", suspended=" + suspended + ", status=" + status
				+ ", externalId=" + externalId
				+ ", existent=" + existent
				+ ", delStatus=" + delStatus 
				+ ", processData=" + HunterUtility.getBlobStrFromDB("processData", "groupId", Long.toString(groupId), this.getClass())  
				+ ", verifiedBy=" + verifiedBy
				+ ", acquired=" + acquired
				+ ", defaultSocialApp=" + defaultSocialApp 
				+ ", verifiedDate=" + HunterUtility.formatDate(verifiedDate, HunterConstants.DATE_FORMAT_STRING)
				+ ", suspensionDescription=" + suspensionDescription + "]";
	}
	
	
	
	
	
	
	
	

}
