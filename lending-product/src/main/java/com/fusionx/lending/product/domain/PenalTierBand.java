package com.fusionx.lending.product.domain;

/**
 * Penal Tier Band Service - Domain entity
 * @author 	VenukiR
 * @Date    02-06-2020
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-06-2020   FX-1517       FX-3902    VenukiR       Created
 *    
 ********************************************************************************************************
 */

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.FixedVariableType;

import lombok.Data;

@Entity
@Table(name = "penal_tier_band")
//@Data
public class PenalTierBand extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 3441L;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "penal_tier_band_set_id", nullable=true)
	private PenalTierBandSet penalTierBandSet;
	
	@Column(name = "code",  nullable=false)
	private String code;
	
	@Temporal(TemporalType.DATE)
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
	
	@Column(name = "aer",  columnDefinition="Decimal(25,5)", nullable=false)
	private BigDecimal aer;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Column(name = "created_user", length=20, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", length=20, nullable=true)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;

	@Column(name = "notes", length=255, nullable=true)
	private String note;
	
//	@Transient
//	private String fixedVariableRateType;

	
//	@Transient
//	@JsonInclude(Include.NON_NULL)
//	private List<PenalTierBandNotes> notes;

	public PenalTierBand() {
		super();
	}

	public PenalTierBandSet getPenalTierBandSet() {
		return penalTierBandSet;
	}

	public void setPenalTierBandSet(PenalTierBandSet penalTierBandSet) {
		this.penalTierBandSet = penalTierBandSet;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public BigDecimal getTierValueMaximum() {
		return tierValueMaximum;
	}

	public void setTierValueMaximum(BigDecimal tierValueMaximum) {
		this.tierValueMaximum = tierValueMaximum;
	}

	public BigDecimal getAer() {
		return aer;
	}

	public void setAer(BigDecimal aer) {
		this.aer = aer;
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

	public String getLoanProviderIntRate() {
		return loanProviderIntRate;
	}

	public void setLoanProviderIntRate(String loanProviderIntRate) {
		this.loanProviderIntRate = loanProviderIntRate;
	}

	public InterestRateType getInterestRateType() {
		return interestRateType;
	}

	public void setInterestRateType(InterestRateType interestRateType) {
		this.interestRateType = interestRateType;
	}
}
