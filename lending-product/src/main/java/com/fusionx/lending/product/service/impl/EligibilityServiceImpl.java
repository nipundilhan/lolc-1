package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.AgeEligibility;
import com.fusionx.lending.product.domain.Eligibility;
import com.fusionx.lending.product.domain.EligibilityPending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.enums.*;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.RecordNotFoundException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.AgeEligibilityRepository;
import com.fusionx.lending.product.repository.EligibilityPendingRepository;
import com.fusionx.lending.product.repository.EligibilityRepository;
import com.fusionx.lending.product.repository.LendingWorkflowRepository;
import com.fusionx.lending.product.resources.EligibilityAddResource;
import com.fusionx.lending.product.resources.EligibilityUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.EligibilityHistoryService;
import com.fusionx.lending.product.service.EligibilityService;
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
 * API Service related to Eligibility.
 *
 * @author Menuka Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        10-06-2021    	-               		             Menuka Jayasinghe		Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class EligibilityServiceImpl extends MessagePropertyBase implements EligibilityService {


    private EligibilityRepository eligibilityRepository;
    private ValidationService validationService;
    private EligibilityHistoryService eligibilityHistoryService;
    private EligibilityPendingRepository eligibilityPendingRepository;
    private LendingWorkflowService lendingWorkflowService;
    private LendingWorkflowRepository lendingWorkflowRepository;
    private AgeEligibilityRepository ageEligibilityRepository;

    private static final String DEFAULT_APPROVAL_USER = "kie-server";
    private static final String DOMAIN_PATH = "com.fusionx.LendingWF";
    private static final String DOMAIN = "LendingWF";

    @Autowired
    public void setLendingWorkflowService(LendingWorkflowService lendingWorkflowService) {
        this.lendingWorkflowService = lendingWorkflowService;
    }

    @Autowired
    public void setLendingWorkflowRepository(LendingWorkflowRepository lendingWorkflowRepository) {
        this.lendingWorkflowRepository = lendingWorkflowRepository;
    }

    @Autowired
    public void setAgeEligibilityRepository(AgeEligibilityRepository ageEligibilityRepository) {
        this.ageEligibilityRepository = ageEligibilityRepository;
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
    public void setEligibilityHistoryService(EligibilityHistoryService eligibilityHistoryService) {
        this.eligibilityHistoryService = eligibilityHistoryService;
    }

    @Autowired
    public void setEligibilityPendingRepository(EligibilityPendingRepository eligibilityPendingRepository) {
        this.eligibilityPendingRepository = eligibilityPendingRepository;
    }

    @Override
    public List<Eligibility> getAll() {
        return eligibilityRepository.findAll();
    }

    @Override
    public Optional<Eligibility> getById(Long id) {
        return eligibilityRepository.findById(id);
    }

    @Override
    public Optional<Eligibility> getByCode(String code) {
        return eligibilityRepository.findByCode(code);
    }

    @Override
    public List<Eligibility> getByName(String name) {
        return eligibilityRepository.findByNameContaining(name);
    }

    @Override
    public List<Eligibility> getByStatus(String status) {
        return eligibilityRepository.findByStatus(CommonStatus.valueOf(status));
    }

    @Override
    public Eligibility addEligibility(String tenantId,
                                      EligibilityAddResource eligibilityAddResource) {

        Optional<Eligibility> isPresentEligibility = eligibilityRepository.findByCode(eligibilityAddResource.getCode());

        if (isPresentEligibility.isPresent())
            throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.ELIGIBILTY);

        AgeEligibility ageEligibility = validateAgeEligibility(eligibilityAddResource.getAgeEligiId());

        Eligibility eligibility = new Eligibility();
        eligibility.setTenantId(tenantId);
        eligibility.setAgeEligibility(ageEligibility);
        eligibility.setCode(eligibilityAddResource.getCode());
        eligibility.setName(eligibilityAddResource.getName());
        eligibility.setGuarantorEligibility(Long.parseLong(eligibilityAddResource.getGuarantorEligibility()));
        eligibility.setStatus(CommonStatus.valueOf(eligibilityAddResource.getStatus()));
        eligibility.setCreatedDate(validationService.getCreateOrModifyDate());
        eligibility.setSyncTs(validationService.getCreateOrModifyDate());
        eligibility.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

        return eligibilityRepository.save(eligibility);
    }

    @Override
    public Eligibility updateEligibility(String tenantId, Long id, EligibilityUpdateResource eligibilityUpdateResource) {

        Optional<Eligibility> isPresentEligibility = eligibilityRepository.findById(id);

        if (isPresentEligibility.isPresent()) {
            if (!isPresentEligibility.get().getVersion().equals(Long.parseLong(eligibilityUpdateResource.getVersion())))
                throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.ELIGIBILTY);

            if (!isPresentEligibility.get().getCode().equalsIgnoreCase(eligibilityUpdateResource.getCode()))
                throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE, EntityPoint.ELIGIBILTY);

            AgeEligibility ageEligibility = validateAgeEligibility(eligibilityUpdateResource.getAgeEligiId());

            LoggerRequest.getInstance().logInfo("Eligibility > Workflow Call");
            approveOrGenerateWorkFlow(tenantId, LogginAuthentcation.getInstance().getUserName(), isPresentEligibility.get(), eligibilityUpdateResource, ageEligibility);

            return isPresentEligibility.get();
        } else
            throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
    }

    private AgeEligibility validateAgeEligibility(String id) {

        Optional<AgeEligibility> ageEligibility = ageEligibilityRepository.findByIdAndStatus(validationService.stringToLong(id), CommonStatus.ACTIVE);

        if (!ageEligibility.isPresent())
            throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.AGE_ELIGIBILITY_ID, EntityPoint.ELIGIBILTY);
        else
            return ageEligibility.get();
    }

    private void approveOrGenerateWorkFlow(String tenantId, String user, Eligibility eligibility, EligibilityUpdateResource eligibilityUpdateResource, AgeEligibility ageEligibility) {
        WorkflowRulesResource workflowRulesResource;
        Long processId;
        WorkflowType workflowType = WorkflowType.ELIGI_TEMP_APPROVAL;
        LendingWorkflow lendingWorkflow = null;

        WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
        workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
        workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

        LoggerRequest.getInstance().logInfo("Eligibility > Get Workflow Rules");
        workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);

        if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
            processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
            if (processId != null) {
                lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
                savePendingEligibility(tenantId, eligibility, eligibilityUpdateResource, lendingWorkflow, user, ageEligibility);
            }
        } else {
            EligibilityPending eligibilityPending = savePendingEligibility(tenantId, eligibility, eligibilityUpdateResource, lendingWorkflow, user, ageEligibility);
            updateEligibility(eligibilityPending, eligibility, CommonApproveStatus.APPROVED, user);
        }
    }

    private EligibilityPending savePendingEligibility(String tenantId, Eligibility eligibility, EligibilityUpdateResource eligibilityUpdateResource, LendingWorkflow lendingWorkflow, String user, AgeEligibility ageEligibility) {

        LoggerRequest.getInstance().logInfo("Eligibility > Save History");
        eligibilityHistoryService.saveHistory(tenantId, eligibility, user);

        Eligibility e = eligibility;
        e.setApproveStatus(CommonApproveStatus.PENDING);
        e.setModifiedUser(user);
        e.setModifiedDate(validationService.getCreateOrModifyDate());
        e.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityRepository.saveAndFlush(e);

        EligibilityPending eligibilityPending = new EligibilityPending();

        eligibilityPending.setTenantId(tenantId);
        if (lendingWorkflow != null)
            eligibilityPending.setLendingWorkflow(lendingWorkflow);
        eligibilityPending.setEligibility(eligibility);
        eligibilityPending.setCode(eligibilityUpdateResource.getCode());
        eligibilityPending.setName(eligibilityUpdateResource.getName());
        eligibilityPending.setAgeEligibility(ageEligibility);
        eligibilityPending.setGuarantorEligibility(Long.parseLong(eligibilityUpdateResource.getGuarantorEligibility()));
        eligibilityPending.setStatus(CommonStatus.valueOf(eligibilityUpdateResource.getStatus()));
        eligibilityPending.setPcreatedDate(validationService.getCreateOrModifyDate());
        eligibilityPending.setPcreatedUser(user);
        eligibilityPending.setSyncTs(validationService.getCreateOrModifyDate());

        return eligibilityPendingRepository.save(eligibilityPending);

    }

    private void updateEligibility(EligibilityPending eligibilityPending, Eligibility eligibility, CommonApproveStatus approveStatus, String user) {

        LoggerRequest.getInstance().logInfo("Eligibility > Save History");
        eligibilityHistoryService.saveHistory(eligibility.getTenantId(), eligibility, user);

        if (approveStatus.toString().equalsIgnoreCase(CommonApproveStatus.APPROVED.toString())) {
            eligibility.setAgeEligibility(eligibilityPending.getAgeEligibility());
            eligibility.setGuarantorEligibility(eligibilityPending.getGuarantorEligibility());
            eligibility.setName(eligibilityPending.getName());
            eligibility.setStatus(eligibilityPending.getStatus());
            eligibility.setModifiedDate(eligibilityPending.getPcreatedDate());
            eligibility.setModifiedUser(eligibilityPending.getPcreatedUser());
            eligibility.setApproveStatus(approveStatus);
            eligibility.setPenApprovedUser(user);
            eligibility.setPenApprovedDate(validationService.getCreateOrModifyDate());
        } else {
            eligibility.setPenRejectedUser(user);
            eligibility.setApproveStatus(approveStatus);
            eligibility.setPenRejectedDate(validationService.getCreateOrModifyDate());
        }
        eligibility.setSyncTs(validationService.getCreateOrModifyDate());

        eligibilityRepository.saveAndFlush(eligibility);
    }

    @Override
    public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus) {

        WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
        workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
        workflowInitiationRequestResource.setApprovWorkflowType(WorkflowType.ELIGI_TEMP_APPROVAL);
        WorkflowRulesResource workflowRulesResource;
        WorkflowType workflowType = WorkflowType.ELIGI_TEMP_APPROVAL;
        String user = LogginAuthentcation.getInstance().getUserName();
        CommonApproveStatus approveStatus;

        Optional<EligibilityPending> isPresentEligibilityPending = eligibilityPendingRepository.findById(id);

        if (isPresentEligibilityPending.isPresent()) {
        Optional<Eligibility> eligibility = eligibilityRepository.findById(isPresentEligibilityPending.get().getEligibility().getId());

        
        	if(isPresentEligibilityPending.get().getEligibility().getApproveStatus().equals(CommonApproveStatus.APPROVED)||isPresentEligibilityPending.get().getEligibility().getApproveStatus().equals(CommonApproveStatus.REJECTED) )
        		throw new ValidateRecordException(environment.getProperty("already.process.approve"), "message");


            if (!isPresentEligibilityPending.get().getEligibility().getApproveStatus().equals(CommonApproveStatus.PENDING))
                throw new ValidateRecordException(environment.getProperty("common.can-not-rej-app"), "message");
            
                    
            Optional<LendingWorkflow> lendingWorkflow = lendingWorkflowRepository.findById(isPresentEligibilityPending.get().getLendingWorkflow().getId()); 
            
            if(!lendingWorkflow.isPresent()) {
            	 throw new ValidateRecordException(environment.getProperty("already.process.approve"), "message");
            }
            
            if( (lendingWorkflow.get().getWorkflowStatus().toString()).equals(WorkflowStatus.COMPLETE.toString()) ) {
            	throw new ValidateRecordException(environment.getProperty("already.process.approve"), "message");
            }
            
            LoggerRequest.getInstance().logInfo("Eligibility > Get Workflow Rules");
            workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN, tenantId);

            if (workflowRulesResource.getApprovalAllowed().equalsIgnoreCase(CommonStatus.NO.toString())) {
                if (lendingWorkflow.get().getCreatedUser().equalsIgnoreCase(LogginAuthentcation.getInstance().getUserName()))
                    throw new ValidateRecordException(environment.getProperty("cannot.process.approve"), "message");
            }

       
        
            if (workflowStatus.toString().equalsIgnoreCase(WorkflowStatus.COMPLETE.toString())) {
                LoggerRequest.getInstance().logInfo("Eligibility > Approve Workflow");           
                validationService.approveWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user, tenantId);
                approveStatus = CommonApproveStatus.APPROVED;
                

            } else {
                LoggerRequest.getInstance().logInfo("Eligibility > Abort Workflow");
                validationService.abortedWorkFlow(lendingWorkflow.get().getWorkflowProcessId(), workflowType, user, tenantId);
            
                approveStatus = CommonApproveStatus.REJECTED;
            }

            lendingWorkflowService.update(lendingWorkflow.get(),workflowStatus,user );
            LoggerRequest.getInstance().logInfo("Eligibility > Update");
            updateEligibility(isPresentEligibilityPending.get(), eligibility.get(), approveStatus, user);

            return true;
        } else throw new RecordNotFoundException();
    }

    @Override
    public Optional<EligibilityPending> getByPendingId(Long pendingId) {
        return eligibilityPendingRepository.findById(pendingId);
    }

    @Override
    public Page<EligibilityPending> searchEligibilityPending(String searchQ, String status, String approveStatus, Pageable pageable) {
        searchQ = (searchQ == null || searchQ.isEmpty()) ? null : searchQ;
        status = (status == null || status.isEmpty()) ? null : status;
        approveStatus = (approveStatus == null || approveStatus.isEmpty()) ? null : approveStatus;

        return eligibilityPendingRepository.searchEligibilityPending(searchQ, status, approveStatus, pageable);
    }
}
