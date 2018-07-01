package org.tyrant.surfboard.dto;

public class UpdatePowerDTO {
	private String groupName;
	private String productName;
	private Boolean newValue;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Boolean getNewValue() {
		return newValue;
	}
	public void setNewValue(Boolean newValue) {
		this.newValue = newValue;
	}
}
