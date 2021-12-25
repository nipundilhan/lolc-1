package com.fusionx.lending.product.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.domain.MasterDefinition;
import com.fusionx.lending.product.domain.MasterDefinitionPending;
import com.fusionx.lending.product.domain.PenalInterest;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.MasterDefinitionCategory;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.repository.MasterDefinitionPendingRepository;
import com.fusionx.lending.product.repository.MasterDefinitionRepository;
import com.fusionx.lending.product.repository.PenalInterestRepository;
import com.fusionx.lending.product.resources.MasterPenalInterestResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.MasterDefinitionHistoryService;
import com.fusionx.lending.product.service.MasterInterestService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * API Service related to Master Interest.
 *
 * @author Dilhan Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        25-08-2021      -               -           Dilhan Jayasinghe      Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class MasterInterestServiceImpl extends MessagePropertyBase implements MasterInterestService {

	@Autowired
	private ValidationService validationService;

	@Autowired
	private MasterDefinitionPendingRepository masterDefinitionPendingRepository;

	@Autowired
	private MasterDefinitionRepository masterDefinitionRepository;
	
	@Autowired
	private MasterDefinitionHistoryService masterDefinitionHistoryService;

	@Autowired
	private PenalInterestRepository penalInterestRepository;

	@Autowired
	private LendingWorkflowService lendingWorkflowService;

	@Autowired
	private LendingWorkflowRepository lendingWorkflowRepository;

	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";

	@Override
	public Optional<MasterDefinition> getById(Long id) {
		Optional<MasterDefinition> isPresentMasterDefinition = masterDefinitionRepository.findById(id);
		if (isPresentMasterDefinition.isPresent()) {
			return Optional.ofNullable(isPresentMasterDefinition.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Long updateMasterDefinitionWithInterestTemplate(String tenantId, Long masterDefId,
			MasterPenalInterestResource masterPenalInterestResource) {

		MasterDefinitionPending masterDefPending = null;
		Optional<MasterDefinition> masterDefinitionOpt = masterDefinitionRepository.findByIdAndStatus(masterDefId, CommonStatus.ACTIVE);

		if (!masterDefinitionOpt.isPresent()) {
			LoggerRequest.getInstance().logInfo(
					"MasterDefinition********************************Validate MasterDefinition*********************************************");
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "message");
		}

		if (!masterDefinitionOpt.get().getVersion()
				.equals(Long.parseLong(masterPenalInterestResource.getVersion()))) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "version");
		}
		
		Optional<PenalInterest> penalInterest = penalInterestRepository.findByIdAndNameAndStatus(
				validationService.stringToLong(masterPenalInterestResource.getPenalInterestId()),
				masterPenalInterestResource.getPenalInterestName(), CommonStatus.ACTIVE);

		if (!penalInterest.isPresent()) {
			LoggerRequest.getInstance().logInfo(
					"PenalInterest********************************Validate PenalInterest*********************************************");
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "message");
		}

		Optional<MasterDefinitionPending> masterDefinitionPending = masterDefinitionPendingRepository
				.findByMasterDefinitionIdAndStatusAndApproveStatus(masterDefId, CommonStatus.ACTIVE, CommonApproveStatus.PENDING);

		if (masterDefinitionPending.isPresent()) {

			throw new ValidateRecordException(environment.getProperty("interestTemplate.can-not-update"), "message");
		} else {

			masterDefPending = approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(), masterDefinitionOpt.get(),
					masterPenalInterestResource, penalInterest.get());
		}

		return masterDefPending != null ? masterDefPending.getId() : null;
	}

	private MasterDefinitionPending approveOrGenerateWorkFlow(String tenantId, String userName, MasterDefinition masterDefinition,
			MasterPenalInterestResource masterPenalInterestResource, PenalInterest penalInterest) {
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
						masterPenalInterestResource, lendingWorkflow, penalInterest, userName);

			}
		} else {
			masterDefinitionPending = savePendingMasterDefinition(tenantId, masterDefinition,
					masterPenalInterestResource, lendingWorkflow, penalInterest, userName);

			updateMasterDefinition(tenantId,masterDefinitionPending, masterDefinition, CommonApproveStatus.APPROVED);

		}
		return masterDefinitionPending;

	}

	private void updateMasterDefinition(String tenantId,MasterDefinitionPending masterDefinitionPending, MasterDefinition md,
			CommonApproveStatus approveStatus) {

		//create history
		masterDefinitionHistoryService.saveHistory(tenantId, md, LogginAuthentcation.getInstance().getUserName());
		
		MasterDefinition masterDefinition = md;
		masterDefinition.setModifiedDate(masterDefinitionPending.getPenCreatedDate());
		masterDefinition.setModifiedUser(masterDefinitionPending.getPenCreatedUser());
		masterDefinition.setApproveStatus(approveStatus);

		if (approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
			masterDefinition.setPenApprovedUser(LogginAuthentcation.getInstance().getUserName());
			masterDefinition.setPenApprovedDate(validationService.getCreateOrModifyDate());
			masterDefinition.setPenalInterest(masterDefinitionPending.getPenalInterest());
			masterDefinition.setMaxInterestRate(masterDefinitionPending.getMaxInterestRate());
			masterDefinition.setMiniInterestRate(masterDefinitionPending.getMiniInterestRate());
			masterDefinition.setMaxPenalInterestRate(masterDefinitionPending.getMaxPenalInterestRate());
			masterDefinition.setMiniPenalInterestRate(masterDefinitionPending.getMiniPenalInterestRate());
			
		} else {
			masterDefinition.setPenRejectedUser(LogginAuthentcation.getInstance().getUserName());
			masterDefinition.setPenRejectedDate(validationService.getCreateOrModifyDate());
		}
		masterDefinition.setSyncTs(validationService.getCreateOrModifyDate());

		masterDefinitionPending.setApproveStatus(approveStatus);
		masterDefinitionPending.setSyncTs(validationService.getCreateOrModifyDate());
		masterDefinitionPendingRepository.saveAndFlush(masterDefinitionPending);
		masterDefinitionRepository.saveAndFlush(masterDefinition);
	}

	private MasterDefinitionPending savePendingMasterDefinition(String tenantId, MasterDefinition masterDefinition,
			MasterPenalInterestResource masterPenalInterestResource, LendingWorkflow lendingWorkflow,
			PenalInterest penalInterest, String userName) {
		
		if((masterPenalInterestResource.getMiniInterestRate() != null &&  !masterPenalInterestResource.getMiniInterestRate().isEmpty()) 
				&& (masterPenalInterestResource.getMaxInterestRate() != null &&  !masterPenalInterestResource.getMaxInterestRate().isEmpty())) {
			if(validationService.stringToBigDecimal(masterPenalInterestResource.getMiniInterestRate()).compareTo(validationService.stringToBigDecimal(masterPenalInterestResource.getMaxInterestRate())) >= 0) {
	    		throw new ValidateRecordException(environment.getProperty("interestRate.minimum-maximum"), "message");
	    	}
		}
		if((masterPenalInterestResource.getMiniPenalInterestRate() != null &&  !masterPenalInterestResource.getMiniPenalInterestRate().isEmpty()) 
				&& (masterPenalInterestResource.getMaxPenalInterestRate() != null &&  !masterPenalInterestResource.getMaxPenalInterestRate().isEmpty())) {
			if(validationService.stringToBigDecimal(masterPenalInterestResource.getMiniPenalInterestRate()).compareTo(validationService.stringToBigDecimal(masterPenalInterestResource.getMaxPenalInterestRate())) >= 0) {
	    		throw new ValidateRecordException(environment.getProperty("penalRate.minimum-maximum"), "message");
	    	}
		}
		
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
		masterDefinitionPending.setMaxInterestRate(
				validationService.stringToBigDecimal(masterPenalInterestResource.getMaxInterestRate()));
		masterDefinitionPending.setMiniInterestRate(
				validationService.stringToBigDecimal(masterPenalInterestResource.getMiniInterestRate()));
		masterDefinitionPending.setMaxPenalInterestRate(
				validationService.stringToBigDecimal(masterPenalInterestResource.getMaxPenalInterestRate()));
		masterDefinitionPending.setMiniPenalInterestRate(
				validationService.stringToBigDecimal(masterPenalInterestResource.getMiniPenalInterestRate()));
		masterDefinitionPending.setPenalInterest(penalInterest);
		masterDefinitionPending.setMasterDefinitionCategory(MasterDefinitionCategory.ACCOUNTING);
		masterDefinitionPending.setBusinessPrinciple(md.getBusinessPrinciple());
		masterDefinitionPending.setModule(md.getModule());
		masterDefinitionPending.setSubModuleCode(md.getSubModuleCode());
		masterDefinitionPending.setSubModuleName(md.getSubModuleName());
		masterDefinitionPending.setCode(md.getCode());
		masterDefinitionPending.setName(md.getName());
		masterDefinitionPending.setStatus(md.getStatus());
		masterDefinitionPending.setVersion(md.getVersion());
		masterDefinitionPending.setPenCreatedDate(validationService.getCreateOrModifyDate());
		masterDefinitionPending.setPenCreatedUser(userName);
		masterDefinitionPending.setSyncTs(validationService.getCreateOrModifyDate());

		masterDefinitionPending = masterDefinitionPendingRepository.save(masterDefinitionPending);

		return masterDefinitionPending;
	}

	@Override
	public Optional<MasterDefinitionPending> getByPendingId(Long pendingId) {
		Optional<MasterDefinitionPending> isPresentMasterDefinitionPending = masterDefinitionPendingRepository
				.findById(pendingId);
		if (isPresentMasterDefinitionPending.isPresent()) {
			return Optional.ofNullable(isPresentMasterDefinitionPending.get());
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
		if (!isPresentMasterDefinitionPending.isPresent()) {
			LoggerRequest.getInstance().logInfo(
					"MasterDefinitionPending********************************Validate MasterDefinitionPending*********************************************");
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");
		}

		Optional<MasterDefinition> masterDefinition = masterDefinitionRepository
				.findById(isPresentMasterDefinitionPending.get().getMasterDefinition().getId());

		if (!masterDefinition.get().getApproveStatus().equals(CommonApproveStatus.PENDING))
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

		updateMasterDefinition(tenantId,isPresentMasterDefinitionPending.get(), masterDefinition.get(), approveStatus);

		return true;
	}

}
