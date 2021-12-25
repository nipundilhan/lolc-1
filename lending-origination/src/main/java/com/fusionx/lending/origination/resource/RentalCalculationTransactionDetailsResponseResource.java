package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RentalCalculationTransactionDetailsResponseResource {
	
	private String addcriteriaCode;
	private String addcriteriaName;
	private String chargeAmount;
	private String createdDate;
	private String createdUser;
	private String quotationNumber;
	private String txnTypeCode;
	private String txnTypeName;
	
	public String getAddcriteriaCode() {
		return addcriteriaCode;
	}
	public void setAddcriteriaCode(String addcriteriaCode) {
		this.addcriteriaCode = addcriteriaCode;
	}
	public String getAddcriteriaName() {
		return addcriteriaName;
	}
	public void setAddcriteriaName(String addcriteriaName) {
		this.addcriteriaName = addcriteriaName;
	}
	public String getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(String chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public String getQuotationNumber() {
		return quotationNumber;
	}
	public void setQuotationNumber(String quotationNumber) {
		this.quotationNumber = quotationNumber;
	}
	public String getTxnTypeCode() {
		return txnTypeCode;
	}
	public void setTxnTypeCode(String txnTypeCode) {
		this.txnTypeCode = txnTypeCode;
	}
	public String getTxnTypeName() {
		return txnTypeName;
	}
	public void setTxnTypeName(String txnTypeName) {
		this.txnTypeName = txnTypeName;
	}
	
	

}
