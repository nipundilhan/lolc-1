package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
Master Definition 
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-07-2019                            Dilki        Created
 *    
 ********************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MasterDefinitionAddResource {

	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String name;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

//	@NotBlank(message = "{common.not-null}")
	@Size(max = 350, message = "{common-description.size}")
	private String description;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "GENERAL|ISLAMIC", message = "{masterDefinition-businessPrinciple.pattern}")
	private String businessPrinciple;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-module.size}")
	private String module;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String subModuleCode;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-subModuleName.size}")
	private String subModuleName;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBusinessPrinciple() {
		return businessPrinciple;
	}

	public void setBusinessPrinciple(String businessPrinciple) {
		this.businessPrinciple = businessPrinciple;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getSubModuleCode() {
		return subModuleCode;
	}

	public void setSubModuleCode(String subModuleCode) {
		this.subModuleCode = subModuleCode;
	}

	public String getSubModuleName() {
		return subModuleName;
	}

	public void setSubModuleName(String subModuleName) {
		this.subModuleName = subModuleName;
	}

}
