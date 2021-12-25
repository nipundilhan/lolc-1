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

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.ResidencyInclude;
import com.fusionx.lending.product.resources.ResidencyIncludeResource;
import com.fusionx.lending.product.resources.ResidencyIncludeUpdateResource;

@Service
public interface ResidencyIncludeService {
	
	/**
     * Find all Residency Include
	 * @author Rangana
	 * 
	 * @return   		- JSON Array of all Other Include objects
	 * 
	 */
	List<ResidencyInclude> findAll(String tenantId);
	
	/**
     * Find Residency Include by Id
	 * @author Rangana
	 * 
	 * @return   		- JSON Array of Other Include objects
	 * 
	 */
	public Optional<ResidencyInclude> findById(String tenantId, Long residencyIncludeId);
	
	/**
     * Find Residency Include by Residency Eligibility Id
	 * @author Rangana
	 * 
	 * @return   		- JSON Array of Other Include objects
	 * 
	 */
	public Optional<List<ResidencyInclude>> findByResidencyEligiId(String tenantId, Long residencyEligibilityId);	
	
	/**
     * Find Residency Include by Status
	 * @author Rangana
	 * 
	 * @return   		- JSON Array of Other Include objects
	 * 
	 */
	public List<ResidencyInclude> findByStatus(String tenantId, String status);
	
	/**
     * Insert Residency Include
	 * @author Rangana
	 * 
	 * @param	- ResidencyIncludeAddResource
	 * @return  - Successfully saved message
	 * 
	 */
	public ResidencyInclude addResidencyInclude(String tenantId, Long residencyEligibilityId, ResidencyIncludeResource residencyIncludeAddResource, String userName);
	
	/**
     * Update Residency Include
	 * @author Rangana
	 * 
	 * @param	- OtherEligibilityUpdateResource
	 * @return  - Successfully saved message
	 * 
	 */
	public ResidencyInclude updateResidencyInclude(String tenantId, Long id, ResidencyIncludeUpdateResource residencyIncludeUpdateResource, String userName);

}
