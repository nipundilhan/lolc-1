package com.fusionx.lending.transaction.resource;

import com.fusionx.lending.transaction.enums.ServiceEntity;

/**
 * Transaction Event Validate Resource
 *
 * @author Senitha
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   17-02-2020   FX-3038       FX-2961    Senitha      Created
 * <p>
 * *******************************************************************************************************
 */

public class TransactionEventValidateResource {

    private String id;
    private String code;
    private ServiceEntity serviceEntity;

    public ServiceEntity getServiceEntity() {
        return serviceEntity;
    }

    public void setServiceEntity(ServiceEntity serviceEntity) {
        this.serviceEntity = serviceEntity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
