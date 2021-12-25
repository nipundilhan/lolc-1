package com.fusionx.lending.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
Eligibility Template Disbursment
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   21-07-2019   FXL-1         FXL-42     Dilki        Created
*    
********************************************************************************************************
*/
import com.fusionx.lending.product.domain.EligibilityTemplateDisbursement;
import com.fusionx.lending.product.domain.EligibilityTemplateDisbursementHistory;
import com.fusionx.lending.product.repository.EligibilityTemplateDisbursementHistoryRepository;
import com.fusionx.lending.product.service.EligibilityTemplateDisbursmentHistoryService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor = Exception.class)
public class EligibilityTemplateDisbursmentHistoryServiceImpl implements EligibilityTemplateDisbursmentHistoryService {

	@Autowired
	private EligibilityTemplateDisbursementHistoryRepository eligibilityTemplateDisbursementHistoryRepository;

	@Autowired
	private ValidationService validationService;

	@Override
	public void saveHistory(String tenantId, EligibilityTemplateDisbursement eligibilityTemplateDisbursement,
			String historyCreatedUser) {

		EligibilityTemplateDisbursementHistory eligibilityTemplateDisbursementHistory = new EligibilityTemplateDisbursementHistory();

		eligibilityTemplateDisbursementHistory.setTenantId(tenantId);
		eligibilityTemplateDisbursementHistory.setEligibilityId(eligibilityTemplateDisbursement.getEligibilityId());
		eligibilityTemplateDisbursementHistory.setEligibilityName(eligibilityTemplateDisbursement.getEligibilityName());
		eligibilityTemplateDisbursementHistory.setDisbursementId(eligibilityTemplateDisbursement.getDisbursementId());
		eligibilityTemplateDisbursementHistory
				.setDisbursementName(eligibilityTemplateDisbursement.getDisbursementName());
		eligibilityTemplateDisbursementHistory.setEligibilityDisbursmentId(eligibilityTemplateDisbursement.getId());
		eligibilityTemplateDisbursementHistory.setStatus(eligibilityTemplateDisbursement.getStatus());
		eligibilityTemplateDisbursementHistory.setApproveStatus(eligibilityTemplateDisbursement.getApproveStatus());
		eligibilityTemplateDisbursementHistory.setCreatedDate(eligibilityTemplateDisbursement.getCreatedDate());
		eligibilityTemplateDisbursementHistory.setCreatedUser(eligibilityTemplateDisbursement.getCreatedUser());
		eligibilityTemplateDisbursementHistory.setModifiedDate(eligibilityTemplateDisbursement.getModifiedDate());
		eligibilityTemplateDisbursementHistory.setModifiedUser(eligibilityTemplateDisbursement.getModifiedUser());
		eligibilityTemplateDisbursementHistory.setPenApprovedDate(eligibilityTemplateDisbursement.getPenApprovedDate());
		eligibilityTemplateDisbursementHistory.setPenApprovedUser(eligibilityTemplateDisbursement.getPenApprovedUser());
		eligibilityTemplateDisbursementHistory.setPenRejectedDate(eligibilityTemplateDisbursement.getPenRejectedDate());
		eligibilityTemplateDisbursementHistory.setPenRejectedUser(eligibilityTemplateDisbursement.getPenRejectedUser());
		eligibilityTemplateDisbursementHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
		eligibilityTemplateDisbursementHistory.setHistoryCreatedUser(historyCreatedUser);
		eligibilityTemplateDisbursementHistory.setVersion(eligibilityTemplateDisbursement.getVersion());
		eligibilityTemplateDisbursementHistory.setSyncTs(validationService.getCreateOrModifyDate());

		eligibilityTemplateDisbursementHistoryRepository.save(eligibilityTemplateDisbursementHistory);

	}

}
