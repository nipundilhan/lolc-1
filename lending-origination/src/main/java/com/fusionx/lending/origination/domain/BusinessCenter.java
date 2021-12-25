package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;

import lombok.Data;

/**
 * Business Center
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-08-2021      		     FX-	   Thsmokshi      Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "business_center")
@Data
public class BusinessCenter extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6570160972603385840L;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
//	
//	@Column(name = "adress_line1")
//	private String adressLine1;
//	
//	@Column(name = "adress_line2")
//	private String adressLine2;
//	
//	@Column(name = "adress_line3")
//	private String adressLine3;
//	
//	@Column(name = "adress_line4")
//	private String adressLine4;
	
	@Column(name = "collection_frequency_id")
	private Long collectionFrequencyId;
	
	@Column(name = "collection_frequency")
	private String collectionFrequency;
	
	@Column(name = "emp_id")
	private Long empId;
	
	@Column(name = "emp_no")
	private String empNo;
	
	@Column(name = "emp_user_id")
	private String empUserId;
	
	@Column(name = "branch_id")
	private Long branchId;
	
	@Column(name = "branch_code")
	private String branchCode;
	
	@Column(name = "contact_no")
	private String contactNo;
	
	@Column(name = "center_head")
	private String centerHead;
	
	@Column(name = "max_client_per_sub_center")
	private Long maxClientPerSubCenter;
	
	@Column(name = "max_no_of_sub_center")
	private Long maxNoOfSubCenter;
	
	@Column(name = "center_limit")
	private Long centerLimit;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	/*@Transient
	private String empName;*/

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
//
//	public String getAdressLine1() {
//		return adressLine1;
//	}
//
//	public void setAdressLine1(String adressLine1) {
//		this.adressLine1 = adressLine1;
//	}
//
//	public String getAdressLine2() {
//		return adressLine2;
//	}
//
//	public void setAdressLine2(String adressLine2) {
//		this.adressLine2 = adressLine2;
//	}
//
//	public String getAdressLine3() {
//		return adressLine3;
//	}
//
//	public void setAdressLine3(String adressLine3) {
//		this.adressLine3 = adressLine3;
//	}
//
//	public String getAdressLine4() {
//		return adressLine4;
//	}
//
//	public void setAdressLine4(String adressLine4) {
//		this.adressLine4 = adressLine4;
//	}

	public Long getCollectionFrequencyId() {
		return collectionFrequencyId;
	}

	public void setCollectionFrequencyId(Long collectionFrequencyId) {
		this.collectionFrequencyId = collectionFrequencyId;
	}

	public String getCollectionFrequency() {
		return collectionFrequency;
	}

	public void setCollectionFrequency(String collectionFrequency) {
		this.collectionFrequency = collectionFrequency;
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

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getCenterHead() {
		return centerHead;
	}

	public void setCenterHead(String centerHead) {
		this.centerHead = centerHead;
	}

	public Long getMaxClientPerSubCenter() {
		return maxClientPerSubCenter;
	}

	public void setMaxClientPerSubCenter(Long maxClientPerSubCenter) {
		this.maxClientPerSubCenter = maxClientPerSubCenter;
	}

	public Long getMaxNoOfSubCenter() {
		return maxNoOfSubCenter;
	}

	public void setMaxNoOfSubCenter(Long maxNoOfSubCenter) {
		this.maxNoOfSubCenter = maxNoOfSubCenter;
	}

	public Long getCenterLimit() {
		return centerLimit;
	}

	public void setCenterLimit(Long centerLimit) {
		this.centerLimit = centerLimit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public String getEmpUserId() {
		return empUserId;
	}

	public void setEmpUserId(String empUserId) {
		this.empUserId = empUserId;
	}


	
	
}
