package com.fusionx.lending.transaction.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.WaiveOffApprovalGroup;
import com.fusionx.lending.transaction.domain.WaiveOffApprovalTypes;
import com.fusionx.lending.transaction.domain.WaiveOffType;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.exception.ValidateRecordException;
import com.fusionx.lending.transaction.repo.WaiveOffApprovalGroupRepository;
import com.fusionx.lending.transaction.repo.WaiveOffApprovalTypesRepository;
import com.fusionx.lending.transaction.repo.WaiveOffTypeRepository;
import com.fusionx.lending.transaction.resource.WaiveOffApprovalTypesAddResource;
import com.fusionx.lending.transaction.resource.WaiveOffApprovalTypesUpdateResource;
import com.fusionx.lending.transaction.service.ValidationService;
import com.fusionx.lending.transaction.service.WaiveOffApprovalTypesService;

@Component
@Transactional(rollbackFor=Exception.class)
public class WaiveOffApprovalTypesServiceImpl extends MessagePropertyBase implements WaiveOffApprovalTypesService {

	@Autowired
	private WaiveOffApprovalTypesRepository waiveOffApprovalTypesRepository;
	@Autowired
	private WaiveOffTypeRepository waiveOffTypeRepository;
	@Autowired
	private WaiveOffApprovalGroupRepository waiveOffApprovalGroupRepository;
	@Autowired
	private ValidationService validationService;
	
	@Override
	public List<WaiveOffApprovalTypes> findAll() {
		return waiveOffApprovalTypesRepository.findAll();
	}

	@Override
	public Optional<WaiveOffApprovalTypes> findById(Long waiveOffTransactionTypeId) {
		return waiveOffApprovalTypesRepository.findById(waiveOffTransactionTypeId);
	}

	@Override
	public List<WaiveOffApprovalTypes> findByStatus(String status) {
		return waiveOffApprovalTypesRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public List<WaiveOffApprovalTypes> findByWaiveOffTypeId(Long waiveOffTypeId) {
		return waiveOffApprovalTypesRepository.findByWaiveOffTypeId(waiveOffTypeId);
	}

	@Override
	public List<WaiveOffApprovalTypes> findByWaiveOffApprovalGroupId(Long waiveOffApprovalGroupId) {
		return waiveOffApprovalTypesRepository.findByWaiveOffApprovalGroupId(waiveOffApprovalGroupId);
	}

	@Override
	public WaiveOffApprovalTypes addWaiveOffApprovalTypes(String tenantId,WaiveOffApprovalTypesAddResource waiveOffApprovalTypesAddResource) {
		Optional<WaiveOffType> optWaiveOffType = waiveOffTypeRepository.findById(Long.valueOf(waiveOffApprovalTypesAddResource.getWaiveOffTypeId()));
		if(!optWaiveOffType.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "waiveOffTypeId");
		}
		
		Optional<WaiveOffApprovalGroup> optWaiveOffApprovalGroup = waiveOffApprovalGroupRepository.findById(Long.valueOf(waiveOffApprovalTypesAddResource.getWaiveOffApprovalGroupId()));
		if(!optWaiveOffApprovalGroup.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "waiveOffApprovalGroupId");
		}
		
		Optional<WaiveOffApprovalTypes> theWaiveOffApprovalTypes = waiveOffApprovalTypesRepository.findByWaiveOffTypeIdAndWaiveOffApprovalGroupIdAndStatus(
				optWaiveOffType.get().getId(), optWaiveOffApprovalGroup.get().getId(), CommonStatus.ACTIVE);
		if(theWaiveOffApprovalTypes.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(ALREADY_WAIVE_OFF_GROUP_HAS_TYPE), "waiveOffApprovalGroupId");
		}
		
		WaiveOffApprovalTypes waiveOffApprovalTypes = new WaiveOffApprovalTypes();
		waiveOffApprovalTypes.setTenantId(tenantId);
		waiveOffApprovalTypes.setStatus(CommonStatus.valueOf(waiveOffApprovalTypesAddResource.getStatus()));
		waiveOffApprovalTypes.setWaiveOffType(optWaiveOffType.get());
		waiveOffApprovalTypes.setWaiveOffApprovalGroup(optWaiveOffApprovalGroup.get());
		waiveOffApprovalTypes.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		waiveOffApprovalTypes.setCreatedDate(validationService.getCreateOrModifyDate());
		waiveOffApprovalTypes.setSyncTs(validationService.getCreateOrModifyDate());
		return waiveOffApprovalTypesRepository.save(waiveOffApprovalTypes);
	}

	@Override
	public WaiveOffApprovalTypes updateWaiveOffApprovalTypes(String tenantId, Long id,WaiveOffApprovalTypesUpdateResource waiveOffApprovalTypesUpdateResource) {
		Optional<WaiveOffApprovalTypes> optWaiveOffApprovalTypes = waiveOffApprovalTypesRepository.findById(id);
		if(optWaiveOffApprovalTypes.isPresent()) {
			if (!optWaiveOffApprovalTypes.get().getVersion().equals(Long.parseLong(waiveOffApprovalTypesUpdateResource.getVersion()))) {
				throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");
			}
			
			Optional<WaiveOffType> optWaiveOffType = waiveOffTypeRepository.findById(Long.valueOf(waiveOffApprovalTypesUpdateResource.getWaiveOffTypeId()));
			if(!optWaiveOffType.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "waiveOffTypeId");
			} else {
				if(!(optWaiveOffApprovalTypes.get().getWaiveOffType().getId().equals(Long.valueOf(waiveOffApprovalTypesUpdateResource.getWaiveOffTypeId())))) {
					throw new ValidateRecordException(environment.getProperty(WAIVE_OFF_TYPE_UPDATE_VALUE), "waiveOffTypeId");
				}
			}
			
			Optional<WaiveOffApprovalGroup> optWaiveOffApprovalGroup = waiveOffApprovalGroupRepository.findById(Long.valueOf(waiveOffApprovalTypesUpdateResource.getWaiveOffApprovalGroupId()));
			if(!optWaiveOffApprovalGroup.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "waiveOffApprovalGroupId");
			} else {
				if(!(optWaiveOffApprovalTypes.get().getWaiveOffApprovalGroup().getId().equals(Long.valueOf(waiveOffApprovalTypesUpdateResource.getWaiveOffApprovalGroupId())))) {
					throw new ValidateRecordException(environment.getProperty(WAIVE_OFF_APPROVAL_GROUP_UPDATE_VALUE), "waiveOffApprovalGroupId");
				}
			}
			
			if(waiveOffApprovalTypesUpdateResource.getStatus().equals(CommonStatus.ACTIVE.toString())) {
				Optional<WaiveOffApprovalTypes> theWaiveOffApprovalTypes = waiveOffApprovalTypesRepository.findByWaiveOffTypeIdAndWaiveOffApprovalGroupIdAndStatusAndIdNotIn(
						optWaiveOffType.get().getId(), optWaiveOffApprovalGroup.get().getId(), CommonStatus.ACTIVE, id);
				if(theWaiveOffApprovalTypes.isPresent()) {
					throw new ValidateRecordException(environment.getProperty(ALREADY_WAIVE_OFF_GROUP_HAS_TYPE), "waiveOffApprovalGroupId");
				}
			}
			
			WaiveOffApprovalTypes waiveOffApprovalTypes = optWaiveOffApprovalTypes.get();
			waiveOffApprovalTypes.setTenantId(tenantId);
			waiveOffApprovalTypes.setStatus(CommonStatus.valueOf(waiveOffApprovalTypesUpdateResource.getStatus()));
			waiveOffApprovalTypes.setWaiveOffType(optWaiveOffType.get());
			waiveOffApprovalTypes.setWaiveOffApprovalGroup(optWaiveOffApprovalGroup.get());
			waiveOffApprovalTypes.setModifiedDate(validationService.getCreateOrModifyDate());
			waiveOffApprovalTypes.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			return waiveOffApprovalTypesRepository.save(waiveOffApprovalTypes);
		} else {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
			
		}
		
	}

}
