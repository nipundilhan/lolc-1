package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Salary Expense Type Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-08-2021   	FXL-521   	 FX-685		Piyumi      Created
 *    
 ********************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SalaryExpenseTypeAddResource {
	
	private List<ExpenseTypeListResource> expenseTypeList;

	public List<ExpenseTypeListResource> getExpenseTypeList() {
		return expenseTypeList;
	}

	public void setExpenseTypeList(List<ExpenseTypeListResource> expenseTypeList) {
		this.expenseTypeList = expenseTypeList;
	}

}
