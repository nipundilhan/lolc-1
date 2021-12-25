package com.fusionx.lending.origination.service;
/**
 * 	Exception Approval Group Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-08-2021   FXL-632  	 FXL-564       Piyumi     Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ExceptionApprovalGroup;
import com.fusionx.lending.origination.resource.ExceptionApprovalGroupAddResource;
import com.fusionx.lending.origination.resource.ExceptionApprovalGroupUpdateResource;



@Service
public interface ExceptionApprovalGroupService {
	
	/**
	 * Save 
	 *
	 * @param tenantId - the tenant id
	 * @param ExceptionApprovalGroupAddResource - the exception approval group add resource
	 * @return the Exception Approval Group Object
	 */
	ExceptionApprovalGroup save(String tenantId, ExceptionApprovalGroupAddResource exceptionApprovalGroupAddResource);

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the Exception Approval Group Object
	 */
	Optional<ExceptionApprovalGroup> findById(Long id);
	

	List<ExceptionApprovalGroup> findAll();

	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the Exception Approval Group Object
	 */
	Optional<ExceptionApprovalGroup> findByCode(String code);

	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the Exception Approval Group Object Array
	 */
	List<ExceptionApprovalGroup> findByName(String name);

	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the Exception Approval Group Object Array
	 */
	List<ExceptionApprovalGroup> findByStatus(String status);

	/**
	 * update
	 *
	 * @param tenantId - the tenant id
	 * @param id - the Exception Approval Group id
	 * @param ExceptionApprovalGroupUpdateResource - the exception approval group update resource
	 * @return the Exception Approval Group Object
	 */
	ExceptionApprovalGroup update(String tenantId, Long id,  ExceptionApprovalGroupUpdateResource exceptionApprovalGroupUpdateResource);

	/**
	 * Search ExceptionApprovalGroup.
	 *
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param status - the status
	 * @param pageable - the pageable
	 * @return the page
	 */
	public Page<ExceptionApprovalGroup> searchExceptionApprovalGroup(String name, String code, String status,Pageable pageable);
}
