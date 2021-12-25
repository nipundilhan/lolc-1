package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.domain.HouseHoldExpenseCategory;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.BusinessTypeRepository;
import com.fusionx.lending.origination.repository.HouseHoldExpenseCategoryRepository;
import com.fusionx.lending.origination.resource.BusinessTypeAddResource;
import com.fusionx.lending.origination.resource.BusinessTypeUpdateResource;
import com.fusionx.lending.origination.resource.HouseHoldExpenseCategoryAddResource;
import com.fusionx.lending.origination.resource.HouseHoldExpenseCategoryUpdateResource;
import com.fusionx.lending.origination.service.HouseHoldExpenseCategoryService;

@Component
@Transactional(rollbackFor = Exception.class)
public class HouseHoldExpenseCategoryServiceImpl implements HouseHoldExpenseCategoryService{
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private HouseHoldExpenseCategoryRepository businessTypeRepository;
	
	@Override
	public List<HouseHoldExpenseCategory> findAll() {
		return businessTypeRepository.findAll();
	}

	@Override
	public Optional<HouseHoldExpenseCategory> findById(Long id) {
		Optional<HouseHoldExpenseCategory> businessType = businessTypeRepository.findById(id);
		if (businessType.isPresent()) {
			return Optional.ofNullable(businessType.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<HouseHoldExpenseCategory> findByStatus(String status) {
		return businessTypeRepository.findByStatus(status);
	}

	@Override
	public List<HouseHoldExpenseCategory> findByName(String name) {
		return businessTypeRepository.findByNameContaining(name);
	}

	@Override
	public Optional<HouseHoldExpenseCategory> findByCode(String code) {
		Optional<HouseHoldExpenseCategory> businessType = businessTypeRepository.findByCode(code);
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
	public Long saveAndValidateBusinessType(String tenantId, String createdUser, HouseHoldExpenseCategoryAddResource businessTypeAddResource) {
		
		LoggerRequest.getInstance().logInfo("BusinessType********************************Validate Code*********************************************");
		if(businessTypeRepository.existsByCodeAndStatus(businessTypeAddResource.getCode(), CommonStatus.ACTIVE.toString()))
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		LoggerRequest.getInstance().logInfo("BusinessType********************************Save Business Type*********************************************");
		HouseHoldExpenseCategory businessType=saveBusinessType(tenantId, createdUser, businessTypeAddResource);
		
		return businessType.getId();
	}

	private HouseHoldExpenseCategory saveBusinessType(String tenantId, String createdUser, HouseHoldExpenseCategoryAddResource businessTypeAddResource) {
		HouseHoldExpenseCategory businessType = new HouseHoldExpenseCategory();
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
	public Long updateAndValidateBusinessType(String tenantId, String createdUser, Long id, HouseHoldExpenseCategoryUpdateResource businessTypeUpdateResource) {
		
		LoggerRequest.getInstance().logInfo("BusinessType********************************Validate Version*********************************************");
		Optional<HouseHoldExpenseCategory> isPresentBusinessType = businessTypeRepository.findById(id);
		if(!isPresentBusinessType.get().getVersion().equals(Long.parseLong(businessTypeUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
		
		LoggerRequest.getInstance().logInfo("BusinessType********************************Validate Code*********************************************");
		Optional<HouseHoldExpenseCategory> isPresentBusinessTypeByCode = businessTypeRepository.findByCodeAndIdNotIn(businessTypeUpdateResource.getCode(), id);
		if (isPresentBusinessTypeByCode.isPresent())
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		LoggerRequest.getInstance().logInfo("BusinessType********************************Update Business Type*********************************************");
		HouseHoldExpenseCategory businessType=updateBusinessType(createdUser, businessTypeUpdateResource, id);
		
		return businessType.getId();
	}
	
	private HouseHoldExpenseCategory updateBusinessType(String createdUser, HouseHoldExpenseCategoryUpdateResource businessTypeUpdateResource, Long id) {
		HouseHoldExpenseCategory businessType = businessTypeRepository.getOne(id);
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

}
