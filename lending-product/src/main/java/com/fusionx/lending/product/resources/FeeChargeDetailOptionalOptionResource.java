package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FeeChargeDetailOptionalOptionResource {
	

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String feeChargeDetailOptionalOptionId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String option;
	

	@Pattern(regexp = "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$",message="{common.rate-pattern.one}")
	private String rate;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common.invalid-amount-pattern}")
	private String amount;

	public String getFeeChargeDetailOptionalOptionId() {
		return feeChargeDetailOptionalOptionId;
	}

	public void setFeeChargeDetailOptionalOptionId(String feeChargeDetailOptionalOptionId) {
		this.feeChargeDetailOptionalOptionId = feeChargeDetailOptionalOptionId;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	

}
