package com.techmaster.hunter.angular.grid;

import java.util.Arrays;

public class GridDataQueryReq {
	
	private GridFieldUserInput filterBy[];
	private GridFieldUserInput orderBy[];
	private int pageNo;
	private int pageSize;
	private String reference;
	
	public GridFieldUserInput[] getFilterBy() {
		return filterBy;
	}
	public void setFilterBy(GridFieldUserInput[] filterBy) {
		this.filterBy = filterBy;
	}
	public GridFieldUserInput[] getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(GridFieldUserInput[] orderBy) {
		this.orderBy = orderBy;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(filterBy);
		result = prime * result + Arrays.hashCode(orderBy);
		result = prime * result + pageNo;
		result = prime * result + pageSize;
		result = prime * result + ((reference == null) ? 0 : reference.hashCode());
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
		GridDataQueryReq other = (GridDataQueryReq) obj;
		if (!Arrays.equals(filterBy, other.filterBy))
			return false;
		if (!Arrays.equals(orderBy, other.orderBy))
			return false;
		if (pageNo != other.pageNo)
			return false;
		if (pageSize != other.pageSize)
			return false;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GridDataQueryReq [filterBy=" + Arrays.toString(filterBy) + ", orderBy=" + Arrays.toString(orderBy)
				+ ", pageNo=" + pageNo + ", pageSize=" + pageSize + ", reference=" + reference + "]";
	}
	
	
	
	
}
