package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseGuarantorValueResource {

	private ResponseGuarantorResource value;

	public ResponseGuarantorResource getValue() {
		return value;
	}

	public void setValue(ResponseGuarantorResource value) {
		this.value = value;
	}

    
}
