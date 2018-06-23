package org.tyrant.surfboard.entity;

import org.tyrant.dao.BaseEntity;

/**
 * 产品
 * @author xiaoyangliu
 *
 */
public class ProductEntity implements BaseEntity {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
