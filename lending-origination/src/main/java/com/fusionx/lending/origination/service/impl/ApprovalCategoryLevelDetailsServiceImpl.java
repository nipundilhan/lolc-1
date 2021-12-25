package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
 * Approval Category Level Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-09-2021    		 	 FXL-840 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ApprovalCategoryLevelDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ApprovalCategoryLevelDetailsRepository;
import com.fusionx.lending.origination.resource.ApprovalCategoryLevelDetailsAddResource;
import com.fusionx.lending.origination.resource.ApprovalCategoryLevelDetailsUpdateResource;
import com.fusionx.lending.origination.service.ApprovalCategoryLevelDetailsService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class ApprovalCategoryLevelDetailsServiceImpl extends MessagePropertyBase
		implements ApprovalCategoryLevelDetailsService {

	@Autowired
	private ApprovalCategoryLevelDetailsRepository approvalCategoryLevelDetailsRepository;

	@Autowired
	private ValidateService validationService;

	@Override
	public List<ApprovalCategoryLevelDetails> getAll() {
		return approvalCategoryLevelDetailsRepository.findAll();
	}

	@Override
	public Optional<ApprovalCategoryLevelDetails> getById(Long id) {
		Optional<ApprovalCategoryLevelDetails> isPresentApprovalCategoryLevelDetails = approvalCategoryLevelDetailsRepository
				.findById(id);
		if (isPresentApprovalCategoryLevelDetails.isPresent()) {
			return Optional.ofNullable(isPresentApprovalCategoryLevelDetails.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<ApprovalCategoryLevelDetails> getByCode(String code) {
		Optional<ApprovalCategoryLevelDetails> isPresentApprovalCategoryLevelDetails = approvalCategoryLevelDetailsRepository
				.findByCode(code);
		if (isPresentApprovalCategoryLevelDetails.isPresent()) {
			return Optional.ofNullable(isPresentApprovalCategoryLevelDetails.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<ApprovalCategoryLevelDetails> getByName(String name) {
		return approvalCategoryLevelDetailsRepository.findByNameContaining(name);
	}

	@Override
	public List<ApprovalCategoryLevelDetails> getByStatus(String status) {
		return approvalCategoryLevelDetailsRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public ApprovalCategoryLevelDetails addApprovalCategoryLevelDetails(String tenantId,
			ApprovalCategoryLevelDetailsAddResource approvalCategoryLevelDetailsAddResource) {

		Optional<ApprovalCategoryLevelDetails> isPresentApprovalCategoryLevelDetails = approvalCategoryLevelDetailsRepository
				.findByCode(approvalCategoryLevelDetailsAddResource.getCode());

		if (isPresentApprovalCategoryLevelDetails.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE);

		ApprovalCategoryLevelDetails approvalCategoryLevelDetails = new ApprovalCategoryLevelDetails();
		approvalCategoryLevelDetails.setTenantId(tenantId);
		approvalCategoryLevelDetails.setCode(approvalCategoryLevelDetailsAddResource.getCode());
		approvalCategoryLevelDetails.setName(approvalCategoryLevelDetailsAddResource.getName());
		approvalCategoryLevelDetails.setSequence(Long.parseLong(approvalCategoryLevelDetailsAddResource.getSequence()));
		approvalCategoryLevelDetails
				.setMandatory(CommonStatus.valueOf(approvalCategoryLevelDetailsAddResource.getMandatory()));
		approvalCategoryLevelDetails
				.setStatus(CommonStatus.valueOf(approvalCategoryLevelDetailsAddResource.getStatus()));
		approvalCategoryLevelDetails.setCreatedDate(validationService.getCreateOrModifyDate());
		approvalCategoryLevelDetails.setSyncTs(validationService.getCreateOrModifyDate());
		approvalCategoryLevelDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		return approvalCategoryLevelDetailsRepository.save(approvalCategoryLevelDetails);

	}

	@Override
	public ApprovalCategoryLevelDetails updateApprovalCategoryLevelDetails(String tenantId, Long id,
			ApprovalCategoryLevelDetailsUpdateResource approvalCategoryLevelDetailsUpdateResource) {

		Optional<ApprovalCategoryLevelDetails> isPresentApprovalCategoryLevelDetails = approvalCategoryLevelDetailsRepository
				.findById(id);

		if (!isPresentApprovalCategoryLevelDetails.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		} else {
			if (!isPresentApprovalCategoryLevelDetails.get().getVersion()
					.equals(Long.parseLong(approvalCategoryLevelDetailsUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
						ServiceEntity.VERSION);

			if (!isPresentApprovalCategoryLevelDetails.get().getCode()
					.equalsIgnoreCase(approvalCategoryLevelDetailsUpdateResource.getCode()))
				throw new InvalidServiceIdException(environment.getProperty(RECORD_UPDATED), ServiceEntity.CODE);

			ApprovalCategoryLevelDetails approvalCategoryLevelDetails = isPresentApprovalCategoryLevelDetails.get();
			approvalCategoryLevelDetails.setTenantId(tenantId);
			approvalCategoryLevelDetails.setName(approvalCategoryLevelDetailsUpdateResource.getName());
			approvalCategoryLevelDetails
					.setStatus(CommonStatus.valueOf(approvalCategoryLevelDetailsUpdateResource.getStatus()));
			approvalCategoryLevelDetails
					.setSequence(Long.parseLong(approvalCategoryLevelDetailsUpdateResource.getSequence()));
			approvalCategoryLevelDetails
					.setMandatory(CommonStatus.valueOf(approvalCategoryLevelDetailsUpdateResource.getMandatory()));
			approvalCategoryLevelDetails.setModifiedDate(validationService.getCreateOrModifyDate());
			approvalCategoryLevelDetails.setSyncTs(validationService.getCreateOrModifyDate());
			approvalCategoryLevelDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

			return approvalCategoryLevelDetailsRepository.saveAndFlush(approvalCategoryLevelDetails);
		}
	}

	@Override
	public List<ApprovalCategoryLevelDetails> getByApprovalCategoryDetailsId(Long approvalCategoryDetailsId) {
		
			return this.approvalCategoryLevelDetailsRepository
					.findByApprovalCategoryDetailsId(approvalCategoryDetailsId);
		
	}

}
