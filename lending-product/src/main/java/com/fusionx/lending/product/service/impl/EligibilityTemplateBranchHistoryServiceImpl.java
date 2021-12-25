package com.fusionx.lending.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
Eligibility Template Branch
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   21-07-2019   FXL-1         FXL-42     Dilki        Created
*    
********************************************************************************************************
*/
import com.fusionx.lending.product.domain.EligibilityTemplateBranch;
import com.fusionx.lending.product.domain.EligibilityTemplateBranchHistory;
import com.fusionx.lending.product.repository.EligibilityTemplateBranchHistoryRepository;
import com.fusionx.lending.product.service.EligibilityTemplateBranchHistoryService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor = Exception.class)
public class EligibilityTemplateBranchHistoryServiceImpl implements EligibilityTemplateBranchHistoryService {

	@Autowired
	private EligibilityTemplateBranchHistoryRepository eligibilityTemplateBranchHistoryRepository;

	@Autowired
	private ValidationService validationService;

	@Override
	public void saveHistory(String tenantId, EligibilityTemplateBranch eligibilityTemplateBranch,
			String historyCreatedUser) {

		EligibilityTemplateBranchHistory eligibilityTemplateBranchHistory = new EligibilityTemplateBranchHistory();

		eligibilityTemplateBranchHistory.setTenantId(tenantId);
		eligibilityTemplateBranchHistory.setEligibilityId(eligibilityTemplateBranch.getEligibilityId());
		eligibilityTemplateBranchHistory.setEligibilityName(eligibilityTemplateBranch.getEligibilityName());
		eligibilityTemplateBranchHistory.setBranchName(eligibilityTemplateBranch.getBranchName());
		eligibilityTemplateBranchHistory.setBranchId(eligibilityTemplateBranch.getBranchId());
		eligibilityTemplateBranchHistory.setEligibilityBranchId(eligibilityTemplateBranch.getId());
		eligibilityTemplateBranchHistory.setStatus(eligibilityTemplateBranch.getStatus());
		eligibilityTemplateBranchHistory.setApproveStatus(eligibilityTemplateBranch.getApproveStatus());
		eligibilityTemplateBranchHistory.setCreatedDate(eligibilityTemplateBranch.getCreatedDate());
		eligibilityTemplateBranchHistory.setCreatedUser(eligibilityTemplateBranch.getCreatedUser());
		eligibilityTemplateBranchHistory.setModifiedDate(eligibilityTemplateBranch.getModifiedDate());
		eligibilityTemplateBranchHistory.setModifiedUser(eligibilityTemplateBranch.getModifiedUser());
		eligibilityTemplateBranchHistory.setPenApprovedDate(eligibilityTemplateBranch.getPenApprovedDate());
		eligibilityTemplateBranchHistory.setPenApprovedUser(eligibilityTemplateBranch.getPenApprovedUser());
		eligibilityTemplateBranchHistory.setPenRejectedDate(eligibilityTemplateBranch.getPenRejectedDate());
		eligibilityTemplateBranchHistory.setPenRejectedUser(eligibilityTemplateBranch.getPenRejectedUser());
		eligibilityTemplateBranchHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
		eligibilityTemplateBranchHistory.setHistoryCreatedUser(historyCreatedUser);
		eligibilityTemplateBranchHistory.setVersion(eligibilityTemplateBranch.getVersion());
		eligibilityTemplateBranchHistory.setSyncTs(validationService.getCreateOrModifyDate());

		eligibilityTemplateBranchHistoryRepository.save(eligibilityTemplateBranchHistory);

	}

}
