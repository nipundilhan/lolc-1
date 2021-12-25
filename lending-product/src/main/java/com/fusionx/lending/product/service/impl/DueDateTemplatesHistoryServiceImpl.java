package com.fusionx.lending.product.service.impl;
/**
 * Due Date Templates History Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-09-2021    FXL-139  	 FXL-926	Piyumi      Created
 *    
 ********************************************************************************************************
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.DueDateTemplates;
import com.fusionx.lending.product.domain.DueDateTemplatesHistory;
import com.fusionx.lending.product.repository.DueDateTemplatesHistoryRepository;
import com.fusionx.lending.product.service.DueDateTemplatesHistoryService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor=Exception.class)
public class DueDateTemplatesHistoryServiceImpl implements DueDateTemplatesHistoryService {

	@Autowired
	private DueDateTemplatesHistoryRepository dueDateTemplatesHistoryRepository;
	
	@Autowired
	private ValidationService validationService;
	
	@Override
	public void saveHistory(String tenantId, DueDateTemplates dueDateTemplates,
			String historyCreatedUser) {
        
        DueDateTemplatesHistory dueDateTemplatesHistory = new DueDateTemplatesHistory(); 
        dueDateTemplatesHistory.setDueDateTemplateId(dueDateTemplates.getId());
        dueDateTemplatesHistory.setCode(dueDateTemplates.getCode());
        dueDateTemplatesHistory.setName(dueDateTemplates.getName());
        dueDateTemplatesHistory.setDueDateType(dueDateTemplates.getDueDateType().toString());
        dueDateTemplatesHistory.setEffectiveDate(dueDateTemplates.getEffectiveDate());
        dueDateTemplatesHistory.setTenantId(dueDateTemplates.getTenantId());
        dueDateTemplatesHistory.setStatus(dueDateTemplates.getStatus());
        dueDateTemplatesHistory.setCreatedDate(dueDateTemplates.getCreatedDate());
        dueDateTemplatesHistory.setCreatedUser(dueDateTemplates.getCreatedUser());
        dueDateTemplatesHistory.setModifiedDate(dueDateTemplates.getModifiedDate());
        dueDateTemplatesHistory.setModifiedUser(dueDateTemplates.getModifiedUser());
        dueDateTemplatesHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
        dueDateTemplatesHistory.setHistoryCreatedUser(historyCreatedUser);
        dueDateTemplatesHistory.setSyncTs(validationService.getSyncTs());
        dueDateTemplatesHistoryRepository.saveAndFlush(dueDateTemplatesHistory);

	}

}
