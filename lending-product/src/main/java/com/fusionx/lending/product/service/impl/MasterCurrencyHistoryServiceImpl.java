package com.fusionx.lending.product.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.MasterCurrency;
import com.fusionx.lending.product.domain.MasterCurrencyHistory;
import com.fusionx.lending.product.repository.MasterCurrencyHistoryRepository;
import com.fusionx.lending.product.service.MasterCurrencyHistoryService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor = Exception.class)
public class MasterCurrencyHistoryServiceImpl implements MasterCurrencyHistoryService {
	
	private MasterCurrencyHistoryRepository masterCurrencyHistoryRepository;
	
	@Autowired
	private ValidationService validationService;

	@Override
	public void saveHistory(String tenantId, MasterCurrency masterCurrency, String historyCreatedUser) {
		
        MasterCurrencyHistory masterCurrencyHistory = new MasterCurrencyHistory();
        masterCurrencyHistory.setTenantId(tenantId);
        masterCurrencyHistory.setMasterCurrencyId(masterCurrency.getId());
        masterCurrencyHistory.setCurrencyId(masterCurrency.getCurrencyId());
        masterCurrencyHistory.setCurrencyName(masterCurrency.getCurrencyName());
        masterCurrencyHistory.setNumOfDecimalPlaces(masterCurrency.getNumOfDecimalPlaces());
        masterCurrencyHistory.setMasterDefinitionId(masterCurrency.getMasterDefinition().getId());
        masterCurrencyHistory.setStatus(masterCurrency.getStatus());
        masterCurrencyHistory.setCreatedDate(masterCurrency.getCreatedDate());
        masterCurrencyHistory.setCreatedUser(masterCurrency.getCreatedUser());
        masterCurrencyHistory.setModifiedDate(masterCurrency.getModifiedDate());
        masterCurrencyHistory.setModifiedUser(masterCurrency.getModifiedUser());
        masterCurrencyHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
        masterCurrencyHistory.setHistoryCreatedUser(historyCreatedUser);
        masterCurrencyHistory.setVersion(masterCurrency.getVersion());
        masterCurrencyHistory.setSyncTs(validationService.getCreateOrModifyDate());      
        //  tempory commented
        //	masterCurrencyHistoryRepository.save(masterCurrencyHistory);

       	
	}

}
