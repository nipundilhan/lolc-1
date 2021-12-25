package com.fusionx.lending.origination.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.domain.LinkedPerson;
import com.fusionx.lending.origination.resource.BlacklistAddResource;

@Service
public interface BlacklistDetailsService {



	void setBlacklist(BlacklistAddResource blacklist, String tenantId, String userName, Customer customer,
			Guarantor guarantor);

}
