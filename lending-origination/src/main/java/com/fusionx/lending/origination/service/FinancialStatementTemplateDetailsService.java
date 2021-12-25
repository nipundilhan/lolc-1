package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.FinancialStatementTemplate;
import com.fusionx.lending.origination.domain.FinancialStatementTemplateDetails;
import com.fusionx.lending.origination.resource.FinancialStatementDetailsAddResource;
import com.fusionx.lending.origination.resource.FinancialStatementDetailsUpdateResource;

@Service
public interface FinancialStatementTemplateDetailsService {

	/**
	 * 
	 * Add FinancialStatementDetails
	 * 
	 * @author Dilki
	 * @param - FinancialStatementDetailsAddResource
	 * @return - Successfully saved
	 * 
	 */
	public FinancialStatementTemplateDetails addFinancialStatementDetails(String tenantId,
			FinancialStatementTemplate financialStatement,
			FinancialStatementDetailsAddResource financialStatementDetailsAddResource, int index);

	/**
	 * 
	 * Update FinancialStatementDetails
	 * 
	 * @author Dilki
	 * @param - FinancialStatementDetailsUpdateResource
	 * @return - Successfully updated
	 * 
	 */
	public FinancialStatementTemplateDetails updateFinancialStatementDetails(String tenantId, Long id,
			FinancialStatementTemplate financialStatement,
			FinancialStatementDetailsUpdateResource financialStatementDetailsUpdateResource, int index);
}
