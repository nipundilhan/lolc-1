package com.fusionx.lending.product.resources;

import com.fusionx.lending.product.enums.ServiceStatus;

/**
 * Tax Profile Service
 * @author 	KilasiG
 * @Date     05-11-2019
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   05-11-2019   FX-1545       FX-2175    KilasiG      Created
 *    
 ********************************************************************************************************
 */

public class CustomerSubTypeInd {

	private String personTypeCode;;
	private String personTypeDescription;
	private String personTypeStatus;
	private ServiceStatus serviceStatus;

	public String getPersonTypeCode() {
		return personTypeCode;
	}

	public void setPersonTypeCode(String personTypeCode) {
		this.personTypeCode = personTypeCode;
	}

	public String getPersonTypeDescription() {
		return personTypeDescription;
	}

	public void setPersonTypeDescription(String personTypeDescription) {
		this.personTypeDescription = personTypeDescription;
	}

	public String getPersonTypeStatus() {
		return personTypeStatus;
	}

	public void setPersonTypeStatus(String personTypeStatus) {
		this.personTypeStatus = personTypeStatus;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
}
