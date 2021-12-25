package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.ExceptionApprovalGroupType;
import com.fusionx.lending.origination.enums.CommonStatus;


/**
 * Exception Approval Group Type Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1  23-08-2021   FXL-632   	 FX-586		Piyumi      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface ExceptionApprovalGroupTypeRepository extends JpaRepository<ExceptionApprovalGroupType, Long> {

	List<ExceptionApprovalGroupType> findByExceptionApprovalGroupId(Long exceptionApprovalGroupTypeId);
	
	Optional<ExceptionApprovalGroupType> findByExceptionApprovalGroupIdAndExceptionTypeId(Long exceptionApprovalGroupTypeId, Long exceptionTypeId);

	List<ExceptionApprovalGroupType> findByStatus(CommonStatus status);
	
	Optional<ExceptionApprovalGroupType> findByExceptionTypeIdAndStatus(Long exceptionTypeId, CommonStatus status);
	
	
}