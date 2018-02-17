package com.techmaster.hunter.obj.beans;

public class HunterCacheRefresh {
	
	private String name;
	private String key;
	private boolean selected;
	private boolean refreshing;
	private boolean disabled;
	private boolean retired;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public boolean isRefreshing() {
		return refreshing;
	}
	public void setRefreshing(boolean refreshing) {
		this.refreshing = refreshing;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (disabled ? 1231 : 1237);
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (refreshing ? 1231 : 1237);
		result = prime * result + (retired ? 1231 : 1237);
		result = prime * result + (selected ? 1231 : 1237);
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
		HunterCacheRefresh other = (HunterCacheRefresh) obj;
		if (disabled != other.disabled)
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (refreshing != other.refreshing)
			return false;
		if (retired != other.retired)
			return false;
		if (selected != other.selected)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "HunterCacheRefresh [name=" + name + ", key=" + key + ", selected=" + selected + ", refreshing="
				+ refreshing + ", disabled=" + disabled + ", retired=" + retired + "]";
	}
	
	
	
	

}
