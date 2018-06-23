package org.tyrant.surfboard.entity;

import org.tyrant.core.utils.excel.ExcelColumn;
import org.tyrant.dao.BaseEntity;

/**
 * 滑板操作日志
 * @author xiaoyangliu
 *
 */
public class SurfboardOperationLogEntity implements BaseEntity {
	@ExcelColumn(title = "ID", columnIndex = 1)
	private String id;
	@ExcelColumn(title = "操作日期", columnIndex = 2)
	private String createTime;
	@ExcelColumn(title = "操作类型", columnIndex = 3)
	private String type;
	@ExcelColumn(title = "操作员", columnIndex = 4)
	private String userName;
	@ExcelColumn(title = "滑板ID", columnIndex = 5)
	private String surfboardId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSurfboardId() {
		return surfboardId;
	}
	public void setSurfboardId(String surfboardId) {
		this.surfboardId = surfboardId;
	}
}
