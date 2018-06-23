package org.tyrant.surfboard.entity;

import org.tyrant.dao.BaseEntity;

public class UserProductEntity implements BaseEntity {
	private String userId;
	private String productName;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
}
