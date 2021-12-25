package com.fusionx.lending.product.service.impl;

/**
 * Interest Template Service
 * @author 	Venuki
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019   FX-2879        FX-6532    Venuki      Created
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

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.InterestTemplate;
import com.fusionx.lending.product.domain.InterestTemplatePending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.domain.MasterDefinitionPending;
import com.fusionx.lending.product.domain.Repayment;
import com.fusionx.lending.product.domain.RepaymentPending;
import com.fusionx.lending.product.enums.ApproveStatus;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.InterestTemplatePendingRepository;
import com.fusionx.lending.product.repository.InterestTemplateRepository;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.resources.InterestTemplateAddResource;
import com.fusionx.lending.product.resources.InterestTemplateUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.InterestTemplateHistoryService;
import com.fusionx.lending.product.service.InterestTemplateService;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor=Exception.class)
public class InterestTemplateServiceImpl extends MessagePropertyBase implements InterestTemplateService{
	
	@Autowired
	private InterestTemplateRepository interestTemplateRepository;
	
	@Autowired
	private InterestTemplateHistoryService interestTemplateHistoryService;
	
	@Autowired
	private InterestTemplatePendingRepository interestTemplatePendingRepository;
	
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
	public List<InterestTemplate> findAll() {
		return interestTemplateRepository.findAll();
	}

	@Override
	public Optional<InterestTemplate> findById(Long interestTemplateId) {
		Optional<InterestTemplate> interestTemplate = interestTemplateRepository.findById(interestTemplateId);
		if(interestTemplate.isPresent())
			return Optional.ofNullable(interestTemplate.get());
		else
			return Optional.empty();
	}

	@Override
	public List<InterestTemplate> findByStatus(String status) {
		return interestTemplateRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public Optional<InterestTemplate> getInterestTemplateByCode(String interestTemplateCode) {
		Optional<InterestTemplate> interestTemplate = interestTemplateRepository.findByCode(interestTemplateCode);
		if(interestTemplate.isPresent())
			return Optional.ofNullable(interestTemplate.get());
		else
			return Optional.empty();
	}

	@Override
	public InterestTemplate addInterestTemplate(String tenantId,InterestTemplateAddResource interestTemplateAddResource, String userName) {
		
		InterestTemplate interestTemplate = new InterestTemplate();
		
		Optional<InterestTemplate>isPresentInterestTemplateCode = interestTemplateRepository.findByCode(interestTemplateAddResource.getCode());
		if (isPresentInterestTemplateCode.isPresent()) 
			throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE, EntityPoint.INTEREST_TEMPLATE);
		
		interestTemplate.setTenantId(tenantId);
		interestTemplate.setCode(interestTemplateAddResource.getCode());
		interestTemplate.setName(interestTemplateAddResource.getName());
		interestTemplate.setStatus(CommonStatus.valueOf(interestTemplateAddResource.getStatus()));
		interestTemplate.setApproveStatus(ApproveStatus.PENDING.toString());
		interestTemplate.setCreatedUser(userName);
		interestTemplate.setCreatedDate(validationService.getCreateOrModifyDate());
		interestTemplate.setSyncTs(validationService.getCreateOrModifyDate());
		interestTemplate = interestTemplateRepository.saveAndFlush(interestTemplate);
		return interestTemplate;
	}

	@Override
	public InterestTemplate updateInterestTemplate(String tenantId, Long id,InterestTemplateUpdateResource interestTemplateUpdateResource, String userName) {
	
		InterestTemplate interestTemplate = null;
		
		Optional<InterestTemplate> isPresentInterestTemplate = interestTemplateRepository.findById(id);
		if(isPresentInterestTemplate.isPresent()) {
			
			if(!isPresentInterestTemplate.get().getVersion().equals(Long.parseLong(interestTemplateUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION, EntityPoint.INTEREST_TEMPLATE);

			interestTemplate = isPresentInterestTemplate.get();
			
			if(interestTemplate.getApproveStatus().equalsIgnoreCase(ApproveStatus.PENDING.toString()))
				throw new ValidateRecordException(environment.getProperty(CANT_UPDATED),"message");
			
			Optional<InterestTemplate>isPresentInterestTemplateCode = interestTemplateRepository.findByCodeAndId(interestTemplateUpdateResource.getCode(), id);
			if (!isPresentInterestTemplateCode.isPresent())
				throw new InvalidServiceIdException(environment.getProperty("code.update.mismatch"), ServiceEntity.CODE, EntityPoint.INTEREST_TEMPLATE);
			
	        executeWorkflowCall(tenantId, interestTemplate, interestTemplateUpdateResource, WorkflowType.INTEREST_TEMP_APPROVAL );   
		}
		else
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND),"message");
	
		return interestTemplate;
	
		}
	
		private void executeWorkflowCall(String tenantId, InterestTemplate interestTemplate, InterestTemplateUpdateResource interestTemplateUpdateResource, WorkflowType workflowType) {
			Long processId = null;
			LendingWorkflow lendingWorkflow =  null;
			String user = LogginAuthentcation.getInstance().getUserName();
			WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
			workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
			workflowInitiationRequestResource.setApprovWorkflowType(workflowType);
	
			WorkflowRulesResource workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);
	
			if(workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
				processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
					if(processId != null) {
						lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
						savePendingInterestTemplate(tenantId, interestTemplate, interestTemplateUpdateResource, lendingWorkflow, user);
					}
			} else {
				
				updateInterestTemplateDetails(tenantId, interestTemplateUpdateResource, interestTemplate, CommonApproveStatus.APPROVED, user);
			}
	
		}
	
	
		private void updateInterestTemplateDetails(String tenantId,InterestTemplateUpdateResource interestTemplateUpdateResource,
		InterestTemplate interestTemplate, CommonApproveStatus approvedStatus, String userName) {
			
			interestTemplateHistoryService.insertinterestTemplateHistory(tenantId, interestTemplate, userName);
			interestTemplate.setName(interestTemplateUpdateResource.getName());
			interestTemplate.setStatus(CommonStatus.valueOf(interestTemplateUpdateResource.getStatus()));
			interestTemplate.setApproveStatus(approvedStatus.toString());
			interestTemplate.setModifiedUser(userName);
			interestTemplate.setModifiedDate(validationService.getCreateOrModifyDate());
			interestTemplate.setSyncTs(validationService.getCreateOrModifyDate());
			interestTemplate.setVersion(validationService.stringToLong(interestTemplateUpdateResource.getVersion()));
			interestTemplate = interestTemplateRepository.saveAndFlush(interestTemplate);
			
		}
	
		private void savePendingInterestTemplate(String tenantId, InterestTemplate oldInterestTemplate,
				InterestTemplateUpdateResource interestTemplateUpdateResource, LendingWorkflow lendingWorkflow, String userName) {
		
			interestTemplateHistoryService.insertinterestTemplateHistory(tenantId, oldInterestTemplate, userName);
		
			oldInterestTemplate.setApproveStatus(ApproveStatus.PENDING.toString());
			oldInterestTemplate.setModifiedUser(userName);
			oldInterestTemplate.setModifiedDate(validationService.getCreateOrModifyDate());
			oldInterestTemplate.setSyncTs(validationService.getCreateOrModifyDate());
			interestTemplateRepository.saveAndFlush(oldInterestTemplate);
		
			InterestTemplatePending interestTemplatePending = new InterestTemplatePending();
			interestTemplatePending.setTenantId(tenantId);
			if(lendingWorkflow != null)
				interestTemplatePending.setLendingWorkflow(lendingWorkflow);
			interestTemplatePending.setInterestTemplate(oldInterestTemplate);
			interestTemplatePending.setUpdated(true);
			interestTemplatePending.setCode(interestTemplateUpdateResource.getCode());
			interestTemplatePending.setName(interestTemplateUpdateResource.getName());
			interestTemplatePending.setStatus(CommonStatus.valueOf(interestTemplateUpdateResource.getStatus()));
			interestTemplatePending.setPenCreatedDate(validationService.getCreateOrModifyDate());
			interestTemplatePending.setPenCreatedUser(userName);
			interestTemplatePending.setSyncTs(validationService.getCreateOrModifyDate());
			interestTemplatePendingRepository.save(interestTemplatePending);
		
		}

		@Override
		public Page<InterestTemplate> searchInterestTemplateList(String searchq, String name, String code, String status, Pageable pageable) {
			if(searchq==null || searchq.isEmpty())
				searchq=null;
			if(name==null || name.isEmpty())
				name=null;
			if(code==null || code.isEmpty())
				code=null;
			if(status==null || status.isEmpty())
				status=null;
			
			return interestTemplateRepository.searchInterestTemplateList(searchq, name, code,status, pageable);
		}

		@Override
		public List<InterestTemplate> findByName(String name) {
			return interestTemplateRepository.findByNameContaining(name);
		}
		
		@Override
		public Optional<InterestTemplatePending> getByPendingId(Long pendingId) {
			Optional<InterestTemplatePending> isPresentInterestTemplatePending= interestTemplatePendingRepository.findById(pendingId);
			if (isPresentInterestTemplatePending.isPresent()) {
				return Optional.ofNullable(isPresentInterestTemplatePending.get());
			}
			else {
				return Optional.empty();
			}
		}
		
		@Override
		public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus) {

			WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
			workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
			workflowInitiationRequestResource.setApprovWorkflowType(WorkflowType.INTEREST_TEMP_APPROVAL);
			WorkflowRulesResource workflowRulesResource = null;
			WorkflowType workflowType = WorkflowType.INTEREST_TEMP_APPROVAL;
			String user = LogginAuthentcation.getInstance().getUserName();
			CommonApproveStatus approveStatus= null;
		
			Optional<InterestTemplatePending> isPresentIntTempPending= interestTemplatePendingRepository.findById(id);
			
			Optional<InterestTemplate> intTemp = interestTemplateRepository.findById(isPresentIntTempPending.get().getInterestTemplate().getId());
			
			if(!isPresentIntTempPending.get().getInterestTemplate().getApproveStatus().equals(CommonApproveStatus.PENDING.toString()))
				throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), "message");
			
			Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository.findById(isPresentIntTempPending.get().getLendingWorkflow().getId());
		
			workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);
		
			if(workflowRulesResource.getApprovalAllowed().equalsIgnoreCase(CommonStatus.NO.toString())) {
				if(lendingWorkflow.get().getCreatedUser().equalsIgnoreCase(LogginAuthentcation.getInstance().getUserName()))
					throw new ValidateRecordException(environment.getProperty("cannot.process.approve"), "message");
			}
		
			if(workflowStatus.toString().equalsIgnoreCase(WorkflowStatus.COMPLETE.toString())) {
				validationService.approveWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user, tenantId);
				approveStatus = CommonApproveStatus.APPROVED;
			}
			else {
				validationService.abortedWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user, tenantId);
				approveStatus = CommonApproveStatus.REJECTED;
			}
		
			lendingWorkflowService.update(lendingWorkflow.get(), workflowStatus, user);
			
			updateIntTemplate(isPresentIntTempPending.get(), intTemp.get(), approveStatus, user);
			
			return true;
		}
		
		private void updateIntTemplate(InterestTemplatePending intTempPending, InterestTemplate intTemplate, CommonApproveStatus approveStatus, String user) {
			
			InterestTemplate interestTeamplte = intTemplate;
			
			InterestTemplatePending interestTemplatePending= intTempPending;
			interestTemplatePending.setApproveStatus(approveStatus.toString());
			
	        interestTeamplte.setName(intTempPending.getName());
	        interestTeamplte.setStatus(intTempPending.getStatus());
			interestTeamplte.setModifiedDate(intTempPending.getPenCreatedDate());
			interestTeamplte.setModifiedUser(intTempPending.getPenCreatedUser());
			interestTeamplte.setApproveStatus(approveStatus.toString());
			
			if(approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
				interestTeamplte.setPenApprovedUser(user);
				interestTeamplte.setPenApprovedDate(validationService.getCreateOrModifyDate());
			} else {
				interestTeamplte.setPenRejectedUser(user);
				interestTeamplte.setPenRejectedDate(validationService.getCreateOrModifyDate());
			} 
			interestTeamplte.setSyncTs(validationService.getCreateOrModifyDate());
			
			interestTemplateRepository.saveAndFlush(interestTeamplte);
			interestTemplatePendingRepository.saveAndFlush(interestTemplatePending);
		}

}
