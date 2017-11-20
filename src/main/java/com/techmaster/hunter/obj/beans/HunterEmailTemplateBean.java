package com.techmaster.hunter.obj.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.techmaster.hunter.util.HunterUtility;

public class HunterEmailTemplateBean {
	
	private String templateName;
	private String templateDesc;
	private String templateContent;
	private String subject;
	private String fromList;
	private String toList;
	private String ccList;
	private String contentType;
	private String template;
	
	List<String> embeddedAttachments  = new ArrayList<>();
	Map<String, String> miscelaneous = new HashMap<String, String>();
	Map<String, String> attachments = new HashMap<String, String>();
	
	public HunterEmailTemplateBean() {
		super();
	}
	
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateDesc() {
		return templateDesc;
	}
	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}
	public String getTemplateContent() {
		return templateContent;
	}
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Map<String, String> getMiscelaneous() {
		return miscelaneous;
	}
	public void setMiscelaneous(Map<String, String> miscelaneous) {
		this.miscelaneous = miscelaneous;
	}

	public String getFromList() {
		return fromList;
	}
	public void setFromList(String fromList) {
		this.fromList = fromList;
	}
	public String getToList() {
		return toList;
	}
	public void setToList(String toList) {
		this.toList = toList;
	}
	public String getCcList() {
		return ccList;
	}
	public void setCcList(String ccList) {
		this.ccList = ccList;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public Map<String, String> getAttachments() {
		return attachments;
	}
	public void setAttachments(Map<String, String> attachments) {
		this.attachments = attachments;
	}
	public List<String> getEmbeddedAttachments() {
		return embeddedAttachments;
	}
	public void setEmbeddedAttachments(List<String> embeddedAttachments) {
		this.embeddedAttachments = embeddedAttachments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ccList == null) ? 0 : ccList.hashCode());
		result = prime * result
				+ ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result
				+ ((fromList == null) ? 0 : fromList.hashCode());
		result = prime * result
				+ ((miscelaneous == null) ? 0 : miscelaneous.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result
				+ ((template == null) ? 0 : template.hashCode());
		result = prime * result
				+ ((templateContent == null) ? 0 : templateContent.hashCode());
		result = prime * result
				+ ((templateDesc == null) ? 0 : templateDesc.hashCode());
		result = prime * result
				+ ((templateName == null) ? 0 : templateName.hashCode());
		result = prime * result + ((toList == null) ? 0 : toList.hashCode());
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
		HunterEmailTemplateBean other = (HunterEmailTemplateBean) obj;
		if (ccList == null) {
			if (other.ccList != null)
				return false;
		} else if (!ccList.equals(other.ccList))
			return false;
		if (contentType == null) {
			if (other.contentType != null)
				return false;
		} else if (!contentType.equals(other.contentType))
			return false;
		if (fromList == null) {
			if (other.fromList != null)
				return false;
		} else if (!fromList.equals(other.fromList))
			return false;
		if (miscelaneous == null) {
			if (other.miscelaneous != null)
				return false;
		} else if (!miscelaneous.equals(other.miscelaneous))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (template == null) {
			if (other.template != null)
				return false;
		} else if (!template.equals(other.template))
			return false;
		if (templateContent == null) {
			if (other.templateContent != null)
				return false;
		} else if (!templateContent.equals(other.templateContent))
			return false;
		if (templateDesc == null) {
			if (other.templateDesc != null)
				return false;
		} else if (!templateDesc.equals(other.templateDesc))
			return false;
		if (templateName == null) {
			if (other.templateName != null)
				return false;
		} else if (!templateName.equals(other.templateName))
			return false;
		if (toList == null) {
			if (other.toList != null)
				return false;
		} else if (!toList.equals(other.toList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HunterEmailTemplateBean [templateName=" + templateName
				+ ", templateDesc=" + templateDesc + ", templateContent="
				+ templateContent + ", subject=" + subject + ", fromList="
				+ fromList + ", toList=" + toList + ", ccList=" + ccList
				+ ", contentType=" + contentType + ", template=" + template
				+ ", miscelaneous=" + HunterUtility.stringifyMap(miscelaneous) 
				+ ", attachments=" + HunterUtility.stringifyMap(attachments) 
				+ ", embeddedAttachments=" + HunterUtility.stringifyList(embeddedAttachments) + "]";   
	}

	

	
	

	
	

	
	
	
	

}
