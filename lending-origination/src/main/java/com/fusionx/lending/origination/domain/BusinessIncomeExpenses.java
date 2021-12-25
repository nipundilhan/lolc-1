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
import com.fusionx.lending.origination.enums.CommonStatus;

/**
 * 	Business Income Expenses
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-09-2021   FXL-115  	 FXL-785       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@Entity
@Table(name = "business_income_expenses")
public class BusinessIncomeExpenses extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -9053337896731384028L;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "business_income_details_id")  
	private BusinessIncomeDetails businessIncomeDetail;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "business_income_type_id",  nullable=true)  
	private IncomeType businessIncomeType;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "business_expense_type_id",  nullable=true)  
	private BusinessExpenseType businessExpenseType;
	
	@Column(name = "frequency_id",nullable=false)
	private Long frequencyId;
	
	@Column(name = "frequency_code", nullable=false)
	private String frequencyCode;
	
	@Column(name="amount", columnDefinition="Decimal(38,2)", nullable=false)
	private BigDecimal amount;
	
	@Column(name = "currencyId", nullable=false)
	private Long currencyId;
	
	@Column(name = "currency_code", length=3, nullable=false)
	private String currencyCode;
	
	@Column(name = "currency_code_numeric", length=3, nullable=false)
	private String currencyCodeNumeric;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Column(name = "description", length=300, nullable=false)
	private String description;
	
	@Column(name = "created_user",length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date",nullable=true)
	private Timestamp modifiedDate;

	@Transient
	private Long businessIncomeTypesId;
	
	@Transient
	private String businessIncomeTypesName;
	
	@Transient
	private Long businessExpenseTypesId;
//	
//	@Transient
//	private String businessExpenseTypesName;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public BusinessIncomeDetails getBusinessIncomeDetail() {
		return businessIncomeDetail;
	}

	public void setBusinessIncomeDetail(BusinessIncomeDetails businessIncomeDetail) {
		this.businessIncomeDetail = businessIncomeDetail;
	}

	public IncomeType getBusinessIncomeType() {
		return businessIncomeType;
	}

	public void setBusinessIncomeType(IncomeType businessIncomeType) {
		this.businessIncomeType = businessIncomeType;
	}

	public BusinessExpenseType getBusinessExpenseType() {
		return businessExpenseType;
	}

	public void setBusinessExpenseType(BusinessExpenseType businessExpenseType) {
		this.businessExpenseType = businessExpenseType;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyCodeNumeric() {
		return currencyCodeNumeric;
	}

	public void setCurrencyCodeNumeric(String currencyCodeNumeric) {
		this.currencyCodeNumeric = currencyCodeNumeric;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
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

	public Long getBusinessIncomeTypesId() {
		return businessIncomeTypesId;
	}

	public void setBusinessIncomeTypesId(Long businessIncomeTypesId) {
		this.businessIncomeTypesId = businessIncomeTypesId;
	}

	public String getBusinessIncomeTypesName() {
		return businessIncomeTypesName;
	}

	public void setBusinessIncomeTypesName(String businessIncomeTypesName) {
		this.businessIncomeTypesName = businessIncomeTypesName;;
	}

	public Long getBusinessExpenseTypesId() {
		return businessExpenseTypesId;
	}

	public void setBusinessExpenseTypesId(Long businessExpenseTypesId) {
		this.businessExpenseTypesId = businessExpenseTypesId;
	}

//	public String getBusinessExpenseTypesName() {
//		return businessExpenseTypesName;
//	}
//
//	public void setBusinessExpenseTypesName(String businessExpenseTypesName) {
//		this.businessExpenseTypesName = businessExpenseTypesName;
//	}
	
}

