package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.FeatureBenefitGroupType;

/**
 * Feature Benefit Group Type Service - Feature Benefit Group Type Domain entity
 * @author 	Sanatha
 * @Date    15-JUN-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-JUN-2021  						     Sanatha      Created
 *    
 ********************************************************************************************************
 */
@Service
public interface FeatureBenefitGroupTypeHistoryService {
	
	/**
	 * @param tenantId
	 * @param FeatureBenefitGroupType
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, FeatureBenefitGroupType featureBenefitGroupType, String historyCreatedUser);

}
