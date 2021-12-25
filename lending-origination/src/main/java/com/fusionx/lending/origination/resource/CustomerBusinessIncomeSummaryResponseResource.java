package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.domain.BusinessIncomeExpense;

/**
 * Salary Income Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6301    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CustomerBusinessIncomeSummaryResponseResource {
	
	private String  customerBusinessGrossIncome;
	
	private String  customerBusinessExpense;
	
	private String  customerBusinessNetIncome;
	
	private String  customerBusinessTaxAmount;
	
	private String  customerBusinessProfitAfterTax;
	
	
	private List<BusinessIncomeExpense> businessIncomeExpenses;
	
	private List<CustomerBusinessIncomeExpenseTaxResponseResource> businessIncomeExpenseTaxDetails;

	

	public String getCustomerBusinessGrossIncome() {
		return customerBusinessGrossIncome;
	}

	public void setCustomerBusinessGrossIncome(String customerBusinessGrossIncome) {
		this.customerBusinessGrossIncome = customerBusinessGrossIncome;
	}

	public String getCustomerBusinessExpense() {
		return customerBusinessExpense;
	}

	public void setCustomerBusinessExpense(String customerBusinessExpense) {
		this.customerBusinessExpense = customerBusinessExpense;
	}

	public String getCustomerBusinessNetIncome() {
		return customerBusinessNetIncome;
	}

	public void setCustomerBusinessNetIncome(String customerBusinessNetIncome) {
		this.customerBusinessNetIncome = customerBusinessNetIncome;
	}

	public String getCustomerBusinessTaxAmount() {
		return customerBusinessTaxAmount;
	}

	public void setCustomerBusinessTaxAmount(String customerBusinessTaxAmount) {
		this.customerBusinessTaxAmount = customerBusinessTaxAmount;
	}

	public String getCustomerBusinessProfitAfterTax() {
		return customerBusinessProfitAfterTax;
	}

	public void setCustomerBusinessProfitAfterTax(String customerBusinessProfitAfterTax) {
		this.customerBusinessProfitAfterTax = customerBusinessProfitAfterTax;
	}

	

	public List<BusinessIncomeExpense> getBusinessIncomeExpenses() {
		return businessIncomeExpenses;
	}

	public void setBusinessIncomeExpenses(List<BusinessIncomeExpense> businessIncomeExpenses) {
		this.businessIncomeExpenses = businessIncomeExpenses;
	}

	public List<CustomerBusinessIncomeExpenseTaxResponseResource> getBusinessIncomeExpenseTaxDetails() {
		return businessIncomeExpenseTaxDetails;
	}

	public void setBusinessIncomeExpenseTaxDetails(
			List<CustomerBusinessIncomeExpenseTaxResponseResource> businessIncomeExpenseTaxDetails) {
		this.businessIncomeExpenseTaxDetails = businessIncomeExpenseTaxDetails;
	}
	
	
	


}
