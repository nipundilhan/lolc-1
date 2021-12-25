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
@Table(name = "fee_charge_cap")
@Data
public class FeeChargeCap extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -3045400763715853560L;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fee_charge_id", nullable=true)
	private FeeCharge feeCharge;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "other_fee_type_id", nullable=false)
	private OtherFeeType otherFeeType;
	
	@Column(name = "code", length=4, nullable=false)
	private String code;
	
	@Column(name = "fee_cap_period_id", nullable=true)
	private Long feeCapPeriodId;
	
	@Column(name = "period_code", nullable=true)
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
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;


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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
	public Long getFeeTypeId() {
		return otherFeeType.getId();
	}
	
	public String getFeeTypeName() {
		return otherFeeType.getName();
		
	}
}	
