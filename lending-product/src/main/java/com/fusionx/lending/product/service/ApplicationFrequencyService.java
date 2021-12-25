package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.ApplicationFrequency;
import com.fusionx.lending.product.resources.ApplicationFrequencyAddResource;
import com.fusionx.lending.product.resources.ApplicationFrequencyUpdateResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * API Service related to Application Frequency.
 *
 * @author Senitha Perera
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        04-06-2020      -               FX-6511             Senitha Perera          Created
 * <p>
 */
@Service
public interface ApplicationFrequencyService {

    /**
     * Gets the all application frequencies
     *
     * @param tenantId the tenant id
     * @return the list of application frequencies
     */
    List<ApplicationFrequency> getAll(String tenantId);

    /**
     * Gets the application frequency by id
     *
     * @param tenantId the tenant id
     * @param id       the id
     * @return the application frequency
     */
    Optional<ApplicationFrequency> getById(String tenantId, Long id);

    /**
     * Gets the application frequency by code
     *
     * @param tenantId the tenant id
     * @param code     the code
     * @return the application frequency
     */
    Optional<ApplicationFrequency> getByCode(String tenantId, String code);

    /**
     * Get the application frequency by name
     *
     * @param tenantId the tenant id
     * @param name     the name
     * @return the application frequency
     */
    List<ApplicationFrequency> getByName(String tenantId, String name);

    /**
     * Gets the application frequency by status
     *
     * @param tenantId the tenant id
     * @param status   the status
     * @return the application frequency
     */
    List<ApplicationFrequency> getByStatus(String tenantId, String status);

    /**
     * Saves the application frequency
     *
     * @param tenantId                        the tenant id
     * @param addApplicationFrequencyResource the object to save
     * @return the saved record
     */
    ApplicationFrequency addApplicationFrequency(String tenantId, ApplicationFrequencyAddResource addApplicationFrequencyResource);

    /**
     * Updates the application frequency
     *
     * @param tenantId                           the tenant id
     * @param updateApplicationFrequencyResource the object to update
     * @return the updated record.
     */
    ApplicationFrequency updateApplicationFrequency(String tenantId, ApplicationFrequencyUpdateResource updateApplicationFrequencyResource);

}
