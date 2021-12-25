package com.fusionx.lending.product.resources;

/**
 * Tax Profile Service
 * @author 	KilasiG
 * @Date     05-11-2019
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   05-11-2019   FX-1545       FX-2175    KilasiG      Created
 *    
 ********************************************************************************************************
 */

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class UpdateTaxProfileRequestResource{
	
	private String id;

	@JsonIgnore
	private String modifiedUser;
	
	@JsonProperty("version")
	@NotBlank(message="{version.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{version.pattern}") 
	private String version;
	
	@JsonProperty("taxProfileStatus")
	@NotBlank(message = "{taxProfileStatus.not-null}")
	@Pattern(regexp = "^$|ACTIVE|INACTIVE", message = "{taxProfileStatus.pattern}")
	private String taxProfileStatus;

	public String getTaxProfileStatus() {
		return taxProfileStatus;
	}

	public void setTaxProfileStatus(String taxProfileStatus) {
		this.taxProfileStatus = taxProfileStatus;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
