package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * MasterDef Due Date Mapping Update Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   29-09-2021    FXL-680  	 FXL-924	Piyumi      Created
 *    
 ********************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MasterDefDueDateMappingUpdateResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String dueDateTemplateId;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String dueDateTemplateCode;

	@Size(max = 1000, message = "{remark.size}")
	private String remark;

	public String getDueDateTemplateId() {
		return dueDateTemplateId;
	}

	public void setDueDateTemplateId(String dueDateTemplateId) {
		this.dueDateTemplateId = dueDateTemplateId;
	}

	public String getDueDateTemplateCode() {
		return dueDateTemplateCode;
	}

	public void setDueDateTemplateCode(String dueDateTemplateCode) {
		this.dueDateTemplateCode = dueDateTemplateCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
