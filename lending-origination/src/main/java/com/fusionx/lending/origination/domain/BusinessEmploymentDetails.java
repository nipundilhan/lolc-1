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
 * Business Employment Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   06-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "business_employment_details")
public class BusinessEmploymentDetails extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 2673861277874403970L;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "business_additional_dtl_id" ,nullable=false)
	private BusinessAdditionalDetails businessAdditionalDtl;
	
	@Column(name = "employment_type_id" ,nullable=false)
	private Long employmentTypeId;
	
	@Column(name = "no_of_employees" ,nullable=false)
	private Long noOfEmployees;
	
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
	private String employmentTypeDesc;
	
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
	
	public Long getEmploymentTypeId() {
		return employmentTypeId;
	}

	public void setEmploymentTypeId(Long employmentTypeId) {
		this.employmentTypeId = employmentTypeId;
	}

	public Long getNoOfEmployees() {
		return noOfEmployees;
	}

	public void setNoOfEmployees(Long noOfEmployees) {
		this.noOfEmployees = noOfEmployees;
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

	public String getEmploymentTypeNameDesc() {
		return employmentTypeDesc;
	}

	public void setEmploymentTypeDesc(String employmentTypeDesc) {
		this.employmentTypeDesc= employmentTypeDesc;
	}
	
}
