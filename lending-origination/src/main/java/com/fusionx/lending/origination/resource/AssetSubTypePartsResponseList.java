package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.enums.ServiceStatus;
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AssetSubTypePartsResponseList {
	
	private ServiceStatus serviceStatus;

	List<AssetSubTypePartsResponseResource> AssetSubTypePartsResponseResource;

	public List<AssetSubTypePartsResponseResource> getAssetSubTypePartsResponseResource() {
		return AssetSubTypePartsResponseResource;
	}

	public void setAssetSubTypePartsResponseResource(
			List<AssetSubTypePartsResponseResource> assetSubTypePartsResponseResource) {
		AssetSubTypePartsResponseResource = assetSubTypePartsResponseResource;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	
	
}
