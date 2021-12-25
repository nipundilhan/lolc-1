package com.fusionx.lending.product.service;

/**
 * Residency eligibility service
 * @author 	Rangana
 * @Dat     08-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-06-2021     FX-6        FX-6524    Rangana      Created
 *    
 ********************************************************************************************************
 */

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.ResidencyInclude;


@Service
public interface ResidencyIncludeHistoryService {
	
	/**
     * Insert Residency Include history
	 * @author Rangana
	 * 
	 * @param	- tennantId
	 * @Param   - Residency Include Object
	 * @Param   - History user
	 * @return  - Successfully saved message
	 * 
	 */

	public void insertResidencyIncludeHistory(String tenantId, ResidencyInclude residencyInclude, String historyCreatedUser );

}
