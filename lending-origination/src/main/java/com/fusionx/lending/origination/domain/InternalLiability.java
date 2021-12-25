package com.fusionx.lending.origination.domain;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

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
 *  internalLiability Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-08-2020      		     FXL-413 	    NipunDilhan  Created
 *    
 *    need to be add validations in service layer and finalize the the task . at the moment of developing
 *    there was a unresolved dependancy get internal liabilities details
 ********************************************************************************************************
 */

@Entity
@Table(name = "internal_liability")
//@Data
public class InternalLiability   extends BaseEntity implements Serializable {
	


	private static final long serialVersionUID = 6570160972603385840L;
	

	@Column(name = "tenant_id", nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_id", nullable=true)
	private Customer customer;	
	
	@Column(name = "facility_type_id", nullable=false)
	private Long facilityTypeId;
	
	@Column(name = "facility_type_code", length=20, nullable=false)
	private String facilityTypeCode;
	
	@Column(name = "facility_type_name", length=250, nullable=false)
	private String facilityTypeName;
	
	
	@Column(name = "customer_type_id", nullable=false)
	private Long customerTypeId;
	
	@Column(name = "customer_type_code", length=20, nullable=false)
	private String customerTypeCode;
	
	@Column(name = "customer_type_name", length=250, nullable=false)
	private String customerTypeName;
	
	
	@Column(name = "security_type_id", nullable=true)
	private Long securityTypeId;
	
	@Column(name = "security_type_code", length=20, nullable=true)
	private String securityTypeCode;
	
	@Column(name = "security_type_name", length=250, nullable=true)
	private String securityTypeName;
	
	
	@Column(name = "repayment_type_id", nullable=true)
	private Long repaymentTypeId;
	
	@Column(name = "repayment_type_code", length=20, nullable=true)
	private String repaymentTypeCode;
	
	@Column(name = "repayment_type_name", length=250, nullable=true)
	private String repaymentTypeName;
	
	@Column(name = "account_id",  nullable=true)
	private Long accountId;
	
	@Column(name = "facility_account_no", length=255, nullable=false)
	private String facilityAccountNo;
	
	@Column(name = "facility_status", length=20, nullable=false)
	private String facilityStatus;
	
	@Column(name = "facility_created_user", length=255, nullable=false)
	private String facilityCreatedUser;
	
	@Column(name = "facility_created_date", nullable=false)
	private Date facilityCreatedDate;	
	
	@Column(name = "facility_amount",  nullable=false)
	private BigDecimal facilityAmount;
	
	@Column(name = " current_installment",  nullable=false)
	private BigDecimal  currentInstallment;	
	
	@Column(name = "current_due",  nullable=true)
	private BigDecimal currentDue;
	
	@Column(name = " over_due",  nullable=true)
	private BigDecimal  overDue;
	
	@Column(name = " avaliable_balance_limit",  nullable=true)
	private BigDecimal  avaliableBalanceLimit;		
	
	@Column(name = "no_of_rental_paid", nullable=false)
	private Long noOfRentalPaid;	
	
	@Column(name = "write_off", length=20, nullable=false)
	private String writeOff;
	
	@Column(name = "company", length=255, nullable=true)
	private String company;
	
	@Column(name = "group_company", length=255, nullable=true)
	private String groupCompany;
	
	@Column(name = "status", length=20, nullable=false)
	private String status;
	
	@Column(name = "note", length=350, nullable=false)
	private String note;
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;
	
	@Transient
	private Long customerId;
	
	@Transient
	private String customerName;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getFacilityTypeId() {
		return facilityTypeId;
	}

	public void setFacilityTypeId(Long facilityTypeId) {
		this.facilityTypeId = facilityTypeId;
	}

	public String getFacilityTypeCode() {
		return facilityTypeCode;
	}

	public void setFacilityTypeCode(String facilityTypeCode) {
		this.facilityTypeCode = facilityTypeCode;
	}

	public String getFacilityTypeName() {
		return facilityTypeName;
	}

	public void setFacilityTypeName(String facilityTypeName) {
		this.facilityTypeName = facilityTypeName;
	}

	public Long getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(Long customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public String getCustomerTypeCode() {
		return customerTypeCode;
	}

	public void setCustomerTypeCode(String customerTypeCode) {
		this.customerTypeCode = customerTypeCode;
	}

	public String getCustomerTypeName() {
		return customerTypeName;
	}

	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}

	public Long getSecurityTypeId() {
		return securityTypeId;
	}

	public void setSecurityTypeId(Long securityTypeId) {
		this.securityTypeId = securityTypeId;
	}

	public String getSecurityTypeCode() {
		return securityTypeCode;
	}

	public void setSecurityTypeCode(String securityTypeCode) {
		this.securityTypeCode = securityTypeCode;
	}

	public String getSecurityTypeName() {
		return securityTypeName;
	}

	public void setSecurityTypeName(String securityTypeName) {
		this.securityTypeName = securityTypeName;
	}

	public Long getRepaymentTypeId() {
		return repaymentTypeId;
	}

	public void setRepaymentTypeId(Long repaymentTypeId) {
		this.repaymentTypeId = repaymentTypeId;
	}

	public String getRepaymentTypeCode() {
		return repaymentTypeCode;
	}

	public void setRepaymentTypeCode(String repaymentTypeCode) {
		this.repaymentTypeCode = repaymentTypeCode;
	}

	public String getRepaymentTypeName() {
		return repaymentTypeName;
	}

	public void setRepaymentTypeName(String repaymentTypeName) {
		this.repaymentTypeName = repaymentTypeName;
	}

	public String getFacilityStatus() {
		return facilityStatus;
	}

	public void setFacilityStatus(String facilityStatus) {
		this.facilityStatus = facilityStatus;
	}

	public String getFacilityCreatedUser() {
		return facilityCreatedUser;
	}

	public void setFacilityCreatedUser(String facilityCreatedUser) {
		this.facilityCreatedUser = facilityCreatedUser;
	}



	public Date getFacilityCreatedDate() {
		return facilityCreatedDate;
	}

	public void setFacilityCreatedDate(Date facilityCreatedDate) {
		this.facilityCreatedDate = facilityCreatedDate;
	}

	public BigDecimal getFacilityAmount() {
		return facilityAmount;
	}

	public void setFacilityAmount(BigDecimal facilityAmount) {
		this.facilityAmount = facilityAmount;
	}

	public BigDecimal getCurrentInstallment() {
		return currentInstallment;
	}

	public void setCurrentInstallment(BigDecimal currentInstallment) {
		this.currentInstallment = currentInstallment;
	}

	public BigDecimal getCurrentDue() {
		return currentDue;
	}

	public void setCurrentDue(BigDecimal currentDue) {
		this.currentDue = currentDue;
	}

	public BigDecimal getOverDue() {
		return overDue;
	}

	public void setOverDue(BigDecimal overDue) {
		this.overDue = overDue;
	}

	public BigDecimal getAvaliableBalanceLimit() {
		return avaliableBalanceLimit;
	}

	public void setAvaliableBalanceLimit(BigDecimal avaliableBalanceLimit) {
		this.avaliableBalanceLimit = avaliableBalanceLimit;
	}

	public Long getNoOfRentalPaid() {
		return noOfRentalPaid;
	}

	public void setNoOfRentalPaid(Long noOfRentalPaid) {
		this.noOfRentalPaid = noOfRentalPaid;
	}

	public String getWriteOff() {
		return writeOff;
	}

	public void setWriteOff(String writeOff) {
		this.writeOff = writeOff;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getGroupCompany() {
		return groupCompany;
	}

	public void setGroupCompany(String groupCompany) {
		this.groupCompany = groupCompany;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public String getFacilityAccountNo() {
		return facilityAccountNo;
	}

	public void setFacilityAccountNo(String facilityAccountNo) {
		this.facilityAccountNo = facilityAccountNo;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	
	public Long getCustomerId() {
		if(customer != null) {
			return customer.getId();
		} else {
			return null;
		}
	}



	public String getCustomerName() {
		if(customer != null) {
			return customer.getFullName();
		} else {
			return null;
		}
	}
	
	
}
