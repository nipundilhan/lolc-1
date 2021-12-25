package com.fusionx.lending.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.MasterDefinition;
import com.fusionx.lending.product.domain.MasterDefinitionHistory;
import com.fusionx.lending.product.repository.MasterDefinitionHistoryRepository;
import com.fusionx.lending.product.service.MasterDefinitionHistoryService;
import com.fusionx.lending.product.service.ValidationService;

/**
Master Definition 
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-07-2019                            Dilki        Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class MasterDefinitionHistoryServiceImpl implements MasterDefinitionHistoryService {

	@Autowired
	private MasterDefinitionHistoryRepository masterDefinitionHistoryRepository;

	@Autowired
	private ValidationService validationService;

	@Override
	public void saveHistory(String tenantId, MasterDefinition masterDefinition, String historyCreatedUser) {

		MasterDefinitionHistory MasterDefinitionHistory = new MasterDefinitionHistory();

		MasterDefinitionHistory.setTenantId(tenantId);
		MasterDefinitionHistory.setMasterDefinitionId(masterDefinition.getId());
		MasterDefinitionHistory.setCode(masterDefinition.getCode());
		MasterDefinitionHistory.setName(masterDefinition.getName());
		MasterDefinitionHistory.setModule(masterDefinition.getModule());
		MasterDefinitionHistory.setDescription(masterDefinition.getDescription());
		MasterDefinitionHistory.setSubModuleCode(masterDefinition.getSubModuleCode());
		MasterDefinitionHistory.setSubModuleName(masterDefinition.getSubModuleName());
		MasterDefinitionHistory.setBusinessPrinciple(masterDefinition.getBusinessPrinciple());
		MasterDefinitionHistory.setStatus(masterDefinition.getStatus());
		MasterDefinitionHistory.setApproveStatus(masterDefinition.getApproveStatus());
		MasterDefinitionHistory.setCreatedDate(masterDefinition.getCreatedDate());
		MasterDefinitionHistory.setCreatedUser(masterDefinition.getCreatedUser());
		MasterDefinitionHistory.setModifiedDate(masterDefinition.getModifiedDate());
		MasterDefinitionHistory.setModifiedUser(masterDefinition.getModifiedUser());
		MasterDefinitionHistory.setPenApprovedDate(masterDefinition.getPenApprovedDate());
		MasterDefinitionHistory.setPenApprovedUser(masterDefinition.getPenApprovedUser());
		MasterDefinitionHistory.setPenRejectedDate(masterDefinition.getPenRejectedDate());
		MasterDefinitionHistory.setPenRejectedUser(masterDefinition.getPenRejectedUser());
		MasterDefinitionHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
		MasterDefinitionHistory.setHistoryCreatedUser(historyCreatedUser);
		MasterDefinitionHistory.setVersion(masterDefinition.getVersion());
		MasterDefinitionHistory.setSyncTs(validationService.getCreateOrModifyDate());
        //added by dilhan
		MasterDefinitionHistory.setMaxInterestRate(masterDefinition.getMaxInterestRate());
		MasterDefinitionHistory.setMiniInterestRate(masterDefinition.getMiniInterestRate());
		MasterDefinitionHistory.setMaxPenalInterestRate(masterDefinition.getMaxPenalInterestRate());
		MasterDefinitionHistory.setMiniPenalInterestRate(masterDefinition.getMiniPenalInterestRate());
		MasterDefinitionHistory.setPenalInterestId(masterDefinition.getPenalInterest() != null? masterDefinition.getPenalInterest().getId():null);
		MasterDefinitionHistory.setDueDateTemplate(masterDefinition.getDueDateTemplate() != null ? masterDefinition.getDueDateTemplate().getId() :null);
		MasterDefinitionHistory.setDueDateTemplateRemark(masterDefinition.getDueDateTemplateRemark());
		masterDefinitionHistoryRepository.save(MasterDefinitionHistory);

	}

}
