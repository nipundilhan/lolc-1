package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Account Status Add Resource
 * 
 ********************************************************************************************************
 * ### Date Story Point Task No Author Description
 * -------------------------------------------------------------------------------------------------------
 * 1 14-10-2021 FXL-174 FXL-1170 Pasindu Created
 * 
 ********************************************************************************************************
 */
public class RepaymentFrequencyUpdateResource extends RepaymentFrequencyAddResource{

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
