package com.fusionx.lending.product.resources;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BusinessCenterProductMapAddResource {

	@Valid
	private List<ProductRequestResource> products;

	public List<ProductRequestResource> getProducts() {
		return products;
	}

	public void setProducts(List<ProductRequestResource> products) {
		this.products = products;
	}
	
	
}
