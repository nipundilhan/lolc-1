package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.domain.IncomeExpenseTax;

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
public class IncomeExpenseTaxSummaryResponseResource {
	
	private String taxAmount;
	
	private List<IncomeExpenseTax> incomeExpenseTax;

	

	public String getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}

	public List<IncomeExpenseTax> getIncomeExpenseTax() {
		return incomeExpenseTax;
	}

	public void setIncomeExpenseTax(List<IncomeExpenseTax> incomeExpenseTax) {
		this.incomeExpenseTax = incomeExpenseTax;
	}
	
	

}
