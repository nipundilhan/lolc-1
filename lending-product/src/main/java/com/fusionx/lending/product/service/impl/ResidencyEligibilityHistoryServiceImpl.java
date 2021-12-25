package com.fusionx.lending.product.service.impl;
/**
 * Residency eligibility service
 * @author 	Rangana
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019     FX-6        FX-6523    Rangana      Created
 *    
 ********************************************************************************************************
 */
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.ResidencyEligibility;
import com.fusionx.lending.product.domain.ResidencyEligibilityHistory;
import com.fusionx.lending.product.repository.ResidencyEligibilityHistoryRepository;
import com.fusionx.lending.product.service.ResidencyEligibilityHistoryService;

@Component
@Transactional(rollbackFor=Exception.class)
public class ResidencyEligibilityHistoryServiceImpl implements ResidencyEligibilityHistoryService {
	
	@Autowired
	private ResidencyEligibilityHistoryRepository residencyEligibilityHistoryRepository;

	@Override
	public void insertResidencyEligibilityHistory(String tenantId, ResidencyEligibility residencyEligibility,String historyCreatedUser) {
		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        ResidencyEligibilityHistory residencyEligibilityHistory = new ResidencyEligibilityHistory();
        
        residencyEligibilityHistory.setResidencyEligibilityId(residencyEligibility.getId());
        residencyEligibilityHistory.setCode(residencyEligibility.getCode());
        residencyEligibilityHistory.setTenantId(residencyEligibility.getTenantId());
        residencyEligibilityHistory.setResidencyTypeId(residencyEligibility.getResidencyTypeId());
        residencyEligibilityHistory.setStatus(residencyEligibility.getStatus());
        residencyEligibilityHistory.setCreatedDate(residencyEligibility.getCreatedDate());
        residencyEligibilityHistory.setCreatedUser(residencyEligibility.getCreatedUser());
        residencyEligibilityHistory.setModifiedDate(residencyEligibility.getModifiedDate());
        residencyEligibilityHistory.setModifiedUser(residencyEligibility.getModifiedUser());
        residencyEligibilityHistory.setHistoryCreatedDate(currentTimestamp);
        residencyEligibilityHistory.setHistoryCreatedUser(historyCreatedUser);
        residencyEligibilityHistory.setSyncTs(currentTimestamp);
        residencyEligibilityHistoryRepository.saveAndFlush(residencyEligibilityHistory);
		
	}

}
