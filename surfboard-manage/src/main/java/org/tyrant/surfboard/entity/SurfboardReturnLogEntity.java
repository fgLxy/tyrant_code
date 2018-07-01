package org.tyrant.surfboard.entity;

import java.math.BigDecimal;

import org.tyrant.core.utils.excel.ExcelColumn;
import org.tyrant.dao.BaseEntity;

public class SurfboardReturnLogEntity implements BaseEntity {
	private String id;
	@ExcelColumn(title="data.manage.id", columnIndex = 1)
	private String surfboardId;
	@ExcelColumn(title="data.manage.storage", columnIndex = 2)
	private String storage;
	private String createDate;
	private String createTime;
	@ExcelColumn(title="data.manage.name", columnIndex = 3)
	private String name;
	@ExcelColumn(title="data.manage.type", columnIndex = 4)
	private String type;
	@ExcelColumn(title="data.manage.size", columnIndex = 5)
	private String size;
	@ExcelColumn(title="data.manage.seq", columnIndex = 6)
	private String num;
	@ExcelColumn(title="data.manage.state", columnIndex = 7)
	private String rentStatus;
	@ExcelColumn(title="data.manage.leasetype", columnIndex = 8)
	private String rentType;
	@ExcelColumn(title="data.manage.leasetime", columnIndex = 9)
	private String rentLength;
	@ExcelColumn(title="data.manage.leaseunitprice", columnIndex = 10)
	private BigDecimal eachPrice;
	@ExcelColumn(title="data.manage.outtime", columnIndex = 11)
	private String outTime;
	@ExcelColumn(title="data.manage.estimateintime", columnIndex = 12)
	private String returnTime;
	@ExcelColumn(title="data.manage.income", columnIndex = 13)
	private BigDecimal income;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSurfboardId() {
		return surfboardId;
	}
	public void setSurfboardId(String surfboardId) {
		this.surfboardId = surfboardId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getRentStatus() {
		return rentStatus;
	}
	public void setRentStatus(String rentStatus) {
		this.rentStatus = rentStatus;
	}
	public String getRentType() {
		return rentType;
	}
	public void setRentType(String rentType) {
		this.rentType = rentType;
	}
	public String getRentLength() {
		return rentLength;
	}
	public void setRentLength(String rentLength) {
		this.rentLength = rentLength;
	}
	public BigDecimal getEachPrice() {
		return eachPrice;
	}
	public void setEachPrice(BigDecimal eachPrice) {
		this.eachPrice = eachPrice;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
	public BigDecimal getIncome() {
		return income;
	}
	public void setIncome(BigDecimal income) {
		this.income = income;
	}
	public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
	}
	
}
