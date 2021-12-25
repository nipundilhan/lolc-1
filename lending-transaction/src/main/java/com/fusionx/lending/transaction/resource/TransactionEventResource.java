package com.fusionx.lending.transaction.resource;

import com.fusionx.lending.transaction.enums.ServiceStatus;

/**
 * Transaction Event Resource
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

public class TransactionEventResource {

    private String code;
    private String description;
    private String status;
    private ServiceStatus serviceStatus;

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
