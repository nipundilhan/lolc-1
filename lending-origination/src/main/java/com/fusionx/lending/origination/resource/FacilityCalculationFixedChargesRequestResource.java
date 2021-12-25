package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class FacilityCalculationFixedChargesRequestResource {
	
	@NotBlank(message = "{chargeTypeCode.not-null}")
	@Size(max = 20, message = "{common.size20}")
	private String chargeTypeCode;
	
	@NotBlank(message = "{chargeTypeName.not-null}")
	@Size(max = 255, message = "{common.size255}")
	private String chargeTypeName;
	
	@NotBlank(message = "{criteriaCode.not-null}")
	@Size(max = 20, message = "{common.size20}")
	private String criteriaCode;
	
	@NotBlank(message = "{criteriaName.not-null}")
	@Size(max = 255, message = "{common.size255}")
	private String criteriaName;
	
	@NotBlank(message = "{calculationMethodCode.not-null}")
	@Size(max = 20, message = "{common.size20}")
	private String calculationMethodCode;
	
	@NotBlank(message = "{calculationMethodName.not-null}")
	@Size(max = 255, message = "{common.size255}")
	private String calculationMethodName;
	
	@Pattern(regexp = "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$",message="{rate.pattern}")
    private String chargeRate;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
    private String chargeAmount;
	
	@NotBlank(message = "{totalChargeAmount.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{common.invalid-amount-pattern}")
    private String totalChargeAmount;

	public String getChargeTypeCode() {
		return chargeTypeCode;
	}

	public void setChargeTypeCode(String chargeTypeCode) {
		this.chargeTypeCode = chargeTypeCode;
	}

	public String getChargeTypeName() {
		return chargeTypeName;
	}

	public void setChargeTypeName(String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}

	public String getCriteriaCode() {
		return criteriaCode;
	}

	public void setCriteriaCode(String criteriaCode) {
		this.criteriaCode = criteriaCode;
	}

	public String getCriteriaName() {
		return criteriaName;
	}

	public void setCriteriaName(String criteriaName) {
		this.criteriaName = criteriaName;
	}

	public String getCalculationMethodCode() {
		return calculationMethodCode;
	}

	public void setCalculationMethodCode(String calculationMethodCode) {
		this.calculationMethodCode = calculationMethodCode;
	}

	public String getCalculationMethodName() {
		return calculationMethodName;
	}

	public void setCalculationMethodName(String calculationMethodName) {
		this.calculationMethodName = calculationMethodName;
	}

	public String getChargeRate() {
		return chargeRate;
	}

	public void setChargeRate(String chargeRate) {
		this.chargeRate = chargeRate;
	}

	public String getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(String chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getTotalChargeAmount() {
		return totalChargeAmount;
	}

	public void setTotalChargeAmount(String totalChargeAmount) {
		this.totalChargeAmount = totalChargeAmount;
	}

	@Override
	public String toString() {
		return "{'chargeTypeCode'='" + chargeTypeCode + "', 'chargeTypeName'='"
				+ chargeTypeName + "', 'criteriaCode'='" + criteriaCode + "', 'criteriaName'='" + criteriaName
				+ "', 'calculationMethodCode'='" + calculationMethodCode + "', 'calculationMethodName'='"
				+ calculationMethodName + "', 'chargeRate'='" + chargeRate + "', 'chargeAmount'='" + chargeAmount
				+ "', 'totalChargeAmount'='" + totalChargeAmount + "'}";
	}
	
	
}
