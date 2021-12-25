package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FixedChargesResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String chargeTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@NotBlank(message = "{common.not-null}")
	private String chargeTypeName;
	
    @NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String totalChargeAmount;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String criteriaId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "AFIM|AFUP|AFFC", message = "{tc-calculation-criteriaName.invalid}")
	private String criteriaName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "RATE|AMOUNT", message = "{feeCharge-type-value-error}")
	private String calculationMethod;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String rateOrAmount;


	public String getCriteriaId() {
		return criteriaId;
	}

	public void setCriteriaId(String criteriaId) {
		this.criteriaId = criteriaId;
	}

	public String getCriteriaName() {
		return criteriaName;
	}

	public void setCriteriaName(String criteriaName) {
		this.criteriaName = criteriaName;
	}

	public String getRateOrAmount() {
		return rateOrAmount;
	}

	public void setRateOrAmount(String rateOrAmount) {
		this.rateOrAmount = rateOrAmount;
	}

//	public String getFrequencyId() {
//		return frequencyId;
//	}
//
//	public void setFrequencyId(String frequencyId) {
//		this.frequencyId = frequencyId;
//	}
//
//	public String getFrequencyName() {
//		return frequencyName;
//	}
//
//	public void setFrequencyName(String frequencyName) {
//		this.frequencyName = frequencyName;
//	}

	public String getChargeTypeId() {
		return chargeTypeId;
	}

	public void setChargeTypeId(String chargeTypeId) {
		this.chargeTypeId = chargeTypeId;
	}

	public String getChargeTypeName() {
		return chargeTypeName;
	}

	public void setChargeTypeName(String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}

	public String getTotalChargeAmount() {
		return totalChargeAmount;
	}

	public void setTotalChargeAmount(String totalChargeAmount) {
		this.totalChargeAmount = totalChargeAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCalculationMethod() {
		return calculationMethod;
	}

	public void setCalculationMethod(String calculationMethod) {
		this.calculationMethod = calculationMethod;
	}

}
