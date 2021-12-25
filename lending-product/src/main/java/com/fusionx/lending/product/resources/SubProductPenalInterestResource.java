package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SubProductPenalInterestResource {

	@Pattern(regexp = "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$",message="{rate.pattern}")
	private String maxPenalInterestRate;
	
	@Pattern(regexp = "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$",message="{rate.pattern}")
	private String miniPenalInterestRate;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String penalInterestId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String penalInterestName;

	public String getPenalInterestId() {
		return penalInterestId;
	}

	public void setPenalInterestId(String penalInterestId) {
		this.penalInterestId = penalInterestId;
	}

	public String getPenalInterestName() {
		return penalInterestName;
	}

	public void setPenalInterestName(String penalInterestName) {
		this.penalInterestName = penalInterestName;
	}

	public String getMaxPenalInterestRate() {
		return maxPenalInterestRate;
	}

	public void setMaxPenalInterestRate(String maxPenalInterestRate) {
		this.maxPenalInterestRate = maxPenalInterestRate;
	}

	public String getMiniPenalInterestRate() {
		return miniPenalInterestRate;
	}

	public void setMiniPenalInterestRate(String miniPenalInterestRate) {
		this.miniPenalInterestRate = miniPenalInterestRate;
	}

}
