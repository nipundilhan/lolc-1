package com.fusionx.lending.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Eligibility;
import com.fusionx.lending.product.domain.EligibilityCurrencyPending;
import com.fusionx.lending.product.domain.EligibilityCustomerTypePending;
import com.fusionx.lending.product.domain.EligibilityOther;
import com.fusionx.lending.product.domain.EligibilityOtherPending;
import com.fusionx.lending.product.domain.EligibilityPending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.domain.OtherEligibilityType;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.EligibilityOtherHistoryRepository;
import com.fusionx.lending.product.repository.EligibilityOtherPendingRepository;
import com.fusionx.lending.product.repository.EligibilityOtherRepository;
import com.fusionx.lending.product.repository.EligibilityPendingRepository;
import com.fusionx.lending.product.repository.EligibilityRepository;
import com.fusionx.lending.product.repository.OtherEligibilityTypeRepository;
import com.fusionx.lending.product.resources.Currency;
import com.fusionx.lending.product.resources.EligibilityOtherAddResource;
import com.fusionx.lending.product.resources.EligibilityOtherUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.EligibilityOtherService;
import com.fusionx.lending.product.service.EligibilityOtherHistoryService;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * Eligibility Other Service Impl
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   27-07-2021    FXL_July_2021_2  	FXL-54		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class EligibilityOtherServiceImpl extends MessagePropertyBase implements EligibilityOtherService {

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
	private OtherEligibilityTypeRepository otherEligibilityTypeRepository;
	
	@Autowired
	private EligibilityPendingRepository eligibilityPendingRepository;
	
	@Autowired
	private EligibilityOtherHistoryService eligibilityOtherHistoryService;
	
	private EligibilityOtherPendingRepository eligibilityOtherPendingRepository;

	@Autowired
	public void setEligibilityOtherPendingRepository(
			EligibilityOtherPendingRepository eligibilityOtherPendingRepository) {
		this.eligibilityOtherPendingRepository = eligibilityOtherPendingRepository;
	}

	public EligibilityOtherPendingRepository getEligibilityOtherPendingRepository() {
		return eligibilityOtherPendingRepository;
	}

	private EligibilityOtherHistoryRepository eligibilityOtherHistoryRepository;

	@Autowired
	public void setEligibilityOtherHistoryRepository(
			EligibilityOtherHistoryRepository eligibilityOtherHistoryRepository) {
		this.eligibilityOtherHistoryRepository = eligibilityOtherHistoryRepository;
	}

	public EligibilityOtherHistoryRepository getEligibilityOtherHistoryRepository() {
		return eligibilityOtherHistoryRepository;
	}

	private EligibilityOtherRepository eligibilityOtherRepository;

	@Autowired
	public void setEligibilityOtherRepository(EligibilityOtherRepository eligibilityOtherRepository) {
		this.eligibilityOtherRepository = eligibilityOtherRepository;
	}

	public EligibilityOtherRepository getEligibilityOtherRepository() {
		return eligibilityOtherRepository;
	}

	@Override
	public List<EligibilityOther> findAll() {
		return eligibilityOtherRepository.findAll();
	}

	@Override
	public Optional<EligibilityOther> getById(Long id) {
		Optional<EligibilityOther> isEligibilityOther = eligibilityOtherRepository.findById(id);

		if (isEligibilityOther.isPresent()) {
			return Optional.ofNullable(isEligibilityOther.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Long addEligibilityOther(String tenantId, Long eligibilityId,
			EligibilityOtherAddResource eligibilityOtherAddResource) {
		
		Optional<Eligibility> isPresentEligibility = eligibilityRepository.findByIdAndStatus(eligibilityId,CommonStatus.ACTIVE);
		
		if(!isPresentEligibility.isPresent()){
			LoggerRequest.getInstance().logInfo("Eligibility********************************Validate Eligibility*********************************************");
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "eligibilityId");	
		}
		
		Optional<OtherEligibilityType> isPresentOtherEligibilityType =  otherEligibilityTypeRepository.findByIdAndNameAndStatus(validationService.stringToLong(eligibilityOtherAddResource.getOtherEligibilityTypeId()),eligibilityOtherAddResource.getOtherEligibilityTypeName() ,CommonStatus.ACTIVE);
		if(!isPresentOtherEligibilityType.isPresent()){
			LoggerRequest.getInstance().logInfo("OtherEligibilityType********************************Validate OtherEligibilityType*********************************************");
			throw new InvalidServiceIdException(environment.getProperty(INVALID_ID), ServiceEntity.OTHER_ELIGIBILITY_TYPE_ID, EntityPoint.ELIGIBILITY_OTHER);
		}		
		
		Optional<EligibilityOther> isPresentEligibilityOtherDup = eligibilityOtherRepository.findByOtherEligibilityTypeIdAndEligibilityIdAndStatus(validationService.stringToLong(eligibilityOtherAddResource.getOtherEligibilityTypeId()), isPresentEligibility.get().getId(), CommonStatus.ACTIVE);
		if(isPresentEligibilityOtherDup.isPresent())
			throw new InvalidServiceIdException(environment.getProperty("common.already-exists"), ServiceEntity.OTHER_ELIGIBILITY_TYPE_ID, EntityPoint.ELIGIBILITY_OTHER);
		
		  
		EligibilityOther eligibilityOther = new EligibilityOther();
        eligibilityOther.setTenantId(tenantId);
        eligibilityOther.setEligibility(isPresentEligibility.get());
        eligibilityOther.setOtherEligibilityType(isPresentOtherEligibilityType.get());
        eligibilityOther.setStatus(CommonStatus.valueOf(eligibilityOtherAddResource.getStatus()));
        eligibilityOther.setCreatedDate(validationService.getCreateOrModifyDate());
        eligibilityOther.setSyncTs(validationService.getCreateOrModifyDate());
        eligibilityOther.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        eligibilityOtherRepository.save(eligibilityOther);
        return eligibilityOther.getId();
	}

	@Override
	public Long updateEligibilityOther(String tenantId, Long eligibilityId,
			EligibilityOtherUpdateResource eligibilityOtherUpdateResource) {
		
		Optional<EligibilityPending> eligibilityPendingOpt= null;
		EligibilityPending eligibilityPending= null;
		Optional<Eligibility> isPresentEligibility = eligibilityRepository.findByIdAndStatus(eligibilityId,CommonStatus.ACTIVE);
		//Optional<Eligibility> isPresentEligibility = eligibilityRepository.findByIdAndStatus(Long.parseLong(eligibilityOtherUpdateResource.getId()),CommonStatus.ACTIVE);
		if(!isPresentEligibility.isPresent()){
			LoggerRequest.getInstance().logInfo("Eligibility********************************Validate Eligibility*********************************************");
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "eligibilityId");	
		}
		
		if(CommonApproveStatus.PENDING.toString().equals(isPresentEligibility.get().getApproveStatus() != null ?isPresentEligibility.get().getApproveStatus().toString():null) && eligibilityOtherUpdateResource.getEligibilityPendingId() == null)
			throw new ValidateRecordException(environment.getProperty("eligibility.can-not-update"), "message");
		
		
		//Eligibility Template Update - EligibilityOther Update existing 
		if(eligibilityOtherUpdateResource.getId() != null && !eligibilityOtherUpdateResource.getId().isEmpty()) {			
			Optional<EligibilityOther> isPresentEligibilityOther = eligibilityOtherRepository.findByEligibilityIdAndId(eligibilityId, validationService.stringToLong(eligibilityOtherUpdateResource.getId()));
			
			if(isPresentEligibilityOther.isPresent()) {
				if (!isPresentEligibilityOther.get().getVersion()
						.equals(Long.parseLong(eligibilityOtherUpdateResource.getVersion()))) {
					throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
				}
				
				Optional<EligibilityOther> isPresentEligibilityOtherDup = eligibilityOtherRepository.findByOtherEligibilityTypeIdAndEligibilityIdAndStatusAndIdNotIn(validationService.stringToLong(eligibilityOtherUpdateResource.getOtherEligibilityTypeId()), isPresentEligibility.get().getId(), CommonStatus.ACTIVE,isPresentEligibilityOther.get().getId());
				if(isPresentEligibilityOtherDup.isPresent())
					throw new InvalidServiceIdException(environment.getProperty("common.already-exists"), ServiceEntity.OTHER_ELIGIBILITY_TYPE_ID, EntityPoint.ELIGIBILITY_OTHER);
				
				if(eligibilityOtherUpdateResource.getEligibilityPendingId() != null && !eligibilityOtherUpdateResource.getEligibilityPendingId().isEmpty()) {
						eligibilityPendingOpt = eligibilityPendingRepository.findById(validationService.stringToLong(eligibilityOtherUpdateResource.getEligibilityPendingId()));
						if(!(eligibilityPendingOpt.isPresent()))
			    			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "eligibilityPendingId");
			    		
						eligibilityPending = createEligibilityOtherPending(tenantId, isPresentEligibility.get() ,eligibilityPendingOpt.get() , isPresentEligibilityOther.get(), eligibilityPendingOpt.get().getLendingWorkflow(), eligibilityOtherUpdateResource);
				}else {
					eligibilityPending =approveOrGenerateWorkFlow(tenantId, isPresentEligibility.get() , isPresentEligibilityOther.get() , eligibilityOtherUpdateResource);
				}
					
				
			}else {
				LoggerRequest.getInstance().logInfo("EligibilityOther********************************Validate EligibilityOther*********************************************");
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "eligibilityOtherId");	
			}
		}else { //Eligibility Template Update -  add new EligibilityOther
			
				Optional<EligibilityOther> isPresentEligibilityOtherDup = eligibilityOtherRepository.findByOtherEligibilityTypeIdAndEligibilityIdAndStatus(validationService.stringToLong(eligibilityOtherUpdateResource.getOtherEligibilityTypeId()), isPresentEligibility.get().getId(), CommonStatus.ACTIVE);
				if(isPresentEligibilityOtherDup.isPresent())
					throw new InvalidServiceIdException(environment.getProperty("common.already-exists"), ServiceEntity.OTHER_ELIGIBILITY_TYPE_ID, EntityPoint.ELIGIBILITY_OTHER);
				
				
		        if(eligibilityOtherUpdateResource.getEligibilityPendingId() != null && !eligibilityOtherUpdateResource.getEligibilityPendingId().isEmpty()) {
		        	eligibilityPendingOpt = eligibilityPendingRepository.findById(validationService.stringToLong(eligibilityOtherUpdateResource.getEligibilityPendingId()));
		        	if(!(eligibilityPendingOpt.isPresent()))
		    			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "eligibilityPendingId");
		    		
		        	
		        	eligibilityPending = createEligibilityOtherPending(tenantId, isPresentEligibility.get() , eligibilityPendingOpt.get() , null ,eligibilityPendingOpt.get().getLendingWorkflow(), eligibilityOtherUpdateResource);
				}else {
					eligibilityPending = approveOrGenerateWorkFlow(tenantId , isPresentEligibility.get(), null, eligibilityOtherUpdateResource);
				}
		}
		
		return eligibilityPending.getId();

	}
	
	private EligibilityPending approveOrGenerateWorkFlow(String tenantId, Eligibility eligibility , EligibilityOther  eligibilityOther , EligibilityOtherUpdateResource eligibilityOtherUpdateResource) {
	WorkflowRulesResource workflowRulesResource = null;
	Long processId = null;
	WorkflowType workflowType = WorkflowType.ELIGI_TEMP_APPROVAL;
	LendingWorkflow lendingWorkflow = null;
	EligibilityPending eligibilityPending= null;
	
	WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
	workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
	workflowInitiationRequestResource.setApprovWorkflowType(workflowType);
	
	workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
			tenantId);
	
	if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
		processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
		if (processId != null) {
			lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, LogginAuthentcation.getInstance().getUserName());
			eligibilityPending = createEligibilityOtherPending(tenantId,eligibility,null,eligibilityOther,lendingWorkflow,eligibilityOtherUpdateResource);
		}
	} else {
			directUpdate(tenantId,eligibility,eligibilityOtherUpdateResource);
	}
	
	return eligibilityPending;
		
	}
	
	private EligibilityPending createEligibilityOtherPending(String tenantId, Eligibility eligibility , EligibilityPending eligibilityPending, EligibilityOther eligibilityOther,LendingWorkflow lendingWorkflow ,  EligibilityOtherUpdateResource eligibilityOtherUpdateResource){
	
		Optional<OtherEligibilityType> isPresentOtherEligibilityType =  otherEligibilityTypeRepository.findByIdAndNameAndStatus(validationService.stringToLong(eligibilityOtherUpdateResource.getOtherEligibilityTypeId()),eligibilityOtherUpdateResource.getOtherEligibilityTypeName() ,CommonStatus.ACTIVE);
		if(!isPresentOtherEligibilityType.isPresent()){
			LoggerRequest.getInstance().logInfo("OtherEligibilityType********************************Validate OtherEligibilityType*********************************************");
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "otherEligibilityTypeId");	
		}
		
		if(eligibilityPending == null) {
			eligibilityPending = createEligibilityPending(tenantId,eligibility,lendingWorkflow);
		}
		
		EligibilityOtherPending eligibilityOtherPending = new EligibilityOtherPending();
		eligibilityOtherPending.setTenantId(tenantId);
		eligibilityOtherPending.setLendingWorkflow(lendingWorkflow);
		eligibilityOtherPending.setEligibilitys(eligibility);
		eligibilityOtherPending.setEligibilityOther(eligibilityOther);
		eligibilityOtherPending.setEligibilityPending(eligibilityPending);
		eligibilityOtherPending.setOtherEligibilityTypes(isPresentOtherEligibilityType.get());
		eligibilityOtherPending.setApproveStatus(CommonApproveStatus.PENDING);
		eligibilityOtherPending.setStatus(CommonStatus.valueOf(eligibilityOtherUpdateResource.getStatus()));
		eligibilityOtherPending.setCreatedDate(validationService.getCreateOrModifyDate());
		eligibilityOtherPending.setSyncTs(validationService.getCreateOrModifyDate());
		eligibilityOtherPending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		eligibilityOtherPendingRepository.saveAndFlush(eligibilityOtherPending);
		
		return eligibilityPending;
	} 
	
	private EligibilityPending createEligibilityPending(String tenantId, Eligibility eligibility,LendingWorkflow lendingWorkflow) {
	
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
	
	private void directUpdate(String tenantId, Eligibility eligibility , EligibilityOtherUpdateResource eligibilityOtherUpdateResource) {	
	
	Optional<EligibilityOther> eligibilityOtherOpt = null;	
	EligibilityOther eligibilityOther;
	
	Optional<OtherEligibilityType> isPresentOtherEligibilityType =  otherEligibilityTypeRepository.findByIdAndNameAndStatus(validationService.stringToLong(eligibilityOtherUpdateResource.getOtherEligibilityTypeId()),eligibilityOtherUpdateResource.getOtherEligibilityTypeName() ,CommonStatus.ACTIVE);
	if(!isPresentOtherEligibilityType.isPresent()){
		LoggerRequest.getInstance().logInfo("OtherEligibilityType********************************Validate OtherEligibilityType*********************************************");
		throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "OtherEligibilityTypeId");	
	}
	
	if(eligibilityOtherUpdateResource.getId() != null) {
		eligibilityOtherOpt = eligibilityOtherRepository.findById(validationService.stringToLong(eligibilityOtherUpdateResource.getId()));
		eligibilityOtherHistoryService.saveHistory(tenantId, eligibilityOtherOpt.get(), LogginAuthentcation.getInstance().getUserName());
		
		eligibilityOther = eligibilityOtherOpt.get();
		eligibilityOther.setModifiedDate(validationService.getCreateOrModifyDate());
		eligibilityOther.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
	    
	}else {
		eligibilityOther = new EligibilityOther();
		eligibilityOther.setCreatedDate(validationService.getCreateOrModifyDate());
		eligibilityOther.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
	}
		eligibilityOther.setTenantId(tenantId);
		eligibilityOther.setEligibility(eligibility);
		eligibilityOther.setOtherEligibilityType(isPresentOtherEligibilityType.get());
		eligibilityOther.setStatus(CommonStatus.valueOf(eligibilityOtherUpdateResource.getStatus()));
		eligibilityOther.setSyncTs(validationService.getCreateOrModifyDate());
		eligibilityOther.setApprovedDate(validationService.getCreateOrModifyDate());
		eligibilityOther.setApprovedUser(LogginAuthentcation.getInstance().getUserName());
		eligibilityOtherRepository.save(eligibilityOther);
	
	}


	@Override
	public Optional<EligibilityOtherPending> getByPendingId(Long pendingId) {
		Optional<EligibilityOtherPending> isPresentEligibilityOtherPending = eligibilityOtherPendingRepository
				.findById(pendingId);
		 
//		if (isPresentEligibilityOtherPending.isPresent()) {
//			return Optional.ofNullable(isPresentEligibilityOtherPending.get());
//		} else {
//			return Optional.empty();
//		}
		
		if (isPresentEligibilityOtherPending.isPresent()) {
//			EligibilityOtherPending eligibilityOtherPending = isPresentEligibilityOtherPending.get();
//			return Optional.ofNullable(eligibilityOtherPending);
			
			return Optional.of(setEligibilityFieldsForPending(isPresentEligibilityOtherPending.get()));
		} else {
			return Optional.empty();
		}
	}	
	
	@Override
	public List<EligibilityOther> getByEligibilityId(Long eligibilityId) {
		List<EligibilityOther> eligibilityOtherList = new ArrayList<>();
		
		Optional<Eligibility> isPresentEligibility = eligibilityRepository
				.findById(eligibilityId);

		if (isPresentEligibility.isPresent())
			eligibilityOtherList = eligibilityOtherRepository.findByEligibility(isPresentEligibility.get());		
		return eligibilityOtherList;
	}

	@Override
	public Page<EligibilityOtherPending> searchEligibilityOtherPending(String searchq, String status,
			String approveStatus, Pageable pageable) {
		if (searchq == null || searchq.isEmpty())
			searchq = null;
		if (status == null || status.isEmpty())
			status = null;
		if (approveStatus == null || approveStatus.isEmpty())
			approveStatus = null;
//		return eligibilityOtherPendingRepository.searchEligibilityOtherPending(searchq, status, approveStatus,
//				pageable);
		
		Page<EligibilityOtherPending> eligibilityOtherList = eligibilityOtherPendingRepository.searchEligibilityOtherPending(searchq, status, approveStatus,
				pageable);
		
		for (EligibilityOtherPending eligibilityOtherTypePending : eligibilityOtherList.getContent()) {
			setEligibilityFieldsForPending(eligibilityOtherTypePending);
		}
		
		return eligibilityOtherList;
	}
	
	private EligibilityOtherPending setEligibilityFieldsForPending(EligibilityOtherPending eligibilityOtherPending) {
		
		Optional<Eligibility> eligibilityOpt = null;	
		Eligibility eligibility;		
		eligibilityOpt = eligibilityRepository.findById(eligibilityOtherPending.getEligibilitys().getId());
		if (eligibilityOpt != null) {
			eligibility = eligibilityOpt.get();		
			eligibilityOtherPending.setEligibilitys(eligibility);
		}
		
		Optional<OtherEligibilityType> eligibilityOptType = null;	
		OtherEligibilityType eligibilityType;		
		eligibilityOptType = otherEligibilityTypeRepository.findById(eligibilityOtherPending.getOtherEligibilityTypes().getId());
		if (eligibilityOptType != null) {
			eligibilityType = eligibilityOptType.get();		
			eligibilityOtherPending.setOtherEligibilityTypes(eligibilityType);
		}
					
		return eligibilityOtherPending;
	 }

}
