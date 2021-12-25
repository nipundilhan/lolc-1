package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AssetsDetailsRequestResource {

	String id;
	String customerId;
	String customerReferenceCode;
	String pendingCustomerId;
	String assetEntityId;
	String customerAssetId;
	String assetTypeId;
	String assetTypeName;
	String assetSubTypeId;
	String assetSubTypeName;
	String assetPartId;	
	String assetPartName;
	String subTypeId;	
	String subTypeName;
	String registrationAuthorityId;
	String registrationAuthorityName;
	String pledgeTypeId;
	String pledgeTypeName;
	String name;
	String description;
	String supplierId;
	String supplierReferenceCode;
	String invoiceNo;
	String referenceNo;
	String status;
	String version;
	
	AssetsDocumentsUpload assetsDocumentsUpload;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerReferenceCode() {
		return customerReferenceCode;
	}

	public void setCustomerReferenceCode(String customerReferenceCode) {
		this.customerReferenceCode = customerReferenceCode;
	}

	public String getAssetEntityId() {
		return assetEntityId;
	}

	public void setAssetEntityId(String assetEntityId) {
		this.assetEntityId = assetEntityId;
	}

	public String getCustomerAssetId() {
		return customerAssetId;
	}

	public void setCustomerAssetId(String customerAssetId) {
		this.customerAssetId = customerAssetId;
	}

	public String getAssetTypeId() {
		return assetTypeId;
	}

	public void setAssetTypeId(String assetTypeId) {
		this.assetTypeId = assetTypeId;
	}

	public String getAssetTypeName() {
		return assetTypeName;
	}

	public void setAssetTypeName(String assetTypeName) {
		this.assetTypeName = assetTypeName;
	}

	public String getAssetSubTypeId() {
		return assetSubTypeId;
	}

	public void setAssetSubTypeId(String assetSubTypeId) {
		this.assetSubTypeId = assetSubTypeId;
	}

	public String getAssetSubTypeName() {
		return assetSubTypeName;
	}

	public void setAssetSubTypeName(String assetSubTypeName) {
		this.assetSubTypeName = assetSubTypeName;
	}

	public String getAssetPartId() {
		return assetPartId;
	}

	public void setAssetPartId(String assetPartId) {
		this.assetPartId = assetPartId;
	}

	public String getAssetPartName() {
		return assetPartName;
	}

	public void setAssetPartName(String assetPartName) {
		this.assetPartName = assetPartName;
	}

	public String getSubTypeId() {
		return subTypeId;
	}

	public void setSubTypeId(String subTypeId) {
		this.subTypeId = subTypeId;
	}

	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

	public String getRegistrationAuthorityId() {
		return registrationAuthorityId;
	}

	public void setRegistrationAuthorityId(String registrationAuthorityId) {
		this.registrationAuthorityId = registrationAuthorityId;
	}

	public String getRegistrationAuthorityName() {
		return registrationAuthorityName;
	}

	public void setRegistrationAuthorityName(String registrationAuthorityName) {
		this.registrationAuthorityName = registrationAuthorityName;
	}

	public String getPledgeTypeId() {
		return pledgeTypeId;
	}

	public void setPledgeTypeId(String pledgeTypeId) {
		this.pledgeTypeId = pledgeTypeId;
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

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierReferenceCode() {
		return supplierReferenceCode;
	}

	public void setSupplierReferenceCode(String supplierReferenceCode) {
		this.supplierReferenceCode = supplierReferenceCode;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public AssetsDocumentsUpload getAssetsDocumentsUpload() {
		return assetsDocumentsUpload;
	}

	public void setAssetsDocumentsUpload(AssetsDocumentsUpload assetsDocumentsUpload) {
		this.assetsDocumentsUpload = assetsDocumentsUpload;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getPendingCustomerId() {
		return pendingCustomerId;
	}

	public void setPendingCustomerId(String pendingCustomerId) {
		this.pendingCustomerId = pendingCustomerId;
	}
	
	
}
