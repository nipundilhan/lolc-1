package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ExceptionApprovalGroup;
import com.fusionx.lending.origination.domain.UserMappingDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ExceptionApprovalGroupRepository;
import com.fusionx.lending.origination.repository.UserMappingDetailsRepository;
import com.fusionx.lending.origination.resource.UserMappingDetailsAddResource;
import com.fusionx.lending.origination.resource.UserMappingDetailsUpdateResource;
import com.fusionx.lending.origination.service.UserMappingDetailsHistoryService;
import com.fusionx.lending.origination.service.ExceptionApprovalGroupUserMappingDetailsService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class ExceptionApprovalGroupUserMappingDetailsServiceImpl extends MessagePropertyBase
		implements ExceptionApprovalGroupUserMappingDetailsService {

	@Autowired
	private UserMappingDetailsRepository userMappingDetailsRepository;

	@Autowired
	private ExceptionApprovalGroupRepository approvalCategoryRepository;

	@Autowired
	private ValidateService validationService;

	@Autowired
	private UserMappingDetailsHistoryService userMappingDetailsHistoryService;

	@Override
	public List<UserMappingDetails> getAll() {
		return userMappingDetailsRepository.findAll();
	}

	@Override
	public Optional<UserMappingDetails> getById(Long id) {
		Optional<UserMappingDetails> isPresentUserMappingDetails = userMappingDetailsRepository.findById(id);
		if (isPresentUserMappingDetails.isPresent()) {
			return Optional.ofNullable(isPresentUserMappingDetails.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<UserMappingDetails> getByStatus(String status) {
		return userMappingDetailsRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public UserMappingDetails addUserMappingDetails(String tenantId,
			UserMappingDetailsAddResource userMappingDetailsAddResource) {

		Optional<ExceptionApprovalGroup> isPresentApprovalCategory = approvalCategoryRepository
				.findById(Long.parseLong(userMappingDetailsAddResource.getApprovalGroupId()));

		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate status*******************");
		if (!userMappingDetailsAddResource.getStatus().equals("ACTIVE")) {
			throw new ValidateRecordException(environment.getProperty("active-status"), "status");
		}
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Dupilcates*******************");
		if(userMappingDetailsRepository.existsByUserIdAndName(Long.parseLong(userMappingDetailsAddResource.getUserId()), userMappingDetailsAddResource.getName()))
			throw new ValidateRecordException(environment.getProperty("user.unique"), "userId");
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate user*******************");
		validationService.validateUserProfileById(tenantId,Long.parseLong(userMappingDetailsAddResource.getUserId()), userMappingDetailsAddResource.getName());
		
		UserMappingDetails userMappingDetails = new UserMappingDetails();
		userMappingDetails.setTenantId(tenantId);
		userMappingDetails.setUserId(Long.parseLong(userMappingDetailsAddResource.getUserId()));
		userMappingDetails.setApprovalGroup(isPresentApprovalCategory.get());
		userMappingDetails.setName(userMappingDetailsAddResource.getName());
		userMappingDetails.setStatus(CommonStatus.valueOf(userMappingDetailsAddResource.getStatus()));
		userMappingDetails.setCreatedDate(validationService.getCreateOrModifyDate());
		userMappingDetails.setSyncTs(validationService.getCreateOrModifyDate());
		userMappingDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		return userMappingDetailsRepository.save(userMappingDetails);

	}

	@Override
	public UserMappingDetails updateUserMappingDetails(String tenantId, Long id,
			UserMappingDetailsUpdateResource userMappingDetailsUpdateResource) {

		Optional<UserMappingDetails> isPresentUserMappingDetails = userMappingDetailsRepository.findById(id);

		userMappingDetailsHistoryService.saveHistory(tenantId, isPresentUserMappingDetails.get(),
				LogginAuthentcation.getInstance().getUserName());

		if (!isPresentUserMappingDetails.get().getVersion()
				.equals(Long.parseLong(userMappingDetailsUpdateResource.getVersion())))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION);
		
		LoggerRequest.getInstance().logInfo("************************** User Can Not Update *****************************");
		String user=Long.toString(isPresentUserMappingDetails.get().getUserId());
		if (!user.equals(userMappingDetailsUpdateResource.getUserId())) {
            throw new ValidateRecordException(environment.getProperty("user.update"), "userId");
        }	
		
		if (!isPresentUserMappingDetails.get().getName().equals(userMappingDetailsUpdateResource.getName())) {
            throw new ValidateRecordException(environment.getProperty("user.update"), "userName");
        }
				
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate user*******************");
		validationService.validateUserProfileById(tenantId,Long.parseLong(userMappingDetailsUpdateResource.getUserId()), userMappingDetailsUpdateResource.getName());
				
		Optional<ExceptionApprovalGroup> isPresentApprovalCategory = approvalCategoryRepository
				.findById(Long.parseLong(userMappingDetailsUpdateResource.getApprovalGroupId()));

		UserMappingDetails userMappingDetails = isPresentUserMappingDetails.get();
		userMappingDetails.setTenantId(tenantId);
		userMappingDetails.setUserId(Long.parseLong(userMappingDetailsUpdateResource.getUserId()));
		userMappingDetails.setApprovalGroup(isPresentApprovalCategory.get());
		userMappingDetails.setName(userMappingDetailsUpdateResource.getName());
		userMappingDetails.setStatus(CommonStatus.valueOf(userMappingDetailsUpdateResource.getStatus()));
		userMappingDetails.setModifiedDate(validationService.getCreateOrModifyDate());
		userMappingDetails.setSyncTs(validationService.getCreateOrModifyDate());
		userMappingDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

		return userMappingDetailsRepository.saveAndFlush(userMappingDetails);

	}

	@Override
	public List<UserMappingDetails> getByApprovalCategoryId(Long approvalCategoryId) {

		return this.userMappingDetailsRepository.findByApprovalGroupId(approvalCategoryId);
	}

}
