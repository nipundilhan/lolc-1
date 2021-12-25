package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.transaction.Transactional;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;

import lombok.Data;


@Entity
@Table(name = "identification_detail")
@Transactional
@Data
public class IdentificationDetail extends BaseEntity implements Serializable{

	@Column(name="tenant_id")
	private String tenantId;
	
	@Column(name="status")
	private String status; 
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_id", nullable=false)
	private Customer customer;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "guarantor_id", nullable=false)
	private Guarantor guarantor;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "linked_person_id", nullable=false)
	private LinkedPerson linkPerson;
	
	@Column(name="identification_type_id")
	private Long identificationTypeId;
	
	@Column(name="identification_type")
	private String identificationType;
	
	@Column(name="identification_no")
	private String identificationNo;
	
	@Column(name="created_user")
	private String createdUser;
	
	@Column(name="created_date")
	private Date createdDate;
	
	@Column(name="pidt_id")
    private Long pidtId;
   
	@Column(name="ppidt_id")
    private Long ppidtId;

	@Column(name="issue_date")
	private Date issueDate;
	
	@Column(name="expiry_date")
	private Date expiryDate;
	
 	@Column(name = "modified_user")
  	private String modifiedUser;
  	
  	@Column(name = "modified_date")
  	private Timestamp modifiedDate;
	
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

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getIdentificationTypeId() {
		return identificationTypeId;
	}

	public void setIdentificationTypeId(Long identificationTypeId) {
		this.identificationTypeId = identificationTypeId;
	}

	public String getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}

	public String getIdentificationNo() {
		return identificationNo;
	}

	public void setIdentificationNo(String identificationNo) {
		this.identificationNo = identificationNo;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Guarantor getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(Guarantor guarantor) {
		this.guarantor = guarantor;
	}

	public LinkedPerson getLinkPerson() {
		return linkPerson;
	}

	public void setLinkPerson(LinkedPerson linkPerson) {
		this.linkPerson = linkPerson;
	}

	public Long getPidtId() {
		return pidtId;
	}

	public void setPidtId(Long pidtId) {
		this.pidtId = pidtId;
	}

	public Long getPpidtId() {
		return ppidtId;
	}

	public void setPpidtId(Long ppidtId) {
		this.ppidtId = ppidtId;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}




	
	
}
