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
import com.fusionx.lending.product.domain.EligibilityCurrency;
import com.fusionx.lending.product.domain.EligibilityCurrencyHistory;
import com.fusionx.lending.product.domain.EligibilityCurrencyPending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.EligibilityCurrencyHistoryRepository;
import com.fusionx.lending.product.repository.EligibilityCurrencyPendingRepository;
import com.fusionx.lending.product.repository.EligibilityCurrencyRepository;
import com.fusionx.lending.product.repository.EligibilityRepository;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.resources.Currency;
import com.fusionx.lending.product.resources.EligibilityCurrencyAddResource;
import com.fusionx.lending.product.resources.EligibilityCurrencyUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.EligibilityCurrencyService;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * Eligibility Currency Service Implementation
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   01-07-2021      		     FX-6891	MiyuruW      Created
 *    
 *******************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class EligibilityCurrencyServiceImpl implements EligibilityCurrencyService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private EligibilityCurrencyRepository eligibilityCurrencyRepository;
	
	@Autowired
	private EligibilityRepository eligibilityRepository;
	
	@Autowired 
	private ValidationService validationService;
	
	@Autowired 
	private EligibilityCurrencyHistoryRepository eligibilityCurrencyHistoryRepository;
	
	@Autowired 
	private EligibilityCurrencyPendingRepository eligibilityCurrencyPendingRepository;
	
	@Autowired
	private LendingWorkflowService lendingWorkflowService;
	
	@Autowired
	private LendingWorkflowRepository lendingWorkflowRepository;
	
	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";

	@Override
	public List<EligibilityCurrency> findAll(String tenantId) {
		List<EligibilityCurrency> eligibilityCurrencyList = eligibilityCurrencyRepository.findAll();
		
		for(EligibilityCurrency eligibilityCurrency : eligibilityCurrencyList) {
			setCurrencyFields(tenantId, eligibilityCurrency);
		}
		return eligibilityCurrencyList;
	}
	
	@Override
	public Optional<EligibilityCurrency> findById(String tenantId, Long id) {
		Optional<EligibilityCurrency> eligibilityCurrencyOptional = eligibilityCurrencyRepository.findById(id);
		if (eligibilityCurrencyOptional.isPresent()) {
			EligibilityCurrency eligibilityCurrency = eligibilityCurrencyOptional.get();
			setCurrencyFields(tenantId, eligibilityCurrency);
			return Optional.ofNullable(eligibilityCurrency);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<EligibilityCurrency> findByStatus(String tenantId, String status) {
		List<EligibilityCurrency> eligibilityCurrencyList = eligibilityCurrencyRepository.findByStatus(CommonStatus.valueOf(status));
		
		for(EligibilityCurrency eligibilityCurrency : eligibilityCurrencyList) {
			setCurrencyFields(tenantId, eligibilityCurrency);
		}
		return eligibilityCurrencyList;
	}

	@Override
	public List<EligibilityCurrency> findByEligibilityId(String tenantId, Long eligibilityId) {
		List<EligibilityCurrency> eligibilityCurrencyList = eligibilityCurrencyRepository.findByEligibilitysId(eligibilityId);
		
		for(EligibilityCurrency eligibilityCurrency : eligibilityCurrencyList) {
			setCurrencyFields(tenantId, eligibilityCurrency);
		}
		return eligibilityCurrencyList;
	}

	@Override
	public List<EligibilityCurrency> findByCurrencyId(String tenantId, Long currencyId) {
		List<EligibilityCurrency> eligibilityCurrencyList = eligibilityCurrencyRepository.findByCurrencyId(currencyId);
		
		for(EligibilityCurrency eligibilityCurrency : eligibilityCurrencyList) {
			setCurrencyFields(tenantId, eligibilityCurrency);
		}
		return eligibilityCurrencyList;
	}

	private void setCurrencyFields(String tenantId, EligibilityCurrency eligibilityCurrency) {
		Currency currency = validationService.getCurrency(tenantId, eligibilityCurrency.getCurrencyId());
		if (currency != null && currency.getServiceStatus() == null) {
			eligibilityCurrency.setCurrencyName(currency.getCurrencyName());
			eligibilityCurrency.setCurrencyCode(currency.getCurrencyCode());
		}
	 }
	
	private void setCurrencyFieldsForPending(String tenantId, EligibilityCurrencyPending eligibilityCurrencyPending) {
		Currency currency = validationService.getCurrency(tenantId, eligibilityCurrencyPending.getCurrencyId());
		if (currency != null && currency.getServiceStatus() == null) {
			eligibilityCurrencyPending.setCurrencyName(currency.getCurrencyName());
			eligibilityCurrencyPending.setCurrencyCode(currency.getCurrencyCode());
		}
	 }
	
	@Override
	public Long saveAndValidateEligibilityCurrency(String tenantId, String createdUser, EligibilityCurrencyAddResource eligibilityCurrencyAddResource) {
		
		LoggerRequest.getInstance().logInfo("EligibilityCurrency********************************Validate Eligibility*********************************************");
		Eligibility eligibility = setEligibilityAndValidate(Long.parseLong(eligibilityCurrencyAddResource.getEligibilityId()));
		
		LoggerRequest.getInstance().logInfo("EligibilityCurrency********************************Validate Currency*********************************************");
		validationService.validateCurrency(tenantId, eligibilityCurrencyAddResource.getCurrencyId(), eligibilityCurrencyAddResource.getCurrencyName(), EntityPoint.ELIGIBILITY_CURRENCY);
		
		LoggerRequest.getInstance().logInfo("EligibilityCurrency********************************Validate Eligibility Currency Duplicate*********************************************");
		if(eligibilityCurrencyRepository.existsByEligibilitysIdAndCurrencyId(Long.parseLong(eligibilityCurrencyAddResource.getEligibilityId()), Long.parseLong(eligibilityCurrencyAddResource.getCurrencyId())))
			throw new ValidateRecordException(environment.getProperty("eligibilityCurrency.unique"), "message");
		
		LoggerRequest.getInstance().logInfo("EligibilityCurrency********************************Save Eligibility Currency*********************************************");
		EligibilityCurrency eligibilityCurrency = saveEligibilityCurrency(tenantId, createdUser, eligibility, eligibilityCurrencyAddResource);
				
		return eligibilityCurrency.getId();
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
	
	private EligibilityCurrency saveEligibilityCurrency(String tenantId, String createdUser, Eligibility eligibility, EligibilityCurrencyAddResource eligibilityCurrencyAddResource) {
		
		EligibilityCurrency eligibilityCurrency = new EligibilityCurrency();
		eligibilityCurrency.setTenantId(tenantId);
		eligibilityCurrency.setEligibilitys(eligibility);
		eligibilityCurrency.setCurrencyId(Long.parseLong(eligibilityCurrencyAddResource.getCurrencyId()));
		eligibilityCurrency.setStatus(CommonStatus.valueOf(eligibilityCurrencyAddResource.getStatus()));
		eligibilityCurrency.setCreatedUser(createdUser);
		eligibilityCurrency.setCreatedDate(validationService.getCreateOrModifyDate());
		eligibilityCurrency.setSyncTs(validationService.getCreateOrModifyDate());
		eligibilityCurrencyRepository.saveAndFlush(eligibilityCurrency);
		return eligibilityCurrency;
	}

	@Override
	public Long updateAndValidateEligibilityCurrency(String tenantId, String createdUser, Long id, EligibilityCurrencyUpdateResource eligibilityCurrencyUpdateResource) {
		
		Optional<EligibilityCurrency> isPresentEligibilityCurrency = eligibilityCurrencyRepository.findById(id);
		if (!isPresentEligibilityCurrency.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		} else if(!isPresentEligibilityCurrency.get().getVersion().equals(Long.parseLong(eligibilityCurrencyUpdateResource.getVersion()))) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
		}
		
		LoggerRequest.getInstance().logInfo("EligibilityCurrency********************************Validate Eligibility*********************************************");
		Eligibility eligibility = setEligibilityAndValidate(Long.parseLong(eligibilityCurrencyUpdateResource.getEligibilityId()));
		
		LoggerRequest.getInstance().logInfo("EligibilityCurrency********************************Validate Currency*********************************************");
		validationService.validateCurrency(tenantId, eligibilityCurrencyUpdateResource.getCurrencyId(), eligibilityCurrencyUpdateResource.getCurrencyName(), EntityPoint.ELIGIBILITY_CURRENCY);
		
		LoggerRequest.getInstance().logInfo("EligibilityCurrency********************************Validate Eligibility Currency Duplicate*********************************************");
		if(eligibilityCurrencyRepository.existsByEligibilitysIdAndCurrencyIdAndIdNotIn(Long.parseLong(eligibilityCurrencyUpdateResource.getEligibilityId()), Long.parseLong(eligibilityCurrencyUpdateResource.getCurrencyId()), id))
			throw new ValidateRecordException(environment.getProperty("eligibilityCurrency.unique"), "message");
		
		LoggerRequest.getInstance().logInfo("EligibilityCurrency********************************Initiate Workflow*********************************************");
		approveOrGenerateWorkFlow(tenantId, createdUser, isPresentEligibilityCurrency.get(), eligibility, eligibilityCurrencyUpdateResource);
		
		return isPresentEligibilityCurrency.get().getId();
	}
	
	private EligibilityCurrency updateEligibilityCurrency(EligibilityCurrencyPending eligibilityCurrencyPending, EligibilityCurrency eligi, CommonApproveStatus approveStatus, String user) {
		
		EligibilityCurrency eligibilityCurrency = eligi;
		
		LoggerRequest.getInstance().logInfo("EligibilityCurrency********************************Save Eligibility Currency History*********************************************");
		saveEligibilityCurrencyHistory(user, eligibilityCurrency);
		
		if(approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
			eligibilityCurrency.setEligibilitys(eligibilityCurrencyPending.getEligibilitys());
			eligibilityCurrency.setCurrencyId(eligibilityCurrency.getCurrencyId());
			eligibilityCurrency.setStatus(eligibilityCurrencyPending.getStatus());
			eligibilityCurrency.setModifiedUser(eligibilityCurrencyPending.getCreatedUser());
			eligibilityCurrency.setModifiedDate(eligibilityCurrencyPending.getCreatedDate());
		}
		
		eligibilityCurrency.setApproveStatus(approveStatus);
		
		if(approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
			eligibilityCurrency.setPenApprovedUser(user);
			eligibilityCurrency.setPenApprovedDate(validationService.getCreateOrModifyDate());
		} else {
			eligibilityCurrency.setPenRejectedUser(user);
			eligibilityCurrency.setPenRejectedDate(validationService.getCreateOrModifyDate());
		}
		
		eligibilityCurrency.setSyncTs(validationService.getCreateOrModifyDate());
		eligibilityCurrencyRepository.saveAndFlush(eligibilityCurrency);
		return eligibilityCurrency;
	}

	private void saveEligibilityCurrencyHistory(String historyCreatedUser, EligibilityCurrency eligibilityCurrency) {
		
		EligibilityCurrencyHistory eligibilityCurrencyHistory = new EligibilityCurrencyHistory();
		eligibilityCurrencyHistory.setEligibilityCurrencyId(eligibilityCurrency.getId());
		eligibilityCurrencyHistory.setTenantId(eligibilityCurrency.getTenantId());
		eligibilityCurrencyHistory.setEligibilityId(eligibilityCurrency.getEligibilitys().getId());
		eligibilityCurrencyHistory.setCurrencyId(eligibilityCurrency.getCurrencyId());
		eligibilityCurrencyHistory.setStatus(eligibilityCurrency.getStatus());
		eligibilityCurrencyHistory.setApproveStatus(eligibilityCurrency.getApproveStatus());
		eligibilityCurrencyHistory.setCreatedUser(eligibilityCurrency.getCreatedUser());
		eligibilityCurrencyHistory.setCreatedDate(eligibilityCurrency.getCreatedDate());
		eligibilityCurrencyHistory.setModifiedUser(eligibilityCurrency.getModifiedUser());
		eligibilityCurrencyHistory.setModifiedDate(eligibilityCurrency.getModifiedDate());
		eligibilityCurrencyHistory.setPenApprovedUser(eligibilityCurrency.getPenApprovedUser());
		eligibilityCurrencyHistory.setPenApprovedDate(eligibilityCurrency.getPenApprovedDate());
		eligibilityCurrencyHistory.setPenRejectedUser(eligibilityCurrency.getPenRejectedUser());
		eligibilityCurrencyHistory.setPenRejectedDate(eligibilityCurrency.getPenRejectedDate());
		eligibilityCurrencyHistory.setHisCreatedUser(historyCreatedUser);
		eligibilityCurrencyHistory.setHisCreatedDate(validationService.getCreateOrModifyDate());
		eligibilityCurrencyHistory.setVersion(eligibilityCurrency.getVersion());
		eligibilityCurrencyHistory.setSyncTs(validationService.getCreateOrModifyDate());
		eligibilityCurrencyHistoryRepository.saveAndFlush(eligibilityCurrencyHistory);
	}

	private void approveOrGenerateWorkFlow(String tenantId, String user, EligibilityCurrency eligibilityCurrency, Eligibility eligibility, EligibilityCurrencyUpdateResource eligibilityCurrencyUpdateResource) {
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
					saveEligibilityCurrencyPending(tenantId, eligibilityCurrency, eligibility, eligibilityCurrencyUpdateResource, lendingWorkflow, user);
				}
		} else {
			EligibilityCurrencyPending eligibilityCurrencyPending = saveEligibilityCurrencyPending(tenantId, eligibilityCurrency, eligibility, eligibilityCurrencyUpdateResource, lendingWorkflow, user);
			
			LoggerRequest.getInstance().logInfo("EligibilityCurrency********************************Update Eligibility Currency*********************************************");
			updateEligibilityCurrency(eligibilityCurrencyPending, eligibilityCurrency, CommonApproveStatus.APPROVED, user);
		}
	}
	
	private EligibilityCurrencyPending saveEligibilityCurrencyPending(String tenantId, EligibilityCurrency eligibilityCurrency, Eligibility eligibility, EligibilityCurrencyUpdateResource eligibilityCurrencyUpdateResource, LendingWorkflow lendingWorkflow, String user) {
		
		LoggerRequest.getInstance().logInfo("EligibilityCurrency********************************Save Eligibility Currency History*********************************************");
		saveEligibilityCurrencyHistory(user, eligibilityCurrency);
		
		EligibilityCurrency eligi = eligibilityCurrency;
		eligi.setApproveStatus(CommonApproveStatus.PENDING);
		eligi.setModifiedUser(user);
		eligi.setModifiedDate(validationService.getCreateOrModifyDate());
		eligi.setSyncTs(validationService.getCreateOrModifyDate());
		eligibilityCurrencyRepository.saveAndFlush(eligi);
		
		EligibilityCurrencyPending eligibilityCurrencyPending = new EligibilityCurrencyPending();
		eligibilityCurrencyPending.setTenantId(tenantId);
		if(lendingWorkflow != null) {
			eligibilityCurrencyPending.setLendingWorkflow(lendingWorkflow);
		}	
		eligibilityCurrencyPending.setEligibilityCurrencys(eligibilityCurrency);
		eligibilityCurrencyPending.setEligibilitys(eligibility);
		eligibilityCurrencyPending.setCurrencyId(Long.parseLong(eligibilityCurrencyUpdateResource.getCurrencyId()));
		eligibilityCurrencyPending.setStatus(CommonStatus.valueOf(eligibilityCurrencyUpdateResource.getStatus()));
		eligibilityCurrencyPending.setCreatedDate(validationService.getCreateOrModifyDate());
		eligibilityCurrencyPending.setCreatedUser(user);
		eligibilityCurrencyPending.setSyncTs(validationService.getCreateOrModifyDate());
		eligibilityCurrencyPendingRepository.save(eligibilityCurrencyPending);
		
		return eligibilityCurrencyPending;
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
	
		Optional<EligibilityCurrencyPending> isPresentEligibilityCurrencyPending = eligibilityCurrencyPendingRepository.findById(id);
		
		Optional<EligibilityCurrency> eligiCurrency = eligibilityCurrencyRepository.findById(isPresentEligibilityCurrencyPending.get().getEligibilityCurrencys().getId());
		
		if(!isPresentEligibilityCurrencyPending.get().getEligibilityCurrencys().getApproveStatus().equals(CommonApproveStatus.PENDING))
			throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), "message");
		
		Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository.findById(isPresentEligibilityCurrencyPending.get().getLendingWorkflow().getId());
	
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
	
		LoggerRequest.getInstance().logInfo("EligibilityCurrency********************************Update Workflow*********************************************");
		lendingWorkflowService.update(lendingWorkflow.get(), workflowStatus, user);
		
		LoggerRequest.getInstance().logInfo("EligibilityCurrency********************************Update Eligibility Currency*********************************************");
		updateEligibilityCurrency(isPresentEligibilityCurrencyPending.get(), eligiCurrency.get(), approveStatus, user);
		
		return true;
	}

	@Override
	public Optional<EligibilityCurrencyPending> getByPendingId(Long pendingId) {
		Optional<EligibilityCurrencyPending> isPresentEligibilityCurrencyPending = eligibilityCurrencyPendingRepository.findById(pendingId);
		if (isPresentEligibilityCurrencyPending.isPresent()) {
			EligibilityCurrencyPending eligibilityCurrencyPending = isPresentEligibilityCurrencyPending.get();
			setCurrencyFieldsForPending(eligibilityCurrencyPending.getTenantId(), eligibilityCurrencyPending);
			return Optional.ofNullable(eligibilityCurrencyPending);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Page<EligibilityCurrencyPending> searchEligibilityCurrency(String searchq, String status, String approveStatus, Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(status==null || status.isEmpty())
			status=null;
		if(approveStatus==null || approveStatus.isEmpty())
			approveStatus=null;
		
		Page<EligibilityCurrencyPending> eligibilityCurrencyPendingPage = eligibilityCurrencyPendingRepository.searchEligibilityCurrencyPending(searchq, status, approveStatus, pageable);
        List<EligibilityCurrencyPending> eligibilityCurrencyPendingList = eligibilityCurrencyPendingPage.getContent();
        
        for(EligibilityCurrencyPending eligibilityCurrencyPending : eligibilityCurrencyPendingList) {
        	setCurrencyFieldsForPending(eligibilityCurrencyPending.getTenantId(), eligibilityCurrencyPending);
        }
        
        return eligibilityCurrencyPendingPage;
		
	}
}
