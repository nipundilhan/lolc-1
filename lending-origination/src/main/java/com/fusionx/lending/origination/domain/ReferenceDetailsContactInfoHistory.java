package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;

import lombok.Data;

@Entity
@Table(name = "reference_details_contact_info")
@Data
public class ReferenceDetailsContactInfoHistory extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -7340413004865692707L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
//

	@Column(name = "contact_type", length = 70, nullable = false)
	private String contactType;

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

//	
	@Column(name = "contact_no", length = 70, nullable = false)
	private String contactNo;

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

//
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private CommonStatus status;

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

//	
	@Column(name = "created_user", length = 255, nullable = false)
	private String createdUser;

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

//
	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

//	
	@Column(name = "modified_user", nullable = true, length = 255)
	private String modifiedUser;

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

//	
	@Column(name = "modified_date", nullable = true)
	private Timestamp modifiedDate;

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

//		
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "reference_details_id", nullable = false)
	private ReferenceDetails referenceDetails;

	public ReferenceDetails getReferenceDetails() {
		return referenceDetails;
	}

	public void setReferenceDetails(ReferenceDetails referenceDetails) {
		this.referenceDetails = referenceDetails;
	}

//
	@Column(name = "history_created_user", length = 255, nullable = false)
	private String historyCreatedUser;

	@Column(name = "history_created_date", nullable = false)
	private Timestamp historyCreatedDate;

	public String getHistoryCreatedUser() {
		return historyCreatedUser;
	}

	public void setHistoryCreatedUser(String historyCreatedUser) {
		this.historyCreatedUser = historyCreatedUser;
	}

	public Timestamp getHistoryCreatedDate() {
		return historyCreatedDate;
	}

	public void setHistoryCreatedDate(Timestamp historyCreatedDate) {
		this.historyCreatedDate = historyCreatedDate;
	}
	
	@Column(name = "contact_type_code", length = 4, nullable = false)
	private String contactTypeCode;

	public String getContactTypeCode() {
		return contactTypeCode;
	}

	public void setContactTypeCode(String contactTypeCode) {
		this.contactTypeCode = contactTypeCode;
	}

}
