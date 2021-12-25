package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * HouseHold Expense Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6301    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class HouseHoldExpenseAddRequestResource {
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String id;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String customerId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String expenseTypeExpCategoryDetId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String occuranceFrequencyId;
	private String occuranceFrequencyCode;
	private String occuranceFrequencyName;
	
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String cost;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String calculationFrequencyId;
	private String calculationFrequencyCode;
	private String calculationFrequencyName;
	
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String finalCost;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{status.invalid}")
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getExpenseTypeExpCategoryDetId() {
		return expenseTypeExpCategoryDetId;
	}

	public void setExpenseTypeExpCategoryDetId(String expenseTypeExpCategoryDetId) {
		this.expenseTypeExpCategoryDetId = expenseTypeExpCategoryDetId;
	}

	public String getOccuranceFrequencyId() {
		return occuranceFrequencyId;
	}

	public void setOccuranceFrequencyId(String occuranceFrequencyId) {
		this.occuranceFrequencyId = occuranceFrequencyId;
	}

	public String getOccuranceFrequencyCode() {
		return occuranceFrequencyCode;
	}

	public void setOccuranceFrequencyCode(String occuranceFrequencyCode) {
		this.occuranceFrequencyCode = occuranceFrequencyCode;
	}

	public String getOccuranceFrequencyName() {
		return occuranceFrequencyName;
	}

	public void setOccuranceFrequencyName(String occuranceFrequencyName) {
		this.occuranceFrequencyName = occuranceFrequencyName;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getCalculationFrequencyId() {
		return calculationFrequencyId;
	}

	public void setCalculationFrequencyId(String calculationFrequencyId) {
		this.calculationFrequencyId = calculationFrequencyId;
	}

	public String getCalculationFrequencyCode() {
		return calculationFrequencyCode;
	}

	public void setCalculationFrequencyCode(String calculationFrequencyCode) {
		this.calculationFrequencyCode = calculationFrequencyCode;
	}

	public String getCalculationFrequencyName() {
		return calculationFrequencyName;
	}

	public void setCalculationFrequencyName(String calculationFrequencyName) {
		this.calculationFrequencyName = calculationFrequencyName;
	}

	public String getFinalCost() {
		return finalCost;
	}

	public void setFinalCost(String finalCost) {
		this.finalCost = finalCost;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
