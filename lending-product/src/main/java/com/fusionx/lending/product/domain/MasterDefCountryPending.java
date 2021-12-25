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
@Table(name = "master_def_country_pending")
//@Data
public class MasterDefCountryPending extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1050154021482885778L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "master_def_country_id", nullable = true)
	private MasterDefCountry masterDefCountry;
		
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "mst_def_pend_id", nullable = false)
	private MasterDefinitionPending masterDefinitionPending;
	
	@Column(name = "country_id", nullable=false)
	private Long countryId; 
	
	@Column(name = "country_name", length = 350, nullable = false)
	private String countryName;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length = 20, nullable = false)
	private CommonStatus status;

	@Column(name = "pcreated_user", length = 255, nullable = false)
	private String penCreatedUser;

	@Column(name = "pcreated_date", nullable = false)
	private Timestamp penCreatedDate;
	
	@Transient
	private List<MasterDefProvincePending> province;

	public List<MasterDefProvincePending> getProvince() {
		return province;
	}

	public void setProvince(List<MasterDefProvincePending> province) {
		this.province = province;
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
	
	public MasterDefCountry getMasterDefCountry() {
		return masterDefCountry;
	}

	public void setMasterDefCountry(MasterDefCountry masterDefCountry) {
		this.masterDefCountry = masterDefCountry;
	}

	public MasterDefinitionPending getMasterDefinitionPending() {
		return masterDefinitionPending;
	}

	public void setMasterDefinitionPending(MasterDefinitionPending masterDefinitionPending) {
		this.masterDefinitionPending = masterDefinitionPending;
	}
	
	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
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
