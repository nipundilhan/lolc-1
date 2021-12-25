package com.fusionx.lending.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Due Date Details History Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-09-2021    FXL-139  	 FXL-926	Piyumi      Created
 *    
 ********************************************************************************************************
 */

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.DueDateDetails;
import com.fusionx.lending.product.domain.DueDateDetailsHistory;
import com.fusionx.lending.product.repository.DueDateDetailsHistoryRepository;
import com.fusionx.lending.product.service.DueDateDetailsHistoryService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor=Exception.class)
public class DueDateDetailsHistoryServiceImpl implements DueDateDetailsHistoryService {
	
	@Autowired
	private DueDateDetailsHistoryRepository dueDateDetailsHistoryRepository;
	
	@Autowired
	private ValidationService validationService;
	
	@Override
	public void saveHistory(String tenantId, DueDateDetails dueDateDetails, String historyCreatedUser) {
		
		 	DueDateDetailsHistory dueDateDetailsHistory = new DueDateDetailsHistory(); 
	        dueDateDetailsHistory.setDueDateTemplateId(dueDateDetails.getDueDateTmp().getId());
	        dueDateDetailsHistory.setDueDateDetailId(dueDateDetails.getId());
	        dueDateDetailsHistory.setSequence(dueDateDetails.getSequence());
	        dueDateDetailsHistory.setDueDate(dueDateDetails.getDueDate());
	        dueDateDetailsHistory.setTenantId(dueDateDetails.getTenantId());
	        dueDateDetailsHistory.setStatus(dueDateDetails.getStatus());
	        dueDateDetailsHistory.setCreatedDate(dueDateDetails.getCreatedDate());
	        dueDateDetailsHistory.setCreatedUser(dueDateDetails.getCreatedUser());
	        dueDateDetailsHistory.setModifiedDate(dueDateDetails.getModifiedDate());
	        dueDateDetailsHistory.setModifiedUser(dueDateDetails.getModifiedUser());
	        dueDateDetailsHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
	        dueDateDetailsHistory.setHistoryCreatedUser(historyCreatedUser);
	        dueDateDetailsHistory.setSyncTs(validationService.getSyncTs());
	        dueDateDetailsHistoryRepository.saveAndFlush(dueDateDetailsHistory);
		
	}

	

}
