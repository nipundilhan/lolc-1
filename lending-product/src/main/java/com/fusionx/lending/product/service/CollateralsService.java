package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.Collaterals;
import com.fusionx.lending.product.resources.CollateralsAddResource;
import com.fusionx.lending.product.resources.CollateralsUpdateResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * API Service related to Collaterals.
 *
 * @author Thushan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #          Date            Story Point     Task No       Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        26-10-2021          -               -           Thushan                  Created
 * <p>
 */
@Service
public interface CollateralsService {

    /**
     *  Returns the borrowers by lending account id
     *
     * @param lendingAccountId the id of the lending account
     * @return the Object of  collaterals.
     */
    Collaterals findByLendingAccountId(Long lendingAccountId);

    /**
     *  Returns the collaterals by status
     *
     * @param status  the status <code>ACTIVE | INACTIVE </code>
     * @return the list of collaterals.
     */
    List<Collaterals> findByStatus(String status);

    /**
     *  Gets the collaterals by id
     *
     * @param id the id of the record
     * @return the set of Collaterals
     */
    Optional<Collaterals> findById(Long id);

    /**
     * Create Collaterals into DB
     *
     * @param tenantId the tenant id
     * @param collateralsAddResource the object to save
     * @return the saved collaterals.
     */
    Collaterals addCollaterals(String tenantId, CollateralsAddResource collateralsAddResource);

    /**
     *
     * @param tenantId the tenant id
     * @param id  the id need to be update
     * @param collateralsUpdateResource the object which contains data
     * @return the updated collaterals.
     */
    Collaterals updateCollaterals(String tenantId, Long id, CollateralsUpdateResource collateralsUpdateResource);

    /**
     * return all Collaterals
     *
     * @return list of Collaterals
     */
    List<Collaterals> getAll();
}
