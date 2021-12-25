package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.DisbursementConditions;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.ConditionType;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.YesNo;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.DisbursementConditionsRepository;
import com.fusionx.lending.product.resources.DisbursementConditionsAddResource;
import com.fusionx.lending.product.resources.DisbursementConditionsUpdateResource;
import com.fusionx.lending.product.resources.PeriodResponse;
import com.fusionx.lending.product.service.DisbursementConditionsService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor = Exception.class)
public class DisbursementConditionsServiceImpl extends MessagePropertyBase implements DisbursementConditionsService{

	@Autowired
	private DisbursementConditionsRepository disbursementConditionsRepository;
	
	@Autowired
	private ValidationService validationService;
	
	@Override
	public List<DisbursementConditions> getAll() {
		return disbursementConditionsRepository.findAll();
	}

	@Override
	public Optional<DisbursementConditions> findById(Long id) {
		Optional<DisbursementConditions> isPresentDisbursementConditions= disbursementConditionsRepository.findById(id);
		if (isPresentDisbursementConditions.isPresent()) {
			return Optional.ofNullable(isPresentDisbursementConditions.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<DisbursementConditions> findByStatus(String status) {
		return disbursementConditionsRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public Optional<DisbursementConditions> findByCode(String code) {
		Optional<DisbursementConditions> isPresentDisbursementConditions= disbursementConditionsRepository.findByCode(code);
		if (isPresentDisbursementConditions.isPresent()) {
			return Optional.ofNullable(isPresentDisbursementConditions.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<DisbursementConditions> findByName(String name) {
		return disbursementConditionsRepository.findByNameContaining(name);
	}

	@Override
	public DisbursementConditions addDisbursementConditions(String tenantId,
			DisbursementConditionsAddResource disbursementConditionsAddResource) {
		
		PeriodResponse periodResponse = null;
		Optional<DisbursementConditions> isPresentDisbursementConditions = disbursementConditionsRepository.findByCode(disbursementConditionsAddResource.getCode());
        
        if (isPresentDisbursementConditions.isPresent()) 
        	throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.DISBURSEMENT_CONDITIONS);
        
        DisbursementConditions disbursementConditions = new DisbursementConditions();
        if(ConditionType.PERIOD.equals(ConditionType.valueOf(disbursementConditionsAddResource.getConditionType()))){
        	if(disbursementConditionsAddResource.getPeriodId() == null || disbursementConditionsAddResource.getPeriodName() == null || disbursementConditionsAddResource.getPeriodNumber()== null) {
        		throw new ValidateRecordException(environment.getProperty("conditionType.period"), "message");
        	}else {
        		periodResponse = validationService.validatePeriod(tenantId, disbursementConditionsAddResource.getPeriodId(), disbursementConditionsAddResource.getPeriodName(), EntityPoint.DISBURSEMENT_CONDITIONS);
        		disbursementConditions.setPeriodId(periodResponse!= null ? validationService.stringToLong(disbursementConditionsAddResource.getPeriodId()) : null);
		        disbursementConditions.setPeriodCode(periodResponse != null? periodResponse.getCode() : null);
		        disbursementConditions.setPeriodNumber(periodResponse!= null? Integer.valueOf(disbursementConditionsAddResource.getPeriodNumber()):null);
        	}
        	
        }
        
        if(ConditionType.AMOUNT.equals(ConditionType.valueOf(disbursementConditionsAddResource.getConditionType()))){
        	if(disbursementConditionsAddResource.getAmount() == null) {
        		throw new ValidateRecordException(environment.getProperty("conditionType.amount"), "message");
        	}else {
        		  disbursementConditions.setAmount(validationService.stringToBigDecimal(disbursementConditionsAddResource.getAmount()));
        	}
        }
        
        if(ConditionType.TEXUAL.equals(ConditionType.valueOf(disbursementConditionsAddResource.getConditionType()))){
        	if(disbursementConditionsAddResource.getTexual() == null) {
        		throw new ValidateRecordException(environment.getProperty("conditionType.texual"), "message");
        	}else {
        		disbursementConditions.setTexual(disbursementConditionsAddResource.getTexual());
        	}
        }
        
        if(ConditionType.INDICATOR.equals(ConditionType.valueOf(disbursementConditionsAddResource.getConditionType()))){
        	if(disbursementConditionsAddResource.getIndicatorFlag() == null) {
        		throw new ValidateRecordException(environment.getProperty("conditionType.indicator"), "message");
        	}else {
        		if(YesNo.NO.equals(disbursementConditionsAddResource.getIndicatorFlag())) {
        			throw new ValidateRecordException(environment.getProperty("indicator.value"), "message");
        		}
        	}
        }
        
        disbursementConditions.setTenantId(tenantId);
        disbursementConditions.setCode(disbursementConditionsAddResource.getCode());
        disbursementConditions.setName(disbursementConditionsAddResource.getName());
        disbursementConditions.setDescription(disbursementConditionsAddResource.getDescription());
        disbursementConditions.setStatus(CommonStatus.valueOf(disbursementConditionsAddResource.getStatus()));
        disbursementConditions.setConditionType(ConditionType.valueOf(disbursementConditionsAddResource.getConditionType()));
        disbursementConditions.setIndicatorFlag(YesNo.valueOf(disbursementConditionsAddResource.getIndicatorFlag()));
        disbursementConditions.setCreatedDate(validationService.getCreateOrModifyDate());
        disbursementConditions.setSyncTs(validationService.getCreateOrModifyDate());
        disbursementConditions.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        
        return disbursementConditionsRepository.save(disbursementConditions);
        
	}

	@Override
	public DisbursementConditions updateDisbursementConditions(String tenantId, Long id,
			DisbursementConditionsUpdateResource disbursementConditionsUpdateResource) {
		
		 Optional<DisbursementConditions> isPresentDisbursementConditions= disbursementConditionsRepository.findById(id);
		 PeriodResponse periodResponse = null;	
			if(isPresentDisbursementConditions.isPresent()) {
				
				Optional<DisbursementConditions> disbsmntConditions = disbursementConditionsRepository.findByCodeAndIdNotIn(disbursementConditionsUpdateResource.getCode(), id);
				if(disbsmntConditions.isPresent()) {
					throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.DISBURSEMENT_CONDITIONS);
				}
				
				if(!isPresentDisbursementConditions.get().getVersion().equals(Long.parseLong(disbursementConditionsUpdateResource.getVersion())))
					throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
				
				DisbursementConditions disbursementConditions = isPresentDisbursementConditions.get();
				
				  if(ConditionType.PERIOD.equals(ConditionType.valueOf(disbursementConditionsUpdateResource.getConditionType()))){
			        	if(disbursementConditionsUpdateResource.getPeriodId() == null || disbursementConditionsUpdateResource.getPeriodName() == null || disbursementConditionsUpdateResource.getPeriodNumber()== null) {
			        		throw new ValidateRecordException(environment.getProperty("conditionType.period"), "message");
			        	}
			        	
			        }
			        
			        if(ConditionType.AMOUNT.equals(ConditionType.valueOf(disbursementConditionsUpdateResource.getConditionType()))){
			        	if(disbursementConditionsUpdateResource.getAmount() == null) {
			        		throw new ValidateRecordException(environment.getProperty("conditionType.amount"), "message");
			        	}
			        }
			        
			        if(ConditionType.TEXUAL.equals(ConditionType.valueOf(disbursementConditionsUpdateResource.getConditionType()))){
			        	if(disbursementConditionsUpdateResource.getTexual() == null) {
			        		throw new ValidateRecordException(environment.getProperty("conditionType.texual"), "message");
			        	}
			        }
			        
			        if(ConditionType.INDICATOR.equals(ConditionType.valueOf(disbursementConditionsUpdateResource.getConditionType()))){
			        	if(disbursementConditionsUpdateResource.getIndicatorFlag() == null) {
			        		throw new ValidateRecordException(environment.getProperty("conditionType.indicator"), "message");
			        	}else {
			        		if(YesNo.NO.equals(disbursementConditionsUpdateResource.getIndicatorFlag())) {
			        			throw new ValidateRecordException(environment.getProperty("indicator.value"), "message");
			        		}
			        	}
			        }
				
				disbursementConditions.setTenantId(tenantId);
				disbursementConditions.setCode(disbursementConditionsUpdateResource.getCode());
		        disbursementConditions.setName(disbursementConditionsUpdateResource.getName());
		        disbursementConditions.setDescription(disbursementConditionsUpdateResource.getDescription());
		        disbursementConditions.setStatus(CommonStatus.valueOf(disbursementConditionsUpdateResource.getStatus()));
		        disbursementConditions.setConditionType(ConditionType.valueOf(disbursementConditionsUpdateResource.getConditionType()));
		        disbursementConditions.setPeriodId(periodResponse!= null ? validationService.stringToLong(disbursementConditionsUpdateResource.getPeriodId()) : null);
		        disbursementConditions.setPeriodCode(periodResponse != null? periodResponse.getCode() : null);
		        disbursementConditions.setPeriodNumber(periodResponse!= null? Integer.valueOf(disbursementConditionsUpdateResource.getPeriodNumber()):null);
		        
		        if(ConditionType.TEXUAL.equals(ConditionType.valueOf(disbursementConditionsUpdateResource.getConditionType()))){
		        	disbursementConditions.setTexual(disbursementConditionsUpdateResource.getTexual());
		        }else {
		        	disbursementConditions.setTexual(null);
		        }
		        if(ConditionType.AMOUNT.equals(ConditionType.valueOf(disbursementConditionsUpdateResource.getConditionType()))){
		        	disbursementConditions.setAmount(validationService.stringToBigDecimal(disbursementConditionsUpdateResource.getAmount()));
		        }else {
		        	disbursementConditions.setAmount(null);
		        }
		        disbursementConditions.setIndicatorFlag(YesNo.valueOf(disbursementConditionsUpdateResource.getIndicatorFlag()));
		        disbursementConditions.setModifiedDate(validationService.getCreateOrModifyDate());
		        disbursementConditions.setSyncTs(validationService.getCreateOrModifyDate());
		        disbursementConditions.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		        
		        return disbursementConditionsRepository.save(disbursementConditions);
	        
			}
			 else 
				throw new ValidateRecordException(environment.getProperty("record-not-found"),"message");
		}
	
}
