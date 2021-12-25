package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.resources.TaxCalculationResource;
import com.fusionx.lending.product.resources.TaxCustomerResponse;
import com.fusionx.lending.product.resources.TaxDetailsResponse;

@Service
public interface TaxCalculationService {
	
	List<TaxDetailsResponse> calculateTaxDetailsResponse(String tenantId , TaxCalculationResource taxCalculationResource);

	
	Optional<TaxCustomerResponse> findCustomerDetails(String leadId , String tenantId);
}
