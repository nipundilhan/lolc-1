package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;
/**
 * Salary Income Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   12-08-2021   FXL-338 		 FXL-532 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.SalaryIncomeType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.SalaryIncomeTypeRepository;
import com.fusionx.lending.origination.resource.CommonAddResource;
import com.fusionx.lending.origination.resource.CommonUpdateResource;
import com.fusionx.lending.origination.service.SalaryIncomeTypeHistoryService;
import com.fusionx.lending.origination.service.SalaryIncomeTypeService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class SalaryIncomeTypeServiceImpl extends MessagePropertyBase implements SalaryIncomeTypeService {

	@Autowired
	private SalaryIncomeTypeRepository salaryIncomeTypeRepository;

	@Autowired
	private ValidateService validationService;

	@Autowired
	private SalaryIncomeTypeHistoryService salaryIncomeTypeHistoryService;

	@Override
	public List<SalaryIncomeType> getAll() {
		return salaryIncomeTypeRepository.findAll();
	}

	@Override
	public Optional<SalaryIncomeType> getById(Long id) {
		Optional<SalaryIncomeType> isPresentSalaryIncomeType = salaryIncomeTypeRepository.findById(id);
		if (isPresentSalaryIncomeType.isPresent()) {
			return Optional.ofNullable(isPresentSalaryIncomeType.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<SalaryIncomeType> getByCode(String code) {
		Optional<SalaryIncomeType> isPresentSalaryIncomeType = salaryIncomeTypeRepository.findByCode(code);
		if (isPresentSalaryIncomeType.isPresent()) {
			return Optional.ofNullable(isPresentSalaryIncomeType.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<SalaryIncomeType> getByName(String name) {
		return salaryIncomeTypeRepository.findByNameContaining(name);
	}

	@Override
	public List<SalaryIncomeType> getByStatus(String status) {
		return salaryIncomeTypeRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public SalaryIncomeType addSalaryIncomeType(String tenantId, CommonAddResource commonAddResource) {

		Optional<SalaryIncomeType> isPresentSalaryIncomeType = salaryIncomeTypeRepository
				.findByCode(commonAddResource.getCode());

		if (isPresentSalaryIncomeType.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE);

		SalaryIncomeType salaryIncomeType = new SalaryIncomeType();
		salaryIncomeType.setTenantId(tenantId);
		salaryIncomeType.setCode(commonAddResource.getCode());
		salaryIncomeType.setName(commonAddResource.getName());
		salaryIncomeType.setDescription(commonAddResource.getDescription());
		salaryIncomeType.setStatus(CommonStatus.valueOf(commonAddResource.getStatus()));
		salaryIncomeType.setCreatedDate(validationService.getCreateOrModifyDate());
		salaryIncomeType.setSyncTs(validationService.getCreateOrModifyDate());
		salaryIncomeType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		return salaryIncomeTypeRepository.save(salaryIncomeType);

	}

	@Override
	public SalaryIncomeType updateSalaryIncomeType(String tenantId, Long id,
			CommonUpdateResource commonUpdateResource) {

		Optional<SalaryIncomeType> isPresentSalaryIncomeType = salaryIncomeTypeRepository.findById(id);
		if (!isPresentSalaryIncomeType.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		} else {
			salaryIncomeTypeHistoryService.saveHistory(tenantId, isPresentSalaryIncomeType.get(),
					LogginAuthentcation.getInstance().getUserName());

			if (!isPresentSalaryIncomeType.get().getVersion().equals(Long.parseLong(commonUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
						ServiceEntity.VERSION);

			if (!isPresentSalaryIncomeType.get().getCode().equalsIgnoreCase(commonUpdateResource.getCode()))
				throw new InvalidServiceIdException(environment.getProperty(RECORD_UPDATED), ServiceEntity.CODE);

			SalaryIncomeType salaryIncomeType = isPresentSalaryIncomeType.get();
			salaryIncomeType.setTenantId(tenantId);
			salaryIncomeType.setName(commonUpdateResource.getName());
			salaryIncomeType.setDescription(commonUpdateResource.getDescription());
			salaryIncomeType.setStatus(CommonStatus.valueOf(commonUpdateResource.getStatus()));
			salaryIncomeType.setModifiedDate(validationService.getCreateOrModifyDate());
			salaryIncomeType.setSyncTs(validationService.getCreateOrModifyDate());
			salaryIncomeType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

			return salaryIncomeTypeRepository.saveAndFlush(salaryIncomeType);
		}
	}

}
