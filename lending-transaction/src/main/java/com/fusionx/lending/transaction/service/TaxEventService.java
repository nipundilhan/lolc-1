package com.fusionx.lending.transaction.service;

import com.fusionx.lending.transaction.domain.TaxEvent;
import com.fusionx.lending.transaction.resource.AddTaxEventRequestResource;
import com.fusionx.lending.transaction.resource.UpdateTaxEventRequestResource;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Tax Event
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   12-10-2021   FXL-1234      FXL-1131   Dilki      Created
 * <p>
 * *******************************************************************************************************
 */

@Service
public interface TaxEventService {

    /**
     * Returns all Tax Event record
     *
     * @return the list of Tax Event
     */
    List<TaxEvent> findAll();

    /**
     * Finds the Tax Event record by id
     *
     * @param id the id of the record
     * @return the Tax Event record
     */
    Optional<TaxEvent> findById(Long id);

    /**
     * Finds the Tax Event record by code
     *
     * @param code the code of the record
     * @return the Tax Event record
     */
    Optional<TaxEvent> findByCode(String code);

    /**
     * Finds the Tax Event record by name
     *
     * @param name the name of the record
     * @return the Tax Event record
     */
    Optional<TaxEvent> findByName(String name);

    /**
     * Find Tax Event by status
     *
     * @return JSON array of Tax Event
     */
    List<TaxEvent> findByStatus(String status);

    /**
     * Insert Tax Event
     *
     * @param tenantId            the tenant id
     * @param taxEventAddResource the resource file
     * @return Successfully saved
     */
    TaxEvent addTaxEvent(String tenantId, AddTaxEventRequestResource taxEventAddResource);

    /**
     * Update Tax Event
     *
     * @param taxEventUpdateResource the resource file
     * @return Successfully saved
     */
    TaxEvent updateTaxEvent(String tenantId, UpdateTaxEventRequestResource taxEventUpdateResource, Long id);

}
