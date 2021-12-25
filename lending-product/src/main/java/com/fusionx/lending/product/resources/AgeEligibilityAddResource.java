package com.fusionx.lending.product.resources;

/**
 * Age Eligibility service
 * @author 	MenukaJ
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-07-2021  						    MenukaJ      Created
 *    
 ********************************************************************************************************
 */

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AgeEligibilityAddResource {
	
	@NotBlank(message="{common.not-null}")
	@Size(max = 3, message = "{age.size}") 
	@Pattern(regexp = "[0-9]+",message="{common-numeric.pattern}")
	private String minimumAge;
	
	@NotBlank(message="{common.not-null}")
	@Size(max = 3, message = "{age.size}") 
	@Pattern(regexp = "[0-9]+",message="{common-numeric.pattern}")
	private String maximumAge;
	
	@NotBlank(message="{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	public String getMinimumAge() {
		return minimumAge;
	}

	public void setMinimumAge(String minimumAge) {
		this.minimumAge = minimumAge;
	}

	public String getMaximumAge() {
		return maximumAge;
	}

	public void setMaximumAge(String maximumAge) {
		this.maximumAge = maximumAge;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
