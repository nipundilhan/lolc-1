package com.fusionx.lending.origination.resource;

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
	private String leadId;
	
//	@NotBlank(message = "{common.not-null}")
//	@Size(max = 70, message = "{common-name.size}")
//	private String leadName;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 100, message = "{common-length01.size}")
	private String documentRefNo;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	@NotBlank(message = "{common.not-null}")
	private String documentCheckListDetId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String documentTypeName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "YES|NO", message = "{mandatory.pattern}")
	private String mandatory;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "YES|NO", message = "{collected.pattern}")
	private String collected;
	
	@Size(max = 350, message = "{common-length01.size}")
	private String description;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
    @Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{common-date.pattern}")
    private String collectedDate;
	
	@Valid
	private List<DocumentAddResource> documentDetails;


	public String getDocumentCheckListDetId() {
		return documentCheckListDetId;
	}

	public void setDocumentCheckListDetId(String documentCheckListDetId) {
		this.documentCheckListDetId = documentCheckListDetId;
	}

	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

//	public String getLeadName() {
//		return leadName;
//	}
//
//	public void setLeadName(String leadName) {
//		this.leadName = leadName;
//	}

	public String getDocumentRefNo() {
		return documentRefNo;
	}

	public void setDocumentRefNo(String documentRefNo) {
		this.documentRefNo = documentRefNo;
	}

	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	public String getCollected() {
		return collected;
	}

	public void setCollected(String collected) {
		this.collected = collected;
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

	public String getCollectedDate() {
		return collectedDate;
	}

	public void setCollectedDate(String collectedDate) {
		this.collectedDate = collectedDate;
	}

	public List<DocumentAddResource> getDocumentDetails() {
		return documentDetails;
	}

	public void setDocumentDetails(List<DocumentAddResource> documentDetails) {
		this.documentDetails = documentDetails;
	}

	
}
