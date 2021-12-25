package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.FinancialCommitmentCategory;

import lombok.Data;

/**
 *  ExternalLiability Domain
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-08-2020      		     FXL-414 	    NipunDilhan  Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "external_liability")
@Data
public class ExternalLiability  extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6570160972603385840L;
	

	@Column(name = "tenant_id", nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_id", nullable=true)
	private Customer customer;	
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "category_code", length=20, nullable=false)
	private FinancialCommitmentCategory categoryCode;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "commitment_type_id", nullable=false)  
	private CommonList commonListCommitmentType;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "facility_type_id", nullable=false)  
	private CommonList commonListFacilityType;
	
	@Column(name = "bank_id", nullable=false)
	private Long bankId;
	
	@Column(name = "bank_code", length=20, nullable=false)
	private String bankCode;
	
	@Column(name = "bank_name", length=250, nullable=false)
	private String bankName;
	
	@Column(name = "branch_id", nullable=false)
	private Long branchId;
	
	@Column(name = "branch_code", length=20, nullable=false)
	private String branchCode;

	@Column(name = "branch_name", length=250, nullable=false)
	private String branchName;
	
	@Column(name = "repayment_frequency_id", nullable=false)
	private Long repaymentFrequencyId;
	
	@Column(name = "repayment_frequency_code", length=4, nullable=false)
	private String repaymentFrequencyCode;
	
	@Column(name = "repayment_frequency_name", length=70, nullable=false)
	private String repaymentFrequencyName;
	
	@Column(name = "outstanding_amount",  nullable=false)
	private BigDecimal outstandingAmount;
	
	@Column(name = "no_of_rental_paid", nullable=true)
	private Long noOfRentalPaid;	

	@Column(name = "rental_amount",  nullable=false)
	private BigDecimal rentalAmount;
	
	@Column(name = "facility_disbursement_date", nullable=true)
	private Timestamp facilityDisbursementDate;
	
	@Column(name = "facility_amount",  nullable=false)
	private BigDecimal facilityAmount;
	
	@Column(name = "interest_rate",  nullable=false)
	private BigDecimal interestRate;	

	@Column(name = "overdue_amount",  nullable=false)
	private BigDecimal overdueAmount;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable=false, length=20)
	private CommonStatus status;
	
	@Column(name = "over_due_note", length=350, nullable=true)
	private String overDueNote;
	
	@Column(name = "remark", length=350, nullable=true)
	private String remark;

	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;
	
	@Transient
	private Long commonListCommitmentTypeId;
	
	@Transient
	private String commonListCommitmentTypeName;
	
	@Transient
	private Long commonListFacilityTypeId;
	
	@Transient
	private String commonListFacilityTypeName;

	public Long getCommonListCommitmentTypeId() {
		if(commonListCommitmentType != null) {
			return commonListCommitmentType.getId();
		} else {
			return null;
		}
	}

	public String getCommonListCommitmentTypeName() {
		if(commonListCommitmentType != null) {
			return commonListCommitmentType.getName();
		} else {
			return null;
		}
	}
	
	public Long getCommonListFacilityTypeId() {
		if(commonListFacilityType != null) {
			return commonListFacilityType.getId();
		} else {
			return null;
		}
	}

	public String getCommonListFacilityTypeName() {
		if(commonListFacilityType != null) {
			return commonListFacilityType.getName();
		} else {
			return null;
		}
	}


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

	public FinancialCommitmentCategory getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(FinancialCommitmentCategory categoryCode) {
		this.categoryCode = categoryCode;
	}

	public CommonList getCommonListCommitmentType() {
		return commonListCommitmentType;
	}

	public void setCommonListCommitmentType(CommonList commonListCommitmentType) {
		this.commonListCommitmentType = commonListCommitmentType;
	}

	public CommonList getCommonListFacilityType() {
		return commonListFacilityType;
	}

	public void setCommonListFacilityType(CommonList commonListFacilityType) {
		this.commonListFacilityType = commonListFacilityType;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
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

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Long getRepaymentFrequencyId() {
		return repaymentFrequencyId;
	}

	public void setRepaymentFrequencyId(Long repaymentFrequencyId) {
		this.repaymentFrequencyId = repaymentFrequencyId;
	}

	public String getRepaymentFrequencyCode() {
		return repaymentFrequencyCode;
	}

	public void setRepaymentFrequencyCode(String repaymentFrequencyCode) {
		this.repaymentFrequencyCode = repaymentFrequencyCode;
	}

	public String getRepaymentFrequencyName() {
		return repaymentFrequencyName;
	}

	public void setRepaymentFrequencyName(String repaymentFrequencyName) {
		this.repaymentFrequencyName = repaymentFrequencyName;
	}

	public BigDecimal getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(BigDecimal outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	
	public Long getNoOfRentalPaid() {
		return noOfRentalPaid;
	}

	public void setNoOfRentalPaid(Long noOfRentalPaid) {
		this.noOfRentalPaid = noOfRentalPaid;
	}
	
	public BigDecimal getRentalAmount() {
		return rentalAmount;
	}

	public void setRentalAmount(BigDecimal rentalAmount) {
		this.rentalAmount = rentalAmount;
	}

	public Timestamp getFacilityDisbursementDate() {
		return facilityDisbursementDate;
	}

	public void setFacilityDisbursementDate(Timestamp facilityDisbursementDate) {
		this.facilityDisbursementDate = facilityDisbursementDate;
	}

	public BigDecimal getFacilityAmount() {
		return facilityAmount;
	}

	public void setFacilityAmount(BigDecimal facilityAmount) {
		this.facilityAmount = facilityAmount;
	}

	
	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
	
	public BigDecimal getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(BigDecimal overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public String getOverDueNote() {
		return overDueNote;
	}

	public void setOverDueNote(String overDueNote) {
		this.overDueNote = overDueNote;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	
	
	
}
