package com.fusionx.lending.origination.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ApprovalLevel;
import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.domain.FieldSetup;
import com.fusionx.lending.origination.domain.RiskGrading;
import com.fusionx.lending.origination.domain.RiskGradingDetail;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.FieldSetupRepository;
import com.fusionx.lending.origination.repository.RiskGradingRepository;
import com.fusionx.lending.origination.resource.ApprovalLevelUpdateResource;
import com.fusionx.lending.origination.resource.FieldSetupAddResource;
import com.fusionx.lending.origination.resource.FieldSetupUpdateResource;
import com.fusionx.lending.origination.resource.RiskGradingAddResource;
import com.fusionx.lending.origination.resource.RiskGradingDetailAddResource;
import com.fusionx.lending.origination.resource.RiskGradingDetailUpdateResource;
import com.fusionx.lending.origination.resource.RiskGradingUpdateResource;
import com.fusionx.lending.origination.service.FieldSetupService;

/**
 * Credit risk parameter template field setup Service Implementation
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *   1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class FieldSetupServiceImpl implements FieldSetupService{

	@Autowired
	private FieldSetupRepository fieldSetupRepository;
	
	@Autowired
	private Environment environment;
	
	@Override
	public List<FieldSetup> findAll() {
		return fieldSetupRepository.findAll();
	}
	
	@Override
	public Optional<FieldSetup> findById(Long id) {
		Optional<FieldSetup> isPresentFieldSetup = fieldSetupRepository.findById(id);
		if (isPresentFieldSetup.isPresent()) {
			return Optional.ofNullable(isPresentFieldSetup.get());
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public List<FieldSetup> findByStatus(String status) {
		return fieldSetupRepository.findByStatus(status);
	}
	
	@Override
	public List<FieldSetup> findByName(String name) {
		return fieldSetupRepository.findByFieldNameContaining(name);
	}
	
	@Override
	public Optional<FieldSetup> findByCode(String fieldId) {
		Optional<FieldSetup> fieldSetup = fieldSetupRepository.findByFieldId(fieldId);
		if (fieldSetup.isPresent()) {
			return Optional.ofNullable(fieldSetup.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Long save(String tenantId, String createdUser, FieldSetupAddResource fieldSetupAddResource) {
			Calendar calendar = Calendar.getInstance();
	        java.util.Date now = calendar.getTime();
	        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
	        
			LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Name*******************");
			if(fieldSetupRepository.existsByFieldName(fieldSetupAddResource.getFieldName()))
				throw new ValidateRecordException(environment.getProperty("common.unique"), "name");
			
			LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate field id*******************");
			if(fieldSetupRepository.existsByFieldId(fieldSetupAddResource.getFieldId()))
				throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
			
			LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Dupilcates*******************");
			if(fieldSetupRepository.existsByFieldIdAndFieldName(fieldSetupAddResource.getFieldId(), fieldSetupAddResource.getFieldName()))
				throw new ValidateRecordException(environment.getProperty("typeAndProcess.unique"), "message");
			
			LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Save risk grading*******************");
			FieldSetup fieldSetup = new FieldSetup();
			
			fieldSetup.setFieldId(fieldSetupAddResource.getFieldId());
			fieldSetup.setFieldName(fieldSetupAddResource.getFieldName());
			fieldSetup.setDescription(fieldSetupAddResource.getDescription());
			fieldSetup.setStatus(fieldSetupAddResource.getStatus());
			fieldSetup.setSyncTs(currentTimestamp);
			fieldSetup.setCreatedDate(currentTimestamp);
			fieldSetup.setCreatedUser(createdUser);
			fieldSetup.setTenantId(tenantId);
			fieldSetup = fieldSetupRepository.save(fieldSetup);
			
			return fieldSetup.getId();
	}
	
	@Override
	public Long update(String tenantId, String createdUser, Long id,FieldSetupUpdateResource fieldSetupUpdateResource) {
		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        Optional<FieldSetup> isExists = fieldSetupRepository.findById(id);
        
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate version*******************");
		Optional<FieldSetup> isPresentFieldSetup = fieldSetupRepository.findById(id);
		if(!isPresentFieldSetup.get().getVersion().equals(Long.parseLong(fieldSetupUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate name*******************");
		Optional<FieldSetup> isPresentFieldSetupByName = fieldSetupRepository.findByFieldNameAndIdNotIn(fieldSetupUpdateResource.getFieldName(), id);
		if (isPresentFieldSetupByName.isPresent())
			throw new ValidateRecordException(environment.getProperty("common.unique"), "name");
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate code*******************");
		Optional<FieldSetup> isPresentFieldSetupByCode = fieldSetupRepository.findByFieldIdAndIdNotIn(fieldSetupUpdateResource.getFieldId(), id);
		if (isPresentFieldSetupByCode.isPresent())
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate dusplicates*******************");
		Optional<FieldSetup> isPresentFieldSetupByType = fieldSetupRepository.findByFieldIdAndFieldNameAndIdNotIn(fieldSetupUpdateResource.getFieldId(), fieldSetupUpdateResource.getFieldName(), id);
		if (isPresentFieldSetupByType.isPresent())
			throw new ValidateRecordException(environment.getProperty("typeAndProcess.unique"), "message");
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Update Risk grading*******************");
		
			FieldSetup fieldSetup = isExists.get();
			
			fieldSetup.setFieldId(fieldSetupUpdateResource.getFieldId());
			fieldSetup.setFieldName(fieldSetupUpdateResource.getFieldName());
			fieldSetup.setDescription(fieldSetupUpdateResource.getDescription());
			fieldSetup.setStatus(fieldSetupUpdateResource.getStatus());
			fieldSetup.setVersion(Long.parseLong(fieldSetupUpdateResource.getVersion()));
			fieldSetup.setSyncTs(currentTimestamp);
			fieldSetup.setModifiedDate(currentTimestamp);
			fieldSetup.setModifiedUser(createdUser);
			fieldSetup.setTenantId(tenantId);
			fieldSetup = fieldSetupRepository.save(fieldSetup);
			
			return fieldSetup.getId();
	}
	
	@Override
	public Boolean existsById(Long id) {
		return fieldSetupRepository.existsById(id);
	}
}
