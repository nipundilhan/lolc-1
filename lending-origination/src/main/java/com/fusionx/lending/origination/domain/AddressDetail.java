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
@Table(name = "address_detail")
@Data
public class AddressDetail extends BaseEntity implements Serializable{

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
	
	@Column(name="add_type_comn_li_id")
	private Long addressTypeId;
	
	@Column(name="address1")
	private String address1;
	
	@Column(name="address2")
	private String address2;
	
	@Column(name="address3")
	private String address3;
	
	@Column(name="address4")
	private String address4;
	
	@Column(name="geo_level_id")
	private Long geoLevelId;
	
	@Column(name="country_geo_id")
	private Long countryGeoId;
	
	@Column(name="postal_code")
	private String postalCode;
	
	@Column(name="created_user")
	private String createdUser;
	
	@Column(name="created_date")
	private Date createdDate;
	
	@Column(name="ppadd_id")
	private Long ppaddId;
	
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

	public Long getAddressTypeId() {
		return addressTypeId;
	}

	public void setAddressTypeId(Long addressTypeId) {
		this.addressTypeId = addressTypeId;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public Long getGeoLevelId() {
		return geoLevelId;
	}

	public void setGeoLevelId(Long geoLevelId) {
		this.geoLevelId = geoLevelId;
	}

	public Long getCountryGeoId() {
		return countryGeoId;
	}

	public void setCountryGeoId(Long countryGeoId) {
		this.countryGeoId = countryGeoId;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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

	public Long getPpaddId() {
		return ppaddId;
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

	public void setPpaddId(Long ppaddId) {
		this.ppaddId = ppaddId;
	}

	
	
}
