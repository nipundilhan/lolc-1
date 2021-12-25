package com.fusionx.lending.product.resources;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FeeChargeDetailOptionalAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String feeChargeId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String feeChargeName;

	@NotNull(message = "{common.not-null}")
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{common-date.pattern}")
	private String effectiveDate;
	
	@Valid
    FeeChargeDetailsCommonResource commonResource;
	
	@Valid
	List<FeeChargeDetailOptionalOptionResource> optionsList;
	
	
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

	public FeeChargeDetailsCommonResource getCommonResource() {
		return commonResource;
	}

	public void setCommonResource(FeeChargeDetailsCommonResource commonResource) {
		this.commonResource = commonResource;
	}

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



}
