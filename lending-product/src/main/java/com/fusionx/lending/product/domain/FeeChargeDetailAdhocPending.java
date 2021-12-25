package com.fusionx.lending.product.domain;

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
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;

@Entity
@Table(name = "fee_charge_det_adhoc_pending")
//@Data
public class FeeChargeDetailAdhocPending    extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 3411847766935388125L;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fee_charge_pending_id", nullable=false)
	private FeeChargePending feeChargePending;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "fee_charge_detail_adhoc_id", nullable=true)
	private FeeChargeDetailAdhoc feeChargeDetailAdhoc;

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

	
	public FeeChargeDetailAdhoc getFeeChargeDetailAdhoc() {
		return feeChargeDetailAdhoc;
	}

	public void setFeeChargeDetailAdhoc(FeeChargeDetailAdhoc feeChargeDetailAdhoc) {
		this.feeChargeDetailAdhoc = feeChargeDetailAdhoc;
	}

	@Transient
	private Long feeChargePendingId;
	
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
	


	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

//	public String getModifiedUser() {
//		return modifiedUser;
//	}
//
//	public void setModifiedUser(String modifiedUser) {
//		this.modifiedUser = modifiedUser;
//	}
//
//	public Timestamp getModifiedDate() {
//		return modifiedDate;
//	}
//
//	public void setModifiedDate(Timestamp modifiedDate) {
//		this.modifiedDate = modifiedDate;
//	}

	
	public Long getFeeCategoryId() {
		return feeCategory.getId();
	}
	
	public String getFeeCategoryName() {
		return feeCategory.getName();

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
	
	public Long getFeeChargePendingId() {
		return feeChargePending.getId();
	}
	
	public String getFeeChargeCode() {
		return feeChargePending.getCode();
	}
}

