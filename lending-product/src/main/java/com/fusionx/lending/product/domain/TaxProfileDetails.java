package com.fusionx.lending.product.domain;

/**
 * Tax Profile 
 * @author 	Dilki
 * @Date     05-11-2019
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-10-2021   FX-1234       FX-1132    Dilki      Created
 *    
 ********************************************************************************************************
 */
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
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity
@Table(name = "tax_profile_details")
//@Data
public class TaxProfileDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 8387109435900549230L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "tax_profile_id", nullable = false)
	private TaxProfile taxProfileId;

	@Column(name = "tax_amount", nullable = true)
	private BigDecimal taxAmount;

	@Column(name = "tax_rate", nullable = true)
	private BigDecimal taxRate;

	@Column(name = "tier_min_val", nullable = true)
	private BigDecimal tierMinValue;

	@Column(name = "tier_max_val", nullable = true)
	private BigDecimal tierMaxValue;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private CommonStatus status;

	@Column(name = "created_user", length = 20, nullable = false)
	private String createdUser;

	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;

	@Column(name = "modified_user", length = 20, nullable = true)
	private String modifiedUser;

	@Column(name = "modified_date", nullable = true)
	private Timestamp modifiedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public TaxProfile getTaxProfileId() {
		return taxProfileId;
	}

	public void setTaxProfileId(TaxProfile taxProfileId) {
		this.taxProfileId = taxProfileId;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
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

	public BigDecimal getTierMinValue() {
		return tierMinValue;
	}

	public void setTierMinValue(BigDecimal tierMinValue) {
		this.tierMinValue = tierMinValue;
	}

	public BigDecimal getTierMaxValue() {
		return tierMaxValue;
	}

	public void setTierMaxValue(BigDecimal tierMaxValue) {
		this.tierMaxValue = tierMaxValue;
	}

}
