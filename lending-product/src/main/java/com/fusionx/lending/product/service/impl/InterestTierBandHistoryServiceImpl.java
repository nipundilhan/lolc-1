package com.fusionx.lending.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.InterestTierBand;
import com.fusionx.lending.product.domain.InterestTierBandHistory;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.repository.InterestTierBandHistoryRepository;
import com.fusionx.lending.product.service.InterestTierBandHistoryService;
import com.fusionx.lending.product.service.ValidationService;
/**
 * InterestTierBandHistoryServiceImpl 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   22-07-2021    FXL_July_2021_2  	FXL-53		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class InterestTierBandHistoryServiceImpl implements InterestTierBandHistoryService {
	
	@Autowired
	private InterestTierBandHistoryRepository interestTierBandHistoryRepository;
	
	@Autowired
 	private ValidationService validationService;

	@Override
	public void saveHistory(String tenantId, InterestTierBand interestTierBand, String historyCreatedUser) {

		 	InterestTierBandHistory interestTierBandHistory = new InterestTierBandHistory();
		 	interestTierBandHistory.setTenantId(tenantId);
		 	interestTierBandHistory.setInterestTierBandSet(interestTierBand.getInterestTierBandSet().getId());
		 	interestTierBandHistory.setTierValueMinimum(interestTierBand.getTierValueMinimum());
		 	interestTierBandHistory.setTierValueMaximum(interestTierBand.getTierValueMaximum());
		 	interestTierBandHistory.setTierValueMinTermId(interestTierBand.getTierValueMinTermId() != null ? interestTierBand.getTierValueMinTermId().getId():null);
		 	interestTierBandHistory.setMinTermPeriodId(interestTierBand.getMinTermPeriodId());
		 	interestTierBandHistory.setTierValueMaxTermId(interestTierBand.getTierValueMaxTermId() != null ?interestTierBand.getTierValueMaxTermId().getId():null);
		 	interestTierBandHistory.setMaxTermPeriodId(interestTierBand.getMaxTermPeriodId());
		 	interestTierBandHistory.setInterestRateTypeId(interestTierBand.getInterestRateTypeId() != null ? interestTierBand.getInterestRateTypeId().getId() :null);
		 	interestTierBandHistory.setLoanPrInterestRateTypeId(interestTierBand.getLoanPrInterestRateType() != null ? interestTierBand.getLoanPrInterestRateType().getId() :null);
		 	interestTierBandHistory.setLoanPrInterestRate(interestTierBand.getLoanPrInterestRate());
		 	interestTierBandHistory.setNote(interestTierBand.getNote());
		 	interestTierBandHistory.setCode(interestTierBand.getCode());
		 	interestTierBandHistory.setStatus(interestTierBand.getStatus());
		 	interestTierBandHistory.setCreatedDate(interestTierBand.getCreatedDate());
		 	interestTierBandHistory.setCreatedUser(interestTierBand.getCreatedUser());
		 	interestTierBandHistory.setModifiedDate(interestTierBand.getModifiedDate());
		 	interestTierBandHistory.setModifiedUser(interestTierBand.getModifiedUser());
		 	interestTierBandHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
		 	interestTierBandHistory.setHistoryCreatedUser(historyCreatedUser);
		 	interestTierBandHistory.setVersion(interestTierBand.getVersion());
		 	interestTierBandHistory.setSyncTs(validationService.getCreateOrModifyDate());
	        interestTierBandHistoryRepository.save(interestTierBandHistory);
		
	}

}
