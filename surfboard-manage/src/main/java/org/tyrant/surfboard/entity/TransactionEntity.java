package org.tyrant.surfboard.entity;

import org.tyrant.dao.BaseEntity;

/**
 * 接口
 * @author xiaoyangliu
 *
 */
public class TransactionEntity implements BaseEntity {
	private String name;
	private String productName;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
}
