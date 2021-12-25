package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.enums.ServiceStatus;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AssetsPledgeResponseResource {
	private Long id;
	private String assetsEntityId;
	private String assetPartId;
	private String status;
	private ServiceStatus serviceStatus;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getAssetsEntityId() {
		return assetsEntityId;
	}
	public void setAssetsEntityId(String assetsEntityId) {
		this.assetsEntityId = assetsEntityId;
	}
	public String getAssetPartId() {
		return assetPartId;
	}
	public void setAssetPartId(String assetPartId) {
		this.assetPartId = assetPartId;
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
}
