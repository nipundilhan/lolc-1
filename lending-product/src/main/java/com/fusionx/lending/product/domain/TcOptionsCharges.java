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
import com.fusionx.lending.product.enums.RateOrAmount;

/**
 * Tc Options Charges
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   06-10-2021                       	FXL-994	   Dilhan       Created
 *    
 *******************************************************************************************************
 */

@Entity
@Table(name = "tc_options_charges")
//@Data
public class TcOptionsCharges extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 2787793242997708759L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "rate_or_amount", length=50, nullable=false)
	private RateOrAmount rateOrAmount;
	
	@Column(name = "rate_amount", columnDefinition="Decimal(38,2)", nullable=false)
	private BigDecimal rateAmount;
	
	@Column(name = "total_charge_amount", columnDefinition="Decimal(38,2)", nullable=false)
	private BigDecimal totalChargeAmount;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "applicaction_frequency_id", nullable=true)
	private ApplicationFrequency criteria;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "tc_header_id", nullable=true)
	private TcHeader tcHeader;
	
	@Column(name = "selected_option", length=50, nullable=false)
	private String option;
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "charge_type_id", nullable=true)
	private FeeChargeDetails feeChargeDetails;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "optional_charge_type_id", nullable=true)
	private FeeChargeDetailOptional feeChargeDetailOptional;

	public FeeChargeDetailOptional getFeeChargeDetailOptional() {
		return feeChargeDetailOptional;
	}

	public void setFeeChargeDetailOptional(FeeChargeDetailOptional feeChargeDetailOptional) {
		this.feeChargeDetailOptional = feeChargeDetailOptional;
	}

	public FeeChargeDetails getFeeChargeDetails() {
		return feeChargeDetails;
	}

	public void setFeeChargeDetails(FeeChargeDetails feeChargeDetails) {
		this.feeChargeDetails = feeChargeDetails;
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

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public RateOrAmount getRateOrAmount() {
		return rateOrAmount;
	}

	public void setRateOrAmount(RateOrAmount rateOrAmount) {
		this.rateOrAmount = rateOrAmount;
	}

	public BigDecimal getRateAmount() {
		return rateAmount;
	}

	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
	}

	public BigDecimal getTotalChargeAmount() {
		return totalChargeAmount;
	}

	public void setTotalChargeAmount(BigDecimal totalChargeAmount) {
		this.totalChargeAmount = totalChargeAmount;
	}

	public ApplicationFrequency getCriteria() {
		return criteria;
	}

	public void setCriteria(ApplicationFrequency criteria) {
		this.criteria = criteria;
	}

	public TcHeader getTcHeader() {
		return tcHeader;
	}

	public void setTcHeader(TcHeader tcHeader) {
		this.tcHeader = tcHeader;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
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
