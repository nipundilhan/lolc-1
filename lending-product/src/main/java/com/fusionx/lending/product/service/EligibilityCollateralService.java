package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.EligibilityCollateral;
import com.fusionx.lending.product.domain.EligibilityCollateralPending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.EligibilityCollateralAddResource;
import com.fusionx.lending.product.resources.EligibilityCollateralUpdateResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * API Service related to Eligibility Collateral.
 *
 * @author Miyuru Wijesinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        01-07-2021    	-               FX-6889             Miyuru Wijesinghe          Created
 * <p>
 */
@Service
public interface EligibilityCollateralService {


    /**
     * Find all.
     *
     * @param tenantId the tenant id
     * @return the list
     */
    List<EligibilityCollateral> findAll(String tenantId);


    /**
     * Find by id.
     *
     * @param tenantId the tenant id
     * @param id       the id
     * @return the optional
     */
    Optional<EligibilityCollateral> findById(String tenantId, Long id);


    /**
     * Find by status.
     *
     * @param tenantId the tenant id
     * @param status   the status
     * @return the list
     */
    List<EligibilityCollateral> findByStatus(String tenantId, String status);


    /**
     * Find by eligibility id.
     *
     * @param tenantId      - the tenant id
     * @param eligibilityId - the eligibility id
     * @return the list
     */
    List<EligibilityCollateral> findByEligibilityId(String tenantId, Long eligibilityId);


    /**
     * Find by asset type id.
     *
     * @param tenantId    the tenant id
     * @param assetTypeId the asset type id
     * @return the list
     */
    List<EligibilityCollateral> findByAssetTypeId(String tenantId, Long assetTypeId);


    /**
     * Save and validate eligibility collateral.
     *
     * @param tenantId                         the tenant id
     * @param createdUser                      the created user
     * @param eligibilityCollateralAddResource the eligibility collateral add resource
     * @return the long
     */
    Long saveAndValidateEligibilityCollateral(String tenantId, String createdUser, EligibilityCollateralAddResource eligibilityCollateralAddResource);


    /**
     * Update and validate eligibility collateral.
     *
     * @param tenantId                            the tenant id
     * @param createdUser                         the created user
     * @param id                                  the id
     * @param eligibilityCollateralUpdateResource - the eligibility collateral update resource
     * @return the long
     */
    Long updateAndValidateEligibilityCollateral(String tenantId, String createdUser, Long id, EligibilityCollateralUpdateResource eligibilityCollateralUpdateResource);


    /**
     * Approve reject.
     *
     * @param tenantId       the tenant id
     * @param id             the id
     * @param workflowStatus the workflow status
     * @return the boolean
     */
    Boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus);


    /**
     * Gets the by pending id.
     *
     * @param pendingId the pending id
     * @return the by pending id
     */
    Optional<EligibilityCollateralPending> getByPendingId(Long pendingId);


    /**
     * Search eligibility collateral.
     *
     * @param searchQ       the search query
     * @param status        the status
     * @param approveStatus the approve status
     * @param pageable      the pageable
     * @return the page
     */
    Page<EligibilityCollateralPending> searchEligibilityCollateral(String searchQ, String status, String approveStatus, Pageable pageable);

}
