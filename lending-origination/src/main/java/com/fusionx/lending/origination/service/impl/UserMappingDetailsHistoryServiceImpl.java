package com.fusionx.lending.origination.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.domain.UserMappingDetails;
import com.fusionx.lending.origination.domain.UserMappingDetailsHistory;
import com.fusionx.lending.origination.repository.UserMappingDetailsHistoryRepository;
import com.fusionx.lending.origination.service.UserMappingDetailsHistoryService;

@Component
@Transactional(rollbackFor = Exception.class)
public class UserMappingDetailsHistoryServiceImpl implements UserMappingDetailsHistoryService {

	@Autowired
	private UserMappingDetailsHistoryRepository userMappingDetailsHistoryRepository;

	@Override
	public void saveHistory(String tenantId, UserMappingDetails userMappingDetails, String historyCreatedUser) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		UserMappingDetailsHistory userMappingDetailsHistory = new UserMappingDetailsHistory();

		userMappingDetailsHistory.setTenantId(tenantId);
		userMappingDetailsHistory.setUserMappingDetailsId(userMappingDetails.getId());
		userMappingDetailsHistory.setUserId(userMappingDetails.getUserId());
		userMappingDetailsHistory.setApprovalGroupId(userMappingDetails.getApprovalGroup());
		userMappingDetailsHistory.setName(userMappingDetails.getName());
		userMappingDetailsHistory.setStatus(userMappingDetails.getStatus());
		userMappingDetailsHistory.setCreatedDate(userMappingDetails.getCreatedDate());
		userMappingDetailsHistory.setCreatedUser(userMappingDetails.getCreatedUser());
		userMappingDetailsHistory.setModifiedDate(userMappingDetails.getModifiedDate());
		userMappingDetailsHistory.setModifiedUser(userMappingDetails.getModifiedUser());
		userMappingDetailsHistory.setVersion(userMappingDetails.getVersion());
		userMappingDetailsHistory.setHistoryCreatedUser(historyCreatedUser);
		userMappingDetailsHistory.setHistoryCreatedDate(currentTimestamp);
		userMappingDetailsHistory.setSyncTs(currentTimestamp);

		userMappingDetailsHistoryRepository.saveAndFlush(userMappingDetailsHistory);
	}

}
