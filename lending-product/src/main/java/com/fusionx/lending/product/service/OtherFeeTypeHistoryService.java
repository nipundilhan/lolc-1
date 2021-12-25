package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.OtherFeeType;

/**
 * Other Fee Type History Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6604   	 FX-6509	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface OtherFeeTypeHistoryService {

	/**
	 * @author Senitha
	 * 
	 * Save Other Fee Type History
	 * @param tenantId
	 * @param otherFeeType
	 */
	public void insertOtherFeeTypeHistory(String tenantId, OtherFeeType otherFeeType);
	
}
