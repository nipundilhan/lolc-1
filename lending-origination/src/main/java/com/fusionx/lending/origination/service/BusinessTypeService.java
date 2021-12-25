package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.resource.BusinessTypeAddResource;
import com.fusionx.lending.origination.resource.BusinessTypeUpdateResource;

/**
 * Business Type Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   23-12-2020      		     FX-5269	MiyuruW      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface BusinessTypeService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<BusinessType> findAll();

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the BusinessType
	 */
	public Optional<BusinessType> findById(Long id);
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the BusinessType
	 */
	
	public List<BusinessType> findByStatus(String status);
	
	/**
	 * Find by name.
	 *
	 * @param name - the name
	 * @return the BusinessType
	 */
	public List<BusinessType> findByName(String name);
	
	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the optional
	 */
	public Optional<BusinessType> findByCode(String code);
	
	/**
	 * Exists by id.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Boolean existsById(Long id);
	
	
	/**
	 * Save and validate business type.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param businessTypeAddResource - the business type add resource
	 * @return the id
	 */
	public Long saveAndValidateBusinessType(String tenantId, String createdUser, BusinessTypeAddResource businessTypeAddResource);
	
	/**
	 * Update and validate business type.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param id - the id
	 * @param businessTypeUpdateResource - the business type update resource
	 * @return the id
	 */
	public Long updateAndValidateBusinessType(String tenantId, String createdUser, Long id, BusinessTypeUpdateResource businessTypeUpdateResource);

	/**
	 * Search business type.
	 *
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param pageable - the pageable
	 * @return the page
	 */
	public Page<BusinessType> searchBusinessType(String searchq, String name, String code, Pageable pageable);
	
}
