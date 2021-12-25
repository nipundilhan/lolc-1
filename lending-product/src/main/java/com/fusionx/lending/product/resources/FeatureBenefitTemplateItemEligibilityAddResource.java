package com.fusionx.lending.product.resources;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FeatureBenefitTemplateItemEligibilityAddResource {

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String featureBenefitTemplateId;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String featureBenefitTemplateItemId;
	
	@Valid
	private FeatureBenefitEligibilityUsageResource featureBenefitEligibilityUsageResource;


	
	public String getFeatureBenefitTemplateId() {
		return featureBenefitTemplateId;
	}

	public void setFeatureBenefitTemplateId(String featureBenefitTemplateId) {
		this.featureBenefitTemplateId = featureBenefitTemplateId;
	}

	public String getFeatureBenefitTemplateItemId() {
		return featureBenefitTemplateItemId;
	}

	public void setFeatureBenefitTemplateItemId(String featureBenefitTemplateItemId) {
		this.featureBenefitTemplateItemId = featureBenefitTemplateItemId;
	}

	public FeatureBenefitEligibilityUsageResource getFeatureBenefitEligibilityUsageResource() {
		return featureBenefitEligibilityUsageResource;
	}

	public void setFeatureBenefitEligibilityUsageResource(
			FeatureBenefitEligibilityUsageResource featureBenefitEligibilityUsageResource) {
		this.featureBenefitEligibilityUsageResource = featureBenefitEligibilityUsageResource;
	}

}
