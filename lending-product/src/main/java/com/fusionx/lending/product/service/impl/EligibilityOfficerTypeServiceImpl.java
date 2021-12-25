package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Eligibility;
import com.fusionx.lending.product.domain.EligibilityOfficerType;
import com.fusionx.lending.product.domain.EligibilityOfficerTypeHistory;
import com.fusionx.lending.product.domain.EligibilityOfficerTypePending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.domain.OfficerEligibility;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.EligibilityOfficerTypeHistoryRepository;
import com.fusionx.lending.product.repository.EligibilityOfficerTypePendingRepository;
import com.fusionx.lending.product.repository.EligibilityOfficerTypeRepository;
import com.fusionx.lending.product.repository.EligibilityRepository;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.repository.OfficerEligibilityRepository;
import com.fusionx.lending.product.resources.EligibilityOfficerTypeAddResource;
import com.fusionx.lending.product.resources.EligibilityOfficerTypeUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.EligibilityOfficerTypeService;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * Eligibility Officer Type Service Implementation
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   01-07-2021      		     FX-6888	MiyuruW      Created
 *    
 *******************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class EligibilityOfficerTypeServiceImpl implements EligibilityOfficerTypeService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private EligibilityOfficerTypeRepository eligibilityOfficerTypeRepository;
	
	@Autowired
	private EligibilityRepository eligibilityRepository;
	
	@Autowired
	private OfficerEligibilityRepository officerEligibilityRepository;
	
	@Autowired 
	private ValidationService validationService;
	
	@Autowired 
	private EligibilityOfficerTypeHistoryRepository eligibilityOfficerTypeHistoryRepository;
	
	@Autowired 
	private EligibilityOfficerTypePendingRepository eligibilityOfficerTypePendingRepository;
	
	@Autowired
	private LendingWorkflowService lendingWorkflowService;
	
	@Autowired
	private LendingWorkflowRepository lendingWorkflowRepository;
	
	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";
	
	@Override
	public List<EligibilityOfficerType> findAll() {
		return eligibilityOfficerTypeRepository.findAll();
	}

	@Override
	public Optional<EligibilityOfficerType> findById(Long id) {
		Optional<EligibilityOfficerType> eligibilityOfficerType = eligibilityOfficerTypeRepository.findById(id);
		if (eligibilityOfficerType.isPresent()) {
			return Optional.ofNullable(eligibilityOfficerType.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<EligibilityOfficerType> findByStatus(String status) {
		return eligibilityOfficerTypeRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public List<EligibilityOfficerType> findByEligibilityId(Long eligibilityId) {
		return eligibilityOfficerTypeRepository.findByEligibilitysId(eligibilityId);
	}

	@Override
	public List<EligibilityOfficerType> findByOfficerTypeId(Long officerTypeId) {
		return eligibilityOfficerTypeRepository.findByOfficerEligibilitysId(officerTypeId);
	}

	@Override
	public Long saveAndValidateEligibilityOfficerType(String tenantId, String createdUser, EligibilityOfficerTypeAddResource eligibilityOfficerTypeAddResource) {
		
		LoggerRequest.getInstance().logInfo("EligibilityOfficerType********************************Validate Eligibility*********************************************");
		Eligibility eligibility = setEligibilityAndValidate(Long.parseLong(eligibilityOfficerTypeAddResource.getEligibilityId()));
		
		LoggerRequest.getInstance().logInfo("EligibilityOfficerType********************************Validate Officer Eligibility*********************************************");
		OfficerEligibility officerEligibility = setOfficerEligibilityAndValidate(Long.parseLong(eligibilityOfficerTypeAddResource.getOfficerTypeId()));
		
		LoggerRequest.getInstance().logInfo("EligibilityOfficerType********************************Validate Eligibility Officer Type Duplicate*********************************************");
		if(eligibilityOfficerTypeRepository.existsByEligibilitysIdAndOfficerEligibilitysId(Long.parseLong(eligibilityOfficerTypeAddResource.getEligibilityId()), Long.parseLong(eligibilityOfficerTypeAddResource.getOfficerTypeId())))
			throw new ValidateRecordException(environment.getProperty("eligibilityOfficerType.unique"), "message");
		
		LoggerRequest.getInstance().logInfo("EligibilityOfficerType********************************Save Eligibility Officer Type*********************************************");
		EligibilityOfficerType eligibilityOfficerType = saveEligibilityOfficerType(tenantId, createdUser, eligibility, officerEligibility, eligibilityOfficerTypeAddResource);
				
		return eligibilityOfficerType.getId();
	}
	
	private Eligibility setEligibilityAndValidate(Long eligibilityId) {
		Eligibility eligibility = null;
		Optional<Eligibility> eligibilityOptional = eligibilityRepository.findByIdAndStatus(eligibilityId, CommonStatus.ACTIVE);
		if (!eligibilityOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "eligibilityId");
		} else {
			eligibility = eligibilityOptional.get();
		}
		return eligibility;
	}
	
	private OfficerEligibility setOfficerEligibilityAndValidate(Long officerTypeId) {
		OfficerEligibility officerEligibility = null;
		Optional<OfficerEligibility> officerEligibilityOptional = officerEligibilityRepository.findByIdAndStatus(officerTypeId, CommonStatus.ACTIVE);
		if (!officerEligibilityOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "officerTypeId");
		} else {
			officerEligibility = officerEligibilityOptional.get();
		}
		return officerEligibility;
	}
	
	private EligibilityOfficerType saveEligibilityOfficerType(String tenantId, String createdUser, Eligibility eligibility, OfficerEligibility officerEligibility, EligibilityOfficerTypeAddResource eligibilityOfficerTypeAddResource) {
		
		EligibilityOfficerType eligibilityOfficerType = new EligibilityOfficerType();
		eligibilityOfficerType.setTenantId(tenantId);
		eligibilityOfficerType.setEligibilitys(eligibility);
		eligibilityOfficerType.setOfficerEligibilitys(officerEligibility);
		eligibilityOfficerType.setStatus(CommonStatus.valueOf(eligibilityOfficerTypeAddResource.getStatus()));
		eligibilityOfficerType.setCreatedUser(createdUser);
		eligibilityOfficerType.setCreatedDate(validationService.getCreateOrModifyDate());
		eligibilityOfficerType.setSyncTs(validationService.getCreateOrModifyDate());
		eligibilityOfficerTypeRepository.saveAndFlush(eligibilityOfficerType);
		return eligibilityOfficerType;
	}

	@Override
	public Long updateAndValidateEligibilityOfficerType(String tenantId, String createdUser, Long id, EligibilityOfficerTypeUpdateResource eligibilityOfficerTypeUpdateResource) {
		
		Optional<EligibilityOfficerType> isPresentEligibilityOfficerType = eligibilityOfficerTypeRepository.findById(id);
		if (!isPresentEligibilityOfficerType.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		} else if(!isPresentEligibilityOfficerType.get().getVersion().equals(Long.parseLong(eligibilityOfficerTypeUpdateResource.getVersion()))) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
		}
			
		LoggerRequest.getInstance().logInfo("EligibilityOfficerType********************************Validate Eligibility*********************************************");
		Eligibility eligibility = setEligibilityAndValidate(Long.parseLong(eligibilityOfficerTypeUpdateResource.getEligibilityId()));
		
		LoggerRequest.getInstance().logInfo("EligibilityOfficerType********************************Validate Officer Eligibility*********************************************");
		OfficerEligibility officerEligibility = setOfficerEligibilityAndValidate(Long.parseLong(eligibilityOfficerTypeUpdateResource.getOfficerTypeId()));
		
		LoggerRequest.getInstance().logInfo("EligibilityOfficerType********************************Validate Eligibility Officer Type Duplicate*********************************************");
		if(eligibilityOfficerTypeRepository.existsByEligibilitysIdAndOfficerEligibilitysIdAndIdNotIn(Long.parseLong(eligibilityOfficerTypeUpdateResource.getEligibilityId()), Long.parseLong(eligibilityOfficerTypeUpdateResource.getOfficerTypeId()), id))
			throw new ValidateRecordException(environment.getProperty("eligibilityOfficerType.unique"), "message");
		
		LoggerRequest.getInstance().logInfo("EligibilityOfficerType********************************Initiate Workflow*********************************************");
		approveOrGenerateWorkFlow(tenantId, createdUser, isPresentEligibilityOfficerType.get(), eligibility, officerEligibility, eligibilityOfficerTypeUpdateResource);
		
		return isPresentEligibilityOfficerType.get().getId();
	}

	private EligibilityOfficerType updateEligibilityOfficerType(EligibilityOfficerTypePending eligibilityOfficerTypePending, EligibilityOfficerType eligi, CommonApproveStatus approveStatus, String user) {
		
		EligibilityOfficerType eligibilityOfficerType = eligi;
		
		LoggerRequest.getInstance().logInfo("EligibilityOfficerType********************************Save Eligibility Officer Type History*********************************************");
		saveEligibilityOfficerTypeHistory(user, eligibilityOfficerType);
		
		if(approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
			eligibilityOfficerType.setEligibilitys(eligibilityOfficerTypePending.getEligibilitys());
			eligibilityOfficerType.setOfficerEligibilitys(eligibilityOfficerTypePending.getOfficerEligibilitys());
			eligibilityOfficerType.setStatus(eligibilityOfficerTypePending.getStatus());
			eligibilityOfficerType.setModifiedUser(eligibilityOfficerTypePending.getCreatedUser());
			eligibilityOfficerType.setModifiedDate(eligibilityOfficerTypePending.getCreatedDate());
		}
		
		eligibilityOfficerType.setApproveStatus(approveStatus);
		
		if(approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
			eligibilityOfficerType.setPenApprovedUser(user);
			eligibilityOfficerType.setPenApprovedDate(validationService.getCreateOrModifyDate());
		} else {
			eligibilityOfficerType.setPenRejectedUser(user);
			eligibilityOfficerType.setPenRejectedDate(validationService.getCreateOrModifyDate());
		}
		
		eligibilityOfficerType.setSyncTs(validationService.getCreateOrModifyDate());
		eligibilityOfficerTypeRepository.saveAndFlush(eligibilityOfficerType);
		return eligibilityOfficerType;
	}
	
	private void saveEligibilityOfficerTypeHistory(String historyCreatedUser, EligibilityOfficerType eligibilityOfficerType) {
		
		EligibilityOfficerTypeHistory eligibilityOfficerTypeHistory = new EligibilityOfficerTypeHistory();
		eligibilityOfficerTypeHistory.setEligibilityOfficerTypeId(eligibilityOfficerType.getId());
		eligibilityOfficerTypeHistory.setTenantId(eligibilityOfficerType.getTenantId());
		eligibilityOfficerTypeHistory.setEligibilityId(eligibilityOfficerType.getEligibilitys().getId());
		eligibilityOfficerTypeHistory.setOfficerTypeId(eligibilityOfficerType.getOfficerEligibilitys().getId());
		eligibilityOfficerTypeHistory.setStatus(eligibilityOfficerType.getStatus());
		eligibilityOfficerTypeHistory.setApproveStatus(eligibilityOfficerType.getApproveStatus());
		eligibilityOfficerTypeHistory.setCreatedUser(eligibilityOfficerType.getCreatedUser());
		eligibilityOfficerTypeHistory.setCreatedDate(eligibilityOfficerType.getCreatedDate());
		eligibilityOfficerTypeHistory.setModifiedUser(eligibilityOfficerType.getModifiedUser());
		eligibilityOfficerTypeHistory.setModifiedDate(eligibilityOfficerType.getModifiedDate());
		eligibilityOfficerTypeHistory.setPenApprovedUser(eligibilityOfficerType.getPenApprovedUser());
		eligibilityOfficerTypeHistory.setPenApprovedDate(eligibilityOfficerType.getPenApprovedDate());
		eligibilityOfficerTypeHistory.setPenRejectedUser(eligibilityOfficerType.getPenRejectedUser());
		eligibilityOfficerTypeHistory.setPenRejectedDate(eligibilityOfficerType.getPenRejectedDate());
		eligibilityOfficerTypeHistory.setHisCreatedUser(historyCreatedUser);
		eligibilityOfficerTypeHistory.setHisCreatedDate(validationService.getCreateOrModifyDate());
		eligibilityOfficerTypeHistory.setVersion(eligibilityOfficerType.getVersion());
		eligibilityOfficerTypeHistory.setSyncTs(validationService.getCreateOrModifyDate());
		eligibilityOfficerTypeHistoryRepository.saveAndFlush(eligibilityOfficerTypeHistory);
	}

	private void approveOrGenerateWorkFlow(String tenantId, String user, EligibilityOfficerType eligibilityOfficerType, Eligibility eligibility, OfficerEligibility officerEligibility, EligibilityOfficerTypeUpdateResource eligibilityOfficerTypeUpdateResource) {
		WorkflowRulesResource workflowRulesResource = null;
		Long processId = null;
		WorkflowType workflowType = WorkflowType.ELIGI_TEMP_APPROVAL;
		LendingWorkflow lendingWorkflow =  null;

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

		workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);

		if(workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
			processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
				if(processId != null) {
					lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
					saveEligibilityOfficerTypePending(tenantId, eligibilityOfficerType, eligibility, officerEligibility, eligibilityOfficerTypeUpdateResource, lendingWorkflow, user);
				}
		} else {
			EligibilityOfficerTypePending eligibilityOfficerTypePending = saveEligibilityOfficerTypePending(tenantId, eligibilityOfficerType, eligibility, officerEligibility, eligibilityOfficerTypeUpdateResource, lendingWorkflow, user);
			
			LoggerRequest.getInstance().logInfo("EligibilityOfficerType********************************Update Eligibility Officer Type*********************************************");
			updateEligibilityOfficerType(eligibilityOfficerTypePending, eligibilityOfficerType, CommonApproveStatus.APPROVED, user);
		}
	}
	
	private EligibilityOfficerTypePending saveEligibilityOfficerTypePending(String tenantId, EligibilityOfficerType eligibilityOfficerType, Eligibility eligibility, OfficerEligibility officerEligibility, EligibilityOfficerTypeUpdateResource eligibilityOfficerTypeUpdateResource, LendingWorkflow lendingWorkflow, String user) {
		
		LoggerRequest.getInstance().logInfo("EligibilityOfficerType********************************Save Eligibility Officer Type History*********************************************");
		saveEligibilityOfficerTypeHistory(user, eligibilityOfficerType);
		
		EligibilityOfficerType eligi = eligibilityOfficerType;
		eligi.setApproveStatus(CommonApproveStatus.PENDING);
		eligi.setModifiedUser(user);
		eligi.setModifiedDate(validationService.getCreateOrModifyDate());
		eligi.setSyncTs(validationService.getCreateOrModifyDate());
		eligibilityOfficerTypeRepository.saveAndFlush(eligi);
		
		EligibilityOfficerTypePending eligibilityOfficerTypePending = new EligibilityOfficerTypePending();
		eligibilityOfficerTypePending.setTenantId(tenantId);
		if(lendingWorkflow != null) {
			eligibilityOfficerTypePending.setLendingWorkflow(lendingWorkflow);
		}	
		eligibilityOfficerTypePending.setEligibilityOfficerTypes(eligibilityOfficerType);
		eligibilityOfficerTypePending.setEligibilitys(eligibility);
		eligibilityOfficerTypePending.setOfficerEligibilitys(officerEligibility);
		eligibilityOfficerTypePending.setStatus(CommonStatus.valueOf(eligibilityOfficerTypeUpdateResource.getStatus()));
		eligibilityOfficerTypePending.setCreatedDate(validationService.getCreateOrModifyDate());
		eligibilityOfficerTypePending.setCreatedUser(user);
		eligibilityOfficerTypePending.setSyncTs(validationService.getCreateOrModifyDate());
		eligibilityOfficerTypePendingRepository.save(eligibilityOfficerTypePending);
		
		return eligibilityOfficerTypePending;
	}
	
	@Override
	public Boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus) {
		
		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(WorkflowType.ELIGI_TEMP_APPROVAL);
		WorkflowRulesResource workflowRulesResource = null;
		WorkflowType workflowType = WorkflowType.ELIGI_TEMP_APPROVAL;
		String user = LogginAuthentcation.getInstance().getUserName();
		CommonApproveStatus approveStatus= null;
	
		Optional<EligibilityOfficerTypePending> isPresentEligibilityOfficerTypePending = eligibilityOfficerTypePendingRepository.findById(id);
		
		Optional<EligibilityOfficerType> eligiOfficerType = eligibilityOfficerTypeRepository.findById(isPresentEligibilityOfficerTypePending.get().getEligibilityOfficerTypes().getId());
		
		if(!isPresentEligibilityOfficerTypePending.get().getEligibilityOfficerTypes().getApproveStatus().equals(CommonApproveStatus.PENDING))
			throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), "message");
		
		Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository.findById(isPresentEligibilityOfficerTypePending.get().getLendingWorkflow().getId());
	
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
	
		LoggerRequest.getInstance().logInfo("EligibilityOfficerType********************************Update Workflow*********************************************");
		lendingWorkflowService.update(lendingWorkflow.get(), workflowStatus, user);
		
		LoggerRequest.getInstance().logInfo("EligibilityOfficerType********************************Update Eligibility Officer Type*********************************************");
		updateEligibilityOfficerType(isPresentEligibilityOfficerTypePending.get(), eligiOfficerType.get(), approveStatus, user);
		
		return true;
	}

	@Override
	public Optional<EligibilityOfficerTypePending> getByPendingId(Long pendingId) {
		Optional<EligibilityOfficerTypePending> isPresentEligibilityOfficerTypePending = eligibilityOfficerTypePendingRepository.findById(pendingId);
		if (isPresentEligibilityOfficerTypePending.isPresent()) {
			return Optional.ofNullable(isPresentEligibilityOfficerTypePending.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Page<EligibilityOfficerTypePending> searchEligibilityOfficerType(String searchq, String status, String approveStatus, Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(status==null || status.isEmpty())
			status=null;
		if(approveStatus==null || approveStatus.isEmpty())
			approveStatus=null;
		return eligibilityOfficerTypePendingRepository.searchEligibilityOfficerTypePending(searchq, status, approveStatus, pageable);
	}
	
}
