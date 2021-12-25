package com.fusionx.lending.product.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.OtherFeeRateType;
import com.fusionx.lending.product.domain.OtherFeeRateTypeHistory;
import com.fusionx.lending.product.repository.OtherFeeRateTypeHistoryRepository;
import com.fusionx.lending.product.service.OtherFeeRateTypeHistoryService;

/**
 * Other Fee Rate Type History Service Impl
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
public class OtherFeeRateTypeHistoryServiceImpl implements OtherFeeRateTypeHistoryService{

	@Autowired
	private OtherFeeRateTypeHistoryRepository otherFeeRateTypeHistoryRepository;
	
	/**
	 * @author Senitha
	 * 
	 * Save Other Fee Rate Type History
	 * @param tenantId
	 * @param otherFeeRateType
	 */
	@Override
	public void insertOtherFeeRateTypeHistory(String tenantId, OtherFeeRateType otherFeeRateType) {

		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        OtherFeeRateTypeHistory otherFeeRateTypeHistory = new OtherFeeRateTypeHistory();

        otherFeeRateTypeHistory.setTenantId(otherFeeRateType.getTenantId());
        otherFeeRateTypeHistory.setOtherFeeRateTypeId(otherFeeRateType.getId());
        otherFeeRateTypeHistory.setCode(otherFeeRateType.getCode());
        otherFeeRateTypeHistory.setName(otherFeeRateType.getName());
        otherFeeRateTypeHistory.setStatus(otherFeeRateType.getStatus());
        otherFeeRateTypeHistory.setDescription(otherFeeRateType.getDescription());
        otherFeeRateTypeHistory.setCreatedDate(otherFeeRateType.getCreatedDate());
        otherFeeRateTypeHistory.setCreatedUser(otherFeeRateType.getCreatedUser());
        otherFeeRateTypeHistory.setModifiedDate(otherFeeRateType.getModifiedDate());
        otherFeeRateTypeHistory.setModifiedUser(otherFeeRateType.getModifiedUser());
        otherFeeRateTypeHistory.setOtherFeeRateTypeVersion(otherFeeRateType.getVersion());
        otherFeeRateTypeHistory.setSyncTs(currentTimestamp);
        otherFeeRateTypeHistory.setHistoryCreatedDate(currentTimestamp);
        otherFeeRateTypeHistory.setHistoryCreatedUser(LogginAuthentcation.getInstance().getUserName());
        otherFeeRateTypeHistoryRepository.saveAndFlush(otherFeeRateTypeHistory);
		
	}

}
