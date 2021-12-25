package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.EligibilityOther;

/**
 * Eligibility Other History Service 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   27-07-2021    FXL_July_2021_2  	FXL-54		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Service
public interface EligibilityOtherHistoryService {
	
	/**
	 * @param tenantId
	 * @param EligibilityOther
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, EligibilityOther eligibilityOther, String historyCreatedUser);

}
