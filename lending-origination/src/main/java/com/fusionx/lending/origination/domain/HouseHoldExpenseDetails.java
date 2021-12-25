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

import lombok.Data;

/**
 * Business Sub Center Product Map Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-09-2021                 FXL-115    Dilhan        Created
 *    
 ********************************************************************************************************
 */
@Entity
@Table(name = "house_hold_expense_details")
@Data
public class HouseHoldExpenseDetails extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 4302576764137321503L;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "income_source_details_id")  
	private IncomeSourceDetails incomeSourceDetails;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "house_hold_expense_category_id")  
	private HouseHoldExpenseCategory houseHoldExpenseCategory;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "expense_type_id")  
	private ExpenseType expenseType;
	
	@Transient
	private Long expenseTypeId;
	
	@Transient
	private Long houseHoldExpenseCategoryId;
	
	@Column(name = "occurrence_frequency_id",nullable=false)
	private Long occurranceFrequencyId;
	
	@Column(name = "occurrence_frequency_code", nullable=false)
	private String occurranceFrequencyCode;
	
	@Column(name="cost", columnDefinition="Decimal(25,5)", nullable=false)
	private BigDecimal cost;
	
	@Column(name = "calculation_frequency_id", nullable=false)
	private Long calculationFrequencyId;
	
	@Column(name = "calculation_frequency_code", nullable=false)
	private String calculationFrequencyCode;
	
	@Column(name="final_cost", columnDefinition="Decimal(25,5)", nullable=false)
	private BigDecimal finalCost;
	
	@Column(name = "currencyId", nullable=false)
	private Long currencyId;
	
	@Column(name = "currency_code", length=3, nullable=false)
	private String currencyCode;
	
	@Column(name = "currency_code_numeric", length=3, nullable=false)
	private String currencyCodeNumeric;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Column(name = "description", length=300, nullable=true)
	private String description;
	
	@Column(name = "created_user",length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date",nullable=true)
	private Timestamp modifiedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public IncomeSourceDetails getIncomeSourceDetails() {
		return incomeSourceDetails;
	}

	public void setIncomeSourceDetails(IncomeSourceDetails incomeSourceDetails) {
		this.incomeSourceDetails = incomeSourceDetails;
	}

	public HouseHoldExpenseCategory getHouseHoldExpenseCategory() {
		return houseHoldExpenseCategory;
	}

	public void setHouseHoldExpenseCategory(HouseHoldExpenseCategory houseHoldExpenseCategory) {
		this.houseHoldExpenseCategory = houseHoldExpenseCategory;
	}

	public ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}

	public Long getOccurranceFrequencyId() {
		return occurranceFrequencyId;
	}

	public void setOccurranceFrequencyId(Long occurranceFrequencyId) {
		this.occurranceFrequencyId = occurranceFrequencyId;
	}

	public String getOccurranceFrequencyCode() {
		return occurranceFrequencyCode;
	}

	public void setOccurranceFrequencyCode(String occurranceFrequencyCode) {
		this.occurranceFrequencyCode = occurranceFrequencyCode;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public Long getCalculationFrequencyId() {
		return calculationFrequencyId;
	}

	public void setCalculationFrequencyId(Long calculationFrequencyId) {
		this.calculationFrequencyId = calculationFrequencyId;
	}

	public String getCalculationFrequencyCode() {
		return calculationFrequencyCode;
	}

	public void setCalculationFrequencyCode(String calculationFrequencyCode) {
		this.calculationFrequencyCode = calculationFrequencyCode;
	}

	public BigDecimal getFinalCost() {
		return finalCost;
	}

	public void setFinalCost(BigDecimal finalCost) {
		this.finalCost = finalCost;
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

	public Long getExpenseTypeId() {
		return expenseType.getId();
	}

	public String getExpenseTypeName() {
		return expenseType.getName();
	}

	public Long getHouseHoldExpenseCategoryId() {
		return houseHoldExpenseCategory.getId();
	}

	public String getHouseHoldExpenseCategoryName() {
		return houseHoldExpenseCategory.getName();
	}

}
