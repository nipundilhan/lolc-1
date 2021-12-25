package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RentalCalculationRevolvingLoanDetailsResponseResource {
	
	private String approvedLimit;
	private String disbursementCancelStatus;
	private String dueDate;
	private String expiryDate;
	private String quotationNumber;
	private String remarks1;
	private String remarks2;
	private String rlCancelRemark;
	private String rlCancelUser;
	private String rlCreatedDate;
	private String rlCreatedUser;
	private String rlEffectiveDate;
	private String rlInterestRate;
	private String rlModifiedUser;
	private String rlPenalInterestRate;
	private String rlType;
	private String sequenceNumber;
	
	public String getApprovedLimit() {
		return approvedLimit;
	}
	public void setApprovedLimit(String approvedLimit) {
		this.approvedLimit = approvedLimit;
	}
	public String getDisbursementCancelStatus() {
		return disbursementCancelStatus;
	}
	public void setDisbursementCancelStatus(String disbursementCancelStatus) {
		this.disbursementCancelStatus = disbursementCancelStatus;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getQuotationNumber() {
		return quotationNumber;
	}
	public void setQuotationNumber(String quotationNumber) {
		this.quotationNumber = quotationNumber;
	}
	public String getRemarks1() {
		return remarks1;
	}
	public void setRemarks1(String remarks1) {
		this.remarks1 = remarks1;
	}
	public String getRemarks2() {
		return remarks2;
	}
	public void setRemarks2(String remarks2) {
		this.remarks2 = remarks2;
	}
	public String getRlCancelRemark() {
		return rlCancelRemark;
	}
	public void setRlCancelRemark(String rlCancelRemark) {
		this.rlCancelRemark = rlCancelRemark;
	}
	public String getRlCancelUser() {
		return rlCancelUser;
	}
	public void setRlCancelUser(String rlCancelUser) {
		this.rlCancelUser = rlCancelUser;
	}
	public String getRlCreatedDate() {
		return rlCreatedDate;
	}
	public void setRlCreatedDate(String rlCreatedDate) {
		this.rlCreatedDate = rlCreatedDate;
	}
	public String getRlCreatedUser() {
		return rlCreatedUser;
	}
	public void setRlCreatedUser(String rlCreatedUser) {
		this.rlCreatedUser = rlCreatedUser;
	}
	public String getRlEffectiveDate() {
		return rlEffectiveDate;
	}
	public void setRlEffectiveDate(String rlEffectiveDate) {
		this.rlEffectiveDate = rlEffectiveDate;
	}
	public String getRlInterestRate() {
		return rlInterestRate;
	}
	public void setRlInterestRate(String rlInterestRate) {
		this.rlInterestRate = rlInterestRate;
	}
	public String getRlModifiedUser() {
		return rlModifiedUser;
	}
	public void setRlModifiedUser(String rlModifiedUser) {
		this.rlModifiedUser = rlModifiedUser;
	}
	public String getRlPenalInterestRate() {
		return rlPenalInterestRate;
	}
	public void setRlPenalInterestRate(String rlPenalInterestRate) {
		this.rlPenalInterestRate = rlPenalInterestRate;
	}
	public String getRlType() {
		return rlType;
	}
	public void setRlType(String rlType) {
		this.rlType = rlType;
	}
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
	

}
