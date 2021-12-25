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
import com.fusionx.lending.origination.domain.ApprovalCategoryDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ApprovalCategoryDetailsRepository;
import com.fusionx.lending.origination.resource.ApprovalCategoryApplicableVariablesAddResource;
import com.fusionx.lending.origination.resource.ApprovalCategoryApplicableVariablesUpdateResource;
import com.fusionx.lending.origination.resource.ApprovalCategoryDetailsAddResource;
import com.fusionx.lending.origination.resource.ApprovalCategoryDetailsUpdateResource;
import com.fusionx.lending.origination.service.ApprovalCategoryApplicableVariablesService;
import com.fusionx.lending.origination.service.ApprovalCategoryDetailsHistoryService;
import com.fusionx.lending.origination.service.ApprovalCategoryDetailsService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class ApprovalCategoryDetailsServiceImpl extends MessagePropertyBase implements ApprovalCategoryDetailsService {

	@Autowired
	private ApprovalCategoryDetailsRepository approvalCategoryDetailsRepository;

	@Autowired
	private ValidateService validationService;

	@Autowired
	private ApprovalCategoryDetailsHistoryService approvalCategoryDetailsHistoryService;

	@Autowired
	private ApprovalCategoryApplicableVariablesService approvalCategoryApplicableVariablesService;

	@Override
	public List<ApprovalCategoryDetails> getAll() {
		return approvalCategoryDetailsRepository.findAll();
	}

	@Override
	public Optional<ApprovalCategoryDetails> getById(Long id) {
		Optional<ApprovalCategoryDetails> isPresentApprovalCategoryDetails = approvalCategoryDetailsRepository
				.findById(id);
		if (isPresentApprovalCategoryDetails.isPresent()) {
			return Optional.ofNullable(isPresentApprovalCategoryDetails.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<ApprovalCategoryDetails> getByCode(String code) {
		Optional<ApprovalCategoryDetails> isPresentApprovalCategoryDetails = approvalCategoryDetailsRepository
				.findByCode(code);
		if (isPresentApprovalCategoryDetails.isPresent()) {
			return Optional.ofNullable(isPresentApprovalCategoryDetails.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<ApprovalCategoryDetails> getByName(String name) {
		return approvalCategoryDetailsRepository.findByNameContaining(name);
	}

	@Override
	public List<ApprovalCategoryDetails> getByStatus(String status) {
		return approvalCategoryDetailsRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public ApprovalCategoryDetails addApprovalCategoryDetails(String tenantId,
			ApprovalCategoryDetailsAddResource approvalCategoryDetailsAddResource) {

		Optional<ApprovalCategoryDetails> isPresentApprovalCategoryDetails = approvalCategoryDetailsRepository
				.findByCode(approvalCategoryDetailsAddResource.getCode());

		if (isPresentApprovalCategoryDetails.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE);

		ApprovalCategoryDetails approvalCategoryDetails = new ApprovalCategoryDetails();
		approvalCategoryDetails.setTenantId(tenantId);
		approvalCategoryDetails.setCode(approvalCategoryDetailsAddResource.getCode());
		approvalCategoryDetails.setName(approvalCategoryDetailsAddResource.getName());
		approvalCategoryDetails.setDescription(approvalCategoryDetailsAddResource.getDescription());
		approvalCategoryDetails.setApprovalType(approvalCategoryDetails.getApprovalType());
		approvalCategoryDetails.setModifyWorkflow(approvalCategoryDetails.getModifyWorkflow());
		approvalCategoryDetails.setStatus(CommonStatus.valueOf(approvalCategoryDetailsAddResource.getStatus()));
		approvalCategoryDetails.setCreatedDate(validationService.getCreateOrModifyDate());
		approvalCategoryDetails.setSyncTs(validationService.getCreateOrModifyDate());
		approvalCategoryDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		approvalCategoryDetailsRepository.save(approvalCategoryDetails);

		for (ApprovalCategoryApplicableVariablesAddResource applicableVariables : approvalCategoryDetailsAddResource
				.getApplicableVariables()) {
			approvalCategoryApplicableVariablesService.addApprovalCategoryApplicableVariables(tenantId,
					approvalCategoryDetails, applicableVariables);
		}
		return approvalCategoryDetails;
	}

	@Override
	public ApprovalCategoryDetails updateApprovalCategoryDetails(String tenantId, Long id,
			ApprovalCategoryDetailsUpdateResource approvalCategoryDetailsUpdateResource) {

		Optional<ApprovalCategoryDetails> isPresentApprovalCategoryDetails = approvalCategoryDetailsRepository
				.findById(id);

		if (!isPresentApprovalCategoryDetails.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		} else {
			approvalCategoryDetailsHistoryService.saveHistory(tenantId, isPresentApprovalCategoryDetails.get(),
					LogginAuthentcation.getInstance().getUserName());

			if (!isPresentApprovalCategoryDetails.get().getVersion()
					.equals(Long.parseLong(approvalCategoryDetailsUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
						ServiceEntity.VERSION);

			if (!isPresentApprovalCategoryDetails.get().getCode()
					.equalsIgnoreCase(approvalCategoryDetailsUpdateResource.getCode()))
				throw new InvalidServiceIdException(environment.getProperty(RECORD_UPDATED), ServiceEntity.CODE);

			ApprovalCategoryDetails approvalCategoryDetails = isPresentApprovalCategoryDetails.get();
			approvalCategoryDetails.setTenantId(tenantId);
			approvalCategoryDetails.setName(approvalCategoryDetailsUpdateResource.getName());
			approvalCategoryDetails.setDescription(approvalCategoryDetailsUpdateResource.getDescription());
			approvalCategoryDetails.setApprovalType(approvalCategoryDetails.getApprovalType());
			approvalCategoryDetails.setModifyWorkflow(approvalCategoryDetails.getModifyWorkflow());
			approvalCategoryDetails.setStatus(CommonStatus.valueOf(approvalCategoryDetailsUpdateResource.getStatus()));
			approvalCategoryDetails.setModifiedDate(validationService.getCreateOrModifyDate());
			approvalCategoryDetails.setSyncTs(validationService.getCreateOrModifyDate());
			approvalCategoryDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

			approvalCategoryDetailsRepository.save(approvalCategoryDetails);

			for (ApprovalCategoryApplicableVariablesUpdateResource applicableVariables : approvalCategoryDetailsUpdateResource
					.getApplicableVariables()) {

				approvalCategoryApplicableVariablesService.updateApprovalCategoryApplicableVariables(tenantId,
						approvalCategoryDetails, applicableVariables);
			}
			return approvalCategoryDetails;
		}
	}

}
