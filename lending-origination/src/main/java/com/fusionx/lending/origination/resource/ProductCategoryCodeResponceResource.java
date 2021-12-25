package com.fusionx.lending.origination.resource;

public class ProductCategoryCodeResponceResource {
	
	private Long id;
	
	private String productCategoryCode;
	
	private String productCategoryName;
	
	private String productCategoryStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductCategoryCode() {
		return productCategoryCode;
	}

	public void setProductCategoryCode(String productCategoryCode) {
		this.productCategoryCode = productCategoryCode;
	}

	public String getProductCategoryName() {
		return productCategoryName;
	}

	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}

	public String getProductCategoryStatus() {
		return productCategoryStatus;
	}

	public void setProductCategoryStatus(String productCategoryStatus) {
		this.productCategoryStatus = productCategoryStatus;
	}
}
