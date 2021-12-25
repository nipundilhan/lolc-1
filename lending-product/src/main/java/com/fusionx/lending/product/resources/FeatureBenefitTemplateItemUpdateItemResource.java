package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FeatureBenefitTemplateItemUpdateItemResource {
	

	private Long featureBenefitTemplateItemId;
	
	private FeatureBenefitItemUsageResource FeatureBenefitItemUsageResource;
	
	private String actionType;

	public Long getFeatureBenefitTemplateItemId() {
		return featureBenefitTemplateItemId;
	}

	public void setFeatureBenefitTemplateItemId(Long featureBenefitTemplateItemId) {
		this.featureBenefitTemplateItemId = featureBenefitTemplateItemId;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	
	public FeatureBenefitItemUsageResource getFeatureBenefitItemUsageResource() {
		return FeatureBenefitItemUsageResource;
	}

	public void setFeatureBenefitItemUsageResource(FeatureBenefitItemUsageResource featureBenefitItemUsageResource) {
		FeatureBenefitItemUsageResource = featureBenefitItemUsageResource;
	}

}
