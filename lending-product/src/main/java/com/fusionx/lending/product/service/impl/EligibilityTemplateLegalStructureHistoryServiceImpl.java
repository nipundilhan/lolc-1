package com.fusionx.lending.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
Eligibility Template Legal Structure
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   21-07-2019   FXL-1         FXL-42     Dilki        Created
*    
********************************************************************************************************
*/
import com.fusionx.lending.product.domain.EligibilityTemplateLegalStructure;
import com.fusionx.lending.product.domain.EligibilityTemplateLegalStructureHistory;
import com.fusionx.lending.product.repository.EligibilityTemplateLegalStructureHistoryRepository;
import com.fusionx.lending.product.service.EligibilityTemplateLegalStructureHistoryService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor = Exception.class)
public class EligibilityTemplateLegalStructureHistoryServiceImpl
		implements EligibilityTemplateLegalStructureHistoryService {

	@Autowired
	private EligibilityTemplateLegalStructureHistoryRepository eligibilityTemplateLegalStructureHistoryRepository;

	@Autowired
	private ValidationService validationService;

	@Override
	public void saveHistory(String tenantId, EligibilityTemplateLegalStructure eligibilityTemplateLegalStructure,
			String historyCreatedUser) {

		EligibilityTemplateLegalStructureHistory eligibilityTemplateLegalStructureHistory = new EligibilityTemplateLegalStructureHistory();

		eligibilityTemplateLegalStructureHistory.setTenantId(tenantId);
		eligibilityTemplateLegalStructureHistory.setEligibilityId(eligibilityTemplateLegalStructure.getEligibilityId());
		eligibilityTemplateLegalStructureHistory
				.setEligibilityName(eligibilityTemplateLegalStructure.getEligibilityName());
		eligibilityTemplateLegalStructureHistory
				.setLegalStructureId(eligibilityTemplateLegalStructure.getLegalStructureId());
		eligibilityTemplateLegalStructureHistory
				.setLegalStructureName(eligibilityTemplateLegalStructure.getLegalStructureName());
		eligibilityTemplateLegalStructureHistory
				.setEligibilityLegalStructureId(eligibilityTemplateLegalStructure.getId());
		eligibilityTemplateLegalStructureHistory.setStatus(eligibilityTemplateLegalStructure.getStatus());
		eligibilityTemplateLegalStructureHistory.setApproveStatus(eligibilityTemplateLegalStructure.getApproveStatus());
		eligibilityTemplateLegalStructureHistory.setCreatedDate(eligibilityTemplateLegalStructure.getCreatedDate());
		eligibilityTemplateLegalStructureHistory.setCreatedUser(eligibilityTemplateLegalStructure.getCreatedUser());
		eligibilityTemplateLegalStructureHistory.setModifiedDate(eligibilityTemplateLegalStructure.getModifiedDate());
		eligibilityTemplateLegalStructureHistory.setModifiedUser(eligibilityTemplateLegalStructure.getModifiedUser());
		eligibilityTemplateLegalStructureHistory
				.setPenApprovedDate(eligibilityTemplateLegalStructure.getPenApprovedDate());
		eligibilityTemplateLegalStructureHistory
				.setPenApprovedUser(eligibilityTemplateLegalStructure.getPenApprovedUser());
		eligibilityTemplateLegalStructureHistory
				.setPenRejectedDate(eligibilityTemplateLegalStructure.getPenRejectedDate());
		eligibilityTemplateLegalStructureHistory
				.setPenRejectedUser(eligibilityTemplateLegalStructure.getPenRejectedUser());
		eligibilityTemplateLegalStructureHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
		eligibilityTemplateLegalStructureHistory.setHistoryCreatedUser(historyCreatedUser);
		eligibilityTemplateLegalStructureHistory.setVersion(eligibilityTemplateLegalStructure.getVersion());
		eligibilityTemplateLegalStructureHistory.setSyncTs(validationService.getCreateOrModifyDate());

		eligibilityTemplateLegalStructureHistoryRepository.save(eligibilityTemplateLegalStructureHistory);

	}

}
