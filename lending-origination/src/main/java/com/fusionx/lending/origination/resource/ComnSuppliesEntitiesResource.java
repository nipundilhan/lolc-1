package com.fusionx.lending.origination.resource;

import com.fusionx.lending.origination.enums.ServiceStatus;

public class ComnSuppliesEntitiesResource {
	
	private Long id;
	private String supStatus;
	private String supReferenceCode;
	private String supSuppliesType;
	private String supLawyerType;
	
	private ServiceStatus serviceStatus;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSupStatus() {
		return supStatus;
	}
	public void setSupStatus(String supStatus) {
		this.supStatus = supStatus;
	}
	public String getSupReferenceCode() {
		return supReferenceCode;
	}
	public void setSupReferenceCode(String supReferenceCode) {
		this.supReferenceCode = supReferenceCode;
	}
	public String getSupSuppliesType() {
		return supSuppliesType;
	}
	public void setSupSuppliesType(String supSuppliesType) {
		this.supSuppliesType = supSuppliesType;
	}
	public String getSupLawyerType() {
		return supLawyerType;
	}
	public void setSupLawyerType(String supLawyerType) {
		this.supLawyerType = supLawyerType;
	}
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

}
