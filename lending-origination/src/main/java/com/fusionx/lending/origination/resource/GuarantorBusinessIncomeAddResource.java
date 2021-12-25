package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GuarantorBusinessIncomeAddResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String guarantorId;
	
	@Valid
	private List<GuarantorBusinessIncomeListResource> guBusinessIncomeDetailsList;

	public String getGuarantorId() {
		return guarantorId;
	}

	public void setGuarantorId(String guarantorId) {
		this.guarantorId = guarantorId;
	}

	public List<GuarantorBusinessIncomeListResource> getGuBusinessIncomeDetailsList() {
		return guBusinessIncomeDetailsList;
	}

	public void setGuBusinessIncomeDetailsList(List<GuarantorBusinessIncomeListResource> guBusinessIncomeDetailsList) {
		this.guBusinessIncomeDetailsList = guBusinessIncomeDetailsList;
	}
	
	
}
