package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ExpenseType;
import com.fusionx.lending.origination.resource.ExpenseTypeAddResource;
import com.fusionx.lending.origination.resource.ExpenseTypeUpdateResource;

/**
 * Business Type Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-12-2020      		     FX-5270	MiyuruW      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface ExpenseTypeService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<ExpenseType> findAll();

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the ExpenseType
	 */
	public Optional<ExpenseType> findById(Long id);
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the ExpenseType
	 */
	
	public List<ExpenseType> findByStatus(String status);
	
	/**
	 * Find by name.
	 *
	 * @param name - the name
	 * @return the ExpenseType
	 */
	public List<ExpenseType> findByName(String name);
	
	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the optional
	 */
	public Optional<ExpenseType> findByCode(String code);
	
	/**
	 * Exists by id.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Boolean existsById(Long id);
	
	
	/**
	 * Save and validate expense type.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param expenseTypeAddResource - the expense type add resource
	 * @return the id
	 */
	public Long saveAndValidateExpenseType(String tenantId, String createdUser, ExpenseTypeAddResource expenseTypeAddResource);
	
	/**
	 * Update and validate expense type.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param id - the id
	 * @param expenseTypeUpdateResource - the expense type update resource
	 * @return the id
	 */
	public Long updateAndValidateExpenseType(String tenantId, String createdUser, Long id, ExpenseTypeUpdateResource expenseTypeUpdateResource);

	/**
	 * Search expense type.
	 *
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param pageable - the pageable
	 * @return the page
	 */
	public Page<ExpenseType> searchExpenseType(String searchq, String name, String code, Pageable pageable);
	
}