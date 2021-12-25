package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.transaction.Transactional;

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
@Table(name = "feature_benefit_eligi_his")
@Transactional
public class FeatureBenefEligiHistory extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 878788L;
	
	
	@Column(name = "feature_benefit_eligi_id",  nullable=false)
	private Long featureBenefitEligiId;
	
	@Column(name = "name", length=70, nullable=false)
	private String name;
	
	@Column(name = "description", length=350, nullable=false)
	private String description;

	@Column(name = "type_id", nullable=true)  
	private Long  typeId;
	
	@Column(name = "amount",  nullable=true)
	private BigDecimal amount;
	
	@Column(name = "indicator",  nullable=true)
	private Boolean indicator;
	
	@Column(name = "texual", length=500, nullable=true)
	private String texual;
	
	@Column(name = "period_id", nullable=true)
	private Long periodId;
	
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

	@Column(name = "hcreated_user", length=20, nullable=false)
	private String hcreatedUser;
	
	@Column(name = "hcreated_date", nullable=false)
	private Timestamp hcreatedDate;
	
	@Column(name="feature_benefit_eligi_ver", nullable=false)
	private Long featureBenefitEligiVer;
	
	
	
	public Long getFeatureBenefitEligiVer() {
		return featureBenefitEligiVer;
	}

	public void setFeatureBenefitEligiVer(Long featureBenefitEligiVer) {
		this.featureBenefitEligiVer = featureBenefitEligiVer;
	}

	public FeatureBenefEligiHistory() {
		super();
	}

	public Long getFeatureBenefitEligiId() {
		return featureBenefitEligiId;
	}

	public void setFeatureBenefitEligiId(Long featureBenefitEligiId) {
		this.featureBenefitEligiId = featureBenefitEligiId;
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

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
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

	public String getHcreatedUser() {
		return hcreatedUser;
	}

	public void setHcreatedUser(String hcreatedUser) {
		this.hcreatedUser = hcreatedUser;
	}

	public Timestamp getHcreatedDate() {
		return hcreatedDate;
	}

	public void setHcreatedDate(Timestamp hcreatedDate) {
		this.hcreatedDate = hcreatedDate;
	}
}
