package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;

public class CreditScoreFieldValueResource {
	
	@NotBlank(message = "{common.not-null}")
	private String field;
	
	@NotBlank(message = "{common.not-null}")
	private String value;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
