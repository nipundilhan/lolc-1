package com.fusionx.lending.origination.service.impl;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fusionx.lending.origination.repository.BusinessTypeRepository;
import com.fusionx.lending.origination.repository.RiskGradingDetailRepository;
import com.fusionx.lending.origination.repository.RiskGradingRepository;
import com.fusionx.lending.origination.resource.BusinessPersonType;
import com.fusionx.lending.origination.resource.IndustryType;
import com.fusionx.lending.origination.resource.RiskGradingAddResource;
import com.fusionx.lending.origination.resource.RiskGradingDetailAddResource;
import com.fusionx.lending.origination.resource.RiskGradingDetailUpdateResource;
import com.fusionx.lending.origination.resource.RiskGradingUpdateResource;
import com.fusionx.lending.origination.service.RiskGradingService;
import com.fusionx.lending.origination.service.ValidateService;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.domain.RiskGrading;
import com.fusionx.lending.origination.domain.RiskGradingDetail;
import com.fusionx.lending.origination.enums.ActionType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.InvalidDetailListServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.TransferType;


/**
 * Risk Grading Service Implementation
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *   1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class RiskGradingServiceImpl implements RiskGradingService {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private RiskGradingRepository riskGradingRepository;
	
	@Autowired
	private RiskGradingDetailRepository riskGradingDetailRepository;
	
	@Autowired 
	private ValidateService validationService;
	
	@Autowired 
	private BusinessTypeRepository businessTypeRepository;
	
	@Override
	public List<RiskGrading> findAll() {
		return riskGradingRepository.findAll();
	}
	
	@Override
	public Optional<RiskGrading> findById(Long id) {
		Optional<RiskGrading> isPresentRiskGrading = riskGradingRepository.findById(id);
		if (isPresentRiskGrading.isPresent()) {
			RiskGrading riskGrading = isPresentRiskGrading.get();
			List<RiskGradingDetail> riskGradingDetail = new ArrayList<>();
			riskGradingDetail = riskGradingDetailRepository.findByRiskGradingsId(riskGrading.getId());
			riskGrading.setRiskGradingDetailList(riskGradingDetail);
			return isPresentRiskGrading;
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public List<RiskGrading> findByStatus(String status) {
		List<RiskGrading> isPresentRiskGrading =  riskGradingRepository.findByStatus(status);
		List<RiskGrading> gradingList = new ArrayList<>();
		List<RiskGradingDetail> riskGradingDetail = new ArrayList<>();
		for(RiskGrading riskGrading : isPresentRiskGrading) {
			riskGradingDetail = riskGradingDetailRepository.findByRiskGradingsId(riskGrading.getId());
			riskGrading.setRiskGradingDetailList(riskGradingDetail);
			gradingList.add(riskGrading);
		}
		return gradingList;
	}

	@Override
	public List<RiskGrading> findByName(String name) {
		List<RiskGrading> isPresentRiskGrading = riskGradingRepository.findByNameContaining(name);
		List<RiskGrading> gradingList = new ArrayList<>();
		List<RiskGradingDetail> riskGradingDetail = new ArrayList<>();
		for(RiskGrading riskGrading : isPresentRiskGrading) {
			riskGradingDetail = riskGradingDetailRepository.findByRiskGradingsId(riskGrading.getId());
			riskGrading.setRiskGradingDetailList(riskGradingDetail);
			gradingList.add(riskGrading);
		}
		return gradingList;
	}

	@Override
	public Optional<RiskGrading> findByCode(String code) {
		Optional<RiskGrading> isPresentRiskGrading = riskGradingRepository.findByCode(code);
		if(isPresentRiskGrading.isPresent()) {
			RiskGrading riskGrading = isPresentRiskGrading.get();
			List<RiskGradingDetail> riskGradingDetail = new ArrayList<>();
			riskGradingDetail = riskGradingDetailRepository.findByRiskGradingsId(riskGrading.getId());
			riskGrading.setRiskGradingDetailList(riskGradingDetail);
			return isPresentRiskGrading;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Boolean existsById(Long id) {
		return riskGradingRepository.existsById(id);
	}
	
	@Override
	public Long saveAndValidateRiskGrading(String tenantId, String createdUser, RiskGradingAddResource riskGradingAddResource) {
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate business type*******************");
		BusinessType businessType = setBusinessTypeAndValidate(Long.parseLong(riskGradingAddResource.getBusinessTypeId()));
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Name*******************");
		if(riskGradingRepository.existsByName(riskGradingAddResource.getName()))
			throw new ValidateRecordException(environment.getProperty("common.unique"), "name");
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Code*******************");
		if(riskGradingRepository.existsByCode(riskGradingAddResource.getCode()))
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Dupilcates*******************");
		if(riskGradingRepository.existsByCodeAndName(riskGradingAddResource.getCode(), riskGradingAddResource.getName()))
			throw new ValidateRecordException(environment.getProperty("typeAndProcess.unique"), "message");
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Business person type*******************");
		BusinessPersonType bpt = validationService.validateBusinessPersonType(tenantId, riskGradingAddResource.getBusinessPersonTypeId(), riskGradingAddResource.getBusinessPersonTypeName(), ServiceEntity.BUSINESS_PERSON_TYPE);
		
		if(bpt==null)
			throw new ValidateRecordException(environment.getProperty("typeAndProcess.unique"), "Invalid business person type");
		
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate insdustry type*******************");
		IndustryType ind = validationService.validateIndustryType(tenantId, riskGradingAddResource.getIndustryTypeId(), riskGradingAddResource.getIndustryTypeName(), ServiceEntity.INDUSTRY_TYPE);
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Save risk grading*******************");
		RiskGrading riskGrading = saveRiskGrading(tenantId, createdUser, businessType, riskGradingAddResource);
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Risk grading details*******************");
		if(riskGradingAddResource.getRiskGradingDetails() !=null && !riskGradingAddResource.getRiskGradingDetails().isEmpty()) {			
			Integer index=0;
			int toScorePrevious = 0;
			int fromScorePrevious = 0;
			
			int toScoreNext = 0;
			int fromScoreNext = 0;
			
			int successCount = 0;
			int invalidCount = 0;
			
			int startScore = 0;
			int endScore = 0;
			
			/*Valid score pattern should be
			 * 
			 * 0-30
			 * 31-60
			 * 61-100 like wise
			 */
			ArrayList<RiskGradingDetailAddResource> riskDetail = new ArrayList<RiskGradingDetailAddResource>();
			for(RiskGradingDetailAddResource riskGradingDetailAddResource : riskGradingAddResource.getRiskGradingDetails()) {
				riskDetail.add(riskGradingDetailAddResource);
			}
			
			for(RiskGradingDetailAddResource riskGradingDetailAddResource : riskGradingAddResource.getRiskGradingDetails()) {
				for(int i=0; i<riskDetail.size()-1; i++) {
					fromScorePrevious = Integer.parseInt(riskDetail.get(i).getFromScore());
					toScorePrevious = Integer.parseInt(riskDetail.get(i).getToScore());
					
					fromScoreNext = Integer.parseInt(riskDetail.get(i+1).getFromScore());
					toScoreNext = Integer.parseInt(riskDetail.get(i+1).getToScore());
					
					if((toScorePrevious + 1) == fromScoreNext) {
						LoggerRequest.getInstance().logInfo("NotificationProcessSetup************score validating*******************");
						successCount++;
					}
					else {
						invalidCount++;
					}
				}
				
				startScore = Integer.parseInt(riskDetail.get(0).getFromScore());
				endScore = Integer.parseInt(riskDetail.get(riskDetail.size()-1).getToScore());
				
				if((startScore == 0) && (endScore == 100)) {
					successCount++;
				}
				else {
					invalidCount++;
				}
				
				if(invalidCount == 0) {
					LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Save risk grading details*******************");
					saveRiskGradingDetail(tenantId, createdUser, riskGrading,riskGradingDetailAddResource);
				}else{
					throw new ValidateRecordException(environment.getProperty("common.invalid-score"), "gradingScore");
				}
				
				index++;
			}
			
		}
		
		return riskGrading.getId();
	}
	
	private BusinessType setBusinessTypeAndValidate(Long businessTypeId) {
		BusinessType businessType = null;
		Optional<BusinessType> businessTypeOptional = businessTypeRepository.findByIdAndStatus(businessTypeId, CommonStatus.ACTIVE.toString());
		if (!businessTypeOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "businessTypeId");
		} else {
			businessType = businessTypeOptional.get();
		}
		return businessType;
	}
		
	private RiskGrading saveRiskGrading(String tenantId, String createdUser,BusinessType businessType, RiskGradingAddResource riskGradingAddResource) {
		RiskGrading riskGrading =  new RiskGrading();
		riskGrading.setTenantId(tenantId);
		riskGrading.setBusinessPersonTypeId(Long.parseLong(riskGradingAddResource.getBusinessPersonTypeId()));
		riskGrading.setBusinessTypes(businessType);
		riskGrading.setIndustryTypeId(Long.parseLong(riskGradingAddResource.getIndustryTypeId()));
		riskGrading.setCode(riskGradingAddResource.getCode());
		riskGrading.setName(riskGradingAddResource.getName());
		riskGrading.setDescription(riskGradingAddResource.getDescription());
		riskGrading.setStatus(riskGradingAddResource.getStatus());
		riskGrading.setCreatedUser(createdUser);
		riskGrading.setCreatedDate(getCreateOrModifyDate());
		riskGrading.setSyncTs(getCreateOrModifyDate());
		
		riskGrading = riskGradingRepository.saveAndFlush(riskGrading);
		return riskGrading;
		
	}
	
	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}
	
	private RiskGradingDetail saveRiskGradingDetail(String tenantId, String createdUser,RiskGrading riskGrading,RiskGradingDetailAddResource riskGradingDetailAddResource) {
		RiskGradingDetail riskGradingDetails =  new RiskGradingDetail();
		
		riskGradingDetails.setTenantId(tenantId);
		riskGradingDetails.setGrading(riskGradingDetailAddResource.getGrading());
		riskGradingDetails.setRiskGradings(riskGrading);
		riskGradingDetails.setFromScore(Long.parseLong(riskGradingDetailAddResource.getFromScore()));
		riskGradingDetails.setToScore(Long.parseLong(riskGradingDetailAddResource.getToScore()));
		riskGradingDetails.setShortCode(riskGradingDetailAddResource.getShortCode());
		riskGradingDetails.setStatus(riskGradingDetailAddResource.getStatus());
		riskGradingDetails.setCreatedUser(createdUser);
		riskGradingDetails.setCreatedDate(getCreateOrModifyDate());
		riskGradingDetails.setSyncTs(getCreateOrModifyDate());
		
		riskGradingDetails = riskGradingDetailRepository.saveAndFlush(riskGradingDetails);
		return riskGradingDetails;
	}
	
	@Override
	public Long updateAndValidateRiskGrading(String tenantId, String createdUser, Long id,RiskGradingUpdateResource riskGradingUpdateResource) {

		BusinessType businessType = setBusinessTypeAndValidate(Long.parseLong(riskGradingUpdateResource.getBusinessTypeId()));
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate version*******************");
		Optional<RiskGrading> isPresentRiskGrading = riskGradingRepository.findById(id);
		if(!isPresentRiskGrading.get().getVersion().equals(Long.parseLong(riskGradingUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate name*******************");
		Optional<RiskGrading> isPresentNotificationProcessSetupByName = riskGradingRepository.findByNameAndIdNotIn(riskGradingUpdateResource.getName(), id);
		if (isPresentNotificationProcessSetupByName.isPresent())
			throw new ValidateRecordException(environment.getProperty("common.unique"), "name");
		
//		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate code*******************");
//		Optional<RiskGrading> isPresentNotificationProcessSetupByCode = riskGradingRepository.findByCodeAndIdNotIn(riskGradingUpdateResource.getCode(), id);
//		if (isPresentNotificationProcessSetupByCode.isPresent())
//			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		LoggerRequest.getInstance().logInfo("************************** Code Can Not Update *****************************");
        if (!isPresentRiskGrading.get().getCode().equals(riskGradingUpdateResource.getCode())) {
            throw new ValidateRecordException(environment.getProperty("common.code-update"), "code");
        }
		
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate dusplicates*******************");
		Optional<RiskGrading> isPresentNotificationProcessSetupByType = riskGradingRepository.findByCodeAndNameAndIdNotIn(riskGradingUpdateResource.getCode(), riskGradingUpdateResource.getName(), id);
		if (isPresentNotificationProcessSetupByType.isPresent())
			throw new ValidateRecordException(environment.getProperty("typeAndProcess.unique"), "message");
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate business type*******************");
		validationService.validateBusinessPersonType(tenantId, riskGradingUpdateResource.getBusinessPersonTypeId(), riskGradingUpdateResource.getBusinessPersonTypeName(), ServiceEntity.BUSINESS_PERSON_TYPE);
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate industry type*******************");
		validationService.validateIndustryType(tenantId, riskGradingUpdateResource.getIndustryTypeId(), riskGradingUpdateResource.getIndustryTypeName(), ServiceEntity.INDUSTRY_TYPE);
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Update Risk grading*******************");
		RiskGrading riskGrading = updateRiskGrading(createdUser, businessType, riskGradingUpdateResource, id);
		
		if(riskGradingUpdateResource.getRiskGradingDetails() !=null && !riskGradingUpdateResource.getRiskGradingDetails().isEmpty()) {			
			Integer index=0;
			
			for(RiskGradingDetailUpdateResource rgd : riskGradingUpdateResource.getRiskGradingDetails()) {
				
				if(rgd != null && !rgd.getId().isEmpty()) {
					LoggerRequest.getInstance().logInfo("NotificationProcessSetup************update risk grading detail*******************");
					updateRiskGrading(createdUser, rgd);
					
				} 
				index++;
			}
		}
		
		return riskGrading.getId();
	}
	
	private RiskGrading updateRiskGrading(String createdUser,BusinessType businessType,RiskGradingUpdateResource riskGradingUpdateResource,Long id) {
		RiskGrading riskGrading =  riskGradingRepository.getOne(id);
		riskGrading.setBusinessPersonTypeId(Long.parseLong(riskGradingUpdateResource.getBusinessPersonTypeId()));
		riskGrading.setBusinessTypes(businessType);
		riskGrading.setIndustryTypeId(Long.parseLong(riskGradingUpdateResource.getIndustryTypeId()));
		riskGrading.setCode(riskGradingUpdateResource.getCode());
		riskGrading.setDescription(riskGradingUpdateResource.getDescription());
		riskGrading.setStatus(riskGradingUpdateResource.getStatus());
		riskGrading.setVersion(Long.parseLong(riskGradingUpdateResource.getVersion()));
		riskGrading.setModifiedUser(createdUser);
		riskGrading.setModifiedDate(getCreateOrModifyDate());
		riskGrading.setSyncTs(getCreateOrModifyDate());
		
		riskGrading = riskGradingRepository.saveAndFlush(riskGrading);
		return riskGrading;
	}
	
	private RiskGradingDetail updateRiskGrading(String createdUser,RiskGradingDetailUpdateResource riskGradingDetailUpdateResource) {
		RiskGradingDetail riskGradingDetails =  riskGradingDetailRepository.getOne(Long.parseLong(riskGradingDetailUpdateResource.getId()));
		
		riskGradingDetails.setGrading(riskGradingDetailUpdateResource.getGrading());
		riskGradingDetails.setFromScore(Long.parseLong(riskGradingDetailUpdateResource.getFromScore()));
		riskGradingDetails.setToScore(Long.parseLong(riskGradingDetailUpdateResource.getToScore()));
		riskGradingDetails.setShortCode(riskGradingDetailUpdateResource.getShortCode());
		riskGradingDetails.setStatus(riskGradingDetailUpdateResource.getStatus());
		riskGradingDetails.setVersion(Long.parseLong(riskGradingDetailUpdateResource.getVersion()));
		riskGradingDetails.setModifiedUser(createdUser);
		riskGradingDetails.setModifiedDate(getCreateOrModifyDate());
		riskGradingDetails.setSyncTs(getCreateOrModifyDate());
		riskGradingDetails = riskGradingDetailRepository.saveAndFlush(riskGradingDetails);
		return riskGradingDetails;
	
	}
}
