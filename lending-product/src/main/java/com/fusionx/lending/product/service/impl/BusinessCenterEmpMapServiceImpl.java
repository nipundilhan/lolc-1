package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.BusinessCenter;
import com.fusionx.lending.product.domain.BusinessCenterEmpMap;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.ServicePoint;
import com.fusionx.lending.product.exception.DetailListValidateException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.BusinessCenterEmpMapRepository;
import com.fusionx.lending.product.repository.BusinessCenterRepository;
import com.fusionx.lending.product.resources.BusinessCenterEmpMapAddResource;
import com.fusionx.lending.product.resources.BusinessCenterEmpMapUpdateResource;
import com.fusionx.lending.product.resources.Employee;
import com.fusionx.lending.product.resources.UserProfileResponse;
import com.fusionx.lending.product.service.BusinessCenterEmpMapService;
import com.fusionx.lending.product.service.ValidationService;



/**
 * Business Center emp
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-08-2021      		     FX-	   Thsmokshi      Created
 *    
 ********************************************************************************************************
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class BusinessCenterEmpMapServiceImpl extends MessagePropertyBase implements BusinessCenterEmpMapService{

	@Autowired
	BusinessCenterEmpMapRepository repo;
	
	@Autowired
	BusinessCenterRepository businessCenterRepository;
	
	@Autowired
	ValidationService validateService;

	@Override
	public List<BusinessCenterEmpMap> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<BusinessCenterEmpMap> findById(Long id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
		
	}

	@Override
	public List<BusinessCenterEmpMap> findByStatus(String status) {
		// TODO Auto-generated method stub
		return repo.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public List<BusinessCenterEmpMap> findByBusinessCenterId(Long id) {
		// TODO Auto-generated method stub
		return repo.findByBusinessCenterId(id);
	}

	@Override
	public Long saveAndValidateBusinessCenterEmpMap(String tenantId, String userName,
			@Valid BusinessCenterEmpMapAddResource businessCenterEmpMapAddResource, Long businessCenterId) {
		Optional<BusinessCenter> businessCenter=businessCenterRepository.findById(businessCenterId);
		if(!businessCenter.isPresent())
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
		if(businessCenterEmpMapAddResource.getEmployees() != null && !businessCenterEmpMapAddResource.getEmployees().isEmpty()) {
			Integer index = 0;
			for(Employee emp : businessCenterEmpMapAddResource.getEmployees()) {
				saveEmp(emp, tenantId, businessCenter.get(), index);
				index++;
			}
		}
		return businessCenter.get().getId();
	}

	private void saveEmp(Employee emp, String tenantId, BusinessCenter businessCenter, Integer index) {
		BusinessCenterEmpMap empMap = new BusinessCenterEmpMap();
		if(emp.getId()!=null) {
			if(!emp.getId().isEmpty()) {
				Optional<BusinessCenterEmpMap> opBusinessCenterEmpMap = repo.findById(validateService.stringToLong(emp.getId()));
				UserProfileResponse user=validateService.validateUserProfileByUserId(tenantId, emp.getEmpUserId(), emp.getEmpName());
				if(user.getId()!=Long.parseLong(emp.getEmpId())) {
					throw new ValidateRecordException(environment.getProperty("common.invalid-value"),"empId");
				}
				if(!user.getEmployeeNumber().equals(emp.getEmpNo())) {
					throw new ValidateRecordException(environment.getProperty("common.invalid-value"),"empNo");
				}
				if(!opBusinessCenterEmpMap.isPresent()) {
					throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.PRODUCT_PRODUCT_CATEGORY_DET_ID, ServicePoint.PRODUCT_PRODUCT_CATEGORY, index);
				} else {
					empMap = opBusinessCenterEmpMap.get();
					empMap.setModifiedDate(validateService.getCreateOrModifyDate());
					empMap.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
				}
			}
		} else {
			UserProfileResponse user=validateService.validateUserProfileByUserId(tenantId, emp.getEmpUserId(), emp.getEmpName());
			if(user.getId()!=Long.parseLong(emp.getEmpId())) {
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"),"empId");
			}
			if(!user.getEmployeeNumber().equals(emp.getEmpNo())) {
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"),"empNo");
			}
			empMap.setTenantId(tenantId);
			empMap.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			empMap.setCreatedDate(validateService.getCreateOrModifyDate());
		}
		empMap.setBusinessCenter(businessCenter);
		if(emp.getEmpId() != null && !emp.getEmpId().isEmpty())
			empMap.setEmpId(Long.parseLong(emp.getEmpId()));
		empMap.setEmpUserId(emp.getEmpUserId());
		empMap.setEmpNo(emp.getEmpNo());
		empMap.setStatus(CommonStatus.valueOf(emp.getStatus()));
		empMap.setSyncTs(validateService.getCreateOrModifyDate());
		
		repo.saveAndFlush(empMap);
		
	}
	
	private void upadateEmp(Employee emp, String tenantId, BusinessCenter businessCenter, Integer index) {
		BusinessCenterEmpMap empMap = new BusinessCenterEmpMap();
		if(emp.getId()!=null) {
			if(!emp.getId().isEmpty()) {
				Optional<BusinessCenterEmpMap> opBusinessCenterEmpMap = repo.findById(validateService.stringToLong(emp.getId()));
				
				LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate version*******************");
				if(!opBusinessCenterEmpMap.get().getVersion().equals(Long.parseLong(emp.getVersion())))
					throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
				
				UserProfileResponse user=validateService.validateUserProfileByUserId(tenantId, emp.getEmpUserId(), emp.getEmpName());
				if(user.getId()!=Long.parseLong(emp.getEmpId())) {
					throw new ValidateRecordException(environment.getProperty("common.invalid-value"),"empId");
				}
				if(!user.getEmployeeNumber().equals(emp.getEmpNo())) {
					throw new ValidateRecordException(environment.getProperty("common.invalid-value"),"empNo");
				}
				if(!opBusinessCenterEmpMap.isPresent()) {
					throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.PRODUCT_PRODUCT_CATEGORY_DET_ID, ServicePoint.PRODUCT_PRODUCT_CATEGORY, index);
				} else {
					empMap = opBusinessCenterEmpMap.get();
					empMap.setModifiedDate(validateService.getCreateOrModifyDate());
					empMap.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
				}
			}
		} else {
			UserProfileResponse user=validateService.validateUserProfileByUserId(tenantId, emp.getEmpUserId(), emp.getEmpName());
			if(user.getId()!=Long.parseLong(emp.getEmpId())) {
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"),"empId");
			}
			if(!user.getEmployeeNumber().equals(emp.getEmpNo())) {
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"),"empNo");
			}
			empMap.setTenantId(tenantId);
			empMap.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			empMap.setCreatedDate(validateService.getCreateOrModifyDate());
		}
		empMap.setBusinessCenter(businessCenter);
		if(emp.getEmpId() != null && !emp.getEmpId().isEmpty())
			empMap.setEmpId(Long.parseLong(emp.getEmpId()));
		empMap.setEmpUserId(emp.getEmpUserId());
		empMap.setEmpNo(emp.getEmpNo());
		empMap.setStatus(CommonStatus.valueOf(emp.getStatus()));
		empMap.setSyncTs(validateService.getCreateOrModifyDate());
		
		repo.saveAndFlush(empMap);
		
	}
		

	@Override
	public boolean existsById(Long id) {
		return repo.existsById(id);
	}

	@Override
	public Long updateAndValidateBusinessCenterEmpMap(String tenantId, String userName, Long businessCenterId,
			@Valid BusinessCenterEmpMapUpdateResource businessCenterEmpMapUpdateResource) {
		Optional<BusinessCenter> businessCenter=businessCenterRepository.findById(businessCenterId);
		if(!businessCenter.isPresent())
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
		if(businessCenterEmpMapUpdateResource.getEmployees() != null && !businessCenterEmpMapUpdateResource.getEmployees().isEmpty()) {
			Integer index = 0;
			for(Employee emp : businessCenterEmpMapUpdateResource.getEmployees()) {
				upadateEmp(emp, tenantId, businessCenter.get(), index);
				index++;
			}
		}
		return businessCenter.get().getId();
		
	}
}
