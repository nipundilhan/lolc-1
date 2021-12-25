package com.fusionx.lending.origination.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.enums.ActionType;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.enums.WorkflowType;
import com.fusionx.lending.origination.resource.*;

@Service
public interface ValidateService {

	public Timestamp getCreateOrModifyDate();
	public Timestamp getSyncTs();
	
	public Date formatDate(String date);
	
	/**
	 * Sets the scale.
	 *
	 * @param value the value
	 * @return the big decimal
	 */
	public BigDecimal setScale(BigDecimal value);

	/**
	 * String to long.
	 *
	 * @param value the value
	 * @return the long
	 */
	public Long stringToLong(String value);
	
	/**
	 * String to big decimal.
	 *
	 * @param value the value
	 * @return the big decimal
	 */
	public BigDecimal stringToBigDecimal(String value);
	
	/**
	 * String to float.
	 *
	 * @param value the value
	 * @return the float
	 */
	public Float stringToFloat(String value);

	/**
	 * Invoke micro bpr rules
	 * 
	 * @param workflowType
	 * @param domainPath
	 * @param domain
	 * @return WorkflowRulesResource
	 */
	public WorkflowRulesResource invokedMicroBprRule(WorkflowType workflowType, String domainPath, String domainName, String tenantId);

	/**
	 * Initiate Micro Bpr Workflow
	 * 
	 * @param workflowInitiationRequestResource
	 * @param tenantId
	 * @return process Id
	 */
	public Long initiateMicroBprWorkFlow(WorkflowInitiationRequestResource workflowInitiationRequestResource, String tenantId);
	
	/**
	 * Aborted Workflow
	 *
	 * @param workflowProcessId
	 * @param workflowType
	 * @param username
	 * @param tenantId
	 */
	public void abortedWorkFlow(Long workflowProcessId, WorkflowType workflowType, String username, String tenantId);

	/**
	 * Approve Workflow
	 * 
	 * @param workflowProcessId
	 * @param workflowType
	 * @param username
	 * @param tenantId
	 */
	public void approveWorkFlow(Long workflowProcessId, WorkflowType workflowType, String username, String tenantId);


	public void validateCreditFacilityandFacilityReasonForExternalCrib(ExternalCribValidationResource externalCribValidationResource,
			ServicePoint servicePoint);
	
	/*
	 * validate collateral details
	 * 
	 */
	public void validateExistingCollateraldetails(String tenantId, CreditAppraiselCollateralDetailsAddResource creditAppraiselCollateralDetails);
	
	/*
	 * validate collateral details
	 * 
	 */
	public void validateExistingAssetsEntityDetails(String tenantId, CreditAppraiselCollateralDetailsAddResource creditAppraiselCollateralDetails);

	/*
	 * validate asset type 
	 */
	public AssetTypeResponseResource validateAssetType(String tenantId, Long assetTypeId, String assetTypeName);

	/*
	 * validate asset sub type 
	 */
	public AssetSubTypeResponseResource validateAssetSubType(String tenantId, Long assetSubTypeId, String assetSubTypeName);
	
	/*
	 * validate asset sub type parts
	 */
	public void validateAssetSubTypeParts(String tenantId, Long assetSubTypeId, Long assetPartId, String assetPartName);

	/*
	 * validate asset type sub type
	 */
	public void validateAssetTypeSubType(String tenantId, CreditAppraiselCollateralDetailsAddResource creditAppraiselCollateralDetails);

	/*
	 * validate comn supplies entities
	 */
	public ComnSuppliesEntitiesResource validateComnSuppliesEntities(String tenantId, Long supplierId, String supplierName);

	/*
	 * validate registration authority
	 */
	public RegistrationAuthorityValidationResource validateRegistrationAuthority(String tenantId, Long regAuthorityId, String regAuthority);

	/*
	 * validate and integrate with collateral asset details service
	 */
	public Long validateAndIntergrateWithCollateral(String tenantId, String createdUser,
			AssetsDetailsRequestResource assetsDetailsRequestResource, Long id);

	/**
	 * @author Senitha
	 * 
     * Validate company
     * @param String tenantId   - tenantId
     * @param String companyId   - companyId
     * @param String companyName   - companyName
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Bank class
     * 
     */
	public BankResponse validateCompany(String tenantId, Long companyId,String companyName);
	
	/**
	 * @author Senitha
	 * 
     * Get company name
     * @param String tenantId   - tenantId
     * @param String companyId   - companyId
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Bank class
     * 
     */
	public BankResponse getCompanyName(String tenantId, Long companyId);

	public ResponseGuarantorValueResource createGuarantor(String tenantId,AddSuppliesBasicInfoRequestResource guarantorInfor,String createdUser);

	public PersonCodeResponce generatePersonCode(String tenantId, String code);

	public GuarantorCodeResponce generateGuarantorCode(String tenantId, String code);

	public ResponseGuarantorIdentificationResource createGuarantorIdentification(String tenantId,AddPersonIdentificationRequestResource guarantorIdentification, String createdUser);

	public ResponseGuarantorAddressResource createGuarantorAddress(String tenantId,AddPersonAddressRequestResource guarantorAddressRequest, String createdUser);

	public ResponseGuarantorContactResource createGuarantorContact(String tenantId,	AddPersonContactRequestResource guarantorContactRequest, String createdUser);
	
	/**
	 * @author Senitha
	 * 
     * Validate Identification Type
     * @param String tenantId   - tenantId
     * @param String identificationTypeId   - id
     * @param String identificationTypeCode   - idtpCode
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Identification Type
     * 
     */
	public IdentificationType validateIdentificationType(String tenantId, Long id, String idtpCode);
	
	/**
	 * @author Senitha
	 * 
     * Validate Identification Number
     * @param String tenantId   - tenantId
     * @param String identificationTypeId   - id
     * @param String identificationNumber   - identificationNumber
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Identification Type
     * 
     */
	public IdentificationType validateIdentificationNumber(String tenantId, Long id, String identificationNo);
	
	/**
	 * @author Senitha
	 * 
     * Get Identification Type
     * @param String tenantId   - tenantId
     * @param String identificationTypeId   - id
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Identification Type
     * 
     */
	public IdentificationType getIdentificationTypeName(String tenantId, Long id);
	
	/**
	 * @author Senitha
	 * 
     * Validate Contact Type
     * @param String tenantId   - tenantId
     * @param String conatactTypeId   - id
     * @param String conatactTypeCode   - cntpCode
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of ContactType class
     * 
     */
	public ContactType validateContactType(String tenantId, Long id, String cntpCode);
	
	/**
	 * @author Senitha
	 * 
     * Get Contact Type
     * @param String tenantId   - tenantId
     * @param String conatactTypeId   - id
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of ContactType class
     * 
     */
	public ContactType getContactType(String tenantId, Long id);
	
	/**
	 * @author Senitha
	 * 
     * Validate Contact Number
     * @param String tenantId   - tenantId
     * @param String contactTypeId   - contactTypeId
     * @param String contactNumber   - contactNumber
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of ContactType class
     * 
     */
	public ContactType validateContactNumber(String tenantId, Long contactTypeId, String contactNumber);

	public PendingSuppliesBasicInfoResponseResource getPendingPersonOrSuplies(String tenantId,GuarantorAddResource guarantorAddResource);

	public ResponseGuarantorUpdateResource updatePendingSuplies(String tenantId,PendingSuppliesBasicInfoResponseResource pendingSupplies, UpdateSuppliesBasicInfoRequestResource updateSupplies,String updateUser);

	public SuppliesBasicInfoResponseResource getPersonOrSuplies(String tenantId,GuarantorAddResource guarantorAddResource);

	public ResponseGuarantorUpdateResource updateSuplies(String tenantId, SuppliesBasicInfoResponseResource suppliesDet,UpdateSuppliesBasicInfoRequestResource supplies, String updateUser);
	
	/**
	 * @author Senitha
	 * 
     * Validate Asset Type
     * @param String tenantId   - tenantId
     * @param String assetTypeId   - assetTypeId
     * @param String assetTypeName   - assetTypeName
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of ContactType class
     * 
     */
	public AssetTypeSubType validateSecurityType(String tenantId, Long assetTypeId, String assetTypeName);
	
	/**
	 * @author Senitha
	 * 
     * Get Asset Type
     * @param String tenantId   - tenantId
     * @param String assetTypeId   - assetTypeId
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of ContactType class
     * 
     */
	public AssetTypeSubType getSecurityType(String tenantId, Long assetTypeId);
	
	/**
	 * @author Senitha
	 * 
     * Validate Asset Sub Type
     * @param String tenantId   - tenantId
     * @param String assetSubTypeId   - assetSubTypeId
     * @param String assetSubTypeName   - assetSubTypeName
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of ContactType class
     * 
     */
	public AssetTypeSubType validateSecuritySubType(String tenantId, Long assetTypeId, Long assetSubTypeId, String assetSubTypeName);
	
	/**
	 * @author Senitha
	 * 
     * Get Asset Sub Type
     * @param String tenantId   - tenantId
     * @param String assetSubTypeId   - assetSubTypeId
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of ContactType class
     * 
     */
	public AssetTypeSubType getSecuritySubType(String tenantId, Long assetSubTypeId);


	public void validatePledgeType(String tenantId, Long assetTypeId, Long pledgeTypeId, String pledgeTypeName);

	public void validateCollateraldetailsForUpdate(String tenantId,
			CreditAppraiselCollateralDetailsUpdateResource creditAppraiselCollateralDetailsUpdateResource);

	public Long initiateWorkFlowDynamic(String url, DynaBPMStaticParamsResource bpmRequestResource, WorkflowType workflowType);
	public CustomerCodeResponce generateCustomerCode(String tenantId, String code);

	public ResponseCustomerValueResource createCustomer(String tenantId,AddCustomerBasicInfoRequestResource customerInfor, String userName);

	public ResponseCustomerAddressResource createCustomerAddress(String tenantId,AddPersonAddressRequestResource customerAddressRequest, String userName);

	public ResponseCustomerIdentificationResource createCustomerIdentification(String tenantId,AddPersonIdentificationRequestResource customerIdentification, String userName);

	public ResponseCustomerContactResource createCustomerContact(String tenantId,AddPersonContactRequestResource customerContactRequest, String userName);

	public CustomerBasicInfoResponseResource getPersonOrCustomer(String tenantId,@Valid CustomerResource customerResource);

	public ResponseCustomerValueResource updateCustomer(String tenantId,UpdateCustomerBasicInfoRequestResource customerDet, CustomerBasicInfoResponseResource customer,String userName);

	public PendingCustomerBasicInfoResponseResource getPendingPersonOrCustomer(String tenantId,CustomerResource customerResource);

	public ResponseCustomerValueResource updatePendingCustomer(String tenantId,UpdateCustomerBasicInfoRequestResource customerDet, CustomerResource customerResource, String userName);


	/**
	 * Bulk Finish Pending Customers
	 * @author MenukaJ
	 * @param PendingCutomerApproveRequestResource
	 * @param username
	 * @param tenantId
	 * @return list PendingCustomerFinishResponce
	 */
	public List<PendingCustomerFinishResponce> bulkFinishPendingCustomer(String tenantId, PendingCutomerApproveRequestResource customerIds, String userName);

	/**
	 * Bulk Finish Pending Supplies
	 * @author MenukaJ
	 * @param PendingSuppliesApproveRequestResource
	 * @param username
	 * @param tenantId
	 * @return PendingSuppliesFinishResponce
	 */
	public List<PendingSuppliesFinishResponce> bulkFinishPendingSupplies(String tenantId, PendingSuppliesApproveRequestResource supplierIds, String userName);

	public ResponseCustomerRelationshipResource saveCommonCustomerRelationship(String tenantId, String createdUser,AddRelationshipBasicInfoRequestResource addRelationshipBasicInfoRequestResource,Long pendingCustomerId);

	public ResponseCustomerRelationshipIdentificationResource saveCommonCustomerRelationshipIdentification(String tenantId, String createdUser,AddCustomerPersonIdentificationRequestResource addPersonIdentificationRequestResource,Long pendingCustomerId);     

	/**
	 * Validate for Valuer
	 *
	 * 
	 * */
	public ComnSuppliesEntitiesResource validateValuer(String tenantId, Long valuerId, String valuerRefCode);

//	public ValuationDetailsResponseResource validateValuationDetails(String tenantId, Long assetEntityId);

	public LastValuationDateResponseResource validateLastValuationDate(String tenantId, Long assetEntityId);

	
	public void futureDateValidation(String date, ServiceEntity serviceEntity);
	
	public void pastDateValidation(String date, ServiceEntity serviceEntity);

//	public void valuationDetInteregation(String tenantId, String createdUser,
//			ValuationDetailsListAddResource valuationDetailsAddResourceList, Long assetEntityId);

	public Long valuationDetInteregation(String tenantId, String createdUser,
			ValuationDetailsListAddResource valuationDetailsAddResourceList, Long assetEntityId);
	
//	public void valuationDetInteregation(String tenantId, String createdUser,
//			ValuationDetailsAddResource valuationDetails, Long assetEntityId);

	public void valuationDetUpdateInteregation(String tenantId, String createdUser,
			ValuationDetailsUpdateResource valuationDetails);

	public Long insuranceDetAddColInteregation(String tenantId, String createdUser,
			InsuranceDetailsAddResource insuranceDetails, ActionType save);

	public CurencyResponse validateCurrency(String tenantId, String applicableCurrencyId, String comnCurrencyUrl,
			String applicableCurrencyCode, Class<CurencyResponse> class1);


	public AssetEntityResponseResource validateAssetEntity(String tenantId, Long assetEntityId, ServicePoint servicePoint);

	public void validateAssetEntityInsuDetails(String tenantId, String insuId,Long assetEntityId, ActionType save, String string);

	public ValuationAndInsuranceDetListResponseResource getValuationsForasset(String tenantId, Long assetEntityId);

	public ValuationAndInsuranceDetListResponseResource getInsuranceForasset(String tenantId, Long assetEntityId);

	public ResponseCustomerOnboardingResource saveCustomerOnboarding(String tenantId,AddCustomerOnBoardRequestResource addCustomerOnBoardRequestResource,String userName);

	public ResponseCustomerOnboardProductResource saveCustomerOnboardProduct(String tenantId,AddCustomerOnBoardProductRequestResource addCustomerOnBoardProductRequestResource, String userName);

	public void validateComnPerson(String tenantId, String perId, String perCode, ServicePoint relExternalCrib);

	public void validatePendingPerson(String tenantId, String penPerId, ServicePoint relExternalCrib);

	void validatePerCommonListDet(String tenantId, String genderComListId, String relationGender,
			ServicePoint relExternalCrib, String refCode);

	/**
	 * Bulk Finish Pending Assert Entity
	 * @author MenukaJ
	 * @param AssertApproveRequestResource
	 * @param username
	 * @param tenantId
	 * @return SuccessAndErrorDetailsResource
	 */
	public SuccessAndErrorDetailsResource bulkFinishAssetEntity(String tenantId, AssertApproveRequestResource approveRequestResource, String userName);


	public CustomerOnBoardResource getOnboardPendingCustomerDetail(String tenantId, Long parseLong);

	public List<PendingPersonResponseIdentificationResource> getAllPendingPersonIdentification(String tenantId,String penCustomerrId);

	public AssetSubTypePartsResponseResource validateAssetSubTypeParts(String tenantId, Long assetPartId, String assetPartName);

	public AssetTypeSubTypeResponseResource validateAssetTypeSubType(String tenantId, Long subTypeId, String subTypeName);

	public AssetTypePledgeTypeMappingResource validatePledgeType(String tenantId, Long pledgeTypeId, String pledgeTypeName);

	ValidResource validate(String tenantId, Long perndId);

	public boolean validateCommonListCribStatus(String cribStatusId, String cribStatus);

	public void validateCorporateCategory(String tenantId, String corporateCategoryId, String corporateCategoryName,ServicePoint customer, String refCode);
	
	/**
	 * @author Sanatha
	 * 
     * Validate Sector
     * @param String tenantId   - tenantId
     * @param Long sectorId   - sectorId
     * @param String sectorCode   - sectorCode
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Sector class
     * 
     */
	public SectorResponseResource validateSector(String tenantId, Long sectorId,String sectorCode);
	
	/**
	 * @author Sanatha
	 * 
     * Get Sector name
     * @param String tenantId   - tenantId
     * @param Long sectorId   - sectorId
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Sector class
     * 
     */
	public SectorResponseResource getSectorName(String tenantId, Long sectorId);
	
	/**
	 * @author Sanatha
	 * 
     * Validate Sub Sector
     * @param String tenantId   - tenantId
     * @param Long sectorId   - sectorId
     * @param Long subSectorId   - subSectorId
     * @param String subSectorCode   - subSectorCode
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Sub Sector class
     * 
     */
	public SubSectorResponseResource validateSubSector(String tenantId, Long sectorId, Long subSectorId, String subSectorCode);
	
	/**
	 * @author Sanatha
	 * 
     * Get Sub Sector name
     * @param String tenantId   - tenantId
     * @param Long subSectorId   - subSectorId
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Sub Sector class
     * 
     */
	public SubSectorResponseResource getSubSectorName(String tenantId, Long sectorId,  Long subSectorId);
	
	/**
	 * @author Sanatha
	 * 
     * Validate Bank
     * @param String tenantId   - tenantId
     * @param String bankId   - bankId
     * @param String bankCode   - bankCode
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Bank class
     * 
     */
	public BankResponse validateBank(String tenantId, Long bankId,String bankCode, ServiceEntity serviceEntity, ServicePoint servicePoint, Integer index);
	
	/**
	 * @author Sanatha
	 * 
     * Get Bank name
     * @param String tenantId   - tenantId
     * @param String bankId   - bankId
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Bank class
     * 
     */
	public BankResponse getBankName(String tenantId, Long bankId);
	
	/**
	 * @author Sanatha
	 * 
     * Validate Bank Branch
     * @param String tenantId   - tenantId
     * @param String bankId   - bankId
     * @param String bankBranchId   - bankBranchId
     * @param String bankBranchCode   - bankBranchCode
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Bank Branch class
     * 
     */
	public BankBranchResponseResource validateBankBranch(String tenantId, Long bankId, Long bankBranchId, String bankBranchCode, ServiceEntity serviceEntity, ServicePoint servicePoint, Integer index);
	
	/**
	 * @author Sanatha
	 * 
     * Get Bank Branch name
     * @param String tenantId   - tenantId
     * @param String bankBranchId   - bankBranchId
     * @param String serviceUrl   - serviceUrl
     * @return - JSON Array of Bank Branch class
     * 
     */
	public BankBranchResponseResource getBankBranchName(String tenantId, Long bankBranchId);
	
	public PendingSuppliesBasicInfoResponseResource getPendingSupliesDetail(String tenantId, String pendingCustomerrId);

	public PendingCustomerBasicInfoResponseResource getPendingCustomerDetail(String tenantId,String pendingCustomerrId);

	public RepaymentFrequencyResponse validateRepaymentFrequency(String tenantId, String repaymentFrequencyId);
	/**
	 * @author Piyumi
	 */
	ResponseCustomerKeyPersonResource saveCommonCustomerKeyPerson(String tenantId, String createdUser,
			AddKeyPersonBasicInfoRequestResource addKeyPersonBasicInfoRequestResource, Long pendingCustomerId);

	/**
	 * @author Piyumi
	 */
	 ResponseCustomerRelationshipResource updateCustomerRelationship(String tenantId,
			UpdateRelationshipBasicInfoRequestResource updateRelationshipBasicInfoRequestResource, Long pendingCustomer,Long pculpId ,
			String userName);

	/**
	 * @author Piyumi
	 */
	ResponseCustomerKeyPersonResource updateCustomerKeyPerson(String tenantId,
			UpdateKeyPersonBasicInfoRequestResource updateKeyPersonBasicInfoRequestResource, Long pendingCustomer,
			Long pculpId, String userName);

	/**
	 * @author Piyumi
	 */
	ResponseCustomerRelationshipIdentificationResource updateLinkedPersonIdentification(String tenantId,
			UpdateCustomerPersonIdentificationRequestResource updateCustomerPersonIdentificationRequestResource,
			Long pendingCustomer, Long pculpId, Long ppidtId, String userName);
	/**
	 * @author Piyumi
	 */
	
	CommonBranchResponseResource validateCommonBranch(String tenantId, String branchId, String branchName);

	/**
	 * @author Dilhan
	 */
    FrequencyResponse validateFrequency(String tenantId, String frequencyId);
    /**
	 * @author Dilhan
	 */
	DesignationResponse validateDesignation(String tenantId, String designationId);
	
	/**
	 * @author Dilki
	 */
	//ProductResponse validateProduct(String tenantId, String productId);
    /**
	 * @author Dilki
	 */
	//SubProductResponse validateSubProduct(String tenantId, String subProductId);
	
	
	/**
	 * @author SewwandiH
	 */
	
	public BusinessPersonType getBusinessPersonType(String tenantId, Long businessTypeId);
	
	public BusinessPersonType validateBusinessPersonType(String tenantId, String businessTypeId, String businessTypeName, ServiceEntity serviceEntity);

	/**
	 * @author Piyumi
	 */
	UserProfileResponse validateUserProfileByUserId(String tenantId, String userId, String userName);

	public IndustryType validateIndustryType(String tenantId, String industryTypeId, String industryTypeName, ServiceEntity serviceEntity);

	public IndustryType getIndustryType(String tenantId, Long industryTypeId);
	
	Integer stringToInteger(String value);
	
	/**
	 * @author Piyumi
	 * @return CommonListRemote
	 */
	PerCommonList validatePersonCommonList(String tenantId, String id, String desc, String refCode, Integer index,
			ServiceEntity serviceEntity, ServicePoint servicePoint, String field);
	/**
	 * @author Dilki
	 */
	public Boolean validateBaseUrl(String tenantId, String serviceUrl, Class<?> classObject);
	ProductResponse validateProduct(String tenantId, String mainProductId);
	SubProductResponse validateSubProduct(String tenantId, String subProductId);
	public ContactTypeResponse validationContactType(String tenantId, String id);

	/**
	 * @author Piyumi
	 * @return CommonListRemote
	 */
	Object updateCustomerEmployment(String tenantId,
			CustomerEmploymentUpdateRequestResource customerEmploymentUpdateRequestResource, Long customerId,
			String userName);
	/**
	 * @author Piyumi
	 */
	UserProfileResponse getUserProfileById(String tenantId, String id); 
	
	public CurencyResponse validateCurrencyType(String tenantId, String currencyId, String currencyName);
	
	/**
	 * @author Piyumi
	 */
	CommonListRemote validateComnCommonList(String tenantId, String id , ServiceEntity serviceEntity , ServicePoint servicePoint);
	/**
	 * @author Piyumi
	 */
	Double stringToDouble(String value);
	/**
	 * @author Piyumi
	 */
	DocumentResponse validateDocument(String tenantId, String documentId, String documentName,
			ServicePoint servicePoint, String origin, Integer index);
	/**
	 * @author Piyumi
	 */
	EmployerResponse validateEmployerById(String tenantId, String employerId, String employerName);
	
	/**
	 * @author Piyumi
	 */
	TaxCodeResponse validateTaxCode(String tenantId, String taxCodeId, String taxCodeName);
	/**
	 * @author Piyumi
	 */
	LanguageResponse validateLanguageById(String tenantId, String languageId, String languageDesc);
	
	/**
	 * @author SewwandiH
	 */
	DocumentMaintenanceResource validateDocumentMaintenances(String tenantId, String documentId, String documentName);
	
	DocumentMaintenanceResource getDocumentMaintenance(String tenantId, Long documentId);
	
	IdentificationDetailResponse validateIdentificationDetail(String tenantId, String id, String description, String identificationNo);
	
	DisbursementConditionsResponse validateDisbursementConditions(String tenantId, String id, String name);
	
	PayModeResponse validatePayMode(String tenantId, String id, String name);
	
	DocumentTypeResponse validateDocumentType(String tenantId, String id, String name);

	public UserProfileResponse validateUserProfileById(String tenantId, Long userId, String userName);
}
