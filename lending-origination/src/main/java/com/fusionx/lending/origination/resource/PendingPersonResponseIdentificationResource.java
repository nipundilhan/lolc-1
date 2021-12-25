package com.fusionx.lending.origination.resource;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PendingPersonResponseIdentificationResource {

	private Long id;
	
	private String ppidtTenantId;
	
	private Long pidtId;
	
	private Long ppidtIdentificationTypeId;
	
	private String ppidtIdentificationTypeDesc;
	
	private String ppidtIdentificationTypeCode;
	
	private String ppidtIdentificationNo;
	
	private Date ppidtIssueDate;
	
	private Date ppidtExpiryDate;
	
	private String ppidtDocumentUrl;
	
	private String ppidtExemptedStatus;
	
	private String ppidtExemptedRemark;
	
	private String ppidtStatus;
	
	private String ppidtCreatedUser;
	
	private Date ppidtCreatedDate;
	
	private String ppidtModifiedUser;
	
	private Date ppidtModifiedDate;
	
	private Long version;

	public PendingPersonResponseIdentificationResource() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPidtId() {
		return pidtId;
	}

	public void setPidtId(Long pidtId) {
		this.pidtId = pidtId;
	}

	public Long getPpidtIdentificationTypeId() {
		return ppidtIdentificationTypeId;
	}

	public void setPpidtIdentificationTypeId(Long ppidtIdentificationTypeId) {
		this.ppidtIdentificationTypeId = ppidtIdentificationTypeId;
	}

	public String getPpidtIdentificationNo() {
		return ppidtIdentificationNo;
	}

	public void setPpidtIdentificationNo(String ppidtIdentificationNo) {
		this.ppidtIdentificationNo = ppidtIdentificationNo;
	}

	public Date getPpidtIssueDate() {
		return ppidtIssueDate;
	}

	public void setPpidtIssueDate(Date ppidtIssueDate) {
		this.ppidtIssueDate = ppidtIssueDate;
	}

	public Date getPpidtExpiryDate() {
		return ppidtExpiryDate;
	}

	public void setPpidtExpiryDate(Date ppidtExpiryDate) {
		this.ppidtExpiryDate = ppidtExpiryDate;
	}

	public String getPpidtDocumentUrl() {
		return ppidtDocumentUrl;
	}

	public void setPpidtDocumentUrl(String ppidtDocumentUrl) {
		this.ppidtDocumentUrl = ppidtDocumentUrl;
	}

	public String getPpidtStatus() {
		return ppidtStatus;
	}

	public void setPpidtStatus(String ppidtStatus) {
		this.ppidtStatus = ppidtStatus;
	}

	public String getPpidtCreatedUser() {
		return ppidtCreatedUser;
	}

	public void setPpidtCreatedUser(String ppidtCreatedUser) {
		this.ppidtCreatedUser = ppidtCreatedUser;
	}

	public Date getPpidtCreatedDate() {
		return ppidtCreatedDate;
	}

	public void setPpidtCreatedDate(Date ppidtCreatedDate) {
		this.ppidtCreatedDate = ppidtCreatedDate;
	}

	public String getPpidtExemptedStatus() {
		return ppidtExemptedStatus;
	}

	public void setPpidtExemptedStatus(String ppidtExemptedStatus) {
		this.ppidtExemptedStatus = ppidtExemptedStatus;
	}

	public String getPpidtExemptedRemark() {
		return ppidtExemptedRemark;
	}

	public void setPpidtExemptedRemark(String ppidtExemptedRemark) {
		this.ppidtExemptedRemark = ppidtExemptedRemark;
	}

	public String getPpidtTenantId() {
		return ppidtTenantId;
	}

	public void setPpidtTenantId(String ppidtTenantId) {
		this.ppidtTenantId = ppidtTenantId;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getPpidtModifiedUser() {
		return ppidtModifiedUser;
	}

	public void setPpidtModifiedUser(String ppidtModifiedUser) {
		this.ppidtModifiedUser = ppidtModifiedUser;
	}

	public Date getPpidtModifiedDate() {
		return ppidtModifiedDate;
	}

	public void setPpidtModifiedDate(Date ppidtModifiedDate) {
		this.ppidtModifiedDate = ppidtModifiedDate;
	}

	public String getPpidtIdentificationTypeDesc() {
		return ppidtIdentificationTypeDesc;
	}

	public void setPpidtIdentificationTypeDesc(String ppidtIdentificationTypeDesc) {
		this.ppidtIdentificationTypeDesc = ppidtIdentificationTypeDesc;
	}

	public String getPpidtIdentificationTypeCode() {
		return ppidtIdentificationTypeCode;
	}

	public void setPpidtIdentificationTypeCode(String ppidtIdentificationTypeCode) {
		this.ppidtIdentificationTypeCode = ppidtIdentificationTypeCode;
	}
}
