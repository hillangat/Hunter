package com.techmaster.hunter.obj.beans;

import java.util.HashSet;
import java.util.Set;


public class SocialMessage extends Message  {

	private Long socialMsgId;
	private String externalId;
	private String mediaType;
	private String description;
	private String socialPost;
	private String socialPostType;
	private String originalFileFormat;
	private String socialPostAction;
	private boolean useRemoteMedia;

	private HunterSocialMedia socialMedia;
	private Set<HunterSocialGroup> hunterSocialGroups = new HashSet<HunterSocialGroup>();
	private HunterSocialApp defaultSocialApp = new HunterSocialApp();
	
	
	public String getSocialPostType() {
		return socialPostType;
	}
	public void setSocialPostType(String socialPostType) {
		this.socialPostType = socialPostType;
	}
	public Long getSocialMsgId() {
		return socialMsgId;
	}
	public void setSocialMsgId(Long socialMsgId) {
		this.socialMsgId = socialMsgId;
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
	public String getOriginalFileFormat() {
		return originalFileFormat;
	}
	public void setOriginalFileFormat(String originalFileFormat) {
		this.originalFileFormat = originalFileFormat;
	}
	public HunterSocialMedia getSocialMedia() {
		return socialMedia;
	}
	public void setSocialMedia(HunterSocialMedia socialMedia) {
		this.socialMedia = socialMedia;
	}
	public Set<HunterSocialGroup> getHunterSocialGroups() {
		return hunterSocialGroups;
	}
	public void setHunterSocialGroups(Set<HunterSocialGroup> hunterSocialGroups) {
		this.hunterSocialGroups = hunterSocialGroups;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public HunterSocialApp getDefaultSocialApp() {
		return defaultSocialApp;
	}
	public void setDefaultSocialApp(HunterSocialApp defaultSocialApp) {
		this.defaultSocialApp = defaultSocialApp;
	}
	public String getSocialPostAction() {
		return socialPostAction;
	}
	public void setSocialPostAction(String socialPostAction) {
		this.socialPostAction = socialPostAction;
	}
	public boolean isUseRemoteMedia() {
		return useRemoteMedia;
	}
	public void setUseRemoteMedia(boolean useRemoteMedia) {
		this.useRemoteMedia = useRemoteMedia;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime
				* result
				+ ((hunterSocialGroups == null) ? 0 : hunterSocialGroups
						.hashCode());
		result = prime * result
				+ ((mediaType == null) ? 0 : mediaType.hashCode());
		result = prime
				* result
				+ ((originalFileFormat == null) ? 0 : originalFileFormat
						.hashCode());
		result = prime * result
				+ ((socialMedia == null) ? 0 : socialMedia.hashCode());
		result = prime * result
				+ ((socialMsgId == null) ? 0 : socialMsgId.hashCode());
		result = prime * result
				+ ((socialPost == null) ? 0 : socialPost.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SocialMessage other = (SocialMessage) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (hunterSocialGroups == null) {
			if (other.hunterSocialGroups != null)
				return false;
		} else if (!hunterSocialGroups.equals(other.hunterSocialGroups))
			return false;
		if (mediaType == null) {
			if (other.mediaType != null)
				return false;
		} else if (!mediaType.equals(other.mediaType))
			return false;
		if (originalFileFormat == null) {
			if (other.originalFileFormat != null)
				return false;
		} else if (!originalFileFormat.equals(other.originalFileFormat))
			return false;
		if (socialMedia == null) {
			if (other.socialMedia != null)
				return false;
		} else if (!socialMedia.equals(other.socialMedia))
			return false;
		if (socialMsgId == null) {
			if (other.socialMsgId != null)
				return false;
		} else if (!socialMsgId.equals(other.socialMsgId))
			return false;
		if (socialPost == null) {
			if (other.socialPost != null)
				return false;
		} else if (!socialPost.equals(other.socialPost))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "SocialMessage [socialMsgId=" + socialMsgId + ", mediaType="
				+ mediaType + ", description=" + description + ", socialPost="
				+ socialPost + ", originalFileFormat=" + originalFileFormat
				+ ", socialMedia=" + socialMedia
				+ ", externalId=" + externalId
				+ ", socialPostType=" + socialPostType
				+ ", defaultSocialApp=" + defaultSocialApp
				+ ", socialPostAction=" + socialPostAction
				+ ", useRemoteMedia=" + useRemoteMedia
				+ ", hunterSocialGroups=" + hunterSocialGroups + "]";
	}
	

	
	
	
}

