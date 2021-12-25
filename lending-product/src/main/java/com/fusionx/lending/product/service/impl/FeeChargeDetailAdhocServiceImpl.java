package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.*;
import com.fusionx.lending.product.enums.*;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.*;
import com.fusionx.lending.product.resources.*;
import com.fusionx.lending.product.service.FeeChargeDetailAdhocService;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Transactional(rollbackFor = Exception.class)
public class FeeChargeDetailAdhocServiceImpl extends MessagePropertyBase implements FeeChargeDetailAdhocService {

    @Autowired
    private ValidationService validationService;
    @Autowired
    private LendingWorkflowService lendingWorkflowService;
    @Autowired
    private FeeChargePendingRepository feeChargePendingRepository;
    @Autowired
    private FeeChargeRepository feeChargeRepository;
    @Autowired
    private OtherFeeTypeRepository otherFeeTypeRepository;
    @Autowired
    private CalculationFrequencyRepository calculationFrequencyRepository;
    @Autowired
    private ApplicationFrequencyRepository applicationFrequencyRepository;
    @Autowired
    private CommonListItemRepository commonListItemRepository;
    @Autowired
    private FeeChargeDetailAdhocRepository feeChargeDetailAdhocRepository;
    @Autowired
    private FeeChargeDetailAdhocPendingRepository feeChargeDetailAdhocPendingRepository;
    @Autowired
    private FeeChargeDetailAdhocHistoryRepository feeChargeDetailAdhocHistoryRepository;
    @Autowired
    private LendingWorkflowRepository lendingWorkflowRepository;

    private static final String DEFAULT_APPROVAL_USER = "kie-server";
    private static final String DOMAIN_PATH = "com.fusionx.LendingWF";
    private static final String DOMAIN = "LendingWF";


    @Override
    public List<FeeChargeDetailAdhoc> findAll() {

        return feeChargeDetailAdhocRepository.findAll();
    }

    @Override
    public FeeChargeDetailAdhoc create(String tenantId, FeeChargeDetailAdhocAddResource feeChargeDetailAdhocAddResource) {

        Optional<FeeCharge> isFeeCharge = feeChargeRepository
                .findById(validationService.stringToLong(feeChargeDetailAdhocAddResource.getFeeChargeId()));
        if (isFeeCharge.isPresent()) {
            if (!isFeeCharge.get().getName().equalsIgnoreCase(feeChargeDetailAdhocAddResource.getFeeChargeName()))
                throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeName");

        } else {
            throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeId");
        }

        if (!(CommonStatus.ACTIVE).equals(isFeeCharge.get().getStatus())) {
            throw new ValidateRecordException(environment.getProperty("common.status-invaild"), "feeChargeId");
        }

        FeeChargeDetailAdhocPending intermediateAdhoc = createIntermediateFeeChargeDetailAdhocPending(new FeeChargeDetailAdhocPending(), feeChargeDetailAdhocAddResource.getFeeChargeDetailsCommonResource());


        FeeChargeDetailAdhoc feeChargeDetailAdhoc = mapPendingToAdhoc(new FeeChargeDetailAdhoc(), intermediateAdhoc);

        feeChargeDetailAdhoc.setTenantId(tenantId);
        feeChargeDetailAdhoc.setFeeCharge(isFeeCharge.get());
        feeChargeDetailAdhoc.setCreatedDate(validationService.getCreateOrModifyDate());
        feeChargeDetailAdhoc.setSyncTs(validationService.getCreateOrModifyDate());
        feeChargeDetailAdhoc.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        feeChargeDetailAdhoc.setModifiedDate(null);

        feeChargeDetailAdhoc = feeChargeDetailAdhocRepository.save(feeChargeDetailAdhoc);

        saveHistoryFeeChargeDetailAdhoc(null, feeChargeDetailAdhoc, LogginAuthentcation.getInstance().getUserName());

        return feeChargeDetailAdhoc;
    }


    @Override
    public FeeChargePending updateFeeChargeDetailAdhoc(String tenantId, Long id,
                                                       FeeChargeDetailAdhocUpdateResource feeChargeDetailAdhocUpdateResource) {

        Optional<FeeCharge> feeChargeOptional = feeChargeRepository.findById(id);
        if (!feeChargeOptional.isPresent()) {
            throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeId");
        }
        


        if (feeChargeDetailAdhocUpdateResource.getFeeChargePendingId() == null) {
        	
        	
    		List<FeeChargePending> pendingList = feeChargePendingRepository.findAllByFeeChargeAndApproveStatus(feeChargeOptional.get(),CommonApproveStatus.PENDING);
    		if(!pendingList.isEmpty()) {
    			throw new ValidateRecordException(environment.getProperty("feeCharge-pending-active-exists"), MESSAGE);
    		}
    		
//            List<FeeChargePending> findAllByFeeChargeList = feeChargePendingRepository.findAllByFeeCharge(feeChargeOptional.get());
//
//            findAllByFeeChargeList = findAllByFeeChargeList.stream()
//                    .filter(e -> (e.getLendingWorkflow() != null && e.getLendingWorkflow().getWorkflowStatus().equals(WorkflowStatus.ACTIVE)))
//                    .collect(Collectors.toList());
//
//            if (!findAllByFeeChargeList.isEmpty()) {
//                throw new ValidateRecordException(environment.getProperty("feeCharge-pending-active-exists"), MESSAGE);
//            }
        }

        FeeChargePending feeChargePending = approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(), feeChargeDetailAdhocUpdateResource, feeChargeOptional.get());

        return feeChargePending;
    }

    private FeeChargePending approveOrGenerateWorkFlow(String tenantId, String user,
                                                       FeeChargeDetailAdhocUpdateResource feeChargeDetailAdhocUpdateResource, FeeCharge feeCharge) {

        FeeChargePending feeChargePending = null;

        if (feeChargeDetailAdhocUpdateResource.getFeeChargePendingId() != null) {
            Optional<FeeChargePending> feeChargePendingOptional = feeChargePendingRepository.findById(
                    validationService.stringToLong(feeChargeDetailAdhocUpdateResource.getFeeChargePendingId()));
            if (feeChargePendingOptional.isPresent()) {
                feeChargePending = feeChargePendingOptional.get();
            } else {
                throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargePendingId");
            }
        }

        FeeChargeDetailAdhoc feeChargeDetailAdhoc = null;

        if (feeChargeDetailAdhocUpdateResource.getFeeChargeDetailsAdhocId() != null) {
            Optional<FeeChargeDetailAdhoc> isPresentFeeChargeDetailAdhoc = feeChargeDetailAdhocRepository.findById(validationService.stringToLong(feeChargeDetailAdhocUpdateResource.getFeeChargeDetailsAdhocId()));
            if (isPresentFeeChargeDetailAdhoc.isPresent()) {
                feeChargeDetailAdhoc = isPresentFeeChargeDetailAdhoc.get();
                if (!(feeChargeDetailAdhoc.getVersion()).equals(Long.parseLong(feeChargeDetailAdhocUpdateResource.getVersion()))) {
                    throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "version");
                }

            } else {
                // add to validateResource feeChargeDetailAdhocId
                throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeDetailAdhocId");
            }
        }

        if (feeChargePending != null) {
            List<FeeChargeDetailAdhocPending> existingList = null;

            if (feeChargeDetailAdhoc != null) {
                existingList = feeChargeDetailAdhocPendingRepository.findAllByFeeChargePendingAndFeeChargeDetailAdhoc(feeChargePending, feeChargeDetailAdhoc);
            }
            if ((existingList == null) || (existingList.isEmpty())) {
                savePendingFeeChargeDetailAdhoc(tenantId, feeChargePending, feeChargeDetailAdhoc, feeChargeDetailAdhocUpdateResource, user);
            } else {
                throw new ValidateRecordException(environment.getProperty("feeCharge-detail-adhoc-pending-exists"), MESSAGE);
            }
        } else {

            WorkflowRulesResource workflowRulesResource = null;
            Long processId = null;
            WorkflowType workflowType = WorkflowType.FEE_CHARGE_TEMP_APPROVAL;
            LendingWorkflow lendingWorkflow = null;

            WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
            workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
            workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

            workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
                    tenantId);

            if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
                processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource,
                        tenantId);
                if (processId != null) {
                    lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
                    feeChargePending = saveFeeChargePending(feeCharge.getId(), lendingWorkflow.getId(), user);

                    savePendingFeeChargeDetailAdhoc(tenantId, feeChargePending, feeChargeDetailAdhoc, feeChargeDetailAdhocUpdateResource, user);


                }
            } else {

                feeChargePending = saveFeeChargePending(feeCharge.getId(), null, user);

                FeeChargeDetailAdhocPending feeChargeDetailAdhocPending = savePendingFeeChargeDetailAdhoc(tenantId, feeChargePending, feeChargeDetailAdhoc, feeChargeDetailAdhocUpdateResource, user);
                updateAdhocUsingPending(tenantId, feeChargeDetailAdhocPending, user);
            }

        }
        return feeChargePending;
    }

    public FeeChargePending saveFeeChargePending(Long feeChargeId, Long lendingWorkflowId, String user) {
        FeeChargePending feeChargePending = new FeeChargePending();

        if (lendingWorkflowId != null) {
            Optional<LendingWorkflow> lendingWorkflowOptional = lendingWorkflowRepository.findById(lendingWorkflowId);
            if (!lendingWorkflowOptional.isPresent()) {
                throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "lendingWorkFlowId");
            }
            feeChargePending.setLendingWorkflow(lendingWorkflowOptional.get());
        }

        Optional<FeeCharge> feeChargeOptional = feeChargeRepository.findById(feeChargeId);
        if (!feeChargeOptional.isPresent()) {
            throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeId");
        }
        FeeCharge feeCharge = feeChargeOptional.get();
        feeChargePending.setFeeCharge(feeCharge);
        feeChargePending.setTenantId(feeCharge.getTenantId());
        feeChargePending.setCode(feeCharge.getCode());
        feeChargePending.setName(feeCharge.getName());
        feeChargePending.setFeeChargeType(feeCharge.getFeeChargeType());
        feeChargePending.setStatus(feeCharge.getStatus());
		feeChargePending.setApproveStatus(CommonApproveStatus.PENDING);
        feeChargePending.setPcreatedDate(validationService.getCreateOrModifyDate());
        feeChargePending.setPcreatedUser(user);
        feeChargePending.setSyncTs(validationService.getCreateOrModifyDate());
        feeChargePending = feeChargePendingRepository.save(feeChargePending);
        return feeChargePending;
    }

    @Override
    public List<FeeChargeDetailAdhoc> getFeeChargeDetailAdhocByFeeChargeId(Long feeChargeId) {
        Optional<FeeCharge> isFeeCharge = feeChargeRepository.findById(feeChargeId);
        if (!isFeeCharge.isPresent()) {
            return new ArrayList<>();
        }

        return feeChargeDetailAdhocRepository.findAllByFeeCharge(isFeeCharge.get());

    }

    @Override
    public FeeChargeDetailAdhoc getFeeChargeDetailAdhocDetailsById(Long feeChargeDetailAdhocId) {
        Optional<FeeChargeDetailAdhoc> feeChargeDetailAdhocOptional = feeChargeDetailAdhocRepository.findById(feeChargeDetailAdhocId);
        if (feeChargeDetailAdhocOptional.isPresent()) {
            return feeChargeDetailAdhocOptional.get();
        } else {
            return null;
        }

    }

    @Override
    public List<FeeChargeDetailAdhocPending> getPendingAdhocListByFeeChargePendingId(Long feeChargePendingId) {
        Optional<FeeChargePending> feeChargePendingOptional = feeChargePendingRepository.findById(feeChargePendingId);
        if (!feeChargePendingOptional.isPresent()) {
            return new ArrayList<>();
        }

        return feeChargeDetailAdhocPendingRepository.findAllByFeeChargePending(feeChargePendingOptional.get());
    }


    private FeeChargeDetailAdhocPending createIntermediateFeeChargeDetailAdhocPending(FeeChargeDetailAdhocPending fcdap, FeeChargeDetailsCommonResource commonResource) {

        FeeChargeDetailAdhocPending feeChargeDetailAdhocPending = fcdap;

        Optional<ApplicationFrequency> isApplicationFrequency = applicationFrequencyRepository.findById(validationService.stringToLong(commonResource.getApplicationFrequencyId()));
        if (isApplicationFrequency.isPresent()) {
            if (!isApplicationFrequency.get().getName().equalsIgnoreCase(commonResource.getApplicationFrequencyName()))
                throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "applicationFrequencyName");

        } else {
            throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "applicationFrequencyId");
        }


        Optional<CalculationFrequency> isCalculationFrequency = calculationFrequencyRepository
                .findById(validationService.stringToLong(commonResource.getCalculationFrequencyId()));
        if (isCalculationFrequency.isPresent()) {
            if (!isCalculationFrequency.get().getName().equalsIgnoreCase(commonResource.getCalculationFrequencyName()))
                throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "calculationFrequencyName");

        } else {
            throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "calculationFrequencyId");
        }

        Optional<OtherFeeType> isFeeType = otherFeeTypeRepository.findById(validationService.stringToLong(commonResource.getFeeTypeId()));
        if (isFeeType.isPresent()) {
            if (!isFeeType.get().getName().equalsIgnoreCase(commonResource.getFeeTypeName()))
                throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeTypeName");

        } else {
            throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeTypeId");
        }

        if (!(CommonStatus.ACTIVE).equals(isFeeType.get().getStatus())) {
            throw new ValidateRecordException(environment.getProperty("common.status-invaild"), "feeTypeId");
        }
        if (!(CommonStatus.ACTIVE).equals(isCalculationFrequency.get().getStatus())) {
            throw new ValidateRecordException(environment.getProperty("common.status-invaild"), "calculationFrequencyId");
        }
        if (!(CommonStatus.ACTIVE).equals(isApplicationFrequency.get().getStatus())) {
            throw new ValidateRecordException(environment.getProperty("common.status-invaild"), "applicationFrequencyId");
        }

        CommonListItem feeCategory = validateFeeCategory(validationService.stringToLong(commonResource.getFeeCategoryId()), commonResource.getFeeCategoryName());


        feeChargeDetailAdhocPending.setType(FeeChargeDetailsType.valueOf(commonResource.getType()).toString());
        feeChargeDetailAdhocPending.setCalculationFrequency(isCalculationFrequency.get());
        feeChargeDetailAdhocPending.setApplicationFrequency(isApplicationFrequency.get());
        feeChargeDetailAdhocPending.setOtherFeeType(isFeeType.get());
        feeChargeDetailAdhocPending.setFeeCategory(feeCategory);
        feeChargeDetailAdhocPending.setMandatory(YesNo.valueOf(commonResource.getMandatory()).toString());
        feeChargeDetailAdhocPending.setNote(commonResource.getNote());
        feeChargeDetailAdhocPending.setStatus(CommonStatus.valueOf(commonResource.getStatus()));

        return feeChargeDetailAdhocPending;
    }


    private FeeChargeDetailAdhoc mapPendingToAdhoc(FeeChargeDetailAdhoc fcda, FeeChargeDetailAdhocPending fcdap) {

        FeeChargeDetailAdhocPending feeChargeDetailAdhocPending = fcdap;
        FeeChargeDetailAdhoc feeChargeDetailAdhoc = fcda;


        feeChargeDetailAdhoc.setType(feeChargeDetailAdhocPending.getType());
        feeChargeDetailAdhoc.setCalculationFrequency(feeChargeDetailAdhocPending.getCalculationFrequency());
        feeChargeDetailAdhoc.setApplicationFrequency(feeChargeDetailAdhocPending.getApplicationFrequency());
        feeChargeDetailAdhoc.setOtherFeeType(feeChargeDetailAdhocPending.getOtherFeeType());
        feeChargeDetailAdhoc.setFeeCategory(feeChargeDetailAdhocPending.getFeeCategory());
        feeChargeDetailAdhoc.setMandatory(feeChargeDetailAdhocPending.getMandatory());
        feeChargeDetailAdhoc.setNote(feeChargeDetailAdhocPending.getNote());
        feeChargeDetailAdhoc.setStatus(feeChargeDetailAdhocPending.getStatus());

        feeChargeDetailAdhoc.setModifiedDate(validationService.getCreateOrModifyDate());
        feeChargeDetailAdhoc.setSyncTs(validationService.getCreateOrModifyDate());

        return feeChargeDetailAdhoc;


    }

    private FeeChargeDetailAdhocPending savePendingFeeChargeDetailAdhoc(String tenantId, FeeChargePending feeChargePending, FeeChargeDetailAdhoc feeChargeDetailAdhoc, FeeChargeDetailAdhocUpdateResource feeChargeDetailAdhocUpdateResource, String user) {

        FeeChargeDetailAdhocPending feeChargeDetailAdhocPending = createIntermediateFeeChargeDetailAdhocPending(new FeeChargeDetailAdhocPending(), feeChargeDetailAdhocUpdateResource.getFeeChargeDetailsCommonResource());

        feeChargeDetailAdhocPending.setTenantId(tenantId);
        feeChargeDetailAdhocPending.setFeeChargePending(feeChargePending);
        feeChargeDetailAdhocPending.setFeeChargeDetailAdhoc(feeChargeDetailAdhoc);
        feeChargeDetailAdhocPending.setCreatedDate(validationService.getCreateOrModifyDate());
        feeChargeDetailAdhocPending.setSyncTs(validationService.getCreateOrModifyDate());
        feeChargeDetailAdhocPending.setCreatedUser(user);

        feeChargeDetailAdhocPending = feeChargeDetailAdhocPendingRepository.save(feeChargeDetailAdhocPending);

        return feeChargeDetailAdhocPending;

    }


    @Override
    public void approvePendingAdhoc(Long feeChargePendingId) {

        Optional<FeeChargePending> feeChargePendingOptional = feeChargePendingRepository.findById(feeChargePendingId);
        if (!feeChargePendingOptional.isPresent()) {
            throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargePendingId");
        }

        List<FeeChargeDetailAdhocPending> feeChargeDetailAdhocPendingList = feeChargeDetailAdhocPendingRepository.findAllByFeeChargePending(feeChargePendingOptional.get());

        if ((feeChargeDetailAdhocPendingList != null) && (!feeChargeDetailAdhocPendingList.isEmpty())) {
            for (FeeChargeDetailAdhocPending item : feeChargeDetailAdhocPendingList) {
                FeeChargeDetailAdhoc FeeChargeDetailAdhoc = updateAdhocUsingPending(item.getTenantId(), item, item.getCreatedUser());
                saveHistoryFeeChargeDetailAdhoc(item, FeeChargeDetailAdhoc, item.getCreatedUser());
            }
        }
    }


    private FeeChargeDetailAdhoc updateAdhocUsingPending(String tenantId, FeeChargeDetailAdhocPending fcdap, String user) {
        FeeChargeDetailAdhoc feeChargeDetailAdhoc = null;

        if (fcdap.getFeeChargeDetailAdhoc() != null) {
            feeChargeDetailAdhoc = fcdap.getFeeChargeDetailAdhoc();
            feeChargeDetailAdhoc.setModifiedUser(user);
        } else {
            feeChargeDetailAdhoc = new FeeChargeDetailAdhoc();

            feeChargeDetailAdhoc.setFeeCharge(fcdap.getFeeChargePending() != null ? fcdap.getFeeChargePending().getFeeCharge() : null);
            feeChargeDetailAdhoc.setTenantId(tenantId);
            feeChargeDetailAdhoc.setCreatedDate(validationService.getCreateOrModifyDate());
            feeChargeDetailAdhoc.setCreatedUser(user);
            feeChargeDetailAdhoc.setSyncTs(validationService.getCreateOrModifyDate());
        }

        feeChargeDetailAdhoc = mapPendingToAdhoc(feeChargeDetailAdhoc, fcdap);
        return feeChargeDetailAdhocRepository.save(feeChargeDetailAdhoc);

    }


    @Override
    public FeeChargeDetailAdhoc directUpdateFeeChargeDetailAdhoc(Long id, FeeChargeDetailAdhocUpdateResource feeChargeDetailAdhocUpdateResource) {
        Optional<FeeChargeDetailAdhoc> feeChargeDetailAdhocOptional = feeChargeDetailAdhocRepository.findById(id);
        if (!feeChargeDetailAdhocOptional.isPresent()) {
            // add to validateResource feeChargeDetailAdhocId
            throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeChargeDetailAdhocId");
        }

        return directUpdate(null, null, feeChargeDetailAdhocOptional.get(), feeChargeDetailAdhocUpdateResource, LogginAuthentcation.getInstance().getUserName());

    }


    private FeeChargeDetailAdhoc directUpdate(String tenantId, FeeCharge feeCharge, FeeChargeDetailAdhoc fcda, FeeChargeDetailAdhocUpdateResource feeChargeDetailAdhocUpdateResource, String user) {
        FeeChargeDetailAdhoc feeChargeDetailAdhoc = null;

        FeeChargeDetailAdhocPending intermediateAdhoc = createIntermediateFeeChargeDetailAdhocPending(new FeeChargeDetailAdhocPending(), feeChargeDetailAdhocUpdateResource.getFeeChargeDetailsCommonResource());

        if (fcda != null) {
            feeChargeDetailAdhoc = fcda;
            feeChargeDetailAdhoc.setModifiedUser(user);
        } else {
            feeChargeDetailAdhoc = new FeeChargeDetailAdhoc();

            feeChargeDetailAdhoc.setFeeCharge(feeCharge);
            feeChargeDetailAdhoc.setTenantId(tenantId);
            feeChargeDetailAdhoc.setCreatedDate(validationService.getCreateOrModifyDate());
            feeChargeDetailAdhoc.setCreatedUser(user);
            feeChargeDetailAdhoc.setSyncTs(validationService.getCreateOrModifyDate());
        }

        feeChargeDetailAdhoc = mapPendingToAdhoc(feeChargeDetailAdhoc, intermediateAdhoc);
        feeChargeDetailAdhocRepository.save(feeChargeDetailAdhoc);
        return feeChargeDetailAdhoc;

    }

    private CommonListItem validateFeeCategory(Long id, String name) {

        Optional<CommonListItem> commonListItem = commonListItemRepository.findById(id);

        if (!commonListItem.isPresent()) {
            throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeCategoryId");
        }
        if (!commonListItem.get().getReferenceCode().toString()
                .equalsIgnoreCase(CommonListCode.FEE_CATEGORY.toString())) {
            throw new ValidateRecordException(environment.getProperty("commonListItem.referenceCode.invalid"), "feeCategoryId");
        }

        if (!commonListItem.get().getName().equalsIgnoreCase(name))
            throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "feeCategoryName");

        if (!(CommonStatus.ACTIVE).equals(commonListItem.get().getStatus())) {
            throw new ValidateRecordException(environment.getProperty("common.status-invaild"), "feeCategoryId");
        }

        return commonListItem.get();
    }


    private void saveHistoryFeeChargeDetailAdhoc(FeeChargeDetailAdhocPending feeChargeDetailAdhocPending, FeeChargeDetailAdhoc feeChargeDetailAdhoc, String user) {

        FeeChargeDetailAdhocHistory feeChargeDetailAdhocHistory = new FeeChargeDetailAdhocHistory();

        feeChargeDetailAdhocHistory.setTenantId(feeChargeDetailAdhoc.getTenantId());
        feeChargeDetailAdhocHistory.setFeeChargeDetailAdhoc(feeChargeDetailAdhoc);
        feeChargeDetailAdhocHistory.setFeeChargeDetailAdhocPending(feeChargeDetailAdhocPending);
        feeChargeDetailAdhocHistory.setType(feeChargeDetailAdhoc.getType());
        feeChargeDetailAdhocHistory.setCalculationFrequency(feeChargeDetailAdhoc.getCalculationFrequency());
        feeChargeDetailAdhocHistory.setApplicationFrequency(feeChargeDetailAdhoc.getApplicationFrequency());
        feeChargeDetailAdhocHistory.setOtherFeeType(feeChargeDetailAdhoc.getOtherFeeType());
        feeChargeDetailAdhocHistory.setFeeCategory(feeChargeDetailAdhoc.getFeeCategory());
        feeChargeDetailAdhocHistory.setMandatory(feeChargeDetailAdhoc.getMandatory());
        feeChargeDetailAdhocHistory.setNote(feeChargeDetailAdhoc.getNote());
        feeChargeDetailAdhocHistory.setStatus(feeChargeDetailAdhoc.getStatus());
        feeChargeDetailAdhocHistory.setCreatedDate(feeChargeDetailAdhoc.getCreatedDate());
        feeChargeDetailAdhocHistory.setCreatedUser(feeChargeDetailAdhoc.getCreatedUser());
        feeChargeDetailAdhocHistory.setVersion(feeChargeDetailAdhoc.getVersion());
        feeChargeDetailAdhocHistory.setSyncTs(feeChargeDetailAdhoc.getSyncTs());

        feeChargeDetailAdhocHistoryRepository.save(feeChargeDetailAdhocHistory);
    }


    @Override
    public List<FeeChargeDetailAdhoc> findByStatus(String status) {

        return feeChargeDetailAdhocRepository.findAllByStatus(CommonStatus.valueOf(status));
    }

    @Override
    public List<FeeChargeDetailAdhoc> findByCategory(String code) {

        return feeChargeDetailAdhocRepository.findAllByAndFeeCategoryReferenceCodeAndFeeCategoryCode(CommonListCode.FEE_CATEGORY.toString(), code);
    }

    @Override
    public List<FeeChargeDetailAdhoc> findByFeeTypeCode(String feeTypeCode) {

        return feeChargeDetailAdhocRepository.findAllByOtherFeeTypeCode(feeTypeCode);
    }


}
