package com.fusionx.lending.product.service;

import java.util.Collection;
import java.util.Optional;

import com.fusionx.lending.product.domain.FeatureBenefitItemType;
import com.fusionx.lending.product.resources.AddBaseRequest;
import com.fusionx.lending.product.resources.UpdateBaseRequest;
/**
 * Feature Benefit Item Type Service - Feature Benefit Item Type Domain entity
 * @author 	Sanatha
 * @Date    15-JUN-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-JUN-2021  						     Sanatha      Created
 *    
 ********************************************************************************************************
 */
public interface FeatureBenefitItemTypeService {

	 /**
     * Get all Feature Benefit Item Type details
     * @author Sanatha
     * 
     * @return    - JSON Array of all FeatureBenefitItemType objects
     * 
     */
	public Optional<Collection<FeatureBenefitItemType>> findAll();
	
	 /**
     * Get Feature Benefit Item Type by passing Code
     * @author Sanatha
     * 
     * @param code   - Code
     * @return        - JSON Array of FeatureBenefitItemType related  Code
     * 
     */
	public  Optional<FeatureBenefitItemType> findByCode(String code);
	
	
	 /**
     * Get Feature Benefit Item Type by passing name
     * @author Sanatha
     * 
     * @param nameO   - name
     * @return        - JSON Array of FeatureBenefitItemType related  name
     * 
     */
	public  Optional<Collection<FeatureBenefitItemType>> findByName(String name);
	
	
	/**
     * Get Feature Benefit Item Type by passing status
     * @author Sanatha
     * 
     * @param status   - status
     * @return         - JSON Array of FeatureBenefitItemType related status
     * 
     */
	
	public Optional<Collection<FeatureBenefitItemType>> findByStatus(String status);
	
	/**
     * Get Feature Benefit Item Type passing by CommonList id
     * @author Sanatha
     * 
     * @param id   		  -  Id
     * @return            - JSON Array of FeatureBenefitItemType related  id
     * 
     */
	
	public Optional<FeatureBenefitItemType> findById(long id);
	
	/**
     * Insert Feature Benefit Item Type details 
     * @author Sanatha
     * 
     * @param AddBaseRequest  - All column detail of new AddBaseRequest type
     * @return      - Success message.
     * 
     */
	public FeatureBenefitItemType addFeatureBenefitItemType(String tenantId, AddBaseRequest addBaseRequest);
	
	 /**
     * Update Feature Benefit Item Type Details
     * @author Sanatha
     * 
     * @param cmls     - UpdateBaseRequest column detail for Update 
     * @return         - Success message.
     * 
     */
	public FeatureBenefitItemType updateFeatureBenefitItemType(String tenantId, UpdateBaseRequest updateBaseRequest) ;
	
	 /**
     * Check whether there exists desired Feature Benefit Item Type Object 
     * @author Sanatha
     * 
     * @param id   - Id
     * @return         - Boolean value
     * 
     */
	public boolean exists(long id);

	

}
