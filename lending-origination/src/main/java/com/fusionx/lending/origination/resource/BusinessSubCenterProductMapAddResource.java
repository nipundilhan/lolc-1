package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BusinessSubCenterProductMapAddResource {

	@Valid
	private List<ProductRequestResource> products;

	public List<ProductRequestResource> getProducts() {
		return products;
	}

	public void setProducts(List<ProductRequestResource> products) {
		this.products = products;
	}
}
