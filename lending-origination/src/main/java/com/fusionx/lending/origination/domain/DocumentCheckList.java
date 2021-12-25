package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.YesNo;

/**
 * Document Check List  Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-09-2021      		            	Dilhan      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "document_checklist")
//@Data
public class DocumentCheckList extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -8458849928278154630L;
	
	@Column(name = "tenant_id", length=20, nullable=false)
	private String tenantId;
	
	@JsonIgnore	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lead_id", nullable=false)
	private LeadInfo leadInfo;
	
	@Column(name = "document_ref_no", length=100, nullable=false)
	private String documentRefNo;
	
	@Column(name = "document_type_id",nullable=true)
	private Long documentTypeId;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "is_collected", nullable=false, length=20)
	private YesNo isCollected;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "is_mandatory", nullable=false, length=20)
	private YesNo isMandatory;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Column(name = "description", length=350, nullable=true)
	private String description;
	
	@Column(name = "created_user",length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "collected_by",length=255, nullable=false)
	private String collectedBy;
	
	@Column(name = "collected_date",nullable=true)
	private Date collectedDate;
	
	@Column(name = "created_date",nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user",length=255, nullable=true)
	private String modifiedUser;
	
	@Column(name = "modified_date",nullable=true)
	private Timestamp modifiedDate;
	
	@Transient
	@JsonInclude(Include.NON_NULL)
	private List<DocumentCheckListDetails> documentCheckListDetails;


	public Date getCollectedDate() {
		return collectedDate;
	}

	public void setCollectedDate(Date collectedDate) {
		this.collectedDate = collectedDate;
	}

	public String getCollectedBy() {
		return collectedBy;
	}

	public void setCollectedBy(String collectedBy) {
		this.collectedBy = collectedBy;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public LeadInfo getLeadInfo() {
		return leadInfo;
	}

	public void setLeadInfo(LeadInfo leadInfo) {
		this.leadInfo = leadInfo;
	}

	public String getDocumentRefNo() {
		return documentRefNo;
	}

	public void setDocumentRefNo(String documentRefNo) {
		this.documentRefNo = documentRefNo;
	}

	public Long getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(Long documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	public YesNo getIsCollected() {
		return isCollected;
	}

	public void setIsCollected(YesNo isCollected) {
		this.isCollected = isCollected;
	}

	public YesNo getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(YesNo isMandatory) {
		this.isMandatory = isMandatory;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<DocumentCheckListDetails> getDocumentCheckListDetails() {
		return documentCheckListDetails;
	}

	public void setDocumentCheckListDetails(List<DocumentCheckListDetails> documentCheckListDetails) {
		this.documentCheckListDetails = documentCheckListDetails;
	}

}
