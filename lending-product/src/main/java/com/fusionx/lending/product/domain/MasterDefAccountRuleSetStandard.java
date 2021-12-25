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
@Table(name = "master_def_acc_rule_set_stand")
@Data
public class MasterDefAccountRuleSetStandard   extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 961297319632868080L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
		
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "master_definition_id", nullable=false)  
	private MasterDefinition masterDefinitionId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "master_def_account_rule_id" , nullable=false)  
	private MasterDefAccountRule masterDefAccountRule;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "type_value", length=70, nullable=false)
	private MasterDefAccountRuleSetStandardEnum typeValue;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "common_list_item_id", nullable=false)  
	private CommonListItem commonListItem;
	
	@Column(name = "common_list_item_name", length=70, nullable=false)
	private String commonListItemName;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "is_selected", length=70, nullable=false)
	private YesNo isSelected;

	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public MasterDefinition getMasterDefinitionId() {
		return masterDefinitionId;
	}

	public void setMasterDefinitionId(MasterDefinition masterDefinitionId) {
		this.masterDefinitionId = masterDefinitionId;
	}

	public MasterDefAccountRule getMasterDefAccountRule() {
		return masterDefAccountRule;
	}

	public void setMasterDefAccountRule(MasterDefAccountRule masterDefAccountRule) {
		this.masterDefAccountRule = masterDefAccountRule;
	}

	public MasterDefAccountRuleSetStandardEnum getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(MasterDefAccountRuleSetStandardEnum typeValue) {
		this.typeValue = typeValue;
	}

	public CommonListItem getCommonListItem() {
		return commonListItem;
	}

	public void setCommonListItem(CommonListItem commonListItem) {
		this.commonListItem = commonListItem;
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
