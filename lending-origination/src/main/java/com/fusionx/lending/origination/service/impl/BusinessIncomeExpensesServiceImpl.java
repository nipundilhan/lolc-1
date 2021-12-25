package com.fusionx.lending.origination.service.impl;
/**
 * 	Business Income Expenses Service Impl
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
import com.fusionx.lending.origination.domain.BusinessExpenseType;
import com.fusionx.lending.origination.domain.BusinessIncomeExpenses;
import com.fusionx.lending.origination.domain.IncomeType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.BusinessExpenseTypeRepository;
import com.fusionx.lending.origination.repository.BusinessIncomeDetailsRepository;
import com.fusionx.lending.origination.repository.BusinessIncomeExpensesRepository;
import com.fusionx.lending.origination.repository.IncomeTypeRepository;
import com.fusionx.lending.origination.resource.FrequencyResponse;
import com.fusionx.lending.origination.resource.BusinessIncomeExpenseTypeResource;
import com.fusionx.lending.origination.resource.BusinessIncomeExpensesAddResource;
import com.fusionx.lending.origination.resource.CurencyResponse;
import com.fusionx.lending.origination.service.BusinessIncomeExpensesService;
import com.fusionx.lending.origination.service.ValidateService;


@Component
@Transactional(rollbackFor = Exception.class)
public class BusinessIncomeExpensesServiceImpl extends MessagePropertyBase implements BusinessIncomeExpensesService{
	
	
	private BusinessIncomeExpensesRepository businessIncomeExpensesRepository;
	
	@Autowired
	public void setBusinessIncomeExpensesRepository(BusinessIncomeExpensesRepository businessIncomeExpensesRepository) {
		this.businessIncomeExpensesRepository = businessIncomeExpensesRepository;
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
	
	@Autowired
	private IncomeTypeRepository incomeTypeRepository;
	
	@Autowired
	private BusinessExpenseTypeRepository businessExpenseTypeRepository;

	@Override
	public Optional<BusinessIncomeExpenses> findById(Long id) {
		Optional<BusinessIncomeExpenses> isPresentBusinessIncomeExpenses = businessIncomeExpensesRepository.findById(id);
		
		if (isPresentBusinessIncomeExpenses.isPresent()) {
			return Optional.ofNullable(setBusinessIncomeExpenseDetails(isPresentBusinessIncomeExpenses.get()));
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<BusinessIncomeExpenses> findAll() {
		List<BusinessIncomeExpenses> businessIncomeExpensesList = businessIncomeExpensesRepository.findAll();
		
		for (BusinessIncomeExpenses businessIncomeExpenses : businessIncomeExpensesList) {
			setBusinessIncomeExpenseDetails(businessIncomeExpenses);
		}
		return businessIncomeExpensesList;
	}


	@Override
	public List<BusinessIncomeExpenses> findByStatus(String status) {
		List<BusinessIncomeExpenses> businessIncomeExpensesList = businessIncomeExpensesRepository.findByStatus(CommonStatus.valueOf(status));
		
		for (BusinessIncomeExpenses businessIncomeExpenses : businessIncomeExpensesList) {
			setBusinessIncomeExpenseDetails(businessIncomeExpenses);
		}
		return businessIncomeExpensesList;
	}
	
	@Override
	public Boolean save(String tenantId,BusinessIncomeExpensesAddResource businessIncomeExpensesAddResource) {
        
        if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");
        
        Optional<IncomeType> incomeType= Optional.empty();
		Optional<BusinessExpenseType> expenseType = Optional.empty();
		
        Optional<BusinessIncomeDetails> isPresentBusinessIncomeDetails = businessIncomeDetailsRepository.findById(validateService.stringToLong(businessIncomeExpensesAddResource.getBusinessIncomeDetailId()));
		if (!isPresentBusinessIncomeDetails.isPresent()) 
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "businessIncomeDetailId");
		
		if(businessIncomeExpensesAddResource.getBusinessIncomeExenseTypes() != null && !businessIncomeExpensesAddResource.getBusinessIncomeExenseTypes().isEmpty()) {
			Integer index = 0;
			for(BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource : businessIncomeExpensesAddResource.getBusinessIncomeExenseTypes()) {
				
				if(businessIncomeExpenseTypeResource.getBusinessIncomeTypeId() != null && !businessIncomeExpenseTypeResource.getBusinessIncomeTypeId().isEmpty()
						&& businessIncomeExpenseTypeResource.getBusinessExpenseTypeId() != null && !businessIncomeExpenseTypeResource.getBusinessExpenseTypeId().isEmpty()) {
					throw new DetailListValidateException(environment.getProperty(INVALID_INCOME_EXPENSE_TYPE_NOT_EMPTY), ServiceEntity.BUSINESS_INCOME_TYPE, ServicePoint.BUSINESS_INCOME_EXPENSE_TYPES, index);
				
				}else if((businessIncomeExpenseTypeResource.getBusinessIncomeTypeId() == null || businessIncomeExpenseTypeResource.getBusinessIncomeTypeId().isEmpty())
						&& (businessIncomeExpenseTypeResource.getBusinessExpenseTypeId() == null || businessIncomeExpenseTypeResource.getBusinessExpenseTypeId().isEmpty())){
					throw new DetailListValidateException(environment.getProperty(INVALID_INCOME_EXPENSE_TYPE_EMPTY), ServiceEntity.BUSINESS_INCOME_TYPE, ServicePoint.BUSINESS_INCOME_EXPENSE_TYPES, index);
				}				
				
				if(businessIncomeExpenseTypeResource.getBusinessIncomeTypeId() != null && !businessIncomeExpenseTypeResource.getBusinessIncomeTypeId().isEmpty()) {
					incomeType = incomeTypeRepository.findByIdAndStatus(validateService.stringToLong(businessIncomeExpenseTypeResource.getBusinessIncomeTypeId()), CommonStatus.ACTIVE.toString());
					if(!incomeType.isPresent()) {
						throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.BUSINESS_INCOME_TYPE, ServicePoint.BUSINESS_INCOME_EXPENSE_TYPES, index);
					}
					
					if(!incomeType.get().getName().equalsIgnoreCase(businessIncomeExpenseTypeResource.getBusinessIncomeTypeName())) {
						throw new DetailListValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.BUSINESS_INCOME_TYPE_NAME, ServicePoint.BUSINESS_INCOME_EXPENSE_TYPES, index);
					}
				}
				
				if(businessIncomeExpenseTypeResource.getBusinessExpenseTypeId() != null && !businessIncomeExpenseTypeResource.getBusinessExpenseTypeId().isEmpty()) {
					expenseType = businessExpenseTypeRepository.findByIdAndStatus(validateService.stringToLong(businessIncomeExpenseTypeResource.getBusinessExpenseTypeId()), CommonStatus.ACTIVE.toString());
					if(!expenseType.isPresent()) {
						throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.BUSINESS_EXPENSE_TYPE, ServicePoint.BUSINESS_INCOME_EXPENSE_TYPES, index);
					}					
				}
				
				FrequencyResponse frequencyResponse = validateService.validateFrequency(tenantId, businessIncomeExpenseTypeResource.getFrequencyId());
				if (!frequencyResponse.getName().equals(businessIncomeExpenseTypeResource.getFrequencyName())) {
					throw new DetailListValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.FREQUENCY_NAME, ServicePoint.BUSINESS_INCOME_EXPENSE_TYPES, index);						
					
				}
				
				CurencyResponse curencyResponse = validateService.validateCurrencyType(tenantId,businessIncomeExpenseTypeResource.getCurrencyId(),businessIncomeExpenseTypeResource.getCurrencyName());
				
					BusinessIncomeExpenses businessIncomeExpenses = new BusinessIncomeExpenses();
					businessIncomeExpenses.setTenantId(tenantId);
					businessIncomeExpenses.setBusinessIncomeDetail(isPresentBusinessIncomeDetails.get());
					businessIncomeExpenses.setBusinessIncomeType(incomeType.isPresent() ? incomeType.get() : null);
					businessIncomeExpenses.setBusinessExpenseType(expenseType.isPresent() ? expenseType.get() :null);
					businessIncomeExpenses.setAmount(validateService.stringToBigDecimal(businessIncomeExpenseTypeResource.getAmount()));
					businessIncomeExpenses.setFrequencyId(validateService.stringToLong(frequencyResponse.getId()));
					businessIncomeExpenses.setFrequencyCode(frequencyResponse.getCode());
					businessIncomeExpenses.setCurrencyId(curencyResponse.getCurrencyId());
					businessIncomeExpenses.setCurrencyCode(curencyResponse.getCurrencyCode());
					businessIncomeExpenses.setCurrencyCodeNumeric(curencyResponse.getCodeNumeric());
					businessIncomeExpenses.setDescription(businessIncomeExpenseTypeResource.getDescription());
			        businessIncomeExpenses.setStatus(CommonStatus.valueOf(businessIncomeExpenseTypeResource.getStatus()));
			        businessIncomeExpenses.setSyncTs(validateService.getSyncTs());
			        businessIncomeExpenses.setCreatedDate(validateService.getCreateOrModifyDate());
			        businessIncomeExpenses.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			        businessIncomeExpensesRepository.save(businessIncomeExpenses); 
			        
			        index++;
			}
		}else {
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "businessIncomeExenseTypes");
		}
		         
	        return true;
	}

	@Override
	public BusinessIncomeExpenses update(String tenantId, Long id , BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource) {
		
		if (LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");
		
		Optional<IncomeType> incomeType= Optional.empty();
		Optional<BusinessExpenseType> expenseType = Optional.empty();
			
		Optional<BusinessIncomeExpenses> isPresentBusinessIncomeExpenses = businessIncomeExpensesRepository.findById(id);
		if (!isPresentBusinessIncomeExpenses.isPresent()) 
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
		
		if(businessIncomeExpenseTypeResource.getBusinessIncomeTypeId() != null && !businessIncomeExpenseTypeResource.getBusinessIncomeTypeId().isEmpty()
				&& businessIncomeExpenseTypeResource.getBusinessExpenseTypeId() != null && !businessIncomeExpenseTypeResource.getBusinessExpenseTypeId().isEmpty()) {
			throw new ValidateRecordException(environment.getProperty(INVALID_INCOME_EXPENSE_TYPE_NOT_EMPTY), "businessIncomeTypeId");
		
		}else if((businessIncomeExpenseTypeResource.getBusinessIncomeTypeId() == null || businessIncomeExpenseTypeResource.getBusinessIncomeTypeId().isEmpty())
				&& (businessIncomeExpenseTypeResource.getBusinessExpenseTypeId() == null || businessIncomeExpenseTypeResource.getBusinessExpenseTypeId().isEmpty())){
			throw new ValidateRecordException(environment.getProperty(INVALID_INCOME_EXPENSE_TYPE_EMPTY), "businessIncomeTypeId");
		}	
		
		if(businessIncomeExpenseTypeResource.getBusinessIncomeTypeId() != null && !businessIncomeExpenseTypeResource.getBusinessIncomeTypeId().isEmpty() &&
				isPresentBusinessIncomeExpenses.get().getBusinessExpenseType() !=null) {
			throw new ValidateRecordException(environment.getProperty(INVALID_INCOME_TYPE), "businessIncomeTypeId");
		}
		
		if(businessIncomeExpenseTypeResource.getBusinessExpenseTypeId() != null && !businessIncomeExpenseTypeResource.getBusinessExpenseTypeId().isEmpty() &&
				isPresentBusinessIncomeExpenses.get().getBusinessIncomeType() !=null) {
			throw new ValidateRecordException(environment.getProperty(INVALID_EXPENSE_TYPE), "businessExpenseTypeId");
		}
		
		if(businessIncomeExpenseTypeResource.getBusinessIncomeTypeId() != null && !businessIncomeExpenseTypeResource.getBusinessIncomeTypeId().isEmpty()) {
			incomeType = incomeTypeRepository.findByIdAndStatus(validateService.stringToLong(businessIncomeExpenseTypeResource.getBusinessIncomeTypeId()), CommonStatus.ACTIVE.toString());
			if(!incomeType.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "businessIncomeTypeId");
			}
			
			if(!incomeType.get().getName().equalsIgnoreCase(businessIncomeExpenseTypeResource.getBusinessIncomeTypeName())) {
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH), "businessIncomeTypeName");
			}
		}
		
		if(businessIncomeExpenseTypeResource.getBusinessExpenseTypeId() != null && !businessIncomeExpenseTypeResource.getBusinessExpenseTypeId().isEmpty()) {
			expenseType = businessExpenseTypeRepository.findByIdAndStatus(validateService.stringToLong(businessIncomeExpenseTypeResource.getBusinessExpenseTypeId()), CommonStatus.ACTIVE.toString());
			if(!expenseType.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "businessExpenseTypeId");
			}					
		}
		
		FrequencyResponse frequencyResponse = validateService.validateFrequency(tenantId, businessIncomeExpenseTypeResource.getFrequencyId());
		if (!frequencyResponse.getName().equals(businessIncomeExpenseTypeResource.getFrequencyName())) {
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH), "frequencyName");						
			
		}
		
		CurencyResponse curencyResponse = validateService.validateCurrencyType(tenantId,businessIncomeExpenseTypeResource.getCurrencyId(),businessIncomeExpenseTypeResource.getCurrencyName());
		
		if(!isPresentBusinessIncomeExpenses.get().getVersion().toString().equals(businessIncomeExpenseTypeResource.getVersion())) {
			throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");					
		}
			BusinessIncomeExpenses businessIncomeExpenses = isPresentBusinessIncomeExpenses.get();
			businessIncomeExpenses.setTenantId(tenantId);
			businessIncomeExpenses.setBusinessIncomeType(incomeType.isPresent() ? incomeType.get() : null);
			businessIncomeExpenses.setBusinessExpenseType(expenseType.isPresent() ? expenseType.get() :null);
			businessIncomeExpenses.setAmount(validateService.stringToBigDecimal(businessIncomeExpenseTypeResource.getAmount()));
			businessIncomeExpenses.setFrequencyId(validateService.stringToLong(frequencyResponse.getId()));
			businessIncomeExpenses.setFrequencyCode(frequencyResponse.getCode());
			businessIncomeExpenses.setCurrencyId(curencyResponse.getCurrencyId());
			businessIncomeExpenses.setCurrencyCode(curencyResponse.getCurrencyCode());
			businessIncomeExpenses.setCurrencyCodeNumeric(curencyResponse.getCodeNumeric());
			businessIncomeExpenses.setDescription(businessIncomeExpenseTypeResource.getDescription());
	        businessIncomeExpenses.setStatus(CommonStatus.valueOf(businessIncomeExpenseTypeResource.getStatus()));
	        businessIncomeExpenses.setSyncTs(validateService.getSyncTs());
	        businessIncomeExpenses.setModifiedDate(validateService.getCreateOrModifyDate());
	        businessIncomeExpenses.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
	        businessIncomeExpenses = businessIncomeExpensesRepository.save(businessIncomeExpenses); 
	        
	        return businessIncomeExpenses;
	}
	
	@Override
	public List<BusinessIncomeExpenses> findByBusinessIncomeDetailsId(Long businessIncomeDetailsId) {
		
		List<BusinessIncomeExpenses> businessIncomeExpensesList = businessIncomeExpensesRepository.findByBusinessIncomeDetailId(businessIncomeDetailsId);
		
		for (BusinessIncomeExpenses businessIncomeExpenses : businessIncomeExpensesList) {
			setBusinessIncomeExpenseDetails(businessIncomeExpenses);
		}
		return businessIncomeExpensesList;
	}
	
	private  BusinessIncomeExpenses setBusinessIncomeExpenseDetails(BusinessIncomeExpenses businessIncomeExpenses) {
		
		if(businessIncomeExpenses.getBusinessIncomeType() != null) {
			Optional<IncomeType> incomeType = incomeTypeRepository.findById(businessIncomeExpenses.getBusinessIncomeType().getId());
			businessIncomeExpenses.setBusinessIncomeTypesId(incomeType.get().getId());
			businessIncomeExpenses.setBusinessIncomeTypesName(incomeType.get().getName());
		}
		
		if(businessIncomeExpenses.getBusinessExpenseType() != null) {
			businessIncomeExpenses.setBusinessIncomeTypesId(businessIncomeExpenses.getBusinessExpenseType().getId());
		}
		
		return businessIncomeExpenses;
	}

}
