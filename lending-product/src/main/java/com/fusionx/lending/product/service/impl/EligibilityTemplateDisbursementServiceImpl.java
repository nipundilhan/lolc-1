package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.*;
import com.fusionx.lending.product.enums.*;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.*;
import com.fusionx.lending.product.resources.EligibilityTemplateDisbursementAddResource;
import com.fusionx.lending.product.resources.EligibilityTemplateDisbursementUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.EligibilityTemplateDisbursementService;
import com.fusionx.lending.product.service.EligibilityTemplateDisbursmentHistoryService;
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
 * Eligibility Template Legal Structure
 * <p>
 * *******************************************************************************************************
 * ### Date Story Point Task No Author Description
 * -------------------------------------------------------------------------------------------------------
 * 1 21-07-2019 FXL-1 FXL-42 Dilki Created
 * <p>
 * *******************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class EligibilityTemplateDisbursementServiceImpl extends MessagePropertyBase
        implements EligibilityTemplateDisbursementService {

    @Autowired
    private EligibilityTemplateDisbursementRepository eligibilityTemplateDisbursementRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private CommonListItemRepository commonListItemRepository;

    @Autowired
    private EligibilityRepository eligibilityRepository;

    @Autowired
    private EligibilityTemplateDisbursmentHistoryService eligibilityTemplateDisbursmentHistoryService;

    @Autowired
    private EligibilityTemplateDisbursementPendingRepository eligibilityTemplateDisbursementPendingRepository;

    @Autowired
    private LendingWorkflowService lendingWorkflowService;

    @Autowired
    private LendingWorkflowRepository lendingWorkflowRepository;

    private static final String DEFAULT_APPROVAL_USER = "kie-server";

    private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

    private static final String DOMAIN = "LendingWF";

    @Override
    public List<EligibilityTemplateDisbursement> getAll() {
        return eligibilityTemplateDisbursementRepository.findAll();
    }

    @Override
    public Optional<EligibilityTemplateDisbursement> getById(Long id) {
        Optional<EligibilityTemplateDisbursement> isPresentEligibilityTemplateDisbursment = eligibilityTemplateDisbursementRepository
                .findById(id);
        if (isPresentEligibilityTemplateDisbursment.isPresent()) {
            return Optional.ofNullable(isPresentEligibilityTemplateDisbursment.get());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<EligibilityTemplateDisbursement> getByStatus(String status) {
        return eligibilityTemplateDisbursementRepository.findByStatus(CommonStatus.valueOf(status));
    }

    @Override
    public EligibilityTemplateDisbursement addEligibilityTemplateDisbursment(String tenantId,
                                                                             EligibilityTemplateDisbursementAddResource eligibilityTemplateDisbursementAddResource) {

        Optional<Eligibility> eligibility = eligibilityRepository
                .findByIdAndName(Long.parseLong(eligibilityTemplateDisbursementAddResource.getEligibilityId()), eligibilityTemplateDisbursementAddResource.getEligibilityName());

        if (!eligibility.isPresent()) {
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "eligibilityId");
        } else {
            Eligibility isPresentEligibility = eligibility.get();
            List<EligibilityTemplateDisbursement> isPresentEligibilityTemplateDisbursement = eligibilityTemplateDisbursementRepository
                    .findByEligibilityId(validationService
                            .stringToLong(eligibilityTemplateDisbursementAddResource.getEligibilityId()));

            if (!isPresentEligibilityTemplateDisbursement.isEmpty()) {
                for (EligibilityTemplateDisbursement eligibilityTemplateDisbursement : isPresentEligibilityTemplateDisbursement) {
                    if (eligibilityTemplateDisbursement.getDisbursementId()
                            .equals(eligibilityTemplateDisbursementAddResource.getDisbursementId())) {
//						throw new InvalidServiceIdException(environment.getProperty("common.already-exists"),
//								ServiceEntity.DISBURSETYPE, EntityPoint.ELIGIBILITY_DISBURSMENT);
                        throw new ValidateRecordException(environment.getProperty("common.already-exists"), "disbursementId");
                    }
                }
            }

            CommonListItem disbursement = this.validateDisbursement(
                    validationService.stringToLong(eligibilityTemplateDisbursementAddResource.getDisbursementId()),
                    eligibilityTemplateDisbursementAddResource.getDisbursementName());

            if (disbursement == null) {
                throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "disbursementId");
            }

            EligibilityTemplateDisbursement eligibilityTemplateDisbursement = new EligibilityTemplateDisbursement();
            eligibilityTemplateDisbursement.setTenantId(tenantId);
            eligibilityTemplateDisbursement.setEligibilityId(isPresentEligibility.getId());
            eligibilityTemplateDisbursement.setEligibilityName(isPresentEligibility.getName());
            eligibilityTemplateDisbursement.setDisbursementId(disbursement.getId().toString());
            eligibilityTemplateDisbursement.setDisbursementName(disbursement.getName());
            eligibilityTemplateDisbursement
                    .setStatus(CommonStatus.valueOf(eligibilityTemplateDisbursementAddResource.getStatus()));
            eligibilityTemplateDisbursement.setCreatedDate(validationService.getCreateOrModifyDate());
            eligibilityTemplateDisbursement.setSyncTs(validationService.getCreateOrModifyDate());
            eligibilityTemplateDisbursement.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

            return eligibilityTemplateDisbursementRepository.save(eligibilityTemplateDisbursement);
        }
    }

    @Override
    public EligibilityTemplateDisbursement updateEligibilityTemplateDisbursment(String tenantId, Long id,
                                                                                EligibilityTemplateDisbursementUpdateResource eligibilityTemplateDisbursementUpdateResource) {

        Optional<EligibilityTemplateDisbursement> isPresentEligibilityTemplateDisbursment = eligibilityTemplateDisbursementRepository
                .findById(id);

        if (!isPresentEligibilityTemplateDisbursment.isPresent()) {
            throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "message");
        }

        Optional<Eligibility> eligibility = eligibilityRepository
                .findByIdAndName(Long.parseLong(eligibilityTemplateDisbursementUpdateResource.getEligibilityId()), eligibilityTemplateDisbursementUpdateResource.getEligibilityName());

        if (!eligibility.isPresent()) {
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "eligibilityId");
        } else {
            Eligibility isPresentEligibility = eligibility.get();
            List<EligibilityTemplateDisbursement> isPresentEligibilityTemplateDisbursement = eligibilityTemplateDisbursementRepository
                    .findByEligibilityId(validationService
                            .stringToLong(eligibilityTemplateDisbursementUpdateResource.getEligibilityId()));

            if (!isPresentEligibilityTemplateDisbursement.isEmpty()) {
                for (EligibilityTemplateDisbursement eligibilityTemplateDisbursement : isPresentEligibilityTemplateDisbursement) {
                    if (eligibilityTemplateDisbursement.getDisbursementId()
                            .equals(eligibilityTemplateDisbursementUpdateResource.getDisbursementId())) {
                        throw new ValidateRecordException(environment.getProperty("common.already-exists"), "disbursementId");
                    }
                }
            }
        }

        if (!isPresentEligibilityTemplateDisbursment.get().getVersion()
                .equals(Long.parseLong(eligibilityTemplateDisbursementUpdateResource.getVersion())))
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");


        CommonListItem disbursement = this.validateDisbursement(
                validationService.stringToLong(eligibilityTemplateDisbursementUpdateResource.getDisbursementId()),
                eligibilityTemplateDisbursementUpdateResource.getDisbursementName());

        if (disbursement == null) {
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "disbursementId");
        }

        LoggerRequest.getInstance().logInfo(
                "Eligibility Template Disbursment********************************Workflow Call*********************************************");
        approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(),
                isPresentEligibilityTemplateDisbursment.get(), eligibilityTemplateDisbursementUpdateResource);

        return isPresentEligibilityTemplateDisbursment.get();
    }

    private void approveOrGenerateWorkFlow(String tenantId, String user,
                                           EligibilityTemplateDisbursement eligibilityTemplateDisbursement,
                                           EligibilityTemplateDisbursementUpdateResource eligibilityTemplateUpdateResource) {
        WorkflowRulesResource workflowRulesResource = null;
        Long processId = null;
        WorkflowType workflowType = WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL;
        LendingWorkflow lendingWorkflow = null;

        WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
        workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
        workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

        LoggerRequest.getInstance().logInfo(
                "Eligibility Template Disbursment********************************Get Workflow Rules*********************************************");
        workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
                tenantId);

        if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
            processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
            if (processId != null) {
                lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
                savePendingEligibilityTemplateDisbursment(tenantId, eligibilityTemplateDisbursement,
                        eligibilityTemplateUpdateResource, lendingWorkflow, user);
            }
        } else {
            EligibilityTemplateDisbursementPending eligibilityTemplateDisbursementPending = savePendingEligibilityTemplateDisbursment(
                    tenantId, eligibilityTemplateDisbursement, eligibilityTemplateUpdateResource, lendingWorkflow,
                    user);
            updateEligibilityTemplateDisbursment(eligibilityTemplateDisbursementPending,
                    eligibilityTemplateDisbursement, CommonApproveStatus.APPROVED, user);
        }
    }

    private EligibilityTemplateDisbursementPending savePendingEligibilityTemplateDisbursment(String tenantId,
                                                                                             EligibilityTemplateDisbursement eligibilityTemplateDisbursement,
                                                                                             EligibilityTemplateDisbursementUpdateResource eligibilityTemplateUpdateResource,
                                                                                             LendingWorkflow lendingWorkflow, String user) {

        LoggerRequest.getInstance().logInfo(
                "Eligibility Template Disbursment********************************Save History*********************************************");
        eligibilityTemplateDisbursmentHistoryService.saveHistory(tenantId, eligibilityTemplateDisbursement, user);

        EligibilityTemplateDisbursement charge = eligibilityTemplateDisbursement;
        charge.setApproveStatus(CommonApproveStatus.PENDING);
        charge.setModifiedUser(user);
        charge.setModifiedDate(validationService.getCreateOrModifyDate());
        charge.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityTemplateDisbursementRepository.saveAndFlush(charge);

        Optional<Eligibility> isPresentEligibility = eligibilityRepository.findByIdAndNameAndStatus(
                validationService.stringToLong(eligibilityTemplateUpdateResource.getEligibilityId()),
                eligibilityTemplateUpdateResource.getEligibilityName(), CommonStatus.ACTIVE);

        if (!isPresentEligibility.isPresent()) {
            throw new ValidateRecordException(environment.getProperty("common.not-match"), "eligibilityId");
        }

        List<EligibilityTemplateDisbursement> isPresentEligibilityTemplateDisbursement = eligibilityTemplateDisbursementRepository
                .findByEligibilityId(
                        validationService.stringToLong(eligibilityTemplateUpdateResource.getEligibilityId()));

//		if (!isPresentEligibilityTemplateDisbursement.isEmpty()) {
//			for (EligibilityTemplateDisbursement eligibilityTemplateDisbursement2 : isPresentEligibilityTemplateDisbursement) {
//				if (eligibilityTemplateDisbursement2.getDisbursementId()
//						.equals(eligibilityTemplateUpdateResource.getDisbursementId())) {
//					throw new InvalidServiceIdException(environment.getProperty("common.duplicate"),
//							ServiceEntity.DISBURSETYPE, EntityPoint.ELIGIBILITY_DISBURSMENT);
//				}
//			}
//		}

        CommonListItem disbursement = this.validateDisbursement(
                validationService.stringToLong(eligibilityTemplateUpdateResource.getDisbursementId()),
                eligibilityTemplateUpdateResource.getDisbursementName());

        EligibilityTemplateDisbursementPending eligibilityTemplateDisbursementPending = new EligibilityTemplateDisbursementPending();

        eligibilityTemplateDisbursementPending.setTenantId(tenantId);
        if (lendingWorkflow != null)
            eligibilityTemplateDisbursementPending.setLendingWorkflow(lendingWorkflow);
        eligibilityTemplateDisbursementPending.setEligibilityDisbursmentId(eligibilityTemplateDisbursement);
        eligibilityTemplateDisbursementPending.setEligibilityId(isPresentEligibility.get().getId());
        eligibilityTemplateDisbursementPending.setEligibilityName(isPresentEligibility.get().getName());
        eligibilityTemplateDisbursementPending.setDisbursementId(disbursement.getId().toString());
        eligibilityTemplateDisbursementPending.setDisbursementName(disbursement.getName());
        eligibilityTemplateDisbursementPending
                .setStatus(CommonStatus.valueOf(eligibilityTemplateUpdateResource.getStatus()));
        eligibilityTemplateDisbursementPending.setPcreatedDate(validationService.getCreateOrModifyDate());
        eligibilityTemplateDisbursementPending.setPcreatedUser(user);
        eligibilityTemplateDisbursementPending.setSyncTs(validationService.getCreateOrModifyDate());

        return eligibilityTemplateDisbursementPendingRepository.save(eligibilityTemplateDisbursementPending);

    }

    private void updateEligibilityTemplateDisbursment(
            EligibilityTemplateDisbursementPending eligibilityTemplateDisbursementPending,
            EligibilityTemplateDisbursement chrage, CommonApproveStatus approveStatus, String user) {

        LoggerRequest.getInstance().logInfo(
                "Eligibility Template Disbursment********************************Save History*********************************************");
        eligibilityTemplateDisbursmentHistoryService.saveHistory(chrage.getTenantId(), chrage, user);

        EligibilityTemplateDisbursement eligibilityTemplateDisbursement = chrage;

        if (approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
            eligibilityTemplateDisbursement.setEligibilityId(eligibilityTemplateDisbursementPending.getEligibilityId());
            eligibilityTemplateDisbursement
                    .setEligibilityName(eligibilityTemplateDisbursementPending.getEligibilityName());
            eligibilityTemplateDisbursement
                    .setDisbursementId(eligibilityTemplateDisbursementPending.getDisbursementId());
            eligibilityTemplateDisbursement
                    .setDisbursementName(eligibilityTemplateDisbursementPending.getDisbursementName());
            eligibilityTemplateDisbursement.setStatus(eligibilityTemplateDisbursementPending.getStatus());
            eligibilityTemplateDisbursement.setModifiedDate(eligibilityTemplateDisbursementPending.getPcreatedDate());
            eligibilityTemplateDisbursement.setModifiedUser(eligibilityTemplateDisbursementPending.getPcreatedUser());
            eligibilityTemplateDisbursement.setApproveStatus(approveStatus);
            eligibilityTemplateDisbursement.setPenApprovedUser(user);
            eligibilityTemplateDisbursement.setPenApprovedDate(validationService.getCreateOrModifyDate());
        } else {
            eligibilityTemplateDisbursement.setPenRejectedUser(user);
            eligibilityTemplateDisbursement.setPenRejectedDate(validationService.getCreateOrModifyDate());
        }
        eligibilityTemplateDisbursement.setSyncTs(validationService.getCreateOrModifyDate());

        eligibilityTemplateDisbursementRepository.saveAndFlush(eligibilityTemplateDisbursement);
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

        Optional<EligibilityTemplateDisbursementPending> isPresentEligibilityTemplateDisbursmentPending = eligibilityTemplateDisbursementPendingRepository.findById(id);

        if (!isPresentEligibilityTemplateDisbursmentPending.isPresent())
            throw new ValidateRecordException(environment.getProperty("invalid.pending-record-id"), "message");

        Optional<EligibilityTemplateDisbursement> eligibilityTemplateDisbursment = eligibilityTemplateDisbursementRepository.findById(isPresentEligibilityTemplateDisbursmentPending.get().getEligibilityDisbursmentId().getId());

        if (!isPresentEligibilityTemplateDisbursmentPending.get().getEligibilityDisbursmentId().getApproveStatus().equals(CommonApproveStatus.PENDING))
            throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), "message");

        Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository.findById(isPresentEligibilityTemplateDisbursmentPending.get().getLendingWorkflow().getId());

        LoggerRequest.getInstance().logInfo("Eligibility Template Disbursment********************************Get Workflow Rules*********************************************");
        workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);

        if (workflowRulesResource.getApprovalAllowed().equalsIgnoreCase(CommonStatus.NO.toString())) {
            if (lendingWorkflow.get().getCreatedUser().equalsIgnoreCase(LogginAuthentcation.getInstance().getUserName()))
                throw new ValidateRecordException(environment.getProperty("cannot.process.approve"), "message");
        }

        if (workflowStatus.toString().equalsIgnoreCase(WorkflowStatus.COMPLETE.toString())) {
            LoggerRequest.getInstance().logInfo("Eligibility Template Disbursment********************************Approve Workflow*********************************************");
            validationService.approveWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user, tenantId);
            approveStatus = CommonApproveStatus.APPROVED;
        } else {
            LoggerRequest.getInstance().logInfo("Eligibility Template Disbursment********************************Abort Workflow*********************************************");
            validationService.abortedWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user, tenantId);
            approveStatus = CommonApproveStatus.REJECTED;
        }

        lendingWorkflowService.update(lendingWorkflow.get(), workflowStatus, user);
        LoggerRequest.getInstance().logInfo("Eligibility Template Disbursment********************************update*********************************************");

        updateEligibilityTemplateDisbursment(isPresentEligibilityTemplateDisbursmentPending.get(), eligibilityTemplateDisbursment.get(), approveStatus, user);

        return true;
    }

    @Override
    public Optional<EligibilityTemplateDisbursementPending> getByPendingId(Long pendingId) {
        Optional<EligibilityTemplateDisbursementPending> isPresentEligibilityTemplateDisbursmentPending = eligibilityTemplateDisbursementPendingRepository
                .findById(pendingId);
        if (isPresentEligibilityTemplateDisbursmentPending.isPresent()) {
            return Optional.ofNullable(isPresentEligibilityTemplateDisbursmentPending.get());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Page<EligibilityTemplateDisbursementPending> searchEligibilityTemplateDisbursementPending(String searchq,
                                                                                                     String status, String approveStatus, Pageable pageable) {
        if (searchq == null || searchq.isEmpty())
            searchq = null;
        if (status == null || status.isEmpty())
            status = null;
        if (approveStatus == null || approveStatus.isEmpty())
            approveStatus = null;
        return eligibilityTemplateDisbursementPendingRepository.searchEligibilityTemplateDisbursementPending(searchq,
                status, approveStatus, pageable);
    }

    public CommonListItem validateDisbursement(Long id, String name) {

        Optional<CommonListItem> commonListItem = commonListItemRepository.findById(id);
        if (!commonListItem.isPresent())
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "disbursementId");

        else if (!CommonStatus.ACTIVE.toString().equalsIgnoreCase(commonListItem.get().getStatus().toString()))
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "disbursementId");

        else if (!CommonListReferenceCode.DISBURSETYPE.toString()
                .equalsIgnoreCase(commonListItem.get().getReferenceCode())
                || !commonListItem.get().getName().equalsIgnoreCase(name))
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "disbursementId");

        return commonListItem.get();
    }
}
