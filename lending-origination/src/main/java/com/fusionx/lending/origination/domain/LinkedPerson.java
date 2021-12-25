package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;

import lombok.Data;

@Entity
@Table(name = "linked_person")
@Transactional
@Data
public class LinkedPerson extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 4537474747L;

	@Column(name="tenant_id")
	private String tenantId;
	
	@Column(name="linked_person_type")
	private String linkedPersonType;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_id", nullable=false)
	private Customer customer;
	
	@Column(name="name")
	private String name; 
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="middle_name")
	private String middleName;
    
	@Column(name="last_name")
	private String lastName;
	
	@Column(name = "cmlist_gender_id")
	private Long genderId;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "cmlist_title_id")
	private Long titleId;

	@Column(name = "title")
	private String title;
	
	@Column(name="status")
	private String status; 
	
	@Column(name="created_user")
	private String createdUser;
	
	@Column(name="created_date")
	private Date createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;
	
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	@Column(name = "culp_id")
	private String culpId;
	   
	@Column(name = "pculp_id")
    private String pculpId;
	    
	@Column(name = "pen_per_id")
	private Long penPerId;

	@Column(name = "per_id")
	private Long perId;

	@Column(name = "per_code")
	private String perCode;
	
	@Column(name = "com_cus_link_person_id")
	private Long commonCustomerLinkPersonId;
	
	@Column(name = "cmlist_relation_Id")
	private Long relationId;
	
	@Column(name = "relationship_type")
	private String relationshipType;
	
	@Column(name="internal_crib_status_id")
	private Long internalCribStatusId;
	
	@Column(name="external_crib_status_id")
	private Long externalCribStatusId;
	
	@Transient
	private String internalCribStatus;
	
	@Transient
	private String externalCribStatus;
	
	@Transient
	private List<IdentificationDetail> identificationDetails;
	
	@Transient
	private List<ContactDetail> contactDetails;
	
	@Transient
	private List<AddressDetail> addressDetails;	

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLinkedPersonType() {
		return linkedPersonType;
	}

	public void setLinkedPersonType(String linkedPersonType) {
		this.linkedPersonType = linkedPersonType;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
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


	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<IdentificationDetail> getIdentificationDetails() {
		return identificationDetails;
	}

	public void setIdentificationDetails(List<IdentificationDetail> identificationDetails) {
		this.identificationDetails = identificationDetails;
	}

	public List<ContactDetail> getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(List<ContactDetail> contactDetails) {
		this.contactDetails = contactDetails;
	}

	public List<AddressDetail> getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(List<AddressDetail> addressDetails) {
		this.addressDetails = addressDetails;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCulpId() {
		return culpId;
	}

	public void setCulpId(String culpId) {
		this.culpId = culpId;
	}

	public String getPculpId() {
		return pculpId;
	}

	public void setPculpId(String pculpId) {
		this.pculpId = pculpId;
	}

	public Long getPenPerId() {
		return penPerId;
	}

	public void setPenPerId(Long penPerId) {
		this.penPerId = penPerId;
	}

	public Long getPerId() {
		return perId;
	}

	public void setPerId(Long perId) {
		this.perId = perId;
	}

	public String getPerCode() {
		return perCode;
	}

	public void setPerCode(String perCode) {
		this.perCode = perCode;
	}

	public Long getCommonCustomerLinkPersonId() {
		return commonCustomerLinkPersonId;
	}

	public void setCommonCustomerLinkPersonId(Long commonCustomerLinkPersonId) {
		this.commonCustomerLinkPersonId = commonCustomerLinkPersonId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}

	public Long getGenderId() {
		return genderId;
	}

	public void setGenderId(Long genderId) {
		this.genderId = genderId;
	}

	public Long getTitleId() {
		return titleId;
	}

	public void setTitleId(Long titleId) {
		this.titleId = titleId;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public Long getInternalCribStatusId() {
		return internalCribStatusId;
	}

	public void setInternalCribStatusId(Long internalCribStatusId) {
		this.internalCribStatusId = internalCribStatusId;
	}

	public Long getExternalCribStatusId() {
		return externalCribStatusId;
	}

	public void setExternalCribStatusId(Long externalCribStatusId) {
		this.externalCribStatusId = externalCribStatusId;
	}

	public String getInternalCribStatus() {
		return internalCribStatus;
	}

	public void setInternalCribStatus(String internalCribStatus) {
		this.internalCribStatus = internalCribStatus;
	}

	public String getExternalCribStatus() {
		return externalCribStatus;
	}

	public void setExternalCribStatus(String externalCribStatus) {
		this.externalCribStatus = externalCribStatus;
	}	
	
}
