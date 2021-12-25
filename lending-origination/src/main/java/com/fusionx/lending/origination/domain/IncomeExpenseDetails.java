package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.Type;

import lombok.Data;
/**
 * Income Expense Details Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-05-2021      		     			MENUKAJ      Created
 *    
 ********************************************************************************************************
 */


@Entity
@Table(name = "income_expense_detail")
@Data
public class IncomeExpenseDetails extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6570160972603385840L;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "business_income_expense_id", nullable=false)
	private BusinessIncomeExpense businessIncomeExpense;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "type", length=30, nullable=false)
	private Type type;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "business_income_type_id", nullable=true)
	private IncomeType incomeType;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "expense_type_id", nullable=true)
	private ExpenseType expenseType;
	
	@Column(name = "description", length=350, nullable=true)
	private String description;
	
	@Column(name = "frequency_id", nullable=false)
	private Long frequencyId;
	
	@Column(name = "gross_income", nullable=true)
	private BigDecimal grossIncome;
	
	@Column(name = "amount", nullable=true)
	private BigDecimal amount;
	
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

	public BusinessIncomeExpense getBusinessIncomeExpense() {
		return businessIncomeExpense;
	}

	public void setBusinessIncomeExpense(BusinessIncomeExpense businessIncomeExpense) {
		this.businessIncomeExpense = businessIncomeExpense;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
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

	public Long getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(Long frequencyId) {
		this.frequencyId = frequencyId;
	}

	public BigDecimal getGrossIncome() {
		return grossIncome;
	}

	public void setGrossIncome(BigDecimal grossIncome) {
		this.grossIncome = grossIncome;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public IncomeType getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(IncomeType incomeType) {
		this.incomeType = incomeType;
	}
}
