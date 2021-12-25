package com.fusionx.lending.product.resources;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.product.domain.FeatureBenifitTemplatePending;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FeatureBenifitTemplatePendingDetailsResponse {

	private FeatureBenifitTemplatePending featureBenifitTemplatePending;
	
	private List<FeatureBenefitTemplateItemPendingDetailsResponse> featureBenefitTemplateItemPendingDetailsList;

	public FeatureBenifitTemplatePending getFeatureBenifitTemplatePending() {
		return featureBenifitTemplatePending;
	}

	public void setFeatureBenifitTemplatePending(FeatureBenifitTemplatePending featureBenifitTemplatePending) {
		this.featureBenifitTemplatePending = featureBenifitTemplatePending;
	}

	public List<FeatureBenefitTemplateItemPendingDetailsResponse> getFeatureBenefitTemplateItemPendingDetailsList() {
		return featureBenefitTemplateItemPendingDetailsList;
	}

	public void setFeatureBenefitTemplateItemPendingDetailsList(
			List<FeatureBenefitTemplateItemPendingDetailsResponse> featureBenefitTemplateItemPendingDetailsList) {
		this.featureBenefitTemplateItemPendingDetailsList = featureBenefitTemplateItemPendingDetailsList;
	}



}
