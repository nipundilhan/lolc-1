package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
import com.fusionx.lending.origination.domain.RiskRatingLevels;
import com.fusionx.lending.origination.resource.RiskRatingLevelAddResource;
import com.fusionx.lending.origination.resource.RiskRatingLevelUpdateResource;

@Service
public interface RiskRatingLevelsService {

	/**
	 * 
	 * Find all RiskRatingLevels
	 * 
	 * @author Dilki
	 * @return JSON array of all RiskRatingLevels
	 * 
	 */
	public List<RiskRatingLevels> getAll();

	/**
	 * 
	 * Find RiskRatingLevels by ID
	 * 
	 * @author Dilki
	 * @return JSON array of RiskRatingLevels
	 * 
	 */
	public Optional<RiskRatingLevels> getById(Long id);

	/**
	 * 
	 * Find RiskRatingLevels by risk type id
	 * 
	 * @author Dilki
	 * @return JSON array of RiskRatingLevels
	 * 
	 */
	public List<RiskRatingLevels> getByRiskTypeId(Long riskTypeId);

	/**
	 * 
	 * Find RiskRatingLevels by status
	 * 
	 * @author Dilki
	 * @return JSON array of RiskRatingLevels
	 * 
	 */
	public List<RiskRatingLevels> getByStatus(String status);

	/**
	 * 
	 * Insert RiskRatingLevels
	 * 
	 * @author Dilki
	 * @param RiskRatingLevelAddResource
	 * @return Successfully saved
	 * 
	 */
	public RiskRatingLevels addRiskRatingLevels(String tenantId, RiskRatingLevelAddResource riskRatingLevelAddResource);

	/**
	 * 
	 * Update RiskRatingLevels
	 * 
	 * @author Dilki
	 * @param RiskRatingLevelUpdateResource
	 * @return Successfully updated
	 * 
	 */
	public RiskRatingLevels updateRiskRatingLevels(String tenantId, Long id,
			RiskRatingLevelUpdateResource riskRatingLevelUpdateResource);

}
