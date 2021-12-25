package com.fusionx.lending.product.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.FeeChargeDetails;
import com.fusionx.lending.product.domain.InterestRateType;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.domain.MasterDefinition;
import com.fusionx.lending.product.domain.PenalInterest;
import com.fusionx.lending.product.domain.PenalInterestPending;
import com.fusionx.lending.product.domain.PenalTierBand;
import com.fusionx.lending.product.domain.PenalTierBandPending;
import com.fusionx.lending.product.domain.PenalTierBandSet;
import com.fusionx.lending.product.domain.PenalTierBandSetPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.FixedVariableType;
import com.fusionx.lending.product.enums.PenalTierBandMethod;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.enums.YesNo;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.InterestRateTypeRepository;
import com.fusionx.lending.product.repository.MasterDefinitionRepository;
import com.fusionx.lending.product.repository.PenalInterestPendingRepository;
import com.fusionx.lending.product.repository.PenalInterestRepository;
import com.fusionx.lending.product.repository.PenalTierBandPendingRepository;
import com.fusionx.lending.product.repository.PenalTierBandRepository;
import com.fusionx.lending.product.repository.PenalTierBandSetPendingRepository;
import com.fusionx.lending.product.repository.PenalTierBandSetRepository;
import com.fusionx.lending.product.resources.PenalTierBandAddRequest;
import com.fusionx.lending.product.resources.PenalTierBandUpdateRequest;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.PenalTierBandService;
import com.fusionx.lending.product.service.PenalTierBandSetService;
import com.fusionx.lending.product.service.ValidationService;


@Component
@Transactional(rollbackFor = Exception.class)
public class PenalTierBandServiceImpl  extends MessagePropertyBase implements PenalTierBandService {

	@Autowired
	private PenalTierBandSetService  penalTierBandSetService;
	
	@Autowired
	private PenalTierBandRepository  penalTierBandRepository;
	
	@Autowired
	private PenalTierBandPendingRepository  penalTierBandPendingRepository;
	
	@Autowired
	private PenalTierBandSetPendingRepository  penalTierBandSetPendingRepository;
	
	@Autowired
	private PenalInterestPendingRepository  penalInterestTemplatePendingRepository;
	
	@Autowired
	private InterestRateTypeRepository  interestRateTypeRepository;
	
	@Autowired
	private PenalInterestRepository  penalInterestTemplateRepository;
	
	@Autowired
	private PenalTierBandSetRepository  penalTierBandSetRepository;
	
	@Autowired
	private MasterDefinitionRepository  masterDefinitionRepository;
	
	@Autowired
	private ValidationService validationService;
	
	@Autowired
	private LendingWorkflowService lendingWorkflowService;
	
	@Autowired
	private Environment environment;
	
	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";


	@Override
	public Optional<PenalTierBand> getById(Long id) {
		Optional<PenalTierBand> isPenalTierBand = penalTierBandRepository.findById(id);

		if (isPenalTierBand.isPresent()) {
			return Optional.ofNullable(isPenalTierBand.get());
		} else {
			return Optional.empty();
		}
	}
	
	private Date formatDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
		
	}

	@Override
	public Long addPenalTierBand(String tenantId, Long penalTierBandSetId,
			PenalTierBandAddRequest penalTierBandAddRequest) {
		Optional<InterestRateType> loanPrInterestRateType = null;
		
			Optional<PenalTierBandSet> isPresentInterestTierBandSet = penalTierBandSetRepository.findByIdAndStatus(penalTierBandSetId,CommonStatus.ACTIVE);
			
			if(!isPresentInterestTierBandSet.isPresent()){
				LoggerRequest.getInstance().logInfo("PenalTierBandSet********************************Validate PenalTierBandSet*********************************************");
				throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "message");
			}
			
			Optional<PenalTierBand> isPresentInterestTierBand = penalTierBandRepository.findByCode(penalTierBandAddRequest.getCode());       
	        if (isPresentInterestTierBand.isPresent()) 
	        	throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE, EntityPoint.PENAL_TIER_BAND);
	        
			
			if(penalTierBandAddRequest.getLoanPrInterestRateTypeId() != null && !penalTierBandAddRequest.getLoanPrInterestRateTypeId().isEmpty()) {
				
				loanPrInterestRateType = interestRateTypeRepository.findByIdAndNameAndStatus(validationService.stringToLong(penalTierBandAddRequest.getLoanPrInterestRateTypeId()), 
						penalTierBandAddRequest.getLoanPrInterestRateTypeName(), CommonStatus.ACTIVE);
				if(!loanPrInterestRateType.isPresent()){
					throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.LOAN_PR_INTEREST_RATE_TYPE_ID, EntityPoint.PENAL_TIER_BAND);
				}
			}
			
			if(validationService.stringToBigDecimal(penalTierBandAddRequest.getTierValueMinimum()).compareTo(validationService.stringToBigDecimal(penalTierBandAddRequest.getTierValueMaximum())) >= 0) {
        		throw new ValidateRecordException(environment.getProperty("penaltierBand.minimum-maximum"), "message");
        	}
			
			if(PenalTierBandMethod.INWH.equals(isPresentInterestTierBandSet.get().getTierBandMethod())) {
				List<PenalTierBand> isPresentTierBandMethod = penalTierBandRepository.findByPenalTierBandSetAndStatus(isPresentInterestTierBandSet.get(),CommonStatus.ACTIVE);
				
				if(!isPresentTierBandMethod.isEmpty())
					throw new ValidateRecordException(environment.getProperty("tierBand.can-not-update"), "message");
			}
			
			
			List<PenalTierBand> penalTierBandAvailability = penalTierBandRepository.findByPenalTierBandSetAndEffectiveDate(isPresentInterestTierBandSet.get(), formatDate(penalTierBandAddRequest.getEffectiveDate()));
			for(PenalTierBand item : penalTierBandAvailability) {
				
				if(validationService.stringToBigDecimal(penalTierBandAddRequest.getTierValueMinimum()).compareTo(item.getTierValueMinimum()) == 0 
		    			&& validationService.stringToBigDecimal(penalTierBandAddRequest.getTierValueMaximum()).compareTo(item.getTierValueMaximum()) ==0 && validationService.stringToBigDecimal(penalTierBandAddRequest.getRepArp()).compareTo(item.getAer()) == 0) {
					throw new ValidateRecordException(environment.getProperty("minMaxRateDate.can-not-duplicate"), "message");
		    	}
				
				if(validationService.stringToBigDecimal(penalTierBandAddRequest.getTierValueMinimum()).compareTo(item.getTierValueMinimum()) >= 0 
	        			&& validationService.stringToBigDecimal(penalTierBandAddRequest.getTierValueMinimum()).compareTo(item.getTierValueMaximum()) <=0) {
	        		throw new ValidateRecordException(environment.getProperty("tierBand.minimum-can-not-update"), "message");
	        	}
	        	if(validationService.stringToBigDecimal(penalTierBandAddRequest.getTierValueMaximum()).compareTo(item.getTierValueMinimum()) >= 0 
	        			&& validationService.stringToBigDecimal(penalTierBandAddRequest.getTierValueMaximum()).compareTo(item.getTierValueMaximum()) <=0) {
	        		throw new ValidateRecordException(environment.getProperty("tierBand.maximum-can-not-update"), "message");
	        	}
			}
			
			Optional<MasterDefinition> masterDefinition = masterDefinitionRepository.findByPenalInterest(isPresentInterestTierBandSet.get().getPenalInterest());
			if(masterDefinition.isPresent()) {
				if(masterDefinition.get().getMiniPenalInterestRate() != null && masterDefinition.get().getMiniPenalInterestRate().compareTo(validationService.stringToBigDecimal(penalTierBandAddRequest.getRepArp()))>= 0) {
		        	throw new ValidateRecordException(environment.getProperty("aer-minimum"), "message");
		        }
				
				if(masterDefinition.get().getMaxPenalInterestRate() != null && validationService.stringToBigDecimal(penalTierBandAddRequest.getRepArp()).compareTo(masterDefinition.get().getMaxPenalInterestRate())>= 0) {
					throw new ValidateRecordException(environment.getProperty("aer-maximum"), "message");
				}
				
			}
	        
	        PenalTierBand penalTierBand = new PenalTierBand();
	        penalTierBand.setTenantId(tenantId);
	        penalTierBand.setPenalTierBandSet(isPresentInterestTierBandSet.get());
	        penalTierBand.setTierValueMinimum(validationService.stringToBigDecimal(penalTierBandAddRequest.getTierValueMinimum()));
	        penalTierBand.setTierValueMaximum(validationService.stringToBigDecimal(penalTierBandAddRequest.getTierValueMaximum()));
	        penalTierBand.setInterestRateType(loanPrInterestRateType != null ? loanPrInterestRateType.get():null);
	        penalTierBand.setLoanProviderIntRate(penalTierBandAddRequest.getLoanPrInterestRate());
	        penalTierBand.setNote(penalTierBandAddRequest.getNote());
	        penalTierBand.setCode(penalTierBandAddRequest.getCode());
	        penalTierBand.setFixedVariableTypeValue(FixedVariableType.valueOf(penalTierBandAddRequest.getInterestRateType()));
	        penalTierBand.setEffectiveDate(formatDate(penalTierBandAddRequest.getEffectiveDate()));
	        penalTierBand.setAer(validationService.stringToBigDecimal(penalTierBandAddRequest.getRepArp()));
	        penalTierBand.setStatus(CommonStatus.valueOf(penalTierBandAddRequest.getStatus()));
	        penalTierBand.setCreatedDate(validationService.getCreateOrModifyDate());
	        penalTierBand.setSyncTs(validationService.getCreateOrModifyDate());
	        penalTierBand.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
	        penalTierBandRepository.save(penalTierBand);
	        return penalTierBand.getId();
		}


	@Override
	public Long updatePenalTierBand(String tenantId, Long interestTempId, Long interestTierBandSetId,
			PenalTierBandUpdateRequest penalTierBandUpdateRequest) {
	
		Optional<PenalInterestPending> interestTempPendingOpt = null;
		Optional<PenalTierBandSetPending> interestTierBandSetPendingOpt = null;
		PenalTierBandSetPending interestTierBandSetPending = null;
		Optional<InterestRateType> loanPrInterestRateType = null;
		
		Optional<PenalInterest> isPresentInterestTemplate = penalInterestTemplateRepository.findByIdAndStatus(interestTempId,CommonStatus.ACTIVE);
		
		if(!isPresentInterestTemplate.isPresent()){
			LoggerRequest.getInstance().logInfo("PenalInterestTemplate********************************Validate PenalInterestTemplate*********************************************");
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");	
		}
//			if(CommonApproveStatus.PENDING.toString().equals(isPresentInterestTemplate.get().getApproveStatus()) && interestTierBandUpdateResource.getInterestTempPendingId() == null && interestTierBandUpdateResource.getTierBandSetPendingId() == null)
//				throw new ValidateRecordException(environment.getProperty("interestTemplate.can-not-update"), "message");			
		
		if(penalTierBandUpdateRequest.getPenalInterestTempPendingId() != null && penalTierBandUpdateRequest.getPenalTierBandSetPendingId() != null) {
			Optional<PenalTierBandSetPending> isPresentPenalTierBandSetPending = penalTierBandSetPendingRepository.findByIdAndStatus(validationService.stringToLong(penalTierBandUpdateRequest.getPenalTierBandSetPendingId()),CommonStatus.ACTIVE);
			
			if(!isPresentPenalTierBandSetPending.isPresent()){
				LoggerRequest.getInstance().logInfo("PenalInterestTemplatePending********************************Validate PenalInterestTemplatePending*********************************************");
				throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");	
			}else {
				if(!CommonApproveStatus.PENDING.toString().equals(isPresentPenalTierBandSetPending.get().getApproveStatus())){
					throw new ValidateRecordException(environment.getProperty("interestTemplate.can-not-update"), "message");
				}
			}
			
			
		}
		
		Optional<PenalTierBandSet> isPresentInterestTierBandSet = penalTierBandSetRepository.findByPenalInterestIdAndIdAndStatus(interestTempId,interestTierBandSetId,CommonStatus.ACTIVE);
		
		if(!isPresentInterestTierBandSet.isPresent()){
			LoggerRequest.getInstance().logInfo("PenalTierBandSet********************************Validate PenalTierBandSet*********************************************");
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");	
		}
		
		if(PenalTierBandMethod.INWH.equals(isPresentInterestTierBandSet.get().getTierBandMethod())) {
			List<PenalTierBand> isPresentTierBandMethod = penalTierBandRepository.findByPenalTierBandSetAndStatus(isPresentInterestTierBandSet.get(),CommonStatus.ACTIVE);
			
			if(!isPresentTierBandMethod.isEmpty())
				throw new ValidateRecordException(environment.getProperty("tierBand.can-not-update"), "message");
		}
		
		if(validationService.stringToBigDecimal(penalTierBandUpdateRequest.getTierValueMinimum()).compareTo(validationService.stringToBigDecimal(penalTierBandUpdateRequest.getTierValueMaximum())) >= 0) {
    		throw new ValidateRecordException(environment.getProperty("penaltierBand.minimum-maximum"), "message");
    	}
		
		Optional<MasterDefinition> masterDefinition = masterDefinitionRepository.findByPenalInterest(isPresentInterestTierBandSet.get().getPenalInterest());
		if(masterDefinition.isPresent()) {
			if(masterDefinition.get().getMiniPenalInterestRate() != null && masterDefinition.get().getMiniPenalInterestRate().compareTo(validationService.stringToBigDecimal(penalTierBandUpdateRequest.getRepArp()))>= 0) {
	        	throw new ValidateRecordException(environment.getProperty("aer-minimum"), "message");
	        }
			
			if(masterDefinition.get().getMaxPenalInterestRate() != null && validationService.stringToBigDecimal(penalTierBandUpdateRequest.getRepArp()).compareTo(masterDefinition.get().getMaxPenalInterestRate())>= 0) {
				throw new ValidateRecordException(environment.getProperty("aer-maximum"), "message");
			}
			
		}
		
		if(penalTierBandUpdateRequest.getLoanPrInterestRateTypeId() != null && !penalTierBandUpdateRequest.getLoanPrInterestRateTypeId().isEmpty()) {
			
			loanPrInterestRateType = interestRateTypeRepository.findByIdAndNameAndStatus(validationService.stringToLong(penalTierBandUpdateRequest.getLoanPrInterestRateTypeId()), 
					penalTierBandUpdateRequest.getLoanPrInterestRateTypeName(), CommonStatus.ACTIVE);
			if(!loanPrInterestRateType.isPresent()){
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.LOAN_PR_INTEREST_RATE_TYPE_ID, EntityPoint.PENAL_TIER_BAND);
			}
		}
		
		//Penal Interest Template Update - PenalTierBand Update existing 
		if(penalTierBandUpdateRequest.getId() != null) {			
			Optional<PenalTierBand> isPresentPenalTierBand = penalTierBandRepository.findByPenalTierBandSetIdAndId(interestTierBandSetId, validationService.stringToLong(penalTierBandUpdateRequest.getId()));
			
			if(!isPresentPenalTierBand.isPresent()) {
				LoggerRequest.getInstance().logInfo("PenalTierBandSet********************************Validate PenalTierBandSet*********************************************");
				throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");	
			}
			
			 List<PenalTierBand> penalTierBandList = penalTierBandRepository.findByPenalTierBandSet(isPresentInterestTierBandSet.get());
		        for(PenalTierBand penalTierBand : penalTierBandList) {
		        	if(validationService.stringToBigDecimal(penalTierBandUpdateRequest.getTierValueMinimum()).compareTo(penalTierBand.getTierValueMinimum()) >= 0 
		        			&& validationService.stringToBigDecimal(penalTierBandUpdateRequest.getTierValueMinimum()).compareTo(penalTierBand.getTierValueMaximum()) <=0 && penalTierBand.getId() != isPresentPenalTierBand.get().getId()) {
		        		throw new ValidateRecordException(environment.getProperty("tierBand.minimum-can-not-update"), "message");
		        	}
		        	if(validationService.stringToBigDecimal(penalTierBandUpdateRequest.getTierValueMaximum()).compareTo(penalTierBand.getTierValueMinimum()) >= 0 
		        			&& validationService.stringToBigDecimal(penalTierBandUpdateRequest.getTierValueMaximum()).compareTo(penalTierBand.getTierValueMaximum()) <=0 && penalTierBand.getId() != isPresentPenalTierBand.get().getId()) {
		        		throw new ValidateRecordException(environment.getProperty("tierBand.maximum-can-not-update"), "message");
		        	}
		        }
		        
		     List<PenalTierBandPending> interestTierBandPendingList = penalTierBandPendingRepository.findByPenalTierBandSetAndApproveStatus(isPresentInterestTierBandSet.get(), CommonApproveStatus.PENDING);
		        for(PenalTierBandPending penalTierBandPending : interestTierBandPendingList) {
		        	if(validationService.stringToBigDecimal(penalTierBandUpdateRequest.getTierValueMinimum()).compareTo(penalTierBandPending.getTierValueMaximum()) >= 0 
		        			&& validationService.stringToBigDecimal(penalTierBandUpdateRequest.getTierValueMinimum()).compareTo(penalTierBandPending.getTierValueMaximum()) <=0) {
		        		throw new ValidateRecordException(environment.getProperty("tierBand.minimum-pending-can-not-update"), "message");
		        	}
		        	if(validationService.stringToBigDecimal(penalTierBandUpdateRequest.getTierValueMaximum()).compareTo(penalTierBandPending.getTierValueMaximum()) >= 0 
		        			&& validationService.stringToBigDecimal(penalTierBandUpdateRequest.getTierValueMaximum()).compareTo(penalTierBandPending.getTierValueMaximum()) <=0) {
		        		throw new ValidateRecordException(environment.getProperty("tierBand.maximum-pending-can-not-update"), "message");
		        	}
		        }
		        
				if (!isPresentPenalTierBand.get().getVersion()
						.equals(Long.parseLong(penalTierBandUpdateRequest.getVersion()))) {
					throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION,
							EntityPoint.PENAL_TIER_BAND);
				}
		
				if (!isPresentPenalTierBand.get().getCode().equalsIgnoreCase(penalTierBandUpdateRequest.getCode())) {
					throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE,
							EntityPoint.PENAL_TIER_BAND);
				}
				
					if(penalTierBandUpdateRequest.getPenalTierBandSetPendingId() != null) {
						interestTierBandSetPendingOpt = penalTierBandSetPendingRepository.findById(validationService.stringToLong(penalTierBandUpdateRequest.getPenalTierBandSetPendingId()));	
						
						if(!interestTierBandSetPendingOpt.isPresent()) {
							LoggerRequest.getInstance().logInfo("InterestTierBandSetPending********************************Validate InterestTierBandSetPending*********************************************");
							throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");	
						}							
						 interestTierBandSetPending = createInterestTierBandPending(tenantId, interestTierBandSetPendingOpt.get() , isPresentInterestTierBandSet.get(), isPresentPenalTierBand.get(), penalTierBandUpdateRequest);
					}else {
						
						if(penalTierBandUpdateRequest.getPenalInterestTempPendingId() != null) {
							interestTempPendingOpt = penalInterestTemplatePendingRepository.findById(validationService.stringToLong(penalTierBandUpdateRequest.getPenalInterestTempPendingId()));					
							
							if(!interestTempPendingOpt.isPresent()) {
								LoggerRequest.getInstance().logInfo("InterestTemplatePending********************************Validate InterestTemplatePending*********************************************");
								throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");							
							}
							interestTierBandSetPending = createInterestTierBandSetPending(tenantId, isPresentInterestTierBandSet.get(), interestTempPendingOpt.get());
							createInterestTierBandPending(tenantId, interestTierBandSetPending , isPresentInterestTierBandSet.get(), isPresentPenalTierBand.get(), penalTierBandUpdateRequest);
							
						}else {
							
							interestTierBandSetPending = approveOrGenerateWorkFlow(tenantId, isPresentInterestTemplate.get() , isPresentInterestTierBandSet.get() ,isPresentPenalTierBand.get(), penalTierBandUpdateRequest);
						}
					}
								
		}else { //Penal Interest Template Update -  add new PenalTierBand
			
			Optional<PenalTierBand> isPresentInterestTierBand = penalTierBandRepository.findByCode(penalTierBandUpdateRequest.getCode());       
	        if (isPresentInterestTierBand.isPresent()) 
	        	throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.INTEREST_TIER_BAND);
	        
	        List<PenalTierBand> interestTierBandList = penalTierBandRepository.findByPenalTierBandSet(isPresentInterestTierBandSet.get());
		        for(PenalTierBand penalTierBand : interestTierBandList) {
		        	if(validationService.stringToBigDecimal(penalTierBandUpdateRequest.getTierValueMinimum()).compareTo(penalTierBand.getTierValueMinimum()) >= 0 
		        			&& validationService.stringToBigDecimal(penalTierBandUpdateRequest.getTierValueMinimum()).compareTo(penalTierBand.getTierValueMaximum()) <=0) {
		        		throw new ValidateRecordException(environment.getProperty("tierBand.minimum-can-not-update"), "message");
		        	}
		        	if(validationService.stringToBigDecimal(penalTierBandUpdateRequest.getTierValueMaximum()).compareTo(penalTierBand.getTierValueMinimum()) >= 0 
		        			&& validationService.stringToBigDecimal(penalTierBandUpdateRequest.getTierValueMaximum()).compareTo(penalTierBand.getTierValueMaximum()) <=0) {
		        		throw new ValidateRecordException(environment.getProperty("tierBand.maximum-can-not-update"), "message");
		        	}
		        }
		        		     
		        List<PenalTierBandPending> interestTierBandPendingList = penalTierBandPendingRepository.findByPenalTierBandSetAndApproveStatus(isPresentInterestTierBandSet.get(), CommonApproveStatus.PENDING);
		        for(PenalTierBandPending penalTierBandPending : interestTierBandPendingList) {
		        	if(validationService.stringToBigDecimal(penalTierBandUpdateRequest.getTierValueMinimum()).compareTo(penalTierBandPending.getTierValueMaximum()) >= 0 
		        			&& validationService.stringToBigDecimal(penalTierBandUpdateRequest.getTierValueMinimum()).compareTo(penalTierBandPending.getTierValueMaximum()) <=0) {
		        		throw new ValidateRecordException(environment.getProperty("tierBand.minimum-pending-can-not-update"), "message");
		        	}
		        	if(validationService.stringToBigDecimal(penalTierBandUpdateRequest.getTierValueMaximum()).compareTo(penalTierBandPending.getTierValueMaximum()) >= 0 
		        			&& validationService.stringToBigDecimal(penalTierBandUpdateRequest.getTierValueMaximum()).compareTo(penalTierBandPending.getTierValueMaximum()) <=0) {
		        		throw new ValidateRecordException(environment.getProperty("tierBand.maximum-pending-can-not-update"), "message");
		        	}
		        }
			
	    	if(penalTierBandUpdateRequest.getPenalTierBandSetPendingId() != null) {
				interestTierBandSetPendingOpt = penalTierBandSetPendingRepository.findById(validationService.stringToLong(penalTierBandUpdateRequest.getPenalTierBandSetPendingId()));	
				
				if(!interestTierBandSetPendingOpt.isPresent()) {
					LoggerRequest.getInstance().logInfo("InterestTierBandSetPending********************************Validate InterestTierBandSetPending*********************************************");
					throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");	
				}							
				 interestTierBandSetPending = createInterestTierBandPending(tenantId, interestTierBandSetPendingOpt.get() , isPresentInterestTierBandSet.get(), null, penalTierBandUpdateRequest);
			}else {
				
				if(penalTierBandUpdateRequest.getPenalInterestTempPendingId() != null) {
					interestTempPendingOpt = penalInterestTemplatePendingRepository.findById(validationService.stringToLong(penalTierBandUpdateRequest.getPenalInterestTempPendingId()));					
					
					if(!interestTempPendingOpt.isPresent()) {
						LoggerRequest.getInstance().logInfo("InterestTemplatePending********************************Validate InterestTemplatePending*********************************************");
						throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");							
					}
					interestTierBandSetPending = createInterestTierBandSetPending(tenantId, isPresentInterestTierBandSet.get(), interestTempPendingOpt.get());
					createInterestTierBandPending(tenantId, interestTierBandSetPending , isPresentInterestTierBandSet.get(), null, penalTierBandUpdateRequest);
					
				}else {
					
					interestTierBandSetPending = approveOrGenerateWorkFlow(tenantId, isPresentInterestTemplate.get() , isPresentInterestTierBandSet.get() ,null, penalTierBandUpdateRequest);
				}
			}
		}
		
		return interestTierBandSetPending.getId();
	}
	
	private PenalTierBandSetPending createInterestTierBandPending(String tenantId, PenalTierBandSetPending interestTierBandSetPending, PenalTierBandSet interestTierBandSet, PenalTierBand interestTierBand  , PenalTierBandUpdateRequest interestTierBandUpdateResource){
			
//			if(interestTierBandUpdateResource.getLoanPrInterestRateTypeId() != null)
//				loanPrInterestRateType = interestRateTypeRepository.findByIdAndNameAndStatus(validationService.stringToLong(interestTierBandUpdateResource.getLoanPrInterestRateTypeId()), 
//						interestTierBandUpdateResource.getLoanPrInterestRateTypeName(), CommonStatus.ACTIVE);
        
		 	PenalTierBandPending penalTierBandPending = new PenalTierBandPending();
		 	penalTierBandPending.setTenantId(tenantId);
		 	penalTierBandPending.setPenalTierBand(interestTierBand);
		 	penalTierBandPending.setPenalTierBandSetPending(interestTierBandSetPending);
		 	penalTierBandPending.setPenalTierBandSet(interestTierBandSet);
		 	penalTierBandPending.setEffectiveDate(formatDate(interestTierBandUpdateResource.getEffectiveDate()));
		 	penalTierBandPending.setTierValueMinimum(validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMinimum()));
		 	penalTierBandPending.setTierValueMaximum(validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMaximum()));
		 	penalTierBandPending.setAer(validationService.stringToBigDecimal(interestTierBandUpdateResource.getRepArp()));
		 //	penalTierBandPending.setInterestRateType(loanPrInterestRateType != null ? loanPrInterestRateType.get():null);
		 	penalTierBandPending.setLoanProviderIntRate(interestTierBandUpdateResource.getLoanPrInterestRate());
		 	penalTierBandPending.setNote(interestTierBandUpdateResource.getNote());
		 	penalTierBandPending.setCode(interestTierBandUpdateResource.getCode());
		 	penalTierBandPending.setStatus(CommonStatus.valueOf(interestTierBandUpdateResource.getStatus()));
		 	penalTierBandPending.setFixedVariableTypeValue(FixedVariableType.valueOf(interestTierBandUpdateResource.getInterestRateType()));
		 	penalTierBandPending.setLendingWorkflow(interestTierBandSetPending.getLendingWorkflow());
		 	penalTierBandPending.setApproveStatus(CommonApproveStatus.PENDING);
		 	penalTierBandPending.setUpdated(YesNo.YES);
		 	penalTierBandPending.setCreatedDate(validationService.getCreateOrModifyDate());
		 	penalTierBandPending.setSyncTs(validationService.getCreateOrModifyDate());
		 	penalTierBandPending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
	        penalTierBandPendingRepository.save(penalTierBandPending);        
	        return interestTierBandSetPending;
	} 
	
	private PenalTierBandSetPending createInterestTierBandSetPending(String tenantId, PenalTierBandSet penalTierBandSet , PenalInterestPending interestTemplatePending){
		
		PenalTierBandSetPending penalTierBandSetPending = new PenalTierBandSetPending();
		penalTierBandSetPending.setTenantId(tenantId);
		penalTierBandSetPending.setPenalInterest(interestTemplatePending.getPenalInterest());
		penalTierBandSetPending.setPenalInterestPending(interestTemplatePending);
		penalTierBandSetPending.setPenalTierBandSet(penalTierBandSet);
		penalTierBandSetPending.setRateCalculationMethod(penalTierBandSet.getRateCalculationMethod());
		penalTierBandSetPending.setTierBandMethod(penalTierBandSet.getTierBandMethod());
		penalTierBandSetPending.setInterestCalculationMethod(penalTierBandSet.getInterestCalculationMethod());
		penalTierBandSetPending.setPenalInterestType(penalTierBandSet.getPenalInterestType());
		penalTierBandSetPending.setNote(penalTierBandSet.getNote());
		penalTierBandSetPending.setCode(penalTierBandSet.getCode());
		penalTierBandSetPending.setLendingWorkflow(interestTemplatePending.getLendingWorkflow());
		penalTierBandSetPending.setUpdated(YesNo.NO);
		penalTierBandSetPending.setApproveStatus(CommonApproveStatus.PENDING);
		penalTierBandSetPending.setStatus(penalTierBandSet.getStatus());
		penalTierBandSetPending.setCreatedDate(validationService.getCreateOrModifyDate());
		penalTierBandSetPending.setSyncTs(validationService.getCreateOrModifyDate());
		penalTierBandSetPending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		penalTierBandSetPending = penalTierBandSetPendingRepository.saveAndFlush(penalTierBandSetPending);
        
        return penalTierBandSetPending;
	} 
	
	private PenalInterestPending createInterestTemplatePending(String tenantId, PenalInterest interestTemplate,LendingWorkflow lendingWorkflow) {
		
		PenalInterestPending penalInterestTemplatePending = new PenalInterestPending();
		penalInterestTemplatePending.setTenantId(tenantId);
		penalInterestTemplatePending.setLendingWorkflow(lendingWorkflow);
		penalInterestTemplatePending.setPenalInterest(interestTemplate);
		penalInterestTemplatePending.setName(interestTemplate.getName());
		penalInterestTemplatePending.setStatus(CommonStatus.valueOf(interestTemplate.getStatus().toString()));
		penalInterestTemplatePending.setUpdated(YesNo.NO);
		penalInterestTemplatePending.setApproveStatus(CommonApproveStatus.PENDING);
		penalInterestTemplatePending.setCreatedDate(validationService.getCreateOrModifyDate());
		penalInterestTemplatePending.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		penalInterestTemplatePending.setSyncTs(validationService.getCreateOrModifyDate());
		penalInterestTemplatePending = penalInterestTemplatePendingRepository.saveAndFlush(penalInterestTemplatePending);
		
		return penalInterestTemplatePending;
	
	}
	
	private void directUpdate(String tenantId, PenalTierBandSet interestTierBandSet , PenalTierBandUpdateRequest interestTierBandUpdateResource) {		
		Optional<PenalTierBand> intTierBand = null;	
		PenalTierBand penalTierBand =  null;	
		
		Optional<InterestRateType> loanPrInterestRateType = null;
		
		
			if(interestTierBandUpdateResource.getLoanPrInterestRateTypeId() != null && !interestTierBandUpdateResource.getLoanPrInterestRateTypeId().isEmpty())
				loanPrInterestRateType = interestRateTypeRepository.findByIdAndNameAndStatus(validationService.stringToLong(interestTierBandUpdateResource.getLoanPrInterestRateTypeId()), 
						interestTierBandUpdateResource.getLoanPrInterestRateTypeName(), CommonStatus.ACTIVE);
		   
		if(interestTierBandUpdateResource.getId() != null) {
			intTierBand = penalTierBandRepository.findById(validationService.stringToLong(interestTierBandUpdateResource.getId()));
			//interestTierBandHistoryService.saveHistory(intTierBand.get().getTenantId(), intTierBand.get(), LogginAuthentcation.getInstance().getUserName());
			
			penalTierBand = intTierBand.get();
			penalTierBand.setModifiedDate(validationService.getCreateOrModifyDate());
			penalTierBand.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
	        
		}else {
			penalTierBand = new PenalTierBand();
			penalTierBand.setCreatedDate(validationService.getCreateOrModifyDate());
			penalTierBand.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		}
		    penalTierBand.setTenantId(tenantId);
		    penalTierBand.setPenalTierBandSet(interestTierBandSet);
		    penalTierBand.setTierValueMaximum(validationService.stringToBigDecimal(interestTierBandUpdateResource.getTierValueMaximum()));
		    
		    penalTierBand.setInterestRateType(loanPrInterestRateType != null ? loanPrInterestRateType.get():null);
		    penalTierBand.setLoanProviderIntRate(interestTierBandUpdateResource.getLoanPrInterestRate());
		    penalTierBand.setNote(interestTierBandUpdateResource.getNote());
		    
		    penalTierBand.setCode(interestTierBandUpdateResource.getCode());
		    penalTierBand.setStatus(CommonStatus.valueOf(interestTierBandUpdateResource.getStatus()));
		    penalTierBand.setCreatedDate(validationService.getCreateOrModifyDate());
		    penalTierBand.setSyncTs(validationService.getCreateOrModifyDate());
		    penalTierBand.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
	        penalTierBandRepository.save(penalTierBand);
		
	}
	
	private PenalTierBandSetPending approveOrGenerateWorkFlow(String tenantId, PenalInterest interestTemp , PenalTierBandSet interestTierBandSet ,PenalTierBand interestTierBand, PenalTierBandUpdateRequest interestTierBandUpdateResource) {
		WorkflowRulesResource workflowRulesResource = null;
		Long processId = null;
		WorkflowType workflowType = WorkflowType.INTEREST_TEMP_APPROVAL;
		LendingWorkflow lendingWorkflow = null;
		PenalTierBandSetPending interestTierBandSetPending= null;

		WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
		workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
		workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

		workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
				tenantId);

		if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
			processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource, tenantId);
			if (processId != null) {
				lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, LogginAuthentcation.getInstance().getUserName());
				PenalInterestPending interestTemplatePending = createInterestTemplatePending(tenantId , interestTemp , lendingWorkflow);
				interestTierBandSetPending = createInterestTierBandSetPending(tenantId , interestTierBandSet , interestTemplatePending);
				createInterestTierBandPending(tenantId , interestTierBandSetPending , interestTierBandSet , interestTierBand , interestTierBandUpdateResource);
				  
			}
		} else {
				directUpdate(tenantId, interestTierBandSet , interestTierBandUpdateResource);
		}
		
		return interestTierBandSetPending;
			
	}

	@Override
	public Optional<PenalTierBandPending> getByPendingId(Long pendingId) {
		Optional<PenalTierBandPending> isPresentInterestTierBandPending = penalTierBandPendingRepository
				.findById(pendingId);

		if (isPresentInterestTierBandPending.isPresent()) {
			return Optional.ofNullable(isPresentInterestTierBandPending.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<PenalTierBand> getByPenalTierBandSetId(Long interestTierBandSetId) {
		List<PenalTierBand> penalTierBandList = new ArrayList<>();
		Optional<PenalTierBandSet> isPresentPenalTierBandSet = penalTierBandSetRepository
				.findById(interestTierBandSetId);

		if (isPresentPenalTierBandSet.isPresent())
			penalTierBandList = penalTierBandRepository
					.findByPenalTierBandSet(isPresentPenalTierBandSet.get());
		return penalTierBandList;
	}

//	@Override
//	public Page<InterestTierBandPending> searchInterestTierBandPending(String searchq, String status,
//			String approveStatus, Pageable pageable) {
//		if (searchq == null || searchq.isEmpty())
//			searchq = null;
//		if (status == null || status.isEmpty())
//			status = null;
//		if (approveStatus == null || approveStatus.isEmpty())
//			approveStatus = null;
//		return interestTierBandPendingRepository.searchInterestTierBandPending(searchq, status, approveStatus,
//				pageable);
//	}

}
