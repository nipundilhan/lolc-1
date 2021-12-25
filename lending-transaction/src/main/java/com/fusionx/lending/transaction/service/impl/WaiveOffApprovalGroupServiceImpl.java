package com.fusionx.lending.transaction.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.WaiveOffApprovalGroup;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.exception.ValidateRecordException;
import com.fusionx.lending.transaction.repo.WaiveOffApprovalGroupRepository;
import com.fusionx.lending.transaction.resource.WaiveOffApprovalGroupAddResource;
import com.fusionx.lending.transaction.resource.WaiveOffApprovalGroupUpdateResource;
import com.fusionx.lending.transaction.service.ValidationService;
import com.fusionx.lending.transaction.service.WaiveOffApprovalGroupService;

@Component
@Transactional(rollbackFor=Exception.class)
public class WaiveOffApprovalGroupServiceImpl extends MessagePropertyBase implements WaiveOffApprovalGroupService {

	@Autowired
	private WaiveOffApprovalGroupRepository waiveOffApprovalGroupRepository;
	@Autowired
	private ValidationService validationService;
	
	@Override
	public List<WaiveOffApprovalGroup> findAll() {
		return waiveOffApprovalGroupRepository.findAll();
	}

	@Override
	public Optional<WaiveOffApprovalGroup> findById(Long waiveOffTypeId) {
		return waiveOffApprovalGroupRepository.findById(waiveOffTypeId);
	}

	@Override
	public List<WaiveOffApprovalGroup> findByStatus(String status) {
		return waiveOffApprovalGroupRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public List<WaiveOffApprovalGroup> findByName(String name) {
		return waiveOffApprovalGroupRepository.findByNameContaining(name);
	}

	@Override
	public List<WaiveOffApprovalGroup> findByCode(String code) {
		return waiveOffApprovalGroupRepository.findByCode(code);
	}

	@Override
	public WaiveOffApprovalGroup addWaiveOffType(String tenantId,WaiveOffApprovalGroupAddResource addResource) {
		Optional<WaiveOffApprovalGroup> optWaiveOffType = waiveOffApprovalGroupRepository.findByCodeAndStatus(addResource.getCode(),CommonStatus.ACTIVE);
		if(optWaiveOffType.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "code");
		}
		
		WaiveOffApprovalGroup waiveOffApprovalGroup = new WaiveOffApprovalGroup();
		waiveOffApprovalGroup.setTenantId(tenantId);
		waiveOffApprovalGroup.setCode(addResource.getCode());
		waiveOffApprovalGroup.setName(addResource.getName());
		waiveOffApprovalGroup.setDescription(addResource.getDescription());
		waiveOffApprovalGroup.setStatus(CommonStatus.valueOf(addResource.getStatus()));
		waiveOffApprovalGroup.setAuthorityLimit(new BigDecimal(addResource.getAuthorityLimit()));
		waiveOffApprovalGroup.setFutureOutstAllow(CommonStatus.valueOf(addResource.getFutureOutstAllow()));
		waiveOffApprovalGroup.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		waiveOffApprovalGroup.setCreatedDate(validationService.getCreateOrModifyDate());
		waiveOffApprovalGroup.setSyncTs(validationService.getCreateOrModifyDate());
		return waiveOffApprovalGroupRepository.save(waiveOffApprovalGroup);
	}

	@Override
	public WaiveOffApprovalGroup updateWaiveOffType(String tenantId, Long id,WaiveOffApprovalGroupUpdateResource updateResource) {
		Optional<WaiveOffApprovalGroup> optWaiveOffApprovalGroup = waiveOffApprovalGroupRepository.findById(id);
		if(optWaiveOffApprovalGroup.isPresent()) {
			if (!optWaiveOffApprovalGroup.get().getVersion().equals(Long.parseLong(updateResource.getVersion()))) {
				throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");
			}
			Optional<WaiveOffApprovalGroup> optWaiveOffType = waiveOffApprovalGroupRepository.findByCodeAndStatus(updateResource.getCode(),CommonStatus.ACTIVE);
			if(optWaiveOffType.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "code");
			}
			
//			if(!optWaiveOffApprovalGroup.get().getCode().equalsIgnoreCase(updateResource.getCode())) {
//				throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "code");
//			}
//			
//			if(updateResource.getStatus().equals(CommonStatus.ACTIVE.toString())) {
//				Optional<WaiveOffApprovalGroup> fetchedWaiveOffApprovalGroup = waiveOffApprovalGroupRepository
//						.findByCodeAndStatusAndIdNotIn(optWaiveOffApprovalGroup.get().getCode(), CommonStatus.ACTIVE, id);
//	            if (fetchedWaiveOffApprovalGroup.isPresent()) {
//	                throw new ValidateRecordException(environment.getProperty(COMMON_CODE_UPDATE), "code");
//	            }
//			}
			
            
			WaiveOffApprovalGroup waiveOffApprovalGroup = optWaiveOffApprovalGroup.get();
			waiveOffApprovalGroup.setTenantId(tenantId);
			waiveOffApprovalGroup.setCode(updateResource.getCode());
			waiveOffApprovalGroup.setName(updateResource.getName());
			waiveOffApprovalGroup.setDescription(updateResource.getDescription());
			waiveOffApprovalGroup.setStatus(CommonStatus.valueOf(updateResource.getStatus()));
			waiveOffApprovalGroup.setAuthorityLimit(new BigDecimal(updateResource.getAuthorityLimit()));
			waiveOffApprovalGroup.setFutureOutstAllow(CommonStatus.valueOf(updateResource.getFutureOutstAllow()));
			waiveOffApprovalGroup.setModifiedDate(validationService.getCreateOrModifyDate());
			waiveOffApprovalGroup.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			return waiveOffApprovalGroupRepository.save(waiveOffApprovalGroup);
		} else {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
			
		}
	}

}
