package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CustomerOnBoardProductRequestResource {
	@NotBlank(message = "{customerOnBoardRequestId.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{customerOnBoardRequestId.pattern}")
	private String customerOnBoardRequestId;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{productPendingCustomerId.pattern}")
	private String productPendingCustomerId;

	@Pattern(regexp = "^$|[0-9]+", message = "{productCustomerId.pattern}")
	private String productCustomerId;

	@NotBlank(message = "{productCategoryId.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{productCategoryId.pattern}")
	private String productCategoryId;
	
	//@NotBlank(message = "{productCommonPurposeId.not-null}")
	@Pattern(regexp = "^$|[0-9]+",message="{productCommonPurposeId.pattern}")
    private String productCommonPurposeId;
	
	//@NotBlank(message = "{productCommonPurposeCode.not-null}")
	//@Pattern(regexp = "^$|[0-9]+",message="{productCommonPurposeCode.pattern}")
    private String productCommonPurposeCode;
	
	//@NotBlank(message = "{customerOnBoardPurposeDescription.not-null}")
	//@Size(max=255, message = "{customerOnBoardPurposeDescription.size}")
	private String customerOnBoardPurposeDescription;
	
	//@NotBlank(message = "{productExpectedFacilityAmount.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$",message="{productExpectedFacilityAmount.pattern}")
	private String productExpectedFacilityAmount;
	
	//@NotBlank(message = "{productExpectedPeriodTypeId.not-null}")
	@Pattern(regexp = "^$|[0-9]+",message="{productExpectedPeriodTypeId.pattern}")
    private String productExpectedPeriodTypeId;
	
	//@NotBlank(message = "{productExpectedPeriodTypeName.not-null}")
	//@Size(max=255, message = "{customerOnBoardPeriodTypeName.size}")
	//private String productExpectedPeriodTypeName;
	
	//@NotBlank(message = "{productExpectedPeriodTypeCode.not-null}")
	//@Size(max=255, message = "{customerOnBoardPeriodTypeCode.size}")
	//private String productExpectedPeriodTypeCode;
	
	private String productExpectedPeriodTypeDesc;
	
	//@NotBlank(message = "{productExpectedPeriodLength.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{productExpectedPeriodLength.pattern}")
	private String productExpectedPeriodLength;
	
	//@NotBlank(message = "{productNotes.not-null}")
	//@Size(max=255, message = "{customerOnBoardNotes.size}")
	private String productNotes;
	
	@NotBlank(message = "{productStatus.not-null}")
	@Pattern(regexp = "^$|ACTIVE|INACTIVE",message="{productStatus.pattern}")
    private String productStatus;

	public String getCustomerOnBoardRequestId() {
		return customerOnBoardRequestId;
	}

	public void setCustomerOnBoardRequestId(String customerOnBoardRequestId) {
		this.customerOnBoardRequestId = customerOnBoardRequestId;
	}

	public String getProductPendingCustomerId() {
		return productPendingCustomerId;
	}

	public void setProductPendingCustomerId(String productPendingCustomerId) {
		this.productPendingCustomerId = productPendingCustomerId;
	}

	public String getProductCustomerId() {
		return productCustomerId;
	}

	public void setProductCustomerId(String productCustomerId) {
		this.productCustomerId = productCustomerId;
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public String getProductCommonPurposeId() {
		return productCommonPurposeId;
	}

	public void setProductCommonPurposeId(String productCommonPurposeId) {
		this.productCommonPurposeId = productCommonPurposeId;
	}

	public String getProductCommonPurposeCode() {
		return productCommonPurposeCode;
	}

	public void setProductCommonPurposeCode(String productCommonPurposeCode) {
		this.productCommonPurposeCode = productCommonPurposeCode;
	}

	public String getCustomerOnBoardPurposeDescription() {
		return customerOnBoardPurposeDescription;
	}

	public void setCustomerOnBoardPurposeDescription(String customerOnBoardPurposeDescription) {
		this.customerOnBoardPurposeDescription = customerOnBoardPurposeDescription;
	}

	public String getProductExpectedFacilityAmount() {
		return productExpectedFacilityAmount;
	}

	public void setProductExpectedFacilityAmount(String productExpectedFacilityAmount) {
		this.productExpectedFacilityAmount = productExpectedFacilityAmount;
	}

	public String getProductExpectedPeriodTypeId() {
		return productExpectedPeriodTypeId;
	}

	public void setProductExpectedPeriodTypeId(String productExpectedPeriodTypeId) {
		this.productExpectedPeriodTypeId = productExpectedPeriodTypeId;
	}

	/*public String getProductExpectedPeriodTypeName() {
		return productExpectedPeriodTypeName;
	}

	public void setProductExpectedPeriodTypeName(String productExpectedPeriodTypeName) {
		this.productExpectedPeriodTypeName = productExpectedPeriodTypeName;
	}

	public String getProductExpectedPeriodTypeCode() {
		return productExpectedPeriodTypeCode;
	}

	public void setProductExpectedPeriodTypeCode(String productExpectedPeriodTypeCode) {
		this.productExpectedPeriodTypeCode = productExpectedPeriodTypeCode;
	}*/

	public String getProductExpectedPeriodLength() {
		return productExpectedPeriodLength;
	}

	public void setProductExpectedPeriodLength(String productExpectedPeriodLength) {
		this.productExpectedPeriodLength = productExpectedPeriodLength;
	}

	public String getProductNotes() {
		return productNotes;
	}

	public void setProductNotes(String productNotes) {
		this.productNotes = productNotes;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getProductExpectedPeriodTypeDesc() {
		return productExpectedPeriodTypeDesc;
	}

	public void setProductExpectedPeriodTypeDesc(String productExpectedPeriodTypeDesc) {
		this.productExpectedPeriodTypeDesc = productExpectedPeriodTypeDesc;
	}
}
