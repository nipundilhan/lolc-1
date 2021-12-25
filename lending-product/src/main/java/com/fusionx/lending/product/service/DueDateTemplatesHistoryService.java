package com.fusionx.lending.product.service;
/**
 * Due Date Templates History Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   23-09-2021    FXL-139  	 FXL-926	Piyumi      Created
 *    
 ********************************************************************************************************
 */

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.DueDateTemplates;

@Service
public interface DueDateTemplatesHistoryService {
	
	/**
	 * @param tenantId
	 * @param DueDateTemplates
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId,
			DueDateTemplates dueDateTemplates, String historyCreatedUser);

}
