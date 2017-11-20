package com.techmaster.hunter.obj.beans;

import java.util.HashSet;
import java.util.Set;

public class ReceiverGroup {
	
	private long groupId;
	private String ownerUserName;
	private String groupName;
	private String groupDesc;
	private int receiverCount;
	private String receiverType;
	private Set<ReceiverGroupReceiver> receiverGroupReceivers = new HashSet<ReceiverGroupReceiver>();
	private AuditInfo auditInfo;
	// commad delimited string "10,23,45"
	private String importBeanIds; 
	
	public ReceiverGroup() {
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
	public Set<ReceiverGroupReceiver> getReceiverGroupReceivers() {
		return receiverGroupReceivers;
	}
	public void setReceiverGroupReceivers(
			Set<ReceiverGroupReceiver> receiverGroupReceivers) {
		this.receiverGroupReceivers = receiverGroupReceivers;
	}

	public AuditInfo getAuditInfo() {
		return auditInfo;
	}
	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
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
	public String getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	public String getImportBeanIds() {
		return importBeanIds;
	}
	public void setImportBeanIds(String importBeanIds) {
		this.importBeanIds = importBeanIds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((auditInfo == null) ? 0 : auditInfo.hashCode());
		result = prime * result
				+ ((groupDesc == null) ? 0 : groupDesc.hashCode());
		result = prime * result + (int) (groupId ^ (groupId >>> 32));
		result = prime * result
				+ ((groupName == null) ? 0 : groupName.hashCode());
		result = prime * result
				+ ((importBeanIds == null) ? 0 : importBeanIds.hashCode());
		result = prime * result
				+ ((ownerUserName == null) ? 0 : ownerUserName.hashCode());
		result = prime * result + receiverCount;
		result = prime
				* result
				+ ((receiverGroupReceivers == null) ? 0
						: receiverGroupReceivers.hashCode());
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
		ReceiverGroup other = (ReceiverGroup) obj;
		if (auditInfo == null) {
			if (other.auditInfo != null)
				return false;
		} else if (!auditInfo.equals(other.auditInfo))
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
		if (importBeanIds == null) {
			if (other.importBeanIds != null)
				return false;
		} else if (!importBeanIds.equals(other.importBeanIds))
			return false;
		if (ownerUserName == null) {
			if (other.ownerUserName != null)
				return false;
		} else if (!ownerUserName.equals(other.ownerUserName))
			return false;
		if (receiverCount != other.receiverCount)
			return false;
		if (receiverGroupReceivers == null) {
			if (other.receiverGroupReceivers != null)
				return false;
		} else if (!receiverGroupReceivers.equals(other.receiverGroupReceivers))
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
		return "ReceiverGroup [groupId=" + groupId + ", ownerUserName="
				+ ownerUserName + ", groupName=" + groupName + ", groupDesc="
				+ groupDesc + ", receiverCount=" + receiverCount
				+ ", receiverType=" + receiverType
				+ ", receiverGroupReceivers=" + receiverGroupReceivers
				+ ", auditInfo=" + auditInfo + ", importBeanIds="
				+ importBeanIds + "]";
	}

	

	

	

	

	
	

	
	
	
}
