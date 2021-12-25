package com.fusionx.lending.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.FeeChargeHistory;
import com.fusionx.lending.product.repository.FeeChargeHistoryRepository;
import com.fusionx.lending.product.service.FeeChargeHistoryService;
import com.fusionx.lending.product.service.ValidationService;
@Component
@Transactional(rollbackFor=Exception.class)
public class FeeChargeHistoryServiceImpl implements FeeChargeHistoryService {
	
	@Autowired
	private FeeChargeHistoryRepository feeChargeHistoryRepository;
	
	@Autowired
	private ValidationService validationService;

	@Override
	public void saveHistory(String tenantId, FeeCharge feeCharge, String historyCreatedUser) {
		
		FeeChargeHistory FeeChargeHistory = new FeeChargeHistory();
		
		FeeChargeHistory.setTenantId(tenantId);
		FeeChargeHistory.setCode(feeCharge.getCode());
		FeeChargeHistory.setName(feeCharge.getName());
		FeeChargeHistory.setFeeChargeId(feeCharge.getId());
		FeeChargeHistory.setFeeChargeTypeId(feeCharge.getFeeChargeType().getId());
		FeeChargeHistory.setStatus(feeCharge.getStatus());
		FeeChargeHistory.setCreatedDate(feeCharge.getCreatedDate());
		FeeChargeHistory.setCreatedUser(feeCharge.getCreatedUser());
		FeeChargeHistory.setModifiedDate(feeCharge.getModifiedDate());
		FeeChargeHistory.setModifiedUser(feeCharge.getModifiedUser());
		FeeChargeHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
		FeeChargeHistory.setHistoryCreatedUser(historyCreatedUser);
		FeeChargeHistory.setVersion(feeCharge.getVersion());
		FeeChargeHistory.setSyncTs(validationService.getCreateOrModifyDate());
		
		feeChargeHistoryRepository.save(FeeChargeHistory);
		
	}

}
