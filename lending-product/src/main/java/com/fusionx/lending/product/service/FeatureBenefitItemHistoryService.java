package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.FeatureBenefitItem;

public interface FeatureBenefitItemHistoryService {
	
	
	void insertFeatureBenefitItemHistory(String tenantId, FeatureBenefitItem featureBenefitItem, String historyCreatedUser);


}
