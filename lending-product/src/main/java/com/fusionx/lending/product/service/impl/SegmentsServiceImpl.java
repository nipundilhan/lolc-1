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
import com.fusionx.lending.product.domain.Segments;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.SegmentsRepository;
import com.fusionx.lending.product.resources.AddBaseRequest;
import com.fusionx.lending.product.resources.UpdateBaseRequest;
import com.fusionx.lending.product.service.SegmentsHistoryService;
import com.fusionx.lending.product.service.SegmentsService;

/**
 * Segment Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-2880   	 FX-6515	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class SegmentsServiceImpl extends MessagePropertyBase implements SegmentsService{
	
	@Autowired
	private SegmentsRepository segmentsRepository;
	
	@Autowired
	private SegmentsHistoryService segmentsHistoryService;
	
	/**
	 * @author Senitha
	 * 
	 * Find all Segment
	 * @return -JSON array of all Segments
	 * 
	 * */
	@Override
	public List<Segments> getAll() {
		return segmentsRepository.findAll();
	}
	
	/**
	 * @author Senitha
	 * 
	 * Find Segment by ID
	 * @return -JSON array of Segments
	 * 
	 * */
	@Override
	public Optional<Segments> getById(Long id) {
		Optional<Segments> isPresentSegments = segmentsRepository.findById(id);
		if (isPresentSegments.isPresent()) {
			return Optional.ofNullable(isPresentSegments.get());
		}
		else {
			return Optional.empty();
		}
	}
	
	/**
	 * @author Senitha
	 * 
	 * Find Segment by code
	 * @return -JSON array of Segments
	 * 
	 * */
	@Override
	public Optional<Segments> getByCode(String code) {
		Optional<Segments> isPresentSegments = segmentsRepository.findByCode(code);
		if (isPresentSegments.isPresent()) {
			return Optional.ofNullable(isPresentSegments.get());
		}
		else {
			return Optional.empty();
		}
	}
	
	/**
	 * @author Senitha
	 * 
	 * Find Segment by name
	 * @return -JSON array of Segments
	 * 
	 * */
	@Override
	public List<Segments> getByName(String name) {
		return segmentsRepository.findByName(name);
	}

	/**
	 * @author Senitha
	 * 
	 * Find Segment status
	 * @return -JSON array of Segments
	 * 
	 * */
	@Override
	public List<Segments> getByStatus(String status) {
		return segmentsRepository.findByStatus(CommonStatus.valueOf(status));
	}

	/**
	 * @author Senitha
	 * 
	 * Insert Segments
	 * @param  - AddBaseRequest
	 * @return - Successfully saved
	 * 
	 * */
	@Override
	public Segments addSegments(String tenantId, AddBaseRequest addBaseRequest) {

		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty("common.not-null"), "username");
        
        Optional<Segments> isPresentSegments = segmentsRepository.findByCode(addBaseRequest.getCode());
        if (isPresentSegments.isPresent()) {
        	throw new InvalidServiceIdException(environment.getProperty("common.unique"), ServiceEntity.CODE, EntityPoint.SEGMENT_LIST);
		}
        
        else {
        	Segments segments = new Segments();
        	segments.setTenantId(tenantId);
        	segments.setCode(addBaseRequest.getCode());
        	segments.setName(addBaseRequest.getName());
        	segments.setDescription(addBaseRequest.getDescription());
        	segments.setStatus(CommonStatus.valueOf(addBaseRequest.getStatus()));
        	segments.setSyncTs(currentTimestamp);
        	segments.setCreatedDate(currentTimestamp);
        	segments.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        	segments = segmentsRepository.save(segments);
        	return segments;
		}
        
	}

	/**
	 * @author Senitha
	 * 
	 * Update Segments
	 * @param  - UpdateBaseRequest
	 * @return - Successfully saved
	 * 
	 * */
	@Override
	public Segments updateSegments(String tenantId, UpdateBaseRequest updateBaseRequest) {
		
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currenTimestamp = new java.sql.Timestamp(now.getTime());
		
		if (LogginAuthentcation.getInstance().getUserName() ==null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");
		
		Optional<Segments> isPresentSegments = segmentsRepository.findById(Long.parseLong(updateBaseRequest.getId()));
		if (isPresentSegments.isPresent()) {
			
			if (!isPresentSegments.get().getVersion().equals(Long.parseLong(updateBaseRequest.getVersion()))) {
				throw new InvalidServiceIdException(environment.getProperty("common.invalid-value"), ServiceEntity.VERSION, EntityPoint.SEGMENT_LIST);
			}
			
			LoggerRequest.getInstance().logInfo("Segments ******************************** Code Can Not Update *********************************************");
	        if (!isPresentSegments.get().getCode().equals(updateBaseRequest.getCode())) {
	        	throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE, EntityPoint.SEGMENT_LIST);
	        }
	        
	        Segments segments = isPresentSegments.get();

			LoggerRequest.getInstance().logInfo("Segments ******************************** Save Segments History *********************************************");
			segmentsHistoryService.insertSegmentsHistory(tenantId, segments);
			
	        segments.setTenantId(tenantId);
	        segments.setCode(updateBaseRequest.getCode());
	        segments.setName(updateBaseRequest.getName());
	        segments.setDescription(updateBaseRequest.getDescription());
	        segments.setStatus(CommonStatus.valueOf(updateBaseRequest.getStatus()));
	        segments.setSyncTs(currenTimestamp);
	        segments.setModifiedDate(currenTimestamp);
	        segments.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
	        segments = segmentsRepository.saveAndFlush(segments);
        	return segments;
			
		} else {
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
		}
	}

}
