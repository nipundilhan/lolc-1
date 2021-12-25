package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.EmploymentTypeEnum;
import com.fusionx.lending.origination.enums.SourceTypeEnum;

import lombok.Data;

/**
 * 	Salary Income Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-09-2021   FXL-115  	 FXL-658       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@Entity
@Data
@Table(name = "salary_income_details")
public class SalaryIncomeDetails extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 2673861277874403970L;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "income_source_details_id" ,nullable=false)
	private IncomeSourceDetails incomeSourceDetail;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "employment_type", length=20, nullable=false)
	private EmploymentTypeEnum employmentType;
	
	@Column(name = "employer_id")
	private Long employerId;
	
	@Column(name = "employer_name")
	private String employerName;
	
	@Column(name = "experience" ,nullable=false)
	private Integer experience;
	
	@Column(name = "designation_id",nullable=false)
	private Long designationId;
	
	@Column(name = "job_type_id",nullable=false)
	private Long jobTypeId;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "source_type", length=20, nullable=false)
	private SourceTypeEnum sourceType;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;

	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	@Transient
	private String designation;
	
	@Transient
	private String jobType;
	
	@Transient
	private List<SalaryIncomeDocuments> salaryIncomeDocuments;
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public IncomeSourceDetails getIncomeSourceDetail() {
		return incomeSourceDetail;
	}

	public void setIncomeSourceDetail(IncomeSourceDetails incomeSourceDetail) {
		this.incomeSourceDetail = incomeSourceDetail;
	}

	public EmploymentTypeEnum getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(EmploymentTypeEnum employmentType) {
		this.employmentType = employmentType;
	}

	public Long getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Long employerId) {
		this.employerId = employerId;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	public Long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public Long getJobTypeId() {
		return jobTypeId;
	}

	public void setJobTypeId(Long jobTypeId) {
		this.jobTypeId = jobTypeId;
	}

	public SourceTypeEnum getSourceType() {
		return sourceType;
	}

	public void setSourceType(SourceTypeEnum sourceType) {
		this.sourceType = sourceType;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;	
	}
	
	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	
	public List<SalaryIncomeDocuments> getSalaryIncomeDocuments() {
		return salaryIncomeDocuments;
	}

	public void setSalaryIncomeDocuments(List<SalaryIncomeDocuments> salaryIncomeDocuments) {
		this.salaryIncomeDocuments = salaryIncomeDocuments;
	}

}
