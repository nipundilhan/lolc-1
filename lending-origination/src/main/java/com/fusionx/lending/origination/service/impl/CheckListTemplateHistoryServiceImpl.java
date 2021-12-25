package com.fusionx.lending.origination.service.impl;

/**
 * Check List Template
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-08-2021   FXL-75  		 FXL-551 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.domain.CheckListTemplate;
import com.fusionx.lending.origination.domain.CheckListTemplateDetails;
import com.fusionx.lending.origination.domain.CheckListTemplateDetailsHistory;
import com.fusionx.lending.origination.domain.CheckListTemplateHistory;
import com.fusionx.lending.origination.repository.CheckListTemplateDetailsHistoryRepository;
import com.fusionx.lending.origination.repository.CheckListTemplateHistoryRepository;
import com.fusionx.lending.origination.service.CheckListTemplateHistoryService;

@Component
@Transactional(rollbackFor = Exception.class)
public class CheckListTemplateHistoryServiceImpl implements CheckListTemplateHistoryService {

	@Autowired
	private CheckListTemplateHistoryRepository checkListTemplateHistoryRepository;

	@Autowired
	private CheckListTemplateDetailsHistoryRepository checkListTemplateDetailsHistoryRepository;

	@Override
	public void saveHistory(String tenantId, CheckListTemplate checkListTemplate, String historyCreatedUser) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		CheckListTemplateHistory checkListTemplateHistory = new CheckListTemplateHistory();

		checkListTemplateHistory.setTenantId(tenantId);
		checkListTemplateHistory.setCheckListTemplateId(checkListTemplate.getId());
		checkListTemplateHistory.setCode(checkListTemplate.getCode());
		checkListTemplateHistory.setName(checkListTemplate.getName());
		checkListTemplateHistory.setMainProduct(checkListTemplate.getMainProduct());
		checkListTemplateHistory.setSubProduct(checkListTemplate.getSubProduct());
		//checkListTemplateHistory.setApplicableLevel(checkListTemplate.getApplicableLevel());
		checkListTemplateHistory.setComment(checkListTemplate.getComment());
		checkListTemplateHistory.setStatus(checkListTemplate.getStatus());
		checkListTemplateHistory.setCreatedDate(checkListTemplate.getCreatedDate());
		checkListTemplateHistory.setCreatedUser(checkListTemplate.getCreatedUser());
		checkListTemplateHistory.setModifiedDate(checkListTemplate.getModifiedDate());
		checkListTemplateHistory.setModifiedUser(checkListTemplate.getModifiedUser());
		checkListTemplateHistory.setVersion(checkListTemplate.getVersion());
		checkListTemplateHistory.setHistoryCreatedUser(historyCreatedUser);
		checkListTemplateHistory.setHistoryCreatedDate(currentTimestamp);
		checkListTemplateHistory.setSyncTs(currentTimestamp);

		checkListTemplateHistoryRepository.saveAndFlush(checkListTemplateHistory);

		if (checkListTemplate.getCheckListTemplateDetails() != null
				&& !checkListTemplate.getCheckListTemplateDetails().isEmpty()) {

			for (CheckListTemplateDetails checkListTemplateDetails : checkListTemplate.getCheckListTemplateDetails()) {

				CheckListTemplateDetailsHistory checkListTemplateDetailsH = new CheckListTemplateDetailsHistory();
				checkListTemplateDetailsH.setTenantId(tenantId);
				checkListTemplateDetailsH.setCheckListItem(checkListTemplateDetails.getCheckListItem());
				checkListTemplateDetailsH.setCheckListTemplate(checkListTemplateDetails.getCheckListTemplate());
				checkListTemplateDetailsH.setChecklistTemplateName(checkListTemplateDetails.getChecklistTemplateName());
				checkListTemplateDetailsH.setIsMandatory(checkListTemplateDetails.getIsMandatory());
				checkListTemplateDetailsH.setIsChecked(checkListTemplateDetails.getIsChecked());
				checkListTemplateDetailsH.setStatus(checkListTemplateDetails.getStatus());
				checkListTemplateDetailsH.setCreatedDate(checkListTemplateDetails.getCreatedDate());
				checkListTemplateDetailsH.setCreatedUser(checkListTemplateDetails.getCreatedUser());
				checkListTemplateDetailsH.setSyncTs(checkListTemplateDetails.getSyncTs());
				checkListTemplateDetailsH.setHistoryCreatedUser(historyCreatedUser);
				checkListTemplateDetailsH.setHistoryCreatedDate(currentTimestamp);
				checkListTemplateDetailsHistoryRepository.save(checkListTemplateDetailsH);
			}
		}
	}

}
