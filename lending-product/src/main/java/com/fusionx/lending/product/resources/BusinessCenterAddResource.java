package com.fusionx.lending.product.resources;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BusinessCenterAddResource {

	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String name;	
	
	@NotBlank(message = "{common.not-null}")
	private String adressLine1;
	
	private String adressLine2;
	
	private String adressLine3;
	
	private String adressLine4;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String collectionFrequencyId;
	
	@NotBlank(message = "{common.not-null}")
	private String collectionFrequency;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String empId;
	
	@NotBlank(message = "{common.not-null}")
	private String empNo;
	
	@NotBlank(message = "{common.not-null}")
	private String empName;
	
	@NotBlank(message = "{common.not-null}")
	private String empUserId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String branchId;
	
	@NotBlank(message = "{common.not-null}")
	private String branchCode;
	
	@NotBlank(message = "{common.not-null}")
	private String branchName;
	
	private String contactNo;
	
//	@NotBlank(message = "{common.not-null}")
	private String centerHead;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String maxClientPerSubCenter;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String maxNoOfSubCenter;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String centerLimit;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

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

	public String getAdressLine1() {
		return adressLine1;
	}

	public void setAdressLine1(String adressLine1) {
		this.adressLine1 = adressLine1;
	}

	public String getAdressLine2() {
		return adressLine2;
	}

	public void setAdressLine2(String adressLine2) {
		this.adressLine2 = adressLine2;
	}

	public String getAdressLine3() {
		return adressLine3;
	}

	public void setAdressLine3(String adressLine3) {
		this.adressLine3 = adressLine3;
	}

	public String getAdressLine4() {
		return adressLine4;
	}

	public void setAdressLine4(String adressLine4) {
		this.adressLine4 = adressLine4;
	}

	public String getCollectionFrequencyId() {
		return collectionFrequencyId;
	}

	public void setCollectionFrequencyId(String collectionFrequencyId) {
		this.collectionFrequencyId = collectionFrequencyId;
	}

	public String getCollectionFrequency() {
		return collectionFrequency;
	}

	public void setCollectionFrequency(String collectionFrequency) {
		this.collectionFrequency = collectionFrequency;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
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

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getCenterHead() {
		return centerHead;
	}

	public void setCenterHead(String centerHead) {
		this.centerHead = centerHead;
	}

	public String getMaxClientPerSubCenter() {
		return maxClientPerSubCenter;
	}

	public void setMaxClientPerSubCenter(String maxClientPerSubCenter) {
		this.maxClientPerSubCenter = maxClientPerSubCenter;
	}

	public String getMaxNoOfSubCenter() {
		return maxNoOfSubCenter;
	}

	public void setMaxNoOfSubCenter(String maxNoOfSubCenter) {
		this.maxNoOfSubCenter = maxNoOfSubCenter;
	}

	public String getCenterLimit() {
		return centerLimit;
	}

	public void setCenterLimit(String centerLimit) {
		this.centerLimit = centerLimit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmpUserId() {
		return empUserId;
	}

	public void setEmpUserId(String empUserId) {
		this.empUserId = empUserId;
	}
	
	
}
