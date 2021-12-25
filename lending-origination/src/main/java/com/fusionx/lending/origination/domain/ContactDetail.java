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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;

import lombok.Data;

@Entity
@Table(name = "contact_detail")
@Transactional
@Data
public class ContactDetail extends BaseEntity implements Serializable{

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
	
	@Column(name="contact_type_id")
	private Long contactTypeId;
	
	@Column(name="contact_type")
	private String contactType;
	
	@Column(name="contact_no")
	private String contactNo;
	
	@Column(name="created_user")
	private String createdUser;
	
	@Column(name="created_date")
	private Date createdDate;
	
	@Column(name="ppcon_id")
	private Long ppconId;

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

	public Long getContactTypeId() {
		return contactTypeId;
	}

	public void setContactTypeId(Long contactTypeId) {
		this.contactTypeId = contactTypeId;
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
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

	public Long getPpconId() {
		return ppconId;
	}

	public void setPpconId(Long ppconId) {
		this.ppconId = ppconId;
	}

	

	
	
}
