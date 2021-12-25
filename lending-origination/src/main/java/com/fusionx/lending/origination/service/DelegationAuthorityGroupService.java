package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.DelegationAuthorityGroup;
import com.fusionx.lending.origination.resource.DelegationAuthorityGroupAddResource;
import com.fusionx.lending.origination.resource.DelegationAuthorityGroupUpdateResource;

/**
 * Delegation Authority Group Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		            	VenukiR      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface DelegationAuthorityGroupService {

	/**
	 * Find all.
	 *
	 * @param pageable - the pageable
	 * @return the page
	 */
	//public Page<DelegationAuthorityGroup> findAll(Pageable pageable);

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the BusinessType
	 */
	public Optional<DelegationAuthorityGroup> findById(Long id);
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the DelegationAuthorityGroup
	 */
	
	public List<DelegationAuthorityGroup> findByStatus(String status);
	
	/**
	 * Find by name.
	 *
	 * @param name - the name
	 * @return the DelegationAuthorityGroup
	 */
	public List<DelegationAuthorityGroup> findByName(String name);
	
	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the optional
	 */
	public Optional<DelegationAuthorityGroup> findByCode(String code);
	
	/**
	 * Exists by id.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Boolean existsById(Long id);
	
	
	/**
	 * Save and validate DelegationAuthorityGroup.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param DelegationAuthorityGroupAddResource - the business type add resource
	 * @return the id
	 */
	public Long saveAndValidateDelegationAuthorityGroup(String tenantId, String createdUser, DelegationAuthorityGroupAddResource DelegationAuthorityGroupAddResource);
	
	/**
	 * Update and validate business type.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param id - the id
	 * @param DelegationAuthorityGroupUpdateResource - the business type update resource
	 * @return the id
	 */
	public Long updateAndValidateDelegationAuthorityGroup(String tenantId, String createdUser, Long id, DelegationAuthorityGroupUpdateResource DelegationAuthorityGroupUpdateResource);

	/**
	 * Search business type.
	 *
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param pageable - the pageable
	 * @return the page
	 */
	public Page<DelegationAuthorityGroup> searchDelegationAuthorityGroup(String searchq, String name, String code, Pageable pageable);
	
	public List<DelegationAuthorityGroup> findAll();
}
