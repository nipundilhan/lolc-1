package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	House Hold Expense Details Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-09-2021                                Dilhan       Created
 *    
 ********************************************************************************************************
*/
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class HouseHoldExpenseDetailsAddResource {

	@NotNull(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String incomeSourceDetailsId;
	
	@Valid
	List<HouseHoldExpenseInfoResource> houseHoldExpenseInfo;

	public String getIncomeSourceDetailsId() {
		return incomeSourceDetailsId;
	}

	public void setIncomeSourceDetailsId(String incomeSourceDetailsId) {
		this.incomeSourceDetailsId = incomeSourceDetailsId;
	}

	public List<HouseHoldExpenseInfoResource> getHouseHoldExpenseInfo() {
		return houseHoldExpenseInfo;
	}

	public void setHouseHoldExpenseInfo(List<HouseHoldExpenseInfoResource> houseHoldExpenseInfo) {
		this.houseHoldExpenseInfo = houseHoldExpenseInfo;
	}

}