package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.enums.ServiceStatus;
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AssetSubTypePartsResponseResource {
	private Long id;
	private String code;
	private String name;
	private String description;
	private String status;
	private Long assetSubTypesId;
	private String assetSubTypeName;
	private ServiceStatus serviceStatus;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public Long getAssetSubTypesId() {
		return assetSubTypesId;
	}
	public void setAssetSubTypesId(Long assetSubTypesId) {
		this.assetSubTypesId = assetSubTypesId;
	}
	public String getAssetSubTypeName() {
		return assetSubTypeName;
	}
	public void setAssetSubTypeName(String assetSubTypeName) {
		this.assetSubTypeName = assetSubTypeName;
	}
	
	
}
