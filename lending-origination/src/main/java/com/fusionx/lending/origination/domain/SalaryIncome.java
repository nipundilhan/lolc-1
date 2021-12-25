package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;

import lombok.Data;

/**
 * Salary Income Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6301    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Entity
@Data
@Table(name = "gu_salary_income")
public class SalaryIncome  extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 0000000000001;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_id")  
	private Customer customer;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "guarantor_id")  
	private Guarantor guarantor;
	
	@Column(name = "salary_type")
	private String salaryType;
	
	@Column(name = "key_person_id")
	private Long keyPersonId;
	
	@Column(name = "employer_name")
	private String employerName;
	
	@Column(name = "designation_id")
	private Long designationId;
	
	@Column(name = "designation_code")
	private String designationCode;
	
	@Column(name = "designation_name")
	private String designationName;
	
	@Column(name = "frequency_id")
	private Long frequencyId;
	
	@Column(name = "frequency_code")
	private String frequencyCode;
	
	@Column(name = "frequency_name")
	private String frequencyName;
	
	@Column(name = "occurance")
	private Long occurance;
	
	@Column(name = "salary_income")
	private BigDecimal salaryIncome;
	
	@Column(name = "tot_salary_per_freq")
	private BigDecimal totSalaryPerFreq;
	
	@Column(name = "gross_salary")
	private BigDecimal grossSalary;
	
	@Column(name = "deductions")
	private BigDecimal deductions;
	
	@Column(name = "net_salary")
	private BigDecimal netSalary;
	
	@Column(name = "comments")
	private String comment;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	

	public Guarantor getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(Guarantor guarantor) {
		this.guarantor = guarantor;
	}

	public String getSalaryType() {
		return salaryType;
	}

	public void setSalaryType(String salaryType) {
		this.salaryType = salaryType;
	}

	public Long getKeyPersonId() {
		return keyPersonId;
	}

	public void setKeyPersonId(Long keyPersonId) {
		this.keyPersonId = keyPersonId;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public Long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}
	
	

	public String getDesignationCode() {
		return designationCode;
	}

	public void setDesignationCode(String designationCode) {
		this.designationCode = designationCode;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	

	public Long getFrequencyId() {
		return frequencyId;
	}
	
	public String getFrequencyCode() {
		return frequencyCode;
	}

	public void setFrequencyCode(String frequencyCode) {
		this.frequencyCode = frequencyCode;
	}

	public String getFrequencyName() {
		return frequencyName;
	}

	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
	}

	public void setFrequencyId(Long frequencyId) {
		this.frequencyId = frequencyId;
	}

	public Long getOccurance() {
		return occurance;
	}

	public void setOccurance(Long occurance) {
		this.occurance = occurance;
	}

	public BigDecimal getSalaryIncome() {
		return salaryIncome;
	}

	public void setSalaryIncome(BigDecimal salaryIncome) {
		this.salaryIncome = salaryIncome;
	}

	public BigDecimal getTotSalaryPerFreq() {
		return totSalaryPerFreq;
	}

	public void setTotSalaryPerFreq(BigDecimal totSalaryPerFreq) {
		this.totSalaryPerFreq = totSalaryPerFreq;
	}

	public BigDecimal getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(BigDecimal grossSalary) {
		this.grossSalary = grossSalary;
	}

	public BigDecimal getDeductions() {
		return deductions;
	}

	public void setDeductions(BigDecimal deductions) {
		this.deductions = deductions;
	}

	public BigDecimal getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(BigDecimal netSalary) {
		this.netSalary = netSalary;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
	
	

}
