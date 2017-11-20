package com.techmaster.hunter.obj.beans;

import java.sql.Blob;

import com.techmaster.hunter.xml.XMLService;

public class TaskProcessJob {

	private Long jobId;
	private Long taskId;
	private Blob docBlob;
	private String status;
	private XMLService xmlService;
	private AuditInfo auditInfo;
	
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Blob getDocBlob() {
		return docBlob;
	}
	public void setDocBlob(Blob docBlob) {
		this.docBlob = docBlob;
	}
	public XMLService getXmlService() {
		return xmlService;
	}
	public void setXmlService(XMLService xmlService) {
		this.xmlService = xmlService;
	}
	public AuditInfo getAuditInfo() {
		return auditInfo;
	}
	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
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
		result = prime * result + ((jobId == null) ? 0 : jobId.hashCode());
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
		TaskProcessJob other = (TaskProcessJob) obj;
		if (jobId == null) {
			if (other.jobId != null)
				return false;
		} else if (!jobId.equals(other.jobId))
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
		return "TaskProcessJob [jobId=" + jobId + ", taskId=" + taskId
				+ ", status=" + status + ", xmlService=" + xmlService
				+ ", auditInfo=" + auditInfo + "]";
	}
	
	
	
	
	
	
	

}
