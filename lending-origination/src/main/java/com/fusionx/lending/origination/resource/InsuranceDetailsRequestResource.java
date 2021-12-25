package com.fusionx.lending.origination.resource;
/**
 * Insurance Details Service
 * @author Sanatha
 * @Date 16-SEP-2020
 *
 *********************************************************************************************************
 *  ###        Date                  Story Point         Task No              Author           Description
 *-------------------------------------------------------------------------------------------------------
 *    1        16-SEP-2020   		 FX-4293             FX-4693              Sanatha         Initial Development.
 *    
 ********************************************************************************************************
 */
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.enums.CommonStatus;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class InsuranceDetailsRequestResource {
	
	private String id;
	private String tenantId;

	private String insuranceSubType;
	private String policyNo;
	private String policyCoverPeriodFrom;
	private String policyCoverPeriodTo;
	private String coverNoteNumber;
	private String coverNotePeriodFrom;
	private String coverNotePeriodTo;
	private String coverNoteStatus;	
	private String applicableCurrencyId;
	private String insuranceAmount;
	private String premiumAmount;
	private String paidAmount;
	private String paymentStatus;
	private String paidBy;
	private String outstandingAmount;
	private String taxes;
	private String charges;
	private String nextRenewalDate;
	private String specialRemark;
	private CommonStatus status;
	private String createdUser;
	private String createdDate;
	private String modifiedUser;
	private String modifiedDate;
	private String ApproveStatus;
	private String approvedUser;
	private String approvedDate;
	private String rejectedUser;
	private String rejectedDate;
	private String remark;

	private String assetEntityId;
	private String insuranceTypeId;
	private String insuranceCompanyMaintId;
	private String insuranceCoverTypeId;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public String getInsuranceSubType() {
		return insuranceSubType;
	}

	public void setInsuranceSubType(String insuranceSubType) {
		this.insuranceSubType = insuranceSubType;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getPolicyCoverPeriodFrom() {
		return policyCoverPeriodFrom;
	}

	public void setPolicyCoverPeriodFrom(String policyCoverPeriodFrom) {
		this.policyCoverPeriodFrom = policyCoverPeriodFrom;
	}

	public String getPolicyCoverPeriodTo() {
		return policyCoverPeriodTo;
	}

	public void setPolicyCoverPeriodTo(String policyCoverPeriodTo) {
		this.policyCoverPeriodTo = policyCoverPeriodTo;
	}

	public String getCoverNoteNumber() {
		return coverNoteNumber;
	}

	public void setCoverNoteNumber(String coverNoteNumber) {
		this.coverNoteNumber = coverNoteNumber;
	}

	public String getCoverNotePeriodFrom() {
		return coverNotePeriodFrom;
	}

	public void setCoverNotePeriodFrom(String coverNotePeriodFrom) {
		this.coverNotePeriodFrom = coverNotePeriodFrom;
	}

	public String getCoverNotePeriodTo() {
		return coverNotePeriodTo;
	}

	public void setCoverNotePeriodTo(String coverNotePeriodTo) {
		this.coverNotePeriodTo = coverNotePeriodTo;
	}

	public String getCoverNoteStatus() {
		return coverNoteStatus;
	}

	public void setCoverNoteStatus(String coverNoteStatus) {
		this.coverNoteStatus = coverNoteStatus;
	}

	public String getApplicableCurrencyId() {
		return applicableCurrencyId;
	}

	public void setApplicableCurrencyId(String applicableCurrencyId) {
		this.applicableCurrencyId = applicableCurrencyId;
	}

	public String getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(String insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public String getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(String premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public String getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}

	public String getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(String outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public String getTaxes() {
		return taxes;
	}

	public void setTaxes(String taxes) {
		this.taxes = taxes;
	}

	public String getCharges() {
		return charges;
	}

	public void setCharges(String charges) {
		this.charges = charges;
	}

	public String getNextRenewalDate() {
		return nextRenewalDate;
	}

	public void setNextRenewalDate(String nextRenewalDate) {
		this.nextRenewalDate = nextRenewalDate;
	}

	public String getSpecialRemark() {
		return specialRemark;
	}

	public void setSpecialRemark(String specialRemark) {
		this.specialRemark = specialRemark;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getApproveStatus() {
		return ApproveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		ApproveStatus = approveStatus;
	}

	public String getApprovedUser() {
		return approvedUser;
	}

	public void setApprovedUser(String approvedUser) {
		this.approvedUser = approvedUser;
	}

	public String getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getRejectedUser() {
		return rejectedUser;
	}

	public void setRejectedUser(String rejectedUser) {
		this.rejectedUser = rejectedUser;
	}

	public String getRejectedDate() {
		return rejectedDate;
	}

	public void setRejectedDate(String rejectedDate) {
		this.rejectedDate = rejectedDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAssetEntityId() {
		return assetEntityId;
	}

	public void setAssetEntityId(String assetEntityId) {
		this.assetEntityId = assetEntityId;
	}

	public String getInsuranceTypeId() {
		return insuranceTypeId;
	}

	public void setInsuranceTypeId(String insuranceTypeId) {
		this.insuranceTypeId = insuranceTypeId;
	}

	public String getInsuranceCompanyMaintId() {
		return insuranceCompanyMaintId;
	}

	public void setInsuranceCompanyMaintId(String insuranceCompanyMaintId) {
		this.insuranceCompanyMaintId = insuranceCompanyMaintId;
	}

	public String getInsuranceCoverTypeId() {
		return insuranceCoverTypeId;
	}

	public void setInsuranceCoverTypeId(String insuranceCoverTypeId) {
		this.insuranceCoverTypeId = insuranceCoverTypeId;
	}

}
