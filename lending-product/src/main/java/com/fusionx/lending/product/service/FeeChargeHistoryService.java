package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.FeeCharge;

/**
 * Fee Charge  History Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-06-2021                           MenukaJ        Created
 *    
 ********************************************************************************************************
 */
@Service
public interface FeeChargeHistoryService {
	
	/**
	 * @param tenantId
	 * @param feeCharge
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, FeeCharge feeCharge, String historyCreatedUser);

}
