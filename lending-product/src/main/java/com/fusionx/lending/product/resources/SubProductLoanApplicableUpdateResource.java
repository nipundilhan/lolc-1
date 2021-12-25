package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SubProductLoanApplicableUpdateResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String loanApplicableRangeId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common.invalid-value}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String loanApplicableRangeCode;

	public String getLoanApplicableRangeId() {
		return loanApplicableRangeId;
	}

	public void setLoanApplicableRangeId(String loanApplicableRangeId) {
		this.loanApplicableRangeId = loanApplicableRangeId;
	}

	public String getLoanApplicableRangeCode() {
		return loanApplicableRangeCode;
	}

	public void setLoanApplicableRangeCode(String loanApplicableRangeCode) {
		this.loanApplicableRangeCode = loanApplicableRangeCode;
	}
	
	

}
