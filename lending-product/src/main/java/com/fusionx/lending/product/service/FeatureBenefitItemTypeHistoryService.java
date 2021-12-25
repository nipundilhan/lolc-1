package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.FeatureBenefitItemType;

/**
 * Feature Benefit Item Type Service - Feature Benefit Item Type Domain entity
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
public interface FeatureBenefitItemTypeHistoryService {
	
	/**
	 * @param tenantId
	 * @param FeatureBenefitItemType
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, FeatureBenefitItemType featureBenefitItemType, String historyCreatedUser);

}
