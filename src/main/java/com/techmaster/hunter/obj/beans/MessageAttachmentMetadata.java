package com.techmaster.hunter.obj.beans;

public class MessageAttachmentMetadata {
	
	private Long mDataId;
	private boolean embedded;
	private Long msgAttchmentBnId;
	private Long msgId;
	private String url;
	private String key;
	private String desc;
	private String msgCid;
	private String originalFileName;
	private String fileFormat;
	private String templateName;
	
	public Long getmDataId() {
		return mDataId;
	}
	public void setmDataId(Long mDataId) {
		this.mDataId = mDataId;
	}
	public boolean isEmbedded() {
		return embedded;
	}
	public void setEmbedded(boolean embedded) {
		this.embedded = embedded;
	}
	public Long getMsgAttchmentBnId() {
		return msgAttchmentBnId;
	}
	public void setMsgAttchmentBnId(Long msgAttchmentBnId) {
		this.msgAttchmentBnId = msgAttchmentBnId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getMsgCid() {
		return msgCid;
	}
	public void setMsgCid(String msgCid) {
		this.msgCid = msgCid;
	}
	public String getFileFormat() {
		return fileFormat;
	}
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
	public Long getMsgId() {
		return msgId;
	}
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mDataId == null) ? 0 : mDataId.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime
				* result
				+ ((msgAttchmentBnId == null) ? 0 : msgAttchmentBnId.hashCode());
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
		MessageAttachmentMetadata other = (MessageAttachmentMetadata) obj;
		if (mDataId == null) {
			if (other.mDataId != null)
				return false;
		} else if (!mDataId.equals(other.mDataId))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (msgAttchmentBnId == null) {
			if (other.msgAttchmentBnId != null)
				return false;
		} else if (!msgAttchmentBnId.equals(other.msgAttchmentBnId))
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
		return "MessageAttachmentMetadata [mDataId=" + mDataId + ", embedded=" + embedded
				+ ", msgAttchmentBnId=" + msgAttchmentBnId + ", msgId=" + msgId
				+ ", url=" + url + ", key=" + key + ", desc=" + desc
				+ ", msgCid=" + msgCid + ", fileFormat=" + fileFormat
				+ ", templateName=" + templateName
				+ ", originalFileName=" + originalFileName + "]";
	}
	
	
	
	
}
