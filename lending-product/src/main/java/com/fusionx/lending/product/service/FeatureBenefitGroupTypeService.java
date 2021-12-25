package com.fusionx.lending.product.service;

import java.util.Collection;
import java.util.Optional;

import com.fusionx.lending.product.domain.FeatureBenefitGroupType;
import com.fusionx.lending.product.resources.AddBaseRequest;
import com.fusionx.lending.product.resources.UpdateBaseRequest;

public interface FeatureBenefitGroupTypeService {

	 /**
     * Get all Feature Benefit Group Type details
     * @author Sanatha
     * 
     * @return    - JSON Array of all FeatureBenefitGroupType objects
     * 
     */
	public Optional<Collection<FeatureBenefitGroupType>> findAll();
	
	 /**
     * Get Feature Benefit Group Type by passing Code
     * @author Sanatha
     * 
     * @param code   - Code
     * @return        - JSON Array of FeatureBenefitGroupType related  Code
     * 
     */
	public  Optional<FeatureBenefitGroupType> findByCode(String code);
	
	

	 /**
    * Get Feature Benefit Group Type by passing Name
    * @author Sanatha
    * 
    * @param Name   - Name
    * @return        - JSON Array of FeatureBenefitGroupType related  Name
    * 
    */
	public  Optional<Collection<FeatureBenefitGroupType>> findByName(String name);
	
	/**
     * Get Feature Benefit Group Type by passing status
     * @author Sanatha
     * 
     * @param status   - status
     * @return         - JSON Array of FeatureBenefitGroupType related status
     * 
     */
	
	public Optional<Collection<FeatureBenefitGroupType>> findByStatus(String status);
	
	/**
     * Get Feature Benefit Group Type passing by CommonList id
     * @author Sanatha
     * 
     * @param id   		  -  Id
     * @return            - JSON Array of FeatureBenefitGroupType related  id
     * 
     */
	
	public Optional<FeatureBenefitGroupType> findById(long id);
	
	/**
     * Insert Feature Benefit Group Type details 
     * @author Sanatha
     * 
     * @param AddBaseRequest  - All column detail of new AddBaseRequest type
     * @return      - Success message.
     * 
     */
	public FeatureBenefitGroupType addFeatureBenefitGroupType(String tenantId, AddBaseRequest addBaseRequest);
	
	 /**
     * Update Feature Benefit Group Type Details
     * @author Sanatha
     * 
     * @param cmls     - UpdateBaseRequest column detail for Update 
     * @return         - Success message.
     * 
     */
	public FeatureBenefitGroupType updateFeatureBenefitGroupType(String tenantId, UpdateBaseRequest updateBaseRequest) ;
	
	 /**
     * Check whether there exists desired Feature Benefit Group Type Object 
     * @author Sanatha
     * 
     * @param id   - Id
     * @return         - Boolean value
     * 
     */
	public boolean exists(long id);
	


}
