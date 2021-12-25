package com.fusionx.lending.product.resources;

import com.fusionx.lending.product.enums.ServiceStatus;

/**
 * Residency eligibility service
 * @author 	Rangana
 * @Dat     08-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-06-2021     FX-6        FX-6524    Rangana      Created
 *    
 ********************************************************************************************************
 */

public class Country {
	
	private String geohiCode;
	private String geohiName;
	private ServiceStatus serviceStatus;
	
	public String getGeohiCode() {
		return geohiCode;
	}
	public void setGeohiCode(String geohiCode) {
		this.geohiCode = geohiCode;
	}
	public String getGeohiName() {
		return geohiName;
	}
	public void setGeohiName(String geohiName) {
		this.geohiName = geohiName;
	}
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	

	
}
