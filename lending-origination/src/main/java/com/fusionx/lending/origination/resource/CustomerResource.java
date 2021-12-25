package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.enums.CommonStatus;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CustomerResource {

	private String leadId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String customerTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "INDIVIDUAL|CORPORATE", message = "{customerType.pattern}")
	private String customerType;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "MAIN|CO-BORROWER", message = "{customerMainType.pattern}")
	private String customerMainType;
	
	private String customerId;
	
	@NotBlank(message = "{common.not-null}")
	private String customerReference;
	
	private String firstName;
	
	private String middleName;
    
	private String lastName;

    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String genderId;
	private String gender;
    
    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String titleId;
	private String title;
	
	private String fullName;

	private String initials;

	@Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{common.invalid-date-pattern}")
	private String dateOfBirth;

	@NotBlank(message = "{common.not-null}")
	private String cusReferenceCode;

	private String ref01;
    
	private String ref02;
	
	private String ref03;
    
	private String brNumber;
	
	private String referenceNo;
	
	private String taxNo;
	
	private String companyName;   
	
	private String corporateCategoryId; 
	
	private String corporateCategoryName; 
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status; 
	
    @Pattern(regexp = "^$|[0-9]+", message = "{perId.pattern}")
    private String penPerId;

    @Pattern(regexp = "^$|[0-9]+", message = "{perId.pattern}")
    private String perId;

    @Size(max = 15, message = "{perCode.size}")
    private String perCode;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String nearestBranchId;	
    @NotBlank(message = "{common.not-null}")
	private String nearestBranchName;

	@Pattern(regexp = "^$|YES|NO",message="{within-branch-area.pattern}")
	private String withinBranchArea;

    //added
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String preferredLanguageId;
	private String preferredLanguageDesc;

	
	private String contactPerson;
	
	private List<LinkedPersonResource> linkedPersonList;
	
	private List<FacilityOtherProductsResource> productCategoryList;
	
	private List<AddressDetailsResource> address;
	
	private List<IdentificationDetailResource> identification;
	
	private List<ContactDetailsResource> contact;

	
	public String getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(String customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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

	public String getCusReferenceCode() {
		return cusReferenceCode;
	}

	public void setCusReferenceCode(String cusReferenceCode) {
		this.cusReferenceCode = cusReferenceCode;
	}

	public String getRef01() {
		return ref01;
	}

	public void setRef01(String ref01) {
		this.ref01 = ref01;
	}

	public String getRef02() {
		return ref02;
	}

	public void setRef02(String ref02) {
		this.ref02 = ref02;
	}

	public String getRef03() {
		return ref03;
	}

	public void setRef03(String ref03) {
		this.ref03 = ref03;
	}

	public String getBrNumber() {
		return brNumber;
	}

	public void setBrNumber(String brNumber) {
		this.brNumber = brNumber;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCorporateCategoryId() {
		return corporateCategoryId;
	}

	public void setCorporateCategoryId(String corporateCategoryId) {
		this.corporateCategoryId = corporateCategoryId;
	}

	public String getCorporateCategoryName() {
		return corporateCategoryName;
	}

	public void setCorporateCategoryName(String corporateCategoryName) {
		this.corporateCategoryName = corporateCategoryName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<AddressDetailsResource> getAddress() {
		return address;
	}

	public void setAddress(List<AddressDetailsResource> address) {
		this.address = address;
	}

	public List<IdentificationDetailResource> getIdentification() {
		return identification;
	}

	public void setIdentification(List<IdentificationDetailResource> identification) {
		this.identification = identification;
	}

	public List<ContactDetailsResource> getContact() {
		return contact;
	}

	public void setContact(List<ContactDetailsResource> contact) {
		this.contact = contact;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

//	public BlacklistAddResource getBlacklist() {
//		return blacklist;
//	}
//
//	public void setBlacklist(BlacklistAddResource blacklist) {
//		this.blacklist = blacklist;
//	}

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

	public String getCustomerMainType() {
		return customerMainType;
	}

	public void setCustomerMainType(String customerMainType) {
		this.customerMainType = customerMainType;
	}

	
	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getGenderId() {
		return genderId;
	}

	public void setGenderId(String genderId) {
		this.genderId = genderId;
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public List<FacilityOtherProductsResource> getProductCategoryList() {
		return productCategoryList;
	}

	public void setProductCategoryList(List<FacilityOtherProductsResource> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}

	public List<LinkedPersonResource> getLinkedPersonList() {
		return linkedPersonList;
	}

	public void setLinkedPersonList(List<LinkedPersonResource> linkedPersonList) {
		this.linkedPersonList = linkedPersonList;
	}

	public String getPreferredLanguageId() {
		return preferredLanguageId;
	}

	public void setPreferredLanguageId(String preferredLanguageId) {
		this.preferredLanguageId = preferredLanguageId;
	}

	public String getPreferredLanguageDesc() {
		return preferredLanguageDesc;
	}

	public void setPreferredLanguageDesc(String preferredLanguageDesc) {
		this.preferredLanguageDesc = preferredLanguageDesc;
	}

	public String getWithinBranchArea() {
		return withinBranchArea;
	}

	public void setWithinBranchArea(String withinBranchArea) {
		this.withinBranchArea = withinBranchArea;
	}

	public String getNearestBranchId() {
		return nearestBranchId;
	}

	public void setNearestBranchId(String nearestBranchId) {
		this.nearestBranchId = nearestBranchId;
	}

	public String getNearestBranchName() {
		return nearestBranchName;
	}

	public void setNearestBranchName(String nearestBranchName) {
		this.nearestBranchName = nearestBranchName;
	}

	
}
