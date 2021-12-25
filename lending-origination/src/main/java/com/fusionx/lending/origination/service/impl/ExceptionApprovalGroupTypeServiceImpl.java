package com.fusionx.lending.origination.service.impl;
/**
 * Exception Approval Group Type Service impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1  23-08-2021    FXL-632   	 FX-586		Piyumi      Created
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
import com.fusionx.lending.origination.domain.ExceptionType;
import com.fusionx.lending.origination.domain.ExceptionApprovalGroup;
import com.fusionx.lending.origination.domain.ExceptionApprovalGroupType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ExceptionApprovalGroupRepository;
import com.fusionx.lending.origination.repository.ExceptionApprovalGroupTypeRepository;
import com.fusionx.lending.origination.repository.ExceptionTypeRepository;
import com.fusionx.lending.origination.resource.ExceptionApprovalGroupTypeAddResource;
import com.fusionx.lending.origination.resource.ExceptionApprovalGroupTypeUpdateResource;
import com.fusionx.lending.origination.service.ExceptionApprovalGroupTypeService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor=Exception.class)
public class ExceptionApprovalGroupTypeServiceImpl extends MessagePropertyBase implements ExceptionApprovalGroupTypeService {

	private ExceptionApprovalGroupTypeRepository exceptionApprovalGroupTypeRepository;
	
	@Autowired
	public void setExceptionApprovalGroupTypeRepository(ExceptionApprovalGroupTypeRepository exceptionApprovalGroupTypeRepository) {
		this.exceptionApprovalGroupTypeRepository = exceptionApprovalGroupTypeRepository;
	}
	
	private ValidateService validateService;
	
	@Autowired
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	private ExceptionTypeRepository exceptionTypeRepository;
	
	@Autowired
	public void setExceptionTypeRepository(ExceptionTypeRepository exceptionTypeRepository) {
		this.exceptionTypeRepository = exceptionTypeRepository;
	}
	
	private ExceptionApprovalGroupRepository exceptionApprovalGroupRepository;
	
	@Autowired
	public void setExceptionApprovalGroupRepository(ExceptionApprovalGroupRepository exceptionApprovalGroupRepository) {
		this.exceptionApprovalGroupRepository = exceptionApprovalGroupRepository;
	}


	@Override
	public List<ExceptionApprovalGroupType> findAll() {
		List<ExceptionApprovalGroupType> exceptionApprovalGroupTypeList = exceptionApprovalGroupTypeRepository.findAll();
	
		for (ExceptionApprovalGroupType exceptionApprovalGroupType : exceptionApprovalGroupTypeList) {
			setExceptionApprovalGroupTypeDetails(exceptionApprovalGroupType);
		}
		return exceptionApprovalGroupTypeList;
	}

	@Override
	public Optional<ExceptionApprovalGroupType> findById(Long id) {
		Optional<ExceptionApprovalGroupType> isPresentExceptionApprovalGroupType = exceptionApprovalGroupTypeRepository.findById(id);
		if (isPresentExceptionApprovalGroupType.isPresent()) {			
			return Optional.ofNullable(setExceptionApprovalGroupTypeDetails(isPresentExceptionApprovalGroupType.get()));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<ExceptionApprovalGroupType> findByStatus(String status) {
		List<ExceptionApprovalGroupType> exceptionApprovalGroupTypeList = exceptionApprovalGroupTypeRepository.findByStatus(CommonStatus.valueOf(status));
		
		for (ExceptionApprovalGroupType exceptionApprovalGroupType : exceptionApprovalGroupTypeList) {
			setExceptionApprovalGroupTypeDetails(exceptionApprovalGroupType);
		}
		return exceptionApprovalGroupTypeList;
	}

	@Override
	public ExceptionApprovalGroupType addExceptionApprovalGroupType(String tenantId, 
			ExceptionApprovalGroupTypeAddResource exceptionApprovalGroupTypeAddResource) {
        
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);

		Optional<ExceptionApprovalGroup> isPresentExceptionApprovalGroup = exceptionApprovalGroupRepository.findById(validateService.stringToLong(exceptionApprovalGroupTypeAddResource.getExceptionApprovalGroupId()));
		
		if(!isPresentExceptionApprovalGroup.isPresent())
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND),"exceptionApprovalGroupId");
		
		if(!exceptionApprovalGroupTypeAddResource.getStatus().equals(CommonStatus.ACTIVE.toString()))
			throw new ValidateRecordException(environment.getProperty("active-status"), "status");
		
		ExceptionType exceptionType = validateExceptionType(validateService.stringToLong(exceptionApprovalGroupTypeAddResource.getExceptionTypeId()), exceptionApprovalGroupTypeAddResource.getExceptionTypeName());
		
		Optional<ExceptionApprovalGroupType> isPresentExceptionApprovalGroupType = exceptionApprovalGroupTypeRepository.findByExceptionApprovalGroupIdAndExceptionTypeId(validateService.stringToLong(exceptionApprovalGroupTypeAddResource.getExceptionApprovalGroupId()), exceptionType.getId());
		
		if(isPresentExceptionApprovalGroupType.isPresent())
			throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "exceptionTypeId");
		
		ExceptionApprovalGroupType exceptionApprovalGroupType = new ExceptionApprovalGroupType();
		exceptionApprovalGroupType.setTenantId(tenantId);
		exceptionApprovalGroupType.setExceptionApprovalGroup(isPresentExceptionApprovalGroup.get());
		exceptionApprovalGroupType.setExceptionType(exceptionType);
		exceptionApprovalGroupType.setStatus(CommonStatus.valueOf(exceptionApprovalGroupTypeAddResource.getStatus()));
		exceptionApprovalGroupType.setCreatedDate(validateService.getCreateOrModifyDate());
		exceptionApprovalGroupType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		exceptionApprovalGroupType.setSyncTs(validateService.getSyncTs());		
		exceptionApprovalGroupType = exceptionApprovalGroupTypeRepository.saveAndFlush(exceptionApprovalGroupType);
		
		return exceptionApprovalGroupType;
	}

	@Override
	public ExceptionApprovalGroupType updateExceptionApprovalGroupType(String tenantId, Long id,
			ExceptionApprovalGroupTypeUpdateResource exceptionApprovalGroupTypeUpdateResource) {
		
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		Optional<ExceptionApprovalGroupType> isPresentExceptionApprovalGroupType = exceptionApprovalGroupTypeRepository.findById(id);
		
		if(!isPresentExceptionApprovalGroupType.isPresent())
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
		
		if(!isPresentExceptionApprovalGroupType.get().getVersion().equals(Long.parseLong(exceptionApprovalGroupTypeUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
		
		ExceptionApprovalGroupType exceptionApprovalGroupType = isPresentExceptionApprovalGroupType.get();
		exceptionApprovalGroupType.setStatus(CommonStatus.valueOf(exceptionApprovalGroupTypeUpdateResource.getStatus()));
		exceptionApprovalGroupType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		exceptionApprovalGroupType.setModifiedDate(validateService.getCreateOrModifyDate());
		exceptionApprovalGroupType.setSyncTs(validateService.getCreateOrModifyDate());
		exceptionApprovalGroupType = exceptionApprovalGroupTypeRepository.saveAndFlush(exceptionApprovalGroupType);
		
		return exceptionApprovalGroupType;
	}
	
	private ExceptionType validateExceptionType(Long id, String name) {
		
		Optional<ExceptionType> isPresentExceptionType = exceptionTypeRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE);
		
		if(!isPresentExceptionType.isPresent())
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND),"exceptionTypeId");
		
		return isPresentExceptionType.get();
	}
	
	private ExceptionApprovalGroupType setExceptionApprovalGroupTypeDetails(ExceptionApprovalGroupType exceptionApprovalGroupType) {	
		
		Optional<ExceptionType> exceptionType = exceptionTypeRepository.findById(exceptionApprovalGroupType.getExceptionType().getId());
		if(exceptionType.isPresent()) {
			exceptionApprovalGroupType.setExceptionTypesId(exceptionType.get().getId());
			exceptionApprovalGroupType.setExceptionTypesName(exceptionType.get().getName());
		}			
		return exceptionApprovalGroupType;
		
	}


	@Override
	public List<ExceptionApprovalGroupType> findByExceptionApprovalGroupId(Long exceptionApprovalGroupId) {
		List<ExceptionApprovalGroupType> exceptionApprovalGroupTypeList = exceptionApprovalGroupTypeRepository.findByExceptionApprovalGroupId(exceptionApprovalGroupId);
		
		for (ExceptionApprovalGroupType exceptionApprovalGroupType : exceptionApprovalGroupTypeList) {
			setExceptionApprovalGroupTypeDetails(exceptionApprovalGroupType);
		}
		return exceptionApprovalGroupTypeList;
	}
}
