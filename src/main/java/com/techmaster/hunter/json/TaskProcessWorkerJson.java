package com.techmaster.hunter.json;

import java.io.Serializable;

public class TaskProcessWorkerJson implements Serializable {

	private static final long serialVersionUID = 2929053635252295679L;
	
	private Long processJobId;
	private int workerNo;
	private String workerName;
	private String workerStatus;
	private String responseCode;
	private String responseText;
	private String errorType;
	private String errorText;
	private String duration;
	private String cnnStatus;
	private int msgCount;
	private String msgIds;
	private Long taskId;
	
	public TaskProcessWorkerJson() {
		super();
	}

	public Long getProcessJobId() {
		return processJobId;
	}

	public void setProcessJobId(Long processJobId) {
		this.processJobId = processJobId;
	}

	public int getWorkerNo() {
		return workerNo;
	}

	public void setWorkerNo(int workerNo) {
		this.workerNo = workerNo;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public String getWorkerStatus() {
		return workerStatus;
	}

	public void setWorkerStatus(String workerStatus) {
		this.workerStatus = workerStatus;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getCnnStatus() {
		return cnnStatus;
	}

	public void setCnnStatus(String cnnStatus) {
		this.cnnStatus = cnnStatus;
	}

	public int getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(int msgCount) {
		this.msgCount = msgCount;
	}

	public String getMsgIds() {
		return msgIds;
	}

	public void setMsgIds(String msgIds) {
		this.msgIds = msgIds;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cnnStatus == null) ? 0 : cnnStatus.hashCode());
		result = prime * result
				+ ((duration == null) ? 0 : duration.hashCode());
		result = prime * result
				+ ((errorText == null) ? 0 : errorText.hashCode());
		result = prime * result
				+ ((errorType == null) ? 0 : errorType.hashCode());
		result = prime * result + msgCount;
		result = prime * result + ((msgIds == null) ? 0 : msgIds.hashCode());
		result = prime * result
				+ ((processJobId == null) ? 0 : processJobId.hashCode());
		result = prime * result
				+ ((responseCode == null) ? 0 : responseCode.hashCode());
		result = prime * result
				+ ((responseText == null) ? 0 : responseText.hashCode());
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
		result = prime * result
				+ ((workerName == null) ? 0 : workerName.hashCode());
		result = prime * result + workerNo;
		result = prime * result
				+ ((workerStatus == null) ? 0 : workerStatus.hashCode());
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
		TaskProcessWorkerJson other = (TaskProcessWorkerJson) obj;
		if (cnnStatus == null) {
			if (other.cnnStatus != null)
				return false;
		} else if (!cnnStatus.equals(other.cnnStatus))
			return false;
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (errorText == null) {
			if (other.errorText != null)
				return false;
		} else if (!errorText.equals(other.errorText))
			return false;
		if (errorType == null) {
			if (other.errorType != null)
				return false;
		} else if (!errorType.equals(other.errorType))
			return false;
		if (msgCount != other.msgCount)
			return false;
		if (msgIds == null) {
			if (other.msgIds != null)
				return false;
		} else if (!msgIds.equals(other.msgIds))
			return false;
		if (processJobId == null) {
			if (other.processJobId != null)
				return false;
		} else if (!processJobId.equals(other.processJobId))
			return false;
		if (responseCode == null) {
			if (other.responseCode != null)
				return false;
		} else if (!responseCode.equals(other.responseCode))
			return false;
		if (responseText == null) {
			if (other.responseText != null)
				return false;
		} else if (!responseText.equals(other.responseText))
			return false;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		if (workerName == null) {
			if (other.workerName != null)
				return false;
		} else if (!workerName.equals(other.workerName))
			return false;
		if (workerNo != other.workerNo)
			return false;
		if (workerStatus == null) {
			if (other.workerStatus != null)
				return false;
		} else if (!workerStatus.equals(other.workerStatus))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TaskProcessJobResultJson [processJobId=" + processJobId
				+ ", workerNo=" + workerNo + ", workerName=" + workerName
				+ ", workerStatus=" + workerStatus + ", responseCode="
				+ responseCode + ", responseText=" + responseText
				+ ", errorType=" + errorType + ", errorText=" + errorText
				+ ", duration=" + duration + ", cnnStatus=" + cnnStatus
				+ ", msgCount=" + msgCount + ", msgIds=" + msgIds + ", taskId="
				+ taskId + "]";
	}
	
	
	
	
}
