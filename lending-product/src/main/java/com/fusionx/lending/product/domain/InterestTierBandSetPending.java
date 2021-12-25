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
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;

/**
 * InterestTierBandSetPending 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   19-07-2021    FXL_July_2021_2  	FXL-52		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Entity
@Table(name = "interest_tier_band_set_pending")
@Data
public class InterestTierBandSetPending extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -2525891654121401609L;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "interest_tier_band_set_id", nullable = true)
	private InterestTierBandSet interestTierBandSet;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "tier_band_method_id", nullable=false)
	private CommonListItem tierBandMethod;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "calculation_method_id", nullable=false)
	private CommonListItem calculationMethod;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "interest_template_id", nullable = false)
	private InterestTemplate interestTemplate;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "interest_template_pending_id", nullable = false)
	private InterestTemplatePending interestTemplatePending;
	
	@Column(name = "note", length=255, nullable=true)
	private String note;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lending_workflow_id", nullable=true)
	private LendingWorkflow lendingWorkflow;
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "approve_status",  nullable=true, length=30)
	private CommonApproveStatus approveStatus;
	
	@Column(name = "approved_user", nullable=true, length=255)
	private String approvedUser;
	
	@Column(name = "approved_date", nullable=true)
	private Timestamp approvedDate;
	
	@Column(name = "updated", nullable=false)
	private Boolean updated;
	
	public InterestTierBandSet getInterestTierBandSet() {
		return interestTierBandSet;
	}

	public void setInterestTierBandSet(InterestTierBandSet interestTierBandSet) {
		this.interestTierBandSet = interestTierBandSet;
	}
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public CommonListItem getTierBandMethod() {
		return tierBandMethod;
	}

	public void setTierBandMethod(CommonListItem tierBandMethod) {
		this.tierBandMethod = tierBandMethod;
	}
	
	public CommonListItem getCalculationMethod() {
		return calculationMethod;
	}

	public void setCalculationMethod(CommonListItem calculationMethod) {
		this.calculationMethod = calculationMethod;
	}
	
	public InterestTemplate getInterestTemplate() {
		return interestTemplate;
	}

	public void setInterestTemplate(InterestTemplate interestTemplate) {
		this.interestTemplate = interestTemplate;
	}
	
	public InterestTemplatePending getInterestTemplatePending() {
		return interestTemplatePending;
	}

	public void setInterestTemplatePending(InterestTemplatePending interestTemplatePending) {
		this.interestTemplatePending = interestTemplatePending;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
	
	public LendingWorkflow getLendingWorkflow() {
		return lendingWorkflow;
	}

	public void setLendingWorkflow(LendingWorkflow lendingWorkflow) {
		this.lendingWorkflow = lendingWorkflow;
	}
	
	public Boolean getUpdated() {
		return updated;
	}

	public void setUpdated(Boolean updated) {
		this.updated = updated;
	}

}

