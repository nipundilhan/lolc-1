package com.fusionx.lending.transaction.service;

import com.fusionx.lending.transaction.domain.TaxCode;
import com.fusionx.lending.transaction.resource.AddTaxCodeRequestResource;
import com.fusionx.lending.transaction.resource.UpdateTaxCodeRequestResource;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Tax Code Mapping
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   11-10-2021      		     FXL-1130	Dilki      Created
 * <p>
 * *******************************************************************************************************
 */

@Service
public interface TaxCodeService {

	/**
	 * Returns all Tax Code record
	 *
	 * @return the list of Tax Code
	 */
	List<TaxCode> findAll();

	/**
	 * Finds the Tax Code record by id
	 *
	 * @param id the id of the record
	 * @return the Tax Code record
	 */
	Optional<TaxCode> findById(Long id);

	/**
	 * Finds the Tax Code record by code
	 *
	 * @param code the code of the record
	 * @return the Tax Code record
	 */
	Optional<TaxCode> findByCode(String code);

	/**
	 * Finds the Tax Code record by name
	 *
	 * @param name the name of the record
	 * @return the Tax Code record
	 */
	Optional<TaxCode> findByName(String name);

	/**
	 * Find Tax Code by status
	 *
	 * @return JSON array of Tax Code
	 */
	List<TaxCode> findByStatus(String status);

	/**
	 * Find Tax Code by BankTransactionCode
	 *
	 * @return JSON array of Tax Code
	 */
	List<TaxCode> findByBankTransactionCodeId(String bankTransactionCode);

	/**
	 * Find Tax Code by bankTransactionSubCode
	 *
	 * @return JSON array of Tax Code
	 */
	List<TaxCode> findByBankTransactionSubCode(String bankTransactionSubCode);

	/**
	 * Insert Tax Code
	 *
	 * @param tenantId           the tenant id
	 * @param taxCodeAddResource the resource file
	 * @return Successfully saved
	 */
	TaxCode addTaxCode(String tenantId, AddTaxCodeRequestResource taxCodeAddResource);

	/**
	 * Update Tax Code
	 *
	 * @param taxCodeUpdateResource the resource file
	 * @return Successfully saved
	 */
	TaxCode updateTaxCode(String tenantId, UpdateTaxCodeRequestResource taxCodeUpdateResource, Long id);

}
