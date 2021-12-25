package com.fusionx.lending.product.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Segments;
import com.fusionx.lending.product.domain.SegmentsHistory;
import com.fusionx.lending.product.repository.SegmentsHistoryRepository;
import com.fusionx.lending.product.service.SegmentsHistoryService;

/**
 * Segment History Service Impl
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
public class SegmentsHistoryServiceImpl implements SegmentsHistoryService{

	@Autowired
	private SegmentsHistoryRepository segmentsHistoryRepository;

	/**
	 * @author Senitha
	 * 
	 * Save Segments History
	 * @param tenantId
	 * @param segmentsHistory
	 */
	@Override
	public void insertSegmentsHistory(String tenantId, Segments segments) {

		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        SegmentsHistory segmentsHistory = new SegmentsHistory();

        segmentsHistory.setTenantId(segments.getTenantId());
        segmentsHistory.setSegmentsId(segments.getId());
        segmentsHistory.setCode(segments.getCode());
        segmentsHistory.setName(segments.getName());
        segmentsHistory.setStatus(segments.getStatus());
        segmentsHistory.setDescription(segments.getDescription());
        segmentsHistory.setCreatedDate(segments.getCreatedDate());
        segmentsHistory.setCreatedUser(segments.getCreatedUser());
        segmentsHistory.setModifiedDate(segments.getModifiedDate());
        segmentsHistory.setModifiedUser(segments.getModifiedUser());
        segmentsHistory.setSegmentsVersion(segments.getVersion());
        segmentsHistory.setSyncTs(currentTimestamp);
        segmentsHistory.setHistoryCreatedDate(currentTimestamp);
        segmentsHistory.setHistoryCreatedUser(LogginAuthentcation.getInstance().getUserName());
        segmentsHistoryRepository.saveAndFlush(segmentsHistory);
		
	}
	
}
