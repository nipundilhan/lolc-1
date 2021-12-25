package com.fusionx.lending.product.domain;



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
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;

/**
 * feature benefit eligibility Service - Domain entity
 * @author 	Sanatha
 * @Date    24-JUN-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-JUN-2021   					     Sanatha      Initial development.
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "feature_benefit_eligibility")
@Transactional
public class FeatureBenefitEligibility extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 878788L;
	
	@Column(name = "code", length=20, nullable=false)
	private String code;
	
	@Column(name = "name", length=70, nullable=false)
	private String name;
	
	@Column(name = "description", length=350, nullable=false)
	private String description;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "type_id", nullable=true)  
	private FeatureBenefitEligibilityType featureBenefitEligiType;
	
	@Column(name = "amount",  nullable=true)
	private BigDecimal amount;
	
	@Column(name = "indicator",  nullable=true)
	private Boolean indicator;
	
	@Column(name = "texual", length=500, nullable=true)
	private String texual;
	
	@Column(name = "period_id", nullable=true)
	private Long periodId;
	
	@Column(name = "period", nullable=true, length=50)
	private String period;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "status", length=20, nullable=false)
	private String status;
	
	@Column(name = "created_user", length=20, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", length=20, nullable=true)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;


	
	@Transient
	private String typeId;
	
	@Transient
	private String type;
	
	@Transient
	private String typeCode;
	
	public FeatureBenefitEligibility() {
		super();
	}



	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getPeriod() {
		return period;
	}


	public void setPeriod(String period) {
		this.period = period;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}



	public FeatureBenefitEligibilityType getFeatureBenefitEligiType() {
		return featureBenefitEligiType;
	}



	public void setFeatureBenefitEligiType(FeatureBenefitEligibilityType featureBenefitEligiType) {
		this.featureBenefitEligiType = featureBenefitEligiType;
	}



	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public Boolean getIndicator() {
		return indicator;
	}


	public void setIndicator(Boolean indicator) {
		this.indicator = indicator;
	}


	public String getTexual() {
		return texual;
	}


	public void setTexual(String texual) {
		this.texual = texual;
	}


	public Long getPeriodId() {
		return periodId;
	}


	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}


	public String getTenantId() {
		return tenantId;
	}


	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
