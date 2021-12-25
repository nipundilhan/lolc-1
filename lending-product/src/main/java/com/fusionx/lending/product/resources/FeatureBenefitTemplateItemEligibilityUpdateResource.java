package com.fusionx.lending.product.resources;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FeatureBenefitTemplateItemEligibilityUpdateResource {

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String featureBenefitTemplatePendingId;
	
	@NotNull(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String featureBenefitTemplateItemId;

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String featureBenefitTemplateItemEligibilityId;
	
	@Valid
	private FeatureBenefitEligibilityUsageResource featureBenefitEligibilityUsageResource;

	public String getFeatureBenefitTemplatePendingId() {
		return featureBenefitTemplatePendingId;
	}

	public void setFeatureBenefitTemplatePendingId(String featureBenefitTemplatePendingId) {
		this.featureBenefitTemplatePendingId = featureBenefitTemplatePendingId;
	}

	public String getFeatureBenefitTemplateItemId() {
		return featureBenefitTemplateItemId;
	}

	public void setFeatureBenefitTemplateItemId(String featureBenefitTemplateItemId) {
		this.featureBenefitTemplateItemId = featureBenefitTemplateItemId;
	}

	public String getFeatureBenefitTemplateItemEligibilityId() {
		return featureBenefitTemplateItemEligibilityId;
	}

	public void setFeatureBenefitTemplateItemEligibilityId(String featureBenefitTemplateItemEligibilityId) {
		this.featureBenefitTemplateItemEligibilityId = featureBenefitTemplateItemEligibilityId;
	}

	public FeatureBenefitEligibilityUsageResource getFeatureBenefitEligibilityUsageResource() {
		return featureBenefitEligibilityUsageResource;
	}

	public void setFeatureBenefitEligibilityUsageResource(
			FeatureBenefitEligibilityUsageResource featureBenefitEligibilityUsageResource) {
		this.featureBenefitEligibilityUsageResource = featureBenefitEligibilityUsageResource;
	}
	
	
	
}
