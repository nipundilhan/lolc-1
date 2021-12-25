package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
 * Risk Rating Levels
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-09-2021   FXL-338 		 FXL-684 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.BusinessRiskType;
import com.fusionx.lending.origination.domain.RiskAuthorities;
import com.fusionx.lending.origination.domain.RiskRatingLevelDetails;
import com.fusionx.lending.origination.domain.RiskRatingLevels;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.BusinessRiskTypeRepository;
import com.fusionx.lending.origination.repository.RiskAuthoritiesRepository;
import com.fusionx.lending.origination.repository.RiskRatingLevelDetailsRepository;
import com.fusionx.lending.origination.repository.RiskRatingLevelsRepository;
import com.fusionx.lending.origination.resource.RiskRatingLevelDetailsAddResource;
import com.fusionx.lending.origination.resource.RiskRatingLevelDetailsUpdateResource;
import com.fusionx.lending.origination.resource.RiskRatingLevelUpdateResource;
import com.fusionx.lending.origination.resource.RiskRatingLevelAddResource;
import com.fusionx.lending.origination.service.RiskRatingLevelDetailsService;
import com.fusionx.lending.origination.service.RiskRatingLevelsService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class RiskRatingLevelsServiceImpl extends MessagePropertyBase implements RiskRatingLevelsService {

	@Autowired
	private RiskRatingLevelDetailsRepository riskRatingLevelDetailsRepository;

	@Autowired
	private RiskRatingLevelsRepository riskRatingLevelsRepository;

	@Autowired
	private ValidateService validationService;

	@Autowired
	private RiskRatingLevelDetailsService riskRatingLevelDetailsService;

	@Autowired
	private BusinessRiskTypeRepository businessRiskTypeRepository;

	@Autowired
	private RiskAuthoritiesRepository riskAuthoritiesRepository;

	@Override
	public List<RiskRatingLevels> getAll() {
		return riskRatingLevelsRepository.findAll();
	}

	@Override
	public Optional<RiskRatingLevels> getById(Long id) {
		Optional<RiskRatingLevels> isPresentRiskRatingLevels = riskRatingLevelsRepository.findById(id);
		if (isPresentRiskRatingLevels.isPresent()) {
			return Optional.ofNullable(isPresentRiskRatingLevels.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<RiskRatingLevels> getByStatus(String status) {
		return riskRatingLevelsRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public RiskRatingLevels addRiskRatingLevels(String tenantId,
			RiskRatingLevelAddResource riskRatingLevelAddResource) {

		Optional<BusinessRiskType> isPresentBusinessRiskType = this.businessRiskTypeRepository
				.findById(Long.parseLong(riskRatingLevelAddResource.getRiskType()));
		if (!isPresentBusinessRiskType.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		}
		Optional<RiskAuthorities> isPresentRiskAuthorities = this.riskAuthoritiesRepository
				.findById(Long.parseLong(riskRatingLevelAddResource.getRiskAuthorityId()));
		if (!isPresentRiskAuthorities.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		}
		RiskRatingLevels riskRatingLevels = new RiskRatingLevels();
		riskRatingLevels.setTenantId(tenantId);
		riskRatingLevels.setRiskType(isPresentBusinessRiskType.get());
		riskRatingLevels.setRiskAuthorityId(isPresentRiskAuthorities.get());
		riskRatingLevels.setRiskRatingType(riskRatingLevelAddResource.getRiskRatingType());
		riskRatingLevels.setNote(riskRatingLevelAddResource.getNote());
		riskRatingLevels.setStatus(CommonStatus.valueOf(riskRatingLevelAddResource.getStatus()));
		riskRatingLevels.setCreatedDate(validationService.getCreateOrModifyDate());
		riskRatingLevels.setSyncTs(validationService.getCreateOrModifyDate());
		riskRatingLevels.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		riskRatingLevelsRepository.save(riskRatingLevels);

		for (RiskRatingLevelDetailsAddResource riskRatingLevelDetailsAddResource : riskRatingLevelAddResource
				.getRiskRatingLevelDetails()) {
			riskRatingLevelDetailsService.addRiskRatingLevelDetails(tenantId, riskRatingLevels,
					riskRatingLevelDetailsAddResource);
		}
		return riskRatingLevels;
	}

	@Override
	public List<RiskRatingLevels> getByRiskTypeId(Long riskTypeId) {

		return riskRatingLevelsRepository.findByRiskTypeId(riskTypeId);
	}

	@Override
	public RiskRatingLevels updateRiskRatingLevels(String tenantId, Long id,
			RiskRatingLevelUpdateResource riskRatingLevelUpdateResource) {

		Optional<RiskRatingLevels> isPresentRiskRatingLevels = riskRatingLevelsRepository.findById(id);

		Optional<BusinessRiskType> isPresentBusinessRiskType = this.businessRiskTypeRepository
				.findById(Long.parseLong(riskRatingLevelUpdateResource.getRiskType()));
		if (!isPresentBusinessRiskType.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		}
		Optional<RiskAuthorities> isPresentRiskAuthorities = this.riskAuthoritiesRepository
				.findById(Long.parseLong(riskRatingLevelUpdateResource.getRiskAuthorityId()));
		if (!isPresentRiskAuthorities.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		}
		RiskRatingLevels riskRatingLevels = isPresentRiskRatingLevels.get();
		riskRatingLevels.setTenantId(tenantId);
		riskRatingLevels.setRiskType(isPresentBusinessRiskType.get());
		riskRatingLevels.setRiskAuthorityId(isPresentRiskAuthorities.get());
		riskRatingLevels.setRiskRatingType(riskRatingLevelUpdateResource.getRiskRatingType());
		riskRatingLevels.setNote(riskRatingLevelUpdateResource.getNote());
		riskRatingLevels.setStatus(CommonStatus.valueOf(riskRatingLevelUpdateResource.getStatus()));
		riskRatingLevels.setModifiedDate(validationService.getCreateOrModifyDate());
		riskRatingLevels.setSyncTs(validationService.getCreateOrModifyDate());
		riskRatingLevels.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

		riskRatingLevelsRepository.save(riskRatingLevels);

		for (RiskRatingLevelDetailsUpdateResource riskRatingLevelDetailsUpdateResource : riskRatingLevelUpdateResource
				.getRiskRatingLevelDetails()) {

			Optional<RiskRatingLevelDetails> isPresentRiskRatingLevelDetails = riskRatingLevelDetailsRepository
					.findById(riskRatingLevelDetailsUpdateResource.getId());

			RiskRatingLevelDetails riskRatingLevelDetails = isPresentRiskRatingLevelDetails.get();
			riskRatingLevelDetails.setTenantId(tenantId);
			riskRatingLevelDetails.setRiskRatingLevels(riskRatingLevels);
			riskRatingLevelDetails.setSequence(riskRatingLevelDetailsUpdateResource.getSequence());
			riskRatingLevelDetails.setLevel(riskRatingLevelDetailsUpdateResource.getLevel());
			riskRatingLevelDetails.setGrade(riskRatingLevelDetailsUpdateResource.getGrade());
			riskRatingLevelDetails.setStatus(CommonStatus.valueOf(riskRatingLevelDetailsUpdateResource.getStatus()));
			riskRatingLevelDetails.setModifiedDate(validationService.getCreateOrModifyDate());
			riskRatingLevelDetails.setSyncTs(validationService.getCreateOrModifyDate());
			riskRatingLevelDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			riskRatingLevelDetailsRepository.save(riskRatingLevelDetails);

		}
		return riskRatingLevels;
	}

}
