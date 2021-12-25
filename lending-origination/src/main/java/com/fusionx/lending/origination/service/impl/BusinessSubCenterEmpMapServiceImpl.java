package com.fusionx.lending.origination.service.impl;

import java.util.ArrayList;
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
import com.fusionx.lending.origination.domain.BusinessSubCenter;
import com.fusionx.lending.origination.domain.BusinessSubCenterEmpMap;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.BusinessSubCenterEmpMapRepository;
import com.fusionx.lending.origination.repository.BusinessSubCenterProductMapRepository;
import com.fusionx.lending.origination.repository.BusinessSubCenterRepository;
import com.fusionx.lending.origination.resource.BusinessCenterEmpMapAddResource;
import com.fusionx.lending.origination.resource.BusinessCenterEmpMapUpdateResource;
import com.fusionx.lending.origination.resource.BusinessSubCenterEmpMapAddResource;
import com.fusionx.lending.origination.resource.BusinessSubCenterEmpMapUpdateResource;
import com.fusionx.lending.origination.resource.Employee;
import com.fusionx.lending.origination.resource.UserProfileResponse;
import com.fusionx.lending.origination.service.BusinessSubCenterEmpMapService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class BusinessSubCenterEmpMapServiceImpl   extends MessagePropertyBase implements  BusinessSubCenterEmpMapService{

	@Autowired
	private BusinessSubCenterRepository businessSubCenterRepository;	
	
	@Autowired
	private BusinessSubCenterEmpMapRepository businessSubCenterEmpMapRepository;
	
	@Autowired
	private ValidateService validateService;
	
	@Override
	public List<BusinessSubCenterEmpMap> findAll() {
		return businessSubCenterEmpMapRepository.findAll();
	}

	@Override
	public Optional<BusinessSubCenterEmpMap> findById(Long id) {
		return businessSubCenterEmpMapRepository.findById(id);
	}

	@Override
	public List<BusinessSubCenterEmpMap> findByStatus(String status) {
		return businessSubCenterEmpMapRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public List<BusinessSubCenterEmpMap> findByBusinessSubCenterId(Long id) {
		return businessSubCenterEmpMapRepository.findByBusinessSubCenterId(id);
	}
	
	@Override
	public Long saveAndValidateBusinessSubCenterEmpMap(String tenantId, String userName,
			BusinessSubCenterEmpMapAddResource businessSubCenterEmpMapAddResource, Long businessSubCenterId) {
		Optional<BusinessSubCenter> businessSubCenter=businessSubCenterRepository.findById(businessSubCenterId);
		if(!businessSubCenter.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
		}
		if((CommonStatus.ACTIVE.toString()).equals(businessSubCenter.get().getStatus().toString())) {
			throw new ValidateRecordException("inactive business sub center", MESSAGE);
		}	
		
		if(businessSubCenterEmpMapAddResource.getEmployees() != null && !businessSubCenterEmpMapAddResource.getEmployees().isEmpty()) {
			Integer index = 0;
			for(Employee emp : businessSubCenterEmpMapAddResource.getEmployees()) {
				saveOrUpadateEmp(emp, tenantId, businessSubCenter.get(), index);
				index++;
			}
		}
		return businessSubCenter.get().getId();
	}
	
	/*
	 * 
	        	
	  BusinessSubCenterEmpMapAddResource businessSubCenterEmpMapAddResource = null;
	        	
	        	case BUSINESS_SUB_CENTER_EMP_MAP: 
        		businessSubCenterEmpMapAddResource=new BusinessSubCenterEmpMapAddResource();
        		businessSubCenterEmpMapListValidateException(businessSubCenterEmpMapAddResource, ex.getServiceEntity(), ex.getMessage(), ex.getIndex());
	            break;
	            
	            
	            
	private void businessSubCenterEmpMapListValidateException(
			BusinessSubCenterEmpMapAddResource businessSubCenterEmpMapAddResource, ServiceEntity serviceEntity, String message,
			Integer index) {
		
		List<Employee> employeeList =new ArrayList<>();
		for(int i=0;i<=index;i++){
			employeeList.add(i, new Employee());
		}
		switch(serviceEntity) 
		{	 
			case EMP_ID: 
				employeeList.get(index).setEmpId(message);
		         break;
	        default:   
        }
		businessSubCenterEmpMapAddResource.setEmployees(employeeList);
		
		
	}
	 */

	private void saveOrUpadateEmp(Employee emp, String tenantId, BusinessSubCenter businessSubCenter, Integer index) {
		BusinessSubCenterEmpMap businessSubCenterEmpMap = null;
		if(emp.getId() != null && !emp.getId().isEmpty()) {
			Optional<BusinessSubCenterEmpMap> businessSubCenterEmpMapOptional = businessSubCenterEmpMapRepository.findById(validateService.stringToLong(emp.getId()));
			if(!businessSubCenterEmpMapOptional.isPresent()) {
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.PRODUCT_PRODUCT_CATEGORY_DET_ID, ServicePoint.PRODUCT_PRODUCT_CATEGORY, index);
				//throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.PRODUCT_PRODUCT_CATEGORY_DET_ID, ServicePoint.PRODUCT_PRODUCT_CATEGORY, index);
				
			} else {
				businessSubCenterEmpMap = businessSubCenterEmpMapOptional.get();
				businessSubCenterEmpMap.setModifiedDate(validateService.getCreateOrModifyDate());
				businessSubCenterEmpMap.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
				
				if(!(businessSubCenterEmpMap.getBusinessSubCenter().getId()).equals(businessSubCenter.getId())) {
					//throw new DetailListValidateException("imcompatible business sub center id", ServiceEntity.EMP_ID, ServicePoint.BUSINESS_SUB_CENTER_EMP_MAP, index);
					throw new DetailListValidateException("imcompatible business sub center id", ServiceEntity.PRODUCT_PRODUCT_CATEGORY_DET_ID, ServicePoint.PRODUCT_PRODUCT_CATEGORY, index);
					
				}
			}
		} else {
			businessSubCenterEmpMap =new BusinessSubCenterEmpMap();
			businessSubCenterEmpMap.setTenantId(tenantId);
			businessSubCenterEmpMap.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			businessSubCenterEmpMap.setCreatedDate(validateService.getCreateOrModifyDate());
		}
		
		UserProfileResponse userProfileResponse =validateService.validateUserProfileByUserId(tenantId,emp.getEmpId(),emp.getEmpName());
		if(userProfileResponse == null) {
			throw new ValidateRecordException("invalid emp id", MESSAGE);
		}
		
		businessSubCenterEmpMap.setBusinessSubCenter(businessSubCenter);
		if(emp.getEmpId() != null && !emp.getEmpId().isEmpty())
			businessSubCenterEmpMap.setEmpId(Long.parseLong(emp.getEmpId()));
		businessSubCenterEmpMap.setEmpCode(emp.getEmpNo());
		businessSubCenterEmpMap.setStatus(CommonStatus.valueOf(emp.getStatus()));
		businessSubCenterEmpMap.setSyncTs(validateService.getCreateOrModifyDate());
		
		businessSubCenterEmpMapRepository.saveAndFlush(businessSubCenterEmpMap);
		
	}
	
	@Override
	public Long updateAndValidateBusinessCenterEmpMap(String tenantId, String userName, Long businessSubCenterId,
			@Valid BusinessSubCenterEmpMapUpdateResource businessSubCenterEmpMapUpdateResource) {
		
		Optional<BusinessSubCenter> businessSubCenter=businessSubCenterRepository.findById(businessSubCenterId);
		if(!businessSubCenter.isPresent())
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
		
		if(businessSubCenterEmpMapUpdateResource.getEmployees() != null && !businessSubCenterEmpMapUpdateResource.getEmployees().isEmpty()) {
			Integer index = 0;
			for(Employee emp : businessSubCenterEmpMapUpdateResource.getEmployees()) {
				saveOrUpadateEmp(emp, tenantId, businessSubCenter.get(), index);
				index++;
			}
		}
		return businessSubCenter.get().getId();
		
	}
	
}
