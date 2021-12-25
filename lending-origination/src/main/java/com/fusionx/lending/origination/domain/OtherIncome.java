package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;

import lombok.Data;

/**
 * Other Income Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6301    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Entity
@Data
@Table(name = "gu_other_income")
public class OtherIncome extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 0000000000001;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_id")  
	private Customer customer;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "guarantor_id")  
	private Guarantor guarantor;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "other_income_type_id")  
	private OtherIncomeType otherIncomeType;
	
	@Transient
	private Long otherIncomeTypesId;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "frequency_id")
	private Long frequencyId;
	
	@Column(name = "frequency_code")
	private String frequencyCode;
	
	@Column(name = "frequency_name")
	private String frequencyName;
	
	@Column(name = "occurance")
	private Long occurance;
	
	@Column(name = "income_per_occurance")
	private BigDecimal incomePerOccurance;
	
	@Column(name = "tot_income_per_freq")
	private BigDecimal totIncomePerFreq;
	
	@Column(name = "gross_income")
	private BigDecimal grossIncome;
	
	@Column(name = "deductions")
	private BigDecimal deductions;
	
	@Column(name = "net_income")
	private BigDecimal netIncome;
	
	@Column(name = "comments")
	private String comment;
	
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
	
	
	public Guarantor getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(Guarantor guarantor) {
		this.guarantor = guarantor;
	}

	public OtherIncomeType getOtherIncomeType() {
		return otherIncomeType;
	}

	public void setOtherIncomeType(OtherIncomeType otherIncomeType) {
		this.otherIncomeType = otherIncomeType;
	}
	
	public Long getOtherIncomeTypesId() {
		return otherIncomeType.getId();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(Long frequencyId) {
		this.frequencyId = frequencyId;
	}

	
	public String getFrequencyCode() {
		return frequencyCode;
	}

	public void setFrequencyCode(String frequencyCode) {
		this.frequencyCode = frequencyCode;
	}

	public String getFrequencyName() {
		return frequencyName;
	}

	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
	}

	public Long getOccurance() {
		return occurance;
	}

	public void setOccurance(Long occurance) {
		this.occurance = occurance;
	}

	public BigDecimal getIncomePerOccurance() {
		return incomePerOccurance;
	}

	public void setIncomePerOccurance(BigDecimal incomePerOccurance) {
		this.incomePerOccurance = incomePerOccurance;
	}

	public BigDecimal getTotIncomePerFreq() {
		return totIncomePerFreq;
	}

	public void setTotIncomePerFreq(BigDecimal totIncomePerFreq) {
		this.totIncomePerFreq = totIncomePerFreq;
	}

	public BigDecimal getGrossIncome() {
		return grossIncome;
	}

	public void setGrossIncome(BigDecimal grossIncome) {
		this.grossIncome = grossIncome;
	}

	public BigDecimal getDeductions() {
		return deductions;
	}

	public void setDeductions(BigDecimal deductions) {
		this.deductions = deductions;
	}

	public BigDecimal getNetIncome() {
		return netIncome;
	}

	public void setNetIncome(BigDecimal netIncome) {
		this.netIncome = netIncome;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
	
	

}
