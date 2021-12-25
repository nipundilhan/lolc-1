package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PersonRequestValidateResource{

	@JsonProperty("penPerId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perId.pattern}")
	private String penPerId;
	
	@JsonProperty("perId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perId.pattern}")
	private String perId;
	
	@JsonProperty("perCode")
	@NotBlank(message = "{perCode.not-null}")
	@Size(max = 15, message = "{perCode.size}")
	private String perCode;

	public PersonRequestValidateResource() {
		super();
	}

	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}

	public String getPerCode() {
		return perCode;
	}

	public void setPerCode(String perCode) {
		this.perCode = perCode;
	}

	public String getPenPerId() {
		return penPerId;
	}

	public void setPenPerId(String penPerId) {
		this.penPerId = penPerId;
	}
	
}
