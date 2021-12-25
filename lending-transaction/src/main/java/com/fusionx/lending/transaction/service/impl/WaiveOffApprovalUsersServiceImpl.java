package com.fusionx.lending.transaction.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.WaiveOffApprovalGroup;
import com.fusionx.lending.transaction.domain.WaiveOffApprovalUsers;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.exception.ValidateRecordException;
import com.fusionx.lending.transaction.repo.WaiveOffApprovalGroupRepository;
import com.fusionx.lending.transaction.repo.WaiveOffApprovalUsersRepository;
import com.fusionx.lending.transaction.resource.UserProfileResource;
import com.fusionx.lending.transaction.resource.WaiveOffApprovalUsersAddResource;
import com.fusionx.lending.transaction.resource.WaiveOffApprovalUsersUpdateResource;
import com.fusionx.lending.transaction.service.ValidateService;
import com.fusionx.lending.transaction.service.ValidationService;
import com.fusionx.lending.transaction.service.WaiveOffApprovalUsersService;

@Component
@Transactional(rollbackFor = Exception.class)
public class WaiveOffApprovalUsersServiceImpl extends MessagePropertyBase implements WaiveOffApprovalUsersService {

	@Autowired
	private WaiveOffApprovalUsersRepository waiveOffApprovalUsersRepository;
	@Autowired
	private WaiveOffApprovalGroupRepository waiveOffApprovalGroupRepository;
	@Autowired
	private ValidationService validationService;
	@Autowired 
	private ValidateService validateService;
	
	@Override
	public List<WaiveOffApprovalUsers> findAll() {
		return waiveOffApprovalUsersRepository.findAll();
	}

	@Override
	public Optional<WaiveOffApprovalUsers> findById(Long waiveOffTransactionTypeId) {
		return waiveOffApprovalUsersRepository.findById(waiveOffTransactionTypeId);
	}

	@Override
	public List<WaiveOffApprovalUsers> findByStatus(String status) {
		return waiveOffApprovalUsersRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public List<WaiveOffApprovalUsers> findByUserId(String userId) {
		return waiveOffApprovalUsersRepository.findByUserId(userId);
	}

	@Override
	public List<WaiveOffApprovalUsers> findByWaiveOffApprovalGroupId(Long waiveOffApprovalGroupId) {
		return waiveOffApprovalUsersRepository.findByWaiveOffApprovalGroupId(waiveOffApprovalGroupId);
	}

	@Override
	public WaiveOffApprovalUsers addWaiveOffApprovalUsers(String tenantId,WaiveOffApprovalUsersAddResource waiveOffApprovalUsersAddResource) {
		Optional<WaiveOffApprovalGroup> optWaiveOffApprovalGroup = waiveOffApprovalGroupRepository.findById(Long.valueOf(waiveOffApprovalUsersAddResource.getWaiveOffApprovalGroupId()));
		if(!optWaiveOffApprovalGroup.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "waiveOffApprovalGroupId");
		}
		UserProfileResource userProfileResource = validateService.validateUserProfileByUserId(
				tenantId, waiveOffApprovalUsersAddResource.getUserId(), waiveOffApprovalUsersAddResource.getUserName());
		
		Optional<WaiveOffApprovalUsers> theWaiveOffApprovalUsers = waiveOffApprovalUsersRepository.findByWaiveOffApprovalGroupIdAndUserIdAndStatus(
				optWaiveOffApprovalGroup.get().getId(), userProfileResource.getUserId(), CommonStatus.ACTIVE);
		if(theWaiveOffApprovalUsers.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(ALREADY_WAIVE_OFF_GROUP_HAS_USER), "waiveOffApprovalGroupId");
		}
		
		WaiveOffApprovalUsers waiveOffApprovalUsers = new WaiveOffApprovalUsers();
		waiveOffApprovalUsers.setTenantId(tenantId);
		waiveOffApprovalUsers.setStatus(CommonStatus.valueOf(waiveOffApprovalUsersAddResource.getStatus()));
		waiveOffApprovalUsers.setWaiveOffApprovalGroup(optWaiveOffApprovalGroup.get());
		waiveOffApprovalUsers.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		waiveOffApprovalUsers.setCreatedDate(validationService.getCreateOrModifyDate());
		waiveOffApprovalUsers.setSyncTs(validationService.getCreateOrModifyDate());
		waiveOffApprovalUsers.setUserTableId(userProfileResource.getId());
		waiveOffApprovalUsers.setUserId(userProfileResource.getUserId());
		waiveOffApprovalUsers.setUserName(userProfileResource.getUserName());
		return waiveOffApprovalUsersRepository.save(waiveOffApprovalUsers);
	}

	@Override
	public WaiveOffApprovalUsers updateWaiveOffApprovalUsers(String tenantId, Long id,WaiveOffApprovalUsersUpdateResource waiveOffApprovalUsersUpdateResource) {
		Optional<WaiveOffApprovalUsers> optWaiveOffApprovalUsers = waiveOffApprovalUsersRepository.findById(id);
		if(optWaiveOffApprovalUsers.isPresent()) {
			WaiveOffApprovalUsers waiveOffApprovalUsers = optWaiveOffApprovalUsers.get();
			if (!optWaiveOffApprovalUsers.get().getVersion().equals(Long.parseLong(waiveOffApprovalUsersUpdateResource.getVersion()))) {
				throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");
			}
			
			UserProfileResource userProfileResource = validateService.validateUserProfileByUserId(
					tenantId, waiveOffApprovalUsersUpdateResource.getUserId(), waiveOffApprovalUsersUpdateResource.getUserName());
			if(!(userProfileResource.getUserId().equals(waiveOffApprovalUsers.getUserId()))) {
				throw new ValidateRecordException(environment.getProperty(USER_UPDATE_VALUE), "userId");
			}
			
			Optional<WaiveOffApprovalGroup> optWaiveOffApprovalGroup = waiveOffApprovalGroupRepository.findById(Long.valueOf(waiveOffApprovalUsersUpdateResource.getWaiveOffApprovalGroupId()));
			if(!optWaiveOffApprovalGroup.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "waiveOffApprovalGroupId");
			} else {
				if(!(optWaiveOffApprovalUsers.get().getWaiveOffApprovalGroup().getId().equals(Long.valueOf(waiveOffApprovalUsersUpdateResource.getWaiveOffApprovalGroupId())))) {
					throw new ValidateRecordException(environment.getProperty(WAIVE_OFF_APPROVAL_GROUP_UPDATE_VALUE), "waiveOffApprovalGroupId");
				}
			}
			
			if(waiveOffApprovalUsersUpdateResource.getStatus().equals(CommonStatus.ACTIVE.toString())) {
				Optional<WaiveOffApprovalUsers> theWaiveOffApprovalUsers = waiveOffApprovalUsersRepository.findByWaiveOffApprovalGroupIdAndUserIdAndStatusAndIdNotIn(
						optWaiveOffApprovalGroup.get().getId(), userProfileResource.getUserId(), CommonStatus.ACTIVE, id);
				if(theWaiveOffApprovalUsers.isPresent()) {
					throw new ValidateRecordException(environment.getProperty(ALREADY_WAIVE_OFF_GROUP_HAS_USER), "waiveOffApprovalGroupId");
				}
			}
			
			waiveOffApprovalUsers.setTenantId(tenantId);
			waiveOffApprovalUsers.setStatus(CommonStatus.valueOf(waiveOffApprovalUsersUpdateResource.getStatus()));
			waiveOffApprovalUsers.setWaiveOffApprovalGroup(optWaiveOffApprovalGroup.get());
			waiveOffApprovalUsers.setModifiedDate(validationService.getCreateOrModifyDate());
			waiveOffApprovalUsers.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			return waiveOffApprovalUsersRepository.save(waiveOffApprovalUsers);
			
		} else {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
			
		}
	}

}
