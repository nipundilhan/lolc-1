package com.fusionx.lending.product.service.impl;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.domain.ResidencyEligibility;
import com.fusionx.lending.product.domain.ResidencyInclude;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.repository.ResidencyEligibilityRepository;
import com.fusionx.lending.product.repository.ResidencyIncludeRepository;
import com.fusionx.lending.product.resources.ResidencyIncludeResource;
import com.fusionx.lending.product.resources.ResidencyIncludeUpdateResource;
import com.fusionx.lending.product.service.ResidencyIncludeHistoryService;
import com.fusionx.lending.product.service.ResidencyIncludeService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor=Exception.class)
public class ResidencyIncludeServiceImpl extends MessagePropertyBase implements ResidencyIncludeService{
	
	@Autowired
	Environment environment;
	
	@Autowired
	private ResidencyIncludeRepository residencyIncludeRepository;
	
	@Autowired
	private ResidencyEligibilityRepository residencyEligibilityRepository;
	
	@Autowired
	private ResidencyIncludeHistoryService residencyIncludeHistoryService;
	
	@Autowired
	private ValidationService validationService;

	@Override
	public List<ResidencyInclude> findAll(String tenantId) {
		return residencyIncludeRepository.findAll();
	}

	@Override
	public Optional<ResidencyInclude> findById(String tenantId, Long residencyIncludeId) {
		Optional<ResidencyInclude> residencyInclude = residencyIncludeRepository.findById(residencyIncludeId);
		if(residencyInclude.isPresent())
			return Optional.ofNullable(residencyInclude.get());
		else
			return Optional.empty();
	}

	@Override
	public Optional<List<ResidencyInclude>> findByResidencyEligiId(String tenantId, Long residencyEligibilityId) {
		Optional<ResidencyEligibility> isPresentResidencyEligibility = residencyEligibilityRepository.findById(residencyEligibilityId);
		if(isPresentResidencyEligibility.isPresent())
			return Optional.ofNullable(residencyIncludeRepository.findByResidencyEligibilityId(residencyEligibilityId));
		else
			return Optional.empty();
	}

	@Override
	public List<ResidencyInclude> findByStatus(String tenantId, String status) {
		return residencyIncludeRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public ResidencyInclude addResidencyInclude(String tenantId, Long residencyEligibilityId,ResidencyIncludeResource residencyIncludeAddResource, String userName) {

		Optional<ResidencyEligibility> isPresentResidencyEligibility = residencyEligibilityRepository.findByIdAndStatus(residencyEligibilityId, CommonStatus.ACTIVE);
		if(isPresentResidencyEligibility.isPresent()) {
			ResidencyInclude residencyInclude = new ResidencyInclude();
			validationService.validateCountry(tenantId, residencyIncludeAddResource.getCountryId(), residencyIncludeAddResource.getCountryName(), EntityPoint.RESIDENCY_INCLUDE);
			Optional<ResidencyInclude> isPresentResidencyInclude = residencyIncludeRepository.findByResidencyEligibilityIdAndCountryId(isPresentResidencyEligibility.get().getId(), validationService.stringToLong(residencyIncludeAddResource.getCountryId()));
			if(isPresentResidencyInclude.isPresent())
				throw new InvalidServiceIdException(environment.getProperty("common.already-exists"), ServiceEntity.COUNTRY, EntityPoint.RESIDENCY_INCLUDE);
			else {
				residencyInclude.setCountryId(validationService.stringToLong(residencyIncludeAddResource.getCountryId()));
				residencyInclude.setResidencyEligibility(isPresentResidencyEligibility.get());
			}
			residencyInclude.setTenantId(tenantId);
			residencyInclude.setStatus(residencyIncludeAddResource.getStatus());
			residencyInclude.setCreatedUser(userName);
			residencyInclude.setCreatedDate(validationService.getCreateOrModifyDate());
			residencyInclude.setSyncTs(validationService.getCreateOrModifyDate());
			residencyInclude = residencyIncludeRepository.saveAndFlush(residencyInclude);
			return residencyInclude;
		}else {
			return null;
		}
	}

	@Override
	public ResidencyInclude updateResidencyInclude(String tenantId, Long id, ResidencyIncludeUpdateResource residencyIncludeUpdateResource, String userName) {
		
		Optional<ResidencyInclude> isPresentResidencyInclude = residencyIncludeRepository.findById(id);
		if(isPresentResidencyInclude.isPresent()) {
			ResidencyInclude residencyInclude = isPresentResidencyInclude.get();
			residencyIncludeHistoryService.insertResidencyIncludeHistory(tenantId, residencyInclude, userName);
			validationService.validateCountry(tenantId, residencyIncludeUpdateResource.getCountryId(), residencyIncludeUpdateResource.getCountryName(), EntityPoint.RESIDENCY_INCLUDE);
			
			if (!isPresentResidencyInclude.get().getVersion().equals(Long.parseLong(residencyIncludeUpdateResource.getVersion())))
                throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.RESIDENCY_ELIGIBILITY);
			
			residencyInclude.setCountryId(validationService.stringToLong(residencyIncludeUpdateResource.getCountryId()));
			
			residencyInclude.setStatus(residencyIncludeUpdateResource.getStatus());
			residencyInclude.setModifiedUser(userName);
			residencyInclude.setModifiedDate(validationService.getCreateOrModifyDate());
			residencyInclude.setSyncTs(validationService.getCreateOrModifyDate());
			residencyInclude.setVersion(validationService.stringToLong(residencyIncludeUpdateResource.getVersion()));
			residencyInclude = residencyIncludeRepository.saveAndFlush(residencyInclude);
			return residencyInclude;
		}
		else
			return null;
	}

}
