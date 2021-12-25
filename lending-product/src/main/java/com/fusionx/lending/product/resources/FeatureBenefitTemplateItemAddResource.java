package com.fusionx.lending.product.resources;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FeatureBenefitTemplateItemAddResource {


	@NotNull(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String featureBenefitTemplateId;
	
	@Valid
	private FeatureBenefitItemUsageResource featureBenefitItemUsageResource;
	


	
	public String getFeatureBenefitTemplateId() {
		return featureBenefitTemplateId;
	}

	public void setFeatureBenefitTemplateId(String featureBenefitTemplateId) {
		this.featureBenefitTemplateId = featureBenefitTemplateId;
	}

	public FeatureBenefitItemUsageResource getFeatureBenefitItemUsageResource() {
		return featureBenefitItemUsageResource;
	}

	public void setFeatureBenefitItemUsageResource(FeatureBenefitItemUsageResource featureBenefitItemUsageResource) {
		this.featureBenefitItemUsageResource = featureBenefitItemUsageResource;
	}


}
