package com.fusionx.lending.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.FeeChargeCap;
import com.fusionx.lending.product.domain.FeeChargeCapHistory;
import com.fusionx.lending.product.repository.FeeChargeCapHistoryRepository;
import com.fusionx.lending.product.service.FeeChargeCapHistoryService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor=Exception.class)
public class FeeChargeCapHistoryServiceImpl implements FeeChargeCapHistoryService{

	@Autowired
	private FeeChargeCapHistoryRepository feeChargeCapHistoryRepository;
	
	@Autowired
	private ValidationService validationService;
	
	@Override
	public void saveHistory(String tenantId, FeeChargeCap feeChargeCap, String historyCreatedUser) {
		
        FeeChargeCapHistory feeChargeCapHistory = new FeeChargeCapHistory();
		
        feeChargeCapHistory.setTenantId(tenantId);
        feeChargeCapHistory.setCode(feeChargeCap.getCode());
        feeChargeCapHistory.setFeeChargeId(feeChargeCap.getFeeCharge().getId());
        feeChargeCapHistory.setOtherFeeTypeId(feeChargeCap.getOtherFeeType().getId());
        feeChargeCapHistory.setFeeChargeCapId(feeChargeCap.getId());
        feeChargeCapHistory.setMinimumAmount(feeChargeCap.getMinimumAmount());
        feeChargeCapHistory.setFeeCapPeriodId(feeChargeCap.getFeeCapPeriodId());
        feeChargeCapHistory.setPeriodCode(feeChargeCap.getPeriodCode());
        feeChargeCapHistory.setMinMaxType(feeChargeCap.getMinMaxType());
        feeChargeCapHistory.setStatus(feeChargeCap.getStatus());
        feeChargeCapHistory.setCreatedDate(feeChargeCap.getCreatedDate());
        feeChargeCapHistory.setCreatedUser(feeChargeCap.getCreatedUser());
        feeChargeCapHistory.setModifiedDate(feeChargeCap.getModifiedDate());
        feeChargeCapHistory.setModifiedUser(feeChargeCap.getModifiedUser());
        feeChargeCapHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
        feeChargeCapHistory.setHistoryCreatedUser(historyCreatedUser);
        feeChargeCapHistory.setVersion(feeChargeCap.getVersion());
        feeChargeCapHistory.setSyncTs(validationService.getCreateOrModifyDate());
		
		feeChargeCapHistoryRepository.save(feeChargeCapHistory);
	}

}
