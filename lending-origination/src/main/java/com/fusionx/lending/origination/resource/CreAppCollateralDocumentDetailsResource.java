package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CreAppCollateralDocumentDetailsResource {
	
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String creditAppraiselCollateralDetId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String documentId;

	private String documentName;

	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	public String getCreditAppraiselCollateralDetId() {
		return creditAppraiselCollateralDetId;
	}

	public void setCreditAppraiselCollateralDetId(String creditAppraiselCollateralDetId) {
		this.creditAppraiselCollateralDetId = creditAppraiselCollateralDetId;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
