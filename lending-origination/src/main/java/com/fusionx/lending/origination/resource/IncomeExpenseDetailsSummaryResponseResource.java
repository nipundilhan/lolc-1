package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.domain.IncomeExpenseDetails;

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
public class IncomeExpenseDetailsSummaryResponseResource {
	
	
	
	private String grossIncome;
	
	private String expences;
	
	private List<IncomeExpenseDetails> incomeExpenseDetails;

	

	public String getGrossIncome() {
		return grossIncome;
	}

	public void setGrossIncome(String grossIncome) {
		this.grossIncome = grossIncome;
	}

	public String getExpences() {
		return expences;
	}

	public void setExpences(String expences) {
		this.expences = expences;
	}

	public List<IncomeExpenseDetails> getIncomeExpenseDetails() {
		return incomeExpenseDetails;
	}

	public void setIncomeExpenseDetails(List<IncomeExpenseDetails> incomeExpenseDetails) {
		this.incomeExpenseDetails = incomeExpenseDetails;
	}
	
	

}
