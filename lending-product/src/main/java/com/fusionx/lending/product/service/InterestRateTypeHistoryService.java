package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.InterestRateType;

/**
 * Interest Rate Type History Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6601   	 FX-6508	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface InterestRateTypeHistoryService {

	/**
	 * @author Senitha
	 * 
	 * Save Interest Rate Type History
	 * @param tenantId
	 * @param interestRateType
	 */
	public void insertInterestRateTypeHistory(String tenantId, InterestRateType interestRateType);

}
