package com.fusionx.lending.origination.resource;

/**
 * 	Other Income Expenses
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-09-2021   FXL-641  	 FXL-792       Dilki        Created
 *    
 ********************************************************************************************************
*/

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class OtherIncomeExpensesAddResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String otherIncomeDetailsId;

	@Valid
	private List<OtherIncomeExpensesResource> otherIncomeExpenses;

	public String getOtherIncomeDetailsId() {
		return otherIncomeDetailsId;
	}

	public void setOtherIncomeDetailsId(String otherIncomeDetailsId) {
		this.otherIncomeDetailsId = otherIncomeDetailsId;
	}

	public List<OtherIncomeExpensesResource> getOtherIncomeExpenses() {
		return otherIncomeExpenses;
	}

	public void setOtherIncomeExpenses(List<OtherIncomeExpensesResource> otherIncomeExpenses) {
		this.otherIncomeExpenses = otherIncomeExpenses;
	}

}
