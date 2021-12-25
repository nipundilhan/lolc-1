package com.fusionx.lending.origination.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.BusinessIncomeExpense;
import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.BusinessIncomeExpenseRepository;
import com.fusionx.lending.origination.repository.BusinessTypeRepository;
import com.fusionx.lending.origination.repository.GuarantorRepository;
import com.fusionx.lending.origination.resource.FrequencyResponse;
import com.fusionx.lending.origination.resource.GuarantorBusinessIncomeAddResource;
import com.fusionx.lending.origination.resource.GuarantorBusinessIncomeListResource;
import com.fusionx.lending.origination.resource.GuarantorBusinessIncomeUpdateResource;
import com.fusionx.lending.origination.service.GuarantorBusinessIncomeService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class GuarantorBusinessIncomeServiceImpl extends MessagePropertyBase implements GuarantorBusinessIncomeService{
	
	@Autowired
	private GuarantorRepository guarantorRepository;
	
	@Autowired
	private BusinessIncomeExpenseRepository businessIncomeExpenseRepository;
	
	@Autowired
	private BusinessTypeRepository businessTypeRepository;
	
	@Autowired
	private ValidateService validateService;
	

	@Override
	public Optional<BusinessIncomeExpense> findById(String tenantId, Long id) {
		
		Optional<BusinessIncomeExpense> isPresentBusinessIncomeExpense = businessIncomeExpenseRepository.findById(id);
		
		if (isPresentBusinessIncomeExpense.isPresent()) {
			return Optional.ofNullable(isPresentBusinessIncomeExpense.get());
		} else {
			return Optional.empty();
		}		
	}

	@Override
	public List<BusinessIncomeExpense> findByIdGuarantorId(String tenantId, Long guarantorId) {
		
		List<BusinessIncomeExpense> businessIncomeExpenseList = businessIncomeExpenseRepository.findByGuarantorId(guarantorId);
		if(businessIncomeExpenseList.isEmpty()) {
			new ArrayList<BusinessIncomeExpense>();
		}
		return  businessIncomeExpenseList;
	}

	@Override
	public List<BusinessIncomeExpense> findAll(String tenantId) {
		return businessIncomeExpenseRepository.findAll();
	}

	@Override
	public List<BusinessIncomeExpense> findByStatus(String tenantId, String status) {
		return businessIncomeExpenseRepository.findByStatus(CommonStatus.valueOf(status).toString());
	}

	@Override
	public void saveBusinessIncomes(String tenantId,
			GuarantorBusinessIncomeAddResource guarantorBusinessIncomeAddResource) {
		
		Optional<Guarantor> isPresentGuarantor = guarantorRepository.findByIdAndStatus(
				validateService.stringToLong(guarantorBusinessIncomeAddResource.getGuarantorId()),
				CommonStatus.ACTIVE.toString());

		if (!isPresentGuarantor.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "guarantorId");
		}

		Integer index=0;
		if (guarantorBusinessIncomeAddResource.getGuBusinessIncomeDetailsList() != null && !guarantorBusinessIncomeAddResource.getGuBusinessIncomeDetailsList().isEmpty()) {
			
			for(GuarantorBusinessIncomeListResource item : guarantorBusinessIncomeAddResource.getGuBusinessIncomeDetailsList()) {
				
				 Optional<BusinessType> businessType = businessTypeRepository.findById(validateService.stringToLong(item.getBusinessTypesId()));
					
			 		if (!businessType.isPresent()) {
			 			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.BUSINESS_INCOME_TYPE_ID, ServicePoint.BUSINESS_INCOME_EXPENSE, index);
			 			}
					
			 		if(!businessType.get().getName().equalsIgnoreCase(item.getBusinessTypeName())) {
			 			throw new DetailListValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.BUSINESS_INCOME_TYPE_ID, ServicePoint.BUSINESS_INCOME_EXPENSE, index);
			 		}
			 		
			 		BusinessIncomeExpense businessIncomeExpense = new BusinessIncomeExpense();
					businessIncomeExpense.setTenantId(tenantId);
					businessIncomeExpense.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
					businessIncomeExpense.setCreatedDate(validateService.getCreateOrModifyDate());

					businessIncomeExpense.setGuarantor(isPresentGuarantor.get());
					businessIncomeExpense.setBusinessType(businessType.get());
					businessIncomeExpense.setDescription(item.getDescription());
					FrequencyResponse validateFrequency = validateService.validateFrequency(tenantId, item.getFrequencyId());
					businessIncomeExpense.setFrequencyId(validateService.stringToLong(item.getFrequencyId()));
					businessIncomeExpense.setFrequencyCode(validateFrequency.getCode());
					businessIncomeExpense.setFrequencyName(validateFrequency.getName());
					if(item.getTotalGrossIncome() != null && !item.getTotalGrossIncome().isEmpty())
						businessIncomeExpense.setTotalGrossIncome(new BigDecimal(item.getTotalGrossIncome()));
					if(item.getTotalExpences() != null && !item.getTotalExpences().isEmpty())
						businessIncomeExpense.setTotalExpences(new BigDecimal(item.getTotalExpences()));
					if(item.getProfitMargin() != null && !item.getProfitMargin().isEmpty())
						businessIncomeExpense.setProfitMargin(new BigDecimal(item.getProfitMargin()));
					if(item.getTotalNetIncome() != null && !item.getTotalNetIncome().isEmpty())
						businessIncomeExpense.setTotalNetIncome(new BigDecimal(item.getTotalNetIncome()));
					businessIncomeExpense.setComments(item.getComment());
					businessIncomeExpense.setStatus(CommonStatus.valueOf(item.getStatus()));
					businessIncomeExpense.setSyncTs(validateService.getSyncTs());
					
					businessIncomeExpenseRepository.save(businessIncomeExpense);
					
					index++;
			}
			
		}
			
	}

	@Override
	public BusinessIncomeExpense updateBusinessIncome(String tenantId, Long id,
			GuarantorBusinessIncomeUpdateResource guarantorBusinessIncomeUpdateResource) {
		
		BusinessIncomeExpense businessIncomeExpense;
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), USERNAME);

		Optional<BusinessIncomeExpense> isPresentBusinessIncomeExpense = businessIncomeExpenseRepository.findById(id);
		if (!isPresentBusinessIncomeExpense.isPresent())
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), MESSAGE);
		
		if (!isPresentBusinessIncomeExpense.get().getVersion().equals(Long.parseLong(guarantorBusinessIncomeUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), VERSION);
		
		Optional<BusinessType> businessType = businessTypeRepository.findById(validateService.stringToLong(guarantorBusinessIncomeUpdateResource.getBusinessTypesId()));
		
 		if (!businessType.isPresent()) {
 			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.BUSINESS_INCOME_TYPE_ID, ServicePoint.BUSINESS_INCOME_EXPENSE, null);
 			}
		
 		if(!businessType.get().getName().equalsIgnoreCase(guarantorBusinessIncomeUpdateResource.getBusinessTypeName())) {
 			throw new DetailListValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.BUSINESS_INCOME_TYPE_ID, ServicePoint.BUSINESS_INCOME_EXPENSE, null);
 		}
		
		businessIncomeExpense = isPresentBusinessIncomeExpense.get();
		businessIncomeExpense.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		businessIncomeExpense.setModifiedDate(validateService.getCreateOrModifyDate());
		FrequencyResponse validateFrequency = validateService.validateFrequency(tenantId, guarantorBusinessIncomeUpdateResource.getFrequencyId());
		businessIncomeExpense.setFrequencyId(validateService.stringToLong((guarantorBusinessIncomeUpdateResource.getFrequencyId())));
		businessIncomeExpense.setFrequencyCode(validateFrequency.getCode());
		businessIncomeExpense.setFrequencyName(validateFrequency.getName());
		
		businessIncomeExpense.setBusinessType(businessType.get());
		businessIncomeExpense.setDescription(guarantorBusinessIncomeUpdateResource.getDescription());
		if(guarantorBusinessIncomeUpdateResource.getTotalGrossIncome() != null && !guarantorBusinessIncomeUpdateResource.getTotalGrossIncome().isEmpty())
			businessIncomeExpense.setTotalGrossIncome(new BigDecimal(guarantorBusinessIncomeUpdateResource.getTotalGrossIncome()));
		if(guarantorBusinessIncomeUpdateResource.getTotalExpences() != null && !guarantorBusinessIncomeUpdateResource.getTotalExpences().isEmpty())
			businessIncomeExpense.setTotalExpences(new BigDecimal(guarantorBusinessIncomeUpdateResource.getTotalExpences()));
		if(guarantorBusinessIncomeUpdateResource.getProfitMargin() != null && !guarantorBusinessIncomeUpdateResource.getProfitMargin().isEmpty())
			businessIncomeExpense.setProfitMargin(new BigDecimal(guarantorBusinessIncomeUpdateResource.getProfitMargin()));
		if(guarantorBusinessIncomeUpdateResource.getTotalNetIncome() != null && !guarantorBusinessIncomeUpdateResource.getTotalNetIncome().isEmpty())
			businessIncomeExpense.setTotalNetIncome(new BigDecimal(guarantorBusinessIncomeUpdateResource.getTotalNetIncome()));
		businessIncomeExpense.setComments(guarantorBusinessIncomeUpdateResource.getComment());
		businessIncomeExpense.setStatus(CommonStatus.valueOf(guarantorBusinessIncomeUpdateResource.getStatus()));
		businessIncomeExpense.setSyncTs(validateService.getSyncTs());
		return businessIncomeExpenseRepository.save(businessIncomeExpense);
	}

}
