package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * Customer Cultivation Income Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-05-2021      		     			MENUKAJ      Created
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CustomerCultivationIncomeResource {
	
	private String id;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String cultivationCategoryId;

	@Size(max = 70, message = "{common-name.size}")
	private String cultivationCategoryName;
	
	@Size(max = 255, message = "{description.size}")
	private String description;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String ownershipId;
	private String ownershipName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String landOwnershipId;
	private String landOwnershipName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String experienceId;
	private String experienceName;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String occurrenceFrequencyId;
	private String occurrenceFrequencyCode;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String occurrenceId;
	private String occurrenceCode;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String calculationFrequencyId;
	private String calculationFrequencyName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String noOfUnitId;
	private String noOfUnitName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String noOfUnitValue;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String currencyId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String pricePerUnit;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String totalGrossIncome;
	
	@Size(max = 255, message = "{comment.size}")
	private String comment;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|ACTIVE|INACTIVE",message="{common-status.pattern}")
	private String status;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;
	
	@Valid
	private List<CustomerCultivationExpenseResource> customerCultivationExpenses;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCultivationCategoryId() {
		return cultivationCategoryId;
	}

	public void setCultivationCategoryId(String cultivationCategoryId) {
		this.cultivationCategoryId = cultivationCategoryId;
	}

	public String getCultivationCategoryName() {
		return cultivationCategoryName;
	}

	public void setCultivationCategoryName(String cultivationCategoryName) {
		this.cultivationCategoryName = cultivationCategoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwnershipId() {
		return ownershipId;
	}

	public void setOwnershipId(String ownershipId) {
		this.ownershipId = ownershipId;
	}

	public String getOwnershipName() {
		return ownershipName;
	}

	public void setOwnershipName(String ownershipName) {
		this.ownershipName = ownershipName;
	}

	public String getLandOwnershipId() {
		return landOwnershipId;
	}

	public void setLandOwnershipId(String landOwnershipId) {
		this.landOwnershipId = landOwnershipId;
	}

	public String getLandOwnershipName() {
		return landOwnershipName;
	}

	public void setLandOwnershipName(String landOwnershipName) {
		this.landOwnershipName = landOwnershipName;
	}

	public String getExperienceId() {
		return experienceId;
	}

	public void setExperienceId(String experienceId) {
		this.experienceId = experienceId;
	}

	public String getExperienceName() {
		return experienceName;
	}

	public void setExperienceName(String experienceName) {
		this.experienceName = experienceName;
	}

	public String getOccurrenceFrequencyId() {
		return occurrenceFrequencyId;
	}

	public void setOccurrenceFrequencyId(String occurrenceFrequencyId) {
		this.occurrenceFrequencyId = occurrenceFrequencyId;
	}

	public String getOccurrenceFrequencyCode() {
		return occurrenceFrequencyCode;
	}

	public void setOccurrenceFrequencyCode(String occurrenceFrequencyCode) {
		this.occurrenceFrequencyCode = occurrenceFrequencyCode;
	}

	public String getOccurrenceId() {
		return occurrenceId;
	}

	public void setOccurrenceId(String occurrenceId) {
		this.occurrenceId = occurrenceId;
	}

	public String getOccurrenceCode() {
		return occurrenceCode;
	}

	public void setOccurrenceCode(String occurrenceCode) {
		this.occurrenceCode = occurrenceCode;
	}

	public String getCalculationFrequencyId() {
		return calculationFrequencyId;
	}

	public void setCalculationFrequencyId(String calculationFrequencyId) {
		this.calculationFrequencyId = calculationFrequencyId;
	}

	public String getCalculationFrequencyName() {
		return calculationFrequencyName;
	}

	public void setCalculationFrequencyName(String calculationFrequencyName) {
		this.calculationFrequencyName = calculationFrequencyName;
	}

	public String getNoOfUnitId() {
		return noOfUnitId;
	}

	public void setNoOfUnitId(String noOfUnitId) {
		this.noOfUnitId = noOfUnitId;
	}

	public String getNoOfUnitName() {
		return noOfUnitName;
	}

	public void setNoOfUnitName(String noOfUnitName) {
		this.noOfUnitName = noOfUnitName;
	}

	public String getNoOfUnitValue() {
		return noOfUnitValue;
	}

	public void setNoOfUnitValue(String noOfUnitValue) {
		this.noOfUnitValue = noOfUnitValue;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(String pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public String getTotalGrossIncome() {
		return totalGrossIncome;
	}

	public void setTotalGrossIncome(String totalGrossIncome) {
		this.totalGrossIncome = totalGrossIncome;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<CustomerCultivationExpenseResource> getCustomerCultivationExpenses() {
		return customerCultivationExpenses;
	}

	public void setCustomerCultivationExpenses(List<CustomerCultivationExpenseResource> customerCultivationExpenses) {
		this.customerCultivationExpenses = customerCultivationExpenses;
	}

}
