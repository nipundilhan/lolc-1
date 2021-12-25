package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.transaction.Transactional;

import com.fusionx.lending.product.core.BaseEntity;

/**
 * Feature Benefit Group Type Service - Feature Benefit Group Type Domain entity
 * @author 	Sanatha
 * @Date    15-JUN-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-JUN-2021  						     Sanatha      Created
 *    
 ********************************************************************************************************
 */
@Entity
@Table(name = "feature_benefit_group_type_his")
@Transactional
public class FeatureBenefitGroupTypeHistory extends BaseEntity implements Serializable{
	
	@Column(name="feature_benefit_group_type_id", nullable=false)
	private Long featureBenefitGroupTypeId;
	
	@Column(name = "code", length=70, nullable=true)
	private String code;
	
	@Column(name = "name", length=70, nullable=false)
	private String name;
	
	@Column(name = "description", length=350, nullable=false)
	private String description;
	
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
	
	@Column(name="history_created_user", length=255, nullable=false)
	private String historyCreatedUser;
	
	@Column(name="history_created_date", nullable=false)
	private Timestamp historyCreatedDate;
	
	@Column(name="feature_benef_grp_type_version", nullable=false)
	private Long featureBenefitGroupTypeVersion;

	public Long getFeatureBenefitGroupTypeId() {
		return featureBenefitGroupTypeId;
	}

	public void setFeatureBenefitGroupTypeId(Long featureBenefitGroupTypeId) {
		this.featureBenefitGroupTypeId = featureBenefitGroupTypeId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getHistoryCreatedUser() {
		return historyCreatedUser;
	}

	public void setHistoryCreatedUser(String historyCreatedUser) {
		this.historyCreatedUser = historyCreatedUser;
	}

	public Timestamp getHistoryCreatedDate() {
		return historyCreatedDate;
	}

	public void setHistoryCreatedDate(Timestamp historyCreatedDate) {
		this.historyCreatedDate = historyCreatedDate;
	}

	public Long getFeatureBenefitGroupTypeVersion() {
		return featureBenefitGroupTypeVersion;
	}

	public void setFeatureBenefitGroupTypeVersion(Long featureBenefitGroupTypeVersion) {
		this.featureBenefitGroupTypeVersion = featureBenefitGroupTypeVersion;
	}
	
	

}
