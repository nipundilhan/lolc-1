package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Eligibility;
import com.fusionx.lending.product.domain.EligibilityCollateral;
import com.fusionx.lending.product.domain.EligibilityCollateralHistory;
import com.fusionx.lending.product.domain.EligibilityCollateralPending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.RecordNotFoundException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.EligibilityCollateralHistoryRepository;
import com.fusionx.lending.product.repository.EligibilityCollateralPendingRepository;
import com.fusionx.lending.product.repository.EligibilityCollateralRepository;
import com.fusionx.lending.product.repository.EligibilityRepository;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.resources.AssetType;
import com.fusionx.lending.product.resources.EligibilityCollateralAddResource;
import com.fusionx.lending.product.resources.EligibilityCollateralUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.EligibilityCollateralService;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * API Service related to Eligibility Collateral.
 *
 * @author Miyuru Wijesinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        01-07-2021    	-               FX-6889             Miyuru Wijesinghe          Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class EligibilityCollateralServiceImpl implements EligibilityCollateralService {

    private Environment environment;
    private EligibilityCollateralRepository eligibilityCollateralRepository;
    private EligibilityRepository eligibilityRepository;
    private ValidationService validationService;
    private EligibilityCollateralHistoryRepository eligibilityCollateralHistoryRepository;
    private EligibilityCollateralPendingRepository eligibilityCollateralPendingRepository;
    private LendingWorkflowService lendingWorkflowService;
    private LendingWorkflowRepository lendingWorkflowRepository;

    private static final String DEFAULT_APPROVAL_USER = "kie-server";
    private static final String DOMAIN_PATH = "com.fusionx.LendingWF";
    private static final String DOMAIN = "LendingWF";
    private static final String message = "message";

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Autowired
    public void setEligibilityCollateralRepository(EligibilityCollateralRepository eligibilityCollateralRepository) {
        this.eligibilityCollateralRepository = eligibilityCollateralRepository;
    }

    @Autowired
    public void setEligibilityRepository(EligibilityRepository eligibilityRepository) {
        this.eligibilityRepository = eligibilityRepository;
    }

    @Autowired
    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Autowired
    public void setEligibilityCollateralHistoryRepository(EligibilityCollateralHistoryRepository eligibilityCollateralHistoryRepository) {
        this.eligibilityCollateralHistoryRepository = eligibilityCollateralHistoryRepository;
    }

    @Autowired
    public void setEligibilityCollateralPendingRepository(EligibilityCollateralPendingRepository eligibilityCollateralPendingRepository) {
        this.eligibilityCollateralPendingRepository = eligibilityCollateralPendingRepository;
    }

    @Autowired
    public void setLendingWorkflowService(LendingWorkflowService lendingWorkflowService) {
        this.lendingWorkflowService = lendingWorkflowService;
    }

    @Autowired
    public void setLendingWorkflowRepository(LendingWorkflowRepository lendingWorkflowRepository) {
        this.lendingWorkflowRepository = lendingWorkflowRepository;
    }

    @Override
    public List<EligibilityCollateral> findAll(String tenantId) {
        List<EligibilityCollateral> eligibilityCollateralList = eligibilityCollateralRepository.findAll();

        eligibilityCollateralList.forEach(eligibilityCollateral -> setAssetTypeFields(tenantId, eligibilityCollateral));
        return eligibilityCollateralList;
    }

    @Override
    public Optional<EligibilityCollateral> findById(String tenantId, Long id) {
        Optional<EligibilityCollateral> eligibilityCollateralOptional = eligibilityCollateralRepository.findById(id);

        if (eligibilityCollateralOptional.isPresent()) {
            EligibilityCollateral eligibilityCollateral = eligibilityCollateralOptional.get();
            setAssetTypeFields(tenantId, eligibilityCollateral);
            return Optional.of(eligibilityCollateral);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<EligibilityCollateral> findByStatus(String tenantId, String status) {
        List<EligibilityCollateral> eligibilityCollateralList = eligibilityCollateralRepository.findByStatus(CommonStatus.valueOf(status));

        eligibilityCollateralList.forEach(eligibilityCollateral -> setAssetTypeFields(tenantId, eligibilityCollateral));
        return eligibilityCollateralList;
    }

    @Override
    public List<EligibilityCollateral> findByEligibilityId(String tenantId, Long eligibilityId) {
        List<EligibilityCollateral> eligibilityCollateralList = eligibilityCollateralRepository.findByEligibilitiesId(eligibilityId);

        eligibilityCollateralList.forEach(eligibilityCollateral -> setAssetTypeFields(tenantId, eligibilityCollateral));
        return eligibilityCollateralList;
    }

    @Override
    public List<EligibilityCollateral> findByAssetTypeId(String tenantId, Long assetTypeId) {
        List<EligibilityCollateral> eligibilityCollateralList = eligibilityCollateralRepository.findByAssetTypeId(assetTypeId);

        eligibilityCollateralList.forEach(eligibilityCollateral -> setAssetTypeFields(tenantId, eligibilityCollateral));
        return eligibilityCollateralList;
    }


    private void setAssetTypeFields(String tenantId, EligibilityCollateral eligibilityCollateral) {
        AssetType assetType = validationService.getAssetType(tenantId, eligibilityCollateral.getAssetTypeId());

        if (assetType != null && assetType.getServiceStatus() == null) {
            eligibilityCollateral.setAssetTypeName(assetType.getName());
            eligibilityCollateral.setAssetTypeCode(assetType.getCode());
        }
    }
    
    private void setAssetTypeFieldsForPending(String tenantId, EligibilityCollateralPending eligibilityCollateralPending) {
        AssetType assetType = validationService.getAssetType(tenantId, eligibilityCollateralPending.getAssetTypeId());

        if (assetType != null && assetType.getServiceStatus() == null) {
            eligibilityCollateralPending.setAssetTypeName(assetType.getName());
            eligibilityCollateralPending.setAssetTypeCode(assetType.getCode());
        }
    }

    @Override
    public Long saveAndValidateEligibilityCollateral(String tenantId, String createdUser, EligibilityCollateralAddResource eligibilityCollateralAddResource) {

        LoggerRequest.getInstance().logInfo("EligibilityCollateral********************************Validate Eligibility*********************************************");
        Eligibility eligibility = setEligibilityAndValidate(Long.parseLong(eligibilityCollateralAddResource.getEligibilityId()));

        LoggerRequest.getInstance().logInfo("EligibilityCollateral********************************Validate Asset Type*********************************************");
        validationService.validateAssetType(tenantId, eligibilityCollateralAddResource.getAssetTypeId(), eligibilityCollateralAddResource.getAssetTypeName(), EntityPoint.ELIGIBILITY_COLLATERAL);

        LoggerRequest.getInstance().logInfo("EligibilityCollateral********************************Validate Eligibility Collateral Duplicate*********************************************");
        Long eligibilityId = Long.parseLong(eligibilityCollateralAddResource.getEligibilityId());
        Long assetTypeId = Long.parseLong(eligibilityCollateralAddResource.getAssetTypeId());
        if (Boolean.TRUE.equals(eligibilityCollateralRepository.existsByEligibilitiesIdAndAssetTypeId(eligibilityId, assetTypeId)))
            throw new ValidateRecordException(environment.getProperty("eligibilityCollateral.unique"), message);

        LoggerRequest.getInstance().logInfo("EligibilityCollateral********************************Save Eligibility Collateral*********************************************");
        EligibilityCollateral eligibilityCollateral = saveEligibilityCollateral(tenantId, createdUser, eligibility, eligibilityCollateralAddResource);

        return eligibilityCollateral.getId();
    }

    private Eligibility setEligibilityAndValidate(Long eligibilityId) {
        Eligibility eligibility;
        Optional<Eligibility> eligibilityOptional = eligibilityRepository.findByIdAndStatus(eligibilityId, CommonStatus.ACTIVE);

        if (!eligibilityOptional.isPresent()) {
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "eligibilityId");
        } else {
            eligibility = eligibilityOptional.get();
        }
        return eligibility;
    }

    private EligibilityCollateral saveEligibilityCollateral(String tenantId, String createdUser, Eligibility eligibility, EligibilityCollateralAddResource eligibilityCollateralAddResource) {

        EligibilityCollateral eligibilityCollateral = new EligibilityCollateral();
        eligibilityCollateral.setTenantId(tenantId);
        eligibilityCollateral.setEligibilities(eligibility);
        eligibilityCollateral.setAssetTypeId(Long.parseLong(eligibilityCollateralAddResource.getAssetTypeId()));
        eligibilityCollateral.setStatus(CommonStatus.valueOf(eligibilityCollateralAddResource.getStatus()));
        eligibilityCollateral.setCreatedUser(createdUser);
        eligibilityCollateral.setCreatedDate(validationService.getCreateOrModifyDate());
        eligibilityCollateral.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityCollateralRepository.saveAndFlush(eligibilityCollateral);
        return eligibilityCollateral;
    }

    @Override
    public Long updateAndValidateEligibilityCollateral(String tenantId, String createdUser, Long id, EligibilityCollateralUpdateResource eligibilityCollateralUpdateResource) {

        Optional<EligibilityCollateral> isPresentEligibilityCollateral = eligibilityCollateralRepository.findById(id);

        if (!isPresentEligibilityCollateral.isPresent()) {
            throw new ValidateRecordException(environment.getProperty("common.record-not-found"), message);
        } else if (!isPresentEligibilityCollateral.get().getVersion().equals(Long.parseLong(eligibilityCollateralUpdateResource.getVersion()))) {
            throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
        }

        LoggerRequest.getInstance().logInfo("Eligibility Collateral > Validate Collateral");
        Eligibility eligibility = setEligibilityAndValidate(Long.parseLong(eligibilityCollateralUpdateResource.getEligibilityId()));

        LoggerRequest.getInstance().logInfo("EligibilityCollateral > Validate Asset Type");
        validationService.validateAssetType(tenantId, eligibilityCollateralUpdateResource.getAssetTypeId(), eligibilityCollateralUpdateResource.getAssetTypeName(), EntityPoint.ELIGIBILITY_COLLATERAL);

        LoggerRequest.getInstance().logInfo("EligibilityCollateral > Validate Eligibility Collateral Duplicate");
        Long eligibilityId = Long.parseLong(eligibilityCollateralUpdateResource.getEligibilityId());
        Long assetTypeId = Long.parseLong(eligibilityCollateralUpdateResource.getAssetTypeId());

        if (!Boolean.TRUE.equals(eligibilityCollateralRepository.existsByEligibilitiesIdAndAssetTypeIdAndIdNotIn(eligibilityId, assetTypeId, id))) {
            LoggerRequest.getInstance().logInfo("EligibilityCollateral > Initiate Workflow");
            approveOrGenerateWorkFlow(tenantId, createdUser, isPresentEligibilityCollateral.get(), eligibility, eligibilityCollateralUpdateResource);

            return isPresentEligibilityCollateral.get().getId();
        } else {
            throw new ValidateRecordException(environment.getProperty("eligibilityCollateral.unique"), message);
        }
    }

    private EligibilityCollateral updateEligibilityCollateral(EligibilityCollateralPending eligibilityCollateralPending, EligibilityCollateral ec, CommonApproveStatus approveStatus, String user) {

        EligibilityCollateral eligibilityCollateral = ec;

        LoggerRequest.getInstance().logInfo("EligibilityCollateral > Save Eligibility Collateral History");
        saveEligibilityCollateralHistory(user, eligibilityCollateral);

        if (approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
            eligibilityCollateral.setEligibilities(eligibilityCollateralPending.getEligibilitys());
            eligibilityCollateral.setAssetTypeId(eligibilityCollateral.getAssetTypeId());
            eligibilityCollateral.setStatus(eligibilityCollateralPending.getStatus());
            eligibilityCollateral.setModifiedUser(eligibilityCollateralPending.getCreatedUser());
            eligibilityCollateral.setModifiedDate(eligibilityCollateralPending.getCreatedDate());
        }

        eligibilityCollateral.setApproveStatus(approveStatus);

        if (approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
            eligibilityCollateral.setPenApprovedUser(user);
            eligibilityCollateral.setPenApprovedDate(validationService.getCreateOrModifyDate());
        } else {
            eligibilityCollateral.setPenRejectedUser(user);
            eligibilityCollateral.setPenRejectedDate(validationService.getCreateOrModifyDate());
        }

        eligibilityCollateral.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityCollateralRepository.saveAndFlush(eligibilityCollateral);
        return eligibilityCollateral;
    }

    private void saveEligibilityCollateralHistory(String historyCreatedUser, EligibilityCollateral eligibilityCollateral) {

        EligibilityCollateralHistory eligibilityCollateralHistory = new EligibilityCollateralHistory();
        eligibilityCollateralHistory.setEligibilityCollateralId(eligibilityCollateral.getId());
        eligibilityCollateralHistory.setTenantId(eligibilityCollateral.getTenantId());
        eligibilityCollateralHistory.setEligibilityId(eligibilityCollateral.getEligibilities().getId());
        eligibilityCollateralHistory.setAssetTypeId(eligibilityCollateral.getAssetTypeId());
        eligibilityCollateralHistory.setStatus(eligibilityCollateral.getStatus());
        eligibilityCollateralHistory.setApproveStatus(eligibilityCollateral.getApproveStatus());
        eligibilityCollateralHistory.setCreatedUser(eligibilityCollateral.getCreatedUser());
        eligibilityCollateralHistory.setCreatedDate(eligibilityCollateral.getCreatedDate());
        eligibilityCollateralHistory.setModifiedUser(eligibilityCollateral.getModifiedUser());
        eligibilityCollateralHistory.setModifiedDate(eligibilityCollateral.getModifiedDate());
        eligibilityCollateralHistory.setPenApprovedUser(eligibilityCollateral.getPenApprovedUser());
        eligibilityCollateralHistory.setPenApprovedDate(eligibilityCollateral.getPenApprovedDate());
        eligibilityCollateralHistory.setPenRejectedUser(eligibilityCollateral.getPenRejectedUser());
        eligibilityCollateralHistory.setPenRejectedDate(eligibilityCollateral.getPenRejectedDate());
        eligibilityCollateralHistory.setHisCreatedUser(historyCreatedUser);
        eligibilityCollateralHistory.setHisCreatedDate(validationService.getCreateOrModifyDate());
        eligibilityCollateralHistory.setVersion(eligibilityCollateral.getVersion());
        eligibilityCollateralHistory.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityCollateralHistoryRepository.saveAndFlush(eligibilityCollateralHistory);
    }

    private void approveOrGenerateWorkFlow(String tenantId, String user, EligibilityCollateral eligibilityCollateral, Eligibility eligibility, EligibilityCollateralUpdateResource eligibilityCollateralUpdateResource) {
        WorkflowRulesResource workflowRulesResource;
        Long processId;
        WorkflowType workflowType = WorkflowType.ELIGI_TEMP_APPROVAL;
        LendingWorkflow lendingWorkflow = null;

        WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
        workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
        workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

        workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);

        if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
            processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
            if (processId != null) {
                lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
                saveEligibilityCollateralPending(tenantId, eligibilityCollateral, eligibility, eligibilityCollateralUpdateResource, lendingWorkflow, user);
            }
        } else {
            EligibilityCollateralPending eligibilityCollateralPending = saveEligibilityCollateralPending(tenantId, eligibilityCollateral, eligibility, eligibilityCollateralUpdateResource, lendingWorkflow, user);

            LoggerRequest.getInstance().logInfo("EligibilityCollateral > Update Eligibility Collateral");
            updateEligibilityCollateral(eligibilityCollateralPending, eligibilityCollateral, CommonApproveStatus.APPROVED, user);
        }
    }

    private EligibilityCollateralPending saveEligibilityCollateralPending(String tenantId, EligibilityCollateral eligibilityCollateral, Eligibility eligibility, EligibilityCollateralUpdateResource eligibilityCollateralUpdateResource, LendingWorkflow lendingWorkflow, String user) {

        LoggerRequest.getInstance().logInfo("EligibilityCollateral > Save Eligibility Collateral History");
        saveEligibilityCollateralHistory(user, eligibilityCollateral);

        EligibilityCollateral ec = eligibilityCollateral;
        ec.setApproveStatus(CommonApproveStatus.PENDING);
        ec.setModifiedUser(user);
        ec.setModifiedDate(validationService.getCreateOrModifyDate());
        ec.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityCollateralRepository.saveAndFlush(ec);

        EligibilityCollateralPending eligibilityCollateralPending = new EligibilityCollateralPending();
        eligibilityCollateralPending.setTenantId(tenantId);

        if (lendingWorkflow != null) {
            eligibilityCollateralPending.setLendingWorkflow(lendingWorkflow);
        }

        eligibilityCollateralPending.setEligibilityCollaterals(ec);
        eligibilityCollateralPending.setEligibilitys(eligibility);
        eligibilityCollateralPending.setAssetTypeId(Long.parseLong(eligibilityCollateralUpdateResource.getAssetTypeId()));
        eligibilityCollateralPending.setStatus(CommonStatus.valueOf(eligibilityCollateralUpdateResource.getStatus()));
        eligibilityCollateralPending.setCreatedDate(validationService.getCreateOrModifyDate());
        eligibilityCollateralPending.setCreatedUser(user);
        eligibilityCollateralPending.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityCollateralPendingRepository.save(eligibilityCollateralPending);

        return eligibilityCollateralPending;
    }

    @Override
    public Boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus) {

        WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();

        workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
        workflowInitiationRequestResource.setApprovWorkflowType(WorkflowType.ELIGI_TEMP_APPROVAL);
        WorkflowRulesResource workflowRulesResource;
        WorkflowType workflowType = WorkflowType.ELIGI_TEMP_APPROVAL;
        String user = LogginAuthentcation.getInstance().getUserName();
        CommonApproveStatus approveStatus = CommonApproveStatus.PENDING;

        Optional<EligibilityCollateralPending> isPresentEligibilityCollateralPending = eligibilityCollateralPendingRepository.findById(id);

        if (isPresentEligibilityCollateralPending.isPresent()) {
            Optional<EligibilityCollateral> ec = eligibilityCollateralRepository.findById(isPresentEligibilityCollateralPending.get().getEligibilityCollaterals().getId());

            if (!isPresentEligibilityCollateralPending.get().getEligibilityCollaterals().getApproveStatus().equals(CommonApproveStatus.PENDING))
                throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), message);

            Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository.findById(isPresentEligibilityCollateralPending.get().getLendingWorkflow().getId());

            if (lendingWorkflow.isPresent()) {
                workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);

                if (workflowRulesResource.getApprovalAllowed().equalsIgnoreCase(CommonStatus.NO.toString()) && lendingWorkflow.get().getCreatedUser().equalsIgnoreCase(LogginAuthentcation.getInstance().getUserName())) {
                    throw new ValidateRecordException(environment.getProperty("cannot.process.approve"), message);
                }

                if (workflowStatus.toString().equalsIgnoreCase(WorkflowStatus.COMPLETE.toString())) {
                    validationService.approveWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user, tenantId);
                    approveStatus = CommonApproveStatus.APPROVED;
                } else {
                    validationService.abortedWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user, tenantId);
                    approveStatus = CommonApproveStatus.REJECTED;
                }

                LoggerRequest.getInstance().logInfo("EligibilityCollateral > Update Workflow");
                lendingWorkflowService.update(lendingWorkflow.get(), workflowStatus, user);
            }
            LoggerRequest.getInstance().logInfo("EligibilityCollateral > Update Eligibility Collateral");

            if (ec.isPresent())
                updateEligibilityCollateral(isPresentEligibilityCollateralPending.get(), ec.get(), approveStatus, user);

            return true;
        }

        throw new RecordNotFoundException();
    }

    @Override
    public Optional<EligibilityCollateralPending> getByPendingId(Long pendingId) {
    	Optional<EligibilityCollateralPending> eligibilityCollateralPendingOptional = eligibilityCollateralPendingRepository.findById(pendingId);
    	if (eligibilityCollateralPendingOptional.isPresent()) {
    		EligibilityCollateralPending eligibilityCollateral = eligibilityCollateralPendingOptional.get();
            setAssetTypeFieldsForPending(eligibilityCollateral.getTenantId(), eligibilityCollateral);
            return Optional.of(eligibilityCollateral);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Page<EligibilityCollateralPending> searchEligibilityCollateral(String searchQ, String status, String approveStatus, Pageable pageable) {
        searchQ = searchQ == null || searchQ.isEmpty() ? null : searchQ;
        status = status == null || status.isEmpty() ? null : status;
        approveStatus = approveStatus == null || approveStatus.isEmpty() ? null : approveStatus;

        Page<EligibilityCollateralPending> eligibilityCollateralPendingPage = eligibilityCollateralPendingRepository.searchEligibilityCollateralPending(searchQ, status, approveStatus, pageable);
        List<EligibilityCollateralPending> eligibilityCollateralPendingList = eligibilityCollateralPendingPage.getContent();
        
        for(EligibilityCollateralPending eligibilityCollateralPending : eligibilityCollateralPendingList) {
        	setAssetTypeFieldsForPending(eligibilityCollateralPending.getTenantId(), eligibilityCollateralPending);
        }
        
        return eligibilityCollateralPendingPage;
    }

}
