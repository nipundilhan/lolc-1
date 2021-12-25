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
import com.fusionx.lending.product.domain.CommonListItem;
import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.FeeChargePending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.domain.ResidencyEligibility;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonListCode;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.CommonListItemRepository;
import com.fusionx.lending.product.repository.FeeChargePendingRepository;
import com.fusionx.lending.product.repository.FeeChargeRepository;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.resources.FeeChargeAddResource;
import com.fusionx.lending.product.resources.FeeChargeUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.FeeChargeCapService;
import com.fusionx.lending.product.service.FeeChargeDetailAdhocService;
import com.fusionx.lending.product.service.FeeChargeDetailOptionalService;
import com.fusionx.lending.product.service.FeeChargeDetailsService;
import com.fusionx.lending.product.service.FeeChargeHistoryService;
import com.fusionx.lending.product.service.FeeChargeService;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * FeeCharge  Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-06-2021             				 MenukaJ        Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class FeeChargeServiceImpl extends MessagePropertyBase implements FeeChargeService {
	
	@Autowired
	private FeeChargeRepository feeChargeRepository;
	
	@Autowired 
	private ValidationService validationService;
	
	@Autowired
	private FeeChargeHistoryService feeChargeHistoryService;
	
	@Autowired
	private FeeChargePendingRepository feeChargePendingRepository;
	
	@Autowired
	private LendingWorkflowService lendingWorkflowService;
	
	@Autowired
	private LendingWorkflowRepository lendingWorkflowRepository;
	
	@Autowired
	private CommonListItemRepository commonListItemRepository;
	
	@Autowired
	private FeeChargeDetailOptionalService feeChargeDetailOptionalService;
	
	@Autowired
	private FeeChargeDetailsService feeChargeDetailsService;
	
	@Autowired
	private FeeChargeCapService feeChargeCapService;
	
	@Autowired
	private FeeChargeDetailAdhocService feeChargeDetailAdhocService;
	
	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";

	@Override
	public List<FeeCharge> getAll() {
		return feeChargeRepository.findAll();
	}

	@Override
	public Optional<FeeCharge> getById(Long id) {
		Optional<FeeCharge> isPresentFeeCharge= feeChargeRepository.findById(id);
		if (isPresentFeeCharge.isPresent()) {
			return Optional.ofNullable(isPresentFeeCharge.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<FeeCharge> getByCode(String code) {
		Optional<FeeCharge> isPresentFeeCharge= feeChargeRepository.findByCode(code);
		if (isPresentFeeCharge.isPresent()) {
			return Optional.ofNullable(isPresentFeeCharge.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<FeeCharge> getByName(String name) {
		return feeChargeRepository.findByNameContaining(name);
	}

	@Override
	public List<FeeCharge> getByStatus(String status) {
		return feeChargeRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public FeeCharge addFeeCharge(String tenantId,
			FeeChargeAddResource feeChargeAddResource) {
		
		Optional<FeeCharge> isPresentFeeCharge = feeChargeRepository.findByCode(feeChargeAddResource.getCode());
        
        if (isPresentFeeCharge.isPresent()) 
        	throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.FEE_CHARGE);
        
        CommonListItem feeChargeType = validateFeeChargeType(validationService.stringToLong(feeChargeAddResource.getFeeChargeTypeId()), feeChargeAddResource.getFeeChargeTypeName());
        
        FeeCharge feeCharge = new FeeCharge();
        feeCharge.setTenantId(tenantId);
        feeCharge.setCode(feeChargeAddResource.getCode());
        feeCharge.setName(feeChargeAddResource.getName());
        feeCharge.setFeeChargeType(feeChargeType);
        feeCharge.setStatus(CommonStatus.valueOf(feeChargeAddResource.getStatus()));
        feeCharge.setCreatedDate(validationService.getCreateOrModifyDate());
        feeCharge.setSyncTs(validationService.getCreateOrModifyDate());
        feeCharge.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        
        return feeChargeRepository.save(feeCharge);
	}
	
	private CommonListItem validateFeeChargeType(Long id, String name) {
		
		Optional<CommonListItem> commonListItem = commonListItemRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE);
		
		if(!commonListItem.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.FEE_CHARGE_TYPE_ID, EntityPoint.FEE_CHARGE);
		if(!commonListItem.get().getReferenceCode().toString().equalsIgnoreCase(CommonListCode.FEECHARGETYPE.toString()))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.FEE_CHARGE_TYPE_ID, EntityPoint.FEE_CHARGE);
		return commonListItem.get();
	}

	@Override
	public FeeCharge updateFeeCharge(String tenantId, Long id, FeeChargeUpdateResource feeChargeUpdateResource) {
		
		Optional<FeeCharge> isPresentFeeCharge= feeChargeRepository.findById(id);
		
		if(!isPresentFeeCharge.get().getVersion().equals(Long.parseLong(feeChargeUpdateResource.getVersion())))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.FEE_CHARGE);
		
		if(!isPresentFeeCharge.get().getCode().equalsIgnoreCase(feeChargeUpdateResource.getCode()))
			throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE, EntityPoint.FEE_CHARGE);
		
		List<FeeChargePending> pendingList = feeChargePendingRepository.findAllByFeeChargeAndApproveStatus(isPresentFeeCharge.get(),CommonApproveStatus.PENDING);
		if(!pendingList.isEmpty()) {
			throw new ValidateRecordException(environment.getProperty("feeCharge-pending-active-exists"), MESSAGE);
		}
		LoggerRequest.getInstance().logInfo("Fee Charge********************************Workflow Call*********************************************");
		approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(), isPresentFeeCharge.get(), feeChargeUpdateResource);
		
		return isPresentFeeCharge.get();
	}
	
	private void approveOrGenerateWorkFlow(String tenantId, String user, FeeCharge feeCharge, FeeChargeUpdateResource feeChargeUpdateResource) {
		WorkflowRulesResource workflowRulesResource = null;
		Long processId = null;
		WorkflowType workflowType = WorkflowType.FEE_CHARGE_TEMP_APPROVAL;
		LendingWorkflow lendingWorkflow =  null;

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

		LoggerRequest.getInstance().logInfo("Fee Charge********************************Get Workflow Rules*********************************************");
		workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);

		if(workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
			processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
				if(processId != null) {
					lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
					savePendingFeeCharge(tenantId, feeCharge, feeChargeUpdateResource, lendingWorkflow, user);
				}
		} else {
			FeeChargePending feeChargePending = savePendingFeeCharge(tenantId, feeCharge, feeChargeUpdateResource, lendingWorkflow, user);
			updateFeeCharge(feeChargePending, feeCharge, CommonApproveStatus.APPROVED, user);
		}
	}
	
	private FeeChargePending savePendingFeeCharge(String tenantId, FeeCharge feeCharge, FeeChargeUpdateResource feeChargeUpdateResource, LendingWorkflow lendingWorkflow, String user) {
		
		LoggerRequest.getInstance().logInfo("Fee Charge********************************Save History*********************************************");
		feeChargeHistoryService.saveHistory(tenantId, feeCharge, user);
		
		FeeCharge charge = feeCharge;
//		charge.setApproveStatus(CommonApproveStatus.PENDING);
		charge.setModifiedUser(user);
		charge.setModifiedDate(validationService.getCreateOrModifyDate());
		charge.setSyncTs(validationService.getCreateOrModifyDate());
		feeChargeRepository.saveAndFlush(charge);
		
		FeeChargePending feeChargePending = new FeeChargePending();
		
		CommonListItem feeChargeType = validateFeeChargeType(validationService.stringToLong(feeChargeUpdateResource.getFeeChargeTypeId()), feeChargeUpdateResource.getFeeChargeTypeName());
		
		feeChargePending.setTenantId(tenantId);
		if(lendingWorkflow != null)
			feeChargePending.setLendingWorkflow(lendingWorkflow);
		feeChargePending.setFeeCharge(feeCharge);
		feeChargePending.setCode(feeChargeUpdateResource.getCode());
		feeChargePending.setName(feeChargeUpdateResource.getName());
		feeChargePending.setFeeChargeType(feeChargeType);
		feeChargePending.setStatus(CommonStatus.valueOf(feeChargeUpdateResource.getStatus()));
		feeChargePending.setApproveStatus(CommonApproveStatus.PENDING);
		feeChargePending.setPcreatedDate(validationService.getCreateOrModifyDate());
		feeChargePending.setPcreatedUser(user);
		feeChargePending.setSyncTs(validationService.getCreateOrModifyDate());
		
		return feeChargePendingRepository.save(feeChargePending);
		
	}
	
	private void updateFeeCharge(FeeChargePending fcp, FeeCharge chrage, CommonApproveStatus approveStatus, String user) {
		
		LoggerRequest.getInstance().logInfo("Fee Charge********************************Save History*********************************************");
		feeChargeHistoryService.saveHistory(chrage.getTenantId(), chrage, user);
		
		FeeCharge feeCharge = chrage;
		FeeChargePending feeChargePending = fcp;
		
		if(approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
			feeCharge.setFeeChargeType(feeChargePending.getFeeChargeType());
			feeCharge.setName(feeChargePending.getName());
			feeCharge.setStatus(feeChargePending.getStatus());
			feeCharge.setModifiedDate(feeChargePending.getPcreatedDate());
			feeCharge.setModifiedUser(feeChargePending.getPcreatedUser());
			
			feeChargePending.setApproveStatus(approveStatus);
			feeChargePending.setPenApprovedUser(user);
			feeChargePending.setPenApprovedDate(validationService.getCreateOrModifyDate());
		} else {
			feeChargePending.setApproveStatus(approveStatus);
			feeChargePending.setPenRejectedUser(user);
			feeChargePending.setPenRejectedDate(validationService.getCreateOrModifyDate());
		} 
		
		feeCharge.setSyncTs(validationService.getCreateOrModifyDate());
		feeChargeRepository.saveAndFlush(feeCharge);
		
		
		feeChargePending.setModifiedDate(feeChargePending.getPcreatedDate());
		feeChargePending.setModifiedUser(feeChargePending.getPcreatedUser());
		feeChargePending.setSyncTs(validationService.getCreateOrModifyDate());
		feeChargePendingRepository.saveAndFlush(feeChargePending);

	}

	@Override
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus) {

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(WorkflowType.FEE_CHARGE_TEMP_APPROVAL);
		WorkflowRulesResource workflowRulesResource = null;
		WorkflowType workflowType = WorkflowType.FEE_CHARGE_TEMP_APPROVAL;
		String user = LogginAuthentcation.getInstance().getUserName();
		CommonApproveStatus approveStatus= null;
	
		Optional<FeeChargePending> isPresentFeeChargePending= feeChargePendingRepository.findById(id);
		
		Optional<FeeCharge> feeCharge = feeChargeRepository.findById(isPresentFeeChargePending.get().getFeeCharge().getId());
		
		if(!isPresentFeeChargePending.get().getApproveStatus().equals(CommonApproveStatus.PENDING))
			throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), "message");
		
		Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository.findById(isPresentFeeChargePending.get().getLendingWorkflow().getId());
	
		LoggerRequest.getInstance().logInfo("Fee Charge********************************Get Workflow Rules*********************************************");
		workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);
	
		if(workflowRulesResource.getApprovalAllowed().equalsIgnoreCase(CommonStatus.NO.toString())) {
			if(lendingWorkflow.get().getCreatedUser().equalsIgnoreCase(LogginAuthentcation.getInstance().getUserName()))
				throw new ValidateRecordException(environment.getProperty("cannot.process.approve"), "message");
		}
	
		if(workflowStatus.toString().equalsIgnoreCase(WorkflowStatus.COMPLETE.toString())) {
			LoggerRequest.getInstance().logInfo("Fee Charge********************************Approve Workflow*********************************************");
			validationService.approveWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user, tenantId);
			approveStatus = CommonApproveStatus.APPROVED;
			
			feeChargeDetailOptionalService.approvePendingFeeChargeDetailsOptional(isPresentFeeChargePending.get().getId());
			feeChargeDetailAdhocService.approvePendingAdhoc(isPresentFeeChargePending.get().getId());
		}
		else {
			LoggerRequest.getInstance().logInfo("Fee Charge********************************Abort Workflow*********************************************");
			validationService.abortedWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user, tenantId);
			approveStatus = CommonApproveStatus.REJECTED;
		}
	
		lendingWorkflowService.update(lendingWorkflow.get(), workflowStatus, user);
		
		LoggerRequest.getInstance().logInfo("Fee Charge********************************update*********************************************");
		updateFeeCharge(isPresentFeeChargePending.get(), feeCharge.get(), approveStatus, user);
		feeChargeDetailsService.approvalPendingFeeChargeDetails(isPresentFeeChargePending.get().getId(), approveStatus);
		feeChargeCapService.approvalPendingFeeChargeCap(isPresentFeeChargePending.get().getId(), approveStatus);
		return true;
	}

	@Override
	public Optional<FeeChargePending> getByPendingId(Long pendingId) {
		Optional<FeeChargePending> isPresentFeeChargePending= feeChargePendingRepository.findById(pendingId);
		if (isPresentFeeChargePending.isPresent()) {
			return Optional.ofNullable(isPresentFeeChargePending.get());
		}
		else {
			return Optional.empty();
		}
	}
	
	@Override
	public Page<FeeChargePending> searchFeeChargePending(String searchq, String status, String approveStatus, Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(status==null || status.isEmpty())
			status=null;
		if(approveStatus==null || approveStatus.isEmpty())
			approveStatus=null;
		return feeChargePendingRepository.searchFeeChargePending(searchq, status, approveStatus, pageable);
	}
	
	@Override
	public Optional<FeeCharge> findById(String tenantId, Long feeChargeId) {
		Optional<FeeCharge> feeChargeOptional = feeChargeRepository.findById(feeChargeId);
		if(feeChargeOptional.isPresent()) {
			FeeCharge feeCharge = feeChargeOptional.get();
			//setResidencyTypeName(tenantId, residencyEligi);
			return Optional.ofNullable(feeCharge);
		}else {
			return Optional.empty();
		}
	}
}
