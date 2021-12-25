package com.fusionx.lending.origination.resource;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ValuationAndInsuranceDetailsUpdateResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String customerId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String assetsEntityId;
	
	@Valid
	@JsonProperty(value = "valuationDetails")
	ValuationDetailsUpdateResource valuationDetails;
	
	@Valid
	@JsonProperty(value = "insuranceDetails")
	InsuranceDetailsUpdateResource insuranceDetails;

	public ValuationDetailsUpdateResource getValuationDetails() {
		return valuationDetails;
	}

	public void setValuationDetails(ValuationDetailsUpdateResource valuationDetails) {
		this.valuationDetails = valuationDetails;
	}

	public InsuranceDetailsUpdateResource getInsuranceDetails() {
		return insuranceDetails;
	}

	public void setInsuranceDetails(InsuranceDetailsUpdateResource insuranceDetails) {
		this.insuranceDetails = insuranceDetails;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAssetsEntityId() {
		return assetsEntityId;
	}

	public void setAssetsEntityId(String assetsEntityId) {
		this.assetsEntityId = assetsEntityId;
	}
	
}
