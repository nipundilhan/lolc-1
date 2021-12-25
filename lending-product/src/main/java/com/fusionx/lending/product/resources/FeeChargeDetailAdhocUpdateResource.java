package com.fusionx.lending.product.resources;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FeeChargeDetailAdhocUpdateResource {

	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String feeChargePendingId;
	
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String feeChargeDetailsAdhocId;	
	
	@NotNull
	@Valid
	FeeChargeDetailsCommonResource feeChargeDetailsCommonResource;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;


	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getFeeChargeDetailsAdhocId() {
		return feeChargeDetailsAdhocId;
	}

	public void setFeeChargeDetailsAdhocId(String feeChargeDetailsAdhocId) {
		this.feeChargeDetailsAdhocId = feeChargeDetailsAdhocId;
	}


	public String getFeeChargePendingId() {
		return feeChargePendingId;
	}

	public void setFeeChargePendingId(String feeChargePendingId) {
		this.feeChargePendingId = feeChargePendingId;
	}

	public FeeChargeDetailsCommonResource getFeeChargeDetailsCommonResource() {
		return feeChargeDetailsCommonResource;
	}

	public void setFeeChargeDetailsCommonResource(FeeChargeDetailsCommonResource feeChargeDetailsCommonResource) {
		this.feeChargeDetailsCommonResource = feeChargeDetailsCommonResource;
	}




}
