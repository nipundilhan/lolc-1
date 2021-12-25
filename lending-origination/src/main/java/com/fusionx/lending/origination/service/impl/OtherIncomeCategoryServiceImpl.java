package com.fusionx.lending.origination.service.impl;

/**
 * Other Income Category Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021   FXL-639  	 FXL-535       Piyumi     Created
 *    
 ********************************************************************************************************
*/
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.OtherIncomeCategory;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.OtherIncomeCategoryRepository;
import com.fusionx.lending.origination.resource.OtherIncomeCategoryAddResource;
import com.fusionx.lending.origination.resource.OtherIncomeCategoryUpdateResource;
import com.fusionx.lending.origination.service.OtherIncomeCategoryService;
import com.fusionx.lending.origination.service.ValidateService;




@Component
@Transactional(rollbackFor = Exception.class)
public class OtherIncomeCategoryServiceImpl extends MessagePropertyBase implements OtherIncomeCategoryService{
	
	
	private OtherIncomeCategoryRepository otherIncomeCategoryRepository;
	
	@Autowired
	public void setOtherIncomeCategoryRepository(OtherIncomeCategoryRepository otherIncomeCategoryRepository) {
		this.otherIncomeCategoryRepository = otherIncomeCategoryRepository;
	}
	
	private ValidateService validateService;
	
	@Autowired
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	@Override
	public OtherIncomeCategory save(String tenantId, OtherIncomeCategoryAddResource otherIncomeCategoryAddResource) {
        
        if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");
        
        Optional<OtherIncomeCategory> isPresentOtherIncomeCategory = otherIncomeCategoryRepository.findByCode(otherIncomeCategoryAddResource.getCode());
        if (isPresentOtherIncomeCategory.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "code");
		}
        
        	
        OtherIncomeCategory otherIncomeCategory = new OtherIncomeCategory();
        otherIncomeCategory.setTenantId(tenantId);
        otherIncomeCategory.setCode(otherIncomeCategoryAddResource.getCode());
        otherIncomeCategory.setName(otherIncomeCategoryAddResource.getName());
        otherIncomeCategory.setDescription(otherIncomeCategoryAddResource.getDescription());
        otherIncomeCategory.setStatus(CommonStatus.valueOf(otherIncomeCategoryAddResource.getStatus()));
        otherIncomeCategory.setSyncTs(validateService.getSyncTs());
        otherIncomeCategory.setCreatedDate(validateService.getCreateOrModifyDate());
        otherIncomeCategory.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        otherIncomeCategory = otherIncomeCategoryRepository.save(otherIncomeCategory);
    	return otherIncomeCategory;
	}

	@Override
	public Optional<OtherIncomeCategory> findById(Long id) {
		Optional<OtherIncomeCategory> isPresentOtherIncomeCategory = otherIncomeCategoryRepository.findById(id);
		if (isPresentOtherIncomeCategory.isPresent()) {
			return Optional.ofNullable(isPresentOtherIncomeCategory.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<OtherIncomeCategory> findAll() {
		return 	otherIncomeCategoryRepository.findAll();
	}

	@Override
	public Optional<OtherIncomeCategory> findByCode(String code) {
		Optional<OtherIncomeCategory> isPresentOtherIncomeCategory = otherIncomeCategoryRepository.findByCode(code);
		if (isPresentOtherIncomeCategory.isPresent()) {
			return Optional.ofNullable(isPresentOtherIncomeCategory.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<OtherIncomeCategory> findByName(String name) {
		return otherIncomeCategoryRepository.findByNameContaining(name);
	}

	@Override
	public List<OtherIncomeCategory> findByStatus(String status) {
		return 	otherIncomeCategoryRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public OtherIncomeCategory update(String tenantId, Long id , OtherIncomeCategoryUpdateResource otherIncomeCategoryUpdateResource) {
		
		if (LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");
		
		Optional<OtherIncomeCategory> isPresentOtherIncomeCategory = otherIncomeCategoryRepository.findById(id);
		if (!isPresentOtherIncomeCategory.isPresent()) 
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
        
		
		if(!isPresentOtherIncomeCategory.get().getVersion().equals(Long.parseLong(otherIncomeCategoryUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");
		
		Optional<OtherIncomeCategory> isPresentOtherIncomeCategoryCode = otherIncomeCategoryRepository.findByCodeAndIdNotIn(otherIncomeCategoryUpdateResource.getCode(), id);
		if (isPresentOtherIncomeCategoryCode.isPresent())
			throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "code");
		
		
		OtherIncomeCategory otherIncomeCategory = isPresentOtherIncomeCategory.get();
		
		otherIncomeCategory.setTenantId(tenantId);
		otherIncomeCategory.setCode(otherIncomeCategoryUpdateResource.getCode());
		otherIncomeCategory.setName(otherIncomeCategoryUpdateResource.getName());
		otherIncomeCategory.setDescription(otherIncomeCategoryUpdateResource.getDescription());
		otherIncomeCategory.setStatus(CommonStatus.valueOf(otherIncomeCategoryUpdateResource.getStatus()));
		otherIncomeCategory.setSyncTs(validateService.getSyncTs());
		otherIncomeCategory.setModifiedDate(validateService.getCreateOrModifyDate());
		otherIncomeCategory.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		otherIncomeCategory = otherIncomeCategoryRepository.saveAndFlush(otherIncomeCategory);
    	return otherIncomeCategory;
	}

}
