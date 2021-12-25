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
import com.fusionx.lending.product.enums.InterestCalculationMethod;
import com.fusionx.lending.product.enums.PenalTierBandMethod;
import com.fusionx.lending.product.enums.RateCalculationMethod;
import com.fusionx.lending.product.enums.YesNo;

import lombok.Data;

@Entity
@Table(name = "penal_tier_band_set_pending")
//@Data
public class PenalTierBandSetPending extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1130097236713955619L;

	@Column(name = "code",  nullable=false)
	private String code;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "penal_interest_id", nullable=true)
	private PenalInterest penalInterest;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "penal_tier_band_set_id", nullable = true)
	private PenalTierBandSet penalTierBandSet;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "penal_interest_pending_id", nullable = false)
	private PenalInterestPending penalInterestPending;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "penal_interest_type_id", nullable=true)
	private PenalInterestType penalInterestType;
		
	@Enumerated(value = EnumType.STRING)
	@Column(name = "tier_band_method",  nullable=true)
	private PenalTierBandMethod tierBandMethod;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "rate_calculation_method",  nullable=true)
	private RateCalculationMethod rateCalculationMethod;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "interest_calculation_method",  nullable=true)
	private InterestCalculationMethod interestCalculationMethod;
	
	@Column(name = "grace_period_length",  nullable=true)
	private Long  gracePeriodLength;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Column(name = "created_user", length=20, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "notes", length=255, nullable=true)
	private String note;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "approve_status",  nullable=true, length=30)
	private CommonApproveStatus approveStatus;
	
	@Column(name = "approved_user", nullable=true, length=255)
	private String approvedUser;
	
	@Column(name = "approved_date", nullable=true)
	private Timestamp approvedDate;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "updated_status", nullable=false)
	private YesNo updated;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lending_workflow_id", nullable=true)
	private LendingWorkflow lendingWorkflow;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public PenalInterest getPenalInterest() {
		return penalInterest;
	}

	public void setPenalInterest(PenalInterest penalInterest) {
		this.penalInterest = penalInterest;
	}

	public PenalTierBandSet getPenalTierBandSet() {
		return penalTierBandSet;
	}

	public void setPenalTierBandSet(PenalTierBandSet penalTierBandSet) {
		this.penalTierBandSet = penalTierBandSet;
	}

	public PenalInterestPending getPenalInterestPending() {
		return penalInterestPending;
	}

	public void setPenalInterestPending(PenalInterestPending penalInterestPending) {
		this.penalInterestPending = penalInterestPending;
	}

	public PenalInterestType getPenalInterestType() {
		return penalInterestType;
	}

	public void setPenalInterestType(PenalInterestType penalInterestType) {
		this.penalInterestType = penalInterestType;
	}

	public Long getGracePeriodLength() {
		return gracePeriodLength;
	}

	public void setGracePeriodLength(Long gracePeriodLength) {
		this.gracePeriodLength = gracePeriodLength;
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

	public void setStatus(CommonStatus commonStatus) {
		this.status = commonStatus;
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

	public YesNo getUpdated() {
		return updated;
	}

	public void setUpdated(YesNo updated) {
		this.updated = updated;
	}

	public LendingWorkflow getLendingWorkflow() {
		return lendingWorkflow;
	}

	public void setLendingWorkflow(LendingWorkflow lendingWorkflow) {
		this.lendingWorkflow = lendingWorkflow;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public PenalTierBandMethod getTierBandMethod() {
		return tierBandMethod;
	}

	public void setTierBandMethod(PenalTierBandMethod tierBandMethod) {
		this.tierBandMethod = tierBandMethod;
	}

	public RateCalculationMethod getRateCalculationMethod() {
		return rateCalculationMethod;
	}

	public void setRateCalculationMethod(RateCalculationMethod rateCalculationMethod) {
		this.rateCalculationMethod = rateCalculationMethod;
	}

	public InterestCalculationMethod getInterestCalculationMethod() {
		return interestCalculationMethod;
	}

	public void setInterestCalculationMethod(InterestCalculationMethod interestCalculationMethod) {
		this.interestCalculationMethod = interestCalculationMethod;
	}

}
