package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Net Worth Liabilities Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-04-2021   		         FX-6157    SenithaP     Created
 *    
 ********************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class NetWorthLiabilityAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "EXTERNAL|INTERNAL", message = "{common-internalExternal.pattern}")
	private String typeOfLiability;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String companyId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String companyName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String typeOfFacilityId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String typeOfFacilityName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String facilityAmount;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String rental;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String outstandingAmount;
	
	@Size(max = 350, message = "{note.size}")
	private String comment;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	public String getTypeOfLiability() {
		return typeOfLiability;
	}

	public void setTypeOfLiability(String typeOfLiability) {
		this.typeOfLiability = typeOfLiability;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(String outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
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
