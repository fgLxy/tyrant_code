package org.tyrant.surfboard.dto;

import java.util.List;

import org.tyrant.surfboard.entity.ProductEntity;
import org.tyrant.surfboard.entity.TransactionEntity;

public class QryMenuResponseDTO {
	private List<ProductEntity> products;
	private List<TransactionEntity> trans;
	public List<ProductEntity> getProducts() {
		return products;
	}
	public void setProducts(List<ProductEntity> products) {
		this.products = products;
	}
	public List<TransactionEntity> getTrans() {
		return trans;
	}
	public void setTrans(List<TransactionEntity> trans) {
		this.trans = trans;
	}
	
}
