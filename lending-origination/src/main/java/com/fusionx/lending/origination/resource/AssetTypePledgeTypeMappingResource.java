package com.fusionx.lending.origination.resource;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.enums.ServiceStatus;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AssetTypePledgeTypeMappingResource{
	
	private Long id;
	
	private Long asset_type_id;

	private String assetTypeCode;

	private String assetTypeName;
	
	private Long pledge_type_id;

	private String pledgeTypeCode;

	private String pledgeTypeName;

	private String status;
	
	private String code;
	
	private String name;
	
	private ServiceStatus serviceStatus;

	public String getAssetTypeCode() {
		return assetTypeCode;
	}

	public void setAssetTypeCode(String assetTypeCode) {
		this.assetTypeCode = assetTypeCode;
	}

	public String getAssetTypeName() {
		return assetTypeName;
	}

	public void setAssetTypeName(String assetTypeName) {
		this.assetTypeName = assetTypeName;
	}

	public String getPledgeTypeCode() {
		return pledgeTypeCode;
	}

	public void setPledgeTypeCode(String pledgeTypeCode) {
		this.pledgeTypeCode = pledgeTypeCode;
	}

	public String getPledgeTypeName() {
		return pledgeTypeName;
	}

	public void setPledgeTypeName(String pledgeTypeName) {
		this.pledgeTypeName = pledgeTypeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public Long getAsset_type_id() {
		return asset_type_id;
	}

	public void setAsset_type_id(Long asset_type_id) {
		this.asset_type_id = asset_type_id;
	}

	public Long getPledge_type_id() {
		return pledge_type_id;
	}

	public void setPledge_type_id(Long pledge_type_id) {
		this.pledge_type_id = pledge_type_id;
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

}
