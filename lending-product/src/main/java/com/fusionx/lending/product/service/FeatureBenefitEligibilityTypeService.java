package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.FeatureBenefitEligibilityType;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;



/**
 * Feature Benefit Eligibility Type Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-06-2021                           MenukaJ        Created
 *    
 ********************************************************************************************************
 */


@Service
public interface FeatureBenefitEligibilityTypeService {

	/**
	 * 
	 * Find all Feature Benefit Eligibility Type
	 * @author MenukaJ
	 * @return -JSON array of all Feature Benefit Eligibility Type
	 * 
	 * */
	public List<FeatureBenefitEligibilityType> getAll();
	
	/**
	 * 
	 * Find Feature Benefit Eligibility Type by ID
	 * @author MenukaJ
	 * @return -JSON array of Feature Benefit Eligibility Type
	 * 
	 * */
	public Optional<FeatureBenefitEligibilityType> getById(Long id);
	
	/**
	 * 
	 * Find Feature Benefit Eligibility Type by code
	 * @author MenukaJ
	 * @return -JSON array of Feature Benefit Eligibility Type
	 * 
	 * */
	public Optional<FeatureBenefitEligibilityType>getByCode(String code);
	
	/**
	 * 
	 * Find Feature Benefit Eligibility Type by name
	 * @author MenukaJ
	 * @return -JSON array of Feature Benefit Eligibility Type
	 * 
	 * */
	public List<FeatureBenefitEligibilityType> getByName(String name);
	
	/**
	 * 
	 * Find Feature Benefit Eligibility Type by status
	 * @author MenukaJ
	 * @return -JSON array of Feature Benefit Eligibility Type
	 * 
	 * */
	public List<FeatureBenefitEligibilityType> getByStatus(String status);
	
	
	/**
	 * 
	 * Insert Feature Benefit Eligibility Type
	 * @author MenukaJ
	 * @param  - CommonAddResource
	 * @return - Successfully saved
	 * 
	 * */
	public FeatureBenefitEligibilityType addFeatureBenefitEligibilityType(String tenantId , CommonAddResource commonAddResource);

	/**
	 * 
	 * Update Feature Benefit Eligibility Type
	 * @author MenukaJ
	 * @param  - CommonUpdateResource
	 * @return - Successfully saved
	 * 
	 * */
	public FeatureBenefitEligibilityType updateFeatureBenefitEligibilityType(String tenantId, Long id, CommonUpdateResource commonUpdateResource);
}
