package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.domain.FieldSetup;
import com.fusionx.lending.origination.domain.RiskGrading;
import com.fusionx.lending.origination.domain.RiskGradingDetail;
import com.fusionx.lending.origination.domain.RiskMainCriteria;
import com.fusionx.lending.origination.domain.RiskParaTempCalFeild;
import com.fusionx.lending.origination.domain.RiskParameterTemplate;
import com.fusionx.lending.origination.domain.RiskSubCriteria;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.FieldSetupRepository;
import com.fusionx.lending.origination.repository.RiskMainCriteriaRepository;
import com.fusionx.lending.origination.repository.RiskParaTempCalFeildRepository;
import com.fusionx.lending.origination.repository.RiskParameterTemplateRepository;
import com.fusionx.lending.origination.repository.RiskSubCriteriaRepository;
import com.fusionx.lending.origination.resource.BusinessPersonType;
import com.fusionx.lending.origination.resource.IndustryType;
import com.fusionx.lending.origination.resource.RiskGradingAddResource;
import com.fusionx.lending.origination.resource.RiskGradingDetailAddResource;
import com.fusionx.lending.origination.resource.RiskParaTempCalFeildAddResource;
import com.fusionx.lending.origination.resource.RiskParaTempCalFeildUpdateResource;
import com.fusionx.lending.origination.resource.RiskParameterTemplateAddResource;
import com.fusionx.lending.origination.resource.RiskParameterTemplateUpdateResource;
import com.fusionx.lending.origination.service.RiskParameterTemplateService;
import com.google.protobuf.Field;

/**
 * Risk Parameter Template Service Implementation
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
public class RiskParameterTemplateServiceImpl implements RiskParameterTemplateService{

	@Autowired
	private Environment environment;
	
	@Autowired 
	private RiskParameterTemplateRepository riskParameterTemplateRepository;
	
	@Autowired 
	private RiskParaTempCalFeildRepository riskParaTempCalFeildRepository;
	
	@Autowired 
	private RiskMainCriteriaRepository riskMainCriteriaRepository;
	
	@Autowired 
	private RiskSubCriteriaRepository riskSubCriteriaRepository;
	
	@Autowired 
	private FieldSetupRepository fieldSetupRepository;
	
	@Override
	public List<RiskParameterTemplate> findAll() {
		return riskParameterTemplateRepository.findAll();
	}
	
	@Override
	public Optional<RiskParameterTemplate> findById(Long id) {
		Optional<RiskParameterTemplate> isPresentRiskParameterTemp = riskParameterTemplateRepository.findById(id);
		if (isPresentRiskParameterTemp.isPresent()) {
			RiskParameterTemplate riskParameterTemplate = isPresentRiskParameterTemp.get();
			List<RiskParaTempCalFeild> riskParaTempCalFeild = new ArrayList<>();
			riskParaTempCalFeild = riskParaTempCalFeildRepository.findByRiskParameterTemplatesId(riskParameterTemplate.getId());
			riskParameterTemplate.setRiskParaTempCalFeild(riskParaTempCalFeild);
			return isPresentRiskParameterTemp;
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public List<RiskParameterTemplate> findByStatus(String status) {
		List<RiskParameterTemplate> isPresentRiskParameterTemp =  riskParameterTemplateRepository.findByStatus(status);
		List<RiskParameterTemplate> paraList = new ArrayList<>();
		List<RiskParaTempCalFeild> calFieldList = new ArrayList<>();
		for(RiskParameterTemplate riskParameterTemplate : isPresentRiskParameterTemp) {
			calFieldList = riskParaTempCalFeildRepository.findByRiskParameterTemplatesId(riskParameterTemplate.getId());
			riskParameterTemplate.setRiskParaTempCalFeild(calFieldList);
			paraList.add(riskParameterTemplate);
		}
		return paraList;
	}
	
	@Override
	public List<RiskParameterTemplate> findByName(String name) {
		List<RiskParameterTemplate> isPresentRiskParameterTemp = riskParameterTemplateRepository.findByNameContaining(name);
		List<RiskParameterTemplate> paraList = new ArrayList<>();
		List<RiskParaTempCalFeild> calFieldList = new ArrayList<>();
		for(RiskParameterTemplate riskParameterTemplate : isPresentRiskParameterTemp) {
			calFieldList = riskParaTempCalFeildRepository.findByRiskParameterTemplatesId(riskParameterTemplate.getId());
			riskParameterTemplate.setRiskParaTempCalFeild(calFieldList);
			paraList.add(riskParameterTemplate);
		}
		return paraList;
	}

	@Override
	public Optional<RiskParameterTemplate> findByCode(String code) {
		Optional<RiskParameterTemplate> isPresentRiskParameterTemp = riskParameterTemplateRepository.findByCode(code);
		if (isPresentRiskParameterTemp.isPresent()) {
			RiskParameterTemplate riskParameterTemplate = isPresentRiskParameterTemp.get();
			List<RiskParaTempCalFeild> riskParaTempCalFeild = new ArrayList<>();
			riskParaTempCalFeild = riskParaTempCalFeildRepository.findByRiskParameterTemplatesId(riskParameterTemplate.getId());
			riskParameterTemplate.setRiskParaTempCalFeild(riskParaTempCalFeild);
			return isPresentRiskParameterTemp;
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public Boolean existsById(Long id) {
		return riskParameterTemplateRepository.existsById(id);
	}
	
	
	@Override
	public Long saveAndValidateRiskParameterTemplate(String tenantId, String createdUser, RiskParameterTemplateAddResource riskParameterTemplateAddResource) {
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Risk Main Criteria*******************");
		RiskMainCriteria riskMainCriteria = setMainCriteriaAndValidate(Long.parseLong(riskParameterTemplateAddResource.getRiskMainCriteriaId()));
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Risk Sub Criteria*******************");
		RiskSubCriteria riskSubCriteria = setSubCriteriaAndValidate(Long.parseLong(riskParameterTemplateAddResource.getRiskSubCriteriaId()));	
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Code*******************");
		if(riskParameterTemplateRepository.existsByCode(riskParameterTemplateAddResource.getCode()))
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Code length*******************");
		if(riskParameterTemplateAddResource.getCode().length() != 4)
			//throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
			throw new ValidateRecordException(environment.getProperty("code-length"),"Invalid code length.");
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Dupilcates*******************");
		if(riskParameterTemplateRepository.existsByCodeAndName(riskParameterTemplateAddResource.getCode(), riskParameterTemplateAddResource.getName()))
			throw new ValidateRecordException(environment.getProperty("typeAndProcess.unique"), "message");
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Save risk parameter template*******************");
		RiskParameterTemplate riskParameterTemplate = saveRiskParameterTemplate(tenantId,createdUser,riskMainCriteria,riskSubCriteria,riskParameterTemplateAddResource);
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Risk para cal field*******************");
		if(riskParameterTemplateAddResource.getRiskParaTempCalFeild()!=null && !riskParameterTemplateAddResource.getRiskParaTempCalFeild().isEmpty()) {			
			
			for(RiskParaTempCalFeildAddResource riskParaTempCalFeildAddResource : riskParameterTemplateAddResource.getRiskParaTempCalFeild()) {
				LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Field setup*******************");
				FieldSetup fieldSetup = setFieldSetupAndValidate(Long.parseLong(riskParaTempCalFeildAddResource.getFieldSetupId()));
				
				LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Save risk para cal field*******************");
					saveRiskParaTempCalField(tenantId, createdUser, fieldSetup,riskParameterTemplate,riskParaTempCalFeildAddResource);			
			}
			
		}
		
		return riskParameterTemplate.getId();
	}
	
	
	private RiskMainCriteria setMainCriteriaAndValidate(Long mainCriteriaId) {
		RiskMainCriteria riskMainCriteria = null;
		Optional<RiskMainCriteria> riskMainCriteriaOptional = riskMainCriteriaRepository.findByIdAndStatus(mainCriteriaId, CommonStatus.ACTIVE.toString());
		if (!riskMainCriteriaOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "mainCriteriaId");
		} else {
			riskMainCriteria = riskMainCriteriaOptional.get();
		}
		return riskMainCriteria;
	}
	
	private RiskSubCriteria setSubCriteriaAndValidate(Long subCriteriaId) {
		RiskSubCriteria riskSubCriteria = null;
		Optional<RiskSubCriteria> riskSubCriteriaOptional = riskSubCriteriaRepository.findByIdAndStatus(subCriteriaId, CommonStatus.ACTIVE.toString());
		if (!riskSubCriteriaOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "subCriteriaId");
		} else {
			riskSubCriteria = riskSubCriteriaOptional.get();
		}
		return riskSubCriteria;
	}
	
	private FieldSetup setFieldSetupAndValidate(Long fieldSetupId) {
		FieldSetup fieldSetup = null;
		Optional<FieldSetup> fieldSetupOptional = fieldSetupRepository.findByIdAndStatus(fieldSetupId, CommonStatus.ACTIVE.toString());
		if (!fieldSetupOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "feildSetupId");
		} else {
			fieldSetup = fieldSetupOptional.get();
		}
		return fieldSetup;
	}
	
	private RiskParameterTemplate saveRiskParameterTemplate(String tenantId, String createdUser,RiskMainCriteria riskMainCriteria,RiskSubCriteria riskSubCriteria,RiskParameterTemplateAddResource riskParameterTemplateAddResource) {
		RiskParameterTemplate riskParameterTemplate =  new RiskParameterTemplate();
		riskParameterTemplate.setTenantId(tenantId);
		riskParameterTemplate.setCode(riskParameterTemplateAddResource.getCode());
		riskParameterTemplate.setName(riskParameterTemplateAddResource.getName());
		riskParameterTemplate.setDescription(riskParameterTemplateAddResource.getDescription());
		riskParameterTemplate.setCalculationMethod(riskParameterTemplateAddResource.getCalculationMethod());
		riskParameterTemplate.setEquation(riskParameterTemplateAddResource.getEquation());
		riskParameterTemplate.setRiskMainCriterias(riskMainCriteria);
		riskParameterTemplate.setRiskSubCriterias(riskSubCriteria);
		riskParameterTemplate.setStatus(riskParameterTemplateAddResource.getStatus());
		riskParameterTemplate.setCreatedUser(createdUser);
		riskParameterTemplate.setCreatedDate(getCreateOrModifyDate());
		riskParameterTemplate.setSyncTs(getCreateOrModifyDate());
		
		riskParameterTemplate = riskParameterTemplateRepository.saveAndFlush(riskParameterTemplate);
		return riskParameterTemplate;
		
	}
	
	private RiskParaTempCalFeild saveRiskParaTempCalField(String tenantId, String createdUser,FieldSetup fieldSetup,RiskParameterTemplate riskParameterTemplate,RiskParaTempCalFeildAddResource riskParaTempCalFeildAddResource) {
		RiskParaTempCalFeild riskParaTempCalFeil =  new RiskParaTempCalFeild();
		riskParaTempCalFeil.setTenantId(tenantId);
		riskParaTempCalFeil.setRiskParameterTemplates(riskParameterTemplate);
		riskParaTempCalFeil.setFieldSetups(fieldSetup);
		riskParaTempCalFeil.setStatus(riskParaTempCalFeildAddResource.getStatus());
		riskParaTempCalFeil.setCreatedUser(createdUser);
		riskParaTempCalFeil.setCreatedDate(getCreateOrModifyDate());
		riskParaTempCalFeil.setSyncTs(getCreateOrModifyDate());
		
		riskParaTempCalFeil = riskParaTempCalFeildRepository.saveAndFlush(riskParaTempCalFeil);
		return riskParaTempCalFeil;
		
	}
	
	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}
	
	@Override
	public Long updateAndValidateRiskParameterTemplate(String tenantId, String createdUser, Long id,RiskParameterTemplateUpdateResource riskParameterTemplateUpdateResource) {
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Risk Main Criteria*******************");
		RiskMainCriteria riskMainCriteria = setMainCriteriaAndValidate(Long.parseLong(riskParameterTemplateUpdateResource.getRiskMainCriteriaId()));
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Risk Sub Criteria*******************");
		RiskSubCriteria riskSubCriteria = setSubCriteriaAndValidate(Long.parseLong(riskParameterTemplateUpdateResource.getRiskSubCriteriaId()));			
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************update risk parameter template*******************");
		RiskParameterTemplate riskParameterTemplate = updateRiskParameterTemplate(tenantId,createdUser,riskMainCriteria,riskSubCriteria,riskParameterTemplateUpdateResource, id);
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Risk para cal field*******************");
		if(riskParameterTemplateUpdateResource.getRiskParaTempCalFeild()!=null && !riskParameterTemplateUpdateResource.getRiskParaTempCalFeild().isEmpty()) {			
			
			for(RiskParaTempCalFeildUpdateResource riskParaTempCalFeildUpdateResource : riskParameterTemplateUpdateResource.getRiskParaTempCalFeild()) {
				LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate Field setup*******************");
				FieldSetup fieldSetup = setFieldSetupAndValidate(Long.parseLong(riskParaTempCalFeildUpdateResource.getFieldSetupId()));
				
				LoggerRequest.getInstance().logInfo("NotificationProcessSetup************update risk para cal field*******************");
				updateRiskParameterTempCalField(tenantId, createdUser, fieldSetup,riskParameterTemplate,riskParaTempCalFeildUpdateResource);			
			}
			
		}
		
		return riskParameterTemplate.getId();
	}
	
	private RiskParameterTemplate updateRiskParameterTemplate(String tenantId, String createdUser,RiskMainCriteria riskMainCriteria,RiskSubCriteria riskSubCriteria,RiskParameterTemplateUpdateResource riskParameterTemplateUpdateResource,Long id) {
		RiskParameterTemplate riskParameterTemplate =  riskParameterTemplateRepository.getOne(id);
		riskParameterTemplate.setTenantId(tenantId);
		riskParameterTemplate.setName(riskParameterTemplateUpdateResource.getName());
		riskParameterTemplate.setDescription(riskParameterTemplateUpdateResource.getDescription());
		riskParameterTemplate.setCalculationMethod(riskParameterTemplateUpdateResource.getCalculationMethod());
		riskParameterTemplate.setEquation(riskParameterTemplateUpdateResource.getEquation());
		riskParameterTemplate.setRiskMainCriterias(riskMainCriteria);
		riskParameterTemplate.setRiskSubCriterias(riskSubCriteria);
		riskParameterTemplate.setStatus(riskParameterTemplateUpdateResource.getStatus());
		riskParameterTemplate.setModifiedUser(createdUser);
		riskParameterTemplate.setModifiedDate(getCreateOrModifyDate());
		riskParameterTemplate.setVersion(Long.parseLong(riskParameterTemplateUpdateResource.getVersion()));
		riskParameterTemplate.setSyncTs(getCreateOrModifyDate());
		
		riskParameterTemplate = riskParameterTemplateRepository.saveAndFlush(riskParameterTemplate);
		return riskParameterTemplate;
		
	}
	
	private RiskParaTempCalFeild updateRiskParameterTempCalField(String tenantId, String createdUser,FieldSetup fieldSetup,RiskParameterTemplate riskParameterTemplate,RiskParaTempCalFeildUpdateResource riskParaTempCalFeildUpdateResource) {
		RiskParaTempCalFeild riskParaTempCalFeild =  riskParaTempCalFeildRepository.getOne(Long.parseLong(riskParaTempCalFeildUpdateResource.getId()));
		
		riskParaTempCalFeild.setTenantId(tenantId);
		riskParaTempCalFeild.setRiskParameterTemplates(riskParameterTemplate);
		riskParaTempCalFeild.setFieldSetups(fieldSetup);
		riskParaTempCalFeild.setStatus(riskParaTempCalFeildUpdateResource.getStatus());
		riskParaTempCalFeild.setModifiedUser(createdUser);
		riskParaTempCalFeild.setModifiedDate(getCreateOrModifyDate());
		riskParaTempCalFeild.setSyncTs(getCreateOrModifyDate());
		
		riskParaTempCalFeild = riskParaTempCalFeildRepository.saveAndFlush(riskParaTempCalFeild);
		return riskParaTempCalFeild;
		
	}
}
