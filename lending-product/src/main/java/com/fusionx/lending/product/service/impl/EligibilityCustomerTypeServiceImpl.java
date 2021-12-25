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
import com.fusionx.lending.product.service.EligibilityCustomerTypeHistoryService;
import com.fusionx.lending.product.service.EligibilityCustomerTypeService;
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
 * EligibilityCustomerTypeServiceImpl
 * <p>
 * ******************************************************************************************************
 * ###   Date         Story Point   		Task No    Author       Description
 * ------------------------------------------------------------------------------------------------------
 * 1   29-07-2021    FXL_365			 	FXL-56		Piyumi      Created
 * <p>
 * ******************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class EligibilityCustomerTypeServiceImpl extends MessagePropertyBase implements EligibilityCustomerTypeService {


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
    private EligibilityPendingRepository eligibilityPendingRepository;

    @Autowired
    private EligibilityCustomerTypeHistoryService eligibilityCustomerTypeHistoryService;

    private EligibilityCustomerTypePendingRepository eligibilityCustomerTypePendingRepository;

    @Autowired
    public void setEligibilityCustomerTypePendingRepository(
            EligibilityCustomerTypePendingRepository eligibilityCustomerTypePendingRepository) {
        this.eligibilityCustomerTypePendingRepository = eligibilityCustomerTypePendingRepository;
    }

    public EligibilityCustomerTypePendingRepository getEligibilityCustomerTypePendingRepository() {
        return eligibilityCustomerTypePendingRepository;
    }

    private EligibilityCustomerTypeHistoryRepository eligibilityCustomerTypeHistoryRepository;

    @Autowired
    public void setEligibilityCustomerTypeHistoryRepository(
            EligibilityCustomerTypeHistoryRepository eligibilityCustomerTypeHistoryRepository) {
        this.eligibilityCustomerTypeHistoryRepository = eligibilityCustomerTypeHistoryRepository;
    }

    public EligibilityCustomerTypeHistoryRepository getEligibilityCustomerTypeHistoryRepository() {
        return eligibilityCustomerTypeHistoryRepository;
    }

    private EligibilityCustomerTypeRepository eligibilityCustomerTypeRepository;

    @Autowired
    public void setEligibilityCustomerTypeRepository(EligibilityCustomerTypeRepository eligibilityCustomerTypeRepository) {
        this.eligibilityCustomerTypeRepository = eligibilityCustomerTypeRepository;
    }

    public EligibilityCustomerTypeRepository getEligibilityCustomerTypeRepository() {
        return eligibilityCustomerTypeRepository;
    }

    @Override
    public Optional<EligibilityCustomerType> getById(String tenantId, Long id) {
        Optional<EligibilityCustomerType> isPresentEligibilityCustomerType = eligibilityCustomerTypeRepository.findById(id);

        if (isPresentEligibilityCustomerType.isPresent()) {
            return Optional.of(setCustomerTypeDetails(tenantId, isPresentEligibilityCustomerType.get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Long addEligibilityCustomerType(String tenantId,
                                           EligibilityCustomerTypeAddResource eligibilityCustomerTypeAddResource) {

        Optional<Eligibility> isPresentEligibility = eligibilityRepository.findByIdAndNameAndStatus(validationService.stringToLong(eligibilityCustomerTypeAddResource.getEligibilityId()), eligibilityCustomerTypeAddResource.getEligibilityName(), CommonStatus.ACTIVE);

        if (!isPresentEligibility.isPresent()) {
            LoggerRequest.getInstance().logInfo("Eligibility********************************Validate Eligibility*********************************************");
            throw new InvalidServiceIdException(environment.getProperty(INVALID_ID), ServiceEntity.ELIGIBILITY_ID,
                    EntityPoint.ELIGIBILITY_CUSTOMER_TYPE);
        }

        PerCommonList customerType = validateCustomerType(tenantId, validationService.stringToLong(eligibilityCustomerTypeAddResource.getCustomerTypeId()), eligibilityCustomerTypeAddResource.getCustomerTypeName());

        Optional<EligibilityCustomerType> isPresentEligibilityCustomerTypeDup = eligibilityCustomerTypeRepository.findByCustomerTypeIdAndEligibilityIdAndStatus(
                customerType.getId(), isPresentEligibility.get().getId(), CommonStatus.ACTIVE);
        if (isPresentEligibilityCustomerTypeDup.isPresent())
            throw new InvalidServiceIdException(environment.getProperty("common.already-exists"), ServiceEntity.CUSTOMER_TYPE_ID, EntityPoint.ELIGIBILITY_CUSTOMER_TYPE);


        EligibilityCustomerType eligibilityCustomerType = new EligibilityCustomerType();
        eligibilityCustomerType.setTenantId(tenantId);
        eligibilityCustomerType.setEligibility(isPresentEligibility.get());
        eligibilityCustomerType.setCustomerTypeId(customerType.getId());
        eligibilityCustomerType.setStatus(CommonStatus.valueOf(eligibilityCustomerTypeAddResource.getStatus()));
        eligibilityCustomerType.setCreatedDate(validationService.getCreateOrModifyDate());
        eligibilityCustomerType.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityCustomerType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        eligibilityCustomerTypeRepository.save(eligibilityCustomerType);
        return eligibilityCustomerType.getId();
    }

    private PerCommonList validateCustomerType(String tenantId, Long id, String name) {

        return validationService.validatePersonCommonList(tenantId, id.toString(), name, CommonListCode.ORGANIZATIONTYPE.toString(), ServiceEntity.CUSTOMER_TYPE_ID, EntityPoint.ELIGIBILITY_CUSTOMER_TYPE, "customerType");

    }

    @Override
    public Long updateEligibilityCustomerType(String tenantId,
                                              EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource) {
        Optional<EligibilityPending> eligibilityPendingOpt = null;
        EligibilityPending eligibilityPending = null;

        Optional<Eligibility> isPresentEligibility = eligibilityRepository.findByIdAndNameAndStatus(validationService.stringToLong(eligibilityCustomerTypeUpdateResource.getEligibilityId()), eligibilityCustomerTypeUpdateResource.getEligibilityName(), CommonStatus.ACTIVE);

        if (!isPresentEligibility.isPresent()) {
            LoggerRequest.getInstance().logInfo("Eligibility********************************Validate Eligibility*********************************************");
            throw new InvalidServiceIdException(environment.getProperty(INVALID_ID), ServiceEntity.ELIGIBILITY_ID,
                    EntityPoint.ELIGIBILITY_CUSTOMER_TYPE);
        }

        if (CommonApproveStatus.PENDING.toString().equals(isPresentEligibility.get().getApproveStatus() != null ? isPresentEligibility.get().getApproveStatus().toString() : null) && eligibilityCustomerTypeUpdateResource.getEligibilityPendingId() == null)
            throw new ValidateRecordException(environment.getProperty("eligibility.can-not-update"), "message");


        //Eligibility Template Update - EligibilityCustomerType Update existing
        if (eligibilityCustomerTypeUpdateResource.getId() != null && !eligibilityCustomerTypeUpdateResource.getId().isEmpty()) {
            Optional<EligibilityCustomerType> isPresentEligibilityCustomerType = eligibilityCustomerTypeRepository.findByEligibilityIdAndId(isPresentEligibility.get().getId(), validationService.stringToLong(eligibilityCustomerTypeUpdateResource.getId()));

            if (isPresentEligibilityCustomerType.isPresent()) {
                if (!isPresentEligibilityCustomerType.get().getVersion()
                        .equals(Long.parseLong(eligibilityCustomerTypeUpdateResource.getVersion()))) {
                    throw new InvalidServiceIdException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION,
                            EntityPoint.ELIGIBILITY_CUSTOMER_TYPE);
                }

                Optional<EligibilityCustomerType> isPresentEligibilityCustomerTypeDup = eligibilityCustomerTypeRepository.findByCustomerTypeIdAndEligibilityIdAndStatusAndIdNotIn(
                        validationService.stringToLong(eligibilityCustomerTypeUpdateResource.getCustomerTypeId()), isPresentEligibility.get().getId(), CommonStatus.ACTIVE, isPresentEligibilityCustomerType.get().getId());
                if (isPresentEligibilityCustomerTypeDup.isPresent())
                    throw new InvalidServiceIdException(environment.getProperty("common.already-exists"), ServiceEntity.CUSTOMER_TYPE_ID, EntityPoint.ELIGIBILITY_CUSTOMER_TYPE);

                if (eligibilityCustomerTypeUpdateResource.getEligibilityPendingId() != null && !eligibilityCustomerTypeUpdateResource.getEligibilityPendingId().isEmpty()) {
                    eligibilityPendingOpt = eligibilityPendingRepository.findById(validationService.stringToLong(eligibilityCustomerTypeUpdateResource.getEligibilityPendingId()));


                    if (eligibilityPendingOpt.isPresent())
                        throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "eligibilityPendingId");
                    else
                        throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "eligibilityPendingId");

                    //eligibilityPending = createEligibilityCustomerTypePending(tenantId, isPresentEligibility.get() ,eligibilityPendingOpt.get() , isPresentEligibilityCustomerType.get(), eligibilityPendingOpt.get().getLendingWorkflow(), eligibilityCustomerTypeUpdateResource);
                } else {
                    eligibilityPending = approveOrGenerateWorkFlow(tenantId, isPresentEligibility.get(), isPresentEligibilityCustomerType.get(), eligibilityCustomerTypeUpdateResource);
                }


            } else {
                LoggerRequest.getInstance().logInfo("EligibilityCustomerType********************************Validate EligibilityCustomerType*********************************************");
                throw new InvalidServiceIdException(environment.getProperty(INVALID_ID), ServiceEntity.ID,
                        EntityPoint.ELIGIBILITY_CUSTOMER_TYPE);
            }
        } else { //Eligibility Template Update -  add new EligibilityCustomerType

            Optional<EligibilityCustomerType> isPresentEligibilityCustomerTypeDup = eligibilityCustomerTypeRepository.findByCustomerTypeIdAndEligibilityIdAndStatus(
                    validationService.stringToLong(eligibilityCustomerTypeUpdateResource.getCustomerTypeId()), isPresentEligibility.get().getId(), CommonStatus.ACTIVE);
            if (isPresentEligibilityCustomerTypeDup.isPresent())
                throw new InvalidServiceIdException(environment.getProperty("common.already-exists"), ServiceEntity.CUSTOMER_TYPE_ID, EntityPoint.ELIGIBILITY_CUSTOMER_TYPE);


            if (eligibilityCustomerTypeUpdateResource.getEligibilityPendingId() != null && !eligibilityCustomerTypeUpdateResource.getEligibilityPendingId().isEmpty()) {
                eligibilityPendingOpt = eligibilityPendingRepository.findById(validationService.stringToLong(eligibilityCustomerTypeUpdateResource.getEligibilityPendingId()));
                if (eligibilityPendingOpt.isPresent())
                    throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "eligibilityPendingId");

                eligibilityPending = createEligibilityCustomerTypePending(tenantId, isPresentEligibility.get(), eligibilityPendingOpt.get(), null, eligibilityPendingOpt.get().getLendingWorkflow(), eligibilityCustomerTypeUpdateResource);
            } else {
                eligibilityPending = approveOrGenerateWorkFlow(tenantId, isPresentEligibility.get(), null, eligibilityCustomerTypeUpdateResource);
            }
        }

        return eligibilityPending.getId();
    }

    private EligibilityPending approveOrGenerateWorkFlow(String tenantId, Eligibility eligibility, EligibilityCustomerType eligibilityCustomerType, EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource) {
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
                eligibilityPending = createEligibilityCustomerTypePending(tenantId, eligibility, null, eligibilityCustomerType, lendingWorkflow, eligibilityCustomerTypeUpdateResource);
            }
        } else {
            directUpdate(tenantId, eligibility, eligibilityCustomerTypeUpdateResource);
        }

        return eligibilityPending;

    }

    private EligibilityPending createEligibilityCustomerTypePending(String tenantId, Eligibility eligibility, EligibilityPending eligibilityPending, EligibilityCustomerType eligibilityCustomerType, LendingWorkflow lendingWorkflow, EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource) {

        PerCommonList customerType = validateCustomerType(tenantId, validationService.stringToLong(eligibilityCustomerTypeUpdateResource.getCustomerTypeId()), eligibilityCustomerTypeUpdateResource.getCustomerTypeName());

        if (eligibilityPending == null) {
            eligibilityPending = createEligibilityPending(tenantId, eligibility, lendingWorkflow);
        }

        EligibilityCustomerTypePending eligibilityCustomerTypePending = new EligibilityCustomerTypePending();
        eligibilityCustomerTypePending.setTenantId(tenantId);
        eligibilityCustomerTypePending.setLendingWorkflow(lendingWorkflow);
        eligibilityCustomerTypePending.setEligibility(eligibility);
        eligibilityCustomerTypePending.setEligibilityCustomerType(eligibilityCustomerType);
        eligibilityCustomerTypePending.setEligibilityPending(eligibilityPending);
        eligibilityCustomerTypePending.setCustomerTypeId(customerType.getId());
        eligibilityCustomerTypePending.setApproveStatus(CommonApproveStatus.PENDING);
        eligibilityCustomerTypePending.setStatus(CommonStatus.valueOf(eligibilityCustomerTypeUpdateResource.getStatus()));
        eligibilityCustomerTypePending.setCreatedDate(validationService.getCreateOrModifyDate());
        eligibilityCustomerTypePending.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityCustomerTypePending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        eligibilityCustomerTypePending = eligibilityCustomerTypePendingRepository.saveAndFlush(eligibilityCustomerTypePending);

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
        eligibilityPending.setPcreatedDate(validationService.getCreateOrModifyDate());
        eligibilityPending.setPcreatedUser(LogginAuthentcation.getInstance().getUserName());
        eligibilityPending.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityPending = eligibilityPendingRepository.saveAndFlush(eligibilityPending);

        return eligibilityPending;

    }

    private void directUpdate(String tenantId, Eligibility eligibility, EligibilityCustomerTypeUpdateResource eligibilityCustomerTypeUpdateResource) {

        Optional<EligibilityCustomerType> eligibilityCustomerTypeOpt = null;
        EligibilityCustomerType eligibilityCustomerType;

        PerCommonList customerType = validateCustomerType(tenantId, validationService.stringToLong(eligibilityCustomerTypeUpdateResource.getCustomerTypeId()), eligibilityCustomerTypeUpdateResource.getCustomerTypeName());


        if (eligibilityCustomerTypeUpdateResource.getId() != null) {
            eligibilityCustomerTypeOpt = eligibilityCustomerTypeRepository.findById(validationService.stringToLong(eligibilityCustomerTypeUpdateResource.getId()));
            eligibilityCustomerTypeHistoryService.saveHistory(tenantId, eligibilityCustomerTypeOpt.get(), LogginAuthentcation.getInstance().getUserName());

            eligibilityCustomerType = eligibilityCustomerTypeOpt.get();
            eligibilityCustomerType.setModifiedDate(validationService.getCreateOrModifyDate());
            eligibilityCustomerType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

        } else {
            eligibilityCustomerType = new EligibilityCustomerType();
            eligibilityCustomerType.setCreatedDate(validationService.getCreateOrModifyDate());
            eligibilityCustomerType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        }
        eligibilityCustomerType.setTenantId(tenantId);
        eligibilityCustomerType.setEligibility(eligibility);
        eligibilityCustomerType.setCustomerTypeId(customerType.getId());
        eligibilityCustomerType.setStatus(CommonStatus.valueOf(eligibilityCustomerTypeUpdateResource.getStatus()));
        eligibilityCustomerType.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityCustomerType.setApprovedDate(validationService.getCreateOrModifyDate());
        eligibilityCustomerType.setApprovedUser(LogginAuthentcation.getInstance().getUserName());
        eligibilityCustomerTypeRepository.save(eligibilityCustomerType);

    }

    @Override
    public Optional<EligibilityCustomerTypePending> getByPendingId(String tenantId, Long pendingId) {
        Optional<EligibilityCustomerTypePending> isPresentEligibilityCustomerTypePending = eligibilityCustomerTypePendingRepository
                .findById(pendingId);

        if (isPresentEligibilityCustomerTypePending.isPresent()) {
            return Optional.of(setCustomerTypeDetailsForPending(tenantId, isPresentEligibilityCustomerTypePending.get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<EligibilityCustomerType> getByEligibilityId(String tenantId, Long eligibilityId) {
        List<EligibilityCustomerType> eligibilityCustomerTypeList = new ArrayList<>();

        Optional<Eligibility> isPresentEligibility = eligibilityRepository.findById(eligibilityId);
        if (isPresentEligibility.isPresent())
            eligibilityCustomerTypeList = eligibilityCustomerTypeRepository.findByEligibilityId(isPresentEligibility.get().getId());

        for (EligibilityCustomerType eligibilityCustomerType : eligibilityCustomerTypeList) {
            setCustomerTypeDetails(tenantId, eligibilityCustomerType);
        }

        return eligibilityCustomerTypeList;
    }

    @Override
    public Page<EligibilityCustomerTypePending> searchEligibilityCustomerTypePending(String tenantId, String searchq,
                                                                                     String status, String approveStatus, Pageable pageable) {
        if (searchq == null || searchq.isEmpty())
            searchq = null;
        if (status == null || status.isEmpty())
            status = null;
        if (approveStatus == null || approveStatus.isEmpty())
            approveStatus = null;
        Page<EligibilityCustomerTypePending> eligibilityCustomerTypeList = eligibilityCustomerTypePendingRepository.searchEligibilityCustomerTypePending(
                searchq, status, approveStatus,
                pageable);
        for (EligibilityCustomerTypePending eligibilityCustomerTypePending : eligibilityCustomerTypeList.getContent()) {
            setCustomerTypeDetailsForPending(tenantId, eligibilityCustomerTypePending);
        }

        return eligibilityCustomerTypeList;

    }

    @Override
    public List<EligibilityCustomerType> getByCustomerTypeId(String tenantId, Long customerTypeId) {
        List<EligibilityCustomerType> eligibilityCustomerTypeList = eligibilityCustomerTypeRepository.findByCustomerTypeId(customerTypeId);

        for (EligibilityCustomerType eligibilityCustomerType : eligibilityCustomerTypeList) {
            setCustomerTypeDetails(tenantId, eligibilityCustomerType);
        }

        return eligibilityCustomerTypeList;

    }

    @Override
    public List<EligibilityCustomerType> getByStatus(String tenantId, String status) {
        List<EligibilityCustomerType> eligibilityCustomerTypeList = eligibilityCustomerTypeRepository.findByStatus(CommonStatus.valueOf(status));

        for (EligibilityCustomerType eligibilityCustomerType : eligibilityCustomerTypeList) {
            setCustomerTypeDetails(tenantId, eligibilityCustomerType);
        }

        return eligibilityCustomerTypeList;
    }

    private EligibilityCustomerType setCustomerTypeDetails(String tenantId, EligibilityCustomerType eligibilityCustomerType) {

        PerCommonList personCommonListItem = validationService.getPersonCommonListItemById(tenantId, eligibilityCustomerType.getCustomerTypeId().toString());
        eligibilityCustomerType.setCustomerTypeName(personCommonListItem.getCmlsDesc());
        return eligibilityCustomerType;

    }

    private EligibilityCustomerTypePending setCustomerTypeDetailsForPending(String tenantId, EligibilityCustomerTypePending eligibilityCustomerTypePending) {

        PerCommonList personCommonListItem = validationService.getPersonCommonListItemById(tenantId, eligibilityCustomerTypePending.getCustomerTypeId().toString());
        eligibilityCustomerTypePending.setCustomerTypeName(personCommonListItem.getCmlsDesc());
        return eligibilityCustomerTypePending;

    }


}
