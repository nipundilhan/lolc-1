package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
Eligibility Template 
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   21-07-2019   FXL-1         FXL-42     Dilki        Created
*    
********************************************************************************************************
*/
public class EligibilityTemplateDisbursementUpdateResource extends EligibilityTemplateDisbursementAddResource {

	@NotBlank(message = "{version.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{version.pattern}")
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
