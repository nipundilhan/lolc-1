package com.fusionx.lending.transaction.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
/**
 * Tax Event Details
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   13-10-2021   FXL-1234      FXL-1131	Dilki      Created
 * <p>
 * *******************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TaxEventDetailsAddResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String sequence;

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
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String codeId;

	@Size(max = 50, message = "{taxEvent-applicableOn.size}")
	@Pattern(regexp = "^$|LOAN_AMOUNT|INSTALLMENT|CAPITAL_PORTION|INTEREST_PORTION|TERMINATION_FEE|TERMINATION_CAPITAL", message = "{taxEvent-applicableOn.pattern}")
	private String applicableOn;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|RATE|AMOUNT", message = "{taxEvent-amountType.pattern}")
	@Size(max = 10, message = "{taxEvent-amountType.size}")
	private String amountType;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	@Size(max = 70)
	private String applicationFrequency;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String taxEventId;

	@Size(max = 100, message = "{taxEvent-formula.size}")
	private String formula;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|YES|NO", message = "{common.invalid-value}")
	private String isFixed;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|ACTIVE|INACTIVE", message = "{status.pattern}")
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getApplicableOn() {
		return applicableOn;
	}

	public void setApplicableOn(String applicableOn) {
		this.applicableOn = applicableOn;
	}

	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public String getApplicationFrequency() {
		return applicationFrequency;
	}

	public void setApplicationFrequency(String applicationFrequency) {
		this.applicationFrequency = applicationFrequency;
	}

	public String getTaxEventId() {
		return taxEventId;
	}

	public void setTaxEventId(String taxEventId) {
		this.taxEventId = taxEventId;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getIsFixed() {
		return isFixed;
	}

	public void setIsFixed(String isFixed) {
		this.isFixed = isFixed;
	}

}
