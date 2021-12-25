package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.domain.GuarantorIncomeCultivation;

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
public class GuarantorCultivationIncomeSummaryResponseResource {
	
	private String totalCultivationIncome;
	
	private String totalExpences;
	
	private String netCultivatinIncome;

	private List<GuarantorIncomeCultivation> cultivationIncome;

	public String getTotalCultivationIncome() {
		return totalCultivationIncome;
	}

	public void setTotalCultivationIncome(String totalCultivationIncome) {
		this.totalCultivationIncome = totalCultivationIncome;
	}

	public String getTotalExpences() {
		return totalExpences;
	}

	public void setTotalExpences(String totalExpences) {
		this.totalExpences = totalExpences;
	}

	public String getNetCultivatinIncome() {
		return netCultivatinIncome;
	}

	public void setNetCultivatinIncome(String netCultivatinIncome) {
		this.netCultivatinIncome = netCultivatinIncome;
	}

	public List<GuarantorIncomeCultivation> getCultivationIncome() {
		return cultivationIncome;
	}

	public void setCultivationIncome(List<GuarantorIncomeCultivation> cultivationIncome) {
		this.cultivationIncome = cultivationIncome;
	}
	
	
}
