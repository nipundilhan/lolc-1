package com.fusionx.lending.origination.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.domain.SalaryIncome;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.BusinessIncomeExpenseRepository;
import com.fusionx.lending.origination.repository.BusinessTypeRepository;
import com.fusionx.lending.origination.repository.CultivationCategoryRepository;
import com.fusionx.lending.origination.repository.GuarantorCultivationIncomeRepository;
import com.fusionx.lending.origination.repository.GuarantorRepository;
import com.fusionx.lending.origination.repository.OtherIncomeRepository;
import com.fusionx.lending.origination.repository.OtherIncomeTypeRepository;
import com.fusionx.lending.origination.repository.SalaryIncomeRepository;
import com.fusionx.lending.origination.resource.DesignationResponse;
import com.fusionx.lending.origination.resource.FrequencyResponse;
import com.fusionx.lending.origination.resource.GuarantorSalaryIncomeAddResource;
import com.fusionx.lending.origination.resource.GuarantorSalaryIncomeListResource;
import com.fusionx.lending.origination.resource.GuarantorSalaryIncomeUpdateResource;
import com.fusionx.lending.origination.service.GuarantorSalaryIncomeService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class GuarantorSalaryIncomeServiceImpl extends MessagePropertyBase implements GuarantorSalaryIncomeService {

	@Autowired
	private GuarantorRepository guarantorRepository;

	@Autowired
	private SalaryIncomeRepository salaryIncomeRepository;

	@Autowired
	private ValidateService validateService;

	@Override
	public Optional<SalaryIncome> findById(String tenantId, Long id) {
		
		Optional<SalaryIncome> isPresentSalaryIncome = salaryIncomeRepository.findById(id);
		
		if (isPresentSalaryIncome.isPresent()) {
			return Optional.ofNullable(isPresentSalaryIncome.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<SalaryIncome> findAll(String tenantId) {
		return salaryIncomeRepository.findAll();
	}

	@Override
	public List<SalaryIncome> findByStatus(String tenantId, String status) {
		return salaryIncomeRepository.findByStatus(CommonStatus.valueOf(status).toString());
	}

	@Override
	public void saveSalaryIncomes(String tenantId, GuarantorSalaryIncomeAddResource guarantorSalaryIncomeAddResource) {

		Optional<Guarantor> isPresentGuarantor = guarantorRepository.findByIdAndStatus(
				validateService.stringToLong(guarantorSalaryIncomeAddResource.getGuarantorId()),
				CommonStatus.ACTIVE.toString());

		if (!isPresentGuarantor.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "guarantorId");
		}

		if (guarantorSalaryIncomeAddResource.getGuSalaryIncomeDetailsList() != null && !guarantorSalaryIncomeAddResource.getGuSalaryIncomeDetailsList().isEmpty()) {

			Integer index = 0;
			for (GuarantorSalaryIncomeListResource item : guarantorSalaryIncomeAddResource.getGuSalaryIncomeDetailsList()) {

				SalaryIncome salaryIncome = new SalaryIncome();
				salaryIncome.setTenantId(tenantId);
				salaryIncome.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
				salaryIncome.setCreatedDate(validateService.getCreateOrModifyDate());

				salaryIncome.setGuarantor(isPresentGuarantor.get());
				salaryIncome.setEmployerName(item.getEmployerName());
				DesignationResponse validateDesignation = validateService.validateDesignation(tenantId,item.getDesignationId());
				salaryIncome.setDesignationId(validateService.stringToLong(validateDesignation.getId()));
				salaryIncome.setDesignationCode(validateDesignation.getDesgCode());
				salaryIncome.setDesignationName(validateDesignation.getDesgName());

				FrequencyResponse validateFrequency = validateService.validateFrequency(tenantId,item.getFrequencyId());
				salaryIncome.setFrequencyId(validateService.stringToLong(item.getFrequencyId()));
				salaryIncome.setFrequencyCode(validateFrequency.getCode());
				salaryIncome.setFrequencyName(validateFrequency.getName());
				salaryIncome.setGrossSalary(validateService.stringToBigDecimal(item.getGrossSalary()));
				salaryIncome.setDeductions(validateService.stringToBigDecimal(item.getDeductions()));
				salaryIncome.setNetSalary(validateService.stringToBigDecimal(item.getNetSalary()));
				salaryIncome.setComment(item.getComment());
				salaryIncome.setStatus(CommonStatus.valueOf(item.getStatus()).toString());
				salaryIncome.setSyncTs(validateService.getSyncTs());
				salaryIncomeRepository.save(salaryIncome);

				index++;
			}
		}
	}

	@Override
	public SalaryIncome updateSalaryIncome(String tenantId, Long id,
			GuarantorSalaryIncomeUpdateResource guarantorSalaryIncomeUpdateResource) {
		
		SalaryIncome salaryIncome;
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), USERNAME);

		Optional<SalaryIncome> isPresentSalaryIncome = salaryIncomeRepository.findById(id);
		if (!isPresentSalaryIncome.isPresent())
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), MESSAGE);
		
		if (!isPresentSalaryIncome.get().getVersion().equals(Long.parseLong(guarantorSalaryIncomeUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), VERSION);
		
		salaryIncome = isPresentSalaryIncome.get();
		salaryIncome.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		salaryIncome.setModifiedDate(validateService.getCreateOrModifyDate());
		salaryIncome.setEmployerName(guarantorSalaryIncomeUpdateResource.getEmployerName());
		DesignationResponse validateDesignation = validateService.validateDesignation(tenantId, guarantorSalaryIncomeUpdateResource.getDesignationId());
		salaryIncome.setDesignationId(validateService.stringToLong((validateDesignation.getId())));
		salaryIncome.setDesignationCode(validateDesignation.getDesgCode());
		salaryIncome.setDesignationName(validateDesignation.getDesgName());
		
		FrequencyResponse validateFrequency = validateService.validateFrequency(tenantId, guarantorSalaryIncomeUpdateResource.getFrequencyId());
		salaryIncome.setFrequencyId(validateService.stringToLong((guarantorSalaryIncomeUpdateResource.getFrequencyId())));
		salaryIncome.setFrequencyCode(validateFrequency.getCode());
		salaryIncome.setFrequencyName(validateFrequency.getName());
		salaryIncome.setGrossSalary(validateService.stringToBigDecimal(guarantorSalaryIncomeUpdateResource.getGrossSalary()));
		salaryIncome.setDeductions(validateService.stringToBigDecimal(guarantorSalaryIncomeUpdateResource.getDeductions()));
		salaryIncome.setNetSalary(validateService.stringToBigDecimal(guarantorSalaryIncomeUpdateResource.getNetSalary()));
		salaryIncome.setComment(guarantorSalaryIncomeUpdateResource.getComment());
		salaryIncome.setStatus(guarantorSalaryIncomeUpdateResource.getStatus());
		salaryIncome.setSyncTs(validateService.getSyncTs());
		return salaryIncomeRepository.save(salaryIncome);
	}

	@Override
	public List<SalaryIncome> findByIdGuarantorId(String tenantId, Long guarantorId) {
		
		List<SalaryIncome> salaryIncomeList = salaryIncomeRepository.findByGuarantorId(guarantorId);
		if(salaryIncomeList.isEmpty()) {
			new ArrayList<SalaryIncome>();
		}
		return  salaryIncomeList;
	}

}
