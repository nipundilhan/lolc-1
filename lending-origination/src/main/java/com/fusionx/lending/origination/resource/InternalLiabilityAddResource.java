package com.fusionx.lending.origination.resource;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class InternalLiabilityAddResource {
	
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String facilityTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 20, message = "{common.code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{code.pattern}")
	private String facilityTypeCode;	

	@NotBlank(message = "{common.not-null}")
	@Size(max = 250, message = "{common-name.size}")
	private String facilityType;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String customerTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 20, message = "{common.code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{code.pattern}")
	private String customerTypeCode;	

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{code.pattern}")
	private String customerType;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String assetTypeId;
		
	@NotBlank(message = "{common.not-null}")
	@Size(max = 20, message = "{common.code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{code.pattern}")
	private String assetTypeCode;	

	@NotBlank(message = "{common.not-null}")
	@Size(max = 250, message = "{common-name.size}")
	private String assetType;	
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String repaymentTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 20, message = "{common.code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{code.pattern}")
	private String repaymentTypeCode;	

	@NotBlank(message = "{common.not-null}")
	@Size(max = 250, message = "{common-name.size}")
	private String repaymentType;	
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 255, message = "{common-name.size}")
	private String accountNo;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{date.pattern}")
	private String facilityIssueDate;
	
	@NotBlank(message = "{common.not-null}")
	@NotNull(message = "{common.not-null}")
	private String facilityCreatedUser;
	
	@NotBlank(message = "{common.not-null}")
	@NotNull(message = "{common.not-null}")
	@Pattern(regexp ="^$|\\d{1,20}\\.\\d{1,5}$", message = "{amount.pattern}") 
	private String facilityAmount;
	
	@NotBlank(message = "{common.not-null}")
	@NotNull(message = "{common.not-null}")
	@Pattern(regexp ="^$|\\d{1,20}\\.\\d{1,5}$", message = "{amount.pattern}") 
	private String currentInstallment;	
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp ="^$|\\d{1,20}\\.\\d{1,5}$", message = "{amount.pattern}") 
	private String currentDue;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp ="^$|\\d{1,20}\\.\\d{1,5}$", message = "{amount.pattern}") 
	private String overDue;		
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp ="^$|\\d{1,20}\\.\\d{1,5}$", message = "{amount.pattern}") 
	private String avaliableBalance;	
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String noOfRentalPaid;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 20, message = "{common-name.size}")
	private String writeOff;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 255, message = "{common-name.size}")
	private String company;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 255, message = "{common-name.size}")
	private String groupCompany;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 350, message = "{common-name.size}")
	private String note;

	public String getFacilityTypeId() {
		return facilityTypeId;
	}

	public void setFacilityTypeId(String facilityTypeId) {
		this.facilityTypeId = facilityTypeId;
	}

	public String getFacilityTypeCode() {
		return facilityTypeCode;
	}

	public void setFacilityTypeCode(String facilityTypeCode) {
		this.facilityTypeCode = facilityTypeCode;
	}

	public String getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	public String getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(String customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public String getCustomerTypeCode() {
		return customerTypeCode;
	}

	public void setCustomerTypeCode(String customerTypeCode) {
		this.customerTypeCode = customerTypeCode;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getAssetTypeId() {
		return assetTypeId;
	}

	public void setAssetTypeId(String assetTypeId) {
		this.assetTypeId = assetTypeId;
	}

	public String getAssetTypeCode() {
		return assetTypeCode;
	}

	public void setAssetTypeCode(String assetTypeCode) {
		this.assetTypeCode = assetTypeCode;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getRepaymentTypeId() {
		return repaymentTypeId;
	}

	public void setRepaymentTypeId(String repaymentTypeId) {
		this.repaymentTypeId = repaymentTypeId;
	}

	public String getRepaymentTypeCode() {
		return repaymentTypeCode;
	}

	public void setRepaymentTypeCode(String repaymentTypeCode) {
		this.repaymentTypeCode = repaymentTypeCode;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getFacilityIssueDate() {
		return facilityIssueDate;
	}

	public void setFacilityIssueDate(String facilityIssueDate) {
		this.facilityIssueDate = facilityIssueDate;
	}

	public String getFacilityCreatedUser() {
		return facilityCreatedUser;
	}

	public void setFacilityCreatedUser(String facilityCreatedUser) {
		this.facilityCreatedUser = facilityCreatedUser;
	}

	public String getFacilityAmount() {
		return facilityAmount;
	}

	public void setFacilityAmount(String facilityAmount) {
		this.facilityAmount = facilityAmount;
	}

	public String getCurrentInstallment() {
		return currentInstallment;
	}

	public void setCurrentInstallment(String currentInstallment) {
		this.currentInstallment = currentInstallment;
	}

	public String getCurrentDue() {
		return currentDue;
	}

	public void setCurrentDue(String currentDue) {
		this.currentDue = currentDue;
	}

	public String getOverDue() {
		return overDue;
	}

	public void setOverDue(String overDue) {
		this.overDue = overDue;
	}

	public String getAvaliableBalance() {
		return avaliableBalance;
	}

	public void setAvaliableBalance(String avaliableBalance) {
		this.avaliableBalance = avaliableBalance;
	}

	public String getNoOfRentalPaid() {
		return noOfRentalPaid;
	}

	public void setNoOfRentalPaid(String noOfRentalPaid) {
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
	

	
}
