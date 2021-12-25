package com.fusionx.lending.product.service;

/**
 * Tax Profile Service
 * @author 	KilasiG
 * @Date     05-11-2019
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   05-11-2019   FX-1545       FX-2175    KilasiG      Created
 *    
 ********************************************************************************************************
 */

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.TaxProfile;
import com.fusionx.lending.product.resources.AddTaxProfileRequestResource;
import com.fusionx.lending.product.resources.UpdateTaxProfileRequestResource; 


@Service
public interface TaxProfileService {
	/**
     * Find all Tax Profiles
	 * @author KilasiG
	 * @param pageable the Pageable
	 * @return   		- JSON Array of all Tax Profile objects
	 * 
	 */
	public Page<TaxProfile> findAll(Pageable pageable);
	
	/**
     * Find Tax Profile by Id
	 * @author KilasiG
	 * @param taxProfileId the Tax Profile Id
	 * @return   		- JSON Array of Tax Profile objects
	 * 
	 */
	public Optional<TaxProfile> findById(Long taxProfileId);
	
	/**
     * Find TaxProfile by Status
	 * @author KilasiG
	 * @param status the Status
	 * @return   		- JSON Array of Tax Profile objects
	 * 
	 */
	public Optional<Collection<TaxProfile>> findByStatus(String status);
	
	
	/**
     * Insert Tax Profiles
	 * @author KilasiG
	 * @param tenantId the Tanent Id
	 * @param addTaxProfileRequestResource the Add Tax Profile Request Response
	 * @return  - Successfully saved message
	 * 
	 */
	public TaxProfile saveTaxProfile(String tenantId, AddTaxProfileRequestResource addTaxProfileRequestResource);
	
	/**
     * Update Tax Profile
	 * @author KilasiG
	 * @param tenantId the Tenant Id
	 * @param updateTaxProfileRequestResource the Update Tax Profile Request Resourse
	 * @return  - Successfully saved message
	 * 
	 */
	public TaxProfile updateTaxProfile(String tenantId, UpdateTaxProfileRequestResource updateTaxProfileRequestResource);
}
