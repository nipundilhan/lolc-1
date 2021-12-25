package com.fusionx.lending.origination.service;

import java.util.List;

import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.domain.LinkedPerson;
import com.fusionx.lending.origination.resource.ContactDetailsResource;

public interface ContactDetailService {


	void setContact(List<ContactDetailsResource> contacts, String tenantId, String userName, Customer customer,
			Guarantor guarantor);

}
