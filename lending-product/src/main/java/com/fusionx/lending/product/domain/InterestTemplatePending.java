package com.fusionx.lending.product.domain;

/**
 * InterestTemplate
 * @author 	Venuki
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019   FX-2879        FX-6532    Venuki      Created
 *    
 ********************************************************************************************************
 */

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;

@Entity
@Table(name = "interest_template_pending")
@Data
public class InterestTemplatePending extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6846856856L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "interest_template_id", nullable=true)
	private InterestTemplate interestTemplate;
	
	@Column(name = "code", length=20, nullable=false)
	private String code;
	
	@Column(name = "name", length=350, nullable=false)
	private String name;
	
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Column(name = "approve_status", length=20, nullable=false)
	private String approveStatus;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lending_workflow_id", nullable=true)
	private LendingWorkflow lendingWorkflow;
	
	@Column(name = "pcreated_user", length=255, nullable=false)
	private String penCreatedUser;
	
	@Column(name = "pcreated_date", nullable=false)
	private Timestamp penCreatedDate;
	
	@Column(name = "updated", nullable=false)
	private Boolean updated;
	
//	@Column(name = "pmodified_user", length=255, nullable=true)
//	private String penModifiedUser;
//	
//	@Column(name = "pmodified_date", nullable=true)
//	private Timestamp penModifiedDate;
//	
//	@Column(name = "papproved_user", length=255, nullable=false)
//	private String approvedUser;
//	
//	@Column(name = "papproved_date", nullable=false)
//	private Timestamp approvedDate;
//	
//	@Column(name = "prejected_user", length=255, nullable=true)
//	private String rejectedUser;
//	
//	@Column(name = "prejected_date", nullable=true)
//	private Timestamp rejectedDate;

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

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}
	
//	public void setStatus(String status) {
//		this.status = CommonStatus.valueOf(status);
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
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

	public InterestTemplate getInterestTemplate() {
		return interestTemplate;
	}

	public void setInterestTemplate(InterestTemplate interestTemplate) {
		this.interestTemplate = interestTemplate;
	}

	public LendingWorkflow getLendingWorkflow() {
		return lendingWorkflow;
	}

	public void setLendingWorkflow(LendingWorkflow lendingWorkflow) {
		this.lendingWorkflow = lendingWorkflow;
	}

//	public String getPenModifiedUser() {
//		return penModifiedUser;
//	}
//
//	public void setPenModifiedUser(String penModifiedUser) {
//		this.penModifiedUser = penModifiedUser;
//	}
//
//	public Timestamp getPenModifiedDate() {
//		return penModifiedDate;
//	}
//
//	public void setPenModifiedDate(Timestamp penModifiedDate) {
//		this.penModifiedDate = penModifiedDate;
//	}
//
//	public String getApprovedUser() {
//		return approvedUser;
//	}
//
//	public void setApprovedUser(String approvedUser) {
//		this.approvedUser = approvedUser;
//	}
//
//	public Timestamp getApprovedDate() {
//		return approvedDate;
//	}
//
//	public void setApprovedDate(Timestamp approvedDate) {
//		this.approvedDate = approvedDate;
//	}
//
//	public String getRejectedUser() {
//		return rejectedUser;
//	}
//
//	public void setRejectedUser(String rejectedUser) {
//		this.rejectedUser = rejectedUser;
//	}
//
//	public Timestamp getRejectedDate() {
//		return rejectedDate;
//	}
//
//	public void setRejectedDate(Timestamp rejectedDate) {
//		this.rejectedDate = rejectedDate;
//	}
	
	public Boolean getUpdated() {
		return updated;
	}

	public void setUpdated(Boolean updated) {
		this.updated = updated;
	}
	
}
