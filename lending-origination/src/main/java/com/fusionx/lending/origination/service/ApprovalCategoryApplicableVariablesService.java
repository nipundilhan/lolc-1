package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ApprovalCategoryApplicableVariables;
import com.fusionx.lending.origination.domain.ApprovalCategoryDetails;
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
import com.fusionx.lending.origination.resource.ApprovalCategoryApplicableVariablesAddResource;
import com.fusionx.lending.origination.resource.ApprovalCategoryApplicableVariablesUpdateResource;

@Service
public interface ApprovalCategoryApplicableVariablesService {

	public ApprovalCategoryApplicableVariables addApprovalCategoryApplicableVariables(String tenantId,
			ApprovalCategoryDetails approvalCategoryDetails,
			ApprovalCategoryApplicableVariablesAddResource approvalCategoryApplicableVariablesAddResource);

	List<ApprovalCategoryApplicableVariables> getByStatus(String status);

	Optional<ApprovalCategoryApplicableVariables> getByName(String name);

	Optional<ApprovalCategoryApplicableVariables> getById(Long id);

	public ApprovalCategoryApplicableVariables updateApprovalCategoryApplicableVariables(String tenantId,
			ApprovalCategoryDetails approvalCategoryDetails,
			ApprovalCategoryApplicableVariablesUpdateResource applicableVariables);

}
