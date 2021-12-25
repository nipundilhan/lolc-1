package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;

import lombok.Data;

/**
 * Facility Contract Details History Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		            	MenukaJ      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "facility_contracts_det_his")
@Data
public class FacilityContractDetailsHistory extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 4537474747L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "lead_info_id", nullable=false)
	private Long leadInfoId;
	
	@Column(name = "contract_no", length=100, nullable=true)
	private String contractNo;
	
	@Column(name = "facility_contracts_det_id", length=100, nullable=true)
	private Long facilityContractsDetId;
	
	@Column(name = "contract_status", length=20, nullable=true)
	private String contractStatus;
	
	@Column(name = "contract_status_des", length=100, nullable=true)
	private String contractStatusDes;
	
	@Column(name = "lese_code", nullable=true)
	private String leseCode;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=30, nullable=false)
	private CommonStatus status;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	@Column(name = "hcreated_user")
	private String hcreatedUser;
	
	@Column(name = "hcreated_date")
	private Timestamp hcreatedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getContractStatusDes() {
		return contractStatusDes;
	}

	public void setContractStatusDes(String contractStatusDes) {
		this.contractStatusDes = contractStatusDes;
	}

	public String getLeseCode() {
		return leseCode;
	}

	public void setLeseCode(String leseCode) {
		this.leseCode = leseCode;
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

	public String getHcreatedUser() {
		return hcreatedUser;
	}

	public void setHcreatedUser(String hcreatedUser) {
		this.hcreatedUser = hcreatedUser;
	}

	public Timestamp getHcreatedDate() {
		return hcreatedDate;
	}

	public void setHcreatedDate(Timestamp hcreatedDate) {
		this.hcreatedDate = hcreatedDate;
	}

	public Long getFacilityContractsDetId() {
		return facilityContractsDetId;
	}

	public void setFacilityContractsDetId(Long facilityContractsDetId) {
		this.facilityContractsDetId = facilityContractsDetId;
	}

	public Long getLeadInfoId() {
		return leadInfoId;
	}

	public void setLeadInfoId(Long leadInfoId) {
		this.leadInfoId = leadInfoId;
	}

}
