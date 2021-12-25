package com.fusionx.lending.origination.exception;

import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;

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
