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
import com.fusionx.lending.origination.enums.ChargeCatogory;
import com.fusionx.lending.origination.enums.CommonStatus;

import lombok.Data;

@Entity
@Table(name = "facility_charges")
@Data
public class FacilityCharges extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 76676556698766657L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "charge_type_id" , nullable=true)
	private Long chargeTypeId;
	
	@Column(name = "charge_type_code", length=20, nullable=false)
	private String chargeTypeCode;
	
	@Column(name = "charge_type_name", length=255, nullable=true)
	private String chargeTypeName;
	
	@Column(name = "criteria_id" , nullable=true)
	private Long criteriaId;
	
	@Column(name = "criteria_code", length=20, nullable=false)
	private String criteriaCode;
	
	@Column(name = "criteria_name", length=255, nullable=true)
	private String criteriaName;
	
	@Column(name = "calculation_method_id" , nullable=true)
	private Long calculationMethodId;
	
	@Column(name = "calculation_method_code", length=20, nullable=true)
	private String calculationMethodCode;
	
	@Column(name = "calculation_method_name", length=255, nullable=true)
	private String calculationMethodName;
	
	@Column(name = "options", length=255, nullable=true)
	private String options;
	
	@Column(name = "frequency_id" , nullable=true)
	private Long frequencyId;
	
	@Column(name = "frequency_code", length=20, nullable=true)
	private String frequencyCode;
	
	@Column(name = "frequency_name", length=255, nullable=true)
	private String frequencyName;
	
	@Column(name="charge_rate", nullable=true)
    private Float chargeRate;
	
	@Column(name="charge_amount", columnDefinition="Decimal(25,5)", nullable=true)
    private BigDecimal chargeAmount;
	
	@Column(name="total_charge_amount", columnDefinition="Decimal(25,5)", nullable=true)
    private BigDecimal totalChargeAmount;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "charge_catogory", length=20, nullable=false)
	private ChargeCatogory chargeCatogory;
	
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
	
	@Transient
	private List<FacilityChargesDetail> chargeDetails;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Long getChargeTypeId() {
		return chargeTypeId;
	}

	public void setChargeTypeId(Long chargeTypeId) {
		this.chargeTypeId = chargeTypeId;
	}

	public String getChargeTypeCode() {
		return chargeTypeCode;
	}

	public void setChargeTypeCode(String chargeTypeCode) {
		this.chargeTypeCode = chargeTypeCode;
	}

	public String getChargeTypeName() {
		return chargeTypeName;
	}

	public void setChargeTypeName(String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}

	public Long getCriteriaId() {
		return criteriaId;
	}

	public void setCriteriaId(Long criteriaId) {
		this.criteriaId = criteriaId;
	}

	public String getCriteriaCode() {
		return criteriaCode;
	}

	public void setCriteriaCode(String criteriaCode) {
		this.criteriaCode = criteriaCode;
	}

	public String getCriteriaName() {
		return criteriaName;
	}

	public void setCriteriaName(String criteriaName) {
		this.criteriaName = criteriaName;
	}

	public Long getCalculationMethodId() {
		return calculationMethodId;
	}

	public void setCalculationMethodId(Long calculationMethodId) {
		this.calculationMethodId = calculationMethodId;
	}

	public String getCalculationMethodCode() {
		return calculationMethodCode;
	}

	public void setCalculationMethodCode(String calculationMethodCode) {
		this.calculationMethodCode = calculationMethodCode;
	}

	public String getCalculationMethodName() {
		return calculationMethodName;
	}

	public void setCalculationMethodName(String calculationMethodName) {
		this.calculationMethodName = calculationMethodName;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public Long getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(Long frequencyId) {
		this.frequencyId = frequencyId;
	}

	public String getFrequencyCode() {
		return frequencyCode;
	}

	public void setFrequencyCode(String frequencyCode) {
		this.frequencyCode = frequencyCode;
	}

	public String getFrequencyName() {
		return frequencyName;
	}

	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
	}

	public Float getChargeRate() {
		return chargeRate;
	}

	public void setChargeRate(Float chargeRate) {
		this.chargeRate = chargeRate;
	}

	public BigDecimal getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(BigDecimal chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public BigDecimal getTotalChargeAmount() {
		return totalChargeAmount;
	}

	public void setTotalChargeAmount(BigDecimal totalChargeAmount) {
		this.totalChargeAmount = totalChargeAmount;
	}

	public ChargeCatogory getChargeCatogory() {
		return chargeCatogory;
	}

	public void setChargeCatogory(ChargeCatogory chargeCatogory) {
		this.chargeCatogory = chargeCatogory;
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

	public List<FacilityChargesDetail> getChargeDetails() {
		return chargeDetails;
	}

	public void setChargeDetails(List<FacilityChargesDetail> chargeDetails) {
		this.chargeDetails = chargeDetails;
	}
}
