package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Mobile Notebook Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-06-2021   		         FX-6506    SenithaP     Created
 *    
 ********************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MobileNotebookAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String identificationTypeId;
	private String identificationTypeCode;

	@NotBlank(message = "{common.not-null}")
	private String nicNo;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 255, message = "{common.size255}")
	private String customerFullName;
	
	@Pattern(regexp = "^$|^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String requiredLoanAmount;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String contactTypeId;
	private String contactTypeCode;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String contactNumber;

	@Size(max = 350, message = "{note.size}")
	private String customerLivingArea;
	
	private String productCode;
	
	private String productName;

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String securityTypeId;
	private String securityTypeName;

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String securitySubTypeId;
	private String securitySubTypeName;

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String spouseProfessionId;
	private String spouseProfessionName;

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String priorityLevelId;
	private String priorityLevelName;

	@Size(max = 350, message = "{common.size2000}")
	private String remarks;

	@Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{date.pattern}")
	private String nextMeetingDate;

	public String getIdentificationTypeId() {
		return identificationTypeId;
	}

	public void setIdentificationTypeId(String identificationTypeId) {
		this.identificationTypeId = identificationTypeId;
	}

	public String getIdentificationTypeCode() {
		return identificationTypeCode;
	}

	public void setIdentificationTypeCode(String identificationTypeCode) {
		this.identificationTypeCode = identificationTypeCode;
	}

	public String getNicNo() {
		return nicNo;
	}

	public void setNicNo(String nicNo) {
		this.nicNo = nicNo;
	}

	public String getCustomerFullName() {
		return customerFullName;
	}

	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	public String getRequiredLoanAmount() {
		return requiredLoanAmount;
	}

	public void setRequiredLoanAmount(String requiredLoanAmount) {
		this.requiredLoanAmount = requiredLoanAmount;
	}

	public String getContactTypeId() {
		return contactTypeId;
	}

	public void setContactTypeId(String contactTypeId) {
		this.contactTypeId = contactTypeId;
	}

	public String getContactTypeCode() {
		return contactTypeCode;
	}

	public void setContactTypeCode(String contactTypeCode) {
		this.contactTypeCode = contactTypeCode;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getCustomerLivingArea() {
		return customerLivingArea;
	}

	public void setCustomerLivingArea(String customerLivingArea) {
		this.customerLivingArea = customerLivingArea;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSecurityTypeId() {
		return securityTypeId;
	}

	public void setSecurityTypeId(String securityTypeId) {
		this.securityTypeId = securityTypeId;
	}

	public String getSecurityTypeName() {
		return securityTypeName;
	}

	public void setSecurityTypeName(String securityTypeName) {
		this.securityTypeName = securityTypeName;
	}

	public String getSecuritySubTypeId() {
		return securitySubTypeId;
	}

	public void setSecuritySubTypeId(String securitySubTypeId) {
		this.securitySubTypeId = securitySubTypeId;
	}

	public String getSecuritySubTypeName() {
		return securitySubTypeName;
	}

	public void setSecuritySubTypeName(String securitySubTypeName) {
		this.securitySubTypeName = securitySubTypeName;
	}

	public String getSpouseProfessionId() {
		return spouseProfessionId;
	}

	public void setSpouseProfessionId(String spouseProfessionId) {
		this.spouseProfessionId = spouseProfessionId;
	}

	public String getSpouseProfessionName() {
		return spouseProfessionName;
	}

	public void setSpouseProfessionName(String spouseProfessionName) {
		this.spouseProfessionName = spouseProfessionName;
	}

	public String getPriorityLevelId() {
		return priorityLevelId;
	}

	public void setPriorityLevelId(String priorityLevelId) {
		this.priorityLevelId = priorityLevelId;
	}

	public String getPriorityLevelName() {
		return priorityLevelName;
	}

	public void setPriorityLevelName(String priorityLevelName) {
		this.priorityLevelName = priorityLevelName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getNextMeetingDate() {
		return nextMeetingDate;
	}

	public void setNextMeetingDate(String nextMeetingDate) {
		this.nextMeetingDate = nextMeetingDate;
	}
}
