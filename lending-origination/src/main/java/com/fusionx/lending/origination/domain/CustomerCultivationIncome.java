package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
/**
 * Customer Cultivation Income Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-05-2021      		     			MENUKAJ      Created
 *    
 ********************************************************************************************************
 */


@Entity
@Table(name = "customer_cultivation_income")
@Data
public class CustomerCultivationIncome extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6570160972603385840L;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_id", nullable=false)
	private Customer customer;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "cultivation_category_id", nullable=false)
	private CultivationCategory cultivationCategory;
	
	@Column(name = "description", length=350, nullable=true)
	private String description;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "ownership_id", nullable=false)
	private CommonList ownershipId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "land_ownership_id", nullable=false)
	private CommonList landOwnershipId;
	
	@Column(name = "experience_id", nullable=false)
	private Long experienceId;
	
	@Column(name = "occurrence_frequency_id", nullable=false)
	private Long occurrenceFrequencyId;
	
	@Column(name = "occurrence_id", nullable=false)
	private Long occurrenceId;
	
	@Column(name = "calculation_frequency_id", nullable=false)
	private Long calculationFrequencyId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "no_of_unit_id", nullable=false)
	private CommonList noOfUnitId;
	
	@Column(name = "no_of_unit_value", nullable=false)
	private BigDecimal noOfUnitValue;
	
	@Column(name = "currency_id", nullable=false)
	private Long currencyId;
	
	@Column(name = "price_per_unit", nullable=false)
	private BigDecimal pricePerUnit;
	
	@Column(name = "total_gross_income", nullable=false)
	private BigDecimal totalGrossIncome;
	
	@Column(name = "comments", nullable=true, length =350)
	private String comments;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=30, nullable=false)
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
	private List<CustomerCultivationExpense> cultivationExpenses;

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

	public CultivationCategory getCultivationCategory() {
		return cultivationCategory;
	}

	public void setCultivationCategory(CultivationCategory cultivationCategory) {
		this.cultivationCategory = cultivationCategory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CommonList getOwnershipId() {
		return ownershipId;
	}

	public void setOwnershipId(CommonList ownershipId) {
		this.ownershipId = ownershipId;
	}

	public CommonList getLandOwnershipId() {
		return landOwnershipId;
	}

	public void setLandOwnershipId(CommonList landOwnershipId) {
		this.landOwnershipId = landOwnershipId;
	}

	public Long getExperienceId() {
		return experienceId;
	}

	public void setExperienceId(Long experienceId) {
		this.experienceId = experienceId;
	}

	public Long getOccurrenceFrequencyId() {
		return occurrenceFrequencyId;
	}

	public void setOccurrenceFrequencyId(Long occurrenceFrequencyId) {
		this.occurrenceFrequencyId = occurrenceFrequencyId;
	}

	public Long getOccurrenceId() {
		return occurrenceId;
	}

	public void setOccurrenceId(Long occurrenceId) {
		this.occurrenceId = occurrenceId;
	}

	public Long getCalculationFrequencyId() {
		return calculationFrequencyId;
	}

	public void setCalculationFrequencyId(Long calculationFrequencyId) {
		this.calculationFrequencyId = calculationFrequencyId;
	}

	public CommonList getNoOfUnitId() {
		return noOfUnitId;
	}

	public void setNoOfUnitId(CommonList noOfUnitId) {
		this.noOfUnitId = noOfUnitId;
	}

	public BigDecimal getNoOfUnitValue() {
		return noOfUnitValue;
	}

	public void setNoOfUnitValue(BigDecimal noOfUnitValue) {
		this.noOfUnitValue = noOfUnitValue;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public BigDecimal getTotalGrossIncome() {
		return totalGrossIncome;
	}

	public void setTotalGrossIncome(BigDecimal totalGrossIncome) {
		this.totalGrossIncome = totalGrossIncome;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public List<CustomerCultivationExpense> getCultivationExpenses() {
		return cultivationExpenses;
	}

	public void setCultivationExpenses(List<CustomerCultivationExpense> cultivationExpenses) {
		this.cultivationExpenses = cultivationExpenses;
	}

}
