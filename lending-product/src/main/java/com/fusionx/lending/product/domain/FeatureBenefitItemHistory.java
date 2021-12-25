package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.Indicator;

import lombok.Data;

/**
 * Feature Benefit Item history
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-06-2021     FX-6614     FX-6550    RavishikaS     Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "feature_benefit_item_his")
@Transactional
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FeatureBenefitItemHistory extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 5553306283187489406L;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "type_id")
	private Long featureBenefitItemTypeId;
		
	@Column(name = "amount")
	private BigDecimal amount;
	
	@Column(name = "textual")
	private String textual;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "indicator")
	private Indicator indicator;
		
	@Column(name = "status")
	private String status;
	
	@Column(name = "sync_ts")
	private Timestamp syncTs;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	@Column(name = "feature_benefit_item_id")
	private Long featureBenefitItemId;
	
	@Column(name="history_created_user")
	private String historyCreatedUser;
	
	@Column(name="history_created_date")
	private Timestamp historyCreatedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

	public Long getFeatureBenefitItemTypeId() {
		return featureBenefitItemTypeId;
	}

	public void setFeatureBenefitItemTypeId(Long featureBenefitItemTypeId) {
		this.featureBenefitItemTypeId = featureBenefitItemTypeId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTextual() {
		return textual;
	}

	public void setTextual(String textual) {
		this.textual = textual;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getSyncTs() {
		return syncTs;
	}

	public void setSyncTs(Timestamp syncTs) {
		this.syncTs = syncTs;
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

	public Long getFeatureBenefitItemId() {
		return featureBenefitItemId;
	}

	public void setFeatureBenefitItemId(Long featureBenefitItemId) {
		this.featureBenefitItemId = featureBenefitItemId;
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

}
