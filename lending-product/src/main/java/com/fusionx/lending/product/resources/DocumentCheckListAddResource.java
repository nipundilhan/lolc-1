package com.fusionx.lending.product.resources;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DocumentCheckListAddResource {

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	@NotBlank(message = "{common.not-null}")
	private String productId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String productName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	@NotBlank(message = "{common.not-null}")
	private String subProductId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String subProductName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	@NotBlank(message = "{common.not-null}")
	private String applicableLevelId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String applicableLevelName;
	
//	@NotBlank(message = "{code.not-null}")
//	@Size(max = 4, min = 4, message = "{code.size}")
//	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{code.pattern}")
//	private String subProductCode;
	
	@NotBlank(message = "{code.not-null}")
	@Size(max = 4, min = 4, message = "{code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{code.pattern}")
	private String code;
	
	@NotBlank(message = "{name.not-null}")
	@Size(max = 70, message = "{name.size}")
	private String name;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@Valid
	private List<DocumentDetailsListResource> documentDetails;

	public String getApplicableLevelId() {
		return applicableLevelId;
	}

	public void setApplicableLevelId(String applicableLevelId) {
		this.applicableLevelId = applicableLevelId;
	}

	public String getApplicableLevelName() {
		return applicableLevelName;
	}

	public void setApplicableLevelName(String applicableLevelName) {
		this.applicableLevelName = applicableLevelName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSubProductName() {
		return subProductName;
	}

	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	
	public void setNamee(String name) {
		this.name = name;
	}
	public String getSubProductId() {
		return subProductId;
	}
	
	public void setSubProductId(String subProductId) {
		this.subProductId = subProductId;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<DocumentDetailsListResource> getDocumentDetails() {
		return documentDetails;
	}

	public void setDocumentDetails(List<DocumentDetailsListResource> documentDetails) {
		this.documentDetails = documentDetails;
	}

}
