package com.fusionx.lending.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
Eligibility Template Industry
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   21-07-2019   FXL-1         FXL-42     Dilki        Created
*    
********************************************************************************************************
*/
import com.fusionx.lending.product.domain.EligibilityTemplateIndustry;
import com.fusionx.lending.product.domain.EligibilityTemplateIndustryHistory;
import com.fusionx.lending.product.repository.EligibilityTemplateIndustryHistoryRepository;
import com.fusionx.lending.product.service.EligibilityTemplateIndustryHistoryService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor = Exception.class)
public class EligibilityTemplateIndustryHistoryServiceImpl implements EligibilityTemplateIndustryHistoryService {

	@Autowired
	private EligibilityTemplateIndustryHistoryRepository eligibilityTemplateIndustryHistoryRepository;

	@Autowired
	private ValidationService validationService;

	@Override
	public void saveHistory(String tenantId, EligibilityTemplateIndustry eligibilityTemplateIndustry,
			String historyCreatedUser) {

		EligibilityTemplateIndustryHistory eligibilityTemplateIndustryHistory = new EligibilityTemplateIndustryHistory();

		eligibilityTemplateIndustryHistory.setTenantId(tenantId);
		eligibilityTemplateIndustryHistory.setEligibilityId(eligibilityTemplateIndustry.getEligibilityId());
		eligibilityTemplateIndustryHistory.setEligibilityName(eligibilityTemplateIndustry.getEligibilityName());
		eligibilityTemplateIndustryHistory.setIndustryName(eligibilityTemplateIndustry.getIndustryName());
		eligibilityTemplateIndustryHistory.setIndustryId(eligibilityTemplateIndustry.getIndustryId());
		eligibilityTemplateIndustryHistory.setEligibilityIndustryId(eligibilityTemplateIndustry.getId());
		eligibilityTemplateIndustryHistory.setStatus(eligibilityTemplateIndustry.getStatus());
		eligibilityTemplateIndustryHistory.setApproveStatus(eligibilityTemplateIndustry.getApproveStatus());
		eligibilityTemplateIndustryHistory.setCreatedDate(eligibilityTemplateIndustry.getCreatedDate());
		eligibilityTemplateIndustryHistory.setCreatedUser(eligibilityTemplateIndustry.getCreatedUser());
		eligibilityTemplateIndustryHistory.setModifiedDate(eligibilityTemplateIndustry.getModifiedDate());
		eligibilityTemplateIndustryHistory.setModifiedUser(eligibilityTemplateIndustry.getModifiedUser());
		eligibilityTemplateIndustryHistory.setPenApprovedDate(eligibilityTemplateIndustry.getPenApprovedDate());
		eligibilityTemplateIndustryHistory.setPenApprovedUser(eligibilityTemplateIndustry.getPenApprovedUser());
		eligibilityTemplateIndustryHistory.setPenRejectedDate(eligibilityTemplateIndustry.getPenRejectedDate());
		eligibilityTemplateIndustryHistory.setPenRejectedUser(eligibilityTemplateIndustry.getPenRejectedUser());
		eligibilityTemplateIndustryHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
		eligibilityTemplateIndustryHistory.setHistoryCreatedUser(historyCreatedUser);
		eligibilityTemplateIndustryHistory.setVersion(eligibilityTemplateIndustry.getVersion());
		eligibilityTemplateIndustryHistory.setSyncTs(validationService.getCreateOrModifyDate());

		eligibilityTemplateIndustryHistoryRepository.save(eligibilityTemplateIndustryHistory);

	}

}
