package com.fusionx.lending.product.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.domain.PenalInterest;
import com.fusionx.lending.product.domain.SubProduct;
import com.fusionx.lending.product.domain.SubProductPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.repository.PenalInterestRepository;
import com.fusionx.lending.product.repository.SubProductPendingRepository;
import com.fusionx.lending.product.repository.SubProductRepository;
import com.fusionx.lending.product.resources.SubProductPenalInterestResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.SubProductPenalInterestService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * API Service related to Sub Product Penal Interest.
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
 * 1        27-08-2021      -               -           Dilhan Jayasinghe      Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class SubProductPenalInterestServiceImpl extends MessagePropertyBase implements SubProductPenalInterestService{
	
	@Autowired
	private ValidationService validationService;

	@Autowired
	private SubProductRepository subProductRepository;
	
	@Autowired
	private SubProductPendingRepository subProductPendingRepository;
	
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
	public Long updateSubProductWithInterestTemplate(String tenantId, Long subProductId,
			SubProductPenalInterestResource subProductPenalInterestResource) {
		SubProductPending subProductPending = null;
		Optional<SubProduct> subProductOpt = subProductRepository.findById(subProductId);

		if (!subProductOpt.isPresent()) {
			LoggerRequest.getInstance().logInfo(
					"SubProduct********************************Validate SubProduct*********************************************");
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");
		}

		Optional<PenalInterest> penalInterest = penalInterestRepository.findByIdAndNameAndStatus(
				validationService.stringToLong(subProductPenalInterestResource.getPenalInterestId()),
				subProductPenalInterestResource.getPenalInterestName(), CommonStatus.ACTIVE);

		if (!penalInterest.isPresent()) {
			LoggerRequest.getInstance().logInfo(
					"PenalInterest********************************Validate PenalInterest*********************************************");
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");
		}

		Optional<SubProductPending> PendingSubProduct = subProductPendingRepository
				.findBySubProductIdAndStatusAndApproveStatus(subProductId, CommonStatus.ACTIVE.toString(), CommonApproveStatus.PENDING.toString());

		if (PendingSubProduct.isPresent()) {

			throw new ValidateRecordException(environment.getProperty("interestTemplate.can-not-update"), "message");
		} else {

			subProductPending = approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(), subProductOpt.get(),
					subProductPenalInterestResource, penalInterest.get());
		}

		return subProductPending != null ? subProductPending.getId() : null;
	}

	private SubProductPending approveOrGenerateWorkFlow(String tenantId, String userName, SubProduct subProduct,
			SubProductPenalInterestResource subProductPenalInterestResource, PenalInterest penalInterest) {
		WorkflowRulesResource workflowRulesResource = null;
		Long processId = null;
		WorkflowType workflowType = WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL;
		LendingWorkflow lendingWorkflow = null;
		SubProductPending subProductPending = null;

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

		workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
				tenantId);

		if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
			processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
			if (processId != null) {
				lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, userName);
				subProductPending = savePendingSubProduct(tenantId, subProduct,
						subProductPenalInterestResource, lendingWorkflow, penalInterest, userName);

			}
		} else {
			subProductPending = savePendingSubProduct(tenantId, subProduct,
					subProductPenalInterestResource, lendingWorkflow, penalInterest, userName);

			updateSubProduct(tenantId,subProductPending, subProduct, CommonApproveStatus.APPROVED);

		}
		return subProductPending;

	}

	private void updateSubProduct(String tenantId,SubProductPending subProductPending, SubProduct sp,
			CommonApproveStatus approveStatus) {

		//create history once the subProductHistoryService is available
		
		SubProduct subProduct = sp;
		subProduct.setModifiedDate(subProductPending.getCreatedDate());
		subProduct.setModifiedUser(subProductPending.getCreatedUser());
		subProduct.setApproveStatus(approveStatus.toString());

		if (approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
			subProduct.setPendingApprovedUser(LogginAuthentcation.getInstance().getUserName());
			subProduct.setPendingApprovedDate(validationService.getCreateOrModifyDate());
			subProduct.setPenalInterest(subProductPending.getPenalInterest());
			subProduct.setMaxPenalInterestRate(subProductPending.getMaxPenalInterestRate());
			subProduct.setMiniPenalInterestRate(subProductPending.getMiniPenalInterestRate());
			
		} else {
			subProduct.setPendingRejectedUser(LogginAuthentcation.getInstance().getUserName());
			subProduct.setPendingRejectedDate(validationService.getCreateOrModifyDate());
		}
		subProduct.setSyncTs(validationService.getCreateOrModifyDate());

		subProductPending.setApproveStatus(approveStatus.toString());
		subProductPending.setSyncTs(validationService.getCreateOrModifyDate());
		subProductPendingRepository.saveAndFlush(subProductPending);
		subProductRepository.saveAndFlush(subProduct);
	}

	private SubProductPending savePendingSubProduct(String tenantId, SubProduct subProduct,
			SubProductPenalInterestResource subProductPenalInterestResource, LendingWorkflow lendingWorkflow,
			PenalInterest penalInterest, String userName) {

		SubProduct sp = subProduct;
		sp.setApproveStatus(CommonApproveStatus.PENDING.toString());
		sp.setModifiedUser(userName);
		sp.setModifiedDate(validationService.getCreateOrModifyDate());
		sp.setSyncTs(validationService.getCreateOrModifyDate());
		subProductRepository.saveAndFlush(sp);

		SubProductPending subProductPending = new SubProductPending();

		subProductPending.setTenantId(tenantId);
		if (lendingWorkflow != null)
			subProductPending.setLendingWorkflow(lendingWorkflow);

		subProductPending.setSubProduct(sp);
		subProductPending.setApproveStatus(CommonApproveStatus.PENDING.toString());
		subProductPending.setMaxPenalInterestRate(
				validationService.stringToBigDecimal(subProductPenalInterestResource.getMaxPenalInterestRate()));
		subProductPending.setMiniPenalInterestRate(
				validationService.stringToBigDecimal(subProductPenalInterestResource.getMiniPenalInterestRate()));
		subProductPending.setPenalInterest(penalInterest);
		
		subProductPending.setCode(sp.getCode());
		subProductPending.setName(sp.getName());
		subProductPending.setProduct(sp.getProduct());
		subProductPending.setPredecessorId(sp.getPredecessorId());
		subProductPending.setFeatureBenifitTemplate(sp.getFeatureBenifitTemplate());
		subProductPending.setEligibility(sp.getEligibility());
		subProductPending.setInterestTemplate(sp.getInterestTemplate());
		subProductPending.setRepayment(sp.getRepayment());
		subProductPending.setCoreProductId(sp.getCoreProductId());
		subProductPending.setStateTenurePeriodId(sp.getStateTenurePeriodId());
		subProductPending.setMarketingStateId(sp.getMarketingStateId());
		subProductPending.setFirstMarketedDate(sp.getFirstMarketedDate());
		subProductPending.setLastMarketedDate(sp.getLastMarketedDate());
		subProductPending.setStatus(sp.getStatus());
		subProductPending.setModifiedDate(sp.getPendingApprovedDate());
		subProductPending.setModifiedUser(sp.getPendingApprovedUser());
		subProductPending.setVersion(sp.getVersion());
		subProductPending.setCreatedDate(validationService.getCreateOrModifyDate());
		subProductPending.setCreatedUser(userName);
		subProductPending.setSyncTs(validationService.getCreateOrModifyDate());

		subProductPending = subProductPendingRepository.save(subProductPending);

		return subProductPending;
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

		Optional<SubProductPending> isPresentSubProductPending = subProductPendingRepository
				.findById(id);
		if (!isPresentSubProductPending.isPresent()) {
			LoggerRequest.getInstance().logInfo(
					"SubProductPending********************************Validate SubProductPending*********************************************");
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");
		}

		Optional<SubProduct> subProduct = subProductRepository
				.findById(isPresentSubProductPending.get().getSubProduct().getId());

		if (!subProduct.get().getApproveStatus().equals(CommonApproveStatus.PENDING.toString()))
			throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), "message");

		Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository
				.findById(isPresentSubProductPending.get().getLendingWorkflow().getId());

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

		updateSubProduct(tenantId,isPresentSubProductPending.get(), subProduct.get(), approveStatus);

		return true;
	}

}
