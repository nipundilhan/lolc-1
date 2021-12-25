package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;


@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BusinessCenterUpdateResource extends BusinessCenterAddResource{

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
