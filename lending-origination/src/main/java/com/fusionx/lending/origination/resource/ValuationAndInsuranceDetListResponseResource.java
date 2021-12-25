package com.fusionx.lending.origination.resource;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.enums.ServiceStatus;

import lombok.Data;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ValuationAndInsuranceDetListResponseResource {
	
	private ServiceStatus serviceStatus;
	
	List<ValuationDetailsRequestResource> valuationDetails;
	
	List<InsuranceDetailsRequestResource> insuranceDetails;

	public List<ValuationDetailsRequestResource> getValuationDetails() {
		return valuationDetails;
	}

	public void setValuationDetails(List<ValuationDetailsRequestResource> valuationDetails) {
		this.valuationDetails = valuationDetails;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public List<InsuranceDetailsRequestResource> getInsuranceDetails() {
		return insuranceDetails;
	}

	public void setInsuranceDetails(List<InsuranceDetailsRequestResource> insuranceDetails) {
		this.insuranceDetails = insuranceDetails;
	}

	

}
