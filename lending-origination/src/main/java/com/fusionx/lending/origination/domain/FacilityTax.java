package com.fusionx.lending.origination.domain;

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
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;

import lombok.Data;

@Entity
@Table(name = "facility_tax")
@Data
public class FacilityTax extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 787687534247L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "tax_type_id" , nullable=true)
	private Long taxTypeId;
	
	@Column(name = "tax_type_code", length=20, nullable=false)
	private String taxTypeCode;
	
	@Column(name = "tax_type_name", length=255, nullable=true)
	private String taxTypeName;
	
	@Column(name = "tax_applicable_on_id" , nullable=true)
	private Long taxApplicableOnId;
	
	@Column(name = "tax_applicable_on_code", length=20, nullable=false)
	private String taxApplicableOnCode;
	
	@Column(name = "tax_applicable_on_name", length=255, nullable=true)
	private String taxApplicableOnName;
	
	@Column(name="tax_rate", nullable=true)
    private Float taxRate;
	
	@Column(name="tax_amount", columnDefinition="Decimal(25,5)", nullable=true)
    private BigDecimal taxAmount;
	
	@Column(name="total_tax_amount", columnDefinition="Decimal(25,5)", nullable=false)
    private BigDecimal totalTaxAmount;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "facility_parameter_id", nullable=true)
	private FacilityParameter facilityParameter;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "facility_structure_id", nullable=true)
	private FacilityStructure facilityStructure;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Long getTaxTypeId() {
		return taxTypeId;
	}

	public void setTaxTypeId(Long taxTypeId) {
		this.taxTypeId = taxTypeId;
	}

	public String getTaxTypeCode() {
		return taxTypeCode;
	}

	public void setTaxTypeCode(String taxTypeCode) {
		this.taxTypeCode = taxTypeCode;
	}

	public String getTaxTypeName() {
		return taxTypeName;
	}

	public void setTaxTypeName(String taxTypeName) {
		this.taxTypeName = taxTypeName;
	}

	public Long getTaxApplicableOnId() {
		return taxApplicableOnId;
	}

	public void setTaxApplicableOnId(Long taxApplicableOnId) {
		this.taxApplicableOnId = taxApplicableOnId;
	}

	public String getTaxApplicableOnCode() {
		return taxApplicableOnCode;
	}

	public void setTaxApplicableOnCode(String taxApplicableOnCode) {
		this.taxApplicableOnCode = taxApplicableOnCode;
	}

	public String getTaxApplicableOnName() {
		return taxApplicableOnName;
	}

	public void setTaxApplicableOnName(String taxApplicableOnName) {
		this.taxApplicableOnName = taxApplicableOnName;
	}

	public Float getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Float taxRate) {
		this.taxRate = taxRate;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public BigDecimal getTotalTaxAmount() {
		return totalTaxAmount;
	}

	public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
		this.totalTaxAmount = totalTaxAmount;
	}

	public FacilityParameter getFacilityParameter() {
		return facilityParameter;
	}

	public void setFacilityParameter(FacilityParameter facilityParameter) {
		this.facilityParameter = facilityParameter;
	}

	public FacilityStructure getFacilityStructure() {
		return facilityStructure;
	}

	public void setFacilityStructure(FacilityStructure facilityStructure) {
		this.facilityStructure = facilityStructure;
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
