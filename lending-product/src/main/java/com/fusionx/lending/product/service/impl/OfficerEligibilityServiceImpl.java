package com.fusionx.lending.product.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.OfficerEligibility;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.OfficerEligibilityRepository;
import com.fusionx.lending.product.resources.OfficerEligibilityResource;
import com.fusionx.lending.product.resources.OfficerEligibilityUpdateResource;
import com.fusionx.lending.product.resources.OfficerType;
import com.fusionx.lending.product.service.OfficerEligibilityHistoryService;
import com.fusionx.lending.product.service.OfficerEligibilityService;
import com.fusionx.lending.product.service.ValidationService;

/**
* Officer Eligibility Service Impl
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   09-06-2020   			   	        	Thamokshi    Created
*    
********************************************************************************************************
*/

@Component
@Transactional(rollbackFor=Exception.class)
public class OfficerEligibilityServiceImpl extends MessagePropertyBase implements OfficerEligibilityService{
	
	@Autowired
	private OfficerEligibilityRepository officerEligibilityRepository;
	
	@Autowired
	private OfficerEligibilityHistoryService officerEligibilityHistoryService;
	
	@Autowired 
	private ValidationService validationService;
	
	@Override
	public List<OfficerEligibility> findAll() {
		return officerEligibilityRepository.findAll();
	}

	@Override
	public Optional<OfficerEligibility> findById(Long id) {
		Optional<OfficerEligibility> isPresentOfficerEligibility = officerEligibilityRepository.findById(id);
		if (isPresentOfficerEligibility.isPresent()) {
			return Optional.ofNullable(isPresentOfficerEligibility.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<OfficerEligibility> findByOfficerTypeId(Long officerTypeId) {
		return officerEligibilityRepository.findByOfficerTypeId(officerTypeId);
	}

	@Override
	public Optional<OfficerEligibility> findByCode(String code) {

		Optional<OfficerEligibility> isPresentOfficerEligibility = officerEligibilityRepository.findByCode(code);
		if (isPresentOfficerEligibility.isPresent()) {
			return Optional.ofNullable(isPresentOfficerEligibility.get());
		}
		else {
			return Optional.empty();
		}
		
	}

	@Override
	public List<OfficerEligibility> findByStatus(String status) {
		return officerEligibilityRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public OfficerEligibility addOfficerEligibility(String tenantId, OfficerEligibilityResource officerEligibilityAddResource) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		OfficerEligibility officerEligibility = new OfficerEligibility();
		officerEligibility.setTenantId(tenantId);
		
		Optional<OfficerEligibility>isPresentOfficerEligibilityCode = officerEligibilityRepository.findByCode(officerEligibilityAddResource.getCode());
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate code*******************");
		if (isPresentOfficerEligibilityCode.isPresent()) 
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate officer type*******************");
		OfficerType officerType = validationService.validateOfficerType(tenantId, officerEligibilityAddResource.getOfficerTypeId(), officerEligibilityAddResource.getOfficerTypeName());
				
		if(!officerEligibilityAddResource.getMinAmount().isEmpty()) {
			BigDecimal  value1 = new BigDecimal(officerEligibilityAddResource.getMinAmount()); 
			int res1 = value1.compareTo(BigDecimal.ZERO);
			
			if((res1 == -1) || (res1 == -0))
				throw new ValidateRecordException(environment.getProperty("common.value-cannot-lessthan-zero"),"eligibilityMinAmount");
			else
				officerEligibility.setMinAmount(formatDecimal(officerEligibilityAddResource.getMinAmount()));
		}
		
		if(!officerEligibilityAddResource.getMaxAmount().isEmpty()) {
			BigDecimal  value2 = new BigDecimal(officerEligibilityAddResource.getMinAmount()); 
			int res2 = value2.compareTo(BigDecimal.ZERO);
			if((res2 == -1) || (res2 == -0))
				throw new ValidateRecordException(environment.getProperty("common.value-cannot-lessthan-zero"),"eligibilityMaxAmount");		
			else if(formatDecimal(officerEligibilityAddResource.getMaxAmount()).compareTo(formatDecimal(officerEligibilityAddResource.getMinAmount())) < 0)
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"),"eligibilityMaxAmount");
			else
				officerEligibility.setMaxAmount(formatDecimal(officerEligibilityAddResource.getMaxAmount()));
		}
		officerEligibility.setOfficerTypeId(Long.parseLong(officerEligibilityAddResource.getOfficerTypeId()));
		officerEligibility.setOfficerType(officerEligibilityAddResource.getOfficerTypeName());
		officerEligibility.setCode(officerEligibilityAddResource.getCode());
		officerEligibility.setStatus(CommonStatus.valueOf(officerEligibilityAddResource.getStatus()));
		officerEligibility.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		officerEligibility.setCreatedDate(currentTimestamp);
		officerEligibility.setSyncTs(currentTimestamp);
		officerEligibility = officerEligibilityRepository.saveAndFlush(officerEligibility);
		return officerEligibility;
		
	}

	@Override
	public OfficerEligibility updateOfficerEligibility(String tenantId, OfficerEligibilityUpdateResource officerEligibilityUpdateResource) {
		
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		Optional<OfficerEligibility> isPresentOfficerEligibility = officerEligibilityRepository.findById(Long.parseLong((officerEligibilityUpdateResource.getId())));
		if(isPresentOfficerEligibility.isPresent()) {
			
			if(!isPresentOfficerEligibility.get().getVersion().equals(Long.parseLong(officerEligibilityUpdateResource.getVersion())))
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
			
			LoggerRequest.getInstance().logInfo("************************** Code Can Not Update *****************************");
	        if (!isPresentOfficerEligibility.get().getCode().equals(officerEligibilityUpdateResource.getCode())) {
	            throw new ValidateRecordException(environment.getProperty("common.code-update"), "code");
	        }
			
			LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate officer type*******************");
			OfficerType officerType = validationService.validateOfficerType(tenantId, officerEligibilityUpdateResource.getOfficerTypeId(), officerEligibilityUpdateResource.getOfficerTypeName());
					
			OfficerEligibility officerEligibility = isPresentOfficerEligibility.get();
			officerEligibilityHistoryService.insertOfficerEligibilityHistory(tenantId, officerEligibility, LogginAuthentcation.getInstance().getUserName());
			
//			Optional<OfficerEligibility>isPresentOfficerEligibilityCode = officerEligibilityRepository.findByCode(officerEligibilityUpdateResource.getCode());
//			if (isPresentOfficerEligibilityCode.isPresent()) 
//				//throw new ValidateRecordException(environment.getProperty("otherEligibilityCode.duplicate"),"message");
//				throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
			
			if(!officerEligibilityUpdateResource.getMinAmount().isEmpty()) {
				BigDecimal  value1 = new BigDecimal(officerEligibilityUpdateResource.getMinAmount()); 
				int res1 = value1.compareTo(BigDecimal.ZERO);
				
				if((res1 == -1) || (res1 == -0))
					throw new ValidateRecordException(environment.getProperty("common.value-cannot-lessthan-zero"),"eligibilityMinAmount");
				else
					officerEligibility.setMinAmount(formatDecimal(officerEligibilityUpdateResource.getMinAmount()));
			}
			
			if(!officerEligibilityUpdateResource.getMaxAmount().isEmpty()) {
				BigDecimal  value2 = new BigDecimal(officerEligibilityUpdateResource.getMinAmount()); 
				int res2 = value2.compareTo(BigDecimal.ZERO);
				if((res2 == -1) || (res2 == -0))
					throw new ValidateRecordException(environment.getProperty("common.value-cannot-lessthan-zero"),"eligibilityMaxAmount");		
				else if(formatDecimal(officerEligibilityUpdateResource.getMaxAmount()).compareTo(formatDecimal(officerEligibilityUpdateResource.getMinAmount())) < 0)
					throw new ValidateRecordException(environment.getProperty("common.invalid-value"),"eligibilityMaxAmount");
				else
					officerEligibility.setMaxAmount(formatDecimal(officerEligibilityUpdateResource.getMaxAmount()));
			}
			officerEligibility.setOfficerTypeId(Long.parseLong(officerEligibilityUpdateResource.getOfficerTypeId()));
			officerEligibility.setOfficerType(officerEligibilityUpdateResource.getOfficerTypeName());
			officerEligibility.setTenantId(tenantId);
			officerEligibility.setCode(officerEligibilityUpdateResource.getCode());
			officerEligibility.setStatus(CommonStatus.valueOf(officerEligibilityUpdateResource.getStatus()));
			officerEligibility.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			officerEligibility.setModifiedDate(currentTimestamp);
			officerEligibility.setVersion(Long.parseLong(officerEligibilityUpdateResource.getVersion()));
			officerEligibility.setSyncTs(currentTimestamp);
			officerEligibility = officerEligibilityRepository.saveAndFlush(officerEligibility);
			return officerEligibility;
		}
		else
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"),"id");
		
	}
	
	/**
	 * String value convert to Decimal
	 * @param String value
	 */
	private BigDecimal formatDecimal(String amt) {
		return new BigDecimal(amt);
	}

}
