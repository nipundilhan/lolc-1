package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Eligibility;
import com.fusionx.lending.product.domain.EligibilityTemplateIndustry;
import com.fusionx.lending.product.domain.EligibilityTemplateIndustryPending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.enums.*;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.EligibilityRepository;
import com.fusionx.lending.product.repository.EligibilityTemplateIndustryPendingRepository;
import com.fusionx.lending.product.repository.EligibilityTemplateIndustryRepository;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.resources.*;
import com.fusionx.lending.product.service.EligibilityTemplateIndustryHistoryService;
import com.fusionx.lending.product.service.EligibilityTemplateIndustryService;
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
public class EligibilityTemplateIndustryServiceImpl extends MessagePropertyBase
        implements EligibilityTemplateIndustryService {

    @Autowired
    private EligibilityTemplateIndustryRepository eligibilityTemplateIndustryRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private EligibilityRepository eligibilityRepository;

    @Autowired
    private EligibilityTemplateIndustryHistoryService eligibilityTemplateIndustryHistoryService;

    @Autowired
    private EligibilityTemplateIndustryPendingRepository eligibilityTemplateIndustryPendingRepository;

    @Autowired
    private LendingWorkflowService lendingWorkflowService;

    @Autowired
    private LendingWorkflowRepository lendingWorkflowRepository;

    private static final String DEFAULT_APPROVAL_USER = "kie-server";

    private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

    private static final String DOMAIN = "LendingWF";

    @Override
    public List<EligibilityTemplateIndustry> getAll() {
        return eligibilityTemplateIndustryRepository.findAll();
    }

    @Override
    public Optional<EligibilityTemplateIndustry> getById(Long id) {
        Optional<EligibilityTemplateIndustry> isPresentEligibilityTemplateIndustry = eligibilityTemplateIndustryRepository
                .findById(id);
        if (isPresentEligibilityTemplateIndustry.isPresent()) {
            return Optional.ofNullable(isPresentEligibilityTemplateIndustry.get());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<EligibilityTemplateIndustry> getByStatus(String status) {
        return eligibilityTemplateIndustryRepository.findByStatus(CommonStatus.valueOf(status));
    }

    @Override
    public EligibilityTemplateIndustry addEligibilityTemplateIndustry(String tenantId,
                                                                      EligibilityTemplateIndustryAddResource eligibilityTemplateAddResource) {

        Optional<Eligibility> isPresentEligibility = eligibilityRepository.findByIdAndNameAndStatus(
                validationService.stringToLong(eligibilityTemplateAddResource.getEligibilityId()),
                eligibilityTemplateAddResource.getEligibilityName(), CommonStatus.ACTIVE);

        if (!isPresentEligibility.isPresent()) {
            throw new ValidateRecordException(environment.getProperty("common.not-match"), "eligibilityId");
        }

        ComnSector comnSector = validationService.validateComnSector(tenantId,
                eligibilityTemplateAddResource.getIndustryId(), eligibilityTemplateAddResource.getIndustryName(),
                EntityPoint.ELIGIBILITY_INDUSTRY);

        List<EligibilityTemplateIndustry> isPresentEligibilityTemplateIndustry = eligibilityTemplateIndustryRepository
                .findByEligibilityId(validationService.stringToLong(eligibilityTemplateAddResource.getEligibilityId()));

        if (!isPresentEligibilityTemplateIndustry.isEmpty()) {
            for (EligibilityTemplateIndustry eligibilityTemplateIndustry : isPresentEligibilityTemplateIndustry) {
                if (eligibilityTemplateIndustry.getIndustryId()
                        .equals(eligibilityTemplateAddResource.getIndustryId())) {
                    throw new InvalidServiceIdException(environment.getProperty("common.already-exists"),
                            ServiceEntity.INDUSTRY, EntityPoint.ELIGIBILITY_INDUSTRY);
                }
            }
        }

        EligibilityTemplateIndustry eligibilityTemplateIndustry = new EligibilityTemplateIndustry();
        eligibilityTemplateIndustry.setTenantId(tenantId);
        eligibilityTemplateIndustry.setEligibilityId(isPresentEligibility.get().getId());
        eligibilityTemplateIndustry.setEligibilityName(isPresentEligibility.get().getName());
        eligibilityTemplateIndustry.setIndustryId(comnSector.getId());
        eligibilityTemplateIndustry.setIndustryName(comnSector.getSectorName());
        eligibilityTemplateIndustry.setStatus(CommonStatus.valueOf(eligibilityTemplateAddResource.getStatus()));
        eligibilityTemplateIndustry.setCreatedDate(validationService.getCreateOrModifyDate());
        eligibilityTemplateIndustry.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityTemplateIndustry.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

        return eligibilityTemplateIndustryRepository.save(eligibilityTemplateIndustry);
    }

    @Override
    public EligibilityTemplateIndustry updateEligibilityTemplateIndustry(String tenantId, Long id,
                                                                         EligibilityTemplateIndustryUpdateResource eligibilityTemplateUpdateResource) {

        Optional<EligibilityTemplateIndustry> isPresentEligibilityTemplateIndustry = eligibilityTemplateIndustryRepository.findById(id);

        if (!isPresentEligibilityTemplateIndustry.isPresent()) {
            throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");
        }

        if (eligibilityTemplateIndustryRepository.existsByEligibilityIdAndIndustryIdAndIdNotIn(Long.parseLong(eligibilityTemplateUpdateResource.getEligibilityId()), eligibilityTemplateUpdateResource.getIndustryId(), id))
            throw new ValidateRecordException(environment.getProperty("common.unique"), "message");

        if (!isPresentEligibilityTemplateIndustry.get().getVersion().equals(Long.parseLong(eligibilityTemplateUpdateResource.getVersion())))
            throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.ELIGIBILITY_INDUSTRY);

        LoggerRequest.getInstance().logInfo("Eligibility Template Industry********************************Workflow Call*********************************************");
        approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(), isPresentEligibilityTemplateIndustry.get(), eligibilityTemplateUpdateResource);

        return isPresentEligibilityTemplateIndustry.get();
    }

    private void approveOrGenerateWorkFlow(String tenantId, String user,
                                           EligibilityTemplateIndustry eligibilityTemplateIndustry,
                                           EligibilityTemplateIndustryUpdateResource eligibilityTemplateUpdateResource) {
        WorkflowRulesResource workflowRulesResource = null;
        Long processId = null;
        WorkflowType workflowType = WorkflowType.MASTER_DEFINITION_TEMP_APPROVAL;
        LendingWorkflow lendingWorkflow = null;

        WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
        workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
        workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

        LoggerRequest.getInstance().logInfo("Eligibility Template Industry********************************Get Workflow Rules*********************************************");
        workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);

        if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
            processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
            if (processId != null) {
                lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
                savePendingEligibilityTemplateIndustry(tenantId, eligibilityTemplateIndustry, eligibilityTemplateUpdateResource, lendingWorkflow, user);
            }
        } else {
            EligibilityTemplateIndustryPending eligibilityTemplateIndustryPending = savePendingEligibilityTemplateIndustry(tenantId, eligibilityTemplateIndustry, eligibilityTemplateUpdateResource, lendingWorkflow, user);
            updateEligibilityTemplateIndustry(eligibilityTemplateIndustryPending, eligibilityTemplateIndustry, CommonApproveStatus.APPROVED, user);
        }
    }

    private EligibilityTemplateIndustryPending savePendingEligibilityTemplateIndustry(String tenantId,
                                                                                      EligibilityTemplateIndustry eligibilityTemplateIndustry,
                                                                                      EligibilityTemplateIndustryUpdateResource eligibilityTemplateUpdateResource,
                                                                                      LendingWorkflow lendingWorkflow, String user) {

        LoggerRequest.getInstance().logInfo(
                "Eligibility Template Industry********************************Save History*********************************************");
        eligibilityTemplateIndustryHistoryService.saveHistory(tenantId, eligibilityTemplateIndustry, user);

        EligibilityTemplateIndustry charge = eligibilityTemplateIndustry;
        charge.setApproveStatus(CommonApproveStatus.PENDING);
        charge.setModifiedUser(user);
        charge.setModifiedDate(validationService.getCreateOrModifyDate());
        charge.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityTemplateIndustryRepository.saveAndFlush(charge);

        Optional<Eligibility> isPresentEligibility = eligibilityRepository.findByIdAndNameAndStatus(
                validationService.stringToLong(eligibilityTemplateUpdateResource.getEligibilityId()),
                eligibilityTemplateUpdateResource.getEligibilityName(), CommonStatus.ACTIVE);

        if (!isPresentEligibility.isPresent()) {
            throw new ValidateRecordException(environment.getProperty("common.not-match"), "eligibilityId");
        }

        ComnSector comnSector = validationService.validateComnSector(tenantId,
                eligibilityTemplateUpdateResource.getIndustryId(), eligibilityTemplateUpdateResource.getIndustryName(),
                EntityPoint.ELIGIBILITY_INDUSTRY);

//		List<EligibilityTemplateIndustry> isPresentEligibilityTemplateIndustry = eligibilityTemplateIndustryRepository
//				.findByEligibilityId(
//						validationService.stringToLong(eligibilityTemplateUpdateResource.getEligibilityId()));
//
//		if (!isPresentEligibilityTemplateIndustry.isEmpty()) {
//			for (EligibilityTemplateIndustry eligibilityTemplateIndustry2 : isPresentEligibilityTemplateIndustry) {
//				if (eligibilityTemplateIndustry2.getIndustryId()
//						.equals(eligibilityTemplateUpdateResource.getIndustryId())) {
//					throw new InvalidServiceIdException(environment.getProperty("common.duplicate"),
//							ServiceEntity.INDUSTRY, EntityPoint.ELIGIBILITY_INDUSTRY);
//				}
//			}
//		}

        EligibilityTemplateIndustryPending eligibilityTemplateIndustryPending = new EligibilityTemplateIndustryPending();

        eligibilityTemplateIndustryPending.setTenantId(tenantId);
        if (lendingWorkflow != null)
            eligibilityTemplateIndustryPending.setLendingWorkflow(lendingWorkflow);
        eligibilityTemplateIndustryPending.setEligibilityIndustryId(eligibilityTemplateIndustry);
        eligibilityTemplateIndustryPending.setEligibilityId(isPresentEligibility.get().getId());
        eligibilityTemplateIndustryPending.setEligibilityName(isPresentEligibility.get().getName());
        eligibilityTemplateIndustryPending.setIndustryId(comnSector.getId());
        eligibilityTemplateIndustryPending.setIndustryName(comnSector.getSectorName());
        eligibilityTemplateIndustryPending
                .setStatus(CommonStatus.valueOf(eligibilityTemplateUpdateResource.getStatus()));
        eligibilityTemplateIndustryPending.setPcreatedDate(validationService.getCreateOrModifyDate());
        eligibilityTemplateIndustryPending.setPcreatedUser(user);
        eligibilityTemplateIndustryPending.setSyncTs(validationService.getCreateOrModifyDate());

        return eligibilityTemplateIndustryPendingRepository.save(eligibilityTemplateIndustryPending);

    }

    private void updateEligibilityTemplateIndustry(EligibilityTemplateIndustryPending eligibilityTemplateIndustryPending, EligibilityTemplateIndustry chrage,
                                                   CommonApproveStatus approveStatus, String user) {

        LoggerRequest.getInstance().logInfo("Eligibility Template Industry********************************Save History*********************************************");
        eligibilityTemplateIndustryHistoryService.saveHistory(chrage.getTenantId(), chrage, user);

        EligibilityTemplateIndustry eligibilityTemplateIndustry = chrage;

        if (approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
            eligibilityTemplateIndustry.setEligibilityId(eligibilityTemplateIndustryPending.getEligibilityId());
            eligibilityTemplateIndustry.setEligibilityName(eligibilityTemplateIndustryPending.getEligibilityName());
            eligibilityTemplateIndustry.setIndustryId(eligibilityTemplateIndustryPending.getIndustryId());
            eligibilityTemplateIndustry.setIndustryName(eligibilityTemplateIndustryPending.getIndustryName());
            eligibilityTemplateIndustry.setStatus(eligibilityTemplateIndustryPending.getStatus());
            eligibilityTemplateIndustry.setModifiedDate(eligibilityTemplateIndustryPending.getPcreatedDate());
            eligibilityTemplateIndustry.setModifiedUser(eligibilityTemplateIndustryPending.getPcreatedUser());
            eligibilityTemplateIndustry.setApproveStatus(approveStatus);
            eligibilityTemplateIndustry.setPenApprovedUser(user);
            eligibilityTemplateIndustry.setPenApprovedDate(validationService.getCreateOrModifyDate());
        } else {
            eligibilityTemplateIndustry.setPenRejectedUser(user);
            eligibilityTemplateIndustry.setPenRejectedDate(validationService.getCreateOrModifyDate());
        }
        eligibilityTemplateIndustry.setSyncTs(validationService.getCreateOrModifyDate());

        eligibilityTemplateIndustryRepository.saveAndFlush(eligibilityTemplateIndustry);
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

        Optional<EligibilityTemplateIndustryPending> isPresentEligibilityTemplateIndustryPending = eligibilityTemplateIndustryPendingRepository
                .findById(id);

        /*
         * Optional<EligibilityTemplateIndustry> eligibilityTemplateIndustry =
         * eligibilityTemplateIndustryRepository
         * .findById(isPresentEligibilityTemplateIndustryPending.get().getIndustry().
         * getId());
         *
         * if (!isPresentEligibilityTemplateIndustryPending.get().getIndustry().
         * getApproveStatus() .equals(CommonApproveStatus.PENDING)) throw new
         * ValidateRecordException(environment.getProperty("common.can-not-rej-app"),
         * "message");
         */

        Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository
                .findById(isPresentEligibilityTemplateIndustryPending.get().getLendingWorkflow().getId());

        LoggerRequest.getInstance().logInfo(
                "Eligibility Template Industry********************************Get Workflow Rules*********************************************");
        workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
                tenantId);

        if (workflowRulesResource.getApprovalAllowed().equalsIgnoreCase(CommonStatus.NO.toString())) {
            if (lendingWorkflow.get().getCreatedUser()
                    .equalsIgnoreCase(LogginAuthentcation.getInstance().getUserName()))
                throw new ValidateRecordException(environment.getProperty("cannot.process.approve"), "message");
        }

        if (workflowStatus.toString().equalsIgnoreCase(WorkflowStatus.COMPLETE.toString())) {
            LoggerRequest.getInstance().logInfo(
                    "Eligibility Template Industry********************************Approve Workflow*********************************************");
            validationService.approveWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user,
                    tenantId);
            approveStatus = CommonApproveStatus.APPROVED;
        } else {
            LoggerRequest.getInstance().logInfo(
                    "Eligibility Template Industry********************************Abort Workflow*********************************************");
            validationService.abortedWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user,
                    tenantId);
            approveStatus = CommonApproveStatus.REJECTED;
        }

        lendingWorkflowService.update(lendingWorkflow.get(), workflowStatus, user);

        LoggerRequest.getInstance().logInfo(
                "Eligibility Template Industry********************************update*********************************************");
        /*
         * updateEligibilityTemplateIndustry(isPresentEligibilityTemplateIndustryPending
         * .get(), eligibilityTemplateIndustry.get(), approveStatus, user);
         */

        return true;
    }

    @Override
    public Optional<EligibilityTemplateIndustryPending> getByPendingId(Long pendingId) {
        Optional<EligibilityTemplateIndustryPending> isPresentEligibilityTemplateIndustryPending = eligibilityTemplateIndustryPendingRepository
                .findById(pendingId);
        if (isPresentEligibilityTemplateIndustryPending.isPresent()) {
            return Optional.ofNullable(isPresentEligibilityTemplateIndustryPending.get());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Page<EligibilityTemplateIndustryPending> searchEligibilityTemplateIndustryPending(String searchq,
                                                                                             String status, String approveStatus, Pageable pageable) {
        if (searchq == null || searchq.isEmpty())
            searchq = null;
        if (status == null || status.isEmpty())
            status = null;
        if (approveStatus == null || approveStatus.isEmpty())
            approveStatus = null;
        return eligibilityTemplateIndustryPendingRepository.searchEligibilityTemplateIndustryPending(searchq, status,
                approveStatus, pageable);
    }

}
