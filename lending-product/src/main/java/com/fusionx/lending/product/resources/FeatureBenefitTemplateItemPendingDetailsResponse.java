package com.fusionx.lending.product.resources;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItemEligibilityPending;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItemPending;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FeatureBenefitTemplateItemPendingDetailsResponse {
	
	
	private FeatureBenefitTemplateItemPending featureBenefitTemplateItemPending;
	
	private List<FeatureBenefitTemplateItemEligibilityPending> featureBenefitTemplateEligibilityPendingList;


	public FeatureBenefitTemplateItemPending getFeatureBenefitTemplateItemPending() {
		return featureBenefitTemplateItemPending;
	}

	public void setFeatureBenefitTemplateItemPending(FeatureBenefitTemplateItemPending featureBenefitTemplateItemPending) {
		this.featureBenefitTemplateItemPending = featureBenefitTemplateItemPending;
	}

	public List<FeatureBenefitTemplateItemEligibilityPending> getFeatureBenefitTemplateEligibilityPendingList() {
		return featureBenefitTemplateEligibilityPendingList;
	}

	public void setFeatureBenefitTemplateEligibilityPendingList(
			List<FeatureBenefitTemplateItemEligibilityPending> featureBenefitTemplateEligibilityPendingList) {
		this.featureBenefitTemplateEligibilityPendingList = featureBenefitTemplateEligibilityPendingList;
	}
}
