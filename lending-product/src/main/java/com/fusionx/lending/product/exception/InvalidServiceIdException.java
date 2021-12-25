package com.fusionx.lending.product.exception;

import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;

public class InvalidServiceIdException extends RuntimeException{
	private final ServiceEntity serviceEntity;
	private final EntityPoint entityPoint;
	public InvalidServiceIdException(String exception, ServiceEntity serviceEntity, EntityPoint entityPoint) {
		super(exception);
		this.serviceEntity=serviceEntity;
		this.entityPoint=entityPoint;
	}
	public ServiceEntity getServiceEntity() {
		return serviceEntity;
	}
	public EntityPoint getEntityPoint() {
		return entityPoint;
	}
}
