package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.RiskRatingLevelDetails;
import com.fusionx.lending.origination.domain.RiskRatingLevels;
/**
 * Risk Rating Levels
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-09-2021   FXL-338 		 FXL-684 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.resource.RiskRatingLevelDetailsAddResource;

@Service
public interface RiskRatingLevelDetailsService {

	/**
	 * 
	 * Insert RiskRatingLevels
	 * 
	 * @author Dilki
	 * @param - RiskRatingLevelDetailsAddResource
	 * @return - Successfully saved
	 * 
	 */
	public RiskRatingLevelDetails addRiskRatingLevelDetails(String tenantId, RiskRatingLevels riskRatingLevels,
			RiskRatingLevelDetailsAddResource riskRatingLevelDetailsAddResource);

}
