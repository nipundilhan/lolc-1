package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ApprovalCatProductMapAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String approvalCatId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String approvalCatCode;
	
	@JsonProperty("products")
	//@NotBlank(message = "{common.not-null}")
	@Valid
	private List<ApprovalCatProductMapAddProductResource> products;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	public String getApprovalCatId() {
		return approvalCatId;
	}

	public void setApprovalCatId(String approvalCatId) {
		this.approvalCatId = approvalCatId;
	}

	public String getApprovalCatCode() {
		return approvalCatCode;
	}

	public void setApprovalCatCode(String approvalCatCode) {
		this.approvalCatCode = approvalCatCode;
	}

//	public String getApprovalCatName() {
//		return approvalCatName;
//	}
//
//	public void setApprovalCatName(String approvalCatName) {
//		this.approvalCatName = approvalCatName;
//	}
//
//	public String getApprovalCatDesc() {
//		return approvalCatDesc;
//	}
//
//	public void setApprovalCatDesc(String approvalCatDesc) {
//		this.approvalCatDesc = approvalCatDesc;
//	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ApprovalCatProductMapAddProductResource> getProducts() {
		return products;
	}

	public void setProducts(List<ApprovalCatProductMapAddProductResource> products) {
		this.products = products;
	}
	
}
