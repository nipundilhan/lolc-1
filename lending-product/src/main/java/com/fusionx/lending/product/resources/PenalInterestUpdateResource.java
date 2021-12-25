package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;


/**
 * Penal Interest Service - Resource
 * @author 	VenukiR
 * @Date    02-06-2020
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-06-2020   FX-1517       FX-3902    VenukiR       Created
 *    
 ********************************************************************************************************
 */

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PenalInterestUpdateResource extends PenalInterestAddResource {

	@JsonProperty("version")	
	@NotBlank(message = "{version.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{version.pattern}")
	private String version;
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	

}
