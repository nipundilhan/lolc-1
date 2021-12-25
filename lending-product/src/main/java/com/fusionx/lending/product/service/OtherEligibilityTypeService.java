package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.OtherEligibilityType;
import com.fusionx.lending.product.resources.AddBaseRequest;
import com.fusionx.lending.product.resources.UpdateBaseRequest;

/**
 * Other Eligibility Type Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-06-2020   			   	        	Thamokshi    Created
 *    
 ********************************************************************************************************
 */

@Service
public interface OtherEligibilityTypeService {

	/**
     * Find all Other Eligibility Type
	 * @author ThamokshiD
	 * 
	 * @return   		- JSON Array of all Other Eligibility Type objects
	 * 
	 */
	public List<OtherEligibilityType> findAll();
	
	/**
     * Find Other Eligibility Type by Id
	 * @author ThamokshiD
	 * 
	 * @return   		- JSON Array of Other Eligibility Type objects
	 * 
	 */
	public Optional<OtherEligibilityType> findById(Long id);
	
	/**
     * Find Other Eligibility Type by code
	 * @author ThamokshiD
	 * 
	 * @return   		- JSON Array of Other Eligibility Type objects
	 * 
	 */
	public Optional<OtherEligibilityType> findByCode(String code);
	
	/**
     * Find Other Eligibility Type by Status
	 * @author ThamokshiD
	 * 
	 * @return   		- JSON Array of Other Eligibility Type objects
	 * 
	 */
	public List<OtherEligibilityType> findByStatus(String status);
	
	/**
     * Find Other Eligibility Type by Name
	 * @author ThamokshiD
	 * 
	 * @return   		- JSON Array of Other Eligibility Type objects
	 * 
	 */
	public List<OtherEligibilityType> findByName(String name);
	
	/**
     * Insert Other Eligibility Type
	 * @author ThamokshiD
	 * 
	 * @param	- AddBaseRequest
	 * @return  - Successfully saved message
	 * 
	 */
	public OtherEligibilityType addOtherEligibilityType(String tenantId, AddBaseRequest addBaseRequest);
	
	/**
     * Update Other Eligibility Type
	 * @author ThamokshiD
	 * 
	 * @param	- UpdateBaseRequest
	 * @return  - Successfully saved message
	 * 
	 */
	public OtherEligibilityType updateOtherEligibilityType(String tenantId, UpdateBaseRequest updateBaseRequest);

}
