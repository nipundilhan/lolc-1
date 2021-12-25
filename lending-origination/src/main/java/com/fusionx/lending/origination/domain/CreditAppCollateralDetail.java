package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CollateralType;
import com.fusionx.lending.origination.enums.CommonStatus;

import lombok.Data;


@Entity
@Data
@Table(name = "credit_app_collateral_det")
public class CreditAppCollateralDetail  extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 0000000000001;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_id", nullable=true)
	private Customer customer;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "collateral_type", length=30, nullable=false)
	private CollateralType collateralType;
	
	@Column(name = "asset_type_id", nullable=false)
	private Long assetTypeId;
	
	@Column(name = "asset_sub_type_id", nullable=false)
	private Long assetSubTypeId;

	@Column(name = "asset_part_id", nullable=true)
	private Long assetPartId;
	
	@Column(name = "sub_type_id", nullable=true)
	private Long subTypeId;
	
	@Column(name = "ownership_type_id", nullable=true)
	private Long ownershipTypeId;
	
	@Column(name = "invoice_no", length=250, nullable=true)
	private String invoiceNo;
	
	@Column(name = "supplier_id", nullable=true)
	private Long supplierId;
	
	@Column(name = "name", length=70, nullable=false)
	private String name;
	
	@Column(name = "description", length=250, nullable=true)
	private String description;
	
	@Column(name = "registration_auth_id", nullable=true)
	private Long registrationAuthorityId;
	
	@Column(name = "asset_entity_id", nullable=true)
	private Long assetEntityId;
	
	@Column(name = "final_valuation_id", nullable=true)
	private Long finalValuationId;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private CommonStatus status;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	@Transient
	private List<CreditAppCollateralDocuments> creditAppCollateralDocuments;
	
	@Transient
	private Long custId;

	@Transient
	private String assetTypeCode;
	
	@Transient
	private String assetTypeName;
	
	@Transient
	private String assetSubTypeName;
	
	@Transient
	private String assetPartName;
	
//	@Transient
//	private String assetClassTypeName;
//	
//	@Transient
//	private String assetClassName;
	
	@Transient
	private String subTypeName;
	
	@Transient
	private String supplierReferenceCode;
	
//	@Transient
//	private String supplierName;
	
	@Transient
	private String registrationAuthority;
	
	@Transient
	private String ownershipType;
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CollateralType getCollateralType() {
		return collateralType;
	}

	public void setCollateralType(CollateralType collateralType) {
		this.collateralType = collateralType;
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

	public Long getAssetPartId() {
		return assetPartId;
	}

	public void setAssetPartId(Long assetPartId) {
		this.assetPartId = assetPartId;
	}

	public Long getSubTypeId() {
		return subTypeId;
	}

	public void setSubTypeId(Long subTypeId) {
		this.subTypeId = subTypeId;
	}

	public Long getOwnershipTypeId() {
		return ownershipTypeId;
	}

	public void setOwnershipTypeId(Long ownershipTypeId) {
		this.ownershipTypeId = ownershipTypeId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
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

	public Long getRegistrationAuthorityId() {
		return registrationAuthorityId;
	}

	public void setRegistrationAuthorityId(Long registrationAuthorityId) {
		this.registrationAuthorityId = registrationAuthorityId;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}
	
	public void setStatus(String status) {
		this.status = CommonStatus.valueOf(status);
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getAssetEntityId() {
		return assetEntityId;
	}

	public void setAssetEntityId(Long assetEntityId) {
		this.assetEntityId = assetEntityId;
	}

	public List<CreditAppCollateralDocuments> getCreditAppCollateralDocuments() {
		return creditAppCollateralDocuments;
	}

	public void setCreditAppCollateralDocuments(List<CreditAppCollateralDocuments> creditAppCollateralDocuments) {
		this.creditAppCollateralDocuments = creditAppCollateralDocuments;
	}

	public Long getFinalValuationId() {
		return finalValuationId;
	}

	public void setFinalValuationId(Long finalValuationId) {
		this.finalValuationId = finalValuationId;
	}

	public Long getCustId() {
		if(customer!=null)
			return	customer.getId();
		else return null;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getAssetTypeName() {
		return assetTypeName;
	}

	public void setAssetTypeName(String assetTypeName) {
		this.assetTypeName = assetTypeName;
	}

	public String getAssetSubTypeName() {
		return assetSubTypeName;
	}

	public void setAssetSubTypeName(String assetSubTypeName) {
		this.assetSubTypeName = assetSubTypeName;
	}

	public String getAssetPartName() {
		return assetPartName;
	}

	public void setAssetPartName(String assetPartName) {
		this.assetPartName = assetPartName;
	}

	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

	public String getSupplierReferenceCode() {
		return supplierReferenceCode;
	}

	public void setSupplierReferenceCode(String supplierReferenceCode) {
		this.supplierReferenceCode = supplierReferenceCode;
	}

	public String getRegistrationAuthority() {
		return registrationAuthority;
	}

	public void setRegistrationAuthority(String registrationAuthority) {
		this.registrationAuthority = registrationAuthority;
	}

	public String getOwnershipType() {
		return ownershipType;
	}

	public void setOwnershipType(String ownershipType) {
		this.ownershipType = ownershipType;
	}

	public String getAssetTypeCode() {
		return assetTypeCode;
	}

	public void setAssetTypeCode(String assetTypeCode) {
		this.assetTypeCode = assetTypeCode;
	}

}
