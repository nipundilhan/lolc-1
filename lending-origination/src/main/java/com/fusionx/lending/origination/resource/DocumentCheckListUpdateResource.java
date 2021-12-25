package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DocumentCheckListUpdateResource {
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	@NotBlank(message = "{common.not-null}")
	private String leadId;
	
//	@NotBlank(message = "{common.not-null}")
//	@Size(max = 70, message = "{common-name.size}")
//	private String leadName;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 100, message = "{common-length01.size}")
	private String documentRefNo;
	
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
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;
	
	@Valid
	private List<DocumentUpdateResource> documentDetails;

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getDocumentRefNo() {
		return documentRefNo;
	}

	public void setDocumentRefNo(String documentRefNo) {
		this.documentRefNo = documentRefNo;
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

	public List<DocumentUpdateResource> getDocumentDetails() {
		return documentDetails;
	}

	public void setDocumentDetails(List<DocumentUpdateResource> documentDetails) {
		this.documentDetails = documentDetails;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
