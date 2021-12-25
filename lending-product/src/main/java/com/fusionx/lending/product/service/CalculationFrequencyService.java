package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.CalculationFrequency;
import com.fusionx.lending.product.resources.ApplicationFrequencyAddResource;
import com.fusionx.lending.product.resources.ApplicationFrequencyUpdateResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * API Service related to Calculation Frequency.
 *
 * @author Senitha Perera
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  	Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        04-06-2020      -               FX-6511       		Senitha Perera         		Created
 * <p>
 */
@Service
public interface CalculationFrequencyService {


    /**
     * Returns the all calculation frequencies
     *
     * @param tenantId the tenant id
     * @return the calculation frequencies
     */
    List<CalculationFrequency> getAll(String tenantId);

    /**
     * Returns the calculation frequency by id
     *
     * @param tenantId the tenant id
     * @param id       the id
     * @return the calculation frequency
     */
    Optional<CalculationFrequency> getById(String tenantId, Long id);

    /**
     * Returns the calculation frequency by code
     *
     * @param tenantId the tenant id
     * @param code     the code
     * @return the calculation frequency
     */
    Optional<CalculationFrequency> getByCode(String tenantId, String code);

    /**
     * Returns the calculation frequency by name
     *
     * @param tenantId the tenant id
     * @param name     the name
     * @return the calculation frequency
     */
    List<CalculationFrequency> getByName(String tenantId, String name);

    /**
     * Returns the calculation frequency  by status
     *
     * @param tenantId the tenant id
     * @param status   the status
     * @return the calculation frequency
     */
    List<CalculationFrequency> getByStatus(String tenantId, String status);

    /**
     * Saves the calculation frequency
     *
     * @param tenantId                        the tenant id
     * @param addApplicationFrequencyResource the object to save
     * @return the message.
     */
    CalculationFrequency addCalculationFrequency(String tenantId, ApplicationFrequencyAddResource addApplicationFrequencyResource);

    /**
     * Updates the calculation frequency
     *
     * @param tenantId                           the tenant id
     * @param updateApplicationFrequencyResource the object which contains update date
     * @return the message.
     */
    CalculationFrequency updateCalculationFrequency(String tenantId, ApplicationFrequencyUpdateResource updateApplicationFrequencyResource);

}
