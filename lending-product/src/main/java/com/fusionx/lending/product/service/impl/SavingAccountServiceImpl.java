package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.config.CommonModuleProperties;
import com.fusionx.lending.product.core.DefaultRequestHeaders;
import com.fusionx.lending.product.resources.*;
import com.fusionx.lending.product.service.RemoteService;
import com.fusionx.lending.product.service.SavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


/**
 * Saving's Account service
 *
 * @author Thushan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   07-07-2021        -           -      Thushan       Created
 * <p>
 * *******************************************************************************************************
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class SavingAccountServiceImpl implements SavingAccountService {

    private CommonModuleProperties commonModuleProperties;
    private RestTemplate restTemplate;
    private RemoteService remoteService;

    @Autowired
    public void setRemoteService(RemoteService remoteService) {
        this.remoteService = remoteService;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setCommonModuleProperties(CommonModuleProperties commonModuleProperties) {
        this.commonModuleProperties = commonModuleProperties;
    }

    /**
     * create saving account into CASA service
     *
     * @param tenantId                 the id of the tenant
     * @param savingAccountAddResource the savingAccountAddResource
     */
    @Override
    public Object addSavingAccount(String tenantId, SavingAccountAddResource savingAccountAddResource) {
        ProductRequestResponseResource productRequest;
        SubProductRequestResponseResource subProductRequest;


        subProductRequest = remoteService.getSubProductIdById(tenantId, String.valueOf(savingAccountAddResource.getSubProductId()), commonModuleProperties.getLendingProduct());
        productRequest = remoteService.getProductById(tenantId, String.valueOf(savingAccountAddResource.getProductId()), commonModuleProperties.getLendingProduct());

        /*savingAccountAddResource.setProductCode(productRequest.getCode());
        savingAccountAddResource.setSubProductCode(subProductRequest.getProductCode());
*/
        return remoteService.addSavingAccount(tenantId, commonModuleProperties.getCaseAccount(), savingAccountAddResource);

    }

}
