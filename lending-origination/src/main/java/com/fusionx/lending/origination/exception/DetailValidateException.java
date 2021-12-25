package com.fusionx.lending.origination.exception;

import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;

public class DetailValidateException extends RuntimeException{

	private final ServiceEntity serviceEntity;
	private final ServicePoint servicePoint;
    public DetailValidateException(String exception, ServiceEntity serviceEntity, ServicePoint servicePoint) {
        super(exception);
        this.serviceEntity=serviceEntity;
        this.servicePoint=servicePoint;
    }
    public ServiceEntity getServiceEntity() {
        return serviceEntity;
    }
    public ServicePoint getServicePoint() {
        return servicePoint;
    }
}
