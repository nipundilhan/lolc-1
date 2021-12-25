package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.CultivationIncomeType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.CultivationIncomeTypeRepository;
import com.fusionx.lending.origination.resource.CommonAddResource;
import com.fusionx.lending.origination.resource.CommonUpdateResource;
import com.fusionx.lending.origination.service.CultivationIncomeTypeHistoryService;
import com.fusionx.lending.origination.service.CultivationIncomeTypeService;
import com.fusionx.lending.origination.service.ValidateService;
/**
 * Cultivation Income Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-08-2021   FXL-338 		 FXL-533 	Dilki        Created
 *    
 ********************************************************************************************************
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class CultivationIncomeTypeServiceImpl extends MessagePropertyBase implements CultivationIncomeTypeService {

	@Autowired
	private CultivationIncomeTypeRepository cultivationIncomeTypeRepository;

	@Autowired
	private ValidateService validationService;

	@Autowired
	private CultivationIncomeTypeHistoryService cultivationIncomeTypeHistoryService;

	@Override
	public List<CultivationIncomeType> getAll() {
		return cultivationIncomeTypeRepository.findAll();
	}

	@Override
	public Optional<CultivationIncomeType> getById(Long id) {
		Optional<CultivationIncomeType> isPresentCultivationIncomeType = cultivationIncomeTypeRepository.findById(id);
		if (isPresentCultivationIncomeType.isPresent()) {
			return Optional.ofNullable(isPresentCultivationIncomeType.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<CultivationIncomeType> getByCode(String code) {
		Optional<CultivationIncomeType> isPresentCultivationIncomeType = cultivationIncomeTypeRepository
				.findByCode(code);
		if (isPresentCultivationIncomeType.isPresent()) {
			return Optional.ofNullable(isPresentCultivationIncomeType.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<CultivationIncomeType> getByName(String name) {
		return cultivationIncomeTypeRepository.findByNameContaining(name);
	}

	@Override
	public List<CultivationIncomeType> getByStatus(String status) {
		return cultivationIncomeTypeRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public CultivationIncomeType addCultivationIncomeType(String tenantId, CommonAddResource commonAddResource) {

		Optional<CultivationIncomeType> isPresentCultivationIncomeType = cultivationIncomeTypeRepository
				.findByCode(commonAddResource.getCode());

		if (isPresentCultivationIncomeType.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE);

		CultivationIncomeType cultivationIncomeType = new CultivationIncomeType();
		cultivationIncomeType.setTenantId(tenantId);
		cultivationIncomeType.setCode(commonAddResource.getCode());
		cultivationIncomeType.setName(commonAddResource.getName());
		cultivationIncomeType.setDescription(commonAddResource.getDescription());
		cultivationIncomeType.setStatus(CommonStatus.valueOf(commonAddResource.getStatus()));
		cultivationIncomeType.setCreatedDate(validationService.getCreateOrModifyDate());
		cultivationIncomeType.setSyncTs(validationService.getCreateOrModifyDate());
		cultivationIncomeType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		return cultivationIncomeTypeRepository.save(cultivationIncomeType);

	}

	@Override
	public CultivationIncomeType updateCultivationIncomeType(String tenantId, Long id,
			CommonUpdateResource commonUpdateResource) {

		Optional<CultivationIncomeType> isPresentCultivationIncomeType = cultivationIncomeTypeRepository.findById(id);
		if (!isPresentCultivationIncomeType.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		} else {

			cultivationIncomeTypeHistoryService.saveHistory(tenantId, isPresentCultivationIncomeType.get(),
					LogginAuthentcation.getInstance().getUserName());

			if (!isPresentCultivationIncomeType.get().getVersion()
					.equals(Long.parseLong(commonUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
						ServiceEntity.VERSION);

			if (!isPresentCultivationIncomeType.get().getCode().equalsIgnoreCase(commonUpdateResource.getCode()))
				throw new InvalidServiceIdException(environment.getProperty(RECORD_UPDATED), ServiceEntity.CODE);

			CultivationIncomeType cultivationIncomeType = isPresentCultivationIncomeType.get();
			cultivationIncomeType.setTenantId(tenantId);
			cultivationIncomeType.setName(commonUpdateResource.getName());
			cultivationIncomeType.setDescription(commonUpdateResource.getDescription());
			cultivationIncomeType.setStatus(CommonStatus.valueOf(commonUpdateResource.getStatus()));
			cultivationIncomeType.setModifiedDate(validationService.getCreateOrModifyDate());
			cultivationIncomeType.setSyncTs(validationService.getCreateOrModifyDate());
			cultivationIncomeType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

			return cultivationIncomeTypeRepository.saveAndFlush(cultivationIncomeType);
		}
	}

}
