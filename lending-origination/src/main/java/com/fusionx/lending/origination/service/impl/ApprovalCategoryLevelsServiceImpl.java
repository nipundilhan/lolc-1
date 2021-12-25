package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
 * Approval Category Levels
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   14-09-2021    		 	 FXL-823 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ApprovalCategoryLevels;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ApprovalCategoryLevelsRepository;
import com.fusionx.lending.origination.resource.ApprovalCategoryLevelsAddResource;
import com.fusionx.lending.origination.resource.ApprovalCategoryLevelsUpdateResource;
import com.fusionx.lending.origination.service.ApprovalCategoryLevelsService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class ApprovalCategoryLevelsServiceImpl extends MessagePropertyBase implements ApprovalCategoryLevelsService {

	@Autowired
	private ApprovalCategoryLevelsRepository approvalCategoryLevelsRepository;

	@Autowired
	private ValidateService validationService;

	@Override
	public List<ApprovalCategoryLevels> getAll() {
		return approvalCategoryLevelsRepository.findAll();
	}

	@Override
	public Optional<ApprovalCategoryLevels> getById(Long id) {
		Optional<ApprovalCategoryLevels> isPresentApprovalCategoryLevels = approvalCategoryLevelsRepository
				.findById(id);
		if (isPresentApprovalCategoryLevels.isPresent()) {
			return Optional.ofNullable(isPresentApprovalCategoryLevels.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<ApprovalCategoryLevels> getByCode(String code) {
		Optional<ApprovalCategoryLevels> isPresentApprovalCategoryLevels = approvalCategoryLevelsRepository
				.findByCode(code);
		if (isPresentApprovalCategoryLevels.isPresent()) {
			return Optional.ofNullable(isPresentApprovalCategoryLevels.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<ApprovalCategoryLevels> getByName(String name) {
		return approvalCategoryLevelsRepository.findByNameContaining(name);
	}

	@Override
	public List<ApprovalCategoryLevels> getByStatus(String status) {
		return approvalCategoryLevelsRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public ApprovalCategoryLevels addApprovalCategoryLevels(String tenantId,
			ApprovalCategoryLevelsAddResource approvalCategoryLevelsAddResource) {

		Optional<ApprovalCategoryLevels> isPresentApprovalCategoryLevels = approvalCategoryLevelsRepository
				.findByCode(approvalCategoryLevelsAddResource.getCode());

		if (isPresentApprovalCategoryLevels.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE);

		ApprovalCategoryLevels approvalCategoryLevels = new ApprovalCategoryLevels();
		approvalCategoryLevels.setTenantId(tenantId);
		approvalCategoryLevels.setCode(approvalCategoryLevelsAddResource.getCode());
		approvalCategoryLevels.setName(approvalCategoryLevelsAddResource.getName());
		approvalCategoryLevels.setDescription(approvalCategoryLevelsAddResource.getDescription());
		approvalCategoryLevels.setSequence(Long.parseLong(approvalCategoryLevelsAddResource.getSequence()));
		approvalCategoryLevels.setStatus(CommonStatus.valueOf(approvalCategoryLevelsAddResource.getStatus()));
		approvalCategoryLevels.setCreatedDate(validationService.getCreateOrModifyDate());
		approvalCategoryLevels.setSyncTs(validationService.getCreateOrModifyDate());
		approvalCategoryLevels.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		return approvalCategoryLevelsRepository.save(approvalCategoryLevels);

	}

	@Override
	public ApprovalCategoryLevels updateApprovalCategoryLevels(String tenantId, Long id,
			ApprovalCategoryLevelsUpdateResource approvalCategoryLevelsUpdateResource) {

		Optional<ApprovalCategoryLevels> isPresentApprovalCategoryLevels = approvalCategoryLevelsRepository
				.findById(id);

		if (!isPresentApprovalCategoryLevels.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		} else {
			if (!isPresentApprovalCategoryLevels.get().getVersion()
					.equals(Long.parseLong(approvalCategoryLevelsUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
						ServiceEntity.VERSION);

			if (!isPresentApprovalCategoryLevels.get().getCode()
					.equalsIgnoreCase(approvalCategoryLevelsUpdateResource.getCode()))
				throw new InvalidServiceIdException(environment.getProperty(RECORD_UPDATED), ServiceEntity.CODE);

			ApprovalCategoryLevels approvalCategoryLevels = isPresentApprovalCategoryLevels.get();
			approvalCategoryLevels.setTenantId(tenantId);
			approvalCategoryLevels.setName(approvalCategoryLevelsUpdateResource.getName());
			approvalCategoryLevels.setDescription(approvalCategoryLevelsUpdateResource.getDescription());
			approvalCategoryLevels.setStatus(CommonStatus.valueOf(approvalCategoryLevelsUpdateResource.getStatus()));
			approvalCategoryLevels.setSequence(Long.parseLong(approvalCategoryLevelsUpdateResource.getSequence()));
			approvalCategoryLevels.setModifiedDate(validationService.getCreateOrModifyDate());
			approvalCategoryLevels.setSyncTs(validationService.getCreateOrModifyDate());
			approvalCategoryLevels.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

			return approvalCategoryLevelsRepository.saveAndFlush(approvalCategoryLevels);
		}
	}

}
