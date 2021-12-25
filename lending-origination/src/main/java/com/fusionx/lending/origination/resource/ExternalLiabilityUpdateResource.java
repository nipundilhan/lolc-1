package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ExternalLiabilityUpdateResource {


	@NotBlank(message = "{version.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{version.pattern}")
	private String version;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "EXTERNAL|INFORMAL", message = "{common-informalExternal.pattern}")
	private String category;
	
	@NotNull(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String commitmentTypeId;
	
	@NotNull(message = "{common.not-null}")
	@Size(max = 250, message = "{common-name.size}")
	private String commitmentTypeName;
	
	@NotNull(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String facilityTypeId;
	
	@NotNull(message = "{common.not-null}")
	@Size(max = 250, message = "{common-name.size}")
	private String facilityTypeName;
	

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String bankId;
	
//	@NotBlank(message = "{code.not-null}")
//	@Size(max = 4, message = "{common.code.size}")
//	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{code.pattern}")
//	private String bankCode;
//	

	@Size(max = 250, message = "{common-name.size}")
	private String bankName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String branchId;
	
	@Size(max = 20, message = "{common-name.size}")
	private String bankBranchCode;

	@NotNull(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String repaymentFrequencyId;
	
	@Size(max = 250, message = "{common-name.size}")
	private String repaymentFrequencyName;
	
	@Pattern(regexp ="^$|\\d{1,20}\\.\\d{1,5}$", message = "{amount.pattern}") 
	private String outstandingAmount;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String noOfRentalPaid;

	@Pattern(regexp ="^$|\\d{1,20}\\.\\d{1,5}$", message = "{amount.pattern}") 
	private String rentalAmount;

//	@NotNull(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{date.pattern}")
	private String disbursementDate;
	
	@Pattern(regexp ="^$|\\d{1,20}\\.\\d{1,5}$", message = "{amount.pattern}") 
	private String facilityAmount;
	
	@Pattern(regexp = "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$",message="{rate.pattern}")
	private String interestRate;
	
	@Pattern(regexp ="^$|\\d{1,20}\\.\\d{1,5}$", message = "{amount.pattern}") 
	private String overdueAmount;
	
	@Size(max = 350, message = "{common-description.size}")
	private String note;
	
	@Size(max = 350, message = "{common-description.size}")
	private String remark;
	
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|ACTIVE|INACTIVE",message="{common-status.pattern}")
	private String status;
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCommitmentTypeId() {
		return commitmentTypeId;
	}

	public void setCommitmentTypeId(String commitmentTypeId) {
		this.commitmentTypeId = commitmentTypeId;
	}

	public String getCommitmentTypeName() {
		return commitmentTypeName;
	}

	public void setCommitmentTypeName(String commitmentTypeName) {
		this.commitmentTypeName = commitmentTypeName;
	}

	public String getFacilityTypeId() {
		return facilityTypeId;
	}

	public void setFacilityTypeId(String facilityTypeId) {
		this.facilityTypeId = facilityTypeId;
	}

	public String getFacilityTypeName() {
		return facilityTypeName;
	}

	public void setFacilityTypeName(String facilityTypeName) {
		this.facilityTypeName = facilityTypeName;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBankBranchCode() {
		return bankBranchCode;
	}

	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	public String getRepaymentFrequencyId() {
		return repaymentFrequencyId;
	}

	public void setRepaymentFrequencyId(String repaymentFrequencyId) {
		this.repaymentFrequencyId = repaymentFrequencyId;
	}

	public String getRepaymentFrequencyName() {
		return repaymentFrequencyName;
	}

	public void setRepaymentFrequencyName(String repaymentFrequencyName) {
		this.repaymentFrequencyName = repaymentFrequencyName;
	}

	public String getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(String outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public String getRentalAmount() {
		return rentalAmount;
	}

	public void setRentalAmount(String rentalAmount) {
		this.rentalAmount = rentalAmount;
	}	
	
	public String getNoOfRentalPaid() {
		return noOfRentalPaid;
	}

	public void setNoOfRentalPaid(String noOfRentalPaid) {
		this.noOfRentalPaid = noOfRentalPaid;
	}

	public String getDisbursementDate() {
		return disbursementDate;
	}

	public void setDisbursementDate(String disbursementDate) {
		this.disbursementDate = disbursementDate;
	}

	public String getFacilityAmount() {
		return facilityAmount;
	}

	public void setFacilityAmount(String facilityAmount) {
		this.facilityAmount = facilityAmount;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	public String getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
