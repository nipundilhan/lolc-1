package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Guarantor Income Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6301    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GuarantorIncomeAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String guarantorDetailId;
	
	@Valid
	private List<GuarantorIncomeBusinessAddResource> guarantorIncomeBusiness;

	public String getGuarantorDetailId() {
		return guarantorDetailId;
	}

	public void setGuarantorDetailId(String guarantorDetailId) {
		this.guarantorDetailId = guarantorDetailId;
	}

	public List<GuarantorIncomeBusinessAddResource> getGuarantorIncomeBusiness() {
		return guarantorIncomeBusiness;
	}

	public void setGuarantorIncomeBusiness(List<GuarantorIncomeBusinessAddResource> guarantorIncomeBusiness) {
		this.guarantorIncomeBusiness = guarantorIncomeBusiness;
	}
	
	

}
