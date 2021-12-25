package com.fusionx.lending.product.domain;

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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;


import lombok.Data;

/**
 * Penal Interest Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   14-08-2020                   	        Dilhan        Created
 *    
 ********************************************************************************************************
 */

@Entity
@Data
@Table(name = "penal_interest_type")
public class PenalInterestType extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 8106535943719027129L;

	@Column(name = "tenant_id", length=20, nullable=false)
	private String tenantId;
	
	@Column(name = "code", length=4, nullable=false)
	private String code;

	@Column(name = "name", length=70, nullable=false)
	private String name;
	
	@Column(name = "description", length=2500, nullable=true)
	private String description;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "applicaction_frequency_id", nullable=false)
	private ApplicationFrequency applicationFrequency;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "calculation_frequency_id", nullable=false)
	private CalculationFrequency calculationFrequency;
	
	@Column(name = "trans_sub_code_id")
	private Long transSubCodeId;
	
	@Column(name = "trans_sub_code", length=4, nullable=false)
	private String transSubCode;
	
//	@Column(name = "trans_sub_code_description", nullable=false)
//	private String transSubCodeDesc;                      
	
	@Column(name = "created_user",length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date",nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user",length=255, nullable=true)
	private String modifiedUser;
	
	@Column(name = "modified_date",nullable=true)
	private Timestamp modifiedDate;
	
	@Transient
	//@JsonInclude(Include.NON_NULL)
	private List<PenalInterestTypeDetails> penalInterestTypeDetails;
	
	@Transient
	private Long applicactionFrequencyId;
	
	@Transient
	private Long calculationFrequencyId;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public ApplicationFrequency getApplicationFrequency() {
		return applicationFrequency;
	}

	public void setApplicationFrequency(ApplicationFrequency applicationFrequency) {
		this.applicationFrequency = applicationFrequency;
	}

	public CalculationFrequency getCalculationFrequency() {
		return calculationFrequency;
	}

	public void setCalculationFrequency(CalculationFrequency calculationFrequency) {
		this.calculationFrequency = calculationFrequency;
	}

	public Long getTransSubCodeId() {
		return transSubCodeId;
	}

	public void setTransSubCodeId(Long transSubCodeId) {
		this.transSubCodeId = transSubCodeId;
	}

	public String getTransSubCode() {
		return transSubCode;
	}

	public void setTransSubCode(String transSubCode) {
		this.transSubCode = transSubCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<PenalInterestTypeDetails> getPenalInterestTypeDetails() {
		return penalInterestTypeDetails;
	}

	public void setPenalInterestTypeDetails(List<PenalInterestTypeDetails> penalInterestTypeDetails) {
		this.penalInterestTypeDetails = penalInterestTypeDetails;
	}
	
	public Long getApplicactionFrequencyId() {
		return applicationFrequency.getId();
	}
	
	public String getApplicactionFrequencyName() {
		return applicationFrequency.getName();
		
	}
	public Long getCalculationFrequencyId() {
		return calculationFrequency.getId();
	}
	
	public String getCalculationFrequencyName() {
		return calculationFrequency.getName();
	}

}
