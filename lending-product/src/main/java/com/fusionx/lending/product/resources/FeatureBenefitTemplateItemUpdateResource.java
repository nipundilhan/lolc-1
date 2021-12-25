package com.fusionx.lending.product.resources;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FeatureBenefitTemplateItemUpdateResource {

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String featureBenefitTemplatePendingId;

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String featureBenefitTemplateItemId;
	
	@Valid
	private FeatureBenefitItemUsageResource featureBenefitItemUsageResource;



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

	public FeatureBenefitItemUsageResource getFeatureBenefitItemUsageResource() {
		return featureBenefitItemUsageResource;
	}

	public void setFeatureBenefitItemUsageResource(FeatureBenefitItemUsageResource featureBenefitItemUsageResource) {
		this.featureBenefitItemUsageResource = featureBenefitItemUsageResource;
	}
	
	
	
}
