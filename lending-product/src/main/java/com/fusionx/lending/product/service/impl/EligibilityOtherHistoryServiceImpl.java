package com.fusionx.lending.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.EligibilityOther;
import com.fusionx.lending.product.domain.EligibilityOtherHistory;
import com.fusionx.lending.product.repository.EligibilityOtherHistoryRepository;
import com.fusionx.lending.product.service.EligibilityOtherHistoryService;
import com.fusionx.lending.product.service.ValidationService;
/**
 * EligibilityOtherHistoryServiceImpl
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   27-07-2021    FXL_July_2021_2  	FXL-54		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class EligibilityOtherHistoryServiceImpl  implements EligibilityOtherHistoryService {
	
	@Autowired
	private EligibilityOtherHistoryRepository eligibilityOtherHistoryRepository;
	
	@Autowired
 	private ValidationService validationService;

	@Override
	public void saveHistory(String tenantId, EligibilityOther eligibilityOther, String historyCreatedUser) {
		
		EligibilityOtherHistory eligibilityOtherHistory = new EligibilityOtherHistory();
		eligibilityOtherHistory.setTenantId(tenantId);
		eligibilityOtherHistory.setEligibilityOtherId(eligibilityOther.getId());
		eligibilityOtherHistory.setEligibilityId(eligibilityOther.getEligibility().getId());
		eligibilityOtherHistory.setOtherEligibilityTypeId(eligibilityOther.getOtherEligibilityType().getId());
	 	eligibilityOtherHistory.setStatus(eligibilityOther.getStatus());
	 	eligibilityOtherHistory.setCreatedDate(eligibilityOther.getCreatedDate());
	 	eligibilityOtherHistory.setCreatedUser(eligibilityOther.getCreatedUser());
	 	eligibilityOtherHistory.setModifiedDate(eligibilityOther.getModifiedDate());
	 	eligibilityOtherHistory.setModifiedUser(eligibilityOther.getModifiedUser());
	 	eligibilityOtherHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
	 	eligibilityOtherHistory.setHistoryCreatedUser(historyCreatedUser);
	 	eligibilityOtherHistory.setVersion(eligibilityOther.getVersion());
	 	eligibilityOtherHistory.setSyncTs(validationService.getCreateOrModifyDate());
	 	eligibilityOtherHistoryRepository.save(eligibilityOtherHistory);
		
	}

}
