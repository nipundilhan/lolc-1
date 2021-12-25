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
 * Interest Tier Band Pending 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   22-07-2021    FXL_July_2021_2  	FXL-53		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Entity
@Table(name = "interest_tier_band_pending")
@Data
public class InterestTierBandPending  extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1362108144602383341L;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "interest_tier_band_id", nullable=false)
	private InterestTierBand interestTierBand;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "interest_tier_band_set_id", nullable=false)
	private InterestTierBandSet interestTierBandSet;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "interest_tier_band_set_pend_id", nullable=false)
	private InterestTierBandSetPending interestTierBandSetPending;
	
	@JoinColumn(name = "tier_value_minimum", nullable=false)
	private BigDecimal tierValueMinimum;
	
	@JoinColumn(name = "tier_value_maximum", nullable = false)
	private BigDecimal tierValueMaximum;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "tier_value_min_term_id", nullable=true)
	private CommonListItem tierValueMinTermId;
	
	@Column(name = "min_term_period_id", nullable=true)
	private Long minTermPeriodId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "tier_value_max_term_id", nullable=true)
	private CommonListItem tierValueMaxTermId;
	
	@Column(name = "max_term_period_id", nullable=true)
	private Long maxTermPeriodId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "interest_rate_type_id", nullable=true)
	private CommonListItem interestRateTypeId;
	
	@JoinColumn(name = "rep_arp", nullable=true)
	private BigDecimal repArp;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "loan_pr_interest_rate_type_id", nullable=true)
	private InterestRateType loanPrInterestRateType;
	
	@JoinColumn(name = "loan_pr_interest_rate", nullable = true)
	private BigDecimal loanPrInterestRate;
	
	@Column(name = "note", length=255, nullable=true)
	private String note;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
		
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
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
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public InterestTierBand getInterestTierBand() {
		return interestTierBand;
	}

	public void setInterestTierBand(InterestTierBand interestTierBand) {
		this.interestTierBand = interestTierBand;
	}
	
	public InterestTierBandSet getInterestTierBandSet() {
		return interestTierBandSet;
	}

	public void setInterestTierBandSet(InterestTierBandSet interestTierBandSet) {
		this.interestTierBandSet = interestTierBandSet;
	}
	
	public InterestTierBandSetPending getInterestTierBandSetPending() {
		return interestTierBandSetPending;
	}

	public void setInterestTierBandSetPending(InterestTierBandSetPending interestTierBandSetPending) {
		this.interestTierBandSetPending = interestTierBandSetPending;
	}
	
	public BigDecimal getTierValueMinimum() {
		return tierValueMinimum;
	}

	public void setTierValueMinimum(BigDecimal tierValueMinimum) {
		this.tierValueMinimum = tierValueMinimum;
	}
	
	public BigDecimal getTierValueMaximum() {
		return tierValueMaximum;
	}

	public void setTierValueMaximum(BigDecimal tierValueMaximum) {
		this.tierValueMaximum = tierValueMaximum;
	}
	
	public CommonListItem getTierValueMinTermId() {
		return tierValueMinTermId;
	}

	public void setTierValueMinTermId(CommonListItem tierValueMinTermId) {
		this.tierValueMinTermId = tierValueMinTermId;
	}
	
	public Long getMinTermPeriodId() {
		return minTermPeriodId;
	}

	public void setMinTermPeriodId(Long minTermPeriodId) {
		this.minTermPeriodId = minTermPeriodId;
	}
	
	public CommonListItem getTierValueMaxTermId() {
		return tierValueMaxTermId;
	}

	public void setTierValueMaxTermId(CommonListItem tierValueMaxTermId) {
		this.tierValueMaxTermId = tierValueMaxTermId;
	}
	
	public Long getMaxTermPeriodId() {
		return maxTermPeriodId;
	}

	public void setMaxTermPeriodId(Long maxTermPeriodId) {
		this.maxTermPeriodId = maxTermPeriodId;
	}
	
	public CommonListItem getInterestRateTypeId() {
		return interestRateTypeId;
	}

	public void setInterestRateTypeId(CommonListItem interestRateTypeId) {
		this.interestRateTypeId = interestRateTypeId;
	}
	
	public InterestRateType getLoanPrInterestRateType() {
		return loanPrInterestRateType;
	}

	public void setLoanPrInterestRateType(InterestRateType loanPrInterestRateType) {
		this.loanPrInterestRateType = loanPrInterestRateType;
	}
	
	public BigDecimal getRepArp() {
		return repArp;
	}

	public void setRepArp(BigDecimal repArp) {
		this.repArp = repArp;
	}
	
	public BigDecimal getLoanPrInterestRate() {
		return loanPrInterestRate;
	}

	public void setLoanPrInterestRate(BigDecimal loanPrInterestRate) {
		this.loanPrInterestRate = loanPrInterestRate;
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

}
