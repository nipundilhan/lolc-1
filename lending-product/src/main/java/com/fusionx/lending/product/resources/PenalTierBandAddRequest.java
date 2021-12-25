package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PenalTierBandAddRequest {

	//private String penalTierBandSetId;
	
	@NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{common-date.pattern}")
    private String effectiveDate;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String tierValueMinimum;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String tierValueMaximum;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{rate.pattern}")
	private String repArp;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String loanPrInterestRateTypeId;
	
	@Size(max = 70, message = "{common-name.size}")
	private String loanPrInterestRateTypeName;
	
	@Pattern(regexp = "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$",message="{rate.pattern}")
	private String loanPrInterestRate;
	
	@Size(max = 255, message = "{common-name.size}")
	private String note;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "FIXED|VARIABLE", message = "{common-interest-rate.pattern}")
	private String interestRateType;

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTierValueMinimum() {
		return tierValueMinimum;
	}

	public void setTierValueMinimum(String tierValueMinimum) {
		this.tierValueMinimum = tierValueMinimum;
	}

	public String getTierValueMaximum() {
		return tierValueMaximum;
	}

	public void setTierValueMaximum(String tierValueMaximum) {
		this.tierValueMaximum = tierValueMaximum;
	}

	public String getRepArp() {
		return repArp;
	}

	public void setRepArp(String repArp) {
		this.repArp = repArp;
	}

	public String getLoanPrInterestRateTypeId() {
		return loanPrInterestRateTypeId;
	}

	public void setLoanPrInterestRateTypeId(String loanPrInterestRateTypeId) {
		this.loanPrInterestRateTypeId = loanPrInterestRateTypeId;
	}

	public String getLoanPrInterestRateTypeName() {
		return loanPrInterestRateTypeName;
	}

	public void setLoanPrInterestRateTypeName(String loanPrInterestRateTypeName) {
		this.loanPrInterestRateTypeName = loanPrInterestRateTypeName;
	}

	public String getLoanPrInterestRate() {
		return loanPrInterestRate;
	}

	public void setLoanPrInterestRate(String loanPrInterestRate) {
		this.loanPrInterestRate = loanPrInterestRate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInterestRateType() {
		return interestRateType;
	}

	public void setInterestRateType(String interestRateType) {
		this.interestRateType = interestRateType;
	}

}
