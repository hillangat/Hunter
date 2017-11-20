package com.techmaster.hunter.obj.beans;

import java.sql.Blob;
import java.util.Arrays;
import java.util.Date;

import com.techmaster.hunter.util.HunterUtility;

public class GateWayMessage {
	
	private Long msgId;
	private Long taskId;
	private Blob text;
	private String messageType;
	private String clntRspCode;
	private String clntRspText;
	private String subsRspnsCode;
	private String subsRspnsText;
	private String contact;
	private String gClient;
	private Date sendDate;
	private String status;
	private Date createdOn;
	private String createdBy;
	private Long duration;
	private byte[] requestBody;
	private String errorText;
	private String clientTagKey;
	
	public GateWayMessage() {
		super();
	}
	
	
	public Long getMsgId() {
		return msgId;
	}
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Blob getText() {
		return text;
	}
	public void setText(Blob text) {
		this.text = text;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getClntRspCode() {
		return clntRspCode;
	}
	public void setClntRspCode(String clntRspCode) {
		this.clntRspCode = clntRspCode;
	}
	public String getClntRspText() {
		return clntRspText;
	}
	public void setClntRspText(String clntRspText) {
		this.clntRspText = clntRspText;
	}
	public String getSubsRspnsCode() {
		return subsRspnsCode;
	}
	public void setSubsRspnsCode(String subsRspnsCode) {
		this.subsRspnsCode = subsRspnsCode;
	}
	public String getSubsRspnsText() {
		return subsRspnsText;
	}
	public void setSubsRspnsText(String subsRspnsText) {
		this.subsRspnsText = subsRspnsText;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getgClient() {
		return gClient;
	}
	public void setgClient(String gClient) {
		this.gClient = gClient;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public byte[] getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(byte[] requestBody) {
		this.requestBody = requestBody;
	}
	public String getErrorText() {
		return errorText;
	}
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}
	public String getClientTagKey() {
		return clientTagKey;
	}
	public void setClientTagKey(String clientTagKey) {
		this.clientTagKey = clientTagKey;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((clientTagKey == null) ? 0 : clientTagKey.hashCode());
		result = prime * result
				+ ((clntRspCode == null) ? 0 : clntRspCode.hashCode());
		result = prime * result
				+ ((clntRspText == null) ? 0 : clntRspText.hashCode());
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdOn == null) ? 0 : createdOn.hashCode());
		result = prime * result
				+ ((duration == null) ? 0 : duration.hashCode());
		result = prime * result
				+ ((errorText == null) ? 0 : errorText.hashCode());
		result = prime * result + ((gClient == null) ? 0 : gClient.hashCode());
		result = prime * result
				+ ((messageType == null) ? 0 : messageType.hashCode());
		result = prime * result + ((msgId == null) ? 0 : msgId.hashCode());
		result = prime * result + Arrays.hashCode(requestBody);
		result = prime * result
				+ ((sendDate == null) ? 0 : sendDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((subsRspnsCode == null) ? 0 : subsRspnsCode.hashCode());
		result = prime * result
				+ ((subsRspnsText == null) ? 0 : subsRspnsText.hashCode());
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		GateWayMessage other = (GateWayMessage) obj;
		if (clientTagKey == null) {
			if (other.clientTagKey != null)
				return false;
		} else if (!clientTagKey.equals(other.clientTagKey))
			return false;
		if (clntRspCode == null) {
			if (other.clntRspCode != null)
				return false;
		} else if (!clntRspCode.equals(other.clntRspCode))
			return false;
		if (clntRspText == null) {
			if (other.clntRspText != null)
				return false;
		} else if (!clntRspText.equals(other.clntRspText))
			return false;
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.equals(other.contact))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdOn == null) {
			if (other.createdOn != null)
				return false;
		} else if (!createdOn.equals(other.createdOn))
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
		if (gClient == null) {
			if (other.gClient != null)
				return false;
		} else if (!gClient.equals(other.gClient))
			return false;
		if (messageType == null) {
			if (other.messageType != null)
				return false;
		} else if (!messageType.equals(other.messageType))
			return false;
		if (msgId == null) {
			if (other.msgId != null)
				return false;
		} else if (!msgId.equals(other.msgId))
			return false;
		if (!Arrays.equals(requestBody, other.requestBody))
			return false;
		if (sendDate == null) {
			if (other.sendDate != null)
				return false;
		} else if (!sendDate.equals(other.sendDate))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (subsRspnsCode == null) {
			if (other.subsRspnsCode != null)
				return false;
		} else if (!subsRspnsCode.equals(other.subsRspnsCode))
			return false;
		if (subsRspnsText == null) {
			if (other.subsRspnsText != null)
				return false;
		} else if (!subsRspnsText.equals(other.subsRspnsText))
			return false;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "GateWayMessage [msgId=" + msgId + ", taskId=" + taskId
				+ ", text=" + HunterUtility.getBlobStr(text) + ", messageType=" + messageType 
				+ ", clntRspCode=" + clntRspCode + ", clntRspText="
				+ clntRspText + ", subsRspnsCode=" + subsRspnsCode
				+ ", subsRspnsText=" + subsRspnsText + ", contact=" + contact
				+ ", gClient=" + gClient + ", sendDate=" + sendDate
				+ ", status=" + status + ", createdOn=" + createdOn
				+ ", createdBy=" + createdBy + ", duration=" + duration
				+ ", requestBody=" + Arrays.toString(requestBody)
				+ ", errorText=" + errorText + ", clientTagKey=" + clientTagKey
				+ "]";
	}



	
	
	
		
	
				
	

}
