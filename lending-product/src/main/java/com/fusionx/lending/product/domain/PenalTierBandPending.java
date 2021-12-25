package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

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
import com.fusionx.lending.product.enums.FixedVariableType;
import com.fusionx.lending.product.enums.YesNo;

import lombok.Data;

@Entity
@Data
@Table(name = "penal_tier_band_pending")
public class PenalTierBandPending extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -4785272315921972662L;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "penal_tier_band_id", nullable=true)
	private PenalTierBand penalTierBand;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "penal_tier_band_set_id", nullable=false)
	private PenalTierBandSet penalTierBandSet;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "penal_tier_band_set_pending_id", nullable=false)
	private PenalTierBandSetPending penalTierBandSetPending;
	
	@Column(name = "code",  nullable=false)
	private String code;
	
	@Column(name = "effective_date",  nullable=false)
	private Date effectiveDate;
	
	@Column(name = "tier_value_minimum", columnDefinition="Decimal(25,5)", nullable=false)
	private BigDecimal tierValueMinimum;
	
	@Column(name = "tier_value_maximum", columnDefinition="Decimal(25,5)", nullable=false)
	private BigDecimal tierValueMaximum;
	
	@Column(name = "loan_provider_int_rate",  nullable=true)
	private String loanProviderIntRate;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "loan_provider_interest_type_id", nullable=true)
	private InterestRateType interestRateType;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "fixed_vari_int_rate_type")
	private FixedVariableType  fixedVariableTypeValue;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Column(name = "aer",  columnDefinition="Decimal(25,5)", nullable=false)
	private BigDecimal aer;
	
	@Column(name = "created_user", length=20, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "notes", length=255, nullable=true)
	private String note;
	

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
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "updated_status", nullable=false)
	private YesNo updated;

	public PenalTierBand getPenalTierBand() {
		return penalTierBand;
	}

	public void setPenalTierBand(PenalTierBand penalTierBand) {
		this.penalTierBand = penalTierBand;
	}

	public PenalTierBandSet getPenalTierBandSet() {
		return penalTierBandSet;
	}

	public void setPenalTierBandSet(PenalTierBandSet penalTierBandSet) {
		this.penalTierBandSet = penalTierBandSet;
	}

	public PenalTierBandSetPending getPenalTierBandSetPending() {
		return penalTierBandSetPending;
	}

	public void setPenalTierBandSetPending(PenalTierBandSetPending penalTierBandSetPending) {
		this.penalTierBandSetPending = penalTierBandSetPending;
	}

	public BigDecimal getTierValueMaximum() {
		return tierValueMaximum;
	}

	public void setTierValueMaximum(BigDecimal tierValueMaximum) {
		this.tierValueMaximum = tierValueMaximum;
	}

	public FixedVariableType getFixedVariableTypeValue() {
		return fixedVariableTypeValue;
	}

	public void setFixedVariableTypeValue(FixedVariableType fixedVariableTypeValue) {
		this.fixedVariableTypeValue = fixedVariableTypeValue;
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
	
	public YesNo getUpdated() {
		return updated;
	}

	public void setUpdated(YesNo updated) {
		this.updated = updated;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public String getLoanProviderIntRate() {
		return loanProviderIntRate;
	}

	public void setLoanProviderIntRate(String loanProviderIntRate) {
		this.loanProviderIntRate = loanProviderIntRate;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public BigDecimal getTierValueMinimum() {
		return tierValueMinimum;
	}

	public void setTierValueMinimum(BigDecimal tierValueMinimum) {
		this.tierValueMinimum = tierValueMinimum;
	}

	public InterestRateType getInterestRateType() {
		return interestRateType;
	}

	public void setInterestRateType(InterestRateType interestRateType) {
		this.interestRateType = interestRateType;
	}

	public BigDecimal getAer() {
		return aer;
	}

	public void setAer(BigDecimal aer) {
		this.aer = aer;
	}


}
