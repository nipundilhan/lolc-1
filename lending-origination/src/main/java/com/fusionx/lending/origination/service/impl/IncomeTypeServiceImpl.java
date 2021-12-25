package com.fusionx.lending.origination.service.impl;
/**
 * Income Type Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021   FXL-340        FXL-534   Piyumi       Modified
 *    
 ********************************************************************************************************
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.domain.IncomeType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.UserNotFound;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.BusinessTypeRepository;
import com.fusionx.lending.origination.repository.IncomeTypeRepository;
import com.fusionx.lending.origination.resource.IncomeTypeRequest;
import com.fusionx.lending.origination.resource.UpdateIncomeTypeRequest;
import com.fusionx.lending.origination.service.IncomeTypeService;


@Component
@Transactional(rollbackFor=Exception.class)
public class IncomeTypeServiceImpl extends MessagePropertyBase implements IncomeTypeService{
	
	@Autowired
	private IncomeTypeRepository incomeTypeRepository;
	
	private BusinessTypeRepository businessTypeRepository;
	
	@Autowired
	public void setBusinessTypeRepository(BusinessTypeRepository businessTypeRepository) {
		this.businessTypeRepository = businessTypeRepository;
	}

	@Override
	public List<IncomeType> findAll() {
		return incomeTypeRepository.findAll();
	}

	@Override
	public Optional<IncomeType> findById(Long id) {
		return incomeTypeRepository.findById(id);
		
	}

	@Override
	public Optional<IncomeType> findByCode(String code) {
		return incomeTypeRepository.findByCode(code);
		
	}

	@Override
	public List<IncomeType> findByStatus(String status) {
		return incomeTypeRepository.findByStatus(status);
		
	}
	

	@Override
	public List<IncomeType> findByName(String name) {
		return incomeTypeRepository.findByNameContaining(name);
	
	}

	@Override
	public IncomeType addIncomeType(String tenantId, IncomeTypeRequest incomeTypeRequest) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) 
			throw new UserNotFound(environment.getProperty("common.user-not-found"));
		
		Optional<BusinessType> isPresentBusinessType = businessTypeRepository.findByIdAndStatus(stringToLong(incomeTypeRequest.getBusinessTypeId()),CommonStatus.ACTIVE.toString());		
		
		if(!isPresentBusinessType.isPresent()) 
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "businessTypeId");
	
		if(!isPresentBusinessType.get().getName().equalsIgnoreCase(incomeTypeRequest.getBusinessTypename())) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),"businessTypeName");    
		}
		
		Optional<IncomeType> isPresentIncomeType = incomeTypeRepository.findByCode(incomeTypeRequest.getCode());
		if(!isPresentIncomeType.isPresent()) {
			IncomeType incomeType = new IncomeType();
			incomeType.setTenantId(tenantId);
			incomeType.setCode(incomeTypeRequest.getCode());
			incomeType.setName(incomeTypeRequest.getName());
			incomeType.setDescription(incomeTypeRequest.getDescription());
			incomeType.setStatus(incomeTypeRequest.getStatus());
			incomeType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			incomeType.setCreatedDate(currentTimestamp);
			incomeType.setSyncTs(currentTimestamp);
			incomeType.setBusinessType(isPresentBusinessType.get());
			incomeType = incomeTypeRepository.save(incomeType);
			return incomeType;
		}
		else
		    throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "code");
	}

	@Override
	public IncomeType updateIncomeType(String tenantId, UpdateIncomeTypeRequest updateIncomeTypeRequest) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		if(LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty()) 
			throw new UserNotFound(environment.getProperty("common.user-not-found"));
		
		Optional<BusinessType> isPresentBusinessType = businessTypeRepository.findByIdAndStatus(stringToLong(updateIncomeTypeRequest.getBusinessTypeId()),CommonStatus.ACTIVE.toString());		
		
		if(!isPresentBusinessType.isPresent()) 
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "businessTypeId");
	
		if(!isPresentBusinessType.get().getName().equalsIgnoreCase(updateIncomeTypeRequest.getBusinessTypename())) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),"businessTypeName");    
		}
		
		Optional<IncomeType> isPresentIncomeType = incomeTypeRepository.findById(stringToLong(updateIncomeTypeRequest.getId()));
		if(isPresentIncomeType.isPresent()) {
			if (isPresentIncomeType.get().getVersion().equals(stringToLong(updateIncomeTypeRequest.getVersion()))) {
                throw new ValidateRecordException(environment.getProperty(INVALID_VERSION),"version");                    
			}
			
			Optional<IncomeType> isPresentIncomeTypeCode = incomeTypeRepository.findByCodeAndIdNotIn(updateIncomeTypeRequest.getCode(),stringToLong(updateIncomeTypeRequest.getId()));
			if(isPresentIncomeTypeCode.isPresent())
				throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "code");
			else{
				IncomeType incomeType = isPresentIncomeType.get();
				incomeType.setName(updateIncomeTypeRequest.getName());
				incomeType.setCode(updateIncomeTypeRequest.getCode());
				incomeType.setDescription(updateIncomeTypeRequest.getDescription());
				incomeType.setStatus(updateIncomeTypeRequest.getStatus());
				incomeType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
				incomeType.setModifiedDate(currentTimestamp);
				incomeType.setSyncTs(currentTimestamp);
				incomeType.setBusinessType(isPresentBusinessType.get());
				incomeType = incomeTypeRepository.saveAndFlush(incomeType);
				return incomeType;
			}
		}
		else
			throw new ValidateRecordException(environment.getProperty(NOT_FOUND),"message");
	}
	
	/**
	 * String value convert to Long
	 * @param String value
     * @return int value
	 */
	private Long stringToLong(String str) {
		return Long.parseLong(str);
	}
	

	@Override
	public List<IncomeType> findByBusinessType(Long id) {
		List<IncomeType> incomeTypeList = new ArrayList<>();
		Optional<BusinessType> businessType = businessTypeRepository.findById(id);	
		
		if(businessType.isPresent()) {
			incomeTypeList = incomeTypeRepository.findByBusinessType(businessType.get());		
		}
		return incomeTypeList;
		
	}


}
