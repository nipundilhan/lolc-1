package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.enums.ServiceStatus;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ColInetragationRequestResponceResource {

	private String value;
	private String messages;
	private String message;
	
	private String code;
	private String username;
	private ServiceStatus serviceStatus;
	private String lastValuationDate;
	private String version;
	private String tenantId;
	private String valuationDetailsId;
	private String valuerId;
	private String valuerRefCode;
	private String valuerType;
	private String valuationDate;
	private String reValuationDate;
	private String nominalValue;
	private String marketValue;
	private String haircut;
	private String remark;
	
	/***insu details ***/
	private String insuranceDetailsId;
	private String assetsEntity;
	private String insuranceType;
	private String insuranceTypeCode;
	private String insuranceCompanyMaint;
	private String insuranceCompanyMaintCode;
	private String policyNo;
	private String policyCoverPeriodFrom;
	private String policyCoverPeriodTo;
	private String coverNoteNumber;
	private String coverNotePeriodFrom;
	private String coverNotePeriodTo;	
	private String coverNoteStatus;
	private String insuranceCoverType;
	private String insuranceCoverTypeCode;
	private String applicableCurrencyId;
	private String applicableCurrencyCode;
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
	private String insuranceSubType;
	
	private Long assetEntityId;
	private Long insuranceTypeId;
	private Long insuranceCompanyMaintId;
	private Long insuranceCoverTypeId;
	
	
	private String status;
	private String modifiedUser;
	private String createdUser;
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getMessages() {
		return messages;
	}
	public void setMessages(String messages) {
		this.messages = messages;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLastValuationDate() {
		return lastValuationDate;
	}
	public void setLastValuationDate(String lastValuationDate) {
		this.lastValuationDate = lastValuationDate;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getValuationDetailsId() {
		return valuationDetailsId;
	}
	public void setValuationDetailsId(String valuationDetailsId) {
		this.valuationDetailsId = valuationDetailsId;
	}
	public String getValuerId() {
		return valuerId;
	}
	public void setValuerId(String valuerId) {
		this.valuerId = valuerId;
	}
	public String getValuerRefCode() {
		return valuerRefCode;
	}
	public void setValuerRefCode(String valuerRefCode) {
		this.valuerRefCode = valuerRefCode;
	}
	public String getValuerType() {
		return valuerType;
	}
	public void setValuerType(String valuerType) {
		this.valuerType = valuerType;
	}
	public String getValuationDate() {
		return valuationDate;
	}
	public void setValuationDate(String valuationDate) {
		this.valuationDate = valuationDate;
	}
	public String getReValuationDate() {
		return reValuationDate;
	}
	public void setReValuationDate(String reValuationDate) {
		this.reValuationDate = reValuationDate;
	}
	public String getNominalValue() {
		return nominalValue;
	}
	public void setNominalValue(String nominalValue) {
		this.nominalValue = nominalValue;
	}
	public String getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}
	public String getHaircut() {
		return haircut;
	}
	public void setHaircut(String haircut) {
		this.haircut = haircut;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getModifiedUser() {
		return modifiedUser;
	}
	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
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
	public Long getAssetEntityId() {
		return assetEntityId;
	}
	public void setAssetEntityId(Long assetEntityId) {
		this.assetEntityId = assetEntityId;
	}
	public Long getInsuranceTypeId() {
		return insuranceTypeId;
	}
	public void setInsuranceTypeId(Long insuranceTypeId) {
		this.insuranceTypeId = insuranceTypeId;
	}
	public Long getInsuranceCompanyMaintId() {
		return insuranceCompanyMaintId;
	}
	public void setInsuranceCompanyMaintId(Long insuranceCompanyMaintId) {
		this.insuranceCompanyMaintId = insuranceCompanyMaintId;
	}
	public Long getInsuranceCoverTypeId() {
		return insuranceCoverTypeId;
	}
	public void setInsuranceCoverTypeId(Long insuranceCoverTypeId) {
		this.insuranceCoverTypeId = insuranceCoverTypeId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getInsuranceDetailsId() {
		return insuranceDetailsId;
	}
	public void setInsuranceDetailsId(String insuranceDetailsId) {
		this.insuranceDetailsId = insuranceDetailsId;
	}
	public String getInsuranceSubType() {
		return insuranceSubType;
	}
	public void setInsuranceSubType(String insuranceSubType) {
		this.insuranceSubType = insuranceSubType;
	}

	
}
