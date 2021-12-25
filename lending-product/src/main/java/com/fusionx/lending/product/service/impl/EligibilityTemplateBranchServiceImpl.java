package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Eligibility;
import com.fusionx.lending.product.domain.EligibilityTemplateBranch;
import com.fusionx.lending.product.domain.EligibilityTemplateBranchPending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.enums.*;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.EligibilityRepository;
import com.fusionx.lending.product.repository.EligibilityTemplateBranchPendingRepository;
import com.fusionx.lending.product.repository.EligibilityTemplateBranchRepository;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.resources.*;
import com.fusionx.lending.product.service.EligibilityTemplateBranchHistoryService;
import com.fusionx.lending.product.service.EligibilityTemplateBranchService;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Eligibility Template Branch
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   21-07-2019   FXL-1         FXL-42     Dilki        Created
 * <p>
 * *******************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class EligibilityTemplateBranchServiceImpl extends MessagePropertyBase
        implements EligibilityTemplateBranchService {

    @Autowired
    private EligibilityTemplateBranchRepository eligibilityTemplateBranchRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private EligibilityRepository eligibilityRepository;

    @Autowired
    private EligibilityTemplateBranchHistoryService eligibilityTemplateBranchHistoryService;

    @Autowired
    private EligibilityTemplateBranchPendingRepository eligibilityTemplateBranchPendingRepository;

    @Autowired
    private LendingWorkflowService lendingWorkflowService;

    @Autowired
    private LendingWorkflowRepository lendingWorkflowRepository;

    private static final String DEFAULT_APPROVAL_USER = "kie-server";

    private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

    private static final String DOMAIN = "LendingWF";

    @Override
    public List<EligibilityTemplateBranch> getAll() {
        return eligibilityTemplateBranchRepository.findAll();
    }

    @Override
    public Optional<EligibilityTemplateBranch> getById(Long id) {
        Optional<EligibilityTemplateBranch> isPresentEligibilityTemplateBranch = eligibilityTemplateBranchRepository
                .findById(id);
        if (isPresentEligibilityTemplateBranch.isPresent()) {
            return Optional.ofNullable(isPresentEligibilityTemplateBranch.get());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<EligibilityTemplateBranch> getByStatus(String status) {
        return eligibilityTemplateBranchRepository.findByStatus(CommonStatus.valueOf(status));
    }

    @Override
    public EligibilityTemplateBranch addEligibilityTemplateBranch(String tenantId,
                                                                  EligibilityTemplateBranchAddResource eligibilityTemplateAddResource) {

        Optional<Eligibility> isPresentEligibility = eligibilityRepository.findByIdAndNameAndStatus(
                validationService.stringToLong(eligibilityTemplateAddResource.getEligibilityId()),
                eligibilityTemplateAddResource.getEligibilityName(), CommonStatus.ACTIVE);

        if (!isPresentEligibility.isPresent()) {
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "eligibilityId");
        }

        CommonBranchResponseResource branch = validationService.validateComnBranch(tenantId,
                eligibilityTemplateAddResource.getBranchId(), eligibilityTemplateAddResource.getBranchName(),
                EntityPoint.ELIGIBILITY_BRANCH);

        List<EligibilityTemplateBranch> isPresentEligibilityTemplateBranch = eligibilityTemplateBranchRepository
                .findByEligibilityId(validationService.stringToLong(eligibilityTemplateAddResource.getEligibilityId()));

        if (!isPresentEligibilityTemplateBranch.isEmpty()) {
            for (EligibilityTemplateBranch eligibilityTemplateBranch : isPresentEligibilityTemplateBranch) {
                if (eligibilityTemplateBranch.getBranchId().equals(eligibilityTemplateAddResource.getBranchId())) {
                    throw new InvalidServiceIdException(environment.getProperty("common.already-exists"),
                            ServiceEntity.BRANCH_ID, EntityPoint.ELIGIBILITY_BRANCH);
                }
            }
        }

        EligibilityTemplateBranch eligibilityTemplateBranch = new EligibilityTemplateBranch();
        eligibilityTemplateBranch.setTenantId(tenantId);
        eligibilityTemplateBranch.setEligibilityId(isPresentEligibility.get().getId());
        eligibilityTemplateBranch.setEligibilityName(isPresentEligibility.get().getName());
        eligibilityTemplateBranch.setBranchId(branch.getId());
        eligibilityTemplateBranch.setBranchName(branch.getName());
        eligibilityTemplateBranch.setStatus(CommonStatus.valueOf(eligibilityTemplateAddResource.getStatus()));
        eligibilityTemplateBranch.setCreatedDate(validationService.getCreateOrModifyDate());
        eligibilityTemplateBranch.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityTemplateBranch.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

        return eligibilityTemplateBranchRepository.save(eligibilityTemplateBranch);
    }

    @Override
    public EligibilityTemplateBranch updateEligibilityTemplateBranch(String tenantId, Long id,
                                                                     EligibilityTemplateBranchUpdateResource eligibilityTemplateUpdateResource) {

        Optional<EligibilityTemplateBranch> isPresentEligibilityTemplateBranch = eligibilityTemplateBranchRepository.findById(id);

        if (!isPresentEligibilityTemplateBranch.isPresent()) {
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "id");
        }

        CommonBranchResponseResource branch = validationService.validateComnBranch(tenantId,
                eligibilityTemplateUpdateResource.getBranchId(), eligibilityTemplateUpdateResource.getBranchName(),
                EntityPoint.ELIGIBILITY_BRANCH);

        if (!isPresentEligibilityTemplateBranch.get().getVersion().equals(Long.parseLong(eligibilityTemplateUpdateResource.getVersion())))
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");

        if (eligibilityTemplateBranchRepository.existsByEligibilityIdAndBranchIdAndIdNotIn(Long.parseLong(eligibilityTemplateUpdateResource.getEligibilityId()),
                eligibilityTemplateUpdateResource.getBranchId(), id))
            throw new ValidateRecordException(environment.getProperty("eligibilityBranch.unique"), "message");

        LoggerRequest.getInstance().logInfo("Eligibility Template Branch********************************Workflow Call*********************************************");
        approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(), isPresentEligibilityTemplateBranch.get(), eligibilityTemplateUpdateResource);

        return isPresentEligibilityTemplateBranch.get();
    }

    private void approveOrGenerateWorkFlow(String tenantId, String user,
                                           EligibilityTemplateBranch eligibilityTemplateBranch,
                                           EligibilityTemplateBranchUpdateResource eligibilityTemplateUpdateResource) {
        WorkflowRulesResource workflowRulesResource = null;
        Long processId = null;
        WorkflowType workflowType = WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL;
        LendingWorkflow lendingWorkflow = null;

        WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
        workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
        workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

        LoggerRequest.getInstance().logInfo("Eligibility Template Branch********************************Get Workflow Rules*********************************************");
        workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);

        if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
            processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
            if (processId != null) {
                lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
                savePendingEligibilityTemplateBranch(tenantId, eligibilityTemplateBranch, eligibilityTemplateUpdateResource, lendingWorkflow, user);
            }
        } else {
            EligibilityTemplateBranchPending eligibilityTemplateBranchPending = savePendingEligibilityTemplateBranch(tenantId, eligibilityTemplateBranch, eligibilityTemplateUpdateResource, lendingWorkflow, user);
            updateEligibilityTemplateBranch(eligibilityTemplateBranchPending, eligibilityTemplateBranch, CommonApproveStatus.APPROVED, user);
        }
    }

    private EligibilityTemplateBranchPending savePendingEligibilityTemplateBranch(String tenantId,
                                                                                  EligibilityTemplateBranch eligibilityTemplateBranch,
                                                                                  EligibilityTemplateBranchUpdateResource eligibilityTemplateUpdateResource, LendingWorkflow lendingWorkflow,
                                                                                  String user) {

        LoggerRequest.getInstance().logInfo("Eligibility Template Branch********************************Save History*********************************************");
        eligibilityTemplateBranchHistoryService.saveHistory(tenantId, eligibilityTemplateBranch, user);

        Optional<Eligibility> isPresentEligibility = eligibilityRepository.findByIdAndNameAndStatus(
                validationService.stringToLong(eligibilityTemplateUpdateResource.getEligibilityId()),
                eligibilityTemplateUpdateResource.getEligibilityName(), CommonStatus.ACTIVE);

        if (!isPresentEligibility.isPresent()) {
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "eligibilityId");
        }

        CommonBranchResponseResource branch = validationService.validateCommonBranch(tenantId,
                eligibilityTemplateUpdateResource.getBranchId(), eligibilityTemplateUpdateResource.getBranchName());

//		List<EligibilityTemplateBranch> isPresentEligibilityTemplateBranch = eligibilityTemplateBranchRepository
//				.findByEligibilityId(
//						validationService.stringToLong(eligibilityTemplateUpdateResource.getEligibilityId()));
//
//		if (!isPresentEligibilityTemplateBranch.isEmpty()) {
//			for (EligibilityTemplateBranch eligibilityTemplateBranch2 : isPresentEligibilityTemplateBranch) {
//				if (eligibilityTemplateBranch2.getBranchId().equals(eligibilityTemplateUpdateResource.getBranchId())) {
//					throw new InvalidServiceIdException(environment.getProperty("common.duplicate"),
//							ServiceEntity.BRANCH_ID, EntityPoint.ELIGIBILITY_BRANCH);
//				}
//			}
//		}

        EligibilityTemplateBranch charge = eligibilityTemplateBranch;
        charge.setApproveStatus(CommonApproveStatus.PENDING);
        charge.setModifiedUser(user);
        charge.setModifiedDate(validationService.getCreateOrModifyDate());
        charge.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityTemplateBranchRepository.saveAndFlush(charge);

        EligibilityTemplateBranchPending eligibilityTemplateBranchPending = new EligibilityTemplateBranchPending();
        eligibilityTemplateBranchPending.setTenantId(tenantId);

        if (lendingWorkflow != null)
            eligibilityTemplateBranchPending.setLendingWorkflow(lendingWorkflow);

        eligibilityTemplateBranchPending.setEligibilityBranchId(eligibilityTemplateBranch);
        eligibilityTemplateBranchPending.setEligibilityId(isPresentEligibility.get().getId());
        eligibilityTemplateBranchPending.setEligibilityName(isPresentEligibility.get().getName());
        eligibilityTemplateBranchPending.setBranchId(branch.getId());
        eligibilityTemplateBranchPending.setBranchName(branch.getName());
        eligibilityTemplateBranchPending.setStatus(CommonStatus.valueOf(eligibilityTemplateUpdateResource.getStatus()));
        eligibilityTemplateBranchPending.setPcreatedDate(validationService.getCreateOrModifyDate());
        eligibilityTemplateBranchPending.setPcreatedUser(user);
        eligibilityTemplateBranchPending.setSyncTs(validationService.getCreateOrModifyDate());

        return eligibilityTemplateBranchPendingRepository.save(eligibilityTemplateBranchPending);

    }

    private void updateEligibilityTemplateBranch(EligibilityTemplateBranchPending eligibilityTemplateBranchPending,
                                                 EligibilityTemplateBranch chrage, CommonApproveStatus approveStatus, String user) {

        LoggerRequest.getInstance().logInfo(
                "Eligibility Template Branch********************************Save History*********************************************");
        eligibilityTemplateBranchHistoryService.saveHistory(chrage.getTenantId(), chrage, user);

        EligibilityTemplateBranch eligibilityTemplateBranch = chrage;

        if (approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
            eligibilityTemplateBranch.setEligibilityId(eligibilityTemplateBranchPending.getEligibilityId());
            eligibilityTemplateBranch.setEligibilityName(eligibilityTemplateBranchPending.getEligibilityName());
            eligibilityTemplateBranch.setBranchId(eligibilityTemplateBranchPending.getBranchId());
            eligibilityTemplateBranch.setBranchName(eligibilityTemplateBranchPending.getBranchName());
            eligibilityTemplateBranch.setStatus(eligibilityTemplateBranchPending.getStatus());
            eligibilityTemplateBranch.setModifiedDate(eligibilityTemplateBranchPending.getPcreatedDate());
            eligibilityTemplateBranch.setModifiedUser(eligibilityTemplateBranchPending.getPcreatedUser());
            eligibilityTemplateBranch.setApproveStatus(approveStatus);
            eligibilityTemplateBranch.setPenApprovedUser(user);
            eligibilityTemplateBranch.setPenApprovedDate(validationService.getCreateOrModifyDate());
        } else {
            eligibilityTemplateBranch.setPenRejectedUser(user);
            eligibilityTemplateBranch.setPenRejectedDate(validationService.getCreateOrModifyDate());
        }
        eligibilityTemplateBranch.setSyncTs(validationService.getCreateOrModifyDate());

        eligibilityTemplateBranchRepository.saveAndFlush(eligibilityTemplateBranch);
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

        Optional<EligibilityTemplateBranchPending> isPresentEligibilityTemplateBranchPending = eligibilityTemplateBranchPendingRepository.findById(id);

        if (!isPresentEligibilityTemplateBranchPending.isPresent())
            throw new ValidateRecordException(environment.getProperty("invalid.pending-record-id"), "message");

        Optional<EligibilityTemplateBranch> eligibilityTemplateBranch = eligibilityTemplateBranchRepository.findById(isPresentEligibilityTemplateBranchPending.get()
                .getEligibilityBranchId().getId());

        if (!eligibilityTemplateBranch.isPresent())
            throw new ValidateRecordException(environment.getProperty("invalid.eligibility-template-branch-id"), "message");

        Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository.findById(isPresentEligibilityTemplateBranchPending.get().getLendingWorkflow().getId());

        LoggerRequest.getInstance().logInfo("Eligibility Template Branch********************************Get Workflow Rules*********************************************");
        workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);

        if (workflowRulesResource.getApprovalAllowed().equalsIgnoreCase(CommonStatus.NO.toString())) {
            if (lendingWorkflow.get().getCreatedUser().equalsIgnoreCase(LogginAuthentcation.getInstance().getUserName()))
                throw new ValidateRecordException(environment.getProperty("cannot.process.approve"), "message");
        }

        if (workflowStatus.toString().equalsIgnoreCase(WorkflowStatus.COMPLETE.toString())) {
            LoggerRequest.getInstance().logInfo("Eligibility Template Branch********************************Approve Workflow*********************************************");
            validationService.approveWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user, tenantId);
            approveStatus = CommonApproveStatus.APPROVED;
        } else {
            LoggerRequest.getInstance().logInfo("Eligibility Template Branch********************************Abort Workflow*********************************************");
            validationService.abortedWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user, tenantId);
            approveStatus = CommonApproveStatus.REJECTED;
        }

        lendingWorkflowService.update(lendingWorkflow.get(), workflowStatus, user);
        LoggerRequest.getInstance().logInfo("Eligibility Template Branch********************************update*********************************************");

        updateEligibilityTemplateBranch(isPresentEligibilityTemplateBranchPending.get(), eligibilityTemplateBranch.get(), approveStatus, user);
        return true;
    }

    @Override
    public Optional<EligibilityTemplateBranchPending> getByPendingId(Long pendingId) {
        Optional<EligibilityTemplateBranchPending> isPresentEligibilityTemplateBranchPending = eligibilityTemplateBranchPendingRepository
                .findById(pendingId);
        if (isPresentEligibilityTemplateBranchPending.isPresent()) {
            return Optional.ofNullable(isPresentEligibilityTemplateBranchPending.get());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Page<EligibilityTemplateBranchPending> searchEligibilityTemplateBranchPending(String searchq, String status,
                                                                                         String approveStatus, Pageable pageable) {
        if (searchq == null || searchq.isEmpty())
            searchq = null;
        if (status == null || status.isEmpty())
            status = null;
        if (approveStatus == null || approveStatus.isEmpty())
            approveStatus = null;
        return eligibilityTemplateBranchPendingRepository.searchEligibilityTemplateBranchPending(searchq, status,
                approveStatus, pageable);
    }
}
