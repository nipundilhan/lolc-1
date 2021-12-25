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
import com.fusionx.lending.origination.enums.PayeeType;

import lombok.Data;

/**
 * Disbursement Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6484    Sanatha      Initial Development.
 *    1   28-OCT-2021   		      FX-6484    Dilhan      
 *    
 ********************************************************************************************************
 */
@Entity
//@Data
@Table(name = "disbursement_details")
public class DisbursementDetails    extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 0000000000001;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_id")  
	private Customer customer;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lead_id")  
	private LeadInfo lead;
	
	@Transient
	private Long leadInfoId;
	
	@Column(name = "schedule_no", nullable=false)
	private Integer scheduleNo;
	
	@Column(name = "amount",columnDefinition="Decimal(38,2)", nullable=false)
	private BigDecimal amount;
	
	@Column(name = "loan_amount",columnDefinition="Decimal(38,2)", nullable=false)
	private BigDecimal loanAmount;
	
	@Column(name = "deductions",columnDefinition="Decimal(38,2)", nullable=false)
	private BigDecimal deductions;
	
	@Column(name = "balance_disbursement_amount",columnDefinition="Decimal(38,2)", nullable=false)
	private BigDecimal balanceDisbursementAmount;
	
	@JoinColumn(name = "pay_mode_id", nullable=false)
	private Long payModeId;
	
	@Column(name = "pay_mode_code", nullable=false)
	private String payModeCode;
	
//	@Transient
//	private Long payMethodId;
	
//	@Transient
//	private String payMethodCode;
	
//	@Transient
//	private String payMethodName;
	
	@Transient
	private String bankName;
	
	@Transient
	private String bankBranchName;
	
	@Column(name = "bank_id", nullable=false)
	private Long bankId;
	
	@Column(name = "bank_code", nullable=false)
	private String bankCode;
	
	@Column(name = "disbursement_condition_id", nullable=true)
	private Long disbursementConditionId;
	
	@Column(name = "disbursement_condition_code", nullable=true)
	private String disbursementConditionCode;
	
	@Column(name = "bank_branch_id",nullable=false)
	private Long bankBranchId;
	
	@Column(name = "bank_branch_code", nullable=false)
	private String bankBranchCode;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "payee_type", nullable=false)
	private PayeeType payeeType;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Column(name = "payee_name", nullable=false)
	private String payeeName;
	
	@Column(name = "account", nullable=false)
	private String account;
	
	@Column(name = "comments", length=500, nullable=true)
	private String comments;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;

	public Long getPayModeId() {
		return payModeId;
	}

	public void setPayModeId(Long payModeId) {
		this.payModeId = payModeId;
	}

	public String getPayModeCode() {
		return payModeCode;
	}

	public void setPayModeCode(String payModeCode) {
		this.payModeCode = payModeCode;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public PayeeType getPayeeType() {
		return payeeType;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setPayeeType(PayeeType payeeType) {
		this.payeeType = payeeType;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public Integer getScheduleNo() {
		return scheduleNo;
	}

	public void setScheduleNo(Integer scheduleNo) {
		this.scheduleNo = scheduleNo;
	}

	public Long getDisbursementConditionId() {
		return disbursementConditionId;
	}

	public void setDisbursementConditionId(Long disbursementConditionId) {
		this.disbursementConditionId = disbursementConditionId;
	}

	public String getDisbursementConditionCode() {
		return disbursementConditionCode;
	}

	public void setDisbursementConditionCode(String disbursementConditionCode) {
		this.disbursementConditionCode = disbursementConditionCode;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getDeductions() {
		return deductions;
	}

	public void setDeductions(BigDecimal deductions) {
		this.deductions = deductions;
	}

	public BigDecimal getBalanceDisbursementAmount() {
		return balanceDisbursementAmount;
	}

	public void setBalanceDisbursementAmount(BigDecimal balanceDisbursementAmount) {
		this.balanceDisbursementAmount = balanceDisbursementAmount;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public void setLeadInfoId(Long leadInfoId) {
		this.leadInfoId = leadInfoId;
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

	public LeadInfo getLead() {
		return lead;
	}

	public void setLead(LeadInfo lead) {
		this.lead = lead;
	}
	
	public Long getLeadInfoId() {
		if(lead.getId()!= null) {
			return lead.getId();
		}else {
			return null;
		}
	}


	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Long getBankBranchId() {
		return bankBranchId;
	}

	public void setBankBranchId(Long bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	public String getBankBranchCode() {
		return bankBranchCode;
	}

	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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
