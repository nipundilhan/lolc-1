package com.fusionx.lending.origination.resource;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ValuationDetailsListAddResource {
	
	private String assetsEntityId;
	
	@Valid
	ValuationDetailsAddResource valuationDetails;

	public ValuationDetailsAddResource getValuationDetails() {
		return valuationDetails;
	}

	public void setValuationDetails(ValuationDetailsAddResource valuationDetails) {
		this.valuationDetails = valuationDetails;
	}
	
//	@Valid
//	List<ValuationDetailsAddResource> valuationDetails;
//
//	public List<ValuationDetailsAddResource> getValuationDetails() {
//		return valuationDetails;
//	}
//
//	public void setValuationDetails(List<ValuationDetailsAddResource> valuationDetails) {
//		this.valuationDetails = valuationDetails;
//	}

	public String getAssetsEntityId() {
		return assetsEntityId;
	}

	public void setAssetsEntityId(String assetsEntityId) {
		this.assetsEntityId = assetsEntityId;
	}

}
