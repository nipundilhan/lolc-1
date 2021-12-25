package com.fusionx.lending.product.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.InterestRateType;
import com.fusionx.lending.product.domain.InterestRateTypeHistory;
import com.fusionx.lending.product.repository.InterestRateTypeHistoryRepository;
import com.fusionx.lending.product.service.InterestRateTypeHistoryService;

/**
 * Interest Rate Type History Service Impl
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
public class InterestRateTypeHistoryServiceImpl implements InterestRateTypeHistoryService {

	@Autowired
	private InterestRateTypeHistoryRepository interestRateTypeHistoryRepository;
	
	/**
	 * @author Senitha
	 * 
	 * Save Interest Rate Type History
	 * @param tenantId
	 * @param interestRateType
	 */
	@Override
	public void insertInterestRateTypeHistory(String tenantId, InterestRateType interestRateType) {

		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        InterestRateTypeHistory interestRateTypeHistory = new InterestRateTypeHistory();

        interestRateTypeHistory.setTenantId(interestRateType.getTenantId());
        interestRateTypeHistory.setInterestRateTypeId(interestRateType.getId());
        interestRateTypeHistory.setCode(interestRateType.getCode());
        interestRateTypeHistory.setName(interestRateType.getName());
        interestRateTypeHistory.setStatus(interestRateType.getStatus());
        interestRateTypeHistory.setDescription(interestRateType.getDescription());
        interestRateTypeHistory.setCreatedDate(interestRateType.getCreatedDate());
        interestRateTypeHistory.setCreatedUser(interestRateType.getCreatedUser());
        interestRateTypeHistory.setModifiedDate(interestRateType.getModifiedDate());
        interestRateTypeHistory.setModifiedUser(interestRateType.getModifiedUser());
        interestRateTypeHistory.setInterestRateTypeVersion(interestRateType.getVersion());
        interestRateTypeHistory.setSyncTs(currentTimestamp);
        interestRateTypeHistory.setHistoryCreatedDate(currentTimestamp);
        interestRateTypeHistory.setHistoryCreatedUser(LogginAuthentcation.getInstance().getUserName());
        interestRateTypeHistoryRepository.saveAndFlush(interestRateTypeHistory);
		
	}

}
