package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Financial Commitment Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6301    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FinancialCommitmentAddRequestResource {
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String id;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String customerId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 50, message = "{note.size}")
	@Pattern(regexp = "INTERNAL|EXTERNAL|INFORMAL", message = "{financialCommitment-category.invalid}")
	private String category;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String typeOfCommitmentId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 350, message = "{note.size}")
	private String typeOfCommitmentName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String typeOfFacilityId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 350, message = "{note.size}")
	private String typeOfFacilityName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String externalInstituteId;
	private String externalInstituteCode;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String internalInstituteId;
	private String internalInstituteCode;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String repaymentFrequencyId;
	private String repaymentFrequencyCode;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String facilityAmount;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String rental;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String calculationFrequencyId;
	private String calculationFrequencyCode;
	private String calculationFrequencyName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String rentalCalcPerFreq;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String noOfRentalsLeft;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String toalOutstanding;
	
	@Size(max = 350, message = "{note.size}")
	private String comment;
	
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	
	public String getTypeOfCommitmentId() {
		return typeOfCommitmentId;
	}

	public void setTypeOfCommitmentId(String typeOfCommitmentId) {
		this.typeOfCommitmentId = typeOfCommitmentId;
	}

	

	public String getTypeOfCommitmentName() {
		return typeOfCommitmentName;
	}

	public void setTypeOfCommitmentName(String typeOfCommitmentName) {
		this.typeOfCommitmentName = typeOfCommitmentName;
	}

	public String getTypeOfFacilityId() {
		return typeOfFacilityId;
	}

	public void setTypeOfFacilityId(String typeOfFacilityId) {
		this.typeOfFacilityId = typeOfFacilityId;
	}

	public String getTypeOfFacilityName() {
		return typeOfFacilityName;
	}

	public void setTypeOfFacilityName(String typeOfFacilityName) {
		this.typeOfFacilityName = typeOfFacilityName;
	}

	public String getExternalInstituteId() {
		return externalInstituteId;
	}

	public void setExternalInstituteId(String externalInstituteId) {
		this.externalInstituteId = externalInstituteId;
	}

	public String getExternalInstituteCode() {
		return externalInstituteCode;
	}

	public void setExternalInstituteCode(String externalInstituteCode) {
		this.externalInstituteCode = externalInstituteCode;
	}

	public String getInternalInstituteId() {
		return internalInstituteId;
	}

	public void setInternalInstituteId(String internalInstituteId) {
		this.internalInstituteId = internalInstituteId;
	}

	public String getInternalInstituteCode() {
		return internalInstituteCode;
	}

	public void setInternalInstituteCode(String internalInstituteCode) {
		this.internalInstituteCode = internalInstituteCode;
	}

	public String getRepaymentFrequencyId() {
		return repaymentFrequencyId;
	}

	public void setRepaymentFrequencyId(String repaymentFrequencyId) {
		this.repaymentFrequencyId = repaymentFrequencyId;
	}

	public String getRepaymentFrequencyCode() {
		return repaymentFrequencyCode;
	}

	public void setRepaymentFrequencyCode(String repaymentFrequencyCode) {
		this.repaymentFrequencyCode = repaymentFrequencyCode;
	}

	public String getFacilityAmount() {
		return facilityAmount;
	}

	public void setFacilityAmount(String facilityAmount) {
		this.facilityAmount = facilityAmount;
	}

	public String getRental() {
		return rental;
	}

	public void setRental(String rental) {
		this.rental = rental;
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

	public String getRentalCalcPerFreq() {
		return rentalCalcPerFreq;
	}

	public void setRentalCalcPerFreq(String rentalCalcPerFreq) {
		this.rentalCalcPerFreq = rentalCalcPerFreq;
	}

	public String getNoOfRentalsLeft() {
		return noOfRentalsLeft;
	}

	public void setNoOfRentalsLeft(String noOfRentalsLeft) {
		this.noOfRentalsLeft = noOfRentalsLeft;
	}

	public String getToalOutstanding() {
		return toalOutstanding;
	}

	public void setToalOutstanding(String toalOutstanding) {
		this.toalOutstanding = toalOutstanding;
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
	
	

}
