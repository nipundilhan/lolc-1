package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.Eligibility;
import com.fusionx.lending.product.domain.EligibilityPending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.EligibilityAddResource;
import com.fusionx.lending.product.resources.EligibilityUpdateResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * API Service related to Eligibility.
 *
 * @author Menuka Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        10-06-2021    	-               		             Menuka Jayasinghe		Created
 * <p>
 */
@Service
public interface EligibilityService {

    /**
     * Find all eligibilities
     *
     * @return the list of eligibilities
     */
    List<Eligibility> getAll();

    /**
     * Find eligibility by id
     *
     * @param id the id of the record
     * @return the eligibility record
     */
    Optional<Eligibility> getById(Long id);

    /**
     * Find eligibility by code
     *
     * @param code the code of the eligibility
     * @return the eligibility
     */
    Optional<Eligibility> getByCode(String code);

    /**
     * Returns the eligibility by name
     *
     * @param name the name of the eligibility
     * @return the list of eligibilities
     */
    List<Eligibility> getByName(String name);

    /**
     * Returns the eligibility by status
     *
     * @param status the static
     * @return the list of eligibilities
     */
    List<Eligibility> getByStatus(String status);


    /**
     * Saves eligibilities
     *
     * @param tenantId               the tenant id
     * @param eligibilityAddResource the eligibility resource
     * @return the eligibility record
     */
    Eligibility addEligibility(String tenantId, EligibilityAddResource eligibilityAddResource);

    /**
     * Updates the given eligibility record
     *
     * @param tenantId                  the tenant id
     * @param id                        the id of the record
     * @param eligibilityUpdateResource the resource contains update details
     * @return the updated record
     */
    Eligibility updateEligibility(String tenantId, Long id, EligibilityUpdateResource eligibilityUpdateResource);

    /**
     * Approve or Reject the record
     *
     * @param tenantId       the tenant id
     * @param id             the id of the record
     * @param workflowStatus the workflow status
     * @return <code>true</code> if successfully approved, otherwise <code>false</code>
     */
    boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus);


    /**
     * Returns the Pending eligibility record by its id
     *
     * @param pendingId the id of the pending record
     * @return the pending object.
     */
    Optional<EligibilityPending> getByPendingId(Long pendingId);

    /**
     * Returns the pageable response by given parameters
     *
     * @param searchQ       the search query
     * @param status        the status
     * @param approveStatus the approve status
     * @param pageable      the pageable details
     * @return the pageable records.
     */
    Page<EligibilityPending> searchEligibilityPending(String searchQ, String status, String approveStatus, Pageable pageable);
}
