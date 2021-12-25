package com.fusionx.lending.product.domain;

import java.io.Serializable;

/**
Master Definition 
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-07-2019                            Dilki        Created
 *    
 ********************************************************************************************************
 */

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
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;

@Entity
@Table(name = "master_def_province_history")
//@Data
public class MasterDefProvinceHistory extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -3137541602172043116L;


	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;
	
	@Column(name = "master_def_province_id", nullable = false)
	private Long masterDefProvinceId;
	
	@Column(name = "master_def_country_id", nullable = false)
	private Long masterDefCountryId;

	@Column(name = "province_id", nullable = false)
	private Long provinceId;
	
	@Column(name = "province_name", length = 350, nullable = false)
	private String provinceName;

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

	public Long getMasterDefProvinceId() {
		return masterDefProvinceId;
	}

	public void setMasterDefProvinceId(Long masterDefProvinceId) {
		this.masterDefProvinceId = masterDefProvinceId;
	}

	public Long getMasterDefCountryId() {
		return masterDefCountryId;
	}

	public void setMasterDefCountryId(Long masterDefCountryId) {
		this.masterDefCountryId = masterDefCountryId;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}


}
