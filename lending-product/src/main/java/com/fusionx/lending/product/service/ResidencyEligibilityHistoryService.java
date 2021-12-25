package com.fusionx.lending.product.service;

/**
 * Residency eligibility service
 * @author 	Rangana
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019     FX-6        FX-6523    Rangana      Created
 *    
 ********************************************************************************************************
 */

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.ResidencyEligibility;


@Service
public interface ResidencyEligibilityHistoryService {
	
	/**
	 * @param tenantId
	 * @param residencyEligibility
	 * @param historyCreatedUser
	 */
	public void insertResidencyEligibilityHistory(String tenantId, ResidencyEligibility residencyEligibility, String historyCreatedUser);

}
