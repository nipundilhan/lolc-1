package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.FeatureBenefitEligibilityType;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.repository.FeatureBenefitEligibilityTypeRepository;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
import com.fusionx.lending.product.service.FeatureBenefitEligibilityTypeHistoryService;
import com.fusionx.lending.product.service.FeatureBenefitEligibilityTypeService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * Feature Benefit Eligibility Type Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-06-2021             				 MenukaJ        Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class FeatureBenefitEligibilityTypeServiceImpl extends MessagePropertyBase implements FeatureBenefitEligibilityTypeService {
	
	@Autowired
	private FeatureBenefitEligibilityTypeRepository featureBenefitEligibilityTypeRepository;
	
	@Autowired 
	private ValidationService validationService;
	
	@Autowired
	private FeatureBenefitEligibilityTypeHistoryService featureBenefitEligibilityTypeHistoryService;

	@Override
	public List<FeatureBenefitEligibilityType> getAll() {
		return featureBenefitEligibilityTypeRepository.findAll();
	}

	@Override
	public Optional<FeatureBenefitEligibilityType> getById(Long id) {
		Optional<FeatureBenefitEligibilityType> isPresentFetBenefitEliType= featureBenefitEligibilityTypeRepository.findById(id);
		if (isPresentFetBenefitEliType.isPresent()) {
			return Optional.ofNullable(isPresentFetBenefitEliType.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<FeatureBenefitEligibilityType> getByCode(String code) {
		Optional<FeatureBenefitEligibilityType> isPresentFetBenefitEliType= featureBenefitEligibilityTypeRepository.findByCode(code);
		if (isPresentFetBenefitEliType.isPresent()) {
			return Optional.ofNullable(isPresentFetBenefitEliType.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<FeatureBenefitEligibilityType> getByName(String name) {
		return featureBenefitEligibilityTypeRepository.findByNameContaining(name);
	}

	@Override
	public List<FeatureBenefitEligibilityType> getByStatus(String status) {
		return featureBenefitEligibilityTypeRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public FeatureBenefitEligibilityType addFeatureBenefitEligibilityType(String tenantId, CommonAddResource commonAddResource) {
        
        Optional<FeatureBenefitEligibilityType> isPresentFetBenefitEliType = featureBenefitEligibilityTypeRepository.findByCode(commonAddResource.getCode());
        
        if (isPresentFetBenefitEliType.isPresent()) 
        	throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.FEATURE_BENEFIT_ELIGIBILITY_TYPE);
        
        FeatureBenefitEligibilityType featureBenefitEligibilityType = new FeatureBenefitEligibilityType();
        featureBenefitEligibilityType.setTenantId(tenantId);
        featureBenefitEligibilityType.setCode(commonAddResource.getCode());
        featureBenefitEligibilityType.setName(commonAddResource.getName());
        featureBenefitEligibilityType.setDescription(commonAddResource.getDescription());
        featureBenefitEligibilityType.setStatus(CommonStatus.valueOf(commonAddResource.getStatus()));
        featureBenefitEligibilityType.setCreatedDate(validationService.getCreateOrModifyDate());
        featureBenefitEligibilityType.setSyncTs(validationService.getCreateOrModifyDate());
        featureBenefitEligibilityType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        
        return featureBenefitEligibilityTypeRepository.save(featureBenefitEligibilityType);

	}

	@Override
	public FeatureBenefitEligibilityType updateFeatureBenefitEligibilityType(String tenantId, Long id, CommonUpdateResource commonUpdateResource) {
		
		Optional<FeatureBenefitEligibilityType> isPresentEligiType= featureBenefitEligibilityTypeRepository.findById(id);
		
		featureBenefitEligibilityTypeHistoryService.saveHistory(tenantId, isPresentEligiType.get(), LogginAuthentcation.getInstance().getUserName());
		
		if(!isPresentEligiType.get().getVersion().equals(Long.parseLong(commonUpdateResource.getVersion())))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.FEATURE_BENEFIT_ELIGIBILITY_TYPE);
		
		// Commented by Sanatha as per Bug report FXL-928 on 23-SEP2021
		/*if(!isPresentEligiType.get().getCode().equalsIgnoreCase(commonUpdateResource.getCode()))
			throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE, EntityPoint.FEATURE_BENEFIT_ELIGIBILITY_TYPE);*/
		
		Optional<FeatureBenefitEligibilityType> isPresentEligiTypeByCode= featureBenefitEligibilityTypeRepository.findByCodeAndIdNotIn(commonUpdateResource.getCode(), id);
		if(isPresentEligiTypeByCode.isPresent())
			throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.FEATURE_BENEFIT_ELIGIBILITY_TYPE);
		
        FeatureBenefitEligibilityType featureBenefitEligibilityType = isPresentEligiType.get();
        featureBenefitEligibilityType.setTenantId(tenantId);
        featureBenefitEligibilityType.setCode(commonUpdateResource.getCode());
        featureBenefitEligibilityType.setName(commonUpdateResource.getName());
        featureBenefitEligibilityType.setDescription(commonUpdateResource.getDescription());
        featureBenefitEligibilityType.setStatus(CommonStatus.valueOf(commonUpdateResource.getStatus()));
        featureBenefitEligibilityType.setModifiedDate(validationService.getCreateOrModifyDate());
        featureBenefitEligibilityType.setSyncTs(validationService.getCreateOrModifyDate());
        featureBenefitEligibilityType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
        
        return featureBenefitEligibilityTypeRepository.saveAndFlush(featureBenefitEligibilityType);
		
	}

}
