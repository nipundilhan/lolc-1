package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

//@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BusinessSubCenterAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String name;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String businessCenterId;
	
	@NotBlank(message = "{common.not-null}")
	private String businessCenterName;
	
	@NotBlank(message = "{common.not-null}")
	private String empId;
	
	@NotBlank(message = "{common.not-null}")
	private String empNo;
	
	@NotBlank(message = "{common.not-null}")
	private String empName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String maxClientPerSubCenter;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String maxSubCenterLimit;
	
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

	public String getBusinessCenterId() {
		return businessCenterId;
	}

	public void setBusinessCenterId(String businessCenterId) {
		this.businessCenterId = businessCenterId;
	}

	public String getBusinessCenterName() {
		return businessCenterName;
	}

	public void setBusinessCenterName(String businessCenterName) {
		this.businessCenterName = businessCenterName;
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

	public String getMaxClientPerSubCenter() {
		return maxClientPerSubCenter;
	}

	public void setMaxClientPerSubCenter(String maxClientPerSubCenter) {
		this.maxClientPerSubCenter = maxClientPerSubCenter;
	}
	
	public String getMaxSubCenterLimit() {
		return maxSubCenterLimit;
	}

	public void setMaxSubCenterLimit(String maxSubCenterLimit) {
		this.maxSubCenterLimit = maxSubCenterLimit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
