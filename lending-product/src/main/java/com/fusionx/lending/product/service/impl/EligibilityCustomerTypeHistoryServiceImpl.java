package com.fusionx.lending.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.EligibilityCustomerTypeHistory;
import com.fusionx.lending.product.domain.EligibilityCustomerType;
import com.fusionx.lending.product.repository.EligibilityCustomerTypeHistoryRepository;
import com.fusionx.lending.product.service.EligibilityCustomerTypeHistoryService;
import com.fusionx.lending.product.service.ValidationService;


/**
 * EligibilityCustomerTypeHistoryServiceImpl
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1    29-07-2021    FXL_365			FXL-56		Piyumi      Created
 *    
 *******************************************************************************************************
 */


@Component
@Transactional(rollbackFor=Exception.class)
public class EligibilityCustomerTypeHistoryServiceImpl
		implements EligibilityCustomerTypeHistoryService {
	
	@Autowired
	private EligibilityCustomerTypeHistoryRepository eligibilityCustomerTypeHistoryRepository;
	
	@Autowired
 	private ValidationService validationService;


	@Override
	public void saveHistory(String tenantId, EligibilityCustomerType eligibilityCustomerType,
			String historyCreatedUser) {
		
		EligibilityCustomerTypeHistory eligibilityCustomerTypeHistory = new EligibilityCustomerTypeHistory();
		eligibilityCustomerTypeHistory.setTenantId(tenantId);
		eligibilityCustomerTypeHistory.setEligibilityCustomerTypeId(eligibilityCustomerType.getId());
		eligibilityCustomerTypeHistory.setEligibilityId(eligibilityCustomerType.getEligibility().getId());
		eligibilityCustomerTypeHistory.setCustomerTypeId(eligibilityCustomerType.getCustomerTypeId());
	 	eligibilityCustomerTypeHistory.setStatus(eligibilityCustomerType.getStatus());
	 	eligibilityCustomerTypeHistory.setCreatedDate(eligibilityCustomerType.getCreatedDate());
	 	eligibilityCustomerTypeHistory.setCreatedUser(eligibilityCustomerType.getCreatedUser());
	 	eligibilityCustomerTypeHistory.setModifiedDate(eligibilityCustomerType.getModifiedDate());
	 	eligibilityCustomerTypeHistory.setModifiedUser(eligibilityCustomerType.getModifiedUser());
	 	eligibilityCustomerTypeHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
	 	eligibilityCustomerTypeHistory.setHistoryCreatedUser(historyCreatedUser);
	 	eligibilityCustomerTypeHistory.setVersion(eligibilityCustomerType.getVersion());
	 	eligibilityCustomerTypeHistory.setSyncTs(validationService.getCreateOrModifyDate());
	 	eligibilityCustomerTypeHistoryRepository.save(eligibilityCustomerTypeHistory);

	}

}
