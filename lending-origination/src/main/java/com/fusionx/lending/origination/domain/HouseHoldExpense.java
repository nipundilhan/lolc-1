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
 * House Hold Expense Detail Service.
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
@Table(name = "customer_house_hold_expense")
public class HouseHoldExpense extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 0000000000001;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_id")  
	private Customer customer;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "expense_typ_exp_category_det_id")  
	private ExpenseTypeHouseholdExpenseCategoriesDetails expenseTypeHouseholdExpenseCategoriesDetails;
	
	@Transient
	private Long expenseTypeHouseholdExpenseCategoriesDetailsId;
	
	@Column(name = "occurance_frequency_id")
	private Long occuranceFrequencyId;
	
	@Column(name = "occurance_frequency_code")
	private String occuranceFrequencyCode;
	
	@Column(name = "occurance_frequency_name")
	private String occuranceFrequencyName;
	
	@Column(name = "cost")
	private BigDecimal cost;
	
	@Column(name = "calcu_frequency_id")
	private Long calculationFrequencyId;
	
	@Column(name = "calcu_frequency_code")
	private String calculationFrequencyCode;
	
	@Column(name = "calcu_frequency_name")
	private String calculationFrequencyName;
	
	@Column(name = "final_cost")
	private BigDecimal finalCost;
	
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

	public ExpenseTypeHouseholdExpenseCategoriesDetails getExpenseTypeHouseholdExpenseCategoriesDetails() {
		return expenseTypeHouseholdExpenseCategoriesDetails;
	}

	public void setExpenseTypeHouseholdExpenseCategoriesDetails(
			ExpenseTypeHouseholdExpenseCategoriesDetails expenseTypeHouseholdExpenseCategoriesDetails) {
		this.expenseTypeHouseholdExpenseCategoriesDetails = expenseTypeHouseholdExpenseCategoriesDetails;
	}
	
	public Long getExpenseTypeHouseholdExpenseCategoriesDetailsId() {
		return expenseTypeHouseholdExpenseCategoriesDetails.getId();
	}


	public Long getOccuranceFrequencyId() {
		return occuranceFrequencyId;
	}

	public void setOccuranceFrequencyId(Long occuranceFrequencyId) {
		this.occuranceFrequencyId = occuranceFrequencyId;
	}

	public String getOccuranceFrequencyCode() {
		return occuranceFrequencyCode;
	}

	public void setOccuranceFrequencyCode(String occuranceFrequencyCode) {
		this.occuranceFrequencyCode = occuranceFrequencyCode;
	}

	public String getOccuranceFrequencyName() {
		return occuranceFrequencyName;
	}

	public void setOccuranceFrequencyName(String occuranceFrequencyName) {
		this.occuranceFrequencyName = occuranceFrequencyName;
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

	public String getCalculationFrequencyName() {
		return calculationFrequencyName;
	}

	public void setCalculationFrequencyName(String calculationFrequencyName) {
		this.calculationFrequencyName = calculationFrequencyName;
	}

	public BigDecimal getFinalCost() {
		return finalCost;
	}

	public void setFinalCost(BigDecimal finalCost) {
		this.finalCost = finalCost;
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
