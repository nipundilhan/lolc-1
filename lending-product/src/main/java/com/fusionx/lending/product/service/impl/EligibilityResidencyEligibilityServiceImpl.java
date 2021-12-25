package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.*;
import com.fusionx.lending.product.enums.*;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.*;
import com.fusionx.lending.product.resources.*;
import com.fusionx.lending.product.service.EligibilityResidencyEligibilityHistoryService;
import com.fusionx.lending.product.service.EligibilityResidencyEligibilityService;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * EligibilityResidencyEligibilityServiceImpl
 * <p>
 * ******************************************************************************************************
 * ###   Date         Story Point   		Task No    Author       Description
 * ------------------------------------------------------------------------------------------------------
 * 1   28-07-2021    FXL_July_2021_3  	FXL-55		Piyumi      Created
 * <p>
 * ******************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class EligibilityResidencyEligibilityServiceImpl extends MessagePropertyBase implements EligibilityResidencyEligibilityService {


    @Autowired
    private ValidationService validationService;

    @Autowired
    private LendingWorkflowService lendingWorkflowService;

    private static final String DEFAULT_APPROVAL_USER = "kie-server";

    private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

    private static final String DOMAIN = "LendingWF";

    @Autowired
    private EligibilityRepository eligibilityRepository;

    @Autowired
    private ResidencyEligibilityRepository residencyEligibilityRepository;

    @Autowired
    private EligibilityPendingRepository eligibilityPendingRepository;

    @Autowired
    private EligibilityResidencyEligibilityHistoryService eligibilityResidencyEligibilityHistoryService;

    private EligibilityResidencyEligibilityPendingRepository eligibilityResidencyEligibilityPendingRepository;

    @Autowired
    public void setEligibilityResidencyEligibilityPendingRepository(
            EligibilityResidencyEligibilityPendingRepository eligibilityResidencyEligibilityPendingRepository) {
        this.eligibilityResidencyEligibilityPendingRepository = eligibilityResidencyEligibilityPendingRepository;
    }

    public EligibilityResidencyEligibilityPendingRepository getEligibilityResidencyEligibilityPendingRepository() {
        return eligibilityResidencyEligibilityPendingRepository;
    }

    private EligibilityResidencyEligibilityHistoryRepository eligibilityResidencyEligibilityHistoryRepository;

    @Autowired
    public void setEligibilityResidencyEligibilityHistoryRepository(
            EligibilityResidencyEligibilityHistoryRepository eligibilityResidencyEligibilityHistoryRepository) {
        this.eligibilityResidencyEligibilityHistoryRepository = eligibilityResidencyEligibilityHistoryRepository;
    }

    public EligibilityResidencyEligibilityHistoryRepository getEligibilityResidencyEligibilityHistoryRepository() {
        return eligibilityResidencyEligibilityHistoryRepository;
    }

    private EligibilityResidencyEligibilityRepository eligibilityResidencyEligibilityRepository;

    @Autowired
    public void setEligibilityResidencyEligibilityRepository(EligibilityResidencyEligibilityRepository eligibilityResidencyEligibilityRepository) {
        this.eligibilityResidencyEligibilityRepository = eligibilityResidencyEligibilityRepository;
    }

    public EligibilityResidencyEligibilityRepository getEligibilityResidencyEligibilityRepository() {
        return eligibilityResidencyEligibilityRepository;
    }

    @Override
    public Optional<EligibilityResidencyEligibility> getById(String tenantId, Long id) {
        Optional<EligibilityResidencyEligibility> isPresentEligibilityResidencyEligibility = eligibilityResidencyEligibilityRepository.findById(id);

        if (isPresentEligibilityResidencyEligibility.isPresent()) {
            return Optional.of(setResidencyTypeName(tenantId, isPresentEligibilityResidencyEligibility.get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Long addEligibilityResidencyEligibility(String tenantId,
                                                   EligibilityResidencyEligibilityAddResource eligibilityResidencyEligibilityAddResource) {

        Optional<Eligibility> isPresentEligibility = eligibilityRepository.findByIdAndNameAndStatus(validationService.stringToLong(eligibilityResidencyEligibilityAddResource.getEligibilityId()), eligibilityResidencyEligibilityAddResource.getEligibilityName(), CommonStatus.ACTIVE);

        if (!isPresentEligibility.isPresent()) {
            throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.ELIGIBILITY_ID, EntityPoint.ELIGIBILITY_RESIDENCY_ELIGIBILITY);
        }

        Optional<ResidencyEligibility> isPresentResidencyEligibility = residencyEligibilityRepository.findByIdAndStatus(validationService.stringToLong(eligibilityResidencyEligibilityAddResource.getResidencyEligibilityId()), CommonStatus.ACTIVE);
        if (!isPresentResidencyEligibility.isPresent()) {
            throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.RESIDENCY_ELIGIBILITY_ID, EntityPoint.ELIGIBILITY_RESIDENCY_ELIGIBILITY);
        }

        Optional<EligibilityResidencyEligibility> isPresentEliResiEliDup = eligibilityResidencyEligibilityRepository.findByResidencyEligibilityIdAndEligibilityIdAndStatus(isPresentResidencyEligibility.get().getId(), isPresentEligibility.get().getId(), CommonStatus.ACTIVE);
        if (isPresentEliResiEliDup.isPresent()) {
            throw new InvalidServiceIdException(environment.getProperty("common.already-exists"), ServiceEntity.RESIDENCY_ELIGIBILITY_ID, EntityPoint.ELIGIBILITY_RESIDENCY_ELIGIBILITY);
        }

        EligibilityResidencyEligibility eligibilityResidencyEligibility = new EligibilityResidencyEligibility();
        eligibilityResidencyEligibility.setTenantId(tenantId);
        eligibilityResidencyEligibility.setEligibility(isPresentEligibility.get());
        eligibilityResidencyEligibility.setResidencyEligibility(isPresentResidencyEligibility.get());
        eligibilityResidencyEligibility.setStatus(CommonStatus.valueOf(eligibilityResidencyEligibilityAddResource.getStatus()));
        eligibilityResidencyEligibility.setCreatedDate(validationService.getCreateOrModifyDate());
        eligibilityResidencyEligibility.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityResidencyEligibility.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        eligibilityResidencyEligibilityRepository.save(eligibilityResidencyEligibility);
        return eligibilityResidencyEligibility.getId();
    }

    @Override
    public Long updateEligibilityResidencyEligibility(String tenantId, EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource) {
        Optional<EligibilityPending> eligibilityPendingOpt = null;
        EligibilityPending eligibilityPending = null;

        Optional<Eligibility> isPresentEligibility = eligibilityRepository.findByIdAndNameAndStatus(validationService.stringToLong(eligibilityResidencyEligibilityUpdateResource.getEligibilityId()), eligibilityResidencyEligibilityUpdateResource.getEligibilityName(), CommonStatus.ACTIVE);

        if (!isPresentEligibility.isPresent()) {
            throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.ELIGIBILITY_ID, EntityPoint.ELIGIBILITY_RESIDENCY_ELIGIBILITY);
        }

        if (CommonApproveStatus.PENDING.toString().equals(isPresentEligibility.get().getApproveStatus() != null ? isPresentEligibility.get().getApproveStatus().toString() : null) && eligibilityResidencyEligibilityUpdateResource.getEligibilityPendingId() == null)
            throw new ValidateRecordException(environment.getProperty("eligibility.can-not-update"), "message");


        //Eligibility Template Update - EligibilityResidencyEligibility Update existing
        if (eligibilityResidencyEligibilityUpdateResource.getId() != null && !eligibilityResidencyEligibilityUpdateResource.getId().isEmpty()) {
            Optional<EligibilityResidencyEligibility> isPresentEligibilityResidencyEligibility = eligibilityResidencyEligibilityRepository.findByEligibilityIdAndId(isPresentEligibility.get().getId(), validationService.stringToLong(eligibilityResidencyEligibilityUpdateResource.getId()));

            if (isPresentEligibilityResidencyEligibility.isPresent()) {
                if (!isPresentEligibilityResidencyEligibility.get().getVersion().equals(Long.parseLong(eligibilityResidencyEligibilityUpdateResource.getVersion()))) {
                    throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION,
                            EntityPoint.ELIGIBILITY_RESIDENCY_ELIGIBILITY);
                }

                Optional<EligibilityResidencyEligibility> isPresentEligibilityResidencyEligibilityDup = eligibilityResidencyEligibilityRepository.findByResidencyEligibilityIdAndEligibilityIdAndStatusAndIdNotIn(validationService.stringToLong(eligibilityResidencyEligibilityUpdateResource.getResidencyEligibilityId()), isPresentEligibility.get().getId(), CommonStatus.ACTIVE, isPresentEligibilityResidencyEligibility.get().getId());
                if (isPresentEligibilityResidencyEligibilityDup.isPresent())
                    throw new InvalidServiceIdException(environment.getProperty("common.already-exists"), ServiceEntity.RESIDENCY_ELIGIBILITY_ID, EntityPoint.ELIGIBILITY_RESIDENCY_ELIGIBILITY);

                if (eligibilityResidencyEligibilityUpdateResource.getEligibilityPendingId() != null && !eligibilityResidencyEligibilityUpdateResource.getEligibilityPendingId().isEmpty()) {
                    eligibilityPendingOpt = eligibilityPendingRepository.findByIdAndStatus(validationService.stringToLong(eligibilityResidencyEligibilityUpdateResource.getEligibilityPendingId()), CommonStatus.ACTIVE);
                    if (!(eligibilityPendingOpt.isPresent()))
                        throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "eligibilityPendingId");

                    eligibilityPending = createEligibilityResidencyEligibilityPending(tenantId, isPresentEligibility.get(), eligibilityPendingOpt.get(), isPresentEligibilityResidencyEligibility.get(), eligibilityPendingOpt.get().getLendingWorkflow(), eligibilityResidencyEligibilityUpdateResource);
                } else {
                    eligibilityPending = approveOrGenerateWorkFlow(tenantId, isPresentEligibility.get(), isPresentEligibilityResidencyEligibility.get(), eligibilityResidencyEligibilityUpdateResource);
                }


            } else {
                LoggerRequest.getInstance().logInfo("EligibilityResidencyEligibility********************************Validate EligibilityResidencyEligibility*********************************************");
                throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "id");
            }
        } else { //Eligibility Template Update -  add new EligibilityResidencyEligibility

            Optional<EligibilityResidencyEligibility> isPresentEligibilityResidencyEligibilityDup = eligibilityResidencyEligibilityRepository.findByResidencyEligibilityIdAndEligibilityIdAndStatus(validationService.stringToLong(eligibilityResidencyEligibilityUpdateResource.getResidencyEligibilityId()), isPresentEligibility.get().getId(), CommonStatus.ACTIVE);
            if (isPresentEligibilityResidencyEligibilityDup.isPresent())
                throw new InvalidServiceIdException(environment.getProperty("common.already-exists"), ServiceEntity.RESIDENCY_ELIGIBILITY_ID, EntityPoint.ELIGIBILITY_RESIDENCY_ELIGIBILITY);


            if (eligibilityResidencyEligibilityUpdateResource.getEligibilityPendingId() != null && !eligibilityResidencyEligibilityUpdateResource.getEligibilityPendingId().isEmpty()) {
                eligibilityPendingOpt = eligibilityPendingRepository.findById(validationService.stringToLong(eligibilityResidencyEligibilityUpdateResource.getEligibilityPendingId()));
                eligibilityPending = createEligibilityResidencyEligibilityPending(tenantId, isPresentEligibility.get(), eligibilityPendingOpt.get(), null, eligibilityPendingOpt.get().getLendingWorkflow(), eligibilityResidencyEligibilityUpdateResource);
            } else {
                eligibilityPending = approveOrGenerateWorkFlow(tenantId, isPresentEligibility.get(), null, eligibilityResidencyEligibilityUpdateResource);
            }
        }

        return eligibilityPending.getId();
    }

    private EligibilityPending approveOrGenerateWorkFlow(String tenantId, Eligibility eligibility, EligibilityResidencyEligibility eligibilityResidencyEligibility, EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource) {
        WorkflowRulesResource workflowRulesResource = null;
        Long processId = null;
        WorkflowType workflowType = WorkflowType.ELIGI_TEMP_APPROVAL;
        LendingWorkflow lendingWorkflow = null;
        EligibilityPending eligibilityPending = null;

        WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
        workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
        workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

        workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
                tenantId);

        if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
            processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
            if (processId != null) {
                lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, LogginAuthentcation.getInstance().getUserName());
                eligibilityPending = createEligibilityResidencyEligibilityPending(tenantId, eligibility, null, eligibilityResidencyEligibility, lendingWorkflow, eligibilityResidencyEligibilityUpdateResource);
            }
        } else {
            directUpdate(tenantId, eligibility, eligibilityResidencyEligibilityUpdateResource);
        }

        return eligibilityPending;

    }

    private EligibilityPending createEligibilityResidencyEligibilityPending(String tenantId, Eligibility eligibility, EligibilityPending eligibilityPending, EligibilityResidencyEligibility eligibilityResidencyEligibility, LendingWorkflow lendingWorkflow, EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource) {

        Optional<ResidencyEligibility> isPresentResidencyEligibility = residencyEligibilityRepository.findByIdAndStatus(validationService.stringToLong(eligibilityResidencyEligibilityUpdateResource.getResidencyEligibilityId()), CommonStatus.ACTIVE);
        if (!isPresentResidencyEligibility.isPresent()) {
            LoggerRequest.getInstance().logInfo("ResidencyEligibility********************************Validate ResidencyEligibility*********************************************");
            throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.RESIDENCY_ELIGIBILITY_ID, EntityPoint.ELIGIBILITY_RESIDENCY_ELIGIBILITY);
        }

        if (eligibilityPending == null) {
            eligibilityPending = createEligibilityPending(tenantId, eligibility, lendingWorkflow);
        }

        EligibilityResidencyEligibilityPending eligibilityResidencyEligibilityPending = new EligibilityResidencyEligibilityPending();
        eligibilityResidencyEligibilityPending.setTenantId(tenantId);
        eligibilityResidencyEligibilityPending.setLendingWorkflow(lendingWorkflow);
        eligibilityResidencyEligibilityPending.setEligibility(eligibility);
        eligibilityResidencyEligibilityPending.setEliResidencyEligibility(eligibilityResidencyEligibility);
        eligibilityResidencyEligibilityPending.setEligibilityPending(eligibilityPending);
        eligibilityResidencyEligibilityPending.setResidencyEligibility(isPresentResidencyEligibility.get());
        eligibilityResidencyEligibilityPending.setApproveStatus(CommonApproveStatus.PENDING);
        eligibilityResidencyEligibilityPending.setStatus(CommonStatus.valueOf(eligibilityResidencyEligibilityUpdateResource.getStatus()));
        eligibilityResidencyEligibilityPending.setCreatedDate(validationService.getCreateOrModifyDate());
        eligibilityResidencyEligibilityPending.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityResidencyEligibilityPending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        eligibilityResidencyEligibilityPending = eligibilityResidencyEligibilityPendingRepository.saveAndFlush(eligibilityResidencyEligibilityPending);

        return eligibilityPending;
    }

    private EligibilityPending createEligibilityPending(String tenantId, Eligibility eligibility, LendingWorkflow lendingWorkflow) {

        Eligibility eligi = eligibility;
        eligi.setApproveStatus(CommonApproveStatus.PENDING);
        eligi.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
        eligi.setModifiedDate(validationService.getCreateOrModifyDate());
        eligi.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityRepository.saveAndFlush(eligi);

        EligibilityPending eligibilityPending = new EligibilityPending();
        eligibilityPending.setTenantId(tenantId);
        eligibilityPending.setLendingWorkflow(lendingWorkflow);
        eligibilityPending.setEligibility(eligibility);
        eligibilityPending.setCode(eligibility.getCode());
        eligibilityPending.setName(eligibility.getName());
        eligibilityPending.setAgeEligibility(eligibility.getAgeEligibility());
        eligibilityPending.setGuarantorEligibility(eligibility.getGuarantorEligibility());
        eligibilityPending.setStatus(eligibility.getStatus());
        //eligibilityPending.setApproveStatus(CommonApproveStatus.PENDING.toString());
        eligibilityPending.setPcreatedDate(validationService.getCreateOrModifyDate());
        eligibilityPending.setPcreatedUser(LogginAuthentcation.getInstance().getUserName());
        eligibilityPending.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityPending = eligibilityPendingRepository.saveAndFlush(eligibilityPending);

        return eligibilityPending;

    }

    private void directUpdate(String tenantId, Eligibility eligibility, EligibilityResidencyEligibilityUpdateResource eligibilityResidencyEligibilityUpdateResource) {

        Optional<EligibilityResidencyEligibility> eligibilityResidencyEligibilityOpt = null;
        EligibilityResidencyEligibility eligibilityResidencyEligibility;

        Optional<ResidencyEligibility> isPresentResidencyEligibility = residencyEligibilityRepository.findByIdAndStatus(validationService.stringToLong(eligibilityResidencyEligibilityUpdateResource.getResidencyEligibilityId()), CommonStatus.ACTIVE);
        if (!isPresentResidencyEligibility.isPresent()) {
            LoggerRequest.getInstance().logInfo("ResidencyEligibility********************************Validate ResidencyEligibility*********************************************");
            throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.RESIDENCY_ELIGIBILITY_ID, EntityPoint.ELIGIBILITY_RESIDENCY_ELIGIBILITY);
        }

        if (eligibilityResidencyEligibilityUpdateResource.getId() != null) {
            eligibilityResidencyEligibilityOpt = eligibilityResidencyEligibilityRepository.findById(validationService.stringToLong(eligibilityResidencyEligibilityUpdateResource.getId()));
            eligibilityResidencyEligibilityHistoryService.saveHistory(tenantId, eligibilityResidencyEligibilityOpt.get(), LogginAuthentcation.getInstance().getUserName());

            eligibilityResidencyEligibility = eligibilityResidencyEligibilityOpt.get();
            eligibilityResidencyEligibility.setModifiedDate(validationService.getCreateOrModifyDate());
            eligibilityResidencyEligibility.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

        } else {
            eligibilityResidencyEligibility = new EligibilityResidencyEligibility();
            eligibilityResidencyEligibility.setCreatedDate(validationService.getCreateOrModifyDate());
            eligibilityResidencyEligibility.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        }
        eligibilityResidencyEligibility.setTenantId(tenantId);
        eligibilityResidencyEligibility.setEligibility(eligibility);
        eligibilityResidencyEligibility.setResidencyEligibility(isPresentResidencyEligibility.get());
        eligibilityResidencyEligibility.setStatus(CommonStatus.valueOf(eligibilityResidencyEligibilityUpdateResource.getStatus()));
        eligibilityResidencyEligibility.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityResidencyEligibility.setApprovedDate(validationService.getCreateOrModifyDate());
        eligibilityResidencyEligibility.setApprovedUser(LogginAuthentcation.getInstance().getUserName());
        eligibilityResidencyEligibilityRepository.save(eligibilityResidencyEligibility);

    }

    @Override
    public Optional<EligibilityResidencyEligibilityPending> getByPendingId(String tenantId, Long pendingId) {
        Optional<EligibilityResidencyEligibilityPending> isPresentEligibilityResidencyEligibilityPending = eligibilityResidencyEligibilityPendingRepository
                .findById(pendingId);

        if (isPresentEligibilityResidencyEligibilityPending.isPresent()) {
            return Optional.of(setResidencyTypeNameForPending(tenantId, isPresentEligibilityResidencyEligibilityPending.get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<EligibilityResidencyEligibility> getByEligibilityId(String tenantId, Long eligibilityId) {
        List<EligibilityResidencyEligibility> eligibilityResidencyEligibilityList = new ArrayList<>();

        Optional<Eligibility> isPresentEligibility = eligibilityRepository
                .findById(eligibilityId);

        if (isPresentEligibility.isPresent())
            eligibilityResidencyEligibilityList = eligibilityResidencyEligibilityRepository.findByEligibilityId(isPresentEligibility.get().getId());

        for (EligibilityResidencyEligibility eligibilityResidencyEligibility : eligibilityResidencyEligibilityList) {
            setResidencyTypeName(tenantId, eligibilityResidencyEligibility);
        }

        return eligibilityResidencyEligibilityList;
    }

    @Override
    public Page<EligibilityResidencyEligibilityPending> searchEligibilityResidencyEligibilityPending(String tenantId, String searchq,
                                                                                                     String status, String approveStatus, Pageable pageable) {
        if (searchq == null || searchq.isEmpty())
            searchq = null;
        if (status == null || status.isEmpty())
            status = null;
        if (approveStatus == null || approveStatus.isEmpty())
            approveStatus = null;
        Page<EligibilityResidencyEligibilityPending> list = eligibilityResidencyEligibilityPendingRepository.searchEligibilityResidencyEligibilityPending(searchq, status, approveStatus,
                pageable);

        for (EligibilityResidencyEligibilityPending eligibilityResidencyEligibilityPending : list.getContent()) {
            setResidencyTypeNameForPending(tenantId, eligibilityResidencyEligibilityPending);
        }

        return list;

    }

    @Override
    public List<EligibilityResidencyEligibility> getByResidencyEligibilityId(String tenantId, Long residencyEligibilityId) {
        List<EligibilityResidencyEligibility> list = eligibilityResidencyEligibilityRepository.findByResidencyEligibilityId(residencyEligibilityId);

        for (EligibilityResidencyEligibility eligibilityResidencyEligibility : list) {
            setResidencyTypeName(tenantId, eligibilityResidencyEligibility);
        }

        return list;

    }

    @Override
    public List<EligibilityResidencyEligibility> getByStatus(String tenantId, String status) {
        List<EligibilityResidencyEligibility> list = eligibilityResidencyEligibilityRepository.findByStatus(CommonStatus.valueOf(status));

        for (EligibilityResidencyEligibility eligibilityResidencyEligibility : list) {
            setResidencyTypeName(tenantId, eligibilityResidencyEligibility);
        }

        return list;
    }

    private EligibilityResidencyEligibility setResidencyTypeName(String tenantId, EligibilityResidencyEligibility eligibilityResidencyEligibility) {
        Optional<ResidencyEligibility> isPresentResidencyEligibility = residencyEligibilityRepository.findById(eligibilityResidencyEligibility.getResidencyEligibility().getId());

        if (isPresentResidencyEligibility.isPresent()) {
            ResidencyType residencyType = validationService.getResidencyType(tenantId, isPresentResidencyEligibility.get().getResidencyTypeId());
            isPresentResidencyEligibility.get().setResidencyTypeName(residencyType.getName());
        }

        return eligibilityResidencyEligibility;
    }

    private EligibilityResidencyEligibilityPending setResidencyTypeNameForPending(String tenantId, EligibilityResidencyEligibilityPending eligibilityResidencyEligibilityPending) {
        Optional<ResidencyEligibility> isPresentResidencyEligibility = residencyEligibilityRepository.findById(eligibilityResidencyEligibilityPending.getResidencyEligibility().getId());

        if (isPresentResidencyEligibility.isPresent()) {
            ResidencyType residencyType = validationService.getResidencyType(tenantId, isPresentResidencyEligibility.get().getResidencyTypeId());
            isPresentResidencyEligibility.get().setResidencyTypeName(residencyType.getName());
        }

        return eligibilityResidencyEligibilityPending;
    }

}
