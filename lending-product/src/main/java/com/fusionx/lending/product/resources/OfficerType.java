package com.fusionx.lending.product.resources;

import com.fusionx.lending.product.enums.ServiceStatus;

/**
 * Legal Notification Process Setup Service Implementation
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   19-08-2021							SewwandiH    Officer type. 
 *    
 ********************************************************************************************************
 */
public class OfficerType {
	
	private String desgCode;
	private String desgName;
	private ServiceStatus serviceStatus;
	
	public String getDesgCode() {
		return desgCode;
	}
	public void setDesgCode(String desgCode) {
		this.desgCode = desgCode;
	}
	public String getDesgName() {
		return desgName;
	}
	public void setDesgName(String desgName) {
		this.desgName = desgName;
	}
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

}
