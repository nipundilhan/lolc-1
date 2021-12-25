package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FeeChargeDetailsCommonResource {

	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String applicationFrequencyId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String applicationFrequencyName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String calculationFrequencyId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String calculationFrequencyName;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String feeTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String feeTypeName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String feeCategoryId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String feeCategoryName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "Amount|Rate", message = "{feeCharge-type-value-error}")
	private String type;	
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "YES|NO", message = "{common-enable.pattern}")
	private String mandatory;


	@Size(max = 350, message = "{common-note.size-two}")
	private String note;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApplicationFrequencyId() {
		return applicationFrequencyId;
	}

	public void setApplicationFrequencyId(String applicationFrequencyId) {
		this.applicationFrequencyId = applicationFrequencyId;
	}

	public String getApplicationFrequencyName() {
		return applicationFrequencyName;
	}

	public void setApplicationFrequencyName(String applicationFrequencyName) {
		this.applicationFrequencyName = applicationFrequencyName;
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

	public String getFeeTypeId() {
		return feeTypeId;
	}

	public void setFeeTypeId(String feeTypeId) {
		this.feeTypeId = feeTypeId;
	}

	public String getFeeTypeName() {
		return feeTypeName;
	}

	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}

	public String getFeeCategoryId() {
		return feeCategoryId;
	}

	public void setFeeCategoryId(String feeCategoryId) {
		this.feeCategoryId = feeCategoryId;
	}

	public String getFeeCategoryName() {
		return feeCategoryName;
	}

	public void setFeeCategoryName(String feeCategoryName) {
		this.feeCategoryName = feeCategoryName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
