package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Guarantor Income Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6301    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GuarantorIncomeBusinessAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String businessTypeId;
	private String businessTypeCode;
	
	@Size(max = 350, message = "{note.size}")
	private String description;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String frequencyId;
	private String frequencyCode;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String grossIncome;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String expences;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String netIncome;
	
	@Size(max = 350, message = "{note.size}")
	private String comment;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{status.invalid}")
	private String status;

	public String getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(String businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	public String getBusinessTypeCode() {
		return businessTypeCode;
	}

	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getGrossIncome() {
		return grossIncome;
	}

	public void setGrossIncome(String grossIncome) {
		this.grossIncome = grossIncome;
	}

	public String getExpences() {
		return expences;
	}

	public void setExpences(String expences) {
		this.expences = expences;
	}

	public String getNetIncome() {
		return netIncome;
	}

	public void setNetIncome(String netIncome) {
		this.netIncome = netIncome;
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
