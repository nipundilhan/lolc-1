package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;

/**
 * Fee Charge  History Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-08-2021      		     			Dilhan      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "fee_charge_details_history")
@Data
public class FeeChargeDetailsHistory extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 4030501911480503867L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "fee_charge_details_id")
	private Long feeChargeDetailsId;
	
	@Column(name = "fee_charge_id", nullable=false)
	private Long feeChargeId;
	
	@Column(name = "fee_category_id", nullable=false)
	private Long feeCategoryId;
	
	@Column(name = "applicaction_frequency_id", nullable=false)
	private Long applicationFrequencyId;
	
	@Column(name = "calculation_frequency_id", nullable=false)
	private Long calculationFrequencyId;
	
	@Column(name = "fee_type_id", nullable=false)
	private Long otherFeeTypeId;
	
	@Column(name = "rate_type_id", nullable=true)
	private Long rateTypeId;
	
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
	
	@Column(name="his_created_user", length=255, nullable=false)
	private String historyCreatedUser;
	
	@Column(name="his_created_date", nullable=false)
	private Timestamp historyCreatedDate;
	
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

	public Long getFeeChargeId() {
		return feeChargeId;
	}

	public void setFeeChargeId(Long feeChargeId) {
		this.feeChargeId = feeChargeId;
	}

	public Long getFeeCategoryId() {
		return feeCategoryId;
	}

	public void setFeeCategoryId(Long feeCategoryId) {
		this.feeCategoryId = feeCategoryId;
	}

	public Long getApplicationFrequencyId() {
		return applicationFrequencyId;
	}

	public void setApplicationFrequencyId(Long applicationFrequencyId) {
		this.applicationFrequencyId = applicationFrequencyId;
	}

	public Long getCalculationFrequencyId() {
		return calculationFrequencyId;
	}

	public void setCalculationFrequencyId(Long calculationFrequencyId) {
		this.calculationFrequencyId = calculationFrequencyId;
	}

	public Long getOtherFeeTypeId() {
		return otherFeeTypeId;
	}

	public void setOtherFeeTypeId(Long otherFeeTypeId) {
		this.otherFeeTypeId = otherFeeTypeId;
	}

	public Long getRateTypeId() {
		return rateTypeId;
	}

	public void setRateTypeId(Long rateTypeId) {
		this.rateTypeId = rateTypeId;
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

	public String getHistoryCreatedUser() {
		return historyCreatedUser;
	}

	public void setHistoryCreatedUser(String historyCreatedUser) {
		this.historyCreatedUser = historyCreatedUser;
	}

	public Timestamp getHistoryCreatedDate() {
		return historyCreatedDate;
	}

	public void setHistoryCreatedDate(Timestamp historyCreatedDate) {
		this.historyCreatedDate = historyCreatedDate;
	}
	
	public Long getFeeChargeDetailsId() {
		return feeChargeDetailsId;
	}

	public void setFeeChargeDetailsId(Long feeChargeDetailsId) {
		this.feeChargeDetailsId = feeChargeDetailsId;
	}
}
