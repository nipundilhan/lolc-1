package com.fusionx.lending.origination.service.impl;

/**
 * 	Business Tax Details Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-09-2021   FXL-115  	 FXL-785       Piyumi       Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.BusinessIncomeDetails;
import com.fusionx.lending.origination.domain.BusinessTaxDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.BusinessIncomeDetailsRepository;
import com.fusionx.lending.origination.repository.BusinessTaxDetailsRepository;
import com.fusionx.lending.origination.resource.FrequencyResponse;
import com.fusionx.lending.origination.resource.TaxCodeResponse;
import com.fusionx.lending.origination.resource.BusinessTaxDetailsAddResource;
import com.fusionx.lending.origination.resource.BusinessTaxTypeResource;
import com.fusionx.lending.origination.resource.CurencyResponse;
import com.fusionx.lending.origination.service.BusinessTaxDetailsService;
import com.fusionx.lending.origination.service.ValidateService;


@Component
@Transactional(rollbackFor = Exception.class)
public class BusinessTaxDetailsServiceImpl extends MessagePropertyBase implements BusinessTaxDetailsService{
	
	
	private BusinessTaxDetailsRepository businessTaxDetailsRepository;
	
	@Autowired
	public void setBusinessTaxDetailsRepository(BusinessTaxDetailsRepository businessTaxDetailsRepository) {
		this.businessTaxDetailsRepository = businessTaxDetailsRepository;
	}
	
	private ValidateService validateService;
	
	@Autowired
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	private BusinessIncomeDetailsRepository businessIncomeDetailsRepository;
	
	@Autowired
	public void setBusinessIncomeDetailsRepository(BusinessIncomeDetailsRepository businessIncomeDetailsRepository) {
		this.businessIncomeDetailsRepository = businessIncomeDetailsRepository;
	}

	@Override
	public Optional<BusinessTaxDetails> findById(Long id) {
		Optional<BusinessTaxDetails> isPresentBusinessTaxDetails = businessTaxDetailsRepository.findById(id);
		
		if (isPresentBusinessTaxDetails.isPresent()) {
			return Optional.ofNullable(isPresentBusinessTaxDetails.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<BusinessTaxDetails> findAll() {
		return  businessTaxDetailsRepository.findAll();
	}


	@Override
	public List<BusinessTaxDetails> findByStatus(String status) {
		
		return 	businessTaxDetailsRepository.findByStatus(CommonStatus.valueOf(status));
	}
	
	@Override
	public Boolean save(String tenantId,BusinessTaxDetailsAddResource businessTaxDetailsAddResource) {
        
        if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");
		
        Optional<BusinessIncomeDetails> isPresentBusinessIncomeDetails = businessIncomeDetailsRepository.findById(validateService.stringToLong(businessTaxDetailsAddResource.getBusinessIncomeDetailId()));
		if (!isPresentBusinessIncomeDetails.isPresent()) 
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "businessIncomeDetailId");
		
		if(businessTaxDetailsAddResource.getBusinessTaxDetails() != null && !businessTaxDetailsAddResource.getBusinessTaxDetails().isEmpty()) {
			Integer index = 0;
			for(BusinessTaxTypeResource businessTaxTypeResource : businessTaxDetailsAddResource.getBusinessTaxDetails()) {					

				TaxCodeResponse taxCodeResponse = validateService.validateTaxCode(tenantId,businessTaxTypeResource.getTaxCodeId(),businessTaxTypeResource.getTaxCodeName());
				
				FrequencyResponse frequencyResponse = validateService.validateFrequency(tenantId, businessTaxTypeResource.getFrequencyId());
				if (!frequencyResponse.getName().equals(businessTaxTypeResource.getFrequencyName())) {
					throw new DetailListValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.FREQUENCY_NAME, ServicePoint.BUSINESS_TAX_DETAILS, index);										
				}
				
				CurencyResponse curencyResponse = validateService.validateCurrencyType(tenantId,businessTaxTypeResource.getCurrencyId(),businessTaxTypeResource.getCurrencyName());
				
					BusinessTaxDetails businessTaxDetails = new BusinessTaxDetails();
					businessTaxDetails.setTenantId(tenantId);
					businessTaxDetails.setBusinessIncomeDetail(isPresentBusinessIncomeDetails.get());
					businessTaxDetails.setTaxTypeId(taxCodeResponse.getId());
					businessTaxDetails.setTaxTypeCode(taxCodeResponse.getTaxCode());
					businessTaxDetails.setTaxTypeName(taxCodeResponse.getTaxCodeName());
					businessTaxDetails.setApplicableOn(businessTaxTypeResource.getApplicableOn());
					businessTaxDetails.setRate(validateService.stringToBigDecimal(businessTaxTypeResource.getRate()));
					businessTaxDetails.setAmount(validateService.stringToBigDecimal(businessTaxTypeResource.getAmount()));
					businessTaxDetails.setFrequencyId(validateService.stringToLong(frequencyResponse.getId()));
					businessTaxDetails.setFrequencyCode(frequencyResponse.getCode());
					businessTaxDetails.setCurrencyId(curencyResponse.getCurrencyId());
					businessTaxDetails.setCurrencyCode(curencyResponse.getCurrencyCode());
					businessTaxDetails.setCurrencyCodeNumeric(curencyResponse.getCodeNumeric());
			        businessTaxDetails.setStatus(CommonStatus.valueOf(businessTaxTypeResource.getStatus()));
			        businessTaxDetails.setSyncTs(validateService.getSyncTs());
			        businessTaxDetails.setCreatedDate(validateService.getCreateOrModifyDate());
			        businessTaxDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			        businessTaxDetailsRepository.save(businessTaxDetails); 
			        
			        index++;
			}
		}else {
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "businessTaxDetailResource");
		}
		         
	        return true;
	}

	@Override
	public BusinessTaxDetails update(String tenantId, Long id , BusinessTaxTypeResource businessTaxTypeResource) {
		
		if (LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");
		
			
		Optional<BusinessTaxDetails> isPresentBusinessTaxDetails = businessTaxDetailsRepository.findById(id);
		if (!isPresentBusinessTaxDetails.isPresent()) 
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");

		TaxCodeResponse taxCodeResponse = validateService.validateTaxCode(tenantId,businessTaxTypeResource.getTaxCodeId(),businessTaxTypeResource.getTaxCodeName());
		
		FrequencyResponse frequencyResponse = validateService.validateFrequency(tenantId, businessTaxTypeResource.getFrequencyId());
		if (!frequencyResponse.getName().equals(businessTaxTypeResource.getFrequencyName())) {
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH), "frequencyName");								
		}
		
		CurencyResponse curencyResponse = validateService.validateCurrencyType(tenantId,businessTaxTypeResource.getCurrencyId(),businessTaxTypeResource.getCurrencyName());
		
		if(!isPresentBusinessTaxDetails.get().getVersion().toString().equals(businessTaxTypeResource.getVersion())) {
			throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");					
		}
			BusinessTaxDetails businessTaxDetails = isPresentBusinessTaxDetails.get();
			businessTaxDetails.setTenantId(tenantId);
			businessTaxDetails.setTaxTypeId(taxCodeResponse.getId());
			businessTaxDetails.setTaxTypeCode(taxCodeResponse.getTaxCode());
			businessTaxDetails.setTaxTypeName(taxCodeResponse.getTaxCodeName());
			businessTaxDetails.setApplicableOn(businessTaxTypeResource.getApplicableOn());
			businessTaxDetails.setRate(validateService.stringToBigDecimal(businessTaxTypeResource.getRate()));
			businessTaxDetails.setAmount(validateService.stringToBigDecimal(businessTaxTypeResource.getAmount()));
			businessTaxDetails.setFrequencyId(validateService.stringToLong(frequencyResponse.getId()));
			businessTaxDetails.setFrequencyCode(frequencyResponse.getCode());
			businessTaxDetails.setCurrencyId(curencyResponse.getCurrencyId());
			businessTaxDetails.setCurrencyCode(curencyResponse.getCurrencyCode());
			businessTaxDetails.setCurrencyCodeNumeric(curencyResponse.getCodeNumeric());
	        businessTaxDetails.setStatus(CommonStatus.valueOf(businessTaxTypeResource.getStatus()));
	        businessTaxDetails.setSyncTs(validateService.getSyncTs());
	        businessTaxDetails.setModifiedDate(validateService.getCreateOrModifyDate());
	        businessTaxDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
	        businessTaxDetails = businessTaxDetailsRepository.save(businessTaxDetails); 
	        
	        return businessTaxDetails;
	}
	
	@Override
	public List<BusinessTaxDetails> findByBusinessIncomeDetailsId(Long businessIncomeDetailsId) {
		
		return businessTaxDetailsRepository.findByBusinessIncomeDetailId(businessIncomeDetailsId);
	}

}
