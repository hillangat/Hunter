package com.techmaster.hunter.json;


public class EmailTemplateObjJson {

	private Long templateId;
	private String templateName;
	private String templateDescription;
	private String status;
	private String cretDate;
	private String createdBy;
	private String lastUpdate;
	private String lastUpdatedBy;
	
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
	public String getCretDate() {
		return cretDate;
	}
	public void setCretDate(String cretDate) {
		this.cretDate = cretDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
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
		EmailTemplateObjJson other = (EmailTemplateObjJson) obj;
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
		return "EmailTemplateObjJson [templateId=" + templateId
				+ ", templateName=" + templateName + ", templateDescription="
				+ templateDescription + ", cretDate=" + cretDate
				+ ", createdBy=" + createdBy + ", lastUpdate=" + lastUpdate
				+ ", status=" + status
				+ ", lastUpdatedBy=" + lastUpdatedBy
				+ "]";
	}
	
	
	
	
	
}
