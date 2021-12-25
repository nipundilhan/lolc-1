package com.fusionx.lending.origination.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ApprovalLevel;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ApprovalLevelRepository;
import com.fusionx.lending.origination.resource.ApprovalLevelAddResource;
import com.fusionx.lending.origination.resource.ApprovalLevelUpdateResource;
import com.fusionx.lending.origination.service.ApprovalLevelService;




@Component
@Transactional(rollbackFor = Exception.class)
public class ApprovalLevelServiceImpl extends MessagePropertyBase implements ApprovalLevelService{
	
	@Autowired
	private ApprovalLevelRepository approvalLevelRepository;
	
	@Override
	public ApprovalLevel save(String tenantId, ApprovalLevelAddResource approvalLevelAddResource) {
		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");
        
        Optional<ApprovalLevel> isPresentApprovalLevel = approvalLevelRepository.findByCode(approvalLevelAddResource.getCode());
        if (isPresentApprovalLevel.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("common.code-duplicate"), "code");
		}
        
        Optional<ApprovalLevel> isPresentApprovalLevelSequence = approvalLevelRepository.findBySequence(Long.parseLong(approvalLevelAddResource.getSequence()));
        if (isPresentApprovalLevelSequence.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("sequence-duplicate"), "sequence");
		}
        	
        ApprovalLevel approvalLevel = new ApprovalLevel();
        approvalLevel.setTenantId(tenantId);
        approvalLevel.setCode(approvalLevelAddResource.getCode());
        approvalLevel.setName(approvalLevelAddResource.getName());
        approvalLevel.setDescription(approvalLevelAddResource.getDescription());
        approvalLevel.setSequence(Long.parseLong(approvalLevelAddResource.getSequence()));
        approvalLevel.setLimitValRequired(approvalLevelAddResource.getLimitValRequired());
        approvalLevel.setStatus(approvalLevelAddResource.getStatus());
        approvalLevel.setSyncTs(currentTimestamp);
        approvalLevel.setCreatedDate(currentTimestamp);
        approvalLevel.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        approvalLevel = approvalLevelRepository.save(approvalLevel);
    	return approvalLevel;
	}

	@Override
	public Optional<ApprovalLevel> findById(Long id) {
		Optional<ApprovalLevel> isPresentApprovalLevel = approvalLevelRepository.findById(id);
		if (isPresentApprovalLevel.isPresent()) {
			return Optional.ofNullable(isPresentApprovalLevel.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<ApprovalLevel> findAll() {
		
		return 	approvalLevelRepository.findAll();
	}

	@Override
	public Optional<ApprovalLevel> findByCode(String code) {
		Optional<ApprovalLevel> isPresentApprovalLevel = approvalLevelRepository.findByCode(code);
		if (isPresentApprovalLevel.isPresent()) {
			return Optional.ofNullable(isPresentApprovalLevel.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<ApprovalLevel> findByName(String name) {
		return approvalLevelRepository.findByName(name);
	}

	@Override
	public List<ApprovalLevel> findByStatus(String status) {
		
		return 	approvalLevelRepository.findByStatus(status);
	}

	@Override
	public ApprovalLevel update(String tenantId, @Valid ApprovalLevelUpdateResource approvalLevelUpdateResource) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		if (LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");
		
		Optional<ApprovalLevel> isPresentApprovalLevel = approvalLevelRepository.findById(Long.parseLong(approvalLevelUpdateResource.getId()));
		if (!isPresentApprovalLevel.isPresent()) 
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
        
		
		if(!isPresentApprovalLevel.get().getVersion().equals(Long.parseLong(approvalLevelUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");
		
//		Optional<ApprovalLevel> isPresentApprovalLevelCode = approvalLevelRepository.findByCodeAndIdNotIn(approvalLevelUpdateResource.getCode(), Long.parseLong(approvalLevelUpdateResource.getId()));
//		if (isPresentApprovalLevelCode.isPresent())
//			throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "code");
		
		LoggerRequest.getInstance().logInfo("************************** Code Can Not Update *****************************");
        if (!isPresentApprovalLevel.get().getCode().equals(approvalLevelUpdateResource.getCode())) {
            throw new ValidateRecordException(environment.getProperty("common.code-update"), "code");
        }
		
		Optional<ApprovalLevel> isPresentApprovalLevelSequence = approvalLevelRepository.findBySequenceAndIdNotIn(Long.parseLong(approvalLevelUpdateResource.getSequence()), Long.parseLong(approvalLevelUpdateResource.getId()));
		if (isPresentApprovalLevelSequence.isPresent())
			throw new ValidateRecordException(environment.getProperty("sequence-duplicate"), "sequence");
		
	
		ApprovalLevel approvalLevel = isPresentApprovalLevel.get();
		
		approvalLevel.setTenantId(tenantId);
		approvalLevel.setCode(approvalLevelUpdateResource.getCode());
		approvalLevel.setName(approvalLevelUpdateResource.getName());
		approvalLevel.setDescription(approvalLevelUpdateResource.getDescription());
		approvalLevel.setSequence(Long.parseLong(approvalLevelUpdateResource.getSequence()));
		approvalLevel.setLimitValRequired(approvalLevelUpdateResource.getLimitValRequired());
		approvalLevel.setStatus(approvalLevelUpdateResource.getStatus());
		approvalLevel.setSyncTs(currentTimestamp);
		approvalLevel.setModifiedDate(currentTimestamp);
		approvalLevel.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		approvalLevel = approvalLevelRepository.saveAndFlush(approvalLevel);
    	return approvalLevel;
	}

}
