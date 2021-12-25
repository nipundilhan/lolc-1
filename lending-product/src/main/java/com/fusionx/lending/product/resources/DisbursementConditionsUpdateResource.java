package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Disbursement Conditions Update Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-09-2021      		     FXL-788	Dilhan      Created
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DisbursementConditionsUpdateResource extends DisbursementConditionsAddResource{

	@NotBlank(message="{version.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{version.pattern}") 
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
