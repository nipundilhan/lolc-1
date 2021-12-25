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

@Entity
@Table(name = "fee_charge_det_option_opt_pend")
//@Data
public class FeeChargeDetailOptionalOptionPending  extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 3411847766935388125L;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fee_charge_det_opt_pend_id", nullable=false)
	private FeeChargeDetailOptionalPending feeChargeDetailOptionalPending;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fee_charge_det_opt_option_id", nullable=true)
	private FeeChargeDetailOptionalOption feeChargeDetailOptionalOption;

	@Column(name = "option_value", nullable=true)
	private Long optionValue;

	@Column(name="rate", columnDefinition="Decimal(25,5)", nullable=true)
	private BigDecimal rate;

	@Column(name="amount", columnDefinition="Decimal(25,5)", nullable=true)
	private BigDecimal amount;

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
	
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	
	public FeeChargeDetailOptionalPending getFeeChargeDetailOptionalPending() {
		return feeChargeDetailOptionalPending;
	}

	public void setFeeChargeDetailOptionalPending(FeeChargeDetailOptionalPending feeChargeDetailOptionalPending) {
		this.feeChargeDetailOptionalPending = feeChargeDetailOptionalPending;
	}

	public FeeChargeDetailOptionalOption getFeeChargeDetailOptionalOption() {
		return feeChargeDetailOptionalOption;
	}

	public void setFeeChargeDetailOptionalOption(FeeChargeDetailOptionalOption feeChargeDetailOptionalOption) {
		this.feeChargeDetailOptionalOption = feeChargeDetailOptionalOption;
	}
	
	public Long getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(Long optionValue) {
		this.optionValue = optionValue;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

}
