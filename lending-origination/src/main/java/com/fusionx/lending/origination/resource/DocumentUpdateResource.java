package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Document Update Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-08-2021   FXL-117  	 FXL-543       Piyumi     Created
 *    
 ********************************************************************************************************
*/

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class DocumentUpdateResource extends DocumentAddResource{
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String id;	
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
