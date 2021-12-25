package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AssertListResource {
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common.invalid-number-format}")
	@NotBlank(message = "{common.not-null}")
	private String assetEntityId;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common.invalid-number-format}")
	@NotBlank(message = "{common.not-null}")
	private String finalValuationId;

	public String getAssetEntityId() {
		return assetEntityId;
	}

	public void setAssetEntityId(String assetEntityId) {
		this.assetEntityId = assetEntityId;
	}

	public String getFinalValuationId() {
		return finalValuationId;
	}

	public void setFinalValuationId(String finalValuationId) {
		this.finalValuationId = finalValuationId;
	}	

}
