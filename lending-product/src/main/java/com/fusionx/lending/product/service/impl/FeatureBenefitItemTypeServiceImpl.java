package com.fusionx.lending.product.service.impl;


import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.FeatureBenefitItemType;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.OtherException;
import com.fusionx.lending.product.repository.FeatureBenefitItemTypeRepository;
import com.fusionx.lending.product.resources.AddBaseRequest;
import com.fusionx.lending.product.resources.UpdateBaseRequest;
import com.fusionx.lending.product.service.FeatureBenefitItemTypeHistoryService;
import com.fusionx.lending.product.service.FeatureBenefitItemTypeService;
import com.fusionx.lending.product.service.ValidationService;
/**
 * Feature Benefit Item Type Service - Feature Benefit Item Type Domain entity
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
public class FeatureBenefitItemTypeServiceImpl extends MessagePropertyBase implements FeatureBenefitItemTypeService {

	@Autowired
	private FeatureBenefitItemTypeRepository repo;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private Environment environment;
	
	@Autowired 
	private ValidationService validationService;
	
	@Autowired
	private FeatureBenefitItemTypeHistoryService featureBenefitItemTypeHistoryService;

	@Override
	public Optional<Collection<FeatureBenefitItemType>> findAll() {

		return Optional.ofNullable(repo.findAll());
	}

	@Override
	public Optional<FeatureBenefitItemType> findById(long id) {

		Optional<FeatureBenefitItemType> objs = repo.findById(id);
		if (objs.isPresent()) {
			return Optional.ofNullable(objs.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<FeatureBenefitItemType> findByCode(String code) {

		Optional<FeatureBenefitItemType> objs = repo.findByCode(code);
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
	public Optional<Collection<FeatureBenefitItemType>> findByStatus(String status) {

		return Optional.ofNullable(repo.findByStatus(status));

	}

	@Override
	public FeatureBenefitItemType addFeatureBenefitItemType(String tenantId, AddBaseRequest addBaseRequest) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		Optional<FeatureBenefitItemType> isExists = repo.findByCode(addBaseRequest.getCode());
		if (isExists.isPresent())
			throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.FEATURE_BENEFIT_ELIGIBILITY_TYPE);
		else {
			FeatureBenefitItemType newFeatureBenefitItemType = new FeatureBenefitItemType();
			newFeatureBenefitItemType.setTenantId(tenantId);
			newFeatureBenefitItemType.setCode(addBaseRequest.getCode());
			if(addBaseRequest.getDescription()!=null) {
				newFeatureBenefitItemType.setDescription(addBaseRequest.getDescription());
			}
			newFeatureBenefitItemType.setStatus(addBaseRequest.getStatus());
			newFeatureBenefitItemType.setName(addBaseRequest.getName());
			newFeatureBenefitItemType.setCreatedDate(validationService.getCreateOrModifyDate());
			newFeatureBenefitItemType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			newFeatureBenefitItemType.setSyncTs(validationService.getCreateOrModifyDate());
			newFeatureBenefitItemType = repo.saveAndFlush(newFeatureBenefitItemType);
			return newFeatureBenefitItemType;
		}

	}

	@Override
	public FeatureBenefitItemType updateFeatureBenefitItemType(String tenantId, UpdateBaseRequest updateBaseRequest) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		Optional<FeatureBenefitItemType> isExists = repo.findById(stringToLong(updateBaseRequest.getId()));

		if (isExists.isPresent()) {
			
			if(!isExists.get().getVersion().equals(Long.parseLong(updateBaseRequest.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.FEATURE_BENEFIT_ELIGIBILITY_TYPE);

			if (!(updateBaseRequest.getCode().equals(isExists.get().getCode()))) {

				List<FeatureBenefitItemType> isCodeExists = repo.findByCodeAndIdNotIn(updateBaseRequest.getCode(), stringToLong(updateBaseRequest.getId()));

				if (!isCodeExists.isEmpty()) {
					throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.FEATURE_BENEFIT_ELIGIBILITY_TYPE);
				}

			}
			//saving history
			featureBenefitItemTypeHistoryService.saveHistory(tenantId, isExists.get(), LogginAuthentcation.getInstance().getUserName());
			
			FeatureBenefitItemType oldFeatureBenefitItemType = isExists.get();

			oldFeatureBenefitItemType.setCode(updateBaseRequest.getCode());
			if(updateBaseRequest.getDescription()!=null) {
				oldFeatureBenefitItemType.setDescription(updateBaseRequest.getDescription());
			}else {
				oldFeatureBenefitItemType.setDescription(null);
			}
			oldFeatureBenefitItemType.setStatus(updateBaseRequest.getStatus());
			oldFeatureBenefitItemType.setName(updateBaseRequest.getName());
			oldFeatureBenefitItemType.setModifiedDate(validationService.getCreateOrModifyDate());
			oldFeatureBenefitItemType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			oldFeatureBenefitItemType.setSyncTs(validationService.getCreateOrModifyDate());
			oldFeatureBenefitItemType = repo.saveAndFlush(oldFeatureBenefitItemType);
			return oldFeatureBenefitItemType;

		} else {
			throw new OtherException(environment.getProperty("record-not-found"));
		}

	}

	@Override
	public Optional<Collection<FeatureBenefitItemType>> findByName(String name) {
		return Optional.ofNullable(repo.findByNameContaining(name));
	}

	
}
