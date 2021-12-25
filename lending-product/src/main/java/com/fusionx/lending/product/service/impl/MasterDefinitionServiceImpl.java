package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.config.CommonModuleProperties;
import com.fusionx.lending.product.config.ContractNumberFormatProperties;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.LendingAccountDetail;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.domain.MasterDefinition;
import com.fusionx.lending.product.domain.MasterDefinitionPending;
import com.fusionx.lending.product.enums.*;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.NoRecordFoundException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.repository.MasterDefinitionPendingRepository;
import com.fusionx.lending.product.repository.MasterDefinitionRepository;
import com.fusionx.lending.product.resources.*;
import com.fusionx.lending.product.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * API Service related to Lending Module Definition - Master Definition
 *
 * @author Dilki Kalansooriya (Inova)
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Version History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description     Verified By     Verified Date
 * <br/>
 * .....................................................................................................................................<br/>
 * 1        12-July-2021	FXL-1			FXL-5				DilkiK (Inova)			Created			ChinthakaMa     16-Sep-2021
 * <p>
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class MasterDefinitionServiceImpl extends MessagePropertyBase implements MasterDefinitionService {

	private MasterDefinitionRepository masterDefinitionRepository;
	private ValidationService validationService;
	private MasterDefinitionHistoryService masterDefinitionHistoryService;
	private MasterDefinitionPendingRepository masterDefinitionPendingRepository;
	private LendingWorkflowService lendingWorkflowService;
	private LendingWorkflowRepository lendingWorkflowRepository;
	private ContractNumberFormatProperties contractNumberFormatProperties;
	private LendingAccountDetailService lendingAccountDetailService;
	private RemoteService remoteService;
	private CommonModuleProperties commonModuleProperties;

	
	private MasterDefAccountRuleService masterDefAccountRuleService;
	
	private MasterDefCountryService masterDefCountryService;
	
	private MasterCurrencyService masterCurrencyService;
	
	private static final String DEFAULT_APPROVAL_USER = "kie-server";
	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";
	private static final String DOMAIN = "LendingWF";
	private static final String MESSAGE = "message";
	
	@Autowired
	public void setMasterDefAccountRuleService(MasterDefAccountRuleService masterDefAccountRuleService) {
		this.masterDefAccountRuleService = masterDefAccountRuleService;
	}
	
	@Autowired
	public void setMasterDefCountryService(MasterDefCountryService masterDefCountryService) {
		this.masterDefCountryService = masterDefCountryService;
	}

	@Autowired
	public void setMasterDefinitionRepository(MasterDefinitionRepository masterDefinitionRepository) {
		this.masterDefinitionRepository = masterDefinitionRepository;
	}

	@Autowired
	public void setValidationService(ValidationService validationService) {
		this.validationService = validationService;
	}

	@Autowired
	public void setMasterDefinitionHistoryService(MasterDefinitionHistoryService masterDefinitionHistoryService) {
		this.masterDefinitionHistoryService = masterDefinitionHistoryService;
	}

	@Autowired
	public void setMasterDefinitionPendingRepository(
			MasterDefinitionPendingRepository masterDefinitionPendingRepository) {
		this.masterDefinitionPendingRepository = masterDefinitionPendingRepository;
	}

	@Autowired
	public void setLendingWorkflowService(LendingWorkflowService lendingWorkflowService) {
		this.lendingWorkflowService = lendingWorkflowService;
	}

	@Autowired
	public void setLendingWorkflowRepository(LendingWorkflowRepository lendingWorkflowRepository) {
		this.lendingWorkflowRepository = lendingWorkflowRepository;
	}
	
	@Autowired
	public void setMasterCurrencyService(MasterCurrencyService masterCurrencyService) {
		this.masterCurrencyService = masterCurrencyService;
	}

	@Autowired
	public void setContractNumberFormatProperties(ContractNumberFormatProperties contractNumberFormatProperties) {
		this.contractNumberFormatProperties = contractNumberFormatProperties;
	}

	@Autowired
	public void setLendingAccountDetailService(LendingAccountDetailService lendingAccountDetailService) {
		this.lendingAccountDetailService = lendingAccountDetailService;
	}

	@Autowired
	public void setRemoteService(RemoteService remoteService) {
		this.remoteService = remoteService;
	}

	@Autowired
	public void setCommonModuleProperties(CommonModuleProperties commonModuleProperties) {
		this.commonModuleProperties = commonModuleProperties;
	}

	@Override
	public List<MasterDefinition> getAll() {
		return masterDefinitionRepository.findAll();
	}

	@Override
	public Optional<MasterDefinition> getById(Long id) {
		return masterDefinitionRepository.findById(id);
	}

	@Override
	public Optional<MasterDefinition> getByCode(String code) {
		return masterDefinitionRepository.findByCode(code);
	}

	@Override
	public List<MasterDefinition> getByStatus(String status) {
		return masterDefinitionRepository.findByStatus(CommonStatus.valueOf(status));
	}
	
	

	@Override
	public MasterDefinition addMasterDefinition(String tenantId,
			MasterDefinitionAddResource masterDefinitionAddResource) {

		Optional<MasterDefinition> isPresentMasterDefinition = masterDefinitionRepository
				.findByCode(masterDefinitionAddResource.getCode());

		if (isPresentMasterDefinition.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE,
					EntityPoint.MASTER_DEFINITION);
		
		if (masterDefinitionAddResource.getCode().equals(masterDefinitionAddResource.getSubModuleCode())) {
			throw new InvalidServiceIdException(environment.getProperty("masterDefinition-moduleCode.duplicate"), ServiceEntity.CODE,
					EntityPoint.MASTER_DEFINITION);
		}

		MasterDefinition masterDefinition = new MasterDefinition();
		masterDefinition.setTenantId(tenantId);
		masterDefinition.setCode(masterDefinitionAddResource.getCode());
		masterDefinition.setName(masterDefinitionAddResource.getName());
		masterDefinition.setModule(MasterDefinitionModule.valueOf(masterDefinitionAddResource.getModule()));
		masterDefinition.setBusinessPrinciple(MasterDefinitionBusinessPrinciple.valueOf(masterDefinitionAddResource.getBusinessPrinciple()));
		masterDefinition.setDescription(masterDefinitionAddResource.getDescription());
		masterDefinition.setSubModuleCode(masterDefinitionAddResource.getSubModuleCode());
		masterDefinition.setSubModuleName(masterDefinitionAddResource.getSubModuleName());
		masterDefinition.setStatus(CommonStatus.valueOf(masterDefinitionAddResource.getStatus()));
		masterDefinition.setCreatedDate(validationService.getCreateOrModifyDate());
		masterDefinition.setSyncTs(validationService.getCreateOrModifyDate());
		masterDefinition.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		return masterDefinitionRepository.save(masterDefinition);
	}

	@Override
	public MasterDefinition updateMasterDefinition(String tenantId, Long id,
			MasterDefinitionUpdateResource masterDefinitionUpdateResource) {

		Optional<MasterDefinition> isPresentMasterDefinition = masterDefinitionRepository.findById(id);

		if (isPresentMasterDefinition.isPresent()) {

			if (!isPresentMasterDefinition.get().getVersion()
					.equals(Long.parseLong(masterDefinitionUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
						ServiceEntity.VERSION, EntityPoint.MASTER_DEFINITION);

			if (!isPresentMasterDefinition.get().getCode().equalsIgnoreCase(masterDefinitionUpdateResource.getCode()))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_CODE_UPDATE), ServiceEntity.CODE,
						EntityPoint.MASTER_DEFINITION);

			approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(),
					isPresentMasterDefinition.get(), masterDefinitionUpdateResource);

			return isPresentMasterDefinition.get();
		} else
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
	}

	private void approveOrGenerateWorkFlow(String tenantId, String user, MasterDefinition masterDefinition,
			MasterDefinitionUpdateResource masterDefinitionUpdateResource) {

		WorkflowType workflowType = WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL;
		LendingWorkflow lendingWorkflow;

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

		WorkflowRulesResource workflowRulesResource = validationService.invokedLendingProductRule(workflowType,
				DOMAIN_PATH, DOMAIN, tenantId);

		if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
			Long processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource,
					tenantId);
			if (processId != null) {
				lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
				savePendingMasterDefinition(tenantId, masterDefinition, masterDefinitionUpdateResource, lendingWorkflow,
						user);
			}
		} else {
			MasterDefinitionPending masterDefinitionPending = savePendingMasterDefinition(tenantId, masterDefinition,
					masterDefinitionUpdateResource, null, user);
			updateMasterDefinition(masterDefinitionPending, masterDefinition, CommonApproveStatus.APPROVED, user);
			
			
		}
	}

	private MasterDefinitionPending savePendingMasterDefinition(String tenantId, MasterDefinition masterDefinition,
			MasterDefinitionUpdateResource masterDefinitionUpdateResource, LendingWorkflow lendingWorkflow,
			String user) {

		masterDefinitionHistoryService.saveHistory(tenantId, masterDefinition, user);

		MasterDefinition md = masterDefinition;
		md.setApproveStatus(CommonApproveStatus.PENDING);
		md.setModifiedUser(user);
		md.setModifiedDate(validationService.getCreateOrModifyDate());
		md.setSyncTs(validationService.getCreateOrModifyDate());
		masterDefinitionRepository.saveAndFlush(md);

		MasterDefinitionPending masterDefinitionPending = new MasterDefinitionPending();

		masterDefinitionPending.setTenantId(tenantId);

		if (lendingWorkflow != null)
			masterDefinitionPending.setLendingWorkflow(lendingWorkflow);

		masterDefinitionPending.setMasterDefinition(masterDefinition);
		masterDefinitionPending.setCode(masterDefinitionUpdateResource.getCode());
		masterDefinitionPending.setName(masterDefinitionUpdateResource.getName());
		masterDefinitionPending.setBusinessPrinciple(MasterDefinitionBusinessPrinciple.valueOf(masterDefinitionUpdateResource.getBusinessPrinciple()));
		masterDefinitionPending.setModule(MasterDefinitionModule.valueOf(masterDefinitionUpdateResource.getModule()));
		masterDefinitionPending.setDescription(masterDefinitionUpdateResource.getDescription());
		masterDefinitionPending.setSubModuleCode(masterDefinitionUpdateResource.getSubModuleCode());
		masterDefinitionPending.setSubModuleName(masterDefinitionUpdateResource.getSubModuleName());
		masterDefinitionPending.setMasterDefinitionCategory(MasterDefinitionCategory.MASTER_DEFINITION);
		masterDefinitionPending.setStatus(CommonStatus.valueOf(masterDefinitionUpdateResource.getStatus()));
		masterDefinitionPending.setPenCreatedDate(validationService.getCreateOrModifyDate());
		masterDefinitionPending.setPenCreatedUser(user);
		masterDefinitionPending.setSyncTs(validationService.getCreateOrModifyDate());

		return masterDefinitionPendingRepository.save(masterDefinitionPending);

	}

	private void updateMasterDefinition(MasterDefinitionPending masterDefinitionPending, MasterDefinition md,
			CommonApproveStatus approveStatus, String user) {

		masterDefinitionHistoryService.saveHistory(md.getTenantId(), md, user);

		MasterDefinition masterDefinition = md;
		masterDefinition.setName(masterDefinitionPending.getName());
		masterDefinition.setCode(masterDefinitionPending.getCode());
		masterDefinition.setStatus(masterDefinitionPending.getStatus());
		masterDefinition.setBusinessPrinciple(masterDefinitionPending.getBusinessPrinciple());
		masterDefinition.setModule(masterDefinitionPending.getModule());
		masterDefinition.setDescription(masterDefinitionPending.getDescription());
		masterDefinition.setSubModuleCode(masterDefinitionPending.getSubModuleCode());
		masterDefinition.setSubModuleName(masterDefinitionPending.getSubModuleName());
		masterDefinition.setBusinessPrinciple(masterDefinitionPending.getBusinessPrinciple());
		masterDefinition.setModifiedDate(masterDefinitionPending.getPenCreatedDate());
		masterDefinition.setModifiedUser(masterDefinitionPending.getPenCreatedUser());
		masterDefinition.setApproveStatus(approveStatus);

		if (approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
			masterDefinition.setPenApprovedUser(user);
			masterDefinition.setPenApprovedDate(validationService.getCreateOrModifyDate());
		} else {
			masterDefinition.setPenRejectedUser(user);
			masterDefinition.setPenRejectedDate(validationService.getCreateOrModifyDate());
		}

		masterDefinition.setSyncTs(validationService.getCreateOrModifyDate());

		masterDefinitionRepository.saveAndFlush(masterDefinition);
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

		Optional<MasterDefinitionPending> isPresentMasterDefinitionPending = masterDefinitionPendingRepository
				.findById(id);

		if (isPresentMasterDefinitionPending.isPresent()) {
			Optional<MasterDefinition> masterDefinition = masterDefinitionRepository
					.findById(isPresentMasterDefinitionPending.get().getMasterDefinition().getId());
			if (masterDefinition.isPresent()) {
				if (!isPresentMasterDefinitionPending.get().getMasterDefinition().getApproveStatus()
						.equals(CommonApproveStatus.PENDING))
					throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), MESSAGE);

				Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository
						.findById(isPresentMasterDefinitionPending.get().getLendingWorkflow().getId());
				workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
						tenantId);

				if (lendingWorkflow.isPresent()) {
					if (workflowRulesResource.getApprovalAllowed().equalsIgnoreCase(CommonStatus.NO.toString())
							&& lendingWorkflow.get().getCreatedUser()
									.equalsIgnoreCase(LogginAuthentcation.getInstance().getUserName())) {
						throw new ValidateRecordException(environment.getProperty("cannot.process.approve"), MESSAGE);
					}

					if (workflowStatus.toString().equalsIgnoreCase(WorkflowStatus.COMPLETE.toString())) {
						validationService.approveWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType,
								user, tenantId);
						approveStatus = CommonApproveStatus.APPROVED;
						
						masterDefAccountRuleService.updateMasterDefAccoutRule(isPresentMasterDefinitionPending.get().getId());
						masterDefCountryService.updateMasterDefCountryUsingPending(isPresentMasterDefinitionPending.get().getId());
						masterCurrencyService.updateApprovalDetails(isPresentMasterDefinitionPending.get().getId());
					} else {
						validationService.abortedWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType,
								user, tenantId);
						approveStatus = CommonApproveStatus.REJECTED;
					}

					lendingWorkflowService.update(lendingWorkflow.get(), workflowStatus, user);
					updateMasterDefinition(isPresentMasterDefinitionPending.get(), masterDefinition.get(),
							approveStatus, user);

					return true;
				}
			}
		}

		return false;
	}

	@Override
	public Optional<MasterDefinitionPending> getByPendingId(Long pendingId) {
		return masterDefinitionPendingRepository.findById(pendingId);
	}


	/**
	 * retrieve contract number using account id
	 *
	 * @param tenantId  the tenant id
	 * @param code the master definition code
	 * @return ContractNumberResponseResource
	 */
	@Override
	public ContractNumberResponseResource getNextContractNumberByAccountId(String tenantId, String code) {
		String contractNumber = "";

		Optional<MasterDefinition> isPresentMasterDefinition=masterDefinitionRepository.findByCode(code);
		if (!isPresentMasterDefinition.isPresent())
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));

		for (String val : contractNumberFormatProperties.getContractNumberFormatArray()
			) {
				switch (ContractNumberGenerateEnum.valueOf(val)) {
					case COMPANY_CODE:
						contractNumber+=("COMPANY_CODE");
						break;
					case MODULE:
						contractNumber+=("MODULE");
						break;
					case PRODUCT_CODE:
						contractNumber+=(code);
						break;
					case LOAN_ACCOUNT:
						contractNumber+=(isPresentMasterDefinition.get().getNextContractNumber().toString());
						break;
					case VERSION:
						contractNumber+=("VERSION");
						break;
					default:
						break;
				}
			}
			return new ContractNumberResponseResource(contractNumber);
	}

	
}