package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Approval Category Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-09-2021   FXL-338 		 FXL-803 	Dilki        Created
 *    
 ********************************************************************************************************
 */

import com.fusionx.lending.origination.domain.ApprovalCategoryApplicableVariables;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface ApprovalCategoryApplicableVariablesRepository
		extends JpaRepository<ApprovalCategoryApplicableVariables, Long> {

	Optional<ApprovalCategoryApplicableVariables> findByNameContaining(String name);

	List<ApprovalCategoryApplicableVariables> findByStatus(CommonStatus status);

	List<ApprovalCategoryApplicableVariables> findByApprovalCategoryDetailsId(Long approvalCategoryDetailsId);

}
