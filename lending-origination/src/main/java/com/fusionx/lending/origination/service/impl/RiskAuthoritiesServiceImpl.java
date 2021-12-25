package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
 * Risk Authorities
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-09-2021   FXL-338 		 FXL-682 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.RiskAuthorities;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.RiskAuthoritiesRepository;
import com.fusionx.lending.origination.resource.CommonAddResource;
import com.fusionx.lending.origination.resource.CommonUpdateResource;
import com.fusionx.lending.origination.service.RiskAuthoritiesHistoryService;
import com.fusionx.lending.origination.service.RiskAuthoritiesService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class RiskAuthoritiesServiceImpl extends MessagePropertyBase implements RiskAuthoritiesService {

	@Autowired
	private RiskAuthoritiesRepository riskAuthoritiesRepository;

	@Autowired
	private ValidateService validationService;

	@Autowired
	private RiskAuthoritiesHistoryService riskAuthoritiesHistoryService;

	@Override
	public List<RiskAuthorities> getAll() {
		return riskAuthoritiesRepository.findAll();
	}

	@Override
	public Optional<RiskAuthorities> getById(Long id) {
		Optional<RiskAuthorities> isPresentRiskAuthorities = riskAuthoritiesRepository.findById(id);
		if (isPresentRiskAuthorities.isPresent()) {
			return Optional.ofNullable(isPresentRiskAuthorities.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<RiskAuthorities> getByCode(String code) {
		Optional<RiskAuthorities> isPresentRiskAuthorities = riskAuthoritiesRepository.findByCode(code);
		if (isPresentRiskAuthorities.isPresent()) {
			return Optional.ofNullable(isPresentRiskAuthorities.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<RiskAuthorities> getByName(String name) {
		return riskAuthoritiesRepository.findByNameContaining(name);
	}

	@Override
	public List<RiskAuthorities> getByStatus(String status) {
		return riskAuthoritiesRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public RiskAuthorities addRiskAuthorities(String tenantId, CommonAddResource commonAddResource) {

		Optional<RiskAuthorities> isPresentRiskAuthorities = riskAuthoritiesRepository
				.findByCode(commonAddResource.getCode());

		if (isPresentRiskAuthorities.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE);

		RiskAuthorities riskAuthorities = new RiskAuthorities();
		riskAuthorities.setTenantId(tenantId);
		riskAuthorities.setCode(commonAddResource.getCode());
		riskAuthorities.setName(commonAddResource.getName());
		riskAuthorities.setDescription(commonAddResource.getDescription());
		riskAuthorities.setStatus(CommonStatus.valueOf(commonAddResource.getStatus()));
		riskAuthorities.setCreatedDate(validationService.getCreateOrModifyDate());
		riskAuthorities.setSyncTs(validationService.getCreateOrModifyDate());
		riskAuthorities.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		return riskAuthoritiesRepository.save(riskAuthorities);

	}

	@Override
	public RiskAuthorities updateRiskAuthorities(String tenantId, Long id, CommonUpdateResource commonUpdateResource) {

		Optional<RiskAuthorities> isPresentRiskAuthorities = riskAuthoritiesRepository.findById(id);
		if (!isPresentRiskAuthorities.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		} else {
			riskAuthoritiesHistoryService.saveHistory(tenantId, isPresentRiskAuthorities.get(),
					LogginAuthentcation.getInstance().getUserName());

			if (!isPresentRiskAuthorities.get().getVersion().equals(Long.parseLong(commonUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
						ServiceEntity.VERSION);

			if (!isPresentRiskAuthorities.get().getCode().equalsIgnoreCase(commonUpdateResource.getCode()))
				throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE);

			if (!isPresentRiskAuthorities.get().getName().equalsIgnoreCase(commonUpdateResource.getName()))
				throw new ValidateRecordException(environment.getProperty("common.name-update"), "name");
			
			RiskAuthorities riskAuthorities = isPresentRiskAuthorities.get();
			riskAuthorities.setTenantId(tenantId);
			riskAuthorities.setName(commonUpdateResource.getName());
			riskAuthorities.setDescription(commonUpdateResource.getDescription());
			riskAuthorities.setStatus(CommonStatus.valueOf(commonUpdateResource.getStatus()));
			riskAuthorities.setModifiedDate(validationService.getCreateOrModifyDate());
			riskAuthorities.setSyncTs(validationService.getCreateOrModifyDate());
			riskAuthorities.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

			return riskAuthoritiesRepository.saveAndFlush(riskAuthorities);
		}
	}

}
