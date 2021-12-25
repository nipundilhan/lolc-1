package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.util.Date;
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
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;


@Entity
@Table(name = "customer")
@Transactional
public class Customer extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 6336350473440460585L;

	@Column(name="tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lead_id", nullable=false)
	private LeadInfo lead;
	
	@Column(name="customer_type")
	private String customerType;
	
	@Column(name="customer_type_id")
	private Long customerTypeId;
	
	@Column(name="customer_id")
	private Long customerId;
	
	@Column(name="pending_customer_id")
	private Long pendingCustomerId;
	
	@Column(name="customer_reference")
	private String customerReference;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="middle_name")
	private String middleName;
    
	@Column(name="last_name")
	private String lastName;

	@Column(name="full_name")
	private String fullName;

	@Column(name="initials")
	private String initials;

	@Column(name="date_of_birth")
	private Date dateOfBirth;

	@Column(name="cus_reference_code")
	private String cusReferenceCode;

	@Column(name="ref_01")
	private String ref01;
    
	@Column(name="ref_02")
	private String ref02;
	
	@Column(name="ref_03")
	private String ref03;
    
	@Column(name="br_number")
	private String brNumber;
	
	@Column(name="reference_no")
	private String referenceNo;
	
	@Column(name="tax_no")
	private String taxNo;
	
	@Column(name="company_name")
	private String companyName;   
	
	@Column(name="status")
	private String status; 
	
	@Column(name="created_user")
	private String createdUser;
	
	@Column(name="created_date")
	private Date createdDate;
	
	@Column(name="blacklst_approve_reject_remark")
	private String blacklstApproveRejectRemark;
	
	@Column(name = "pen_per_id")
	private String penPerId;

	@Column(name = "per_id")
	private String perId;

	@Column(name = "per_code")
	private String perCode;
	
	@Column(name = "gender_id")
	private Long genderId;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "title_id")
	private Long titleId;

	@Column(name = "title")
	private String title;
	
	@Column(name="customer_main_type")
	private String customerMainType;
	
	@Column(name="contact_person")
	private String contactPerson;
	
	@Column(name="internal_crib_status_id")
	private Long internalCribStatusId;
	
	@Column(name="external_crib_status_id")
	private Long externalCribStatusId;
	
	@Transient
	private String internalCribStatus;
	
	@Transient
	private String externalCribStatus;

	@Column(name="corporate_category_id")
	private Long corporateCategoryId;
	
	@Column(name="corporate_category")
	private String corporateCategory;
	@Column(name="modified_user")
	private String modifiedUser;
	
	@Column(name="modified_date")
	private Date modifiedDate;
	
	@Column(name = "pref_lang_id")
	private Long prefLangId;

	@Column(name = "nearest_branch_id")
	private Long nearestBranchId;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "within_branch_area", length=30)
	private CommonStatus withinBranchArea;
	
	@Transient
	private List<IdentificationDetail> identificationDetails;
	
	@Transient
	private List<ContactDetail> contactDetails;
	
	@Transient
	private List<AddressDetail> addressDetails;
	
	@Transient
	private List<LinkedPerson> linkedPersons;
	
	@Transient
	private List<BusinessIncomeExpense> businessIncomeExpenses;
	
	@Transient
	private List<SalaryIncome> salaryIncomes;
	
	@Transient
	private List<OtherIncome> otherIncomes;
	
	@Transient
	private List<CustomerCultivationIncome> cultivationIncomes;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public LeadInfo getLead() {
		return lead;
	}

	public void setLead(LeadInfo lead) {
		this.lead = lead;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public List<LinkedPerson> getLinkedPersons() {
		return linkedPersons;
	}

	public void setLinkedPersons(List<LinkedPerson> linkedPersons) {
		this.linkedPersons = linkedPersons;
	}

	public Long getPendingCustomerId() {
		return pendingCustomerId;
	}

	public void setPendingCustomerId(Long pendingCustomerId) {
		this.pendingCustomerId = pendingCustomerId;
	}

	public List<BusinessIncomeExpense> getBusinessIncomeExpenses() {
		return businessIncomeExpenses;
	}

	public void setBusinessIncomeExpenses(List<BusinessIncomeExpense> businessIncomeExpenses) {
		this.businessIncomeExpenses = businessIncomeExpenses;
	}

	public List<SalaryIncome> getSalaryIncomes() {
		return salaryIncomes;
	}

	public void setSalaryIncomes(List<SalaryIncome> salaryIncomes) {
		this.salaryIncomes = salaryIncomes;
	}

	public List<OtherIncome> getOtherIncomes() {
		return otherIncomes;
	}

	public void setOtherIncomes(List<OtherIncome> otherIncomes) {
		this.otherIncomes = otherIncomes;
	}

	public List<CustomerCultivationIncome> getCultivationIncomes() {
		return cultivationIncomes;
	}

	public void setCultivationIncomes(List<CustomerCultivationIncome> cultivationIncomes) {
		this.cultivationIncomes = cultivationIncomes;
	}

	public String getBlacklstApproveRejectRemark() {
		return blacklstApproveRejectRemark;
	}

	public void setBlacklstApproveRejectRemark(String blacklstApproveRejectRemark) {
		this.blacklstApproveRejectRemark = blacklstApproveRejectRemark;
	}
//
//	public List<BlacklistDetail> getBlacklistDetails() {
//		return blacklistDetails;
//	}
//
//	public void setBlacklistDetails(List<BlacklistDetail> blacklistDetails) {
//		this.blacklistDetails = blacklistDetails;
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

	public Long getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(Long customerTypeId) {
		this.customerTypeId = customerTypeId;
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

	public Long getCorporateCategoryId() {
		return corporateCategoryId;
	}

	public void setCorporateCategoryId(Long corporateCategoryId) {
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

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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

	public Long getPrefLangId() {
		return prefLangId;
	}

	public void setPrefLangId(Long prefLangId) {
		this.prefLangId = prefLangId;
	}

	public Long getNearestBranchId() {
		return nearestBranchId;
	}

	public void setNearestBranchId(Long nearestBranchId) {
		this.nearestBranchId = nearestBranchId;
	}

	public CommonStatus getWithinBranchArea() {
		return withinBranchArea;
	}

	public void setWithinBranchArea(CommonStatus withinBranchArea) {
		this.withinBranchArea = withinBranchArea;
	}

	
}
