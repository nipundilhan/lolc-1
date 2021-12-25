package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Add Base Request
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-2880   	 FX-6515	Senitha      Created
 *    
 ********************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AddBaseRequest {
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{code.pattern}")
	private String code;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{name.size}")
	private String name;

	@Size(max = 350, message = "{description.size}")
	private String description;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{status.pattern}")
	private String status;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
