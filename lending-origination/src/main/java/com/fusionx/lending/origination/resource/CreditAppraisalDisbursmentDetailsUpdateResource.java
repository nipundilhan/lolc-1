package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


/**
 * CreditAppraisalDisbursmentDetailsUpdateResource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-10-2021      		     			PasinduT      Created
 *    
 ********************************************************************************************************
 */

public class CreditAppraisalDisbursmentDetailsUpdateResource extends CreditAppraisalDisbursmentDetailsAddResource {
	
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
