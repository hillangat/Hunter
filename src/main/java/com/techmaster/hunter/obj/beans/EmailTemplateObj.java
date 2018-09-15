package com.techmaster.hunter.obj.beans;

import java.sql.Blob;

import com.techmaster.hunter.util.HunterUtility;

public class EmailTemplateObj {

	private Long templateId;
	private String templateName;
	private String templateDescription;
	private String status;
	private Blob xmlTemplates;
	private Blob documentMetadata;
	private AuditInfo auditInfo;
	
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateDescription() {
		return templateDescription;
	}
	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}
	public Blob getXmlTemplates() {
		return xmlTemplates;
	}
	public void setXmlTemplates(Blob xmlTemplates) {
		this.xmlTemplates = xmlTemplates;
	}
	public AuditInfo getAuditInfo() {
		return auditInfo;
	}
	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}
	public Blob getDocumentMetadata() {
		return documentMetadata;
	}
	public void setDocumentMetadata(Blob documentMetadata) {
		this.documentMetadata = documentMetadata;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((templateDescription == null) ? 0 : templateDescription
						.hashCode());
		result = prime * result
				+ ((templateId == null) ? 0 : templateId.hashCode());
		result = prime * result
				+ ((templateName == null) ? 0 : templateName.hashCode());
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
		EmailTemplateObj other = (EmailTemplateObj) obj;
		if (templateDescription == null) {
			if (other.templateDescription != null)
				return false;
		} else if (!templateDescription.equals(other.templateDescription))
			return false;
		if (templateId == null) {
			if (other.templateId != null)
				return false;
		} else if (!templateId.equals(other.templateId))
			return false;
		if (templateName == null) {
			if (other.templateName != null)
				return false;
		} else if (!templateName.equals(other.templateName))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "EmailTemplateObj [templateId=" + templateId + ", templateName="
				+ templateName + ", templateDescription=" + templateDescription
				+ ", auditInfo=" + auditInfo
				+ ", xmlTemplates=" + HunterUtility.getBlobStrFromDB("xmlTemplates", "templateId", Long.toString(templateId), this.getClass()) 
				+ ", documentMetadata=" + HunterUtility.getBlobStrFromDB("documentMetadata", "templateId", Long.toString(templateId), this.getClass())
				+ ", status=" + status
				+ "]";
	}
	
	
	
	
	
	
	
	
}
