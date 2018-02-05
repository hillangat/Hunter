package com.techmaster.hunter.angular.data;

public class HunterTableConfig {
	
	private boolean sortable;
    private boolean show;
    private boolean currentOrder;
    private boolean actionCol;
    private boolean checkBox;
	private int index;
    private String headerId;
    private String dataId;
    private String displayName;
    private String bootstrapIconName;
    private String dataType;
    private String actionColIconName;
    private String width;
    
	public boolean isSortable() {
		return sortable;
	}
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public boolean isCurrentOrder() {
		return currentOrder;
	}
	public void setCurrentOrder(boolean currentOrder) {
		this.currentOrder = currentOrder;
	}
	public boolean isActionCol() {
		return actionCol;
	}
	public void setActionCol(boolean isActionCol) {
		this.actionCol = isActionCol;
	}
	public boolean isCheckBox() {
		return checkBox;
	}
	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getHeaderId() {
		return headerId;
	}
	public void setHeaderId(String headerId) {
		this.headerId = headerId;
	}
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getBootstrapIconName() {
		return bootstrapIconName;
	}
	public void setBootstrapIconName(String bootstrapIconName) {
		this.bootstrapIconName = bootstrapIconName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getActionColIconName() {
		return actionColIconName;
	}
	public void setActionColIconName(String actionColIconName) {
		this.actionColIconName = actionColIconName;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (actionCol ? 1231 : 1237);
		result = prime * result + ((actionColIconName == null) ? 0 : actionColIconName.hashCode());
		result = prime * result + ((bootstrapIconName == null) ? 0 : bootstrapIconName.hashCode());
		result = prime * result + (checkBox ? 1231 : 1237);
		result = prime * result + (currentOrder ? 1231 : 1237);
		result = prime * result + ((dataId == null) ? 0 : dataId.hashCode());
		result = prime * result + ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
		result = prime * result + ((headerId == null) ? 0 : headerId.hashCode());
		result = prime * result + index;
		result = prime * result + (show ? 1231 : 1237);
		result = prime * result + (sortable ? 1231 : 1237);
		result = prime * result + ((width == null) ? 0 : width.hashCode());
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
		HunterTableConfig other = (HunterTableConfig) obj;
		if (actionCol != other.actionCol)
			return false;
		if (actionColIconName == null) {
			if (other.actionColIconName != null)
				return false;
		} else if (!actionColIconName.equals(other.actionColIconName))
			return false;
		if (bootstrapIconName == null) {
			if (other.bootstrapIconName != null)
				return false;
		} else if (!bootstrapIconName.equals(other.bootstrapIconName))
			return false;
		if (checkBox != other.checkBox)
			return false;
		if (currentOrder != other.currentOrder)
			return false;
		if (dataId == null) {
			if (other.dataId != null)
				return false;
		} else if (!dataId.equals(other.dataId))
			return false;
		if (dataType == null) {
			if (other.dataType != null)
				return false;
		} else if (!dataType.equals(other.dataType))
			return false;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		if (headerId == null) {
			if (other.headerId != null)
				return false;
		} else if (!headerId.equals(other.headerId))
			return false;
		if (index != other.index)
			return false;
		if (show != other.show)
			return false;
		if (sortable != other.sortable)
			return false;
		if (width == null) {
			if (other.width != null)
				return false;
		} else if (!width.equals(other.width))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "HunterTableConfig [sortable=" + sortable + ", show=" + show + ", currentOrder=" + currentOrder
				+ ", actionCol=" + actionCol + ", checkBox=" + checkBox + ", index=" + index + ", headerId=" + headerId
				+ ", dataId=" + dataId + ", displayName=" + displayName + ", bootstrapIconName=" + bootstrapIconName
				+ ", dataType=" + dataType + ", actionColIconName=" + actionColIconName + ", width=" + width + "]";
	}
    
    
}
