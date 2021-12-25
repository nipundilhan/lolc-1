package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	House Hold Expense Details Update Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-09-2021                               Dilhan       Created
 *    
 ********************************************************************************************************
*/
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class HouseHoldExpenseDetailsUpdateResource extends HouseHoldExpenseDetailsAddResource{

	//@NotBlank(message = "{common.not-null}")
//	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
//	private String version;
//
//
//	public String getVersion() {
//		return version;
//	}
//
//	public void setVersion(String version) {
//		this.version = version;
//	}
}
