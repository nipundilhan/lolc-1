package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.BusinessCenter;
import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.BusinessCenterRepository;
import com.fusionx.lending.origination.resource.BusinessCenterAddResource;
import com.fusionx.lending.origination.resource.BusinessCenterUpdateResource;
import com.fusionx.lending.origination.resource.CommonBranchResponseResource;
import com.fusionx.lending.origination.resource.RepaymentFrequencyResponse;
import com.fusionx.lending.origination.resource.UserProfileResponse;
import com.fusionx.lending.origination.service.BusinessCenterService;
import com.fusionx.lending.origination.service.ValidateService;

/**
 * Business Center
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
public class BusinessCenterServiceImpl  extends MessagePropertyBase implements BusinessCenterService{

	@Autowired
	BusinessCenterRepository businessCenterRepository;
	
	@Autowired
	ValidateService validateService;

	@Override
	public List<BusinessCenter> findAll() {
		return businessCenterRepository.findAll();
	}

	@Override
	public Optional<BusinessCenter> findById(Long id) {
		return businessCenterRepository.findById(id);
	}

	@Override
	public List<BusinessCenter> findByStatus(String status) {
		return businessCenterRepository.findByStatus(status);
	}

	@Override
	public List<BusinessCenter> findByName(String name) {
		return businessCenterRepository.findByNameContaining(name);
	}

	@Override
	public Optional<BusinessCenter> findByCode(String code) {
		return businessCenterRepository.findByCode(code);
	}

	@Override
	public Long saveAndValidateBusinessCenter(String tenantId, String userName,
			@Valid BusinessCenterAddResource businessCenterAddResource) {
		
		if(businessCenterRepository.existsByCodeAndStatus(businessCenterAddResource.getCode(), CommonStatus.ACTIVE.toString()))
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
		
		CommonBranchResponseResource branch=validateService.validateCommonBranch(tenantId, businessCenterAddResource.getBranchId(), businessCenterAddResource.getBranchName());
		if(branch.getBrhCode()!=businessCenterAddResource.getBranchCode() || branch.getBrhCode().equals(businessCenterAddResource.getBranchCode())) {
			throw new DetailValidateException(environment.getProperty("common.not-match"),ServiceEntity.BRANCH_ID, ServicePoint.LEAD_INFOR);

		}
		UserProfileResponse u=validateService.validateUserProfileByUserId(tenantId, businessCenterAddResource.getEmpUserId(), businessCenterAddResource.getEmpName());
		if(u.getId()!=Long.parseLong(businessCenterAddResource.getEmpId())) {
			throw new ValidateRecordException(environment.getProperty("user.invalid"),"empId");
		}
		if(u.getEmployeeNumber()!=businessCenterAddResource.getEmpNo() || !u.getEmployeeNumber().equals(businessCenterAddResource.getEmpNo())) {
			throw new ValidateRecordException(environment.getProperty("user.invalid"),"empNo");
		}
		RepaymentFrequencyResponse f=validateService.validateRepaymentFrequency(tenantId, businessCenterAddResource.getCollectionFrequencyId());
		if(f.getName()!=businessCenterAddResource.getCollectionFrequency() || !f.getName().equals(businessCenterAddResource.getCollectionFrequency())) {
			throw new ValidateRecordException(environment.getProperty("common.not-available"),"repaymentFrequencyId");

		}
		BusinessCenter businessCenter = new BusinessCenter();
		businessCenter.setTenantId(tenantId);
		businessCenter.setCode(businessCenterAddResource.getCode());
		businessCenter.setName(businessCenterAddResource.getName());
		businessCenter.setCenterHead(businessCenterAddResource.getCenterHead());
		businessCenter.setStatus(businessCenterAddResource.getStatus());
		businessCenter.setCreatedUser(userName);
//		businessCenter.setAdressLine1(businessCenterAddResource.getAdressLine1());
//		businessCenter.setAdressLine2(businessCenterAddResource.getAdressLine2());
//		businessCenter.setAdressLine3(businessCenterAddResource.getAdressLine3());
//		businessCenter.setAdressLine4(businessCenterAddResource.getAdressLine4());
		businessCenter.setBranchId(Long.parseLong(businessCenterAddResource.getBranchId()));
		businessCenter.setBranchCode(branch.getBrhCode());
		if(businessCenterAddResource.getCenterLimit()!=null && !businessCenterAddResource.getCenterLimit().isEmpty())
		businessCenter.setCenterLimit(Long.parseLong(businessCenterAddResource.getCenterLimit()));
		businessCenter.setCollectionFrequency(businessCenterAddResource.getCollectionFrequency());
		businessCenter.setCollectionFrequencyId(Long.parseLong(businessCenterAddResource.getCollectionFrequencyId()));
		businessCenter.setContactNo(businessCenterAddResource.getContactNo());
		businessCenter.setEmpId(Long.parseLong(businessCenterAddResource.getEmpId()));
		businessCenter.setEmpNo(businessCenterAddResource.getEmpNo());
		businessCenter.setEmpUserId(businessCenterAddResource.getEmpUserId());
		if(businessCenterAddResource.getMaxNoOfSubCenter()!=null && !businessCenterAddResource.getMaxNoOfSubCenter().isEmpty())
		businessCenter.setMaxClientPerSubCenter(Long.parseLong(businessCenterAddResource.getMaxClientPerSubCenter()));
		if(businessCenterAddResource.getMaxNoOfSubCenter()!=null && !businessCenterAddResource.getMaxNoOfSubCenter().isEmpty())
		businessCenter.setMaxNoOfSubCenter(Long.parseLong(businessCenterAddResource.getMaxNoOfSubCenter()));
		businessCenter.setCreatedDate(getCreateOrModifyDate());
		businessCenter.setSyncTs(getCreateOrModifyDate());
		businessCenter = businessCenterRepository.saveAndFlush(businessCenter);
		return businessCenter.getId();
	}

	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}
	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return businessCenterRepository.existsById(id);
	}

	@Override
	public void updateAndValidateBusinessCenter(String tenantId, String userName, Long id,
			@Valid BusinessCenterUpdateResource businessCenterUpdateResource) {
		LoggerRequest.getInstance().logInfo("BusinessType********************************Validate Version*********************************************");
		Optional<BusinessCenter> isPresentbusinessCenter = businessCenterRepository.findById(id);
		if(!isPresentbusinessCenter.get().getVersion().equals(Long.parseLong(businessCenterUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
		
		
		LoggerRequest.getInstance().logInfo("businessCenter********************************Validate Code*********************************************");
		/*Optional<BusinessCenter> isPresentbusinessCenterByCode = businessCenterRepository.findByCodeAndIdNotIn(businessCenterUpdateResource.getCode(), id);
		if (isPresentbusinessCenterByCode.isPresent())
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");*/
		if(!isPresentbusinessCenter.get().getCode().equals(businessCenterUpdateResource.getCode()) || isPresentbusinessCenter.get().getCode()!=businessCenterUpdateResource.getCode()) {
			throw new ValidateRecordException(environment.getProperty(CAN_NOT_UPDATE), "code");
		}
		

		CommonBranchResponseResource branch=validateService.validateCommonBranch(tenantId, businessCenterUpdateResource.getBranchId(), businessCenterUpdateResource.getBranchName());
		if(branch.getBrhCode()!=businessCenterUpdateResource.getBranchCode() || branch.getBrhCode().equals(businessCenterUpdateResource.getBranchCode())) {
			throw new DetailValidateException(environment.getProperty("common.not-match"),ServiceEntity.BRANCH_ID, ServicePoint.LEAD_INFOR);

		}
		UserProfileResponse u=validateService.validateUserProfileByUserId(tenantId, businessCenterUpdateResource.getEmpNo(), businessCenterUpdateResource.getEmpName());
		if(u.getId()!=Long.parseLong(businessCenterUpdateResource.getEmpId())) {
			throw new ValidateRecordException(environment.getProperty("user.invalid"),"empId");
		}
		if(u.getEmployeeNumber()!=businessCenterUpdateResource.getEmpNo() || !u.getEmployeeNumber().equals(businessCenterUpdateResource.getEmpNo())) {
			throw new ValidateRecordException(environment.getProperty("user.invalid"),"empNo");
		}
		RepaymentFrequencyResponse f=validateService.validateRepaymentFrequency(tenantId, businessCenterUpdateResource.getCollectionFrequencyId());
		if(f.getName()!=businessCenterUpdateResource.getCollectionFrequency() || !f.getName().equals(businessCenterUpdateResource.getCollectionFrequency())) {
			throw new ValidateRecordException(environment.getProperty("common.not-available"),"repaymentFrequencyId");

		}		BusinessCenter businessCenter=isPresentbusinessCenter.get();
		businessCenter.setTenantId(tenantId);
		businessCenter.setCode(businessCenterUpdateResource.getCode());
		businessCenter.setName(businessCenterUpdateResource.getName());
		businessCenter.setCenterHead(businessCenterUpdateResource.getCenterHead());
		businessCenter.setStatus(businessCenterUpdateResource.getStatus());
		businessCenter.setModifiedUser(userName);
//		businessCenter.setAdressLine1(businessCenterUpdateResource.getAdressLine1());
//		businessCenter.setAdressLine2(businessCenterUpdateResource.getAdressLine2());
//		businessCenter.setAdressLine3(businessCenterUpdateResource.getAdressLine3());
//		businessCenter.setAdressLine4(businessCenterUpdateResource.getAdressLine4());
		businessCenter.setBranchId(Long.parseLong(businessCenterUpdateResource.getBranchId()));
		businessCenter.setBranchCode(businessCenterUpdateResource.getBranchCode());
		if(businessCenterUpdateResource.getCenterLimit()!=null && !businessCenterUpdateResource.getCenterLimit().isEmpty())
		businessCenter.setCenterLimit(Long.parseLong(businessCenterUpdateResource.getCenterLimit()));
		businessCenter.setCollectionFrequency(businessCenterUpdateResource.getCollectionFrequency());
		businessCenter.setCollectionFrequencyId(Long.parseLong(businessCenterUpdateResource.getCollectionFrequencyId()));
		businessCenter.setContactNo(businessCenterUpdateResource.getContactNo());
		businessCenter.setEmpId(Long.parseLong(businessCenterUpdateResource.getEmpId()));
		businessCenter.setEmpNo(businessCenterUpdateResource.getEmpNo());
		businessCenter.setEmpUserId(businessCenterUpdateResource.getEmpUserId());
		if(businessCenterUpdateResource.getMaxNoOfSubCenter()!=null && !businessCenterUpdateResource.getMaxNoOfSubCenter().isEmpty())
		businessCenter.setMaxClientPerSubCenter(Long.parseLong(businessCenterUpdateResource.getMaxClientPerSubCenter()));
		if(businessCenterUpdateResource.getMaxNoOfSubCenter()!=null && !businessCenterUpdateResource.getMaxNoOfSubCenter().isEmpty())
		businessCenter.setMaxNoOfSubCenter(Long.parseLong(businessCenterUpdateResource.getMaxNoOfSubCenter()));
		businessCenter.setModifiedDate(getCreateOrModifyDate());
		businessCenter.setSyncTs(getCreateOrModifyDate());
		businessCenter = businessCenterRepository.saveAndFlush(businessCenter);
		
	}
}
