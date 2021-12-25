package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.OtherFeeRateType;

/**
 * Other Fee Rate Type History Service
 *  
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6605   	 FX-6510	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface OtherFeeRateTypeHistoryService {

	/**
	 * @author Senitha
	 * 
	 * Save Other Fee Rate Type History
	 * @param tenantId
	 * @param otherFeeRateType
	 */
	public void insertOtherFeeRateTypeHistory(String tenantId, OtherFeeRateType otherFeeRateType);
	
}
