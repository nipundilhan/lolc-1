package com.fusionx.lending.origination.domain;

/**
 * Salaary Income Expense Domain
 * @author 	NipunDilhan
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   12-09-2021  						    NipunDilhan      Created
 *    
 ********************************************************************************************************
 */
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

@Entity
@Table(name = "salary_income_expenses")
//@Data
public class SalaryIncomeExpenses  extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6570160972603385840L;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "salary_income_details_id", nullable=false)
	private SalaryIncomeDetails salaryIncomeDetails;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "salary_income_type_id", nullable=true)
	private SalaryIncomeType salaryIncomeType;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "expense_type_id", nullable=true)
	private ExpenseType expenseType;
	
	@Column(name = "description", length=350, nullable=true)
	private String description;
	
	@Column(name = "amount", nullable=false)
	private BigDecimal amount;
	
	@Column(name = "frequency_id", nullable=false)
	private Long frequencyId;
	
	@Column(name = "frequency_code", nullable=true, length =30)
	private String frequencyCode;
	
	@Column(name = "frequency_name", nullable=true, length =350)
	private String frequencyName;
	
	@Column(name = "currency_id", nullable=true)
	private Long currencyId;
	
	@Column(name = "currency_code", nullable=true, length =30)
	private String currencyCode;
	
	@Column(name = "currency_code_numeric", nullable=true, length =30)
	private String currencyCodeNumeric;
	
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

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public SalaryIncomeDetails getSalaryIncomeDetails() {
		return salaryIncomeDetails;
	}

	public void setSalaryIncomeDetails(SalaryIncomeDetails salaryIncomeDetails) {
		this.salaryIncomeDetails = salaryIncomeDetails;
	}

	public SalaryIncomeType getSalaryIncomeType() {
		return salaryIncomeType;
	}

	public void setSalaryIncomeType(SalaryIncomeType salaryIncomeType) {
		this.salaryIncomeType = salaryIncomeType;
	}


	public ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
