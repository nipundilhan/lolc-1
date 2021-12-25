package com.fusionx.lending.product.resources;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AddProductSegmentRequest {

	private String tenantId;

	private String productSegmentId;
	
	@NotBlank(message = "{common.not-null}")
	private String productId;
	
//	@Size(max = 4, min = 4, message = "{common-code.size}")
//	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
//	@NotBlank(message = "{common.not-null}")
//	private String productCode;

	@NotBlank(message = "{segmentId.not-null}")
	private String segmentId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{code.pattern}")
	private String segmentCode;

	@NotBlank(message = "{status.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	private String createdUser;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSegmentId() {
		return segmentId;
	}

	public void setSegmentId(String segmentId) {
		this.segmentId = segmentId;
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

	public String getSegmentCode() {
		return segmentCode;
	}

	public void setSegmentCode(String segmentCode) {
		this.segmentCode = segmentCode;
	}

	public String getProductSegmentId() {
		return productSegmentId;
	}

	public void setProductSegmentId(String productSegmentId) {
		this.productSegmentId = productSegmentId;
	}

//	public String getProductCode() {
//		return productCode;
//	}
//
//	public void setProductCode(String productCode) {
//		this.productCode = productCode;
//	}

}
