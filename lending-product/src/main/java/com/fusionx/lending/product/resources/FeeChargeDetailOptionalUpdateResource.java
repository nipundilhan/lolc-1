package com.fusionx.lending.product.resources;


import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FeeChargeDetailOptionalUpdateResource {

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String feeChargePendingId;
	
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String feeChargeDetailsOptionalId;	
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{common-date.pattern}")
	private String effectiveDate;
		
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;

	@Valid
    FeeChargeDetailsCommonResource commonResource;
	
	@Valid
	List<FeeChargeDetailOptionalOptionResource> optionsList;

	public List<FeeChargeDetailOptionalOptionResource> getOptionsList() {
		return optionsList;
	}

	public void setOptionsList(List<FeeChargeDetailOptionalOptionResource> optionsList) {
		this.optionsList = optionsList;
	}
	

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getFeeChargePendingId() {
		return feeChargePendingId;
	}

	public void setFeeChargePendingId(String feeChargePendingId) {
		this.feeChargePendingId = feeChargePendingId;
	}

	public String getFeeChargeDetailsOptionalId() {
		return feeChargeDetailsOptionalId;
	}

	public void setFeeChargeDetailsOptionalId(String feeChargeDetailsOptionalId) {
		this.feeChargeDetailsOptionalId = feeChargeDetailsOptionalId;
	}
	
	public FeeChargeDetailsCommonResource getCommonResource() {
		return commonResource;
	}

	public void setCommonResource(FeeChargeDetailsCommonResource commonResource) {
		this.commonResource = commonResource;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


}
