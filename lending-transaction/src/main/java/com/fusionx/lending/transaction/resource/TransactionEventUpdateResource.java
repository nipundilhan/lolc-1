package com.fusionx.lending.transaction.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TransactionEventUpdateResource extends TransactionEventAddResource {
	
	@JsonProperty("version")
    @NotBlank(message = "{version.not-null}")
    @Pattern(regexp = "^$|[0-9]", message = "{version.pattern}")
    private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	

}
