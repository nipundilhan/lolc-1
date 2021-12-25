package com.fusionx.lending.product.service;

import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.ServiceStatus;
import com.fusionx.lending.product.resources.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * API Service related to Remote API Calls.
 *
 * @author Thushan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author          Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        08-10-2021         -               -         Thushan          Created
 * <p>
 */
@Service
public interface RemoteService {

	 public void serviceValidationExceptionHadle(ServiceStatus serviceStatus, ServiceEntity serviceEntity, EntityPoint entityPoint);

	 Object checkExist(String tenantId, String id, String serviceUrl, Class<?> classObject);

    /**
     *Check is exist entity in another service
     *Support only HTTP get method
     *
     * @param tenantId     the tenant id
     * @param id           the related id
     * @Param serviceUrl    the relevant service URL
     * @return Object
     */
    Boolean checkIsExist(String tenantId, String id, String serviceUrl);

    /**
     *Check is exist entity in another service
     *Support only HTTP get method
     *
     * @param tenantId     the tenant id
     * @param id           the related id
     * @Param serviceUrl    the relevant service URL
     * @return Object
     */
    Boolean checkIsExistWithStatus(String tenantId, String id, String serviceUrl, String status);

    /**
     * Retrieve product using Product Id
     *
     * @param tenantId the tenant id
     * @param productId the product id
     * @param serviceUrl the lending-origination service url
     * @return ProductRequestResponseResource
     */
    ProductRequestResponseResource getProductById(String tenantId, String productId, String serviceUrl);

    /**
     * Retrieve product using Product Id
     *
     * @param tenantId the tenant id
     * @param subProductId the sub product id
     * @param serviceUrl the lending-origination service url
     * @return SubProductRequestResponseResource
     */
    SubProductRequestResponseResource getSubProductIdById(String tenantId, String subProductId, String serviceUrl);

    /**
     * Retrieve bank branch using Id
     *
     * @param tenantId the tenant id
     * @param branchId the branch id
     * @param serviceUrl the comon-comon service URL
     * @return BankBranchRequestResponseResource
     */
    BankBranchRequestResponseResource getBranchById(String tenantId, String branchId, String serviceUrl);

    /**
     * Retrieve bank account by Id
     *
     * @param tenantId the tenant id
     * @param accountId the account id
     * @param serviceUrl the casa service URL
     * @return Object
     */
    Object getAccountById(String tenantId, String accountId, String serviceUrl);

    /**
     *Retrieve bank  using Id
     *
     * @param tenantId the tenant id
     * @param bankId  the bank id
     * @param serviceUrl the comon-comon service URL
     * @return BankRequestResponseResource
     */
    BankRequestResponseResource getBankById(String tenantId, String bankId, String serviceUrl);

    /**
     *Retrieve lead information using lead id
     *
     * @param tenantId the tenant id
     * @param leadId the lead id
     * @param serviceUrl the lending-origination service url
     * @return LeadInfoRequestResponseResource
     */
    LeadInfoRequestResponseResource getLeadInfoById(String tenantId, String leadId, String serviceUrl);

    /**
     *Automated savings account creating
     *
     * @param tenantId the tenant id
     * @param savingAccountAddResource the automated-savings-account object
     * @param serviceUrl the casa service URL
     */
    SavingAccountResponseResource addSavingAccount(String tenantId, String serviceUrl, SavingAccountAddResource savingAccountAddResource);

    /**
     * Retrieve lead information by lead status
     *
     * @param tenantId the tenant id
     * @param serviceUrl  the lending-organization service url
     * @param leadStatus the leadStatus
     * @return get all lead information by lead status
     */
    List<LeadInfoRequestResponseResource> getAllAppraisalByLeadsStatus(String tenantId, String serviceUrl, String leadStatus);

    /**
     *
     * @param tenantId the tenant id
     * @param serviceUrl the lending-origination service url
     * @param leadId the lead id
     * @return GuarantorRequestResponseResource
     */
    GuarantorRequestResponseResource getGuarantorByLeadId(String tenantId, String serviceUrl, String leadId);

    /**
     * Retrieve TcHeaderRequestResponseResource using tc id
     *
     * @param tenantId   the tenant id
     * @param tcId       the lead id
     * @param serviceUrl the tc header service url
     * @return TcHeaderRequestResponseResource
     */
    TcHeaderRequestResponseResource getTcHeaderById(String tenantId, String tcId, String serviceUrl);

}
