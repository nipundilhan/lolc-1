package com.fusionx.lending.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.CommonListItem;
import com.fusionx.lending.product.domain.InterestTemplate;
import com.fusionx.lending.product.domain.InterestTemplatePending;
import com.fusionx.lending.product.domain.InterestTierBandSet;
import com.fusionx.lending.product.domain.InterestTierBandSetPending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonListCode;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.CommonListItemRepository;
import com.fusionx.lending.product.repository.InterestTemplatePendingRepository;
import com.fusionx.lending.product.repository.InterestTemplateRepository;
import com.fusionx.lending.product.repository.InterestTierBandSetPendingRepository;
import com.fusionx.lending.product.repository.InterestTierBandSetRepository;
import com.fusionx.lending.product.resources.InterestTierBandSetAddResource;
import com.fusionx.lending.product.resources.InterestTierBandSetUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.InterestTierBandSetHistoryService;
import com.fusionx.lending.product.service.InterestTierBandSetService;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.ValidationService;
/**
 * InterestTierBandSetServiceImpl 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   19-07-2021    FXL_July_2021_2  	FXL-52		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class InterestTierBandSetServiceImpl extends MessagePropertyBase implements InterestTierBandSetService{
	
	@Autowired
	private InterestTierBandSetRepository interestTierBandSetRepository;
	
	@Autowired
	private InterestTierBandSetPendingRepository interestTierBandSetPendingRepository;
	
	@Autowired
	private InterestTemplateRepository interestTemplateRepository;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ValidationService validationService;
	
	@Autowired
	private CommonListItemRepository commonListItemRepository;

	@Autowired
	private LendingWorkflowService lendingWorkflowService;
	
	@Autowired
	private InterestTierBandSetHistoryService interestTierBandSetHistoryService;
	
	@Autowired
	private InterestTemplatePendingRepository interestTemplatePendingRepository;

	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";

	@Override
	public Optional<InterestTierBandSet> getById(Long id) {
		Optional<InterestTierBandSet> isInterestTierBandSet = interestTierBandSetRepository.findById(id);
		if (isInterestTierBandSet.isPresent()) {
			return Optional.ofNullable(isInterestTierBandSet.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Long addInterestTierBandSet(String tenantId, Long interestTempId,
			InterestTierBandSetAddResource interestTierBandSetAddResource) {
		
		Optional<InterestTemplate> isPresentInterestTemplate = interestTemplateRepository.findByIdAndStatus(interestTempId,CommonStatus.ACTIVE);
		
		if(!isPresentInterestTemplate.isPresent()){
			LoggerRequest.getInstance().logInfo("InterestTemplate********************************Validate InterestTemplate*********************************************");
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");	
		}
		
		Optional<InterestTierBandSet> isPresentInterestTierBandSet = interestTierBandSetRepository.findByCode(interestTierBandSetAddResource.getCode());       
        if (isPresentInterestTierBandSet.isPresent()) 
        	throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.INTEREST_TIER_BAND_SET);
        
        CommonListItem tierBandMethod = validateTierBandMethod(validationService.stringToLong(interestTierBandSetAddResource.getTierBandMethodId()), interestTierBandSetAddResource.getTierBandMethodName());
        
        CommonListItem calculationMethod = validateCalculationMethod(validationService.stringToLong(interestTierBandSetAddResource.getCalculateMethodId()), interestTierBandSetAddResource.getCalculateMethodName());
        
        InterestTierBandSet interestTierBandSet = new InterestTierBandSet();
        interestTierBandSet.setTenantId(tenantId);
        interestTierBandSet.setInterestTemplate(isPresentInterestTemplate.get());
        interestTierBandSet.setTierBandMethod(tierBandMethod);
        interestTierBandSet.setCalculationMethod(calculationMethod);
        interestTierBandSet.setNote(interestTierBandSetAddResource.getNote());
        interestTierBandSet.setCode(interestTierBandSetAddResource.getCode());
        interestTierBandSet.setStatus(CommonStatus.valueOf(interestTierBandSetAddResource.getStatus()));
        interestTierBandSet.setCreatedDate(validationService.getCreateOrModifyDate());
        interestTierBandSet.setSyncTs(validationService.getCreateOrModifyDate());
        interestTierBandSet.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        interestTierBandSetRepository.save(interestTierBandSet);
        return interestTierBandSet.getId();
	}

	@Override
	public Long updateInterestTierBandSet(String tenantId, Long interestTempId,
			InterestTierBandSetUpdateResource interestTierBandSetUpdateResource) {
		Optional<InterestTemplatePending> interestTempPendingOpt= null;
		InterestTemplatePending interestTempPending= null;
		Optional<InterestTemplate> isPresentInterestTemplate = interestTemplateRepository.findByIdAndStatus(interestTempId,CommonStatus.ACTIVE);
		
		if(!isPresentInterestTemplate.isPresent()){
			LoggerRequest.getInstance().logInfo("InterestTemplate********************************Validate InterestTemplate*********************************************");
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");	
		}
			if(CommonApproveStatus.PENDING.toString().equals(isPresentInterestTemplate.get().getApproveStatus()) && interestTierBandSetUpdateResource.getInterestTempPendingId() == null)
				throw new ValidateRecordException(environment.getProperty("interestTemplate.can-not-update"), "message");
				
			//Interest Template Update - InterestTierBandSet Update existing 
			if(interestTierBandSetUpdateResource.getId() != null && !interestTierBandSetUpdateResource.getId().isEmpty()) {			
				Optional<InterestTierBandSet> isPresentInterestTierBandSet = interestTierBandSetRepository.findByInterestTemplateIdAndId(interestTempId, validationService.stringToLong(interestTierBandSetUpdateResource.getId()));
				
				if(isPresentInterestTierBandSet.isPresent()) {
					if (!isPresentInterestTierBandSet.get().getVersion()
							.equals(Long.parseLong(interestTierBandSetUpdateResource.getVersion()))) {
						throw new InvalidServiceIdException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION,
								EntityPoint.INTEREST_TIER_BAND_SET);
					}
			
					if (!isPresentInterestTierBandSet.get().getCode().equalsIgnoreCase(interestTierBandSetUpdateResource.getCode())) {
						throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE,
								EntityPoint.INTEREST_TIER_BAND_SET);
					}
					
						if(interestTierBandSetUpdateResource.getInterestTempPendingId() != null && !interestTierBandSetUpdateResource.getInterestTempPendingId().isEmpty()) {
							interestTempPendingOpt = interestTemplatePendingRepository.findById(validationService.stringToLong(interestTierBandSetUpdateResource.getInterestTempPendingId()));
							interestTempPending = createInterestTierBandSetPending(tenantId, isPresentInterestTemplate.get() ,interestTempPendingOpt.get() , isPresentInterestTierBandSet.get(),interestTempPendingOpt.get().getLendingWorkflow(), interestTierBandSetUpdateResource);
						}else {
							interestTempPending =approveOrGenerateWorkFlow(tenantId, isPresentInterestTemplate.get() , isPresentInterestTierBandSet.get() , interestTierBandSetUpdateResource);
						}
						
					
				}else {
					LoggerRequest.getInstance().logInfo("InterestTierBandSet********************************Validate InterestTierBandSet*********************************************");
					throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");	
				}
			}else { //Interest Template Update -  add new InterestTierBandSet
				
				Optional<InterestTierBandSet> isPresentInterestTierBandSet = interestTierBandSetRepository.findByCode(interestTierBandSetUpdateResource.getCode());       
		        if (isPresentInterestTierBandSet.isPresent()) 
		        	throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.INTEREST_TIER_BAND_SET);
				
			        if(interestTierBandSetUpdateResource.getInterestTempPendingId() != null && !interestTierBandSetUpdateResource.getInterestTempPendingId().isEmpty()) {
						interestTempPendingOpt = interestTemplatePendingRepository.findById(validationService.stringToLong(interestTierBandSetUpdateResource.getInterestTempPendingId()));
						interestTempPending = createInterestTierBandSetPending(tenantId, isPresentInterestTemplate.get() , interestTempPendingOpt.get() , null ,interestTempPendingOpt.get().getLendingWorkflow(), interestTierBandSetUpdateResource);
					}else {
						interestTempPending = approveOrGenerateWorkFlow(tenantId , isPresentInterestTemplate.get(), null, interestTierBandSetUpdateResource);
					}
			}
			
			return interestTempPending.getId();
		
	}
	
	private InterestTemplatePending approveOrGenerateWorkFlow(String tenantId, InterestTemplate interestTemp , InterestTierBandSet interestTierBandSet , InterestTierBandSetUpdateResource interestTierBandSetUpdateResource) {
		WorkflowRulesResource workflowRulesResource = null;
		Long processId = null;
		WorkflowType workflowType = WorkflowType.INTEREST_TEMP_APPROVAL;
		LendingWorkflow lendingWorkflow = null;
		InterestTemplatePending interestTempPending= null;

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

		workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
				tenantId);

		if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
			processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
			if (processId != null) {
				lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, LogginAuthentcation.getInstance().getUserName());
				interestTempPending = createInterestTierBandSetPending(tenantId,interestTemp,null,interestTierBandSet,lendingWorkflow,interestTierBandSetUpdateResource);
			}
		} else {
				directUpdate(tenantId,interestTemp,interestTierBandSetUpdateResource);
		}
		
		return interestTempPending;
			
	}
	
	private InterestTemplatePending createInterestTierBandSetPending(String tenantId, InterestTemplate interestTemp , InterestTemplatePending interestTemplatePending, InterestTierBandSet interestTierBandSet,LendingWorkflow lendingWorkflow , InterestTierBandSetUpdateResource interestTierBandSetUpdateResource){
		
		CommonListItem tierBandMethod = validateTierBandMethod(validationService.stringToLong(interestTierBandSetUpdateResource.getTierBandMethodId()), interestTierBandSetUpdateResource.getTierBandMethodName());
        
        CommonListItem calculationMethod = validateCalculationMethod(validationService.stringToLong(interestTierBandSetUpdateResource.getCalculateMethodId()), interestTierBandSetUpdateResource.getCalculateMethodName());
        
        if(interestTemplatePending == null) {
        	interestTemplatePending = createInterestTemplatePending(tenantId,interestTemp,lendingWorkflow);
        }
        
		InterestTierBandSetPending interestTierBandSetPending = new InterestTierBandSetPending();
		interestTierBandSetPending.setTenantId(tenantId);
		interestTierBandSetPending.setInterestTemplate(interestTemp);
		interestTierBandSetPending.setInterestTemplatePending(interestTemplatePending);
		interestTierBandSetPending.setInterestTierBandSet(interestTierBandSet);
		interestTierBandSetPending.setTierBandMethod(tierBandMethod);
		interestTierBandSetPending.setCalculationMethod(calculationMethod);
		interestTierBandSetPending.setNote(interestTierBandSetUpdateResource.getNote());
		interestTierBandSetPending.setLendingWorkflow(lendingWorkflow);
		interestTierBandSetPending.setUpdated(true);
		interestTierBandSetPending.setApproveStatus(CommonApproveStatus.PENDING);
		interestTierBandSetPending.setStatus(CommonStatus.valueOf(interestTierBandSetUpdateResource.getStatus()));
		interestTierBandSetPending.setCreatedDate(validationService.getCreateOrModifyDate());
		interestTierBandSetPending.setSyncTs(validationService.getCreateOrModifyDate());
		interestTierBandSetPending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		interestTierBandSetPending = interestTierBandSetPendingRepository.saveAndFlush(interestTierBandSetPending);
        
        return interestTemplatePending;
	} 
	
	private InterestTemplatePending createInterestTemplatePending(String tenantId, InterestTemplate interestTemplate,LendingWorkflow lendingWorkflow) {
		
		InterestTemplate intTemplate = interestTemplate;
		intTemplate.setApproveStatus(CommonApproveStatus.PENDING.toString());
		intTemplate.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		intTemplate.setModifiedDate(validationService.getCreateOrModifyDate());
		intTemplate.setSyncTs(validationService.getCreateOrModifyDate());
		interestTemplateRepository.saveAndFlush(intTemplate);
	
		InterestTemplatePending interestTemplatePending = new InterestTemplatePending();
		interestTemplatePending.setTenantId(tenantId);
		interestTemplatePending.setLendingWorkflow(lendingWorkflow);
		interestTemplatePending.setInterestTemplate(interestTemplate);
		interestTemplatePending.setCode(interestTemplate.getCode());
		interestTemplatePending.setName(interestTemplate.getName());
		interestTemplatePending.setStatus(interestTemplate.getStatus());
		interestTemplatePending.setUpdated(false);
		interestTemplatePending.setApproveStatus(CommonApproveStatus.PENDING.toString());
		interestTemplatePending.setPenCreatedDate(validationService.getCreateOrModifyDate());
		interestTemplatePending.setPenCreatedUser(LogginAuthentcation.getInstance().getUserName());
		interestTemplatePending.setSyncTs(validationService.getCreateOrModifyDate());
		interestTemplatePending = interestTemplatePendingRepository.saveAndFlush(interestTemplatePending);
		
		return interestTemplatePending;
	
	}
	
	private void directUpdate(String tenantId, InterestTemplate interestTemp , InterestTierBandSetUpdateResource interestTierBandSetUpdateResource) {	
		
		Optional<InterestTierBandSet> intTierBandSet = null;	
		InterestTierBandSet interestTierBandSet;
		
		CommonListItem tierBandMethod = validateTierBandMethod(validationService.stringToLong(interestTierBandSetUpdateResource.getTierBandMethodId()), interestTierBandSetUpdateResource.getTierBandMethodName());
        
        CommonListItem calculationMethod = validateCalculationMethod(validationService.stringToLong(interestTierBandSetUpdateResource.getCalculateMethodId()), interestTierBandSetUpdateResource.getCalculateMethodName());
        
		if(interestTierBandSetUpdateResource.getId() != null) {
			intTierBandSet = interestTierBandSetRepository.findById(validationService.stringToLong(interestTierBandSetUpdateResource.getId()));
			interestTierBandSetHistoryService.saveHistory(intTierBandSet.get().getTenantId(), intTierBandSet.get(), LogginAuthentcation.getInstance().getUserName());
			
			interestTierBandSet = intTierBandSet.get();
	        interestTierBandSet.setModifiedDate(validationService.getCreateOrModifyDate());
	        interestTierBandSet.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
	        
		}else {
	        interestTierBandSet = new InterestTierBandSet();
	        interestTierBandSet.setCreatedDate(validationService.getCreateOrModifyDate());
	        interestTierBandSet.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		}
			interestTierBandSet.setTenantId(tenantId);
	        interestTierBandSet.setInterestTemplate(interestTemp);
	        interestTierBandSet.setTierBandMethod(tierBandMethod);
	        interestTierBandSet.setCalculationMethod(calculationMethod);
	        interestTierBandSet.setNote(interestTierBandSetUpdateResource.getNote());
	        interestTierBandSet.setCode(interestTierBandSetUpdateResource.getCode());
	        interestTierBandSet.setStatus(CommonStatus.valueOf(interestTierBandSetUpdateResource.getStatus()));
	        interestTierBandSet.setSyncTs(validationService.getCreateOrModifyDate());
			interestTierBandSet.setApprovedDate(validationService.getCreateOrModifyDate());
	        interestTierBandSet.setApprovedUser(LogginAuthentcation.getInstance().getUserName());
	        interestTierBandSetRepository.save(interestTierBandSet);
		
	}


	@Override
	public Optional<InterestTierBandSetPending> getByPendingId(Long pendingId) {
		Optional<InterestTierBandSetPending> isPresentInterestTierBandSetPending = interestTierBandSetPendingRepository.findById(pendingId);
		
		if (isPresentInterestTierBandSetPending.isPresent()) {
			return Optional.ofNullable(isPresentInterestTierBandSetPending.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<InterestTierBandSet> getByInterestTemplateId(Long interestTempId) {
		List<InterestTierBandSet> interestTierBandSetList = new ArrayList<>();
		Optional<InterestTemplate> isPresentInterestTemplate = interestTemplateRepository.findById(interestTempId);
		
		if(isPresentInterestTemplate.isPresent()) 
			interestTierBandSetList = interestTierBandSetRepository.findByInterestTemplate(isPresentInterestTemplate.get());			
		return interestTierBandSetList;
	}
	
	@Override
	public Optional<InterestTierBandSet> getByCodeAndInterestTemplateId(String code,Long interestTempId) {
		Optional<InterestTierBandSet> isPresentInterestTemplate = interestTierBandSetRepository.findByCodeAndInterestTemplateId(code,interestTempId);					
		return isPresentInterestTemplate;
	}

	@Override
	public List<InterestTierBandSetPending> getPendingByInterestTemplateId(Long interestTempId) {		
		List<InterestTierBandSetPending> interestTierBandSetPendingList = new ArrayList<>();
		Optional<InterestTemplate> isPresentInterestTemplate = interestTemplateRepository.findById(interestTempId);
		
		if(isPresentInterestTemplate.isPresent()) 
			interestTierBandSetPendingList = interestTierBandSetPendingRepository.findByInterestTemplate(isPresentInterestTemplate.get());			
		return interestTierBandSetPendingList;
	}
	
	private CommonListItem validateTierBandMethod(Long id, String name) {
		
		Optional<CommonListItem> commonListItem = commonListItemRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE);
		
		if(!commonListItem.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.TIER_BAND_METHOD_ID, EntityPoint.INTEREST_TIER_BAND_SET);
		if(!commonListItem.get().getReferenceCode().toString().equalsIgnoreCase(CommonListCode.TIRE_BAND_METHOD.toString()))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.TIER_BAND_METHOD_ID, EntityPoint.INTEREST_TIER_BAND_SET);
		return commonListItem.get();
	}
	
	private CommonListItem validateCalculationMethod(Long id, String name) {
		
		Optional<CommonListItem> commonListItem = commonListItemRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE);
		
		if(!commonListItem.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.CALCULATION_METHOD_ID, EntityPoint.INTEREST_TIER_BAND_SET);
		if(!commonListItem.get().getReferenceCode().toString().equalsIgnoreCase(CommonListCode.INT_CALC_METHOD.toString()))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.CALCULATION_METHOD_ID, EntityPoint.INTEREST_TIER_BAND_SET);
		return commonListItem.get();
	}
	
	@Override
	public Page<InterestTierBandSetPending> searchInterestTierBandSetPending(String searchq, String status, String approveStatus,
			Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(status==null || status.isEmpty())
			status=null;
		if(approveStatus==null || approveStatus.isEmpty())
			approveStatus=null;
		return interestTierBandSetPendingRepository.searchInterestTierBandSetPending(searchq, status, approveStatus, pageable);
	}

}
