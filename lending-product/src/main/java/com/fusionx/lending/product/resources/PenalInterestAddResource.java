package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Penal Interest Service - Resource
 * @author 	Dilhan
 * @Date    02-06-2020
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-06-2020   FX-1517       FX-3902    VenukiR       Created
 *    
 ********************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PenalInterestAddResource {

	@NotBlank(message = "{status.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{status.pattern}")
	private String status;

	@NotBlank(message="{code.not-null}")
	@Size(max = 4, min = 4, message = "{common.invalid-value}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{code.pattern}") 
	private String code;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String name;
	
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


}
