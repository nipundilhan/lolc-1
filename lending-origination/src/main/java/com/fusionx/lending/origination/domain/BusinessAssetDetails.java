package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;

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
import com.fusionx.lending.origination.enums.CommonStatus;


 /**
 * Business Asset Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   06-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "business_asset_details")
public class BusinessAssetDetails extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 2673861277874403970L;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "business_additional_dtl_id" ,nullable=false)
	private BusinessAdditionalDetails businessAdditionalDtl;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "asset_type_id" ,nullable=false)
	private CommonList assetType;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "ownership_id" ,nullable=false)
	private CommonList ownership;

	@Column(name = "location")
	private String location;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "no_of_assets")
	private Long noOfAssets;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
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
	private Long assetTypesId;
	
	@Transient
	private String assetTypesName;

	@Transient
	private Long ownershipsId;

	@Transient
	private String ownershipsName;

	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public BusinessAdditionalDetails getBusinessAdditionalDtl() {
		return businessAdditionalDtl;
	}

	public void setBusinessAdditionalDtl(BusinessAdditionalDetails businessAdditionalDtl) {
		this.businessAdditionalDtl = businessAdditionalDtl;
	}

	public CommonList getAssetType() {
		return assetType;
	}

	public void setAssetType(CommonList assetType) {
		this.assetType = assetType;
	}

	public CommonList getOwnership() {
		return ownership;
	}

	public void setOwnership(CommonList ownership) {
		this.ownership = ownership;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getNoOfAssets() {
		return noOfAssets;
	}

	public void setNoOfAssets(Long noOfAssets) {
		this.noOfAssets = noOfAssets;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
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

	public Long getAssetTypesId() {
		return assetTypesId;
	}

	public void setAssetTypesId(Long assetTypesId) {
		this.assetTypesId = assetTypesId;
	}

	public String getAssetTypesName() {
		return assetTypesName;
	}

	public void setAssetTypesName(String assetTypesName) {
		this.assetTypesName = assetTypesName;
	}

	public Long getOwnershipsId() {
		return ownershipsId;
	}

	public void setOwnershipsId(Long ownershipsId) {
		this.ownershipsId = ownershipsId;
	}

	public String getOwnershipsName() {
		return ownershipsName;
	}

	public void setOwnershipsName(String ownershipsName) {
		this.ownershipsName = ownershipsName;
	}
	
}
