package org.tyrant.surfboard.entity;

import org.tyrant.core.utils.excel.ExcelColumn;
import org.tyrant.dao.BaseEntity;

public class SurfboardEntity implements BaseEntity {
	@ExcelColumn(title = "id", columnIndex = 1)
	private String id;
	@ExcelColumn(title = "name", columnIndex = 1)
	private String name;
	@ExcelColumn(title = "type", columnIndex = 1)
	private String type;
	@ExcelColumn(title = "size", columnIndex = 1)
	private String size;
	@ExcelColumn(title = "num", columnIndex = 1)
	private String num;
	@ExcelColumn(title = "productionDate", columnIndex = 1)
	private String productionDate;
	@ExcelColumn(title = "storage", columnIndex = 1)
	private String storage;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}
	public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
	}
	
	
}
