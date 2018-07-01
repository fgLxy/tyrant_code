package org.tyrant.surfboard.dto;

import java.util.List;

public class SurfboardStateListDTO {
	private Integer pageNo;
	private Integer pageSize;
	private String orderKey;
	private Boolean ascFlag;
	private List<String> status;
	private List<String> type;
	private List<String> size;
	private List<String> storage;
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getOrderKey() {
		return orderKey;
	}
	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}
	public Boolean getAscFlag() {
		return ascFlag;
	}
	public void setAscFlag(Boolean ascFlag) {
		this.ascFlag = ascFlag;
	}
	public List<String> getStatus() {
		return status;
	}
	public void setStatus(List<String> status) {
		this.status = status;
	}
	public List<String> getType() {
		return type;
	}
	public void setType(List<String> type) {
		this.type = type;
	}
	public List<String> getSize() {
		return size;
	}
	public void setSize(List<String> size) {
		this.size = size;
	}
	public List<String> getStorage() {
		return storage;
	}
	public void setStorage(List<String> storage) {
		this.storage = storage;
	}
	
}
