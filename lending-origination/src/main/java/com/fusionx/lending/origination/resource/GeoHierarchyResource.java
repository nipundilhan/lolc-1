package com.fusionx.lending.origination.resource;

import com.fusionx.lending.origination.enums.ServiceStatus;

public class GeoHierarchyResource {

	private String geohiGeolevelCode;
	private String geohiName;
	private ServiceStatus serviceStatus;
	public String getGeohiGeolevelCode() {
		return geohiGeolevelCode;
	}
	public void setGeohiGeolevelCode(String geohiGeolevelCode) {
		this.geohiGeolevelCode = geohiGeolevelCode;
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
