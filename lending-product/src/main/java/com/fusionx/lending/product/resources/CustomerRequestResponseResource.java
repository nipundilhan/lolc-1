package com.fusionx.lending.product.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestResponseResource {

    private String id;
    private String syncTs;
    private String version;
    private String tenantId;
    private String customerType;
    private String customerTypeId;
    private String customerId;
    private String pendingCustomerId;
    private String customerReference;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private String initials;
    private String dateOfBirth;
    private String cusReferenceCode;
    private String ref01;
    private String ref02;
    private String ref03;
    private String brNumber;
    private String referenceNo;
    private String taxNo;
    private String companyName;
    private String status;
    private String createdUser;
    private String createdDate;
    private String blacklstApproveRejectRemark;
    private String penPerId;
    private String perId;
    private String perCode;
    private String genderId;
    private String gender;
    private String titleId;
    private String title;
    private String customerMainType;
    private String contactPerson;
    private String internalCribStatusId;
    private String externalCribStatusId;
    private String internalCribStatus;
    private String externalCribStatus;
    private String corporateCategoryId;
    private String corporateCategory;
    private String modifiedUser;
    private String modifiedDate;
    private String prefLangId;
    private String nearestBranchId;
    private String withinBranchArea;
    private List<IdentificationDetailsRequestResponseResource> identificationDetails;
    private List<ContactDetailsRequestResponseResource> contactDetails;
    private List<AddressDetailsRequestResponseResource> addressDetails;
    private String linkedPersons;
    private String businessIncomeExpenses;
    private String salaryIncomes;
    private String otherIncomes;
    private String cultivationIncomes;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSyncTs() {
		return syncTs;
	}
	public void setSyncTs(String syncTs) {
		this.syncTs = syncTs;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getCustomerTypeId() {
		return customerTypeId;
	}
	public void setCustomerTypeId(String customerTypeId) {
		this.customerTypeId = customerTypeId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getPendingCustomerId() {
		return pendingCustomerId;
	}
	public void setPendingCustomerId(String pendingCustomerId) {
		this.pendingCustomerId = pendingCustomerId;
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
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getBlacklstApproveRejectRemark() {
		return blacklstApproveRejectRemark;
	}
	public void setBlacklstApproveRejectRemark(String blacklstApproveRejectRemark) {
		this.blacklstApproveRejectRemark = blacklstApproveRejectRemark;
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
	public String getInternalCribStatusId() {
		return internalCribStatusId;
	}
	public void setInternalCribStatusId(String internalCribStatusId) {
		this.internalCribStatusId = internalCribStatusId;
	}
	public String getExternalCribStatusId() {
		return externalCribStatusId;
	}
	public void setExternalCribStatusId(String externalCribStatusId) {
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
	public String getCorporateCategoryId() {
		return corporateCategoryId;
	}
	public void setCorporateCategoryId(String corporateCategoryId) {
		this.corporateCategoryId = corporateCategoryId;
	}
	public String getCorporateCategory() {
		return corporateCategory;
	}
	public void setCorporateCategory(String corporateCategory) {
		this.corporateCategory = corporateCategory;
	}
	public String getModifiedUser() {
		return modifiedUser;
	}
	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getPrefLangId() {
		return prefLangId;
	}
	public void setPrefLangId(String prefLangId) {
		this.prefLangId = prefLangId;
	}
	public String getNearestBranchId() {
		return nearestBranchId;
	}
	public void setNearestBranchId(String nearestBranchId) {
		this.nearestBranchId = nearestBranchId;
	}
	public String getWithinBranchArea() {
		return withinBranchArea;
	}
	public void setWithinBranchArea(String withinBranchArea) {
		this.withinBranchArea = withinBranchArea;
	}
	public List<IdentificationDetailsRequestResponseResource> getIdentificationDetails() {
		return identificationDetails;
	}
	public void setIdentificationDetails(List<IdentificationDetailsRequestResponseResource> identificationDetails) {
		this.identificationDetails = identificationDetails;
	}
	public List<ContactDetailsRequestResponseResource> getContactDetails() {
		return contactDetails;
	}
	public void setContactDetails(List<ContactDetailsRequestResponseResource> contactDetails) {
		this.contactDetails = contactDetails;
	}
	public List<AddressDetailsRequestResponseResource> getAddressDetails() {
		return addressDetails;
	}
	public void setAddressDetails(List<AddressDetailsRequestResponseResource> addressDetails) {
		this.addressDetails = addressDetails;
	}
	public String getLinkedPersons() {
		return linkedPersons;
	}
	public void setLinkedPersons(String linkedPersons) {
		this.linkedPersons = linkedPersons;
	}
	public String getBusinessIncomeExpenses() {
		return businessIncomeExpenses;
	}
	public void setBusinessIncomeExpenses(String businessIncomeExpenses) {
		this.businessIncomeExpenses = businessIncomeExpenses;
	}
	public String getSalaryIncomes() {
		return salaryIncomes;
	}
	public void setSalaryIncomes(String salaryIncomes) {
		this.salaryIncomes = salaryIncomes;
	}
	public String getOtherIncomes() {
		return otherIncomes;
	}
	public void setOtherIncomes(String otherIncomes) {
		this.otherIncomes = otherIncomes;
	}
	public String getCultivationIncomes() {
		return cultivationIncomes;
	}
	public void setCultivationIncomes(String cultivationIncomes) {
		this.cultivationIncomes = cultivationIncomes;
	}


    

}

