package com.fusionx.lending.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.EligibilityResidencyEligibilityHistory;
import com.fusionx.lending.product.domain.EligibilityResidencyEligibility;
import com.fusionx.lending.product.repository.EligibilityResidencyEligibilityHistoryRepository;
import com.fusionx.lending.product.service.EligibilityResidencyEligibilityHistoryService;
import com.fusionx.lending.product.service.ValidationService;


/**
 * EligibilityResidencyEligibilityHistoryServiceImpl
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   28-07-2021    FXL_July_2021_2  	FXL-55		Piyumi      Created
 *    
 *******************************************************************************************************
 */


@Component
@Transactional(rollbackFor=Exception.class)
public class EligibilityResidencyEligibilityHistoryServiceImpl
		implements EligibilityResidencyEligibilityHistoryService {
	
	@Autowired
	private EligibilityResidencyEligibilityHistoryRepository eligibilityResidencyEligibilityHistoryRepository;
	
	@Autowired
 	private ValidationService validationService;


	@Override
	public void saveHistory(String tenantId, EligibilityResidencyEligibility eligibilityResidencyEligibility,
			String historyCreatedUser) {
		
		EligibilityResidencyEligibilityHistory eligibilityResidencyEligibilityHistory = new EligibilityResidencyEligibilityHistory();
		eligibilityResidencyEligibilityHistory.setTenantId(tenantId);
		eligibilityResidencyEligibilityHistory.setEliResidencyEligibilityId(eligibilityResidencyEligibility.getId());
		eligibilityResidencyEligibilityHistory.setEligibilityId(eligibilityResidencyEligibility.getEligibility().getId());
		eligibilityResidencyEligibilityHistory.setResidencyEligibilityId(eligibilityResidencyEligibility.getResidencyEligibility().getId());
	 	eligibilityResidencyEligibilityHistory.setStatus(eligibilityResidencyEligibility.getStatus());
	 	eligibilityResidencyEligibilityHistory.setCreatedDate(eligibilityResidencyEligibility.getCreatedDate());
	 	eligibilityResidencyEligibilityHistory.setCreatedUser(eligibilityResidencyEligibility.getCreatedUser());
	 	eligibilityResidencyEligibilityHistory.setModifiedDate(eligibilityResidencyEligibility.getModifiedDate());
	 	eligibilityResidencyEligibilityHistory.setModifiedUser(eligibilityResidencyEligibility.getModifiedUser());
	 	eligibilityResidencyEligibilityHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
	 	eligibilityResidencyEligibilityHistory.setHistoryCreatedUser(historyCreatedUser);
	 	eligibilityResidencyEligibilityHistory.setVersion(eligibilityResidencyEligibility.getVersion());
	 	eligibilityResidencyEligibilityHistory.setSyncTs(validationService.getCreateOrModifyDate());
	 	eligibilityResidencyEligibilityHistoryRepository.save(eligibilityResidencyEligibilityHistory);

	}

}
