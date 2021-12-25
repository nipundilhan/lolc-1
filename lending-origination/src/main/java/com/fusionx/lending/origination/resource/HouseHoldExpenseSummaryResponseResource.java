package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.domain.HouseHoldExpense;

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
public class HouseHoldExpenseSummaryResponseResource {
	
	private String totalHouseHoldExpense;
	
	private List<HouseHoldExpense> HouseHoldExpenseDetails;

	public String getTotalHouseHoldExpense() {
		return totalHouseHoldExpense;
	}

	public void setTotalHouseHoldExpense(String totalHouseHoldExpense) {
		this.totalHouseHoldExpense = totalHouseHoldExpense;
	}

	public List<HouseHoldExpense> getHouseHoldExpenseDetails() {
		return HouseHoldExpenseDetails;
	}

	public void setHouseHoldExpenseDetails(List<HouseHoldExpense> houseHoldExpenseDetails) {
		HouseHoldExpenseDetails = houseHoldExpenseDetails;
	}
	
	

}
