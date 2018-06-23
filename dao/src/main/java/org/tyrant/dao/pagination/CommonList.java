package org.tyrant.dao.pagination;

import java.util.List;

public class CommonList<T> {
	//当前条数
	private Integer recNum;
	//页号
	private Integer pageNo;
	//页尺寸
	private Integer pageSize;
	
	private List<T> records;
	
	public CommonList(Integer recNum, Integer pageNo, Integer pageSize) {
		this.recNum = recNum;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public Integer getRecNum() {
		return recNum;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public void setRecNum(Integer recNum) {
		this.recNum = recNum;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
