package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * 	Other Income Documents
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-09-2021   FXL-78  	     FXL-777       Dilki        Created
 *    
 ********************************************************************************************************
*/
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
@Data
@Table(name = "other_income_detail_documents")
public class OtherIncomeDetailDocuments extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 9217999754716341645L;

	@Column(name = "tenant_id")
	private String tenantId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "other_income_details_id", nullable = false)
	private OtherIncomeDetails otherIncomeDetails;

	@Column(name = "document_id", nullable = false)
	private Long documentId;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length = 20, nullable = false)
	private CommonStatus status;

	@Column(name = "created_user")
	private String createdUser;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "modified_user")
	private String modifiedUser;

	@Column(name = "modified_date")
	private Timestamp modifiedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public OtherIncomeDetails getOtherIncomeDetails() {
		return otherIncomeDetails;
	}

	public void setOtherIncomeDetails(OtherIncomeDetails otherIncomeDetails) {
		this.otherIncomeDetails = otherIncomeDetails;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
