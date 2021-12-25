package com.fusionx.lending.origination.resource;

import com.fusionx.lending.origination.enums.ServiceStatus;

public class DesignationResponse {

	private String id;
	private String desgName;
	private String desgCode;
	private String desgStatus;
	private ServiceStatus serviceStatus;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDesgName() {
		return desgName;
	}
	public void setDesgName(String desgName) {
		this.desgName = desgName;
	}
	public String getDesgCode() {
		return desgCode;
	}
	public void setDesgCode(String desgCode) {
		this.desgCode = desgCode;
	}
	public String getDesgStatus() {
		return desgStatus;
	}
	public void setDesgStatus(String desgStatus) {
		this.desgStatus = desgStatus;
	}
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
}
