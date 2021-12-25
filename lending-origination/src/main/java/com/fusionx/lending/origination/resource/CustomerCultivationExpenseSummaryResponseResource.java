package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.domain.CustomerCultivationExpense;

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
public class CustomerCultivationExpenseSummaryResponseResource {
	
	private String finalCost;
	
	private List<CustomerCultivationExpense> customerCultivationExpense;

	public String getFinalCost() {
		return finalCost;
	}

	public void setFinalCost(String finalCost) {
		this.finalCost = finalCost;
	}

	public List<CustomerCultivationExpense> getCustomerCultivationExpense() {
		return customerCultivationExpense;
	}

	public void setCustomerCultivationExpense(List<CustomerCultivationExpense> customerCultivationExpense) {
		this.customerCultivationExpense = customerCultivationExpense;
	}
	
	

}
