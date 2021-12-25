package com.fusionx.lending.product.exception;

import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.ServicePoint;

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
