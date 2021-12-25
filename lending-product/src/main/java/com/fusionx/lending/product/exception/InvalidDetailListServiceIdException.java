package com.fusionx.lending.product.exception;


import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;

public class InvalidDetailListServiceIdException extends RuntimeException {
	private final ServiceEntity serviceEntity;
	private final EntityPoint entityPoint;
	private final Integer index;
	public InvalidDetailListServiceIdException(String exception, ServiceEntity serviceEntity, EntityPoint entityPoint, Integer index) {
		super(exception);
		this.serviceEntity=serviceEntity;
		this.entityPoint=entityPoint;
		this.index=index;
	}
	public ServiceEntity getServiceEntity() {
		return serviceEntity;
	}
	public EntityPoint getEntityPoint() {
		return entityPoint;
	}
	public Integer getIndex() {
		return index;
	}
}
