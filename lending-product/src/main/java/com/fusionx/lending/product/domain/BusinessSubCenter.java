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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;

/**
 * Business Sub Center Doamin
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   28-08-2021                 FXL-649   Nipun Dilhan      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "business_sub_center")
//@Data
public class BusinessSubCenter  extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6570160972603385840L;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@JsonIgnore	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "business_center_id", nullable=false)
	private BusinessCenter businessCenter;
		
	@Column(name = "emp_id")
	private Long empId;
	
	@Column(name = "emp_no")
	private String empNo;
	
	@Column(name = "max_client_per_sub_center")
	private Long maxClientPerSubCenter;
	
	@Column(name = "max_sub_center_limit")
	private Long maxSubCenterLimit;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable=false, length=20)
	private CommonStatus status;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "user_name")
	private String userName;

	@Transient
	private Long businessCenterIdValue;
	
	@Transient
	private String businessCenterName;


	public Long getBusinessCenterIdValue() {
		if(businessCenter != null) {
			return businessCenter.getId();
		} else {
			return null;
		}
	}

	public String getBusinessCenterName() {
		if(businessCenter != null) {
			return businessCenter.getName();
		} else {
			return null;
		}
	}

	
	public BusinessCenter getBusinessCenter() {
		return businessCenter;
	}

	public void setBusinessCenter(BusinessCenter businessCenter) {
		this.businessCenter = businessCenter;
	}

	public Long getMaxSubCenterLimit() {
		return maxSubCenterLimit;
	}

	public void setMaxSubCenterLimit(Long maxSubCenterLimit) {
		this.maxSubCenterLimit = maxSubCenterLimit;
	}

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

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public Long getMaxClientPerSubCenter() {
		return maxClientPerSubCenter;
	}

	public void setMaxClientPerSubCenter(Long maxClientPerSubCenter) {
		this.maxClientPerSubCenter = maxClientPerSubCenter;
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
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}
