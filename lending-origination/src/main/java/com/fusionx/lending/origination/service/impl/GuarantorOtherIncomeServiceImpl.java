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
import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.domain.OtherIncome;
import com.fusionx.lending.origination.domain.OtherIncomeType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.GuarantorRepository;
import com.fusionx.lending.origination.repository.OtherIncomeRepository;
import com.fusionx.lending.origination.repository.OtherIncomeTypeRepository;
import com.fusionx.lending.origination.resource.FrequencyResponse;
import com.fusionx.lending.origination.resource.GuarantorOtherIncomeAddResource;
import com.fusionx.lending.origination.resource.GuarantorOtherIncomeListResource;
import com.fusionx.lending.origination.resource.GuarantorOtherIncomeUpdateResource;
import com.fusionx.lending.origination.service.GuarantorOtherIncomeService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class GuarantorOtherIncomeServiceImpl extends MessagePropertyBase implements GuarantorOtherIncomeService {

	
	private GuarantorRepository guarantorRepository;

	private OtherIncomeTypeRepository otherIncomeTypeRepository;

	private OtherIncomeRepository otherIncomeRepository;

	private ValidateService validateService;
	
	@Autowired
	public void setGuarantorRepository(GuarantorRepository guarantorRepository) {
		this.guarantorRepository = guarantorRepository;
	}
	@Autowired
	public void setOtherIncomeTypeRepository(OtherIncomeTypeRepository otherIncomeTypeRepository) {
		this.otherIncomeTypeRepository = otherIncomeTypeRepository;
	}
	@Autowired
	public void setOtherIncomeRepository(OtherIncomeRepository otherIncomeRepository) {
		this.otherIncomeRepository = otherIncomeRepository;
	}
	@Autowired
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	@Override
	public Optional<OtherIncome> findById(String tenantId, Long id) {
		
        Optional<OtherIncome> isPresentOtherIncome = otherIncomeRepository.findById(id);
		
		if (isPresentOtherIncome.isPresent()) {
			return Optional.ofNullable(isPresentOtherIncome.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<OtherIncome> findByIdGuarantorId(String tenantId, Long guarantorId) {
		
		List<OtherIncome> otherIncomeList = otherIncomeRepository.findByGuarantorId(guarantorId);
		if(otherIncomeList.isEmpty()) {
			new ArrayList<OtherIncome>();
		}
		return  otherIncomeList;
	}

	@Override
	public List<OtherIncome> findAll(String tenantId) {
		return otherIncomeRepository.findAll();
	}

	@Override
	public List<OtherIncome> findByStatus(String tenantId, String status) {
		return otherIncomeRepository.findByStatus(CommonStatus.valueOf(status).toString());
	}

	@Override
	public void saveOtherIncomes(String tenantId, GuarantorOtherIncomeAddResource guarantorOtherIncomeAddResource) {
		
		Optional<Guarantor> isPresentGuarantor = guarantorRepository.findByIdAndStatus(
				validateService.stringToLong(guarantorOtherIncomeAddResource.getGuarantorId()),
				CommonStatus.ACTIVE.toString());

		if (!isPresentGuarantor.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "guarantorId");
		}
		
		
		if (guarantorOtherIncomeAddResource.getGuOtherIncomeDetailsList() != null && !guarantorOtherIncomeAddResource.getGuOtherIncomeDetailsList().isEmpty()) {

			Integer index = 0;
			for (GuarantorOtherIncomeListResource item : guarantorOtherIncomeAddResource.getGuOtherIncomeDetailsList()) {
				
				Optional<OtherIncomeType> otherIncomeType = otherIncomeTypeRepository.findByIdAndCodeAndStatus(validateService.stringToLong(item.getOtherIncomeTypeId()),item.getOtherIncomeTypeCode(),"ACTIVE");
		 		if (!otherIncomeType.isPresent()) {
		 			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.OTHER_INCOME_TYPE_ID, ServicePoint.GUARANTOR_OTHER_INCOME,index);
		 		}

				OtherIncome otherIncome = new OtherIncome();
				otherIncome.setTenantId(tenantId);
				otherIncome.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
				otherIncome.setCreatedDate(validateService.getCreateOrModifyDate());

				otherIncome.setGuarantor(isPresentGuarantor.get());
		 		otherIncome.setOtherIncomeType(otherIncomeType.get());
		 		otherIncome.setDescription(item.getDescription());
		 		FrequencyResponse validateFrequency = validateService.validateFrequency(tenantId, item.getFrequencyId());
				otherIncome.setFrequencyId(validateService.stringToLong(item.getFrequencyId()));
				otherIncome.setFrequencyCode(validateFrequency.getCode());
				otherIncome.setFrequencyName(validateFrequency.getName());
				otherIncome.setGrossIncome(stringToBigDecimal(item.getGrossIncome()));
				otherIncome.setDeductions(stringToBigDecimal(item.getDeductions()));
				otherIncome.setNetIncome(stringToBigDecimal(item.getNetIncome()));
				otherIncome.setComment(item.getComment());
				otherIncome.setStatus(CommonStatus.valueOf(item.getStatus()).toString());
				otherIncome.setSyncTs(validateService.getSyncTs());
				otherIncomeRepository.save(otherIncome);

				index++;
			}
		}
		
	}

	@Override
	public OtherIncome updateOtherIncome(String tenantId, Long id,
			GuarantorOtherIncomeUpdateResource guarantorOtherIncomeUpdateResource) {
		
		OtherIncome otherIncome;
		if (LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), USERNAME);

		Optional<OtherIncome> isPresentOtherIncome = otherIncomeRepository.findById(id);
		if (!isPresentOtherIncome.isPresent())
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), MESSAGE);
		
		if (!isPresentOtherIncome.get().getVersion().equals(Long.parseLong(guarantorOtherIncomeUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), VERSION);
		
		otherIncome = isPresentOtherIncome.get();
		otherIncome.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		otherIncome.setModifiedDate(validateService.getCreateOrModifyDate());
		
		FrequencyResponse validateFrequency = validateService.validateFrequency(tenantId, guarantorOtherIncomeUpdateResource.getFrequencyId());
		otherIncome.setFrequencyId(validateService.stringToLong((guarantorOtherIncomeUpdateResource.getFrequencyId())));
		otherIncome.setFrequencyCode(validateFrequency.getCode());
		otherIncome.setFrequencyName(validateFrequency.getName());
		otherIncome.setGrossIncome(stringToBigDecimal(guarantorOtherIncomeUpdateResource.getGrossIncome()));
		otherIncome.setDeductions(stringToBigDecimal(guarantorOtherIncomeUpdateResource.getDeductions()));
		otherIncome.setNetIncome(stringToBigDecimal(guarantorOtherIncomeUpdateResource.getNetIncome()));
		otherIncome.setComment(guarantorOtherIncomeUpdateResource.getComment());
		otherIncome.setStatus(guarantorOtherIncomeUpdateResource.getStatus());
		otherIncome.setSyncTs(validateService.getSyncTs());
		return otherIncomeRepository.save(otherIncome);
	}
	
	
	 private BigDecimal stringToBigDecimal(String value) {
	        return BigDecimal.valueOf(new Double(value));
	    }

}
