package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.CalculationFrequency;

/**
 * Calculation Frequency History Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-59   		 FX-6511	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface CalculationFrequencyHistoryService {
	
	/**
	 * @author Senitha
	 * 
	 * Save Calculation Frequency History
	 * @param tenantId
	 * @param calculationFrequency
	 */
	public void insertCalculationFrequencyHistory(String tenantId, CalculationFrequency calculationFrequency);

}
