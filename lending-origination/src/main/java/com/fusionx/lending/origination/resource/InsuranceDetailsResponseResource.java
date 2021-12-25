package com.fusionx.lending.origination.resource;
import java.util.List;

/**
 * Insurance Details Service
 * @author Sanatha
 * @Date 16-SEP-2020
 *
 *********************************************************************************************************
 *  ###        Date                  Story Point         Task No              Author           Description
 *-------------------------------------------------------------------------------------------------------
 *    1        16-SEP-2020   		 FX-4293             FX-4693              Sanatha         Initial Development.
 *    
 ********************************************************************************************************
 */
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.enums.ServiceStatus;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class InsuranceDetailsResponseResource {
	
	private ServiceStatus serviceStatus;
	
	List<InsuranceDetailsRequestResource> insuranceDetailsRequestResource;

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public List<InsuranceDetailsRequestResource> getInsuranceDetailsRequestResource() {
		return insuranceDetailsRequestResource;
	}

	public void setInsuranceDetailsRequestResource(List<InsuranceDetailsRequestResource> insuranceDetailsRequestResource) {
		this.insuranceDetailsRequestResource = insuranceDetailsRequestResource;
	}

}
