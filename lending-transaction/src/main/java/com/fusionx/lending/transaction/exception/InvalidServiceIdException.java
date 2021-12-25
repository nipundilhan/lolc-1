package com.fusionx.lending.transaction.exception;

import com.fusionx.lending.transaction.enums.ServiceEntity;

public class InvalidServiceIdException extends RuntimeException {

    private final ServiceEntity serviceEntity;

    public InvalidServiceIdException(String exception, ServiceEntity serviceEntity) {
        super(exception);
        this.serviceEntity = serviceEntity;
    }

    public ServiceEntity getServiceEntity() {
        return serviceEntity;
    }
}
