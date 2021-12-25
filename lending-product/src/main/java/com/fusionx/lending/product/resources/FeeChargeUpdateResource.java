package com.fusionx.lending.product.resources;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
/**
 * Fee Charge  Update Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-06-2021      		     			MenukaJ      Created
 *    
 ********************************************************************************************************
 */
public class FeeChargeUpdateResource extends FeeChargeAddResource {
	
	
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
