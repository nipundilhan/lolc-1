package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.enums.ServiceStatus;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ColInetragationAssetsDetailsRequestResource {
	
	private String value;
	private String messages;
	private String code;
	private String username;
	private String assetEntityId;
	private ServiceStatus serviceStatus;

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getMessages() {
		return messages;
	}
	public void setMessages(String messages) {
		this.messages = messages;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAssetEntityId() {
		return assetEntityId;
	}
	public void setAssetEntityId(String assetEntityId) {
		this.assetEntityId = assetEntityId;
	}
	
	
	
}
