package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.domain.BusinessIncomeExpense;

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
public class GuarantorBusinessIncomeSummaryResponseResource {
	
	private String totalBusinessIncome;
	
	private String totalExpences;
	
	private String netBusinessIncome;

	private List<BusinessIncomeExpense> businessIncome;

	public String getTotalBusinessIncome() {
		return totalBusinessIncome;
	}

	public void setTotalBusinessIncome(String totalBusinessIncome) {
		this.totalBusinessIncome = totalBusinessIncome;
	}

	public String getTotalExpences() {
		return totalExpences;
	}

	public void setTotalExpences(String totalExpences) {
		this.totalExpences = totalExpences;
	}

	public String getNetBusinessIncome() {
		return netBusinessIncome;
	}

	public void setNetBusinessIncome(String netBusinessIncome) {
		this.netBusinessIncome = netBusinessIncome;
	}

	public List<BusinessIncomeExpense> getBusinessIncome() {
		return businessIncome;
	}

	public void setBusinessIncome(List<BusinessIncomeExpense> businessIncome) {
		this.businessIncome = businessIncome;
	}
	
	

}
