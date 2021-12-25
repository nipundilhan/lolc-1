package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) 
public class CreditAppraiselCollateralDetailsAddResource {
	
//	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|NEW|EXISTING",message="{collateralType.pattern}")
	private String collateralType;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String assetEntityId;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String assetDetailId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String customerId;
	
	@NotBlank(message = "{common.not-null}")
	private String customerName;
	
	//@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String comnCustomerId;
	
	//@NotBlank(message = "{common.not-null}")
	private String comnCustomerReference;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String pendingCustomerId;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String customerAssetId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String assetTypeId;
	
	//@NotBlank(message = "{common.not-null}")
	private String assetTypeName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String assetSubTypeId;
	
	@NotBlank(message = "{common.not-null}")
	private String assetSubTypeName;

	//@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String assetPartId;
	
	//@NotBlank(message = "{common.not-null}")
	private String assetPartName;
	
	//@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String assetClassTypeId;
	
	//@NotBlank(message = "{common.not-null}")
	private String assetClassTypeName;
	
	//@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String assetClassId;
	
	//@NotBlank(message = "{common.not-null}")
	private String assetClassName;

	//@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String subTypeId;
	
	//@NotBlank(message = "{common.not-null}")
	private String subTypeName;

	//@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String pledgeTypeId;
	
	//@NotBlank(message = "{common.not-null}")
	private String pledgeTypeName;
	
	//@NotBlank(message = "{common.not-null}")
	private String invoiceNo;
	
	//@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String supplierId;
	
	//@NotBlank(message = "{common.not-null}")
	private String supplierReferenceCode;
	
	//@NotBlank(message = "{common.not-null}")
	private String supplierName;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String name;
	
	@Size(max = 255, message = "{description.size}")
	private String description;
	
	//@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String registrationAuthorityId;
	
	//@NotBlank(message = "{common.not-null}")
	private String registrationAuthority;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	List<CreAppCollateralDocumentDetailsResource> documentUploadDetails;
	
	private String assetDetailVersion;
	
	private Long assetDetailRefCode;


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

	public String getCollateralType() {
		return collateralType;
	}

	public void setCollateralType(String collateralType) {
		this.collateralType = collateralType;
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

	public String getAssetClassTypeId() {
		return assetClassTypeId;
	}

	public void setAssetClassTypeId(String assetClassTypeId) {
		this.assetClassTypeId = assetClassTypeId;
	}

	public String getAssetClassTypeName() {
		return assetClassTypeName;
	}

	public void setAssetClassTypeName(String assetClassTypeName) {
		this.assetClassTypeName = assetClassTypeName;
	}

	public String getAssetClassId() {
		return assetClassId;
	}

	public void setAssetClassId(String assetClassId) {
		this.assetClassId = assetClassId;
	}

	public String getAssetClassName() {
		return assetClassName;
	}

	public void setAssetClassName(String assetClassName) {
		this.assetClassName = assetClassName;
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



	public String getPledgeTypeId() {
		return pledgeTypeId;
	}

	public void setPledgeTypeId(String pledgeTypeId) {
		this.pledgeTypeId = pledgeTypeId;
	}

	public String getPledgeTypeName() {
		return pledgeTypeName;
	}

	public void setPledgeTypeName(String pledgeTypeName) {
		this.pledgeTypeName = pledgeTypeName;
	}

	public String getSupplierReferenceCode() {
		return supplierReferenceCode;
	}

	public void setSupplierReferenceCode(String supplierReferenceCode) {
		this.supplierReferenceCode = supplierReferenceCode;
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

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getRegistrationAuthorityId() {
		return registrationAuthorityId;
	}

	public void setRegistrationAuthorityId(String registrationAuthorityId) {
		this.registrationAuthorityId = registrationAuthorityId;
	}

	public String getRegistrationAuthority() {
		return registrationAuthority;
	}

	public void setRegistrationAuthority(String registrationAuthority) {
		this.registrationAuthority = registrationAuthority;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAssetEntityId() {
		return assetEntityId;
	}

	public void setAssetEntityId(String assetEntityId) {
		this.assetEntityId = assetEntityId;
	}

	public String getAssetDetailId() {
		return assetDetailId;
	}

	public void setAssetDetailId(String assetDetailId) {
		this.assetDetailId = assetDetailId;
	}

	public List<CreAppCollateralDocumentDetailsResource> getDocumentUploadDetails() {
		return documentUploadDetails;
	}

	public void setDocumentUploadDetails(List<CreAppCollateralDocumentDetailsResource> documentUploadDetails) {
		this.documentUploadDetails = documentUploadDetails;
	}

	public String getCustomerAssetId() {
		return customerAssetId;
	}

	public void setCustomerAssetId(String customerAssetId) {
		this.customerAssetId = customerAssetId;
	}

	public String getAssetDetailVersion() {
		return assetDetailVersion;
	}

	public void setAssetDetailVersion(String assetDetailVersion) {
		this.assetDetailVersion = assetDetailVersion;
	}

	public Long getAssetDetailRefCode() {
		return assetDetailRefCode;
	}

	public void setAssetDetailRefCode(Long assetDetailRefCode) {
		this.assetDetailRefCode = assetDetailRefCode;
	}

	public String getPendingCustomerId() {
		return pendingCustomerId;
	}

	public void setPendingCustomerId(String pendingCustomerId) {
		this.pendingCustomerId = pendingCustomerId;
	}

	public String getComnCustomerId() {
		return comnCustomerId;
	}

	public void setComnCustomerId(String comnCustomerId) {
		this.comnCustomerId = comnCustomerId;
	}

	public String getComnCustomerReference() {
		return comnCustomerReference;
	}

	public void setComnCustomerReference(String comnCustomerReference) {
		this.comnCustomerReference = comnCustomerReference;
	}
	
}
