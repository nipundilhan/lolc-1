package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.OfficerEligibility;
import com.fusionx.lending.product.resources.OfficerEligibilityResource;
import com.fusionx.lending.product.resources.OfficerEligibilityUpdateResource;

/**
* Officer Eligibility Service
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   09-06-2020   			   	        	Thamokshi    Created
*    
********************************************************************************************************
*/

@Service
public interface OfficerEligibilityService {

	/**
     * Find all Officer Eligibility
	 * @author ThamokshiD
	 * 
	 * @return   		- JSON Array of all Officer Eligibility objects
	 * 
	 */
	List<OfficerEligibility> findAll();
	
	/**
     * Find Officer Eligibility by Id
	 * @author ThamokshiD
	 * 
	 * @return   		- JSON Array of Officer Eligibility objects
	 * 
	 */
	public Optional<OfficerEligibility> findById(Long id);
	
	/**
     * Find Officer Eligibility by Id
	 * @author ThamokshiD
	 * 
	 * @return   		- JSON Array of Officer Eligibility objects
	 * 
	 */
	public List<OfficerEligibility> findByOfficerTypeId(Long officerTypeId);
	
	/**
     * Find Officer Eligibility by Id
	 * @author ThamokshiD
	 * 
	 * @return   		- JSON Array of Officer Eligibility objects
	 * 
	 */
	public Optional<OfficerEligibility> findByCode(String code);
	
	
	/**
     * Find Officer Eligibility by Status
	 * @author ThamokshiD
	 * 
	 * @return   		- JSON Array of Officer Eligibility objects
	 * 
	 */
	public List<OfficerEligibility> findByStatus(String status);
	
	/**
     * Insert Officer Eligibility
	 * @author ThamokshiD
	 * 
	 * @param	- OfficerEligibilityAddResource
	 * @return  - Successfully saved message
	 * 
	 */
	public OfficerEligibility addOfficerEligibility(String tenantId, OfficerEligibilityResource officerEligibilityAddResource);
	
	/**
     * Update Legal Structure Eligibility
	 * @author ThamokshiD
	 * 
	 * @param	- OfficerEligibilityUpdateResource
	 * @return  - Successfully saved message
	 * 
	 */
	public OfficerEligibility updateOfficerEligibility(String tenantId, OfficerEligibilityUpdateResource officerEligibilityUpdateResource);

}
