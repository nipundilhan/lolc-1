package com.fusionx.lending.transaction.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WaiveOffApprovalTypesUpdateResource extends WaiveOffApprovalTypesAddResource {

	@NotBlank(message="{version.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{version.pattern}") 
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
	
}
