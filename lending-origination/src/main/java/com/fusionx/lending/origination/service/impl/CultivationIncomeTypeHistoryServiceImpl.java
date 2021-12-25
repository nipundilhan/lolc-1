package com.fusionx.lending.origination.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.domain.CultivationIncomeType;
import com.fusionx.lending.origination.domain.CultivationIncomeTypeHistory;
import com.fusionx.lending.origination.repository.CultivationIncomeTypeHistoryRepository;
import com.fusionx.lending.origination.service.CultivationIncomeTypeHistoryService;
/**
 * Cultivation Income Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-08-2021   FXL-338 		 FXL-533 	Dilki        Created
 *    
 ********************************************************************************************************
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class CultivationIncomeTypeHistoryServiceImpl implements CultivationIncomeTypeHistoryService {

	@Autowired
	private CultivationIncomeTypeHistoryRepository cultivationIncomeTypeHistoryRepository;

	@Override
	public void saveHistory(String tenantId, CultivationIncomeType cultivationIncomeType, String historyCreatedUser) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		CultivationIncomeTypeHistory cultivationIncomeTypeHistory = new CultivationIncomeTypeHistory();

		cultivationIncomeTypeHistory.setTenantId(tenantId);
		cultivationIncomeTypeHistory.setCultivationIncomeTypeId(cultivationIncomeType.getId());
		cultivationIncomeTypeHistory.setCode(cultivationIncomeType.getCode());
		cultivationIncomeTypeHistory.setName(cultivationIncomeType.getName());
		cultivationIncomeTypeHistory.setDescription(cultivationIncomeType.getDescription());
		cultivationIncomeTypeHistory.setStatus(cultivationIncomeType.getStatus());
		cultivationIncomeTypeHistory.setCreatedDate(cultivationIncomeType.getCreatedDate());
		cultivationIncomeTypeHistory.setCreatedUser(cultivationIncomeType.getCreatedUser());
		cultivationIncomeTypeHistory.setModifiedDate(cultivationIncomeType.getModifiedDate());
		cultivationIncomeTypeHistory.setModifiedUser(cultivationIncomeType.getModifiedUser());
		cultivationIncomeTypeHistory.setVersion(cultivationIncomeType.getVersion());
		cultivationIncomeTypeHistory.setHistoryCreatedUser(historyCreatedUser);
		cultivationIncomeTypeHistory.setHistoryCreatedDate(currentTimestamp);
		cultivationIncomeTypeHistory.setSyncTs(currentTimestamp);

		cultivationIncomeTypeHistoryRepository.saveAndFlush(cultivationIncomeTypeHistory);
	}

}
