package com.techmaster.hunter.obj.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.json.ReceiverGroupJson;


public class Task {
	
	private long taskId;
	private Long clientId;
	private String taskType;
	private String taskName;
	private String taskObjective;
	private String description;
	private String tskAgrmntLoc;
	private String tskMsgType;
	private float taskBudget;
	private float taskCost;
	private boolean recurrentTask;
	private Date taskDateline;
	private String taskLifeStatus;
	private String taskDeliveryStatus;
	private boolean taskApproved;
	private String taskApprover;
	private String gateWayClient;
	private int desiredReceiverCount;
	private int availableReceiverCount;
	private int confirmedReceiverCount;
	private String srlzdTskPrcssJbObjsFilLoc;
	
	private String processedBy;
	private Date processedOn;
	
	private java.util.Date cretDate;
	private java.util.Date lastUpdate;
	private String updatedBy;
	private String createdBy;
	
	private Message taskMessage;
	private Set<ReceiverRegion> taskRegions  = new HashSet<>();
	private Set<ReceiverGroupJson> taskGroups = new HashSet<>();
	
	public Task() {
		super();
		this.setCretDate(new Date());
		this.setLastUpdate(new Date());
		this.setTaskLifeStatus(HunterConstants.STATUS_DRAFT);
		this.setTaskDeliveryStatus(HunterConstants.STATUS_CONCEPTUAL);
	}
	
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskObjective() {
		return taskObjective;
	}
	public void setTaskObjective(String taskObjective) {
		this.taskObjective = taskObjective;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTskAgrmntLoc() {
		return tskAgrmntLoc;
	}
	public void setTskAgrmntLoc(String tskAgrmntLoc) {
		this.tskAgrmntLoc = tskAgrmntLoc;
	}
	public float getTaskBudget() {
		return taskBudget;
	}
	public void setTaskBudget(float taskBudget) {
		this.taskBudget = taskBudget;
	}
	public float getTaskCost() {
		return taskCost;
	}
	public void setTaskCost(float taskCost) {
		this.taskCost = taskCost;
	}
	public boolean isRecurrentTask() {
		return recurrentTask;
	}
	public void setRecurrentTask(boolean recurrentTask) {
		this.recurrentTask = recurrentTask;
	}
	public Date getTaskDateline() {
		return taskDateline;
	}
	public void setTaskDateline(Date taskDateline) {
		this.taskDateline = taskDateline;
	}
	public String getTaskLifeStatus() {
		return taskLifeStatus;
	}
	public void setTaskLifeStatus(String taskLifeStatus) {
		this.taskLifeStatus = taskLifeStatus;
	}
	public String getTaskDeliveryStatus() {
		return taskDeliveryStatus;
	}
	public void setTaskDeliveryStatus(String taskDeliveryStatus) {
		this.taskDeliveryStatus = taskDeliveryStatus;
	}
	public boolean isTaskApproved() {
		return taskApproved;
	}
	public void setTaskApproved(boolean taskApproved) {
		this.taskApproved = taskApproved;
	}
	public String getTaskApprover() {
		return taskApprover;
	}
	public void setTaskApprover(String taskApprover) {
		this.taskApprover = taskApprover;
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
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public Message getTaskMessage() {
		return taskMessage;
	}
	public void setTaskMessage(Message taskMessage) {
		this.taskMessage = taskMessage;
	}
	public String getGateWayClient() {
		return gateWayClient;
	}
	public void setGateWayClient(String gateWayClient) {
		this.gateWayClient = gateWayClient;
	}
	public Set<ReceiverRegion> getTaskRegions() {
		return taskRegions;
	}
	public void setTaskRegions(Set<ReceiverRegion> taskRegions) {
		this.taskRegions = taskRegions;
	}

	public int getDesiredReceiverCount() {
		return desiredReceiverCount;
	}

	public void setDesiredReceiverCount(int desiredReceiverCount) {
		this.desiredReceiverCount = desiredReceiverCount;
	}

	public int getAvailableReceiverCount() {
		return availableReceiverCount;
	}

	public void setAvailableReceiverCount(int availableReceiverCount) {
		this.availableReceiverCount = availableReceiverCount;
	}

	public int getConfirmedReceiverCount() {
		return confirmedReceiverCount;
	}

	public void setConfirmedReceiverCount(int confirmedReceiverCount) {
		this.confirmedReceiverCount = confirmedReceiverCount;
	}

	public String getTskMsgType() {
		return tskMsgType;
	}

	public void setTskMsgType(String tskMsgType) {
		this.tskMsgType = tskMsgType;
	}

	public Set<ReceiverGroupJson> getTaskGroups() {
		return taskGroups;
	}

	public void setTaskGroups(Set<ReceiverGroupJson> taskGroups) {
		this.taskGroups = taskGroups;
	}

	public String getProcessedBy() {
		return processedBy;
	}

	public void setProcessedBy(String processedBy) {
		this.processedBy = processedBy;
	}

	public Date getProcessedOn() {
		return processedOn;
	}

	public void setProcessedOn(Date processedOn) {
		this.processedOn = processedOn;
	}
	public String getSrlzdTskPrcssJbObjsFilLoc() {
		return srlzdTskPrcssJbObjsFilLoc;
	}
	public void setSrlzdTskPrcssJbObjsFilLoc(String srlzdTskPrcssJbObjsFilLoc) {
		this.srlzdTskPrcssJbObjsFilLoc = srlzdTskPrcssJbObjsFilLoc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + availableReceiverCount;
		result = prime * result
				+ ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + confirmedReceiverCount;
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((cretDate == null) ? 0 : cretDate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + desiredReceiverCount;
		result = prime * result
				+ ((gateWayClient == null) ? 0 : gateWayClient.hashCode());
		result = prime * result
				+ ((lastUpdate == null) ? 0 : lastUpdate.hashCode());
		result = prime * result
				+ ((processedBy == null) ? 0 : processedBy.hashCode());
		result = prime * result
				+ ((processedOn == null) ? 0 : processedOn.hashCode());
		result = prime * result + (recurrentTask ? 1231 : 1237);
		result = prime * result + (taskApproved ? 1231 : 1237);
		result = prime * result
				+ ((taskApprover == null) ? 0 : taskApprover.hashCode());
		result = prime * result + Float.floatToIntBits(taskBudget);
		result = prime * result + Float.floatToIntBits(taskCost);
		result = prime * result
				+ ((taskDateline == null) ? 0 : taskDateline.hashCode());
		result = prime
				* result
				+ ((taskDeliveryStatus == null) ? 0 : taskDeliveryStatus
						.hashCode());
		result = prime * result
				+ ((taskGroups == null) ? 0 : taskGroups.hashCode());
		result = prime * result + (int) (taskId ^ (taskId >>> 32));
		result = prime * result
				+ ((taskLifeStatus == null) ? 0 : taskLifeStatus.hashCode());
		result = prime * result
				+ ((taskMessage == null) ? 0 : taskMessage.hashCode());
		result = prime * result
				+ ((taskName == null) ? 0 : taskName.hashCode());
		result = prime * result
				+ ((taskObjective == null) ? 0 : taskObjective.hashCode());
		result = prime * result
				+ ((taskRegions == null) ? 0 : taskRegions.hashCode());
		result = prime * result
				+ ((taskType == null) ? 0 : taskType.hashCode());
		result = prime * result
				+ ((tskAgrmntLoc == null) ? 0 : tskAgrmntLoc.hashCode());
		result = prime * result
				+ ((tskMsgType == null) ? 0 : tskMsgType.hashCode());
		result = prime * result
				+ ((updatedBy == null) ? 0 : updatedBy.hashCode());
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
		Task other = (Task) obj;
		if (availableReceiverCount != other.availableReceiverCount)
			return false;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		if (confirmedReceiverCount != other.confirmedReceiverCount)
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
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (desiredReceiverCount != other.desiredReceiverCount)
			return false;
		if (gateWayClient == null) {
			if (other.gateWayClient != null)
				return false;
		} else if (!gateWayClient.equals(other.gateWayClient))
			return false;
		if (lastUpdate == null) {
			if (other.lastUpdate != null)
				return false;
		} else if (!lastUpdate.equals(other.lastUpdate))
			return false;
		if (processedBy == null) {
			if (other.processedBy != null)
				return false;
		} else if (!processedBy.equals(other.processedBy))
			return false;
		if (processedOn == null) {
			if (other.processedOn != null)
				return false;
		} else if (!processedOn.equals(other.processedOn))
			return false;
		if (recurrentTask != other.recurrentTask)
			return false;
		if (taskApproved != other.taskApproved)
			return false;
		if (taskApprover == null) {
			if (other.taskApprover != null)
				return false;
		} else if (!taskApprover.equals(other.taskApprover))
			return false;
		if (Float.floatToIntBits(taskBudget) != Float
				.floatToIntBits(other.taskBudget))
			return false;
		if (Float.floatToIntBits(taskCost) != Float
				.floatToIntBits(other.taskCost))
			return false;
		if (taskDateline == null) {
			if (other.taskDateline != null)
				return false;
		} else if (!taskDateline.equals(other.taskDateline))
			return false;
		if (taskDeliveryStatus == null) {
			if (other.taskDeliveryStatus != null)
				return false;
		} else if (!taskDeliveryStatus.equals(other.taskDeliveryStatus))
			return false;
		if (taskGroups == null) {
			if (other.taskGroups != null)
				return false;
		} else if (!taskGroups.equals(other.taskGroups))
			return false;
		if (taskId != other.taskId)
			return false;
		if (taskLifeStatus == null) {
			if (other.taskLifeStatus != null)
				return false;
		} else if (!taskLifeStatus.equals(other.taskLifeStatus))
			return false;
		if (taskMessage == null) {
			if (other.taskMessage != null)
				return false;
		} else if (!taskMessage.equals(other.taskMessage))
			return false;
		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		if (taskObjective == null) {
			if (other.taskObjective != null)
				return false;
		} else if (!taskObjective.equals(other.taskObjective))
			return false;
		if (taskRegions == null) {
			if (other.taskRegions != null)
				return false;
		} else if (!taskRegions.equals(other.taskRegions))
			return false;
		if (taskType == null) {
			if (other.taskType != null)
				return false;
		} else if (!taskType.equals(other.taskType))
			return false;
		if (tskAgrmntLoc == null) {
			if (other.tskAgrmntLoc != null)
				return false;
		} else if (!tskAgrmntLoc.equals(other.tskAgrmntLoc))
			return false;
		if (tskMsgType == null) {
			if (other.tskMsgType != null)
				return false;
		} else if (!tskMsgType.equals(other.tskMsgType))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", taskType=" + taskType
				+ ", taskName=" + taskName + ", taskObjective=" + taskObjective
				+ ", description=" + description + ", tskAgrmntLoc="
				+ tskAgrmntLoc + ", tskMsgType=" + tskMsgType + ", taskBudget="
				+ taskBudget + ", taskCost=" + taskCost + ", recurrentTask="
				+ recurrentTask + ", taskDateline=" + taskDateline
				+ ", taskLifeStatus=" + taskLifeStatus
				+ ", taskDeliveryStatus=" + taskDeliveryStatus
				+ ", taskApproved=" + taskApproved + ", taskApprover="
				+ taskApprover + ", gateWayClient=" + gateWayClient
				+ ", desiredReceiverCount=" + desiredReceiverCount
				+ ", availableReceiverCount=" + availableReceiverCount
				+ ", confirmedReceiverCount=" + confirmedReceiverCount
				+ ", processedBy=" + processedBy + ", processedOn="
				+ processedOn + ", cretDate=" + cretDate + ", lastUpdate="
				+ lastUpdate + ", updatedBy=" + updatedBy + ", createdBy="
				+ createdBy + ", clientId=" + clientId + ", taskMessage="
				+ taskMessage + ", taskRegions=" + taskRegions
				+ ", taskGroups=" + taskGroups 
				+ ", srlzdTskPrcssJbObjsFilLoc=" + srlzdTskPrcssJbObjsFilLoc + "]";
	}

	

	

	
	

	
	



	

}
