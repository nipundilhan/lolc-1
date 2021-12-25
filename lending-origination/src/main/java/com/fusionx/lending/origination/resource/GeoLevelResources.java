package com.fusionx.lending.origination.resource;

import com.fusionx.lending.origination.enums.ServiceStatus;

public class GeoLevelResources {

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
