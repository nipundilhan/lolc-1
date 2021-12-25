package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ValidResource {
  public String value;

public String getValue() {
	return value;
}

public void setValue(String value) {
	this.value = value;
}
  
}
