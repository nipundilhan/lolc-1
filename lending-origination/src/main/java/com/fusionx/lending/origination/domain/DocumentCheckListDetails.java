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
import com.fusionx.lending.origination.enums.YesNo;

/**
 * Document Check List Details Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-09-2021      		            	Dilhan      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "document_checklist_details")
//@Data
public class DocumentCheckListDetails extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -4664217111636989908L;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "document_checklist_id", nullable=false)
	private DocumentCheckList documentCheckList;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Column(name = "document_id", nullable=false)
	private Long documentId;
	
	@Column(name = "created_user",length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date",nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user",length=255, nullable=true)
	private String modifiedUser;
	
	@Column(name = "modified_date",nullable=true)
	private Timestamp modifiedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public DocumentCheckList getDocumentCheckList() {
		return documentCheckList;
	}

	public void setDocumentCheckList(DocumentCheckList documentCheckList) {
		this.documentCheckList = documentCheckList;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
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
