package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.OtherIncomeCategory;
import com.fusionx.lending.origination.domain.OtherIncomeType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.OtherIncomeCategoryRepository;
import com.fusionx.lending.origination.repository.OtherIncomeTypeRepository;
import com.fusionx.lending.origination.resource.OtherIncomeTypeAddResource;
import com.fusionx.lending.origination.resource.OtherIncomeTypeUpdateResource;
import com.fusionx.lending.origination.service.OtherIncomeTypeService;
import com.fusionx.lending.origination.service.ValidateService;

/**
 * Other Income Type Service Implementation
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-12-2020      		     FX-5272	MiyuruW      Created
 *    2   18-08-2021     FXL-639     FXL-537	Piyumi       Modified    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class OtherIncomeTypeServiceImpl extends MessagePropertyBase implements OtherIncomeTypeService {
	
	@Autowired
	private OtherIncomeTypeRepository otherIncomeTypeRepository;
	
	private ValidateService validateService;
	
	@Autowired
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	private OtherIncomeCategoryRepository otherIncomeCategoryRepository;
	
	@Autowired
	public void setOtherIncomeCategoryRepository(OtherIncomeCategoryRepository otherIncomeCategoryRepository) {
		this.otherIncomeCategoryRepository = otherIncomeCategoryRepository;
	}
	
	@Override
	public List<OtherIncomeType> findAll() {
		List<OtherIncomeType> otherIncomeTypeList= otherIncomeTypeRepository.findAll();
			for(OtherIncomeType otherIncomeType: otherIncomeTypeList){
				setOtherIncomeCategory(otherIncomeType);
			}
		return otherIncomeTypeList;
	}
	
	@Override
	public Optional<OtherIncomeType> findById(Long id) {
		Optional<OtherIncomeType> otherIncomeType = otherIncomeTypeRepository.findById(id);
		if (otherIncomeType.isPresent()) {
			return Optional.ofNullable(setOtherIncomeCategory(otherIncomeType.get()));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<OtherIncomeType> findByStatus(String status) {
		List<OtherIncomeType> otherIncomeTypeList= otherIncomeTypeRepository.findByStatus(status);
		
		for(OtherIncomeType otherIncomeType: otherIncomeTypeList){
			setOtherIncomeCategory(otherIncomeType);
		}
		return otherIncomeTypeList;
	}

	@Override
	public List<OtherIncomeType> findByName(String name) {	
		List<OtherIncomeType> otherIncomeTypeList= otherIncomeTypeRepository.findByNameContaining(name);
		
		for(OtherIncomeType otherIncomeType: otherIncomeTypeList){
			setOtherIncomeCategory(otherIncomeType);
		}
		return otherIncomeTypeList;
	}

	@Override
	public Optional<OtherIncomeType> findByCode(String code) {
		Optional<OtherIncomeType> otherIncomeType = otherIncomeTypeRepository.findByCode(code);
		if (otherIncomeType.isPresent()) {
			return Optional.ofNullable(setOtherIncomeCategory(otherIncomeType.get()));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Boolean existsById(Long id) {
		return otherIncomeTypeRepository.existsById(id);
	}

	@Override
	public Long saveAndValidateOtherIncomeType(String tenantId, String createdUser, OtherIncomeTypeAddResource otherIncomeTypeAddResource) {
		
		LoggerRequest.getInstance().logInfo("OtherIncomeType********************************Validate Code*********************************************");
		if(otherIncomeTypeRepository.existsByCodeAndStatus(otherIncomeTypeAddResource.getCode(), CommonStatus.ACTIVE.toString()))
			throw new ValidateRecordException(environment.getProperty(COMMON_UNIQUE), "code");
		
		Optional<OtherIncomeCategory> isPresentOtherIncomeCategory = otherIncomeCategoryRepository.findById(validateService.stringToLong(otherIncomeTypeAddResource.getOtherIncomeCategoryId()));		
		
		if(!isPresentOtherIncomeCategory.isPresent()) 
			throw new ValidateRecordException(environment.getProperty("common.invalid-record"), "otherIncomeCategoryId");
	
		if(!isPresentOtherIncomeCategory.get().getName().equalsIgnoreCase(otherIncomeTypeAddResource.getOtherIncomeCategoryName())) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),"otherIncomeCategoryName");    
		}
		
		LoggerRequest.getInstance().logInfo("OtherIncomeType********************************Save Other Income Type*********************************************");
		OtherIncomeType otherIncomeType = new OtherIncomeType();
		otherIncomeType.setTenantId(tenantId);
		otherIncomeType.setCode(otherIncomeTypeAddResource.getCode());
		otherIncomeType.setName(otherIncomeTypeAddResource.getName());
		otherIncomeType.setDescription(otherIncomeTypeAddResource.getDescription());
		otherIncomeType.setStatus(otherIncomeTypeAddResource.getStatus());
		otherIncomeType.setCreatedUser(createdUser);
		otherIncomeType.setCreatedDate(getCreateOrModifyDate());
		otherIncomeType.setSyncTs(validateService.getSyncTs());
		otherIncomeType.setOtherIncomeCategory(isPresentOtherIncomeCategory.get());
		otherIncomeType = otherIncomeTypeRepository.saveAndFlush(otherIncomeType);
		return otherIncomeType.getId();
	}

	@Override
	public Long updateAndValidateOtherIncomeType(String tenantId, String createdUser, Long id, OtherIncomeTypeUpdateResource otherIncomeTypeUpdateResource) {
		
		LoggerRequest.getInstance().logInfo("OtherIncomeType********************************Validate Version*********************************************");
		Optional<OtherIncomeType> isPresentOtherIncomeType = otherIncomeTypeRepository.findById(id);
		
		if(!isPresentOtherIncomeType.isPresent())
			throw new ValidateRecordException(environment.getProperty(NOT_FOUND),"message");
		
		if(!isPresentOtherIncomeType.get().getVersion().equals(Long.parseLong(otherIncomeTypeUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "version");
		
//		LoggerRequest.getInstance().logInfo("OtherIncomeType********************************Validate Code*********************************************");
//		Optional<OtherIncomeType> isPresentOtherIncomeTypeByCode = otherIncomeTypeRepository.findByCodeAndIdNotIn(otherIncomeTypeUpdateResource.getCode(), id);
//		if (isPresentOtherIncomeTypeByCode.isPresent())
//			throw new ValidateRecordException(environment.getProperty(COMMON_UNIQUE), "code");
		
		LoggerRequest.getInstance().logInfo("************************** Code Can Not Update *****************************");
        if (!isPresentOtherIncomeType.get().getCode().equals(otherIncomeTypeUpdateResource.getCode())) {
            throw new ValidateRecordException(environment.getProperty("common.code-update"), "code");
        }
		
		
		Optional<OtherIncomeCategory> isPresentOtherIncomeCategory = otherIncomeCategoryRepository.findById(validateService.stringToLong(otherIncomeTypeUpdateResource.getOtherIncomeCategoryId()));		
		
		if(!isPresentOtherIncomeCategory.isPresent()) 
			throw new ValidateRecordException(environment.getProperty("common.invalid-record"), "otherIncomeCategoryId");
	
		if(!isPresentOtherIncomeCategory.get().getName().equalsIgnoreCase(otherIncomeTypeUpdateResource.getOtherIncomeCategoryName())) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),"otherIncomeCategoryName");    
		}	
		
		LoggerRequest.getInstance().logInfo("OtherIncomeType********************************Update Other Income Type*********************************************");
		
		OtherIncomeType otherIncomeType = isPresentOtherIncomeType.get();
		otherIncomeType.setCode(otherIncomeTypeUpdateResource.getCode());
		otherIncomeType.setName(otherIncomeTypeUpdateResource.getName());
		otherIncomeType.setDescription(otherIncomeTypeUpdateResource.getDescription());
		otherIncomeType.setStatus(otherIncomeTypeUpdateResource.getStatus());
		otherIncomeType.setModifiedUser(createdUser);
		otherIncomeType.setModifiedDate(getCreateOrModifyDate());
		otherIncomeType.setSyncTs(validateService.getSyncTs());
		otherIncomeType.setOtherIncomeCategory(isPresentOtherIncomeCategory.get());
		otherIncomeType=otherIncomeTypeRepository.saveAndFlush(otherIncomeType);
		
		return otherIncomeType.getId();
	}

	
	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}
	
	@Override
	public Page<OtherIncomeType> searchOtherIncomeType(String searchq, String name, String code, Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(name==null || name.isEmpty())
			name=null;
		if(code==null || code.isEmpty())
			code=null;
		return otherIncomeTypeRepository.searchOtherIncomeType(searchq, name, code, pageable);
	}
	

	private OtherIncomeType setOtherIncomeCategory(OtherIncomeType otherIncomeType) {
		Optional<OtherIncomeCategory> otherIncomeCategoryOpt = otherIncomeCategoryRepository.findById(otherIncomeType.getOtherIncomeCategory().getId());	
		
		if(otherIncomeCategoryOpt.isPresent()) {
				otherIncomeType.setOtherIncomeCategoryId(otherIncomeCategoryOpt.get().getId());
				otherIncomeType.setOtherIncomeCategoryName(otherIncomeCategoryOpt.get().getName());
			}
		return otherIncomeType;
		
	}

	@Override
	public List<OtherIncomeType> findByOtherIncomeCategory(Long id) {
		List<OtherIncomeType> otherIncomeTypeList = new ArrayList<>();
		Optional<OtherIncomeCategory> otherIncomeCategoryOpt = otherIncomeCategoryRepository.findById(id);	
		
		if(otherIncomeCategoryOpt.isPresent()) {
			otherIncomeTypeList= otherIncomeTypeRepository.findByOtherIncomeCategory(otherIncomeCategoryOpt.get());
			
			for(OtherIncomeType otherIncomeType: otherIncomeTypeList){
				setOtherIncomeCategory(otherIncomeType);
			}
		}
		return otherIncomeTypeList;
	}
	
}
