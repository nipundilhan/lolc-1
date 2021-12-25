package com.fusionx.lending.transaction.domain;

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
import com.fusionx.lending.transaction.core.BaseEntity;
import com.fusionx.lending.transaction.enums.CommonApproveStatus;
import com.fusionx.lending.transaction.enums.CommonStatus;

import lombok.Data;

@Entity
@Data
@Table(name = "allocation_template_pending")
public class AllocationTemplatePending extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -7559670409169254770L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "code", length=4, nullable=false)
	private String code;
	
	@Column(name = "name", length=70, nullable=false)
	private String name;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "allocation_template_id", nullable=true)
	private AllocationTemplate allocationTemplate;
	
	@Column(name = "loan_account_status_id", nullable=true)
	private Long loanAccountStatusId;
	
	@Column(name = "loan_account_status_code", nullable=true)
	private String loanAccountStatusCode;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lending_workflow_id", nullable=true)
	private LendingWorkflow lendingWorkflow;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "approve_status",  nullable=true, length=30)
	private CommonApproveStatus approveStatus;
	
	@Column(name = "approved_user", nullable=true, length=255)
	private String approvedUser;
	
	@Column(name = "approved_date", nullable=true)
	private Timestamp approvedDate;
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public AllocationTemplate getAllocationTemplate() {
		return allocationTemplate;
	}

	public void setAllocationTemplate(AllocationTemplate allocationTemplate) {
		this.allocationTemplate = allocationTemplate;
	}

	public Long getLoanAccountStatusId() {
		return loanAccountStatusId;
	}

	public void setLoanAccountStatusId(Long loanAccountStatusId) {
		this.loanAccountStatusId = loanAccountStatusId;
	}

	public String getLoanAccountStatusCode() {
		return loanAccountStatusCode;
	}

	public void setLoanAccountStatusCode(String loanAccountStatusCode) {
		this.loanAccountStatusCode = loanAccountStatusCode;
	}

	public LendingWorkflow getLendingWorkflow() {
		return lendingWorkflow;
	}

	public void setLendingWorkflow(LendingWorkflow lendingWorkflow) {
		this.lendingWorkflow = lendingWorkflow;
	}

	public CommonApproveStatus getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(CommonApproveStatus approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getApprovedUser() {
		return approvedUser;
	}

	public void setApprovedUser(String approvedUser) {
		this.approvedUser = approvedUser;
	}

	public Timestamp getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Timestamp approvedDate) {
		this.approvedDate = approvedDate;
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
}
