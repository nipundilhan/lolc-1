package com.fusionx.lending.product.service.impl;

import java.util.ArrayList;
/**
 * Master Def Currency Eligibility Service Impl
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   13-07-2021      		     FX-	Piyumi      Created
 *    
 *******************************************************************************************************
 */
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.domain.MasterCurrency;
import com.fusionx.lending.product.domain.MasterCurrencyPending;
import com.fusionx.lending.product.domain.MasterDefinition;
import com.fusionx.lending.product.domain.MasterDefinitionPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.MasterDefinitionCategory;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.repository.MasterCurrencyPendingRepository;
import com.fusionx.lending.product.repository.MasterCurrencyRepository;
import com.fusionx.lending.product.repository.MasterDefinitionPendingRepository;
import com.fusionx.lending.product.repository.MasterDefinitionRepository;
import com.fusionx.lending.product.resources.MasterCurrencyAddResource;
import com.fusionx.lending.product.resources.MasterCurrencyMainAddResource;
import com.fusionx.lending.product.resources.MasterCurrencyMainUpdateResource;
import com.fusionx.lending.product.resources.MasterCurrencyUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.MasterCurrencyHistoryService;
import com.fusionx.lending.product.service.MasterCurrencyService;
import com.fusionx.lending.product.service.MasterDefinitionHistoryService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor=Exception.class)
public class MasterCurrencyServiceImpl extends MessagePropertyBase  implements MasterCurrencyService {
	
    protected static final String MASTER_DEFINITION_PENDING = "masterDefinitionPendingId";
	
	@Autowired
	private MasterCurrencyRepository masterCurrencyRepository;
	
	@Autowired
	private MasterCurrencyPendingRepository masterCurrencyPendingRepository;
	
	@Autowired
	private MasterDefinitionRepository masterDefinitionRepository;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ValidationService validationService;
	
	@Autowired
	private MasterDefinitionHistoryService masterDefinitionHistoryService;

	@Autowired
	private MasterDefinitionPendingRepository masterDefinitionPendingRepository;
	
	@Autowired
	private LendingWorkflowService lendingWorkflowService;
	
	@Autowired
	private LendingWorkflowRepository lendingWorkflowRepository;
	
	@Autowired
	private MasterCurrencyHistoryService masterCurrencyHistoryService;
	
	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";


	@Override
	public Optional<MasterCurrency> getById(Long id) {
		Optional<MasterCurrency> isPresentMasterCurrency = masterCurrencyRepository.findById(id);
		if (isPresentMasterCurrency.isPresent()) {
			return Optional.ofNullable(isPresentMasterCurrency.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus) {
		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL);
		WorkflowRulesResource workflowRulesResource = null;
		WorkflowType workflowType = WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL;
		String user = LogginAuthentcation.getInstance().getUserName();
		CommonApproveStatus approveStatus = null;

		Optional<MasterDefinitionPending> isPresentMasterDefinitionPending = masterDefinitionPendingRepository
				.findById(id);
		if(!isPresentMasterDefinitionPending.isPresent()) {
			LoggerRequest.getInstance().logInfo("MasterDefinitionPending********************************Validate MasterDefinitionPending*********************************************");
			throw new ValidateRecordException(environment.getProperty("record-not-found"),"message");
		}

		Optional<MasterDefinition> masterDefinition = masterDefinitionRepository
				.findById(isPresentMasterDefinitionPending.get().getMasterDefinition().getId());

		if (!masterDefinition.get().getApproveStatus()
				.equals(CommonApproveStatus.PENDING))
			throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), "message");

		Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository
				.findById(isPresentMasterDefinitionPending.get().getLendingWorkflow().getId());

		workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
				tenantId);

		if (workflowRulesResource.getApprovalAllowed().equalsIgnoreCase(CommonStatus.NO.toString())) {
			if (lendingWorkflow.get().getCreatedUser()
					.equalsIgnoreCase(LogginAuthentcation.getInstance().getUserName()))
				throw new ValidateRecordException(environment.getProperty("cannot.process.approve"), "message");
		}

		if (workflowStatus.toString().equalsIgnoreCase(WorkflowStatus.COMPLETE.toString())) {
			validationService.approveWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user,
					tenantId);
			approveStatus = CommonApproveStatus.APPROVED;
		} else {
			validationService.abortedWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user,
					tenantId);
			approveStatus = CommonApproveStatus.REJECTED;
		}

		lendingWorkflowService.update(lendingWorkflow.get(), workflowStatus, user);

		updateMasterDefinition(isPresentMasterDefinitionPending.get(), masterDefinition.get(), approveStatus);

		return true;
	}

	@Override
	public Optional<MasterCurrencyPending> getByPendingId(Long pendingId) {
		Optional<MasterCurrencyPending> isPresentMasterCurrencyPending = masterCurrencyPendingRepository.findById(pendingId);
		if (isPresentMasterCurrencyPending.isPresent()) {
			return Optional.ofNullable(isPresentMasterCurrencyPending.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<MasterCurrency> getByMasterDefinitionId(Long masterDefId) {		
		List<MasterCurrency> mcurrencyList = new ArrayList<>();
		Optional<MasterDefinition> isPresentMasterDefinition = masterDefinitionRepository.findById(masterDefId);
		
		if(isPresentMasterDefinition.isPresent()) 
			mcurrencyList =  masterCurrencyRepository.findByMasterDefinition(isPresentMasterDefinition.get());
		return mcurrencyList;
		
	}

	@Override
	public List<MasterCurrencyPending> getPendingByMasterDefinitionId(Long masterDefId) {
		List<MasterCurrencyPending> mcurrencyPenList = new ArrayList<>();
		Optional<MasterDefinition> isPresentMasterDefinition = masterDefinitionRepository.findById(masterDefId);
		if(isPresentMasterDefinition.isPresent()) 
			mcurrencyPenList = masterCurrencyPendingRepository.findByMasterDefinition(isPresentMasterDefinition.get());			
		return mcurrencyPenList;
	}

	@Override
	public List<Long> addMasterCurrency(String tenantId, Long masterDefId,
			MasterCurrencyMainAddResource masterCurrencyMainAddResource) {
		
		List<Long> masterCurrencyList = null;
		Optional<MasterDefinition> isPresentMasterDefinition = masterDefinitionRepository.findByIdAndStatus(masterDefId,CommonStatus.ACTIVE);
		
		if(isPresentMasterDefinition.isPresent()){		
			if(masterCurrencyMainAddResource.getCurrencyList() != null && !masterCurrencyMainAddResource.getCurrencyList().isEmpty()) {
				masterCurrencyList = new ArrayList<>();
				for(MasterCurrencyAddResource masterCurrencyResource :masterCurrencyMainAddResource.getCurrencyList()) {
					
					LoggerRequest.getInstance().logInfo("MasterCurrency********************************Validate Currency*********************************************");
					validationService.validateCurrency(tenantId, masterCurrencyResource.getCurrencyId(), masterCurrencyResource.getCurrencyName(), EntityPoint.MASTER_CURRENCY);
					
					LoggerRequest.getInstance().logInfo("MasterCurrency********************************Validate Master Currency Duplicate*********************************************");
					if(masterCurrencyRepository.existsByMasterDefinitionIdAndCurrencyId(masterDefId, Long.parseLong(masterCurrencyResource.getCurrencyId())))
						throw new ValidateRecordException(environment.getProperty("masterCurrency.unique"), "message");
						
							MasterCurrency masterCurrency = new MasterCurrency();
							masterCurrency.setTenantId(tenantId);
							masterCurrency.setMasterDefinition(isPresentMasterDefinition.get());
							masterCurrency.setCurrencyId(Long.parseLong(masterCurrencyResource.getCurrencyId()));
							masterCurrency.setCurrencyName(masterCurrencyResource.getCurrencyName());
							masterCurrency.setNumOfDecimalPlaces(Long.parseLong(masterCurrencyResource.getNumOfDecimalPlaces()));
							masterCurrency.setStatus(CommonStatus.valueOf(masterCurrencyResource.getStatus()));
							masterCurrency.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
							masterCurrency.setCreatedDate(validationService.getCreateOrModifyDate());
							masterCurrency.setSyncTs(validationService.getCreateOrModifyDate());
							masterCurrency = masterCurrencyRepository.save(masterCurrency);
							
							masterCurrencyHistoryService.saveHistory(tenantId, masterCurrency, LogginAuthentcation.getInstance().getUserName());
							masterCurrencyList.add(masterCurrency.getId());
								
				}
				
				return masterCurrencyList;
			}
		}		
		else {
			LoggerRequest.getInstance().logInfo("MasterDefinition********************************Validate MasterDefinition*********************************************");
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");	
		}
		return null;
	}

	@Override
	public Long updateMasterCurrency(String tenantId, Long masterDefId,
			MasterCurrencyMainUpdateResource masterCurrencyMainUpdateResource) {
		MasterDefinitionPending pendingMasterDefinition = null;
		Optional<MasterDefinitionPending> isPresentMasterDefinitionPendingId = null;
		Optional<MasterDefinition> isPresentMasterDefinition = masterDefinitionRepository.findByIdAndStatus(masterDefId,CommonStatus.ACTIVE);
		
			if(isPresentMasterDefinition.isPresent()){	
				Optional<MasterDefinitionPending> isPresentMasterDefinitionPending = masterDefinitionPendingRepository.findByMasterDefinitionIdAndStatusAndApproveStatus(masterDefId,CommonStatus.ACTIVE, CommonApproveStatus.PENDING);
				
				if(isPresentMasterDefinitionPending.isPresent() && masterCurrencyMainUpdateResource.getMasterDefinitionPendingId() == null)
					throw new ValidateRecordException(environment.getProperty("masterCurrency.can-not-update"), "message");
				
				if(masterCurrencyMainUpdateResource.getCurrencyList() != null && !masterCurrencyMainUpdateResource.getCurrencyList().isEmpty()) {
					
					if(masterCurrencyMainUpdateResource.getMasterDefinitionPendingId() != null && !"".equals(masterCurrencyMainUpdateResource.getMasterDefinitionPendingId().trim())) {
						isPresentMasterDefinitionPendingId = masterDefinitionPendingRepository.findById(Long.parseLong(masterCurrencyMainUpdateResource.getMasterDefinitionPendingId()));
					}
						if(isPresentMasterDefinitionPendingId != null){
							pendingMasterDefinition = isPresentMasterDefinitionPendingId.get();
							validateMasterCurrencyUpdateResource(tenantId,masterDefId,masterCurrencyMainUpdateResource,pendingMasterDefinition);
						}else {
							pendingMasterDefinition = approveOrGenerateWorkFlow(tenantId,LogginAuthentcation.getInstance().getUserName(),isPresentMasterDefinition.get(),masterCurrencyMainUpdateResource);
						}
				
				}
			}		
			else {
				LoggerRequest.getInstance().logInfo("MasterDefinition********************************Validate MasterDefinition*********************************************");
				throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");	
			}
			return pendingMasterDefinition != null ? pendingMasterDefinition.getId():null;
			
	}
	
	public void validateMasterCurrencyUpdateResource(String tenantId, Long masterDefId,
			MasterCurrencyMainUpdateResource masterCurrencyMainUpdateResource,MasterDefinitionPending pendingMasterDefinition) {
		for(MasterCurrencyUpdateResource masterCurrencyResource :masterCurrencyMainUpdateResource.getCurrencyList()) {
			
			LoggerRequest.getInstance().logInfo("MasterCurrency********************************Validate Currency*********************************************");
			validationService.validateCurrency(tenantId, masterCurrencyResource.getCurrencyId(), masterCurrencyResource.getCurrencyName(), EntityPoint.MASTER_CURRENCY);
		
			
			if(masterCurrencyResource.getId() == null) {
				LoggerRequest.getInstance().logInfo("MasterCurrency********************************Validate Master Currency Duplicate*********************************************");
				if(masterCurrencyRepository.existsByMasterDefinitionIdAndCurrencyId(masterDefId, Long.parseLong(masterCurrencyResource.getCurrencyId())))
					throw new ValidateRecordException(environment.getProperty("masterCurrency.unique"), "message");
				
				if(masterCurrencyPendingRepository.existsByMasterDefinitionIdAndCurrencyId(masterDefId, Long.parseLong(masterCurrencyResource.getCurrencyId())))
					throw new ValidateRecordException(environment.getProperty("masterCurrencyPending.unique"), "message");
				
					saveMasterCurrencyPending(tenantId,masterCurrencyResource,pendingMasterDefinition);												
			}else {
				if(masterCurrencyRepository.existsByMasterDefinitionIdAndCurrencyIdAndIdNotIn(masterDefId, Long.parseLong(masterCurrencyResource.getCurrencyId()), Long.parseLong(masterCurrencyResource.getId())))
					throw new ValidateRecordException(environment.getProperty("masterCurrency.unique"), "message");
				
//				if(masterCurrencyPendingRepository.existsByMasterDefinitionIdAndCurrencyIdAndIdNotIn(masterDefId, Long.parseLong(masterCurrencyResource.getCurrencyId()),Long.parseLong(masterCurrencyResource.getId())))
//					throw new ValidateRecordException(environment.getProperty("masterCurrencyPending.unique"), "message");
				
					saveMasterCurrencyPending(tenantId,masterCurrencyResource, pendingMasterDefinition);
						
			}
		}
	}
	
	public Long saveMasterCurrencyPending(String tenantId, MasterCurrencyUpdateResource masterCurrencyResource, MasterDefinitionPending masterDefinitionPending) {
		MasterCurrency masterCurrency = null;
		if(masterCurrencyResource.getId() != null && !"".equals(masterCurrencyResource.getId().trim())) {
			 Optional<MasterCurrency> isPresentMasterCurrency = masterCurrencyRepository.findById(Long.parseLong(masterCurrencyResource.getId()));
			 if(isPresentMasterCurrency.isPresent()) {
				 if(!isPresentMasterCurrency.get().getVersion().equals(Long.parseLong(masterCurrencyResource.getVersion()))) {
						throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
				 }				 
				 masterCurrency = isPresentMasterCurrency.get();
			 }else
				 throw new ValidateRecordException(environment.getProperty("masterCurrency.not-found"), "message");
		}
		
		MasterCurrencyPending masterCurrencyPending = new MasterCurrencyPending();
		masterCurrencyPending.setTenantId(tenantId);
		masterCurrencyPending.setMasterCurrency(masterCurrency);
		masterCurrencyPending.setMasterDefinition(masterDefinitionPending.getMasterDefinition());
		masterCurrencyPending.setMasterDefinitionPending(masterDefinitionPending);
		masterCurrencyPending.setCurrencyId(Long.parseLong(masterCurrencyResource.getCurrencyId()));
		masterCurrencyPending.setCurrencyName(masterCurrencyResource.getCurrencyName());
		masterCurrencyPending.setNumOfDecimalPlaces(Long.parseLong(masterCurrencyResource.getNumOfDecimalPlaces()));
		masterCurrencyPending.setLendingWorkflow(masterDefinitionPending.getLendingWorkflow());
		masterCurrencyPending.setStatus(CommonStatus.valueOf(masterCurrencyResource.getStatus()));
		masterCurrencyPending.setCreatedDate(validationService.getCreateOrModifyDate());
		masterCurrencyPending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		masterCurrencyPending.setApproveStatus(CommonApproveStatus.PENDING);
		masterCurrencyPending.setSyncTs(validationService.getCreateOrModifyDate());
		masterCurrencyPending = masterCurrencyPendingRepository.save(masterCurrencyPending);
		return masterCurrencyPending.getId();
		
	}
	
	private MasterDefinitionPending savePendingMasterDefinition(String tenantId, MasterDefinition masterDefinition, LendingWorkflow lendingWorkflow) {

		masterDefinitionHistoryService.saveHistory(tenantId, masterDefinition, LogginAuthentcation.getInstance().getUserName());

		MasterDefinition md = masterDefinition;
		md.setApproveStatus(CommonApproveStatus.PENDING);
		md.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		md.setModifiedDate(validationService.getCreateOrModifyDate());
		md.setSyncTs(validationService.getCreateOrModifyDate());
		masterDefinitionRepository.saveAndFlush(md);

		MasterDefinitionPending masterDefinitionPending = new MasterDefinitionPending();

		masterDefinitionPending.setTenantId(tenantId);
		if (lendingWorkflow != null)
			masterDefinitionPending.setLendingWorkflow(lendingWorkflow);
		masterDefinitionPending.setMasterDefinition(masterDefinition);
		masterDefinitionPending.setCode(masterDefinition.getCode());
		masterDefinitionPending.setName(masterDefinition.getName());
		masterDefinitionPending.setStatus(masterDefinition.getStatus());
		masterDefinitionPending.setDescription(masterDefinition.getDescription());
		masterDefinitionPending.setBusinessPrinciple(masterDefinition.getBusinessPrinciple());
		masterDefinitionPending.setApproveStatus(CommonApproveStatus.PENDING);
		masterDefinitionPending.setModule(masterDefinition.getModule());
		masterDefinitionPending.setSubModuleCode(masterDefinition.getSubModuleCode());
		masterDefinitionPending.setSubModuleName(masterDefinition.getSubModuleName());
		masterDefinitionPending.setPenCreatedDate(validationService.getCreateOrModifyDate());
		masterDefinitionPending.setPenCreatedUser(LogginAuthentcation.getInstance().getUserName());
		masterDefinitionPending.setMasterDefinitionCategory(MasterDefinitionCategory.CURRENCY_MAPPING);
		masterDefinitionPending.setSyncTs(validationService.getCreateOrModifyDate());

		return masterDefinitionPendingRepository.saveAndFlush(masterDefinitionPending);

	}
	
	private MasterDefinitionPending approveOrGenerateWorkFlow(String tenantId, String user, MasterDefinition masterDefinition, MasterCurrencyMainUpdateResource masterCurrencyMainUpdateResource) {
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
				lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
				masterDefinitionPending = savePendingMasterDefinition(tenantId, masterDefinition, lendingWorkflow);
				validateMasterCurrencyUpdateResource(tenantId,masterDefinition.getId(),masterCurrencyMainUpdateResource, masterDefinitionPending);
			}
		} else {
			 masterDefinitionPending = savePendingMasterDefinition(tenantId, masterDefinition, lendingWorkflow);
			 validateMasterCurrencyUpdateResource(tenantId,masterDefinition.getId(),masterCurrencyMainUpdateResource, masterDefinitionPending);
			 updateMasterDefinition(masterDefinitionPending, masterDefinition, CommonApproveStatus.APPROVED);
		}
		
		return masterDefinitionPending;
	}
	
	private void updateMasterDefinition(MasterDefinitionPending masterDefPending, MasterDefinition md,
			CommonApproveStatus approveStatus) {

		masterDefinitionHistoryService.saveHistory(md.getTenantId(), md, LogginAuthentcation.getInstance().getUserName());

		MasterDefinition masterDefinition = md;
		masterDefinition.setApproveStatus(approveStatus);
		
		MasterDefinitionPending masterDefinitionPending= masterDefPending;
		masterDefinitionPending.setApproveStatus(approveStatus);
		
		updateApprovalDetails(masterDefPending.getId());

		if (approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {			
			masterDefinition.setName(masterDefPending.getName());
			masterDefinition.setStatus(masterDefPending.getStatus());
			masterDefinition.setModule(masterDefPending.getModule());
			masterDefinition.setDescription(masterDefPending.getDescription());
			masterDefinition.setSubModuleCode(masterDefPending.getSubModuleCode());
			masterDefinition.setSubModuleName(masterDefPending.getSubModuleName());
			masterDefinition.setBusinessPrinciple(masterDefPending.getBusinessPrinciple());
			masterDefinition.setModifiedDate(masterDefPending.getPenCreatedDate());
			masterDefinition.setModifiedUser(masterDefPending.getPenCreatedUser());
			masterDefinition.setPenApprovedUser(LogginAuthentcation.getInstance().getUserName());
			masterDefinition.setPenApprovedDate(validationService.getCreateOrModifyDate());
			
		} else {
			masterDefinition.setPenRejectedUser(LogginAuthentcation.getInstance().getUserName());
			masterDefinition.setPenRejectedDate(validationService.getCreateOrModifyDate());
		}
		masterDefinition.setSyncTs(validationService.getCreateOrModifyDate());

		masterDefinitionRepository.save(masterDefinition);
		masterDefinitionPendingRepository.save(masterDefinitionPending);
	}

	@Override
	public Page<MasterCurrencyPending> searchMasterCurrency(String searchq, String status, String approveStatus,
			Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(status==null || status.isEmpty())
			status=null;
		if(approveStatus==null || approveStatus.isEmpty())
			approveStatus=null;
		return masterCurrencyPendingRepository.searchMasterCurrencyPending(searchq, status, approveStatus, pageable);
	}
	
	@Override
	public void updateApprovalDetails(Long MasterDefinitionPendingId) {
		
		Optional<MasterDefinitionPending> masterDefinitionPendingOptional = masterDefinitionPendingRepository.findById(MasterDefinitionPendingId);
		if(!masterDefinitionPendingOptional.isPresent()) {
			 throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MASTER_DEFINITION_PENDING);
		}
		
		List<MasterCurrencyPending> masterCurrencyPendingList = masterCurrencyPendingRepository.findByMasterDefinitionPending(masterDefinitionPendingOptional.get());
		
		if(masterCurrencyPendingList != null && !masterCurrencyPendingList.isEmpty()){
			for(MasterCurrencyPending masterCurrencyPending: masterCurrencyPendingList) {
				MasterCurrency masterCurrency = null;
				
				MasterCurrencyPending masterCurPen = masterCurrencyPending;
				CommonApproveStatus approveStatus = CommonApproveStatus.APPROVED;
				masterCurPen.setApproveStatus(approveStatus);
				
					if(CommonApproveStatus.APPROVED.equals(approveStatus)) {
						masterCurPen.setPenApprovedUser(LogginAuthentcation.getInstance().getUserName());
						masterCurPen.setPenApprovedDate(validationService.getCreateOrModifyDate());
						
						if(masterCurrencyPending.getMasterCurrency() == null) {
							masterCurrency = new MasterCurrency();
							masterCurrency.setCreatedUser(masterCurrencyPending.getCreatedUser());
							masterCurrency.setCreatedDate(masterCurrencyPending.getCreatedDate());
							
						}else {
							masterCurrency = masterCurrencyPending.getMasterCurrency();
							
							masterCurrencyHistoryService.saveHistory(masterCurrencyPending.getTenantId(), masterCurrency, LogginAuthentcation.getInstance().getUserName());
							
							masterCurrency.setModifiedUser(masterCurrencyPending.getCreatedUser());
							masterCurrency.setModifiedDate(masterCurrencyPending.getCreatedDate());
						}
						
						masterCurrency.setTenantId(masterCurrencyPending.getTenantId());
						masterCurrency.setMasterDefinition(masterCurrencyPending.getMasterDefinition());
						masterCurrency.setCurrencyId(masterCurrencyPending.getCurrencyId());
						masterCurrency.setCurrencyName(masterCurrencyPending.getCurrencyName());
						masterCurrency.setNumOfDecimalPlaces(masterCurrencyPending.getNumOfDecimalPlaces());
						masterCurrency.setStatus(masterCurrencyPending.getStatus());
						masterCurrency.setSyncTs(validationService.getCreateOrModifyDate());
						masterCurrency = masterCurrencyRepository.save(masterCurrency);
				}else {
					masterCurPen.setPenRejectedDate(validationService.getCreateOrModifyDate());
					masterCurPen.setPenRejectedUser(LogginAuthentcation.getInstance().getUserName());
					
				}
					masterCurrencyPendingRepository.save(masterCurPen);
				
			}
			
		}
	}

}
