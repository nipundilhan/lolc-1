package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.Segments;

/**
 * Segment History Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-2880   	 FX-6515	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface SegmentsHistoryService {
	
	/**
	 * @author Senitha
	 * 
	 * Save Segments History
	 * @param tenantId
	 * @param segments
	 */
	public void insertSegmentsHistory(String tenantId, Segments segments);

}
