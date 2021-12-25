package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * InterestTierBandSetAddResource 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   19-07-2021    FXL_July_2021_2  	FXL-52		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class InterestTierBandSetAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String tierBandMethodId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String tierBandMethodName;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String calculateMethodId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String calculateMethodName;
	
	@Size(max = 255, message = "{common-name.size}")
	private String note;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	public String getTierBandMethodId() {
		return tierBandMethodId;
	}

	public void setTierBandMethodId(String tierBandMethodId) {
		this.tierBandMethodId = tierBandMethodId;
	}
	
	public String getTierBandMethodName() {
		return tierBandMethodName;
	}

	public void setTierBandMethodName(String tierBandMethodName) {
		this.tierBandMethodName = tierBandMethodName;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCalculateMethodId() {
		return calculateMethodId;
	}

	public void setCalculateMethodId(String calculateMethodId) {
		this.calculateMethodId = calculateMethodId;
	}
	
	public String getCalculateMethodName() {
		return calculateMethodName;
	}

	public void setCalculateMethodName(String calculateMethodName) {
		this.calculateMethodName = calculateMethodName;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
