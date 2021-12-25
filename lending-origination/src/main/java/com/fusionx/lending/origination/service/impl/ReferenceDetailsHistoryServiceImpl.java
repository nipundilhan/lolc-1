package com.fusionx.lending.origination.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.domain.ReferenceDetails;
import com.fusionx.lending.origination.repository.ReferenceDetailsHistoryRepository;
import com.fusionx.lending.origination.service.ReferenceDetailsHistoryService;
import com.fusionx.lending.origination.domain.ReferenceDetailsHistory;

@Component
@Transactional(rollbackFor = Exception.class)
public class ReferenceDetailsHistoryServiceImpl implements ReferenceDetailsHistoryService {

	@Autowired
	private ReferenceDetailsHistoryRepository referenceDetailsHistoryRepository;

	@Override
	public void saveHistory(String tenantId, ReferenceDetails referenceDetails, String historyCreatedUser) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		ReferenceDetailsHistory referenceDetailsHistory = new ReferenceDetailsHistory();

		referenceDetailsHistory.setTenantId(tenantId);
		referenceDetailsHistory.setReferenceDetailsId(referenceDetails.getId());
		referenceDetailsHistory.setCurrentAddressLine1(referenceDetails.getCurrentAddressLine1());
		referenceDetailsHistory.setCurrentAddressLine2(referenceDetails.getCurrentAddressLine2());
		referenceDetailsHistory.setCurrentAddressLine3(referenceDetails.getCurrentAddressLine3());
		referenceDetailsHistory.setCurrentAddressLine4(referenceDetails.getCurrentAddressLine4());
		referenceDetailsHistory.setpermanentAddressLine1(referenceDetails.getPermanentAddressLine1());
		referenceDetailsHistory.setpermanentAddressLine2(referenceDetails.getPermanentAddressLine2());
		referenceDetailsHistory.setpermanentAddressLine3(referenceDetails.getPermanentAddressLine3());
		referenceDetailsHistory.setpermanentAddressLine4(referenceDetails.getPermanentAddressLine4());
		referenceDetailsHistory.setName(referenceDetails.getName());
		referenceDetailsHistory.setProfessionalStatus(referenceDetails.getProfessionalStatus());
		referenceDetailsHistory.setBusinessEmployer(referenceDetails.getBusinessEmployer());
		referenceDetailsHistory.setStatus(referenceDetails.getStatus());
		referenceDetailsHistory.setCreatedDate(referenceDetails.getCreatedDate());
		referenceDetailsHistory.setCreatedUser(referenceDetails.getCreatedUser());
		referenceDetailsHistory.setModifiedDate(referenceDetails.getModifiedDate());
		referenceDetailsHistory.setModifiedUser(referenceDetails.getModifiedUser());
		referenceDetailsHistory.setVersion(referenceDetails.getVersion());
		referenceDetailsHistory.setHistoryCreatedUser(historyCreatedUser);
		referenceDetailsHistory.setHistoryCreatedDate(currentTimestamp);
		referenceDetailsHistory.setSyncTs(currentTimestamp);

		referenceDetailsHistoryRepository.saveAndFlush(referenceDetailsHistory);
	}

}
