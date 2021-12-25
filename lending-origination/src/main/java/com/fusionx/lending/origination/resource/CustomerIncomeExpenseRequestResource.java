package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Customer Income Expense Request Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *   1   13-05-2020      		     			MenukaJ       Created
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CustomerIncomeExpenseRequestResource {
	
	@Valid
	List<BusinessIncomeExpenseRequestResource> businessIncomeExpenses;
	
	@Valid
	List<CustomerCultivationIncomeResource> customerCultivationIncomes;
	
	@Valid
	List<SalaryIncomeAddRequestResource> salaryIncomes;
	
	@Valid
	List<OtherIncomeAddRequestResource> otherIncomes;
	
	@Valid
	List<FinancialCommitmentAddRequestResource> financialCommitment;
	
	@Valid
	List<HouseHoldExpenseAddRequestResource> houseHoldExpense;
	
	
	

	

	public List<SalaryIncomeAddRequestResource> getSalaryIncomes() {
		return salaryIncomes;
	}

	public void setSalaryIncomes(List<SalaryIncomeAddRequestResource> salaryIncomes) {
		this.salaryIncomes = salaryIncomes;
	}

	

	public List<OtherIncomeAddRequestResource> getOtherIncomes() {
		return otherIncomes;
	}

	public void setOtherIncomes(List<OtherIncomeAddRequestResource> otherIncomes) {
		this.otherIncomes = otherIncomes;
	}

	public List<BusinessIncomeExpenseRequestResource> getBusinessIncomeExpenses() {
		return businessIncomeExpenses;
	}

	public void setBusinessIncomeExpenses(List<BusinessIncomeExpenseRequestResource> businessIncomeExpenses) {
		this.businessIncomeExpenses = businessIncomeExpenses;
	}

	public List<CustomerCultivationIncomeResource> getCustomerCultivationIncomes() {
		return customerCultivationIncomes;
	}

	public void setCustomerCultivationIncomes(List<CustomerCultivationIncomeResource> customerCultivationIncomes) {
		this.customerCultivationIncomes = customerCultivationIncomes;
	}

	public List<FinancialCommitmentAddRequestResource> getFinancialCommitment() {
		return financialCommitment;
	}

	public void setFinancialCommitment(List<FinancialCommitmentAddRequestResource> financialCommitment) {
		this.financialCommitment = financialCommitment;
	}

	public List<HouseHoldExpenseAddRequestResource> getHouseHoldExpense() {
		return houseHoldExpense;
	}

	public void setHouseHoldExpense(List<HouseHoldExpenseAddRequestResource> houseHoldExpense) {
		this.houseHoldExpense = houseHoldExpense;
	}

	
	
	


}
