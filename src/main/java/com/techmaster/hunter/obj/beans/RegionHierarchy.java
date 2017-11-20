package com.techmaster.hunter.obj.beans;

public class RegionHierarchy {
	
	private Long id;
	private Long beanId;
	private String name;
	private Long parent;
	private Long genParent;
	private int population;
	private int hunterPopuplation;
	private String mapDots;
	private String levelType;
	private String regionCode;
	private String city;
	private boolean hasState;
	
	private java.util.Date cretDate;
	private String createdBy;
	private java.util.Date lastUpdate;
	private String lastUpdatedBy;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getParent() {
		return parent;
	}
	public void setParent(Long parent) {
		this.parent = parent;
	}
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	public int getHunterPopuplation() {
		return hunterPopuplation;
	}
	public void setHunterPopuplation(int hunterPopuplation) {
		this.hunterPopuplation = hunterPopuplation;
	}
	public String getMapDots() {
		return mapDots;
	}
	public void setMapDots(String mapDots) {
		this.mapDots = mapDots;
	}
	public String getLevelType() {
		return levelType;
	}
	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public boolean isHasState() {
		return hasState;
	}
	public void setHasState(boolean hasState) {
		this.hasState = hasState;
	}
	public java.util.Date getCretDate() {
		return cretDate;
	}
	public void setCretDate(java.util.Date cretDate) {
		this.cretDate = cretDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public java.util.Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(java.util.Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Long getBeanId() {
		return beanId;
	}
	public void setBeanId(Long beanId) {
		this.beanId = beanId;
	}
	public Long getGenParent() {
		return genParent;
	}
	public void setGenParent(Long genParent) {
		this.genParent = genParent;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beanId == null) ? 0 : beanId.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((cretDate == null) ? 0 : cretDate.hashCode());
		result = prime * result
				+ ((genParent == null) ? 0 : genParent.hashCode());
		result = prime * result + (hasState ? 1231 : 1237);
		result = prime * result + hunterPopuplation;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastUpdate == null) ? 0 : lastUpdate.hashCode());
		result = prime * result
				+ ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
		result = prime * result
				+ ((levelType == null) ? 0 : levelType.hashCode());
		result = prime * result + ((mapDots == null) ? 0 : mapDots.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + population;
		result = prime * result
				+ ((regionCode == null) ? 0 : regionCode.hashCode());
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
		RegionHierarchy other = (RegionHierarchy) obj;
		if (beanId == null) {
			if (other.beanId != null)
				return false;
		} else if (!beanId.equals(other.beanId))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (cretDate == null) {
			if (other.cretDate != null)
				return false;
		} else if (!cretDate.equals(other.cretDate))
			return false;
		if (genParent == null) {
			if (other.genParent != null)
				return false;
		} else if (!genParent.equals(other.genParent))
			return false;
		if (hasState != other.hasState)
			return false;
		if (hunterPopuplation != other.hunterPopuplation)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastUpdate == null) {
			if (other.lastUpdate != null)
				return false;
		} else if (!lastUpdate.equals(other.lastUpdate))
			return false;
		if (lastUpdatedBy == null) {
			if (other.lastUpdatedBy != null)
				return false;
		} else if (!lastUpdatedBy.equals(other.lastUpdatedBy))
			return false;
		if (levelType == null) {
			if (other.levelType != null)
				return false;
		} else if (!levelType.equals(other.levelType))
			return false;
		if (mapDots == null) {
			if (other.mapDots != null)
				return false;
		} else if (!mapDots.equals(other.mapDots))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (population != other.population)
			return false;
		if (regionCode == null) {
			if (other.regionCode != null)
				return false;
		} else if (!regionCode.equals(other.regionCode))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RegionHierarchy [id=" + id + ", beanId=" + beanId + ", name="
				+ name + ", parent=" + parent + ", genParent=" + genParent
				+ ", population=" + population + ", hunterPopuplation="
				+ hunterPopuplation + ", mapDots=" + mapDots + ", levelType="
				+ levelType + ", regionCode=" + regionCode + ", city=" + city
				+ ", hasState=" + hasState + ", cretDate=" + cretDate
				+ ", createdBy=" + createdBy + ", lastUpdate=" + lastUpdate
				+ ", lastUpdatedBy=" + lastUpdatedBy + "]";
	}
	
	

}
