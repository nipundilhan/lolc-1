package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EligibilityTemplateDisbursementAddResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String eligibilityId;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String eligibilityName;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String disbursementId;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String disbursementName;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	public String getEligibilityId() {
		return eligibilityId;
	}

	public void setEligibilityId(String eligibilityId) {
		this.eligibilityId = eligibilityId;
	}

	public String getEligibilityName() {
		return eligibilityName;
	}

	public void setEligibilityName(String eligibilityName) {
		this.eligibilityName = eligibilityName;
	}

	public String getDisbursementId() {
		return disbursementId;
	}

	public void setDisbursementId(String disbursementId) {
		this.disbursementId = disbursementId;
	}

	public String getDisbursementName() {
		return disbursementName;
	}

	public void setDisbursementName(String disbursementName) {
		this.disbursementName = disbursementName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
