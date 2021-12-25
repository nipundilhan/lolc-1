package com.fusionx.lending.product.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.OtherFeeType;
import com.fusionx.lending.product.domain.OtherFeeTypeHistory;
import com.fusionx.lending.product.enums.CollectionTypeEnum;
import com.fusionx.lending.product.enums.YesNo;
import com.fusionx.lending.product.repository.OtherFeeTypeHistoryRepository;
import com.fusionx.lending.product.service.OtherFeeTypeHistoryService;

/**
 * Other Fee Type History Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6604   	 FX-6509	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class OtherFeeTypeHistoryServiceImpl implements OtherFeeTypeHistoryService{

	@Autowired
	private OtherFeeTypeHistoryRepository otherFeeTypeHistoryRepository;
	
	/**
	 * @author Senitha
	 * 
	 * Save Other Fee Type History
	 * @param tenantId
	 * @param otherFeeType
	 */
	@Override
	public void insertOtherFeeTypeHistory(String tenantId, OtherFeeType otherFeeType) {

		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        OtherFeeTypeHistory otherFeeTypeHistory = new OtherFeeTypeHistory();

        otherFeeTypeHistory.setTenantId(otherFeeType.getTenantId());
        otherFeeTypeHistory.setOtherFeeTypeId(otherFeeType.getId());
        otherFeeTypeHistory.setCode(otherFeeType.getCode());
        otherFeeTypeHistory.setName(otherFeeType.getName());
        otherFeeTypeHistory.setStatus(otherFeeType.getStatus());
        otherFeeTypeHistory.setFeeCategoryId(otherFeeType.getFeeCategoryId());
        otherFeeTypeHistory.setTransactionCodeId(otherFeeType.getTransactionCodeId());
        otherFeeTypeHistory.setTransactionSubCodeId(otherFeeType.getTransactionSubCodeId());
        otherFeeTypeHistory.setDescription(otherFeeType.getDescription());
        otherFeeTypeHistory.setCreatedDate(otherFeeType.getCreatedDate());
        otherFeeTypeHistory.setCreatedUser(otherFeeType.getCreatedUser());
        otherFeeTypeHistory.setModifiedDate(otherFeeType.getModifiedDate());
        otherFeeTypeHistory.setModifiedUser(otherFeeType.getModifiedUser());
        otherFeeTypeHistory.setOtherFeeTypeVersion(otherFeeType.getVersion());
        otherFeeTypeHistory.setSyncTs(currentTimestamp);
        otherFeeTypeHistory.setHistoryCreatedDate(currentTimestamp);
        otherFeeTypeHistory.setHistoryCreatedUser(LogginAuthentcation.getInstance().getUserName());
        otherFeeTypeHistory.setRefundable(otherFeeType.getRefundable());
        otherFeeTypeHistory.setRefundablePercentage(otherFeeType.getRefundablePercentage());
        otherFeeTypeHistory.setCollectionType(otherFeeType.getCollectionType());   	 
    	
        otherFeeTypeHistoryRepository.saveAndFlush(otherFeeTypeHistory);
		
	}
	
}
