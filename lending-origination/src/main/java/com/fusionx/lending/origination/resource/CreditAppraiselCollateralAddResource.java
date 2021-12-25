package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CreditAppraiselCollateralAddResource {
	
	@Valid
	@JsonProperty(value = "collateralDetails")
	List<CreditAppraiselCollateralDetailsAddResource> collateralDetails;

	public List<CreditAppraiselCollateralDetailsAddResource> getCollateralDetails() {
		return collateralDetails;
	}

	public void setCollateralDetails(List<CreditAppraiselCollateralDetailsAddResource> collateralDetails) {
		this.collateralDetails = collateralDetails;
	}


	
}
