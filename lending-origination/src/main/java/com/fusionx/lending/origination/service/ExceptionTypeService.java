package com.fusionx.lending.origination.service;
/**
 * Exception Type Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-08-2021   FXL-627  	 FXL-563       Piyumi     Created
 *    
 ********************************************************************************************************
*/
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ExceptionType;
import com.fusionx.lending.origination.resource.CommonAddResource;
import com.fusionx.lending.origination.resource.CommonUpdateResource;



@Service
public interface ExceptionTypeService {
	
	/**
	 * Save 
	 *
	 * @param tenantId - the tenant id
	 * @param CommonAddResource - the common add resource
	 * @return the Exception Type Object
	 */
	ExceptionType save(String tenantId, CommonAddResource commonAddResource);

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the Exception Type Object
	 */
	Optional<ExceptionType> findById(Long id);
	

	List<ExceptionType> findAll();

	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the Exception Type Object
	 */
	Optional<ExceptionType> findByCode(String code);

	/**
	 * Find by Name.
	 *
	 * @param Name - the name
	 * @return the Exception Type Object Array
	 */
	List<ExceptionType> findByName(String name);

	/**
	 * Find by Status.
	 *
	 * @param Status - the Status
	 * @return the Exception Type Object Array
	 */
	List<ExceptionType> findByStatus(String status);

	/**
	 * update
	 *
	 * @param tenantId - the tenant id
	 * @param id - the exception type id
	 * @param CommonUpdateResource - the common update resource
	 * @return the Exception Type Object
	 */
	ExceptionType update(String tenantId, Long id,  CommonUpdateResource commonUpdateResource);

	/**
	 * Search ExceptionType.
	 *
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param pageable - the pageable
	 * @return the page
	 */
	//public Page<ExceptionType> searchExceptionType(String searchq, String name, String code, Pageable pageable);
	public Page<ExceptionType> searchExceptionType(String name, String code, Pageable pageable);
}
