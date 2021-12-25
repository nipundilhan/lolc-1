package com.fusionx.lending.product.resources;

/**
 * Residency eligibility service
 * @author 	Rangana
 * @Dat     08-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-06-2021     FX-6        FX-6524    Rangana      Created
 *    
 ********************************************************************************************************
 */

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResidencyIncludeUpdateResource extends ResidencyIncludeResource {
	
	
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
