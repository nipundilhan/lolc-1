package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ValidateResource {
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("username")
	private String username;
	
	@JsonProperty("userStatus")
	private String userStatus;
	
	@JsonProperty("empId")
	private String empId;
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("version")
	private String version;
	
	@JsonProperty("code")
	private String code;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("frequencyTypeId")
	private String frequencyTypeId;
	
	@JsonProperty("typeId")
	private String typeId;

	@JsonProperty("amount")
	private String amount;
	
	@JsonProperty("textual")
	private String textual;
	
	@JsonProperty("eligibilityId")
	private String eligibilityId;
	
	@JsonProperty("officerTypeId")
	private String officerTypeId;
	
	@JsonProperty("featureBenefitTemplateId")
	private String featureBenefitTemplateId;

	@JsonProperty("featureBenefitTemplatePendingId")
	private String featureBenefitTemplatePendingId;

	@JsonProperty("featureBenefitTemplateItemId")
	private String featureBenefitTemplateItemId;

	@JsonProperty("featureBenefitItemId")
	private String featureBenefitItemId;

	@JsonProperty("featureBenefitTemplateItemEligibilityId")
	private String featureBenefitTemplateItemEligibilityId;

	@JsonProperty("featureBenefitEligibilityId")
	private String featureBenefitEligibilityId;
	

	@JsonProperty("applicationFrequencyId")
	private String applicationFrequencyId;
	
	@JsonProperty("applicationFrequencyName")
	private String applicationFrequencyName;
	
	@JsonProperty("calculationFrequencyId")
	private String calculationFrequencyId;
	
	@JsonProperty("calculationFrequencyName")
	private String calculationFrequencyName;

	@JsonProperty("feeTypeId")
	private String feeTypeId;
	
	@JsonProperty("feeTypeName")
	private String feeTypeName;
	
	@JsonProperty("feeCategoryId")
	private String feeCategoryId;

	@JsonProperty("feeCategoryName")
	private String feeCategoryName;
	
	@JsonProperty("feeChargeDetailAdhocId")
	private String feeChargeDetailAdhocId;

	@JsonProperty("masterDefinitionId")
	private String masterDefinitionId;

	@JsonProperty("masterDefinitionPendingId")
	private String masterDefinitionPendingId;
	
	@JsonProperty("dueDateTypeList")
	private String dueDateTypeList;
	
	@JsonProperty("serviceAccessChannelId")
	private String serviceAccessChannelId;
	
	@JsonProperty("salesAccessChannelId")
	private String salesAccessChannelId;

	@JsonProperty("dueDateTemplateId")
	private String dueDateTemplateId;

	@JsonProperty("dueDateTemplatecode")
	private String dueDateTemplatecode;
	
	@JsonProperty("documentId")
	private String documentId;
	
	@JsonProperty("documentName")
	private String documentName;
	
	@JsonProperty("transactionSubCodeId")
	private String transactionSubCodeId;
	
	
	@JsonProperty("branchId")
	private String branchId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("empNo")
	private String empNo;
	
	@JsonProperty("empName")
	private String empName;

	@JsonProperty("otherFeeType")
	private String otherFeeType;
	
	@JsonProperty("riskTemplateId")
	private String riskTemplateId;
	
	@JsonProperty("feeChargeName")
	private String feeChargeName;
	@JsonProperty("customerType")
	private String customerType;

	@JsonProperty("feeChargeId")
	private String feeChargeId;
	
	@JsonProperty("rateTypeId")
	private String rateTypeId;
	
	@JsonProperty("rateTypeName")
	private String rateTypeName;
	
	@JsonProperty("rate")
	private String rate;
	
	@JsonProperty("productId")
	private String productId;
	
	@JsonProperty("subProductId")
	private String subProductId;
		
	@JsonProperty("repaymentFrequencyId")
	private String repaymentFrequencyId;

	@JsonProperty("eligibilityMinAmount")
	private String eligibilityMinAmount;

	@JsonProperty("eligibilityMaxAmount")
	private String eligibilityMaxAmount;
	
	@JsonProperty("eligibilityOtherId")
	private String eligibilityOtherId;
	
	@JsonProperty("eligibilityPendingId")
	private String eligibilityPendingId;

	@JsonProperty("otherEligibilityTypeId")
	private String otherEligibilityTypeId;
	
	@JsonProperty("disbursementId")
	private String disbursementId;
		
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSubProductId() {
		return subProductId;
	}

	public void setSubProductId(String subProductId) {
		this.subProductId = subProductId;
	}

	public String getTransactionSubCodeId() {
		return transactionSubCodeId;
	}

	public void setTransactionSubCodeId(String transactionSubCodeId) {
		this.transactionSubCodeId = transactionSubCodeId;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMasterDefinitionId() {
		return masterDefinitionId;
	}

	public void setMasterDefinitionId(String masterDefinitionId) {
		this.masterDefinitionId = masterDefinitionId;
	}

	public String getMasterDefinitionPendingId() {
		return masterDefinitionPendingId;
	}

	public void setMasterDefinitionPendingId(String masterDefinitionPendingId) {
		this.masterDefinitionPendingId = masterDefinitionPendingId;
	}

	public String getFeeChargeDetailAdhocId() {
		return feeChargeDetailAdhocId;
	}

	public void setFeeChargeDetailAdhocId(String feeChargeDetailAdhocId) {
		this.feeChargeDetailAdhocId = feeChargeDetailAdhocId;
	}

	public String getApplicationFrequencyId() {
		return applicationFrequencyId;
	}

	public void setApplicationFrequencyId(String applicationFrequencyId) {
		this.applicationFrequencyId = applicationFrequencyId;
	}

	public String getApplicationFrequencyName() {
		return applicationFrequencyName;
	}

	public void setApplicationFrequencyName(String applicationFrequencyName) {
		this.applicationFrequencyName = applicationFrequencyName;
	}

	public String getCalculationFrequencyId() {
		return calculationFrequencyId;
	}

	public void setCalculationFrequencyId(String calculationFrequencyId) {
		this.calculationFrequencyId = calculationFrequencyId;
	}

	public String getCalculationFrequencyName() {
		return calculationFrequencyName;
	}

	public void setCalculationFrequencyName(String calculationFrequencyName) {
		this.calculationFrequencyName = calculationFrequencyName;
	}

	public String getFeeTypeId() {
		return feeTypeId;
	}

	public void setFeeTypeId(String feeTypeId) {
		this.feeTypeId = feeTypeId;
	}

	public String getFeeTypeName() {
		return feeTypeName;
	}

	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}

	public String getFeeCategoryId() {
		return feeCategoryId;
	}

	public void setFeeCategoryId(String feeCategoryId) {
		this.feeCategoryId = feeCategoryId;
	}

	public String getFeeCategoryName() {
		return feeCategoryName;
	}

	public void setFeeCategoryName(String feeCategoryName) {
		this.feeCategoryName = feeCategoryName;
	}

	public String getFrequencyTypeId() {
		return frequencyTypeId;
	}

	public void setFrequencyTypeId(String frequencyTypeId) {
		this.frequencyTypeId = frequencyTypeId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getEligibilityId() {
		return eligibilityId;
	}

	public void setEligibilityId(String eligibilityId) {
		this.eligibilityId = eligibilityId;
	}

	public String getOfficerTypeId() {
		return officerTypeId;
	}

	public void setOfficerTypeId(String officerTypeId) {
		this.officerTypeId = officerTypeId;
	}
	
	public String getFeatureBenefitTemplateId() {
		return featureBenefitTemplateId;
	}

	public void setFeatureBenefitTemplateId(String featureBenefitTemplateId) {
		this.featureBenefitTemplateId = featureBenefitTemplateId;
	}

	public String getFeatureBenefitTemplatePendingId() {
		return featureBenefitTemplatePendingId;
	}

	public void setFeatureBenefitTemplatePendingId(String featureBenefitTemplatePendingId) {
		this.featureBenefitTemplatePendingId = featureBenefitTemplatePendingId;
	}

	public String getFeatureBenefitTemplateItemId() {
		return featureBenefitTemplateItemId;
	}

	public void setFeatureBenefitTemplateItemId(String featureBenefitTemplateItemId) {
		this.featureBenefitTemplateItemId = featureBenefitTemplateItemId;
	}

	public String getFeatureBenefitItemId() {
		return featureBenefitItemId;
	}

	public void setFeatureBenefitItemId(String featureBenefitItemId) {
		this.featureBenefitItemId = featureBenefitItemId;
	}

	public String getFeatureBenefitTemplateItemEligibilityId() {
		return featureBenefitTemplateItemEligibilityId;
	}

	public void setFeatureBenefitTemplateItemEligibilityId(String featureBenefitTemplateItemEligibilityId) {
		this.featureBenefitTemplateItemEligibilityId = featureBenefitTemplateItemEligibilityId;
	}

	public String getFeatureBenefitEligibilityId() {
		return featureBenefitEligibilityId;
	}

	public void setFeatureBenefitEligibilityId(String featureBenefitEligibilityId) {
		this.featureBenefitEligibilityId = featureBenefitEligibilityId;
	}

	public String getDueDateTypeList() {
		return dueDateTypeList;
	}

	public void setDueDateTypeList(String dueDateTypeList) {
		this.dueDateTypeList = dueDateTypeList;
	}
	
	public String getServiceAccessChannelId() {
		return serviceAccessChannelId;
	}

	public void setServiceAccessChannelId(String serviceAccessChannelId) {
		this.serviceAccessChannelId = serviceAccessChannelId;
	}

	public String getSalesAccessChannelId() {
		return salesAccessChannelId;
	}

	public void setSalesAccessChannelId(String salesAccessChannelId) {
		this.salesAccessChannelId = salesAccessChannelId;
	}

	public String getDueDateTemplateId() {
		return dueDateTemplateId;
	}

	public void setDueDateTemplateId(String dueDateTemplateId) {
		this.dueDateTemplateId = dueDateTemplateId;
	}

	public String getDueDateTemplatecode() {
		return dueDateTemplatecode;
	}

	public void setDueDateTemplatecode(String dueDateTemplatecode) {
		this.dueDateTemplatecode = dueDateTemplatecode;
	}

	public String getTextual() {
		return textual;
	}

	public void setTextual(String textual) {
		this.textual = textual;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getOtherFeeType() {
		return otherFeeType;
	}

	public void setOtherFeeType(String otherFeeType) {
		this.otherFeeType = otherFeeType;
	}

	public String getFeeChargeId() {
		return feeChargeId;
	}

	public void setFeeChargeId(String feeChargeId) {
		this.feeChargeId = feeChargeId;
	}

	public String getFeeChargeName() {
		return feeChargeName;
	}

	public void setFeeChargeName(String feeChargeName) {
		this.feeChargeName = feeChargeName;
	}
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getRateTypeName() {
		return rateTypeName;
	}

	public void setRateTypeName(String rateTypeName) {
		this.rateTypeName = rateTypeName;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getRateTypeId() {
		return rateTypeId;
	}

	public void setRateTypeId(String rateTypeId) {
		this.rateTypeId = rateTypeId;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getRepaymentFrequencyId() {
		return repaymentFrequencyId;
	}

	public void setRepaymentFrequencyId(String repaymentFrequencyId) {
		this.repaymentFrequencyId = repaymentFrequencyId;
	}

	public String getRiskTemplateId() {
		return riskTemplateId;
	}

	public void setRiskTemplateId(String riskTemplateId) {
		this.riskTemplateId = riskTemplateId;
	}

	public String getEligibilityMinAmount() {
		return eligibilityMinAmount;
	}

	public void setEligibilityMinAmount(String eligibilityMinAmount) {
		this.eligibilityMinAmount = eligibilityMinAmount;
	}

	public String getEligibilityMaxAmount() {
		return eligibilityMaxAmount;
	}

	public void setEligibilityMaxAmount(String eligibilityMaxAmount) {
		this.eligibilityMaxAmount = eligibilityMaxAmount;
	}

	public String getEligibilityOtherId() {
		return eligibilityOtherId;
	}

	public void setEligibilityOtherId(String eligibilityOtherId) {
		this.eligibilityOtherId = eligibilityOtherId;
	}

	public String getEligibilityPendingId() {
		return eligibilityPendingId;
	}

	public void setEligibilityPendingId(String eligibilityPendingId) {
		this.eligibilityPendingId = eligibilityPendingId;
	}

	public String getOtherEligibilityTypeId() {
		return otherEligibilityTypeId;
	}

	public void setOtherEligibilityTypeId(String otherEligibilityTypeId) {
		this.otherEligibilityTypeId = otherEligibilityTypeId;
	}

	public String getDisbursementId() {
		return disbursementId;
	}

	public void setDisbursementId(String disbursementId) {
		this.disbursementId = disbursementId;
	}
	
}
