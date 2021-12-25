package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;

import lombok.Data;
/**
 * Financial Commitment Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6301    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Entity
@Data
@Table(name = "financial_commitment")
public class FinancialCommitment   extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 0000000000001;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_id")  
	private Customer customer;
	
	@Column(name = "category")
	private String category;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "type_of_commitment_id", nullable=false)
	private CommonList typeOfCommitment;
	
	@Transient
	private Long typeOfCommitmentId;
	
	@Transient
	private String typeOfCommitmentCode;
	
	@Column(name = "type_of_commitment")
	private String typeOfCommitmentName;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "type_of_facility_id", nullable=false)
	private CommonList typeOfFacility;
	
	@Transient
	private Long typeOfFacilityId;
	
	@Transient
	private String typeOfFacilityCode;
	
	@Column(name = "type_of_facility")
	private String typeOfFacilityName;
	
	@Column(name = "external_institute_id")
	private Long externalInstituteId;
	
	@Column(name = "internal_institute_id")
	private Long internalInstituteId;
	
	@Column(name = "repayment_frequency_id")
	private Long repaymentFrequencyId;
	
	@Column(name = "facility_amount")
	private BigDecimal facilityAmount;
	
	@Column(name = "rental")
	private BigDecimal rental;
	
	@Column(name = "calculation_frequency_id")
	private Long calculationFrequencyId;
	
	@Column(name = "calculation_frequency_code")
	private String calculationFrequencyCode;
	
	@Column(name = "calculation_frequency_name")
	private String calculationFrequencyName;
	
	@Column(name = "rental_calc_per_freq")
	private BigDecimal rentalCalcPerFreq;
	
	@Column(name = "no_of_rentals_left")
	private Long noOfRentalsLeft;
	
	@Column(name = "toal_outstanding")
	private BigDecimal toalOutstanding;
	
	@Column(name = "comments")
	private String comment;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	

	public Long getTypeOfCommitmentId() {
		return typeOfCommitment.getId();
	}

	public Long getTypeOfFacilityId() {
		return typeOfFacility.getId();
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	public CommonList getTypeOfCommitment() {
		return typeOfCommitment;
	}

	public void setTypeOfCommitment(CommonList typeOfCommitment) {
		this.typeOfCommitment = typeOfCommitment;
	}

	public String getTypeOfCommitmentCode() {
		return typeOfCommitmentCode;
	}

	public void setTypeOfCommitmentCode(String typeOfCommitmentCode) {
		this.typeOfCommitmentCode = typeOfCommitmentCode;
	}

	public String getTypeOfCommitmentName() {
		return typeOfCommitmentName;
	}

	public void setTypeOfCommitmentName(String typeOfCommitmentName) {
		this.typeOfCommitmentName = typeOfCommitmentName;
	}

	public CommonList getTypeOfFacility() {
		return typeOfFacility;
	}

	public void setTypeOfFacility(CommonList typeOfFacility) {
		this.typeOfFacility = typeOfFacility;
	}

	public String getTypeOfFacilityCode() {
		return typeOfFacilityCode;
	}

	public void setTypeOfFacilityCode(String typeOfFacilityCode) {
		this.typeOfFacilityCode = typeOfFacilityCode;
	}

	public String getTypeOfFacilityName() {
		return typeOfFacilityName;
	}

	public void setTypeOfFacilityName(String typeOfFacilityName) {
		this.typeOfFacilityName = typeOfFacilityName;
	}

	public Long getExternalInstituteId() {
		return externalInstituteId;
	}

	public void setExternalInstituteId(Long externalInstituteId) {
		this.externalInstituteId = externalInstituteId;
	}

	public Long getInternalInstituteId() {
		return internalInstituteId;
	}

	public void setInternalInstituteId(Long internalInstituteId) {
		this.internalInstituteId = internalInstituteId;
	}

	public Long getRepaymentFrequencyId() {
		return repaymentFrequencyId;
	}

	public void setRepaymentFrequencyId(Long repaymentFrequencyId) {
		this.repaymentFrequencyId = repaymentFrequencyId;
	}

	public BigDecimal getFacilityAmount() {
		return facilityAmount;
	}

	public void setFacilityAmount(BigDecimal facilityAmount) {
		this.facilityAmount = facilityAmount;
	}

	public BigDecimal getRental() {
		return rental;
	}

	public void setRental(BigDecimal rental) {
		this.rental = rental;
	}

	public Long getCalculationFrequencyId() {
		return calculationFrequencyId;
	}

	public void setCalculationFrequencyId(Long calculationFrequencyId) {
		this.calculationFrequencyId = calculationFrequencyId;
	}

	
	public String getCalculationFrequencyCode() {
		return calculationFrequencyCode;
	}

	public void setCalculationFrequencyCode(String calculationFrequencyCode) {
		this.calculationFrequencyCode = calculationFrequencyCode;
	}

	public String getCalculationFrequencyName() {
		return calculationFrequencyName;
	}

	public void setCalculationFrequencyName(String calculationFrequencyName) {
		this.calculationFrequencyName = calculationFrequencyName;
	}

	public BigDecimal getRentalCalcPerFreq() {
		return rentalCalcPerFreq;
	}

	public void setRentalCalcPerFreq(BigDecimal rentalCalcPerFreq) {
		this.rentalCalcPerFreq = rentalCalcPerFreq;
	}

	public Long getNoOfRentalsLeft() {
		return noOfRentalsLeft;
	}

	public void setNoOfRentalsLeft(Long noOfRentalsLeft) {
		this.noOfRentalsLeft = noOfRentalsLeft;
	}

	public BigDecimal getToalOutstanding() {
		return toalOutstanding;
	}

	public void setToalOutstanding(BigDecimal toalOutstanding) {
		this.toalOutstanding = toalOutstanding;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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
