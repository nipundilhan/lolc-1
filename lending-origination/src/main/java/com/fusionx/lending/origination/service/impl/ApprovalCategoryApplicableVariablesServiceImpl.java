package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ApprovalCategoryApplicableVariables;
import com.fusionx.lending.origination.domain.ApprovalCategoryDetails;
import com.fusionx.lending.origination.domain.CommonList;
import com.fusionx.lending.origination.enums.CommonListReferenceCode;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ApprovalCategoryApplicableVariablesRepository;
import com.fusionx.lending.origination.repository.CommonListRepository;
import com.fusionx.lending.origination.resource.ApprovalCategoryApplicableVariablesAddResource;
import com.fusionx.lending.origination.resource.ApprovalCategoryApplicableVariablesUpdateResource;
import com.fusionx.lending.origination.service.ApprovalCategoryApplicableVariablesService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class ApprovalCategoryApplicableVariablesServiceImpl extends MessagePropertyBase
		implements ApprovalCategoryApplicableVariablesService {

	@Autowired
	private ApprovalCategoryApplicableVariablesRepository approvalCategoryApplicableVariablesRepository;

	@Autowired
	private CommonListRepository commonListRepository;

	@Autowired
	private ValidateService validationService;

	@Override
	public Optional<ApprovalCategoryApplicableVariables> getById(Long id) {
		Optional<ApprovalCategoryApplicableVariables> isPresentApprovalCategoryApplicableVariables = approvalCategoryApplicableVariablesRepository
				.findById(id);
		if (isPresentApprovalCategoryApplicableVariables.isPresent()) {
			return Optional.ofNullable(isPresentApprovalCategoryApplicableVariables.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<ApprovalCategoryApplicableVariables> getByName(String name) {
		return approvalCategoryApplicableVariablesRepository.findByNameContaining(name);
	}

	@Override
	public List<ApprovalCategoryApplicableVariables> getByStatus(String status) {
		return approvalCategoryApplicableVariablesRepository.findByStatus(CommonStatus.valueOf(status));
	}

	public CommonList validateApplicableVariable(Long id, String name) {

		Optional<CommonList> commonListItem = commonListRepository.findById(id);
		if (!commonListItem.isPresent())
			throw new ValidateRecordException(environment.getProperty(NOT_FOUND), "applicableVariableId");

		else if (CommonStatus.ACTIVE.toString().equalsIgnoreCase(commonListItem.get().getStatus()))
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "applicableVariableId");

		else if (!CommonListReferenceCode.APPROVAL_VARIABLES.toString()
				.equalsIgnoreCase(commonListItem.get().getReferenceCode())
				|| !commonListItem.get().getName().equalsIgnoreCase(name))
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH), "applicableVariableName");

		return commonListItem.get();
	}

	@Override
	public ApprovalCategoryApplicableVariables addApprovalCategoryApplicableVariables(String tenantId,
			ApprovalCategoryDetails approvalCategoryApplicableVariables,
			ApprovalCategoryApplicableVariablesAddResource approvalCategoryApplicableVariablesAddResource) {

		Optional<ApprovalCategoryApplicableVariables> isPresentApprovalCategoryApplicableVariables = approvalCategoryApplicableVariablesRepository
				.findByNameContaining(approvalCategoryApplicableVariablesAddResource.getName());

		if (isPresentApprovalCategoryApplicableVariables.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.NAME);

		ApprovalCategoryApplicableVariables approvalCategoryApplicableVariablesNew = new ApprovalCategoryApplicableVariables();
		approvalCategoryApplicableVariablesNew.setTenantId(tenantId);
		approvalCategoryApplicableVariablesNew.setCode(approvalCategoryApplicableVariablesAddResource.getCode());
		approvalCategoryApplicableVariablesNew.setName(approvalCategoryApplicableVariablesAddResource.getName());
		approvalCategoryApplicableVariablesNew
				.setStatus(CommonStatus.valueOf(approvalCategoryApplicableVariablesAddResource.getStatus()));
		approvalCategoryApplicableVariablesNew.setCreatedDate(validationService.getCreateOrModifyDate());
		approvalCategoryApplicableVariablesNew.setSyncTs(validationService.getCreateOrModifyDate());
		approvalCategoryApplicableVariablesNew.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		return approvalCategoryApplicableVariablesRepository.save(approvalCategoryApplicableVariablesNew);
	}

	@Override
	public ApprovalCategoryApplicableVariables updateApprovalCategoryApplicableVariables(String tenantId,
			ApprovalCategoryDetails approvalCategoryDetails,
			ApprovalCategoryApplicableVariablesUpdateResource applicableVariables) {

		List<ApprovalCategoryApplicableVariables> appVar = approvalCategoryApplicableVariablesRepository
				.findByApprovalCategoryDetailsId(approvalCategoryDetails.getId());

		for (ApprovalCategoryApplicableVariables approvalCategoryApplicableVariables : appVar) {
			Optional<ApprovalCategoryApplicableVariables> isPresentApplicableVariables = approvalCategoryApplicableVariablesRepository
					.findById(approvalCategoryApplicableVariables.getId());
			if (!isPresentApplicableVariables.isPresent()) {
				throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
			} else {
				ApprovalCategoryApplicableVariables approvalCategoryApplicableVariablesOp = isPresentApplicableVariables
						.get();
				approvalCategoryApplicableVariablesOp.setStatus(CommonStatus.valueOf(applicableVariables.getStatus()));
				approvalCategoryApplicableVariablesOp.setModifiedDate(validationService.getCreateOrModifyDate());
				approvalCategoryApplicableVariablesOp.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

				approvalCategoryApplicableVariablesRepository.save(approvalCategoryApplicableVariablesOp);
			}
		}
		return (ApprovalCategoryApplicableVariables) appVar;
	}

}
