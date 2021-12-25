package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.BusinessCenter;
import com.fusionx.lending.product.domain.BusinessSubCenter;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.BusinessCenterRepository;
import com.fusionx.lending.product.repository.BusinessSubCenterRepository;
import com.fusionx.lending.product.resources.BusinessSubCenterAddResource;
import com.fusionx.lending.product.resources.BusinessSubCenterUpdateResource;
import com.fusionx.lending.product.resources.UserProfileResponse;
import com.fusionx.lending.product.service.BusinessSubCenterService;
import com.fusionx.lending.product.service.ValidationService;



/**
 * API Service related to Business Sub Center.
 *
 * @author Nipun Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        28-08-2021      	             FXL-649          Nipun Dilhan      Created
 * <p>
 *
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class BusinessSubCenterServiceImpl  extends MessagePropertyBase implements BusinessSubCenterService{

	protected static final String BUSINESS_CENTER_ID = "businessCenterId";

	@Autowired
	private BusinessSubCenterRepository businessSubCenterRepository;	
	@Autowired
	private BusinessCenterRepository businessCenterRepository;
	
	@Autowired
	private ValidationService validateService;
	
	
	@Override
	public BusinessSubCenter create(String tenantId,BusinessSubCenterAddResource businessSubCenterAddResource) {
		
		
		
		Optional<BusinessCenter> businessCenterOptional = businessCenterRepository.findById(Long.parseLong(businessSubCenterAddResource.getBusinessCenterId()));
		if(!businessCenterOptional.isPresent()){
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), BUSINESS_CENTER_ID);
		}
		if(!(businessSubCenterAddResource.getBusinessCenterName()).equals(businessCenterOptional.get().getName())) {
			throw new ValidateRecordException(environment.getProperty("businessSubCenter.name-incompatible"), MESSAGE);
		}
		
		if(!(CommonStatus.ACTIVE.toString()).equals(businessCenterOptional.get().getStatus())) {
			throw new ValidateRecordException(environment.getProperty("COMMON_INACTIVE"), BUSINESS_CENTER_ID);
		}
		
		Optional<BusinessSubCenter> businessSubCenterOptional = businessSubCenterRepository.findByCode(businessSubCenterAddResource.getCode());
		if(businessSubCenterOptional.isPresent())
			throw new ValidateRecordException(environment.getProperty("common.unique"), "CODE");
		
		UserProfileResponse userProfileResponse =validateService.validateUserProfileByUserId(tenantId,businessSubCenterAddResource.getEmpUserId(),businessSubCenterAddResource.getEmpName());
		if(userProfileResponse.getId()!=Long.parseLong(businessSubCenterAddResource.getEmpId())) {
			throw new ValidateRecordException(environment.getProperty("common.not-match"),"empId");
		}
		if(!userProfileResponse.getEmployeeNumber().equals(businessSubCenterAddResource.getEmpNo())) {
			throw new ValidateRecordException(environment.getProperty("common.not-match"),"empNo");
		}
		
		BusinessSubCenter businessSubCenter = new BusinessSubCenter();
		businessSubCenter.setTenantId(tenantId);
		businessSubCenter.setBusinessCenter(businessCenterOptional.get());
		businessSubCenter.setCode(businessSubCenterAddResource.getCode());
		businessSubCenter.setName(businessSubCenterAddResource.getName());
		businessSubCenter.setEmpId(userProfileResponse.getId());
		businessSubCenter.setEmpNo(userProfileResponse.getEmployeeNumber());
		businessSubCenter.setUserId(userProfileResponse.getUserId());
		businessSubCenter.setUserName(userProfileResponse.getUserName());
		businessSubCenter.setMaxClientPerSubCenter(Long.parseLong(businessSubCenterAddResource.getMaxClientPerSubCenter()));
		businessSubCenter.setMaxSubCenterLimit(Long.parseLong(businessSubCenterAddResource.getMaxSubCenterLimit()));
		businessSubCenter.setCreatedDate(validateService.getCreateOrModifyDate());
		businessSubCenter.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		businessSubCenter.setStatus(CommonStatus.valueOf(businessSubCenterAddResource.getStatus()));
		businessSubCenter.setSyncTs(validateService.getCreateOrModifyDate());		
		businessSubCenter = businessSubCenterRepository.save(businessSubCenter);
		
		return businessSubCenter;
	}
	
	
	@Override
	public BusinessSubCenter update(String tenantId,Long id,BusinessSubCenterUpdateResource businessSubCenterUpdateResource) {
		
		Optional<BusinessSubCenter> businessSubCenterOptional = businessSubCenterRepository.findById(id);
		if(!businessSubCenterOptional.isPresent()){
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "ID");
		}
		
		Optional<BusinessCenter> businessCenterOptional = businessCenterRepository.findById(Long.parseLong(businessSubCenterUpdateResource.getBusinessCenterId()));
		if(!businessCenterOptional.isPresent()){
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), BUSINESS_CENTER_ID);
		}
		if(!(businessSubCenterUpdateResource.getBusinessCenterName()).equals(businessCenterOptional.get().getName())) {
			throw new ValidateRecordException(environment.getProperty("businessSubCenter.name-incompatible"), MESSAGE);
		}
		if((businessCenterOptional.get().getStatus()).equals(CommonStatus.INACTIVE.toString())) {
			throw new ValidateRecordException(environment.getProperty("COMMON_INACTIVE"), BUSINESS_CENTER_ID);
		}
		
		
		if(!(businessSubCenterOptional.get().getVersion().toString().equals(businessSubCenterUpdateResource.getVersion()))){
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), VERSION);
		}
	
		UserProfileResponse userProfileResponse =validateService.validateUserProfileByUserId(tenantId,businessSubCenterUpdateResource.getEmpUserId(),businessSubCenterUpdateResource.getEmpName());
		if(userProfileResponse.getId()!=Long.parseLong(businessSubCenterUpdateResource.getEmpId())) {
			throw new ValidateRecordException(environment.getProperty("common.not-match"),"empId");
		}
		if(!userProfileResponse.getEmployeeNumber().equals(businessSubCenterUpdateResource.getEmpNo())) {
			throw new ValidateRecordException(environment.getProperty("common.not-match"),"empNo");
		}
		BusinessSubCenter businessSubCenter = businessSubCenterOptional.get();
		businessSubCenter.setTenantId(tenantId);
		businessSubCenter.setBusinessCenter(businessCenterOptional.get());
		businessSubCenter.setName(businessSubCenterUpdateResource.getName());
		businessSubCenter.setEmpId(userProfileResponse.getId());
		businessSubCenter.setEmpNo(userProfileResponse.getEmployeeNumber());
		businessSubCenter.setUserId(userProfileResponse.getUserId());
		businessSubCenter.setUserName(userProfileResponse.getUserName());
		businessSubCenter.setMaxClientPerSubCenter(Long.parseLong(businessSubCenterUpdateResource.getMaxClientPerSubCenter()));
		businessSubCenter.setMaxSubCenterLimit(Long.parseLong(businessSubCenterUpdateResource.getMaxSubCenterLimit()));
		businessSubCenter.setModifiedDate(validateService.getCreateOrModifyDate());
		businessSubCenter.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		businessSubCenter.setStatus(CommonStatus.valueOf(businessSubCenterUpdateResource.getStatus()));
		businessSubCenter.setSyncTs(validateService.getCreateOrModifyDate());		
		businessSubCenter = businessSubCenterRepository.save(businessSubCenter);
		
		return businessSubCenter;
	}
	
	public List<BusinessSubCenter> findByBusinessCenterCode(String code) {
		Optional<BusinessCenter> businessCenterOptional = businessCenterRepository.findByCode(code);
		if(!businessCenterOptional.isPresent()){
			return null;
		}
		
		List<BusinessSubCenter> businessSubCenterList = businessSubCenterRepository.findAllByBusinessCenterId(businessCenterOptional.get().getId());
		return businessSubCenterList;
	}
	

}
