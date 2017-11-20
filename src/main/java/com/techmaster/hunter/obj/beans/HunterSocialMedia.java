package com.techmaster.hunter.obj.beans;

import java.sql.Blob;


public class HunterSocialMedia {
	
	private Long mediaId;
	private String mediaName;
	private String mediaType;
	private String mediaDescription;
	private String clientName;
	private String dstrbtnDrctns;
	private boolean hunterOwned;
	private int byteSize;
	private Blob mediaData;
	private String userSpecs;
	private String fileFormat;
	private String originalFileName;
	private String mimeType;
	private String channelType;
	private String remoteURL;
	private String localURL;
	private double durationInSecs;
	private String mediaSuffix;
	private int width;
	private int height;
	private AuditInfo auditInfo;
	
	
	public Long getMediaId() {
		return mediaId;
	}
	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}
	public String getMediaName() {
		return mediaName;
	}
	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public String getMediaDescription() {
		return mediaDescription;
	}
	public void setMediaDescription(String mediaDescription) {
		this.mediaDescription = mediaDescription;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public boolean isHunterOwned() {
		return hunterOwned;
	}
	public void setHunterOwned(boolean hunterOwned) {
		this.hunterOwned = hunterOwned;
	}
	public int getByteSize() {
		return byteSize;
	}
	public void setByteSize(int byteSize) {
		this.byteSize = byteSize;
	}
	public Blob getMediaData() {
		return mediaData;
	}
	public void setMediaData(Blob mediaData) {
		this.mediaData = mediaData;
	}
	public String getUserSpecs() {
		return userSpecs;
	}
	public void setUserSpecs(String userSpecs) {
		this.userSpecs = userSpecs;
	}
	public String getFileFormat() {
		return fileFormat;
	}
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public double getDurationInSecs() {
		return durationInSecs;
	}
	public void setDurationInSecs(double durationInSecs) {
		this.durationInSecs = durationInSecs;
	}
	public String getMediaSuffix() {
		return mediaSuffix;
	}
	public void setMediaSuffix(String mediaSuffix) {
		this.mediaSuffix = mediaSuffix;
	}
	public AuditInfo getAuditInfo() {
		return auditInfo;
	}
	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}
	public String getDstrbtnDrctns() {
		return dstrbtnDrctns;
	}
	public void setDstrbtnDrctns(String dstrbtnDrctns) {
		this.dstrbtnDrctns = dstrbtnDrctns;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getRemoteURL() {
		return remoteURL;
	}
	public void setRemoteURL(String remoteURL) {
		this.remoteURL = remoteURL;
	}
	public String getLocalURL() {
		return localURL;
	}
	public void setLocalURL(String localURL) {
		this.localURL = localURL;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((channelType == null) ? 0 : channelType.hashCode());
		result = prime * result
				+ ((clientName == null) ? 0 : clientName.hashCode());
		result = prime * result
				+ ((dstrbtnDrctns == null) ? 0 : dstrbtnDrctns.hashCode());
		result = prime * result
				+ ((fileFormat == null) ? 0 : fileFormat.hashCode());
		result = prime
				* result
				+ ((mediaDescription == null) ? 0 : mediaDescription.hashCode());
		result = prime * result + ((mediaId == null) ? 0 : mediaId.hashCode());
		result = prime * result
				+ ((mediaName == null) ? 0 : mediaName.hashCode());
		result = prime * result
				+ ((mediaSuffix == null) ? 0 : mediaSuffix.hashCode());
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
		HunterSocialMedia other = (HunterSocialMedia) obj;
		if (channelType == null) {
			if (other.channelType != null)
				return false;
		} else if (!channelType.equals(other.channelType))
			return false;
		if (clientName == null) {
			if (other.clientName != null)
				return false;
		} else if (!clientName.equals(other.clientName))
			return false;
		if (dstrbtnDrctns == null) {
			if (other.dstrbtnDrctns != null)
				return false;
		} else if (!dstrbtnDrctns.equals(other.dstrbtnDrctns))
			return false;
		if (fileFormat == null) {
			if (other.fileFormat != null)
				return false;
		} else if (!fileFormat.equals(other.fileFormat))
			return false;
		if (mediaDescription == null) {
			if (other.mediaDescription != null)
				return false;
		} else if (!mediaDescription.equals(other.mediaDescription))
			return false;
		if (mediaId == null) {
			if (other.mediaId != null)
				return false;
		} else if (!mediaId.equals(other.mediaId))
			return false;
		if (mediaName == null) {
			if (other.mediaName != null)
				return false;
		} else if (!mediaName.equals(other.mediaName))
			return false;
		if (mediaSuffix == null) {
			if (other.mediaSuffix != null)
				return false;
		} else if (!mediaSuffix.equals(other.mediaSuffix))
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
		return "HunterSocialMedia [mediaId=" + mediaId + ", mediaName="
				+ mediaName + ", mediaType=" + mediaType
				+ ", mediaDescription=" + mediaDescription + ", clientName="
				+ clientName + ", dstrbtnDrctns=" + dstrbtnDrctns
				+ ", hunterOwned=" + hunterOwned + ", byteSize=" + byteSize
				+ ", userSpecs=" + userSpecs + ", fileFormat=" + fileFormat
				+ ", originalFileName=" + originalFileName + ", mimeType="
				+ mimeType + ", channelType=" + channelType
				+ ", durationInSecs=" + durationInSecs + ", mediaSuffix="
				+ mediaSuffix + ", width=" + width + ", height=" + height
				+ ", remoteURL=" + remoteURL + ", localURL=" + localURL
				+ ", auditInfo=" + auditInfo + "]";
	}
	
	
	
	
	
	
	
	
	
}