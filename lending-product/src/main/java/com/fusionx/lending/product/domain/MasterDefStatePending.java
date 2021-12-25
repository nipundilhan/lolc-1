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
@Table(name = "master_def_state_pending")
//@Data
public class MasterDefStatePending extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1050154021482885778L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "master_def_state_id", nullable = true)
	private MasterDefState masterDefState;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "mst_def_prvnc_pend_id", nullable = true)
	private MasterDefProvincePending masterDefProvincePending;

	@Column(name = "state_id", nullable=false)
	private Long stateId;	
	
	@Column(name = "state_name", length = 350, nullable = false)
	private String stateName;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length = 20, nullable = false)
	private CommonStatus status;


	@Column(name = "pcreated_user", length = 255, nullable = false)
	private String penCreatedUser;

	@Column(name = "pcreated_date", nullable = false)
	private Timestamp penCreatedDate;
	
	@Transient
	private List<MasterDefDistrictPending> district;

	public List<MasterDefDistrictPending> getDistrict() {
		return district;
	}

	public void setDistrict(List<MasterDefDistrictPending> district) {
		this.district = district;
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


	public MasterDefState getMasterDefState() {
		return masterDefState;
	}

	public void setMasterDefState(MasterDefState masterDefState) {
		this.masterDefState = masterDefState;
	}

	public MasterDefProvincePending getMasterDefProvincePending() {
		return masterDefProvincePending;
	}

	public void setMasterDefProvincePending(MasterDefProvincePending masterDefProvincePending) {
		this.masterDefProvincePending = masterDefProvincePending;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
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

}
