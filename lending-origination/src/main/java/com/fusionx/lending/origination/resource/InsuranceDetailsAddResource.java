package com.fusionx.lending.origination.resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class InsuranceDetailsAddResource {
	
	private String insuId;
	
	private String tenantId;
	
	private String assetsEntity;
	
	@NotBlank(message = "{insuranceType.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{insuranceType.pattern}")
	private String insuranceType;
	
	@NotBlank(message = "{insuranceTypeCode.not-null}")
	private String insuranceTypeCode;
	
	@NotBlank(message = "{insuranceCompanyMaint.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{insuranceCompanyMaint.pattern}")
	private String insuranceCompanyMaint;
	
	@NotBlank(message = "{insuranceCompanyMaintCode.not-null}")
	private String insuranceCompanyMaintCode;
	
	@NotBlank(message = "{insuranceSubType.not-null}")
	@Pattern(regexp = "^$|POLICY|COVERNOTE", message = "{insuranceSubType.pattern}")
	private String insuranceSubType;
	
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{policyNo.pattern}")
	private String policyNo;
	
	@Pattern(regexp = "^$|\\d{4}(\\/)(((0)[0-9])|((1)[0-2]))(\\/)([0-2][0-9]|(3)[0-1])$", message = "{policyCoverPeriodFrom.pattern}")
	private String policyCoverPeriodFrom;
	
	@Pattern(regexp = "^$|\\d{4}(\\/)(((0)[0-9])|((1)[0-2]))(\\/)([0-2][0-9]|(3)[0-1])$", message = "{policyCoverPeriodTo.pattern}")
	private String policyCoverPeriodTo;
	
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{coverNoteNumber.pattern}")
	private String coverNoteNumber;
	
	@Pattern(regexp = "^$|\\d{4}(\\/)(((0)[0-9])|((1)[0-2]))(\\/)([0-2][0-9]|(3)[0-1])$", message = "{coverNotePeriodFrom.pattern}")
	private String coverNotePeriodFrom;
	
	@Pattern(regexp = "^$|\\d{4}(\\/)(((0)[0-9])|((1)[0-2]))(\\/)([0-2][0-9]|(3)[0-1])$", message = "{coverNotePeriodTo.pattern}")
	private String coverNotePeriodTo;
	
	private String coverNoteStatus;
	
	@NotBlank(message = "{insuranceCoverType.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{insuranceCoverType.pattern}")
	private String insuranceCoverType;
	
	@NotBlank(message = "{insuranceCoverTypeCode.not-null}")
	private String insuranceCoverTypeCode;

	@NotBlank(message = "{applicableCurrencyId.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{applicableCurrencyId.pattern}")
	private String applicableCurrencyId;
	
	@NotBlank(message = "{applicableCurrencyCode.not-null}")
	private String applicableCurrencyCode;

	@Pattern(regexp ="^$|\\d{1,20}\\.\\d{1,5}$", message = "{insuranceAmount.pattern}")
	private String insuranceAmount;
	
	@Pattern(regexp ="^$|\\d{1,20}\\.\\d{1,5}$", message = "{premiumAmount.pattern}")
	private String premiumAmount;
	

	@Pattern(regexp ="^$|\\d{1,20}\\.\\d{1,5}$", message = "{paidAmount.pattern}")
	private String paidAmount;
	
	
	@Pattern(regexp = "^$|PAID|PENDING", message = "{paymentStatus.pattern}")
	private String paymentStatus;
	
	
	@Pattern(regexp = "^$|CUSTOMER|COMPANY", message = "{paidBy.pattern}")
	private String paidBy;
	
	private String outstandingAmount;
	
	private String taxes;
	
	private String charges;
	
	@Pattern(regexp = "^$|\\d{4}(\\/)(((0)[0-9])|((1)[0-2]))(\\/)([0-2][0-9]|(3)[0-1])$", message = "{nextRenewalDate.pattern}")
	private String nextRenewalDate;
	

	private String specialRemark;

	@NotBlank(message = "{status.not-null}")
	@Pattern(regexp = "^$|ACTIVE|INACTIVE", message = "{status.pattern}")
	private String status;
	
	private String createdUser;
	
	private String version;
	
	
	private String approveStatus;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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
	
	public String getInsuranceTypeCode() {
		return insuranceTypeCode;
	}

	public void setInsuranceTypeCode(String insuranceTypeCode) {
		this.insuranceTypeCode = insuranceTypeCode;
	}

	public String getInsuranceCompanyMaint() {
		return insuranceCompanyMaint;
	}

	public void setInsuranceCompanyMaint(String insuranceCompanyMaint) {
		this.insuranceCompanyMaint = insuranceCompanyMaint;
	}
	
	

	public String getInsuranceCompanyMaintCode() {
		return insuranceCompanyMaintCode;
	}

	public void setInsuranceCompanyMaintCode(String insuranceCompanyMaintCode) {
		this.insuranceCompanyMaintCode = insuranceCompanyMaintCode;
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
	
	
	public String getInsuranceCoverTypeCode() {
		return insuranceCoverTypeCode;
	}

	public void setInsuranceCoverTypeCode(String insuranceCoverTypeCode) {
		this.insuranceCoverTypeCode = insuranceCoverTypeCode;
	}

	public String getApplicableCurrencyId() {
		return applicableCurrencyId;
	}

	public void setApplicableCurrencyId(String applicableCurrencyId) {
		this.applicableCurrencyId = applicableCurrencyId;
	}
	
	

	public String getApplicableCurrencyCode() {
		return applicableCurrencyCode;
	}

	public void setApplicableCurrencyCode(String applicableCurrencyCode) {
		this.applicableCurrencyCode = applicableCurrencyCode;
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

	public String getInsuId() {
		return insuId;
	}

	public void setInsuId(String insuId) {
		this.insuId = insuId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
		

}
