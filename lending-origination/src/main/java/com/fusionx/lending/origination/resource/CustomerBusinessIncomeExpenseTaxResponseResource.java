package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.domain.IncomeExpenseDetails;
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
public class CustomerBusinessIncomeExpenseTaxResponseResource {
	
	private String netIncome;
	
	private String profitAfterTax;
	
	private IncomeExpenseDetailsSummaryResponseResource incomeExpenseDetails;
	
	private IncomeExpenseTaxSummaryResponseResource TaxDetails;
	
	//private List<IncomeExpenseDetails> incomeExpenseDetails;
	
	//private List<IncomeExpenseTax> incomeExpenseTax;

	

	public String getNetIncome() {
		return netIncome;
	}

	public IncomeExpenseDetailsSummaryResponseResource getIncomeExpenseDetails() {
		return incomeExpenseDetails;
	}

	public void setIncomeExpenseDetails(IncomeExpenseDetailsSummaryResponseResource incomeExpenseDetails) {
		this.incomeExpenseDetails = incomeExpenseDetails;
	}

	public IncomeExpenseTaxSummaryResponseResource getTaxDetails() {
		return TaxDetails;
	}

	public void setTaxDetails(IncomeExpenseTaxSummaryResponseResource taxDetails) {
		TaxDetails = taxDetails;
	}

	public void setNetIncome(String netIncome) {
		this.netIncome = netIncome;
	}

	public String getProfitAfterTax() {
		return profitAfterTax;
	}

	public void setProfitAfterTax(String profitAfterTax) {
		this.profitAfterTax = profitAfterTax;
	}

	
	



	

}
