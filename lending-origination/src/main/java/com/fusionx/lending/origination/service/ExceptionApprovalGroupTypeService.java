package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ExceptionApprovalGroupType;
import com.fusionx.lending.origination.resource.ExceptionApprovalGroupTypeAddResource;
import com.fusionx.lending.origination.resource.ExceptionApprovalGroupTypeUpdateResource;

/**
 * Exception Approval Group Type Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1  23-08-2021    FXL-632   	 FX-586		Piyumi      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface ExceptionApprovalGroupTypeService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	 List<ExceptionApprovalGroupType> findAll();

	
	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the ExceptionApprovalGroupType
	 */
	 Optional<ExceptionApprovalGroupType> findById(Long id);
	
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the ExceptionApprovalGroupType
	 */
	 List<ExceptionApprovalGroupType> findByStatus(String status);
	
	/**
	 * Save Exception Approval Group Type
	 *
	 * @param tenantId - the tenant id
	 * @param ExceptionApprovalGroupTypeAddResource - the Exception Approval Group Type Add Resource
	 * @return the boolean
	 */
	 ExceptionApprovalGroupType addExceptionApprovalGroupType(String tenantId, ExceptionApprovalGroupTypeAddResource exceptionApprovalGroupTypeAddResource);

	
	/**
	 * Update Exception Approval Group Type
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param ExceptionApprovalGroupTypeUpdateResource - the Exception Approval Group Type Update Resource
	 * @return the long
	 */
	 ExceptionApprovalGroupType updateExceptionApprovalGroupType(String tenantId, Long id, ExceptionApprovalGroupTypeUpdateResource exceptionApprovalGroupTypeUpdateResource);

	 /**
	 * Find by id.
	 *
	 * @param exceptionApprovalGroupId - the exception approval group id
	 * @return the list
	 */
	 List<ExceptionApprovalGroupType> findByExceptionApprovalGroupId(Long exceptionApprovalGroupId);
	
}
