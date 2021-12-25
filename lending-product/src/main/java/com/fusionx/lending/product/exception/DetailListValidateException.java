package com.fusionx.lending.product.exception;

import com.fusionx.lending.product.enums.ServicePoint;
import com.fusionx.lending.product.enums.ServiceEntity;

public class DetailListValidateException extends RuntimeException{

	private final ServiceEntity serviceEntity;
	private final ServicePoint servicePoint;
	private final Integer index;
    public DetailListValidateException(String exception, ServiceEntity serviceEntity, ServicePoint servicePoint, Integer index) {
        super(exception);
        this.serviceEntity=serviceEntity;
        this.servicePoint=servicePoint;
        this.index=index;
    }
    public ServiceEntity getServiceEntity() {
        return serviceEntity;
    }
    public ServicePoint getServicePoint() {
        return servicePoint;
    }
    public Integer getIndex() {
		return index;
	}
}
