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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.core.MasterPropertyBase;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;


/**
 * EligibilityResidencyEligibilityPending
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   28-07-2021    FXL_July_2021_2  	FXL-55		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Entity
@Table(name = "eligibility_residency_eli_pend")
@JsonIgnoreProperties({MasterPropertyBase.HIBERNATE_LAZY_INITIALIZER, MasterPropertyBase.JSON_INITIALIZER_HANDLER})
public class EligibilityResidencyEligibilityPending extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -7087204095346878892L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "eli_residency_eligibility_id", nullable = true)
	private EligibilityResidencyEligibility eliResidencyEligibility;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lending_workflow_id", nullable=true)
	private LendingWorkflow lendingWorkflow;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "eligibility_id", nullable = false)
	private Eligibility eligibility;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "eligibility_pending_id", nullable = false)
	private EligibilityPending eligibilityPending;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "residency_eligibility_id", nullable = false)
	private ResidencyEligibility residencyEligibility;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;	
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "approved_user", nullable=true, length=255)
	private String approvedUser;
	
	@Column(name = "approved_date", nullable=true)
	private Timestamp approvedDate;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "approve_status",  nullable=true, length=30)
	private CommonApproveStatus approveStatus;
	
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

	public EligibilityResidencyEligibility getEliResidencyEligibility() {
		return eliResidencyEligibility;
	}

	public void setEliResidencyEligibility(EligibilityResidencyEligibility eliResidencyEligibility) {
		this.eliResidencyEligibility = eliResidencyEligibility;
	}

	public Eligibility getEligibility() {
		return eligibility;
	}

	public void setEligibility(Eligibility eligibility) {
		this.eligibility = eligibility;
	}

	public EligibilityPending getEligibilityPending() {
		return eligibilityPending;
	}

	public void setEligibilityPending(EligibilityPending eligibilityPending) {
		this.eligibilityPending= eligibilityPending;
	}

	public ResidencyEligibility getResidencyEligibility() {
		return residencyEligibility;
	}

	public void setResidencyEligibility(ResidencyEligibility residencyEligibility) {
		this.residencyEligibility = residencyEligibility;
	}
}
