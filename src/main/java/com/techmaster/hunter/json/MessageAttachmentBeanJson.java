package com.techmaster.hunter.json;


public class MessageAttachmentBeanJson {

	private Long beanId;
	private String beanName;
	private String beanDesc;
	private String originalFileName;
	private String fileFormat;
	private int fileWidth;
	private int fileHeight;
	private int fileSize;
	private String cretDate;
	private String createdBy;
	private String lastUpdate;
	private String lastUpdatedBy;
	private String cid;
	
	public Long getBeanId() {
		return beanId;
	}
	public void setBeanId(Long beanId) {
		this.beanId = beanId;
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String getBeanDesc() {
		return beanDesc;
	}
	public void setBeanDesc(String beanDesc) {
		this.beanDesc = beanDesc;
	}
	public String getFileFormat() {
		return fileFormat;
	}
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
	public int getFileWidth() {
		return fileWidth;
	}
	public void setFileWidth(int fileWidth) {
		this.fileWidth = fileWidth;
	}
	public int getFileHeight() {
		return fileHeight;
	}
	public void setFileHeight(int fileHeight) {
		this.fileHeight = fileHeight;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
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
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((beanDesc == null) ? 0 : beanDesc.hashCode());
		result = prime * result + ((beanId == null) ? 0 : beanId.hashCode());
		result = prime * result
				+ ((beanName == null) ? 0 : beanName.hashCode());
		result = prime * result
				+ ((fileFormat == null) ? 0 : fileFormat.hashCode());
		result = prime * result + fileHeight;
		result = prime * result + fileSize;
		result = prime * result + fileWidth;
		result = prime
				* result
				+ ((originalFileName == null) ? 0 : originalFileName.hashCode());
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
		MessageAttachmentBeanJson other = (MessageAttachmentBeanJson) obj;
		if (beanDesc == null) {
			if (other.beanDesc != null)
				return false;
		} else if (!beanDesc.equals(other.beanDesc))
			return false;
		if (beanId == null) {
			if (other.beanId != null)
				return false;
		} else if (!beanId.equals(other.beanId))
			return false;
		if (beanName == null) {
			if (other.beanName != null)
				return false;
		} else if (!beanName.equals(other.beanName))
			return false;
		if (fileFormat == null) {
			if (other.fileFormat != null)
				return false;
		} else if (!fileFormat.equals(other.fileFormat))
			return false;
		if (fileHeight != other.fileHeight)
			return false;
		if (fileSize != other.fileSize)
			return false;
		if (fileWidth != other.fileWidth)
			return false;
		if (originalFileName == null) {
			if (other.originalFileName != null)
				return false;
		} else if (!originalFileName.equals(other.originalFileName))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "MessageAttachmentBeanJson [beanId=" + beanId + ", beanName="
				+ beanName + ", beanDesc=" + beanDesc + ", originalFileName="
				+ originalFileName + ", fileFormat=" + fileFormat
				+ ", fileWidth=" + fileWidth + ", fileHeight=" + fileHeight
				+ ", fileSize=" + fileSize + ", cretDate=" + cretDate
				+ ", createdBy=" + createdBy + ", lastUpdate=" + lastUpdate
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", cid=" + cid + "]";
	}
	
	
	
	
	
	
}
