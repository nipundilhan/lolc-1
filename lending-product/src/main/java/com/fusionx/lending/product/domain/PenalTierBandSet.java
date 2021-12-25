package com.fusionx.lending.product.domain;

/**
 * Penal Interest Service - Domain entity
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
import java.sql.Timestamp;
import java.util.List;

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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.InterestCalculationMethod;
import com.fusionx.lending.product.enums.PenalTierBandMethod;
import com.fusionx.lending.product.enums.RateCalculationMethod;

import lombok.Data;

@Entity
@Table(name = "penal_tier_band_set")
@Data
public class PenalTierBandSet extends BaseEntity implements Serializable {
	
	
	private static final long serialVersionUID = 55555L;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "penal_interest_id", nullable=true)
	private PenalInterest penalInterest;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "penal_interest_type_id", nullable=true)
	private PenalInterestType penalInterestType;
	
	@Column(name = "code",  nullable=true)
	private String code;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "tier_band_method",  nullable=true)
	private PenalTierBandMethod tierBandMethod;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "rate_calculation_method",  nullable=true)
	private RateCalculationMethod rateCalculationMethod;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "interest_calculation_method",  nullable=true)
	private InterestCalculationMethod interestCalculationMethod;
	
	@Column(name = "grace_period_length",  nullable=true)
	private Long  gracePeriodLength;
	
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
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public PenalInterest getPenalInterest() {
		return penalInterest;
	}

	public void setPenalInterest(PenalInterest penalInterest) {
		this.penalInterest = penalInterest;
	}

	public PenalInterestType getPenalInterestType() {
		return penalInterestType;
	}

	public void setPenalInterestType(PenalInterestType penalInterestType) {
		this.penalInterestType = penalInterestType;
	}

	public Long getGracePeriodLength() {
		return gracePeriodLength;
	}

	public void setGracePeriodLength(Long gracePeriodLength) {
		this.gracePeriodLength = gracePeriodLength;
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

	public List<PenalTierBand> getPenalTierBand() {
		return penalTierBand;
	}

	public void setPenalTierBand(List<PenalTierBand> penalTierBand) {
		this.penalTierBand = penalTierBand;
	}

	public String getPenalTierBandMethod() {
		return penalTierBandMethod;
	}

	public void setPenalTierBandMethod(String penalTierBandMethod) {
		this.penalTierBandMethod = penalTierBandMethod;
	}

	public String getCalculationMethod() {
		return calculationMethod;
	}

	public void setCalculationMethod(String calculationMethod) {
		this.calculationMethod = calculationMethod;
	}

	public String getPenalInterestIdNo() {
		return penalInterestIdNo;
	}

	public void setPenalInterestIdNo(String penalInterestIdNo) {
		this.penalInterestIdNo = penalInterestIdNo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public PenalTierBandMethod getTierBandMethod() {
		return tierBandMethod;
	}

	public void setTierBandMethod(PenalTierBandMethod tierBandMethod) {
		this.tierBandMethod = tierBandMethod;
	}

	public RateCalculationMethod getRateCalculationMethod() {
		return rateCalculationMethod;
	}

	public void setRateCalculationMethod(RateCalculationMethod rateCalculationMethod) {
		this.rateCalculationMethod = rateCalculationMethod;
	}

	public InterestCalculationMethod getInterestCalculationMethod() {
		return interestCalculationMethod;
	}

	public void setInterestCalculationMethod(InterestCalculationMethod interestCalculationMethod) {
		this.interestCalculationMethod = interestCalculationMethod;
	}

	@JsonInclude(Include.NON_NULL)
	@Transient
	private List<PenalTierBand> penalTierBand;
	
	@JsonInclude(Include.NON_NULL)
	@Transient
	private String penalTierBandMethod;
	
	@JsonInclude(Include.NON_NULL)
	@Transient
	private String calculationMethod;
	
	@JsonInclude(Include.NON_NULL)
	@Transient
	private String penalInterestIdNo;

	private Timestamp syncTs;
	
	
}
