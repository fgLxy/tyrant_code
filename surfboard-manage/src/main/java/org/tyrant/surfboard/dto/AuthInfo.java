package org.tyrant.surfboard.dto;

import java.util.HashSet;
import java.util.Set;

public class AuthInfo {
	private String token;
	private Boolean authFlag;
	private String openId;
	private String sessionKey;
	private String userId;
	private String name;
	private String unionId;
	private String phoneNumber;
	private Set<String> transCodes;
	
	public AuthInfo() {
		this.transCodes = new HashSet<>();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getAuthFlag() {
		return authFlag;
	}

	public void setAuthFlag(Boolean authFlag) {
		this.authFlag = authFlag;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<String> getTransCodes() {
		return transCodes;
	}

	public void setTransCodes(Set<String> transCodes) {
		this.transCodes = transCodes;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
}
