package com.fusionx.lending.transaction.service;

/**
 * Tax Event Details Mapping
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   11-10-2021      		     FXL-1130	Dilki      Created
 * <p>
 * *******************************************************************************************************
 */
import com.fusionx.lending.transaction.domain.TaxEventDetails;
import com.fusionx.lending.transaction.resource.AddTaxEventDetailsRequestResource;
import com.fusionx.lending.transaction.resource.UpdateTaxEventDetailsRequestResource;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TaxEventDetailsService {

	/**
	 * Returns all Tax Event Details record
	 *
	 * @return the list of Tax Event Details
	 */
	List<TaxEventDetails> findAll();

	/**
	 * Finds the Tax Event Details record by id
	 *
	 * @param id the id of the record
	 * @return the Tax Event Details record
	 */
	Optional<TaxEventDetails> findById(Long id);

	/**
	 * Finds the Tax Event Details record by code
	 *
	 * @param code the code of the record
	 * @return the Tax Event Details record
	 */
	List<TaxEventDetails> findByCode(String code);

	/**
	 * Finds the Tax Event Details record by name
	 *
	 * @param name the name of the record
	 * @return the Tax Event Details record
	 */
	Optional<TaxEventDetails> findByName(String name);

	/**
	 * Find Tax Event Details by status
	 *
	 * @return JSON array of Tax Event Details
	 */
	List<TaxEventDetails> findByStatus(String status);

	/**
	 * Find Tax Event Details by Tax Event Id
	 *
	 * @return JSON array of Tax Event Details
	 */
	List<TaxEventDetails> findByTaxEventId(String taxEventId);

	/**
	 * Insert Tax Event Details
	 *
	 * @param tenantId                   the tenant id
	 * @param taxEventDetailsAddResource the resource file
	 * @return Successfully saved
	 */
	TaxEventDetails addTaxEventDetails(String tenantId, AddTaxEventDetailsRequestResource taxEventDetailsAddResource);

	/**
	 * Update Tax Code
	 *
	 * @param taxEventDetailsUpdateResource the resource file
	 * @return Successfully saved
	 */
	TaxEventDetails updateTaxEventDetails(String tenantId,
			UpdateTaxEventDetailsRequestResource taxEventDetailsUpdateResource, Long id);

}
