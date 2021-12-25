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
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.MasterDefAccountRuleSetStandardEnum;
import com.fusionx.lending.product.enums.YesNo;

import lombok.Data;

@Entity
@Table(name = "master_def_acc_rule_stand_hist")
@Data
public class MasterDefAccountRuleSetStandardHistory    extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 961297319632868080L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "master_def_acc_rule_stand_id", nullable=false)
	private Long masterDefAccountRuleSetStandardId;
		
	@Column(name = "master_definition_id", nullable=false)
	private Long masterDefinitionId;
	
	@Column(name = "master_def_account_rule_id", nullable=false)
	private Long masterDefAccountRuleId;
	
	@Column(name = "master_def_acc_rule_hist_id", nullable=false)
	private Long masterDefAccountRuleHistoryId;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "type_value", length=70, nullable=false)
	private MasterDefAccountRuleSetStandardEnum typeValue;
	
	@Column(name = "common_list_item_id", nullable=false)
	private Long commonListItemId;
	
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

	public Long getMasterDefAccountRuleSetStandardId() {
		return masterDefAccountRuleSetStandardId;
	}

	public void setMasterDefAccountRuleSetStandardId(Long masterDefAccountRuleSetStandardId) {
		this.masterDefAccountRuleSetStandardId = masterDefAccountRuleSetStandardId;
	}

	public Long getMasterDefinitionId() {
		return masterDefinitionId;
	}

	public void setMasterDefinitionId(Long masterDefinitionId) {
		this.masterDefinitionId = masterDefinitionId;
	}

	public Long getMasterDefAccountRuleId() {
		return masterDefAccountRuleId;
	}

	public void setMasterDefAccountRuleId(Long masterDefAccountRuleId) {
		this.masterDefAccountRuleId = masterDefAccountRuleId;
	}

	public Long getMasterDefAccountRuleHistoryId() {
		return masterDefAccountRuleHistoryId;
	}

	public void setMasterDefAccountRuleHistoryId(Long masterDefAccountRuleHistoryId) {
		this.masterDefAccountRuleHistoryId = masterDefAccountRuleHistoryId;
	}

	public MasterDefAccountRuleSetStandardEnum getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(MasterDefAccountRuleSetStandardEnum typeValue) {
		this.typeValue = typeValue;
	}

	public Long getCommonListItemId() {
		return commonListItemId;
	}

	public void setCommonListItemId(Long commonListItemId) {
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
