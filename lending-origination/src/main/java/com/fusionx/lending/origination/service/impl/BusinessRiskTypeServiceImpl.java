package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.BusinessRiskType;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.BusinessRiskTypeRepository;
import com.fusionx.lending.origination.repository.BusinessTypeRepository;
import com.fusionx.lending.origination.resource.CommonAddResource;
import com.fusionx.lending.origination.resource.CommonUpdateResource;
import com.fusionx.lending.origination.service.BusinessRiskTypeService;


@Component
@Transactional(rollbackFor = Exception.class)
public class BusinessRiskTypeServiceImpl   extends MessagePropertyBase  implements BusinessRiskTypeService{
	

		
	@Autowired
	private BusinessRiskTypeRepository businessRiskTypeRepository;
	
	@Override
	public List<BusinessRiskType> findAll() {
		return businessRiskTypeRepository.findAll();
	}

	@Override
	public Optional<BusinessRiskType> findById(Long id) {
		Optional<BusinessRiskType> businessType = businessRiskTypeRepository.findById(id);
		if (businessType.isPresent()) {
			return Optional.ofNullable(businessType.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<BusinessRiskType> findByStatus(String status) {
		return businessRiskTypeRepository.findByStatus(status);
	}

	@Override
	public List<BusinessRiskType> findByName(String name) {
		return businessRiskTypeRepository.findByNameContaining(name);
	}

	@Override
	public Optional<BusinessRiskType> findByCode(String code) {
		Optional<BusinessRiskType> businessRiskType = businessRiskTypeRepository.findByCode(code);
		if (businessRiskType.isPresent()) {
			return Optional.ofNullable(businessRiskType.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Boolean existsById(Long id) {
		return businessRiskTypeRepository.existsById(id);
	}

	@Override
	public Long saveAndValidateBusinessRiskType(String tenantId, String createdUser, CommonAddResource businessRiskTypeAddResource) {
		
		LoggerRequest.getInstance().logInfo("BusinessType********************************Validate Code*********************************************");
		
		Optional<BusinessRiskType> businessRiskTypeOptional = businessRiskTypeRepository.findByCode(businessRiskTypeAddResource.getCode());
		if(businessRiskTypeOptional.isPresent())
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		LoggerRequest.getInstance().logInfo("BusinessType********************************Save Business Type*********************************************");
		BusinessRiskType businessRiskType=saveBusinessRiskType(tenantId, createdUser, businessRiskTypeAddResource);
		
		return businessRiskType.getId();
	}

	private BusinessRiskType saveBusinessRiskType(String tenantId, String createdUser, CommonAddResource businessRiskTypeAddResource) {
		BusinessRiskType businessRiskType = new BusinessRiskType();
		businessRiskType.setTenantId(tenantId);
		businessRiskType.setCode(businessRiskTypeAddResource.getCode());
		businessRiskType.setName(businessRiskTypeAddResource.getName());
		businessRiskType.setDescription(businessRiskTypeAddResource.getDescription());
		businessRiskType.setStatus(businessRiskTypeAddResource.getStatus());
		businessRiskType.setCreatedUser(createdUser);
		businessRiskType.setCreatedDate(getCreateOrModifyDate());
		businessRiskType.setSyncTs(getCreateOrModifyDate());
		businessRiskType = businessRiskTypeRepository.saveAndFlush(businessRiskType);
		
		return businessRiskType;
	}
	
	@Override
	public Long updateAndValidateBusinessRiskType(String tenantId, String createdUser, Long id, CommonUpdateResource businessRiskTypeUpdateResource) {
		
		LoggerRequest.getInstance().logInfo("BusinessType********************************Validate Version*********************************************");
		Optional<BusinessRiskType> isPresentBusinessRiskType = businessRiskTypeRepository.findById(id);
		if(!isPresentBusinessRiskType.get().getVersion().equals(Long.parseLong(businessRiskTypeUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
		
		if(!isPresentBusinessRiskType.get().getCode().equals(businessRiskTypeUpdateResource.getCode()))
			throw new ValidateRecordException(environment.getProperty("common.code-cannot-change"), "code");
		
//		LoggerRequest.getInstance().logInfo("BusinessType********************************Validate Code*********************************************");
//		Optional<BusinessRiskType> isPresentBusinessRiskTypeByCode = businessRiskTypeRepository.findByCodeAndIdNotIn(businessRiskTypeUpdateResource.getCode(), id);
//		if (isPresentBusinessRiskTypeByCode.isPresent())
//			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		LoggerRequest.getInstance().logInfo("BusinessType********************************Update Business Type*********************************************");
		BusinessRiskType businessRiskType=updateBusinessType(createdUser, businessRiskTypeUpdateResource, id);
		
		return businessRiskType.getId();
	}
	
	private BusinessRiskType updateBusinessType(String createdUser, CommonUpdateResource businessRiskTypeUpdateResource, Long id) {
		BusinessRiskType businessRiskType = businessRiskTypeRepository.getOne(id);
		businessRiskType.setName(businessRiskTypeUpdateResource.getName());
		businessRiskType.setDescription(businessRiskTypeUpdateResource.getDescription());
		businessRiskType.setStatus(businessRiskTypeUpdateResource.getStatus());
		businessRiskType.setModifiedUser(createdUser);
		businessRiskType.setModifiedDate(getCreateOrModifyDate());
		businessRiskType.setSyncTs(getCreateOrModifyDate());
		businessRiskType=businessRiskTypeRepository.saveAndFlush(businessRiskType);
		
		return businessRiskType;
	}

	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}



}
