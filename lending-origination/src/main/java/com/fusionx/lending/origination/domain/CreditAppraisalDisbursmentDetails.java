package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;

import lombok.Data;



@Entity
@Data
@Table(name = "credit_appraisal_disbursement_details")
public class CreditAppraisalDisbursmentDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 9990000001L;

	@Column(name = "id")
	private Long id;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name = "loan_amount")
	private Double loanAmount;
	
	@Column(name = "deductions")
	private Double deductions;
	
	@Column(name = "balance_disbursement_amount")
	private Double balanceDisbursementAmount;	
	
	@Column(name = "schedule_number")
	private String scheduleNo;
	
	@Column(name = "payee_type_id")
	private Long payeeTypeId;
	
	@Column(name = "payee_type")
	private String payeeType;
		
	@Column(name = "payee_name")
	private String PayeeName;
	
	@Column(name = "amount")
	private Double amount;
		
	@Column(name = "pay_method_id")
	private Long payMethodId; 
	
	@Column(name = "pay_method")
	private String payMethod; 
	
	@Column(name = "disbursement_condition_id")
	private Long disbursementConditionId; 
	
	@Column(name = "disbursement_condition")
	private String disbursementCondition; 
		
	@Column(name = "bank_id")
	private Long bankId; 
	
	@Column(name = "bank")
	private String bank;
	
	@Column(name = "branch_id")
	private Long branchId; 
		
	@Column(name = "branch")
	private String branch;
	
	@Column(name = "account_no")
	private String accountNo;
	
	@Column(name = "comment")
	private String comment;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	@Column(name = "version")
	private Long version;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Double getDeductions() {
		return deductions;
	}

	public void setDeductions(Double deductions) {
		this.deductions = deductions;
	}

	public Double getBalanceDisbursementAmount() {
		return balanceDisbursementAmount;
	}

	public void setBalanceDisbursementAmount(Double balanceDisbursementAmount) {
		this.balanceDisbursementAmount = balanceDisbursementAmount;
	}

	public String getScheduleNo() {
		return scheduleNo;
	}

	public void setScheduleNo(String scheduleNo) {
		this.scheduleNo = scheduleNo;
	}

	public Long getPayeeTypeId() {
		return payeeTypeId;
	}

	public void setPayeeTypeId(Long payeeTypeId) {
		this.payeeTypeId = payeeTypeId;
	}

	public String getPayeeType() {
		return payeeType;
	}

	public void setPayeeType(String payeeType) {
		this.payeeType = payeeType;
	}

	public String getPayeeName() {
		return PayeeName;
	}

	public void setPayeeName(String payeeName) {
		PayeeName = payeeName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getPayMethodId() {
		return payMethodId;
	}

	public void setPayMethodId(Long payMethodId) {
		this.payMethodId = payMethodId;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public Long getDisbursementConditionId() {
		return disbursementConditionId;
	}

	public void setDisbursementConditionId(Long disbursementConditionId) {
		this.disbursementConditionId = disbursementConditionId;
	}

	public String getDisbursementCondition() {
		return disbursementCondition;
	}

	public void setDisbursementCondition(String disbursementCondition) {
		this.disbursementCondition = disbursementCondition;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}
	
	
	
	
	
}
