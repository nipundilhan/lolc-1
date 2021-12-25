package com.fusionx.lending.origination.resource;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AddSuppliesBasicInfoRequestResource extends SuppliesRequestResource{
	
	@JsonProperty("perIndividualInfo")
	@Valid
	private SuppliesIndividualInfoRequestResource perIndividualInfo;
	
	@JsonProperty("perCorporateInfo")
	@Valid
	private SuppliesCorporateBasicInfoRequestResource perCorporateInfo;

	public SuppliesIndividualInfoRequestResource getPerIndividualInfo() {
		return perIndividualInfo;
	}

	public void setPerIndividualInfo(SuppliesIndividualInfoRequestResource perIndividualInfo) {
		this.perIndividualInfo = perIndividualInfo;
	}

	public SuppliesCorporateBasicInfoRequestResource getPerCorporateInfo() {
		return perCorporateInfo;
	}

	public void setPerCorporateInfo(SuppliesCorporateBasicInfoRequestResource perCorporateInfo) {
		this.perCorporateInfo = perCorporateInfo;
	}
}
