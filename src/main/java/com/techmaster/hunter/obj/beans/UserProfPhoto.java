package com.techmaster.hunter.obj.beans;

import java.sql.Blob;

public class UserProfPhoto {
	
	private Long photoId;
	private Blob photoBlob;
	private int size;
	private String sizeFormat;
	private String photoFormat;
	private String originalName;
	private Long userId;
	private String category;
	private AuditInfo auditInfo;
	
	public Long getPhotoId() {
		return photoId;
	}
	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getSizeFormat() {
		return sizeFormat;
	}
	public void setSizeFormat(String sizeFormat) {
		this.sizeFormat = sizeFormat;
	}
	public String getPhotoFormat() {
		return photoFormat;
	}
	public void setPhotoFormat(String photoFormat) {
		this.photoFormat = photoFormat;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public AuditInfo getAuditInfo() {
		return auditInfo;
	}
	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}
	
	public Blob getPhotoBlob() {
		return photoBlob;
	}
	public void setPhotoBlob(Blob photoBlob) {
		this.photoBlob = photoBlob;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((auditInfo == null) ? 0 : auditInfo.hashCode());
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result
				+ ((originalName == null) ? 0 : originalName.hashCode());
		result = prime * result
				+ ((photoFormat == null) ? 0 : photoFormat.hashCode());
		result = prime * result + ((photoId == null) ? 0 : photoId.hashCode());
		result = prime * result + size;
		result = prime * result
				+ ((sizeFormat == null) ? 0 : sizeFormat.hashCode());
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
		UserProfPhoto other = (UserProfPhoto) obj;
		if (auditInfo == null) {
			if (other.auditInfo != null)
				return false;
		} else if (!auditInfo.equals(other.auditInfo))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (originalName == null) {
			if (other.originalName != null)
				return false;
		} else if (!originalName.equals(other.originalName))
			return false;
		if (photoFormat == null) {
			if (other.photoFormat != null)
				return false;
		} else if (!photoFormat.equals(other.photoFormat))
			return false;
		if (photoId == null) {
			if (other.photoId != null)
				return false;
		} else if (!photoId.equals(other.photoId))
			return false;
		if (size != other.size)
			return false;
		if (sizeFormat == null) {
			if (other.sizeFormat != null)
				return false;
		} else if (!sizeFormat.equals(other.sizeFormat))
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
		return "UserProfPhoto [photoId=" + photoId + ", size=" + size
				+ ", sizeFormat=" + sizeFormat + ", photoFormat=" + photoFormat
				+ ", originalName=" + originalName + ", userId=" + userId
				+ ", category=" + category + ", auditInfo=" + auditInfo + "]";
	}
	
	
	
	
	
	
	

}
