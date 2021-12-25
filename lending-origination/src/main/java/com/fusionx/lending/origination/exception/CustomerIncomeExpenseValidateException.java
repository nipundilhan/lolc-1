package com.fusionx.lending.origination.exception;

import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;

public class CustomerIncomeExpenseValidateException extends RuntimeException{

	private final ServiceEntity serviceEntity;
	private final ServicePoint servicePoint;
	private final Integer index;
	private final Integer index1;
    public CustomerIncomeExpenseValidateException(String exception, ServiceEntity serviceEntity, ServicePoint servicePoint, Integer index, Integer index1) {
        super(exception);
        this.serviceEntity=serviceEntity;
        this.servicePoint=servicePoint;
        this.index=index;
        this.index1=index1;
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
	public Integer getIndex1() {
		return index1;
	}
}
