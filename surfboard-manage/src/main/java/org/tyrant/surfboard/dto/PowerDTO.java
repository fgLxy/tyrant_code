package org.tyrant.surfboard.dto;

import java.util.Set;

public class PowerDTO {
	private String groupName;
	private Set<String> powerList;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Set<String> getPowerList() {
		return powerList;
	}
	public void setPowerList(Set<String> powerList) {
		this.powerList = powerList;
	}
	
}
