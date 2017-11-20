package com.techmaster.hunter.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.techmaster.hunter.util.HunterUtility;

public class TaskProcessJobJson implements Serializable{
	
	private static final long serialVersionUID = -3297849356400591162L;
	
	private Long processJobId;
	private String genStatus;
	private String numberOfWorkers;
	private int totalMsgs;
	private String clientName;
	private Long genDuration;
	private String startDate;
	private String endDate;
	private List<TaskProcessWorkerJson> workerJsons = new ArrayList<TaskProcessWorkerJson>();
	
	public Long getProcessJobId() {
		return processJobId;
	}
	public void setProcessJobId(Long processJobId) {
		this.processJobId = processJobId;
	}
	public String getGenStatus() {
		return genStatus;
	}
	public void setGenStatus(String genStatus) {
		this.genStatus = genStatus;
	}
	public String getNumberOfWorkers() {
		return numberOfWorkers;
	}
	public void setNumberOfWorkers(String numberOfWorkers) {
		this.numberOfWorkers = numberOfWorkers;
	}
	public int getTotalMsgs() {
		return totalMsgs;
	}
	public void setTotalMsgs(int totalMsgs) {
		this.totalMsgs = totalMsgs;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Long getGenDuration() {
		return genDuration;
	}
	public void setGenDuration(Long genDuration) {
		this.genDuration = genDuration;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public List<TaskProcessWorkerJson> getWorkerJsons() {
		return workerJsons;
	}
	public void setWorkerJsons(List<TaskProcessWorkerJson> workerJsons) {
		this.workerJsons = workerJsons;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((clientName == null) ? 0 : clientName.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result
				+ ((genDuration == null) ? 0 : genDuration.hashCode());
		result = prime * result
				+ ((genStatus == null) ? 0 : genStatus.hashCode());
		result = prime * result
				+ ((numberOfWorkers == null) ? 0 : numberOfWorkers.hashCode());
		result = prime * result
				+ ((processJobId == null) ? 0 : processJobId.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + totalMsgs;
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
		TaskProcessJobJson other = (TaskProcessJobJson) obj;
		if (clientName == null) {
			if (other.clientName != null)
				return false;
		} else if (!clientName.equals(other.clientName))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (genDuration == null) {
			if (other.genDuration != null)
				return false;
		} else if (!genDuration.equals(other.genDuration))
			return false;
		if (genStatus == null) {
			if (other.genStatus != null)
				return false;
		} else if (!genStatus.equals(other.genStatus))
			return false;
		if (numberOfWorkers == null) {
			if (other.numberOfWorkers != null)
				return false;
		} else if (!numberOfWorkers.equals(other.numberOfWorkers))
			return false;
		if (processJobId == null) {
			if (other.processJobId != null)
				return false;
		} else if (!processJobId.equals(other.processJobId))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (totalMsgs != other.totalMsgs)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TaskProcessJobJson [processJobId=" + processJobId
				+ ", genStatus=" + genStatus + ", numberOfWorkers="
				+ numberOfWorkers + ", totalMsgs=" + totalMsgs
				+ ", clientName=" + clientName + ", genDuration=" + genDuration
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", workerJsons=" + HunterUtility.stringifyList(workerJsons) + "]"; 
	}
	
	
	

}
