package com.techmaster.hunter.obj.beans;

import java.sql.Blob;

import com.techmaster.hunter.util.HunterUtility;

public class HunterSocialApp {
	
	private Long appId;
	private String appName;
	private String appDesc;
	private String extrnlId;
	private String extrnalPassCode;
	private String socialType;
	private Blob appConfigs;
	private AuditInfo auditInfo;
	
	
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppDesc() {
		return appDesc;
	}
	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}
	public String getExtrnlId() {
		return extrnlId;
	}
	public void setExtrnlId(String extrnlId) {
		this.extrnlId = extrnlId;
	}
	public String getExtrnalPassCode() {
		return extrnalPassCode;
	}
	public void setExtrnalPassCode(String extrnalPassCode) {
		this.extrnalPassCode = extrnalPassCode;
	}
	public String getSocialType() {
		return socialType;
	}
	public void setSocialType(String socialType) {
		this.socialType = socialType;
	}
	public Blob getAppConfigs() {
		return appConfigs;
	}
	public void setAppConfigs(Blob appConfigs) {
		this.appConfigs = appConfigs;
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
		result = prime * result + ((appDesc == null) ? 0 : appDesc.hashCode());
		result = prime * result + ((appId == null) ? 0 : appId.hashCode());
		result = prime * result + ((appName == null) ? 0 : appName.hashCode());
		result = prime * result
				+ ((extrnalPassCode == null) ? 0 : extrnalPassCode.hashCode());
		result = prime * result
				+ ((extrnlId == null) ? 0 : extrnlId.hashCode());
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
		HunterSocialApp other = (HunterSocialApp) obj;
		if (appDesc == null) {
			if (other.appDesc != null)
				return false;
		} else if (!appDesc.equals(other.appDesc))
			return false;
		if (appId == null) {
			if (other.appId != null)
				return false;
		} else if (!appId.equals(other.appId))
			return false;
		if (appName == null) {
			if (other.appName != null)
				return false;
		} else if (!appName.equals(other.appName))
			return false;
		if (extrnalPassCode == null) {
			if (other.extrnalPassCode != null)
				return false;
		} else if (!extrnalPassCode.equals(other.extrnalPassCode))
			return false;
		if (extrnlId == null) {
			if (other.extrnlId != null)
				return false;
		} else if (!extrnlId.equals(other.extrnlId))
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
		return "HunterSocialApp [appId=" + appId + ", appName=" + appName
				+ ", appDesc=" + appDesc + ", extrnlId=" + extrnlId
				+ ", extrnalPassCode=" + extrnalPassCode + ", socialType="
				+ socialType + ", appConfigs=" + HunterUtility.getBlobStrFromDB("appConfigs", "appId", Long.toString(appId), this.getClass()) + ", auditInfo=" 
				+ auditInfo + "]";
	}
	
	
	
	

}
