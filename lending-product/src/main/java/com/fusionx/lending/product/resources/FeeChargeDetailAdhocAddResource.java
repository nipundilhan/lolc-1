package com.fusionx.lending.product.resources;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FeeChargeDetailAdhocAddResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String feeChargeId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String feeChargeName;
	
	@NotNull
	@Valid
	private FeeChargeDetailsCommonResource feeChargeDetailsCommonResource;
	
	
	public String getFeeChargeId() {
		return feeChargeId;
	}

	public void setFeeChargeId(String feeChargeId) {
		this.feeChargeId = feeChargeId;
	}

	public String getFeeChargeName() {
		return feeChargeName;
	}

	public void setFeeChargeName(String feeChargeName) {
		this.feeChargeName = feeChargeName;
	}

	public FeeChargeDetailsCommonResource getFeeChargeDetailsCommonResource() {
		return feeChargeDetailsCommonResource;
	}

	public void setFeeChargeDetailsCommonResource(FeeChargeDetailsCommonResource feeChargeDetailsCommonResource) {
		this.feeChargeDetailsCommonResource = feeChargeDetailsCommonResource;
	}


}
