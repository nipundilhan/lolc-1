package com.fusionx.lending.product.domain;

import java.io.Serializable;

/**
Master Definition 
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-07-2019                            Dilki        Created
 *    
 ********************************************************************************************************
 */

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import lombok.Data;

@Entity
@Table(name = "master_def_province_pending")
//@Data
public class MasterDefProvincePending extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -6766783770349518221L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "mst_def_country_pend_id", nullable = false)
	private MasterDefCountryPending masterDefCountryPending;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "master_def_province_id", nullable = true)
	private MasterDefProvince masterDefProvince;

	@Column(name = "province_id", nullable = false)
	private Long provinceId;
	
	@Column(name = "province_name", length = 350, nullable = false)
	private String provinceName;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length = 20, nullable = false)
	private CommonStatus status;

	@Column(name = "pcreated_user", length = 255, nullable = false)
	private String penCreatedUser;

	@Column(name = "pcreated_date", nullable = false)
	private Timestamp penCreatedDate;
	
	@Transient
	private List<MasterDefStatePending> state;

	public List<MasterDefStatePending> getState() {
		return state;
	}

	public void setState(List<MasterDefStatePending> state) {
		this.state = state;
	}

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

	public void setStatus(String status) {
		this.status = CommonStatus.valueOf(status);
	}


	public String getPenCreatedUser() {
		return penCreatedUser;
	}

	public void setPenCreatedUser(String penCreatedUser) {
		this.penCreatedUser = penCreatedUser;
	}

	public Timestamp getPenCreatedDate() {
		return penCreatedDate;
	}

	public void setPenCreatedDate(Timestamp penCreatedDate) {
		this.penCreatedDate = penCreatedDate;
	}

	public MasterDefCountryPending getMasterDefCountryPending() {
		return masterDefCountryPending;
	}

	public void setMasterDefCountryPending(MasterDefCountryPending masterDefCountryPending) {
		this.masterDefCountryPending = masterDefCountryPending;
	}

	public MasterDefProvince getMasterDefProvince() {
		return masterDefProvince;
	}

	public void setMasterDefProvince(MasterDefProvince masterDefProvince) {
		this.masterDefProvince = masterDefProvince;
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
