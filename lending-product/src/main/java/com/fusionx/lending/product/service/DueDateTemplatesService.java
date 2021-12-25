package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.DueDateTemplates;
import com.fusionx.lending.product.resources.DueDateTemplatesAddResource;
import com.fusionx.lending.product.resources.DueDateTemplatesUpdateResource;

/**
 * Due Date Templates Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   23-09-2021    FXL-139  	 FXL-926	Piyumi      Created
 *    
 ********************************************************************************************************
 */


@Service
public interface DueDateTemplatesService {

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the Due Date Templates Object
	 */
	Optional<DueDateTemplates> findById(Long id);
	

	List<DueDateTemplates> findAll();

	/**
	 * Find by Status.
	 *
	 * @param Status - the Status
	 * @return the Due Date Templates Object Array
	 */
	List<DueDateTemplates> findByStatus(String status);
	
	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the Due Date Templates Object Array
	 */
	Optional<DueDateTemplates> findByCode(String code);
	
	/**
	 * Find by name.
	 *
	 * @param name - the name
	 * @return the Due Date Templates Object Array
	 */
	List<DueDateTemplates> findByNameContaining(String name);
	
	/**
	 * Find by effectiveDate.
	 *
	 * @param effectiveDate - the effectiveDate
	 * @return the Due Date Templates Object Array
	 */
	List<DueDateTemplates> findByEffectiveDate(String effectiveDate);
	
	/**
	 * Save 
	 *
	 * @param tenantId - the tenant id
	 * @param dueDateTemplatesAddResource - the due date templates add resource
	 * @return the Due Date Templates Object
	 */
	DueDateTemplates save(String tenantId, DueDateTemplatesAddResource dueDateTemplatesAddResource);
	
	/**
	 * update
	 *
	 * @param tenantId - the tenant id
	 * @param id - the DueDateTemplatesId
	 * @param dueDateTemplatesUpdateResource - the due date templates update resource
	 * @return the Due Date Templates Object
	 */
	DueDateTemplates update(String tenantId, Long id,  DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource);
}