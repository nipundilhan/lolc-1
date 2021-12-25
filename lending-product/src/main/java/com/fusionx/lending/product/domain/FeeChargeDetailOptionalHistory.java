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


@Entity
@Table(name = "fee_charge_det_optional_hist")
//@Data
public class FeeChargeDetailOptionalHistory extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 3411847766935388125L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fee_charge_detail_optional_id", nullable=false)
	private FeeChargeDetailOptional feeChargeDetailOptional;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fee_charg_det_optional_pend_id", nullable=true)
	private FeeChargeDetailOptionalPending feeChargeDetailOptionalPending;

	@Column(name = "fee_charge_id", nullable=false)
	private Long feeChargeId;
	
	@Column(name = "effective_date", nullable=false)
	private Timestamp effectiveDate;
	
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


	@Column(name = "type", length=20, nullable=false)
	private String type;	
	
	@Column(name = "note", length=350, nullable=false)
	private String note;
		
	@Column(name = "mandatory", length=20, nullable=false)
	private String mandatory;
	
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
	
	public FeeChargeDetailOptional getFeeChargeDetailOptional() {
		return feeChargeDetailOptional;
	}

	public void setFeeChargeDetailOptional(FeeChargeDetailOptional feeChargeDetailOptional) {
		this.feeChargeDetailOptional = feeChargeDetailOptional;
	}

	public FeeChargeDetailOptionalPending getFeeChargeDetailOptionalPending() {
		return feeChargeDetailOptionalPending;
	}

	public void setFeeChargeDetailOptionalPending(FeeChargeDetailOptionalPending feeChargeDetailOptionalPending) {
		this.feeChargeDetailOptionalPending = feeChargeDetailOptionalPending;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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


	public Timestamp getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	public Timestamp getHistoryCreatedDate() {
		return historyCreatedDate;
	}

	public void setHistoryCreatedDate(Timestamp historyCreatedDate) {
		this.historyCreatedDate = historyCreatedDate;
	}

}
