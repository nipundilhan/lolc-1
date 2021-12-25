package com.fusionx.lending.origination.domain;
/**
 * Check List Item
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021   FXL-75  		 FXL-550 	Dilki        Created
 *    
 ********************************************************************************************************
 */
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
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;

import lombok.Data;

@Entity
@Table(name = "check_list_item_history")
@Data
public class CheckListItemHistory extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1419675825820343420L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@Column(name = "check_list_item_id", nullable = false)
	private Long checkListItemId;

	@Column(name = "code", length = 4, nullable = false)
	private String code;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "category", nullable = false)
	private CommonList category;

	@Column(name = "name", length = 70, nullable = false)
	private String name;

	@Column(name = "description", length = 350, nullable = true)
	private String description;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private CommonStatus status;

	@Column(name = "created_user", length = 255, nullable = false)
	private String createdUser;

	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;

	@Column(name = "modified_user", nullable = true, length = 255)
	private String modifiedUser;

	@Column(name = "modified_date", nullable = true)
	private Timestamp modifiedDate;

	@Column(name = "history_created_user", length = 255, nullable = false)
	private String historyCreatedUser;

	@Column(name = "history_created_date", nullable = false)
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

	public CommonList getCategory() {
		return category;
	}

	public void setCategory(CommonList category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Long getCheckListItemId() {
		return checkListItemId;
	}

	public void setCheckListItemId(Long checkListItemId) {
		this.checkListItemId = checkListItemId;
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
