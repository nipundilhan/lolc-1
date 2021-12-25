package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ReferenceDetails;
import com.fusionx.lending.origination.resource.ReferenceDetailsAddResource;
import com.fusionx.lending.origination.resource.ReferenceDetailsUpdateResource;

@Service
public interface ReferenceDetailsService {

	public Optional<ReferenceDetails> findById(Long id);

	public List<ReferenceDetails> findByStatus(String status);

	public ReferenceDetails addReferenceDetails(String tenantId,
			ReferenceDetailsAddResource referenceDetailsAddResource);

	public ReferenceDetails updateReferenceDetails(String tenantId, Long id,
			ReferenceDetailsUpdateResource referenceDetailsUpdateResource);

	public List<ReferenceDetails> getByLeadId(Long leadInfoId);

	public List<ReferenceDetails> findAll();

}
