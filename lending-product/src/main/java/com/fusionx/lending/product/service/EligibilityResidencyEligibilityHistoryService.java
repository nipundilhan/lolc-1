package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.EligibilityResidencyEligibility;

/**
 * EligibilityResidencyEligibilityHistoryService
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   28-07-2021    FXL_July_2021_2  	FXL-55		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Service
public interface EligibilityResidencyEligibilityHistoryService {
	
	/**
	 * @param tenantId
	 * @param EligibilityResidencyEligibility
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, EligibilityResidencyEligibility eligibilityResidencyEligibility, String historyCreatedUser);

}
