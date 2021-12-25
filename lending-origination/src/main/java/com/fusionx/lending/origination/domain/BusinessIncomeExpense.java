package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
import com.fusionx.lending.origination.enums.CommonStatus;

import lombok.Data;
/**
 * Business Income Expense Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-05-2021      		     			MENUKAJ      Created
 *    
 ********************************************************************************************************
 */


@Entity
@Table(name = "gu_business_income_expense")
@Data
public class BusinessIncomeExpense extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6570160972603385840L;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "guarantor_id", nullable=true)
	private Guarantor guarantor;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_id", nullable=true)
	private Customer customer;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "business_type_id", nullable=false)
	private BusinessType businessTypes;
	
	@Column(name = "description", length=350, nullable=true)
	private String description;
	
	@Column(name = "frequency_id", nullable=true)
	private Long frequencyId;
	
	@Column(name = "frequency_code", nullable=true, length =30)
	private String frequencyCode;
	
	@Column(name = "frequency_name", nullable=true, length =350)
	private String frequencyName;
	
	@Column(name = "total_gross_income", nullable=false)
	private BigDecimal totalGrossIncome;
	
	@Column(name = "total_expences", nullable=false)
	private BigDecimal totalExpences;
	
	@Column(name = "profit_margin", nullable=true)
	private BigDecimal profitMargin;
	
	@Column(name = "total_net_income", nullable=false)
	private BigDecimal totalNetIncome;
	
	@Column(name = "comments", nullable=true, length =350)
	private String comments;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=30, nullable=false)
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
	private List<IncomeExpenseDetails> incomeExpenseDetails;
	
	@Transient
	private List<IncomeExpenseTax> IncomeExpenseTaxes;

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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public BusinessType getBusinessType() {
		return businessTypes;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessTypes = businessType;
	}

	public Long getBusinessTypeId() {
		return businessTypes.getId();
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

	public BigDecimal getTotalGrossIncome() {
		return totalGrossIncome;
	}

	public void setTotalGrossIncome(BigDecimal totalGrossIncome) {
		this.totalGrossIncome = totalGrossIncome;
	}

	public BigDecimal getTotalExpences() {
		return totalExpences;
	}

	public void setTotalExpences(BigDecimal totalExpences) {
		this.totalExpences = totalExpences;
	}

	public BigDecimal getProfitMargin() {
		return profitMargin;
	}

	public void setProfitMargin(BigDecimal profitMargin) {
		this.profitMargin = profitMargin;
	}

	public BigDecimal getTotalNetIncome() {
		return totalNetIncome;
	}

	public void setTotalNetIncome(BigDecimal totalNetIncome) {
		this.totalNetIncome = totalNetIncome;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public List<IncomeExpenseDetails> getIncomeExpenseDetails() {
		return incomeExpenseDetails;
	}

	public void setIncomeExpenseDetails(List<IncomeExpenseDetails> incomeExpenseDetails) {
		this.incomeExpenseDetails = incomeExpenseDetails;
	}

	public List<IncomeExpenseTax> getIncomeExpenseTaxes() {
		return IncomeExpenseTaxes;
	}

	public void setIncomeExpenseTaxes(List<IncomeExpenseTax> incomeExpenseTaxes) {
		IncomeExpenseTaxes = incomeExpenseTaxes;
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
}
