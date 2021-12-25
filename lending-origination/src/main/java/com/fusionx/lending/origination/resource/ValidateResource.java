package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ValidateResource {
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("username")
	private String username;
	
	@JsonProperty("version")
	private String version;
	
	@JsonProperty("code")
	private String code;
	
	@JsonProperty("subCode")
	private String subCode;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("name")
	private String name;
		
	@JsonProperty("category")
	private String category;
	
	@JsonProperty("businessTypesId")
	private String businessTypesId;
	
	@JsonProperty("businessTypeCode")
	private String businessTypeCode;
	
	@JsonProperty("businessTypeName")
	private String businessTypeName;
	
	@JsonProperty("businessTypeDescription")
	private String businessTypeDescription;
	
	@JsonProperty("expenseTypesId")
	private String expenseTypesId;
	
	@JsonProperty("expenseTypeCode")
	private String expenseTypeCode;
	
	@JsonProperty("expenseTypeName")
	private String expenseTypeName;
	
	@JsonProperty("expenseTypeDescription")
	private String expenseTypeDescription;
	
	@JsonProperty("cultivationCategoryId")
	private String cultivationCategoryId;
	
	@JsonProperty("authorityGroupId")
	private String authorityGroupId;
	
	@JsonProperty("authorityGroupName")
	private String authorityGroupName;
	
	@JsonProperty("daLevel")
	private String daLevel;
	
	@JsonProperty("userName")
	private String userName;
	
	@JsonProperty("approvalCategoryId")
	private String approvalCategoryId;
	
	@JsonProperty("approvalCategoryName")
	private String approvalCategoryName;
	
	@JsonProperty("daLimitValue")
	private String daLimitValue;
	
	@JsonProperty("leadId")
	private String leadId;
	
	@JsonProperty("productId")
	private String productId;
	
	@JsonProperty("subProductId")
	private String subProductId;
	
	@JsonProperty("order")
	private String order;
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("companyId")
	private String companyId;
	
	@JsonProperty("totalAssetValue")
	private String totalAssetValue;
	
	@JsonProperty("totalLiabilityValue")
	private String totalLiabilityValue;
	
	@JsonProperty("networth")
	private String networth;
	
	@JsonProperty("typeOfAssetId")
	private String typeOfAssetId;
	
	@JsonProperty("ownershipId")
	private String ownershipId;
	
	@JsonProperty("checkListCategoryId")
	private String checkListCategoryId;
	
	@JsonProperty("checkListItemId")
	private String checkListItemId;
	
	@JsonProperty("typeOfFacilityId")
	private String typeOfFacilityId;
	
	@JsonProperty("guarantorId")
	private String guarantorId;
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("linkpersonId")
	private String linkpersonId;
	
	@JsonProperty("identificationNo")
	private String identificationNo;
	
	@JsonProperty("identificationType")
	private String identificationType;
	
	@JsonProperty("identificationTypeId")
	private String identificationTypeId;
	
	@JsonProperty("addressTypeId")
	private String addressTypeId;
	
	@JsonProperty("geoLevelId")
	private String geoLevelId;
	
	@JsonProperty("countryGeoId")
	private String countryGeoId;
	
	@JsonProperty("contactTypeId")
	private String contactTypeId;
		
	@JsonProperty("contactNo")
	private String contactNo;
	
	@JsonProperty("availableBalance")
	private String availableBalance;
	
	@JsonProperty("totalDueAmount")
	private String totalDueAmount;
	
	@JsonProperty("totalSettlementAmount")
	private String totalSettlementAmount;

	@JsonProperty("spouseProfessionId")
	private String spouseProfessionId;
		
	@JsonProperty("priorityLevelId")
	private String priorityLevelId;
	
	@JsonProperty("securitySubTypeId")
	private String securitySubTypeId;
	
	@JsonProperty("securityTypeId")
	private String securityTypeId;
	
	@JsonProperty("nextMeetingDate")
	private String nextMeetingDate;
	
	@JsonProperty("nicNo")
	private String nicNo;
	
	@JsonProperty("sequence")
	private String sequence;
	
	@JsonProperty("remark")
	private String remark;
	
	@JsonProperty("productCategoryId")
	private String productCategoryId;
	
	@JsonProperty("productCategoryCode")
	private String productCategoryCode;
	
	@JsonProperty("leadInfoId")
	private String leadInfoId;
	
	@JsonProperty("contactNumber")
	private String contactNumber;

	
	@JsonProperty("reasonId")
	private String reasonId;
	

	@JsonProperty("finalValuationFlag")
	private String finalValuationFlag;
	
	@JsonProperty("valuerId")
	private String valuerId;
	
	@JsonProperty("coverNoteNumber")
	private String coverNoteNumber;
	
	@JsonProperty("coverNotePeriodFrom")
	private String coverNotePeriodFrom;
	
	@JsonProperty("coverNotePeriodTo")
	private String coverNotePeriodTo;
	
	@JsonProperty("coverNoteStatus")
	private String coverNoteStatus;
	
	@JsonProperty("policyNo")
	private String policyNo;
	
	@JsonProperty("policyCoverPeriodFrom")
	private String policyCoverPeriodFrom;
	
	@JsonProperty("policyCoverPeriodTo")
	private String policyCoverPeriodTo;
	
	@JsonProperty("pedingIdentificationId")
	private String pedingIdentificationId;
	
	@JsonProperty("pedingCustomerId")
	private String pedingCustomerId;
	
	@JsonProperty("houseHoldExpenseCategoryId")
	private String houseHoldExpenseCategoryId;
	
	@JsonProperty("relationId")
	private String relationId;
	
	@JsonProperty("valuationDate")
	private String valuationDate;
	
	@JsonProperty("penPerId")
	private String penPerId;
	
	@JsonProperty("stagingStatus")
	private String stagingStatus;
	
	@JsonProperty("user")
	private String user;
	
	@JsonProperty("newLoanAmount")
	private String newLoanAmount;
	
	@JsonProperty("cribStatus")
	private String cribStatus;
	
	@JsonProperty("linkedPersonId")
	private String linkedPersonId;

	@JsonProperty("brNumber")
	private String brNumber;
	
	@JsonProperty("companyName")
	private String companyName;
	
	@JsonProperty("corporateCategoryId")
	private String corporateCategoryId;
	
	@JsonProperty("daGroupId")
	private String daGroupId;

	@JsonProperty("sectorId")
	private String sectorId;
	
	@JsonProperty("subSectorId")
	private String subSectorId;
	
	@JsonProperty("bankId")
	private String bankId;
	
	@JsonProperty("bankBranchId")
	private String bankBranchId;

	@JsonProperty("internalCribStatus")
	private String internalCribStatus;
	
	@JsonProperty("externalCribStatus")
	private String externalCribStatus;
	
	@JsonProperty("fullName")
	private String fullName;
	
	@JsonProperty("firstName")
	private String firstName;
	
	@JsonProperty("lastName")
	private String lastName;
	
	@JsonProperty("titleId")
	private String titleId;
	
	@JsonProperty("genderId")
	private String genderId;
	
	@JsonProperty("onboardingStatus")
	private String onboardingStatus;
	
	@JsonProperty("identification")
	private String  identification;
	
	@JsonProperty("address")
	private String address;
	
	@JsonProperty("contact")
	private String contact;
	
	@JsonProperty("blacklist")
	private String blacklist;
	
	@JsonProperty("commitmentTypeId")
	private String commitmentTypeId;
	
	@JsonProperty("repaymentFrequencyId")
	private String repaymentFrequencyId;
	
	@JsonProperty("facilityTypeId")
	private String facilityTypeId;
	
	@JsonProperty("frequencyId")
	private String frequencyId;
	
	@JsonProperty("designationId")
	private String designationId;
	
	@JsonProperty("exceptionApprovalGroupId")
	private String exceptionApprovalGroupId;

	@JsonProperty("exceptionTypeId")
	private String exceptionTypeId;
	
	@JsonProperty("businessCenterId")
	private String businessCenterId;

	@JsonProperty("expenseTypeId")
	private String expenseTypeId;

	@JsonProperty("expenseTypeList")
	private String expenseTypeList;

	@JsonProperty("sourceType")
	private String sourceType;

	@JsonProperty("customerFullName")
	private String customerFullName;

	@JsonProperty("linkedPersonname")
	private String linkedPersonname;

	@JsonProperty("incomeSourceDetailId")
	private String incomeSourceDetailId;
	
	@JsonProperty("salaryIncomeDetailsList")
	private String salaryIncomeDetailsList;
	
	@JsonProperty("designationName")
	private String designationName;
	
	@JsonProperty("documentId")
	private String documentId;

    @JsonProperty("incomeSourceDetails")
    private String incomeSourceDetails;

    @JsonProperty("calFrequencyCode")
    private String calFrequencyCode;

    @JsonProperty("occFrequencyCode")
    private String occFrequencyCode;
	
	@JsonProperty("businessPersonTypeId") //Added by SewwandiH
	private String businessPersonTypeId;
	
	@JsonProperty("industryTypeId") //Added by SewwandiH
	private String industryTypeId;
	
	@JsonProperty("expTypeCategory") 
	private String expTypeCategory;
	
	@JsonProperty("catExpDuplicate") 
	private String catExpDuplicate;
	
	@JsonProperty("landOwnershipId") 
	private String landOwnershipId;
	
	@JsonProperty("landOwnershipCode") 
	private String landOwnershipCode;
	
	@JsonProperty("plantOwnershipId") 
	private String plantOwnershipId;
	
	@JsonProperty("plantOwnershipCode") 
	private String plantOwnershipCode;
	
	@JsonProperty("busiOpertaionStartDate") 
	private String busiOpertaionStartDate;
	
	@JsonProperty("businessRegiDate") 
	private String businessRegiDate;
	
	@JsonProperty("businessTypeId") 
	private String businessTypeId;
	
	@JsonProperty("businessSubTypeId") 
	private String businessSubTypeId;
	
	@JsonProperty("businessSubTypeName") 
	private String businessSubTypeName;
	
	@JsonProperty("businessSizeId") 
	private String businessSizeId;
	
	@JsonProperty("businessSizeName") 
	private String businessSizeName;
	
	@JsonProperty("ownershipName") 
	private String ownershipName;
	
	@JsonProperty("frequencyCode") 
	private String frequencyCode;
	
	@JsonProperty("currencyCode") 
	private String currencyCode;
	
	@JsonProperty("otherIncomeExpensesId") 
	private String otherIncomeExpensesId;
	
	@JsonProperty("otherIncomeTypeId") 
	private String otherIncomeTypeId;
	
	@JsonProperty("otherExpenseTypeId") 
	private String otherExpenseTypeId;
	
	@JsonProperty("otherIncomeTypeName") 
	private String otherIncomeTypeName;
	
	@JsonProperty("businessIncomeDetailId") 
	private String businessIncomeDetailId;
	
	@JsonProperty("businessIncomeExenseTypes") 
	private String businessIncomeExenseTypes;
	
	@JsonProperty("businessIncomeTypeId") 
	private String businessIncomeTypeId;
	
	@JsonProperty("businessExpenseTypeId") 
	private String businessExpenseTypeId;
	
	@JsonProperty("businessIncomeTypeName") 
	private String businessIncomeTypeName;
	
	@JsonProperty("frequencyName") 
	private String frequencyName;
	
	@JsonProperty("employerId") 
	private String employerId;

	@JsonProperty("employerName") 
	private String employerName;

	@JsonProperty("taxCodeId") 
	private String taxCodeId;

	@JsonProperty("taxCodeName") 
	private String taxCodeName;
	
	@JsonProperty("empNo") 
	private String empNo;
	
	@JsonProperty("empId") 
	private String empId;
	
	@JsonProperty("businessTaxDetailResource") 
	private String businessTaxDetailResource;
	
	@JsonProperty("financialStatementId") 
	private String financialStatementId;

	@JsonProperty("languageId") 
	private String languageId;

	@JsonProperty("languageDesc") 
	private String languageDesc;

	@JsonProperty("branchId") 
	private String branchId;
	
	@JsonProperty("formula") 
	private String formula;
	
	@JsonProperty("financialStatementLevel") 
	private String financialStatementLevel;

	@JsonProperty("branchName") 
	private String branchName;

	@JsonProperty("linkedPersonList-identification") 
	private String linkedPsnIdentification;

	@JsonProperty("customerTypeId") 
	private String customerTypeId;

	@JsonProperty("dateOfBirth") 
	private String dateOfBirth;
	
	@JsonProperty("identificationDetailId") 
	private String identificationDetaiId;
	
	@JsonProperty("gradingScore") 
	private String gradingScore;
	
	@JsonProperty("identificationDetails") 
	private String identificationDetails;
	
	@JsonProperty("contactDetails") 
	private String contactDetails;
	
	@JsonProperty("addressDetails") 
	private String addressDetails;
	
	@JsonProperty("gurantorTypeId") 
	private String gurantorTypeId;
	
	@JsonProperty("contactType") 
	private String contactType;
	
	@JsonProperty("organizationTypeId") 
	private String organizationTypeId;
	
	@JsonProperty("relationshipId") 
	private String relationshipId;
	
	@JsonProperty("addressType") 
	private String addressType;
	
	@JsonProperty("statementTypeId") 
	private String statementTypeId;
	
	@JsonProperty("disbursementConditionsId") 
	private String disbursementConditionsId;
	
	@JsonProperty("disbursementConditionsName") 
	private String disbursementConditionsName;
	
	@JsonProperty("payModeId") 
	private String payModeId;
	
	@JsonProperty("payModeName") 
	private String payModeName;
	
	@JsonProperty("collectedDate") 
	private String collectedDate;
	
	@JsonProperty("documentCheckListDetId") 
	private String documentCheckListDetId;
	
	@JsonProperty("documentTypeName") 
	private String documentTypeName;
	
	@JsonProperty("creditNoteTransactionType") 
	private String creditNoteTransactionType;
	
	@JsonProperty("creditNoteType") 
	private String creditNoteType;
	
	@JsonProperty("transactionSubCode") 
	private String transactionSubCode;
	
	@JsonProperty("currency") 
	private String currency;
	
	@JsonProperty("contactTypeCode") 
	private String contactTypeCode;
	
	@JsonProperty("otherIncomeDetailId") 
	private String otherIncomeDetailId;
	
	public String getLandOwnershipId() {
		return landOwnershipId;
	}

	public void setLandOwnershipId(String landOwnershipId) {
		this.landOwnershipId = landOwnershipId;
	}

	public String getLandOwnershipCode() {
		return landOwnershipCode;
	}

	public void setLandOwnershipCode(String landOwnershipCode) {
		this.landOwnershipCode = landOwnershipCode;
	}

	public String getPlantOwnershipId() {
		return plantOwnershipId;
	}

	public void setPlantOwnershipId(String plantOwnershipId) {
		this.plantOwnershipId = plantOwnershipId;
	}

	public String getPlantOwnershipCode() {
		return plantOwnershipCode;
	}

	public void setPlantOwnershipCode(String plantOwnershipCode) {
		this.plantOwnershipCode = plantOwnershipCode;
	}

	public String getCatExpDuplicate() {
		return catExpDuplicate;
	}

	public void setCatExpDuplicate(String catExpDuplicate) {
		this.catExpDuplicate = catExpDuplicate;
	}

	public String getExpTypeCategory() {
		return expTypeCategory;
	}

	public void setExpTypeCategory(String expTypeCategory) {
		this.expTypeCategory = expTypeCategory;
	}

	public String getBusinessCenterId() {
		return businessCenterId;
	}

	public void setBusinessCenterId(String businessCenterId) {
		this.businessCenterId = businessCenterId;
	}

	public String getNewLoanAmount() {
		return newLoanAmount;
	}

	public void setNewLoanAmount(String newLoanAmount) {
		this.newLoanAmount = newLoanAmount;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getStagingStatus() {
		return stagingStatus;
	}

	public void setStagingStatus(String stagingStatus) {
		this.stagingStatus = stagingStatus;
	}

	public String getLeadInfoId() {
		return leadInfoId;
	}

	public void setLeadInfoId(String leadInfoId) {
		this.leadInfoId = leadInfoId;
	}

	public String getAuthorityGroupId() {
		return authorityGroupId;
	}

	public void setAuthorityGroupId(String authorityGroupId) {
		this.authorityGroupId = authorityGroupId;
	}

	public String getAuthorityGroupName() {
		return authorityGroupName;
	}

	public void setAuthorityGroupName(String authorityGroupName) {
		this.authorityGroupName = authorityGroupName;
	}

	public String getDaLevel() {
		return daLevel;
	}

	public void setDaLevel(String daLevel) {
		this.daLevel = daLevel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getApprovalCategoryId() {
		return approvalCategoryId;
	}

	public void setApprovalCategoryId(String approvalCategoryId) {
		this.approvalCategoryId = approvalCategoryId;
	}

	public String getApprovalCategoryName() {
		return approvalCategoryName;
	}

	public void setApprovalCategoryName(String approvalCategoryName) {
		this.approvalCategoryName = approvalCategoryName;
	}

	public String getDaLimitValue() {
		return daLimitValue;
	}

	public void setDaLimitValue(String daLimitValue) {
		this.daLimitValue = daLimitValue;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMessage() {
		return message;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBusinessTypesId() {
		return businessTypesId;
	}

	public void setBusinessTypesId(String businessTypesId) {
		this.businessTypesId = businessTypesId;
	}

	public String getBusinessTypeCode() {
		return businessTypeCode;
	}

	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}

	public String getBusinessTypeName() {
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	public String getBusinessTypeDescription() {
		return businessTypeDescription;
	}

	public void setBusinessTypeDescription(String businessTypeDescription) {
		this.businessTypeDescription = businessTypeDescription;
	}

	public String getExpenseTypesId() {
		return expenseTypesId;
	}

	public void setExpenseTypesId(String expenseTypesId) {
		this.expenseTypesId = expenseTypesId;
	}

	public String getExpenseTypeCode() {
		return expenseTypeCode;
	}

	public void setExpenseTypeCode(String expenseTypeCode) {
		this.expenseTypeCode = expenseTypeCode;
	}

	public String getExpenseTypeName() {
		return expenseTypeName;
	}

	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
	}

	public String getExpenseTypeDescription() {
		return expenseTypeDescription;
	}

	public void setExpenseTypeDescription(String expenseTypeDescription) {
		this.expenseTypeDescription = expenseTypeDescription;
	}

	public String getCultivationCategoryId() {
		return cultivationCategoryId;
	}

	public void setCultivationCategoryId(String cultivationCategoryId) {
		this.cultivationCategoryId = cultivationCategoryId;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getTotalAssetValue() {
		return totalAssetValue;
	}

	public void setTotalAssetValue(String totalAssetValue) {
		this.totalAssetValue = totalAssetValue;
	}

	public String getTotalLiabilityValue() {
		return totalLiabilityValue;
	}

	public void setTotalLiabilityValue(String totalLiabilityValue) {
		this.totalLiabilityValue = totalLiabilityValue;
	}

	public String getNetworth() {
		return networth;
	}

	public void setNetworth(String networth) {
		this.networth = networth;
	}

	public String getTypeOfAssetId() {
		return typeOfAssetId;
	}

	public void setTypeOfAssetId(String typeOfAssetId) {
		this.typeOfAssetId = typeOfAssetId;
	}

	public String getOwnershipId() {
		return ownershipId;
	}

	public void setOwnershipId(String ownershipId) {
		this.ownershipId = ownershipId;
	}

	public String getTypeOfFacilityId() {
		return typeOfFacilityId;
	}

	public void setTypeOfFacilityId(String typeOfFacilityId) {
		this.typeOfFacilityId = typeOfFacilityId;
	}

	public String getGuarantorId() {
		return guarantorId;
	}

	public void setGuarantorId(String guarantorId) {
		this.guarantorId = guarantorId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getLinkpersonId() {
		return linkpersonId;
	}

	public void setLinkpersonId(String linkpersonId) {
		this.linkpersonId = linkpersonId;
	}

	public String getIdentificationNo() {
		return identificationNo;
	}

	public void setIdentificationNo(String identificationNo) {
		this.identificationNo = identificationNo;
	}

	public String getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}

	public String getIdentificationTypeId() {
		return identificationTypeId;
	}

	public void setIdentificationTypeId(String identificationTypeId) {
		this.identificationTypeId = identificationTypeId;
	}

	public String getAddressTypeId() {
		return addressTypeId;
	}

	public void setAddressTypeId(String addressTypeId) {
		this.addressTypeId = addressTypeId;
	}

	public String getGeoLevelId() {
		return geoLevelId;
	}

	public void setGeoLevelId(String geoLevelId) {
		this.geoLevelId = geoLevelId;
	}

	public String getCountryGeoId() {
		return countryGeoId;
	}

	public void setCountryGeoId(String countryGeoId) {
		this.countryGeoId = countryGeoId;
	}

	public String getContactTypeId() {
		return contactTypeId;
	}

	public void setContactTypeId(String contactTypeId) {
		this.contactTypeId = contactTypeId;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}

	public String getTotalDueAmount() {
		return totalDueAmount;
	}

	public void setTotalDueAmount(String totalDueAmount) {
		this.totalDueAmount = totalDueAmount;
	}

	public String getTotalSettlementAmount() {
		return totalSettlementAmount;
	}

	public void setTotalSettlementAmount(String totalSettlementAmount) {
		this.totalSettlementAmount = totalSettlementAmount;
	}

	public String getSpouseProfessionId() {
		return spouseProfessionId;
	}

	public void setSpouseProfessionId(String spouseProfessionId) {
		this.spouseProfessionId = spouseProfessionId;
	}

	public String getPriorityLevelId() {
		return priorityLevelId;
	}

	public void setPriorityLevelId(String priorityLevelId) {
		this.priorityLevelId = priorityLevelId;
	}

	public String getSecuritySubTypeId() {
		return securitySubTypeId;
	}

	public void setSecuritySubTypeId(String securitySubTypeId) {
		this.securitySubTypeId = securitySubTypeId;
	}

	public String getSecurityTypeId() {
		return securityTypeId;
	}

	public void setSecurityTypeId(String securityTypeId) {
		this.securityTypeId = securityTypeId;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public String getNextMeetingDate() {
		return nextMeetingDate;
	}

	public void setNextMeetingDate(String nextMeetingDate) {
		this.nextMeetingDate = nextMeetingDate;
	}

	public String getNicNo() {
		return nicNo;
	}

	public void setNicNo(String nicNo) {
		this.nicNo = nicNo;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getReasonId() {
		return reasonId;
	}

	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}

	public String getPedingIdentificationId() {
		return pedingIdentificationId;
	}

	public void setPedingIdentificationId(String pedingIdentificationId) {
		this.pedingIdentificationId = pedingIdentificationId;
	}

	public String getFinalValuationFlag() {
		return finalValuationFlag;
	}

	public void setFinalValuationFlag(String finalValuationFlag) {
		this.finalValuationFlag = finalValuationFlag;
	}

	public String getValuerId() {
		return valuerId;
	}

	public void setValuerId(String valuerId) {
		this.valuerId = valuerId;
	}

	public String getCoverNoteNumber() {
		return coverNoteNumber;
	}

	public void setCoverNoteNumber(String coverNoteNumber) {
		this.coverNoteNumber = coverNoteNumber;
	}

	public String getCoverNotePeriodFrom() {
		return coverNotePeriodFrom;
	}

	public void setCoverNotePeriodFrom(String coverNotePeriodFrom) {
		this.coverNotePeriodFrom = coverNotePeriodFrom;
	}

	public String getCoverNotePeriodTo() {
		return coverNotePeriodTo;
	}

	public void setCoverNotePeriodTo(String coverNotePeriodTo) {
		this.coverNotePeriodTo = coverNotePeriodTo;
	}

	public String getCoverNoteStatus() {
		return coverNoteStatus;
	}

	public void setCoverNoteStatus(String coverNoteStatus) {
		this.coverNoteStatus = coverNoteStatus;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getPolicyCoverPeriodFrom() {
		return policyCoverPeriodFrom;
	}

	public void setPolicyCoverPeriodFrom(String policyCoverPeriodFrom) {
		this.policyCoverPeriodFrom = policyCoverPeriodFrom;
	}

	public String getPolicyCoverPeriodTo() {
		return policyCoverPeriodTo;
	}

	public void setPolicyCoverPeriodTo(String policyCoverPeriodTo) {
		this.policyCoverPeriodTo = policyCoverPeriodTo;
	}

	public String getPedingCustomerId() {
		return pedingCustomerId;
	}

	public void setPedingCustomerId(String pedingCustomerId) {
		this.pedingCustomerId = pedingCustomerId;
	}

	public String getHouseHoldExpenseCategoryId() {
		return houseHoldExpenseCategoryId;
	}

	public void setHouseHoldExpenseCategoryId(String houseHoldExpenseCategoryId) {
		this.houseHoldExpenseCategoryId = houseHoldExpenseCategoryId;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public String getValuationDate() {
		return valuationDate;
	}

	public void setValuationDate(String valuationDate) {
		this.valuationDate = valuationDate;
	}

	public String getPenPerId() {
		return penPerId;
	}

	public void setPenPerId(String penPerId) {
		this.penPerId = penPerId;
	}

	public String getCribStatus() {
		return cribStatus;
	}

	public void setCribStatus(String cribStatus) {
		this.cribStatus = cribStatus;
	}

	public String getLinkedPersonId() {
		return linkedPersonId;
	}

	public void setLinkedPersonId(String linkedPersonId) {
		this.linkedPersonId = linkedPersonId;
	}

	public String getBrNumber() {
		return brNumber;
	}

	public void setBrNumber(String brNumber) {
		this.brNumber = brNumber;
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


	public String getDaGroupId() {
		return daGroupId;
	}

	public void setDaGroupId(String daGroupId) {
		this.daGroupId = daGroupId;

	}
	public String getSectorId() {
		return sectorId;
	}

	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}

	public String getSubSectorId() {
		return subSectorId;
	}

	public void setSubSectorId(String subSectorId) {
		this.subSectorId = subSectorId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankBranchId() {
		return bankBranchId;
	}

	public void setBankBranchId(String bankBranchId) {
		this.bankBranchId = bankBranchId;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public String getGenderId() {
		return genderId;
	}

	public void setGenderId(String genderId) {
		this.genderId = genderId;
	}

	public String getProductCategoryCode() {
		return productCategoryCode;
	}

	public void setProductCategoryCode(String productCategoryCode) {
		this.productCategoryCode = productCategoryCode;
	}

	public String getOnboardingStatus() {
		return onboardingStatus;
	}

	public void setOnboardingStatus(String onboardingStatus) {
		this.onboardingStatus = onboardingStatus;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}
	
	
	
	public String getCommitmentTypeId() {
		return commitmentTypeId;
	}

	public void setCommitmentTypeId(String commitmentTypeId) {
		this.commitmentTypeId = commitmentTypeId;
	}

	public String getRepaymentFrequencyId() {
		return repaymentFrequencyId;
	}

	public void setRepaymentFrequencyId(String repaymentFrequencyId) {
		this.repaymentFrequencyId = repaymentFrequencyId;
	}

	public String getFacilityTypeId() {
		return facilityTypeId;
	}

	public void setFacilityTypeId(String facilityTypeId) {
		this.facilityTypeId = facilityTypeId;
	}
	
	public String getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(String frequencyId) {
		this.frequencyId = frequencyId;
	}

	public String getDesignationId() {
		return designationId;
	}

	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}

	public String getBusinessPersonTypeId() {
		return businessPersonTypeId;
	}

	public void setBusinessPersonTypeId(String businessPersonTypeId) {
		this.businessPersonTypeId = businessPersonTypeId;
	}

	public String getIndustryTypeId() {
		return industryTypeId;
	}

	public void setIndustryTypeId(String industryTypeId) {
		this.industryTypeId = industryTypeId;
	}
	public String getExceptionApprovalGroupId() {
		return exceptionApprovalGroupId;
	}

	public void setExceptionApprovalGroupId(String exceptionApprovalGroupId) {
		this.exceptionApprovalGroupId = exceptionApprovalGroupId;
	}

	public String getExceptionTypeId() {
		return exceptionTypeId;
	}

	public void setExceptionTypeId(String exceptionTypeId) {
		this.exceptionTypeId = exceptionTypeId;
	}

	public String getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(String expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public String getExpenseTypeList() {
		return expenseTypeList;
	}

	public void setExpenseTypeList(String expenseTypeList) {
		this.expenseTypeList = expenseTypeList;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getCustomerFullName() {
		return customerFullName;
	}

	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	public String getLinkedPersonname() {
		return linkedPersonname;
	}

	public void setLinkedPersonname(String linkedPersonname) {
		this.linkedPersonname = linkedPersonname;
	}

	public String getIncomeSourceDetailId() {
		return incomeSourceDetailId;
	}

	public void setIncomeSourceDetailId(String incomeSourceDetailId) {
		this.incomeSourceDetailId = incomeSourceDetailId;
	}

	public String getSalaryIncomeDetailsList() {
		return salaryIncomeDetailsList;
	}

	public void setSalaryIncomeDetailsList(String salaryIncomeDetailsList) {
		this.salaryIncomeDetailsList = salaryIncomeDetailsList;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

    public String getIncomeSourceDetails() {
	    return incomeSourceDetails;
    }

	public void setIncomeSourceDetails(String incomeSourceDetails) {
		this.incomeSourceDetails = incomeSourceDetails;
	}

	public String getCalFrequencyCode() {
		return calFrequencyCode;
	}

	public void setCalFrequencyCode(String calFrequencyCode) {
		this.calFrequencyCode = calFrequencyCode;
	}

	public String getOccFrequencyCode() {
		return occFrequencyCode;
	}

	public void setOccFrequencyCode(String occFrequencyCode) {
		this.occFrequencyCode = occFrequencyCode;
	}

	public String getBusiOpertaionStartDate() {
		return busiOpertaionStartDate;
	}

	public void setBusiOpertaionStartDate(String busiOpertaionStartDate) {
		this.busiOpertaionStartDate = busiOpertaionStartDate;
	}

	public String getBusinessRegiDate() {
		return businessRegiDate;
	}

	public void setBusinessRegiDate(String businessRegiDate) {
		this.businessRegiDate = businessRegiDate;
	}

	public String getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(String businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	public String getBusinessSubTypeId() {
		return businessSubTypeId;
	}

	public void setBusinessSubTypeId(String businessSubTypeId) {
		this.businessSubTypeId = businessSubTypeId;
	}

	public String getBusinessSubTypeName() {
		return businessSubTypeName;
	}

	public void setBusinessSubTypeName(String businessSubTypeName) {
		this.businessSubTypeName = businessSubTypeName;
	}

	public String getBusinessSizeId() {
		return businessSizeId;
	}

	public void setBusinessSizeId(String businessSizeId) {
		this.businessSizeId = businessSizeId;
	}

	public String getBusinessSizeName() {
		return businessSizeName;
	}

	public void setBusinessSizeName(String businessSizeName) {
		this.businessSizeName = businessSizeName;
	}

	public String getOwnershipName() {
		return ownershipName;
	}

	public void setOwnershipName(String ownershipName) {
		this.ownershipName = ownershipName;
	}
	public String getFrequencyCode() {
		return frequencyCode;
	}

	public void setFrequencyCode(String frequencyCode) {
		this.frequencyCode = frequencyCode;
	}

	public String getBusinessIncomeDetailId() {
		return businessIncomeDetailId;
	}

	public void setBusinessIncomeDetailId(String businessIncomeDetailId) {
		this.businessIncomeDetailId = businessIncomeDetailId;
	}

	public String getBusinessIncomeExenseTypes() {
		return businessIncomeExenseTypes;
	}

	public void setBusinessIncomeExenseTypes(String businessIncomeExenseTypes) {
		this.businessIncomeExenseTypes = businessIncomeExenseTypes;
	}

	public String getBusinessIncomeTypeId() {
		return businessIncomeTypeId;
	}

	public void setBusinessIncomeTypeId(String businessIncomeTypeId) {
		this.businessIncomeTypeId = businessIncomeTypeId;
	}

	public String getBusinessExpenseTypeId() {
		return businessExpenseTypeId;
	}

	public void setBusinessExpenseTypeId(String businessExpenseTypeId) {
		this.businessExpenseTypeId = businessExpenseTypeId;
	}

	public String getBusinessIncomeTypeName() {
		return businessIncomeTypeName;
	}

	public void setBusinessIncomeTypeName(String businessIncomeTypeName) {
		this.businessIncomeTypeName = businessIncomeTypeName;
	}

	public String getFrequencyName() {
		return frequencyName;
	}

	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
	}

	public String getEmployerId() {
		return employerId;
	}

	public void setEmployerId(String employerId) {
		this.employerId = employerId;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public String getTaxCodeId() {
		return taxCodeId;
	}

	public void setTaxCodeId(String taxCodeId) {
		this.taxCodeId = taxCodeId;
	}

	public String getTaxCodeName() {
		return taxCodeName;
	}

	public void setTaxCodeName(String taxCodeName) {
		this.taxCodeName = taxCodeName;
	}

	public String getBusinessTaxDetailResource() {
		return businessTaxDetailResource;
	}

	public void setBusinessTaxDetailResource(String businessTaxDetailResource) {
		this.businessTaxDetailResource = businessTaxDetailResource;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getLanguageDesc() {
		return languageDesc;
	}

	public void setLanguageDesc(String languageDesc) {
		this.languageDesc = languageDesc;
	}

	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getLinkedPsnIdentification() {
		return linkedPsnIdentification;
	}

	public void setLinkedPsnIdentification(String linkedPsnIdentification) {
		this.linkedPsnIdentification = linkedPsnIdentification;
	}

	public String getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(String customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getIdentificationDetaiId() {
		return identificationDetaiId;
	}

	public void setIdentificationDetaiId(String identificationDetaiId) {
		this.identificationDetaiId = identificationDetaiId;
	}
	
	public String getCheckListCategoryId() {
		return checkListCategoryId;
	}

	public void setCheckListCategoryId(String checkListCategoryId) {
		this.checkListCategoryId = checkListCategoryId;
	}

	public String getGradingScore() {
		return gradingScore;
	}

	public void setGradingScore(String gradingScore) {
		this.gradingScore = gradingScore;
	}
	
	public String getIdentificationDetails() {
		return identificationDetails;
	}

	public void setIdentificationDetails(String identificationDetails) {
		this.identificationDetails = identificationDetails;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	public String getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(String addressDetails) {
		this.addressDetails = addressDetails;
	}

	public String getGurantorTypeId() {
		return gurantorTypeId;
	}

	public void setGurantorTypeId(String gurantorTypeId) {
		this.gurantorTypeId = gurantorTypeId;
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getOrganizationTypeId() {
		return organizationTypeId;
	}

	public void setOrganizationTypeId(String organizationTypeId) {
		this.organizationTypeId = organizationTypeId;
	}

	public String getRelationshipId() {
		return relationshipId;
	}

	public void setRelationshipId(String relationshipId) {
		this.relationshipId = relationshipId;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}


	public String getFinancialStatementId() {
		return financialStatementId;
	}

	public void setFinancialStatementId(String financialStatementId) {
		this.financialStatementId = financialStatementId;
	}

	public String getStatementTypeId() {
		return statementTypeId;
	}

	public void setStatementTypeId(String statementTypeId) {
		this.statementTypeId = statementTypeId;
	}	
	
	public String getDisbursementConditionsId() {
		return disbursementConditionsId;
	}

	public void setDisbursementConditionsId(String disbursementConditionsId) {
		this.disbursementConditionsId = disbursementConditionsId;
	}

	public String getDisbursementConditionsName() {
		return disbursementConditionsName;
	}

	public void setDisbursementConditionsName(String disbursementConditionsName) {
		this.disbursementConditionsName = disbursementConditionsName;
	}

	public String getPayModeId() {
		return payModeId;
	}

	public void setPayModeId(String payModeId) {
		this.payModeId = payModeId;
	}

	public String getPayModeName() {
		return payModeName;
	}

	public void setPayModeName(String payModeName) {
		this.payModeName = payModeName;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getFinancialStatementLevel() {
		return financialStatementLevel;
	}

	public void setFinancialStatementLevel(String financialStatementLevel) {
		this.financialStatementLevel = financialStatementLevel;
	}

	public String getCollectedDate() {
		return collectedDate;
	}

	public void setCollectedDate(String collectedDate) {
		this.collectedDate = collectedDate;
	}

	public String getDocumentCheckListDetId() {
		return documentCheckListDetId;
	}

	public void setDocumentCheckListDetId(String documentCheckListDetId) {
		this.documentCheckListDetId = documentCheckListDetId;
	}

	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}

	
	public String getSubProductId() {
		return subProductId;
	}

	public void setSubProductId(String subProductId) {
		this.subProductId = subProductId;
	}

	public String getCheckListItemId() {
		return checkListItemId;
	}

	public void setCheckListItemId(String checkListItemId) {
		this.checkListItemId = checkListItemId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getOtherIncomeExpensesId() {
		return otherIncomeExpensesId;
	}

	public void setOtherIncomeExpensesId(String otherIncomeExpensesId) {
		this.otherIncomeExpensesId = otherIncomeExpensesId;
	}

	public String getOtherIncomeTypeId() {
		return otherIncomeTypeId;
	}

	public void setOtherIncomeTypeId(String otherIncomeTypeId) {
		this.otherIncomeTypeId = otherIncomeTypeId;
	}

	public String getOtherExpenseTypeId() {
		return otherExpenseTypeId;
	}

	public void setOtherExpenseTypeId(String otherExpenseTypeId) {
		this.otherExpenseTypeId = otherExpenseTypeId;
	}

	public String getOtherIncomeTypeName() {
		return otherIncomeTypeName;
	}

	public void setOtherIncomeTypeName(String otherIncomeTypeName) {
		this.otherIncomeTypeName = otherIncomeTypeName;
	}

	public String getCreditNoteTransactionType() {
		return creditNoteTransactionType;
	}

	public void setCreditNoteTransactionType(String creditNoteTransactionType) {
		this.creditNoteTransactionType = creditNoteTransactionType;
	}

	public String getCreditNoteType() {
		return creditNoteType;
	}

	public void setCreditNoteType(String creditNoteType) {
		this.creditNoteType = creditNoteType;
	}

	public String getTransactionSubCode() {
		return transactionSubCode;
	}

	public void setTransactionSubCode(String transactionSubCode) {
		this.transactionSubCode = transactionSubCode;
	}

	public String getOtherIncomeDetailId() {
		return otherIncomeDetailId;
	}

	public void setOtherIncomeDetailId(String otherIncomeDetailId) {
		this.otherIncomeDetailId = otherIncomeDetailId;
	}

	public String getContactTypeCode() {
		return contactTypeCode;
	}

	public void setContactTypeCode(String contactTypeCode) {
		this.contactTypeCode = contactTypeCode;
	}
	
	
}
