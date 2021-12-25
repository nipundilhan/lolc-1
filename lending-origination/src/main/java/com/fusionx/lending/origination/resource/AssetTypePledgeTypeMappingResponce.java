package com.fusionx.lending.origination.resource;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.enums.ServiceStatus;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AssetTypePledgeTypeMappingResponce{
	
	private ServiceStatus serviceStatus;
	
	List<AssetTypePledgeTypeMappingResource> assetTypePledgeTypeMappingResource;

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public List<AssetTypePledgeTypeMappingResource> getAssetTypePledgeTypeMappingResource() {
		return assetTypePledgeTypeMappingResource;
	}

	public void setAssetTypePledgeTypeMappingResource(
			List<AssetTypePledgeTypeMappingResource> assetTypePledgeTypeMappingResource) {
		this.assetTypePledgeTypeMappingResource = assetTypePledgeTypeMappingResource;
	}

}
