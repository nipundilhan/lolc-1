package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.FeeChargeDetails;

/**
 * Fee Charge Details History Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-08-2021                           Dilhan        Created
 *    
 ********************************************************************************************************
 */
@Service
public interface FeeChargeDetailsHistoryService {

	/**
	 * @param tenantId
	 * @param feeChargeDetails
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, FeeChargeDetails feeChargeDetails, String historyCreatedUser);
}
