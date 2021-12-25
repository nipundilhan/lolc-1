package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.EligibilityCustomerType;

/**
 * EligibilityCustomerTypeHistoryService
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   29-07-2021    FXL_365			   	FXL-56		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Service
public interface EligibilityCustomerTypeHistoryService {
	
	/**
	 * @param tenantId
	 * @param EligibilityCustomerType
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, EligibilityCustomerType eligibilityCustomerType, String historyCreatedUser);

}
