package com.fusionx.lending.product.service.impl;
import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import com.fusionx.lending.product.domain.FeatureBenefitEligibilityType;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.domain.FeatureBenefitEligibility;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.OtherException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.FeatureBenefitEligibilityRepository;
import com.fusionx.lending.product.resources.AddBaseEligibilityRequest;
import com.fusionx.lending.product.resources.PeriodResponse;
import com.fusionx.lending.product.resources.UpdateBaseEligibilityRequest;
import com.fusionx.lending.product.service.FeatureBenefEligiHistoryService;
import com.fusionx.lending.product.service.FeatureBenefitEligibilityService;
import com.fusionx.lending.product.service.ValidationService;
import com.fusionx.lending.product.service.FeatureBenefitEligibilityTypeService;

/**
 * feature benefit eligibility Service - Domain entity
 * @author 	Sanatha
 * @Date    24-JUN-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-JUN-2021   					     Sanatha      Initial development.
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class FeatureBenefitEligibilityServiceImpl implements FeatureBenefitEligibilityService {

	@Autowired
	private FeatureBenefitEligibilityRepository repo;
		
	@Autowired
	private FeatureBenefitEligibilityTypeService  featureBenefitEligiTypeService;
	
	@Autowired
	private ValidationService validationService;
	
   
    @Autowired
	private FeatureBenefEligiHistoryService featureBenefEligiHistoryService;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private Environment environment;

	@Override
	public boolean exists(long id) {
		try {
			return repo.existsById(id);
		} finally {

		}
	}
	private Long stringToLong(String str) {
		try {
			Long val = Long.parseLong(str);
			return val;
		}
		catch(Exception e) {
			return null;
		}
	}

	private BigDecimal stringToBigDecimal(String str) {
		try {
			BigDecimal val = new BigDecimal(str);
			return val;
		}
		catch(Exception e) {
			return null;
		}
	}
	
	@Override
	public FeatureBenefitEligibility addFeatureBenefitEligibility(String tenantId,AddBaseEligibilityRequest addBaseEligibilityRequest) {
			Calendar calendar = Calendar.getInstance();
			java.util.Date now = calendar.getTime();
			java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
			PeriodResponse period;
			
			FeatureBenefitEligibility newFeatureBenefitEligibility = new FeatureBenefitEligibility();
			
			/*---------------------------------------Eligibility Type Validation----------------------------------------------------------------*/
			Optional<FeatureBenefitEligibilityType> featureBenefitEligiType = featureBenefitEligiTypeService.getById(Long.parseLong(addBaseEligibilityRequest.getTypeId()));
	
			if (featureBenefitEligiType.isPresent()) {		
	
				if (!(featureBenefitEligiType.get().getName().equals(addBaseEligibilityRequest.getTypeName())))
					throw new ValidateRecordException(environment.getProperty("typeId.invalid"),"typeId");
	 		} 
	 		else {
	 			throw new ValidateRecordException(environment.getProperty("typeId.invalid"),"typeId");
	 		}
				
			Boolean  featureBenefitEligiTypeEsists = repo.existsByFeatureBenefitEligiTypeId(Long.parseLong(addBaseEligibilityRequest.getTypeId()));

            if (featureBenefitEligiTypeEsists!= null && featureBenefitEligiTypeEsists) {
            	throw new ValidateRecordException(environment.getProperty("typeId.duplicate"),"typeId");
            }
				
			
		/*---------------------------------Period Validation-------------------------------------------------------*/
			if (!(addBaseEligibilityRequest.getPeriodId()==null||addBaseEligibilityRequest.getPeriodId().isEmpty())) {
				
				if ((addBaseEligibilityRequest.getPeriod()==null||addBaseEligibilityRequest.getPeriod().isEmpty()))
			    	throw new ValidateRecordException(environment.getProperty("period.invalid"),"period");
			
				period = validationService.validatePeriod(tenantId, addBaseEligibilityRequest.getPeriodId(), addBaseEligibilityRequest.getPeriod(), EntityPoint.FEATURE_BENEFIT_ELIGIBILITY_TYPE);
				newFeatureBenefitEligibility.setPeriodId(stringToLong(addBaseEligibilityRequest.getPeriodId()));
				newFeatureBenefitEligibility.setPeriod(period.getName());
				
			}
			
			
			/*------------------------------------------Validate Indicator-----------------------------------------------------*/
			if ((addBaseEligibilityRequest.getIndicator() != null && !addBaseEligibilityRequest.getIndicator().isEmpty())) {
				if (addBaseEligibilityRequest.getIndicator().toUpperCase().equals("FALSE")) {

					if (addBaseEligibilityRequest.getAmount() == null
							|| addBaseEligibilityRequest.getAmount().isEmpty()
							|| stringToBigDecimal(addBaseEligibilityRequest.getAmount()).compareTo(BigDecimal.ZERO) <= 0) {

						throw new ValidateRecordException(environment.getProperty("amount.invalid"), "amount");
					}
					
					if (addBaseEligibilityRequest.getTexual() != null
							&& !addBaseEligibilityRequest.getTexual().isEmpty()) {
						
						throw new ValidateRecordException(environment.getProperty("textual-not-required"), "textual");
					}
					
				} else {
					
					if (addBaseEligibilityRequest.getAmount() != null
							&& !addBaseEligibilityRequest.getAmount().isEmpty()
							&&  stringToBigDecimal(addBaseEligibilityRequest.getAmount()).compareTo(BigDecimal.ZERO) > 0) {
						
						throw new ValidateRecordException(environment.getProperty("amount-not-required"), "amount");
					}
					
					if (addBaseEligibilityRequest.getTexual() == null
							|| addBaseEligibilityRequest.getTexual().isEmpty()) {

						throw new ValidateRecordException(environment.getProperty("texual.invalid"), "textual");
					}
				}

			}
			
		/*----------------------------------------------------------------------------------------------------------------*/
			newFeatureBenefitEligibility.setAmount(stringToBigDecimal(addBaseEligibilityRequest.getAmount()));
			newFeatureBenefitEligibility.setDescription(addBaseEligibilityRequest.getDescription());
			newFeatureBenefitEligibility.setIndicator(Boolean.parseBoolean(addBaseEligibilityRequest.getIndicator()));
			newFeatureBenefitEligibility.setName(addBaseEligibilityRequest.getName());
			newFeatureBenefitEligibility.setFeatureBenefitEligiType(featureBenefitEligiType.get());
			newFeatureBenefitEligibility.setTexual(addBaseEligibilityRequest.getTexual());
			newFeatureBenefitEligibility.setCreatedDate(currentTimestamp);
			newFeatureBenefitEligibility.setCreatedUser(addBaseEligibilityRequest.getCreatedUser());
			newFeatureBenefitEligibility.setStatus(addBaseEligibilityRequest.getStatus());
			//newFeatureBenefitEligibility.setVersion(stringToLong("0"));
			newFeatureBenefitEligibility.setTenantId(tenantId);
			newFeatureBenefitEligibility.setCode(addBaseEligibilityRequest.getCode());
			newFeatureBenefitEligibility.setSyncTs(currentTimestamp);
			
			if (repo.existsByCode(addBaseEligibilityRequest.getCode()))
				throw new ValidateRecordException(environment.getProperty("common.code-duplicate"), "code");
			
			newFeatureBenefitEligibility = repo.saveAndFlush(newFeatureBenefitEligibility);		
		
			return newFeatureBenefitEligibility;
	}
	@Override
	public Optional<FeatureBenefitEligibility> findById(long id) {
		 Optional<FeatureBenefitEligibility> objs = repo.findById(id);
		if (objs.isPresent()) {		
			objs.get().setType(objs.get().getFeatureBenefitEligiType().getName());
			objs.get().setTypeCode(objs.get().getFeatureBenefitEligiType().getCode());
			objs.get().setTypeId(objs.get().getFeatureBenefitEligiType().getId().toString());
		   //List<FeatureBenefitEligiNotes> notesList  = featureBenefitEligiNotesService.findByFeatureBenefitEligibilityId(objs.get().getId());
		   //objs.get().setNotes(notesList);
			
			return Optional.ofNullable(objs.get());
			
		} else {
			
			return Optional.empty();
		}
	}
	@Override
	public FeatureBenefitEligibility updateFeatureBenefitEligibility(String tenantId,UpdateBaseEligibilityRequest updateBaseEligibilityRequest) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		PeriodResponse period;
		
		Optional<FeatureBenefitEligibility> isExists = repo.findById(stringToLong(updateBaseEligibilityRequest.getId()));

		if (isExists.isPresent()) {
			
			FeatureBenefitEligibility oldFeatureBenefitEligibility = isExists.get();
		
			featureBenefEligiHistoryService.addFeatureBenefEligiHistory(tenantId, oldFeatureBenefitEligibility, updateBaseEligibilityRequest.getModifiedUser());
				/*---------------------------------------Eligibility Type Validation----------------------------------------------------------------*/
			Optional<FeatureBenefitEligibilityType> featureBenefitEligiType = featureBenefitEligiTypeService.getById(Long.parseLong(updateBaseEligibilityRequest.getTypeId()));
			
			if(isExists.get().getVersion()!= stringToLong(updateBaseEligibilityRequest.getVersion())) {
				throw new ValidateRecordException(environment.getProperty("common-invalid.version"), "version");
			}
			
			//if (repo.existsByCode(updateBaseEligibilityRequest.getCode())) {
			if(repo.existsByCodeAndIdNotIn( updateBaseEligibilityRequest.getCode(), stringToLong(updateBaseEligibilityRequest.getId()))) 
				throw new ValidateRecordException(environment.getProperty("common.code-duplicate"), "code");
			
			if (featureBenefitEligiType.isPresent()) {		
	
				if (!(featureBenefitEligiType.get().getName().equals(updateBaseEligibilityRequest.getTypeName())))
					throw new ValidateRecordException(environment.getProperty("typeId.invalid"),"typeId");
	 		} 
	 		else {
	 			throw new ValidateRecordException(environment.getProperty("typeId.invalid"),"typeId");
	 		}
					
				
				Boolean  featureBenefitEligiTypeEsists = repo.existsByFeatureBenefitEligiTypeIdAndIdNotIn(Long.parseLong(updateBaseEligibilityRequest.getTypeId()),oldFeatureBenefitEligibility.getId());

	            if (featureBenefitEligiTypeEsists!= null && featureBenefitEligiTypeEsists) {
	            	throw new ValidateRecordException(environment.getProperty("typeId.duplicate"),"typeId");
	            }
	            
				/*---------------------------------Period Validation-------------------------------------------------------*/
					if (!(updateBaseEligibilityRequest.getPeriodId()==null||updateBaseEligibilityRequest.getPeriodId().isEmpty())) {
						
						if ((updateBaseEligibilityRequest.getPeriod()==null||updateBaseEligibilityRequest.getPeriod().isEmpty()))
					    	throw new ValidateRecordException(environment.getProperty("period.invalid"),"period");
					
						period = validationService.validatePeriod(tenantId, updateBaseEligibilityRequest.getPeriodId(), updateBaseEligibilityRequest.getPeriod(), EntityPoint.FEATURE_BENEFIT_ELIGIBILITY_TYPE);
						if(period != null) {
						
						oldFeatureBenefitEligibility.setPeriodId(stringToLong(updateBaseEligibilityRequest.getPeriodId()));
						oldFeatureBenefitEligibility.setPeriod(period.getName());
						}

					}else {
						oldFeatureBenefitEligibility.setPeriodId(null);
						oldFeatureBenefitEligibility.setPeriod(null);
					}
					
					/*------------------------------------------Validate Indicator-----------------------------------------------------*/
					if ((updateBaseEligibilityRequest.getIndicator() != null && !updateBaseEligibilityRequest.getIndicator().isEmpty())) {
						if (updateBaseEligibilityRequest.getIndicator().toUpperCase().equals("FALSE")) {

							if (updateBaseEligibilityRequest.getAmount() == null
									|| updateBaseEligibilityRequest.getAmount().isEmpty()
									|| stringToBigDecimal(updateBaseEligibilityRequest.getAmount()).compareTo(BigDecimal.ZERO) <= 0) {

								throw new ValidateRecordException(environment.getProperty("amount.invalid"), "amount");
							}
							
							if (updateBaseEligibilityRequest.getTexual() != null
									&& !updateBaseEligibilityRequest.getTexual().isEmpty()) {
								
								throw new ValidateRecordException(environment.getProperty("textual-not-required"), "textual");
							}
							
						} else {
							
							if (updateBaseEligibilityRequest.getAmount() != null
									&& !updateBaseEligibilityRequest.getAmount().isEmpty()
									&&  stringToBigDecimal(updateBaseEligibilityRequest.getAmount()).compareTo(BigDecimal.ZERO) > 0) {
								
								throw new ValidateRecordException(environment.getProperty("amount-not-required"), "amount");
							}
							
							if (updateBaseEligibilityRequest.getTexual() == null
									|| updateBaseEligibilityRequest.getTexual().isEmpty()) {

								throw new ValidateRecordException(environment.getProperty("texual.invalid"), "textual");
							}
						}

					}
					
				/*----------------------------------------------------------------------------------------------------------------*/
			
					oldFeatureBenefitEligibility.setAmount(stringToBigDecimal(updateBaseEligibilityRequest.getAmount()));
					oldFeatureBenefitEligibility.setDescription(updateBaseEligibilityRequest.getDescription());
					oldFeatureBenefitEligibility.setIndicator(Boolean.parseBoolean(updateBaseEligibilityRequest.getIndicator()));
					oldFeatureBenefitEligibility.setName(updateBaseEligibilityRequest.getName());
				    oldFeatureBenefitEligibility.setFeatureBenefitEligiType(featureBenefitEligiType.get());
				    oldFeatureBenefitEligibility.setTexual(updateBaseEligibilityRequest.getTexual());
				    oldFeatureBenefitEligibility.setModifiedDate(currentTimestamp);
				    oldFeatureBenefitEligibility.setModifiedUser(updateBaseEligibilityRequest.getModifiedUser());
				    oldFeatureBenefitEligibility.setStatus(updateBaseEligibilityRequest.getStatus());
				    //oldFeatureBenefitEligibility.setVersion(stringToLong(updateBaseEligibilityRequest.getVersion()));
				    oldFeatureBenefitEligibility.setTenantId(tenantId);
				    oldFeatureBenefitEligibility.setCode(updateBaseEligibilityRequest.getCode());
				    oldFeatureBenefitEligibility.setSyncTs(currentTimestamp);

				    oldFeatureBenefitEligibility = repo.saveAndFlush(oldFeatureBenefitEligibility);
		    	
				return oldFeatureBenefitEligibility;
	
		} else {
			throw new OtherException(environment.getProperty("record-not-found"));}
	}
	
	@Override
	public List<FeatureBenefitEligibility> findAll() {

		List<FeatureBenefitEligibility> featureBenefitEligibilityAll = new ArrayList<>();
		
		List<FeatureBenefitEligibility> objs = repo.findAll();

		//List<FeatureBenefitEligiNotes> notesList = new ArrayList<>();
		
		for (FeatureBenefitEligibility featureBenefitEligibility : objs) {

			featureBenefitEligibility.setType(featureBenefitEligibility.getFeatureBenefitEligiType().getName());
			featureBenefitEligibility.setTypeCode(featureBenefitEligibility.getFeatureBenefitEligiType().getCode());
			featureBenefitEligibility.setTypeId(featureBenefitEligibility.getFeatureBenefitEligiType().getId().toString());
			//notesList  = featureBenefitEligiNotesService.findByFeatureBenefitEligibilityId(featureBenefitEligibility.getId());
			//featureBenefitEligibility.setNotes(notesList);
			featureBenefitEligibilityAll.add(featureBenefitEligibility);
			
		}		
			return featureBenefitEligibilityAll;	
	}
	
	@Override
	public List<FeatureBenefitEligibility> findByStatus(String status) {
		  
		List<FeatureBenefitEligibility> featureBenefitEligibilityAll = new ArrayList<>();
		
        List<FeatureBenefitEligibility> objs = (List<FeatureBenefitEligibility>) repo.findByStatus(status);
		//List<FeatureBenefitEligiNotes> notesList ;
		
		for (FeatureBenefitEligibility featureBenefitEligibility : objs) {

			featureBenefitEligibility.setType(featureBenefitEligibility.getFeatureBenefitEligiType().getName());
			featureBenefitEligibility.setTypeCode(featureBenefitEligibility.getFeatureBenefitEligiType().getCode());
			featureBenefitEligibility.setTypeId(featureBenefitEligibility.getFeatureBenefitEligiType().getId().toString());
			//notesList  = featureBenefitEligiNotesService.findByFeatureBenefitEligibilityId(featureBenefitEligibility.getId());
			//featureBenefitEligibility.setNotes(notesList);
			featureBenefitEligibilityAll.add(featureBenefitEligibility);
			
		}		
			return featureBenefitEligibilityAll;	
	}
	@Override
	public List<FeatureBenefitEligibility>  findByName(String name) {
		 
		 List<FeatureBenefitEligibility> featureBenefitEligibilityAll = new ArrayList<>();
			
	        List<FeatureBenefitEligibility> objs = (List<FeatureBenefitEligibility>) repo.findByNameContaining(name);

			//List<FeatureBenefitEligiNotes> notesList ;
			
			for (FeatureBenefitEligibility featureBenefitEligibility : objs) {

				featureBenefitEligibility.setType(featureBenefitEligibility.getFeatureBenefitEligiType().getName());
				featureBenefitEligibility.setTypeCode(featureBenefitEligibility.getFeatureBenefitEligiType().getCode());
				featureBenefitEligibility.setTypeId(featureBenefitEligibility.getFeatureBenefitEligiType().getId().toString());
				//notesList  = featureBenefitEligiNotesService.findByFeatureBenefitEligibilityId(featureBenefitEligibility.getId());
				//featureBenefitEligibility.setNotes(notesList);
				featureBenefitEligibilityAll.add(featureBenefitEligibility);
				
			}		
				return featureBenefitEligibilityAll;	
	}
	@Override
	public  List<FeatureBenefitEligibility> findByFeatureBenefitEligiTypeCode(String code) {

		 
		 List<FeatureBenefitEligibility> featureBenefitEligibilityAll = new ArrayList<>();
			
	        List<FeatureBenefitEligibility> objs = (List<FeatureBenefitEligibility>) repo.findByCode(code);

			//List<FeatureBenefitEligiNotes> notesList ;
			
			for (FeatureBenefitEligibility featureBenefitEligibility : objs) {

				featureBenefitEligibility.setType(featureBenefitEligibility.getFeatureBenefitEligiType().getName());
				featureBenefitEligibility.setTypeCode(featureBenefitEligibility.getFeatureBenefitEligiType().getCode());
				featureBenefitEligibility.setTypeId(featureBenefitEligibility.getFeatureBenefitEligiType().getId().toString());
				//notesList  = featureBenefitEligiNotesService.findByFeatureBenefitEligibilityId(featureBenefitEligibility.getId());
				//featureBenefitEligibility.setNotes(notesList);
				featureBenefitEligibilityAll.add(featureBenefitEligibility);
				
			}		
				return featureBenefitEligibilityAll;	
	}

	
  
}
