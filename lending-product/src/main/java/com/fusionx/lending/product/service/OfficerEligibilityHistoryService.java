package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.OfficerEligibility;

/**
 * Officer Eligibility History Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-06-2020   			   	        	Thamokshi    Created
 *    
 ********************************************************************************************************
 */

@Service
public interface OfficerEligibilityHistoryService {

	/**
     * Insert Officer Eligibility history
	 * @author Thamokshi
	 * 
	 * @param	- tennantId
	 * @Param   - Officer Eligibility Object
	 * @Param   - History user
	 * @return  - Successfully saved message
	 * 
	 */
	void insertOfficerEligibilityHistory(String tenantId, OfficerEligibility officerEligibility, String historyCreatedUser );

}
