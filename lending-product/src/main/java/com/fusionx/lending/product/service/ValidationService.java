package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.RiskTemplateResponse;
import com.fusionx.lending.product.domain.TaxCodeResponse;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.ServicePoint;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.resources.*;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * API Service related to validation service.
 *
 * @author Senitha Perera
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description     Verified By     Verified Date
 * <br/>
 * ....................................................................................................................................<br/>
 * 1        04-06-2020      -               FX-6514             Senitha Perera          Created         ChinthakaMa     16-Sep-2021
 * <p>
 */
public interface ValidationService {


    /**
     * Validate service period.
     *
     * @param tenantId    the tenant id
     * @param periodId    the period id
     * @param periodName  the period name
     * @param entityPoint the entry point
     * @return the period response
     * @see PeriodResponse
     * @see EntityPoint
     */
    PeriodResponse validatePeriod(String tenantId, String periodId, String periodName, EntityPoint entityPoint);

    /**
     * Returns the Periods
     *
     * @param tenantId    the tenant id
     * @param periodId    the period id
     * @param entityPoint the entry point
     * @return the period response
     */
    PeriodResponse getPeriod(String tenantId, String periodId, EntityPoint entityPoint);


    /**
     * Validate the given currency code
     *
     * @param tenantId     the tenant id
     * @param currencyId   the currency id
     * @param currencyName the currency name
     * @param entityPoint  the entry point
     * @return the validated currency
     */
    Currency validateCurrency(String tenantId, String currencyId, String currencyName, EntityPoint entityPoint);


    /**
     * Validates the given country
     *
     * @param tenantId    the tenant id
     * @param countryId   the country id
     * @param countryName the country name
     * @param entityPoint the entry point
     * @return the validated country
     */
    Country validateCountry(String tenantId, String countryId, String countryName, EntityPoint entityPoint);


    /**
     * Validates the given legal structure
     *
     * @param tenantId           the tenant id
     * @param legalStructureId   the legal structure id
     * @param legalStructureName the legal structure name
     * @param entityPoint        the entry point
     * @return the Validated Legal Structure
     */
    LegalStructure validateLegalStructure(String tenantId, String legalStructureId, String legalStructureName, EntityPoint entityPoint);


    /**
     * Validates the residency type
     *
     * @param tenantId          the tenant id
     * @param residencyTypeId   the residency type id
     * @param residencyTypeName the residency type name
     * @param entityPoint       the entry point
     * @return the validate residency type
     */
    ResidencyType validateResidencyType(String tenantId, String residencyTypeId, String residencyTypeName, EntityPoint entityPoint);

    /**
     * Validates the given document id
     *
     * @param tenantId    the tenant id
     * @param documentId  the document id
     * @param entityPoint the entry point
     * @return the validate document
     */
    ExistsDocumentResponseResource existDocument(String tenantId, String documentId, EntityPoint entityPoint);

    /**
     * Gets the creates the or modify date.
     *
     * @return the creates the or modify date
     */
    Timestamp getCreateOrModifyDate();

    /**
     * Converts string to long
     *
     * @param value the string value
     * @return the converted value
     */
    Long stringToLong(String value);

    /**
     * Gets   residency type by id
     *
     * @param tenantId        the tenant id
     * @param residencyTypeId the residency type id
     * @return the validated residency type
     */
    ResidencyType getResidencyType(String tenantId, Long residencyTypeId);

    /**
     * Validates the frequency type
     *
     * @param tenantId          the tenant id
     * @param frequencyTypeId   the frequency type id
     * @param frequencyTypeName the frequency type name
     * @param unit              the unit
     * @param entityPoint       the entry point
     * @return the validate frequency
     */
    FrequencyResponse validateFrequency(String tenantId, Long frequencyTypeId, String frequencyTypeName, Long unit, EntityPoint entityPoint);

    /**
     * Gets frequency type by id
     *
     * @param tenantId        the tenant id
     * @param frequencyTypeId the frequency type id
     * @param entityPoint     the entry point
     * @return the frequency type
     */
    FrequencyResponse getFrequency(String tenantId, Long frequencyTypeId, EntityPoint entityPoint);

    CommonBranchResponseResource validateCommonBranch(String tenantId, String branchId, String branchName);
    /**
     * Invoked Lending Product Rule
     *
     * @param workflowType the workflow type
     * @param domainPath   the domain path
     * @param domainName   the domain name
     * @param tenantId     the tenant id
     * @return the Workflow rules
     * @author Menuka
     */
    WorkflowRulesResource invokedLendingProductRule(WorkflowType workflowType, String domainPath, String domainName, String tenantId);


    /**
     * Initiate the given Lending Product workflow
     *
     * @param workflowInitiationRequestResource the workflow request
     * @param tenantId                          the tenant id
     * @return the id of the initiated workflow.
     * @author Menuka Jayasinghe
     */
    Long initiateLendingProductWorkFlow(WorkflowInitiationRequestResource workflowInitiationRequestResource, String tenantId);

    /**
     * Abort the given Lending Product WorkFlow
     *
     * @param workflowProcessId the workflow process id
     * @param workflowType      the workflow type
     * @param tenantId          the tenant id
     * @param username          the user name
     * @author Menuka Jayasinghe
     */
    void abortedWorkFlow(Long workflowProcessId, WorkflowType workflowType, String username, String tenantId);

    /**
     * Approve the given Lending Product WorkFlow
     *
     * @param workflowProcessId the workflow process id
     * @param workflowType      the workflow type
     * @param tenantId          the tenant id
     * @param username          the user name
     * @author Menuka
     */
    void approveWorkFlow(Long workflowProcessId, WorkflowType workflowType, String username, String tenantId);


    /**
     * Validates the given transaction code
     *
     * @param tenantId          the tenant id
     * @param transactionCodeId the transaction code id
     * @param transactionCode   the transaction code
     * @param entityPoint       the entry point
     * @return the validated transaction code
     * @author Senitha Perera
     */
    BankTransactionCodeResponse validateTransactionCode(String tenantId, Long transactionCodeId, String transactionCode, EntityPoint entityPoint);

    /**
     * Gets transaction code details by id
     *
     * @param tenantId          the tenant id
     * @param transactionCodeId the transaction code
     * @param entityPoint       the entry point
     * @return the transaction details
     * @author Senitha Perera
     */
    BankTransactionCodeResponse getTransactionCode(String tenantId, Long transactionCodeId, EntityPoint entityPoint);


    /**
     * Validates the transaction sub code
     *
     * @param tenantId             the tenant id
     * @param transactionCodeId    the transaction code id
     * @param transactionSubCodeId the transaction sub code id
     * @param transactionSubCode   the transaction sub code
     * @param entityPoint          the entry point
     * @return the validated transaction sub code details.
     * @author Senitha Perera
     */
    BankTransactionSubCodeResponse validateTransactionSubCode(String tenantId, Long transactionCodeId, Long transactionSubCodeId, String transactionSubCode, EntityPoint entityPoint);

    /**
     * Gets transaction sub code details
     *
     * @param tenantId             the tenant id
     * @param transactionSubCodeId the transaction sub code id
     * @param entityPoint          the entry point
     * @return the transaction sub code details.
     */
    BankTransactionSubCodeResponse getTransactionSubCode(String tenantId, Long transactionSubCodeId, EntityPoint entityPoint);


    /**
     * Gets the currency.
     *
     * @param tenantId   the tenant id
     * @param currencyId the currency id
     * @return the currency
     * @author Miyuru Wijesinghe
     */
    Currency getCurrency(String tenantId, Long currencyId);


    /**
     * Gets the asset type.
     *
     * @param tenantId    the tenant id
     * @param assetTypeId the asset type id
     * @return the asset type
     * @author Miyuru Wijesinghe
     */
    AssetType getAssetType(String tenantId, Long assetTypeId);


    /**
     * Validates asset type.
     *
     * @param tenantId      the tenant id
     * @param assetTypeId   the asset type id
     * @param assetTypeName the asset type name
     * @param entityPoint   the entity point
     * @return the asset type
     * @author Miyuru Wijesinghe
     */
    AssetType validateAssetType(String tenantId, String assetTypeId, String assetTypeName, EntityPoint entityPoint);

    /**
     * Converts String to BigDecimal
     *
     * @param value the value to be convert
     * @return the converted value
     */
    BigDecimal stringToBigDecimal(String value);

    /**
     * Validates the given transaction sub code
     *
     * @param tenantId    the tenant id
     * @param subCodeId   the sub code id
     * @param subCodeDesc the sub code description
     * @param entityPoint the entry point
     * @return the validated transaction sub code
     */
    BankTransactionSubCodeResponse validateBankTransactionSubCode(String tenantId, String subCodeId, String subCodeDesc, EntityPoint entityPoint);

    /**
     * Validates the given geo hierarchy
     *
     * @param tenantId     the tenant id
     * @param id           the id of geo hierarchy
     * @param name         the name of geo hierarchy
     * @param parentId     the parent record id
     * @param geoLevelCode the geo level code
     * @return the Geo Response.
     */
    GeoResponse  validateGeoHierachy(String tenantId,String id ,String name , String parentId ,String geoLevelCode ,String path );


    UserProfileResponse validateUserProfileByUserId(String tenantId, String userId, String userName);

    /**
     * @author Dilki
     * Validates the given ComnSector
     *
     * @param tenantId     the tenant id
     * @param id           the id of ComnSector
     * @param name         the name of ComnSector 
     * @return the ComnSector
     */
    ComnSector validateComnSector(String tenantId, String id,String sectorName, EntityPoint entityPoint);
	
	Timestamp getSyncTs();

	Date formatDate(String date);
	
	public void customerSubTypeNonIndExistValidation(String tenantId, Long customerSubCategoryNonIndId, String customerSubCategoryNonIndDesc);
	
	public void customerSubTypeIndExistValidation(String tenantId, Long customerSubCategoryIndId, String customerSubCategoryIndDesc);
	
	public void customerResidentTypeExistValidation(String tenantId, Long customerResidentTypeId, String customerResidentTypeDesc);

	public void prodCategoryValidation(String tenantId, Long productCategoryId, String productCategoryDesc);

	public void customerCategoryExistValidation(String tenantId, Long customerCategoryId, String customerCategoryCode, String customerCategory);

	public void applicableProductValidation(String tenantId, Long applicableProductId, String applicableProductName);
	
	public CommonBranchResponseResource validateComnBranch(String tenantId, String id, String branchName, EntityPoint entityPoint);
	
	/**
     * @author Dilki
     * Validates the given Tax Code
     *
     * @param tenantId     the tenant id
     * @param id           the id of Tax Code  
     * @param name         the name of Tax Code 
     * @return the Tax Code 
     */
    TaxCodeResponse validateTaxCode(String tenantId, String taxCodeId);
    
    RiskTemplateResponse validateRiskTemplate(String tenantId, String riskTemplateId);

	public RepaymentFrequencyResponse validateRepaymentFrequency(String tenantId, String repaymentFrequencyId);

	/**
     * Validates the document details
     *
     * @param tenantId     the tenant id
     * @param id           the id of document
     * @param name         the name of document
     * @return the Document Response.
     */
	DocumentResource validateDocument(String tenantId, Long documentId,String documentName);
	//DocumentResource validateDocument(String tenantId, Long documentId,String documentName, ServiceEntity serviceEntity, ServicePoint servicePoint, Integer index);

	PerCommonList validatePersonCommonList(String tenantId, String id, String desc, String refCode,
			ServiceEntity serviceEntity, EntityPoint entityPoint,String fileld);
	
	PerCommonList getPersonCommonListItemById(String tenantId, String id );

    /**
     * Converts string to Timestamp
     *
     * @param strDate the value of date
     * @return The converted value into Timestamp
     */
    Timestamp StringToTimeStamp(String strDate);
    
    BankTransactionSubCodeResponse getTransactionTypeCode(String tenantId, String transactionSubCodeId);
    
    /**
	 * @author SewwandiH
	 */
	
	public OfficerType getOfficerType(String tenantId, Long officerTypeId);
	
	public OfficerType validateOfficerType(String tenantId, String businessTypeId, String businessTypeName);

    TaxEventResponse getTaxEvent(String tenantId, String taxCode);

    /**
     * get customer by id
     *
     * @param tenantId the tenant id
     * @param customerId the customer id
     * @return the customer object
     */
    Object getCustomerById(String tenantId, String customerId);

    /**
     * get collaterals by id
     *
     * @param tenantId the tenant id
     * @param collateralsId the collaterals id
     * @return the collaterals object
     */
    Object getCollateralById(String tenantId, String collateralsId);

    /**
     * get customer by lead id
     *
     * @param tenantId the tenant id
     * @param customerId the customer id
     * @param leadId the lead id
     * @return the customer object
     */
    CustomerRequestResponseResource getCustomerByLeadId(String tenantId, String customerId, String leadId);
    
    

	
	CustomerDetResource getCustomerDet(String tenantId, String customerId); 
	
	TaxDeclarationCustomerTypeResponse getCustomerTaxDeclarationDet(String tenantId, String customerId , String controllerUrl);
	
    LeadInfoRequestResponseResource getLeadInfoById(String tenantId, String leadId);
    
    CustomerPendDetResource getPendingCustomerDet(String tenantId, String customerId);
    
    /**
     * Initiate the credit appraisal workflow
     *
     * @param CreateCreditAppraisalProcessInstanceRequest the workflow request
     * @param tenantId                          the tenant id
     * @return the id of the initiated workflow.
     * @author Achini
     */
   Long initiateCreditAppraisalWorkFlow(CreateCreditAppraisalProcessInstanceRequest processInstanceRequest, String tenantId);
    String workFlowTaskStatesUpdate(BpmTaskRequest bpmTaskRequest, String tenantId, String userId,String taskStates);
    CreditAppraisalResponse getAllUpdateTasks(Pageable pageable,String tenantId,CreditAppraisalTaskListRequest taskListRequest);
   String creditAppraisalWorkFlowApproveTaskComplete(CreditAppraisalApproveTaskInstanceRequest approveTaskInstanceRequest, String tenantId,String userId,String taskStates);
    String creditAppraisalWorkFlowUpdateTaskComplete(CreditAppraisalUpdateTaskInstanceBpmRequest updateTaskInstanceRequest, String tenantId,BpmTaskRequest bpmTaskRequest,String userId,String taskStates);
	void getApprovalDetailByLeadId(String tenantId, String leadId);
}
