package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.DaLimit;
import com.fusionx.lending.origination.resource.DaLimitAddResource;
import com.fusionx.lending.origination.resource.DaLimitUpdateResource;

/**
 * DA Limit Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *     1   06-05-2021      		     FX-6269	Amal       Created
 *    
 ********************************************************************************************************
 */

@Service
public interface DaLimitService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	//public Page<?> findAll(Pageable pageable, Predicate predicate);
	
	public List<DaLimit> findAll();

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the DaLimit
	 */
	public Optional<DaLimit> findById(Long id);
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the DaLimit List
	 */
	
	public List<DaLimit> findByStatus(String status);
	

	
	/**
	 * Exists by id.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Boolean existsById(Long id);
	
	
	/**
	 * Save and validate DA Limit
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param DaLimitAddResource - the DA Limit add resource
	 * @return the id
	 */
	public Long saveAndValidateDaLimit(String tenantId, String createdUser, DaLimitAddResource daLimitAddResource);
	
	/**
	 * Update and validate DA Limit
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param id - the id
	 * @param DaLimitUpdateResource - the DA Limit update resource
	 * @return the id
	 */
	public Long updateAndValidateDaLimit(String tenantId, String createdUser, Long id, DaLimitUpdateResource daLimitUpdateResource);
	/**
	 * Find by Group Id ,level and category id.
	 *
	 * @param status - the status
	 * @return the DaLimit List
	 */
	public List<DaLimit> findByDelegationAuthorityGroupIdAndDaLevelAndApprovalCategoryId(Long groupId,String level,Long categoryId);
	
}
