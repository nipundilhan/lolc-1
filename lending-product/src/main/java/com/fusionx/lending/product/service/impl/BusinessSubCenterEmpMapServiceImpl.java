package com.fusionx.lending.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.BusinessSubCenter;
import com.fusionx.lending.product.domain.BusinessSubCenterEmpMap;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.ServicePoint;
import com.fusionx.lending.product.exception.DetailListValidateException;
import com.fusionx.lending.product.exception.ListRecordPrimitiveValidateException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.BusinessSubCenterEmpMapRepository;
import com.fusionx.lending.product.repository.BusinessSubCenterRepository;
import com.fusionx.lending.product.resources.BusinessSubCenterEmpMapAddResource;
import com.fusionx.lending.product.resources.BusinessSubCenterEmpMapUpdateResource;
import com.fusionx.lending.product.resources.Employee;
import com.fusionx.lending.product.resources.UserProfileResponse;
import com.fusionx.lending.product.service.BusinessSubCenterEmpMapService;
import com.fusionx.lending.product.service.ValidationService;



@Component
@Transactional(rollbackFor = Exception.class)
public class BusinessSubCenterEmpMapServiceImpl   extends MessagePropertyBase implements  BusinessSubCenterEmpMapService{

	@Autowired
	private BusinessSubCenterRepository businessSubCenterRepository;	
	
	@Autowired
	private BusinessSubCenterEmpMapRepository businessSubCenterEmpMapRepository;
	
	@Autowired
	private ValidationService validateService;
	
	 public static final String EMPLOYEES = "employees";
	
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
		if(!(CommonStatus.ACTIVE.toString()).equals(businessSubCenter.get().getStatus().toString())) {
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
	

	private void saveOrUpadateEmp(Employee emp, String tenantId, BusinessSubCenter businessSubCenter, Integer index) {
		BusinessSubCenterEmpMap businessSubCenterEmpMap = null;
		if(emp.getId() != null && !emp.getId().isEmpty()) {
			Optional<BusinessSubCenterEmpMap> businessSubCenterEmpMapOptional = businessSubCenterEmpMapRepository.findById(validateService.stringToLong(emp.getId()));
			if(!businessSubCenterEmpMapOptional.isPresent()) {
				throw new ListRecordPrimitiveValidateException(environment.getProperty(COMMON_INVALID_VALUE),null,EMPLOYEES,index,"businessSubCenterEmpId");	
			} else {
				businessSubCenterEmpMap = businessSubCenterEmpMapOptional.get();
				businessSubCenterEmpMap.setModifiedDate(validateService.getCreateOrModifyDate());
				businessSubCenterEmpMap.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
				
				if(!(businessSubCenterEmpMap.getBusinessSubCenter().getId()).equals(businessSubCenter.getId())) {
					throw new ListRecordPrimitiveValidateException(environment.getProperty(COMMON_INVALID_VALUE),null,EMPLOYEES,index,"businessSubCenterId");
				}
				if(!(emp.getVersion()).equals(businessSubCenterEmpMap.getVersion().toString())) {
					
					throw new ListRecordPrimitiveValidateException(environment.getProperty(COMMON_INVALID_VALUE),null,EMPLOYEES,index,VERSION);
				}
			}
		} else {
			businessSubCenterEmpMap =new BusinessSubCenterEmpMap();
			businessSubCenterEmpMap.setTenantId(tenantId);
			businessSubCenterEmpMap.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			businessSubCenterEmpMap.setCreatedDate(validateService.getCreateOrModifyDate());
		}
		
		UserProfileResponse userProfileResponse =validateService.validateUserProfileByUserId(tenantId,emp.getEmpUserId(),emp.getEmpName());
		if(userProfileResponse == null) {
			throw new ValidateRecordException("invalid emp id", MESSAGE);
		}
		if(userProfileResponse.getId()!=Long.parseLong(emp.getEmpId())) {
			throw new ListRecordPrimitiveValidateException(environment.getProperty(COMMON_INVALID_VALUE),null,EMPLOYEES,index,"empId");
		}
		if(!userProfileResponse.getEmployeeNumber().equals(emp.getEmpNo())) {
			throw new ListRecordPrimitiveValidateException(environment.getProperty(COMMON_INVALID_VALUE),null,EMPLOYEES,index,"empNo");
		}
		
		businessSubCenterEmpMap.setBusinessSubCenter(businessSubCenter);
		if(emp.getEmpId() != null && !emp.getEmpId().isEmpty()) {
			businessSubCenterEmpMap.setEmpUserId(emp.getEmpUserId());
			businessSubCenterEmpMap.setEmpId(userProfileResponse.getId());
			businessSubCenterEmpMap.setEmpNo(userProfileResponse.getEmployeeNumber());
			businessSubCenterEmpMap.setEmpName(userProfileResponse.getUserName());
		}
		
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
