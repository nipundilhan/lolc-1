package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.domain.CustomerCultivationIncome;

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
public class CustomerCultivationIncomeSummaryResponseResource {
	
	private String  customerCultivationGrossIncome;
	
	private String  customerCultivationExpense;
	
	private String  customerCultivationNetIncome;
	

	
	private List<CustomerCultivationIncome> cultivationIncome;
	
	private List<CustomerCultivationExpenseSummaryResponseResource> cultivationExpenseDetails;

	public String getCustomerCultivationGrossIncome() {
		return customerCultivationGrossIncome;
	}

	public void setCustomerCultivationGrossIncome(String customerCultivationGrossIncome) {
		this.customerCultivationGrossIncome = customerCultivationGrossIncome;
	}

	public String getCustomerCultivationExpense() {
		return customerCultivationExpense;
	}

	public void setCustomerCultivationExpense(String customerCultivationExpense) {
		this.customerCultivationExpense = customerCultivationExpense;
	}

	public String getCustomerCultivationNetIncome() {
		return customerCultivationNetIncome;
	}

	public void setCustomerCultivationNetIncome(String customerCultivationNetIncome) {
		this.customerCultivationNetIncome = customerCultivationNetIncome;
	}

	public List<CustomerCultivationIncome> getCultivationIncome() {
		return cultivationIncome;
	}

	public void setCultivationIncome(List<CustomerCultivationIncome> cultivationIncome) {
		this.cultivationIncome = cultivationIncome;
	}

	public List<CustomerCultivationExpenseSummaryResponseResource> getCultivationExpenseDetails() {
		return cultivationExpenseDetails;
	}

	public void setCultivationExpenseDetails(
			List<CustomerCultivationExpenseSummaryResponseResource> cultivationExpenseDetails) {
		this.cultivationExpenseDetails = cultivationExpenseDetails;
	}
	
	


}
