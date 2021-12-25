package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Eligibility;
import com.fusionx.lending.product.domain.EligibilityTemplateLegalStructure;
import com.fusionx.lending.product.domain.EligibilityTemplateLegalStructurePending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.enums.*;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.EligibilityRepository;
import com.fusionx.lending.product.repository.EligibilityTemplateLegalStructurePendingRepository;
import com.fusionx.lending.product.repository.EligibilityTemplateLegalStructureRepository;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.resources.*;
import com.fusionx.lending.product.service.EligibilityTemplateLegalStructureHistoryService;
import com.fusionx.lending.product.service.EligibilityTemplateLegalStructureService;
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
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   21-07-2019   FXL-1         FXL-42     Dilki        Created
 * <p>
 * *******************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class EligibilityTemplateLegalStructureServiceImpl extends MessagePropertyBase
        implements EligibilityTemplateLegalStructureService {

    @Autowired
    private EligibilityTemplateLegalStructureRepository eligibilityTemplateLegalStructureRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private EligibilityRepository eligibilityRepository;

    @Autowired
    private EligibilityTemplateLegalStructureHistoryService eligibilityTemplateLegalStructureHistoryService;

    @Autowired
    private EligibilityTemplateLegalStructurePendingRepository eligibilityTemplateLegalStructurePendingRepository;

    @Autowired
    private LendingWorkflowService lendingWorkflowService;

    @Autowired
    private LendingWorkflowRepository lendingWorkflowRepository;

    private static final String DEFAULT_APPROVAL_USER = "kie-server";

    private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

    private static final String DOMAIN = "LendingWF";

    @Override
    public List<EligibilityTemplateLegalStructure> getAll() {
        return eligibilityTemplateLegalStructureRepository.findAll();
    }

    @Override
    public Optional<EligibilityTemplateLegalStructure> getById(Long id) {
        Optional<EligibilityTemplateLegalStructure> isPresentEligibilityTemplateLegalStructure = eligibilityTemplateLegalStructureRepository
                .findById(id);
        if (isPresentEligibilityTemplateLegalStructure.isPresent()) {
            return Optional.ofNullable(isPresentEligibilityTemplateLegalStructure.get());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<EligibilityTemplateLegalStructure> getByStatus(String status) {
        return eligibilityTemplateLegalStructureRepository.findByStatus(CommonStatus.valueOf(status));
    }

    @Override
    public EligibilityTemplateLegalStructure addEligibilityTemplateLegalStructure(String tenantId,
                                                                                  EligibilityTemplateLegalStructureAddResource eligibilityTemplateLegalStructureAddResource) {

        Optional<Eligibility> isPresentEligibility = eligibilityRepository.findByIdAndNameAndStatus(
                validationService.stringToLong(eligibilityTemplateLegalStructureAddResource.getEligibilityId()),
                eligibilityTemplateLegalStructureAddResource.getEligibilityName(), CommonStatus.ACTIVE);

        if (!isPresentEligibility.isPresent()) {
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "eligibilityId");
        }

        LegalStructure legalStructure = validationService.validateLegalStructure(tenantId,
                eligibilityTemplateLegalStructureAddResource.getLegalStructureId(),
                eligibilityTemplateLegalStructureAddResource.getLegalStructureName(),
                EntityPoint.ELIGIBILITY_LEGAL_STRUCTURE);

        List<EligibilityTemplateLegalStructure> isPresentEligibilityTemplateLegalStructure = eligibilityTemplateLegalStructureRepository
                .findByEligibilityId(validationService
                        .stringToLong(eligibilityTemplateLegalStructureAddResource.getEligibilityId()));

        if (!isPresentEligibilityTemplateLegalStructure.isEmpty()) {
            for (EligibilityTemplateLegalStructure eligibilityTemplateLegalStructure : isPresentEligibilityTemplateLegalStructure) {
                if (eligibilityTemplateLegalStructure.getLegalStructureId()
                        .equals(eligibilityTemplateLegalStructureAddResource.getLegalStructureId())) {
                    throw new InvalidServiceIdException(environment.getProperty("common.already-exists"),
                            ServiceEntity.LEGALSTRUCTURE, EntityPoint.ELIGIBILITY_LEGAL_STRUCTURE);
                }
            }

        }

        EligibilityTemplateLegalStructure eligibilityTemplateLegalStructure = new EligibilityTemplateLegalStructure();
        eligibilityTemplateLegalStructure.setTenantId(tenantId);
        eligibilityTemplateLegalStructure.setEligibilityId(isPresentEligibility.get().getId());
        eligibilityTemplateLegalStructure.setEligibilityName(isPresentEligibility.get().getName());
        eligibilityTemplateLegalStructure.setLegalStructureId(legalStructure.getId());
        eligibilityTemplateLegalStructure.setLegalStructureName(legalStructure.getName());
        eligibilityTemplateLegalStructure
                .setStatus(CommonStatus.valueOf(eligibilityTemplateLegalStructureAddResource.getStatus()));
        eligibilityTemplateLegalStructure.setCreatedDate(validationService.getCreateOrModifyDate());
        eligibilityTemplateLegalStructure.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityTemplateLegalStructure.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

        System.out.println("legal");

        return eligibilityTemplateLegalStructureRepository.save(eligibilityTemplateLegalStructure);
    }

    @Override
    public EligibilityTemplateLegalStructure updateEligibilityTemplateLegalStructure(String tenantId, Long id, EligibilityTemplateLegalStructureUpdateResource eligibilityTemplateLegalStructureUpdateResource) {

        Optional<EligibilityTemplateLegalStructure> isPresentEligibilityTemplateLegalStructure = eligibilityTemplateLegalStructureRepository.findById(id);

        if (!isPresentEligibilityTemplateLegalStructure.isPresent()) {
            throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");
        }

        if (eligibilityTemplateLegalStructureRepository.existsByEligibilityIdAndLegalStructureIdAndIdNotIn(Long.parseLong(eligibilityTemplateLegalStructureUpdateResource.getEligibilityId()), eligibilityTemplateLegalStructureUpdateResource.getLegalStructureId(), id))
            throw new ValidateRecordException(environment.getProperty("common.unique"), "message");

        if (!isPresentEligibilityTemplateLegalStructure.get().getVersion().equals(Long.parseLong(eligibilityTemplateLegalStructureUpdateResource.getVersion())))
            throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "version");

        LoggerRequest.getInstance().logInfo("Eligibility Template Legal Structure********************************Workflow Call*********************************************");
        approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(), isPresentEligibilityTemplateLegalStructure.get(), eligibilityTemplateLegalStructureUpdateResource);

        return isPresentEligibilityTemplateLegalStructure.get();
    }

    private void approveOrGenerateWorkFlow(String tenantId, String user,
                                           EligibilityTemplateLegalStructure eligibilityTemplateLegalStructure,
                                           EligibilityTemplateLegalStructureUpdateResource eligibilityTemplateLegalStructureUpdateResource) {
        WorkflowRulesResource workflowRulesResource = null;
        Long processId = null;
        WorkflowType workflowType = WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL;
        LendingWorkflow lendingWorkflow = null;

        WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
        workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
        workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

        LoggerRequest.getInstance().logInfo(
                "Eligibility Template Legal Structure********************************Get Workflow Rules*********************************************");
        workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
                tenantId);

        if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
            processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
            if (processId != null) {
                lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
                savePendingEligibilityTemplateLegalStructure(tenantId, eligibilityTemplateLegalStructure,
                        eligibilityTemplateLegalStructureUpdateResource, lendingWorkflow, user);
            }
        } else {
            EligibilityTemplateLegalStructurePending eligibilityTemplateLegalStructurePending = savePendingEligibilityTemplateLegalStructure(
                    tenantId, eligibilityTemplateLegalStructure, eligibilityTemplateLegalStructureUpdateResource,
                    lendingWorkflow, user);
            updateEligibilityTemplateLegalStructure(eligibilityTemplateLegalStructurePending,
                    eligibilityTemplateLegalStructure, CommonApproveStatus.APPROVED, user);
        }
    }

    private EligibilityTemplateLegalStructurePending savePendingEligibilityTemplateLegalStructure(String tenantId,
                                                                                                  EligibilityTemplateLegalStructure eligibilityTemplateLegalStructure,
                                                                                                  EligibilityTemplateLegalStructureUpdateResource eligibilityTemplateUpdateResource,
                                                                                                  LendingWorkflow lendingWorkflow, String user) {

        LoggerRequest.getInstance().logInfo(
                "Eligibility Template Legal Structure********************************Save History*********************************************");
        eligibilityTemplateLegalStructureHistoryService.saveHistory(tenantId, eligibilityTemplateLegalStructure, user);

        EligibilityTemplateLegalStructure charge = eligibilityTemplateLegalStructure;
        charge.setApproveStatus(CommonApproveStatus.PENDING);
        charge.setModifiedUser(user);
        charge.setModifiedDate(validationService.getCreateOrModifyDate());
        charge.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityTemplateLegalStructureRepository.saveAndFlush(charge);

        Optional<Eligibility> isPresentEligibility = eligibilityRepository.findByIdAndNameAndStatus(
                validationService.stringToLong(eligibilityTemplateUpdateResource.getEligibilityId()),
                eligibilityTemplateUpdateResource.getEligibilityName(), CommonStatus.ACTIVE);

        if (!isPresentEligibility.isPresent()) {
            LoggerRequest.getInstance().logInfo("Eligibility**********************Validate Eligibility**********************");
            throw new ValidateRecordException(environment.getProperty("common.not-match"), "eligibilityId");
        }

        LegalStructure legalStructure = validationService.validateLegalStructure(tenantId, eligibilityTemplateUpdateResource.getLegalStructureId(),
                eligibilityTemplateUpdateResource.getLegalStructureName(), EntityPoint.ELIGIBILITY_LEGAL_STRUCTURE);

//		List<EligibilityTemplateLegalStructure> isPresentEligibilityTemplateLegalStructure = eligibilityTemplateLegalStructureRepository
//				.findByEligibilityId(
//						validationService.stringToLong(eligibilityTemplateUpdateResource.getEligibilityId()));
//
//		if (!isPresentEligibilityTemplateLegalStructure.isEmpty()) {
//			for (EligibilityTemplateLegalStructure EligibilityTemplateLegalStructure : isPresentEligibilityTemplateLegalStructure) {
//				if (eligibilityTemplateLegalStructure.getLegalStructureId()
//						.equals(EligibilityTemplateLegalStructure.getLegalStructureId())) {
//					throw new InvalidServiceIdException(environment.getProperty("common.duplicate"),
//							ServiceEntity.LEGALSTRUCTURE, EntityPoint.ELIGIBILITY_LEGAL_STRUCTURE);
//				}
//			}
//
//		}

        EligibilityTemplateLegalStructurePending eligibilityTemplateLegalStructurePending = new EligibilityTemplateLegalStructurePending();

        eligibilityTemplateLegalStructurePending.setTenantId(tenantId);

        if (lendingWorkflow != null)
            eligibilityTemplateLegalStructurePending.setLendingWorkflow(lendingWorkflow);

        eligibilityTemplateLegalStructurePending.setEligibilityLegalStructureId(eligibilityTemplateLegalStructure);
        eligibilityTemplateLegalStructurePending.setEligibilityId(isPresentEligibility.get().getId());
        eligibilityTemplateLegalStructurePending.setEligibilityName(isPresentEligibility.get().getName());
        eligibilityTemplateLegalStructurePending.setLegalStructureId(legalStructure.getId());
        eligibilityTemplateLegalStructurePending.setLegalStructureName(legalStructure.getName());
        eligibilityTemplateLegalStructurePending.setStatus(CommonStatus.valueOf(eligibilityTemplateUpdateResource.getStatus()));
        eligibilityTemplateLegalStructurePending.setPcreatedDate(validationService.getCreateOrModifyDate());
        eligibilityTemplateLegalStructurePending.setPcreatedUser(user);
        eligibilityTemplateLegalStructurePending.setSyncTs(validationService.getCreateOrModifyDate());

        return eligibilityTemplateLegalStructurePendingRepository.save(eligibilityTemplateLegalStructurePending);

    }

    private void updateEligibilityTemplateLegalStructure(EligibilityTemplateLegalStructurePending eligibilityTemplateLegalStructurePending,
                                                         EligibilityTemplateLegalStructure chrage, CommonApproveStatus approveStatus, String user) {

        LoggerRequest.getInstance().logInfo("Eligibility Template Legal Structure********************************Save History*********************************************");
        eligibilityTemplateLegalStructureHistoryService.saveHistory(chrage.getTenantId(), chrage, user);

        EligibilityTemplateLegalStructure eligibilityTemplateLegalStructure = chrage;

        if (approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
            eligibilityTemplateLegalStructure.setEligibilityId(eligibilityTemplateLegalStructurePending.getEligibilityId());
            eligibilityTemplateLegalStructure.setEligibilityName(eligibilityTemplateLegalStructurePending.getEligibilityName());
            eligibilityTemplateLegalStructure.setLegalStructureId(eligibilityTemplateLegalStructurePending.getLegalStructureId());
            eligibilityTemplateLegalStructure.setLegalStructureName(eligibilityTemplateLegalStructurePending.getLegalStructureName());
            eligibilityTemplateLegalStructure.setStatus(eligibilityTemplateLegalStructurePending.getStatus());
            eligibilityTemplateLegalStructure.setModifiedDate(eligibilityTemplateLegalStructurePending.getPcreatedDate());
            eligibilityTemplateLegalStructure.setModifiedUser(eligibilityTemplateLegalStructurePending.getPcreatedUser());
            eligibilityTemplateLegalStructure.setApproveStatus(approveStatus);
            eligibilityTemplateLegalStructure.setPenApprovedUser(user);
            eligibilityTemplateLegalStructure.setPenApprovedDate(validationService.getCreateOrModifyDate());
        } else {
            eligibilityTemplateLegalStructure.setPenRejectedUser(user);
            eligibilityTemplateLegalStructure.setPenRejectedDate(validationService.getCreateOrModifyDate());
        }

        eligibilityTemplateLegalStructure.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityTemplateLegalStructureRepository.saveAndFlush(eligibilityTemplateLegalStructure);
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

        Optional<EligibilityTemplateLegalStructurePending> isPresentEligibilityTemplateLegalStructurePending = eligibilityTemplateLegalStructurePendingRepository.findById(id);

        if (!isPresentEligibilityTemplateLegalStructurePending.isPresent())
            throw new ValidateRecordException(environment.getProperty("invalid.pending-record-id"), "message");

        Optional<EligibilityTemplateLegalStructure> eligibilityTemplateLegalStructure = eligibilityTemplateLegalStructureRepository.findById(isPresentEligibilityTemplateLegalStructurePending.get().getEligibilityLegalStructureId().getId());

        if (!isPresentEligibilityTemplateLegalStructurePending.get().getEligibilityLegalStructureId().getApproveStatus().equals(CommonApproveStatus.PENDING))
            throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), "message");


        Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository.findById(isPresentEligibilityTemplateLegalStructurePending.get().getLendingWorkflow().getId());

        LoggerRequest.getInstance().logInfo("Eligibility Template Legal Structure********************************Get Workflow Rules*********************************************");
        workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);

        if (workflowRulesResource.getApprovalAllowed().equalsIgnoreCase(CommonStatus.NO.toString())) {
            if (lendingWorkflow.get().getCreatedUser().equalsIgnoreCase(LogginAuthentcation.getInstance().getUserName()))
                throw new ValidateRecordException(environment.getProperty("cannot.process.approve"), "message");
        }

        if (workflowStatus.toString().equalsIgnoreCase(WorkflowStatus.COMPLETE.toString())) {
            LoggerRequest.getInstance().logInfo("Eligibility Template Legal Structure********************************Approve Workflow*********************************************");
            validationService.approveWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user, tenantId);
            approveStatus = CommonApproveStatus.APPROVED;
        } else {
            LoggerRequest.getInstance().logInfo("Eligibility Template Legal Structure********************************Abort Workflow*********************************************");
            validationService.abortedWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user, tenantId);
            approveStatus = CommonApproveStatus.REJECTED;
        }

        lendingWorkflowService.update(lendingWorkflow.get(), workflowStatus, user);
        LoggerRequest.getInstance().logInfo("Eligibility Template Legal Structure********************************update*********************************************");

        updateEligibilityTemplateLegalStructure(isPresentEligibilityTemplateLegalStructurePending.get(), eligibilityTemplateLegalStructure.get(), approveStatus, user);
        return true;
    }

    @Override
    public Optional<EligibilityTemplateLegalStructurePending> getByPendingId(Long pendingId) {

        Optional<EligibilityTemplateLegalStructurePending> isPresentEligibilityTemplateLegalStructurePending = eligibilityTemplateLegalStructurePendingRepository.findById(pendingId);
        if (isPresentEligibilityTemplateLegalStructurePending.isPresent()) {
            return Optional.ofNullable(isPresentEligibilityTemplateLegalStructurePending.get());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Page<EligibilityTemplateLegalStructurePending> searchEligibilityTemplateLegalStructurePending(String searchq,
                                                                                                         String status, String approveStatus, Pageable pageable) {
        if (searchq == null || searchq.isEmpty())
            searchq = null;
        if (status == null || status.isEmpty())
            status = null;
        if (approveStatus == null || approveStatus.isEmpty())
            approveStatus = null;

        return eligibilityTemplateLegalStructurePendingRepository
                .searchEligibilityTemplateLegalStructurePending(searchq, status, approveStatus, pageable);
    }
}
