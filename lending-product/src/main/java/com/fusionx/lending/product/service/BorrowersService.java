package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.Borrowers;
import com.fusionx.lending.product.resources.BorrowersAddResource;
import com.fusionx.lending.product.resources.BorrowersUpdateResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * API Service related to Borrowers.
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
public interface BorrowersService {


    /**
     * Returns the borrowers by lending account id
     *
     * @param lendingAccountId the id of the lending account
     * @return  the Object of  borrowers.
     */
    List<Borrowers> findByLendingAccountId(Long lendingAccountId);

    /**
     * Returns the borrowers by status
     *
     * @param status the status <code>ACTIVE | INACTIVE </code>
     * @return the list of borrowers
     */
    List<Borrowers> findByStatus(String status);

    /**
     *  Gets the borrowers by id
     *
     * @param id the id of the record
     * @return the set of Borrowers
     */
    Optional<Borrowers> findById(Long id);

    /**
     *Create Borrowers into DB
     *
     * @param tenantId the tenant id
     * @param borrowersAddResource  the object to save
     * @return the saved borrowers.
     */
    Borrowers addBorrowers(String tenantId, BorrowersAddResource borrowersAddResource);

    /**
     * Updates the borrowers object by given object
     *
     * @param tenantId the tenant id
     * @param id the id need to be update
     * @param borrowersUpdateResource  the object which contains data
     * @return the updated borrowers.
     */
    Borrowers updateBorrowers(String tenantId, Long id, BorrowersUpdateResource borrowersUpdateResource);

    /**
     * Returns all borrowers
     *
     * @return the list of borrowers
     */
    List<Borrowers> getAll();
}
