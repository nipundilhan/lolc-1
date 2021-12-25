package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.EligibilityPending;
import com.fusionx.lending.product.domain.FeatureBenifitTemplate;
import com.fusionx.lending.product.domain.FeatureBenifitTemplateHistory;
import com.fusionx.lending.product.domain.FeatureBenifitTemplatePending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.FeatureBenifitTemplateHistoryRepository;
import com.fusionx.lending.product.repository.FeatureBenifitTemplatePendingRepository;
import com.fusionx.lending.product.repository.FeatureBenifitTemplateRepository;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.FeatureBenifitTemplateService;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor=Exception.class)
public class FeatureBenifitTemplateServiceImpl extends MessagePropertyBase implements FeatureBenifitTemplateService {

	
	@Autowired
	private FeatureBenifitTemplateRepository featureBenifitTemplateRepository;

	@Autowired
	private FeatureBenifitTemplateHistoryRepository featureBenifitTemplateHistoryRepository;
	
	@Autowired
	private FeatureBenifitTemplatePendingRepository featureBenifitTemplatePendingRepository;
	
	@Autowired 
	private ValidationService validationService;
	@Autowired
	private LendingWorkflowRepository lendingWorkflowRepository;
	@Autowired
	private LendingWorkflowService lendingWorkflowService;
	
	
	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";

	@Override
	public List<FeatureBenifitTemplate> getAll() {
		return featureBenifitTemplateRepository.findAll();
	}

	@Override
	public Optional<FeatureBenifitTemplate> getById(Long id) {
		Optional<FeatureBenifitTemplate> isPresentFeatureBenifitTemplate= featureBenifitTemplateRepository.findById(id);
		if (isPresentFeatureBenifitTemplate.isPresent()) {
			return Optional.ofNullable(isPresentFeatureBenifitTemplate.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<FeatureBenifitTemplate> getByCode(String code) {
		Optional<FeatureBenifitTemplate> isPresentFeatureBenifitTemplate= featureBenifitTemplateRepository.findByCode(code);
		if (isPresentFeatureBenifitTemplate.isPresent()) {
			return Optional.ofNullable(isPresentFeatureBenifitTemplate.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<FeatureBenifitTemplate> getByName(String name) {
		return featureBenifitTemplateRepository.findByNameContaining(name);
	}

	@Override
	public List<FeatureBenifitTemplate> getByStatus(String status) {
		return featureBenifitTemplateRepository.findByStatus(CommonStatus.valueOf(status));
	}
	
	@Override
	public FeatureBenifitTemplate addFeatureBenifitTemplate(String tenantId,
			CommonAddResource commonAddResource) {
		
		Optional<FeatureBenifitTemplate> isPresentFeeCharge = featureBenifitTemplateRepository.findByCode(commonAddResource.getCode());
        
        if (isPresentFeeCharge.isPresent()) 
        	throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.FEE_CHARGE);
        
        
        FeatureBenifitTemplate featureBenifitTemplate = new FeatureBenifitTemplate();
        featureBenifitTemplate.setTenantId(tenantId);
        featureBenifitTemplate.setCode(commonAddResource.getCode());
        featureBenifitTemplate.setName(commonAddResource.getName());
        featureBenifitTemplate.setStatus(CommonStatus.valueOf(commonAddResource.getStatus()));
        featureBenifitTemplate.setCreatedDate(validationService.getCreateOrModifyDate());
        featureBenifitTemplate.setSyncTs(validationService.getCreateOrModifyDate());
        featureBenifitTemplate.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        
        return featureBenifitTemplateRepository.save(featureBenifitTemplate);
	}
	
	
	@Override
	public FeatureBenifitTemplatePending updateFeatureBenifitTemplate(String tenantId, Long id,CommonUpdateResource commonUpdateResource) {
		LoggerRequest.getInstance().logInfo1("***********updateFeatureBenifitTemplate***********");
		Optional<FeatureBenifitTemplate> isPresentFeatureBenifitTemplate = featureBenifitTemplateRepository.findById(id);
		if(!isPresentFeatureBenifitTemplate.isPresent())		
			throw new InvalidServiceIdException(environment.getProperty("common.record-not-found"), ServiceEntity.CODE,
				EntityPoint.MASTER_DEFINITION);

		if(!isPresentFeatureBenifitTemplate.get().getVersion().equals(Long.parseLong(commonUpdateResource.getVersion())))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.ELIGIBILTY);
		
		if(!isPresentFeatureBenifitTemplate.get().getCode().equalsIgnoreCase(commonUpdateResource.getCode()))
			throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE, EntityPoint.ELIGIBILTY);
		LoggerRequest.getInstance().logInfo1("***********Workflow Call***********");
		FeatureBenifitTemplatePending featureBenifitTemplatePending = updateOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(),
				isPresentFeatureBenifitTemplate.get(), commonUpdateResource);

		return featureBenifitTemplatePending;
	}
	
	
	private FeatureBenifitTemplatePending updateOrGenerateWorkFlow(String tenantId, String user, FeatureBenifitTemplate featureBenifitTemplate,
			CommonUpdateResource commonUpdateResource) {
			LoggerRequest.getInstance().logInfo1("***********updateOrGenerateWorkFlow***********");
			
			WorkflowRulesResource workflowRulesResource = null;
			Long processId = null;
			WorkflowType workflowType = WorkflowType.FEATURE_BENEFIT_TEMP_APPROVAL;
			LendingWorkflow lendingWorkflow = null;

			WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
			workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
			workflowInitiationRequestResource.setApprovWorkflowType(workflowType);
			LoggerRequest.getInstance().logInfo1("***********Get Workflow Rule Info***********");
			workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,tenantId);
			

			FeatureBenifitTemplatePending featureBenifitTemplatePending = null;
			
			if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
				processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
				if (processId != null) {
					lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
					featureBenifitTemplatePending = saveFeatureBenifitTemplatePending(commonUpdateResource,featureBenifitTemplate,lendingWorkflow);
					
				}
			} else {
				featureBenifitTemplatePending = saveFeatureBenifitTemplatePending(commonUpdateResource,featureBenifitTemplate,null);
				updateFeatureBenifitTemplate(featureBenifitTemplate,featureBenifitTemplatePending);
				
				
			}
			return featureBenifitTemplatePending;
		
	}
	
	public FeatureBenifitTemplatePending saveFeatureBenifitTemplatePending(CommonUpdateResource commonUpdateResource,FeatureBenifitTemplate featureBenifitTemplate , LendingWorkflow lendingWorkflow) {
				
        FeatureBenifitTemplatePending featureBenifitTemplatePending = new FeatureBenifitTemplatePending();
        featureBenifitTemplatePending.setTenantId(featureBenifitTemplate.getTenantId());
        featureBenifitTemplatePending.setFeatureBenifitTemplate(featureBenifitTemplate);
        featureBenifitTemplatePending.setLendingWorkflow(lendingWorkflow);
        featureBenifitTemplatePending.setCode(featureBenifitTemplate.getCode());
        featureBenifitTemplatePending.setName(commonUpdateResource.getName());
        featureBenifitTemplatePending.setStatus(CommonStatus.valueOf(commonUpdateResource.getStatus().toString()));
        featureBenifitTemplatePending.setApproveStatus(CommonApproveStatus.PENDING);
        featureBenifitTemplatePending.setCreatedDate(validationService.getCreateOrModifyDate());
        featureBenifitTemplatePending.setSyncTs(validationService.getCreateOrModifyDate());
        featureBenifitTemplatePending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        
        featureBenifitTemplatePending = featureBenifitTemplatePendingRepository.save(featureBenifitTemplatePending);
        return featureBenifitTemplatePending;
		
	}
	
	public void updateFeatureBenifitTemplate(FeatureBenifitTemplate fbt , FeatureBenifitTemplatePending featureBenifitTemplatePending) {
        
		saveHistory(fbt);
		
		FeatureBenifitTemplate featureBenifitTemplate = fbt;
        featureBenifitTemplate.setName(featureBenifitTemplatePending.getName());
        featureBenifitTemplate.setStatus(featureBenifitTemplatePending.getStatus());
        featureBenifitTemplate.setModifiedDate(validationService.getCreateOrModifyDate());
        featureBenifitTemplate.setSyncTs(validationService.getCreateOrModifyDate());
        featureBenifitTemplate.setModifiedUser(LogginAuthentcation.getInstance().getUserName());        
        featureBenifitTemplateRepository.save(featureBenifitTemplate);		
	}
	
	public void saveHistory(FeatureBenifitTemplate featureBenifitTemplate) {
		
        FeatureBenifitTemplateHistory featureBenifitTemplateHistory = new FeatureBenifitTemplateHistory();
        featureBenifitTemplateHistory.setTenantId(featureBenifitTemplate.getTenantId());
        featureBenifitTemplateHistory.setFeatureBenifitTemplateId(featureBenifitTemplate.getId());
        featureBenifitTemplateHistory.setCode(featureBenifitTemplate.getCode());
        featureBenifitTemplateHistory.setName(featureBenifitTemplate.getName());
        featureBenifitTemplateHistory.setStatus(featureBenifitTemplate.getStatus());
        featureBenifitTemplateHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
        featureBenifitTemplateHistory.setHistoryCreatedUser(LogginAuthentcation.getInstance().getUserName());
        featureBenifitTemplateHistory.setCreatedUser(featureBenifitTemplate.getCreatedUser());
        featureBenifitTemplateHistory.setCreatedDate(featureBenifitTemplate.getCreatedDate());
        featureBenifitTemplateHistory.setSyncTs(validationService.getCreateOrModifyDate());      
        featureBenifitTemplateHistoryRepository.save(featureBenifitTemplateHistory);		
		
	}
	
	
	@Override
	public Optional<FeatureBenifitTemplatePending> getByPendingId(Long pendingId) {
		Optional<FeatureBenifitTemplatePending> isPresentEligiPending= featureBenifitTemplatePendingRepository.findById(pendingId);
		if (isPresentEligiPending.isPresent()) {
			return Optional.ofNullable(isPresentEligiPending.get());
		}
		else {
			return Optional.empty();
		}
	}
	

	@Override
	public Page<FeatureBenifitTemplatePending> searchFeatureBenifitTemplatePending(String searchq, String status, String approveStatus, Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(status==null || status.isEmpty())
			status=null;
		if(approveStatus==null || approveStatus.isEmpty())
			approveStatus=null;
		return featureBenifitTemplatePendingRepository.searchPending(searchq, status, approveStatus, pageable);
	}
}


