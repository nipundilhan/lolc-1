package com.fusionx.lending.origination.resource;

import com.fusionx.lending.origination.enums.ServiceStatus;

public class SectorResponseResource {
	
	private Long id;
	private String sectorCode;
	private String sectorName;
	private String sectorStatus;
	private ServiceStatus serviceStatus;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSectorCode() {
		return sectorCode;
	}
	public void setSectorCode(String sectorCode) {
		this.sectorCode = sectorCode;
	}
	public String getSectorName() {
		return sectorName;
	}
	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}
	public String getSectorStatus() {
		return sectorStatus;
	}
	public void setSectorStatus(String sectorStatus) {
		this.sectorStatus = sectorStatus;
	}
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	
	

}
