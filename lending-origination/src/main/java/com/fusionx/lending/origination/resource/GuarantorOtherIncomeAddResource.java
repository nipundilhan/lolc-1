package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GuarantorOtherIncomeAddResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String guarantorId;
	
	@Valid
	private List<GuarantorOtherIncomeListResource> guOtherIncomeDetailsList;

	public String getGuarantorId() {
		return guarantorId;
	}

	public void setGuarantorId(String guarantorId) {
		this.guarantorId = guarantorId;
	}

	public List<GuarantorOtherIncomeListResource> getGuOtherIncomeDetailsList() {
		return guOtherIncomeDetailsList;
	}

	public void setGuOtherIncomeDetailsList(List<GuarantorOtherIncomeListResource> guOtherIncomeDetailsList) {
		this.guOtherIncomeDetailsList = guOtherIncomeDetailsList;
	}
}
