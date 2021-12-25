package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;

import lombok.Data;

@Entity
@Table(name = "facility_structure")
@Data
public class FacilityStructure extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 4537474747L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "sequence" , nullable=false)
	private Long sequence;
	
	@Column(name = "period" , nullable=false)
	private Long period;
	
	@Column(name="rate", nullable=true)
    private BigDecimal rate;
	
	@Column(name="installment", columnDefinition="Decimal(25,5)", nullable=false)
    private BigDecimal installment;
	
	@Column(name="total_tax", columnDefinition="Decimal(25,5)", nullable=true)
    private BigDecimal totalTaxes;
	
	@Column(name="total_charges", columnDefinition="Decimal(25,5)", nullable=true)
    private BigDecimal totalCharges;
	
	@Column(name="total_installment", columnDefinition="Decimal(25,5)", nullable=false)
    private BigDecimal totalInstallment;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "facility_parameter_id", nullable=false)
	private FacilityParameter facilityParameter;
	
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
	
	@Transient
	private List<FacilityCharges> facilityStructureCharges;
	
	@Transient
	private List<FacilityTax> facilityStructureTaxes;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public Long getPeriod() {
		return period;
	}

	public void setPeriod(Long period) {
		this.period = period;
	}

	

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getInstallment() {
		return installment;
	}

	public void setInstallment(BigDecimal installment) {
		this.installment = installment;
	}

	public BigDecimal getTotalTaxes() {
		return totalTaxes;
	}

	public void setTotalTaxes(BigDecimal totalTaxes) {
		this.totalTaxes = totalTaxes;
	}

	public BigDecimal getTotalCharges() {
		return totalCharges;
	}

	public void setTotalCharges(BigDecimal totalCharges) {
		this.totalCharges = totalCharges;
	}
	
	public BigDecimal getTotalInstallment() {
		return totalInstallment;
	}

	public void setTotalInstallment(BigDecimal totalInstallment) {
		this.totalInstallment = totalInstallment;
	}

	public FacilityParameter getFacilityParameter() {
		return facilityParameter;
	}

	public void setFacilityParameter(FacilityParameter facilityParameter) {
		this.facilityParameter = facilityParameter;
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

	public List<FacilityCharges> getFacilityStructureCharges() {
		return facilityStructureCharges;
	}

	public void setFacilityStructureCharges(List<FacilityCharges> facilityStructureCharges) {
		this.facilityStructureCharges = facilityStructureCharges;
	}

	public List<FacilityTax> getFacilityStructureTaxes() {
		return facilityStructureTaxes;
	}

	public void setFacilityStructureTaxes(List<FacilityTax> facilityStructureTaxes) {
		this.facilityStructureTaxes = facilityStructureTaxes;
	}
}
