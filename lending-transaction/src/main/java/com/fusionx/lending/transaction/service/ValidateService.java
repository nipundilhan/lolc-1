package com.fusionx.lending.transaction.service;

import java.sql.Timestamp;

/**
 * Standing Order Type service
 * @author 	Nisalak
 * @Dat     01-11-2019
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-11-2019   	FX-1505       FX-1125    Nisalak      Created
 *  
 *    
 ********************************************************************************************************
 */

import org.springframework.stereotype.Service;

import com.fusionx.lending.transaction.enums.WorkflowType;
import com.fusionx.lending.transaction.resource.AccountServiceResource;
import com.fusionx.lending.transaction.resource.AccountStatusResource;
import com.fusionx.lending.transaction.resource.AccountValidateResource;
import com.fusionx.lending.transaction.resource.ApplicationFrequencyResponse;
import com.fusionx.lending.transaction.resource.TaxCodeResponse;
import com.fusionx.lending.transaction.resource.UserProfileResource;
import com.fusionx.lending.transaction.resource.WorkflowInitiationRequestResource;
import com.fusionx.lending.transaction.resource.WorkflowRulesResource;

@Service
public interface ValidateService {

    Object getDetailFromService(String tenantId, Long id, String serviceUrl, Class<?> classObject);

    // Object getDetailFromServiceByStatus(String tenantId, String status, String
    // serviceUrl, Class<?> classObject);

    Object getAccountStatus(String tenantId, String serviceUrl, Class<?> classObject);

    boolean validateAccountStatus(String tenantId, String status);

    Object findByUserId(String tenantId, String serviceUrl, String userName, Class<?> classObject);

    // public void validateUserId(String tenantId, String urlUserRestrictedAccount,
    // String userName);

    public AccountServiceResource validateAccount(String tenantId, AccountValidateResource accountValidateResource);

    /**
     * @author Dilki
     */
    TaxCodeResponse validateTaxCode(String tenantId, String taxCodeId, String taxCodeName);

    ApplicationFrequencyResponse validateApplicationFrequency(String tenantId, String applicationFrequencyId);

    Object findBydesignationId(String tenantId, String urlUserRestrictedAccount, Long designation,
                               Class<?> classObject);

    Object findBydesignation(String tenantId, String serviceUrl, Long designationId, Class<?> classObject);
    
    /**
     * Invoked Lending Product Rule
     *
     * @param workflowType the workflow type
     * @param domainPath   the domain path
     * @param domainName   the domain name
     * @param tenantId     the tenant id
     * @return the Workflow rules
     * @author Dilhan
     */
    WorkflowRulesResource invokedLendingProductRule(WorkflowType workflowType, String domainPath, String domainName, String tenantId);


    /**
     * Initiate the given Lending Product workflow
     *
     * @param workflowInitiationRequestResource the workflow request
     * @param tenantId                          the tenant id
     * @return the id of the initiated workflow.
     * @author Dilhan
     */
    Long initiateLendingProductWorkFlow(WorkflowInitiationRequestResource workflowInitiationRequestResource, String tenantId);

    /**
     * Abort the given Lending Product WorkFlow
     *
     * @param workflowProcessId the workflow process id
     * @param workflowType      the workflow type
     * @param tenantId          the tenant id
     * @param username          the user name
     * @author Dilhan
     */
    void abortedWorkFlow(Long workflowProcessId, WorkflowType workflowType, String username, String tenantId);

    /**
     * Approve the given Lending Product WorkFlow
     *
     * @param workflowProcessId the workflow process id
     * @param workflowType      the workflow type
     * @param tenantId          the tenant id
     * @param username          the user name
     * @author Dilhan
     */
    void approveWorkFlow(Long workflowProcessId, WorkflowType workflowType, String username, String tenantId);

    AccountStatusResource validateAccountStatus(String tenantId, Long accountStatusId, String accountStatusName); 

	public Timestamp getSystemDate();
	
	UserProfileResource validateUserProfileByUserId(String tenantId, String userId, String userName);


}
