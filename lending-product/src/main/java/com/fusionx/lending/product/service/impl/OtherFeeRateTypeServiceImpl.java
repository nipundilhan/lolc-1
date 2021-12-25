package com.fusionx.lending.product.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.OtherFeeRateType;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.OtherFeeRateTypeRepository;
import com.fusionx.lending.product.resources.AddBaseRequest;
import com.fusionx.lending.product.resources.UpdateBaseRequest;
import com.fusionx.lending.product.service.OtherFeeRateTypeHistoryService;
import com.fusionx.lending.product.service.OtherFeeRateTypeService;

/**
 * Other Fee Rate Type Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6605   	 FX-6510	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class OtherFeeRateTypeServiceImpl extends MessagePropertyBase implements OtherFeeRateTypeService{

	@Autowired
	private OtherFeeRateTypeRepository otherFeeRateTypeRepository;
	
	@Autowired
	private OtherFeeRateTypeHistoryService otherFeeRateTypeHistoryService;
	
	/**
	 * @author Senitha
	 * 
	 * Find all Other Fee Rate Type
	 * @return -JSON array of all Other Fee Rate Type
	 * 
	 * */
	@Override
	public List<OtherFeeRateType> getAll() {
		return otherFeeRateTypeRepository.findAll();
	}
	
	/**
	 * @author Senitha
	 * 
	 * Find Other Fee Rate Type by ID
	 * @return -JSON array of Other Fee Rate Type
	 * 
	 * */
	@Override
	public Optional<OtherFeeRateType> getById(Long id) {
		
		Optional<OtherFeeRateType> isPresentOtherFeeRateType = otherFeeRateTypeRepository.findById(id);
		if (isPresentOtherFeeRateType.isPresent()) {
			return Optional.ofNullable(isPresentOtherFeeRateType.get());
		} else {
			return Optional.empty();
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
	 * Find Other Fee Rate Type by code
	 * @return -JSON array of Other Fee Rate Type
	 * 
	 * */
	@Override
	public Optional<OtherFeeRateType> getByCode(String code) {
		
		Optional<OtherFeeRateType> isPresentOtherFeeRateType = otherFeeRateTypeRepository.findByCode(code);
		if (isPresentOtherFeeRateType.isPresent()) {
			return Optional.ofNullable(isPresentOtherFeeRateType.get());
		} else {
			return Optional.empty();
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
	 * Find Other Fee Rate Type by name
	 * @return -JSON array of Other Fee Rate Type
	 * 
	 * */
	@Override
	public List<OtherFeeRateType> getByName(String name) {
		return otherFeeRateTypeRepository.findByName(name);
	}

	/**
	 * @author Senitha
	 * 
	 * Find Other Fee Rate Type status
	 * @return -JSON array of Other Fee Rate Type
	 * 
	 * */
	@Override
	public List<OtherFeeRateType> getByStatus(String status) {
		return otherFeeRateTypeRepository.findByStatus(CommonStatus.valueOf(status));
	}

	/**
	 * @author Senitha
	 * 
	 * Insert Other Fee Rate Type
	 * @param  - AddBaseRequest
	 * @return - Successfully saved
	 * 
	 * */
	@Override
	public OtherFeeRateType addOtherFeeRateType(String tenantId, AddBaseRequest addBaseRequest) {

		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty("common.not-null"), "username");
        
        Optional<OtherFeeRateType> isPresentOtherFeeRateType = otherFeeRateTypeRepository.findByCode(addBaseRequest.getCode());
        if (isPresentOtherFeeRateType.isPresent()) {
        	throw new InvalidServiceIdException(environment.getProperty("common.unique"), ServiceEntity.CODE, EntityPoint.SEGMENT_LIST);
		} else {
        	OtherFeeRateType otherFeeRateType = new OtherFeeRateType();
        	otherFeeRateType.setTenantId(tenantId);
        	otherFeeRateType.setCode(addBaseRequest.getCode());
        	otherFeeRateType.setName(addBaseRequest.getName());
        	otherFeeRateType.setDescription(addBaseRequest.getDescription());
        	otherFeeRateType.setStatus(CommonStatus.valueOf(addBaseRequest.getStatus()));
        	otherFeeRateType.setSyncTs(currentTimestamp);
        	otherFeeRateType.setCreatedDate(currentTimestamp);
        	otherFeeRateType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        	otherFeeRateType = otherFeeRateTypeRepository.save(otherFeeRateType);
        	return otherFeeRateType;
		}
        
	}

	/**
	 * @author Senitha
	 * 
	 * Update Other Fee Rate Type
	 * @param  - UpdateBaseRequest
	 * @return - Successfully saved
	 * 
	 * */
	@Override
	public OtherFeeRateType updateOtherFeeRateType(String tenantId, UpdateBaseRequest updateBaseRequest) {
		
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currenTimestamp = new java.sql.Timestamp(now.getTime());
		
		if (LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");
		
		Optional<OtherFeeRateType> isPresentOtherFeeRateType = otherFeeRateTypeRepository.findById(Long.parseLong(updateBaseRequest.getId()));
		if (isPresentOtherFeeRateType.isPresent()) {
			
			if (!isPresentOtherFeeRateType.get().getVersion().equals(Long.parseLong(updateBaseRequest.getVersion()))) {
				throw new InvalidServiceIdException(environment.getProperty("common.invalid-value"), ServiceEntity.VERSION, EntityPoint.SEGMENT_LIST);
			}
	        
			LoggerRequest.getInstance().logInfo("OtherFeeRateType ******************************** Code Can Not Update *********************************************");
	        if (!isPresentOtherFeeRateType.get().getCode().equals(updateBaseRequest.getCode())) {
	        	throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE, EntityPoint.SEGMENT_LIST);
	        }
	        
	        OtherFeeRateType otherFeeRateType = isPresentOtherFeeRateType.get();

			LoggerRequest.getInstance().logInfo("OtherFeeRateType ******************************** Save Other Fee Rate Type History *********************************************");
			otherFeeRateTypeHistoryService.insertOtherFeeRateTypeHistory(tenantId, otherFeeRateType);
			
			otherFeeRateType.setTenantId(tenantId);
			otherFeeRateType.setCode(updateBaseRequest.getCode());
			otherFeeRateType.setName(updateBaseRequest.getName());
			otherFeeRateType.setDescription(updateBaseRequest.getDescription());
			otherFeeRateType.setStatus(CommonStatus.valueOf(updateBaseRequest.getStatus()));
			otherFeeRateType.setSyncTs(currenTimestamp);
			otherFeeRateType.setModifiedDate(currenTimestamp);
			otherFeeRateType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			otherFeeRateType = otherFeeRateTypeRepository.saveAndFlush(otherFeeRateType);
        	return otherFeeRateType;
			
		} else {
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
		}
	}
	
}
