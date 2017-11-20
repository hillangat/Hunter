package com.techmaster.hunter.obj.beans;

import java.io.Serializable;

public class HunterRawReceiverUser implements Serializable{

	private static final long serialVersionUID = -5456753821824904218L;
	
	private Long userId;
	private String rawUserName;
	private float compensation;
	private int allContctNo;
	private int vrfdContctNo;
	private String country;
	private String county;
	private String constituency;
	private String consWard;
	private String village;
	private AuditInfo auditInfo;
	
	public float getCompensation() {
		return compensation;
	}
	public void setCompensation(float compensation) {
		this.compensation = compensation;
	}
	public int getAllContctNo() {
		return allContctNo;
	}
	public void setAllContctNo(int allContctNo) {
		this.allContctNo = allContctNo;
	}
	public int getVrfdContctNo() {
		return vrfdContctNo;
	}
	public void setVrfdContctNo(int vrfdContctNo) {
		this.vrfdContctNo = vrfdContctNo;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getConstituency() {
		return constituency;
	}
	public void setConstituency(String constituency) {
		this.constituency = constituency;
	}
	public String getConsWard() {
		return consWard;
	}
	public void setConsWard(String consWard) {
		this.consWard = consWard;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getRawUserName() {
		return rawUserName;
	}
	public void setRawUserName(String rawUserName) {
		this.rawUserName = rawUserName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public AuditInfo getAuditInfo() {
		return auditInfo;
	}
	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((rawUserName == null) ? 0 : rawUserName.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		HunterRawReceiverUser other = (HunterRawReceiverUser) obj;
		if (rawUserName == null) {
			if (other.rawUserName != null)
				return false;
		} else if (!rawUserName.equals(other.rawUserName))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "HunterRawReceiverUser [userId=" + userId + ", rawUserName="
				+ rawUserName + ", compensation=" + compensation
				+ ", allContctNo=" + allContctNo + ", vrfdContctNo="
				+ vrfdContctNo + ", country=" + country + ", county=" + county
				+ ", constituency=" + constituency + ", consWard=" + consWard
				+ ", village=" + village + ", auditInfo=" + auditInfo + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
