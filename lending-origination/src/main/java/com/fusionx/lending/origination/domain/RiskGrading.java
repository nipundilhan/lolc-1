package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fusionx.lending.origination.core.BaseEntity;

import lombok.Data;

/**
 * Risk Grading Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "risk_grading")
@Data
public class RiskGrading extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6570160972603385840L;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name="business_person_type_id")  //comn-person
	private Long businessPersonTypeId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "business_type_id", nullable = false)
	private BusinessType businessTypes;
	
	@Transient
    private Long businessTypeId;
	
	@Transient
	private String businessTypeCode;
	
	@Transient
	private String businessTypeName;
	
	@Column(name="industry_type_id")  //comn-comn
	private Long industryTypeId;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	
	//@JsonInclude(Include.NON_NULL)
	@Transient
	private List<RiskGradingDetail> riskGradingDetailList;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Long getBusinessPersonTypeId() {
		return businessPersonTypeId;
	}

	public void setBusinessPersonTypeId(Long businessPersonTypeId) {
		this.businessPersonTypeId = businessPersonTypeId;
	}

	public BusinessType getBusinessTypes() {
		return businessTypes;
	}

	public void setBusinessTypes(BusinessType businessTypes) {
		this.businessTypes = businessTypes;
	}

	public Long getIndustryTypeId() {
		return industryTypeId;
	}

	public void setIndustryTypeId(Long industryTypeId) {
		this.industryTypeId = industryTypeId;
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

	public List<RiskGradingDetail> getRiskGradingDetailList() {
		return riskGradingDetailList;
	}

	public void setRiskGradingDetailList(List<RiskGradingDetail> riskGradingDetailList) {
		this.riskGradingDetailList = riskGradingDetailList;
	}

	//@transient columns
	public Long getBusinessTypeId() {
		return businessTypes.getId();
	}

	public String getBusinessTypeCode() {
		return businessTypes.getCode();
	}

	public String getBusinessTypeName() {
		return businessTypes.getName();
	}
	
}
