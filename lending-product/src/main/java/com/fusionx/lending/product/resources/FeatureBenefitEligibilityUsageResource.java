package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FeatureBenefitEligibilityUsageResource {

	@NotNull(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String featureBenefitEligibilityId;
	
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	
	public String getFeatureBenefitEligibilityId() {
		return featureBenefitEligibilityId;
	}

	public void setFeatureBenefitEligibilityId(String featureBenefitEligibilityId) {
		this.featureBenefitEligibilityId = featureBenefitEligibilityId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
