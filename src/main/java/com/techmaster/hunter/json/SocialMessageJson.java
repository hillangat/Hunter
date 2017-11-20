package com.techmaster.hunter.json;

import com.techmaster.hunter.util.HunterUtility;

public class SocialMessageJson {
	
	private long socialMsgId;
	private String externalId;
	private String mediaType;
	private String description;
	private String socialPost;
	private String socialPostType;
	private String socialPostAction;
	private Long defaultSocialAppId;
	private Long[] hunterSocialGroupsIds;
	private boolean isUseRemoteMedia;
	private String remoteURL;
	
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSocialPost() {
		return socialPost;
	}
	public void setSocialPost(String socialPost) {
		this.socialPost = socialPost;
	}
	public String getSocialPostType() {
		return socialPostType;
	}
	public void setSocialPostType(String socialPostType) {
		this.socialPostType = socialPostType;
	}
	public String getSocialPostAction() {
		return socialPostAction;
	}
	public void setSocialPostAction(String socialPostAction) {
		this.socialPostAction = socialPostAction;
	}
	public Long getDefaultSocialAppId() {
		return defaultSocialAppId;
	}
	public void setDefaultSocialAppId(Long defaultSocialAppId) {
		this.defaultSocialAppId = defaultSocialAppId;
	}
	public boolean isUseRemoteMedia() {
		return isUseRemoteMedia;
	}
	public void setUseRemoteMedia(boolean isUseRemoteMedia) {
		this.isUseRemoteMedia = isUseRemoteMedia;
	}
	public String getRemoteURL() {
		return remoteURL;
	}
	public void setRemoteURL(String remoteURL) {
		this.remoteURL = remoteURL;
	}
	public long getSocialMsgId() {
		return socialMsgId;
	}
	public void setSocialMsgId(long socialMsgId) {
		this.socialMsgId = socialMsgId;
	}
	public Long[] getHunterSocialGroupsIds() {
		return hunterSocialGroupsIds;
	}
	public void setHunterSocialGroupsIds(Long[] hunterSocialGroupsIds) {
		this.hunterSocialGroupsIds = hunterSocialGroupsIds;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (socialMsgId ^ (socialMsgId >>> 32));
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
		SocialMessageJson other = (SocialMessageJson) obj;
		if (socialMsgId != other.socialMsgId)
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "SocialMessageJson [socialMsgId=" + socialMsgId + ", externalId=" + externalId + ", mediaType="
				+ mediaType + ", description=" + description + ", socialPost=" + socialPost + ", socialPostType="
				+ socialPostType + ", socialPostAction=" + socialPostAction + ", defaultSocialAppId="
				+ defaultSocialAppId + ", socialGroupIds=" + HunterUtility.getCommaDelimitedStrings(hunterSocialGroupsIds) + ", isUseRemoteMedia=" 
				+ isUseRemoteMedia + ", remoteURL=" + remoteURL + "]";
	}
	
	
	
	

}
