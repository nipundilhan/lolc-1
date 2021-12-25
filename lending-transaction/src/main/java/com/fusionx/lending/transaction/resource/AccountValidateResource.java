package com.fusionx.lending.transaction.resource;

import com.fusionx.lending.transaction.enums.ServiceEntity;

/**
 * Standing Order Type service
 *
 * @author Nisalak
 * @Dat 01-11-2019
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   01-11-2019   FX-1505       FX-1125    Nisalak      Created
 * <p>
 * *******************************************************************************************************
 */


public class AccountValidateResource {

    private Long accountId;
    private String accountNo;
    private ServiceEntity serviceEntity;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public ServiceEntity getServiceEntity() {
        return serviceEntity;
    }

    public void setServiceEntity(ServiceEntity serviceEntity) {
        this.serviceEntity = serviceEntity;
    }


}
