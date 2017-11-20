package com.techmaster.hunter.obj.beans;

import java.util.HashMap;
import java.util.Map;

import com.techmaster.hunter.util.HunterUtility;

public class TaskClientConfigBean {
	
	private Long clientId;
	private String clientName;
	private String userName;
	private String password;
	private String activeMethod;
	private String methodURL;
	private String denomination;
	private String preempReceivers;
	private float preemptPercentage;
	private String msgType;
	private int batchNo;
	private int batchMark;
	private String batchType;
	private boolean workerDefault;
	private String workerName;
	
	
	private Map<String, String> configs = new HashMap<String, String>();
	private Map<String,String> connConfigs = new HashMap<String,String>();

	public TaskClientConfigBean() {
		super();
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getActiveMethod() {
		return activeMethod;
	}

	public void setActiveMethod(String activeMethod) {
		this.activeMethod = activeMethod;
	}

	public String getMethodURL() {
		return methodURL;
	}

	public void setMethodURL(String methodURL) {
		this.methodURL = methodURL;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public String getPreempReceivers() {
		return preempReceivers;
	}

	public void setPreempReceivers(String preempReceivers) {
		this.preempReceivers = preempReceivers;
	}

	public float getPreemptPercentage() {
		return preemptPercentage;
	}

	public void setPreemptPercentage(float preemptPercentage) {
		this.preemptPercentage = preemptPercentage;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public void setConfigs(Map<String, String> configs) {
		this.configs = configs;
	}
	
	public int getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(int batchNo) {
		this.batchNo = batchNo;
	}

	public int getBatchMark() {
		return batchMark;
	}

	public void setBatchMark(int batchMark) {
		this.batchMark = batchMark;
	}
	
	public String getBatchType() {
		return batchType;
	}

	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
	
	public boolean isWorkerDefault() {
		return workerDefault;
	}

	public void setWorkerDefault(boolean workerDefault) {
		this.workerDefault = workerDefault;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public Map<String, String> getConfigs() {
		return configs;
	}

	public Map<String, String> getConnConfigs() {
		return connConfigs;
	}
	public void setConnConfigs(Map<String, String> connConfigs) {
		this.connConfigs = connConfigs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result
				+ ((clientName == null) ? 0 : clientName.hashCode());
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
		TaskClientConfigBean other = (TaskClientConfigBean) obj;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		if (clientName == null) {
			if (other.clientName != null)
				return false;
		} else if (!clientName.equals(other.clientName))
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "TaskClientConfigBean [clientId=" + clientId + ", clientName="
				+ clientName + ", userName=" + userName + ", password="
				+ password + ", activeMethod=" + activeMethod + ", methodURL="
				+ methodURL + ", denomination=" + denomination
				+ ", preempReceivers=" + preempReceivers
				+ ", preemptPercentage=" + preemptPercentage + ", msgType="
				+ msgType + ", batchNo=" + batchNo + ", batchMark=" + batchMark
				+ ", batchType=" + batchType + ", workerDefault="
				+ workerDefault + ", workerName=" + workerName + ", configs="
				+ HunterUtility.stringifyMap(configs) + "]"; 
	}

	

	

	
	
	
	
	
	

}
