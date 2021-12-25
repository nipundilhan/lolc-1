package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Sub Product Service 
 * @author 	Sanatha
 * @Date    19-JUL-2021
 * 
 ********************************************************************************************************
 *  ###   	Date         	Story Point   	Task No    		Author      	 Description
 *-------------------------------------------------------------------------------------------------------
 *    1   	19-JUL-2021  	FXL-25      	FXL-25   		Sanatha     	 Initial Development.
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SubProductAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common.invalid-value}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String name;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String productId;
	
	@NotBlank(message = "{common.not-null}")
	private String productName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String predecessorId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String marketingStateId;
	
	@NotBlank(message = "{common.not-null}")
	private String marketingStateName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{common-date.pattern}")
	private String firstMarketedDate;
	
	@Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{common-date.pattern}")
	private String lastMarketedDate;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String stateTenureLength;
	
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String stateTenurePeriodId;
	
	
	private String stateTenurePeriodName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String featureBenifitTemplateId;
	
	private String featureBenifitTemplateName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String eligibilityId;
	
	private String eligibilityName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String interestTemplateId;
	
	private String interestTemplateName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String repaymentId;
	
	private String repaymentName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String coreProductId;
	
	private String coreProductName;
	
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String feeChargeId;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String loanApplicableRangeId;
	
	

	public String getLoanApplicableRangeId() {
		return loanApplicableRangeId;
	}


	public void setLoanApplicableRangeId(String loanApplicableRangeId) {
		this.loanApplicableRangeId = loanApplicableRangeId;
	}


	public String getFeeChargeId() {
		return feeChargeId;
	}


	public void setFeeChargeId(String feeChargeId) {
		this.feeChargeId = feeChargeId;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getPredecessorId() {
		return predecessorId;
	}


	public void setPredecessorId(String predecessorId) {
		this.predecessorId = predecessorId;
	}


	public String getMarketingStateId() {
		return marketingStateId;
	}


	public void setMarketingStateId(String marketingStateId) {
		this.marketingStateId = marketingStateId;
	}


	public String getMarketingStateName() {
		return marketingStateName;
	}


	public void setMarketingStateName(String marketingStateName) {
		this.marketingStateName = marketingStateName;
	}


	public String getFirstMarketedDate() {
		return firstMarketedDate;
	}


	public void setFirstMarketedDate(String firstMarketedDate) {
		this.firstMarketedDate = firstMarketedDate;
	}


	public String getLastMarketedDate() {
		return lastMarketedDate;
	}


	public void setLastMarketedDate(String lastMarketedDate) {
		this.lastMarketedDate = lastMarketedDate;
	}


	public String getStateTenureLength() {
		return stateTenureLength;
	}


	public void setStateTenureLength(String stateTenureLength) {
		this.stateTenureLength = stateTenureLength;
	}


	public String getStateTenurePeriodId() {
		return stateTenurePeriodId;
	}


	public void setStateTenurePeriodId(String stateTenurePeriodId) {
		this.stateTenurePeriodId = stateTenurePeriodId;
	}


	public String getStateTenurePeriodName() {
		return stateTenurePeriodName;
	}


	public void setStateTenurePeriodName(String stateTenurePeriodName) {
		this.stateTenurePeriodName = stateTenurePeriodName;
	}


	public String getFeatureBenifitTemplateId() {
		return featureBenifitTemplateId;
	}


	public void setFeatureBenifitTemplateId(String featureBenifitTemplateId) {
		this.featureBenifitTemplateId = featureBenifitTemplateId;
	}


	public String getFeatureBenifitTemplateName() {
		return featureBenifitTemplateName;
	}


	public void setFeatureBenifitTemplateName(String featureBenifitTemplateName) {
		this.featureBenifitTemplateName = featureBenifitTemplateName;
	}


	public String getEligibilityId() {
		return eligibilityId;
	}


	public void setEligibilityId(String eligibilityId) {
		this.eligibilityId = eligibilityId;
	}


	public String getEligibilityName() {
		return eligibilityName;
	}


	public void setEligibilityName(String eligibilityName) {
		this.eligibilityName = eligibilityName;
	}


	public String getInterestTemplateId() {
		return interestTemplateId;
	}


	public void setInterestTemplateId(String interestTemplateId) {
		this.interestTemplateId = interestTemplateId;
	}


	public String getInterestTemplateName() {
		return interestTemplateName;
	}


	public void setInterestTemplateName(String interestTemplateName) {
		this.interestTemplateName = interestTemplateName;
	}


	public String getRepaymentId() {
		return repaymentId;
	}


	public void setRepaymentId(String repaymentId) {
		this.repaymentId = repaymentId;
	}


	public String getRepaymentName() {
		return repaymentName;
	}


	public void setRepaymentName(String repaymentName) {
		this.repaymentName = repaymentName;
	}


	public String getCoreProductId() {
		return coreProductId;
	}


	public void setCoreProductId(String coreProductId) {
		this.coreProductId = coreProductId;
	}


	public String getCoreProductName() {
		return coreProductName;
	}


	public void setCoreProductName(String coreProductName) {
		this.coreProductName = coreProductName;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
