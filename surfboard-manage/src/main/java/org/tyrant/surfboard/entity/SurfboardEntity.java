package org.tyrant.surfboard.entity;

import org.tyrant.core.utils.excel.ExcelColumn;
import org.tyrant.dao.BaseEntity;

public class SurfboardEntity implements BaseEntity {
	@ExcelColumn(title = "ID", columnIndex = 1)
	private String id;
	@ExcelColumn(title = "状态", columnIndex = 2)
	private String status;
	@ExcelColumn(title = "录入时间", columnIndex = 3)
	private String createTime;
	@ExcelColumn(title = "最后更新时间", columnIndex = 4)
	private String updateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
