package com.fusionx.lending.origination.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.domain.CheckListItem;
import com.fusionx.lending.origination.domain.CheckListItemHistory;
import com.fusionx.lending.origination.repository.CheckListItemHistoryRepository;
import com.fusionx.lending.origination.service.CheckListItemHistoryService;
/**
 * Check List Item
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021   FXL-75  		 FXL-550 	Dilki        Created
 *    
 ********************************************************************************************************
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class CheckListItemHistoryServiceImpl implements CheckListItemHistoryService {

	@Autowired
	private CheckListItemHistoryRepository checkListItemHistoryRepository;

	@Override
	public void saveHistory(String tenantId, CheckListItem checkListItem, String historyCreatedUser) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		CheckListItemHistory checkListItemHistory = new CheckListItemHistory();

		checkListItemHistory.setTenantId(tenantId);
		checkListItemHistory.setCheckListItemId(checkListItem.getId());
		checkListItemHistory.setCode(checkListItem.getCode());
		checkListItemHistory.setName(checkListItem.getName());
		checkListItemHistory.setCategory(checkListItem.getCategory());
		checkListItemHistory.setDescription(checkListItem.getDescription());
		checkListItemHistory.setStatus(checkListItem.getStatus());
		checkListItemHistory.setCreatedDate(checkListItem.getCreatedDate());
		checkListItemHistory.setCreatedUser(checkListItem.getCreatedUser());
		checkListItemHistory.setModifiedDate(checkListItem.getModifiedDate());
		checkListItemHistory.setModifiedUser(checkListItem.getModifiedUser());
		checkListItemHistory.setVersion(checkListItem.getVersion());
		checkListItemHistory.setHistoryCreatedUser(historyCreatedUser);
		checkListItemHistory.setHistoryCreatedDate(currentTimestamp);
		checkListItemHistory.setSyncTs(currentTimestamp);

		checkListItemHistoryRepository.saveAndFlush(checkListItemHistory);
	}

}
