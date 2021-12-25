package com.fusionx.lending.origination.service.impl;

/**
 * 	Exception Approval Group Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-08-2021   FXL-632  	 FXL-564       Piyumi     Created
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
import com.fusionx.lending.origination.domain.ExceptionApprovalGroup;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ExceptionApprovalGroupRepository;
import com.fusionx.lending.origination.resource.ExceptionApprovalGroupAddResource;
import com.fusionx.lending.origination.resource.ExceptionApprovalGroupUpdateResource;
import com.fusionx.lending.origination.service.ExceptionApprovalGroupService;
import com.fusionx.lending.origination.service.ValidateService;




@Component
@Transactional(rollbackFor = Exception.class)
public class ExceptionApprovalGroupServiceImpl extends MessagePropertyBase implements ExceptionApprovalGroupService{
	
	
	private ExceptionApprovalGroupRepository exceptionApprovalGroupRepository;
	
	@Autowired
	public void setExceptionApprovalGroupRepository(ExceptionApprovalGroupRepository exceptionApprovalGroupRepository) {
		this.exceptionApprovalGroupRepository = exceptionApprovalGroupRepository;
	}
	
	private ValidateService validateService;
	
	@Autowired
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	@Override
	public ExceptionApprovalGroup save(String tenantId, ExceptionApprovalGroupAddResource exceptionApprovalGroupAddResource) {
        
        if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");
        
        Optional<ExceptionApprovalGroup> isPresentExceptionApprovalGroup = exceptionApprovalGroupRepository.findByCode(exceptionApprovalGroupAddResource.getCode());
        if (isPresentExceptionApprovalGroup.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "code");
		}
        
        	
        ExceptionApprovalGroup exceptionApprovalGroup = new ExceptionApprovalGroup();
        exceptionApprovalGroup.setTenantId(tenantId);
        exceptionApprovalGroup.setCode(exceptionApprovalGroupAddResource.getCode());
        exceptionApprovalGroup.setName(exceptionApprovalGroupAddResource.getName());
        exceptionApprovalGroup.setDescription(exceptionApprovalGroupAddResource.getDescription());
        exceptionApprovalGroup.setStatus(CommonStatus.valueOf(exceptionApprovalGroupAddResource.getStatus()));
        exceptionApprovalGroup.setSyncTs(validateService.getSyncTs());
        exceptionApprovalGroup.setCreatedDate(validateService.getCreateOrModifyDate());
        exceptionApprovalGroup.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        exceptionApprovalGroup = exceptionApprovalGroupRepository.save(exceptionApprovalGroup);
    	return exceptionApprovalGroup;
	}

	@Override
	public Optional<ExceptionApprovalGroup> findById(Long id) {
		Optional<ExceptionApprovalGroup> isPresentExceptionApprovalGroup = exceptionApprovalGroupRepository.findById(id);
		if (isPresentExceptionApprovalGroup.isPresent()) {
			return Optional.ofNullable(isPresentExceptionApprovalGroup.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<ExceptionApprovalGroup> findAll() {
		return 	exceptionApprovalGroupRepository.findAll();
	}

	@Override
	public Optional<ExceptionApprovalGroup> findByCode(String code) {
		Optional<ExceptionApprovalGroup> isPresentExceptionApprovalGroup = exceptionApprovalGroupRepository.findByCode(code);
		if (isPresentExceptionApprovalGroup.isPresent()) {
			return Optional.ofNullable(isPresentExceptionApprovalGroup.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<ExceptionApprovalGroup> findByName(String name) {
		return exceptionApprovalGroupRepository.findByNameContaining(name);
	}

	@Override
	public List<ExceptionApprovalGroup> findByStatus(String status) {
		return 	exceptionApprovalGroupRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public ExceptionApprovalGroup update(String tenantId, Long id , ExceptionApprovalGroupUpdateResource exceptionApprovalGroupUpdateResource) {
		
		if (LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");
		
		Optional<ExceptionApprovalGroup> isPresentExceptionApprovalGroup = exceptionApprovalGroupRepository.findById(id);
		if (!isPresentExceptionApprovalGroup.isPresent()) 
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "exceptionApprovalGroupId");
        
		
		if(!isPresentExceptionApprovalGroup.get().getVersion().equals(Long.parseLong(exceptionApprovalGroupUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");
		
		Optional<ExceptionApprovalGroup> isPresentExceptionApprovalGroupCode = exceptionApprovalGroupRepository.findByCodeAndIdNotIn(exceptionApprovalGroupUpdateResource.getCode(), id);
		if (isPresentExceptionApprovalGroupCode.isPresent())
			throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "code");
		
		
		ExceptionApprovalGroup exceptionApprovalGroup = isPresentExceptionApprovalGroup.get();
		
		exceptionApprovalGroup.setTenantId(tenantId);
		exceptionApprovalGroup.setCode(exceptionApprovalGroupUpdateResource.getCode());
		exceptionApprovalGroup.setName(exceptionApprovalGroupUpdateResource.getName());
		exceptionApprovalGroup.setDescription(exceptionApprovalGroupUpdateResource.getDescription());
		exceptionApprovalGroup.setStatus(CommonStatus.valueOf(exceptionApprovalGroupUpdateResource.getStatus()));
		exceptionApprovalGroup.setSyncTs(validateService.getSyncTs());
		exceptionApprovalGroup.setModifiedDate(validateService.getCreateOrModifyDate());
		exceptionApprovalGroup.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		exceptionApprovalGroup = exceptionApprovalGroupRepository.saveAndFlush(exceptionApprovalGroup);
    	return exceptionApprovalGroup;
	}

	@Override
	public Page<ExceptionApprovalGroup> searchExceptionApprovalGroup(String name, String code, String status,Pageable pageable) {		
		if(name==null || name.isEmpty())
			name=null;
		if(code==null || code.isEmpty())
			code=null;
		if(status==null || status.isEmpty())
			status=null;
		
		return exceptionApprovalGroupRepository.searchExceptionApprovalGroup(name, code, status, pageable);
	}

}
