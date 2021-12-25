package com.fusionx.lending.product.service;
/**
 * Due Date Details History Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   23-09-2021    FXL-139  	 FXL-926	Piyumi      Created
 *    
 ********************************************************************************************************
 */

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.DueDateDetails;

@Service
public interface DueDateDetailsHistoryService {
	
	/**
	 * @param tenantId
	 * @param DueDateDetails
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId,
			DueDateDetails dueDateDetails, String historyCreatedUser);

}
