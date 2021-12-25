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

import lombok.Data;
/**
 * Customer Cultivation Expense Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-05-2021      		     			MENUKAJ      Created
 *    
 ********************************************************************************************************
 */


@Entity
@Table(name = "customer_cultivation_expense")
@Data
public class CustomerCultivationExpense extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6570160972603385840L;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_cultivation_income_id", nullable=false)
	private CustomerCultivationIncome customerCultivationIncome;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "expense_type_id", nullable=false)
	private ExpenseType expenseType;
	
	@Column(name = "description", length=350, nullable=true)
	private String description;
	
	@Column(name = "occurrence_frequency_id", nullable=false)
	private Long occurrenceFrequencyId;
	
	@Column(name = "occurrence_id", nullable=false)
	private Long occurrenceId;
	
	@Column(name = "cost", nullable=false)
	private BigDecimal cost;
	
	@Column(name = "calculation_frequency_id", nullable=false)
	private Long calculationFrequencyId;
	
	@Column(name = "final_cost", nullable=false)
	private BigDecimal finalCost;
	
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

	public CustomerCultivationIncome getCustomerCultivationIncome() {
		return customerCultivationIncome;
	}

	public void setCustomerCultivationIncome(CustomerCultivationIncome customerCultivationIncome) {
		this.customerCultivationIncome = customerCultivationIncome;
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

	public Long getOccurrenceFrequencyId() {
		return occurrenceFrequencyId;
	}

	public void setOccurrenceFrequencyId(Long occurrenceFrequencyId) {
		this.occurrenceFrequencyId = occurrenceFrequencyId;
	}

	public Long getOccurrenceId() {
		return occurrenceId;
	}

	public void setOccurrenceId(Long occurrenceId) {
		this.occurrenceId = occurrenceId;
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

	public BigDecimal getFinalCost() {
		return finalCost;
	}

	public void setFinalCost(BigDecimal finalCost) {
		this.finalCost = finalCost;
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

	public BigDecimal getCost() {
		return cost;
	}
}
