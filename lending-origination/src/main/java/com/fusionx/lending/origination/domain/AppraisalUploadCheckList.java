package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fusionx.lending.origination.core.BaseEntity;

import lombok.Data;


/**
 * Appraisal Upload Check List Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-09-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "appraisal_upload_check_list")
@Data
public class AppraisalUploadCheckList extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6570160972603385840L;
	
	@Column(name = "tenant_id")
    private String tenantId;
 
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "checklist_template_id")  
    private CheckListTemplate checklistTemplates;
    
    @Transient
    private Long checklistTemplateId;
 
    @Transient
    private String checklistTemplateCode;
    
    @Transient
    private String checklistTemplateName;
    
    @Column(name = "document_id")  
    private Long documentId;
    
//    @Transient
//    private String documentCode;
//	
//	@Transient
//    private String documentName;
	
	@Column(name = "mandatory")
    private String isMandatory;
 
	@Column(name = "completed")
    private String isCompleted;
	
	@Column(name = "completed_date")
    private Date completedDate;
	
    @Column(name = "description")
    private String description;
    
    @Column(name = "status")
    private String status;
 
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

	public CheckListTemplate getChecklistTemplates() {
		return checklistTemplates;
	}

	public void setChecklistTemplates(CheckListTemplate checklistTemplates) {
		this.checklistTemplates = checklistTemplates;
	}
	
	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public String getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}

	public String getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(String isCompleted) {
		this.isCompleted = isCompleted;
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

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	//@transient columns
	public Long getChecklistTemplateId() {
		return checklistTemplates.getId();
	}

	public String getChecklistTemplateCode() {
		return checklistTemplates.getCode();
	}

	public String getChecklistTemplateName() {
		return checklistTemplates.getName();
	}

}
