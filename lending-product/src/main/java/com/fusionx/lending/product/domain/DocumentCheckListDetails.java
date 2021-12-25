package com.fusionx.lending.product.domain;

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
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.YesNo;

import lombok.Data;

/**
 * Document Check List Details Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   12-07-2021      		            	Dilhan      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "document_checklist_details")
@Data
public class DocumentCheckListDetails extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -4664217111636989908L;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "document_checklist_id", nullable=false)
	private DocumentCheckList documentCheckList;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Column(name = "document_type_id")
	private Long documentTypeId;
	
	@Column(name = "document_type_name", length=70, nullable=false)
	private String documentTypeName;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "is_mandatory", length=20, nullable=true)
	private YesNo isMandatory;
	
	
	@Column(name = "created_user",length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date",nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user",length=255, nullable=true)
	private String modifiedUser;
	
	@Column(name = "modified_date",nullable=true)
	private Timestamp modifiedDate;
	
	public YesNo getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(YesNo isMandatory) {
		this.isMandatory = isMandatory;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}
	public Long getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(Long documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}

	public DocumentCheckList getDocumentCheckList() {
		return documentCheckList;
	}

	public void setDocumentCheckList(DocumentCheckList documentCheckList) {
		this.documentCheckList = documentCheckList;
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

//	public Long getExpenseTypesId() {
//		return expenseType.getId();
//	}
}
