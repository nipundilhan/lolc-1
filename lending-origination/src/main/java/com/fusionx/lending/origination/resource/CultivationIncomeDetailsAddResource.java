package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Cultivation Income Details Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-09-2021   FXL-115  	 FXL-661       Dilhan       Created
 *    
 ********************************************************************************************************
*/

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CultivationIncomeDetailsAddResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String incomeSourceDetailsId;
	
	@Valid
	private List<CultivationIncomeDetailsListResource> cultivationIncomeDetailsList;

	public String getIncomeSourceDetailsId() {
		return incomeSourceDetailsId;
	}

	public void setIncomeSourceDetailsId(String incomeSourceDetailsId) {
		this.incomeSourceDetailsId = incomeSourceDetailsId;
	}

	public List<CultivationIncomeDetailsListResource> getCultivationIncomeDetailsList() {
		return cultivationIncomeDetailsList;
	}

	public void setCultivationIncomeDetailsList(List<CultivationIncomeDetailsListResource> cultivationIncomeDetailsList) {
		this.cultivationIncomeDetailsList = cultivationIncomeDetailsList;
	}
}

