package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProductListResponseObjectResponseResource {
	
	private String key;
	private String value;
	private String defaultSelected;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDefaultSelected() {
		return defaultSelected;
	}
	public void setDefaultSelected(String defaultSelected) {
		this.defaultSelected = defaultSelected;
	}
	
	

}
