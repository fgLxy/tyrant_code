package org.tyrant.surfboard.dto;

import java.math.BigDecimal;

import org.tyrant.surfboard.entity.SurfboardEntity;

public class SurfboardStateItemDTO extends SurfboardEntity {
	private String id;
	private String rentStatus;
	private String rentType;
	private String rentLength;
	private BigDecimal eachPrice;
	private String outTime;
	private String returnTime;
	private String customName;
	private String customNationality;
	private String customPassport;
	private String customPhone;
	private String customEmail;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getCustomName() {
		return customName;
	}
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	public String getCustomNationality() {
		return customNationality;
	}
	public void setCustomNationality(String customNationality) {
		this.customNationality = customNationality;
	}
	public String getCustomPassport() {
		return customPassport;
	}
	public void setCustomPassport(String customPassport) {
		this.customPassport = customPassport;
	}
	public String getCustomPhone() {
		return customPhone;
	}
	public void setCustomPhone(String customPhone) {
		this.customPhone = customPhone;
	}
	public String getCustomEmail() {
		return customEmail;
	}
	public void setCustomEmail(String customEmail) {
		this.customEmail = customEmail;
	}
}
