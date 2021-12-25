package com.fusionx.lending.origination.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.domain.LinkedPerson;
import com.fusionx.lending.origination.resource.AddressDetailsResource;

@Service
public interface AddressDetailService {



	void serAddress(List<AddressDetailsResource> addresses, String tenantId, String userName, Customer customer,
			Guarantor guarantor);

}
