package com.fusionx.lending.product.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.ResidencyInclude;
import com.fusionx.lending.product.domain.ResidencyIncludeHistory;
import com.fusionx.lending.product.repository.ResidencyIncludeHistoryRepository;
import com.fusionx.lending.product.service.ResidencyIncludeHistoryService;

/**
 * Residency eligibility service
 * @author 	Rangana
 * @Dat     08-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-06-2021     FX-6        FX-6524    Rangana      Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class ResidencyIncludeHistoryServiceImpl implements ResidencyIncludeHistoryService {
	
	@Autowired
	private ResidencyIncludeHistoryRepository residencyIncludeHistoryRepository;

	@Override
	public void insertResidencyIncludeHistory(String tenantId, ResidencyInclude residencyInclude,String historyCreatedUser) {
		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        ResidencyIncludeHistory residencyIncludeHistory = new ResidencyIncludeHistory();
        
        residencyIncludeHistory.setResidencyIncludeId(residencyInclude.getId());
        residencyIncludeHistory.setTenantId(residencyInclude.getTenantId());
        residencyIncludeHistory.setResidencyEligibilityId(residencyInclude.getEligibilityId());
        residencyIncludeHistory.setCountryId(residencyInclude.getCountryId());
        residencyIncludeHistory.setStatus(residencyInclude.getStatus());
        residencyIncludeHistory.setCreatedDate(residencyInclude.getCreatedDate());
        residencyIncludeHistory.setCreatedUser(residencyInclude.getCreatedUser());
        residencyIncludeHistory.setModifiedDate(residencyInclude.getModifiedDate());
        residencyIncludeHistory.setModifiedUser(residencyInclude.getModifiedUser());
        residencyIncludeHistory.setAgeEligibilityVersion(residencyInclude.getVersion());
        residencyIncludeHistory.setHistoryCreatedDate(currentTimestamp);
        residencyIncludeHistory.setHistoryCreatedUser(historyCreatedUser);
        residencyIncludeHistory.setSyncTs(currentTimestamp);
        residencyIncludeHistoryRepository.saveAndFlush(residencyIncludeHistory);
	}

}
