package com.fusionx.lending.origination.resource;
/**
 * 	Business Income Expenses Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-09-2021   FXL-115  	 FXL-785       Piyumi       Created
 *    
 ********************************************************************************************************
*/

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BusinessIncomeExpensesAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String businessIncomeDetailId;
	
    @Valid
	private List<BusinessIncomeExpenseTypeResource> businessIncomeExenseTypes;

	public String getBusinessIncomeDetailId() {
		return businessIncomeDetailId;
	}

	public void setBusinessIncomeDetailId(String businessIncomeDetailId) {
		this.businessIncomeDetailId = businessIncomeDetailId;
	}

	public List<BusinessIncomeExpenseTypeResource> getBusinessIncomeExenseTypes() {
		return businessIncomeExenseTypes;
	}

	public void setBusinessIncomeExenseTypes(List<BusinessIncomeExpenseTypeResource> businessIncomeExenseTypes) {
		this.businessIncomeExenseTypes = businessIncomeExenseTypes;
	}

	
}
