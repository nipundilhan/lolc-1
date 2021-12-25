package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
import com.fusionx.lending.origination.enums.CommonInternalExternal;
import com.fusionx.lending.origination.enums.CommonStatus;

import lombok.Data;

/**
 * Net Worth Liabilities domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-04-2021   		         FX-6157    SenithaP     Created
 *    
 ********************************************************************************************************
 */

@Entity
@Data
@Table(name = "gu_net_worth_liability")
public class NetWorthLiability extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 0000000000001;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "guarantor_id")  
	private Guarantor guarantor;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "type_of_liability")
	private CommonInternalExternal typeOfLiability;
	
	@Column(name = "company_id")
	private Long companyId;
	
	@Column(name = "type_of_facility_id")
	private Long typeOfFacilityId;
	
	@Column(name = "facility_amount")
	private BigDecimal facilityAmount;
	
	@Column(name = "rental")
	private BigDecimal rental;
	
	@Column(name = "outstanding_amount")
	private BigDecimal outstandingAmount;
	
	@Column(name = "note")
	private String note;
	
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
	private String typeOfFacilityName;
	
	@Transient
	private String companyName;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Guarantor getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(Guarantor guarantor) {
		this.guarantor = guarantor;
	}

	public CommonInternalExternal getTypeOfLiability() {
		return typeOfLiability;
	}

	public void setTypeOfLiability(CommonInternalExternal typeOfLiability) {
		this.typeOfLiability = typeOfLiability;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getTypeOfFacilityId() {
		return typeOfFacilityId;
	}

	public void setTypeOfFacilityId(Long typeOfFacilityId) {
		this.typeOfFacilityId = typeOfFacilityId;
	}

	public BigDecimal getFacilityAmount() {
		return facilityAmount;
	}

	public void setFacilityAmount(BigDecimal facilityAmount) {
		this.facilityAmount = facilityAmount;
	}

	public BigDecimal getRental() {
		return rental;
	}

	public void setRental(BigDecimal rental) {
		this.rental = rental;
	}

	public BigDecimal getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(BigDecimal outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public String getTypeOfFacilityName() {
		return typeOfFacilityName;
	}

	public void setTypeOfFacilityName(String typeOfFacilityName) {
		this.typeOfFacilityName = typeOfFacilityName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
