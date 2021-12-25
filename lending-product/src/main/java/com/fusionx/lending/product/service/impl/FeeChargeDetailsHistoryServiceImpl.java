package com.fusionx.lending.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.FeeChargeDetails;
import com.fusionx.lending.product.domain.FeeChargeDetailsHistory;
import com.fusionx.lending.product.repository.FeeChargeDetailsHistoryRepository;
import com.fusionx.lending.product.service.FeeChargeDetailsHistoryService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor=Exception.class)
public class FeeChargeDetailsHistoryServiceImpl implements FeeChargeDetailsHistoryService{

	@Autowired
	private FeeChargeDetailsHistoryRepository feeChargeDetailsHistoryRepository;
	
	@Autowired
	private ValidationService validationService;
	
	@Override
	public void saveHistory(String tenantId, FeeChargeDetails feeChargeDetails, String historyCreatedUser) {
		
		FeeChargeDetailsHistory feeChargeHistoryDetails = new FeeChargeDetailsHistory();
		feeChargeHistoryDetails.setTenantId(tenantId);
		feeChargeHistoryDetails.setFeeChargeDetailsId(feeChargeDetails.getId());
		feeChargeHistoryDetails.setCode(feeChargeDetails.getCode());
		feeChargeHistoryDetails.setAmount(feeChargeDetails.getAmount());
		feeChargeHistoryDetails.setRateTypeId(feeChargeDetails.getRateTypeId());
		feeChargeHistoryDetails.setType(feeChargeDetails.getType());
		feeChargeHistoryDetails.setFeeChargeId(feeChargeDetails.getFeeCharge().getId());
		feeChargeHistoryDetails.setCalculationFrequencyId(feeChargeDetails.getCalculationFrequency().getId());
		feeChargeHistoryDetails.setApplicationFrequencyId(feeChargeDetails.getApplicationFrequency().getId());
		feeChargeHistoryDetails.setOtherFeeTypeId(feeChargeDetails.getOtherFeeType().getId());
		feeChargeHistoryDetails.setFeeCategoryId(feeChargeDetails.getFeeCategory().getId());
		feeChargeHistoryDetails.setIsNegotiable(feeChargeDetails.getIsNegotiable());
		feeChargeHistoryDetails.setMaxAmount(feeChargeDetails.getMaxAmount());
		feeChargeHistoryDetails.setMinAmount(feeChargeDetails.getMinAmount());
		feeChargeHistoryDetails.setEffectiveDate(feeChargeDetails.getEffectiveDate());
		feeChargeHistoryDetails.setStatus(feeChargeDetails.getStatus());
		feeChargeHistoryDetails.setCreatedDate(feeChargeDetails.getCreatedDate());
		feeChargeHistoryDetails.setCreatedUser(feeChargeDetails.getCreatedUser());
		feeChargeHistoryDetails.setModifiedDate(feeChargeDetails.getModifiedDate());
		feeChargeHistoryDetails.setModifiedUser(feeChargeDetails.getModifiedUser());
		feeChargeHistoryDetails.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
		feeChargeHistoryDetails.setHistoryCreatedUser(historyCreatedUser);
		feeChargeHistoryDetails.setVersion(feeChargeDetails.getVersion());
		feeChargeHistoryDetails.setSyncTs(validationService.getCreateOrModifyDate());
			
		feeChargeDetailsHistoryRepository.save(feeChargeHistoryDetails);
		

	}

}
