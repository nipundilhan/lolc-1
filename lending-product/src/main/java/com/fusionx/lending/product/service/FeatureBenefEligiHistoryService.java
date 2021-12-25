package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.FeatureBenefEligiHistory;
import com.fusionx.lending.product.domain.FeatureBenefitEligibility;

/**
 * feature benefit eligibility Service - Domain entity
 * @author 	Sanatha
 * @Date    24-JUN-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-JUN-2021   					     Sanatha      Initial development.
 *    
 ********************************************************************************************************
 */
public interface FeatureBenefEligiHistoryService {

	/**
     * Insert FeatureBenefEligiHistory details  
     * @author Sanatha
     * 
     * @param FeatureBenefEligiHistory  - All column detail of new FeatureBenefEligiHistory type
     * @return      - Success message.
     * 
     */
	public FeatureBenefEligiHistory addFeatureBenefEligiHistory(String tenantId, FeatureBenefitEligibility featureBenefitEligibility,String cUser);
}
