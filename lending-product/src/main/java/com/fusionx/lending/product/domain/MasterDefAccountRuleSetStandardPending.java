package com.fusionx.lending.product.domain;

import java.io.Serializable;
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
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.MasterDefAccountRuleSetStandardEnum;
import com.fusionx.lending.product.enums.YesNo;

import lombok.Data;

@Entity
@Table(name = "master_def_acc_rule_stand_pend")
@Data
public class MasterDefAccountRuleSetStandardPending    extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 961297319632868080L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "master_def_acc_rule_stand_id" , nullable=true)
	private MasterDefAccountRuleSetStandard masterDefAccountRuleSetStandardId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "master_def_acc_rule_pend_id" , nullable=false)
	private MasterDefAccountRulePending masterDefAccountRulePendingId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "master_definition_pending_id", nullable=false)  
	private MasterDefinitionPending masterDefinitionPending;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "type_value", length=70, nullable=false)
	private MasterDefAccountRuleSetStandardEnum typeValue;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "common_list_item_id", nullable=false)  
	private CommonListItem commonListItemId;
	
	
	@Column(name = "common_list_item_name", length=70, nullable=false)
	private String commonListItemName;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "is_selected", length=70, nullable=false)
	private YesNo isSelected;
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public MasterDefAccountRuleSetStandard getMasterDefAccountRuleSetStandardId() {
		return masterDefAccountRuleSetStandardId;
	}

	public void setMasterDefAccountRuleSetStandardId(MasterDefAccountRuleSetStandard masterDefAccountRuleSetStandardId) {
		this.masterDefAccountRuleSetStandardId = masterDefAccountRuleSetStandardId;
	}

	public MasterDefAccountRulePending getMasterDefAccountRulePendingId() {
		return masterDefAccountRulePendingId;
	}

	public void setMasterDefAccountRulePendingId(MasterDefAccountRulePending masterDefAccountRulePendingId) {
		this.masterDefAccountRulePendingId = masterDefAccountRulePendingId;
	}

	public MasterDefinitionPending getMasterDefinitionPending() {
		return masterDefinitionPending;
	}

	public void setMasterDefinitionPending(MasterDefinitionPending masterDefinitionPending) {
		this.masterDefinitionPending = masterDefinitionPending;
	}

	public MasterDefAccountRuleSetStandardEnum getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(MasterDefAccountRuleSetStandardEnum typeValue) {
		this.typeValue = typeValue;
	}

	public CommonListItem getCommonListItemId() {
		return commonListItemId;
	}

	public void setCommonListItemId(CommonListItem commonListItemId) {
		this.commonListItemId = commonListItemId;
	}

	public String getCommonListItemName() {
		return commonListItemName;
	}

	public void setCommonListItemName(String commonListItemName) {
		this.commonListItemName = commonListItemName;
	}

	public YesNo getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(YesNo isSelected) {
		this.isSelected = isSelected;
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




}
