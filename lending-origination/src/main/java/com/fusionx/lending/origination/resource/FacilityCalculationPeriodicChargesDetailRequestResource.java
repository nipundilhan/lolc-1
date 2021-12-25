package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class FacilityCalculationPeriodicChargesDetailRequestResource {
	
	@NotBlank(message = "{periodCode.not-null}")
	@Size(max = 20, message = "{common.size20}")
	private String periodCode;
	
	@NotBlank(message = "{periodName.not-null}")
	@Size(max = 255, message = "{common.size255}")
	private String periodName;
	
	@NotBlank(message = "{chargeAmount.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
    private String chargeAmount;

	public String getPeriodCode() {
		return periodCode;
	}

	public void setPeriodCode(String periodCode) {
		this.periodCode = periodCode;
	}

	public String getPeriodName() {
		return periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	public String getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(String chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
}
