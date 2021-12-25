package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProductCategoryProductMapAddResource {
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common.invalid-number-format}")
	@NotBlank(message = "{common.not-null}")
	private String productCatId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String productCatCode;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@Valid
	private List<ProductRequestResource> products;

	public String getProductCatId() {
		return productCatId;
	}

	public void setProductCatId(String productCatId) {
		this.productCatId = productCatId;
	}

	public String getProductCatCode() {
		return productCatCode;
	}

	public void setProductCatCode(String productCatCode) {
		this.productCatCode = productCatCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ProductRequestResource> getProducts() {
		return products;
	}

	public void setProducts(List<ProductRequestResource> products) {
		this.products = products;
	}
	
}
