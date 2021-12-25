package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceStatus;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AssetEntityResponseResource {

	private Long id;
	
	private Long customerAssetId;
	
	private Long referenceNo;
	
	private Long assetTypeId;
	
	private Long assetSubTypeId;
	
	private Long subTypeId;
	
	private Long registrationAuthorityId;
	
	private Long supplierId;
	
	private String name;
	
	private String description;
	
	private String pledgeTypeId;
	
	private String invoiceNo;
	
	private CommonStatus status;
	
	private ServiceStatus serviceStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(Long referenceNo) {
		this.referenceNo = referenceNo;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
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

	public Long getAssetTypeId() {
		return assetTypeId;
	}

	public void setAssetTypeId(Long assetTypeId) {
		this.assetTypeId = assetTypeId;
	}

	public Long getAssetSubTypeId() {
		return assetSubTypeId;
	}

	public void setAssetSubTypeId(Long assetSubTypeId) {
		this.assetSubTypeId = assetSubTypeId;
	}

	public Long getSubTypeId() {
		return subTypeId;
	}

	public void setSubTypeId(Long subTypeId) {
		this.subTypeId = subTypeId;
	}

	public Long getRegistrationAuthorityId() {
		return registrationAuthorityId;
	}

	public void setRegistrationAuthorityId(Long registrationAuthorityId) {
		this.registrationAuthorityId = registrationAuthorityId;
	}

	public Long getCustomerAssetId() {
		return customerAssetId;
	}

	public void setCustomerAssetId(Long customerAssetId) {
		this.customerAssetId = customerAssetId;
	}

	public String getPledgeTypeId() {
		return pledgeTypeId;
	}

	public void setPledgeTypeId(String pledgeTypeId) {
		this.pledgeTypeId = pledgeTypeId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	
}

