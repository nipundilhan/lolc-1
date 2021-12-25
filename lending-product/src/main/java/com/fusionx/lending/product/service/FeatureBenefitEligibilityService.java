package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import com.fusionx.lending.product.domain.FeatureBenefitEligibility;
import com.fusionx.lending.product.resources.AddBaseEligibilityRequest;
import com.fusionx.lending.product.resources.UpdateBaseEligibilityRequest;

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

public interface FeatureBenefitEligibilityService {

	 /**
     * Get all Feature Benefit Eligibility details
     * @author Sanatha
     * 
     * @return    - JSON Array of all FeatureBenefitEligibility objects
     * 
     */
	public List<FeatureBenefitEligibility> findAll();
	
	
	/**
     * Get Feature Benefit Eligibility by passing status
     * @author Sanatha
     * 
     * @param status   - status
     * @return         - JSON Array of FeatureBenefitEligibility related status
     * 
     */
	
	public List<FeatureBenefitEligibility> findByStatus(String status);
	
	/**
     * Get Feature Benefit Eligibility passing by  id
     * @author Sanatha
     * 
     * @param id   		  -  Id
     * @return            - JSON Array of FeatureBenefitEligibility related  id
     * 
     */
	
	public Optional<FeatureBenefitEligibility> findById(long id);
	
	/**
     * Insert Feature Benefit Eligibility details 
     * @author Sanatha
     * 
     * @param AddBaseEligibilityRequest  - All column detail of new AddBaseEligibilityRequest type
     * @return      - Success message.
     * 
     */
	public FeatureBenefitEligibility addFeatureBenefitEligibility(String tenantId, AddBaseEligibilityRequest addBaseEligibilityRequest);
	
	 /**
     * Update Feature Benefit Eligibility Details
     * @author Sanatha
     * 
     * @param UpdateBaseEligibilityRequest     - UpdateBaseEligibilityRequest column detail for Update 
     * @return         - Success message.
     * 
     */
	public FeatureBenefitEligibility updateFeatureBenefitEligibility(String tenantId, UpdateBaseEligibilityRequest updateBaseEligibilityRequest) ;
	
	 /**
     * Check whether there exists desired Feature Benefit Eligibility object 
     * @author Sanatha
     * 
     * @param id   - Id
     * @return     - Boolean value
     * 
     */
	public boolean exists(long id);
	
	
	 /**
	    * Get CreditInterestEligibility by passing Name
	    * @author Sanatha
	    * 
	    * @param Name   - Name
	    * @return        - JSON Array of CreditInterestEligibility related  Name
	    * 
	    */
 public  List<FeatureBenefitEligibility>  findByName(String name);
 
	 /**
	    * Get FeatureBenefitEligibility by passing code
	    * @author Sanatha
	    * 
	    * @param Name   - Name
	    * @return        - JSON Array of FeatureBenefitEligibility related  Name
	    * 
	    */
public  List<FeatureBenefitEligibility>  findByFeatureBenefitEligiTypeCode(String code);
	


}
