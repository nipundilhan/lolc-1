package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.domain.OtherIncome;
import com.fusionx.lending.origination.domain.SalaryIncome;

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
public class OtherIncomeSummaryResponseResource {
	
	private String totalIncome;
	
	private String deductions;
	
	private String netIncome;
	
	private List<OtherIncome> otherIncomeDetail;

	public String getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(String totalIncome) {
		this.totalIncome = totalIncome;
	}

	public String getDeductions() {
		return deductions;
	}

	public void setDeductions(String deductions) {
		this.deductions = deductions;
	}



	public String getNetIncome() {
		return netIncome;
	}

	public void setNetIncome(String netIncome) {
		this.netIncome = netIncome;
	}

	public List<OtherIncome> getOtherIncomeDetail() {
		return otherIncomeDetail;
	}

	public void setOtherIncomeDetail(List<OtherIncome> otherIncomeDetail) {
		this.otherIncomeDetail = otherIncomeDetail;
	}
	
	

}
