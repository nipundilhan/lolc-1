package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.DueDateTemplates;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.domain.MasterDefinition;
import com.fusionx.lending.product.domain.MasterDefinitionPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.MasterDefinitionCategory;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.DueDateTemplatesRepository;
import com.fusionx.lending.product.repository.MasterDefinitionPendingRepository;
import com.fusionx.lending.product.repository.MasterDefinitionRepository;
import com.fusionx.lending.product.resources.MasterDefDueDateMappingUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.MasterDefDueDateTemplateMappingService;
import com.fusionx.lending.product.service.MasterDefinitionHistoryService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * MasterDef Due Date Template Mapping Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   29-09-2021    FXL-680  	 FXL-924	Piyumi      Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class MasterDefDueDateTemplateMappingServiceImpl  extends MessagePropertyBase implements MasterDefDueDateTemplateMappingService {
	
	@Autowired
	private ValidationService validationService;
	
	@Autowired
	private DueDateTemplatesRepository dueDateTemplatesRepository;
	
	@Autowired
	private MasterDefinitionRepository masterDefinitionRepository;

	@Autowired
	private MasterDefinitionPendingRepository masterDefinitionPendingRepository;

	@Autowired
	private LendingWorkflowService lendingWorkflowService;

	@Autowired
	private MasterDefinitionHistoryService masterDefinitionHistoryService;
	
	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";
	
	@Override
	public List<MasterDefinition> getByDueDateTemplateId(Long dueDateTemplateId) {
		return masterDefinitionRepository.findByDueDateTemplateId(dueDateTemplateId);
	
	}

	@Override
	public Long updateDueDateTempMappingDetails(String tenantId, Long id,
			MasterDefDueDateMappingUpdateResource masterDefDueDateMappingUpdateResource) {
		
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");

		Optional<MasterDefinition> isPresentMasterDefinition = masterDefinitionRepository.findById(id);		

		Optional<MasterDefinitionPending> masterDefinitionPending = masterDefinitionPendingRepository
				.findByMasterDefinitionIdAndStatusAndApproveStatus(id, CommonStatus.ACTIVE, CommonApproveStatus.PENDING);

		if (CommonApproveStatus.PENDING.toString().equals(isPresentMasterDefinition.get().getApproveStatus().toString()) || masterDefinitionPending.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("masterDef.can-not-update"), "message");
		}
		
		Optional<DueDateTemplates> isPresentDueDateTemplates = dueDateTemplatesRepository.findByIdAndStatus(Long.parseLong(masterDefDueDateMappingUpdateResource.getDueDateTemplateId()), CommonStatus.ACTIVE);
		if (!isPresentDueDateTemplates.isPresent()) 
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_INVALID_VALUE), "dueDateTemplateId");
		
		if(!isPresentDueDateTemplates.get().getCode().equals(masterDefDueDateMappingUpdateResource.getDueDateTemplateCode()))
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_MATCH), "dueDateTemplatecode");
		
		approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(),isPresentMasterDefinition.get(), masterDefDueDateMappingUpdateResource, isPresentDueDateTemplates.get());
		

		return isPresentMasterDefinition.get().getId();
	}
	
	private MasterDefinitionPending approveOrGenerateWorkFlow(String tenantId, String userName, MasterDefinition masterDefinition,
			MasterDefDueDateMappingUpdateResource masterDefDueDateMappingUpdateResource, DueDateTemplates dueDateTemplates) {
		WorkflowRulesResource workflowRulesResource = null;
		Long processId = null;
		WorkflowType workflowType = WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL;
		LendingWorkflow lendingWorkflow = null;
		MasterDefinitionPending masterDefinitionPending = null;

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

		workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
				tenantId);

		if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
			processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
			if (processId != null) {
				lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, userName);
				masterDefinitionPending = savePendingMasterDefinition(tenantId, masterDefinition,
						masterDefDueDateMappingUpdateResource, lendingWorkflow, dueDateTemplates, userName);

			}
		} else {

			directUpdateMasterDefinition(tenantId, masterDefinition,dueDateTemplates,masterDefDueDateMappingUpdateResource);

		}
		return masterDefinitionPending;

	}

	private void directUpdateMasterDefinition(String tenantId, MasterDefinition md, DueDateTemplates dueDateTemplates , 
			MasterDefDueDateMappingUpdateResource masterDefDueDateMappingUpdateResource) {

		masterDefinitionHistoryService.saveHistory(tenantId, md, LogginAuthentcation.getInstance().getUserName());
		
		MasterDefinition masterDefinition = md;
		masterDefinition.setTenantId(tenantId);
		masterDefinition.setDueDateTemplate(dueDateTemplates);
		masterDefinition.setDueDateTemplateRemark(masterDefDueDateMappingUpdateResource.getRemark());
		masterDefinition.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		masterDefinition.setModifiedDate(validationService.getCreateOrModifyDate());
		masterDefinition.setSyncTs(validationService.getSyncTs());
	}

	private MasterDefinitionPending savePendingMasterDefinition(String tenantId, MasterDefinition masterDefinition,
			MasterDefDueDateMappingUpdateResource masterDefDueDateMappingUpdateResource, LendingWorkflow lendingWorkflow,
			DueDateTemplates dueDateTemplates, String userName) {

		MasterDefinition md = masterDefinition;
		md.setApproveStatus(CommonApproveStatus.PENDING);
		md.setModifiedUser(userName);
		md.setModifiedDate(validationService.getCreateOrModifyDate());
		md.setSyncTs(validationService.getCreateOrModifyDate());
		masterDefinitionRepository.saveAndFlush(md);

		MasterDefinitionPending masterDefinitionPending = new MasterDefinitionPending();

		masterDefinitionPending.setTenantId(tenantId);
		if (lendingWorkflow != null)
			masterDefinitionPending.setLendingWorkflow(lendingWorkflow);

		masterDefinitionPending.setMasterDefinition(masterDefinition);
		masterDefinitionPending.setApproveStatus(CommonApproveStatus.PENDING);
		masterDefinitionPending.setMaxInterestRate(md.getMaxInterestRate());
		masterDefinitionPending.setMiniInterestRate(md.getMiniInterestRate());
		masterDefinitionPending.setMaxPenalInterestRate(md.getMaxPenalInterestRate());
		masterDefinitionPending.setMiniPenalInterestRate(md.getMiniPenalInterestRate());
		masterDefinitionPending.setPenalInterest(md.getPenalInterest());
		masterDefinitionPending.setMasterDefinitionCategory(MasterDefinitionCategory.MASTER_DEFINITION);
		masterDefinitionPending.setBusinessPrinciple(md.getBusinessPrinciple());
		masterDefinitionPending.setModule(md.getModule());
		masterDefinitionPending.setSubModuleCode(md.getSubModuleCode());
		masterDefinitionPending.setSubModuleName(md.getSubModuleName());
		masterDefinitionPending.setCode(md.getCode());
		masterDefinitionPending.setName(md.getName());
		masterDefinitionPending.setStatus(md.getStatus());
		masterDefinitionPending.setVersion(md.getVersion());
		masterDefinitionPending.setDueDateTemplate(dueDateTemplates);
		masterDefinitionPending.setDueDateTemplateRemark(masterDefDueDateMappingUpdateResource.getRemark());
		masterDefinitionPending.setPenCreatedDate(validationService.getCreateOrModifyDate());
		masterDefinitionPending.setPenCreatedUser(userName);
		masterDefinitionPending.setSyncTs(validationService.getCreateOrModifyDate());

		masterDefinitionPending = masterDefinitionPendingRepository.save(masterDefinitionPending);

		return masterDefinitionPending;
	}

}
