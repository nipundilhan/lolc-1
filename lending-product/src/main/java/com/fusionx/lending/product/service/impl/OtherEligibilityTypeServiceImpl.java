package com.fusionx.lending.product.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.OtherEligibilityType;
import com.fusionx.lending.product.domain.OtherEligibilityTypeHistory;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.OtherEligibilityTypeHistoryRepository;
import com.fusionx.lending.product.repository.OtherEligibilityTypeRepository;
import com.fusionx.lending.product.resources.AddBaseRequest;
import com.fusionx.lending.product.resources.UpdateBaseRequest;
import com.fusionx.lending.product.service.OtherEligibilityTypeService;

/**
 * Other Eligibility Type Service Impl
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
public class OtherEligibilityTypeServiceImpl extends MessagePropertyBase implements OtherEligibilityTypeService{

	@Autowired
	private OtherEligibilityTypeRepository otherEligibilityTypeRepository;
	
	@Autowired
	private OtherEligibilityTypeHistoryRepository otherEligibilityTypeHistoryRepository;

	@Override
	public List<OtherEligibilityType> findAll() {
		return otherEligibilityTypeRepository.findAll();
	}

	@Override
	public Optional<OtherEligibilityType> findById(Long id) {
		Optional<OtherEligibilityType> otherEligibilityType = otherEligibilityTypeRepository.findById(id);
		if(otherEligibilityType.isPresent())
			return Optional.ofNullable(otherEligibilityType.get());
		else
			return Optional.empty();
	}

	@Override
	public Optional<OtherEligibilityType> findByCode(String code) {
		Optional<OtherEligibilityType> otherEligibilityType = otherEligibilityTypeRepository.findByCode(code);
		if(otherEligibilityType.isPresent())
			return Optional.ofNullable(otherEligibilityType.get());
		else
			return Optional.empty();
	}

	@Override
	public List<OtherEligibilityType> findByStatus(String status) {
		return otherEligibilityTypeRepository.findByStatus(CommonStatus.valueOf(status));
	}
	
	@Override
	public List<OtherEligibilityType> findByName(String name) {
		return otherEligibilityTypeRepository.findByNameContaining(name);
	}

	@Override
	public OtherEligibilityType addOtherEligibilityType(String tenantId, AddBaseRequest addBaseRequest) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		Optional<OtherEligibilityType> isPresentOtherEligibilityType = otherEligibilityTypeRepository.findByCode(addBaseRequest.getCode());
		if(!isPresentOtherEligibilityType.isPresent()) {
			OtherEligibilityType otherEligibilityType = new OtherEligibilityType();
			otherEligibilityType.setTenantId(tenantId);
			otherEligibilityType.setCode(addBaseRequest.getCode());
			otherEligibilityType.setName(addBaseRequest.getName());
			otherEligibilityType.setDescription(addBaseRequest.getDescription());
			otherEligibilityType.setStatus(CommonStatus.valueOf(addBaseRequest.getStatus()));
			otherEligibilityType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			otherEligibilityType.setCreatedDate(currentTimestamp);
			otherEligibilityType.setSyncTs(currentTimestamp);
			otherEligibilityType = otherEligibilityTypeRepository.saveAndFlush(otherEligibilityType);
			return otherEligibilityType;
		}
		else
			throw new ValidateRecordException(environment.getProperty("common.unique"), "code");
	}

	@Override
	public OtherEligibilityType updateOtherEligibilityType(String tenantId, UpdateBaseRequest updateBaseRequest) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		Optional<OtherEligibilityType> isPresentOtherEligibilityType = otherEligibilityTypeRepository.findById(Long.parseLong(updateBaseRequest.getId()));
		if(isPresentOtherEligibilityType.isPresent()) {
			Optional<OtherEligibilityType> isPresentOtherEligibilityTypeCode = otherEligibilityTypeRepository.findByCodeAndId(updateBaseRequest.getCode(),Long.parseLong((updateBaseRequest.getId())));
			if(!isPresentOtherEligibilityTypeCode.isPresent())
				throw new ValidateRecordException(environment.getProperty(CANT_UPDATED), "code");
			else{
				if (!isPresentOtherEligibilityType.get().getVersion()
						.equals(Long.parseLong(updateBaseRequest.getVersion())))
					throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
				
				OtherEligibilityType otherEligibilityType = isPresentOtherEligibilityType.get();
				insertHistory(tenantId, otherEligibilityType, LogginAuthentcation.getInstance().getUserName());

				otherEligibilityType.setTenantId(tenantId);
				otherEligibilityType.setCode(updateBaseRequest.getCode());
				otherEligibilityType.setName(updateBaseRequest.getName());
				otherEligibilityType.setDescription(updateBaseRequest.getDescription());
				otherEligibilityType.setStatus(CommonStatus.valueOf(updateBaseRequest.getStatus()));
				otherEligibilityType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
				otherEligibilityType.setModifiedDate(currentTimestamp);
				otherEligibilityType.setSyncTs(currentTimestamp);
				otherEligibilityType.setVersion(Long.parseLong((updateBaseRequest.getVersion())));
				otherEligibilityType = otherEligibilityTypeRepository.saveAndFlush(otherEligibilityType);
				return otherEligibilityType;
			}
		}
		else
			throw new ValidateRecordException(environment.getProperty("record-not-found"),"message");
	}

	private void insertHistory(String tenantId, OtherEligibilityType otherEligibilityType, String userName) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		OtherEligibilityTypeHistory history=new OtherEligibilityTypeHistory();
		history.setTenantId(tenantId);
		history.setCode(otherEligibilityType.getCode());
		history.setName(otherEligibilityType.getName());
		history.setDescription(otherEligibilityType.getDescription());
		history.setStatus(otherEligibilityType.getStatus());
		history.setCreatedUser(otherEligibilityType.getCreatedUser());
		history.setCreatedDate(otherEligibilityType.getCreatedDate());
		history.setModifiedUser(otherEligibilityType.getModifiedUser());
		history.setModifiedDate(otherEligibilityType.getModifiedDate());
		history.setSyncTs(currentTimestamp);
		history.setOtherEligibilityVersion(otherEligibilityType.getVersion());
		history.setOtherEligibilityTypeId(otherEligibilityType.getId());
		history.setHistoryCreatedUser(LogginAuthentcation.getInstance().getUserName());
		history.setHistoryCreatedDate(currentTimestamp);
		history = otherEligibilityTypeHistoryRepository.saveAndFlush(history);
		
	}
	
}
