package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Cultivation Income Details Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-09-2021     	         FXL-661       Dilhan       Created
 *    
 ********************************************************************************************************
*/

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CultivationIncomeExpensesAddResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String cultivationIncomeDetailsId;
	
	@Valid
	private List<CultivationIncomeExpenseResource> incomeOrExpenseDetails;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String frequencyId;
	
	@NotBlank(message = "{code.not-null}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{code.pattern}")
	private String frequencyCode;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String currencyId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String currencyName;


	public List<CultivationIncomeExpenseResource> getIncomeOrExpenseDetails() {
		return incomeOrExpenseDetails;
	}

	public void setIncomeOrExpenseDetails(List<CultivationIncomeExpenseResource> incomeOrExpenseDetails) {
		this.incomeOrExpenseDetails = incomeOrExpenseDetails;
	}

	public String getCultivationIncomeDetailsId() {
		return cultivationIncomeDetailsId;
	}

	public void setCultivationIncomeDetailsId(String cultivationIncomeDetailsId) {
		this.cultivationIncomeDetailsId = cultivationIncomeDetailsId;
	}


	public String getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(String frequencyId) {
		this.frequencyId = frequencyId;
	}

	public String getFrequencyCode() {
		return frequencyCode;
	}

	public void setFrequencyCode(String frequencyCode) {
		this.frequencyCode = frequencyCode;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
}
