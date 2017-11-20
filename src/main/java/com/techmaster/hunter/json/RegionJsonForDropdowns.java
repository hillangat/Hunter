package com.techmaster.hunter.json;

public class RegionJsonForDropdowns {

	private Long regionId;
	private String regionName;
	
	public Long getRegionId() {
		return regionId;
	}
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((regionId == null) ? 0 : regionId.hashCode());
		result = prime * result
				+ ((regionName == null) ? 0 : regionName.hashCode());
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
		RegionJsonForDropdowns other = (RegionJsonForDropdowns) obj;
		if (regionId == null) {
			if (other.regionId != null)
				return false;
		} else if (!regionId.equals(other.regionId))
			return false;
		if (regionName == null) {
			if (other.regionName != null)
				return false;
		} else if (!regionName.equals(other.regionName))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "RegionJsonForDropdowns [regionId=" + regionId + ", regionName="
				+ regionName + "]";
	}
	
	
	
	
}
