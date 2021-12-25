package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ApprovalLevel;
import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.domain.FieldSetup;
import com.fusionx.lending.origination.domain.MasterValuePaire;
import com.fusionx.lending.origination.domain.RiskGrading;
import com.fusionx.lending.origination.domain.RiskGradingDetail;
import com.fusionx.lending.origination.domain.RiskMainCriteria;
import com.fusionx.lending.origination.domain.RiskParameterTemplate;
import com.fusionx.lending.origination.domain.RiskSubCriteria;
import com.fusionx.lending.origination.domain.RiskTemplate;
import com.fusionx.lending.origination.domain.RiskTemplateDetail;
import com.fusionx.lending.origination.domain.RiskTemplateDetailValuePaire;
import com.fusionx.lending.origination.domain.RiskTemplateDetailVariance;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.IndicatorType;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.FieldSetupRepository;
import com.fusionx.lending.origination.repository.MasterValuePaireRepository;
import com.fusionx.lending.origination.repository.RiskGradingRepository;
import com.fusionx.lending.origination.repository.RiskParameterTemplateRepository;
import com.fusionx.lending.origination.repository.RiskTemplateDetailRepository;
import com.fusionx.lending.origination.repository.RiskTemplateDetailValuePaireRepository;
import com.fusionx.lending.origination.repository.RiskTemplateDetailVarianceRepository;
import com.fusionx.lending.origination.repository.RiskTemplateRepository;
import com.fusionx.lending.origination.resource.ApprovalLevelUpdateResource;
import com.fusionx.lending.origination.resource.FieldSetupAddResource;
import com.fusionx.lending.origination.resource.FieldSetupUpdateResource;
import com.fusionx.lending.origination.resource.RiskGradingAddResource;
import com.fusionx.lending.origination.resource.RiskGradingDetailAddResource;
import com.fusionx.lending.origination.resource.RiskGradingDetailUpdateResource;
import com.fusionx.lending.origination.resource.RiskGradingUpdateResource;
import com.fusionx.lending.origination.resource.RiskParaTempCalFeildAddResource;
import com.fusionx.lending.origination.resource.RiskParameterTemplateAddResource;
import com.fusionx.lending.origination.resource.RiskTemplateAddResource;
import com.fusionx.lending.origination.resource.RiskTemplateDetailAddResource;
import com.fusionx.lending.origination.resource.RiskTemplateDetailUpdateResource;
import com.fusionx.lending.origination.resource.RiskTemplateDetailsListAddResource;
import com.fusionx.lending.origination.resource.RiskTemplateDetailsListUpdateResource;
import com.fusionx.lending.origination.resource.RiskTemplateUpdateResource;
import com.fusionx.lending.origination.service.FieldSetupService;
import com.fusionx.lending.origination.service.RiskTemplateService;

/**
 * Credit risk template Service Implementation
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
public class RiskTemplateServiceImpl implements RiskTemplateService{

	@Autowired
	private RiskTemplateRepository riskTemplateRepository;
	
	@Autowired
	private RiskGradingRepository riskGradingRepository;
	
	@Autowired
	private RiskParameterTemplateRepository riskParameterTemplateRepository;
	
	@Autowired
	private RiskTemplateDetailRepository riskTemplateDetailRepository;
	
	@Autowired
	private RiskTemplateDetailVarianceRepository riskTemplateDetailVarianceRepository;
	
	@Autowired
	private RiskTemplateDetailValuePaireRepository riskTemplateDetailValuePaireRepository;
	
	@Autowired
	private MasterValuePaireRepository masterValuePaireRepository;
	
	@Autowired
	private Environment environment;
	
	@Override
	public List<RiskTemplate> findAll() {
		return riskTemplateRepository.findAll();
	}
	
	@Override
	public Optional<RiskTemplate> findById(Long id) {
		Optional<RiskTemplate> isPresentRiskTemplate = riskTemplateRepository.findById(id);
		if (isPresentRiskTemplate.isPresent()) {
			RiskTemplate riskTemplate = isPresentRiskTemplate.get();
			List<RiskTemplateDetail> riskTemplateDetails = riskTemplateDetailRepository.findByRiskTemplatesId(riskTemplate.getId());
			for(RiskTemplateDetail riskTemplateDetail : riskTemplateDetails) {
				List<RiskTemplateDetail> riskTemplateDetailsList = findByTemplateId(riskTemplateDetail.getRiskTemplateId());
				riskTemplate.setRiskTemplateDetailList(riskTemplateDetailsList);
			}
			return Optional.ofNullable(riskTemplate);
		} else {
			return Optional.empty();
		}
	}
	
	private List<RiskTemplateDetail> findByTemplateId(Long id) {
		List<RiskTemplateDetail> riskTemplateDetailList = new ArrayList<>();
		List<RiskTemplateDetail> riskTemplateDetails = riskTemplateDetailRepository.findByRiskTemplatesId(id);

		for(RiskTemplateDetail riskTemplateDetail : riskTemplateDetails) {
			List<RiskTemplateDetailValuePaire> riskTemplateDetailValuePaire = new ArrayList<>();
			//LoggerRequest.getInstance().logInfo("Risk template detail id " + riskTemplateDetail.getId().toString());
			riskTemplateDetailValuePaire = riskTemplateDetailValuePaireRepository.findByRiskTemplateDetailsId(riskTemplateDetail.getId());
			
			List<RiskTemplateDetailVariance> riskTemplateVariance = new ArrayList<>();
			riskTemplateVariance = riskTemplateDetailVarianceRepository.findByRiskTemplateDetailsId(riskTemplateDetail.getId());
			
			if(riskTemplateVariance != null && !riskTemplateVariance.isEmpty()) {
				riskTemplateDetail.setRiskTemplateDetailVariance(riskTemplateVariance);
			}
			else if(riskTemplateDetailValuePaire != null && !riskTemplateDetailValuePaire.isEmpty()) {
				riskTemplateDetail.setRiskTemplateDetailValuePaire(riskTemplateDetailValuePaire);
			}
			riskTemplateDetailList.add(riskTemplateDetail);
		}
			
		return riskTemplateDetailList;
	}


	@Override
	public List<RiskTemplate> findByStatus(String status) {
		List<RiskTemplate> isPresentRiskTemplate =  riskTemplateRepository.findByStatus(status);
		List<RiskTemplate> riskTemplateList = new ArrayList<>();
		List<RiskTemplateDetail> riskTemplateDetail = new ArrayList<>();
		for(RiskTemplate riskTemplate : isPresentRiskTemplate) {
			riskTemplateDetail = riskTemplateDetailRepository.findByRiskTemplatesId(riskTemplate.getId());
			for(RiskTemplateDetail riskTemplateDetails : riskTemplateDetail) {
				List<RiskTemplateDetail> riskTemplateDetailsList = findByTemplateId(riskTemplateDetails.getRiskTemplateId());
				riskTemplate.setRiskTemplateDetailList(riskTemplateDetailsList);
				riskTemplateList.add(riskTemplate);
			}
		}
		return riskTemplateList;
				
	}
	
	
	@Override
	public List<RiskTemplate> findByName(String name) {
		List<RiskTemplate> isPresentRiskTemplate = riskTemplateRepository.findByNameContaining(name);
		List<RiskTemplate> riskTemplateList = new ArrayList<>();
		List<RiskTemplateDetail> riskTemplateDetail = new ArrayList<>();
		for(RiskTemplate riskTemplate : isPresentRiskTemplate) {
			riskTemplateDetail = riskTemplateDetailRepository.findByRiskTemplatesId(riskTemplate.getId());
			for(RiskTemplateDetail riskTemplateDetails : riskTemplateDetail) {
				List<RiskTemplateDetail> riskTemplateDetailsList = findByTemplateId(riskTemplateDetails.getRiskTemplateId());
				riskTemplate.setRiskTemplateDetailList(riskTemplateDetailsList);
				riskTemplateList.add(riskTemplate);
			}
		}
		return riskTemplateList;
	}	

	
	@Override
	public Optional<RiskTemplate> findByCode(String code) {
		Optional<RiskTemplate> isPresentRiskTemplate = riskTemplateRepository.findByCode(code);
		if (isPresentRiskTemplate.isPresent()) {
			RiskTemplate riskTemplate = isPresentRiskTemplate.get();
			List<RiskTemplateDetail> riskTemplateDetails = riskTemplateDetailRepository.findByRiskTemplatesId(riskTemplate.getId());
			for(RiskTemplateDetail riskTemplateDetail : riskTemplateDetails) {
				List<RiskTemplateDetail> riskTemplateDetailsList = findByTemplateId(riskTemplateDetail.getRiskTemplateId());
				riskTemplate.setRiskTemplateDetailList(riskTemplateDetailsList);
			}
			return Optional.ofNullable(riskTemplate);
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public Long saveAndValidateRiskTemplate(String tenantId, String createdUser, RiskTemplateAddResource riskTemplateAddResource) {
			Calendar calendar = Calendar.getInstance();
	        java.util.Date now = calendar.getTime();
	        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
	        
	        LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Risk Grading*******************");
			RiskGrading riskGrading = setRiskGradingAndValidate(Long.parseLong(riskTemplateAddResource.getRiskGradingId()));
			
	        LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Name*******************");
			if(riskTemplateRepository.existsByName(riskTemplateAddResource.getName()))
				throw new ValidateRecordException(environment.getProperty("common.unique"), "name");
			
			LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Code*******************");
			if(riskTemplateRepository.existsByCode(riskTemplateAddResource.getCode()))
				throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
			
			LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Code length*******************");
			if(riskTemplateAddResource.getCode().length() != 4)
				throw new ValidateRecordException(environment.getProperty("code-length"),"Invalid code length.");
			
			LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Dupilcates*******************");
			if(riskTemplateRepository.existsByCodeAndName(riskTemplateAddResource.getCode(), riskTemplateAddResource.getName()))
				throw new ValidateRecordException(environment.getProperty("typeAndProcess.unique"), "message");
			
			LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Save risk grading*******************");
			RiskTemplate riskTemplate = saveRiskTemplate(tenantId,createdUser,riskGrading,riskTemplateAddResource);
	
			LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Risk template details*******************");
			if(riskTemplateAddResource.getRiskTemplateDetails()!=null && !riskTemplateAddResource.getRiskTemplateDetails().isEmpty()) {			
				
				for(RiskTemplateDetailAddResource riskTemplateDetails : riskTemplateAddResource.getRiskTemplateDetails()) {
					LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate risk parameter template*******************");
					RiskParameterTemplate riskParameterTemplate = setRiskParameterTemplateAndValidate(Long.parseLong(riskTemplateDetails.getRiskParameterTemplateId()));
					
					LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Save risk template details*******************");
					RiskTemplateDetail riskTemplateDetail = saveRiskTemplateDetail(tenantId, createdUser, riskParameterTemplate,riskTemplateDetails,riskTemplate);			
					
					LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Risk parameter Detail variance or value paire*******************");
					if(riskTemplateDetails.getRiskTemplateDetailsList()!=null && !riskTemplateDetails.getRiskTemplateDetailsList().isEmpty()) {			
						
						for(RiskTemplateDetailsListAddResource riskTemplateDetailsList : riskTemplateDetails.getRiskTemplateDetailsList()) {
							if(riskTemplateDetails.getIndicator().equalsIgnoreCase(IndicatorType.VALUE_PAIRE.toString())) {
								LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate with master value paire******************");
								MasterValuePaire masterValuePaire = setMasterValuePaireAndValidate(Long.parseLong(riskTemplateDetailsList.getValueId()), riskTemplateDetailsList.getValueCode(), riskTemplateDetailsList.getValueName());
								
								LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Save detail value paire******************");
								saveRiskTemplateDetailValuePaire(tenantId,createdUser,riskTemplateDetail,riskTemplateDetailsList);
							}
							else if(riskTemplateDetails.getIndicator().equalsIgnoreCase(IndicatorType.VARIANCE.toString())) {
								LoggerRequest.getInstance().logInfo("NotificationProcessSetup************save detail variance******************");
								saveRiskTemplateDetailVariance(tenantId,createdUser,riskTemplateDetail,riskTemplateDetailsList); 
							}
							else {
								throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "indicator");
							}
						}
					}																		    
				}
				
			}
			
			return riskTemplate.getId();
	}
	
	
	@Override
	public Long updateAndValidateRiskTemplate(String tenantId, String createdUser, Long id,RiskTemplateUpdateResource riskTemplateUpdateResource) {
		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Risk Grading*******************");
		RiskGrading riskGrading = setRiskGradingAndValidate(Long.parseLong(riskTemplateUpdateResource.getRiskGradingId()));
		
		Optional<RiskTemplate> isPresentRiskTemplate = riskTemplateRepository.findById(id);
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate version*******************");		
		if(!isPresentRiskTemplate.get().getVersion().equals(Long.parseLong(riskTemplateUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
		
		LoggerRequest.getInstance().logInfo("************************** Code Can Not Update *****************************");
        if (!isPresentRiskTemplate.get().getCode().equals(riskTemplateUpdateResource.getCode())) {
            throw new ValidateRecordException(environment.getProperty("common.code-update"), "code");
        }
		        		
//        LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Name*******************");
//		if(riskTemplateRepository.existsByName(riskTemplateUpdateResource.getName()))
//			throw new ValidateRecordException(environment.getProperty("common.unique"), "name");
				
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Update risk template*******************");
		RiskTemplate riskTemplate = updateRiskTemplate(tenantId,createdUser,riskGrading,riskTemplateUpdateResource,id);

		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Risk template details*******************");
		if(riskTemplateUpdateResource.getRiskTemplateDetails()!=null && !riskTemplateUpdateResource.getRiskTemplateDetails().isEmpty()) {			
			
			for(RiskTemplateDetailUpdateResource riskTemplateDetailsUpdate : riskTemplateUpdateResource.getRiskTemplateDetails()) {
				LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate risk parameter template*******************");
				RiskParameterTemplate riskParameterTemplate = setRiskParameterTemplateAndValidate(Long.parseLong(riskTemplateDetailsUpdate.getRiskParameterTemplateId()));
				
				LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Save risk template details*******************");
				RiskTemplateDetail riskTemplateDetail = updateRiskTemplateDetail(tenantId, createdUser, riskParameterTemplate,riskTemplateDetailsUpdate,riskTemplate);			
				
				LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Risk parameter Detail variance or value paire*******************");
				if(riskTemplateDetailsUpdate.getRiskTemplateDetailsList()!=null && !riskTemplateDetailsUpdate.getRiskTemplateDetailsList().isEmpty()) {			
					
					for(RiskTemplateDetailsListUpdateResource riskTemplateDetailsList : riskTemplateDetailsUpdate.getRiskTemplateDetailsList()) {
						if(riskTemplateDetailsUpdate.getIndicator().equals("VALUE PAIRE")) {
							LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate with master value paire******************");
							MasterValuePaire masterValuePaire = setMasterValuePaireAndValidate(Long.parseLong(riskTemplateDetailsList.getValueId()), riskTemplateDetailsList.getValueCode(), riskTemplateDetailsList.getValueName());
							
							LoggerRequest.getInstance().logInfo("NotificationProcessSetup************update detail value paire******************");
							updateRiskTemplateDetailValuePaire(tenantId,createdUser,riskTemplateDetail,riskTemplateDetailsList);
						}
						else if(riskTemplateDetailsUpdate.getIndicator().equals("VARIANCE")) {
							LoggerRequest.getInstance().logInfo("NotificationProcessSetup************update detail variance******************");
							updateRiskTemplateDetailVariance(tenantId,createdUser,riskTemplateDetail,riskTemplateDetailsList); 
						}
						else {
							throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "indicator");
						}
					}
				}																		    
			}
			
		}
		
		return riskTemplate.getId();
	}
	
	@Override
	public Boolean existsById(Long id) {
		return riskTemplateRepository.existsById(id);
	}
	
	private RiskGrading setRiskGradingAndValidate(Long riskGradingId) {
		RiskGrading riskGrading = null;
		Optional<RiskGrading> riskGradingOptional = riskGradingRepository.findByIdAndStatus(riskGradingId, CommonStatus.ACTIVE.toString());
		if (!riskGradingOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "RiskGradingId");
		} else {
			riskGrading = riskGradingOptional.get();
		}
		return riskGrading;
	}
	
	private RiskTemplate saveRiskTemplate(String tenantId, String createdUser,RiskGrading riskGrading,RiskTemplateAddResource riskTemplateAddResource) {
		RiskTemplate riskTemplate = new RiskTemplate();
		
		riskTemplate.setTenantId(tenantId);
		riskTemplate.setRiskGradings(riskGrading);
		riskTemplate.setCode(riskTemplateAddResource.getCode());
		riskTemplate.setName(riskTemplateAddResource.getName());
		riskTemplate.setDescription(riskTemplateAddResource.getDescription());
		riskTemplate.setStatus(riskTemplateAddResource.getStatus());
		riskTemplate.setSyncTs(getCreateOrModifyDate());
		riskTemplate.setCreatedDate(getCreateOrModifyDate());
		riskTemplate.setCreatedUser(createdUser);
		
		riskTemplate = riskTemplateRepository.saveAndFlush(riskTemplate);
		return riskTemplate;
		
	}
	
	private RiskTemplate updateRiskTemplate(String tenantId, String createdUser,RiskGrading riskGrading,RiskTemplateUpdateResource riskTemplateUpdateResource,Long id) {
		RiskTemplate riskTemplate = riskTemplateRepository.getOne(id);
		
		riskTemplate.setTenantId(tenantId);
		riskTemplate.setRiskGradings(riskGrading);
		riskTemplate.setName(riskTemplateUpdateResource.getName());
		riskTemplate.setDescription(riskTemplateUpdateResource.getDescription());
		riskTemplate.setStatus(riskTemplateUpdateResource.getStatus());
		riskTemplate.setSyncTs(getCreateOrModifyDate());
		riskTemplate.setVersion(Long.parseLong(riskTemplateUpdateResource.getVersion()));
		riskTemplate.setModifiedDate(getCreateOrModifyDate());
		riskTemplate.setModifiedUser(createdUser);
		
		riskTemplate = riskTemplateRepository.saveAndFlush(riskTemplate);
		return riskTemplate;
		
	}
	
	private RiskTemplateDetail saveRiskTemplateDetail(String tenantId, String createdUser,RiskParameterTemplate riskParameterTemplate,RiskTemplateDetailAddResource riskTemplateDetailAddResource, RiskTemplate riskTemplate) {
		RiskTemplateDetail riskTemplateDetail = new RiskTemplateDetail();
		
		riskTemplateDetail.setTenantId(tenantId);
		riskTemplateDetail.setIndicator(riskTemplateDetailAddResource.getIndicator());
		riskTemplateDetail.setWeightagePercentage(Double.valueOf(riskTemplateDetailAddResource.getWeightagePercentage()));
		riskTemplateDetail.setStatus(riskTemplateDetailAddResource.getStatus());
		riskTemplateDetail.setRiskParameterTemplates(riskParameterTemplate);
		riskTemplateDetail.setRiskTemplates(riskTemplate);
		riskTemplateDetail.setSyncTs(getCreateOrModifyDate());
		riskTemplateDetail.setCreatedDate(getCreateOrModifyDate());
		riskTemplateDetail.setCreatedUser(createdUser);
		
		riskTemplateDetail = riskTemplateDetailRepository.saveAndFlush(riskTemplateDetail);
		return riskTemplateDetail;
		
	}
	
	private RiskTemplateDetail updateRiskTemplateDetail(String tenantId, String createdUser,RiskParameterTemplate riskParameterTemplate,RiskTemplateDetailUpdateResource riskTemplateDetailUpdateResource, RiskTemplate riskTemplate) {
		RiskTemplateDetail riskTemplateDetail = riskTemplateDetailRepository.getOne(Long.parseLong(riskTemplateDetailUpdateResource.getId()));
		
		riskTemplateDetail.setTenantId(tenantId);
		riskTemplateDetail.setIndicator(riskTemplateDetailUpdateResource.getIndicator());
		riskTemplateDetail.setWeightagePercentage(Double.valueOf(riskTemplateDetailUpdateResource.getWeightagePercentage()));
		riskTemplateDetail.setStatus(riskTemplateDetailUpdateResource.getStatus());
		riskTemplateDetail.setRiskParameterTemplates(riskParameterTemplate);
		riskTemplateDetail.setRiskTemplates(riskTemplate);
		riskTemplateDetail.setVersion(Long.parseLong(riskTemplateDetailUpdateResource.getVersion()));
		riskTemplateDetail.setSyncTs(getCreateOrModifyDate());
		riskTemplateDetail.setModifiedDate(getCreateOrModifyDate());
		riskTemplateDetail.setModifiedUser(createdUser);
		
		riskTemplateDetail = riskTemplateDetailRepository.saveAndFlush(riskTemplateDetail);
		return riskTemplateDetail;
		
	}
	
	private RiskTemplateDetailVariance saveRiskTemplateDetailVariance(String tenantId,String createdUser,RiskTemplateDetail riskTemplateDetail, RiskTemplateDetailsListAddResource riskTemplateDetailsListAddResource) {
		RiskTemplateDetailVariance riskTemplateDetailVariance = new RiskTemplateDetailVariance();
		
		riskTemplateDetailVariance.setTenantId(tenantId);
		riskTemplateDetailVariance.setFromValue(Long.parseLong(riskTemplateDetailsListAddResource.getFromValue()));
		riskTemplateDetailVariance.setToValue(Long.parseLong(riskTemplateDetailsListAddResource.getToValue()));
		riskTemplateDetailVariance.setScore(Long.parseLong(riskTemplateDetailsListAddResource.getScore()));
		riskTemplateDetailVariance.setStatus(riskTemplateDetailsListAddResource.getStatus());
		riskTemplateDetailVariance.setRiskTemplateDetails(riskTemplateDetail);
		riskTemplateDetailVariance.setSyncTs(getCreateOrModifyDate());
		riskTemplateDetailVariance.setCreatedDate(getCreateOrModifyDate());
		riskTemplateDetailVariance.setCreatedUser(createdUser);
		
		riskTemplateDetailVariance = riskTemplateDetailVarianceRepository.saveAndFlush(riskTemplateDetailVariance);
		return riskTemplateDetailVariance;
		
	}
	
	private RiskTemplateDetailVariance updateRiskTemplateDetailVariance(String tenantId,String createdUser,RiskTemplateDetail riskTemplateDetail, RiskTemplateDetailsListUpdateResource riskTemplateDetailsListUpdateResource) {
		RiskTemplateDetailVariance riskTemplateDetailVariance = riskTemplateDetailVarianceRepository.getOne(Long.parseLong(riskTemplateDetailsListUpdateResource.getId()));
		
		riskTemplateDetailVariance.setTenantId(tenantId);
		riskTemplateDetailVariance.setFromValue(Long.parseLong(riskTemplateDetailsListUpdateResource.getFromValue()));
		riskTemplateDetailVariance.setToValue(Long.parseLong(riskTemplateDetailsListUpdateResource.getToValue()));
		riskTemplateDetailVariance.setScore(Long.parseLong(riskTemplateDetailsListUpdateResource.getScore()));
		riskTemplateDetailVariance.setStatus(riskTemplateDetailsListUpdateResource.getStatus());
		riskTemplateDetailVariance.setVersion(Long.parseLong(riskTemplateDetailsListUpdateResource.getVersion()));
		riskTemplateDetailVariance.setRiskTemplateDetails(riskTemplateDetail);
		riskTemplateDetailVariance.setSyncTs(getCreateOrModifyDate());
		riskTemplateDetailVariance.setModifiedDate(getCreateOrModifyDate());
		riskTemplateDetailVariance.setModifiedUser(createdUser);
		
		riskTemplateDetailVariance = riskTemplateDetailVarianceRepository.saveAndFlush(riskTemplateDetailVariance);
		return riskTemplateDetailVariance;
		
	}
	
	private RiskTemplateDetailValuePaire saveRiskTemplateDetailValuePaire(String tenantId,String createdUser,RiskTemplateDetail riskTemplateDetail, RiskTemplateDetailsListAddResource riskTemplateDetailsListAddResource) {
		RiskTemplateDetailValuePaire riskTemplateDetailValuePaire = new RiskTemplateDetailValuePaire();
		
		riskTemplateDetailValuePaire.setTenantId(tenantId);
		riskTemplateDetailValuePaire.setValueId(Long.parseLong(riskTemplateDetailsListAddResource.getValueId()));
		riskTemplateDetailValuePaire.setScore(Long.parseLong(riskTemplateDetailsListAddResource.getScore()));
		riskTemplateDetailValuePaire.setStatus(riskTemplateDetailsListAddResource.getStatus());
		riskTemplateDetailValuePaire.setRiskTemplateDetails(riskTemplateDetail);
		riskTemplateDetailValuePaire.setSyncTs(getCreateOrModifyDate());
		riskTemplateDetailValuePaire.setCreatedDate(getCreateOrModifyDate());
		riskTemplateDetailValuePaire.setCreatedUser(createdUser);
		
		riskTemplateDetailValuePaire = riskTemplateDetailValuePaireRepository.saveAndFlush(riskTemplateDetailValuePaire);
		return riskTemplateDetailValuePaire;
		
	}
	
	private RiskTemplateDetailValuePaire updateRiskTemplateDetailValuePaire(String tenantId,String createdUser,RiskTemplateDetail riskTemplateDetail, RiskTemplateDetailsListUpdateResource riskTemplateDetailsListUpdateResource) {
		RiskTemplateDetailValuePaire riskTemplateDetailValuePaire =riskTemplateDetailValuePaireRepository.getOne(Long.parseLong(riskTemplateDetailsListUpdateResource.getId()));
		
		riskTemplateDetailValuePaire.setTenantId(tenantId);
		riskTemplateDetailValuePaire.setValueId(Long.parseLong(riskTemplateDetailsListUpdateResource.getValueId()));
		riskTemplateDetailValuePaire.setScore(Long.parseLong(riskTemplateDetailsListUpdateResource.getScore()));
		riskTemplateDetailValuePaire.setStatus(riskTemplateDetailsListUpdateResource.getStatus());
		riskTemplateDetailValuePaire.setRiskTemplateDetails(riskTemplateDetail);
		riskTemplateDetailValuePaire.setVersion(Long.parseLong(riskTemplateDetailsListUpdateResource.getVersion()));
		riskTemplateDetailValuePaire.setSyncTs(getCreateOrModifyDate());
		riskTemplateDetailValuePaire.setModifiedDate(getCreateOrModifyDate());
		riskTemplateDetailValuePaire.setModifiedUser(createdUser);
		
		riskTemplateDetailValuePaire = riskTemplateDetailValuePaireRepository.saveAndFlush(riskTemplateDetailValuePaire);
		return riskTemplateDetailValuePaire;
		
	}
	
	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}
	
	private RiskParameterTemplate setRiskParameterTemplateAndValidate(Long riskParameterTemplateId) {
		RiskParameterTemplate riskParameterTemplate = null;
		Optional<RiskParameterTemplate> riskParameterTemplateOptional = riskParameterTemplateRepository.findByIdAndStatus(riskParameterTemplateId, CommonStatus.ACTIVE.toString());
		if (!riskParameterTemplateOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "riskParameterTemplateId");
		} else {
			riskParameterTemplate = riskParameterTemplateOptional.get();
		}
		return riskParameterTemplate;
	}
	
	private MasterValuePaire setMasterValuePaireAndValidate(Long id, String code, String name) {
		MasterValuePaire masterValuePaire = null;
		Optional<MasterValuePaire> masterValuePaireOptional = masterValuePaireRepository.findByIdAndCodeAndNameAndStatus(id,code, name, CommonStatus.ACTIVE.toString());
		if (!masterValuePaireOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "ValuePaire");
		} else {
			masterValuePaire = masterValuePaireOptional.get();
		}
		return masterValuePaire;
	}
		
}
