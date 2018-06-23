package org.tyrant.surfboard.dto;

import java.util.List;

public class SetUserPower {
	private String userId;
	private List<String> products;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<String> getProducts() {
		return products;
	}
	public void setProducts(List<String> products) {
		this.products = products;
	}

}
