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
import com.fusionx.lending.product.domain.InterestRateType;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.InterestRateTypeRepository;
import com.fusionx.lending.product.resources.AddBaseRequest;
import com.fusionx.lending.product.resources.UpdateBaseRequest;
import com.fusionx.lending.product.service.InterestRateTypeHistoryService;
import com.fusionx.lending.product.service.InterestRateTypeService;

/**
 * Interest Rate Type Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6601   	 FX-6508	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class InterestRateTypeServiceImpl extends MessagePropertyBase implements InterestRateTypeService {

	@Autowired
	private InterestRateTypeRepository interestRateTypeRepository;
	
	@Autowired
	private InterestRateTypeHistoryService interestRateTypeHistoryService;

	/**
	 * @author Senitha
	 * 
	 * Find all Interest Rate Type
	 * @return -JSON array of all Interest Rate Type
	 * 
	 * */
	@Override
	public List<InterestRateType> getAll() {
		return interestRateTypeRepository.findAll();
	}

	/**
	 * @author Senitha
	 * 
	 * Find Interest Rate Type by ID
	 * @return -JSON array of Interest Rate Type
	 * 
	 * */
	@Override
	public Optional<InterestRateType> getById(Long id) {
		Optional<InterestRateType> isPresentInterestRateType = interestRateTypeRepository.findById(id);
		if (isPresentInterestRateType.isPresent()) {
			return Optional.ofNullable(isPresentInterestRateType.get());
		}
		else {
			return Optional.empty();
		}
	}

	/**
	 * @author Senitha
	 * 
	 * Find Interest Rate Type by code
	 * @return -JSON array of Interest Rate Type
	 * 
	 * */
	@Override
	public Optional<InterestRateType> getByCode(String code) {
		Optional<InterestRateType> isPresentInterestRateType = interestRateTypeRepository.findByCode(code);
		if (isPresentInterestRateType.isPresent()) {
			return Optional.ofNullable(isPresentInterestRateType.get());
		}
		else {
			return Optional.empty();
		}
	}

	/**
	 * @author Senitha
	 * 
	 * Find Interest Rate Type by name
	 * @return -JSON array of Interest Rate Type
	 * 
	 * */
	@Override
	public List<InterestRateType> getByName(String name) {
		return interestRateTypeRepository.findByName(name);
	}

	/**
	 * @author Senitha
	 * 
	 * Find Interest Rate Type by status
	 * @return -JSON array of Interest Rate Type
	 * 
	 * */
	@Override
	public List<InterestRateType> getByStatus(String status) {
		return interestRateTypeRepository.findByStatus(CommonStatus.valueOf(status));
	}

	/**
	 * @author Senitha
	 * 
	 * Insert Interest Rate Type
	 * @param  - AddBaseRequest
	 * @return - Successfully saved
	 * 
	 * */
	@Override
	public InterestRateType addInterestRateType(String tenantId, AddBaseRequest addBaseRequest) {

		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty("common.not-null"), "username");
        
        Optional<InterestRateType> isPresentInterestRateType = interestRateTypeRepository.findByCode(addBaseRequest.getCode());
        if (isPresentInterestRateType.isPresent()) {
        	throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.SEGMENT_LIST);
		} else {
        	InterestRateType interestRateType = new InterestRateType();
        	interestRateType.setTenantId(tenantId);
        	interestRateType.setCode(addBaseRequest.getCode());
        	interestRateType.setName(addBaseRequest.getName());
        	interestRateType.setDescription(addBaseRequest.getDescription());
        	interestRateType.setStatus(CommonStatus.valueOf(addBaseRequest.getStatus()));
        	interestRateType.setSyncTs(currentTimestamp);
        	interestRateType.setCreatedDate(currentTimestamp);
        	interestRateType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        	interestRateType = interestRateTypeRepository.save(interestRateType);
        	return interestRateType;
		}
        
	}

	/**
	 * @author Senitha
	 * 
	 * Update Interest Rate Type
	 * @param  - UpdateBaseRequest
	 * @return - Successfully saved
	 * 
	 * */
	@Override
	public InterestRateType updateInterestRateType(String tenantId, UpdateBaseRequest updateBaseRequest) {

		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currenTimestamp = new java.sql.Timestamp(now.getTime());
		
		if (LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");
		
		Optional<InterestRateType> isPresentInterestRateType = interestRateTypeRepository.findById(Long.parseLong(updateBaseRequest.getId()));
		if (isPresentInterestRateType.isPresent()) {
			
			if (!isPresentInterestRateType.get().getVersion().equals(Long.parseLong(updateBaseRequest.getVersion()))) {
				throw new InvalidServiceIdException(environment.getProperty("common.invalid-value"), ServiceEntity.VERSION, EntityPoint.SEGMENT_LIST);
			}

			LoggerRequest.getInstance().logInfo("InterestRateType ******************************** Code Can Not Update *********************************************");
	        if (!isPresentInterestRateType.get().getCode().equals(updateBaseRequest.getCode())) {
	        	throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE, EntityPoint.SEGMENT_LIST);
	        }
	        
	        InterestRateType interestRateType = isPresentInterestRateType.get();
	        
			LoggerRequest.getInstance().logInfo("InterestRateType ******************************** Save Interest Rate Type History *********************************************");
			interestRateTypeHistoryService.insertInterestRateTypeHistory(tenantId, interestRateType);
			
	        interestRateType.setTenantId(tenantId);
	        interestRateType.setCode(updateBaseRequest.getCode());
	        interestRateType.setName(updateBaseRequest.getName());
	        interestRateType.setDescription(updateBaseRequest.getDescription());
	        interestRateType.setStatus(CommonStatus.valueOf(updateBaseRequest.getStatus()));
	        interestRateType.setSyncTs(currenTimestamp);
	        interestRateType.setModifiedDate(currenTimestamp);
	        interestRateType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
	        interestRateType = interestRateTypeRepository.saveAndFlush(interestRateType);
        	return interestRateType;
			
		} else {
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
		}
	}

}
