package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.BusinessCenter;
import com.fusionx.lending.origination.domain.BusinessCenterEmpMap;
import com.fusionx.lending.origination.domain.BusinessCenterProductMap;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.BusinessCenterEmpMapRepository;
import com.fusionx.lending.origination.repository.BusinessCenterRepository;
import com.fusionx.lending.origination.resource.BusinessCenterEmpMapAddResource;
import com.fusionx.lending.origination.resource.BusinessCenterEmpMapUpdateResource;
import com.fusionx.lending.origination.resource.Employee;
import com.fusionx.lending.origination.resource.ProductRequestResource;
import com.fusionx.lending.origination.resource.UserProfileResponse;
import com.fusionx.lending.origination.service.BusinessCenterEmpMapService;
import com.fusionx.lending.origination.service.ValidateService;

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
	ValidateService validateService;

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
				saveOrUpadateEmp(emp, tenantId, businessCenter.get(), index);
				index++;
			}
		}
		return businessCenter.get().getId();
	}

	private void saveOrUpadateEmp(Employee emp, String tenantId, BusinessCenter businessCenter, Integer index) {
		BusinessCenterEmpMap product = new BusinessCenterEmpMap();
		if(emp.getId() != null && !emp.getId().isEmpty()) {
			Optional<BusinessCenterEmpMap> opBusinessCenterEmpMap = repo.findById(validateService.stringToLong(emp.getId()));
			UserProfileResponse user=validateService.validateUserProfileByUserId(tenantId, emp.getEmpUserId(), emp.getEmpName());
			if(user.getId()!=Long.parseLong(emp.getEmpId())) {
				throw new ValidateRecordException(environment.getProperty("user.invalid"),"empId");
			}
			if(user.getEmployeeNumber()!=emp.getEmpNo() || !user.getEmployeeNumber().equals(emp.getEmpNo())) {
				throw new ValidateRecordException(environment.getProperty("user.invalid"),"empNo");
			}
			if(!opBusinessCenterEmpMap.isPresent()) {
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.PRODUCT_PRODUCT_CATEGORY_DET_ID, ServicePoint.PRODUCT_PRODUCT_CATEGORY, index);
			} else {
				product = opBusinessCenterEmpMap.get();
				product.setModifiedDate(validateService.getCreateOrModifyDate());
				product.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			}
		} else {
			product.setTenantId(tenantId);
			product.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			product.setCreatedDate(validateService.getCreateOrModifyDate());
		}
		product.setBusinessCenter(businessCenter);
		if(emp.getEmpId() != null && !emp.getEmpId().isEmpty())
			product.setEmpId(Long.parseLong(emp.getEmpId()));
		product.setEmpUserId(emp.getEmpUserId());
		product.setEmpNo(emp.getEmpNo());
		product.setStatus(CommonStatus.valueOf(emp.getStatus()));
		product.setSyncTs(validateService.getCreateOrModifyDate());
		
		repo.saveAndFlush(product);
		
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
				saveOrUpadateEmp(emp, tenantId, businessCenter.get(), index);
				index++;
			}
		}
		return businessCenter.get().getId();
		
	}
}
