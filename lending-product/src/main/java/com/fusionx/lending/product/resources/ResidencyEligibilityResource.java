package com.fusionx.lending.product.resources;

/**
 * Residency eligibility service
 * @author 	Rangana
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019     FX-6        FX-6523    Rangana      Created
 *    
 ********************************************************************************************************
 */

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResidencyEligibilityResource {
	
	@NotBlank(message="{residencyTypeId.not-null}")
	@Pattern(regexp = "|[0-9]+",message="{common.invalid-value}")
	private String residencyTypeId;
	
	@NotBlank(message="{residencyTypeName.not-null}")
	@Size(max = 70, message = "{residencyTypeName.size}") 
	private String residencyTypeName;
	
	@NotBlank(message="{status.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{status.pattern}")
	private String status;
	
	@NotBlank(message="{eligibilityCode.not-null}")
	@Size(min = 4,max = 4, message = "{common-code.size}") 
	private String code;

	public String getResidencyTypeId() {
		return residencyTypeId;
	}

	public void setResidencyTypeId(String residencyTypeId) {
		this.residencyTypeId = residencyTypeId;
	}

	public String getResidencyTypeName() {
		return residencyTypeName;
	}

	public void setResidencyTypeName(String residencyTypeName) {
		this.residencyTypeName = residencyTypeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
