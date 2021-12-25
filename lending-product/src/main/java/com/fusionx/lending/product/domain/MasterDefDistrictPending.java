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
import com.fusionx.lending.product.enums.CommonStatus;
import lombok.Data;

@Entity
@Table(name = "master_def_district_pending")
//@Data
public class MasterDefDistrictPending  extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -6766783770349518221L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "mst_def_state_pend_id", nullable = false)
	private MasterDefStatePending masterDefStatePending;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "master_def_district_id", nullable = true)
	private MasterDefDistrict masterDefDistrict;

	@Column(name = "district_id", nullable = false)
	private Long districtId;
	
	@Column(name = "district_name", length = 350, nullable = false)
	private String districtName;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length = 20, nullable = false)
	private CommonStatus status;

	@Column(name = "pcreated_user", length = 255, nullable = false)
	private String penCreatedUser;

	@Column(name = "pcreated_date", nullable = false)
	private Timestamp penCreatedDate;

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

	public MasterDefStatePending getMasterDefStatePending() {
		return masterDefStatePending;
	}

	public void setMasterDefStatePending(MasterDefStatePending masterDefStatePending) {
		this.masterDefStatePending = masterDefStatePending;
	}

	public MasterDefDistrict getMasterDefDistrict() {
		return masterDefDistrict;
	}

	public void setMasterDefDistrict(MasterDefDistrict masterDefDistrict) {
		this.masterDefDistrict = masterDefDistrict;
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

	

}
