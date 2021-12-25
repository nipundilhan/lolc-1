package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.domain.FinancialCommitment;

/**
 * Salary Income Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6301    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FinancialCommitmentSummaryResponseResource {
	
	private String totalFinancialCommitment;
	
	private List<FinancialCommitment> financialCommitmentDetails;

	public String getTotalFinancialCommitment() {
		return totalFinancialCommitment;
	}

	public void setTotalFinancialCommitment(String totalFinancialCommitment) {
		this.totalFinancialCommitment = totalFinancialCommitment;
	}

	public List<FinancialCommitment> getFinancialCommitmentDetails() {
		return financialCommitmentDetails;
	}

	public void setFinancialCommitmentDetails(List<FinancialCommitment> financialCommitmentDetails) {
		this.financialCommitmentDetails = financialCommitmentDetails;
	}
	
	

}
