package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ApprovalGroup;
import com.fusionx.lending.origination.domain.ApprovalGroupUserMappingDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ApprovalGroupRepository;
import com.fusionx.lending.origination.repository.ApprovalGroupUserMappingDetailsRepository;
import com.fusionx.lending.origination.resource.ApprovalGroupUserMappingDetailsAddResource;
import com.fusionx.lending.origination.resource.ApprovalGroupUserMappingDetailsUpdateResource;
import com.fusionx.lending.origination.resource.EmployerResponse;
import com.fusionx.lending.origination.service.ApprovalGroupUserMappingDetailsService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class ApprovalGroupUserMappingDetailsServiceImpl extends MessagePropertyBase
		implements ApprovalGroupUserMappingDetailsService {

	@Autowired
	private ApprovalGroupUserMappingDetailsRepository userMappingDetailsRepository;

	@Autowired
	private ApprovalGroupRepository approvalGroupRepository;

	@Autowired
	private ValidateService validationService;

	@Override
	public List<ApprovalGroupUserMappingDetails> getAll() {
		return userMappingDetailsRepository.findAll();
	}

	@Override
	public Optional<ApprovalGroupUserMappingDetails> getById(Long id) {
		Optional<ApprovalGroupUserMappingDetails> isPresentUserMappingDetails = userMappingDetailsRepository
				.findById(id);
		if (isPresentUserMappingDetails.isPresent()) {
			return Optional.ofNullable(isPresentUserMappingDetails.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<ApprovalGroupUserMappingDetails> getByStatus(String status) {
		return userMappingDetailsRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public ApprovalGroupUserMappingDetails addUserMappingDetails(String tenantId,
			ApprovalGroupUserMappingDetailsAddResource userMappingDetailsAddResource) {

		Optional<ApprovalGroup> isPresentApprovalGroup = approvalGroupRepository
				.findById(Long.parseLong(userMappingDetailsAddResource.getApprovalGroupId()));
		if (!isPresentApprovalGroup.isPresent())
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "approvalGroup");

		EmployerResponse employerResponse = null;
		if (userMappingDetailsAddResource.getUserId() != null && !userMappingDetailsAddResource.getUserId().isEmpty()) {
			employerResponse = validationService.validateEmployerById(tenantId,
					userMappingDetailsAddResource.getUserId(), userMappingDetailsAddResource.getName());
		}

		ApprovalGroupUserMappingDetails userMappingDetails = new ApprovalGroupUserMappingDetails();
		userMappingDetails.setTenantId(tenantId);
		userMappingDetails.setUserId(employerResponse != null ? employerResponse.getId() : null);
		userMappingDetails.setApprovalGroup(isPresentApprovalGroup.get());
		userMappingDetails.setName(employerResponse != null ? employerResponse.getName() : null);
		userMappingDetails.setStatus(CommonStatus.valueOf(userMappingDetailsAddResource.getStatus()));
		userMappingDetails.setCreatedDate(validationService.getCreateOrModifyDate());
		userMappingDetails.setSyncTs(validationService.getCreateOrModifyDate());
		userMappingDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		return userMappingDetailsRepository.save(userMappingDetails);

	}

	@Override
	public ApprovalGroupUserMappingDetails updateUserMappingDetails(String tenantId, Long id,
			ApprovalGroupUserMappingDetailsUpdateResource userMappingDetailsUpdateResource) {

		Optional<ApprovalGroupUserMappingDetails> isPresentUserMappingDetails = userMappingDetailsRepository
				.findById(id);
		if (!isPresentUserMappingDetails.isPresent())
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");

		if (!isPresentUserMappingDetails.get().getVersion()
				.equals(Long.parseLong(userMappingDetailsUpdateResource.getVersion())))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION);

		Optional<ApprovalGroup> isPresentApprovalGroup = approvalGroupRepository
				.findById(Long.parseLong(userMappingDetailsUpdateResource.getApprovalGroupId()));
		if (!isPresentApprovalGroup.isPresent())
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "approvalGroup");

		EmployerResponse employerResponse = null;
		if (userMappingDetailsUpdateResource.getUserId() != null
				&& !userMappingDetailsUpdateResource.getUserId().isEmpty()) {
			employerResponse = validationService.validateEmployerById(tenantId,
					userMappingDetailsUpdateResource.getUserId(), userMappingDetailsUpdateResource.getName());
		}

		ApprovalGroupUserMappingDetails userMappingDetails = isPresentUserMappingDetails.get();
		userMappingDetails.setTenantId(tenantId);
		userMappingDetails.setUserId(employerResponse != null ? employerResponse.getId() : null);
		userMappingDetails.setApprovalGroup(isPresentApprovalGroup.get());
		userMappingDetails.setName(employerResponse != null ? employerResponse.getName() : null);
		userMappingDetails.setStatus(CommonStatus.valueOf(userMappingDetailsUpdateResource.getStatus()));
		userMappingDetails.setModifiedDate(validationService.getCreateOrModifyDate());
		userMappingDetails.setSyncTs(validationService.getCreateOrModifyDate());
		userMappingDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

		return userMappingDetailsRepository.saveAndFlush(userMappingDetails);

	}

	@Override
	public List<ApprovalGroupUserMappingDetails> getByApprovalCategoryId(Long approvalCategoryId) {

		return this.userMappingDetailsRepository.findByApprovalGroupId(approvalCategoryId);
	}

}
