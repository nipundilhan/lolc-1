package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * Sub Product Risk Mapping Update Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1  18-10-2021 							Dilki        Created
 *    
 ********************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SubProductRiskMappingUpdateResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String riskTemplateId;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String riskTemplateCode;

	@Size(max = 1000, message = "{remark.size}")
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRiskTemplateId() {
		return riskTemplateId;
	}

	public void setRiskTemplateId(String riskTemplateId) {
		this.riskTemplateId = riskTemplateId;
	}

	public String getRiskTemplateCode() {
		return riskTemplateCode;
	}

	public void setRiskTemplateCode(String riskTemplateCode) {
		this.riskTemplateCode = riskTemplateCode;
	}

}
