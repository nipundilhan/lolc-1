package com.fusionx.lending.product.resources;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.product.enums.ServiceStatus;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CommonBranchResponseResource {
	
	private String id;
	private String brhCode;
	private String name;
	private String brhDesc;
	private String brhStatus;
	private ServiceStatus serviceStatus;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public String getBrhCode() {
		return brhCode;
	}
	public void setBrhCode(String brhCode) {
		this.brhCode = brhCode;
	}
	public String getBrhDesc() {
		return brhDesc;
	}
	public void setBrhDesc(String brhDesc) {
		this.brhDesc = brhDesc;
	}
	public String getBrhStatus() {
		return brhStatus;
	}
	public void setBrhStatus(String brhStatus) {
		this.brhStatus = brhStatus;
	}

}
