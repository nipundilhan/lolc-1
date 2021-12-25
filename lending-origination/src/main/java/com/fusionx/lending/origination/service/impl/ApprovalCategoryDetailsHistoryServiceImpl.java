package com.fusionx.lending.origination.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
 * Approval Category Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-09-2021   FXL-338 		 FXL-803 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.domain.ApprovalCategoryDetails;
import com.fusionx.lending.origination.domain.ApprovalCategoryDetailsHistory;
import com.fusionx.lending.origination.repository.ApprovalCategoryDetailsHistoryRepository;
import com.fusionx.lending.origination.service.ApprovalCategoryDetailsHistoryService;

@Component
@Transactional(rollbackFor = Exception.class)
public class ApprovalCategoryDetailsHistoryServiceImpl implements ApprovalCategoryDetailsHistoryService {

	@Autowired
	private ApprovalCategoryDetailsHistoryRepository approvalCategoryDetailsHistoryRepository;

	@Override
	public void saveHistory(String tenantId, ApprovalCategoryDetails approvalCategoryDetails,
			String historyCreatedUser) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		ApprovalCategoryDetailsHistory approvalCategoryDetailsHistory = new ApprovalCategoryDetailsHistory();

		approvalCategoryDetailsHistory.setTenantId(tenantId);
		approvalCategoryDetailsHistory.setApprovalCategoryDetailsId(approvalCategoryDetails.getId());
		approvalCategoryDetailsHistory.setApprovalType(approvalCategoryDetails.getApprovalType());
		approvalCategoryDetailsHistory.setCode(approvalCategoryDetails.getCode());
		approvalCategoryDetailsHistory.setName(approvalCategoryDetails.getName());
		approvalCategoryDetailsHistory.setDescription(approvalCategoryDetails.getDescription());
		approvalCategoryDetailsHistory.setModifyWorkflow(approvalCategoryDetails.getModifyWorkflow());
		approvalCategoryDetailsHistory.setStatus(approvalCategoryDetails.getStatus());
		approvalCategoryDetailsHistory.setCreatedDate(approvalCategoryDetails.getCreatedDate());
		approvalCategoryDetailsHistory.setCreatedUser(approvalCategoryDetails.getCreatedUser());
		approvalCategoryDetailsHistory.setModifiedDate(approvalCategoryDetails.getModifiedDate());
		approvalCategoryDetailsHistory.setModifiedUser(approvalCategoryDetails.getModifiedUser());
		approvalCategoryDetailsHistory.setVersion(approvalCategoryDetails.getVersion());
		approvalCategoryDetailsHistory.setHistoryCreatedUser(historyCreatedUser);
		approvalCategoryDetailsHistory.setHistoryCreatedDate(currentTimestamp);
		approvalCategoryDetailsHistory.setSyncTs(currentTimestamp);

		approvalCategoryDetailsHistoryRepository.saveAndFlush(approvalCategoryDetailsHistory);
	}

}
