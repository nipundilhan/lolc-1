package com.fusionx.lending.origination.domain;

/**
 * Check List Template
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-08-2021   FXL-75  		 FXL-551 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import java.io.Serializable;
import java.sql.Timestamp;
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
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.CommonApproveStatus;

@Entity
@Table(name = "check_list_template_pending")
public class CheckListTemplatePending extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -6680104648301043818L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@Column(name = "code", length = 4, nullable = false)
	private String code;

	@Column(name = "applicable_level", length = 70, nullable = false)
	private CommonList applicableLevel;

	@Column(name = "main_product", length = 70, nullable = false)
	private String mainProduct;

	@Column(name = "sub_product", length = 70, nullable = false)
	private String subProduct;

	@Column(name = "name", length = 70, nullable = false)
	private String name;

	@Column(name = "app_rej_comment", length = 350, nullable = true)
	private String comment;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private CommonStatus status;

	@Transient
	private List<CheckListTemplateDetailsPending> checkListTemplateDetails;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "check_list_template_id", nullable = false)
	private CheckListTemplate checkListTemplate;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "microbpr_workflow_id", nullable = false)
	private MicroBprWorkflow microBprWorkflow;

	@Column(name = "created_user", length = 255, nullable = false)
	private String createdUser;

	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;

	@Column(name = "modified_user", nullable = true, length = 255)
	private String modifiedUser;

	@Column(name = "modified_date", nullable = true)
	private Timestamp modifiedDate;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "approve_status", nullable = true, length = 30)
	private CommonApproveStatus approveStatus;

	@Column(name = "pen_created_user", nullable = true, length = 255)
	private String penCreatedUser;

	@Column(name = "pen_created_date", nullable = true)
	private Timestamp penCreatedDate;

	@Column(name = "pen_approved_user", nullable = true, length = 255)
	private String penApprovedUser;

	@Column(name = "pen_approved_date", nullable = true)
	private Timestamp penApprovedDate;

	@Column(name = "pen_rejected_user", nullable = true, length = 255)
	private String penRejectedUser;

	@Column(name = "pen_rejected_date", nullable = true)
	private Timestamp penRejectedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public CommonList getApplicableLevel() {
		return applicableLevel;
	}

	public void setApplicableLevel(CommonList applicableLevel) {
		this.applicableLevel = applicableLevel;
	}

	public String getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

	public String getSubProduct() {
		return subProduct;
	}

	public void setSubProduct(String subProduct) {
		this.subProduct = subProduct;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public List<CheckListTemplateDetailsPending> getCheckListTemplateDetails() {
		return checkListTemplateDetails;
	}

	public void setCheckListTemplateDetails(List<CheckListTemplateDetailsPending> checkListTemplateDetails) {
		this.checkListTemplateDetails = checkListTemplateDetails;
	}

	public CheckListTemplate getCheckListTemplate() {
		return checkListTemplate;
	}

	public void setCheckListTemplate(CheckListTemplate checkListTemplate) {
		this.checkListTemplate = checkListTemplate;
	}

	public MicroBprWorkflow getMicroBprWorkflow() {
		return microBprWorkflow;
	}

	public void setMicroBprWorkflow(MicroBprWorkflow microBprWorkflow) {
		this.microBprWorkflow = microBprWorkflow;
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

	public CommonApproveStatus getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(CommonApproveStatus approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getPenCreatedUser() {
		return penCreatedUser;
	}

	public void setPenCreatedUser(String penCreatedUser) {
		this.penCreatedUser = penCreatedUser;
	}

	public Timestamp getPenCreatedDate() {
		return penCreatedDate;
	}

	public void setPenCreatedDate(Timestamp penCreatedDate) {
		this.penCreatedDate = penCreatedDate;
	}

	public String getPenApprovedUser() {
		return penApprovedUser;
	}

	public void setPenApprovedUser(String penApprovedUser) {
		this.penApprovedUser = penApprovedUser;
	}

	public Timestamp getPenApprovedDate() {
		return penApprovedDate;
	}

	public void setPenApprovedDate(Timestamp penApprovedDate) {
		this.penApprovedDate = penApprovedDate;
	}

	public String getPenRejectedUser() {
		return penRejectedUser;
	}

	public void setPenRejectedUser(String penRejectedUser) {
		this.penRejectedUser = penRejectedUser;
	}

	public Timestamp getPenRejectedDate() {
		return penRejectedDate;
	}

	public void setPenRejectedDate(Timestamp penRejectedDate) {
		this.penRejectedDate = penRejectedDate;
	}

}
