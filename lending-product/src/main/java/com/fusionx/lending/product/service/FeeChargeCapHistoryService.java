package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.FeeChargeCap;

/**
 * Fee Charge  History Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   19-07-2021                           Dilhan        Created
 *    
 ********************************************************************************************************
 */
@Service
public interface FeeChargeCapHistoryService {
	
	/**
	 * @param tenantId
	 * @param feeChargeCap
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, FeeChargeCap feeChargeCap, String historyCreatedUser);

}
