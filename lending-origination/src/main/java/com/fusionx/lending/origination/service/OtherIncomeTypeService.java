package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.OtherIncomeType;
import com.fusionx.lending.origination.resource.OtherIncomeTypeAddResource;
import com.fusionx.lending.origination.resource.OtherIncomeTypeUpdateResource;


/**
 * Other Income Type Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-12-2020      		     FX-5272	MiyuruW      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface OtherIncomeTypeService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	List<OtherIncomeType> findAll();

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the OtherIncomeType
	 */
	Optional<OtherIncomeType> findById(Long id);
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the OtherIncomeType
	 */
	
	List<OtherIncomeType> findByStatus(String status);
	
	/**
	 * Find by name.
	 *
	 * @param name - the name
	 * @return the OtherIncomeType
	 */
	List<OtherIncomeType> findByName(String name);
	
	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the optional
	 */
	 Optional<OtherIncomeType> findByCode(String code);
	
	/**
	 * Exists by id.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	 Boolean existsById(Long id);
	
	/**
	 * Save and validate other income type.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param otherIncomeTypeAddResource - the other income type add resource
	 * @return the id
	 */
	 Long saveAndValidateOtherIncomeType(String tenantId, String createdUser, OtherIncomeTypeAddResource otherIncomeTypeAddResource);
	
	/**
	 * Update and validate other income type.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param id - the id
	 * @param otherIncomeTypeUpdateResource - the other income type update resource
	 * @return the id
	 */
	 Long updateAndValidateOtherIncomeType(String tenantId, String createdUser, Long id, OtherIncomeTypeUpdateResource otherIncomeTypeUpdateResource);

	/**
	 * Search other income type.
	 *
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param pageable - the pageable
	 * @return the page
	 */
	 Page<OtherIncomeType> searchOtherIncomeType(String searchq, String name, String code, Pageable pageable);
	 
	/**
	* Find by Other Income Category Id.
	*
	* @param id - the id
	* @return the OtherIncomeType Array
	*/
		
	List<OtherIncomeType> findByOtherIncomeCategory(Long id);
	
}