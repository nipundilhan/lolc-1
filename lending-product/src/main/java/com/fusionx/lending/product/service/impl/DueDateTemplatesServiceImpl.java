package com.fusionx.lending.product.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.DueDateDetails;
import com.fusionx.lending.product.domain.DueDateTemplates;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.DueDateType;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.DueDateDetailsRepository;
import com.fusionx.lending.product.repository.DueDateTemplatesRepository;
import com.fusionx.lending.product.resources.DueDateTemplatesAddResource;
import com.fusionx.lending.product.resources.DueDateTemplatesUpdateResource;
import com.fusionx.lending.product.resources.DueDateTypeListResource;
import com.fusionx.lending.product.service.DueDateDetailsHistoryService;
import com.fusionx.lending.product.service.DueDateTemplatesHistoryService;
import com.fusionx.lending.product.service.DueDateTemplatesService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * Due Date Templates Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-09-2021    FXL-139  	 FXL-926	Piyumi       Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class DueDateTemplatesServiceImpl extends MessagePropertyBase implements DueDateTemplatesService {

	@Autowired
	private ValidationService validationService;
	
	@Autowired
	private DueDateTemplatesRepository dueDateTemplatesRepository;
	
	@Autowired
	private DueDateDetailsRepository dueDateDetailsRepository;
	
	@Autowired
	private DueDateTemplatesHistoryService dueDateTemplatesHistoryService;
	
	@Autowired
	private DueDateDetailsHistoryService dueDateDetailsHistoryService;


	@Override
	public Optional<DueDateTemplates> findById(Long id) {
		Optional<DueDateTemplates> isPresentDueDateTemplates = dueDateTemplatesRepository.findById(id);
		
		if (isPresentDueDateTemplates.isPresent()) {
			return Optional.of(setDueDateTemplatesDetails(isPresentDueDateTemplates.get()));
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<DueDateTemplates> findAll() {
		List<DueDateTemplates> dueDateTemplatesList = dueDateTemplatesRepository.findAll();
		
		for (DueDateTemplates dueDateTemplate : dueDateTemplatesList) {
			setDueDateTemplatesDetails(dueDateTemplate);
		}
		return dueDateTemplatesList;
	}

	@Override
	public List<DueDateTemplates> findByStatus(String status) {
		List<DueDateTemplates> dueDateTemplatesList = dueDateTemplatesRepository.findByStatus(CommonStatus.valueOf(status));
		
		for (DueDateTemplates dueDateTemplate : dueDateTemplatesList) {
			setDueDateTemplatesDetails(dueDateTemplate);
		}
		return dueDateTemplatesList;
	}

	@Override
	public Optional<DueDateTemplates> findByCode(String code) {
		Optional<DueDateTemplates> isPresentDueDateTemplates = dueDateTemplatesRepository.findByCode(code);
		
		if (isPresentDueDateTemplates.isPresent()) {
			return Optional.of(setDueDateTemplatesDetails(isPresentDueDateTemplates.get()));
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<DueDateTemplates> findByNameContaining(String name) {
		List<DueDateTemplates> dueDateTemplatesList = dueDateTemplatesRepository.findByNameContaining(name);
		
		for (DueDateTemplates dueDateTemplate : dueDateTemplatesList) {
			setDueDateTemplatesDetails(dueDateTemplate);
		}
		return dueDateTemplatesList;
	}

	@Override
	public List<DueDateTemplates> findByEffectiveDate(String effectiveDate) {
		String fromDateStr = effectiveDate + " 00:00:00";
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
		Date fromDate = null;
		Date toDate = null; 				
		try {			
			fromDate = formatter.parse(fromDateStr);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fromDate);
			calendar.add(Calendar.DATE, 1);	
			toDate  = calendar.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		
		
		List<DueDateTemplates> dueDateTemplatesList = dueDateTemplatesRepository.findByEffectiveDateBetween(fromDate , toDate);
		
		for (DueDateTemplates dueDateTemplate : dueDateTemplatesList) {
			setDueDateTemplatesDetails(dueDateTemplate);
		}
		return dueDateTemplatesList;
	}

	@Override
	public DueDateTemplates save(String tenantId, DueDateTemplatesAddResource dueDateTemplatesAddResource) {
		
	if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
	        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");
	
	Optional<DueDateTemplates> isPresentDueDateTemplates = dueDateTemplatesRepository.findByCode(dueDateTemplatesAddResource.getCode());
	
	if(isPresentDueDateTemplates.isPresent())
		throw new ValidateRecordException(getEnvironment().getProperty(COMMON_DUPLICATE), "code");
	
		DueDateTemplates dueDateTemplates =  new DueDateTemplates();
		dueDateTemplates.setTenantId(tenantId);
		dueDateTemplates.setCode(dueDateTemplatesAddResource.getCode());
		dueDateTemplates.setName(dueDateTemplatesAddResource.getName());
		dueDateTemplates.setEffectiveDate(new Timestamp(validationService.formatDate(dueDateTemplatesAddResource.getEffectiveDate()).getTime()));
		dueDateTemplates.setStatus(CommonStatus.valueOf(dueDateTemplatesAddResource.getStatus()));
		dueDateTemplates.setDueDateType(DueDateType.valueOf(dueDateTemplatesAddResource.getDueDateType()));
		dueDateTemplates.setCreatedDate(validationService.getCreateOrModifyDate());
		dueDateTemplates.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		dueDateTemplates.setSyncTs(validationService.getSyncTs());
		dueDateTemplates = dueDateTemplatesRepository.saveAndFlush(dueDateTemplates);
		
		if(dueDateTemplatesAddResource.getDueDateTypeList() != null && !dueDateTemplatesAddResource.getDueDateTypeList().isEmpty()) {
			
			for(DueDateTypeListResource dueDateTypeListResource : dueDateTemplatesAddResource.getDueDateTypeList()) {
				
				DueDateDetails dueDateDetails = new DueDateDetails();
				dueDateDetails.setTenantId(tenantId);
				dueDateDetails.setDueDateTmp(dueDateTemplates);
				dueDateDetails.setSequence(dueDateTypeListResource.getSequence() != null ? validationService.stringToLong(dueDateTypeListResource.getSequence()):null);
				dueDateDetails.setDueDate(new Timestamp(validationService.formatDate(dueDateTypeListResource.getDueDate()).getTime()));
				dueDateDetails.setStatus(CommonStatus.valueOf(dueDateTypeListResource.getStatus()));
				dueDateDetails.setCreatedDate(validationService.getCreateOrModifyDate());
				dueDateDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
				dueDateDetails.setSyncTs(validationService.getSyncTs());
				dueDateDetailsRepository.saveAndFlush(dueDateDetails);
			}
			
		}else {
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "dueDateTypeList");
		}
		
		return dueDateTemplates;
		 
	}

	@Override
	public DueDateTemplates update(String tenantId, Long id,
			DueDateTemplatesUpdateResource dueDateTemplatesUpdateResource) {
		
		if (LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");
		
		Optional<DueDateTemplates> isPresentDueDateTemplates = dueDateTemplatesRepository.findById(id);
		if (!isPresentDueDateTemplates.isPresent()) 
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
		
		if(!isPresentDueDateTemplates.get().getVersion().toString().equals(dueDateTemplatesUpdateResource.getVersion()))
			throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");
		
		Optional<DueDateTemplates> isDuplicateDueDateTemplates = dueDateTemplatesRepository.findByCodeAndIdNotIn(dueDateTemplatesUpdateResource.getCode(),isPresentDueDateTemplates.get().getId());
		
		if(isDuplicateDueDateTemplates.isPresent())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_DUPLICATE), "code");
		
		DueDateTemplates dueDateTemplates =  isPresentDueDateTemplates.get();
		dueDateTemplatesHistoryService.saveHistory(tenantId, dueDateTemplates, LogginAuthentcation.getInstance().getUserName());
		
		dueDateTemplates.setTenantId(tenantId);
		dueDateTemplates.setCode(dueDateTemplatesUpdateResource.getCode());
		dueDateTemplates.setName(dueDateTemplatesUpdateResource.getName());
		dueDateTemplates.setEffectiveDate(new Timestamp(validationService.formatDate(dueDateTemplatesUpdateResource.getEffectiveDate()).getTime()));
		dueDateTemplates.setStatus(CommonStatus.valueOf(dueDateTemplatesUpdateResource.getStatus()));
		dueDateTemplates.setDueDateType(DueDateType.valueOf(dueDateTemplatesUpdateResource.getDueDateType()));
		dueDateTemplates.setModifiedDate(validationService.getCreateOrModifyDate());
		dueDateTemplates.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		dueDateTemplates.setSyncTs(validationService.getSyncTs());
		dueDateTemplates = dueDateTemplatesRepository.saveAndFlush(dueDateTemplates);
		
		if(dueDateTemplatesUpdateResource.getDueDateTypeList() != null && !dueDateTemplatesUpdateResource.getDueDateTypeList().isEmpty()) {
			Integer index = 0;
			for(DueDateTypeListResource dueDateTypeListResource : dueDateTemplatesUpdateResource.getDueDateTypeList()) {
				
				if(dueDateTypeListResource.getId() != null) {
					
					Optional<DueDateDetails> isPresentDueDateDetails = dueDateDetailsRepository.findById(validationService.stringToLong(dueDateTypeListResource.getId()));
					if (!isPresentDueDateDetails.isPresent()) 
						throw new InvalidServiceIdException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.ID, EntityPoint.DUE_DATE_DETAILS);
					
					if (!isPresentDueDateDetails.get().getDueDateTmp().getId().equals(dueDateTemplates.getId())) {
						throw new InvalidServiceIdException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.ID, EntityPoint.DUE_DATE_DETAILS);
					}
					
					if(!isPresentDueDateDetails.get().getVersion().toString().equals(dueDateTypeListResource.getVersion()))
						throw new InvalidServiceIdException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.VERSION, EntityPoint.DUE_DATE_DETAILS);
					
					DueDateDetails dueDateDetails = isPresentDueDateDetails.get();
					dueDateDetailsHistoryService.saveHistory(tenantId, dueDateDetails, LogginAuthentcation.getInstance().getUserName());
					
					dueDateDetails.setTenantId(tenantId);
					dueDateDetails.setSequence(dueDateTypeListResource.getSequence() != null ? validationService.stringToLong(dueDateTypeListResource.getSequence()):null);
					dueDateDetails.setDueDate(new Timestamp(validationService.formatDate(dueDateTypeListResource.getDueDate()).getTime()));
					dueDateDetails.setStatus(CommonStatus.valueOf(dueDateTypeListResource.getStatus()));
					dueDateDetails.setModifiedDate(validationService.getCreateOrModifyDate());
					dueDateDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
					dueDateDetails.setSyncTs(validationService.getSyncTs());
					dueDateDetailsRepository.saveAndFlush(dueDateDetails);
				
				}else {
					
					DueDateDetails dueDateDetails = new DueDateDetails();
					dueDateDetails.setTenantId(tenantId);
					dueDateDetails.setDueDateTmp(dueDateTemplates);
					dueDateDetails.setSequence(dueDateTypeListResource.getSequence() != null ? validationService.stringToLong(dueDateTypeListResource.getSequence()):null);
					dueDateDetails.setDueDate(new Timestamp(validationService.formatDate(dueDateTypeListResource.getDueDate()).getTime()));
					dueDateDetails.setStatus(CommonStatus.valueOf(dueDateTypeListResource.getStatus()));
					dueDateDetails.setCreatedDate(validationService.getCreateOrModifyDate());
					dueDateDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
					dueDateDetails.setSyncTs(validationService.getSyncTs());
					dueDateDetailsRepository.saveAndFlush(dueDateDetails);
				}
				
				index++;
			}
		}
		
		
		return dueDateTemplates;
	}
	
	private DueDateTemplates setDueDateTemplatesDetails(DueDateTemplates dueDateTemplates){		
		List<DueDateDetails> dueDateDetails = dueDateDetailsRepository.findByStatus(CommonStatus.ACTIVE);
		dueDateTemplates.setDueDateDetails(dueDateDetails);
		return dueDateTemplates;
		
	}

	
}
