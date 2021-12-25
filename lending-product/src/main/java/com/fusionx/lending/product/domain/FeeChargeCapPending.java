package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * Fee Charge  Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-07-2021      		     			Dilhan      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "fee_charge_cap_pending")
@Data
public class FeeChargeCapPending extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -4956422843237420129L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fee_charge_id", nullable=true)
	private FeeCharge feeCharge;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fee_charge_pending_id", nullable=true)
	private FeeChargePending feeChargePending;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fee_charge_cap_id", nullable = false)
	private FeeChargeCap feeChargeCap;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "other_fee_type_id", nullable=false)
	private OtherFeeType otherFeeType;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lending_workflow_id", nullable=true)
	private LendingWorkflow lendingWorkflow;
	
	@Column(name = "fee_cap_period_id",nullable=false)
	private Long feeCapPeriodId;
	
	@Column(name = "period_code", nullable=false)
	private String periodCode;
	
	@Column(name = "fee_occurence")
	private Long feeOccurence;
	
	@Column(name = "min_max_type", length=3, nullable=false)
	private String minMaxType;
	
	@Column(name="minimum_amount", columnDefinition="Decimal(25,5)", nullable=false)
	private BigDecimal minimumAmount;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable=false, length=20)
	private CommonStatus status;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "approve_status",  nullable=true, length=30)
	private CommonApproveStatus approveStatus;
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "pen_approved_user", nullable=true, length=255)
	private String penApprovedUser;
	
	@Column(name = "pen_approved_date", nullable=true)
	private Timestamp penApprovedDate;
	
	@Column(name = "pen_rejected_user", nullable=true, length=255)
	private String penRejectedUser;
	
	@Column(name = "pen_rejected_date", nullable=true)
	private Timestamp penRejectedDate;
	
	public FeeChargePending getFeeChargePending() {
		return feeChargePending;
	}

	public void setFeeChargePending(FeeChargePending feeChargePending) {
		this.feeChargePending = feeChargePending;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getMinMaxType() {
		return minMaxType;
	}
	
	public void setMinMaxType(String minMaxType) {
		this.minMaxType = minMaxType;
	}
	public Long getFeeCapPeriodId() {
		return feeCapPeriodId;
	}
	
	public void setFeeCapPeriodId(Long feeCapPeriodId) {
		this.feeCapPeriodId = feeCapPeriodId;
	}
	public Long getFeeOccurence() {
		return feeOccurence;
	}
	
	public void setFeeOccurence(Long feeOccurence) {
		this.feeOccurence = feeOccurence;
	}

	public BigDecimal getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(BigDecimal minimumAmount) {
		this.minimumAmount = minimumAmount;
	}
	public FeeCharge getFeeCharge() {
		return feeCharge;
	}

	public void setFeeCharge(FeeCharge feeCharge) {
		this.feeCharge = feeCharge;
	}
	public FeeChargeCap getFeeChargeCap() {
		return feeChargeCap;
	}
	
	public void setFeeChargeCap(FeeChargeCap feeChargeCap) {
		this.feeChargeCap = feeChargeCap;
	}

	public CommonApproveStatus getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(CommonApproveStatus approveStatus) {
		this.approveStatus = approveStatus;
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
	
	public LendingWorkflow getLendingWorkflow() {
		return lendingWorkflow;
	}

	public void setLendingWorkflow(LendingWorkflow lendingWorkflow) {
		this.lendingWorkflow = lendingWorkflow;
	}

	public OtherFeeType getOtherFeeType() {
		return otherFeeType;
	}

	public void setOtherFeeType(OtherFeeType otherFeeType) {
		this.otherFeeType = otherFeeType;
	}

	public String getPeriodCode() {
		return periodCode;
	}

	public void setPeriodCode(String periodCode) {
		this.periodCode = periodCode;
	}
	
}	
