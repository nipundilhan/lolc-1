package com.fusionx.lending.origination.resource;
/**
 * 	Business Tax Details Add Resource
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
public class BusinessTaxDetailsAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String businessIncomeDetailId;
	
	@Valid
	private List<BusinessTaxTypeResource> businessTaxDetails;

	public String getBusinessIncomeDetailId() {
		return businessIncomeDetailId;
	}

	public void setBusinessIncomeDetailId(String businessIncomeDetailId) {
		this.businessIncomeDetailId = businessIncomeDetailId;
	}

	public List<BusinessTaxTypeResource> getBusinessTaxDetails() {
		return businessTaxDetails;
	}

	public void setBusinessTaxDetails(List<BusinessTaxTypeResource> businessTaxDetails) {
		this.businessTaxDetails = businessTaxDetails;
	}

	
}
