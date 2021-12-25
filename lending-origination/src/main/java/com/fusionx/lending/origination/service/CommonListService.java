package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.CommonList;
import com.fusionx.lending.origination.resource.CommonListAddResource;
import com.fusionx.lending.origination.resource.CommonListUpdateResource;

/**
 * Common list Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   18-12-2020      		     FX-5273	MiyuruW      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface CommonListService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<CommonList> findAll();

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the CommonList
	 */
	public Optional<CommonList> findById(Long id);
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the CommonList
	 */
	
	public List<CommonList> findByStatus(String status);
	
	/**
	 * Find by name.
	 *
	 * @param name - the name
	 * @return the CommonList
	 */
	public List<CommonList> findByName(String name);
	
	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the list
	 */
	public List<CommonList> findByCode(String code);
	
	/**
	 * Find by reference code.
	 *
	 * @param referenceCode - the reference code
	 * @return the list
	 */
	public List<CommonList> findByReferenceCode(String referenceCode);
	
	/**
	 * Save and validate common list.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param commonListAddResource - the common list add resource
	 * @return the long
	 */
	public Long saveAndValidateCommonList(String tenantId, String createdUser, CommonListAddResource commonListAddResource);
	
	/**
	 * Update and validate common list.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param id - the id
	 * @param commonListUpdateResource - the common list update resource
	 * @return the long
	 */
	public Long updateAndValidateCommonList(String tenantId, String createdUser, Long id, CommonListUpdateResource commonListUpdateResource);

	/**
	 * Exists by id.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Boolean existsById(Long id);
	
}
