package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Salary Income Details Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-09-2021   FXL-115  	 FXL-658       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SalaryIncomeDetailsAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String incomeSourceDetailsId;
	
	@Valid
	private List<SalaryIncomeDetailsListResource> salaryIncomeDetailsList;


	public String getIncomeSourceDetailsId() {
		return incomeSourceDetailsId;
	}


	public void setIncomeSourceDetailsId(String incomeSourceDetailsId) {
		this.incomeSourceDetailsId = incomeSourceDetailsId;
	}


	public List<SalaryIncomeDetailsListResource> getSalaryIncomeDetailsList() {
		return salaryIncomeDetailsList;
	}


	public void setSalaryIncomeDetailsList(List<SalaryIncomeDetailsListResource> salaryIncomeDetailsList) {
		this.salaryIncomeDetailsList = salaryIncomeDetailsList;
	}

}
