package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.google.type.Date;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GuarantorAddResource {

  	@NotBlank(message = "{common.not-null}")
  	private String leadId;
    
  	private String guarantorId;
  	
  	private String pendingGuarantorId;
  	private String customerReference;
  	
  	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String guarantorTypeId;
	
	@NotBlank(message = "{common.not-null}")
	//@Pattern(regexp = "INDIVIDUAL|CORPORATE", message = "{customerType.pattern}")
	private String guarantorType;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String organizationTypeId;
	
	@NotBlank(message = "{common.not-null}")
	//@Pattern(regexp = "INDIVIDUAL|CORPORATE", message = "{customerType.pattern}")
	private String organizationType;
  	
  	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
    
  	@NotBlank(message = "{common.not-null}")
  	private String firstName;
    
  	//@NotBlank(message = "{common.not-null}")
  	private String middleName;
    
  	@NotBlank(message = "{common.not-null}")
  	private String lastName;
    
  	@NotBlank(message = "{common.not-null}")
  	private String fullName;
    
  	@NotBlank(message = "{common.not-null}")
  	private String initials;
    
  	@NotBlank(message = "{common.not-null}")
  	@Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{common.invalid-date-pattern}")
  	private String dateOfBirth;
    
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String relationshipId;
	
  	@NotBlank(message = "{common.not-null}")
  	private String relationshipToCus;
    
  	//@NotBlank(message = "{common.not-null}")
  	private String maritalStatus;
    
  /*	@NotBlank(message = "{common.not-null}")
  	private String preferredLanguage;*/
    
//  	//@NotBlank(message = "{common.not-null}")
//  	private String internalCribStatus;
//    
//  	//@NotBlank(message = "{common.not-null}")
//  	private String externalCribStatus;
  	
    @Pattern(regexp = "^$|[0-9]+", message = "{perId.pattern}")
    private String penPerId;

    @Pattern(regexp = "^$|[0-9]+", message = "{perId.pattern}")
    private String perId;

    @Size(max = 15, message = "{perCode.size}")
    private String perCode;
    
    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
  	private String genderId;
  	private String gender;
      
  	@NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
  	private String titleId;
  	private String title;
  	
  	@Valid
	private List<AddressDetailsResource> addressDetails;
  	@Valid
	private List<IdentificationDetailResource> identificationDetails;
  	@Valid
	private List<ContactDetailsResource> contactDetails;
	

	public String getRelationshipId() {
		return relationshipId;
	}

	public void setRelationshipId(String relationshipId) {
		this.relationshipId = relationshipId;
	}

	public String getOrganizationTypeId() {
		return organizationTypeId;
	}

	public void setOrganizationTypeId(String organizationTypeId) {
		this.organizationTypeId = organizationTypeId;
	}

	public String getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}

	public String getGuarantorTypeId() {
		return guarantorTypeId;
	}

	public void setGuarantorTypeId(String guarantorTypeId) {
		this.guarantorTypeId = guarantorTypeId;
	}

	public String getGuarantorType() {
		return guarantorType;
	}

	public void setGuarantorType(String guarantorType) {
		this.guarantorType = guarantorType;
	}

	public List<AddressDetailsResource> getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(List<AddressDetailsResource> addressDetails) {
		this.addressDetails = addressDetails;
	}

	public List<IdentificationDetailResource> getIdentificationDetails() {
		return identificationDetails;
	}

	public void setIdentificationDetails(List<IdentificationDetailResource> identificationDetails) {
		this.identificationDetails = identificationDetails;
	}

	public List<ContactDetailsResource> getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(List<ContactDetailsResource> contactDetails) {
		this.contactDetails = contactDetails;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getGuarantorId() {
		return guarantorId;
	}

	public void setGuarantorId(String guarantorId) {
		this.guarantorId = guarantorId;
	}

	public String getPendingGuarantorId() {
		return pendingGuarantorId;
	}

	public void setPendingGuarantorId(String pendingGuarantorId) {
		this.pendingGuarantorId = pendingGuarantorId;
	}

	

	public String getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(String customerReference) {
		this.customerReference = customerReference;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getRelationshipToCus() {
		return relationshipToCus;
	}

	public void setRelationshipToCus(String relationshipToCus) {
		this.relationshipToCus = relationshipToCus;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}


	public String getPenPerId() {
		return penPerId;
	}

	public void setPenPerId(String penPerId) {
		this.penPerId = penPerId;
	}

	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}

	public String getPerCode() {
		return perCode;
	}

	public void setPerCode(String perCode) {
		this.perCode = perCode;
	}

	public String getGenderId() {
		return genderId;
	}

	public void setGenderId(String genderId) {
		this.genderId = genderId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

  	
  	
}
