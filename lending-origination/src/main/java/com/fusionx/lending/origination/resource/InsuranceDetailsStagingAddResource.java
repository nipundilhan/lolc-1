package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * Insurance Details Staging Service
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

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class InsuranceDetailsStagingAddResource {
	
	private String tenantId;
	
	private String insuranceDetails;
	
	private String assetsEntity;
	
	private String insuranceType;
	
	
	private String insuranceCompanyMaint;
	
	
	private String insuranceSubType;
	
	
	private String policyNo;
	
	
	private String policyCoverPeriodFrom;
	
	
	private String policyCoverPeriodTo;
	
	
	private String coverNoteNumber;
	
	
	private String coverNotePeriodFrom;
	

	private String coverNotePeriodTo;
	
	
	private String coverNoteStatus;
	

	private String insuranceCoverType;
	

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

	private String status;
	
	private String createdUser;
	
	private String approveStatus;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getInsuranceDetails() {
		return insuranceDetails;
	}

	public void setInsuranceDetails(String insuranceDetails) {
		this.insuranceDetails = insuranceDetails;
	}

	public String getAssetsEntity() {
		return assetsEntity;
	}

	public void setAssetsEntity(String assetsEntity) {
		this.assetsEntity = assetsEntity;
	}

	public String getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public String getInsuranceCompanyMaint() {
		return insuranceCompanyMaint;
	}

	public void setInsuranceCompanyMaint(String insuranceCompanyMaint) {
		this.insuranceCompanyMaint = insuranceCompanyMaint;
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

	public String getInsuranceCoverType() {
		return insuranceCoverType;
	}

	public void setInsuranceCoverType(String insuranceCoverType) {
		this.insuranceCoverType = insuranceCoverType;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	
	

}
