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
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;

/**
 * Fee Charge Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   28-07-2021      		     			Dilhan      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "fee_charge_details_pending")
//@Data
public class FeeChargeDetailsPending extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -6804924148044897726L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fee_charge_pending_id", nullable=true)
	private FeeChargePending feeChargePending;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fee_charge_details_id", nullable=true)
	private FeeChargeDetails feeChargeDetails;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fee_category_id", nullable=true)
	private CommonListItem feeCategory;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "applicaction_frequency_id", nullable=true)
	private ApplicationFrequency applicationFrequency;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "calculation_frequency_id", nullable=true)
	private CalculationFrequency calculationFrequency;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fee_type_id", nullable=true)
	private OtherFeeType otherFeeType;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "rate_type_id", nullable=true)
	private OtherFeeRateType rateType;
	
	@Column(name = "is_negotiable", nullable=false)
	private String isNegotiable;
	
	@Column(name = "type", length=3, nullable=false)
	private String type;
	
	@Column(name="amount", columnDefinition="Decimal(25,5)", nullable=false)
	private BigDecimal amount;
	
	@Column(name = "max_amount", columnDefinition="Decimal(25,5)", nullable=false)
	private BigDecimal maxAmount;
	
	@Column(name = "min_amount", columnDefinition="Decimal(25,5)", nullable=false)
	private BigDecimal minAmount;
	
	@Column(name = "rate", columnDefinition="Decimal(25,5)", nullable=false)
	private BigDecimal rate;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable=false, length=20)
	private CommonStatus status;
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "effective_date", nullable=true)
	private Timestamp effectiveDate;

	public Timestamp getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public FeeChargeDetails getFeeChargeDetails() {
		return feeChargeDetails;
	}

	public void setFeeChargeDetails(FeeChargeDetails feeChargeDetails) {
		this.feeChargeDetails = feeChargeDetails;
	}
	
	public FeeChargePending getFeeChargePending() {
		return feeChargePending;
	}

	public void setFeeChargePending(FeeChargePending feeChargePending) {
		this.feeChargePending = feeChargePending;
	}

	public CommonListItem getFeeCategory() {
		return feeCategory;
	}

	public void setFeeCategory(CommonListItem feeCategory) {
		this.feeCategory = feeCategory;
	}

	public ApplicationFrequency getApplicationFrequency() {
		return applicationFrequency;
	}

	public void setApplicationFrequency(ApplicationFrequency applicationFrequency) {
		this.applicationFrequency = applicationFrequency;
	}

	public CalculationFrequency getCalculationFrequency() {
		return calculationFrequency;
	}

	public void setCalculationFrequency(CalculationFrequency calculationFrequency) {
		this.calculationFrequency = calculationFrequency;
	}

	public OtherFeeType getOtherFeeType() {
		return otherFeeType;
	}

	public void setOtherFeeType(OtherFeeType otherFeeType) {
		this.otherFeeType = otherFeeType;
	}

	public OtherFeeRateType getRateType() {
		return rateType;
	}

	public void setRateType(OtherFeeRateType rateType) {
		this.rateType = rateType;
	}

	public String getIsNegotiable() {
		return isNegotiable;
	}

	public void setIsNegotiable(String isNegotiable) {
		this.isNegotiable = isNegotiable;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
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
}
