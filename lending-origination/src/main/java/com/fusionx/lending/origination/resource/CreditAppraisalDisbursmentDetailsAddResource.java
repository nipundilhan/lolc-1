package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * CreditAppraisalDisbursmentDetailsAddResource
 * 
 ********************************************************************************************************
 * ### Date Story Point Task No Author Description
 * -------------------------------------------------------------------------------------------------------
 * 1 06-10-2021 PasinduT Created
 * 
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CreditAppraisalDisbursmentDetailsAddResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	private double loanAmount;

	private double balanceDisbursementAmount;
	@Size(max = 70, message = "{common-name.size}")
	private String bank;

	private Long bankId;
	@Size(max = 70, message = "{common-name.size}")
	private String branch;

	private Long branchId;

	@Size(max = 350, message = "{common-description.size}")
	private String comment;

	private double deductions;

	private String disbursementCondition;

	private Long disbursementConditionId;

	private String accountNo;

	private String scheduleNo;

	private Long payeeTypeId;

	@Size(max = 70, message = "{common-name.size}")
	private String payeeType;

	@Size(max = 70, message = "{common-name.size}")
	private String payeeName;

	@Size(max = 70, message = "{common-name.size}")
	private String payMethod;

	private Long payMethodId;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getBalanceDisbursementAmount() {
		return balanceDisbursementAmount;
	}

	public void setBalanceDisbursementAmount(double balanceDisbursementAmount) {
		this.balanceDisbursementAmount = balanceDisbursementAmount;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public double getDeductions() {
		return deductions;
	}

	public void setDeductions(double deductions) {
		this.deductions = deductions;
	}

	public String getDisbursementCondition() {
		return disbursementCondition;
	}

	public void setDisbursementCondition(String disbursementCondition) {
		this.disbursementCondition = disbursementCondition;
	}

	public Long getDisbursementConditionId() {
		return disbursementConditionId;
	}

	public void setDisbursementConditionId(Long disbursementConditionId) {
		this.disbursementConditionId = disbursementConditionId;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
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
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public Long getPayMethodId() {
		return payMethodId;
	}

	public void setPayMethodId(Long payMethodId) {
		this.payMethodId = payMethodId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	
	
}
