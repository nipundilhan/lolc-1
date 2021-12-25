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
import javax.persistence.Transient;

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
@Table(name = "fee_charge_details")
//@Data
public class FeeChargeDetails extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 3411847766935388125L;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fee_charge_id", nullable=false)
	private FeeCharge feeCharge;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fee_category_id", nullable=false)
	private CommonListItem feeCategory;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "applicaction_frequency_id", nullable=false)
	private ApplicationFrequency applicationFrequency;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "calculation_frequency_id", nullable=false)
	private CalculationFrequency calculationFrequency;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fee_type_id", nullable=false)
	private OtherFeeType otherFeeType;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "rate_type_id", nullable=true)
	private OtherFeeRateType rateType;
	
	@Column(name = "code", length=4, nullable=false)
	private String code;
	
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
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;
	
	@Column(name = "effective_date", nullable=true)
	private Timestamp effectiveDate;
	
	@Transient
	private Long feeChargeId;
	
	@Transient
	private Long feeCategoryId;
	
	@Transient
	private Long rateTypeId;
	
	@Transient
	private Long feeTypeId;
	
	@Transient
	private Long applicactionFrequencyId;
	
	@Transient
	private Long calculationFrequencyId;
	
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

	public FeeCharge getFeeCharge() {
		return feeCharge;
	}

	public void setFeeCharge(FeeCharge feeCharge) {
		this.feeCharge = feeCharge;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public Long getFeeChargeId() {
		return feeCharge.getId();
	}
	
	public String getFeeChargeName() {
		return feeCharge.getName();

	}
	
	public Long getFeeCategoryId() {
		return feeCategory.getId();
	}
	
	public String getFeeCategoryName() {
		return feeCategory.getName();

	}
	public Long getRateTypeId() {
		return rateType != null ? rateType.getId() : null;
	}
	
	public String getRateTypeName() {
		return rateType != null ? rateType.getName() : null;
		
	}
	public Long getFeeTypeId() {
		return otherFeeType.getId();
	}
	
	public String getFeeTypeName() {
		return otherFeeType.getName();
		
	}
	public Long getApplicactionFrequencyId() {
		return applicationFrequency.getId();
	}
	
	public String getApplicactionFrequencyName() {
		return applicationFrequency.getName();
		
	}
	public Long getCalculationFrequencyId() {
		return calculationFrequency.getId();
	}
	
	public String getCalculationFrequencyName() {
		return calculationFrequency.getName();
		
	}
}
