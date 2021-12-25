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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity; 
import com.fusionx.lending.product.enums.CommonStatus;
/**
Eligibility Template Legal Structure
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   21-07-2019   FXL-1         FXL-42     Dilki        Created
*    
********************************************************************************************************
*/

@Entity
@Table(name = "eligibility_disbursement_pend")
public class EligibilityTemplateDisbursementPending extends BaseEntity implements Serializable {

	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}

	public void setWorkflowProcessId(Long workflowProcessId) {
		this.workflowProcessId = workflowProcessId;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	private static final long serialVersionUID = -572853915840194081L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "eligibility_disbursement_id", nullable = true)
	private EligibilityTemplateDisbursement eligibilityDisbursmentId;

//	@JsonIgnore
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "eligibility_id", nullable = false)
	private Long eligibilityId;

	@Column(name = "eligibility_name", nullable = true, length = 20)
	private String eligibilityName;

	@Column(name = "disbursement_id", nullable = false)
	private String disbursementId;

	@Column(name = "disbursement_name", nullable = true, length = 20)
	private String disbursementName;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private CommonStatus status;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lending_workflow_id", nullable = true)
	private LendingWorkflow lendingWorkflow;

	@Column(name = "pcreated_user", length = 255, nullable = false)
	private String pcreatedUser;

	@Column(name = "pcreated_date", nullable = false)
	private Timestamp pcreatedDate;

	@Transient
	private Long workflowId;

	@Transient
	private Long workflowProcessId;

	@Transient
	private String workflowStatus;

	@Transient
	private String approveStatus;

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

	public String getPcreatedUser() {
		return pcreatedUser;
	}

	public void setPcreatedUser(String pcreatedUser) {
		this.pcreatedUser = pcreatedUser;
	}

	public Timestamp getPcreatedDate() {
		return pcreatedDate;
	}

	public void setPcreatedDate(Timestamp pcreatedDate) {
		this.pcreatedDate = pcreatedDate;
	}

	public LendingWorkflow getLendingWorkflow() {
		return lendingWorkflow;
	}

	public void setLendingWorkflow(LendingWorkflow lendingWorkflow) {
		this.lendingWorkflow = lendingWorkflow;
	}

	public Long getWorkflowId() {
		if (lendingWorkflow != null) {
			return lendingWorkflow.getId();
		} else {
			return null;
		}
	}

	public Long getWorkflowProcessId() {
		if (lendingWorkflow != null) {
			return lendingWorkflow.getWorkflowProcessId();
		} else {
			return null;
		}
	}

	public String getWorkflowStatus() {
		if (lendingWorkflow != null) {
			return lendingWorkflow.getWorkflowStatus().toString();
		} else {
			return null;
		}
	}

	public String getApproveStatus() {
		if (eligibilityDisbursmentId != null && eligibilityDisbursmentId.getApproveStatus() != null) {
			return eligibilityDisbursmentId.getApproveStatus().toString();
		} else {
			return null;
		}
	}

	public EligibilityTemplateDisbursement getEligibilityDisbursmentId() {
		return eligibilityDisbursmentId;
	}

	public void setEligibilityDisbursmentId(EligibilityTemplateDisbursement eligibilityDisbursmentId) {
		this.eligibilityDisbursmentId = eligibilityDisbursmentId;
	}

	public Long getEligibilityId() {
		return eligibilityId;
	}

	public void setEligibilityId(Long eligibilityId) {
		this.eligibilityId = eligibilityId;
	}

	public String getEligibilityName() {
		return eligibilityName;
	}

	public void setEligibilityName(String eligibilityName) {
		this.eligibilityName = eligibilityName;
	}

	public String getDisbursementId() {
		return disbursementId;
	}

	public void setDisbursementId(String disbursementId) {
		this.disbursementId = disbursementId;
	}

	public String getDisbursementName() {
		return disbursementName;
	}

	public void setDisbursementName(String disbursementName) {
		this.disbursementName = disbursementName;
	}

}
