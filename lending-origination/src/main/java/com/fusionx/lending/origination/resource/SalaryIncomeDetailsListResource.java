package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Salary Income Details List  Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-09-2021   FXL-115  	 FXL-658       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SalaryIncomeDetailsListResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "PAST|CURRENT", message = "{common-employment-type.pattern}")
	private String employmentType;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String employerId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String employerName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String experience;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String designationId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String designationName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String jobTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String jobTypeDesc;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "PRIMARY|SECONDARY", message = "{common-source.pattern}")
	private String sourceType;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@Valid
	private List<DocumentAddResource> salaryIncomeDocumentList;


	public String getEmploymentType() {
		return employmentType;
	}


	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}


	public String getEmployerId() {
		return employerId;
	}


	public void setEmployerId(String employerId) {
		this.employerId = employerId;
	}


	public String getEmployerName() {
		return employerName;
	}


	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}


	public String getExperience() {
		return experience;
	}


	public void setExperience(String experience) {
		this.experience = experience;
	}


	public String getDesignationId() {
		return designationId;
	}


	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}


	public String getDesignationName() {
		return designationName;
	}


	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}


	public String getJobTypeId() {
		return jobTypeId;
	}


	public void setJobTypeId(String jobTypeId) {
		this.jobTypeId = jobTypeId;
	}


	public String getJobTypeDesc() {
		return jobTypeDesc;
	}


	public void setJobTypeDesc(String jobTypeDesc) {
		this.jobTypeDesc = jobTypeDesc;
	}


	public String getSourceType() {
		return sourceType;
	}


	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public List<DocumentAddResource> getSalaryIncomeDocumentList() {
		return salaryIncomeDocumentList;
	}


	public void setSalaryIncomeDocumentList(List<DocumentAddResource> salaryIncomeDocumentList) {
		this.salaryIncomeDocumentList = salaryIncomeDocumentList;
	}


}
