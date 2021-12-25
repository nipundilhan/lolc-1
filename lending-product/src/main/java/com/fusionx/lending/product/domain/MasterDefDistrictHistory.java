package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.sql.Timestamp;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;

@Entity
@Table(name = "master_def_district_history")
//@Data
public class MasterDefDistrictHistory   extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -907819812717985128L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@Column(name = "master_def_state_id", nullable = false)
	private Long masterDefStateId;
	
	@Column(name = "master_def_district_id", nullable = false)
	private Long masterDefDistrictId;

	@Column(name = "district_id", nullable = false)
	private Long districtId;
	
	@Column(name = "district_name", length = 350, nullable = false)
	private String districtName;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable=false, length=20)
	private CommonStatus status;

	@Column(name = "created_user", length = 255, nullable = false)
	private String createdUser;

	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;

	@Column(name = "modified_user", length = 255, nullable = true)
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

	
	public Long getMasterDefStateId() {
		return masterDefStateId;
	}

	public void setMasterDefStateId(Long masterDefStateId) {
		this.masterDefStateId = masterDefStateId;
	}

	public Long getMasterDefDistrictId() {
		return masterDefDistrictId;
	}

	public void setMasterDefDistrictId(Long masterDefDistrictId) {
		this.masterDefDistrictId = masterDefDistrictId;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}


}
