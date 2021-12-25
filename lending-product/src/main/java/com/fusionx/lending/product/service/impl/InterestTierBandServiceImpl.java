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
import com.fusionx.lending.product.domain.InterestRateType;
import com.fusionx.lending.product.domain.InterestTemplate;
import com.fusionx.lending.product.domain.InterestTemplatePending;
import com.fusionx.lending.product.domain.InterestTierBand;
import com.fusionx.lending.product.domain.InterestTierBandPending;
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
import com.fusionx.lending.product.repository.InterestRateTypeRepository;
import com.fusionx.lending.product.repository.InterestTemplatePendingRepository;
import com.fusionx.lending.product.repository.InterestTemplateRepository;
import com.fusionx.lending.product.repository.InterestTierBandHistoryRepository;
import com.fusionx.lending.product.repository.InterestTierBandPendingRepository;
import com.fusionx.lending.product.repository.InterestTierBandRepository;
import com.fusionx.lending.product.repository.InterestTierBandSetPendingRepository;
import com.fusionx.lending.product.repository.InterestTierBandSetRepository;
import com.fusionx.lending.product.resources.InterestTierBandAddResource;
import com.fusionx.lending.product.resources.InterestTierBandUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.InterestTierBandHistoryService;
import com.fusionx.lending.product.service.InterestTierBandService;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * InterestTierBandServiceImpl
 * 
 *******************************************************************************************************
 * ### 	Date 			Story Point 		Task No 	Author 		Description
 * ------------------------------------------------------------------------------------------------------
 * 1 	22-07-2021 		FXL_July_2021_2 	FXL-53 		Piyumi 		Created
 * 
 *******************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class InterestTierBandServiceImpl extends MessagePropertyBase implements InterestTierBandService {

	@Autowired
	private Environment environment;

	@Autowired
	private ValidationService validationService;

	@Autowired
	private CommonListItemRepository commonListItemRepository;

	@Autowired
	private LendingWorkflowService lendingWorkflowService;


	@Autowired
	private InterestTemplatePendingRepository interestTemplatePendingRepository;
	
	@Autowired
	private InterestRateTypeRepository interestRateTypeRepository;

	@Autowired
	private InterestTemplateRepository interestTemplateRepository;

	@Autowired
	private InterestTierBandSetPendingRepository interestTierBandSetPendingRepository;
	
	@Autowired
	private InterestTierBandHistoryService interestTierBandHistoryService;;

	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";

	@Autowired
	private InterestTierBandRepository interestTierBandRepository;

	@Autowired
	private InterestTierBandPendingRepository interestTierBandPendingRepository;

	@Autowired
	private InterestTierBandHistoryRepository interestTierBandHistoryRepository;

	@Autowired
	private InterestTierBandSetRepository interestTierBandSetRepository;

	@Override
	public Optional<InterestTierBand> getById(Long id) {
		Optional<InterestTierBand> isInterestTierBand = interestTierBandRepository.findById(id);

		if (isInterestTierBand.isPresent()) {
			return Optional.ofNullable(isInterestTierBand.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Long addInterestTierBand(String tenantId, Long interestTierBandSetId,
				InterestTierBandAddResource interestTierBandAddResource) {
		CommonListItem tierValueMinTerm = null;
		CommonListItem tierValueMaxTerm = null;
		CommonListItem interestRateType = null;
		Optional<InterestRateType> loanPrInterestRateType = null;
		
			Optional<InterestTierBandSet> isPresentInterestTierBandSet = interestTierBandSetRepository.findByIdAndStatus(interestTierBandSetId,CommonStatus.ACTIVE);
			
			if(!isPresentInterestTierBandSet.isPresent()){
				LoggerRequest.getInstance().logInfo("InterestTierBandSet********************************Validate InterestTierBandSet*********************************************");
				throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");	
			}
			
			Optional<InterestTierBand> isPresentInterestTierBand = interestTierBandRepository.findByCode(interestTierBandAddResource.getCode());       
	        if (isPresentInterestTierBand.isPresent()) 
	        	throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.INTEREST_TIER_BAND);
	        
	        
			if(interestTierBandAddResource.getTierValueMinTermId() != null)
	         tierValueMinTerm = validateTierValueMinTerm(validationService.stringToLong(interestTierBandAddResource.getTierValueMinTermId()), interestTierBandAddResource.getTierValueMinTermName());  
			
			if(interestTierBandAddResource.getTierValueMaxTermId() != null)
	         tierValueMaxTerm = validateTierValueMaxTerm(validationService.stringToLong(interestTierBandAddResource.getTierValueMaxTermId()), interestTierBandAddResource.getTierValueMaxTermName());
	       
			if(interestTierBandAddResource.getInterestRateTypeId() != null)
	         interestRateType = validateInterestRateType(validationService.stringToLong(interestTierBandAddResource.getInterestRateTypeId()), interestTierBandAddResource.getInterestRateTypeName());
	        
			if(interestTierBandAddResource.getMinTermPeriodId() != null)
				validationService.validatePeriod(tenantId,
	        		interestTierBandAddResource.getMinTermPeriodId(), interestTierBandAddResource.getMaxTermPeriodName(), EntityPoint.INTEREST_TIER_BAND);
	        
			if(interestTierBandAddResource.getMaxTermPeriodId() != null)
				validationService.validatePeriod(tenantId,
	        		interestTierBandAddResource.getMaxTermPeriodId(), interestTierBandAddResource.getMaxTermPeriodName(), EntityPoint.INTEREST_TIER_BAND);
			
			if(interestTierBandAddResource.getLoanPrInterestRateTypeId() != null)
				loanPrInterestRateType = interestRateTypeRepository.findByIdAndNameAndStatus(validationService.stringToLong(interestTierBandAddResource.getLoanPrInterestRateTypeId()), 
						interestTierBandAddResource.getLoanPrInterestRateTypeName(), CommonStatus.ACTIVE);
	        
	        InterestTierBand interestTierBand = new InterestTierBand();
	        interestTierBand.setTenantId(tenantId);
	        interestTierBand.setInterestTierBandSet(isPresentInterestTierBandSet.get());
	        interestTierBand.setTierValueMinimum(validationService.stringToBigDecimal(interestTierBandAddResource.getTierValueMinimum()));
	        interestTierBand.setTierValueMaximum(validationService.stringToBigDecimal(interestTierBandAddResource.getTierValueMaximum()));
	        interestTierBand.setTierValueMinTermId(tierValueMinTerm);
	        interestTierBand.setMinTermPeriodId(interestTierBandAddResource.getMinTermPeriodId() != null ? validationService.stringToLong(interestTierBandAddResource.getMinTermPeriodId()):null);
	        interestTierBand.setTierValueMaxTermId(tierValueMaxTerm);
	        interestTierBand.setMaxTermPeriodId(interestTierBandAddResource.getMaxTermPeriodId() != null ? validationService.stringToLong(interestTierBandAddResource.getMaxTermPeriodId()):null);
	        interestTierBand.setInterestRateTypeId(interestRateType);
	        interestTierBand.setLoanPrInterestRateType(loanPrInterestRateType != null ? loanPrInterestRateType.get():null);
	        interestTierBand.setLoanPrInterestRate(interestTierBandAddResource.getLoanPrInterestRate() != null ? validationService.stringToBigDecimal(interestTierBandAddResource.getLoanPrInterestRate()):null);
	        interestTierBand.setNote(interestTierBandAddResource.getNote());
	        interestTierBand.setCode(interestTierBandAddResource.getCode());
	        interestTierBand.setStatus(CommonStatus.valueOf(interestTierBandAddResource.getStatus()));
	        interestTierBand.setCreatedDate(validationService.getCreateOrModifyDate());
	        interestTierBand.setSyncTs(validationService.getCreateOrModifyDate());
	        interestTierBand.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
	        interestTierBandRepository.save(interestTierBand);
	        return interestTierBand.getId();
		}

	private CommonListItem validateTierValueMinTerm(Long id, String name) {

		Optional<CommonListItem> commonListItem = commonListItemRepository.findByIdAndNameAndStatus(id, name,
				CommonStatus.ACTIVE);

		if (!commonListItem.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
					ServiceEntity.TIRE_MIN_TERM_ID, EntityPoint.INTEREST_TIER_BAND);
		if (!commonListItem.get().getReferenceCode().toString()
				.equalsIgnoreCase(CommonListCode.TIRE_MIN_TERM.toString()))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
					ServiceEntity.TIRE_MIN_TERM_ID, EntityPoint.INTEREST_TIER_BAND);
		return commonListItem.get();
	}

	private CommonListItem validateTierValueMaxTerm(Long id, String name) {

		Optional<CommonListItem> commonListItem = commonListItemRepository.findByIdAndNameAndStatus(id, name,
				CommonStatus.ACTIVE);

		if (!commonListItem.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
					ServiceEntity.TIRE_MAX_TERM_ID, EntityPoint.INTEREST_TIER_BAND);
		if (!commonListItem.get().getReferenceCode().toString()
				.equalsIgnoreCase(CommonListCode.TIRE_MAX_TERM.toString()))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
					ServiceEntity.TIRE_MAX_TERM_ID, EntityPoint.INTEREST_TIER_BAND);
		return commonListItem.get();
	}

	private CommonListItem validateInterestRateType(Long id, String name) {

		Optional<CommonListItem> commonListItem = commonListItemRepository.findByIdAndNameAndStatus(id, name,
				CommonStatus.ACTIVE);

		if (!commonListItem.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
					ServiceEntity.INT_RATE_ID, EntityPoint.INTEREST_TIER_BAND);
		if (!commonListItem.get().getReferenceCode().toString().equalsIgnoreCase(CommonListCode.INT_RATE.toString()))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
					ServiceEntity.INT_RATE_ID, EntityPoint.INTEREST_TIER_BAND);
		return commonListItem.get();
	}

	@Override
	public Long updateInterestTierBand(String tenantId, Long interestTempId, Long interestTierBandSetId,
			InterestTierBandUpdateResource interestTierBandUpdateResource) {
	
		Optional<InterestTemplatePending> interestTempPendingOpt = null;
		Optional<InterestTierBandSetPending> interestTierBandSetPendingOpt = null;
		InterestTierBandSetPending interestTierBandSetPending = null;
		
		Optional<InterestTemplate> isPresentInterestTemplate = interestTemplateRepository.findByIdAndStatus(interestTempId,CommonStatus.ACTIVE);
		
		if(!isPresentInterestTemplate.isPresent()){
			LoggerRequest.getInstance().logInfo("InterestTemplate********************************Validate InterestTemplate*********************************************");
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");	
		}
			if(CommonApproveStatus.PENDING.toString().equals(isPresentInterestTemplate.get().getApproveStatus()) && interestTierBandUpdateResource.getInterestTempPendingId() == null && interestTierBandUpdateResource.getTierBandSetPendingId() == null)
				throw new ValidateRecordException(environment.getProperty("interestTemplate.can-not-update"), "message");			
		
		Optional<InterestTierBandSet> isPresentInterestTierBandSet = interestTierBandSetRepository.findByInterestTemplateIdAndIdAndStatus(interestTempId,interestTierBandSetId,CommonStatus.ACTIVE);
		
		if(!isPresentInterestTierBandSet.isPresent()){
			LoggerRequest.getInstance().logInfo("InterestTierBandSet********************************Validate InterestTierBandSet*********************************************");
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");	
		}
		
		if("INWH - Whole".equalsIgnoreCase(isPresentInterestTierBandSet.get().getTierBandMethod().getCode())) {
			List<InterestTierBand> isPresentTierBandMethod = interestTierBandRepository.findByInterestTierBandSetAndStatus(isPresentInterestTierBandSet.get(),CommonStatus.ACTIVE);
			
			if(isPresentTierBandMethod.size() > 0)
				throw new ValidateRecordException(environment.getProperty("tierBand.can-not-update"), "message");
		}
		
		//Interest Template Update - InterestTierBand Update existing 
		if(interestTierBandUpdateResource.getId() != null) {			
			Optional<InterestTierBand> isPresentInterestTierBand = interestTierBandRepository.findByInterestTierBandSetIdAndId(interestTierBandSetId, validationService.stringToLong(interestTierBandUpdateResource.getId()));
			
			if(!isPresentInterestTierBand.isPresent()) {
				LoggerRequest.getInstance().logInfo("InterestTierBandSet********************************Validate InterestTierBandSet*********************************************");
				throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");	
			}
			
			 List<InterestTierBand> interestTierBandList = interestTierBandRepository.findByInterestTierBandSet(isPresentInterestTierBandSet.get());
		        for(InterestTierBand interestTierBand : interestTierBandList) {
		        	if(validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMinimum()).compareTo(interestTierBand.getTierValueMinimum()) >= 0 
		        			&& validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMinimum()).compareTo(interestTierBand.getTierValueMaximum()) <=0 && interestTierBand.getId() != isPresentInterestTierBand.get().getId()) {
		        		throw new ValidateRecordException(environment.getProperty("tierBand.minimum-can-not-update"), "message");
		        	}
		        	if(validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMaximum()).compareTo(interestTierBand.getTierValueMinimum()) >= 0 
		        			&& validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMaximum()).compareTo(interestTierBand.getTierValueMaximum()) <=0 && interestTierBand.getId() != isPresentInterestTierBand.get().getId()) {
		        		throw new ValidateRecordException(environment.getProperty("tierBand.maximum-can-not-update"), "message");
		        	}
		        }
		        
		     List<InterestTierBandPending> interestTierBandPendingList = interestTierBandPendingRepository.findByInterestTierBandSetAndApproveStatus(isPresentInterestTierBandSet.get(), CommonApproveStatus.PENDING);
		        for(InterestTierBandPending interestTierBand : interestTierBandPendingList) {
		        	if(validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMinimum()).compareTo(interestTierBand.getTierValueMinimum()) >= 0 
		        			&& validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMinimum()).compareTo(interestTierBand.getTierValueMaximum()) <=0) {
		        		throw new ValidateRecordException(environment.getProperty("tierBand.minimum-pending-can-not-update"), "message");
		        	}
		        	if(validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMaximum()).compareTo(interestTierBand.getTierValueMinimum()) >= 0 
		        			&& validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMaximum()).compareTo(interestTierBand.getTierValueMaximum()) <=0) {
		        		throw new ValidateRecordException(environment.getProperty("tierBand.maximum-pending-can-not-update"), "message");
		        	}
		        }
		        
				if (!isPresentInterestTierBand.get().getVersion()
						.equals(Long.parseLong(interestTierBandUpdateResource.getVersion()))) {
					throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION,
							EntityPoint.INTEREST_TIER_BAND);
				}
		
				if (!isPresentInterestTierBand.get().getCode().equalsIgnoreCase(interestTierBandUpdateResource.getCode())) {
					throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE,
							EntityPoint.INTEREST_TIER_BAND);
				}
				
					if(interestTierBandUpdateResource.getTierBandSetPendingId() != null) {
						interestTierBandSetPendingOpt = interestTierBandSetPendingRepository.findById(validationService.stringToLong(interestTierBandUpdateResource.getTierBandSetPendingId()));	
						
						if(!interestTierBandSetPendingOpt.isPresent()) {
							LoggerRequest.getInstance().logInfo("InterestTierBandSetPending********************************Validate InterestTierBandSetPending*********************************************");
							throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");	
						}							
						 interestTierBandSetPending = createInterestTierBandPending(tenantId, interestTierBandSetPendingOpt.get() , isPresentInterestTierBandSet.get(), isPresentInterestTierBand.get(), interestTierBandUpdateResource);
					}else {
						
						if(interestTierBandUpdateResource.getInterestTempPendingId() != null) {
							interestTempPendingOpt = interestTemplatePendingRepository.findById(validationService.stringToLong(interestTierBandUpdateResource.getInterestTempPendingId()));					
							
							if(!interestTempPendingOpt.isPresent()) {
								LoggerRequest.getInstance().logInfo("InterestTemplatePending********************************Validate InterestTemplatePending*********************************************");
								throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");							
							}
							interestTierBandSetPending = createInterestTierBandSetPending(tenantId, isPresentInterestTierBandSet.get(), interestTempPendingOpt.get());
							createInterestTierBandPending(tenantId, interestTierBandSetPending , isPresentInterestTierBandSet.get(), isPresentInterestTierBand.get(), interestTierBandUpdateResource);
							
						}else {
							
							interestTierBandSetPending = approveOrGenerateWorkFlow(tenantId, isPresentInterestTemplate.get() , isPresentInterestTierBandSet.get() ,isPresentInterestTierBand.get(), interestTierBandUpdateResource);
						}
					}
								
		}else { //Interest Template Update -  add new InterestTierBand
			
			Optional<InterestTierBand> isPresentInterestTierBand = interestTierBandRepository.findByCode(interestTierBandUpdateResource.getCode());       
	        if (isPresentInterestTierBand.isPresent()) 
	        	throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.INTEREST_TIER_BAND);
	        
	        List<InterestTierBand> interestTierBandList = interestTierBandRepository.findByInterestTierBandSet(isPresentInterestTierBandSet.get());
		        for(InterestTierBand interestTierBand : interestTierBandList) {
		        	if(validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMinimum()).compareTo(interestTierBand.getTierValueMinimum()) >= 0 
		        			&& validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMinimum()).compareTo(interestTierBand.getTierValueMaximum()) <=0) {
		        		throw new ValidateRecordException(environment.getProperty("tierBand.minimum-can-not-update"), "message");
		        	}
		        	if(validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMaximum()).compareTo(interestTierBand.getTierValueMinimum()) >= 0 
		        			&& validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMaximum()).compareTo(interestTierBand.getTierValueMaximum()) <=0) {
		        		throw new ValidateRecordException(environment.getProperty("tierBand.maximum-can-not-update"), "message");
		        	}
		        }
		        		     
		        List<InterestTierBandPending> interestTierBandPendingList = interestTierBandPendingRepository.findByInterestTierBandSetAndApproveStatus(isPresentInterestTierBandSet.get(), CommonApproveStatus.PENDING);
		        for(InterestTierBandPending interestTierBand : interestTierBandPendingList) {
		        	if(validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMinimum()).compareTo(interestTierBand.getTierValueMinimum()) >= 0 
		        			&& validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMinimum()).compareTo(interestTierBand.getTierValueMaximum()) <=0) {
		        		throw new ValidateRecordException(environment.getProperty("tierBand.minimum-pending-can-not-update"), "message");
		        	}
		        	if(validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMaximum()).compareTo(interestTierBand.getTierValueMinimum()) >= 0 
		        			&& validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMaximum()).compareTo(interestTierBand.getTierValueMaximum()) <=0) {
		        		throw new ValidateRecordException(environment.getProperty("tierBand.maximum-pending-can-not-update"), "message");
		        	}
		        }
			
	    	if(interestTierBandUpdateResource.getTierBandSetPendingId() != null) {
				interestTierBandSetPendingOpt = interestTierBandSetPendingRepository.findById(validationService.stringToLong(interestTierBandUpdateResource.getTierBandSetPendingId()));	
				
				if(!interestTierBandSetPendingOpt.isPresent()) {
					LoggerRequest.getInstance().logInfo("InterestTierBandSetPending********************************Validate InterestTierBandSetPending*********************************************");
					throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");	
				}							
				 interestTierBandSetPending = createInterestTierBandPending(tenantId, interestTierBandSetPendingOpt.get() , isPresentInterestTierBandSet.get(), null, interestTierBandUpdateResource);
			}else {
				
				if(interestTierBandUpdateResource.getInterestTempPendingId() != null) {
					interestTempPendingOpt = interestTemplatePendingRepository.findById(validationService.stringToLong(interestTierBandUpdateResource.getInterestTempPendingId()));					
					
					if(!interestTempPendingOpt.isPresent()) {
						LoggerRequest.getInstance().logInfo("InterestTemplatePending********************************Validate InterestTemplatePending*********************************************");
						throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");							
					}
					interestTierBandSetPending = createInterestTierBandSetPending(tenantId, isPresentInterestTierBandSet.get(), interestTempPendingOpt.get());
					createInterestTierBandPending(tenantId, interestTierBandSetPending , isPresentInterestTierBandSet.get(), null, interestTierBandUpdateResource);
					
				}else {
					
					interestTierBandSetPending = approveOrGenerateWorkFlow(tenantId, isPresentInterestTemplate.get() , isPresentInterestTierBandSet.get() ,null, interestTierBandUpdateResource);
				}
			}
		}
		
		return interestTierBandSetPending.getId();
	}
	
	private InterestTierBandSetPending createInterestTierBandPending(String tenantId, InterestTierBandSetPending interestTierBandSetPending, InterestTierBandSet interestTierBandSet, InterestTierBand interestTierBand  , InterestTierBandUpdateResource interestTierBandUpdateResource){
		CommonListItem tierValueMinTerm = null;
		CommonListItem tierValueMaxTerm = null;
		CommonListItem interestRateType = null;
		Optional<InterestRateType> loanPrInterestRateType = null;
		if(interestTierBandUpdateResource.getTierValueMinTermId() != null)
	         tierValueMinTerm = validateTierValueMinTerm(validationService.stringToLong(interestTierBandUpdateResource.getTierValueMinTermId()), interestTierBandUpdateResource.getTierValueMinTermName());  
			
			if(interestTierBandUpdateResource.getTierValueMaxTermId() != null)
	         tierValueMaxTerm = validateTierValueMaxTerm(validationService.stringToLong(interestTierBandUpdateResource.getTierValueMaxTermId()), interestTierBandUpdateResource.getTierValueMaxTermName());
	       
			if(interestTierBandUpdateResource.getInterestRateTypeId() != null)
	         interestRateType = validateInterestRateType(validationService.stringToLong(interestTierBandUpdateResource.getInterestRateTypeId()), interestTierBandUpdateResource.getInterestRateTypeName());
	        
			if(interestTierBandUpdateResource.getMinTermPeriodId() != null)
	         validationService.validatePeriod(tenantId,
	        		 interestTierBandUpdateResource.getMinTermPeriodId(), interestTierBandUpdateResource.getMaxTermPeriodName(), EntityPoint.INTEREST_TIER_BAND);
	        
			if(interestTierBandUpdateResource.getMaxTermPeriodId() != null)
	         validationService.validatePeriod(tenantId,
	        		 interestTierBandUpdateResource.getMaxTermPeriodId(), interestTierBandUpdateResource.getMaxTermPeriodName(), EntityPoint.INTEREST_TIER_BAND);
			
			if(interestTierBandUpdateResource.getLoanPrInterestRateTypeId() != null)
				loanPrInterestRateType = interestRateTypeRepository.findByIdAndNameAndStatus(validationService.stringToLong(interestTierBandUpdateResource.getLoanPrInterestRateTypeId()), 
						interestTierBandUpdateResource.getLoanPrInterestRateTypeName(), CommonStatus.ACTIVE);
        
		 	InterestTierBandPending interestTierBandPending = new InterestTierBandPending();
	        interestTierBandPending.setTenantId(tenantId);
	        interestTierBandPending.setInterestTierBand(interestTierBand);
	        interestTierBandPending.setInterestTierBandSetPending(interestTierBandSetPending);
	        interestTierBandPending.setInterestTierBandSet(interestTierBandSet);
	        interestTierBandPending.setTierValueMinimum(validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMinimum()));
	        interestTierBandPending.setTierValueMaximum(validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMaximum()));
	        interestTierBandPending.setTierValueMinTermId(tierValueMinTerm);
	        interestTierBandPending.setMinTermPeriodId(interestTierBandUpdateResource.getMinTermPeriodId() != null ? validationService.stringToLong(interestTierBandUpdateResource.getMinTermPeriodId()):null);
	        interestTierBandPending.setTierValueMaxTermId(tierValueMaxTerm);
	        interestTierBandPending.setMaxTermPeriodId(interestTierBandUpdateResource.getMaxTermPeriodId() != null ? validationService.stringToLong(interestTierBandUpdateResource.getMaxTermPeriodId()):null);
	        interestTierBandPending.setInterestRateTypeId(interestRateType);
	        interestTierBandPending.setLoanPrInterestRateType(loanPrInterestRateType != null ? loanPrInterestRateType.get():null);
	        interestTierBandPending.setLoanPrInterestRate(interestTierBandUpdateResource.getLoanPrInterestRate() != null ? validationService.stringToBigDecimal(interestTierBandUpdateResource.getLoanPrInterestRate()):null);
	        interestTierBandPending.setNote(interestTierBandUpdateResource.getNote());
	     //   interestTierBandPending.setCode(interestTierBandUpdateResource.getCode());
	        interestTierBandPending.setStatus(CommonStatus.valueOf(interestTierBandUpdateResource.getStatus()));
	        interestTierBandPending.setLendingWorkflow(interestTierBandSetPending.getLendingWorkflow());
	        interestTierBandPending.setApproveStatus(CommonApproveStatus.PENDING);
	        interestTierBandPending.setCreatedDate(validationService.getCreateOrModifyDate());
	        interestTierBandPending.setSyncTs(validationService.getCreateOrModifyDate());
	        interestTierBandPending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
	        interestTierBandPendingRepository.save(interestTierBandPending);        
	        return interestTierBandSetPending;
	} 
	
	private InterestTierBandSetPending createInterestTierBandSetPending(String tenantId, InterestTierBandSet interestTierBandSet , InterestTemplatePending interestTemplatePending){
		
		InterestTierBandSetPending interestTierBandSetPending = new InterestTierBandSetPending();
		interestTierBandSetPending.setTenantId(tenantId);
		interestTierBandSetPending.setInterestTemplate(interestTemplatePending.getInterestTemplate());
		interestTierBandSetPending.setInterestTemplatePending(interestTemplatePending);
		interestTierBandSetPending.setInterestTierBandSet(interestTierBandSet);
		interestTierBandSetPending.setTierBandMethod(interestTierBandSet.getTierBandMethod());
		interestTierBandSetPending.setCalculationMethod(interestTierBandSet.getCalculationMethod());
		interestTierBandSetPending.setNote(interestTierBandSet.getNote());
		interestTierBandSetPending.setLendingWorkflow(interestTemplatePending.getLendingWorkflow());
		interestTierBandSetPending.setUpdated(false);
		interestTierBandSetPending.setApproveStatus(CommonApproveStatus.PENDING);
		interestTierBandSetPending.setStatus(interestTierBandSet.getStatus());
		interestTierBandSetPending.setCreatedDate(validationService.getCreateOrModifyDate());
		interestTierBandSetPending.setSyncTs(validationService.getCreateOrModifyDate());
		interestTierBandSetPending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		interestTierBandSetPending = interestTierBandSetPendingRepository.saveAndFlush(interestTierBandSetPending);
        
        return interestTierBandSetPending;
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
	
	private void directUpdate(String tenantId, InterestTierBandSet interestTierBandSet , InterestTierBandUpdateResource interestTierBandUpdateResource) {		
		Optional<InterestTierBand> intTierBand = null;	
		InterestTierBand interestTierBand =  null;	
		
		CommonListItem tierValueMinTerm = null;
		CommonListItem tierValueMaxTerm = null;
		CommonListItem interestRateType = null;
		Optional<InterestRateType> loanPrInterestRateType = null;
		if(interestTierBandUpdateResource.getTierValueMinTermId() != null)
	         tierValueMinTerm = validateTierValueMinTerm(validationService.stringToLong(interestTierBandUpdateResource.getTierValueMinTermId()), interestTierBandUpdateResource.getTierValueMinTermName());  
			
			if(interestTierBandUpdateResource.getTierValueMaxTermId() != null)
	         tierValueMaxTerm = validateTierValueMaxTerm(validationService.stringToLong(interestTierBandUpdateResource.getTierValueMaxTermId()), interestTierBandUpdateResource.getTierValueMaxTermName());
	       
			if(interestTierBandUpdateResource.getInterestRateTypeId() != null)
	         interestRateType = validateInterestRateType(validationService.stringToLong(interestTierBandUpdateResource.getInterestRateTypeId()), interestTierBandUpdateResource.getInterestRateTypeName());
	        
			if(interestTierBandUpdateResource.getMinTermPeriodId() != null)
	         validationService.validatePeriod(tenantId,
	        		 interestTierBandUpdateResource.getMinTermPeriodId(), interestTierBandUpdateResource.getMaxTermPeriodName(), EntityPoint.INTEREST_TIER_BAND);
	        
			if(interestTierBandUpdateResource.getMaxTermPeriodId() != null)
	         validationService.validatePeriod(tenantId,
	        		 interestTierBandUpdateResource.getMaxTermPeriodId(), interestTierBandUpdateResource.getMaxTermPeriodName(), EntityPoint.INTEREST_TIER_BAND);
			
			if(interestTierBandUpdateResource.getLoanPrInterestRateTypeId() != null)
				loanPrInterestRateType = interestRateTypeRepository.findByIdAndNameAndStatus(validationService.stringToLong(interestTierBandUpdateResource.getLoanPrInterestRateTypeId()), 
						interestTierBandUpdateResource.getLoanPrInterestRateTypeName(), CommonStatus.ACTIVE);
		   
		if(interestTierBandUpdateResource.getId() != null) {
			intTierBand = interestTierBandRepository.findById(validationService.stringToLong(interestTierBandUpdateResource.getId()));
			interestTierBandHistoryService.saveHistory(intTierBand.get().getTenantId(), intTierBand.get(), LogginAuthentcation.getInstance().getUserName());
			
			interestTierBand = intTierBand.get();
	        interestTierBand.setModifiedDate(validationService.getCreateOrModifyDate());
	        interestTierBand.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
	        
		}else {
	        interestTierBand = new InterestTierBand();
	        interestTierBand.setCreatedDate(validationService.getCreateOrModifyDate());
	        interestTierBand.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		}
			interestTierBand.setTenantId(tenantId);
	        interestTierBand.setInterestTierBandSet(interestTierBandSet);
	        interestTierBand.setTierValueMinimum(validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMinimum()));
	        interestTierBand.setTierValueMaximum(validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMaximum()));
	        interestTierBand.setTierValueMinTermId(tierValueMinTerm);
	        interestTierBand.setMinTermPeriodId(interestTierBandUpdateResource.getMinTermPeriodId() != null ? validationService.stringToLong(interestTierBandUpdateResource.getMinTermPeriodId()):null);
	        interestTierBand.setTierValueMaxTermId(tierValueMaxTerm);
	        interestTierBand.setMaxTermPeriodId(interestTierBandUpdateResource.getMaxTermPeriodId() != null ? validationService.stringToLong(interestTierBandUpdateResource.getMaxTermPeriodId()):null);
	        interestTierBand.setInterestRateTypeId(interestRateType);
	        interestTierBand.setLoanPrInterestRateType(loanPrInterestRateType != null ? loanPrInterestRateType.get():null);
	        interestTierBand.setLoanPrInterestRate(interestTierBandUpdateResource.getLoanPrInterestRate() != null ? validationService.stringToBigDecimal(interestTierBandUpdateResource.getLoanPrInterestRate()):null);
	        interestTierBand.setNote(interestTierBandUpdateResource.getNote());
	        interestTierBand.setCode(interestTierBandUpdateResource.getCode());
	        interestTierBand.setStatus(CommonStatus.valueOf(interestTierBandUpdateResource.getStatus()));
	        interestTierBand.setCreatedDate(validationService.getCreateOrModifyDate());
	        interestTierBand.setSyncTs(validationService.getCreateOrModifyDate());
	        interestTierBand.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
	        interestTierBandRepository.save(interestTierBand);
		
	}
	
	private InterestTierBandSetPending approveOrGenerateWorkFlow(String tenantId, InterestTemplate interestTemp , InterestTierBandSet interestTierBandSet ,InterestTierBand interestTierBand, InterestTierBandUpdateResource interestTierBandUpdateResource) {
		WorkflowRulesResource workflowRulesResource = null;
		Long processId = null;
		WorkflowType workflowType = WorkflowType.INTEREST_TEMP_APPROVAL;
		LendingWorkflow lendingWorkflow = null;
		InterestTierBandSetPending interestTierBandSetPending= null;

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

		workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
				tenantId);

		if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
			processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
			if (processId != null) {
				lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, LogginAuthentcation.getInstance().getUserName());
				InterestTemplatePending interestTemplatePending = createInterestTemplatePending(tenantId , interestTemp , lendingWorkflow);
				interestTierBandSetPending = createInterestTierBandSetPending(tenantId , interestTierBandSet , interestTemplatePending);
				createInterestTierBandPending(tenantId , interestTierBandSetPending , interestTierBandSet , interestTierBand , interestTierBandUpdateResource);
				  
			}
		} else {
				directUpdate(tenantId, interestTierBandSet , interestTierBandUpdateResource);
		}
		
		return interestTierBandSetPending;
			
	}

	@Override
	public Optional<InterestTierBandPending> getByPendingId(Long pendingId) {
		Optional<InterestTierBandPending> isPresentInterestTierBandPending = interestTierBandPendingRepository
				.findById(pendingId);

		if (isPresentInterestTierBandPending.isPresent()) {
			return Optional.ofNullable(isPresentInterestTierBandPending.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<InterestTierBand> getByInterestTierBandSetId(Long interestTierBandSetId) {
		List<InterestTierBand> interestTierBandList = new ArrayList<>();
		Optional<InterestTierBandSet> isPresentInterestTierBandSet = interestTierBandSetRepository
				.findById(interestTierBandSetId);

		if (isPresentInterestTierBandSet.isPresent())
			interestTierBandList = interestTierBandRepository
					.findByInterestTierBandSet(isPresentInterestTierBandSet.get());
		return interestTierBandList;
	}

	@Override
	public Page<InterestTierBandPending> searchInterestTierBandPending(String searchq, String status,
			String approveStatus, Pageable pageable) {
		if (searchq == null || searchq.isEmpty())
			searchq = null;
		if (status == null || status.isEmpty())
			status = null;
		if (approveStatus == null || approveStatus.isEmpty())
			approveStatus = null;
		return interestTierBandPendingRepository.searchInterestTierBandPending(searchq, status, approveStatus,
				pageable);
	}

}
