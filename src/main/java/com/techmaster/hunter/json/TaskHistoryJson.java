package com.techmaster.hunter.json;

public class TaskHistoryJson {
	private Long historyId;
	private Long taskId;
	private String evenName;
	private String eventStatus;
	private String eventMessage;
	private String eventUser;
	private String eventDate;
	
	public TaskHistoryJson() {
		super();
	}
	
	
	
	public TaskHistoryJson(Long taskId, String evenName, String eventStatus, String eventMessage, String eventUser,String eventDate) {
		super();
		this.taskId = taskId;
		this.evenName = evenName;
		this.eventStatus = eventStatus;
		this.eventMessage = eventMessage;
		this.eventUser = eventUser;
		this.eventDate = eventDate;
	}

	public Long getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getEvenName() {
		return evenName;
	}
	public void setEvenName(String evenName) {
		this.evenName = evenName;
	}
	public String getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
	public String getEventMessage() {
		return eventMessage;
	}
	public void setEventMessage(String eventMessage) {
		this.eventMessage = eventMessage;
	}
	public String getEventUser() {
		return eventUser;
	}
	public void setEventUser(String eventUser) {
		this.eventUser = eventUser;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate( String eventDate ) {
		this.eventDate = eventDate;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((evenName == null) ? 0 : evenName.hashCode());
		result = prime * result + ((eventDate == null) ? 0 : eventDate.hashCode());
		result = prime * result + ((eventMessage == null) ? 0 : eventMessage.hashCode());
		result = prime * result + ((eventStatus == null) ? 0 : eventStatus.hashCode());
		result = prime * result + ((eventUser == null) ? 0 : eventUser.hashCode());
		result = prime * result + ((historyId == null) ? 0 : historyId.hashCode());
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
		TaskHistoryJson other = (TaskHistoryJson) obj;
		if (evenName == null) {
			if (other.evenName != null)
				return false;
		} else if (!evenName.equals(other.evenName))
			return false;
		if (eventDate == null) {
			if (other.eventDate != null)
				return false;
		} else if (!eventDate.equals(other.eventDate))
			return false;
		if (eventMessage == null) {
			if (other.eventMessage != null)
				return false;
		} else if (!eventMessage.equals(other.eventMessage))
			return false;
		if (eventStatus == null) {
			if (other.eventStatus != null)
				return false;
		} else if (!eventStatus.equals(other.eventStatus))
			return false;
		if (eventUser == null) {
			if (other.eventUser != null)
				return false;
		} else if (!eventUser.equals(other.eventUser))
			return false;
		if (historyId == null) {
			if (other.historyId != null)
				return false;
		} else if (!historyId.equals(other.historyId))
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
		return "TaskHistory [historyId=" + historyId + ", taskId=" + taskId + ", evenName=" + evenName
				+ ", eventStatus=" + eventStatus + ", eventMessage=" + eventMessage + ", eventUser=" + eventUser
				+ ", eventDate=" + eventDate + "]";
	}
	
}
