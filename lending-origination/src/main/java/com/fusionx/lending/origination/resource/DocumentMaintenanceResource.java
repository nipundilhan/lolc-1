package com.fusionx.lending.origination.resource;

import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceStatus;

/**
 * Document Maintenance Response
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   29-09-2021      		     			SewwandiH     Created
 *    
 ********************************************************************************************************
 */
public class DocumentMaintenanceResource {
	
	private String id;
	private String code;
	private String name;
	private CommonStatus status;
	private ServiceStatus serviceStatus;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CommonStatus getStatus() {
		return status;
	}
	public void setStatus(CommonStatus status) {
		this.status = status;
	}
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	
}
