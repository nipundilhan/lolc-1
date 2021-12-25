package com.fusionx.lending.origination.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.enums.TransferType;
import com.fusionx.lending.origination.resource.IdentificationDetailResource;

@Service
public interface IdentificationService {



	void setIdentification(List<IdentificationDetailResource> identifications, String tenantId, String userName,
			Customer customer, Guarantor guarantor);

	public void identificationValidationCrib(IdentificationDetailResource identificationDetailRes, String tenantId,
			TransferType transferType, Integer index);
	

}
