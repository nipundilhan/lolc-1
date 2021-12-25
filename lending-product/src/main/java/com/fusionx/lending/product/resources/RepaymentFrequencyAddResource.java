package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Account Status Add Resource
 * 
 ********************************************************************************************************
 * ### Date Story Point Task No Author Description
 * -------------------------------------------------------------------------------------------------------
 * 1 14-10-2021 FXL-174 FXL-1170 Pasindu Created
 * 
 ********************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RepaymentFrequencyAddResource {

	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String name;

	@Size(max = 350, message = "{common-length01.size}")
	private String description;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{repayment.frequency.occuranceperyear.pattern}")
	private String occurrencePerYear;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOccurrencePerYear() {
		return occurrencePerYear;
	}

	public void setOccurrencePerYear(String occurrencePerYear) {
		this.occurrencePerYear = occurrencePerYear;
	}

}
