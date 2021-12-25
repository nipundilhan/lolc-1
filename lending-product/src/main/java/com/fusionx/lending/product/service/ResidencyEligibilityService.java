package com.fusionx.lending.product.service;

/**
 * Residency eligibility service
 * @author 	Rangana
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019     FX-6        FX-6523    Rangana      Created
 *    
 ********************************************************************************************************
 */

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.ResidencyEligibility;
import com.fusionx.lending.product.resources.ResidencyEligibilityResource;
import com.fusionx.lending.product.resources.ResidencyEligibilityUpdateResource;

@Service
public interface ResidencyEligibilityService {
	
	/**
     * Find all Residency Eligibility
	 * @author Rangana
	 * 
	 * @return   		- JSON Array of all Other Eligibility objects
	 * 
	 */
	List<ResidencyEligibility> findAll(String tenantId);
	
	/**
     * get all Residency Eligibility
	 * @author Rangana
	 * 
	 * @return   		- JSON Array of all Other Eligibility objects
	 * 
	 */
	List<ResidencyEligibility> getAll();
	
	/**
     * Find Residency Eligibility by Id
	 * @author Rangana
	 * 
	 * @return   		- JSON Array of Other Eligibility objects
	 * 
	 */
	public Optional<ResidencyEligibility> findResidencyEligibilityDetailById(String tenantId, Long residencyEligibilityId);
	
	/**
     * Find Residency Eligibility by Id
	 * @author Rangana
	 * 
	 * @return   		- JSON Array of Other Eligibility objects
	 * 
	 */
	public Optional<ResidencyEligibility> findById(String tenantId, Long residencyEligibilityId);
	
	/**
     * Find Residency Eligibility by Status
	 * @author Rangana
	 * 
	 * @return   		- JSON Array of Other Eligibility objects
	 * 
	 */
	public List<ResidencyEligibility> findByStatus(String tenantId, String status);
	
	/**
     * Insert Residency Eligibility
	 * @author Rangana
	 * 
	 * @param	- ResidencyEligibilityAddResource
	 * @return  - Successfully saved message
	 * 
	 */
	public ResidencyEligibility addResidencyEligibility(String tenantId, ResidencyEligibilityResource residencyEligibilityAddResource, String userName);
	
	/**
     * Update Residency Eligibility
	 * @author Rangana
	 * 
	 * @param	- OtherEligibilityUpdateResource
	 * @return  - Successfully saved message
	 * 
	 */
	public ResidencyEligibility updateResidencyEligibility(String tenantId, Long id, ResidencyEligibilityUpdateResource residencyEligibilityUpdateResource, String userName);
	
	/**
     * Find Residency Eligibility by code
	 * @author Rangana
	 * 
	 * @return   		- JSON Array of Other Eligibility objects
	 * 
	 */
	public Optional<ResidencyEligibility> getResidencyEligibilityByCode(String residencyEligibilityCode);
	
}
