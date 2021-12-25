package com.fusionx.lending.product.resources;

import com.fusionx.lending.product.enums.ServiceStatus;
/** 
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-10-2021			 			    Dilki      Created
 *    
 ********************************************************************************************************
 */

public class ComnSector {

	private String id;
	private String sectorCode;
	private String sectorName;
	private String sectorStatus;
	private ServiceStatus serviceStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
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

}
