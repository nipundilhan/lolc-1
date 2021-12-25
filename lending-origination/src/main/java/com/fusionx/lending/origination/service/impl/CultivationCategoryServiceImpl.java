package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.CultivationCategory;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.CultivationCategoryRepository;
import com.fusionx.lending.origination.resource.CommonAddResource;
import com.fusionx.lending.origination.resource.CommonUpdateResource;
import com.fusionx.lending.origination.service.CultivationCategoryService;
import com.fusionx.lending.origination.service.ValidateService;

/**
 * Cultivation CategoryService Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-12-2020             				 MenukaJ        Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class CultivationCategoryServiceImpl extends MessagePropertyBase implements CultivationCategoryService {
	
	@Autowired
	private CultivationCategoryRepository cultivationCategoryRepository;
	
	@Autowired 
	private ValidateService validateService;

	@Override
	public List<CultivationCategory> getAll() {
		return cultivationCategoryRepository.findAll();
	}

	@Override
	public Optional<CultivationCategory> getById(Long id) {
		Optional<CultivationCategory> isPresentCultivationCategory= cultivationCategoryRepository.findById(id);
		if (isPresentCultivationCategory.isPresent()) {
			return Optional.ofNullable(isPresentCultivationCategory.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<CultivationCategory> getByCode(String code) {
		Optional<CultivationCategory> isPresentCultivationCategory= cultivationCategoryRepository.findByCode(code);
		if (isPresentCultivationCategory.isPresent()) {
			return Optional.ofNullable(isPresentCultivationCategory.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<CultivationCategory> getByName(String name) {
		return cultivationCategoryRepository.findByNameContaining(name);
	}

	@Override
	public List<CultivationCategory> getByStatus(String status) {
		return cultivationCategoryRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public CultivationCategory addCultivationCategory(String tenantId, CommonAddResource commonAddResource) {
        
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
        
        Optional<CultivationCategory> isPresentCultivationCategory = cultivationCategoryRepository.findByCode(commonAddResource.getCode());
        if (isPresentCultivationCategory.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty(COMMON_UNIQUE), CODE);
		}
        
        CultivationCategory cultivationCategory = new CultivationCategory();
        cultivationCategory.setTenantId(tenantId);
        cultivationCategory.setCode(commonAddResource.getCode());
        cultivationCategory.setName(commonAddResource.getName());
        cultivationCategory.setDescription(commonAddResource.getDescription());
        cultivationCategory.setStatus(CommonStatus.valueOf(commonAddResource.getStatus()));
        cultivationCategory.setCreatedDate(validateService.getCreateOrModifyDate());
        cultivationCategory.setSyncTs(validateService.getCreateOrModifyDate());
        cultivationCategory.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        
        return cultivationCategoryRepository.save(cultivationCategory);

	}

	@Override
	public CultivationCategory updateCultivationCategory(String tenantId, CommonUpdateResource commonUpdateResource) {
		
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		Optional<CultivationCategory> isPresentCultivationCategory= cultivationCategoryRepository.findById(Long.parseLong(commonUpdateResource.getId()));
		if (!isPresentCultivationCategory.isPresent()) 
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), MESSAGE);
		
//		if(!isPresentCultivationCategory.get().getCode().equalsIgnoreCase(commonUpdateResource.getCode()))
//			throw new ValidateRecordException(environment.getProperty(COMMON_CODE_CAN_NOT_CHANGE), MESSAGE);
		Optional<CultivationCategory> isPresentCultivationCategoryCode = cultivationCategoryRepository.findByCode(commonUpdateResource.getCode());
		 if (isPresentCultivationCategoryCode.isPresent()) {
	        	throw new ValidateRecordException(environment.getProperty(COMMON_UNIQUE), CODE);
		}
		 
		if(!isPresentCultivationCategory.get().getVersion().equals(Long.parseLong(commonUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), VERSION);
		
        CultivationCategory cultivationCategory = isPresentCultivationCategory.get();
        cultivationCategory.setTenantId(tenantId);
        cultivationCategory.setCode(commonUpdateResource.getCode());
        cultivationCategory.setName(commonUpdateResource.getName());
        cultivationCategory.setDescription(commonUpdateResource.getDescription());
        cultivationCategory.setStatus(CommonStatus.valueOf(commonUpdateResource.getStatus()));
        cultivationCategory.setModifiedDate(validateService.getCreateOrModifyDate());
        cultivationCategory.setSyncTs(validateService.getCreateOrModifyDate());
        cultivationCategory.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
        
        return cultivationCategoryRepository.saveAndFlush(cultivationCategory);
		
	}

}
