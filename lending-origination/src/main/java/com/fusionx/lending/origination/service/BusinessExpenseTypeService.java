package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.BusinessExpenseType;
import com.fusionx.lending.origination.resource.BusinessExpenseTypeAddResource;
import com.fusionx.lending.origination.resource.BusinessExpenseTypeUpdateResource;

/**
 * Business Expense Type Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-12-2020      		     FX-5271	MiyuruW      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface BusinessExpenseTypeService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<BusinessExpenseType> findAll();

	
	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the BusinessExpenseType
	 */
	public Optional<BusinessExpenseType> findById(Long id);
	
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the BusinessExpenseType
	 */
	public List<BusinessExpenseType> findByStatus(String status);
	
	
	/**
	 * Find by business type id.
	 *
	 * @param businessTypeId - the business type id
	 * @return the list
	 */
	public List<BusinessExpenseType> findByBusinessTypeId(Long businessTypeId);
	
	
	/**
	 * Save and validate business expense type.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param businessExpenseTypeAddResource - the business expense type add resource
	 * @return the boolean
	 */
	public Boolean saveAndValidateBusinessExpenseType(String tenantId, String createdUser, BusinessExpenseTypeAddResource businessExpenseTypeAddResource);

	
	/**
	 * Update and validate business expense type.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param id - the id
	 * @param businessExpenseTypeUpdateResource - the business expense type update resource
	 * @return the long
	 */
	public Long updateAndValidateBusinessExpenseType(String tenantId, String createdUser, Long id, BusinessExpenseTypeUpdateResource businessExpenseTypeUpdateResource);

	
	/**
	 * Exists by id.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Boolean existsById(Long id);
	
	/**
	 * Search business expense type.
	 *
	 * @param searchq - the searchq
	 * @param businessTypeName - the business type name
	 * @param businessTypeCode - the business type code
	 * @param pageable - the pageable
	 * @return the page
	 */
	//public Page<BusinessExpenseType> searchBusinessExpenseType(String searchq, String businessTypeName, String businessTypeCode, Pageable pageable);
	public Page<BusinessExpenseType> searchBusinessExpenseType(String businessTypeName, String businessTypeCode, Pageable pageable);
	
}
