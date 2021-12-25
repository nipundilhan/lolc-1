package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.FeatureBenefitEligibilityType;

/**
 * Feature Benefit Eligibility Type History Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-06-2021                           MenukaJ        Created
 *    
 ********************************************************************************************************
 */
@Service
public interface FeatureBenefitEligibilityTypeHistoryService {
	
	/**
	 * @param tenantId
	 * @param FeatureBenefitEligibilityType
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, FeatureBenefitEligibilityType featureBenefitEligibilityType, String historyCreatedUser);

}
