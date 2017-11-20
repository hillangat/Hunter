package com.techmaster.hunter.obj.beans;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

public class HunterSocialProfile {
	
	private Long profileId;
	private String userName;
	private String password;
	private String socialType;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private Blob profilePic;
	private String hunterAdmin;
	private String profileOwner;
	private String publicURL;
	private AuditInfo auditInfo;

	private Set<HunterSocialGroup> socialGroups = new HashSet<>();

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSocialType() {
		return socialType;
	}

	public void setSocialType(String socialType) {
		this.socialType = socialType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Blob getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(Blob profilePic) {
		this.profilePic = profilePic;
	}

	public String getHunterAdmin() {
		return hunterAdmin;
	}

	public void setHunterAdmin(String hunterAdmin) {
		this.hunterAdmin = hunterAdmin;
	}

	public String getProfileOwner() {
		return profileOwner;
	}

	public void setProfileOwner(String profileOwner) {
		this.profileOwner = profileOwner;
	}

	public String getPublicURL() {
		return publicURL;
	}

	public void setPublicURL(String publicURL) {
		this.publicURL = publicURL;
	}

	public AuditInfo getAuditInfo() {
		return auditInfo;
	}

	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}

	public Set<HunterSocialGroup> getSocialGroups() {
		return socialGroups;
	}

	public void setSocialGroups(Set<HunterSocialGroup> socialGroups) {
		this.socialGroups = socialGroups;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((hunterAdmin == null) ? 0 : hunterAdmin.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((profileId == null) ? 0 : profileId.hashCode());
		result = prime * result
				+ ((profileOwner == null) ? 0 : profileOwner.hashCode());
		result = prime * result
				+ ((publicURL == null) ? 0 : publicURL.hashCode());
		result = prime * result
				+ ((socialType == null) ? 0 : socialType.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
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
		HunterSocialProfile other = (HunterSocialProfile) obj;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (hunterAdmin == null) {
			if (other.hunterAdmin != null)
				return false;
		} else if (!hunterAdmin.equals(other.hunterAdmin))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (profileId == null) {
			if (other.profileId != null)
				return false;
		} else if (!profileId.equals(other.profileId))
			return false;
		if (profileOwner == null) {
			if (other.profileOwner != null)
				return false;
		} else if (!profileOwner.equals(other.profileOwner))
			return false;
		if (publicURL == null) {
			if (other.publicURL != null)
				return false;
		} else if (!publicURL.equals(other.publicURL))
			return false;
		if (socialType == null) {
			if (other.socialType != null)
				return false;
		} else if (!socialType.equals(other.socialType))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	
	
	@Override
	public String toString() {
		return "HunterSocialProfile [profileId=" + profileId + ", userName="
				+ userName + ", password=" + password + ", socialType="
				+ socialType + ", firstName=" + firstName + ", lastName="
				+ lastName + ", emailAddress=" + emailAddress 
				+ ", hunterAdmin=" + hunterAdmin
				+ ", profileOwner=" + profileOwner + ", publicURL=" + publicURL
				+ ", auditInfo=" + auditInfo + ", socialGroups=" + socialGroups
				+ "]";
	}
	
	
	
	
}
