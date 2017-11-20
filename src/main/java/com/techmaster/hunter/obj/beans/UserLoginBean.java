package com.techmaster.hunter.obj.beans;

import java.sql.Blob;

public class UserLoginBean {

	private Long userId;
	private java.util.Date lastLoginTime;
	private boolean blocked;
	private int faildedLoginCount;
	private Blob loginData;
	
	public UserLoginBean() {
		super();
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public int getFaildedLoginCount() {
		return faildedLoginCount;
	}
	public void setFaildedLoginCount(int faildedLoginCount) {
		this.faildedLoginCount = faildedLoginCount;
	}
	public Blob getLoginData() {
		return loginData;
	}
	public void setLoginData(Blob loginData) {
		this.loginData = loginData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (blocked ? 1231 : 1237);
		result = prime * result + faildedLoginCount;
		result = prime * result
				+ ((lastLoginTime == null) ? 0 : lastLoginTime.hashCode());
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
		UserLoginBean other = (UserLoginBean) obj;
		if (blocked != other.blocked)
			return false;
		if (faildedLoginCount != other.faildedLoginCount)
			return false;
		if (lastLoginTime == null) {
			if (other.lastLoginTime != null)
				return false;
		} else if (!lastLoginTime.equals(other.lastLoginTime))
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
		return "UserLoginBean [userId=" + userId + ", lastLoginTime="
				+ lastLoginTime + ", blocked=" + blocked
				+ ", faildedLoginCount=" + faildedLoginCount
				+ ", loginData=" + /*HunterUtility.getBlobStr(loginData)*/ "(Masked)" + "]";  
	}
	
		
	
	
}
