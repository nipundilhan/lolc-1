package com.fusionx.lending.origination.service.impl;

/**
 * Exception Type Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-08-2021   FXL-627  	 FXL-563       Piyumi     Created
 *    
 ********************************************************************************************************
*/
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ExceptionType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ExceptionTypeRepository;
import com.fusionx.lending.origination.resource.CommonAddResource;
import com.fusionx.lending.origination.resource.CommonUpdateResource;
import com.fusionx.lending.origination.service.ExceptionTypeService;
import com.fusionx.lending.origination.service.ValidateService;




@Component
@Transactional(rollbackFor = Exception.class)
public class ExceptionTypeServiceImpl extends MessagePropertyBase implements ExceptionTypeService{
	
	
	private ExceptionTypeRepository exceptionTypeRepository;
	
	@Autowired
	public void setExceptionTypeRepository(ExceptionTypeRepository exceptionTypeRepository) {
		this.exceptionTypeRepository = exceptionTypeRepository;
	}
	
	private ValidateService validateService;
	
	@Autowired
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	@Override
	public ExceptionType save(String tenantId, CommonAddResource commonAddResource) {
        
        if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");
        
        Optional<ExceptionType> isPresentExceptionType = exceptionTypeRepository.findByCode(commonAddResource.getCode());
        if (isPresentExceptionType.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "code");
		}
        
        	
        ExceptionType exceptionType = new ExceptionType();
        exceptionType.setTenantId(tenantId);
        exceptionType.setCode(commonAddResource.getCode());
        exceptionType.setName(commonAddResource.getName());
        exceptionType.setDescription(commonAddResource.getDescription());
        exceptionType.setStatus(CommonStatus.valueOf(commonAddResource.getStatus()));
        exceptionType.setSyncTs(validateService.getSyncTs());
        exceptionType.setCreatedDate(validateService.getCreateOrModifyDate());
        exceptionType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        exceptionType = exceptionTypeRepository.save(exceptionType);
    	return exceptionType;
	}

	@Override
	public Optional<ExceptionType> findById(Long id) {
		Optional<ExceptionType> isPresentExceptionType = exceptionTypeRepository.findById(id);
		if (isPresentExceptionType.isPresent()) {
			return Optional.ofNullable(isPresentExceptionType.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<ExceptionType> findAll() {
		return 	exceptionTypeRepository.findAll();
	}

	@Override
	public Optional<ExceptionType> findByCode(String code) {
		Optional<ExceptionType> isPresentExceptionType = exceptionTypeRepository.findByCode(code);
		if (isPresentExceptionType.isPresent()) {
			return Optional.ofNullable(isPresentExceptionType.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<ExceptionType> findByName(String name) {
		return exceptionTypeRepository.findByNameContaining(name);
	}

	@Override
	public List<ExceptionType> findByStatus(String status) {
		return 	exceptionTypeRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public ExceptionType update(String tenantId, Long id , CommonUpdateResource commonUpdateResource) {
		
		if (LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");
		
		Optional<ExceptionType> isPresentExceptionType = exceptionTypeRepository.findById(id);
		if (!isPresentExceptionType.isPresent()) 
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
        
		
		if(!isPresentExceptionType.get().getVersion().equals(Long.parseLong(commonUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");
		
		Optional<ExceptionType> isPresentExceptionTypeCode = exceptionTypeRepository.findByCodeAndIdNotIn(commonUpdateResource.getCode(), id);
		if (isPresentExceptionTypeCode.isPresent())
			throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "code");
		
		
		ExceptionType exceptionType = isPresentExceptionType.get();
		
		exceptionType.setTenantId(tenantId);
		exceptionType.setCode(commonUpdateResource.getCode());
		exceptionType.setName(commonUpdateResource.getName());
		exceptionType.setDescription(commonUpdateResource.getDescription());
		exceptionType.setStatus(CommonStatus.valueOf(commonUpdateResource.getStatus()));
		exceptionType.setSyncTs(validateService.getSyncTs());
		exceptionType.setModifiedDate(validateService.getCreateOrModifyDate());
		exceptionType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		exceptionType = exceptionTypeRepository.saveAndFlush(exceptionType);
    	return exceptionType;
	}

	@Override
	public Page<ExceptionType> searchExceptionType(String name, String code, Pageable pageable) {		
		if(name==null || name.isEmpty())
			name=null;
		if(code==null || code.isEmpty())
			code=null;
		
		return exceptionTypeRepository.searchExceptionType(name, code, pageable);
	}

}
