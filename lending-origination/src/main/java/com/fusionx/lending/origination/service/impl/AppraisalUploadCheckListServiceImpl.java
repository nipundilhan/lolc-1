package com.fusionx.lending.origination.service.impl;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fusionx.lending.origination.repository.AppraisalUploadCheckListRepository;
import com.fusionx.lending.origination.repository.BusinessTypeRepository;
import com.fusionx.lending.origination.repository.CheckListTemplateRepository;
import com.fusionx.lending.origination.repository.RiskGradingDetailRepository;
import com.fusionx.lending.origination.repository.RiskGradingRepository;
import com.fusionx.lending.origination.resource.AppraisalUploadCheckListAddResource;
import com.fusionx.lending.origination.resource.AppraisalUploadCheckListUpdateResource;
import com.fusionx.lending.origination.resource.BusinessPersonType;
import com.fusionx.lending.origination.resource.DocumentMaintenanceResource;
import com.fusionx.lending.origination.resource.IndustryType;
import com.fusionx.lending.origination.resource.RiskGradingAddResource;
import com.fusionx.lending.origination.resource.RiskGradingDetailAddResource;
import com.fusionx.lending.origination.resource.RiskGradingDetailUpdateResource;
import com.fusionx.lending.origination.resource.RiskGradingUpdateResource;
import com.fusionx.lending.origination.service.AppraisalUploadCheckListService;
import com.fusionx.lending.origination.service.RiskGradingService;
import com.fusionx.lending.origination.service.ValidateService;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.AppraisalUploadCheckList;
import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.domain.CheckListTemplate;
import com.fusionx.lending.origination.domain.FieldSetup;
import com.fusionx.lending.origination.domain.RiskGrading;
import com.fusionx.lending.origination.domain.RiskGradingDetail;
import com.fusionx.lending.origination.enums.ActionType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.DetailValidateException;
import com.fusionx.lending.origination.exception.InvalidDetailListServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.enums.TransferType;


/**
 * Appraisal Upload Check List Service Implementation
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *   1   26-09-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class AppraisalUploadCheckListServiceImpl implements AppraisalUploadCheckListService{ 
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private AppraisalUploadCheckListRepository appraisalUploadCheckListRepository;
	
	@Autowired
	private CheckListTemplateRepository checkListTemplateRepository;
	
	@Autowired 
	private ValidateService validateService;
	
	@Override
	public List<AppraisalUploadCheckList> findAll() {		
		return appraisalUploadCheckListRepository.findAll();
	}
	
	@Override
	public Optional<AppraisalUploadCheckList> findById(Long id) {
		Optional<AppraisalUploadCheckList> isCheckList = appraisalUploadCheckListRepository.findById(id);
		if (isCheckList.isPresent()) {
			return Optional.ofNullable(isCheckList.get());
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public List<AppraisalUploadCheckList> findByStatus(String status) {
		return appraisalUploadCheckListRepository.findByStatus(status);
	}
	
	@Override
	public List<AppraisalUploadCheckList> findByChecklistTemplateId(Long checklistTemplateId) {
		return appraisalUploadCheckListRepository.findByChecklistTemplatesId(checklistTemplateId);
	}
	
	@Override
	public Long saveAndValidateappraisalUploadCheckList(String tenantId, String createdUser, AppraisalUploadCheckListAddResource appraisalUploadCheckListAddResource) {
		
		LoggerRequest.getInstance().logInfo("**************Validate Checklist Template*********************************************");
		CheckListTemplate checkListTemplate = setChecklistTemplateAndValidate(Long.parseLong(appraisalUploadCheckListAddResource.getChecklistTemplateId()));
		
		LoggerRequest.getInstance().logInfo("**************Validate Document*********************************************");
		validateService.validateDocumentMaintenances(tenantId, appraisalUploadCheckListAddResource.getDocumentId(), appraisalUploadCheckListAddResource.getDocumentName());
		
		if(appraisalUploadCheckListRepository.existsByChecklistTemplatesIdAndDocumentId(Long.parseLong(appraisalUploadCheckListAddResource.getChecklistTemplateId()), Long.parseLong(appraisalUploadCheckListAddResource.getDocumentId())))
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "duplicates");
		
		LoggerRequest.getInstance().logInfo("*******Save appraisal upload Checklist*****************************");		
		AppraisalUploadCheckList appraisalUploadCheckList = saveAppraisalUploadCheckList(tenantId, createdUser, checkListTemplate, appraisalUploadCheckListAddResource);
				
		return appraisalUploadCheckList.getId();
	}
	
	private CheckListTemplate setChecklistTemplateAndValidate(Long checklistTemplateId) {
		CheckListTemplate checklistTemplate = null;
		Optional<CheckListTemplate> checklistTemplateOptional = checkListTemplateRepository.findByIdAndStatus(checklistTemplateId, CommonStatus.ACTIVE);
		if (!checklistTemplateOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "checklistTemplateId");
		} else {
			checklistTemplate = checklistTemplateOptional.get();
		}
		return checklistTemplate;
	}
		
	private AppraisalUploadCheckList saveAppraisalUploadCheckList(String tenantId, String createdUser, CheckListTemplate checkListTemplate, AppraisalUploadCheckListAddResource appraisalUploadCheckListAddResource) {
		
		AppraisalUploadCheckList appraisalUploadCheckList = new AppraisalUploadCheckList();
		appraisalUploadCheckList.setTenantId(tenantId);
		appraisalUploadCheckList.setChecklistTemplates(checkListTemplate);
		appraisalUploadCheckList.setDocumentId(Long.parseLong(appraisalUploadCheckListAddResource.getDocumentId()));
		appraisalUploadCheckList.setIsMandatory(appraisalUploadCheckListAddResource.getIsMandatory());
		appraisalUploadCheckList.setIsCompleted(appraisalUploadCheckListAddResource.getIsCompleted());		
		appraisalUploadCheckList.setCompletedDate(validateService.formatDate(appraisalUploadCheckListAddResource.getCompletedDate()));		
		appraisalUploadCheckList.setStatus(appraisalUploadCheckListAddResource.getStatus());
		appraisalUploadCheckList.setCreatedUser(createdUser);
		appraisalUploadCheckList.setCreatedDate(validateService.getCreateOrModifyDate());
		appraisalUploadCheckList.setSyncTs(validateService.getCreateOrModifyDate());
		appraisalUploadCheckListRepository.saveAndFlush(appraisalUploadCheckList);
		return appraisalUploadCheckList;
	}
	
	@Override
	public Long updateAndValidateappraisalUploadCheckList(String tenantId, String createdUser, Long id, AppraisalUploadCheckListUpdateResource appraisalUploadCheckListUpdateResource) {
		
		LoggerRequest.getInstance().logInfo("***************Validate version*******************");
		Optional<AppraisalUploadCheckList> isPresentAppraisalUploadCheckList = appraisalUploadCheckListRepository.findById(id);
		if(!isPresentAppraisalUploadCheckList.get().getVersion().equals(Long.parseLong(appraisalUploadCheckListUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
		
		
		LoggerRequest.getInstance().logInfo("**************Validate Checklist Template*********************************************");
		CheckListTemplate checkListTemplate = setChecklistTemplateAndValidate(Long.parseLong(appraisalUploadCheckListUpdateResource.getChecklistTemplateId()));
		
		LoggerRequest.getInstance().logInfo("**************Validate Document*********************************************");
		validateService.validateDocumentMaintenances(tenantId, appraisalUploadCheckListUpdateResource.getDocumentId(), appraisalUploadCheckListUpdateResource.getDocumentName());
		
		LoggerRequest.getInstance().logInfo("***************Validate Checklist Template Documents Duplicate*********************************************");
		if(appraisalUploadCheckListRepository.existsByChecklistTemplatesIdAndDocumentIdAndIdNotIn(Long.parseLong(appraisalUploadCheckListUpdateResource.getChecklistTemplateId()), Long.parseLong(appraisalUploadCheckListUpdateResource.getDocumentId()), id))
			throw new ValidateRecordException(environment.getProperty("checklistTemplateDocument.unique"), "message");
		
		LoggerRequest.getInstance().logInfo("*******Save appraisal upload Checklist*****************************");
		AppraisalUploadCheckList appraisalUploadCheckList = updateAppraisalUploadCheckList(tenantId, createdUser, checkListTemplate, appraisalUploadCheckListUpdateResource,id);
				
		return appraisalUploadCheckList.getId();
	}
	
	private AppraisalUploadCheckList updateAppraisalUploadCheckList(String tenantId, String createdUser, CheckListTemplate checkListTemplate, AppraisalUploadCheckListUpdateResource appraisalUploadCheckListUpdateResource,Long id) {
		
		AppraisalUploadCheckList appraisalUploadCheckList = appraisalUploadCheckListRepository.getOne(id);
		appraisalUploadCheckList.setTenantId(tenantId);
		appraisalUploadCheckList.setChecklistTemplates(checkListTemplate);
		appraisalUploadCheckList.setDocumentId(Long.parseLong(appraisalUploadCheckListUpdateResource.getDocumentId()));
		appraisalUploadCheckList.setIsMandatory(appraisalUploadCheckListUpdateResource.getIsMandatory());
		appraisalUploadCheckList.setIsCompleted(appraisalUploadCheckListUpdateResource.getIsCompleted());	
		appraisalUploadCheckList.setCompletedDate(validateService.formatDate(appraisalUploadCheckListUpdateResource.getCompletedDate()));
		appraisalUploadCheckList.setStatus(appraisalUploadCheckListUpdateResource.getStatus());
		appraisalUploadCheckList.setVersion(Long.parseLong(appraisalUploadCheckListUpdateResource.getVersion()));
		appraisalUploadCheckList.setModifiedUser(createdUser);
		appraisalUploadCheckList.setModifiedDate(validateService.getCreateOrModifyDate());
		appraisalUploadCheckList.setSyncTs(validateService.getCreateOrModifyDate());
		appraisalUploadCheckListRepository.saveAndFlush(appraisalUploadCheckList);
		return appraisalUploadCheckList;
	}
	
	@Override
	public Boolean existsById(Long id) {
		return appraisalUploadCheckListRepository.existsById(id);
	}
	
}
