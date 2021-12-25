package com.fusionx.lending.product.resources;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;



@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AddMainProduct {
		
	@NotBlank(message = "{brandId.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String brandId;
	
	@NotBlank(message = "{name.not-null}")
	private String brandName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String productGroupId;
	
	@NotBlank(message = "{name.not-null}")
	private String productGroupName;
	
	@NotBlank(message = "{productCode.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String productCode;
	
	@NotBlank(message = "{name.not-null}")  
	@Size(max = 350, message = "{name.size}")
	private String productName;
	
	@Size(max = 2500, message = "{productGroupDescription.size}")
	private String description;
	
//	@JsonProperty("segments")
//	@Valid
//	private List<AddProductSegmentRequest> segments;
	
	@NotBlank(message = "{status.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	private String createdUser;

	private String tenantId;

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getProductGroupId() {
		return productGroupId;
	}

	public void setProductGroupId(String productGroupId) {
		this.productGroupId = productGroupId;
	}

	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

//	public List<AddProductSegmentRequest> getSegments() {
//		return segments;
//	}
//
//	public void setSegments(List<AddProductSegmentRequest> segments) {
//		this.segments = segments;
//	}

	
}
