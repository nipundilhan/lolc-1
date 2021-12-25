package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ApprovalGroup;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ApprovalGroupRepository;
import com.fusionx.lending.origination.resource.ApprovalGroupAddResource;
import com.fusionx.lending.origination.resource.ApprovalGroupUpdateResource;
import com.fusionx.lending.origination.service.ApprovalGroupService;
import com.fusionx.lending.origination.service.ValidateService;
/**
 * Approval Group
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   27-09-2021   FXL-78 		 FXL-977 	Dilki        Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class ApprovalGroupServiceImpl extends MessagePropertyBase implements ApprovalGroupService {

	@Autowired
	private ApprovalGroupRepository approvalGroupRepository;

	@Autowired
	private ValidateService validationService;

	@Override
	public List<ApprovalGroup> getAll() {
		return approvalGroupRepository.findAll();
	}

	@Override
	public Optional<ApprovalGroup> getById(Long id) {
		return approvalGroupRepository.findById(id);
	}

	@Override
	public Optional<ApprovalGroup> getByCode(String code) {
		return approvalGroupRepository.findByCode(code);
	}

	@Override
	public List<ApprovalGroup> getByName(String name) {
		return approvalGroupRepository.findByNameContaining(name);
	}

	@Override
	public List<ApprovalGroup> getByStatus(String status) {
		return approvalGroupRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public ApprovalGroup addApprovalGroup(String tenantId, ApprovalGroupAddResource approvalGroupAddResource) {

		Optional<ApprovalGroup> isPresentApprovalGroup = approvalGroupRepository
				.findByCode(approvalGroupAddResource.getCode());

		if (isPresentApprovalGroup.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE);

		ApprovalGroup approvalGroup = new ApprovalGroup();
		approvalGroup.setTenantId(tenantId);
		approvalGroup.setCode(approvalGroupAddResource.getCode());
		approvalGroup.setName(approvalGroupAddResource.getName());
		approvalGroup.setDescription(approvalGroupAddResource.getDescription());
		approvalGroup.setMinimumApprovals(Long.parseLong(approvalGroupAddResource.getMinimumApprovalNo()));
		approvalGroup.setStatus(CommonStatus.valueOf(approvalGroupAddResource.getStatus()));
		approvalGroup.setCreatedDate(validationService.getCreateOrModifyDate());
		approvalGroup.setSyncTs(validationService.getCreateOrModifyDate());
		approvalGroup.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		return approvalGroupRepository.save(approvalGroup);

	}

	@Override
	public ApprovalGroup updateApprovalGroup(String tenantId, Long id,
			ApprovalGroupUpdateResource approvalGroupUpdateResource) {

		Optional<ApprovalGroup> isPresentApprovalGroup = approvalGroupRepository.findById(id);

		if (!isPresentApprovalGroup.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		} else {
			if (!isPresentApprovalGroup.get().getVersion()
					.equals(Long.parseLong(approvalGroupUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
						ServiceEntity.VERSION);

			if (!isPresentApprovalGroup.get().getCode().equalsIgnoreCase(approvalGroupUpdateResource.getCode()))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_CODE_CAN_NOT_CHANGE), ServiceEntity.CODE);

			ApprovalGroup approvalGroup = isPresentApprovalGroup.get();
			approvalGroup.setTenantId(tenantId);
			approvalGroup.setName(approvalGroupUpdateResource.getName());
			approvalGroup.setDescription(approvalGroupUpdateResource.getDescription());
			approvalGroup.setStatus(CommonStatus.valueOf(approvalGroupUpdateResource.getStatus()));
			approvalGroup.setMinimumApprovals(Long.parseLong(approvalGroupUpdateResource.getMinimumApprovalNo()));
			approvalGroup.setModifiedDate(validationService.getCreateOrModifyDate());
			approvalGroup.setSyncTs(validationService.getCreateOrModifyDate());
			approvalGroup.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

			return approvalGroupRepository.saveAndFlush(approvalGroup);
		}
	}

}
