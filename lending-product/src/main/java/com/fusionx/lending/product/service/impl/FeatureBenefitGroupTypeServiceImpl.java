package com.fusionx.lending.product.service.impl;


import java.util.Calendar;
import java.util.Collection;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.FeatureBenefitGroupType;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.OtherException;
import com.fusionx.lending.product.repository.FeatureBenefitGroupTypeRepository;
import com.fusionx.lending.product.resources.AddBaseRequest;
import com.fusionx.lending.product.resources.UpdateBaseRequest;
import com.fusionx.lending.product.service.FeatureBenefitGroupTypeHistoryService;
import com.fusionx.lending.product.service.FeatureBenefitGroupTypeService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * Feature Benefit Group Type Service - Feature Benefit Group Type Domain entity
 * @author 	Sanatha
 * @Date    15-JUN-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-JUN-2021  						     Sanatha      Created
 *    
 ********************************************************************************************************
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class FeatureBenefitGroupTypeServiceImpl extends MessagePropertyBase implements FeatureBenefitGroupTypeService {

	@Autowired
	private FeatureBenefitGroupTypeRepository repo;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private Environment environment;
	
	@Autowired 
	private ValidationService validationService;
	
	@Autowired 
	private FeatureBenefitGroupTypeHistoryService featureBenefitGroupTypeHistoryService;

	@Override
	public Optional<Collection<FeatureBenefitGroupType>> findAll() {
	
			return Optional.ofNullable(repo.findAll());
	}

	@Override
	public Optional<FeatureBenefitGroupType> findById(long id) {
	
			Optional<FeatureBenefitGroupType> objs = repo.findById(id);
			if (objs.isPresent()) {
				return Optional.ofNullable(objs.get());
			} else {
				return Optional.empty();
			}	
	}

	@Override
	public Optional<FeatureBenefitGroupType> findByCode(String code) {
		
		Optional<FeatureBenefitGroupType> objs = repo.findByCode(code);
		if (objs.isPresent()) {
			return Optional.ofNullable(objs.get());
		} else {
			return Optional.empty();
		}
  }
	
	@Override
	public boolean exists(long id) {
		try {
			return repo.existsById(id);
		} finally {

		}
	}
	private Long stringToLong(String str) {
		return Long.parseLong(str);
	}

	@Override
	public  Optional<Collection<FeatureBenefitGroupType>> findByStatus(String status) {
	
	        return Optional.ofNullable(repo.findByStatus(status));
	    
	}

	@Override
	public FeatureBenefitGroupType addFeatureBenefitGroupType(String tenantId,AddBaseRequest addBaseRequest) {
			Calendar calendar = Calendar.getInstance();
			java.util.Date now = calendar.getTime();
			java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

			Optional<FeatureBenefitGroupType> isExists = repo.findByCode(addBaseRequest.getCode());
			if(isExists.isPresent())
				throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.FEATURE_BENEFIT_ELIGIBILITY_TYPE);
			else{
				FeatureBenefitGroupType newFeatureBenefitGroupType = new FeatureBenefitGroupType();
				newFeatureBenefitGroupType.setTenantId(tenantId);
				newFeatureBenefitGroupType.setCode(addBaseRequest.getCode());
				if(addBaseRequest.getDescription()!=null) {
					newFeatureBenefitGroupType.setDescription(addBaseRequest.getDescription());
				}
				newFeatureBenefitGroupType.setStatus(addBaseRequest.getStatus());
				newFeatureBenefitGroupType.setName(addBaseRequest.getName());
				newFeatureBenefitGroupType.setCreatedDate(validationService.getCreateOrModifyDate());
				newFeatureBenefitGroupType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
				newFeatureBenefitGroupType.setSyncTs(validationService.getCreateOrModifyDate());
				newFeatureBenefitGroupType = repo.saveAndFlush(newFeatureBenefitGroupType);
				return newFeatureBenefitGroupType;
			}
	
	}

	@Override
	public FeatureBenefitGroupType updateFeatureBenefitGroupType(String tenantId,UpdateBaseRequest updateBaseRequest) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		Optional<FeatureBenefitGroupType> isExists = repo.findById(stringToLong(updateBaseRequest.getId()));

		if (isExists.isPresent()) {
			
			if(!isExists.get().getVersion().equals(Long.parseLong(updateBaseRequest.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.FEATURE_BENEFIT_ELIGIBILITY_TYPE);
			
			if (!(updateBaseRequest.getCode().equals(isExists.get().getCode()))) {
				
				Optional<FeatureBenefitGroupType> isCodeExists = repo.findByCode(updateBaseRequest.getCode());
				
				if(isCodeExists.isPresent()) {
					throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.FEATURE_BENEFIT_ELIGIBILITY_TYPE);
				}

			}
			
			//saving history
			featureBenefitGroupTypeHistoryService.saveHistory(tenantId, isExists.get(), LogginAuthentcation.getInstance().getUserName());
			
			FeatureBenefitGroupType oldFeatureBenefitGroupType = isExists.get();
				
				oldFeatureBenefitGroupType.setCode(updateBaseRequest.getCode());
				if(updateBaseRequest.getDescription()!=null) {
					oldFeatureBenefitGroupType.setDescription(updateBaseRequest.getDescription());
				}else {
					oldFeatureBenefitGroupType.setDescription(null);
				}
				oldFeatureBenefitGroupType.setStatus(updateBaseRequest.getStatus());
				oldFeatureBenefitGroupType.setName(updateBaseRequest.getName());
				oldFeatureBenefitGroupType.setModifiedDate(validationService.getCreateOrModifyDate());
				oldFeatureBenefitGroupType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
				oldFeatureBenefitGroupType.setSyncTs(validationService.getCreateOrModifyDate());
				oldFeatureBenefitGroupType = repo.saveAndFlush(oldFeatureBenefitGroupType);
				return oldFeatureBenefitGroupType;
	
		} else
			throw new OtherException(environment.getProperty("record-not-found"));

	}

	@Override
	public Optional<Collection<FeatureBenefitGroupType>> findByName(String name) {
		  return Optional.ofNullable(repo.findByNameContaining(name));
	}
  
}
