package com.fusionx.lending.product.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.AgeEligibility;
import com.fusionx.lending.product.resources.AgeEligibilityAddResource;
import com.fusionx.lending.product.resources.AgeEligibilityUpdateResource;


/**
 * API Service related to Age eligibility.
 *
 * @author Menuka Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        17-07-2021      -               -           Menuka Jayasinghe      Created
 * <p>
 *
 */
@Service
public interface AgeEligibilityService {

    /**
     * Returns the all age eligibility.
     *
     * @return the list of age eligibility
     */
    List<AgeEligibility> findAll();

    /**
     * Gets the age eligibility by id
     *
     * @param id the id of the record
     * @return the age eligibility
     */
    Optional<AgeEligibility> findById(Long id);

    /**
     * Returns the age eligibility by status
     *
     * @param status the status <code>ACTIVE | INACTIVE </code>
     * @return the list of age eligibility
     */
    List<AgeEligibility> findByStatus(String status);

    /**
     * Saves the age eligibility
     *
     * @param tenantId                  the tenant id
     * @param ageEligibilityAddResource the object to save
     * @return the age eligibility
     */
    AgeEligibility addAgeEligibility(String tenantId, AgeEligibilityAddResource ageEligibilityAddResource);

  
    /**
     * Update age eligibility.
     *
     * @param tenantId the tenant id
     * @param ageEligibility the age eligibility
     * @param ageEligibilityUpdateResource the age eligibility update resource
     * @param username the username
     * @return the age eligibility
     */
    AgeEligibility updateAgeEligibility(String tenantId, AgeEligibility ageEligibility, AgeEligibilityUpdateResource ageEligibilityUpdateResource, String username);

}
