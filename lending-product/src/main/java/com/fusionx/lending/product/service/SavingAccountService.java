package com.fusionx.lending.product.service;

import com.fusionx.lending.product.resources.SavingAccountAddResource;
import org.springframework.stereotype.Service;

/**
 * Saving's Account service
 * @author 	Thushan
 * @Date     14-10-2021
 * @version 1.0.0
 * @since 1.0.0
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021        -           -      Thushan       Created
 *
 ********************************************************************************************************
 */
@Service
public interface SavingAccountService {

    /**
     * create saving account into CASA service
     *
     * @param tenantId the id of the tenant
     * @param savingAccountAddResource the savingAccountAddResource
     */
    Object addSavingAccount(String tenantId, SavingAccountAddResource savingAccountAddResource);
}