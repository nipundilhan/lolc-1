package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CustomerPersonIdentificationRequestResource {

	@JsonProperty("culpId")
	@Pattern(regexp = "^$|[0-9]+", message = "{culpId.pattern}")
	private String culpId;
	
	@JsonProperty("pculpId")
	@Pattern(regexp = "^$|[0-9]+", message = "{pculpId.pattern}")
	private String pculpId;
	
	@JsonProperty("pidtId")
	@Pattern(regexp = "^$|[0-9]+", message = "{pidtId.pattern}")
	private String pidtId;
	
	@JsonProperty("ppidtId")
	@Pattern(regexp = "^$|[0-9]+", message = "{pidtId.pattern}")
	private String ppidtId;
	
	@JsonProperty("pidtIdentificationTypeId")
	@NotBlank(message = "{pidtIdentificationTypeId.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{pidtIdentificationTypeId.pattern}")
	private String pidtIdentificationTypeId;
	private String pidtIdentificationTypeDesc;
	
	@JsonProperty("pidtIdentificationNo")
	@NotBlank(message = "{pidtIdentificationNo.not-null}")
	@Size(max = 255, message = "{pidtIdentificationNo.size}")
	private String pidtIdentificationNo;
	
	@JsonProperty("pidtIssueDate")
	@Pattern(regexp = "^$|\\d{4}(\\/)(((0)[0-9])|((1)[0-2]))(\\/)([0-2][0-9]|(3)[0-1])$", message = "{pidtIssueDate.pattern}")
	private String pidtIssueDate;
	
	@JsonProperty("pidtExpiryDate")
	@Pattern(regexp = "^$|\\d{4}(\\/)(((0)[0-9])|((1)[0-2]))(\\/)([0-2][0-9]|(3)[0-1])$", message = "{pidtExpiryDate.pattern}")
	private String pidtExpiryDate;
	
	@JsonProperty("pidtDocumentUrl")
	@Size(max = 255, message = "{pidtDocumentUrl.size}")
	private String pidtDocumentUrl;
	
	@JsonProperty("pidtExemptedStatus")
	@Pattern(regexp = "^$|YES|NO", message = "{pidtExemptedStatus.pattern}")
	private String pidtExemptedStatus;
	
	@Size(max = 255, message = "{pidtExemptedRemark.size}")
	private String pidtExemptedRemark;
	
	@JsonProperty("pidtStatus")
	@NotBlank(message = "{pidtStatus.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{pidtStatus.pattern}")
	private String pidtStatus;

	public CustomerPersonIdentificationRequestResource() {
		super();
	}

	public String getPidtId() {
		return pidtId;
	}

	public void setPidtId(String pidtId) {
		this.pidtId = pidtId;
	}

	public String getPidtIdentificationTypeId() {
		return pidtIdentificationTypeId;
	}

	public void setPidtIdentificationTypeId(String pidtIdentificationTypeId) {
		this.pidtIdentificationTypeId = pidtIdentificationTypeId;
	}

	public String getPidtIdentificationTypeDesc() {
		return pidtIdentificationTypeDesc;
	}

	public void setPidtIdentificationTypeDesc(String pidtIdentificationTypeDesc) {
		this.pidtIdentificationTypeDesc = pidtIdentificationTypeDesc;
	}

	public String getPidtIdentificationNo() {
		return pidtIdentificationNo;
	}

	public void setPidtIdentificationNo(String pidtIdentificationNo) {
		this.pidtIdentificationNo = pidtIdentificationNo;
	}

	public String getPidtIssueDate() {
		return pidtIssueDate;
	}

	public void setPidtIssueDate(String pidtIssueDate) {
		this.pidtIssueDate = pidtIssueDate;
	}

	public String getPidtExpiryDate() {
		return pidtExpiryDate;
	}

	public void setPidtExpiryDate(String pidtExpiryDate) {
		this.pidtExpiryDate = pidtExpiryDate;
	}

	public String getPidtDocumentUrl() {
		return pidtDocumentUrl;
	}

	public void setPidtDocumentUrl(String pidtDocumentUrl) {
		this.pidtDocumentUrl = pidtDocumentUrl;
	}

	public String getPidtStatus() {
		return pidtStatus;
	}

	public void setPidtStatus(String pidtStatus) {
		this.pidtStatus = pidtStatus;
	}

	public String getPidtExemptedStatus() {
		return pidtExemptedStatus;
	}

	public void setPidtExemptedStatus(String pidtExemptedStatus) {
		this.pidtExemptedStatus = pidtExemptedStatus;
	}

	public String getPidtExemptedRemark() {
		return pidtExemptedRemark;
	}

	public void setPidtExemptedRemark(String pidtExemptedRemark) {
		this.pidtExemptedRemark = pidtExemptedRemark;
	}

	public String getCulpId() {
		return culpId;
	}

	public void setCulpId(String culpId) {
		this.culpId = culpId;
	}

	public String getPculpId() {
		return pculpId;
	}

	public void setPculpId(String pculpId) {
		this.pculpId = pculpId;
	}

	public String getPpidtId() {
		return ppidtId;
	}

	public void setPpidtId(String ppidtId) {
		this.ppidtId = ppidtId;
	}
}
