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
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;

/**
 * Eligibility Pending Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-06-2021      		     			MenukaJ      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "eligibility_pending")
@Data
public class EligibilityPending extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6570160972603385840L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "eligibility_id", nullable=true)
	private Eligibility eligibility;
	
	@Column(name = "code", length=4, nullable=false)
	private String code;
	
	@Column(name = "name", length=70, nullable=false)
	private String name;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "age_eligibility_id", nullable=true)
	private AgeEligibility ageEligibility;
	
	@Column(name = "guarantor_eligibility", nullable=false)
	private Long guarantorEligibility;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable=false, length=20)
	private CommonStatus status;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lending_workflow_id", nullable=true)
	private LendingWorkflow lendingWorkflow;
	
	@Column(name = "pcreated_user", length=255, nullable=false)
	private String pcreatedUser;
	
	@Column(name = "pcreated_date", nullable=false)
	private Timestamp pcreatedDate;
	
	@Transient
    private Long workflowId;
	
	@Transient
    private Long workflowProcessId;
	
	@Transient
	private String workflowStatus;
	
	@Transient
	private Long eligiId;
	
	@Transient
    private Long ageEligibilityId;
	
	@Transient
	private String approveStatus;


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


	public AgeEligibility getAgeEligibility() {
		return ageEligibility;
	}

	public void setAgeEligibility(AgeEligibility ageEligibility) {
		this.ageEligibility = ageEligibility;
	}

	public Long getGuarantorEligibility() {
		return guarantorEligibility;
	}

	public void setGuarantorEligibility(Long guarantorEligibility) {
		this.guarantorEligibility = guarantorEligibility;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public Eligibility getEligibility() {
		return eligibility;
	}

	public void setEligibility(Eligibility eligibility) {
		this.eligibility = eligibility;
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
		if(lendingWorkflow != null) {
			return lendingWorkflow.getId();
		} else {
			return null;
		}
	}

	public Long getWorkflowProcessId() {
		if(lendingWorkflow != null) {
			return lendingWorkflow.getWorkflowProcessId();
		} else {
			return null;
		}
	}

	public String getWorkflowStatus() {
		if(lendingWorkflow != null) {
			return lendingWorkflow.getWorkflowStatus().toString();
		} else {
			return null;
		}
	}

	public Long getEligiId() {
		if(lendingWorkflow != null) {
			return eligibility.getId();
		} else {
			return null;
		}
	}

	public String getApproveStatus() {
		if(lendingWorkflow != null && eligibility.getApproveStatus() != null) {
			return eligibility.getApproveStatus().toString();
		} else {
			return null;
		}
	}

	public Long getAgeEligibilityId() {
		if(ageEligibility != null) {
			return ageEligibility.getId();
		} else {
			return null;
		}
	}

}	
