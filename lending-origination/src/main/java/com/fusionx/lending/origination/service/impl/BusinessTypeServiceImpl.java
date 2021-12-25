package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.BusinessTypeRepository;
import com.fusionx.lending.origination.resource.BusinessTypeAddResource;
import com.fusionx.lending.origination.resource.BusinessTypeUpdateResource;
import com.fusionx.lending.origination.service.BusinessTypeService;

/**
 * Business Type Service Implementation
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   23-12-2020      		     FX-5269	MiyuruW      Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class BusinessTypeServiceImpl implements BusinessTypeService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private BusinessTypeRepository businessTypeRepository;
	
	@Override
	public List<BusinessType> findAll() {
		return businessTypeRepository.findAll();
	}

	@Override
	public Optional<BusinessType> findById(Long id) {
		Optional<BusinessType> businessType = businessTypeRepository.findById(id);
		if (businessType.isPresent()) {
			return Optional.ofNullable(businessType.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<BusinessType> findByStatus(String status) {
		return businessTypeRepository.findByStatus(status);
	}

	@Override
	public List<BusinessType> findByName(String name) {
		return businessTypeRepository.findByNameContaining(name);
	}

	@Override
	public Optional<BusinessType> findByCode(String code) {
		Optional<BusinessType> businessType = businessTypeRepository.findByCode(code);
		if (businessType.isPresent()) {
			return Optional.ofNullable(businessType.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Boolean existsById(Long id) {
		return businessTypeRepository.existsById(id);
	}

	@Override
	public Long saveAndValidateBusinessType(String tenantId, String createdUser, BusinessTypeAddResource businessTypeAddResource) {
		
		LoggerRequest.getInstance().logInfo("BusinessType********************************Validate Code*********************************************");
		if(businessTypeRepository.existsByCodeAndStatus(businessTypeAddResource.getCode(), CommonStatus.ACTIVE.toString()))
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		LoggerRequest.getInstance().logInfo("BusinessType********************************Save Business Type*********************************************");
		BusinessType businessType=saveBusinessType(tenantId, createdUser, businessTypeAddResource);
		
		return businessType.getId();
	}

	private BusinessType saveBusinessType(String tenantId, String createdUser, BusinessTypeAddResource businessTypeAddResource) {
		BusinessType businessType = new BusinessType();
		businessType.setTenantId(tenantId);
		businessType.setCode(businessTypeAddResource.getCode());
		businessType.setName(businessTypeAddResource.getName());
		businessType.setDescription(businessTypeAddResource.getDescription());
		businessType.setStatus(businessTypeAddResource.getStatus());
		businessType.setCreatedUser(createdUser);
		businessType.setCreatedDate(getCreateOrModifyDate());
		businessType.setSyncTs(getCreateOrModifyDate());
		businessType = businessTypeRepository.saveAndFlush(businessType);
		
		return businessType;
	}
	
	@Override
	public Long updateAndValidateBusinessType(String tenantId, String createdUser, Long id, BusinessTypeUpdateResource businessTypeUpdateResource) {
		
		LoggerRequest.getInstance().logInfo("BusinessType********************************Validate Version*********************************************");
		Optional<BusinessType> isPresentBusinessType = businessTypeRepository.findById(id);
		if(!isPresentBusinessType.get().getVersion().equals(Long.parseLong(businessTypeUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
		
		LoggerRequest.getInstance().logInfo("BusinessType********************************Validate Code*********************************************");
		Optional<BusinessType> isPresentBusinessTypeByCode = businessTypeRepository.findByCodeAndIdNotIn(businessTypeUpdateResource.getCode(), id);
		if (isPresentBusinessTypeByCode.isPresent())
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		LoggerRequest.getInstance().logInfo("BusinessType********************************Update Business Type*********************************************");
		BusinessType businessType=updateBusinessType(createdUser, businessTypeUpdateResource, id);
		
		return businessType.getId();
	}
	
	private BusinessType updateBusinessType(String createdUser, BusinessTypeUpdateResource businessTypeUpdateResource, Long id) {
		BusinessType businessType = businessTypeRepository.getOne(id);
		businessType.setCode(businessTypeUpdateResource.getCode());
		businessType.setName(businessTypeUpdateResource.getName());
		businessType.setDescription(businessTypeUpdateResource.getDescription());
		businessType.setStatus(businessTypeUpdateResource.getStatus());
		businessType.setModifiedUser(createdUser);
		businessType.setModifiedDate(getCreateOrModifyDate());
		businessType.setSyncTs(getCreateOrModifyDate());
		businessType=businessTypeRepository.saveAndFlush(businessType);
		
		return businessType;
	}

	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}

	@Override
	public Page<BusinessType> searchBusinessType(String searchq, String name, String code, Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(name==null || name.isEmpty())
			name=null;
		if(code==null || code.isEmpty())
			code=null;
		return businessTypeRepository.searchBusinessType(searchq, name, code, pageable);
	}
}
