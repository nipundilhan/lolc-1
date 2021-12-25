package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Repayment;
import com.fusionx.lending.product.domain.RepaymentAmountType;
import com.fusionx.lending.product.domain.RepaymentFrequency;
import com.fusionx.lending.product.domain.RepaymentPending;
import com.fusionx.lending.product.domain.RepaymentType;
import com.fusionx.lending.product.domain.EligibilityPending;
import com.fusionx.lending.product.domain.FeatureBenefitItem;
import com.fusionx.lending.product.domain.FeeChargePending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.RepaymentPendingRepository;
import com.fusionx.lending.product.repository.RepaymentRepository;
import com.fusionx.lending.product.repository.RepaymentTypeRepository;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.repository.RepaymentAmountTypeRepository;
import com.fusionx.lending.product.repository.RepaymentFrequencyRepository;
import com.fusionx.lending.product.resources.RepaymentAddResource;
import com.fusionx.lending.product.resources.RepaymentUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.RepaymentService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * Repayment Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   	Task No    	Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   05-07-2021     FX-6620 		FX-6803     RavishikaS      Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class RepaymentServiceImpl extends MessagePropertyBase implements RepaymentService {

	@Autowired
	private RepaymentRepository repaymentRepository;
	
	@Autowired 
	private ValidationService validationService;

	@Autowired
	private RepaymentPendingRepository repaymentPendingRepository;
	
	@Autowired
	private LendingWorkflowService lendingWorkflowService;
	
	@Autowired
	private LendingWorkflowRepository lendingWorkflowRepository;
	
	@Autowired
	private RepaymentTypeRepository repaymentTypeRepository;
	
	@Autowired
	private RepaymentAmountTypeRepository repaymentAmountTypeRepository;
	
	@Autowired
	private RepaymentFrequencyRepository repaymentFrequencyRepository;
	
	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";

	@Override
	public List<Repayment> getAll() {
		return repaymentRepository.findAll();
	}

	@Override
	public Optional<Repayment> getById(Long id) {
		Optional<Repayment> isPresentRepayment= repaymentRepository.findById(id);
		if (isPresentRepayment.isPresent()) {
			return Optional.ofNullable(isPresentRepayment.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Repayment> getByCode(String code) {
		Optional<Repayment> isPresentRepayment= repaymentRepository.findByCode(code);
		if (isPresentRepayment.isPresent()) {
			return Optional.ofNullable(isPresentRepayment.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<Repayment> getByStatus(String status) {
		return repaymentRepository.findByStatus(CommonStatus.valueOf(status));
	}
	
	@Override
	public List<Repayment> getByName(String name) {
		return repaymentRepository.findByName(name);
	}

	@Override
	public Repayment addRepayment(String tenantId,
			RepaymentAddResource repaymentAddResource) {
		
		Optional<Repayment> isPresentRepayment = repaymentRepository.findByCode(repaymentAddResource.getCode());
        
        if (isPresentRepayment.isPresent()) 
        	throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE, EntityPoint.ELIGIBILTY);
        
        RepaymentType repaymentType = validateRepaymentType(validationService.stringToLong(repaymentAddResource.getRepaymentTypeId()), repaymentAddResource.getRepaymentTypeName());
        
        RepaymentAmountType repaymentAmountType = validateRepaymentAmountType(validationService.stringToLong(repaymentAddResource.getRepaymentAmountTypeId()), repaymentAddResource.getRepaymentAmountTypeName());
        
        RepaymentFrequency repaymentFrequency = validateRepaymentFrequency(validationService.stringToLong(repaymentAddResource.getRepaymentFrequencyId()), repaymentAddResource.getRepaymentFrequencyName());
        
        Repayment repayment = new Repayment();
        repayment.setTenantId(tenantId);
        repayment.setCode(repaymentAddResource.getCode());
        repayment.setRepaymentAmountType(repaymentAmountType);
        repayment.setRepaymentFrequency(repaymentFrequency);
        repayment.setRepaymentType(repaymentType);
        repayment.setName(repaymentAddResource.getName());
        repayment.setStatus(CommonStatus.valueOf(repaymentAddResource.getStatus()));
        repayment.setCreatedDate(validationService.getCreateOrModifyDate());
        repayment.setSyncTs(validationService.getCreateOrModifyDate());
        repayment.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        
        return repaymentRepository.save(repayment);
	}

	@Override
	public Repayment updateRepayment(String tenantId, Long id, RepaymentUpdateResource repaymentUpdateResource) {
		
		Optional<Repayment> isPresentRepayment= repaymentRepository.findById(id);
		
		if(!isPresentRepayment.get().getVersion().equals(Long.parseLong(repaymentUpdateResource.getVersion())))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.ELIGIBILTY);
		
		if(!isPresentRepayment.get().getCode().equalsIgnoreCase(repaymentUpdateResource.getCode()))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_CODE_UPDATE), ServiceEntity.CODE, EntityPoint.ELIGIBILTY);
		
		approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(), isPresentRepayment.get(), repaymentUpdateResource);
		
		return isPresentRepayment.get();
	}
	
	private void approveOrGenerateWorkFlow(String tenantId, String user, Repayment repayment, RepaymentUpdateResource repaymentUpdateResource) {
		WorkflowRulesResource workflowRulesResource = null;
		Long processId = null;
		WorkflowType workflowType = WorkflowType.REPAYMENT_TEMP_APPROVAL;
		LendingWorkflow lendingWorkflow =  null;

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

		workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);

		if(workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
			processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
				if(processId != null) {
					lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
					savePendingRepayment(tenantId, repayment, repaymentUpdateResource, lendingWorkflow, user);
				}
		} else {
			RepaymentPending repaymentPending = savePendingRepayment(tenantId, repayment, repaymentUpdateResource, lendingWorkflow, user);
			updateRepayment(repaymentPending, repayment, CommonApproveStatus.APPROVED, user);
		}
	}
	
	private RepaymentPending savePendingRepayment(String tenantId, Repayment repayment, RepaymentUpdateResource repaymentUpdateResource, LendingWorkflow lendingWorkflow, String user) {
		
		Repayment repay = repayment;
		repay.setApproveStatus(CommonApproveStatus.PENDING);
		repay.setModifiedUser(user);
		repay.setModifiedDate(validationService.getCreateOrModifyDate());
		repay.setSyncTs(validationService.getCreateOrModifyDate());
		repaymentRepository.saveAndFlush(repay);
		
		RepaymentType repaymentType = validateRepaymentType(validationService.stringToLong(repaymentUpdateResource.getRepaymentTypeId()), repaymentUpdateResource.getRepaymentTypeName());
        
        RepaymentAmountType repaymentAmountType = validateRepaymentAmountType(validationService.stringToLong(repaymentUpdateResource.getRepaymentAmountTypeId()), repaymentUpdateResource.getRepaymentAmountTypeName());
        
        RepaymentFrequency repaymentFrequency = validateRepaymentFrequency(validationService.stringToLong(repaymentUpdateResource.getRepaymentFrequencyId()), repaymentUpdateResource.getRepaymentFrequencyName());
		
		RepaymentPending repaymentPending = new RepaymentPending();
		
		repaymentPending.setTenantId(tenantId);
		if(lendingWorkflow != null)
			repaymentPending.setLendingWorkflow(lendingWorkflow);
		repaymentPending.setRepayment(repay);
		repaymentPending.setCode(repaymentUpdateResource.getCode());
		repaymentPending.setName(repaymentUpdateResource.getName());
		repaymentPending.setRepayment(repayment);
		repaymentPending.setRepaymentAmountType(repaymentAmountType);
		repaymentPending.setRepaymentFrequency(repaymentFrequency);
		repaymentPending.setRepaymentType(repaymentType);
		repaymentPending.setStatus(CommonStatus.valueOf(repaymentUpdateResource.getStatus()));
		repaymentPending.setPcreatedDate(validationService.getCreateOrModifyDate());
		repaymentPending.setPcreatedUser(user);
		repaymentPending.setSyncTs(validationService.getCreateOrModifyDate());
		
		return repaymentPendingRepository.save(repaymentPending);
		
	}
	
	private void updateRepayment(RepaymentPending repaymentPending, Repayment repay, CommonApproveStatus approveStatus, String user) {
		
		Repayment repayment = repay;
		
        repayment.setRepaymentAmountType(repaymentPending.getRepaymentAmountType());
        repayment.setRepaymentFrequency(repaymentPending.getRepaymentFrequency());
        repayment.setRepaymentType(repaymentPending.getRepaymentType());
        repayment.setName(repaymentPending.getName());
        repayment.setStatus(repaymentPending.getStatus());
		repayment.setModifiedDate(repaymentPending.getPcreatedDate());
		repayment.setModifiedUser(repaymentPending.getPcreatedUser());
		repayment.setApproveStatus(approveStatus);
		
		if(approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
			repayment.setPenApprovedUser(user);
			repayment.setPenApprovedDate(validationService.getCreateOrModifyDate());
		} else {
			repayment.setPenRejectedUser(user);
			repayment.setPenRejectedDate(validationService.getCreateOrModifyDate());
		} 
		repayment.setSyncTs(validationService.getCreateOrModifyDate());
		
		repaymentRepository.saveAndFlush(repayment);
	}

	private RepaymentType validateRepaymentType(Long id, String name) {
		
		Optional<RepaymentType> isPresentRepaymentType = repaymentTypeRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE.toString());
		
		if(!isPresentRepaymentType.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.REPAYMENT_TYPE_ID, EntityPoint.REPAYMENT);
		
		return isPresentRepaymentType.get();
	}
	
	private RepaymentFrequency validateRepaymentFrequency(Long id, String name) {
		
		Optional<RepaymentFrequency> isPresentRepaymentFrequency = repaymentFrequencyRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE);
		
		if(!isPresentRepaymentFrequency.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.REPAYMENT_FREQUENCY_ID, EntityPoint.REPAYMENT);
		
		return isPresentRepaymentFrequency.get();
	}

	private RepaymentAmountType validateRepaymentAmountType(Long id, String name) {
	
		Optional<RepaymentAmountType> isPresentRepaymentAmountType = repaymentAmountTypeRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE);
		
		if(!isPresentRepaymentAmountType.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.REPAYMENT_AMOUNT_TYPE_ID, EntityPoint.REPAYMENT);
		
		return isPresentRepaymentAmountType.get();
	}
	@Override
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus) {

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(WorkflowType.REPAYMENT_TEMP_APPROVAL);
		WorkflowRulesResource workflowRulesResource = null;
		WorkflowType workflowType = WorkflowType.REPAYMENT_TEMP_APPROVAL;
		String user = LogginAuthentcation.getInstance().getUserName();
		CommonApproveStatus approveStatus= null;
	
		Optional<RepaymentPending> isPresentEligiPending= repaymentPendingRepository.findById(id);
		
		Optional<Repayment> eligi = repaymentRepository.findById(isPresentEligiPending.get().getRepayment().getId());
		
		if(!isPresentEligiPending.get().getRepayment().getApproveStatus().equals(CommonApproveStatus.PENDING))
			throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), "message");
		
		Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository.findById(isPresentEligiPending.get().getLendingWorkflow().getId());
	
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
		
		updateRepayment(isPresentEligiPending.get(), eligi.get(), approveStatus, user);
		
		return true;
	}
	
	@Override
	public Optional<RepaymentPending> getByPendingId(Long pendingId) {
		Optional<RepaymentPending> isPresentRepaymentPending= repaymentPendingRepository.findById(pendingId);
		if (isPresentRepaymentPending.isPresent()) {
			return Optional.ofNullable(isPresentRepaymentPending.get());
		}
		else {
			return Optional.empty();
		}
	}
	
	@Override
	public Page<RepaymentPending> searchRepaymentPending(String searchq, String status, String approveStatus, Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(status==null || status.isEmpty())
			status=null;
		if(approveStatus==null || approveStatus.isEmpty())
			approveStatus=null;
		return repaymentPendingRepository.searchRepaymentPending(searchq, status, approveStatus, pageable);
	}
}


