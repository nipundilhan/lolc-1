package com.fusionx.lending.transaction.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.AllocationTemplate;
import com.fusionx.lending.transaction.domain.AllocationTemplatePending;
import com.fusionx.lending.transaction.domain.LendingWorkflow;
import com.fusionx.lending.transaction.enums.CommonApproveStatus;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.enums.WorkflowStatus;
import com.fusionx.lending.transaction.enums.WorkflowType;
import com.fusionx.lending.transaction.exception.ValidateRecordException;
import com.fusionx.lending.transaction.repo.AllocationTemplatePendingRepository;
import com.fusionx.lending.transaction.repo.AllocationTemplateRepository;
import com.fusionx.lending.transaction.repo.LendingWorkflowRepository;
import com.fusionx.lending.transaction.resource.AccountStatusResource;
import com.fusionx.lending.transaction.resource.AllocationTemplateAddResource;
import com.fusionx.lending.transaction.resource.AllocationTemplateUpdateResource;
import com.fusionx.lending.transaction.resource.WorkflowInitiationRequestResource;
import com.fusionx.lending.transaction.resource.WorkflowRulesResource;
import com.fusionx.lending.transaction.service.AllocationTemplateService;
import com.fusionx.lending.transaction.service.LendingWorkflowService;
import com.fusionx.lending.transaction.service.ValidateService;
import com.fusionx.lending.transaction.service.ValidationService;

@Component
@Transactional(rollbackFor=Exception.class)
public class AllocationTemplateServiceImpl extends MessagePropertyBase implements AllocationTemplateService{

	@Autowired
	private AllocationTemplateRepository allocationTemplateRepository;
	
	@Autowired
	private AllocationTemplatePendingRepository allocationTemplatePendingRepository;
	
	@Autowired
	private LendingWorkflowRepository lendingWorkflowRepository;
	
	@Autowired
	private ValidateService validateService;
	
	@Autowired
	private ValidationService validationService; 
	
	@Autowired
	private LendingWorkflowService lendingWorkflowService; 
	
	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";
	
	@Override
	public List<AllocationTemplate> findAll() {
		return allocationTemplateRepository.findAll();
	}

	@Override
	public Optional<AllocationTemplate> findById(Long allocationTemplateId) {
		Optional<AllocationTemplate> allocationTemplate = allocationTemplateRepository.findById(allocationTemplateId);
		if (allocationTemplate.isPresent())
			return Optional.ofNullable(allocationTemplate.get());
		else
			return Optional.empty();
	}

	@Override
	public List<AllocationTemplate> findByStatus(String status) {
		return allocationTemplateRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public AllocationTemplate addAllocationTemplate(String tenantId,
			AllocationTemplateAddResource allocationTemplateAddResource) {
		
		Optional<AllocationTemplate> isPresentAllocationTemplate = allocationTemplateRepository.findByCode(allocationTemplateAddResource.getCode());       
        if (isPresentAllocationTemplate.isPresent()) 
        	throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), MESSAGE);
        
        AccountStatusResource accountStatusResource = validateService.validateAccountStatus(tenantId, validationService.stringToLong(allocationTemplateAddResource.getAccountStatusId()), 
        		allocationTemplateAddResource.getAccountStatusName());
        
        AllocationTemplate allocationTemplate = new AllocationTemplate();
        allocationTemplate.setTenantId(tenantId);
        allocationTemplate.setLoanAccountStatusId(validationService.stringToLong(allocationTemplateAddResource.getAccountStatusId()));
        allocationTemplate.setLoanAccountStatusCode(accountStatusResource.getCode());
        allocationTemplate.setCode(allocationTemplateAddResource.getCode());
        allocationTemplate.setName(allocationTemplateAddResource.getName());
        allocationTemplate.setStatus(CommonStatus.valueOf(allocationTemplateAddResource.getStatus()));
        allocationTemplate.setCreatedDate(validationService.getCreateOrModifyDate());
        allocationTemplate.setSyncTs(validationService.getCreateOrModifyDate());
        allocationTemplate.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        allocationTemplate = allocationTemplateRepository.save(allocationTemplate);
	    return allocationTemplate;
	}

	@Override
	public AllocationTemplate updateAllocationTemplate(String tenantId, Long id,
			AllocationTemplateUpdateResource allocationTemplateUpdateResource) {
		
		Optional<AllocationTemplate> isPresentAllocationTemplate = allocationTemplateRepository.findById(id);
		if (isPresentAllocationTemplate.isPresent()) {
			if (!isPresentAllocationTemplate.get().getVersion().equals(Long.parseLong(allocationTemplateUpdateResource.getVersion()))) {
				throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), MESSAGE);
			}
			
			if (!isPresentAllocationTemplate.get().getCode().equalsIgnoreCase(allocationTemplateUpdateResource.getCode()))
				throw new ValidateRecordException(environment.getProperty(COMMON_CODE_UPDATE), MESSAGE);
			
			approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(),
					isPresentAllocationTemplate.get(), allocationTemplateUpdateResource);

			return isPresentAllocationTemplate.get();
		}
		else
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
	}

	private void approveOrGenerateWorkFlow(String tenantId, String userName, AllocationTemplate allocationTemplate,
			AllocationTemplateUpdateResource allocationTemplateUpdateResource) {

		WorkflowType workflowType = WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL;
		LendingWorkflow lendingWorkflow = null;
		Long processId = null;

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

		WorkflowRulesResource workflowRulesResource = validateService.invokedLendingProductRule(workflowType,
				DOMAIN_PATH, DOMAIN, tenantId);
		

		if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
			processId = validateService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
			if (processId != null) {
				lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, userName);
				savePendingAllocationTemplate(tenantId, allocationTemplate, allocationTemplateUpdateResource, lendingWorkflow);
			}
		} else {
			AllocationTemplatePending allocationTemplatePending = savePendingAllocationTemplate(tenantId, allocationTemplate,
					allocationTemplateUpdateResource, lendingWorkflow);
			updateAllocationTemplate(allocationTemplatePending, allocationTemplate, CommonApproveStatus.APPROVED, userName);
		}
	}

	

	private AllocationTemplatePending savePendingAllocationTemplate(String tenantId, AllocationTemplate allocationTemplate,
			AllocationTemplateUpdateResource allocationTemplateUpdateResource, LendingWorkflow lendingWorkflow) {
		
		AccountStatusResource accountStatusResource = validateService.validateAccountStatus(tenantId, validationService.stringToLong(allocationTemplateUpdateResource.getAccountStatusId()), 
				allocationTemplateUpdateResource.getAccountStatusName());
		
		AllocationTemplatePending allocationTemplatePending = new AllocationTemplatePending();
		allocationTemplatePending.setTenantId(tenantId);
		if(lendingWorkflow != null)
			allocationTemplatePending.setLendingWorkflow(lendingWorkflow);
		allocationTemplatePending.setAllocationTemplate(allocationTemplate);
		allocationTemplatePending.setCode(allocationTemplateUpdateResource.getCode());
		allocationTemplatePending.setLoanAccountStatusId(validationService.stringToLong(allocationTemplateUpdateResource.getAccountStatusId()));
		allocationTemplatePending.setLoanAccountStatusCode(accountStatusResource.getCode());
		allocationTemplatePending.setName(allocationTemplateUpdateResource.getName());
		allocationTemplatePending.setStatus(CommonStatus.valueOf(allocationTemplateUpdateResource.getStatus()));
		allocationTemplatePending.setApproveStatus(CommonApproveStatus.PENDING);
		allocationTemplatePending.setCreatedDate(validationService.getCreateOrModifyDate());
		allocationTemplatePending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		allocationTemplatePending.setSyncTs(validationService.getCreateOrModifyDate());
		return allocationTemplatePendingRepository.save(allocationTemplatePending);
	}

	@Override
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus) {
		
		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL);
		WorkflowRulesResource workflowRulesResource;
		WorkflowType workflowType = WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL;
		String user = LogginAuthentcation.getInstance().getUserName();
		CommonApproveStatus approveStatus;

		Optional<AllocationTemplatePending> isPresentAllocationTemplatePending = allocationTemplatePendingRepository
				.findById(id);
		
		if (isPresentAllocationTemplatePending.isPresent()) {
			Optional<AllocationTemplate> allocationTemplate = allocationTemplateRepository
					.findById(isPresentAllocationTemplatePending.get().getAllocationTemplate().getId());
			
			if (!isPresentAllocationTemplatePending.get().getApproveStatus().equals(CommonApproveStatus.PENDING))
				throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), "message");


				Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository
						.findById(isPresentAllocationTemplatePending.get().getLendingWorkflow().getId());
				workflowRulesResource = validateService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
						tenantId);

				if (lendingWorkflow.isPresent()) {
					if (workflowRulesResource.getApprovalAllowed().equalsIgnoreCase(CommonStatus.NO.toString())
							&& lendingWorkflow.get().getCreatedUser()
									.equalsIgnoreCase(LogginAuthentcation.getInstance().getUserName())) {
						throw new ValidateRecordException(environment.getProperty("cannot.process.approve"), MESSAGE);
					}

					if (workflowStatus.toString().equalsIgnoreCase(WorkflowStatus.COMPLETE.toString())) {
						validateService.approveWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType,
								user, tenantId);
						approveStatus = CommonApproveStatus.APPROVED;
						
					} else {
						validateService.abortedWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType,
								user, tenantId);
						approveStatus = CommonApproveStatus.REJECTED;
					}

					lendingWorkflowService.update(lendingWorkflow.get(), workflowStatus, user);
					updateAllocationTemplate(isPresentAllocationTemplatePending.get(), allocationTemplate.isPresent()? allocationTemplate.get() :null,
							approveStatus, user);

					return true;
				}
			}

		return false;
	}

	@Override
	public Optional<AllocationTemplatePending> getByPendingId(Long pendingId) {
		
		Optional<AllocationTemplatePending> isPresentAllocationTemplatePending = allocationTemplatePendingRepository
				.findById(pendingId);
		if (isPresentAllocationTemplatePending.isPresent()) {
			return Optional.ofNullable(isPresentAllocationTemplatePending.get());
		} else {
			return Optional.empty();
		}
	}
	
	private void updateAllocationTemplate(AllocationTemplatePending allocationTemplatePending,
			AllocationTemplate allocationTemp, CommonApproveStatus approvedStatus, String userName) {
		
		AllocationTemplate allocationTemplate = allocationTemp;

		if (approvedStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {

			allocationTemplate.setName(allocationTemplatePending.getName());
			allocationTemplate.setStatus(allocationTemplatePending.getStatus());
			allocationTemplate.setLoanAccountStatusId(allocationTemplatePending.getLoanAccountStatusId());
			allocationTemplate.setLoanAccountStatusCode(allocationTemplatePending.getLoanAccountStatusCode());
			allocationTemplate.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			allocationTemplate.setModifiedDate(validationService.getCreateOrModifyDate());
			allocationTemplate.setSyncTs(validationService.getCreateOrModifyDate());
			allocationTemplateRepository.saveAndFlush(allocationTemplate);

			allocationTemplatePending.setApprovedUser(userName);
			allocationTemplatePending.setApproveStatus(CommonApproveStatus.APPROVED);
			allocationTemplatePending.setApprovedDate(validationService.getCreateOrModifyDate());
			allocationTemplatePending.setSyncTs(validationService.getCreateOrModifyDate());
			allocationTemplatePendingRepository.save(allocationTemplatePending);
			
		}else {
			allocationTemplatePending.setApprovedUser(userName);
			allocationTemplatePending.setApproveStatus(CommonApproveStatus.REJECTED);
			allocationTemplatePending.setApprovedDate(validationService.getCreateOrModifyDate());
			allocationTemplatePending.setSyncTs(validationService.getCreateOrModifyDate());
			allocationTemplatePendingRepository.save(allocationTemplatePending);
		}
	}

	@Override
	public Optional<AllocationTemplate> findByCode(String code) {
		Optional<AllocationTemplate> isPresentAllocationTemplate = allocationTemplateRepository.findByCode(code);
		if (isPresentAllocationTemplate.isPresent()) {
			return Optional.ofNullable(isPresentAllocationTemplate.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<AllocationTemplate> getByName(String name) {
		return allocationTemplateRepository.findByNameContaining(name);
	}

	@Override
	public Page<AllocationTemplatePending> searchAllocationTemplate(String searchq, String status, String approveStatus,
			Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(status==null || status.isEmpty())
			status=null;
		if(approveStatus==null || approveStatus.isEmpty())
			approveStatus=null;
		return allocationTemplatePendingRepository.searchAllocationTemplatePending(searchq, status, approveStatus, pageable);
	}

}
