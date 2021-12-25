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
 * Business Clearance Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   06-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "business_clearance_details")
public class BusinessClearanceDetails extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 2673861277874403970L;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "business_additional_dtl_id" ,nullable=false)
	private BusinessAdditionalDetails businessAdditionalDtl;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "clearance_type_id" ,nullable=false)
	private CommonList clearanceType;
	
	@Column(name = "memo",nullable=false)
	private String memo;
	
	@Column(name = "obtain_date")
	private Timestamp obtainDate;
	
	@Column(name = "expire_date")
	private Timestamp expireDate;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "authority_id" ,nullable=false)
	private CommonList authority;
	
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
	private Long clearanceTypesId;
	
	@Transient
	private String clearanceTypesName;
	
	@Transient
	private Long authoritysId;
	
	@Transient
	private String authoritysName;
	
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

	public CommonList getClearanceType() {
		return clearanceType;
	}

	public void setClearanceType(CommonList clearanceType) {
		this.clearanceType = clearanceType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Timestamp getObtainDate() {
		return obtainDate;
	}

	public void setObtainDate(Timestamp obtainDate) {
		this.obtainDate = obtainDate;
	}

	public Timestamp getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Timestamp expireDate) {
		this.expireDate = expireDate;
	}

	public CommonList getAuthority() {
		return authority;
	}

	public void setAuthority(CommonList authority) {
		this.authority = authority;
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

	public Long getClearanceTypesId() {
		return clearanceTypesId;
	}

	public void setClearanceTypesId(Long clearanceTypesId) {
		this.clearanceTypesId = clearanceTypesId;
	}

	public String getClearanceTypesName() {
		return clearanceTypesName;
	}

	public void setClearanceTypesName(String clearanceTypesName) {
		this.clearanceTypesName = clearanceTypesName;
	}

	public Long getAuthoritysId() {
		return authoritysId;
	}

	public void setAuthoritysId(Long authoritysId) {
		this.authoritysId = authoritysId;
	}

	public String getAuthoritysName() {
		return authoritysName;
	}

	public void setAuthoritysName(String authoritysName) {
		this.authoritysName = authoritysName;
	}
	
}
